package xn.core.util.xml.dom4j;

import java.io.StringWriter;
import java.io.Writer;

import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class Dom4jFormat {
	
	public static String format(Object obj) {
		StringWriter sw = new StringWriter();
		format(obj, sw);
		return sw.toString();
	}	
	
	public static void format(Object obj, Writer writer) {
		try {
			OutputFormat xmlFormat = OutputFormat.createPrettyPrint();
			xmlFormat.setEncoding("UTF-8");
			xmlFormat.setNewlines(true);
			xmlFormat.setIndent(true);
			xmlFormat.setIndentSize(4);
			XMLWriter xmlWriter = new XMLWriter(writer, xmlFormat);
			xmlWriter.write(obj);
			xmlWriter.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
