I've created a new project for the chassis manta web application which is built using maven and which overlays atombeat as a war overlay, which should simplify the process of upgrading atombeat.

To get started, follow the steps below.

# 1. Checkout and Build AtomBeat #

AtomBeat is the main dependency of the new project. It's not available yet in any maven repositories, so you have to checkout, build and install it yourself.

The DOS equivalent of the UNIX "export" command is "set", used in the same way.

```
$ svn checkout http://atombeat.googlecode.com/svn/trunk/parent /path/to/atombeat-trunk
$ cd /path/to/atombeat-trunk
$ export MAVEN_OPTS="-Xmx1024M -XX:MaxPermSize=256M"
$ mvn clean install
```

This will take a couple of minutes, to download all dependencies and run the integration tests.

# 2. Checkout, build and install manta-xquery-functions #

```
svn co http://dsn-chassis.googlecode.com/svn/tags/manta-xquery-functions-0.1-beta-1
cd manta-xquery-functions-0.1-beta-1
mvn clean install
```

# 3. Checkout, build and install manta-config #

```
svn co http://dsn-chassis.googlecode.com/svn/tags/manta-config-0.0.1-beta-1
cd manta-config-0.0.1-beta-1
mvn clean install
```

# 4. Checkout and Build Manta-Maven #

To checkout and build the new maven-structured project, do:

```
$ svn checkout https://dsn-chassis.googlecode.com/svn/branches/manta-maven /path/to/manta-maven
$ cd /path/to/manta-maven
$ cd manta-webapp
$ mvn clean package
```

If this works, then you are ready to import the project into eclipse.

# 5. Import Manta-Webapp into Eclipse #

Install m2eclipse from sonatype.

In eclipse, do:

  * File > Import
  * General > Existing Projects into Workspace
  * Select root directory: /path/to/manta-maven
  * Finish

Add manta-webapp to your localhost tomcat server in eclipse.

Start the server, and go to http://localhost:8080/repository/

Follow the usual process of installing the atom collections then trying out the UI functionality. It **should** work :-) N.B., you may need to modify the configuration variable $config:media-storage-dir before the collections will install.

# Notes #

## Migration Notes ##

The new manta-maven/manta-webapp project contains files copied from [revision 3034](https://code.google.com/p/dsn-chassis/source/detail?r=3034) of the current manta project trunk. This means that more recent work will need to be copied across. Also, I made some minor changes to manta-plugin.xqm to get it working, but haven't fully checked or replicated the most recent work on tombstones.

## Updating AtomBeat ##

Using the AtomBeat trunk means that you can pull the latest changes from svn and rebuild. E.g., if you know some work has been done in the AtomBeat trunk, you can do:

```
$ cd /path/to/atombeat-trunk
$ svn up
$ mvn clean install
```

You will then need to clean and repackage manta, e.g.:

```
$ cd /path/to/manta-maven
$ cd manta-webapp
$ mvn clean package
```

Checking out the AtomBeat trunk allows you to use snapshot builds as dependencies in the new manta project. The current version of AtomBeat in the trunk is 0.2-alpha-3-SNAPSHOT.

N.B. if you are using the AtomBeat trunk, bear in mind that the version number in the trunk may change if a new release of AtomBeat is issued, so you would need to update the manta-webapp POM.

You can checkout, build and install specific versions of AtomBeat, e.g.:

```
$ svn checkout http://atombeat.googlecode.com/svn/tags/atombeat-parent-0.2-alpha-2 /path/to/atombeat-0.2-alpha-2
$ cd /path/to/atombeat-0.2-alpha-2
$ mvn clean install
```


## Triggering XQuery Recompile ##

To trigger a recompile of the atombeat xqueries without restarting the servlet container, make a trivial change to the file /path/to/manta-maven/manta-webapp/target/manta-webapp/service/content.xql

FYI the target folder is where the manta-webapp application is packaged when you build with maven. The src/main/webapp folder is where the manta-webapp files are.

When you build with maven, maven constructs a web application by overlaying war dependencies (i.e., atombeat) on the contents of src/main/webapp, but src/main/webapp takes priority, so you can override some files in atombeat. The web application is first constructed in target/manta-webapp before being packaged into a war.

To get instant gratification in Eclipse, the eclipse project is configured to do something similar. In fact, the eclipse project is configured to serve the content from both target/manta-webapp and src/main/webapp, where the latter takes priority. This means that what you get is the latest maven build overlaid with your source code. So any changes you make to your source (i.e., files in src/main/webapp) will get noticed by eclipse and auto-published to tomcat. If you ever want to change this behaviour, see the .settings/org.eclipse.wst.common.component file.