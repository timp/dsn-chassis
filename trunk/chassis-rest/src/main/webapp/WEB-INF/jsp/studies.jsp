<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Collection: study</title>
</head>
<body>
<table border="1">
	<thead><tr>
		<th>ID</th>
		<th>Author</th>
		<th>Title</th>
	</tr></thead>
<c:if test="${not empty $object}">
	<tr>
		<td><a href="study/${object.studyID}">${object.studyID}</a></td>
		<td>${object.author.email}</td>
		<td>${object.content.study.studyTitle}</td>
	</tr>
</c:if>
<c:forEach var="item" items="${studies.entry}">
	<tr>
		<td><a href="study/${item.studyID}">${item.studyID}</a></td>
		<td>${object.author.email}</td>
		<td>${item.content.study.studyTitle}</td>
	</tr>
</c:forEach>
</table>
</body>
</html>

