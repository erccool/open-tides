package com.ideyatech.core.service;

import org.junit.Test;
import org.opentides.service.SystemCodesService;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.jpa.AbstractJpaTests;
import org.springframework.transaction.annotation.Transactional;

public class SystemCodesServiceTest extends AbstractJpaTests {
	
	private SystemCodesService systemCodesService;
	
	public SystemCodesServiceTest() {
		super();
		setAutowireMode(AUTOWIRE_BY_NAME);
	}

	/* (non-Javadoc)
	 * @see org.springframework.test.AbstractSingleSpringContextTests#getConfigLocations()
	 */
	@Override
	protected String[] getConfigLocations() {
		return new String[] { 
		         "classpath:/tides/tidesContext*.xml",
				 "classpath:/tides/testConfig.xml"
		      };
	}
	
	@Test
	@Transactional
	@Rollback
	public final void testNewId() {
		assertEquals( new Long(1), systemCodesService.incrementValue("TESTING"));
		assertEquals( new Long(2), systemCodesService.incrementValue("TESTING"));
		assertEquals( new Long(3), systemCodesService.incrementValue("TESTING"));
		assertEquals( new Long(1), systemCodesService.incrementValue("TESTING2"));
		assertEquals( new Long(2), systemCodesService.incrementValue("TESTING2"));
	}
	
	@Test
	public void testConcurrency() {
	}

}
