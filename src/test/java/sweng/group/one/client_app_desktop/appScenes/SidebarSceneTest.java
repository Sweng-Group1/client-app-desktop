package sweng.group.one.client_app_desktop.appScenes;

import javax.swing.JFrame;

import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JButtonFixture;
import org.assertj.swing.fixture.JPanelFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sweng.group.one.client_app_desktop.App;

public class SidebarSceneTest  {

	// Handles for simulating interaction with various elements
	
	private FrameFixture window;
	private SidebarScene sidebar;
	private JPanelFixture sidebarFixture;
	private JButtonFixture minimise;
	
	@BeforeClass
	public static void setUpOnce() {
		// Causes tests to fail if they aren't executing on the EDT
		// They'll probably fail anyways, but this will throw an 
		// EdtViolationException so you know WHY its failing.
		FailOnThreadViolationRepaintManager.install();
	}
	
	@Before
	public void setUp() throws Exception {
		JFrame frame = GuiActionRunner.execute(() -> new JFrame());
		sidebar = GuiActionRunner.execute(() -> new SidebarScene());
		GuiActionRunner.execute(() -> frame.add(sidebar));
		window = new FrameFixture(frame);
		window.show(); // shows the frame to test
		
		sidebarFixture = new JPanelFixture(window.robot(), sidebar);
		minimise = window.button("Minimise");
	}

	@After
	public void tearDown() throws Exception {
		window.cleanUp();
	}

	// Tests if the minimise button works
	@Test
	public void minimise() {
		minimise.click();
		
		// AssertJ swing doesn't have methods to pull values out
		// But it does have methods to directly make assertions
		sidebarFixture.requireNotVisible();
	}
}
