package com.ideyatech.core.service;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.test.jpa.AbstractJpaTests;

import com.ideyatech.core.bean.PasswordReset;
import com.ideyatech.core.bean.user.BaseUser;
import com.ideyatech.core.persistence.PasswordResetDAO;
import com.ideyatech.core.persistence.UserDAO;

public class UserServiceTest extends AbstractJpaTests {
	
	private UserService userService;
	private UserDAO 	userDAO;
	private PasswordResetDAO passwordResetDAO;
	
	public UserServiceTest() {
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
	public final void testGetRoles() {
		Map<String,String> roles = userService.getRoles();
		assertEquals(2,roles.size());
		assertTrue(roles.containsValue("Administrator"));
		assertTrue(roles.containsValue("Guest"));
	}
	
	@Test 
	public void testRequestPasswordReset() {
		//test #1
		String email = "test1@abc.com";
		userService.requestPasswordReset(email);
		PasswordReset example = new PasswordReset();
		example.setEmailAddress(email);
		example.setStatus(PasswordReset.STATUS_ACTIVE);
		assertNotSame(0, passwordResetDAO.countByExample(example,true));
		// test #2
		email = "test@abc.com";
		userService.requestPasswordReset(email);
		example = new PasswordReset();
		example.setEmailAddress(email);
		example.setStatus(PasswordReset.STATUS_ACTIVE);
		assertNotSame(0, passwordResetDAO.countByExample(example,true));
		try {
			userService.requestPasswordReset("garbage@bin.com");
			fail("No exception thrown on missing email address");
		} catch (RuntimeException re) {	
		}
	}
	
	@Test
	public void testConfirmPasswordResetByEmailToken() {
		String email = "test1@abc.com";
		PasswordReset example = new PasswordReset();
		example.setEmailAddress(email);
		example.setStatus(PasswordReset.STATUS_ACTIVE);
		List<PasswordReset> actuals = passwordResetDAO.findByExample(example);
		PasswordReset actual = actuals.get(0);
		assertFalse(userService.confirmPasswordReset(email, "dummy"));
		assertTrue(userService.confirmPasswordReset(email, actual.getToken()));
	}

	@Test
	public void testConfirmPasswordResetByCipher() {
		String email = "test@abc.com";
		PasswordReset example = new PasswordReset();
		example.setEmailAddress(email);
		example.setStatus(PasswordReset.STATUS_ACTIVE);
		List<PasswordReset> actuals = passwordResetDAO.findByExample(example);
		PasswordReset actual = actuals.get(0);
		PasswordReset dummy = new PasswordReset();
		dummy.setCipher("dummy");
		assertFalse(userService.confirmPasswordResetByCipher(dummy));
		assertTrue(userService.confirmPasswordResetByCipher(actual));
	}
	
	public void testResetPasswordFail() {
		String email = "test@abc.com";
		PasswordReset dummy = new PasswordReset();
		dummy.setEmailAddress(email);
		dummy.setToken("dummy");
		assertFalse(userService.resetPassword(dummy));
	}
	
	@Test
	public void testResetPassword() {
		String email = "test@abc.com";
		PasswordReset example = new PasswordReset();
		example.setEmailAddress(email);
		example.setStatus(PasswordReset.STATUS_ACTIVE);
		List<PasswordReset> actuals = passwordResetDAO.findByExample(example);
		PasswordReset actual = actuals.get(0);
		long actualId = actual.getId();		
		actual.setPassword("tennis");
		assertTrue(userService.resetPassword(actual));
		// password reset must be updated to used
		PasswordReset newActual = passwordResetDAO.loadEntityModel(actualId);
		assertSame(PasswordReset.STATUS_USED, newActual.getStatus());
		// and password must change
		BaseUser user = userDAO.loadByEmailAddress(email);
		assertEquals("tennis", user.getCredential().getPassword());
	}

	/**
	 * @param passwordResetDAO the passwordResetDAO to set
	 */
	public void setPasswordResetDAO(PasswordResetDAO passwordResetDAO) {
		this.passwordResetDAO = passwordResetDAO;
	}
	
	/**
	 * @param userService the userService to set
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * @param userDAO the userDAO to set
	 */
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
}
