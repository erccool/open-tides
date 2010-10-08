/**
 * 
 */
package org.hightides.annotations.filter;

import java.io.File;
import java.util.Map;

/**
 * @author allantan
 *
 */
public class OpentidesFilter implements ProcessorFilter {

	public void doFilter(File path, Map<String, Object> params) {
		// add interface package of Impl
		String templateName = path.getName();
		if (templateName.contains("Impl")) {
			params.put("interfacePackage", 
					params.get("package").toString().replace(".impl", ""));
		}
	}

}
