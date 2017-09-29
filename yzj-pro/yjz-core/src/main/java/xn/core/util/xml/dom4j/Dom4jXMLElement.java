package xn.core.util.xml.dom4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Element;

import xn.core.util.xml.XMLElement;


public class Dom4jXMLElement extends XMLElement {
	private Element e = null;
	
	public Dom4jXMLElement(Element e) {
		this.e = e;
		if (e.getParent() != null)
			parent = new Dom4jXMLElement(e.getParent());
	}
	
	protected Element getElement() {
		return e;
	}

	@Override
	public String getName() {
		return e.getName();
	}
	
	@Override
	public String getChildTextTrim(String name) {
		return e.elementTextTrim(name);
	}

	@Override
	public XMLElement getChild(String name) {
		Element child = e.element(name);
		return child == null ? null : new Dom4jXMLElement(child);
	}
	
	@Override
	public List<XMLElement> getChildren() {
		@SuppressWarnings("unchecked")
		List<Element> eles = e.elements();
		
		List<XMLElement> xmlEles = new ArrayList<XMLElement>();
		for (Element ele : eles) {
			xmlEles.add(new Dom4jXMLElement(ele));
		}
		return xmlEles;
	}
	
	@Override
	public List<XMLElement> getChildren(String name) {
		@SuppressWarnings("unchecked")
		List<Element> eles = e.elements(name);
		
		List<XMLElement> xmlEles = new ArrayList<XMLElement>();
		for (Element ele : eles) {
			xmlEles.add(new Dom4jXMLElement(ele));
		}
		return xmlEles;
	}

	public Map<String, String> getAttributes() {
		Map<String, String> attrs = new HashMap<String, String>();
		@SuppressWarnings("unchecked")
		List<Attribute> attrList = e.attributes();
		for (Attribute attr : attrList) {
			attrs.put(attr.getName(), attr.getValue());
		}
		return attrs;
	}
	
	@Override
	public String getAttributeValue(String name) {
		return e.attributeValue(name);
	}
	
	@Override
	public void setAttribute(String attrName, String attrVal) {
		Attribute attr = e.attribute(attrName);
		if (attr == null) {
			e.addAttribute(attrName, attrVal);
			return;
		}
		attr.setValue(attrVal);
		List<Attribute> attrs = new ArrayList<Attribute>();
		attrs.add(attr);
		e.setAttributes(attrs);
	}
	
	@Override
	public String getTextTrim() {
		return e.getTextTrim();
	}
	
	@Override
	public boolean removeChild(XMLElement child) {
		return e.remove(((Dom4jXMLElement)child).e);
	}
	
	@Override
	public String toString() {
		return Dom4jFormat.format(e);
	}
}
