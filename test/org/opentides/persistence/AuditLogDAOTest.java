package org.opentides.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.GrantedAuthorityImpl;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.context.SecurityContextImpl;
import org.acegisecurity.providers.ProviderManager;
import org.acegisecurity.providers.TestingAuthenticationProvider;
import org.acegisecurity.providers.TestingAuthenticationToken;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.opentides.bean.SystemCodes;
import org.opentides.persistence.impl.AuditLogDAOImpl;
import org.opentides.testsuite.BaseTidesTest;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class AuditLogDAOTest extends BaseTidesTest {
	private static final Logger _log = Logger.getLogger(AuditLogDAOTest.class);
	private ProviderManager authenticationManager;
	
	public AuditLogDAOTest() {
		super();
		setAutowireMode(AUTOWIRE_BY_NAME);
	}
	
	private static final class SystemCodesExtractor implements ResultSetExtractor{
		public Object extractData(ResultSet rs) throws SQLException,
				DataAccessException {
			rs.next();
			SystemCodes sc = new SystemCodes();
			sc.setId(rs.getLong("id"));
			sc.setCategory(rs.getString("category_"));
			sc.setKey(rs.getString("key_"));
	        sc.setValue(rs.getString("value_"));
	        return sc;
		}
	}
			
	@Test
	public void testLogEvent(){
		int prevCount = jdbcTemplate.queryForInt("SELECT count(*) FROM HISTORY_LOG");
		
		jdbcTemplate.execute("INSERT INTO SYSTEM_CODES(CATEGORY_,KEY_,VALUE_) VALUES('OFFICE','HR','Human Resources')");
		SystemCodes sc = (SystemCodes) jdbcTemplate.query("SELECT * FROM SYSTEM_CODES WHERE VALUE_='Human Resources'", new SystemCodesExtractor());
		_log.debug("SC ID: "+sc.getId());
		
		AuditLogDAOImpl.logEvent("Sample log for testing.", sc);
		
		int currCount = jdbcTemplate.queryForInt("SELECT count(*) FROM HISTORY_LOG");
		assertEquals(prevCount+1, currCount);
	}
	
	private void setupSessionUser(){
		TestingAuthenticationToken token = new TestingAuthenticationToken("admin", "admin", 
				new GrantedAuthority[]{
					new GrantedAuthorityImpl("User"),
					new GrantedAuthorityImpl("Administrator")
			});
			
			List<TestingAuthenticationProvider> list = new ArrayList<TestingAuthenticationProvider>();
			list.add(new TestingAuthenticationProvider());
			authenticationManager.setProviders(list);
			
			SecurityContextImpl context = new SecurityContextImpl();
			context.setAuthentication(token);
			SecurityContextHolder.setContext(context);
	}
}
