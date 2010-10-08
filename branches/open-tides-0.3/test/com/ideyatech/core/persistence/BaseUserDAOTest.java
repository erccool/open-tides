package com.ideyatech.core.persistence;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

import javax.sql.DataSource;

import org.opentides.bean.user.BaseUser;
import org.opentides.bean.user.UserGroup;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.opentides.persistence.UserDAO;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.test.jpa.AbstractJpaTests;



public class BaseUserDAOTest extends AbstractJpaTests {
	private final static String BASE_USER = "/test/xml/BASE_USER.xml";

	private UserDAO coreUserDAO;

	@Override
	protected String[] getConfigLocations() {
		return new String[] {
				"file:WebContent/WEB-INF/tides/tidesContext*.xml",
				"file:WebContent/WEB-INF/tides/testConfig.xml" };
	}

	@Override
	protected void onSetUpInTransaction() throws Exception {
		super.onSetUpInTransaction();
		executeDatabaseOperation(DatabaseOperation.CLEAN_INSERT, BASE_USER);
	}

	@Override
	protected void onTearDown() throws Exception {
		super.onTearDown();
		executeDatabaseOperation(DatabaseOperation.DELETE_ALL, BASE_USER);
	}

	public void testAdd() {
		UserGroup group = new UserGroup();
		group.setId(981L);
		group.setName("usergroupxml1");
		group.setDescription("xml");

		BaseUser user = new BaseUser();
		user.addGroup(group);
		coreUserDAO.saveEntityModel(user);

		assertNotNull(user.getId());
		BaseUser testUser = coreUserDAO.loadEntityModel(user.getId());
		Set<UserGroup> groups = testUser.getGroups();
		assertNotNull(groups);
		assertTrue(groups.size() == 1);
		assertTrue(groups.iterator().next().getId().equals(group.getId()));

		testUser.removeGroup(group);
		coreUserDAO.saveEntityModel(testUser);

		assertEquals(testUser.getId(), group.getId());
		BaseUser testUser2 = coreUserDAO.loadEntityModel(testUser.getId());
		assertTrue(testUser2.getGroups().size() == 0);

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
	 * @param coreUserDAO
	 *            the coreUserDAO to set
	 */
	public void setCoreUserDAO(UserDAO coreUserDAO) {
		this.coreUserDAO = coreUserDAO;
	}
}
