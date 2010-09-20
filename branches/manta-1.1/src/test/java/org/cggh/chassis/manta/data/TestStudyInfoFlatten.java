package org.cggh.chassis.manta.data;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.StringTokenizer;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import junit.framework.TestCase;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.DocumentTraversal;
import org.w3c.dom.traversal.NodeFilter;
import org.w3c.dom.traversal.TreeWalker;

import com.sun.org.apache.xerces.internal.parsers.DOMParser;

public class TestStudyInfoFlatten extends TestCase {

	public TestStudyInfoFlatten(String name) {

		super(name);

	}

	protected void setUp() {
	}

	protected void tearDown() {
	}

	protected String processFile(String xml, String xslt)
			throws TransformerException, IOException {
		File xmlFile = new File(xml);
		File xsltFile = new File(xslt);
		File preprocess = new File("src/test/resources/setupFlattenTest.xsl");
		
		// JAXP reads data using the Source interface
		Source xmlSource = new StreamSource(xmlFile);
		Source xsltSource = new StreamSource(xsltFile);
		Source ppSource = new StreamSource(preprocess);
		
		// the factory pattern supports different XSLT processors
		TransformerFactory transFact = TransformerFactory.newInstance();
		//Preprocess the example file
		Transformer trans = transFact.newTransformer(ppSource);
		StringWriter intermediate = new StringWriter();
		Result result1 = new StreamResult(intermediate);

		trans.transform(xmlSource, result1);
		intermediate.close();
		//Now run the actual transform
		trans = transFact.newTransformer(xsltSource);
		StringReader inter = new StringReader(intermediate.toString());
		Source interSource = new StreamSource(inter);
		StringWriter writer = new StringWriter();
		Result result = new StreamResult(writer);
		trans.transform(interSource, result);
		writer.close();
		String csv = writer.toString();
		return (csv);
	}

	public void testContents() {
		String xslt = "war/xslt/flatten-ssq.xsl";
		String file = "src/test/resources/NewFile.xml";
		String cols = null;
		String datavals = null;

		DOMParser parser = new DOMParser();
		try {
			String csv = processFile(file, xslt);
			StringTokenizer st = new StringTokenizer(csv, "\n");
			cols = st.nextToken();
			datavals = st.nextToken();

			parser.parse(file);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Document doc = parser.getDocument();
		DOMImplementation domimpl = doc.getImplementation();
		if (domimpl.hasFeature("Traversal", "2.0")) {

			Node root = doc.getDocumentElement();
			int whattoshow = NodeFilter.SHOW_ALL;
			NodeFilter nodefilter = null;
			boolean expandreferences = false;

			DocumentTraversal traversal = (DocumentTraversal) doc;
			TreeWalker walker = traversal.createTreeWalker(root, whattoshow,
					nodefilter, expandreferences);
			Node thisNode = null;
			thisNode = walker.nextNode();
			
			String[] head = cols.split(",");
			String[] data = datavals.split(",");
			int dataIdx = 2;
			int headIdx = 2;
			boolean started = false;
			while (thisNode != null) {
				
				if (thisNode.getNodeType() == Node.TEXT_NODE) {
					String value = thisNode.getNodeValue().trim();
					if (!value.isEmpty()) {
						String parent = "";
						Node currNode = walker.getCurrentNode();
						parent = walker.parentNode().getNodeName();
						walker.setCurrentNode(currNode);
						try {
							if (parent.equals("start")) {
								started = true;
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						if (started) {

							boolean found = false;
							while (dataIdx < data.length && !found) {
								String colVal = data[dataIdx++];
								
								String headVal = head[headIdx++];
								
								if (colVal.equals(value) && value.length() == colVal.length()) {
									found = true;
									if (!headVal.endsWith(parent)) {
										fail("Unexpected parent:"+headVal+" "+parent);
									}
									System.out.println(parent + ":" + headVal
											+ ":" + colVal);
								} else if (colVal.length() > 0) {
									fail("Unexpected data:"+headVal+":"+colVal+"Expected:"+parent+":"+value);
								}
							}
						}
					}
				}
				

			//	System.out.println(thisNode.getNodeName() + ":"
				//		+ thisNode.getNodeValue());
				thisNode = walker.nextNode();
			}

		} else {
			System.out.println("The Traversal module isn't supported.");
		}

	}
}
