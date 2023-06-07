package sweng.group.one.client_app_desktop.sceneControl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;

import org.assertj.swing.core.GenericTypeMatcher;
import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JButtonFixture;
import org.assertj.swing.fixture.JPanelFixture;
import org.assertj.swing.fixture.JTextComponentFixture;
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
	private JButtonFixture maximise;
	private JTextComponentFixture searchBar;
	private JButtonFixture searchButton;
	
	private String searchBarContent;
	
	private ActionListener searchAction = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			GuiActionRunner.execute(() -> searchBarContent = sidebar.getSearchText());
		}
	};
	
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
		sidebar = GuiActionRunner.execute(() -> new SidebarScene(searchAction));
		GuiActionRunner.execute(() -> frame.add(sidebar));
		window = new FrameFixture(frame);
		window.show(); // shows the frame to test
		
		sidebarFixture = window.panel("Sidebar");
		
		// Since maximise is invisible, we have to override the normal matchers
		maximise = window.button(new GenericTypeMatcher<JButton>(JButton.class) {
			  @Override
			  protected boolean isMatching(JButton button) {
			    return "Maximise".equals(button.getName());
			  }
			});
		
		minimise = window.button("Minimise");
		searchButton = window.button("Search");
		searchBar = window.textBox("Searchbar");
	}

	@After
	public void tearDown() throws Exception {
		window.cleanUp();
	}

	@Test 
	public void startOpened () {
		sidebarFixture.requireVisible();
		maximise.requireNotVisible();
		assertTrue(GuiActionRunner.execute(() -> sidebar.isOpen()));
	}
	// Tests if the minimise button works
	@Test
	public void minimise() {
		sidebarFixture.requireVisible();
		
		minimise.click();
		
		// AssertJ swing doesn't have methods to pull values out
		// But it does have methods to directly make assertions
		sidebarFixture.requireNotVisible();
		maximise.requireVisible();
		assertFalse(GuiActionRunner.execute(() -> sidebar.isOpen()));
	}
	
	@Test
	public void maximise() {
		GuiActionRunner.execute(() -> sidebar.close(100L));
		
		maximise.click();
		sidebarFixture.requireVisible();
		maximise.requireNotVisible();
		assertTrue(GuiActionRunner.execute(() -> sidebar.isOpen()));
	}
	
	// Tests if we can add presentations to the sidebar.
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
	
	// Checks if the searchAction is being passed correctly
	@Test 
	public void searchButton() {
		String str = "Hello World";
		searchBar.enterText(str);
		searchButton.click();
		assertEquals(searchBarContent, str);
		System.out.println(searchBarContent);
	}
	
	@Test 
	public void testTemp() {
		
	}
}






