The current chassis application (2012-06-12) is on a branch:
https://dsn-chassis.googlecode.com/svn/branches/manta-maven/manta-webapp

# Introduction #

Whilst you can setup your system as per MantaConfiguration
this  will rely upon using
```
mvn install tomcat:redeploy 
```
to make changes visible.

For a shorter edit/compile/view cycle setup tomcat 'inside ' eclipse.

# Setup #

Follow instructions here:
http://stackoverflow.com/questions/447289/problem-creating-a-tomcat-6-server-in-eclipse-form-ubuntu

This makes a copy of your tomcat installation.

There are however a couple of gotchas:
the manager application does not appear to get copied, so you can no longer use the tomcat deployer.
The context defined in `/srv/chassis-manta/context/repository.xml` is ignored, so you need to add its contents to the `server.xml` copied into eclipse.


## Resource not found error ##

First go to /repository/service/admin/install.xql

This will show you whether the collections are installed and available - if this is the first time then you will need to install them

If you get an error message containing `org.cggh.chassis.manta.xquery.functions.util.config.GetJNDIVariable`
> then you need to check that you have a JNDI configuration either in `server.xml` and/or `/srv/chassis-manta/context/repository.xml`


Another place to check is /repository/service/admin/changehost.xql
The values in the two input boxes should be the same.

If you delete the data set then remember to recreate the config collection:

# Initialise config collection #
  1. [AdminHome](http://localhost:8080/repository/administration/home)
  1. [update-1.1-1.2](http://localhost:8080/repository/service/admin/update-1.1-1.2/index.xhtml)
  1. [init config](http://localhost:8080/repository/service/admin/init-config.xql)