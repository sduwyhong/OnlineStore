<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>在线书店</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/main.css">
	

  </head>
  
  <body>
    <br/>
    <br/>
    <h1>欢迎光临</h1>
    <br/>
    <br/>
    <a href="${pageContext.request.contextPath}">首页</a>
    <c:if test="${empty customer }">
	    <a href="${pageContext.request.contextPath}/login.jsp">登录</a>
	    <a href="${pageContext.request.contextPath}/regist.jsp">免费注册</a>
    </c:if>
    <c:if test="${!empty customer }">
    	您好：${customer.nickname } <a href="${pageContext.request.contextPath}/client/ClientServlet?op=logout">注销</a>
    </c:if>
    <a href="${pageContext.request.contextPath}/client/ClientServlet?op=showOrders">我的订单</a>
    <a href="${pageContext.request.contextPath}/cart.jsp">购物车</a>
     <br/>
  <br/>
