# FIXME No authentication - see https://wiki.internet2.edu/confluence/display/CPD/Monitoring+Tomcat+with+JMX
JAVA_OPTS="-Xms128m -Xmx1024m -XX:MaxPermSize=256m " #
JAVA_OPTS="$JAVA_OPTS -Dfile.encoding=UTF8 -Djava.awt.headless=true "
JAVA_OPTS="$JAVA_OPTS -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=8999 "
JAVA_OPTS="$JAVA_OPTS -Dcom.sun.management.jmxremote.ssl=false "
JAVA_OPTS="$JAVA_OPTS -Dcom.sun.management.jmxremote.authenticate=false "
JAVA_OPTS="$JAVA_OPTS -Djava.rmi.server.hostname=localhost "
JAVA_OPTS="$JAVA_OPTS -Dcom.sun.management.jmxremote.password.file=$CATALINA_BASE/conf/jmxremote.password"
JAVA_OPTS="$JAVA_OPTS -Dcom.sun.management.jmxremote.access.file=$CATALINA_BASE/conf/jmxremote.access"

export JAVA_OPTS

