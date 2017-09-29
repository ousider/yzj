package xn.core.util.xml.dom4j;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.io.SAXReader;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import xn.core.util.xml.XML;
import xn.core.util.xml.XMLElement;

public class Dom4jXML extends XML {
	private Document doc = null;

	public Dom4jXML(String filePath) throws Exception {
		this(new File(filePath));
	}

	public Dom4jXML(File file) throws Exception {
		this(file, null);
	}

	public Dom4jXML(XMLElement root) throws Exception {
		this.rootElement = root;
		Dom4jXMLElement ele = ((Dom4jXMLElement) rootElement);
		this.doc = ele.getElement().getDocument();
		if (this.doc == null)
			doc = DocumentFactory.getInstance().createDocument(ele.getElement());
	}

	public Dom4jXML(InputStream fileStream) throws Exception {
		this(null, fileStream);
	}

    public Dom4jXML(File file, InputStream fileStream) throws Exception {
        SAXReader reader = new SAXReader();
        reader.setEntityResolver(new IgnoreDTDEntityResolver());
        if (file != null) {
            doc = reader.read(file);
        } else if (fileStream != null) {
            doc = reader.read(fileStream);
        } else {
            throw new Exception("");
        }
        rootElement = new Dom4jXMLElement(doc.getRootElement());
    }

	public XMLElement getRootElement() {
		return rootElement;
	}

	public Document getDocument() {
		return doc;
	}

	public void saveToFile(String filePath) throws Exception {
		saveToFile(new File(filePath));
	}

	public void saveToFile(File file) throws Exception {
		if (!file.getParentFile().exists()) {
			if (!file.getParentFile().mkdirs())
				return;
		}
		Dom4jFormat.format(doc, new FileWriter(file));
	}

	public void saveRootElementToFile(XMLElement rootElement, String filePath) throws Exception {
		new Dom4jXML(rootElement).saveToFile(filePath);
	}

	public void saveRootElementToFile(XMLElement rootElement, File file) throws Exception {
		new Dom4jXML(rootElement).saveToFile(file);
	}

}

class IgnoreDTDEntityResolver implements EntityResolver {
	public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
		System.out.println(systemId);
		if (systemId.startsWith("http") || systemId.endsWith(".dtd")) {
			InputSource source = new InputSource(new ByteArrayInputStream(
					"<?xml version='1.0' encoding='UTF-8'?>".getBytes()));
			source.setEncoding("UTF-8");
			return source;
		}
		if (systemId.startsWith("classpath:")) {
			String entityPath = "service/" + systemId.substring(10);
			InputStream is = IgnoreDTDEntityResolver.class.getClassLoader().getResourceAsStream(entityPath);
			InputSource source = new InputSource(is);
			source.setEncoding("UTF-8");
			return source;
		}
		return null;
	}

}
