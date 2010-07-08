# See http://www.howtoforge.com/apache2_tomcat5_mod_jk_integration

# setup manta source tree
cd /usr/local/src
ln -s /var/lib/hudson/jobs/manta/workspace/manta .


# install the Apache Portable Runtime
apt-get install  libapr1 libaprutil1

#install tomcat6
apt-get install tomcat6
apt-get install tomcat6-docs
apt-get install tomcat6-admin
cd /etc/tomcat6/
rm server.xml
ln -s /<dist>/manta/src/main/conf/tomcat/server.xml .
cd /usr/share/tomcat6/bin
ln -s /<dist>/manta/src/main/conf/tomcat/setenv.sh .

# Edit  tomcat-users.xml to add 
#
#  <role rolename="manager" />
#
#  <!-- Probe roles -->
#  
#  <role rolename="poweruser" />
#  <role rolename="poweruserplus" />
#  <role rolename="probeuser" />
#
#  <user username="admin" password="" roles="manager,admin" />
#

# Install probe from 
# http://www.lambdaprobe.org/d/installation.shtml

wget http://www.lambdaprobe.org/downloads/1.7/probe.1.7b.zip
unzip probe.1.7b.zip
cp probe.war /var/lib/tomcat6/webapps/

# Build Manta
mvn clean install 
cp target/manta.war /var/lib/tomcat6/webapps/

/etc/init.d/tomcat6 restart


# with JAVA_OPTS="-Xms128m -Xmx1024m -XX:MaxPermSize=256m -Dfile.encoding=UTF8 -Djava.awt.headless=true" 


eric13:/usr/share/tomcat6/bin# ab -n 100 http://localhost:8080/manta/home/
This is ApacheBench, Version 2.3 <$Revision: 655654 $>
Copyright 1996 Adam Twiss, Zeus Technology Ltd, http://www.zeustech.net/
Licensed to The Apache Software Foundation, http://www.apache.org/

Benchmarking localhost (be patient).....done


Server Software:        Apache-Coyote/1.1
Server Hostname:        localhost
Server Port:            8080

Document Path:          /manta/home/
Document Length:        1149 bytes

Concurrency Level:      1
Time taken for tests:   6.603 seconds
Complete requests:      100
Failed requests:        0
Write errors:           0
Total transferred:      143100 bytes
HTML transferred:       114900 bytes
Requests per second:    15.14 [#/sec] (mean)
Time per request:       66.030 [ms] (mean)
Time per request:       66.030 [ms] (mean, across all concurrent requests)
Transfer rate:          21.16 [Kbytes/sec] received

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:        0    0   0.1      0       0
Processing:    31   66  27.9     59     179
Waiting:       30   65  27.4     59     179
Total:         31   66  27.9     59     179

Percentage of the requests served within a certain time (ms)
  50%     59
  66%     72
  75%     80
  80%     89
  90%     95
  95%    123
  98%    161
  99%    179
 100%    179 (longest request)
eric13:/usr/share/tomcat6/bin# ab -n 1000 http://localhost:8080/manta/home/
This is ApacheBench, Version 2.3 <$Revision: 655654 $>
Copyright 1996 Adam Twiss, Zeus Technology Ltd, http://www.zeustech.net/
Licensed to The Apache Software Foundation, http://www.apache.org/

Benchmarking localhost (be patient)
Completed 100 requests
Completed 200 requests
Completed 300 requests
Completed 400 requests
Completed 500 requests
Completed 600 requests
Completed 700 requests
Completed 800 requests
Completed 900 requests
Completed 1000 requests
Finished 1000 requests


Server Software:        Apache-Coyote/1.1
Server Hostname:        localhost
Server Port:            8080

Document Path:          /manta/home/
Document Length:        1149 bytes

Concurrency Level:      1
Time taken for tests:   24.333 seconds
Complete requests:      1000
Failed requests:        0
Write errors:           0
Total transferred:      1431000 bytes
HTML transferred:       1149000 bytes
Requests per second:    41.10 [#/sec] (mean)
Time per request:       24.333 [ms] (mean)
Time per request:       24.333 [ms] (mean, across all concurrent requests)
Transfer rate:          57.43 [Kbytes/sec] received

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:        0    0   0.1      0       1
Processing:    12   24  14.1     19      92
Waiting:       12   23  13.3     19      84
Total:         12   24  14.1     19      92

Percentage of the requests served within a certain time (ms)
  50%     19
  66%     24
  75%     28
  80%     33
  90%     46
  95%     55
  98%     63
  99%     68
 100%     92 (longest request)
eric13:/usr/share/tomcat6/bin# ab -c10 -n 1000 http://localhost:8080/manta/home/
This is ApacheBench, Version 2.3 <$Revision: 655654 $>
Copyright 1996 Adam Twiss, Zeus Technology Ltd, http://www.zeustech.net/
Licensed to The Apache Software Foundation, http://www.apache.org/

Benchmarking localhost (be patient)
Completed 100 requests
Completed 200 requests
Completed 300 requests
Completed 400 requests
Completed 500 requests
Completed 600 requests
Completed 700 requests
Completed 800 requests
Completed 900 requests
Completed 1000 requests
Finished 1000 requests


Server Software:        Apache-Coyote/1.1
Server Hostname:        localhost
Server Port:            8080

Document Path:          /manta/home/
Document Length:        1149 bytes

Concurrency Level:      10
Time taken for tests:   12.162 seconds
Complete requests:      1000
Failed requests:        0
Write errors:           0
Total transferred:      1431000 bytes
HTML transferred:       1149000 bytes
Requests per second:    82.23 [#/sec] (mean)
Time per request:       121.616 [ms] (mean)
Time per request:       12.162 [ms] (mean, across all concurrent requests)
Transfer rate:          114.91 [Kbytes/sec] received

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:        0    0   1.7      0      22
Processing:    18  121  35.7    118     320
Waiting:       17  117  36.2    114     300
Total:         18  121  36.0    118     342

Percentage of the requests served within a certain time (ms)
  50%    118
  66%    131
  75%    141
  80%    148
  90%    166
  95%    185
  98%    211
  99%    223
 100%    342 (longest request)
eric13:/usr/share/tomcat6/bin# ab -c 10 -n 1000 http://localhost:8080/manta/home/
This is ApacheBench, Version 2.3 <$Revision: 655654 $>
Copyright 1996 Adam Twiss, Zeus Technology Ltd, http://www.zeustech.net/
Licensed to The Apache Software Foundation, http://www.apache.org/

Benchmarking localhost (be patient)
Completed 100 requests
Completed 200 requests
Completed 300 requests
Completed 400 requests
Completed 500 requests
Completed 600 requests
Completed 700 requests
Completed 800 requests
Completed 900 requests
Completed 1000 requests
Finished 1000 requests


Server Software:        Apache-Coyote/1.1
Server Hostname:        localhost
Server Port:            8080

Document Path:          /manta/home/
Document Length:        1149 bytes

Concurrency Level:      10
Time taken for tests:   10.272 seconds
Complete requests:      1000
Failed requests:        0
Write errors:           0
Total transferred:      1431000 bytes
HTML transferred:       1149000 bytes
Requests per second:    97.36 [#/sec] (mean)
Time per request:       102.716 [ms] (mean)
Time per request:       10.272 [ms] (mean, across all concurrent requests)
Transfer rate:          136.05 [Kbytes/sec] received

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:        0    0   1.8      0      35
Processing:    17  102  29.4    100     206
Waiting:       16   98  29.9     96     206
Total:         17  103  29.5    101     206

Percentage of the requests served within a certain time (ms)
  50%    101
  66%    112
  75%    119
  80%    125
  90%    141
  95%    157
  98%    171
  99%    179
 100%    206 (longest request)
eric13:/usr/share/tomcat6/bin# ab -c 10 -n 1000 http://localhost:8080/manta/home/
This is ApacheBench, Version 2.3 <$Revision: 655654 $>
Copyright 1996 Adam Twiss, Zeus Technology Ltd, http://www.zeustech.net/
Licensed to The Apache Software Foundation, http://www.apache.org/

Benchmarking localhost (be patient)
Completed 100 requests
Completed 200 requests
Completed 300 requests
Completed 400 requests
Completed 500 requests
Completed 600 requests
Completed 700 requests
Completed 800 requests
Completed 900 requests
Completed 1000 requests
Finished 1000 requests


Server Software:        Apache-Coyote/1.1
Server Hostname:        localhost
Server Port:            8080

Document Path:          /manta/home/
Document Length:        1149 bytes

Concurrency Level:      10
Time taken for tests:   9.850 seconds
Complete requests:      1000
Failed requests:        0
Write errors:           0
Total transferred:      1431000 bytes
HTML transferred:       1149000 bytes
Requests per second:    101.52 [#/sec] (mean)
Time per request:       98.503 [ms] (mean)
Time per request:       9.850 [ms] (mean, across all concurrent requests)
Transfer rate:          141.87 [Kbytes/sec] received

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:        0    0   2.8      0      42
Processing:    17   98  28.2     96     197
Waiting:       17   94  28.9     92     197
Total:         17   98  28.3     96     198

Percentage of the requests served within a certain time (ms)
  50%     96
  66%    107
  75%    116
  80%    120
  90%    134
  95%    148
  98%    162
  99%    173
 100%    198 (longest request)
eric13:/usr/share/tomcat6/bin# ab -c 1 -n 100 http://localhost:8080/manta/questionnaire/
This is ApacheBench, Version 2.3 <$Revision: 655654 $>
Copyright 1996 Adam Twiss, Zeus Technology Ltd, http://www.zeustech.net/
Licensed to The Apache Software Foundation, http://www.apache.org/

Benchmarking localhost (be patient).....done


Server Software:        Apache-Coyote/1.1
Server Hostname:        localhost
Server Port:            8080

Document Path:          /manta/questionnaire/
Document Length:        197875 bytes

Concurrency Level:      1
Time taken for tests:   132.214 seconds
Complete requests:      100
Failed requests:        0
Write errors:           0
Total transferred:      19820500 bytes
HTML transferred:       19787500 bytes
Requests per second:    0.76 [#/sec] (mean)
Time per request:       1322.144 [ms] (mean)
Time per request:       1322.144 [ms] (mean, across all concurrent requests)
Transfer rate:          146.40 [Kbytes/sec] received

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:        0    0   0.1      0       0
Processing:   771 1322 693.2   1071    4999
Waiting:      688 1176 612.7    950    4590
Total:        771 1322 693.2   1071    4999

Percentage of the requests served within a certain time (ms)
  50%   1071
  66%   1217
  75%   1402
  80%   1648
  90%   2216
  95%   2824
  98%   3794
  99%   4999
 100%   4999 (longest request)

eric13:/usr/share/tomcat6/bin# ab -c 2 -n 100 http://localhost:8080/manta/questionnaire/
This is ApacheBench, Version 2.3 <$Revision: 655654 $>
Copyright 1996 Adam Twiss, Zeus Technology Ltd, http://www.zeustech.net/
Licensed to The Apache Software Foundation, http://www.apache.org/

Benchmarking localhost (be patient).....done


Server Software:        Apache-Coyote/1.1
Server Hostname:        localhost
Server Port:            8080

Document Path:          /manta/questionnaire/
Document Length:        197875 bytes

Concurrency Level:      2
Time taken for tests:   98.232 seconds
Complete requests:      100
Failed requests:        0
Write errors:           0
Total transferred:      19820500 bytes
HTML transferred:       19787500 bytes
Requests per second:    1.02 [#/sec] (mean)
Time per request:       1964.642 [ms] (mean)
Time per request:       982.321 [ms] (mean, across all concurrent requests)
Transfer rate:          197.04 [Kbytes/sec] received

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:        0    0   0.2      0       2
Processing:  1436 1964 609.2   1800    4543
Waiting:     1298 1771 554.1   1644    4375
Total:       1436 1964 609.2   1800    4543

Percentage of the requests served within a certain time (ms)
  50%   1800
  66%   1875
  75%   1938
  80%   2022
  90%   2323
  95%   4005
  98%   4519
  99%   4543
 100%   4543 (longest request)
eric13:/usr/share/tomcat6/bin# ab -c 3 -n 100 http://localhost:8080/manta/questionnaire/
This is ApacheBench, Version 2.3 <$Revision: 655654 $>
Copyright 1996 Adam Twiss, Zeus Technology Ltd, http://www.zeustech.net/
Licensed to The Apache Software Foundation, http://www.apache.org/

Benchmarking localhost (be patient).....done


Server Software:        Apache-Coyote/1.1
Server Hostname:        localhost
Server Port:            8080

Document Path:          /manta/questionnaire/
Document Length:        197875 bytes

Concurrency Level:      3
Time taken for tests:   101.661 seconds
Complete requests:      100
Failed requests:        0
Write errors:           0
Total transferred:      19820500 bytes
HTML transferred:       19787500 bytes
Requests per second:    0.98 [#/sec] (mean)
Time per request:       3049.821 [ms] (mean)
Time per request:       1016.607 [ms] (mean, across all concurrent requests)
Transfer rate:          190.40 [Kbytes/sec] received

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:        0    0   0.3      0       3
Processing:  1059 3034 971.0   2694    6176
Waiting:      976 2735 925.0   2413    5971
Total:       1059 3035 970.9   2694    6176

Percentage of the requests served within a certain time (ms)
  50%   2694
  66%   2886
  75%   3061
  80%   3256
  90%   4821
  95%   5154
  98%   5482
  99%   6176
 100%   6176 (longest request)
eric13:/usr/share/tomcat6/bin# ab -c 4 -n 100 http://localhost:8080/manta/questionnaire/
This is ApacheBench, Version 2.3 <$Revision: 655654 $>
Copyright 1996 Adam Twiss, Zeus Technology Ltd, http://www.zeustech.net/
Licensed to The Apache Software Foundation, http://www.apache.org/

Benchmarking localhost (be patient).....done


Server Software:        Apache-Coyote/1.1
Server Hostname:        localhost
Server Port:            8080

Document Path:          /manta/questionnaire/
Document Length:        197875 bytes

Concurrency Level:      4
Time taken for tests:   100.572 seconds
Complete requests:      100
Failed requests:        0
Write errors:           0
Total transferred:      19820500 bytes
HTML transferred:       19787500 bytes
Requests per second:    0.99 [#/sec] (mean)
Time per request:       4022.885 [ms] (mean)
Time per request:       1005.721 [ms] (mean, across all concurrent requests)
Transfer rate:          192.46 [Kbytes/sec] received

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:        0    0   1.3      0      11
Processing:  2848 3977 983.3   3560    6313
Waiting:     2603 3607 934.4   3220    6081
Total:       2848 3977 983.2   3560    6313

Percentage of the requests served within a certain time (ms)
  50%   3560
  66%   3898
  75%   4321
  80%   5111
  90%   5762
  95%   6117
  98%   6230
  99%   6313
 100%   6313 (longest request)

eric13:/usr/share/tomcat6/bin# ab -c 5 -n 100 -e questionnaire5.csv http://localhost:8080/manta/questionnaire/
This is ApacheBench, Version 2.3 <$Revision: 655654 $>
Copyright 1996 Adam Twiss, Zeus Technology Ltd, http://www.zeustech.net/
Licensed to The Apache Software Foundation, http://www.apache.org/

Benchmarking localhost (be patient).....done


Server Software:        Apache-Coyote/1.1
Server Hostname:        localhost
Server Port:            8080

Document Path:          /manta/questionnaire/
Document Length:        197875 bytes

Concurrency Level:      5
Time taken for tests:   105.142 seconds
Complete requests:      100
Failed requests:        0
Write errors:           0
Total transferred:      19820500 bytes
HTML transferred:       19787500 bytes
Requests per second:    0.95 [#/sec] (mean)
Time per request:       5257.116 [ms] (mean)
Time per request:       1051.423 [ms] (mean, across all concurrent requests)
Transfer rate:          184.09 [Kbytes/sec] received

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:        0    0   0.6      0       4
Processing:  1746 5215 1215.1   4814    7697
Waiting:     1658 4692 1139.8   4260    7322
Total:       1746 5216 1215.1   4814    7697

Percentage of the requests served within a certain time (ms)
  50%   4814
  66%   5382
  75%   6495
  80%   6627
  90%   7008
  95%   7318
  98%   7691
  99%   7697
 100%   7697 (longest request)
 	 
eric13:/usr/share/tomcat6/bin# ab -c 6 -n 100 -e questionnaire6.csv http://localhost:8080/manta/questionnaire/
This is ApacheBench, Version 2.3 <$Revision: 655654 $>
Copyright 1996 Adam Twiss, Zeus Technology Ltd, http://www.zeustech.net/
Licensed to The Apache Software Foundation, http://www.apache.org/

Benchmarking localhost (be patient).....done


Server Software:        Apache-Coyote/1.1
Server Hostname:        localhost
Server Port:            8080

Document Path:          /manta/questionnaire/
Document Length:        197875 bytes

Concurrency Level:      6
Time taken for tests:   102.503 seconds
Complete requests:      100
Failed requests:        0
Write errors:           0
Total transferred:      19820500 bytes
HTML transferred:       19787500 bytes
Requests per second:    0.98 [#/sec] (mean)
Time per request:       6150.208 [ms] (mean)
Time per request:       1025.035 [ms] (mean, across all concurrent requests)
Transfer rate:          188.83 [Kbytes/sec] received

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:        0    1   2.0      0      14
Processing:  3716 6103 1156.2   5882    8182
Waiting:     3550 5616 1144.4   5352    7554
Total:       3716 6104 1156.7   5882    8182

Percentage of the requests served within a certain time (ms)
  50%   5882
  66%   7040
  75%   7136
  80%   7252
  90%   7643
  95%   7830
  98%   8170
  99%   8182
 100%   8182 (longest request)

 	 
 	 
