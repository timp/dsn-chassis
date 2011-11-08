<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Collection: study</title>
</head>
<body>
<h1>Studies</h1>
<table style="border:1px solid green">
	<thead><tr>
		<th>ID</th>
		<th>Author</th>
		<th>Title</th>
	</tr></thead>
<c:forEach var="entry" items="${studies.entry}">
	<tr>
		<td><a href="study/${entry.studyID}">${entry.studyID}</a></td>
		<td>${entry.author.email}</td>
		<td>${entry.content.study.studyTitle}</td>
	</tr>
</c:forEach>
</table>
</body>
</html>

