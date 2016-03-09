

package com.ultrapower.yworks.yfiles.server.orgchart;
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


import com.ultrapower.yworks.yfiles.server.servlets.BaseServlet;
import com.yworks.yfiles.server.graphml.flexio.data.StyledLayoutGraph;
import com.yworks.yfiles.server.graphml.support.GraphRoundtripSupport;
import com.yworks.yfiles.server.graphml.support.ServletRoundtripSupport;
import y.base.DataProvider;
import y.base.Node;
import y.base.NodeCursor;
import y.base.NodeMap;
import y.io.graphml.KeyScope;
import y.io.graphml.KeyType;
import y.layout.BufferedLayouter;
import y.layout.FixNodeLayoutStage;
import y.layout.Layouter;
import y.layout.tree.AssistantPlacer;
import y.layout.tree.DefaultNodePlacer;
import y.layout.tree.GenericTreeLayouter;
import y.layout.tree.LeftRightPlacer;
import y.layout.tree.NodePlacer;
import y.util.DataProviderAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OrgChartServlet extends BaseServlet {

  protected ServletRoundtripSupport createRoundtripSupport() {
    GraphRoundtripSupport result = new GraphRoundtripSupport();
    result.addMapper("layout", "layout", KeyType.STRING , KeyScope.NODE);
    result.addMapper("assistant", "assistant", KeyType.STRING, KeyScope.NODE);
    return result;
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    doGet(request, response);
  }

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    // insert the following lines to trace the graph sent by the client
//      InputStream inputStream = new GraphDecoder().getGraphInputStream(request, AbstractGraphRoundtripSupport.PARAM_GRAPH);
//      BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
//      StringBuffer buffer = new StringBuffer();
//      String line;
//      System.out.println("\nGraphML send by the client:");
//      while((line = reader.readLine())!= null){
//          buffer.append(line);
//          buffer.append("\n");
//      }
//      System.out.println(buffer.toString());

    GraphRoundtripSupport support = (GraphRoundtripSupport) getRoundtripSupport();
    StyledLayoutGraph graph = (StyledLayoutGraph) support.createRoundtripGraph();
    support.readGraph(request, graph);

    String focusNode = request.getParameter("focusNode");
    layoutGraph(graph, focusNode);

    new OrgChartBendSmoother().calculateBendSmoothing(graph);

    getRoundtripSupport().sendGraph(graph, response);
  }


  private void layoutGraph(StyledLayoutGraph graph, final String focusNode) {
    GenericTreeLayouter gtl = new GenericTreeLayouter();

    NodeMap nodePlacerMap = graph.createNodeMap();
    for (NodeCursor nc = graph.nodes(); nc.ok(); nc.next()) {
      Node n = nc.node();
      String layout = getLayout(n);
      if ("left_below".equals(layout)) {
        DefaultNodePlacer placer = new DefaultNodePlacer();
        placer.setChildPlacement(DefaultNodePlacer.PLACEMENT_VERTICAL_TO_LEFT);
        placer.setRootAlignment(DefaultNodePlacer.ALIGNMENT_LEADING_ON_BUS);

        placer.setRoutingStyle(DefaultNodePlacer.ROUTING_FORK_AT_ROOT);
        nodePlacerMap.set(n, placer);
      } else if ("right_below".equals(layout)) {
        DefaultNodePlacer placer = new DefaultNodePlacer();
        placer.setChildPlacement(DefaultNodePlacer.PLACEMENT_VERTICAL_TO_RIGHT);
        placer.setRootAlignment(DefaultNodePlacer.ALIGNMENT_LEADING_ON_BUS);

        placer.setRoutingStyle(DefaultNodePlacer.ROUTING_FORK_AT_ROOT);
        nodePlacerMap.set(n, placer);
      } else if ("below".equals(layout)) {
        LeftRightPlacer placer = new LeftRightPlacer();
        placer.setPlaceLastOnBottom(false);
        nodePlacerMap.set(n, placer);
      }else if ("down".equals(layout)) {
    	DefaultNodePlacer placer = new DefaultNodePlacer();
    	placer.setChildPlacement(DefaultNodePlacer.PLACEMENT_HORIZONTAL_DOWNWARD);
        placer.setRootAlignment(DefaultNodePlacer.ALIGNMENT_CENTER); // Al_Median ALIGNMENT_CENTER
        placer.setRoutingStyle(DefaultNodePlacer.ROUTING_FORK); //ROUTING_FORK
          
        nodePlacerMap.set(n, placer);
      }else {
        DefaultNodePlacer placer = new DefaultNodePlacer();
        placer.setChildPlacement(DefaultNodePlacer.PLACEMENT_VERTICAL_TO_RIGHT);
        placer.setRootAlignment(DefaultNodePlacer.ALIGNMENT_CENTER); // Al_Median ALIGNMENT_CENTER
        
        placer.setRoutingStyle(DefaultNodePlacer.ROUTING_FORK); //ROUTING_FORK
//        placer.setVerticalDistance(500);
        nodePlacerMap.set(n, placer);
      }
    }

    NodeMap assistMap = graph.createNodeMap();
    graph.addDataProvider(AssistantPlacer.ASSISTANT_DPKEY, assistMap);
    for (NodeCursor nc = graph.nodes(); nc.ok(); nc.next()) {
      Node n = nc.node();
      String assistant = getAssistant(n);
      if ("true".equals(assistant) && n.firstInEdge() != null) {
        AssistantPlacer placer = new AssistantPlacer();
        NodePlacer parentPlacer = (NodePlacer) nodePlacerMap.get(n.firstInEdge().source());
        placer.setChildNodePlacer(parentPlacer);
        nodePlacerMap.set(n.firstInEdge().source(), placer);
        assistMap.setBool(n, true);
      }
    }
    graph.addDataProvider(GenericTreeLayouter.NODE_PLACER_DPKEY, nodePlacerMap);

    Layouter layouter;
    if (focusNode != null) {
      layouter = new FixNodeLayoutStage(gtl);
      final DataProvider node2Id = graph.getDataProvider(GraphRoundtripSupport.NODE_2_ID_DPKEY);
      DataProvider dpFixedNode = new DataProviderAdapter() {
        public boolean getBool(Object dataHolder) {
          return focusNode.equals(node2Id.get(dataHolder));
        }
      };
      graph.addDataProvider(FixNodeLayoutStage.FIXED_NODE_DPKEY, dpFixedNode);
    } else {
      layouter = gtl;
    }

    new BufferedLayouter(layouter).doLayout(graph);

    graph.disposeNodeMap(nodePlacerMap);
    graph.removeDataProvider(GenericTreeLayouter.NODE_PLACER_DPKEY);

  }

  private String getLayout(Node n) {
    DataProvider layoutData = n.getGraph().getDataProvider("layout");
    return (String) layoutData.get(n);
  }

  private String getAssistant(Node n) {
    DataProvider assistantData = n.getGraph().getDataProvider("assistant");
    return (String) assistantData.get(n);
  }

}