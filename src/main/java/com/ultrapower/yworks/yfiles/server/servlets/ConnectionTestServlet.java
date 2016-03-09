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

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ConnectionTestServlet extends HttpServlet {

  private static final String MISSING_LIBRARIES = "Not all " +
                "libraries required to run the yFiles FLEX server component have been " +
                "successfully deployed.&lt;br/>&lt;br/>" +
                "The following libraries are missing:" +
                "&lt;ul>&lt;br/>&lt;br/>";

  private static final String MISSING_LIBRARIES_FOOTER =
          "&lt;/ul>&lt;br/>&lt;br/>Please make sure that your application server " +
          "is properly configured to deploy these libraries.";

	public void doPost( HttpServletRequest request, HttpServletResponse response ) throws IOException {

    String missingLibs = MISSING_LIBRARIES;
    boolean librariesAvailable = true;
    try {
      Class test = Class.forName( "y.base.Graph" ); 
    } catch (ClassNotFoundException e) {
      librariesAvailable = false;
      missingLibs += "&lt;li>yFiles for Java (y.jar)&lt;/li>";
    }
    missingLibs += MISSING_LIBRARIES_FOOTER;

    String r; 
    if (librariesAvailable) {
      r = "success";
    } else {
      r = missingLibs;
    }
		ServletOutputStream os = response.getOutputStream();
		os.write( r.getBytes() );
		os.flush();
    os.close();
	}
	
	public void doGet( HttpServletRequest request,HttpServletResponse response ) throws IOException {
		doPost(request, response);
	}
	
}
