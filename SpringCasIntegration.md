
```
<?xml version="1.0" encoding="UTF-8"?>
<beans 
  xmlns="http://www.springframework.org/schema/beans"
  xmlns:sec="http://www.springframework.org/schema/security"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-2.0.xsd
              http://www.springframework.org/schema/security http://www.springframework.org/schema/security/spring-security-2.0.4.xsd">  


	<!--+
		|
		| SEE http://mattfleming.com/node/269
		| SEE http://static.springsource.org/spring-security/site/docs/2.0.x/reference/cas.html 
		|
		+-->
		
		

	<!-- FIRST SET UP THE SPRING SECURITY FILTER CHAIN AND HTTP SECURITY RULES -->
		
	<sec:http 
		lowercase-comparisons="false" 
		entry-point-ref="casProcessingFilterEntryPoint">

		<sec:intercept-url 
			pattern="/submitter/**"
			access="ROLE_SUBMITTER"/>
			
		<sec:logout 
			logout-success-url="https://skiathos:8443/cas/logout" 
			invalidate-session="true"/>

	</sec:http>


	
	<!-- NEXT, DEFINE THE ENTRY-POINT FOR CAS PROCESSING -->

	<bean 
		id="casProcessingFilterEntryPoint" 
		class="org.springframework.security.ui.cas.CasProcessingFilterEntryPoint">

		<property 
			name="loginUrl" 
			value="https://skiathos:8443/cas/login"/>

		<property 
			name="serviceProperties" 
			ref="serviceProperties"/>

	</bean>


	
	<!-- NEXT DEFINE THE SERVICE PROPERTIES FOR THIS CAS SERVICE -->
	
	<bean 
		id="serviceProperties" 
		class="org.springframework.security.ui.cas.ServiceProperties">

		<property 
			name="service" 
			value="http://skiathos:8080/test-spring-cas/j_spring_cas_security_check"/>

		<property 
			name="sendRenew" 
			value="false"/>

	</bean>


	<sec:authentication-manager 
		alias="authenticationManager"/>      
	
	
	<bean 
		id="casSingleSignOutFilter" 
		class="org.jasig.cas.client.session.SingleSignOutFilter">
		
		<sec:custom-filter 
			before="CAS_PROCESSING_FILTER"/>

	</bean>
	
	
	<bean 
		id="casProcessingFilter" 
		class="org.springframework.security.ui.cas.CasProcessingFilter">

		<sec:custom-filter after="CAS_PROCESSING_FILTER"/>

		<property 
			name="authenticationManager" 
			ref="authenticationManager"/>

		<property 
			name="authenticationFailureUrl" 
			value="/403.jsp"/>

		<property name="defaultTargetUrl" value="/"/>

	</bean>
	
	
	
	<bean id="casAuthenticationProvider" class="org.springframework.security.providers.cas.CasAuthenticationProvider">

		<sec:custom-authentication-provider />

		<property name="userDetailsService" ref="userService"/>

		<property name="serviceProperties" ref="serviceProperties" />

		<property name="ticketValidator">

			<bean class="org.jasig.cas.client.validation.Cas20ServiceTicketValidator">
				<constructor-arg index="0" value="https://skiathos:8443/cas" />
			</bean>

		</property>

		<property name="key" value="an_id_for_this_auth_provider_only"/>

	</bean>
 	 
<!-- 	 
	<sec:user-service id="userService">
		<sec:user name="alice" password="notused" authorities="ROLE_SUBMITTER" />
		<sec:user name="bob" password="notused" authorities="ROLE_ANOTHER" />
	</sec:user-service>   
-->

	<bean id="userService" class="org.springframework.security.userdetails.jdbc.JdbcDaoImpl">
	  <property name="dataSource" ref="drupal6"/>
	  <property
	  	name="usersByUsernameQuery"
	  	value="
SELECT users.name as username , users.pass as password , users.status as enabled 
FROM users 
WHERE users.name = ?	  	
	  	"/>
	  <property 
	  	name="authoritiesByUsernameQuery" 
	  	value="
SELECT users.name AS username, role.name AS authority
FROM users, users_roles, role
WHERE users.uid = users_roles.uid
AND users_roles.rid = role.rid
AND users.name = ?
		"/>
	</bean>



	<!-- Data source definition -->
	  <bean id="drupal6" class="org.apache.commons.dbcp.BasicDataSource">
	    <property name="driverClassName">
	      <value>com.mysql.jdbc.Driver</value>
	    </property>
	    <property name="url">
	      <value>jdbc:mysql://localhost:3306/drupal6</value>
	    </property>
	    <property name="username"><value>someone</value></property>
	    <property name="password"><value>secret</value></property>
	  </bean>	


    <!-- Log failed authentication attempts to commons-logging -->
    <bean id="loggerListener" class="org.springframework.security.event.authentication.LoggerListener"/>
   
</beans>
```