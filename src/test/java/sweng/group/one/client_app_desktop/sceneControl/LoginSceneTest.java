package sweng.group.one.client_app_desktop.sceneControl;

import static org.assertj.core.api.Assertions.*;

import org.junit.*;
import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JButtonFixture;
import org.assertj.swing.fixture.JPanelFixture;

import sweng.group.one.client_app_desktop.App;

public class LoginSceneTest {
	
	private FrameFixture window;
	private JPanelFixture loginScene;
	private JButtonFixture loginButton;
	private JButtonFixture createAccountButton;
	
	@BeforeClass
	public static void setUpOnce() {
		FailOnThreadViolationRepaintManager.install();
	}
	
	@Before
	public void setUp() throws Exception {
		App frame = GuiActionRunner.execute(() -> new App());
		window = new FrameFixture(frame);
		window.show();
		
		loginScene = window.panel("LoginScene");
		loginButton = window.button("loginButton");
		createAccountButton = window.button("createAccountButton");
	}
	
	@After
	public void tearDown() throws Exception {
		window.cleanUp();
	}
	
	@Test
	public void loginButton() {
		loginButton.click();
		//loginScene.requireNotVisible();
	}
	
	@Test
	public void createAccountButton() {
		createAccountButton.click();
		//loginScene.requireNotVisible();
	}
	
}