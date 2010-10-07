package org.opentides.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.opentides.bean.user.UserGroup;
import org.opentides.testsuite.BaseTidesTest;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class UserGroupServiceTest extends BaseTidesTest {
	private UserGroupService userGroupService;
		
	public void setUserGroupService(UserGroupService userGroupService) {
		this.userGroupService = userGroupService;
	}	
	
	private static final class UserGroupsExtractor implements ResultSetExtractor{
		public Object extractData(ResultSet rs) throws SQLException,
				DataAccessException {
			rs.next();
			UserGroup ug = new UserGroup();
			ug.setDescription(rs.getString("description"));
			ug.setName(rs.getString("name"));
			return ug;
		}
	}
	
	public void testLoadUserGroupByName(){
		UserGroup expected = (UserGroup) jdbcTemplate.query("SELECT * FROM USERGROUP WHERE NAME='Super User'",new UserGroupsExtractor());
		UserGroup actual = userGroupService.loadUserGroupByName("Super User");
		
		assertEquals(expected.getDescription(), actual.getDescription());
		assertEquals(expected.getName(), actual.getName());
	}
	
	
}
