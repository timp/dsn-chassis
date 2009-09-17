import httplib

host = "localhost:8080"
contextpath = "/chassis-generic-service-exist/atom/edit"

print "bootstrapping study collection ..."

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

print "boostrapping submission collection ..."

content = u"""
<feed xmlns="http://www.w3.org/2005/Atom">
  <title>Submissions</title>
</feed>
"""

path = contextpath + "/submissions"

headers = { "Content-Type": "application/atom+xml", "Content-Length": len(content) }
conn = httplib.HTTPConnection(host)
conn.request("POST", path, content, headers)
response = conn.getresponse()

print response.status, response.reason
data = response.read()
print data
conn.close()

