/****************************************************************************
 **
 ** This file is part of yFiles FLEX 1.7.
 **
 ** yWorks proprietary/confidential. Use is subject to license terms.
 **
 ** Unauthorized redistribution of this file or of a byte-code version
 ** of this file is strictly forbidden.
 **
 ** Copyright (c) 2006 - 2012 by yWorks GmbH, Vor dem Kreuzberg 28,
 ** 72070 Tuebingen, Germany. All rights reserved.
 **
 ***************************************************************************/
package com.ultrapower.yworks.yfiles.server.orgchart;

import y.base.Edge;
import y.base.EdgeCursor;
import y.base.YList;
import y.layout.LayoutGraph;
import y.geom.YPointPath;
import y.geom.LineSegment;
import y.geom.YPoint;

import java.util.*;

/**
 * @author brunnerm
 */
public class OrgChartBendSmoother {
    // HashMap<YPointPath, HashMap<Integer, LineSegment>> lineSegments
    HashMap lineSegments = new HashMap();

    public void calculateBendSmoothing(LayoutGraph graph) {
        lineSegments.clear();
        HashMap path2Edge = new HashMap(); // HashMap<YPointPath, Edge>

        // HashMap<YPointPath, LinkedList<Integer>>
        HashMap path2newBendPosition = new HashMap();


        HashSet activeLineSegments = new HashSet(); // HashSet<LineSegment>
        HashMap pointsAtX = new HashMap(); // HashMap<Double, LinkedList<UniqueYPoint>>

        HashSet xPositions = new HashSet(); // HashSet<Double>


        EdgeCursor edgeCursor = graph.edges();
        while (edgeCursor.ok()) {
            Edge edge = (Edge) edgeCursor.current();
            YPointPath path = graph.getPath(edge);
            path2Edge.put(path, edge);

            YPoint[] pathPoints = path.toArray();
            for (int i = 0; i < pathPoints.length; i++) {
                YPoint point = pathPoints[i];
                UniqueYPoint uniquePoint = new UniqueYPoint(point, path, i);

                double x = point.getX();
                LinkedList pointsAtThisX; // LinkedList<UniqueYPoint>
                if (pointsAtX.containsKey(new Double(x))) {
                    pointsAtThisX = (LinkedList) pointsAtX.get(new Double(x));
                } else {
                    pointsAtThisX = new LinkedList(); // LinkedList<UniqueYPoint>
                    pointsAtX.put(new Double(x), pointsAtThisX);
                }
                pointsAtThisX.add(uniquePoint);
                xPositions.add(new Double(x));
                
            }

            edgeCursor.next();
        }

        Object[] xPositionObjectArray = xPositions.toArray();
        double[] xPositionArray = new double[xPositionObjectArray.length];
        for (int i = 0; i < xPositionObjectArray.length; i++) {
            xPositionArray[i] = ((Double) xPositionObjectArray[i]).doubleValue();
        }

        java.util.Arrays.sort(xPositionArray);

        // find edge positions to change
        for (int i = 0; i < xPositionArray.length; i++) {
            double x = xPositionArray[i];
            LinkedList pointList = (LinkedList) pointsAtX.get(new Double(x)); // LinkedList<UniqueYPoint>

            //first: add all LineSegments lying on this x position or to it's right and starting/ending at this x position
            Iterator pointIt = pointList.iterator(); // Iterator<UniqueYPoint>
            while (pointIt.hasNext()) {
                UniqueYPoint actPoint = (UniqueYPoint) pointIt.next();
                int position = actPoint.position;
                YPointPath path = actPoint.path;
                if (position > 0) {
                    LineSegment prevSegment = getLineSegment(path, position - 1);
                    if (prevSegment.getFirstEndPoint().x >= x) {
                        activeLineSegments.add(prevSegment);
                    }
                }
                if (position < path.length() - 1) {
                    LineSegment nextSegment = getLineSegment(path, position);
                    if (nextSegment.getSecondEndPoint().x >= x) {
                        activeLineSegments.add(nextSegment);
                    }
                }
            }

            // second: check if any inner YPoints lie on the inner segment of an active LineSegment
            pointIt = pointList.iterator();
            nextPointToCheck: while (pointIt.hasNext()) {
                UniqueYPoint actPoint = (UniqueYPoint) pointIt.next();
                if (actPoint.position == 0
                    || actPoint.position == actPoint.path.length() - 1) {
                    continue nextPointToCheck;
                }
                LinkedList segmentsEndInPoint = new LinkedList(); // LinkedList<LineSegment>
                Iterator lineIt = activeLineSegments.iterator(); // Iterator<LineSegment>
                while (lineIt.hasNext()) {
                    LineSegment segment = (LineSegment) lineIt.next();
                    if (segment.contains(actPoint.yPoint)) {
                        if (segment.getFirstEndPoint().compareTo(actPoint.yPoint) != 0
                            && segment.getSecondEndPoint().compareTo(actPoint.yPoint) != 0) {
                            YPointPath path = actPoint.path;
                            LinkedList newBendPositions = (LinkedList) path2newBendPosition.get(path); // LinkedList<Integer>
                            if (newBendPositions == null) {
                                newBendPositions = new LinkedList(); // LinkedList<Integer>
                                path2newBendPosition.put(path, newBendPositions);
                            }
                            int position = actPoint.position;
                            newBendPositions.add(new Integer(position));
                            continue nextPointToCheck;
                        } else {
                            segmentsEndInPoint.add(segment);
                        }
                    }
                }
                if (segmentsEndInPoint.size() > 2) {
                    // check if there are only two directions for the outgoing segments

                    boolean verticalUp = false;
                    boolean verticalDown = false;
                    double[] degrees = new double[3];
                    boolean[] inQuad1or4 = new boolean[3];
                    int numberOfDegrees = 0;

                    Iterator segIt = segmentsEndInPoint.iterator(); // Iterator<LineSegment>
                    while (segIt.hasNext()) {
                        LineSegment segment = (LineSegment) segIt.next();

                        double fromX;
                        double toX;
                        double fromY;
                        double toY;
                        if (segment.getFirstEndPoint().x == actPoint.yPoint.x
                            && segment.getFirstEndPoint().y == actPoint.yPoint.y) {
                            fromX = segment.getFirstEndPoint().x;
                            fromY = segment.getFirstEndPoint().y;
                            toX = segment.getSecondEndPoint().x;
                            toY = segment.getSecondEndPoint().y;
                        } else {
                            fromX = segment.getSecondEndPoint().x;
                            fromY = segment.getSecondEndPoint().y;
                            toX = segment.getFirstEndPoint().x;
                            toY = segment.getFirstEndPoint().y;
                        }
                        if (fromX == toX) { // vertical
                            boolean up = toY < fromY;
                            if (up) {
                                if (!verticalUp) {
                                    numberOfDegrees++;
                                    verticalUp = true;
                                }
                            } else {
                                if (!verticalDown) {
                                    numberOfDegrees++;
                                    verticalDown = true;
                                }
                            }
                        } else {
                            double m = (toY - fromY) / (toX - fromX);
                            boolean quad1or4 = toX > fromX;

                            int degreesToCompare = numberOfDegrees;
                            degreesToCompare = (verticalUp) ? degreesToCompare - 1 : degreesToCompare;
                            degreesToCompare = (verticalDown) ? degreesToCompare - 1 : degreesToCompare;

                            boolean found = false;
                            for (int j = 0; j < degreesToCompare; j++) {
                                if (inQuad1or4[j] == quad1or4
                                     && (Math.abs(m - degrees[j]) < 0.0000001)) {
                                    found = true;
                                }
                            }
                            if (!found) {
                                int pos = numberOfDegrees;
                                pos = (verticalUp) ? pos - 1 : pos;
                                pos = (verticalDown) ? pos - 1 : pos;
                                degrees[pos] = m;
                                inQuad1or4[pos] = quad1or4;
                                numberOfDegrees++;
                            }


                        }
                        if (numberOfDegrees > 2) {
                            YPointPath path = actPoint.path;
                            LinkedList newBendPositions = (LinkedList) path2newBendPosition.get(path); // LinkedList<Integer>
                            if (newBendPositions == null) {
                                newBendPositions = new LinkedList(); // LinkedList<Integer>
                                path2newBendPosition.put(path, newBendPositions);
                            }
                            int position = actPoint.position;
                            newBendPositions.add(new Integer(position));
                            continue nextPointToCheck;
                        }
                    }
                }
            }

            // third: remove all LineSegments lying on this x position or to it's left and starting/ending there
            pointIt = pointList.iterator();
            while (pointIt.hasNext()) {
                UniqueYPoint actPoint = (UniqueYPoint) pointIt.next();
                int position = actPoint.position;
                YPointPath path = actPoint.path;
                if (position > 0) {
                    LineSegment prevSegment = getLineSegment(path, position - 1);
                    if (prevSegment.getFirstEndPoint().x <= x) {
                        activeLineSegments.remove(prevSegment);
                    }
                }
                if (position < path.length() - 1) {
                    LineSegment nextSegment = getLineSegment(path, position);
                    if (nextSegment.getSecondEndPoint().x <= x) {
                        activeLineSegments.remove(nextSegment);
                    }
                }
            }

        }


        // modify bends
        Iterator pathIt = path2newBendPosition.keySet().iterator(); // Iterator<YPointPath>
        while (pathIt.hasNext()) {
            YPointPath path = (YPointPath) pathIt.next();
            LinkedList bendPositions = (LinkedList) path2newBendPosition.get(path); // LinkedList<Integer>

            Object[] bendPositionObjectArray = bendPositions.toArray();
            int[] bendPositionArray = new int[bendPositionObjectArray.length];
            for (int i = 0; i < bendPositionObjectArray.length; i++) {
                bendPositionArray[i] = ((Integer) bendPositionObjectArray[i]).intValue();
            }

            Arrays.sort(bendPositionArray);

            int actNewBendPosition = 0;
            YList newPathList = new YList();
            YPoint[] oldPathPoints = path.toArray();
            for (int i = 0; i < oldPathPoints.length; i++) {
                if (actNewBendPosition < bendPositionArray.length
                        && bendPositionArray[actNewBendPosition] == i) {
                    double nextX = oldPathPoints[i].getX();
                    double nextY = oldPathPoints[i].getY();

                    double lastX = oldPathPoints[i-1].getX();
                    double lastY = oldPathPoints[i-1].getY();

                    double newX;
                    double newY;
                    double d = 0.0000001;

                    if (nextX == lastX) {
                        newX = nextX;
                        newY = (nextY < lastY) ? nextY + d : nextY - d;
                    } else {
                        double m = (nextY - lastY) / (nextX - lastX);
                        double dx = d * ((nextX < lastX) ? 1 : -1);
                        newX = nextX + dx;
                        newY = nextY + m * dx;
                    }

                    newPathList.add(new YPoint(newX, newY));
                    actNewBendPosition++;
                }
                newPathList.add(oldPathPoints[i]);
            }

            Edge edge = (Edge) path2Edge.get(path);
            graph.setPath(edge, newPathList);
        }
    }

    private LineSegment getLineSegment(YPointPath path, int position) {
        HashMap position2segment = (HashMap) lineSegments.get(path); // HashMap<Integer, LineSegment>
        if (position2segment == null) {
            position2segment = new HashMap(); // HashMap<Integer, LineSegment>
            lineSegments.put(path, position2segment);
        }

        LineSegment segment = (LineSegment) position2segment.get(new Integer(position));
        if (segment == null) {
            segment = path.getLineSegment(position);
            position2segment.put(new Integer(position), segment);
        }
        return segment;
    }

    private class UniqueYPoint {
        public YPoint yPoint;
        public int position;
        public YPointPath path;

        public UniqueYPoint(YPoint yPoint, YPointPath path, int position) {
            this.yPoint = yPoint;
            this.path = path;
            this.position = position;
        }

        public String toString() {
            return ("UniqueYPoint(" + yPoint + "; " + path + "; " + position + ")");
        }
    }
}