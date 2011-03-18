package org.opentides.persistence;

import java.util.Set;

import org.opentides.bean.user.UserGroup;
import org.opentides.bean.user.UserRole;
import org.opentides.testsuite.BaseTidesTest;


public class UserGroupDAOTest extends BaseTidesTest {

	private UserGroupDAO userGroupDAO;

	public void testAdd() {
		UserGroup group = new UserGroup();
		group.setName("test");
		group.setDescription("test");
		UserRole role1 = new UserRole(group, "role1");
		group.addRole(role1);

		userGroupDAO.saveEntityModel(group);

		assertNotNull(group.getId());
		UserGroup testGroup = userGroupDAO.loadEntityModel(group.getId());
		Set<UserRole> roles = testGroup.getRoles();
		assertNotNull(roles);
		assertTrue(roles.size() == 1);
		assertTrue(roles.iterator().next().getRole().equals(role1.getRole()));

		testGroup.removeRole(role1);
		userGroupDAO.saveEntityModel(testGroup);

		assertEquals(testGroup.getId(), group.getId());
		UserGroup testGroup2 = userGroupDAO.loadEntityModel(testGroup.getId());
		assertTrue(testGroup2.getRoles().size() == 0);

	}

	/**
	 * @param userGroupDAO
	 *            the userGroupDAO to set
	 */
	public void setUserGroupDAO(UserGroupDAO userGroupDAO) {
		this.userGroupDAO = userGroupDAO;
	}

}
