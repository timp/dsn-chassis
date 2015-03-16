# Tomcat configuration #
This assumes that you will be running Chassis and CAS (manta-webapp and wwarn-cas) in the same Tomcat instance. If not then the Resource element can be added to the application context directly.

In `server.xml` add to the GlobalNamingResources element the following definition of the JDBC connection to the drupal database, changing the username and password:
```
 <Resource 
    auth="Container" 
    name="jdbc/wwarn_drupal"  
    driverClassName="com.mysql.jdbc.Driver" 
    initialSize="10"
 jdbcInterceptors="ConnectionState;StatementFinalizer;SlowQueryReportJmx(threshold=10000)" 
    jmxEnabled="true" 
    logAbandoned="true" 
    maxActive="100" 
    maxIdle="100" 
    maxWait="10000" 
    minEvictableIdleTimeMillis="30000" 
    minIdle="10" 
    removeAbandoned="true" 
    removeAbandonedTimeout="60" 
    testOnBorrow="true" 
    testOnReturn="false" 
    testWhileIdle="true" 
    timeBetweenEvictionRunsMillis="5000" 
    type="javax.sql.DataSource"
    validationInterval="30000" 
    validationQuery="SELECT 1"
    url="jdbc:mysql://127.0.0.1:3306/wwarn_drupal?autoReconnect=true"

username="username" 
password="password"

/>
```

This connection is used in the `context.xml` for the application
```
  <ResourceLink 
     global="jdbc/wwarn_drupal" 
     name="jdbc/wwarn_drupal" 
     type="javax.sql.DataSource"
   />
```

Add the `mysql-connector-java-5.1.18.jar` to the tomcat endorsed directory (the same place as the manta-config jar ie `/usr/share/tomcat7/lib/`).