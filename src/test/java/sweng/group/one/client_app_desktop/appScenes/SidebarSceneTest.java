package sweng.group.one.client_app_desktop.appScenes;

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

public class SidebarSceneTest {

	private FrameFixture window;
	
	private JPanelFixture sidebar;
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
		App frame = GuiActionRunner.execute(() -> new App());
		window = new FrameFixture(frame);
		window.show(); // shows the frame to test
		
		// The easiest way to lookup components is by name.
		// You can assign names to components using *.setName()
		// Annoyingly you can't lookup hidden components this way
		// so we have to do it all first.
		
		sidebar = window.panel("Sidebar");
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
		sidebar.requireNotVisible();
	}

}
