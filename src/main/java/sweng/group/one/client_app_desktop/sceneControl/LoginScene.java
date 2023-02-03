package sweng.group.one.client_app_desktop.sceneControl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginScene extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static int WINDOW_X_SIZE = 200;
	private static int WINDOW_Y_SIZE = 200;
	private static Color textFieldPanelColor = Color.white;
	private static Color buttonPanelColor = Color.white;
	
	private JPanel textFieldPanel; 
	private JPanel buttonPanel;
	
	private JTextField usernameField;
	private JPasswordField passwordField;
	
	private JButton loginButton;
	private JButton createAccountButton;
	
	private String username;
	private String password;
	private boolean isOpen;
	
	public LoginScene() {
		setupGUI();
	}
	
	private void setupGUI() {
		textFieldPanel = new JPanel(new GridLayout(2,1,10,10));
		buttonPanel = new JPanel(new FlowLayout());
		
		usernameField = new JTextField();
		passwordField = new JPasswordField();
		
		loginButton = new JButton("Login");
		createAccountButton = new JButton("Create Account");
		//continueButton = new JButton("Continue");
		
		textFieldPanel.setName("textFieldPanel");
		buttonPanel.setName("buttonPanel");
		
		usernameField.setName("usernameField");
		passwordField.setName("passwordField");
		
		loginButton.setName("loginButton");
		createAccountButton.setName("createAccountButton");
		//continueButton.setName("continueButton");
		
		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				login();
			}
		});
		
		createAccountButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				createAccount();
			}
		});
		
		textFieldPanel.add(usernameField);
		textFieldPanel.add(passwordField);
		
		buttonPanel.add(loginButton, BorderLayout.EAST);
		buttonPanel.add(createAccountButton, BorderLayout.WEST);
		//textFieldPanel.add(continueButton, BorderLayout.SOUTH);
		
		textFieldPanel.setBackground(textFieldPanelColor);
		buttonPanel.setBackground(buttonPanelColor);
		
		this.setLayout(new BorderLayout());
		this.add(textFieldPanel, BorderLayout.NORTH);
		this.add(buttonPanel, BorderLayout.SOUTH);
		this.setName("LoginScene");
		this.setPreferredSize(new Dimension(WINDOW_X_SIZE,WINDOW_Y_SIZE));
		this.setVisible(true);
	}

	public void login() {
		usernameField.setText("The Login Button Works");
		System.out.println("The Login Button Works");
	}
	
	public void createAccount() {
		usernameField.setText("The Create Account Button Works");
		System.out.println("The Create Account Button Works");
	}
	
	public boolean isOpen() {
		return isOpen;
	}
	
//	private boolean login(String username, String password) {
//		
//		return false;
//	}
//	
//	private boolean createAccount(String username, String password) {
//		
//		return false;
//	}
//	
}
