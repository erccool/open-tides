package org.opentides.testsuite;

import org.hightides.HighTidesTest;
import org.hightides.annotations.util.NamingUtilTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.opentides.annotations.util.CodeUtilTest;
import org.opentides.persistence.UserDAOTest;
import org.opentides.persistence.UserGroupDAOTest;
import org.opentides.service.SystemCodesServiceTest;
import org.opentides.service.UserServiceTest;
import org.opentides.util.BlockSplitterTest;
import org.opentides.util.CrudUtilTest;
import org.opentides.util.DateUtilTest;
import org.opentides.util.StringUtilTest;


/**
 * Test suite that runs all unit tests. This can be useful when running code
 * coverage tool as the unit tests should ensure each line of the code being
 * tested is executed.
 * 
 * @author jcruz
 * 
 */
@RunWith(Suite.class)
@SuiteClasses( { CrudUtilTest.class, DateUtilTest.class, StringUtilTest.class,
		HighTidesTest.class, CodeUtilTest.class, NamingUtilTest.class,
		org.hightides.annotations.util.CodeUtilTest.class,
		BlockSplitterTest.class, UserDAOTest.class, UserGroupDAOTest.class,
		SystemCodesServiceTest.class, UserServiceTest.class })
// TODO: ugly declaration of test classes, find better options how to include
// JUnit 4 annotation-based testing to this test suite.
public class AllTests {
	
}
