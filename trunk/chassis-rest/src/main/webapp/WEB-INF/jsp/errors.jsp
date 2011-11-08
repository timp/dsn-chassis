<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Errors</title>
</head>
<body>
<h2>Errors reported for study ${id}</h2>
<c:if test="${not empty exception}">
${exception}
</c:if>
<c:if test="${not empty errors}">
<table style="border:1px solid red">
	<thead><tr>
		<th>ID</th>
		<th>Title</th>
		<th></th>
	</tr></thead>
	<c:forEach var="error" items="${errors}">
	<tr>
		<td>${error.getError()}</td>
	</tr>
	</c:forEach>
</table>
</c:if>
</body>
</html>

