package org.opentides.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.opentides.InvalidImplementationException;
import org.opentides.bean.SystemCodes;
import org.opentides.testsuite.BaseTidesTest;
import org.springframework.jdbc.core.RowMapper;

/**
 * This unit test checks BaseEntityDAOTest through the concrete class
 * CategoryDAO.
 * 
 * @author allanctan
 * 
 */
public class BaseEntityDAOTest extends BaseTidesTest {

	private SystemCodesDAO dao;

	
	/**
	 * 
	 */
	public BaseEntityDAOTest() {
		super();
		datasetName = "test/xml/SystemCodesDAOTest.xml";
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

	public void setSystemCodesDAO(SystemCodesDAO systemCodesDAO) {
		this.dao = systemCodesDAO;
	}

	
	public void testGetJpqlQuery() {
		assertEquals("from BaseUser where credential.username=:username", dao
				.getJpqlQuery("jpql.user.findByUsername"));
		try {
			dao.getJpqlQuery("missing.key.asgdf");
			fail("No Exception thrown in missing key.");
		} catch (InvalidImplementationException iie) {
		}
	}
	
	public void testLoadEntityModel() {
		SystemCodes sc1 = dao.loadEntityModel(9001l);
		assertEquals("PH", sc1.getKey());
		assertEquals("Philippines", sc1.getValue());
		assertEquals("COUNTRY", sc1.getCategory());
		//TODO: How to test with lock functionality
	}
	
	public void testSaveEntityModelAddObject() {
		int count = jdbcTemplate.queryForInt("select count(*) from SYSTEM_CODES where KEY_='AU' and CATEGORY_='COUNTRY'");
		assertEquals(0, count);
		SystemCodes sc1 = new SystemCodes();
		sc1.setKey("AU");
		sc1.setCategory("COUNTRY");
		sc1.setValue("Australia");
		dao.saveEntityModel(sc1);
		count = jdbcTemplate.queryForInt("select count(*) from SYSTEM_CODES where KEY_='AU' and CATEGORY_='COUNTRY'");
		assertEquals(1, count);
	}
	
	public void testSaveEntityModelUpdateObject() {
		int count = jdbcTemplate.queryForInt("select count(*) from SYSTEM_CODES where KEY_='TK' and CATEGORY_='COUNTRY'");
		assertEquals(0, count);
		count = jdbcTemplate.queryForInt("select count(*) from SYSTEM_CODES where KEY_='PH' and CATEGORY_='COUNTRY'");
		assertEquals(1, count);
		SystemCodes sc1 = dao.loadEntityModel(9001l);
		sc1.setKey("TK");
		sc1.setCategory("COUNTRY");
		sc1.setValue("Thailand");
		dao.saveEntityModel(sc1);
		count = jdbcTemplate.queryForInt("select count(*) from SYSTEM_CODES where KEY_='TK' and CATEGORY_='COUNTRY'");
		assertEquals(1, count);
		count = jdbcTemplate.queryForInt("select count(*) from SYSTEM_CODES where KEY_='PH' and CATEGORY_='COUNTRY'");
		assertEquals(0, count);
		SystemCodes sc2 = dao.loadEntityModel(9001l);
		assertEquals(sc1, sc2);
	}

	public void testDeleteEntityModel() {
		int count = jdbcTemplate.queryForInt("select count(*) from SYSTEM_CODES where KEY_='US'");
		assertEquals(1, count);		
		dao.deleteEntityModel(9002l);
		count = jdbcTemplate.queryForInt("select count(*) from SYSTEM_CODES where KEY_='US'");
		assertEquals(0, count);		

		count = jdbcTemplate.queryForInt("select count(*) from SYSTEM_CODES where KEY_='PH'");
		assertEquals(1, count);	
		SystemCodes sc1 = dao.loadEntityModel(9001l);
		dao.deleteEntityModel(sc1);
		count = jdbcTemplate.queryForInt("select count(*) from SYSTEM_CODES where KEY_='PH'");
		assertEquals(0, count);	
	}
	
	public void testCountAll() {
		long count = dao.countAll();
		long target = jdbcTemplate.queryForInt("select count(*) from SYSTEM_CODES");
		assertEquals(target, count);
	}
	
	public void testFindAll() {
		List<SystemCodes> all = dao.findAll();
		long target = jdbcTemplate.queryForInt("select count(*) from SYSTEM_CODES");
		assertEquals(target, all.size());
	}
	
	@SuppressWarnings("unchecked")
	public void testFindAllPaging() {
		List<SystemCodes> all = dao.findAll(0,2);
		List<SystemCodes> targets = this.jdbcTemplate.query(
			    "select * from SYSTEM_CODES limit 0,2", 
			    new SystemCodesMapper());
		Assert.assertArrayEquals(targets.toArray(), all.toArray());

		all = dao.findAll(3,2);
		targets = this.jdbcTemplate.query(
			    "select * from SYSTEM_CODES limit 3,2", 
			    new SystemCodesMapper());
		Assert.assertArrayEquals(targets.toArray(), all.toArray());

		all = dao.findAll(5,5);
		targets = this.jdbcTemplate.query(
			    "select * from SYSTEM_CODES limit 5,5", 
			    new SystemCodesMapper());
		Assert.assertArrayEquals(targets.toArray(), all.toArray());

	}
	
	// TODO: Test ByExample with various datatypes (e.g. String, Long, Boolean, etc.)
	public void testCountByExampleString() {
		SystemCodes example = new SystemCodes();
		example.setDisableProtection(true);
		example.setCategory("OFFICE");
		long count = dao.countByExample(example);
		long target = jdbcTemplate.queryForInt("select count(*) from SYSTEM_CODES where CATEGORY_='OFFICE'");
		assertEquals(target, count);		
	}
	
	// TODO: Test ByExample with various parameters (e.g. exact match or not)
	@SuppressWarnings("unchecked")
	public void testFindByExample() {
		SystemCodes example = new SystemCodes();
		example.setDisableProtection(true);
		example.setCategory("OFFICE");
		List<SystemCodes> codes = dao.findByExample(example);
		List<SystemCodes> targets = jdbcTemplate.query(
				"select * from SYSTEM_CODES where CATEGORY_='OFFICE'",  
				new SystemCodesMapper());
		Assert.assertArrayEquals(targets.toArray(), codes.toArray());
	}
	
	@SuppressWarnings("unchecked")
	public void testFindByExampleSorting() {
		SystemCodes example = new SystemCodes();
		example.setDisableProtection(true);
		example.setOrderOption("key");
		example.setOrderFlow("desc");
		example.setCategory("COUNTRY");
		List<SystemCodes> codes = dao.findByExample(example);
		List<SystemCodes> targets = jdbcTemplate.query(
				"select * from SYSTEM_CODES where CATEGORY_='COUNTRY' order by KEY_ desc",  
				new SystemCodesMapper());
		Assert.assertArrayEquals(targets.toArray(), codes.toArray());
	}
	
	@SuppressWarnings("unchecked")
	public void testFindByExamplePaging() {
		SystemCodes example = new SystemCodes();
		example.setDisableProtection(true);
		example.setCategory("COUNTRY");
		List<SystemCodes> codes = dao.findByExample(example,0,2);
		List<SystemCodes> targets = this.jdbcTemplate.query(
			    "select * from SYSTEM_CODES where CATEGORY_='COUNTRY' limit 0,2", 
			    new SystemCodesMapper());
		Assert.assertArrayEquals(targets.toArray(), codes.toArray());

		codes = dao.findByExample(example,3,2);
		targets = this.jdbcTemplate.query(
			    "select * from SYSTEM_CODES where CATEGORY_='COUNTRY' limit 3,2", 
			    new SystemCodesMapper());
		Assert.assertArrayEquals(targets.toArray(), codes.toArray());

	}
	
	public void testFindByNamedQuery() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("category", "OFFICE");
		List<SystemCodes> codes = dao.findByNamedQuery("jpql.systemcodes.findByCategory", params);
		List<SystemCodes> targets = this.jdbcTemplate.query(
			    "select * from SYSTEM_CODES WHERE CATEGORY_='OFFICE'", 
			    new SystemCodesMapper());
		Assert.assertArrayEquals(targets.toArray(), codes.toArray());
	}
	
	public void testFindByNamedQueryPaging() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("category", "OFFICE");
		List<SystemCodes> codes = dao.findByNamedQuery("jpql.systemcodes.findByCategory", params,1, 5);
		List<SystemCodes> targets = this.jdbcTemplate.query(
			    "select * from SYSTEM_CODES WHERE CATEGORY_='OFFICE' limit 1,5", 
			    new SystemCodesMapper());
		Assert.assertArrayEquals(targets.toArray(), codes.toArray());
	}
	
	public void testfindSingleResultByNamedQuery() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("keyName", "CN");
		SystemCodes object = dao.findSingleResultByNamedQuery("jpql.systemcodes.findByKey", params);
		List<SystemCodes> target = this.jdbcTemplate.query("select * from SYSTEM_CODES where key_='CN'", new SystemCodesMapper());
		assertEquals(target.get(0), object);		
	}
}
