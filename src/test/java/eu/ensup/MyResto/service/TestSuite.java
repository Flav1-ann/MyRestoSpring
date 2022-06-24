package eu.ensup.MyResto.service;

//import org.junit.platform.RunWith;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.SuiteDisplayName;
import org.junit.platform.suite.api.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.junit.runners.Suite.SuiteClasses;

//@RunWith(Suite.class)
//@SuiteClasses({AuthServiceTest.class})
@Suite
@SuiteDisplayName("A demo Test Suite by cherif")
@SelectClasses({AuthServiceTest.class,OpinionsServiceTest.class, OrderServiceTest.class,ProductServiceTest.class})
public class TestSuite {
}
