/**
 * 
 */
package org.hightides.annotations.processor;

import java.util.Map;

/**
 * @author allantan
 *
 */
public interface Processor {
	public void execute(Map<String, Object> params);
}
