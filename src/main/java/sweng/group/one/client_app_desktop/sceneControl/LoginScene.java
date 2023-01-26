package sweng.group.one.client_app_desktop.sceneControl;

import java.awt.Canvas;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class LoginScene extends JPanel {
	private static int WINDOW_X_SIZE = 600;
	private static int WINDOW_Y_SIZE = 800;
	private static Color backgroundColour = Color.cyan;

	
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JButton loginButton;
	private JButton continueButton;
	private JButton createAccountButton;
	
	private String username;
	private String password;
	
	public LoginScene() {
		setupGUI();
	}
	
	private void setupGUI() {
		JFrame frame = new JFrame();
		Canvas canvas = new Canvas();
		
		frame.setSize(WINDOW_X_SIZE, WINDOW_Y_SIZE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		canvas.setBackground(backgroundColour);
		frame.add(canvas);
	}

	private boolean login(String username, String password) {
		
		return false;
	}
	
	private boolean createAccount(String username, String password) {
		
		return false;
	}
	
}
