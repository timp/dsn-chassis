import httplib

host = "localhost:8080"
path = "/chassis-generic-service-exist/atom/edit/example"
content = u"""
<feed xmlns="http://www.w3.org/2005/Atom">
  <title>Example Collection</title>
</feed>
"""

headers = { "Content-Type": "application/atom+xml", "Content-Length": len(content) }

conn = httplib.HTTPConnection(host)
conn.request("POST", path, content, headers)
response = conn.getresponse()

print response.status, response.reason
data = response.read()
print data
conn.close()


