<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Errors</title>
</head>
<body>
<h2>Errors reported for study ${object.id}</h2>
<table style="border:1px solid red">
	<thead><tr>
		<th>ID</th>
		<th>Title</th>
		<th></th>
	</tr></thead>
	<c:forEach var="errorMessage" items="${object.errors}">
	<tr>
		<td>${errorMessage}</td>
	</tr>
	</c:forEach>
</table>
</body>
</html>

