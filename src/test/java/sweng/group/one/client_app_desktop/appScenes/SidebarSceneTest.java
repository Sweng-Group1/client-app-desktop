package sweng.group.one.client_app_desktop.appScenes;

import java.util.ArrayList;
import java.util.List;

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
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

import sweng.group.one.client_app_desktop.presentation.Presentation;

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
		// Since we can only interact with swing elements in the EDT, we
		// have to use GuiActionRunner to interact with JFrames and JPanels
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
	
	// Tests if we can add presentations to the sidebar.
	// This should, by rights, be parametrised but there are dependency issues with JUnit 4.
	@Test
	public void AddPres() {
		int size = 5;
		List<Presentation> toAdd = new ArrayList<Presentation>();
		
		// Generate a list of blank presentations
		for (int i = 0; i < size; i++) {
			Presentation p = GuiActionRunner.execute(() -> new Presentation());
			String name = Integer.toString(i);
			
			GuiActionRunner.execute(() -> p.setName(name));
			toAdd.add(p);
		}
		
		GuiActionRunner.execute(() -> sidebar.replacePres(toAdd));
		
		for (int i = 0; i < size; i++) {
			JPanelFixture presFix = sidebarFixture.panel(Integer.toString(i));
			presFix.requireVisible();
		}
	}
}






