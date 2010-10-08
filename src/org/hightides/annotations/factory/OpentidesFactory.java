/**
 * 
 */
package org.hightides.annotations.factory;

import java.util.HashMap;

import org.hightides.annotations.Dao;
import org.hightides.annotations.Service;
import org.hightides.annotations.filter.OpentidesFilter;
import org.hightides.annotations.processor.CloningProcessor;
import org.hightides.annotations.processor.Processor;

/**
 * @author allantan
 *
 */
public class OpentidesFactory extends CodeFactory {
	
	@SuppressWarnings("unchecked")
	protected final void initializeProcessors() {
		processorMap = new HashMap<Class, Processor>();
		
		// initialize the service processor
		CloningProcessor serviceProcessor = new CloningProcessor("/opentides/service/","../service/");
		serviceProcessor.setFilter(new OpentidesFilter());
		processorMap.put(Service.class, serviceProcessor);

		// initialize the dao procesor
		CloningProcessor daoProcessor = new CloningProcessor("/opentides/dao/","../dao/");
		daoProcessor.setFilter(new OpentidesFilter());
		processorMap.put(Dao.class, daoProcessor);
	}
	
	public OpentidesFactory() {
		language = Language.OPENTIDES;
	}
}