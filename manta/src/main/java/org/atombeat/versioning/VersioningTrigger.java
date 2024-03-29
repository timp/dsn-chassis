package org.atombeat.versioning;

import org.exist.collections.triggers.FilteringTrigger;
import org.exist.collections.triggers.TriggerException;
import org.exist.collections.Collection;
import org.exist.collections.IndexInfo;
import org.exist.collections.CollectionConfigurationException;
import org.exist.storage.DBBroker;
import org.exist.storage.BrokerPool;
import org.exist.storage.lock.Lock;
import org.exist.storage.txn.Txn;
import org.exist.xmldb.XmldbURI;
import org.exist.dom.DocumentImpl;
import org.exist.dom.QName;
import org.exist.security.*;
import org.exist.util.serializer.SAXSerializer;
import org.exist.util.serializer.SerializerPool;
import org.exist.util.serializer.Receiver;
import org.exist.versioning.Diff;
import org.exist.versioning.StandardDiff;
import org.exist.versioning.VersioningFilter;
import org.exist.versioning.VersioningHelper;
import org.exist.xquery.value.DateTimeValue;
import org.exist.xquery.XPathException;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.AttributesImpl;

import javax.xml.transform.OutputKeys;
import java.io.IOException;
import java.io.File;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.Properties;
import java.util.Date;
import java.util.Map;
import org.exist.dom.BinaryDocument;

public class VersioningTrigger extends FilteringTrigger {

    public final static Logger LOG = Logger.getLogger(VersioningTrigger.class);

    public final static XmldbURI VERSIONS_COLLECTION = XmldbURI.SYSTEM_COLLECTION_URI.append("versions");

    public final static String BASE_SUFFIX = ".base";
    public final static String TEMP_SUFFIX = ".tmp";
    public final static String DELETED_SUFFIX = ".deleted";
    public final static String BINARY_SUFFIX = ".binary";
    public final static String XML_SUFFIX = ".xml";

    public final static String PARAM_OVERWRITE = "overwrite";

    public final static QName ELEMENT_VERSION = new QName("version", StandardDiff.NAMESPACE, StandardDiff.PREFIX);
    public final static QName ELEMENT_REMOVED = new QName("removed", StandardDiff.NAMESPACE, StandardDiff.PREFIX);
    public final static QName PROPERTIES_ELEMENT = new QName("properties", StandardDiff.NAMESPACE, StandardDiff.PREFIX);
    public final static QName ELEMENT_REPLACED_BINARY = new QName("replaced-binary", StandardDiff.NAMESPACE, StandardDiff.PREFIX);
    public final static QName ATTRIBUTE_REF = new QName("ref");
    public final static QName ELEMENT_REPLACED_XML = new QName("replaced-xml", StandardDiff.NAMESPACE, StandardDiff.PREFIX);

    private final static Object latch = new Object();

    private DBBroker broker;
    private XmldbURI documentPath;
    private DocumentImpl lastRev = null;
    private boolean removeLast = false;
    private Collection vCollection;
    private DocumentImpl vDoc = null;

    private int elementStack = 0;

    private String documentKey = null;
    private String documentRev = null;
    private boolean checkForConflicts = false;

    public void configure(DBBroker broker, Collection parent, Map parameters) throws CollectionConfigurationException {
        super.configure(broker, parent, parameters);
        if (parameters != null) {
            String allowOverwrite = (String) parameters.get(PARAM_OVERWRITE);
            if (allowOverwrite != null)
                checkForConflicts = allowOverwrite.equals("false") || allowOverwrite.equals("no");
        }
        LOG.debug("checkForConflicts: " + checkForConflicts);
    }

    public void prepare(int event, DBBroker broker, Txn transaction, XmldbURI documentPath, DocumentImpl existingDocument)
    throws TriggerException {
    	
        this.broker = broker;
        this.documentPath = documentPath;
        User activeUser = broker.getUser();

        try {
            broker.setUser(org.exist.security.SecurityManager.SYSTEM_USER);

            if(event == UPDATE_DOCUMENT_EVENT || event == REMOVE_DOCUMENT_EVENT)
            {
                Collection collection = existingDocument.getCollection();
                if (collection.getURI().startsWith(VERSIONS_COLLECTION))
                    return;
                vCollection = getVersionsCollection(broker, transaction, documentPath.removeLastSegment());

                String existingURI = existingDocument.getFileURI().toString();
                XmldbURI baseURI = XmldbURI.create(existingURI + BASE_SUFFIX);
                DocumentImpl baseRev = vCollection.getDocument(broker, baseURI);

                String vFileName;

                if (baseRev == null) {
                    vFileName = baseURI.toString();
                    removeLast = false;

                    // copy existing document to base revision here!
                    broker.copyResource(transaction, existingDocument, vCollection, baseURI);

                } else if (event == REMOVE_DOCUMENT_EVENT) {
                    vFileName = existingURI + DELETED_SUFFIX;
                    removeLast = false;
                } else {
                    vFileName = existingURI + TEMP_SUFFIX;
                    removeLast = true;
                }
                
                // setReferenced(true) will tell the broker that the document
                // data is referenced from another document and should not be
                // deleted when the orignal document is removed.
                existingDocument.getMetadata().setReferenced(true);


                if(existingDocument instanceof BinaryDocument)
                {
                    XmldbURI binUri = XmldbURI.createInternal(vFileName);
                    broker.copyResource(transaction, existingDocument, vCollection, binUri);
                    vDoc = vCollection.getDocument(broker, binUri);
                }
                else
                {
                    vDoc = new DocumentImpl(broker.getBrokerPool(), vCollection, XmldbURI.createInternal(vFileName));
                    vDoc.copyOf(existingDocument);
                    vDoc.copyChildren(existingDocument);
                }
                
                if (event != REMOVE_DOCUMENT_EVENT)
                    lastRev = vDoc;
            }
        } catch (PermissionDeniedException e) {
            throw new TriggerException("Permission denied in VersioningTrigger: " + e.getMessage(), e);
        } catch (Exception e) {
            LOG.warn("Caught exception in VersioningTrigger: " + e.getMessage(), e);
        } finally {
            broker.setUser(activeUser);
        }
    }

    public void finish(int event, DBBroker broker, Txn transaction, XmldbURI documentPath, DocumentImpl document) {

    	if (documentPath.startsWith(VERSIONS_COLLECTION))
            return;
        
        User activeUser = broker.getUser();

        try {
            broker.setUser(org.exist.security.SecurityManager.SYSTEM_USER);

//            if (vDoc != null && !removeLast) {
//                if(!(vDoc instanceof BinaryDocument))
//                {
//                    try {
//                    	
//                    	// this is where the base document is stored, however trying to store vDoc causes database inconsistencies
//                    	
//                        vDoc.getUpdateLock().acquire(Lock.WRITE_LOCK);
//                        vCollection.addDocument(transaction, broker, vDoc);
//                        broker.storeXMLResource(transaction, vDoc);
//
//                    } catch (LockException e) {
//                        LOG.warn("Versioning trigger could not store base document: " + vDoc.getFileURI() +
//                                e.getMessage(), e);
//					} finally {
//                        vDoc.getUpdateLock().release(Lock.WRITE_LOCK);
//                    }
//                }
//            }

            if (event == STORE_DOCUMENT_EVENT) {
                try {
                    vCollection = getVersionsCollection(broker, transaction, documentPath.removeLastSegment());

                    String existingURI = document.getFileURI().toString();
                    XmldbURI deletedURI = XmldbURI.create(existingURI + DELETED_SUFFIX);
                    lastRev = vCollection.getDocument(broker, deletedURI);
                    if (lastRev == null) {
                        lastRev = vCollection.getDocument(broker, XmldbURI.create(existingURI + BASE_SUFFIX));
                        removeLast = false;
                    } else
                        removeLast = true;
                } catch (IOException e) {
                    LOG.warn("Caught exception in VersioningTrigger: " + e.getMessage(), e);
                } catch (PermissionDeniedException e) {
                    LOG.warn("Permission denied in VersioningTrigger: " + e.getMessage(), e);
                }
            }

            if (lastRev != null || event == REMOVE_DOCUMENT_EVENT)
            {
                try{

                    long revision = newRevision(broker.getBrokerPool());
                    
                    if (documentPath.isCollectionPathAbsolute())
                            documentPath = documentPath.lastSegment();
                    XmldbURI diffUri = XmldbURI.createInternal(documentPath.toString() + '.' + revision);

                    vCollection.setTriggersEnabled(false);

                    StringWriter writer = new StringWriter();
                    SAXSerializer sax = (SAXSerializer) SerializerPool.getInstance().borrowObject(
                            SAXSerializer.class);
                    Properties outputProperties = new Properties();
                    outputProperties.setProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
                    outputProperties.setProperty(OutputKeys.INDENT, "no");
                    sax.setOutput(writer, outputProperties);

                    sax.startDocument();
                    sax.startElement(ELEMENT_VERSION, null);
                    writeProperties(sax, getVersionProperties(revision, documentPath));

                    if(event == REMOVE_DOCUMENT_EVENT)
                    {
                        sax.startElement(ELEMENT_REMOVED, null);
                        sax.endElement(ELEMENT_REMOVED);
                    }
                    else
                    {

                        //Diff
                        if(document instanceof BinaryDocument)
                        {
                            //create a copy of the last Binary revision
                            XmldbURI binUri = XmldbURI.create(diffUri.toString() + BINARY_SUFFIX);
                            broker.copyResource(transaction, document, vCollection, binUri);

                            //Create metadata about the last Binary Version
                            sax.startElement(ELEMENT_REPLACED_BINARY, null);
                            sax.attribute(ATTRIBUTE_REF, binUri.toString());
                            sax.endElement(ELEMENT_REPLACED_BINARY);
                        }
                        else if(lastRev instanceof BinaryDocument)
                        {
                            //create a copy of the last XML revision
                            XmldbURI xmlUri = XmldbURI.create(diffUri.toString() + XML_SUFFIX);
                            broker.copyResource(transaction, document, vCollection, xmlUri);

                            //Create metadata about the last Binary Version
                            sax.startElement(ELEMENT_REPLACED_XML, null);
                            sax.attribute(ATTRIBUTE_REF, xmlUri.toString());
                            sax.endElement(ELEMENT_REPLACED_BINARY); // TODO this looks like a bug?
                        }
                        else
                        {
                            //Diff the XML versions
                            Diff diff = new StandardDiff(broker);
                            diff.diff(lastRev, document);
                            diff.diff2XML(sax);
                        }

                        sax.endElement(ELEMENT_VERSION);

                        sax.endDocument();
                        String editscript = writer.toString();

    //                    System.out.println("documentPath: " + documentPath);
    //                    System.out.println(editscript);

                        if (removeLast)
                        {
                           if(lastRev instanceof BinaryDocument)
                           {
                                vCollection.removeBinaryResource(transaction, broker, lastRev.getFileURI());
                           }
                           else
                           {
                                vCollection.removeXMLResource(transaction, broker, lastRev.getFileURI());
                           }
                        }

                        
                        IndexInfo info = vCollection.validateXMLResource(transaction, broker, diffUri, editscript);
                        vCollection.store(transaction, broker, info, editscript, false);
                        
                    }
                }
                catch (Exception e) {
                    LOG.warn("Caught exception in VersioningTrigger: " + e.getMessage(), e);
                } finally {
                    vCollection.setTriggersEnabled(true);
                }
            }

        } finally {
            broker.setUser(activeUser);
        }
    }

    private Properties getVersionProperties(long revision, XmldbURI documentPath) throws XPathException
    {
        Properties properties = new Properties();

        properties.setProperty("document", documentPath.toString());
        properties.setProperty("revision", Long.toString(revision));
        properties.setProperty("date", new DateTimeValue(new Date()).getStringValue());
        properties.setProperty("user", broker.getUser().getName());
        if (documentKey != null) {
            properties.setProperty("key", documentKey);
        }
        
        return properties;
    }

    public static void writeProperties(Receiver receiver, Properties properties) throws SAXException {
        receiver.startElement(PROPERTIES_ELEMENT, null);
        for (Iterator i = properties.keySet().iterator(); i.hasNext();) {
            String key = (String) i.next();
            QName qn = new QName(key, StandardDiff.NAMESPACE, StandardDiff.PREFIX);
            receiver.startElement(qn, null);
            receiver.characters(properties.get(key).toString());
            receiver.endElement(qn);
        }
        receiver.endElement(PROPERTIES_ELEMENT);
    }

    private Collection getVersionsCollection(DBBroker broker, Txn transaction, XmldbURI collectionPath) throws IOException, PermissionDeniedException {
        XmldbURI path = VERSIONS_COLLECTION.append(collectionPath);
        Collection collection = broker.openCollection(path, Lock.WRITE_LOCK);
        if (collection == null) {
            if(LOG.isDebugEnabled())
                LOG.debug("Creating versioning collection: " + path);
            collection = broker.getOrCreateCollection(transaction, path);
            broker.saveCollection(transaction, collection);
        } else {
            transaction.registerLock(collection.getLock(), Lock.WRITE_LOCK);
        }
        return collection;
    }

    private long newRevision(BrokerPool pool) {
        String dataDir = (String) pool.getConfiguration().getProperty(BrokerPool.PROPERTY_DATA_DIR);
        synchronized (latch) {
            File f = new File(dataDir, "versions.dbx");
            long rev = 0;
            if (f.canRead()) {
                DataInputStream is = null;
                try {
                    is = new DataInputStream(new FileInputStream(f));
                    rev = is.readLong();
                } catch (FileNotFoundException e) {
                    LOG.warn("Failed to read versions.dbx: " + e.getMessage(), e);
                } catch (IOException e) {
                    LOG.warn("Failed to read versions.dbx: " + e.getMessage(), e);
                }
                finally
                {
                    if(is != null)
                    {
                        try
                        {
                            is.close();
                        }
                        catch(IOException ioe)
                        {
                            LOG.warn("Failed to close InputStream for versions.dbx: " + ioe.getMessage(), ioe);
                        }
                    }
                }
            }
            
            ++rev;

            DataOutputStream os = null;
            try {
                os = new DataOutputStream(new FileOutputStream(f));
                os.writeLong(rev);
            } catch (FileNotFoundException e) {
                LOG.warn("Failed to write versions.dbx: " + e.getMessage(), e);
            } catch (IOException e) {
                LOG.warn("Failed to write versions.dbx: " + e.getMessage(), e);
            }
            finally
            {
                if(os != null)
                {
                    try
                    {
                        os.close();
                    }
                    catch(IOException ioe)
                    {
                         LOG.warn("Failed to close OutputStream for versions.dbx: " + ioe.getMessage(), ioe);
                    }
                }
            }
            return rev;
        }
    }

    public void startElement(String namespaceURI, String localName, String qname, Attributes attributes) throws SAXException {
        if (checkForConflicts && isValidating() && elementStack == 0) {
            for (int i = 0; i < attributes.getLength(); i++) {
                if (StandardDiff.NAMESPACE.equals(attributes.getURI(i))) {
                    String attrName = attributes.getLocalName(i);
                    if (VersioningFilter.ATTR_KEY.getLocalName().equals(attrName))
                        documentKey = attributes.getValue(i);
                    else if (VersioningFilter.ATTR_REVISION.getLocalName().equals(attrName))
                        documentRev = attributes.getValue(i);
                }
            }
            if (documentKey != null && documentRev != null) {
                LOG.debug("v:key = " + documentKey + "; v:revision = " + documentRev);
                try {
                    long rev = Long.parseLong(documentRev);
                    if (VersioningHelper.newerRevisionExists(broker, documentPath, rev, documentKey)) {
                        long baseRev = VersioningHelper.getBaseRevision(broker, documentPath, rev, documentKey);
                        LOG.debug("base revision: " + baseRev);
                        throw new TriggerException("Possible version conflict detected for document: " + documentPath);
                    }
                } catch (XPathException e) {
                    LOG.warn("Internal error in VersioningTrigger: " + e.getMessage(), e);
                } catch (IOException e) {
                    LOG.warn("Internal error in VersioningTrigger: " + e.getMessage(), e);
                } catch (NumberFormatException e) {
                    LOG.warn("Illegal revision number in VersioningTrigger: " + documentRev);
                }
            }
        }
        if (elementStack == 0) {
            // Remove the versioning attributes which were inserted during serialization. We don't want
            // to store them in the db
            AttributesImpl nattrs = new AttributesImpl();
            for (int i = 0; i < attributes.getLength(); i++) {
                if (!StandardDiff.NAMESPACE.equals(attributes.getURI(i)))
                    nattrs.addAttribute(attributes.getURI(i), attributes.getLocalName(i),
                            attributes.getQName(i), attributes.getType(i), attributes.getValue(i));
            }
            attributes = nattrs;
        }
        elementStack++;
        super.startElement(namespaceURI, localName, qname, attributes);
    }

    public void endElement(String namespaceURI, String localName, String qname) throws SAXException {
        elementStack--;
        super.endElement(namespaceURI, localName, qname);
    }

    public void startPrefixMapping(String prefix, String namespaceURI) throws SAXException {
        if (StandardDiff.NAMESPACE.equals(namespaceURI))
            return;
        super.startPrefixMapping(prefix, namespaceURI);
    }
}
