**TODO this is out of date**



# Introduction #

The instructions below guide you through a typical deployment, using a web server running Apache proxying through to an application server running Tomcat. N.B. This is one of many possible deployment setups, for more information see also [DevGuideRPCDeployment](http://code.google.com/webtoolkit/doc/1.6/DevGuideServerCommunication.html#DevGuideRPCDeployment) and [DevGuideDeploying](http://code.google.com/webtoolkit/doc/1.6/DevGuideDeploying.html) in the GWT docs.

The examples below assume your **web server** is deployed at `www.example.com`, and your **application server** is deployed at `servlet.example.com:8080`.

# Deploying WARs on the Application Server #

[Download](http://code.google.com/p/dsn-chassis/downloads/list) the latest release of the Chassis Generic GWT Client, the Chassis Generic User Service, and the Chassis Generic Exist Service, to your **application server**. E.g. ...

```
user@servlet.example.com$ wget http://dsn-chassis.googlecode.com/files/chassis-generic-client-gwt-0.1-alpha-2.war
user@servlet.example.com$ wget http://dsn-chassis.googlecode.com/files/chassis-generic-service-user-0.1-alpha-2.war
user@servlet.example.com$ wget http://dsn-chassis.googlecode.com/files/chassis-generic-service-exist-0.1-alpha-2.war
```

Copy these war files to the Tomcat webapps folder. E.g. ...

```
user@servlet.example.com$ sudo cp chassis-generic-client-gwt-0.1-alpha-2.war /var/lib/tomcat6/webapps/
user@servlet.example.com$ sudo cp chassis-generic-service-user-0.1-alpha-2.war /var/lib/tomcat6/webapps/
user@servlet.example.com$ sudo cp chassis-generic-service-exist-0.1-alpha-2.war /var/lib/tomcat6/webapps/
```

Tomcat will usually unpack and deploy these war files automatically. If it doesn't, try restarting Tomcat.

# Creating Atom Collections #

Chassis uses an Atom service (based on Exist) to store data about studies, submissions, etc.

You will need to create the appropriate collections in the Atom service. This can be done by running the following Python script from the **application server**.

```
import httplib

host = "localhost:8080"
contextpath = "/chassis-generic-service-exist-0.1-alpha-2/atom/edit"

print "creating study collection ..."

content = u"""
<feed xmlns="http://www.w3.org/2005/Atom">
  <title>Studies</title>
</feed>
"""

path = contextpath + "/studies"

headers = { "Content-Type": "application/atom+xml", "Content-Length": len(content) }
conn = httplib.HTTPConnection(host)
conn.request("POST", path, content, headers)
response = conn.getresponse()

print response.status, response.reason
data = response.read()
print data
conn.close()
```

If you get a 204 response, the script was successful.

# Initialise config collection #
  1. [AdminHome](http://localhost:8080/repository/administration/home)
  1. [update-1.1-1.2](http://localhost:8080/repository/service/admin/update-1.1-1.2/index.xhtml)
  1. [init config](http://localhost:8080/repository/service/admin/init-config.xql)

# Web Server Proxy Configuration #

Add or update proxy directives to the Apache configuration on your **web server**. E.g. ...

```
ProxyPass         /chassis/client  http://servlet.example.com:8080/chassis-generic-client-gwt-0.1-alpha-2
ProxyPassReverse  /chassis/client  http://servlet.example.com:8080/chassis-generic-client-gwt-0.1-alpha-2

ProxyPass         /chassis/service/user  http://servlet.example.com:8080/chassis-generic-service-user-0.1-alpha-2
ProxyPassReverse  /chassis/service/user  http://servlet.example.com:8080/chassis-generic-service-user-0.1-alpha-2

ProxyPass         /chassis/service/exist  http://servlet.example.com:8080/chassis-generic-service-exist-0.1-alpha-2
ProxyPassReverse  /chassis/service/exist  http://servlet.example.com:8080/chassis-generic-service-exist-0.1-alpha-2
```

Restart Apache on your web server.

# Client Configuration #

The Chassis client application needs to be configured to point to the correct locations for the services you have deployed.

Edit the Chassis Generic Client configuration file deployed on your **application server** to point to the correct User Service location. E.g. do ...

```
user@servlet.example.com$ sudo emacs /var/lib/tomcat6/webapps/chassis-generic-client-gwt-0.1-alpha-2/spike/configuration.js 
```

... and edit to ...

```
CHASSIS.userDetailsServiceEndpointURL = "/chassis/service/user/gwtrpc/userdetails";
CHASSIS.studyFeedURL = "/chassis/service/exist/atom/edit/studies";
```

# Installation of ClamAV #

<pre>
apt-get install clamav clamav-daemon clamav-docs libclamunrar6<br>
</pre>

NOTE: libclamunrar6 is in Debian Karmic but not Debian Jaunty.

## Configuration of ClamAV ##

<pre>
dpkg-reconfigure clamav-base<br>
</pre>

Non-default choices:
  * Enable tcp sockets
  * Only listen to 127.0.0.1
  * Do not scan mail

# Security Configuration #

Chassis uses [Spring Security 2.0.x](http://static.springsource.org/spring-security/site/) to integrate with various authentication and authorisation mechanisms.

To integrate Chassis with your authentication and authorisation environment, you will have to create a Spring Security configuration file.

You will need to create your own Spring Security configuration file, and place it in a secure location on your **application server**. Let's assume that you put this file at `/etc/chassis/security.xml`.

You will then need to edit the `web.xml` files in **each** of the deployed WARs on your application server, and update the following parameter to point to the location where your Spring Security configuration file is located.

```
  <context-param>
    <param-name>contextConfigLocation</param-name>
    <param-value>
      file:///etc/chassis/security.xml
    </param-value>
  </context-param>
```

A sed command like the following could be used to make this less of a chore.

```
sudo sed -i -e "s|classpath:/dev/configuration/spring/security\.xml|file:///etc/chassis/security.xml|" /var/lib/tomcat6/webapps/chassis-generic-*-0.1-alpha-2/WEB-INF/web.xml
```

## Example Spring Security Configuration - Simple ##

The Spring Security configuration file below is the one we use for our development environment. N.B. this may not suitable for a production environment.

```
<?xml version="1.0" encoding="UTF-8"?>
<beans:beans 
  xmlns:beans="http://www.springframework.org/schema/beans"
  xmlns="http://www.springframework.org/schema/security"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-2.0.xsd
              http://www.springframework.org/schema/security http://www.springframework.org/schema/security/spring-security-2.0.4.xsd">  

  <http auto-config='true' realm="chassis-development-realm">
    <intercept-url pattern="/**" access="ROLE_CHASSIS_USER" />
    <http-basic/>
  </http>
  
  <authentication-provider>
    <user-service>
      <user name="foo" password="bar" authorities="ROLE_CHASSIS_USER, ROLE_CHASSIS_SUBMITTER, ROLE_CHASSIS_CURATOR, ROLE_CHASSIS_GATEKEEPER, ROLE_CHASSIS_COORDINATOR" />
      <user name="simple" password="user" authorities="ROLE_CHASSIS_USER" />
    </user-service>
  </authentication-provider>
  
</beans:beans>
```

# It Works!? #

That's it.

You should now be able to visit http://www.example.com/chassis/client/spike/ to check it is working.