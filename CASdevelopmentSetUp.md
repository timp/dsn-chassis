# Details #


  * Checkout wwarn-cas
  * Create the database using the script in resources/min-db.sql
  * Configure tomcat datasources as in [ChassisCASDatabaseSetup](ChassisCASDatabaseSetup.md)
  * Configure Apache

## Apache Configuration ##

1. Set up apache2 to reverse proxy cas through SSL:

```
a2ensite default-ssl
a2enmod ssl
a2enmod proxy_http
```

2. Modify the /etc/apache2/sites-enabled/default-ssl file and add the the following lines to the end of the file, inside the VirtualHost section:

```
        ProxyRequests Off
        <Proxy *>
                Order deny,allow
                Allow from all
        </Proxy>

        ProxyPass /sso http://localhost:8080/sso
        ProxyPassReverse /sso http://localhost:8080/sso
        ProxyPass /repository http://localhost:8080/repository
        ProxyPassReverse /repository http://localhost:8080/repository

```

3. Import the self-signed ssl certificate into the cacerts file for the jvm on the local machine. This will tell java that the local machine is a valid ssl cert issuing authority as far a java is concerned. The ubuntu package for apache2 generates the self-signed certificate when you install it. We can find the one that is being used by apache by looking in the /etc/apache2/sites-enabled/default-ssl configuration file:

```
        SSLCertificateFile    /etc/ssl/certs/ssl-cert-snakeoil.pem
        SSLCertificateKeyFile /etc/ssl/private/ssl-cert-snakeoil.key
```

4. Replace the self-signed SSL certificate to reflect the hostname changes:

```
make-ssl-cert generate-default-snakeoil --force-overwrite
```

5. Import this into the java cacerts file.

```
cd /etc/ssl/certs/java
cp cacerts cacerts.orig
keytool -import -file /etc/ssl/certs/ssl-cert-snakeoil.pem -alias apache -keystore ./cacerts

Enter keystore password:  changeit
Owner: CN=cloud1.cggh.org
Issuer: CN=cloud1.cggh.org
Serial number: f01c6ab885f5c5f5
Valid from: Wed May 18 13:24:53 UTC 2011 until: Sat May 15 13:24:53 UTC 2021
Certificate fingerprints:
	 MD5:  3F:F4:21:86:3E:4B:37:6D:68:18:C2:16:91:EE:C4:58
	 SHA1: 86:36:80:18:CA:F7:9D:6B:21:08:AF:E4:58:F4:B0:63:C5:DF:BA:D4
	 Signature algorithm name: SHA1withRSA
	 Version: 1
Trust this certificate? [no]:  yes
Certificate was added to keystore

```

7. To run the tests you are also going to need to setup trust for localhost

```
 keytool -import -file /etc/ssl/certs/ssl-cert-snakeoil.pem -alias localhost -keystore ./cacerts


 keytool -import -file /etc/ssl/certs/ssl-cert-snakeoil.pem -alias localhost -keystore ./cacerts

```

8. Restart apache2 and tomcat:
```
/etc/init.d/apache2 restart
/etc/init.d/tomcat restart
```