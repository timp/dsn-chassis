<jsp:directive.include file="includes/top.jsp" />
<div id="content-header">	
	<div id="content-header-container">
		<div><h1 class="title">Log in with your <strong>WWARN Account</strong></h1>
		</div>
	</div>
</div>
<div id="content-area" class="entry">
	<p><spring:message code="screen.confirmation.message" arguments="${fn:escapeXml(param.service)}${fn:indexOf(param.service, '?') eq -1 ? '?' : '&'}ticket=${serviceTicketId}" /></p>
</div>  
<jsp:directive.include file="includes/bottom.jsp" /
