package sweng.group.one.client_app_desktop.uiElements;

import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;

import org.assertj.swing.core.GenericTypeMatcher;
import org.assertj.swing.core.MouseButton;
import org.assertj.swing.core.Robot;
import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JButtonFixture;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sweng.group.one.client_app_desktop.sceneControl.SidebarScene;

/** 
 * Test for Circular Button class
 * 
 * AssertJ Swing has a few limitations here that make testing things
 * like the paint method awkward (how do I test if something looks right?)
 * but I've tested what I can.
 * 
 * @author Will
 * @since 29/05/23
 */
public class CircularButtonTest {
	
	// -------------------------------------------------------------- //
	// --------------------- INITIALISATIONS ------------------------ //
	// -------------------------------------------------------------- //
	
	private FrameFixture window;
    private CircularButton cb;
	private JButtonFixture cbFix;
	private Robot robot;
	private Boolean clicked;
	
	// -------------------------------------------------------------- //
	// -------------------------- SETUP ----------------------------- //
	// -------------------------------------------------------------- //
	
	@BeforeClass
	/**
	 * Causes tests to fail if they aren't executing on the EDT
     * They'll probably fail anyways, but this will throw an 
     * EdtViolationException so you know WHY its failing.
	 */
	public static void setUpOnce() {
		FailOnThreadViolationRepaintManager.install();
	}
	
	/**
	 *  Set up our test window. This needs to be executed on the EDT, 
	 *  hence the ugly single use function. More readable than a half
	 *  dozen GUIActionRunner statements.
	 *  
	 *  @returns JFrame with a circular button, named "Button". 
	 */
	private JFrame testWindow() throws Exception {
		JFrame f = new JFrame();
		cb = new CircularButton();
		cb.setName("Button"); // Simplifies creating a fixture for this.
		cb.setVisible(true);
		f.add(cb);
		return f;
	}
	
	@Before
	/**
	 * Creates AssertJ Swing fixtures to let us test the function of 
	 * the button.
	 */
	public void setUp() throws Exception {
		JFrame frame = GuiActionRunner.execute(() -> testWindow());
		window = new FrameFixture(frame);
		window.show();
		robot = window.robot();
		cbFix = window.button("Button");
	}
	
	// -------------------------------------------------------------- //
	// -------------------------- TESTS ----------------------------- //
	// -------------------------------------------------------------- //
	
	@Test
	/**
	 * Makes sure the colour changes when moused over and pressed.
	 */
	public void colorChanges() {
		GuiActionRunner.execute(() -> cb.setMainBackgroundColour(Color.RED, Color.GREEN, Color.BLUE));
		cbFix.background().requireEqualTo(Color.RED);
		robot.moveMouse(cb);
		cbFix.background().requireEqualTo(Color.GREEN);
		robot.pressMouse(MouseButton.LEFT_BUTTON);
		cbFix.background().requireEqualTo(Color.BLUE);
	}
	
	@Test
	/**
	 * Makes sure the button functions as a button.
	 * Sets up an action listener, clicks the button, makes sure the listener ran.
	 */
	public void clickAction() {
		clicked = false;
		
		GuiActionRunner.execute(() -> cb.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        clicked = true;
		    }
		}));
		
		cbFix.click();
		
		assertTrue(clicked);
	}
	
	@Test
	/**
	 * Does this throw any weird exceptions when theres an image?
	 * CircularButton draws things directly, so I cant easily test 
	 * whats going on here. But I can make sure it isnt broken.
	 */
	public void drawsWithImage() throws InterruptedException {
		GuiActionRunner.execute(() -> cb.setIconImage(ImageIO.read(new File("./assets/user.png"))));
		Thread.sleep(1000);
	}
	
	@Test
	/**
	 * Again, another simple sanity check. Does the border drawing code
	 * throw exceptions? 
	 */
	public void drawsWithBorder() throws InterruptedException {
		GuiActionRunner.execute(() -> cb.setBorder(Color.BLUE, 100));
		Thread.sleep(1000);
	}
	
	// -------------------------------------------------------------- //
	// ------------------------ TEARDOWN ---------------------------- //
	// -------------------------------------------------------------- //
	
	@After
	public void tearDown() {
		window.close();
		window.cleanUp();
	}
}
