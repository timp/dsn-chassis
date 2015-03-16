# eXist Setup #

This page contains notes on setting up an Eclipse Dynamic Web Project for the Chassis generic services based the Atom Protocol, based on a distribution of the eXist XML database.

## eXist 1.4rc-rev10028 ##

### Initial Setup ###

**1. Download the jar distribution.**

```
$ wget http://sourceforge.net/projects/exist/files/Stable%20Builds/1.4/eXist-setup-1.4rc-rev10028.jar/download
```

**2. Install eXist on the local system.**

```
$ sudo java -jar eXist-setup-1.4rc-rev10028.jar 
```

I installed to `/usr/local/exist-1.4rc-rev10028`.

Make sure the source package is selected.

**3. Build a WAR distribution.**

```
$ cd /usr/local/exist-1.4rc-rev10028
$ sudo ./build.sh dist-war
```

See also: http://exist.sourceforge.net/building.html#war

**4. Import WAR as dynamic web project.**

In Eclipse (Galileo), do:

```
File > Import
Web > WAR file
Next
WAR file: /usr/local/exist-1.4rc-rev10028/dist/exist-1.4.0rc-rev10028.war
Web project: googlecode_dsn-chassis_generic_service_exist-1.4.0rc-rev10028
Target runtime: Apache Tomcat v6.0
Next
Finish
```

**5. Configure web project settings.**

In Eclipse (Galileo), with the newly created project selected, do:

```
Project > Properties
Web Project Settings
Context root: chassis-generic-service-exist
```

**6. Check it runs.**

In Eclipse (Galileo), open the **Servers** view, right click **Tomcat v6.0 Server at localhost**, select **Add and Remove**, and add the newly created project.

To be on the safe side, right click **Tomcat v6.0 Server at localhost** again, select **Clean**.

Start the server.

Browse to: http://localhost:8080/chassis-generic-service-exist/

You should see the eXist home page.

**7. Share with SVN.**

In Eclipse (Galileo), right click the newly created project, and do:

```
Team > Share
Select a repository type: SVN
Next
Use existing repository location: http://dsn-chassis.googlecode.com/svn
Simple Mode
URL: https://dsn-chassis.googlecode.com/svn/branches/generic/service/exist-1.4.0rc-rev10028
Next
Finish
```

The commit dialog should be launched automatically as soon as the share operation is complete.

**8. Tag in SVN.**

In Eclipse (Galileo), right click the project, and do:

```
Team > Tag
Tag: generic/service/exist-1.4.0rc-rev10028-base
```

This gives a baseline to roll back to, if any configuration goes wrong.

### Customising the Project ###

The following steps customise the project for use within Chassis.

**1. Make a copy of original web.xml configuration file.**

For convenience, before doing any customisation, copy the original web.xml file to the same directory, file name web-original.xml.

**2. Configure Java EE module dependencies.**

Some of the customisations depend on code in the Chassis Generic Java Lib project. This project needs to be added as a Java EE module dependency of the exist web project.

Eclipse (Galileo), with the exist web project selected, do:

```
Project > Properties
Java EE Module Dependencies
Select googlecode_dsn-chassis_generic_lib_java
```

This puts the necessary source code on the build and runtime classpath.

**3. Configure HTTP method override filter for Atom services.**

Add the following directives to the web.xml file:

```
    <!-- HTTP Method Override Filter, for Atom Services -->

    <filter>
      <filter-name>HttpMethodOverrideFilter</filter-name>
      <filter-class>org.cggh.chassis.generic.http.HttpMethodOverrideFilter</filter-class>
      <init-param>
        <param-name>allowedOverrides</param-name>
        <param-value>PUT DELETE</param-value>
      </init-param>
    </filter>
    
    <filter-mapping>
      <filter-name>HttpMethodOverrideFilter</filter-name>
      <servlet-name>AtomServlet</servlet-name>
    </filter-mapping>
```

I put these directives directly after the `<servlet>` declaration for the AtomServlet, and before the start of the Cocoon configuration.

(This filter is required to workaround the fact that GWT only support GET and POST HTTP requests, amongst other things.)

**3. Configure content type override filter.**

The GWT hosted mode browser (at least on linux) has an awkward bug, where content served with content-type application/atom+xml is truncated at 4096 bytes. The exist atom servlet uses application/atom+xml by default, so cannot be used with the GWT hosted mode browser.

To work around this in the development environment, add the following directives to the web.xml file:

```
    <!--+
        | HTTP Content Type Override Filter, for Atom Services 
        | needed to work around bug in GWT hosted mode browser
        | see http://code.google.com/p/google-web-toolkit/issues/detail?id=4095
        | N.B. this is needed for development only - remove this in production
        +-->

    <filter>
      <filter-name>ContentTypeOverrideFilter</filter-name>
      <filter-class>org.cggh.chassis.generic.http.ContentTypeOverrideFilter</filter-class>
      <init-param>
        <param-name>contentType</param-name>
        <param-value>application/xml</param-value>
      </init-param>
    </filter>
    
    <filter-mapping>
      <filter-name>ContentTypeOverrideFilter</filter-name>
      <servlet-name>AtomServlet</servlet-name>
    </filter-mapping>
```

I put these after the method override filter declaration.

N.B. this filter is not required in a production environment, because browsers other than GWT hosted mode browser do not truncate responses with content type application/atom+xml, at least not that we've found so far. The other thing to note is that the filter is based on an HTTP buffering filter, and hence buffers all responses before sending them - this is likely to cause memory bottlenecks in a production setting.


**4. Spring security integration.**

Copy the following JARs to the **WEB-INF/lib** folder:

  * mysql-connector-java-5.0.4-bin.jar
  * spring-framework-2.5.6.SEC01.jar
  * spring-security-core-2.0.5.RELEASE.jar

Add the following directives at the top of the **web.xml** configuration file.

```
  <!--+
      | Spring security configuration
      +-->

  <context-param>
    <param-name>contextConfigLocation</param-name>
    <param-value>
      classpath:/dev/configuration/spring/security.xml
    </param-value>
  </context-param>
    
  <listener>
    <listener-class>
      org.springframework.web.context.ContextLoaderListener
    </listener-class>
  </listener>
    
  <filter>
    <filter-name>springSecurityFilterChain</filter-name>
    <filter-class>org.springframework.web.filter.DelegatingFilterProxy</filter-class>
  </filter>

  <filter-mapping>
    <filter-name>springSecurityFilterChain</filter-name>
    <url-pattern>/*</url-pattern>
  </filter-mapping>
```

The Chassis development environment simulates the production user authentication and authorisation environment, using HTTP basic authentication, configured via Spring Security. The eXist atom servlet will also attempt to authenticate any previously authorised requests using its own user database, and hence a second challenge can get sent. Basically, Spring Security and the eXist Atom Servlet don't play nice, if you're using HTTP basic authentication (and probably digest too).

To work around this, add the following directives to the web.xml configuration file.

```
    <!--+
        | Authorization Override Filter
        | used to strip Authorization header 
        | so eXist AtomServlet doesn't get confused by Spring Security
        | if configured to use HTTP basic or digest authentication
        +-->

    <filter>
      <filter-name>AuthorizationOverrideFilter</filter-name>
      <filter-class>org.cggh.chassis.generic.http.RequestHeaderOverrideFilter</filter-class>
      <init-param>
        <param-name>headerName</param-name>
        <param-value>Authorization</param-value>
      </init-param>
    </filter>
    
    <filter-mapping>
      <filter-name>AuthorizationOverrideFilter</filter-name>
      <servlet-name>AtomServlet</servlet-name>
    </filter-mapping>
```

I put this after the content type override filter.

Also, modify the declaration for the AtomServlet, adding some initialisation parameters ...
```
    <!-- Atom Atom Publishing Protocol -->
    <servlet>
        <servlet-name>AtomServlet</servlet-name>
        <servlet-class>org.exist.atom.http.AtomServlet</servlet-class>
        <init-param>
          <param-name>use-default-user</param-name>
          <param-value>true</param-value>
        </init-param>
        <init-param>
          <param-name>user</param-name>
          <param-value>admin</param-value>
        </init-param>
        <init-param>
          <param-name>password</param-name>
          <param-value></param-value>
        </init-param>
    </servlet>
```

... and modify the declaration for the !EXistServlet, adding the same init params...

```
    <!-- 
        DatabaseAdminServlet: this servlet can be used to ensure that
        eXist is running in the background. Just set the start-parameter 
        to true and load-on-startup to 1 
    -->
    <servlet>
        <servlet-name>EXistServlet</servlet-name>
        <servlet-class>org.exist.http.servlets.EXistServlet</servlet-class>
    
        <!--
            where to find eXist's configuration file relative to the basedir 
            of the web-application.
        -->
        <init-param>
            <param-name>configuration</param-name>
            <param-value>conf.xml</param-value>
        </init-param>

        <!-- 
            eXist's home directory. All file names in the configuration file 
            will be relative to this directory.
        -->
        <init-param>
            <param-name>basedir</param-name>
            <param-value>WEB-INF/</param-value>
        </init-param>

        <init-param>
            <param-name>start</param-name>
            <param-value>true</param-value>
        </init-param>

        <!-- init params to turn off exist's own authentication -->
        
        <init-param>
          <param-name>use-default-user</param-name>
          <param-value>true</param-value>
        </init-param>
        <init-param>
          <param-name>user</param-name>
          <param-value>admin</param-value>
        </init-param>
        <init-param>
          <param-name>password</param-name>
          <param-value></param-value>
        </init-param>

        <load-on-startup>2</load-on-startup>
    </servlet>
```

**5. Custom query services**

Add XQuery scripts to the **WebContent/query** folder as needed.

**TODO**

Still TODO, strip out everything that isn't necessary or that might present a security risk.

### Configuring the Running System ###

The following steps are required to configure the running system for use by Chassis clients. You will have to repeat these steps if you ever subsequently **Clean** the project from within the **Servers** view.

**1. Start the service.**

From within the **Servers** view, start the **Tomcat v6.0 Server at localhost**.

**2. Create atom collections.**

To bootstrap the Atom collections, log in to the Chassis client as an administrator, and use the Collections Admin screen to create all collections.

To check this has worked, browse to any of the collection URLs using Firefox - you should see empty Atom feeds.

