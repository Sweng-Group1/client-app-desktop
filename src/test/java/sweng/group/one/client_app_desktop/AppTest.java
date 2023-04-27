package sweng.group.one.client_app_desktop;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }
    
    // junit.framework doesn't contain @Test need to use @org.junit.Test
    // This isn't the method we were originally instructed to use i.e. 
    // importing junit.framework.Test. Is there an appropriate advantage
    // to having constructed the file in this manner?
    @org.junit.Test
    public void startUpWindowSize() {
    	
    }
}
