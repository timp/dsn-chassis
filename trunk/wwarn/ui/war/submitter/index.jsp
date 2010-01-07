<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0">
<jsp:directive.page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
<jsp:directive.page import="org.springframework.security.context.SecurityContextHolder"/>
<jsp:directive.page import="org.springframework.security.userdetails.UserDetails"/>
<jsp:text><![CDATA[<?xml version="1.0" encoding="UTF-8" ?>
]]></jsp:text>
<jsp:text><![CDATA[<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
]]></jsp:text>
<jsp:scriptlet>
UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
String username = user.getUsername();
</jsp:scriptlet>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>WWARN - Submitter // <jsp:expression>username</jsp:expression></title>
<link rel="stylesheet" type="text/css" href="style.css"/>
<script type="text/javascript" language="javascript">
var config = {};
config.username = "<jsp:expression>username</jsp:expression>";
</script>
</head>
<body>

	<div id="holdall">
		
		<div id="user">
			<!-- user div -->
			<p><span id="username"><jsp:expression>username</jsp:expression></span></p>
		</div>	
		
		<div id="banner">
			<!-- banner div -->
		</div>
		
		<div id="apploading">
			<p>Please wait while the application loads...</p>
		</div>

		<div id="content">
			<!-- content div -->
		</div>

		<div id="footer">
			<!-- footer div -->
		</div>

	</div>

	<script type="text/javascript" language="javascript" src="../org.cggh.chassis.wwarn.ui.submitter.Submitter/org.cggh.chassis.wwarn.ui.submitter.Submitter.nocache.js">
		<!-- bootstrap script -->
	</script>

    <iframe src="javascript:''" id="__gwt_historyFrame" tabIndex='-1' style="position:absolute;width:0;height:0;border:0">
    	<!-- history iframe -->
    </iframe>

</body>
</html>
</jsp:root>