package sweng.group.one.client_app_desktop.sceneControl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JButtonFixture;
import org.assertj.swing.fixture.JPanelFixture;
import org.assertj.swing.fixture.JTextComponentFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sweng.group.one.client_app_desktop.presentation.Slide;

public class LoginSceneTest {
	
	private Slide testSlide;
	private JFrame testFrame;
	
	private FrameFixture window;
	private LoginScene loginScene;
	private JPanelFixture loginSceneFixture;
	private JTextComponentFixture usernameField;
	private JPasswordField passwordField;
	private JButtonFixture loginButton;
	
	private String usernameFieldContent;
	
	@Before
	public void setUp() {
//		JFrame frame = GuiActionRunner.execute(() -> new JFrame());
//		loginScene = GuiActionRunner.execute(() -> new LoginScene());
//		GuiActionRunner.execute(() -> frame.add(loginScene));
//		window = new FrameFixture(frame);
//		window.show(); // shows the frame to test
//		
//		loginSceneFixture = window.panel("Sidebar");
//		
//		usernameField = window.textBox("Username");
		
		testFrame = new JFrame();  
		testFrame.setSize(800, 400);  
        testFrame.setVisible(true);    
        testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        testSlide = new Slide(800, 400);
	}
	
	@After
	public void tearDown() throws Exception {
		window.cleanUp();
	}

	@Test 
	public void startOpened () {
		loginSceneFixture.requireVisible();
		assertTrue(GuiActionRunner.execute(() -> loginScene.isOpen()));
	}
	
	@Test
	public void loginButtonTest() {
		String usernameTest = "Username";
		String passwordTest = "Password";
		
		usernameField.enterText(usernameTest);
		passwordField.setText(passwordTest);
		
		loginButton.click();
		
		assertEquals(usernameFieldContent, usernameTest);
		System.out.println(usernameFieldContent);
	}
}
