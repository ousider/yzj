package xn.core.util.xml;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import xn.core.util.data.StringUtil;
import xn.core.util.xml.dom4j.Dom4jXML;


public abstract class XML {
	
	private static Class<? extends XML> clz = Dom4jXML.class;
	protected XMLElement rootElement = null;

	private static XML createXML(Object obj) throws Exception {
		Constructor<? extends XML> con = null;
		if (obj instanceof InputStream) {
			con = clz.getConstructor(InputStream.class);
		} else
			con = clz.getConstructor(obj.getClass());
		
		XML xml = con.newInstance(obj);
		return xml;
	}

	public static XML getInstance(String filePath) throws Exception {
		return createXML(filePath);
	}

	public static XML getInstance(File file) throws Exception {
		return createXML(file);
	}
	
	public static XML getInstance(InputStream in) throws Exception {
		return createXML(in);
	}
	
	public static XML getInstance(XMLElement root) throws Exception {
		return createXML(root);
	}
	
	public abstract XMLElement getRootElement();
	
	public List<XMLElement> lookupElements(String name) {
		List<XMLElement> eles = new ArrayList<XMLElement>();
		
		lookupElements(eles, rootElement, name);
		return eles;
	}
	
	private void lookupElements(List<XMLElement> result, XMLElement e, String name) {
		if (name.equals(e.getName()))
			result.add(e);
		
		List<XMLElement> eles = e.getChildren();
		for (XMLElement ele : eles) {
			lookupElements(result, ele, name);
		}
	}
	
	
	public String getProperty(String key, String defValue) {
		String[] keys = StringUtil.split(key, '.');
		
		XMLElement ele = rootElement;
		for (int i = 0; i < keys.length - 1; i++) {
			ele = ele.getChild(keys[i]);
			if (ele == null) {
				return defValue;
			}
		}
		String lastKey = keys[keys.length - 1];
		
		String val = ele.getAttributeValue(lastKey);
		if (!StringUtil.isBlank(val)) {
			return val;
		}
		ele = ele.getChild(lastKey);
		return ele == null ? defValue : ele.getTextTrim();
	}
	
	public abstract void saveToFile(String filePath) throws Exception ;
	
	public abstract void saveToFile(File file) throws Exception ;
	
	public abstract void saveRootElementToFile(XMLElement rootElement, String filePath)
			throws Exception;

	public abstract void saveRootElementToFile(XMLElement rootElement, File file)
			throws Exception;

}
