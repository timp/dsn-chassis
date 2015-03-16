# Introduction #

Maven is used to build a release.


# Details #

## Prepare repository ##
Run maven with the following settings:

Goals: release:prepare
This cannot run this with local changes, so check svn status first.
May need to also include the param -Dresume=false

```
cd manta-webapp
svn status
mvn release:prepare -Dusername=_me_ -Dpassword=_mypassword_
```

This will result in a new tag in the repository corresponding to the version in the pom.

## Build server specific wars ##

```
svn co http://dsn-chassis.googlecode.com/svn/tags/manta-webapp-<<VERSION>>-RC-1
cd manta-webapp-<<VERSION>>-RC-1
mvn package -P _profile_ -Duse-alfresco=true -Dalfresco-username=chassis-alfresco -Dalfresco-password=<<SECRET>>
```

## Ensure correct jars are in the tomcat endorsed directory ##

```
cd /usr/share/tomcat6/endorsed
wget http://cloud1.cggh.org/maven2/org/cggh/chassis/manta-config/0.2-alpha-4/manta-config-0.2-alpha-4.jar
```

## Example deployment ##

  1. Unzip the WAR locally to a directory named 1.5.0-RC-2, which contains the WEB-INF, etc.
  1. Upload the directory to your home directory
  1. sudo su
  1. Move the directory to /srv/chassis-manta/
  1. Change the ownership of the directory to tomcat6:tomcat6
  1. Stop Tomcat with /etc/init.d/tomcat6 stop
  1. Remove the repository symlink in /var/lib/tomcat6/webapps/
  1. Create a symlink to the new release with ln -s /srv/chassis-manta/1.5.0-RC-2 repository
  1. Start Tomcat with /etc/init.d/tomcat6 start