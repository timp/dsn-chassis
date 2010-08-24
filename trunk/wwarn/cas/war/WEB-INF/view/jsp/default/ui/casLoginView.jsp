<%@ page contentType="text/html; charset=UTF-8" %>
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
            <h1 class="title"><spring:message code="screen.welcome.instructions" /></h1>
			<form:form method="post" id="fm1" cssClass="fm-v clearfix" commandName="${commandName}" htmlEscape="true">
			    <form:errors path="*" cssClass="errors" id="status" element="div" />
                <div class="box" id="login" style="margin-top: 30px;">
                <!-- <spring:message code="screen.welcome.welcome" /> -->
                    <div class="row">
                        <label for="username" style="display: inline-block; width: 90px"><spring:message code="screen.welcome.label.netid" /></label>
						<c:if test="${not empty sessionScope.openIdLocalId}">
						<strong>${sessionScope.openIdLocalId}</strong>
						<input type="hidden" id="username" name="username" value="${sessionScope.openIdLocalId}" />
						</c:if>
						
						<c:if test="${empty sessionScope.openIdLocalId}">
						<spring:message code="screen.welcome.label.netid.accesskey" var="userNameAccessKey" />
						<form:input cssClass="required" cssErrorClass="error" id="username" size="25" tabindex="1" accesskey="${userNameAccessKey}" path="username" autocomplete="false" htmlEscape="true" />
						</c:if>
                    </div>
                    <div class="row">
                        <label for="password" style="display: inline-block; width: 90px"><spring:message code="screen.welcome.label.password" /></label>
						<%--
						NOTE: Certain browsers will offer the option of caching passwords for a user.  There is a non-standard attribute,
						"autocomplete" that when set to "off" will tell certain browsers not to prompt to cache credentials.  For more
						information, see the following web page:
						http://www.geocities.com/technofundo/tech/web/ie_autocomplete.html
						--%>
						<spring:message code="screen.welcome.label.password.accesskey" var="passwordAccessKey" />
						<form:password cssClass="required" cssErrorClass="error" id="password" size="25" tabindex="2" path="password"  accesskey="${passwordAccessKey}" htmlEscape="true" autocomplete="off" />
                    </div>
                    <div class="row btn-row">
						<input type="hidden" name="lt" value="${flowExecutionKey}" />
						<input type="hidden" name="_eventId" value="submit" />
                        <input class="btn-submit" style="margin-left: 0; float:none;" name="submit" accesskey="l" value="<spring:message code="screen.welcome.button.login" />" tabindex="4" type="submit" />
                    </div>
                </div>
               
	            <div id="sidebar" style="clear:both; margin-top: 20px;">
	                <p><spring:message code="screen.welcome.security" /></p>
	            </div>
	            
        	</form:form>
        </div>
    </div>
</div>
<jsp:directive.include file="includes/bottom.jsp" />
