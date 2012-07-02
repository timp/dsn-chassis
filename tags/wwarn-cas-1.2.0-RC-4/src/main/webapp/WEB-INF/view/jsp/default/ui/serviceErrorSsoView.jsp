<jsp:directive.include file="includes/top.jsp" />
						<div id="content-header">	
                        <div id="content-header-container"><div><h1 class="title"><spring:message code="screen.service.sso.error.header" /></h1></div></div>
                        </div>
							<div id="content-area" class="entry">
							<p><spring:message code="screen.service.sso.error.message"  arguments="${fn:escapeXml(request.requestURI)}" /></p>
                            </div>  
<jsp:directive.include file="includes/bottom.jsp" />