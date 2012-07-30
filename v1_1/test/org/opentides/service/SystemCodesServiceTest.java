package org.opentides.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;
import org.opentides.bean.SystemCodes;
import org.opentides.testsuite.BaseTidesTest;
import org.springframework.jdbc.core.RowMapper;

public class SystemCodesServiceTest extends BaseTidesTest {
	private SystemCodesService systemCodesService;
	
	public void setSystemCodesService(SystemCodesService systemCodesService) {
		this.systemCodesService = systemCodesService;
	}
	
	private static final class SystemCodesMapper implements RowMapper {
	    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
	        SystemCodes sc = new SystemCodes();
	        sc.setCategory(rs.getString("category_"));
	        sc.setKey(rs.getString("key_"));
	        sc.setValue(rs.getString("value_"));
	        return sc;
	    }
	}
	
	@Test
	public void testGetAllCategories(){
		int expected = jdbcTemplate.query("SELECT * FROM SYSTEM_CODES WHERE CATEGORY_ IS NOT NULL GROUP BY CATEGORY_", new SystemCodesMapper()).size();
		int actual = systemCodesService.getAllCategories().size();
		assertEquals(expected, actual);
	}
	
	// fails due to findByExample bug where the column name generated for SystemCodes.key is KEY
	// which should be KEY_ and also as a note KEY is a reserved word in MySQL
    @Test
	public void testFindBykey(){		
		SystemCodes sc = new SystemCodes();
		sc.setDisableProtection(true);
		sc.setCategory("COUNTRY");
		sc.setKey("PH");
		sc.setValue("Philippines");
		
		SystemCodes actual = systemCodesService.findByKey(sc);
		
		assertEquals(sc.getKey(), actual.getKey());
		assertEquals(sc.getCategory(), actual.getCategory());
		assertEquals(sc.getValue(), actual.getValue());
	}
	
    @Test
	public void testFindSystemCodesByCategory(){
		int expected = jdbcTemplate.queryForInt("SELECT count(*) FROM SYSTEM_CODES WHERE CATEGORY_='COUNTRY'");
		int actual = systemCodesService.findSystemCodesByCategory("COUNTRY").size();
		assertEquals(expected, actual);
	}
		   
    @Test    
    public final void testNewId() {
        assertEquals( new Long(1), systemCodesService.incrementValue("TESTING"));
        assertEquals( new Long(1), systemCodesService.incrementValue("TESTING2"));
        assertEquals( new Long(2), systemCodesService.incrementValue("TESTING"));
        assertEquals( new Long(3), systemCodesService.incrementValue("TESTING"));
        assertEquals( new Long(2), systemCodesService.incrementValue("TESTING2"));
    }
}
