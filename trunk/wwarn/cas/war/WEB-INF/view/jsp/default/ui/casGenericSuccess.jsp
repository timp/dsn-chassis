<jsp:directive.include file="includes/top.jsp" />
<div id="bd" class="clearfix">
	<div class="help">
		<h3>Don't have a <strong>WWARN Account</strong>?</h3>
		<p><strong><a href="/user/register">Create an account</a></strong></p>
		<h3>Forgotten your password?</h3>
		<p><strong><a href="/user/password">Request a new password</a></strong></p>
	</div>
	<div class="no-bg">
		<div class="main twoCol">
			<div id="msg" class="success">
				<h2><spring:message code="screen.success.header" /></h2>
				<p><spring:message code="screen.success.success" /></p>
				<p><spring:message code="screen.success.security" /></p>
			</div>
		</div>
	</div>
</div>
<jsp:directive.include file="includes/bottom.jsp" />

