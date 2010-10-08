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
public interface ProcessorFilter {
	public void doFilter(File path, Map<String, Object> params);
}
