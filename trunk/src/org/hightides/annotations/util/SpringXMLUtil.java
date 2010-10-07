/*
   Licensed to the Apache Software Foundation (ASF) under one
   or more contributor license agreements.  See the NOTICE file
   distributed with this work for additional information
   regarding copyright ownership.  The ASF licenses this file
   to you under the Apache License, Version 2.0 (the
   "License"); you may not use this file except in compliance
   with the License.  You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing,
   software distributed under the License is distributed on an
   "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
   KIND, either express or implied.  See the License for the
   specific language governing permissions and limitations
   under the License.    
 */

package org.hightides.annotations.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.opentides.util.FileUtil;
import org.opentides.util.StringUtil;

/**
 * This class is responsible for injecting spring beans and properties
 * into the application spring configuration.
 * Primarily used by SpringConfigProcessor.
 * 
 * @author allantan
 *
 */
public class SpringXMLUtil {
	
	private static Logger _log = Logger.getLogger(SpringXMLUtil.class);
	
	public static final String UrlMappingId = "applicationUrlMapping";
	public static final String UrlMappingProperty = "mappings";
	
	/**
	 * Overloaded method to add url mapping to a spring configuration.
	 * Backup option is set to true.
	 * Assumes bean is declared as SimpleUrlHandlerMapping with id of applicationUrlMapping.
	 * @param xmlFilename
	 * @param property
	 * @return
	 */
	public static Element addURLMapProperty(String xmlFilename, Element property) {
		return addURLMapProperty(xmlFilename, property, true);
	}

	/**
	 * Method to add url mapping to a spring configuration.
	 * Assumes bean is declared as SimpleUrlHandlerMapping with id of applicationUrlMapping.
	 * @param xmlFilename - location of configuration file
	 * @param property - Dom4j Element to insert
	 * @param backup
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Element addURLMapProperty(String xmlFilename, Element property, boolean backup) {
		String filename =FileUtil.getFilename(xmlFilename);
		String beanId = UrlMappingId;
		Document doc = readXMLDocument(xmlFilename);
		if (doc==null)
			return null;
        Element root = doc.getRootElement();
        boolean inserted = false;
        Element mapElement = null;
        List<Element> elements = root.elements();
        for (Element element:elements) {
        	if (beanId.equals(element.attributeValue("id"))) {
        		mapElement = element;
        		break;
        	}
        }	
        if (mapElement==null) {
        	_log.warn("Cannot find element with id ["+beanId+"] in ["+filename+"]");
        	return null;
        } else {
            List<Element> properties = mapElement.elements("property");
            List<Element> backupBeans = new ArrayList<Element>();
            for (Element prop:properties) {
            	if (UrlMappingProperty.equals(prop.attributeValue("name"))) {
            		Element props = prop.element("props");
            		boolean found = false;
            		// inside props, remove existing mapping
            		for (Element el:(List<Element>) props.elements()) {
            			if (property.attributeValue("key").equals(el.attributeValue("key"))) {
            				props.remove(el);
            				found = true;
            			} else if (found) {
            		        backupBeans.add(el);
            		        props.remove(el);
            			}
            		}
            		
            		// add new props
            		cloneChildElement(prop.element("props"), property);
                	if (backupBeans!=null) {
                		for (Element backUpBean:backupBeans) {
                			cloneChildElement(prop.element("props"), backUpBean);
                		}
                	}
            		if (saveXMLDocument(xmlFilename, doc, backup)) {
                		if (found)
                			_log.info("Updated URL mapping for ["+property.attributeValue("key")+"].");
                		else
                			_log.info("Added URL mapping for ["+property.attributeValue("key")+"].");
            			inserted = true;
                	}
    			}
            }
        }
        if (inserted) {
        	return property;
        } else 
        	return null;
	}
	
	/**
	 * Overloaded method to add bean to a spring configuration.
	 * Backup option is defaulted to true.
	 * @param xmlFilename
	 * @param bean
	 * @return
	 */
	public static Element addBean(String xmlFilename, Element bean, String syncMode) {
		return addBean(xmlFilename, bean, true, syncMode);
	}

	/**
	 * Method to add a bean to a spring configuration.
	 * @param xmlFilename
	 * @param bean
	 * @param backup
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Element addBean(String xmlFilename, Element bean, boolean backup, String syncMode) {
		String filename =FileUtil.getFilename(xmlFilename);
		// some checking first
		if (StringUtil.isEmpty(bean.attributeValue("id"))) {
			_log.warn("Attempt to insert empty bean id on ["+filename+"].");
			return null;
		}
		Document doc = readXMLDocument(xmlFilename);
		if (doc==null)
			return null;		
        Element root = doc.getRootElement();
        // beans
        List<Element> elements = root.elements();
        List<Element> backupBeans = new ArrayList<Element>();
        boolean found = false;
        for (Element element:elements) {
        	// check if bean already exist
        	if (bean.attributeValue("id").equals(element.attributeValue("id"))) {
        		if (syncMode.equals("UPDATE")) {
	        		// copy all the attributes
	        		bean.setAttributes(element.attributes());
		            // inside bean (property)
	        		List<Element> properties = element.elements();
	        		List<Element> beanProperties = bean.elements();
		            for (Element property:properties) {
	            		found = false;
		        		for(Element beanProperty : beanProperties) {
		        			// check if property already exists in bean
		        			if (beanProperty.attributeValue("name").equals(property.attributeValue("name"))) {
		        				// remove existing property in bean
		        				bean.remove(beanProperty);
		        				cloneChildElement(bean, property);
		        				found = true;
		        				break;
		        			}
		        		}
		        		if (!found) {
		        			// add property to bean
		        			cloneChildElement(bean, property);
		        		}
		            }
        		}
	        	root.remove(element);
	        	found = true;
        	} else if (found) {
	        	backupBeans.add(element);
	        	root.remove(element);
        	}
        }
    	cloneChildElement(root, bean);
    	if (backupBeans!=null) {
    		for (Element backUpBean:backupBeans) {
    			cloneChildElement(root, backUpBean);
    		}
    	}
    	if (saveXMLDocument(xmlFilename, doc, backup)) {
    		if (found)
      			_log.info("Updated bean ["+bean.attributeValue("id")+"] on ["+filename+"].");
    		else
    			_log.info("Added bean ["+bean.attributeValue("id")+"] on ["+filename+"].");
    	}
    	return bean;
	}
	
	/**
	 * private helper to read XML document.
	 * @param xmlFilename
	 * @return Document
	 */
	private static Document readXMLDocument(String xmlFilename) {
		SAXReader reader = new SAXReader();
        Document doc = null;
		try {
			InputStream is = new FileInputStream(new File(xmlFilename));
			doc = reader.read(is);
			is.close();
			return doc;
		} catch (DocumentException e) {
			_log.error("Failed to read file contents for "+xmlFilename,e);
			return null;
		} catch (FileNotFoundException e) {
			_log.error("File not found for "+xmlFilename,e);
			return null;
		} catch (IOException e) {
			_log.error("Failed to read xml file "+xmlFilename,e);	
			return doc;
		}
	}
	
	/**
	 * Copies the node to target. This is necessary because
	 * DOM4J appends xmlns when bean is inserted as addElement().
	 * @param target
	 * @param node
	 */
	@SuppressWarnings("unchecked")
	private static void cloneChildElement(Element target, Element node) {
		// create new element
		Element newBean = target.addElement(node.getName());
		// copy all attributes
		newBean.setAttributes(node.attributes());
		// copy text elements
		if (!StringUtil.isEmpty(node.getText()))
			newBean.addText(node.getText());
		List<Element> elements = node.elements();
		// copy all elements
		for(Element elem:elements) {
			cloneChildElement(newBean, elem);
		}
	}
	
	/**
	 * Private helper to save XML document.
	 * @param filename
	 * @param doc
	 * @param backup
	 * @return
	 */
	private static boolean saveXMLDocument(String filename, Document doc, boolean backup) {
		// copy the target to backup folder
		if (backup) FileUtil.backupFile(filename);
		
		// overwrite the target
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setExpandEmptyElements(false);
		format.setIndentSize(4);
		format.setNewLineAfterDeclaration(true);
		XMLWriter out;
		try {
			out = new XMLWriter(new FileWriter(filename), format);
			out.write(doc);
			out.flush();
			out.close();
			return true;
		} catch (IOException e) {
        	_log.info("Failed to write to output filename ["+filename+"]",e);
        	return false;
		}
	}
}
