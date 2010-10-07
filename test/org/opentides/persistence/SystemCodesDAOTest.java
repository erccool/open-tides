package org.opentides.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.opentides.bean.SystemCodes;
import org.opentides.testsuite.BaseTidesTest;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

public class SystemCodesDAOTest extends BaseTidesTest {
	private static final Logger _log = Logger.getLogger(SystemCodesDAOTest.class);
	
	private SystemCodesDAO dao;

	private static final class SystemCodesMapper implements RowMapper {
	    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
	        SystemCodes sc = new SystemCodes();
	        sc.setCategory(rs.getString("category_"));
	        sc.setKey(rs.getString("key_"));
	        sc.setValue(rs.getString("value_"));
	        return sc;
	    }
	}
	
	private static final class SystemCodesExtractor implements ResultSetExtractor{
		public Object extractData(ResultSet rs) throws SQLException,
				DataAccessException {
			rs.next();
			SystemCodes sc = new SystemCodes();
			sc.setId(rs.getLong("id"));
			sc.setCategory(rs.getString("category_"));
			sc.setKey(rs.getString("key_"));
	        sc.setValue(rs.getString("value_"));
	        return sc;
		}
	}

	public void setSystemCodesDAO(SystemCodesDAO systemCodesDAO) {
		this.dao = systemCodesDAO;
	}
	
	public void testFindSystemCodesByCategory(){
		int expected = jdbcTemplate.queryForInt("SELECT count(*) FROM SYSTEM_CODES WHERE CATEGORY_='COUNTRY'");
		int actual = dao.findSystemCodesByCategory("COUNTRY").size(); 
		assertEquals(expected, actual);
	}
	
	public void testLoadBySystemCodesByKey(){
		SystemCodes expected = (SystemCodes) jdbcTemplate.query("SELECT * FROM SYSTEM_CODES WHERE KEY_='AD'", new SystemCodesExtractor());		
		SystemCodes actual = dao.loadBySystemCodesByKey("AD");
		
		assertEquals(expected.getId(), actual.getId());
		assertEquals(expected.getCategory(),actual.getCategory());
		assertEquals(expected.getValue(), actual.getValue());
	}
	
	public void testGetAllCategories(){
		int expected = jdbcTemplate.query("SELECT * FROM SYSTEM_CODES WHERE CATEGORY_ IS NOT NULL GROUP BY CATEGORY_", new SystemCodesMapper()).size();
		int actual = dao.getAllCategories().size();
		_log.debug("Number of categories: "+dao.getAllCategories().size());
		assertEquals(expected, actual);
	}

}
