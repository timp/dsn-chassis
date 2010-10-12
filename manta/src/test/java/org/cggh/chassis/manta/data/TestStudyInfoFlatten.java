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
import org.w3c.dom.Node;
import org.w3c.dom.traversal.DocumentTraversal;
import org.w3c.dom.traversal.NodeFilter;
import org.w3c.dom.traversal.TreeWalker;
import org.xml.sax.InputSource;

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

		// JAXP reads data using the Source interface
		Source xmlSource = new StreamSource(xmlFile);
		Source xsltSource = new StreamSource(xsltFile);

		// the factory pattern supports different XSLT processors
		TransformerFactory transFact = TransformerFactory.newInstance();
		// Preprocess the example file
		Transformer trans = transFact.newTransformer(xsltSource);
		StringWriter writer = new StringWriter();
		Result result = new StreamResult(writer);

		trans.transform(xmlSource, result);
		writer.close();
		String csv = writer.toString();
		return (csv);
	}

	public void testContents() throws Exception {
		String xslt = "war/xslt/flatten-study-info.xsl";
		String file = "src/test/resources/study-info.xml";
		String preprocess = "src/test/resources/setupFlattenTest.xsl";
		String cols = null;
		String datavals = null;

		// Do the flattening
		String csv = processFile(file, xslt);
		// Split into 2 rows cols for the header values, datavals for the data
		StringTokenizer st = new StringTokenizer(csv, "\n");
		cols = st.nextToken();
		datavals = st.nextToken();

		// Preprocess the input file to reorder the elements
		String modifiedInput = processFile(file, preprocess);
		InputSource inputSource = new InputSource(new StringReader(
				modifiedInput));

		DOMParser parser = new DOMParser();
		parser.parse(inputSource);

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
			// Ignore version cols
			int dataIdx = 3;
			int headIdx = 3;
			boolean started = false;
			while (thisNode != null) {
				// Only look at text nodes
				if (thisNode.getNodeType() == Node.TEXT_NODE) {
					String value = thisNode.getNodeValue().trim();
					// Ignore empty nodes
					if (!value.isEmpty()) {
						String parent = "";
						Node currNode = walker.getCurrentNode();
						parent = walker.parentNode().getNodeName();
						walker.setCurrentNode(currNode);
						// Ignore version cols
						if (parent.equals("start")) {
							started = true;
						}

						if (started) {

							boolean found = false;
							while (dataIdx < data.length && !found) {
								String colVal = data[dataIdx++];

								String headVal = head[headIdx++];
								// Check the next data value in the XML matches
								// the next data val in the CSV
								if (colVal.equals(value)
										&& value.length() == colVal.length()) {
									found = true;
									// And that the element name matches the
									// column name
									if (!headVal.endsWith(parent)) {
										fail("Unexpected parent:" + headVal
												+ " " + parent);
									}
									System.out.println(parent + ":" + headVal
											+ ":" + colVal);
								} else if (colVal.length() > 0) {
									fail("Unexpected data:" + headVal + ":"
											+ colVal + "Expected:" + parent
											+ ":" + value);
								}
							}
						}
					}
				}

				// System.out.println(thisNode.getNodeName() + ":"
				// + thisNode.getNodeValue());
				thisNode = walker.nextNode();
			}

		} else {
			System.out.println("The Traversal module isn't supported.");
		}

	}
}
