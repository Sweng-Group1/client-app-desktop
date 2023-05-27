package sweng.group.one.client_app_desktop.uiElements;

import static org.junit.Assert.*;

import org.junit.*;

/**
 * @author Will H 
 * Rudimentary test for this module that we use all over the place
 */

public class UploadSceneComponentTest {
	
	UploadSceneComponent usc;
	
	@Before
	/* Initialise upload scene component */
	public void setup() {
		usc = new UploadSceneComponent();
	}
	
	@Test
	/* This is basically a getter */
	public void resizeTest() {
		int DIM = 10;
		
		usc.setBounds(DIM, DIM, DIM, DIM);
		assertEquals(usc.getBounds().height, DIM);
		assertEquals(usc.getBounds().width, DIM);
		assertEquals(usc.getBounds().x, DIM);
		assertEquals(usc.getBounds().y, DIM);
	}
	
}
