package sweng.group.one.client_app_desktop.sceneControl;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginScene extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//private static int WINDOW_X_SIZE = 200;
	//private static int WINDOW_Y_SIZE = 200;
	//private static Color textFieldPanelColor = Color.white;
	//private static Color buttonPanelColor = Color.white;
	
	//private JPanel textFieldPanel; 
	//private JPanel buttonPanel;
	
	private JPanel usernamePanel;
	private JPanel passwordPanel;
	private JLabel logoPanel;
	private BufferedImage logo;
	private JTextField usernameField;
	private JPasswordField passwordField;
	
	private JButton loginButton;
	private JButton createAccountButton;
	private JButton continueButton;
	private String username;
	private String password;
	private boolean isOpen;
	
	public LoginScene() throws IOException {
		this.setOpaque(false);
		this.setLayout(null);
		createUserNameInput();
		createPasswordField();
		createButtons();
		createLogo();
	}
	private void createUserNameInput() {
		usernamePanel= new JPanel() {
			public void paint(Graphics g) {
				g.setColor(new Color(160,203,213));
				//g.setColor(Color.white);
				g.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 30, 30);
				super.paint(g);
			}
		};
		usernameField= new JTextField();
		usernamePanel.add(usernameField);
		usernamePanel.setLayout(null);
		usernamePanel.setOpaque(false);
		usernameField.setOpaque(false);
		usernameField.setBackground(new Color(0,0,0,0));
		usernameField.setBorder(null);
		usernameField.setCaretColor(Color.white);
		usernameField.setSelectedTextColor(Color.white);
		usernameField.setDisabledTextColor(Color.white);
		usernameField.setForeground(Color.white);
		this.add(usernamePanel);
		usernameField.setName("usernameField");
		usernameField.setVisible(true);
	}
	private void createPasswordField() {
		passwordPanel= new JPanel() {
			public void paint(Graphics g) {
				g.setColor(new Color(160,203,213));
				//g.setColor(Color.white);
				g.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 35, 35);
				super.paint(g);
			}
		};
		passwordField= new JPasswordField();
		passwordPanel.add(passwordField);
		passwordPanel.setLayout(null);
		passwordPanel.setOpaque(false);
		passwordField.setOpaque(false);
		passwordField.setBackground(new Color(0,0,0,0));
		passwordField.setBorder(null);
		passwordField.setCaretColor(Color.white);
		passwordField.setSelectedTextColor(Color.white);
		passwordField.setDisabledTextColor(Color.white);
		passwordField.setForeground(Color.white);
		this.add(passwordPanel);
		passwordField.setName("usernameField");
		passwordField.setVisible(true);
	}
	private void createButtons() {
		loginButton= new JButton() {	
			public void paint(Graphics g) {
				g.setColor(new Color(160,203,213));
				g.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 30, 30);
				g.setColor(Color.white);
				g.drawString("Login",(this.getWidth()/2)-(g.getFontMetrics().
						stringWidth("Login")/2),(this.getHeight()/2)+(g.getFontMetrics().getHeight()/4));
				super.paint(g);
			}
		};	
		loginButton.setOpaque(false);
		loginButton.setBackground(new Color(0,0,0,0));
		loginButton.setBorderPainted(false);
		loginButton.setBorder(null);	
		this.add(loginButton);
		
		continueButton= new JButton(){	
			public void paint(Graphics g) {
				g.setColor(new Color(160,203,213));
				g.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 30, 30);
				g.setColor(Color.white);
				g.drawString("Continue",(this.getWidth()/2)-(g.getFontMetrics().
						stringWidth("Continue")/2),(this.getHeight()/2)+(g.getFontMetrics().getHeight()/4));
				super.paint(g);
			}
		};	
		continueButton.setOpaque(false);
		continueButton.setBackground(new Color(0,0,0,0));
		continueButton.setBorderPainted(false);
		continueButton.setBorder(null);	
		this.add(continueButton);
		
		createAccountButton= new JButton(){	
			public void paint(Graphics g) {
				g.setColor(new Color(160,203,213));
				//g.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 50, 50);
				//g.setColor(Color.white);
				g.drawString("Create Account",(this.getWidth()/2)-(g.getFontMetrics().
						stringWidth("Create Account")/2),(this.getHeight()/2)+(g.getFontMetrics().getHeight()/4));
				super.paint(g);
			}
		};	
		createAccountButton.setOpaque(false);
		createAccountButton.setBackground(new Color(0,0,0,0));
		createAccountButton.setBorderPainted(false);
		createAccountButton.setBorder(null);	
		this.add(createAccountButton);
		
		
	}
	private void createLogo() throws IOException {
		logoPanel= new JLabel();
		logo= ImageIO.read(new File("./Yusu Logo 14.png"));
		this.add(logoPanel);
	}
	public void setSize(int width, int height) {
		super.setSize(width, height);
		usernamePanel.setSize((width/6)*4, height/16);
		usernamePanel.setLocation((width-((width/6)*4))/2, (height/2)-(height/16));
		usernameField.setSize(usernamePanel.getWidth()-20, usernamePanel.getHeight());
		usernameField.setLocation(10, 0);
		
		passwordPanel.setSize((width/6)*4, height/16);
		passwordPanel.setLocation((width-((width/6)*4))/2, (height/2)+(height/16));
		passwordField.setSize(usernamePanel.getWidth()-20, usernamePanel.getHeight());
		passwordField.setLocation(10, 0);
		
		continueButton.setSize((passwordPanel.getWidth()/12)*5, passwordPanel.getHeight());
		continueButton.setLocation(passwordPanel.getX(), (height/2)+(3*(height/16)));
		
		loginButton.setSize((passwordPanel.getWidth()/12)*5, passwordPanel.getHeight());
		loginButton.setLocation(passwordPanel.getX()+(passwordPanel.getWidth()/12)*7, (height/2)+(3*(height/16)));
		
		createAccountButton.setSize((passwordPanel.getWidth()/12)*5, passwordPanel.getHeight());
		createAccountButton.setLocation(passwordPanel.getX(), (height/2)+(5*(height/16)));
	
		logoPanel.setIcon(new ImageIcon(logo.getScaledInstance( (height/2)-(height/16), (height/2)-(height/16), java.awt.Image.SCALE_SMOOTH)));
		logoPanel.setLocation( (width/2)-( ((height/2)-(height/16))/2),0);
		logoPanel.setSize(passwordPanel.getWidth(), (height/2)-(height/16));
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
	public void paint(Graphics g) {
		g.setColor(new Color(46,71,118));
		//g.setColor(Color.white);
		g.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 100, 100);
		super.paint(g);
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
