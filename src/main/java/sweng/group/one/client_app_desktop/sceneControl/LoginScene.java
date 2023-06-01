package sweng.group.one.client_app_desktop.sceneControl;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import sweng.group.one.client_app_desktop.data.User;
import sweng.group.one.client_app_desktop.data.UserService;

/**
 * Modified JPanel that allows users to login or create an account using a chosen username and password
 * 
 * @author Srikanth Jakka & Luke George
 * @since 29/05/2023
 *
 */

public class LoginScene extends JPanel implements ComponentInterface{
	
	// -------------------------------------------------------------- //
	// --------------------- INITIALISATIONS ------------------------ //
	// -------------------------------------------------------------- //

	private static final long serialVersionUID = 1L;
	private boolean isOpen;
	
	private BufferedImage logo;
	private JButton continueButton;
	private JButton createAccountButton;
	private JButton loginButton;
	private JLabel logoPanel;
	private JPanel passwordPanel;
	private JPanel usernamePanel;
	private JPanel feedbackPanel;
	private JPasswordField passwordField;
	private JTextField usernameField;
	private JLabel feedbackMessage;
	
	private String accessToken;
	private String refreshToken;
	private UserService userService = new UserService();
	private User user = new User("Default");
	
	// -------------------------------------------------------------- //
	// ----------------------- CONSTRUCTOR -------------------------- //
	// -------------------------------------------------------------- //
	
	/**
	 * 
	 * @throws IOException
	 */
	public LoginScene() throws IOException {
		this.setOpaque(false);
		this.setLayout(null);
		createUserNameInput();
		createPasswordField();
		createFeedbackMessage();
		createButtons();
		createLogo();
		checkAccessToken();
		setMouseListeners();
	}
	
	/**
	 * Creates the username panel that allows the user to input their username, and adds it to LoginScene
	 */
	private void createUserNameInput() {
		usernamePanel = new JPanel() {
			public void paint(Graphics g) {
				g.setColor(colorLight);
				g.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), curvatureRadius, curvatureRadius);
				super.paint(g);
			}
		};
		usernameField = new JTextField();
		
		usernamePanel.add(usernameField);
		usernamePanel.setLayout(null);
		usernamePanel.setOpaque(false);
		
		usernameField.setOpaque(false);
		usernameField.setBackground(transparent);
		usernameField.setBorder(null);
		usernameField.setCaretColor(Color.white);
		usernameField.setSelectedTextColor(Color.white);
		usernameField.setDisabledTextColor(Color.white);
		usernameField.setForeground(Color.white);
		this.add(usernamePanel);
		
		usernameField.setName("usernameField");
		usernameField.setVisible(true);
	}
	
	/**
	 * Creates the password panel that allows the user to input their password, and adds it to LoginScene
	 */
	private void createPasswordField() {
		passwordPanel = new JPanel() {
			public void paint(Graphics g) {
				g.setColor(colorLight);
				g.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), curvatureRadius, curvatureRadius);
				super.paint(g);
			}
		};
		passwordField = new JPasswordField();
		
		passwordPanel.add(passwordField);
		passwordPanel.setLayout(null);
		passwordPanel.setOpaque(false);
		
		passwordField.setOpaque(false);
		passwordField.setBackground(transparent);
		passwordField.setBorder(null);
		passwordField.setCaretColor(Color.white);
		passwordField.setSelectedTextColor(Color.white);
		passwordField.setDisabledTextColor(Color.white);
		passwordField.setForeground(Color.white);
		this.add(passwordPanel);
		
		passwordField.setName("passwordField");
		passwordField.setVisible(true);
	}
	
	private void createFeedbackMessage() {
		feedbackPanel = new JPanel() {
			public void paint(Graphics g) {
				g.setColor(colorLight);
				g.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), curvatureRadius, curvatureRadius);
				super.paint(g);
			}
		};
		feedbackMessage = new JLabel();
		
		feedbackPanel.add(feedbackMessage);
		feedbackPanel.setLayout(null);
		feedbackPanel.setOpaque(false);
		
		feedbackMessage.setOpaque(false);
		feedbackMessage.setBackground(transparent);
		feedbackMessage.setBorder(null);
		feedbackMessage.setForeground(Color.white);
		this.add(feedbackPanel);
		
		feedbackMessage.setName("feedbackMessage");
		feedbackMessage.setVisible(false);
	}
	
	/**
	 * Creates the buttons that the user interacts with to submit their information, and adds them to LoginScene
	 */
	private void createButtons() {
		loginButton = new JButton() {	
			public void paint(Graphics g) {
				g.setColor(colorLight);
				g.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), curvatureRadius, curvatureRadius);
				g.setColor(Color.white);
				g.drawString("Login",(this.getWidth()/2)-(g.getFontMetrics().
						stringWidth("Login")/2),(this.getHeight()/2)+(g.getFontMetrics().getHeight()/4));
				super.paint(g);
			}
		};	
		loginButton.setOpaque(false);
		loginButton.setBackground(transparent);
		loginButton.setBorderPainted(false);
		loginButton.setBorder(null);	
		this.add(loginButton);
		
		continueButton = new JButton(){	
			public void paint(Graphics g) {
				g.setColor(colorLight);
				g.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), curvatureRadius, curvatureRadius);
				g.setColor(Color.white);
				g.drawString("Continue",(this.getWidth()/2)-(g.getFontMetrics().
						stringWidth("Continue")/2),(this.getHeight()/2)+(g.getFontMetrics().getHeight()/4));
				super.paint(g);
			}
		};	
		continueButton.setOpaque(false);
		continueButton.setBackground(transparent);
		continueButton.setBorderPainted(false);
		continueButton.setBorder(null);	
		this.add(continueButton);
		
		createAccountButton = new JButton(){	
			public void paint(Graphics g) {
				g.setColor(colorLight);
				g.drawString("Create Account",(this.getWidth()/2)-(g.getFontMetrics().
						stringWidth("Create Account")/2),(this.getHeight()/2)+(g.getFontMetrics().getHeight()/4));
				super.paint(g);
			}
		};	
		createAccountButton.setOpaque(false);
		createAccountButton.setBackground(transparent);
		createAccountButton.setBorderPainted(false);
		createAccountButton.setBorder(null);	
		this.add(createAccountButton);
	}
	
	/**
	 * Adds the YUSU Logo to LoginScene
	 * @throws IOException
	 */
	private void createLogo() throws IOException {
		logoPanel = new JLabel();
		logo = ImageIO.read(new File("./assets/Yusu Logo 14.png"));
		this.add(logoPanel);
	}
	
	/**
	 * Sets the size of LoginScene and scales its components
	 */
	public void setSize(int width, int height) {
		super.setSize(width, height);
		
		usernamePanel.setSize((width/6)*4, height/16);
		usernamePanel.setLocation((width-((width/6)*4))/2, (height/2)-(height/16));
		usernameField.setSize(usernamePanel.getWidth()-20, usernamePanel.getHeight());
		usernameField.setLocation(10, 0);
		
		passwordPanel.setSize((width/6)*4, height/16);
		passwordPanel.setLocation((width-((width/6)*4))/2, (height/2)+(height/16));
		passwordField.setSize(passwordPanel.getWidth()-20, passwordPanel.getHeight());
		passwordField.setLocation(curvatureRadius/2, 0);
		
		feedbackPanel.setSize((width/6)*4, height/16);
		feedbackPanel.setLocation((width-((width/6)*4))/2, (height/2)+(6*height/16));
		feedbackMessage.setSize(feedbackPanel.getWidth()-20, feedbackPanel.getHeight());
		feedbackMessage.setLocation(curvatureRadius/2, 0);
		
		continueButton.setSize((passwordPanel.getWidth()/12)*5, passwordPanel.getHeight());
		continueButton.setLocation(passwordPanel.getX(), (height/2)+(3*(height/16)));
		
		loginButton.setSize((passwordPanel.getWidth()/12)*5, passwordPanel.getHeight());
		loginButton.setLocation(passwordPanel.getX()+(passwordPanel.getWidth()/12)*7, (height/2)+(3*(height/16)));
		
		createAccountButton.setSize((passwordPanel.getWidth()/12)*5, passwordPanel.getHeight());
		createAccountButton.setLocation(passwordPanel.getX(), (height/2)+(5*(height/16)));
	
		logoPanel.setIcon(new ImageIcon(logo.getScaledInstance((height/2)-(height/16), (height/2)-(height/16), java.awt.Image.SCALE_SMOOTH)));
		logoPanel.setLocation((width/2)-( ((height/2)-(height/16))/2),0);
		logoPanel.setSize(passwordPanel.getWidth(), (height/2)-(height/16));
	}
	
	public boolean loginButtonPressed() {		
		if (usernameField.getText().equals("") || passwordField.getPassword().length==0) {
			feedbackMessage.setText("Please enter your login credentials");
			feedbackMessage.setVisible(true);
		}
		
		if (checkAccessToken()==true) {
			feedbackMessage.setText("You are already logged in!");
			feedbackMessage.setVisible(true);
			return true;
		}
		else {
			user = new User(usernameField.getText());
			String password = String.valueOf(passwordField.getPassword());
			System.out.println(userService.login(user, password));
			if (userService.login(user, password)==403) {
				feedbackMessage.setText("Invalid login credentials");
				feedbackMessage.setVisible(true);
				passwordField.setText("");
				return false;
			} else {
				System.out.println("User successfully logged in");
				changeScene();
				return true;
			}
		}
	}
	
	public boolean checkAccessToken() {
		if (userService.refreshAccessToken(user)==200) {
			System.out.println("User has a valid access token");
			return true;
		}
		else {
			System.out.println("User does not have a valid access token");
			return false;
		}
	}
	
	public void changeScene() {
		isOpen = false;
		this.setVisible(false);
	}
	
	public String getUsername() {
		return usernameField.getText();
	}
	
	private void setMouseListeners() {
		loginButton.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				loginButtonPressed();
				System.out.println("LoginScene");
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		this.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
//				if (e.getX()>100) {
//					changeScene();
//				}
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	public void createAccount() {
		usernameField.setText("The Create Account Button Works");
		System.out.println("The Create Account Button Works");
	}
	
	public boolean isOpen() {
		return isOpen;
	}
	
	public void paint(Graphics g) {
		g.setColor(colorDark);
		g.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), curvatureRadius, curvatureRadius);
		super.paint(g);
	}
	
	public JButton getLoginButton() {
		return loginButton;
	}
	
	public JButton getContinueButton() {
		return continueButton;
	}
	
}

