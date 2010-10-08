/**
 * 
 */
package org.opentides.util;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * This class process and retrieves configuration settings 
 * from persistence.xml.
 * 
 * @author allantan
 */
public class XMLPersistenceUtil {
	private static Logger _log = Logger.getLogger(XMLPersistenceUtil.class);	

	@SuppressWarnings("unchecked")
	public static Properties getProperties (String persistenceFile, String persistenceUnit) {
		Properties properties = new Properties();
		SAXReader reader = new SAXReader();
        Document doc;
		try {
			InputStream is = XMLPersistenceUtil.class.getClassLoader().getResourceAsStream(persistenceFile);
			doc = reader.read(is);
		} catch (DocumentException e) {
			_log.error("Failed to read file contents for "+persistenceFile,e);
			return null;
		}
        Element root = doc.getRootElement();
        List<Element> elements = root.elements();
        for (Element el:elements) {
        	if (persistenceUnit.equals(el.attributeValue("name"))) {
        		// this is the persistence unit specified, let's get all properties inside
        		Element propsElement = el.element("properties");
        		List<Element> xmlProps = propsElement.elements("property");
        		for (Element prop:xmlProps) {
        			properties.put(prop.attributeValue("name"), prop.attributeValue("value"));
        		}
        	}
        }
        return properties;
	}
}
