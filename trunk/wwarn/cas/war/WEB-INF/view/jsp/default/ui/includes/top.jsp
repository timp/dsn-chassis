<%@ page session="true" %>
<%@ page pageEncoding="UTF-8" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
	<head>
	    <title>WWARN - Log in</title>
	    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	    <style type="text/css" media="screen">@import 'css/manta.css';</style>
	    <style type="text/css" media="screen">@import 'css/wwarn.css';</style>
	    <script type="text/javascript" src="js/common_rosters.js"></script>
	    <link rel="icon" href="<%= request.getContextPath() %>/favicon.ico" type="image/x-icon" />
	    <style type="text/css">
#msg, #status {
	padding: 5px;
} 
	    </style>
	</head>

	<body id="cas" onload="init();">
	    <div id="header" class="header">
			<h1 class="logo"><a href="http://www.wwarn.org/">WWARN - Worldwide Antimalarial Resistance Network</a></h1>
			
		    <div id="block-menu-primary-links">
			    <ul id="primary" class="clearfix" style="padding-left: 17px; padding-right: 17px">
			        <li class="current"><a href="http://www.wwarn.org/">Home</a></li>
			        <li><a href="http://www.wwarn.org/about-us">About Us</a></li>
			        <li><a href="http://www.wwarn.org/research">Supporting Research</a></li>
			        <li><a href="http://www.wwarn.org/data">Contributing Data</a></li>
			        <li><a href="http://www.wwarn.org/resistance">Tracking Resistance</a></li>
			        <li><a href="http://www.wwarn.org/community">Community</a></li>
			        <li><a href="http://www.wwarn.org/press">News &amp; Media</a></li>
			    </ul>
		    </div>
		    
			<div class="secondary-wrap">
				<div id="current-user-actions block-menu-secondary-links" class="block block-menu region-odd even region-count-1 count-2">
			        <ul class="menu">
			            <li class="leaf first Contact"><a href="/user/register" title="Register" target="_blank">Register</a></li>
			            <li class="leaf last Contact"><a href="/contact" title="Contact" target="_blank">Contact</a></li>

			        </ul>
			    </div> 		

				<!-- ripped off from drupal -->
			    <div class="block block-search region-even odd region-count-2 count-3" id="block-search-0">
			  
				    <form id="search-block-form" method="post" accept-charset="UTF-8" action="/user">
						<div>
							<div class="container-inline">
					  			<div id="edit-search-block-form-1-wrapper" class="form-item">
					 				<label for="edit-search-block-form-1">Search this site: </label>
					 				<input type="text" class="form-text" title="Enter the terms you wish to search for." value="Search" size="15" id="edit-search-block-form-1" name="search_block_form" maxlength="128">
								</div>
								<input type="submit" class="form-submit" value="Search" id="edit-submit-1" name="op">
								<input type="hidden" value="form-f8e3ebc5c486f688789ee714f5225fed" id="form-f8e3ebc5c486f688789ee714f5225fed" name="form_build_id">
								<input type="hidden" value="search_block_form" id="edit-search-block-form" name="form_id">
							</div>
						</div>
					</form>
					
				</div>	

			</div>

	    </div>
				
        <div id="holdall">
        	<div id="main-inner">
	    		<div id="content" class="content page">
	    			<div id="bd" class="clearfix">
	    				<div class="help">
	    					<h3>Don't have a <strong>WWARN Account</strong>?</h3>
	    					<p><strong><a href="/user/register">Create an account now</a></strong></p>
	    					<h3>Forgotten your password?</h3>
	    					<p><strong><a href="/user/password">Request a new password now</a></strong></p>
	    				</div>
    					<div class="no-bg">
							<div class="main twoCol">
