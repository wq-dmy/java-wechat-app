<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ page import="java.net.URLEncoder"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>doc列表</title>
<style type="text/css">
	ul li{margin: 20px auto;}
</style>
</head>
<body>
	<h1>doc列表：</h1>
	<ul>
		<c:forEach items="${fileList}" var="file">
			<li><a href="/api/file/detail?pathName=<%=URLEncoder.encode(pageContext.getAttribute("file").toString()) %>" >${file}</a></li>
		</c:forEach>
	</ul>
</body>
</html>