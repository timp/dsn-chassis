package org.cggh.chassis.manta.util.transform;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.SSLHandshakeException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class TransformFunctions {

	
	private final static Logger logger = Logger.getLogger(TransformFunctions.class.getName());


	public List<String> convertAcceptHeaderAsStringIntoHeaderAcceptsAsStringList(String acceptHeaderAsString) {
		
		
		List<String> headerAcceptsAsStringList = null;
		
		//
		//this.getLogger().info(request.getHeader("Accept"));
		//expecting "text/plain, */*; q=0.01"
		
		if (acceptHeaderAsString != null) {
			
			String[] headerAcceptsAsStringArray = acceptHeaderAsString.split(",");
			
			for (int i = 0; i < headerAcceptsAsStringArray.length; i++) {
				
				if (headerAcceptsAsStringArray[i].contains(";")) {
				
					headerAcceptsAsStringArray[i] = headerAcceptsAsStringArray[i].substring(0, headerAcceptsAsStringArray[i].indexOf(";"));
				}
				
				headerAcceptsAsStringArray[i] = headerAcceptsAsStringArray[i].trim();
				
			}
			
			headerAcceptsAsStringList = Arrays.asList(headerAcceptsAsStringArray);
			
		} else {
			headerAcceptsAsStringList = new ArrayList<String>();
		}
		
			
		
		return headerAcceptsAsStringList;
	}


	public String convertDataAsFieldModelArrayListIntoCSVRowsWithMappedCustomFieldLabelsAsStringUsingFieldLabelRegExpMappingsAsPatternKeyedHashMap(
			ArrayList<FieldModel> dataAsFieldModelArrayListWithXpathFieldLabels, HashMap<String, String> fieldLabelRegExpMappingsAsPatternKeyedHashMap) {

		String dataAsCSVRowsString = null;

		//NOTE: Enclose value in quotes.
		//NOTE: Replace EOL characters with spaces. 
		//NOTE: Use Unix EOL character.
		
		StringBuilder dataAsCSVStringBuilder = new StringBuilder();
		
		for (int i = 0; i < dataAsFieldModelArrayListWithXpathFieldLabels.size(); i ++) {
			
			StringBuffer labelAsStringBuffer = new StringBuffer("\"");
			StringBuffer valueAsStringBuffer = new StringBuffer("\"");
			
			Boolean matchFound = null;
		
			
			for (String regExpKey : fieldLabelRegExpMappingsAsPatternKeyedHashMap.keySet()) {

				if (regExpKey != null) {
				
					Pattern pattern = Pattern.compile(regExpKey);
					Matcher matcher = pattern.matcher(dataAsFieldModelArrayListWithXpathFieldLabels.get(i).getXPathFieldLabel());
	
					if (matcher.matches()) {
						
						matchFound = true;
						
						labelAsStringBuffer.append(matcher.replaceAll(fieldLabelRegExpMappingsAsPatternKeyedHashMap.get(regExpKey))).append("\"");
						
						
					} else {
						
						//
						//this.getLogger().info(dataAsFieldModelArrayListWithXpathFieldLabels.get(i).getXPathFieldLabel() + " does not match " + regExpKey);
					}
					
				} else {
					logger.error("regExpKey is null");
				}
				
			}
			
			
			if (matchFound == null || matchFound != true) {
				matchFound = false;
			}
			
			//NOTE: Keep unmatched xPathFieldLabels
			if (matchFound == false) {
				
				labelAsStringBuffer.append(dataAsFieldModelArrayListWithXpathFieldLabels.get(i).getXPathFieldLabel()).append("\"");
			} else {
				logger.debug("XPath not found for");
			}
			
			//NOTE: replacements need to be assigned to a string
			
			String nodeValueAsString = dataAsFieldModelArrayListWithXpathFieldLabels.get(i).getNodeValue();
			
			nodeValueAsString = nodeValueAsString.replaceAll("\r\n", " ");
			nodeValueAsString = nodeValueAsString.replaceAll("\n", " ");
			nodeValueAsString = nodeValueAsString.replaceAll("\r", " ");
			nodeValueAsString = nodeValueAsString.replaceAll("\"", "\"\"");
			
			valueAsStringBuffer.append(nodeValueAsString + "\"");
			
			dataAsCSVStringBuilder.append(labelAsStringBuffer.toString()).append(",").append(valueAsStringBuffer.toString());
			
			dataAsCSVStringBuilder.append("\n");
		}
		
		dataAsCSVRowsString = dataAsCSVStringBuilder.toString();		

		return dataAsCSVRowsString;
	}

	public ArrayList<FieldModel> convertNodeListIntoFieldModelArrayListWithXPathFieldLabels(NodeList nodeList, String parentNodeBaseXPathAsString) {
		
		
		ArrayList<FieldModel> fieldModelArrayList = null;
		
		fieldModelArrayList = new ArrayList<FieldModel>();
		
		if (parentNodeBaseXPathAsString == null) {
			parentNodeBaseXPathAsString = "";
		}

		if (nodeList.getLength() == 1 && nodeList.item(0).getNodeName().equals("#text")) {
			
			// node is a leaf
			
			//Note: don't ignore empty nodes, remove later (separate concern)
			Node currentNode = nodeList.item(0);
			Node parentNode = currentNode.getParentNode();
			FieldModel fieldModel = new FieldModel();
			
			fieldModel.setParentNodeName(parentNode.getNodeName());
			fieldModel.setNodeName(currentNode.getNodeName());
			fieldModel.setNodeValue(currentNode.getNodeValue());
			fieldModel.setXPathFieldLabel(parentNodeBaseXPathAsString);
			
			fieldModelArrayList.add(fieldModel);
			
		} else {
			
			// node is a branch
			
			HashMap<String, Integer> nodeSiblings = new HashMap<String, Integer>();
			
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node currentNode = nodeList.item(i);
				String currentNodeName = currentNode.getNodeName();
				
				if (nodeSiblings.containsKey(currentNodeName)) {
					
					nodeSiblings.put(currentNodeName, nodeSiblings.get(currentNodeName).intValue() + 1);
					
				} else {

					nodeSiblings.put(currentNodeName, 1);
					
				}
				Integer numSiblings = nodeSiblings.get(currentNodeName).intValue();
				String nodeBaseXPathAsString = parentNodeBaseXPathAsString + "/" + currentNodeName + "[" + numSiblings + "]";
				
				if (currentNode.hasAttributes()) {
					int numAttrs = currentNode.getAttributes().getLength();
					for (int j = 0; j < numAttrs;j++) {
						Node attr = currentNode.getAttributes().item(j);
						String name = attr.getNodeName();
						if (!(name.startsWith("xmlns") || currentNodeName.startsWith("atom:"))) {
							FieldModel fieldModel = new FieldModel();

							fieldModel.setParentNodeName(currentNode
									.getNodeName());
							fieldModel.setNodeName(attr.getNodeName());
							fieldModel.setNodeValue(attr.getNodeValue());
							fieldModel
									.setXPathFieldLabel(nodeBaseXPathAsString
											+ "/@" + attr.getNodeName());

							fieldModelArrayList.add(fieldModel);
						}
					}
				}
				// More verbosely...
				//ArrayList<FieldModel> childNodesAsFieldModelArrayListWithXPathFieldLabels = convertNodeListIntoFieldModelArrayListWithXPathFieldLabels(nodeList.item(i).getChildNodes(), nodeBaseXPathAsString);
				
				fieldModelArrayList.addAll(convertNodeListIntoFieldModelArrayListWithXPathFieldLabels(currentNode.getChildNodes(), nodeBaseXPathAsString));
				
			}
			
			
		}

		return fieldModelArrayList;
	}


	public Boolean isInvalidURL(String urlAsString) {
		
		Boolean isInvalidURL = null; 
		
		try {
			URL urlAsUrl = new URL(urlAsString);
			@SuppressWarnings("unused")
			InputStream urlAsInputStream = urlAsUrl.openStream();
			isInvalidURL = false;
			
		} catch (UnknownHostException unknownHostException) {

			isInvalidURL = true;
			
		} catch (MalformedURLException e) {

			isInvalidURL = true;
			
		} catch (SSLHandshakeException e) {
			
			//
			//e.printStackTrace();
			
			//Note: This happens when the certificate is invalid.
			logger.warn("SSLHandshakeException", e);
			
			isInvalidURL = true;
			
		} catch (IOException e) {

			//FIXME: This could be an IO error, not a URL error.
			isInvalidURL = true;
		}
		
		return isInvalidURL;
	}

	public String convertURLAsStringIntoFileName(String urlAsString) {

		String fileName = null;
		
		String studyCode = null;
		
		if (urlAsString.lastIndexOf("/") > 1) {
			studyCode = urlAsString.substring(urlAsString.lastIndexOf("/") + 1, urlAsString.length());
		} else {
			
			logger.warn("urlAsString contains no characters after its last forward slash");
		}
		
		if (studyCode != null && !studyCode.equals("")) {
			fileName = studyCode + "_SSQ.csv";
		} else {
			fileName = "SSQ.csv";
		}

		return fileName;
	}



	public Document convertXmlAsStringIntoXmlAsDocument(String urlResponseAsString) throws ParserConfigurationException, SAXException, IOException {

		Document xmlAsDocument = null;
		
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        InputSource inputSource = new InputSource(new StringReader(urlResponseAsString));
        xmlAsDocument = documentBuilder.parse(inputSource);
        
        return xmlAsDocument;
        
		
	}

	
	public HashMap<String, String> convertFieldLabelMappingsXmlAsDocumentIntoFieldLabelMappingsAsPatternKeyedHashMap(
			Document fieldLabelMappingsXmlAsDocument) {
		
		HashMap<String, String> fieldLabelRegExpMappingsAsPatternKeyedHashMap = new HashMap<String, String>();
		
		NodeList fieldLabelMappingsAsNodeList = fieldLabelMappingsXmlAsDocument.getElementsByTagName("fieldLabelMapping");
		
		for (int i = 0; i < fieldLabelMappingsAsNodeList.getLength(); i++) {
			
			//
			//this.getLogger().info("i node name: " +  fieldLabelMappingsAsNodeList.item(i).getNodeName().toString());
			Node mappingNode = fieldLabelMappingsAsNodeList.item(i);
			if (mappingNode.getNodeName().toString().equals("fieldLabelMapping")) {
			
				String patternKey = null;
				String replacementTemplate = null;
				
				for (int j = 0; j < mappingNode.getChildNodes().getLength(); j++) {
					Node childNode = mappingNode.getChildNodes().item(j); 
					
					if (childNode.getNodeName().equals("label")) {
						
						//
						//this.getLogger().info("got label: " + mappingNode.getChildNodes().item(j).getTextContent());
						
						patternKey = childNode.getFirstChild().getNodeValue();
					}
					else if (childNode.getNodeName().equals("value")) {
						replacementTemplate = childNode.getFirstChild().getNodeValue();
					}
					else if (childNode.getNodeName().equals("#text")) {
						//ignore this type of node
					}
					else {
						if (childNode.getNodeName() != null) {
							logger.warn("Unexpected node name: " +  childNode.getNodeName().toString());
						} else {
							logger.warn("Unexpected: node name is null");
						}
					}
					
				}
				
				//fieldLabelRegExpMappingsAsPatternKeyedHashMap.put("/breakfast_menu\\[(\\d+)\\]/food\\[(\\d+)\\]/name\\[(\\d+)\\]", "Food$2Name");
				
				//
				//this.getLogger().info("got: " + patternKey + "," + replacementTemplate);
				
				fieldLabelRegExpMappingsAsPatternKeyedHashMap.put(patternKey, replacementTemplate);

				
			} else {
				logger.warn("Unexpected node name:" + mappingNode.getNodeName().toString());
			}
			
		}
		
		return fieldLabelRegExpMappingsAsPatternKeyedHashMap;
	}

	public HashMap<String, String> convertFieldLabelMappingsXmlAsDocumentIntoFieldLabelsToIgnoreAsPatternKeyedHashMap(
			Document fieldLabelMappingsXmlAsDocument) {
		
		HashMap<String, String> fieldLabelsToIgnoreAsPatternKeyedHashMap = new HashMap<String, String>();
		
		NodeList fieldLabelMappingsAsNodeList = fieldLabelMappingsXmlAsDocument.getElementsByTagName("fieldLabelMapping");
		
		for (int i = 0; i < fieldLabelMappingsAsNodeList.getLength(); i++) {
			
			//
			//this.getLogger().info("i node name: " +  fieldLabelMappingsAsNodeList.item(i).getNodeName().toString());
			
			if (fieldLabelMappingsAsNodeList.item(i).getNodeName().toString().equals("fieldLabelMapping")) {
				
				Boolean ignore = null;
				
				//Get attributes
				if (fieldLabelMappingsAsNodeList.item(i).hasAttributes()) {
					
					NamedNodeMap namedNodeMap = fieldLabelMappingsAsNodeList.item(i).getAttributes();

					//
					//if (namedNodeMap.getNamedItem("filter") != null) {
					//	this.getLogger().info("got filter");
					//	if (namedNodeMap.getNamedItem("filter").getNodeValue() != null) {
					//		this.getLogger().info("got node value");
					//		if (namedNodeMap.getNamedItem("filter").getNodeValue().equals("ignore")) {
					//			this.getLogger().info("got ignore");
					//		}
					//	}
					//}
					
					if(namedNodeMap.getNamedItem("filter") != null && namedNodeMap.getNamedItem("filter").getNodeValue() != null && namedNodeMap.getNamedItem("filter").getNodeValue().equals("ignore")) {
						ignore = true;
					}
					
				}
				
				if (ignore == null || ignore != true) {
					ignore = false;
				}
				
				if (ignore == true) { 
					
					String patternKey = null;
					String replacementTemplate = null;
					
					for (int j = 0; j < fieldLabelMappingsAsNodeList.item(i).getChildNodes().getLength(); j++) {
					
						
						if (fieldLabelMappingsAsNodeList.item(i).getChildNodes().item(j).getNodeName().equals("label")) {
							
							//
							//this.getLogger().info("got label: " + fieldLabelMappingsAsNodeList.item(i).getChildNodes().item(j).getTextContent());
							
							patternKey = fieldLabelMappingsAsNodeList.item(i).getChildNodes().item(j).getNodeValue();
						}
						else if (fieldLabelMappingsAsNodeList.item(i).getChildNodes().item(j).getNodeName().equals("value")) {
							replacementTemplate = fieldLabelMappingsAsNodeList.item(i).getChildNodes().item(j).getNodeValue();
						}
						else if (fieldLabelMappingsAsNodeList.item(i).getChildNodes().item(j).getNodeName().equals("#text")) {
							//ignore this type of node
						}
						else {
							if (fieldLabelMappingsAsNodeList.item(i).getChildNodes().item(j).getNodeName() != null) {
								logger.warn("Unexpected node name: " +  fieldLabelMappingsAsNodeList.item(i).getChildNodes().item(j).getNodeName().toString());
							} else {
								logger.warn("Unexpected: node name is null");
							}
						}
						
					}
					
					//fieldLabelRegExpMappingsAsPatternKeyedHashMap.put("/breakfast_menu\\[(\\d+)\\]/food\\[(\\d+)\\]/name\\[(\\d+)\\]", "Food$2Name");
					
					//
					//this.getLogger().info("ignoring: " + patternKey + "," + replacementTemplate);
					
					fieldLabelsToIgnoreAsPatternKeyedHashMap.put(patternKey, replacementTemplate);
				
				}
				
			} else {
				logger.warn("Unexpected node name:" + fieldLabelMappingsAsNodeList.item(i).getNodeName().toString());
			}
			
		}
		
		return fieldLabelsToIgnoreAsPatternKeyedHashMap;
	}	
	

	public String convertDataAsFieldModelArrayListIntoCSVColumnsWithMappedCustomFieldLabelsAsStringUsingFieldLabelRegExpMappingsAsPatternKeyedHashMap(
			ArrayList<FieldModel> dataAsFieldModelArrayListWithXpathFieldLabels,
			HashMap<String, String> fieldLabelRegExpMappingsAsPatternKeyedHashMap) {

		//TODO: Consolidate repeated code with the rows-format version of the same/similar method.
		
		String dataAsCSVColumnsString = null;

		//NOTE: Enclose value in quotes.
		//NOTE: Replace EOL characters with spaces. 
		//NOTE: Use Unix EOL character.
		
		StringBuilder dataAsCSVColumnsStringBuilder = new StringBuilder();
		StringBuilder labelsAsCSVRowStringBuilder = new StringBuilder();
		StringBuilder valuesAsCSVRowStringBuilder = new StringBuilder();
		
		for (int i = 0; i < dataAsFieldModelArrayListWithXpathFieldLabels.size(); i ++) {
			
			StringBuffer labelAsStringBuffer = new StringBuffer("\"");
			StringBuffer valueAsStringBuffer = new StringBuffer("\"");
			
			Boolean matchFound = false;
		
			FieldModel model = dataAsFieldModelArrayListWithXpathFieldLabels.get(i);
			String replacementLabel = null;
			for (String regExpKey : fieldLabelRegExpMappingsAsPatternKeyedHashMap.keySet()) {

				if (regExpKey != null) {
				
					Pattern pattern = Pattern.compile(regExpKey);
					Matcher matcher = pattern.matcher(model.getXPathFieldLabel());
	
					if (matcher.matches()) {
						
						matchFound = true;
						
						replacementLabel = matcher.replaceAll(fieldLabelRegExpMappingsAsPatternKeyedHashMap.get(regExpKey));
						
						//A match has been dealt with, no need to continue looking for this item.
						break;
						
					} else {
						
						//
						//this.getLogger().info(dataAsFieldModelArrayListWithXpathFieldLabels.get(i).getXPathFieldLabel() + " does not match " + regExpKey);
					}
					
				} else {
					logger.error("regExpKey is null");
				}
				
			}
			
			//NOTE: Keep unmatched xPathFieldLabels
			String label = "";
			if (matchFound) {
				label = replacementLabel;
			} else {
				label = model.getXPathFieldLabel();
				logger.debug("No regexp found for:" + label);
			}
			
			labelAsStringBuffer.append(label).append("\"");
			
			
			
			//valueAsStringBuffer.append(dataAsFieldModelArrayListWithXpathFieldLabels.get(i).getNodeValue().replaceAll("\n", " ").replaceAll("\r", " ").replaceAll("\"", "\"\"")).append("\"");
			
			//NOTE: replacements need to be assigned to a string
			
			String nodeValueAsString = model.getNodeValue();
			
			nodeValueAsString = nodeValueAsString.replaceAll("\r\n", " ");
			nodeValueAsString = nodeValueAsString.replaceAll("\n", " ");
			nodeValueAsString = nodeValueAsString.replaceAll("\r", " ");
			nodeValueAsString = nodeValueAsString.replaceAll("\"", "\"\"");
			
			valueAsStringBuffer.append(nodeValueAsString + "\"");
			
			
			//dataAsCSVColumnsStringBuilder.append(labelAsStringBuffer.toString()).append(",").append(valueAsStringBuffer);
			
			if (i > 0) {
				labelsAsCSVRowStringBuilder.append(",");
				valuesAsCSVRowStringBuilder.append(",");
			}
			
			labelsAsCSVRowStringBuilder.append(labelAsStringBuffer.toString());
			valuesAsCSVRowStringBuilder.append(valueAsStringBuffer.toString());
			
			
		}
		
		dataAsCSVColumnsStringBuilder.append(labelsAsCSVRowStringBuilder.toString());
		dataAsCSVColumnsStringBuilder.append("\n");
		
		dataAsCSVColumnsStringBuilder.append(valuesAsCSVRowStringBuilder.toString());
		dataAsCSVColumnsStringBuilder.append("\n");
		
		dataAsCSVColumnsString = dataAsCSVColumnsStringBuilder.toString();		
		
		return dataAsCSVColumnsString;
		
	}


	public ArrayList<FieldModel> removeFieldsToIgnoreFromFieldModelArrayListUsingFieldModelArrayListAndFieldLabelsToIgnoreAsPatternKeyedHashMap(
			ArrayList<FieldModel> originalDataAsFieldModelArrayListWithXpathFieldLabels,
			HashMap<String, String> fieldLabelsToIgnoreAsPatternKeyedHashMap) {
		
		//Copy the non-ignored items to a new array.
		ArrayList<FieldModel> dataAsFieldModelArrayListWithXpathFieldLabels = new ArrayList<FieldModel>();
		
		
		for (int i = 0; i < originalDataAsFieldModelArrayListWithXpathFieldLabels.size(); i++) {

			
			Boolean ignore = null;
			
			//NOTE: Automatically ignoring fields with blank values (as it always has been)
			FieldModel model = originalDataAsFieldModelArrayListWithXpathFieldLabels.get(i); 
			if (model.getNodeValue().trim().equals("")) {
				
				ignore = true;
				
			} else {
				
				for (String regExpKey : fieldLabelsToIgnoreAsPatternKeyedHashMap.keySet()) {
					
					if (regExpKey != null) {
						
						Pattern pattern = Pattern.compile(regExpKey);
						Matcher matcher = pattern.matcher(model.getXPathFieldLabel());
		
						if (matcher.matches()) {
							
							ignore = true;
							
							//A match has been dealt with, no need to continue looking for this item.
							break;
							
						} else {
							
							//
							//this.getLogger().info(dataAsFieldModelArrayListWithXpathFieldLabels.get(i).getXPathFieldLabel() + " does not match " + regExpKey);
						}
						
					} else {
						logger.error("regExpKey is null");
					}
					
						
					
				}
			
			}
			
			
			if (ignore == null || ignore != true) {
				ignore = false;
			}
			
		
			if (ignore == false) {
				dataAsFieldModelArrayListWithXpathFieldLabels.add(model);
			}
			
			
		}
		
		
		return dataAsFieldModelArrayListWithXpathFieldLabels;
	}

}
