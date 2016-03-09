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
package com.ultrapower.yworks.yfiles.server.servlets;

import com.ultrapower.yworks.yfiles.server.DemoConstants;
import com.yworks.yfiles.server.graphml.support.ServletRoundtripSupport;
import y.base.Graph;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * An abstract servlet Class that provides basic functionality for
 * communicating with the flex client.
 */
public abstract class BaseServlet extends HttpServlet {

  /**
   * The default directory that contains graph files, relative to the content root.
   * <p/>
   * Default value is "/resources/graphs".
   */
  public static final String DEFAULT_GRAPHS_DIR = "/resources/graphs";

  private ServletRoundtripSupport roundtripSupport;

  /**
   * Retrieves the support instance that will be used to load and save graphs to GraphML
   * @return the support object.
   */
  public final ServletRoundtripSupport getRoundtripSupport() {
    if (roundtripSupport == null){
      roundtripSupport = createRoundtripSupport();
    }
    return roundtripSupport;
  }

  /**
   * Factory method that creates the support instance.
   * @return the instance to use
   */
  protected abstract ServletRoundtripSupport createRoundtripSupport();

  /**
   * Send an error to the client.
   * The error is sent in XML format:
   * <pre>
   *  &lt;?xml version="1.0" encoding="UTF-8"?&gt;
   *  &lt;response&gt;
   *    &lt;errors&gt;
   *      &lt;error&gt;
   *       &lt;![CDATA[
   *          message
   *        ]]&gt;
   *      &lt;/error&gt;
   *    &lt;/errors&gt;
   * &lt;/response&gt;
   * </pre>
   * @param message The error message.
   * @param response The response.
   * @throws IOException If writing to the response writer fails.
   */
  protected void sendError( String message, HttpServletResponse response ) throws IOException {
    getRoundtripSupport().sendError(message, response);
  }

  /**
   * If the request contains the
   * {@link com.yworks.yfiles.server.graphml.support.GraphRoundtripSupport#PARAM_GRAPH} parameter, the parameter value is
   * used for updating the graph instance currently stored in the session.
   * @param request The servlet request
   * @throws IOException If parsing goes wrong.
   */
  protected boolean updateGraph(HttpServletRequest request) throws IOException {
    return getRoundtripSupport().readGraph(request, getGraph(request.getSession()));
  }

  /**
   * This implementation calls {@link #updateGraph(javax.servlet.http.HttpServletRequest)}
   */
  protected void doGet( HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse ) throws IOException, ServletException {
    updateGraph( httpServletRequest);
  }

  /**
   * Forwards to {@link #doGet(javax.servlet.http.HttpServletRequest,javax.servlet.http.HttpServletResponse)}
   */
  protected void doPost( HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse ) throws
          ServletException, IOException {
    doGet( httpServletRequest, httpServletResponse );
  }

  /**
   * Send an error to the client.
   * The error is sent in XML format:
   * <pre>
   *  &lt;?xml version="1.0" encoding="UTF-8"?&gt;
	 *  &lt;?xml version="1.0" encoding="UTF-8"?&gt;
   *  &lt;response&gt;
   *    &lt;errors&gt;
   *      &lt;error&gt;
   *        &lt;![CDATA[
   *          message
   *        ]]&gt;
   *      &lt;/error&gt;
   *    &lt;/errors&gt;
   *  &lt;/response&gt;
   * </pre>
   * @param e The error.
   * @param response The response.
   * @throws IOException If writing to the response writer fails.
   */
  protected void sendError( Throwable e, HttpServletResponse response ) throws IOException {
    log( e.getMessage(), e );
    ServletOutputStream os = response.getOutputStream();
    getRoundtripSupport().sendError(e, os);
    os.flush();
    os.close();
  }

  /**
   * Create a new Graph and register it with the session.
   * This implementation delegates to the roundtrip support's
   * <code>createRoundtripGraph</code>.
   * @param session The current session.
   * @return A new Graph2D instance.
   */
  protected Graph createGraph( HttpSession session ) {
    Graph graph = getRoundtripSupport().createRoundtripGraph();
    session.setAttribute( DemoConstants.SESSION_GRAPH, graph );
    return graph;
  }

  /**
   * Retrieve the current graph from the session. If no graph is stored in the session, a new empty graph will be
   * created.
   * @param session The current session.
   * @return The stored graph or a new empty graph.
   */
  protected Graph getGraph( HttpSession session ) {
    Graph graph = ( Graph ) session.getAttribute( DemoConstants.SESSION_GRAPH );
    if ( null == graph ) {
      graph = createGraph( session );
    }
    return graph;
  }

  /**
   * Send the current graph to the client in GraphML format.
   * <p/>
   * @param graph The current graph.
   * @param response The response that will be sent to the client.
   * @throws IOException If something goes wrong during GraphML export.
   */
  protected void sendGraph(Graph graph, HttpServletResponse response) throws IOException {
    getRoundtripSupport().sendGraph(graph, response);
  }
}
