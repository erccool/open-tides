/**
 * 
 */
package org.hightides.annotations.factory;

import java.util.Map;

import org.hightides.annotations.processor.Processor;

/**
 * @author allantan
 * 
 */
public abstract class CodeFactory {
	/**
	 * Enumeration of language being supported.
	 * @author allantan
	 */
	public enum Language {
		OPENTIDES, SPRING
	};

	protected Language language;
	
	/**
	 *  Map of supported annotations for this code factory
	 */
	@SuppressWarnings("unchecked")
	protected Map<Class, Processor> processorMap;


	/**
	 * Initialization of supported processors.
	 * This method must be implemented by concrete classes to
	 * populate processorMap
	 */
	protected abstract void initializeProcessors();
	
	/**
	 * Retrieves the code factory for the specified language.
	 * @param language
	 * @return
	 */
	public static CodeFactory getFactory(Language language) {
		CodeFactory factory = null;
		if (language==Language.OPENTIDES) {
			factory = new OpentidesFactory();
		}
		if (factory!=null) {
			factory.initializeProcessors();
			return factory;
		}
		throw new UnsupportedOperationException(
				"No factory available for "
						+ language.toString());
	}
	
	/**
	 * Retrieves the corresponding processor for an annotation
	 * and language. Returns null if no processor has been defined.
	 * @param annotation
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Processor getProcessor(Class annotation) {
		return processorMap.get(annotation);
	}
}
