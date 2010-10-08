package com.ideyatech.core.persistence;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

import javax.sql.DataSource;

import org.opentides.bean.user.UserGroup;
import org.opentides.bean.user.UserRole;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.opentides.persistence.UserGroupDAO;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.test.jpa.AbstractJpaTests;


public class UserGroupDAOTest extends AbstractJpaTests {
	private final static String USERGROUP = "/test/com/ideyatech/core/persistence/USERGROUP.xml";

	private UserGroupDAO userGroupDAO;

	@Override
	protected String[] getConfigLocations() {
		return new String[] {
				"file:WebContent/WEB-INF/tides/tidesContext*.xml",
				"file:WebContent/WEB-INF/tides/testConfig.xml" };
	}

	@Override
	protected void onSetUpInTransaction() throws Exception {
		super.onSetUpInTransaction();
		// executeDatabaseOperation(DatabaseOperation.CLEAN_INSERT, USERGROUP);
	}

	@Override
	protected void onTearDown() throws Exception {
		super.onTearDown();
		// executeDatabaseOperation(DatabaseOperation.DELETE_ALL, USERGROUP);
	}

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
	 * for deleting/loading dbunit datasetXML
	 * 
	 * @param databaseOperation
	 * @param dataSetXML
	 * @throws DatabaseUnitException
	 * @throws SQLException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void executeDatabaseOperation(DatabaseOperation databaseOperation,
			String dataSetXML) throws DatabaseUnitException, SQLException,
			FileNotFoundException, IOException {
		DataSource ds = jdbcTemplate.getDataSource();
		Connection con = DataSourceUtils.getConnection(ds);
		IDatabaseConnection dbUnitCon = new DatabaseConnection(con);

		IDataSet dataSet = new FlatXmlDataSet(new FileInputStream(dataSetXML));
		databaseOperation.execute(dbUnitCon, dataSet);
		DataSourceUtils.releaseConnection(con, ds);
	}

	/**
	 * @param userGroupDAO
	 *            the userGroupDAO to set
	 */
	public void setUserGroupDAO(UserGroupDAO userGroupDAO) {
		this.userGroupDAO = userGroupDAO;
	}

}
