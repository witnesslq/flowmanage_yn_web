<%@page import="com.ultrapower.flowmanage.modules.userPermissions.vo.UserInfo"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
UserInfo user = (UserInfo)request.getSession().getAttribute("user");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>互联网流量平台</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
  </head>
 
  <body>
    <div style="text-align: center ;">
		<font>当前登录用户</font> <font style="font-size: 20px;color: red;"><%=user.getUsername() %></font> <font>没有访问当前页面的权限，请重新设置权限！</font>
	</div>
  </body>
</html>
