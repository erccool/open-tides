package com.ideyatech.core.persistence;

import java.util.Map;

import org.junit.Test;
import org.opentides.bean.Category;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.jpa.AbstractJpaTests;

import org.opentides.InvalidImplementationException;


/**
 * This unit test checks BaseEntityDAOTest through the concrete class
 * CategoryDAO.
 * @author allanctan
 *
 */
public class BaseEntityDAOTest extends 
		AbstractJpaTests {

	public BaseEntityDAOTest() {
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
	
//	@Test
//	public void testGetJpqlQuery() {
//		assertEquals("from BaseUser where credential.username=:username",
//				dao.getJpqlQuery("BaseUser.findByUsername"));
//		try {
//			dao.getJpqlQuery("ASASD");
//			fail("No Exception thrown in missing key.");
//		} catch (InvalidImplementationException iie) {			
//		}
//	}

//	/**
//	 * 
//	 */
//	@SuppressWarnings("unchecked")
//	@Test
//	@Rollback
//	public final void testSaveNewEntityModel() {
//		Category cat = new Category();
//		cat.setName("JUnit Test Name");
//		cat.setDescription("JUnit Test Description");
//		dao.saveEntityModel(cat);
//		// int cnt = jdbcTemplate.queryForInt("select count(*) from CATEGORY where CATEGORY_NAME='JUnit Test Name'");
//		// assertEquals("Failed to insert new record", prevCount+1, cnt);
//		Map result = jdbcTemplate.queryForMap("select * from CATEGORY where ID=?", new Object[]{cat.getId()});
//		assertEquals("JUnit Test Name",result.get("CATEGORY_NAME"));
//		assertEquals("JUnit Test Description",result.get("CATEGORY_DESC"));
//	}
//	
//	@Test
//	public void testCountAll() {
//		long cnt = dao.countAll();
//		int cnt2 = jdbcTemplate.queryForInt("select count(*) from CATEGORY");
//		assertEquals("Mismatch on count all.", cnt2, (int) cnt);			
//	}
//
//	/**
//	 * @param dao the dao to set
//	 */
//	public void setCategoryDAO(CategoryDAO categoryDAO) {
//		this.dao = categoryDAO;
//	}
}
