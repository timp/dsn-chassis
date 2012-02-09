<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Message: ${message}</title>
</head>
<body>
<h2>Message: ${message}</h2>
<c:if test="${not empty body}">
<p>
${body}
</p>
</c:if>
</body>
</html>
