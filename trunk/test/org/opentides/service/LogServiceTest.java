package org.opentides.service;

import java.util.Arrays;

import org.opentides.bean.SystemCodes;
import org.opentides.testsuite.BaseTidesTest;

public class LogServiceTest extends BaseTidesTest {
	private LogService logService;
		
	public void setLogService(LogService logService) {
		this.logService = logService;
	}
	
	public void testFindLogByReferenceAndClass(){	
		int expected = jdbcTemplate.queryForInt("SELECT count(*) FROM HISTORY_LOG WHERE REFERENCE='80001' AND ENTITY_CLASS='org.opentides.bean.SystemCodes'");
		int actual = logService.findLogByReferenceAndClass("80001", 
				Arrays.asList(new Class[]{SystemCodes.class})).size();		
		assertEquals(expected, actual);
	}
}
