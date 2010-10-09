/**
 * 
 */
package org.opentides.persistence.evolve;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.opentides.InvalidImplementationException;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.jpa.AbstractJpaTests;

/**
 * @author allantan
 *
 */
public class DBEvolveManagerTest extends AbstractJpaTests {

    private DBEvolveManager manager;
    private List<DBEvolve> testList;
    
    /**
     * 
     */
    public DBEvolveManagerTest() {
        super();
        setAutowireMode(AUTOWIRE_BY_NAME);
    }
    
    /* (non-Javadoc)
     * @see org.springframework.test.AbstractSingleSpringContextTests#getConfigLocations()
     */
    @Override
    protected String[] getConfigLocations() {
        return new String[] { 
                "file:WebContent/WEB-INF/tides/tidesContext*.xml",
                "file:WebContent/WEB-INF/tides/testConfig.xml" 
              };
    }
    
    @Override
    @BeforeTransaction
    protected void onSetUpInTransaction() throws Exception {
        super.onSetUpInTransaction();
        manager = (DBEvolveManager) applicationContext.getBean("evolveManager");
        testList = new ArrayList<DBEvolve>();
        testList.add(new TestEvolve(1));
        testList.add(new TestEvolve(2));
        testList.add(new TestEvolve(3));
        manager.setEvolveList(testList);
    }

    /**
     * Test method for {@link org.opentides.persistence.evolve.DBEvolveManager#evolve()}.
     */
    @Test
    public void testEvolveFromEmpty() {
       // delete the version
       jdbcTemplate.execute("DELETE FROM SYSTEM_CODES where KEY_='DB_VERSION'");
       // check for version 1
       manager.evolve();
       long dbVersion = jdbcTemplate.queryForLong("SELECT NUMBER_VALUE FROM SYSTEM_CODES WHERE KEY_='DB_VERSION'");
       assertEquals(3, dbVersion);
       for (DBEvolve evolve:testList) {
           assertTrue(((TestEvolve)evolve).isExecuted());
       }
       // execute another evolve, no change expected.
       manager.evolve();
       dbVersion = jdbcTemplate.queryForLong("SELECT NUMBER_VALUE FROM SYSTEM_CODES WHERE KEY_='DB_VERSION'");
       assertEquals(3, dbVersion);
    }
    
    /**
     * Test method for {@link org.opentides.persistence.evolve.DBEvolveManager#evolve()}.
     */
    @Test
    public void testEvolveDuplicateException() {
       // delete the version
       jdbcTemplate.execute("DELETE FROM SYSTEM_CODES where KEY_='DB_VERSION'");
       // add duplicate evolve version
       testList.add(new TestEvolve(3));
       try {
           manager.evolve();
           fail("No exception thrown on duplicate version.");
       } catch (InvalidImplementationException e) {
           // exception thrown... correct behavior
       }
    }

}
