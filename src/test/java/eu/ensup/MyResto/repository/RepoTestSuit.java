package eu.ensup.MyResto.repository;

//import org.junit.platform.RunWith;
import eu.ensup.MyResto.service.AuthServiceTest;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.SuiteDisplayName;
import org.junit.platform.suite.api.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.junit.runners.Suite.SuiteClasses;

//@RunWith(Suite.class)
//@SuiteClasses({AuthServiceTest.class})
@Suite
@SuiteDisplayName("A demo Test Suite by cherif")
@SelectClasses({AuthServiceTest.class, OpinionsRepositoryTest.class, OrdersRepositoryTest.class, ProductRepositoryTest.class})
public class RepoTestSuit {
}
