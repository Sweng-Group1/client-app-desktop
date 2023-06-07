package sweng.group.one.client_app_desktop.sceneControl;

import static org.junit.Assert.assertEquals;

import javax.swing.JFrame;
import javax.swing.JPasswordField;

import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JButtonFixture;
import org.assertj.swing.fixture.JPanelFixture;
import org.assertj.swing.fixture.JTextComponentFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class LoginSceneTest {
	
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
		testFrame = new JFrame();  
		testFrame.setSize(800, 400);  
        testFrame.setVisible(true);    
        testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	@After
	public void tearDown() throws Exception {
		window.cleanUp();
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
