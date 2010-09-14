import httplib
import base64

# configuration parameters

host = "localhost:8080"
contextpath = "/chassis-generic-service-exist/atom/edit"
username = "foo"
password = "bar"

# define function to create a collection
# N.B. this only works with http basic authentication

def createcollection(name, path):
    print "creating collection, name: "+name+"; path: "+path+"..."
    path = contextpath + "/" + path
    content = u"<feed xmlns=\"http://www.w3.org/2005/Atom\"><title>"+name+"</title></feed>"
    authorizationheader = base64.encodestring('%s:%s' % (username, password))[:-1]
    headers = { "Content-Type": "application/atom+xml", "Content-Length": len(content), "Authorization": "Basic %s" %     authorizationheader }
    conn = httplib.HTTPConnection(host)
    conn.request("POST", path, content, headers)
    response = conn.getresponse()
    if response.status == 204:
        print "[OK]"
    else:
        print "[ERROR]"
        print response.status, response.reason
        print response.msg
        data = response.read()
        print data
    conn.close()

# create collections

createcollection("Studies", "studies")
createcollection("Submissions", "submissions")

