cd
wget http://google-web-toolkit.googlecode.com/files/gwt-linux-1.7.1.tar.bz2
ls
mkdir download
mv gwt-linux-1.7.1.tar.bz2 download/
cd download/
gunzip gwt-linux-1.7.1.tar.bz2 
bunzip gwt-linux-1.7.1.tar.bz2 
bunzip2 gwt-linux-1.7.1.tar.bz2 
ls
tar xf gwt-linux-1.7.1.tar 
ls
cd gwt-linux-1.7.1
ls
mvn install:install-file -DgroupId=com.google.gwt -DartifactId=gwt-dev -Dversion=1.7.1 -Dclassifier=linux -Dpackaging=jar -Dfile=gwt-dev-linux.jar
mail timp@paneris.org
exit
ls
cd ~
cd jobs/
cd googlecode_dsn-chassis_generic_lib_gwt-test/
cd workspace/gwt-test/
mvn clean install
more /etc/init.d/hudson
Xvfb :1 -ac -screen 0 1024x768x8 & export DISPLAY=:1
Xvfb :1 -ac -screen 0 1024x768x8
mvn clean install
exit
cd ~/jobs/googlecode_dsn-chassis_generic_lib_gwt-test/workspace/gwt-test/
mvn clean install
Xvfb :1 -ac -screen 0 1024x768x8 &
export DISPLAY=:1 
mvn clean install
ps axww
ps axww \grep vfb
ps axww |grep vfb
Xvfb :5 >/dev/null 2>&1 &
proc=$! 
export DISPLAY=:5 
ps axww |grep vfb
mvn clean install
exit
cd ~/jobs/googlecode_dsn-chassis_generic_lib_gwt-test/workspace/gwt-test/
mvn clean install
Xvfb :5 >/dev/null 2>&1 &  proc=$! 
export DISPLAY=:5 
mvn clean install
Xvfb -extension RANDR :5 >/dev/null 2>&1 &  proc=$! 
Xvfb -extension RANDR :5 
Xvfb 
exit
cd ~/jobs/googlecode_dsn-chassis_generic_lib_gwt-test/workspace/gwt-test/
Xvfb -extension RANDR -auth /etc/X99.cfg  -screen scrn 800x600x32 :5 
exit
/etc/init.d/xvfb
exit
/etc/init.d/xvfb
/etc/init.d/xvfb start
/etc/init.d/xvfb start
exit
/etc/init.d/xvfb start
exit
cd ~/jobs/googlecode_dsn-chassis_generic_lib_gwt-test/workspace/gwt-test/
mvn clean test
mvn clean install
exit
cd ~/jobs/googlecode_dsn-chassis_generic_lib_gwt-test/workspace/gwt-test/
cd ~
emacs xvfb 
exit
cd ~/jobs/googlecode_dsn-chassis_generic_lib_gwt-test/workspace/gwt-test/

. xvfb
pwd
emacs xvfb
. xvfb
mkdir var
mkdir var/run
. xvfb
. xvfb
pwd
emacs xvfb
. xvfb
emacs xvfb
. xvfb
. xvfb && mvn clean install
emacs xvfb
. xvfb
emacs xvfb
. xvfb
ps axwww
ps axwww |grep xvfb
ps axwww |grep vfb
Xvfb :5 >/dev/null 2>&1 &
ps axwww |grep vfb
export DISPLAY=:5 
cd ~/jobs/googlecode_dsn-chassis_generic_lib_gwt-test/workspace/gwt-test/
mvn clean install
Xvfb --extension=RANDR :5 >/dev/null 2>&1 &
ps axww
more ~/xvfb
 ~/xvfb
chmod u+x ~/xvfb
ps axwww |grep vfb
ps axwww |grep vfb
 ~/xvfb
 ~/xvfb & 
ps axww
ps axww |grep vfb
ps axww |grep vfb
mvn clean install
ps axww |grep vfb
exit
export DISPLAY=:5 exit
emacs hudson
ps axww |grep vfb
emacs hudson
exit
ps axww |grep vfb
cd ~/jobs/googlecode_dsn-chassis_generic_lib_gwt-test/workspace/gwt-test/
mvn clean install
set |grep DISPLAY
exit
cd ~/jobs/googlecode_dsn-chassis_generic_lib_gwt-test/workspace/gwt-test/
mvn clean install
ps axwww
ps axwww |grep vfb
ps axwww |grep vfb
set |grep DSIPLAY
set |grep DISPLAY
export DISPLAY=:5 exit
export DISPLAY=:5 
mvn clean install
exit
cd ~
cd jobs/googlecode_dsn-chassis_generic_service_exist/workspace/exist-1.4.0rc-rev10028/
emacs pom.xml 
http://localhost:8080/manager/list
lynx http://localhost:8080/manager/list
emacs pom.xml 
exit
cd ~/
cd jobs/googlecode_dsn-chassis_generic_client_gwt
cd workspace/gwt/
emacs pom.xml 
exit
ls
cd /var/lib/hudson/
ls
cd jobs/googlecode_dsn-chassis_generic_lib_gwt
mvn clean install tomcat:deploy
cd workspace/gwt/
mvn clean install tomcat:deploy
lynx http://localhost:8080/chassis-generic-client-gwt
lynx http://localhost:8080/chassis-generic-client-gwt
mvn  tomcat:deploy
cd ..
cd ..
cd ..
cd googlecode_dsn-chassis_generic_client_gwt
cd workspace/gwt/
ls
mvn clean install tomcat:deploy
cd ..
cd ..
cd ..
cd googlecode_dsn-chassis_generic_service_exist/
ls 
cd workspace/exist-1.4.0rc-rev10028/
ls
svn status
cd ..
cd ..
cd ..
cd googlecode_dsn-chassis_generic_service_upload/workspace/upload/
mvn -X tomcat:deploy
mvn -X tomcat:deploy
exit
cd /var/lib/hudson/jobs/googlecode_dsn-chassis/workspace/
ls -la
svn up
mvn clean 
exit
ls
exit
mvn clean install 
ls
ls
emacs pom.xml 
mvn clean install 
emacs pom.xml 
mvn clean install 
cd ..
cd ..
cd googlecode_dsn-chassis_generic_service_user/
cd workspace/user/
svn up
whoami
ls
mvn tomcat:redeploy
whoami
cd ..
cd ..
cd ..
cd googlecode_dsn-chassis/workspace/gwt/
ls
more pom.xml 
svn up
svn stat
mvn clean 
emacs 
cd ..
ls
cd gwt/
ls -la
cd ..
ls
ls
cd trunk/
ls
mvn install
mvn install -Dmaven.test.skip=true 
exit
cd ~
ls
cd jobs/
ls
cd googlecode_dsn-chassis
ls
cd workspace/
ls
cd trunk/
ls
svn stat
svn up
mvn clean install
ps axwwww
ps axwwww |grep vfb
cd ~
ls
more xvfb
. xvfb
ls -la /var/lib/hudson/var/
ls -la /var/lib/hudson/var/run/
ls -la /var/lib/hudson/var/run/
whoami
chown u+w  /var/lib/hudson/var/run/
chmod  u+w  /var/lib/hudson/var/run/
ls -la /var/lib/hudson/var/run/
touch /var/lib/hudson/var/run/Xvfb_screen0
. xvfb
. xvfb &
ps axwwww |grep vfb
more xvfb
exit
cd ~
ls
cd jobs/googlecode_dsn-chassis
cd workspace/trunk/
ls
ls tomcat/
svn up
ls
mvn clean install
exit
cd ~
cd jobs/googlecode_dsn-chassis
ls
cd workspace/
ls
cd trunk/
ls
svn up
/etc/init.d/tomcat6 stop
exit
/etc/init.d/tomcat6 start
exit
cd ~
cd jobs/googlecode_dsn-chassis
cd workspace/trunk/
ls
svn stat
ls .gwt-tmp/
ls tomcat/
ls -la
rm -rf tomcat/
svn up
mvn clean install 
exit
cd ~
ls
more xvfb
exit
cd ~
cd jobs/googlecode_dsn-chassis/workspace/trunk/
svn up
mvn clean install
more ~/.m2/repository/com/google/gwt/gwt-user/1.7.1/gwt-user-1.7.1.
cd generic/lib/java/
mvn clean install
cd ..
mvn clean install
cd ..
mvn clean install
cd ..
mvn clean install
exit
cd ~
cd jobs/googlecode_dsn-chassis
cd workspace/
ls
cd trunk/
ls
svn up
mvn clean install
find * -name ]
find * -name *gwt.xml
find * -name *gwt.xml |xargs grep XML 
find * -name *gwt.xml |grep -v target | xargs grep XML 
find * -name *gwt.xml |grep -v target | xargs grep XML 
locate elementImpl.java
locate ElementImpl.java
jedit pom.xml 
cd ~
cd jobs/googlecode_dsn-chassis
svn up
ls
cd workspace/trunk/
svn up
cd generic/service/exist/
mvn install
cd ..
cd ..
ls
cd lib/
ls
mvn install
cd ..
cd ..
cd wwarn/
mvn clean install
svn up
mvn clean install
svn up
du
df
exit
irssi
cd ~
ls 
more hudson.plugins.ircbot.IrcPublisher.xml 
more hudson.plugins.ircbot.IrcPublisher.xml 
more hudson.plugins.ircbot.IrcPublisher.xml 
ls -la var/lib/hudson/jobs/googlecode_dsn-chassis/workspace/trunk/generic/client/gwt/target/chassis-generic-client-gwt-0.1-alpha-7-SNAPSHOT/WEB-INF/classes/org/cggh/chassis/generic/client/gwt/widget/data/client/dataset/
ls -la var/lib/hudson/jobs/googlecode_dsn-chassis/workspace/trunk/generic/client/gwt/target/chassis-generic-client-gwt-0.1-alpha-7-SNAPSHOT/WEB-INF/classes/
ls -la /var/lib/hudson/jobs/googlecode_dsn-chassis/workspace/trunk/generic/client/gwt/target/chassis-generic-client-gwt-0.1-alpha-7-SNAPSHOT/WEB-INF/classes/org/cggh/chassis/generic/client/gwt/widget/data/client/dataset/
ls -la /var/lib/hudson/jobs/googlecode_dsn-chassis/workspace/trunk/generic/client/gwt/target/chassis-generic-client-gwt-0.1-alpha-7-SNAPSHOT/WEB-INF/classes/
ls -la /var/lib/hudson/jobs/googlecode_dsn-chassis/workspace/trunk/generic/client/gwt/target/
ls -la /var/lib/hudson/jobs/googlecode_dsn-chassis/workspace/trunk/
cd  /var/lib/hudson/jobs/googlecode_dsn-chassis/workspace/trunk/
mvn clean
df
ls /mnt
ls /var
cd /var/lib/
ls
du
ls
ls -la
exit
cd hudson
ls -la
pwd
ls -la
rm xvfb
more hudson.plugins.ircbot.IrcPublisher.xml 
ls 
exit
cd ~
ls
emacs hudson.plugins.ircbot.IrcPublisher.xml 
exit
cd ~
cd jobs/googlecode_dsn-chassis_generic_lib_gwt/workspace/
ls
cd gwt/
ls
svn up
mvn clean install
mvn install:install-file -DgroupId=com.google.code.gwt-log -DartifactId=gwt-log -Dversion=3.0.0 -Dpackaging=jar -Dfile=lib/main/gwt-log-3.0.0.jar 
exit
cd ~
cd jobs/googlecode_dsn-chassis
cd workspace/
ls
cd trunk/
ls
svn up
ls
mvn site site:deploy
emacs /etc/apache2/apache2.conf 
exit
cd ~
cd jobs/googlecode_dsn-chassis/workspace/trunk/
svn up
top
mvn site site:deploy
svn up
mvn site site:deploy
svn up
mvn site site:deploy
svn up
mvn site site:deploy
exit
cd ~
ls
ls users/
ls userContent/
more userContent/readme.txt 
cd user
ls
cd users
ls
emacs tim.pizey
ls -la
ls -la tim.pizey
cd tim.pizey\@gmail.com/
ls
more config.xml 
ls
cd ..
rmdir  tim.pizey\@gmail.com/
ls
diff timp/config.xml tim.pizey/config.xml 
ls
diff lee/config.xml timp/config.xml 
emacs  lee/config.xml timp/config.xml 
emacs  lee/config.xml
emacs  timp/config.xml 
emacs  lee/config.xml
emacs Lee\ Hart/config.xml 
cd ..
ls
rm xvfb~ 
ls -la
find * -name '*.xml'  |grep -v jobs |xargs grep timp
emacs config.xml 
exit
set
cd ~
more .bashrc 
more /etc/default/hudson
emacs  /etc/default/hudson
emacs  /etc/init.d/hudson
exit
