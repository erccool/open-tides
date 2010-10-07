package org.opentides.validator;

import org.junit.Test;
import org.opentides.persistence.UserDAO;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

public class ChangePasswordValidatorTest extends AbstractDependencyInjectionSpringContextTests{
	
	private UserDAO coreUserDAO;
	
	public ChangePasswordValidatorTest() {
		super();
		setAutowireMode(AUTOWIRE_BY_NAME);
	}
	
	public void setCoreUserDAO(UserDAO coreUserDAO) {
		this.coreUserDAO = coreUserDAO;
	}
	
	@Test
	public void testValidate(){
		
	}
}
