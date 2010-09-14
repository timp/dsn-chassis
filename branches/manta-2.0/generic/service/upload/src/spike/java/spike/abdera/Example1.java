/**
 * 
 */
package spike.abdera;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.abdera.Abdera;
import org.apache.abdera.model.Document;
import org.apache.abdera.model.Entry;
import org.apache.abdera.model.Feed;
import org.apache.abdera.protocol.Response.ResponseType;
import org.apache.abdera.protocol.client.AbderaClient;
import org.apache.abdera.protocol.client.ClientResponse;
import org.apache.commons.httpclient.UsernamePasswordCredentials;

/**
 * @author aliman
 *
 */
public class Example1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try {

			Abdera abdera = new Abdera();
			AbderaClient client = new AbderaClient(abdera);
			
			client.addCredentials("http://localhost:8080", "chassis-development-realm", "basic", new UsernamePasswordCredentials("foo", "bar"));
			
			ClientResponse res = client.get("http://localhost:8080/chassis-generic-service-exist/atom/edit/studies");
			
			if (res.getType() == ResponseType.SUCCESS) {
				Document<Feed> doc = res.getDocument(abdera.getParser());
				System.out.println(doc.toString());
				Feed feed = doc.getRoot();
				System.out.println(feed.getTitle());
				for (Entry entry : feed.getEntries()) {
				  System.out.println("\t" + entry.getTitle());
				}

			}
			else {
				System.out.println(res.getStatus() + " " +res.getStatusText());
				BufferedReader reader = new BufferedReader(new InputStreamReader(res.getInputStream()));
				String line = reader.readLine();
				while (line != null) {
					System.out.println(line);
					line = reader.readLine();
				}
			}
			
		}
		catch (Throwable t) {
			t.printStackTrace();
		}
		
	}

}
