package com.ideyatech.core.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.opentides.bean.user.BaseUser;

import org.junit.Test;
import org.opentides.persistence.UserDAO;
import org.springframework.test.jpa.AbstractJpaTests;


/**
 * This unit test checks BaseUserDAO functions.
 * @author allanctan
 *
 */
public class UserDAOTest extends 
		AbstractJpaTests {

	private UserDAO userDAO;
	
	public UserDAOTest() {
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
	public void testAddUser() {
		// cleanup
		jdbcTemplate.execute("delete from USER_PROFILE where EMAIL='johndoe@missing.com'");
		jdbcTemplate.execute("delete from USERS where USERNAME='johndoe@missing.com'");
		// make sure db is clean
		BaseUser user = new BaseUser();
		user.setFirstName("John");
		user.setLastName("Doe");
		user.setEmailAddress("johndoe@missing.com");
//		List<String> roles = new ArrayList<String>();
//		roles.add("ROLE_ADMIN");
//		roles.add("ROLE_TEST");
//		user.setRoleNames(roles);
		user.getCredential().setPassword("password");
		userDAO.saveEntityModel(user);
		assertEquals( 1, jdbcTemplate.queryForInt("select count(*) from USER_PROFILE where EMAIL='johndoe@missing.com'"));
		long userId = user.getId();
		
		Map result = jdbcTemplate.queryForMap("select * from USER_PROFILE where ID=?", new Object[]{userId});
		assertEquals("John",result.get("FIRSTNAME"));
		assertEquals("Doe",result.get("LASTNAME"));
		assertEquals("johndoe@missing.com",result.get("EMAIL"));
		// assert the roles
		assertEquals( 2, jdbcTemplate.queryForInt("select count(*) from AUTHORITIES where USERID="+userId));
		// assert the credentials
		Map cred = jdbcTemplate.queryForMap("select * from USERS where USERID=?", new Object[]{userId});
		assertEquals("johndoe@missing.com",cred.get("USERNAME"));
		assertEquals("password",cred.get("PASSWORD"));		
	}
	
	@Test
	public void testRemoveRoles() {
		int id = jdbcTemplate.queryForInt("select id from USER_PROFILE where EMAIL='johndoe@missing.com'");
		BaseUser user = userDAO.loadEntityModel(new Long(id));
//		List<String> roleNames = user.getRoleNames();
//		roleNames.remove(1);
//		user.setRoleNames(roleNames);
		userDAO.saveEntityModel(user);
//		assertEquals(roleNames.size(), jdbcTemplate.queryForInt("select count(*) from AUTHORITIES where USERID="+user.getId()));
	}
	
	@Test
	public void testRemoveUser() {
		int id = jdbcTemplate.queryForInt("select id from USER_PROFILE where EMAIL='johndoe@missing.com'");
		BaseUser user = userDAO.loadEntityModel(new Long(id));
		userDAO.deleteEntityModel(user);
		assertEquals(0, jdbcTemplate.queryForInt("select count(*) from USER_PROFILE where ID="+user.getId()));
		assertEquals(0, jdbcTemplate.queryForInt("select count(*) from AUTHORITIES where USERID="+user.getId()));
		assertEquals(0, jdbcTemplate.queryForInt("select count(*) from USERS where USERID="+user.getId()));
	}
	
	@Test
	public void testUniqueUsernameConstraints() {
		try {
			BaseUser user = new BaseUser();
			user.setFirstName("Test 1");
			user.setLastName("Surname");
			user.setEmailAddress("test@abc.com");
			user.getCredential().setPassword("tester1");
			user.getCredential().setEnabled(true);
			userDAO.saveEntityModel(user);
			fail("Failed to throw exception on duplicate username");
		} catch (Exception e) {
			// success
		}
	}
	
	@Test
	public void testUniqueEmailConstraints() {
		try {
			BaseUser user = new BaseUser();
			user.getCredential().setUsername("test1@abc.com");
			user.getCredential().setPassword("tester1");
			user.getCredential().setEnabled(true);
			user.setFirstName("Test 1");
			user.setLastName("Surname");
			user.setEmailAddress("test1@abc.com");
			userDAO.saveEntityModel(user);
			fail("Failed to throw exception on duplicate email");
		} catch (Exception e) {
			
		}
	}
	
	@Test
	public void testCountAll() {
		long cnt = userDAO.countAll();
		int cnt2 = jdbcTemplate.queryForInt("select count(*) from USER_PROFILE");
		assertEquals("Mismatch on count all.", cnt2, (int) cnt);			
	}
	
	@Test
	public void testIsRegistered() {
		assertTrue(userDAO.isRegisteredByEmail("test1@abc.com"));
		assertFalse(userDAO.isRegisteredByEmail("none@nothing.com"));
		assertFalse(userDAO.isRegisteredByEmail(""));
		assertFalse(userDAO.isRegisteredByEmail(null));
	}
	
	@Test
	public void testLoadByUsername() {
		BaseUser user = userDAO.loadByUsername("test1@abc.com");
		assertEquals("test1@abc.com", user.getEmailAddress());
		assertEquals("test1@abc.com", user.getCredential().getUsername());		
		assertNull(userDAO.loadByUsername("none@nothing.com"));
		assertNull(userDAO.loadByUsername(""));
		assertNull(userDAO.loadByUsername(null));		
	}
	
	/**
	 * @param categoryDAO the categoryDAO to set
	 */
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

}
