package xn.core.util.xml;

import java.util.List;
import java.util.Map;

import xn.core.util.data.StringUtil;

public abstract class XMLElement {
	protected XMLElement parent = null;
	
	public XMLElement getParent() {
		return parent;
	}
	
	public abstract String getName();
	
	public abstract List<XMLElement> getChildren();

	public abstract List<XMLElement> getChildren(String name);
	
	public abstract XMLElement getChild(String name);

	public abstract String getChildTextTrim(String name);

	public abstract String getTextTrim();

	public abstract String getAttributeValue(String string);
	
	public String getAttributeValue(String key, String defValue) {
		String value = getAttributeValue(key);
		if (StringUtil.isBlank(value))
			return defValue;
		return value;
	}

	public abstract Map<String, String> getAttributes();
	
	public abstract void setAttribute(String attrName, String attrVal);
	
	public abstract boolean removeChild(XMLElement child);
	
}
