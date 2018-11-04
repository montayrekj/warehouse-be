<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Admin Panel</title>
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.13/css/all.css" integrity="sha384-DNOHZ68U8hZfKXOrtjWvjxusGo9WQnrNx2sqG0tfsghAvtVlRW3tvkXWZh58N9jp" crossorigin="anonymous">
<link href="/css/bootstrap/bootstrap.min.css" rel="stylesheet">
<link href="/bower_components/sidebar-nav/dist/sidebar-nav.min.css" rel="stylesheet">
<link href="/css/spinners.css" rel="stylesheet">
<link href="/css/animate.css" rel="stylesheet">
<link href="/css/style.css" rel="stylesheet">
<link href="/css/default.css" rel="stylesheet">
<link href="/css/material-design-iconic-font/css/materialdesignicons.min.css" rel="stylesheet">
</head>
<body class="fix-header">
	<div id="wrapper">
		<%@ include file = "/WEB-INF/pages/admin/header.jsp" %>
		<%@ include file = "/WEB-INF/pages/admin/sidebar.jsp" %>
		
		
		<c:if test="${content != null}">
			<c:if test="${content == 'employees.jsp'}">
				<%@ include file = "/WEB-INF/pages/admin/employees.jsp" %>
			</c:if>
		</c:if>
	
	</div>	
</body>
<script src="/bower_components/jquery/dist/jquery.min.js"></script>
<script src="/js/bootstrap/bootstrap.min.js"></script>
<script src="/bower_components/sidebar-nav/dist/sidebar-nav.min.js"></script>
<script src="/js/jquery.slimscroll.js"></script>
<script src="/js/waves.js"></script>
<script src="/js/custom.min.js"></script>
</html>