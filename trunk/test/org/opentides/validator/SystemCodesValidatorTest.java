package org.opentides.validator;

import org.springframework.test.jpa.AbstractJpaTests;

public class SystemCodesValidatorTest extends AbstractJpaTests{
	
	public SystemCodesValidatorTest() {
		super();
		setAutowireMode(AUTOWIRE_BY_NAME);
	}
	
	public void testIsDuplicateKey(){
		
	}
}
