<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Collection: study</title>
</head>
<body>
<h1><a href="../studies">Studies</a></h1>
<a href="../studies">
<table border="1">
	<thead><tr>
		<th>ID</th>
		<th>Author</th>
		<th>Title</th>
	</tr></thead>
	<tr>
		<td>${object.studyID}</td>
		<td>${object.author.email}</td>
		<td>${object.content.study.studyTitle}</td>
	</tr>
</table>
</a>
</body>
</html>

