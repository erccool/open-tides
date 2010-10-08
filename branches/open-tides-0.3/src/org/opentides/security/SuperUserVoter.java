/**
 * 
 */
package org.opentides.security;

import org.acegisecurity.Authentication;
import org.acegisecurity.ConfigAttribute;
import org.acegisecurity.ConfigAttributeDefinition;
import org.acegisecurity.vote.AccessDecisionVoter;

/**
 * This class provides a positive vote for user with role "SUPER_USER".
 * @author allantan
 *
 */
public class SuperUserVoter implements AccessDecisionVoter {
	
	public static final String SUPER_USER = "SUPER_USER";

	/** 
	 * Process only when attribute is named as "SUPER_USER"
	 */
	public boolean supports(ConfigAttribute attrib) {
		if ( SUPER_USER.equals(attrib.getAttribute()) )
			return true;
		else
			return false;
	}

	/* (non-Javadoc)
	 * @see org.acegisecurity.vote.AccessDecisionVoter#supports(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	public boolean supports(Class arg0) {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.acegisecurity.vote.AccessDecisionVoter#vote(org.acegisecurity.Authentication, java.lang.Object, org.acegisecurity.ConfigAttributeDefinition)
	 */
	public int vote(Authentication arg0, Object arg1,
			ConfigAttributeDefinition arg2) {
		return ACCESS_GRANTED;
	}

}
