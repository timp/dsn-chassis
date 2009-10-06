import httplib
import base64

host = "localhost:8088"
contextpath = "/chassis-generic-service-exist/atom/edit"
# contextpath = "/eXist-1.2.6-rev9165/atom/edit"

# TODO fix this
#username = "foo"
#password = "bar"
#authzheader = base64.encodestring('%s:%s' % (username, password))[:-1]

print "bootstrapping study collection ..."

content = u"""<feed xmlns="http://www.w3.org/2005/Atom"><title>Studies</title></feed>"""

path = contextpath + "/studies"

# headers = { "Content-Type": "application/atom+xml", "Content-Length": len(content), "Authorization": "Basic %s" % authzheader }

headers = { "Content-Type": "application/atom+xml", "Content-Length": len(content) }
conn = httplib.HTTPConnection(host)
conn.request("POST", path, content, headers)
response = conn.getresponse()

print response.status, response.reason
data = response.read()
print data
conn.close()

print "boostrapping submission collection ..."

content = u"""<feed xmlns="http://www.w3.org/2005/Atom"><title>Submissions</title></feed>"""

path = contextpath + "/submissions"

#headers = { "Content-Type": "application/atom+xml", "Content-Length": len(content), "Authorization": "Basic %s" % authzheader }

headers = { "Content-Type": "application/atom+xml", "Content-Length": len(content) }
conn = httplib.HTTPConnection(host)
conn.request("POST", path, content, headers)
response = conn.getresponse()

print response.status, response.reason
data = response.read()
print data
conn.close()

