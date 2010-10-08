/**
 * 
 */
package org.hightides;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.velocity.app.Velocity;
import org.hightides.annotations.Dao;
import org.hightides.annotations.Service;
import org.hightides.annotations.factory.CodeFactory;
import org.hightides.annotations.factory.CodeFactory.Language;
import org.hightides.annotations.processor.Processor;
import org.hightides.annotations.util.PackageHelper;
import org.scannotation.AnnotationDB;
import org.scannotation.ClasspathUrlFinder;

/**
 * @author allantan
 * 
 */
@SuppressWarnings("unchecked")
public class HighTides {

	private static Logger _log = Logger.getLogger(HighTides.class);
	private List<Class> annotationList;
	private AnnotationDB db;
	private static final String propFilename="resources/velocity.properties";
	
	private void initializeList() {
		annotationList = new ArrayList<Class>();
		annotationList.add(Service.class);
		annotationList.add(Dao.class);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		HighTides ht = new HighTides();
		ht.generate();
	}

	public HighTides() {
		URL[] urls = ClasspathUrlFinder.findClassPaths();
		try {
			Properties props = new Properties();
			props.load(getClass().getClassLoader().getResourceAsStream(propFilename));
			Velocity.init(props);
			initializeList();
			PackageHelper.setBaseTemplatePath(props.getProperty("file.resource.loader.path"));
			PackageHelper.setBaseOutputPath(props.getProperty("file.output.path"));
			db = new AnnotationDB();
			db.scanArchives(urls);
		} catch (IOException e) {
			_log.error("Initialization failed.", e);
			throw new RuntimeException(e);
		} catch (Exception e1) {
			_log.error("Initialization failed.", e1);
			throw new RuntimeException(e1);
		}
	}

	/**
	 * This method checks all registered annotations and search for annotated classes.
	 * Templates are then applied to the classes.
	 * 
	 */
	public void generate() {
		CodeFactory cf = CodeFactory.getFactory(Language.OPENTIDES);
		for (Class annotation : annotationList) {
			_log.info("============================");
			_log.info("Generating "+annotation.getName()+"...");
			// retrieve the processor for this annotation
			Processor p = cf.getProcessor(annotation);
			// get all classes that are marked with this annotation
			Set<String> serviceClasses = db.getAnnotationIndex().get(annotation.getName());
			if (serviceClasses==null || serviceClasses.size()==0) {
				_log.info("    No classes found.");
				continue;
			}
			for (String className : serviceClasses) {
				_log.info("    Found "+className);
				try {
					Map<String, Object> params = populateParams(annotation, className);
					p.execute(params);
				} catch (Exception e) {
					_log.error("Failed to process class ["+className+"] for annotation ["+annotation+"]");
				}
			}
		}
	}

	private Map<String, Object> populateParams(Class annotation, String className) throws ClassNotFoundException {
		Map<String, Object> params = new HashMap<String,Object>();
		Class clazz = Class.forName(className);
		params.put("modelName", clazz.getSimpleName());
		params.put("modelPackage",clazz.getPackage().getName());
		return params;
	}
}