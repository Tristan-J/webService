package webService;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ JpaHandleTest.class, WebServiceTest.class })
public class AllTests {

}
