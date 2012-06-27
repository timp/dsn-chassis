<%@ page session="true" %>
<%@ page pageEncoding="UTF-8" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<link rel="shortcut icon" href="http://test.wwarn.org/sites/default/files/wwarn_favicon.ico" type="image/x-icon">
<meta name="description" content="Interactive, certificated training for conducting clinical research">
<link type="text/css" rel="stylesheet" media="all" href="./css/superfish.css">
<link type="text/css" rel="stylesheet" media="all" href="./css/superfish-navbar.css">
<link type="text/css" rel="stylesheet" media="all" href="./css/wwarn.css">
<link type="text/css" rel="stylesheet" media="all" href="./css/manta.css">
<script type="text/javascript" src="./css/jquery.min.js"></script>
<script type="text/javascript" src="./css/jquery.hoverIntent.minified.js"></script>
<script type="text/javascript" src="./css/jquery.bgiframe.min.js"></script>
<script type="text/javascript" src="./css/superfish.js"></script>
<script type="text/javascript" src="./css/supersubs.js"></script>
<script type="text/javascript" src="./css/supposition.js"></script>
<script type="text/javascript" src="./css/sftouchscreen.js"></script>
<script type="text/javascript">
jQuery(function(){
jQuery('#superfish-1').supersubs({minWidth: 12, maxWidth: 27, extraWidth: 1}).superfish({
pathClass: 'active-trail',
delay: 2500,
animation: {opacity:'show'},
speed: 'fast',
autoArrows: false,
dropShadows: false});
});
</script>
<style type="text/css">
#msg, #status {
	padding: 5px;
} 
#msg {
	border: 1px solid #05C700 ;
	padding-top: 0;
	padding-bottom: 0;
}
#status {
	border: 1px solid #D77 ;
}
	    </style>
</head>
<body id="cas" onload="init();">
<div class="header">
	<h1 class="logo">	<a href="http://www.wwarn.org/" title="Home">WWARN - Worldwide Antimalarial Resistance Network</a></h1>
	<div class="navcontainer">
		<div class="nav">
			<div id="block-superfish-1">  
	    <ul id="superfish-1" class="sf-menu primary-links sf-navbar sf-style-none sf-total-items-4 sf-parent-items-4 sf-single-items-0 sf-js-enabled"><li id="menu-526-1" class="first odd sf-item-1 sf-depth-1 sf-total-children-8 sf-parent-children-5 sf-single-children-3 menuparent"><a href="http://test.wwarn.org/about-us" title="" class="sf-depth-1 menuparent">About Us</a><ul style="top: -99999em; " class="sf-hidden"><li id="menu-8619-1" class="first odd sf-item-1 sf-depth-2 sf-no-children"><a href="http://test.wwarn.org/about-us/our-work" title="" class="sf-depth-2">Our Work</a></li><li id="menu-8617-1" class="middle even sf-item-2 sf-depth-2"><a href="http://test.wwarn.org/about-us/people" title="" class="sf-depth-2">Our People</a></li><li id="menu-12074-1" class="middle odd sf-item-3 sf-depth-2"><a href="http://test.wwarn.org/about-us/regional-centres" title="" class="sf-depth-2">Regional Centres</a></li><li id="menu-8620-1" class="middle even sf-item-4 sf-depth-2"><a href="http://test.wwarn.org/about-us/scientific-groups" title="" class="sf-depth-2">Scientific Groups</a></li><li id="menu-8683-1" class="middle odd sf-item-5 sf-depth-2"><a href="http://test.wwarn.org/about-us/news" title="" class="sf-depth-2">News &amp; Media</a></li><li id="menu-8712-1" class="middle even sf-item-6 sf-depth-2 sf-no-children"><a href="http://test.wwarn.org/about-us/publications" title="" class="sf-depth-2">Publications</a></li><li id="menu-8714-1" class="middle odd sf-item-7 sf-depth-2 sf-no-children"><a href="http://test.wwarn.org/about-us/job-vacancies" title="" class="sf-depth-2">Jobs</a></li><li id="menu-10763-1" class="last even sf-item-8 sf-depth-2"><a href="http://test.wwarn.org/about-us/beyond-malaria" title="" class="sf-depth-2">Beyond Malaria</a></li></ul></li><li id="menu-890-1" class="middle even sf-item-2 sf-depth-1 sf-total-children-3 sf-parent-children-3 sf-single-children-0 menuparent"><a href="http://test.wwarn.org/resistance" title="" class="sf-depth-1 menuparent">Tracking Resistance</a><ul style="top: -99999em; " class="sf-hidden"><li id="menu-10817-1" class="first odd sf-item-1 sf-depth-2"><a href="http://test.wwarn.org/resistance/malaria" title="" class="sf-depth-2">Malaria Drug Resistance</a></li><li id="menu-8618-1" class="middle even sf-item-2 sf-depth-2"><a href="http://test.wwarn.org/resistance/explorer" title="" class="sf-depth-2">WWARN Explorer</a></li><li id="menu-10815-1" class="last odd sf-item-3 sf-depth-2"><a href="http://test.wwarn.org/resistance/surveyors" title="" class="sf-depth-2">WWARN Surveyors</a></li></ul></li><li id="menu-901-1" class="middle odd sf-item-3 sf-depth-1 sf-total-children-4 sf-parent-children-3 sf-single-children-1 menuparent"><a href="http://test.wwarn.org/partnerships" title="" class="sf-depth-1 menuparent">Platform for Partnership</a><ul style="top: -99999em; " class="sf-hidden"><li id="menu-8624-1" class="first odd sf-item-1 sf-depth-2"><a href="http://test.wwarn.org/partnerships/data" title="" class="sf-depth-2">Sharing Data</a></li><li id="menu-8625-1" class="middle even sf-item-2 sf-depth-2"><a href="http://test.wwarn.org/partnerships/study-groups" title="" class="sf-depth-2">Study Groups</a></li><li id="menu-8626-1" class="middle odd sf-item-3 sf-depth-2 sf-no-children"><a href="http://test.wwarn.org/partnerships/network" title="" class="sf-depth-2">Collaborator Network</a></li><li id="menu-8627-1" class="last even sf-item-4 sf-depth-2"><a href="http://test.wwarn.org/partnerships/projects" title="" class="sf-depth-2">Partner Projects</a></li></ul></li><li id="menu-3181-1" class="last even sf-item-4 sf-depth-1 sf-total-children-5 sf-parent-children-3 sf-single-children-2 menuparent"><a href="http://test.wwarn.org/toolkit" title="" class="sf-depth-1 menuparent">WWARN Toolkit</a><ul style="top: -99999em; " class="sf-hidden"><li id="menu-8628-1" class="first odd sf-item-1 sf-depth-2"><a href="http://test.wwarn.org/toolkit/qaqc" title="" class="sf-depth-2">QA/QC Programmes</a></li><li id="menu-8630-1" class="middle even sf-item-2 sf-depth-2 sf-no-children"><a href="http://test.wwarn.org/toolkit/courses" title="" class="sf-depth-2">Online Courses</a></li><li id="menu-8629-1" class="middle odd sf-item-3 sf-depth-2"><a href="http://test.wwarn.org/toolkit/procedures" title="" class="sf-depth-2">Procedures</a></li><li id="menu-10830-1" class="middle even sf-item-4 sf-depth-2"><a href="http://test.wwarn.org/toolkit/data-management" title="" class="sf-depth-2">Data Management &amp; Analysis</a></li><li id="menu-10831-1" class="last odd sf-item-5 sf-depth-2 sf-no-children"><a href="http://test.wwarn.org/toolkit/glossary" title="" class="sf-depth-2">Glossary</a></li></ul></li></ul>
		</div> 
		</div>
		<div class="navsearch"><div id="block-search-0" class="block block-search region-odd odd region-count-1 count-5">
				    <form id="search-block-form" method="post" accept-charset="UTF-8" action="https://www.wwarn.org/user">
						<div>
							<div class="container-inline">
					  			<div id="edit-search-block-form-1-wrapper" class="form-item">
					 				<label for="edit-search-block-form-1">Search this site: </label>
					 				<input type="text" class="form-text NormalTextBox txtSearch" onfocus="if (this.value == &#39;Search&#39;) {this.value = &#39;&#39;; $(this).addClass(&#39;form-active&#39;);}" onblur="if (this.value == &#39;&#39;) {this.value = &#39;Search&#39;; $(this).removeClass(&#39;form-active&#39;);}" title="Enter the terms you wish to search for." value="Search" size="15" id="edit-search-block-form-1" name="search_block_form" maxlength="128">
								</div>
								<input type="submit" class="form-submit" id="edit-submit" name="op" style="display:block; padding: 0; text-align: left" value="">
								<input type="hidden" value="search_block_form" id="edit-search-block-form" name="form_id">
							</div>
						</div>
					</form>
	</div>
</div> 
	</div>
	<div class="secondary-wrap"> <div id="block-menu-secondary-links" class="block block-menu region-odd odd region-count-1 count-1">
	    <ul class="menu">
<li class="leaf first Register" id=" menu-item-custom-id-9"><a href="http://wwww.wwarn.org/user/register" title="create a WWARN Account"><span class="tab">Register</span></a></li>
<li class="leaf" id=" menu-item-custom-id-101"><a href="http://www.wwarn.org/" title="Home">Home</a></li>
<li class="leaf last Contact" id=" menu-item-custom-id-10"><a href="http://www.wwarn.org/contact" title="Contact">Contact</a></li>
</ul>  
</div> 
 </div>
</div>
    <div id="main">
        <div id="main-inner" class="clear-block">
            <div id="content" class="page">
                <div class="inner clearfix">
                    <div class="main-indent full-width">
                        <div id="sidebar-right" class="sidebar main-sidebar">
                            <div id="sidebar-right-inner" class="region region-right">
                            	<div class="block block-block region-odd odd region-count-1 count-9">
								<h3>Don't have a <strong>WWARN Account</strong>?</h3>
								<p><strong><a href="https://www.wwarn.org/user/register">Create an account</a></strong></p>
								<h3>Forgotten your password?</h3>
								<p><strong><a href="https://www.wwarn.org/user/password">Request a new password</a></strong></p>  
								</div> 
                            </div>
                        </div> 
                    	<div class="main twoCol">                                                       
                        <img src="images/default.jpg" alt="" class="background">
