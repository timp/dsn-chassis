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
<title>WWARN - Curator Home // <jsp:expression>user.getUsername()</jsp:expression></title>
<link rel="stylesheet" type="text/css" href="../style/common.css"/>
<link rel="stylesheet" type="text/css" href="../style/study-questionnaire.css"/>
<script type="text/javascript" language="javascript">
var config = {
	"user.email" : "<jsp:expression>user.getUsername()</jsp:expression>",
	"collection.studies.url"     : "<jsp:expression>request.getContextPath()</jsp:expression>/atom/edit/studies",
	"collection.media.url"       : "<jsp:expression>request.getContextPath()</jsp:expression>/atom/edit/media",
	"collection.submissions.url" : "<jsp:expression>request.getContextPath()</jsp:expression>/atom/edit/submissions",
	"collection.reviews.url"     : "<jsp:expression>request.getContextPath()</jsp:expression>/atom/edit/reviews",
	"query.studies.url"          : "<jsp:expression>request.getContextPath()</jsp:expression>/curator/query/studies.xql",
	"query.filestoreview.url"    : "<jsp:expression>request.getContextPath()</jsp:expression>/curator/query/filestoreview.xql",
	"query.filestoclean.url"     : "<jsp:expression>request.getContextPath()</jsp:expression>/curator/query/filestoclean.xql"
};
</script>
<style>
#jsWarning {
  color: maroon;
  border: solid 2px maroon;
  margin: 10px;
  font-size: x-large;
  text-align: center;
  padding: 5px;
  font-family: Verdana, Arial, sans-serif;
}
</style>
</head>
<body>
<noscript>
    <div id="jsWarning">
    <!-- TODO: Style to look more like the js-enabled website, inc. logo, etc. -->
    <!-- TODO: i18n -->
    <p>JavaScript is currently disabled in this browser.</p> 
    <p>This website requires JavaScript to be enabled.</p>  
    </div>
</noscript>

	<div id="holdall" style="visibility:hidden">
		
		<div id="user">
			<!-- user div -->
			<p><span id="username"><jsp:expression>user.getUsername()</jsp:expression></span></p>
		</div>	
		
		<div id="banner">
			<!-- banner div -->
		</div>
		
		<div id="apploading">
			<!-- TODO: i18n -->
			<p>Please wait while the application loads...</p>
		</div>

		<div id="content">
			<!-- content div -->
		</div>

		<div id="footer">
			<!-- footer div -->
		</div>

	</div>
	<script type="text/javascript" language="javascript">
		document.getElementById("holdall").style.visibility = "visible";
	</script>
	<script type="text/javascript" language="javascript" src="../org.cggh.chassis.wwarn.ui.curator.Curator/org.cggh.chassis.wwarn.ui.curator.Curator.nocache.js">
		<!-- bootstrap script -->
	</script>

    <iframe src="javascript:''" id="__gwt_historyFrame" tabIndex='-1' style="position:absolute;width:0;height:0;border:0">
    	<!-- history iframe -->
    </iframe>

</body>
</html>
</jsp:root>