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
</jsp:scriptlet>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>WWARN - Submitter // <jsp:expression>user.getUsername()</jsp:expression></title>
<link rel="stylesheet" type="text/css" href="../style/common.css"/>
<script type="text/javascript" language="javascript">
var config = {
	"user.email" : "<jsp:expression>user.getUsername()</jsp:expression>",
	"collection.studies.url" : "/chassis-generic-service-exist/atom/edit/studies",
	"collection.media.url" : "/chassis-generic-service-exist/atom/edit/media",
	"collection.submissions.url" : "/chassis-generic-service-exist/atom/edit/submissions",
	"collection.reviews.url" : "/chassis-generic-service-exist/atom/edit/reviews",
	"query.submissions.url" : "/chassis-generic-service-exist/query/submissions.xql",
	"query.studies.url" : "/chassis-generic-service-exist/mock/query/studies.xql_id=abc_oneresult.xml",
	"query.studies.url.none" : "/chassis-generic-service-exist/mock/query/studies.xql_noresults.xml",
	"query.studies.url.two" : "/chassis-generic-service-exist/mock/query/studies.xql_tworesults.xml",
	"query.studies.url.live" : "/chassis-generic-service-exist/query/studies.xql",
	"query.media.url" : "/chassis-generic-service-exist/submitter/query/media.xql",
	"formhandler.fileupload.url" : "/chassis-wwarn-ui/submitter/upload"
};
</script>
</head>
<body>

	<div id="holdall">
		
		<div id="user">
			<!-- user div -->
			<p><span id="username"><jsp:expression>user.getUsername()</jsp:expression></span></p>
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