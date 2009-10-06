/**
 * 
 */
package spike.abdera;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.abdera.Abdera;
import org.apache.abdera.model.Document;
import org.apache.abdera.model.Entry;
import org.apache.abdera.protocol.Response.ResponseType;
import org.apache.abdera.protocol.client.AbderaClient;
import org.apache.abdera.protocol.client.ClientResponse;
import org.apache.abdera.protocol.client.RequestOptions;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;

/**
 * @author aliman
 *
 */
public class Example2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String dataFilesCollectionUrl = "http://localhost:8080/chassis-generic-service-exist/atom/edit/datafiles";
		
		try {
			
			Abdera abdera = new Abdera();
			AbderaClient client = new AbderaClient();

			client.addCredentials("http://localhost:8080", "chassis-development-realm", "basic", new UsernamePasswordCredentials("foo", "bar"));

			InputStream in = new FileInputStream(new File("test/resources/datafile1.xls"));
			InputStreamRequestEntity entity = new InputStreamRequestEntity(in, "application/vnd.ms-excel");
			
			ClientResponse res1 = client.post(dataFilesCollectionUrl, entity);
			
			if (res1.getType() == ResponseType.SUCCESS) {
				
				Document<Entry> doc = res1.getDocument(abdera.getParser());
				System.out.println(doc.toString());
				Entry entry = doc.getRoot();
				System.out.println("title: "+entry.getTitle());
				System.out.println("edit link: "+entry.getEditLink());
				System.out.println("edit media link: "+entry.getEditMediaLink());
				System.out.println("chassis.submission link: "+entry.getLink("chassis.submission"));
				
				// modify entry and put back
				String href = "http://localhost:8080/chassis-generic-service-exist/atom/edit/submissions?id=foo"; // link to submission
				String rel = "chassis.submission";
				entry.addLink(href, rel);
				
				String entryUrl = dataFilesCollectionUrl + entry.getEditLink().getHref().toASCIIString();
				System.out.println("putting back to: "+entryUrl);
				RequestOptions options = new RequestOptions();
				options.setContentType("application/atom+xml; type=entry; charset=UTF-8"); // needed, otherwise content type application/xml, which exist rejects
				ClientResponse res2 = client.put(entryUrl, entry, options);

				if (res2.getType() == ResponseType.SUCCESS) {

					doc = res2.getDocument(abdera.getParser());
					System.out.println(doc.toString());
					entry = doc.getRoot();
					System.out.println("title: "+entry.getTitle());
					System.out.println("edit link: "+entry.getEditLink());
					System.out.println("edit media link: "+entry.getEditMediaLink());
					System.out.println("chassis.submission link: "+entry.getLink("chassis.submission"));
					
				}
				else {
					logFailure(res2);
				}

			}
			else {
				logFailure(res1);
			}

			
		}
		catch (Throwable t) {
			t.printStackTrace();
		}
	}
	
	
	
	private static void logFailure(ClientResponse res) throws IOException {
		System.out.println(res.getStatus() + " " +res.getStatusText());
		BufferedReader reader = new BufferedReader(new InputStreamReader(res.getInputStream()));
		String line = reader.readLine();
		while (line != null) {
			System.out.println(line);
			line = reader.readLine();
		}
	}

}
