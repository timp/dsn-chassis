<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>Collection: study</title>
</head>
<body>
<h1><a href="../studies">Studies</a></h1>
<table style="border:1px solid green">
	<thead><tr>
		<th>ID</th>
		<th>Author</th>
		<th>Title</th>
	</tr></thead>
	<tr>
		<td>${entry.studyID}</td>
		<td>${entry.author.email}</td>
		<td>${entry.content.study.studyTitle}</td>
	</tr>
</table>
</body>
</html>

