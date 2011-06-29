<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Studies</title>
</head>
<body>
<table border=1>
	<thead><tr>
		<th>ID</th>
		<th>Title</th>
		<th></th>
	</tr></thead>
	<c:forEach var="item" items="${studies.entry}">
	<tr>
		<td>${item.studyID}</td>
		<td>${item.title.content}</td>
		<td><a href="study/${item.hjid}">${item.hjid}</a></td>
	</tr>
	</c:forEach>
</table>
</body>
</html>