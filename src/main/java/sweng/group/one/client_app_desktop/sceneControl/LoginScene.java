package sweng.group.one.client_app_desktop.sceneControl;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import sweng.group.one.client_app_desktop.data.User;
import sweng.group.one.client_app_desktop.data.UserService;
import sweng.group.one.client_app_desktop.uiElements.RoundedButton;

/**
 * Modified JPanel that allows users to login or create an account using a chosen username and password
 * 
 * @author Srikanth Jakka & Luke George
 * @since 04/06/2023
 *
 */

public class LoginScene extends JPanel implements ComponentInterface, LayoutManager{
	
	// -------------------------------------------------------------- //
	// --------------------- INITIALISATIONS ------------------------ //
	// -------------------------------------------------------------- //

	private static final long serialVersionUID = 1L;
	private boolean isOpen;
	
	private BufferedImage logo;
	private RoundedButton continueButton;
	private RoundedButton createAccountButton;
	private RoundedButton loginButton;
	private JPanel logoPanel;
	private JPanel passwordPanel;
	private JPanel usernamePanel;
	private JPanel feedbackPanel;
	private JPasswordField passwordField;
	private JTextField usernameField;
	private JLabel feedbackLabel;
	
	private String accessToken;
	private String refreshToken;
	private UserService userService = new UserService();
	private User user = new User("Default");
	
	public boolean userLoggedIn = false;
	
	// -------------------------------------------------------------- //
	// ----------------------- CONSTRUCTOR -------------------------- //
	// -------------------------------------------------------------- //
	
	/**
	 * 
	 * @throws IOException
	 */
	public LoginScene() throws IOException {
		this.setOpaque(false);
		createUserNameInput();
		createPasswordField();
		createFeedbackMessage();
		createButtons();
		createLogo();
		checkAccessToken();
		
		this.setLayout(this);
	}
	
	private JPanel createTextPanel(JTextField textField) {
		JPanel panel = new JPanel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -343751050506935580L;

			public void paint(Graphics g) {
				Graphics2D g2 = (Graphics2D)g;
				RenderingHints qualityHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				qualityHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
				g2.setRenderingHints(qualityHints);
				g2.setColor(colorLight);
				g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), curvatureRadius/2, curvatureRadius/2);
				super.paint(g);
			}
		};
		
		panel.add(usernameField);
		panel.setLayout(null);
		panel.setOpaque(false);
		
		textField.setOpaque(false);
		textField.setBackground(transparent);
		textField.setBorder(null);
		textField.setCaretColor(Color.white);
		textField.setSelectedTextColor(Color.white);
		textField.setDisabledTextColor(Color.white);
		textField.setForeground(Color.white);
		this.add(panel);
		
		textField.setVisible(true);
		
		return panel;
	}
	
	/**
	 * Creates the username panel that allows the user to input their username, and adds it to LoginScene
	 */
	private void createUserNameInput() {
		usernamePanel = new JPanel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -343751050506935580L;

			public void paint(Graphics g) {
				Graphics2D g2 = (Graphics2D)g;
				g2.setColor(colorLight);
				RenderingHints qualityHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				qualityHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
				g2.setRenderingHints(qualityHints);
				g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), curvatureRadius/2, curvatureRadius/2);
				if(usernameField.getText().isBlank()) {
					String str = "Username";
					g2.setColor(colorDark);
					g2.drawString(str, curvatureRadius/2,(this.getHeight()/2)+(g.getFontMetrics().getHeight()/4));
				}
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
			
			private static final long serialVersionUID = -979465226903459677L;

			public void paint(Graphics g) {
				Graphics2D g2 = (Graphics2D)g;
				g2.setColor(colorLight);
				RenderingHints qualityHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				qualityHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
				g2.setRenderingHints(qualityHints);
				g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), curvatureRadius/2, curvatureRadius/2);
				if(passwordField.getPassword().length == 0) {
					String str = "Password";
					g2.setColor(colorDark);
					g2.drawString(str, curvatureRadius/2,(this.getHeight()/2)+(g.getFontMetrics().getHeight()/4));
				}
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
			/**
			 * 
			 */
			private static final long serialVersionUID = 4422159517991702385L;

			public void paint(Graphics g) {
				Graphics2D g2 = (Graphics2D)g;
				super.paint(g);
			}
		};
		feedbackLabel = new JLabel();
		
		feedbackPanel.add(feedbackLabel);
		feedbackPanel.setLayout(null);
		feedbackPanel.setOpaque(false);
		
		feedbackLabel.setForeground(Color.white);
		this.add(feedbackPanel);
		
		feedbackLabel.setName("feedbackMessage");
		feedbackLabel.setVisible(false);
	}
	
	/**
	 * Creates the buttons that the user interacts with to submit their information, and adds them to LoginScene
	 */
	private void createButtons() {
		loginButton = new RoundedButton(null, curvatureRadius, colorLight, colorDark, Color.gray) {

			private static final long serialVersionUID = -4619941565184679039L;

			@Override
			public void paint(Graphics g) {
				Graphics g2 = g.create();
				super.paint(g2);
				String str = "Log In";
				g.setColor(Color.white);
				g.drawString(str,(this.getWidth()/2)-(g.getFontMetrics().stringWidth(str)/2),(this.getHeight()/2)+(g.getFontMetrics().getHeight()/4));
				g.dispose();
			}
			
			@Override
			public void buttonPressed() {
				loginButtonPressed();
			}
		};	
		this.add(loginButton);
		
		continueButton = new RoundedButton(null, curvatureRadius, colorLight, colorDark, Color.gray) {
			
			private static final long serialVersionUID = 4841957375392333030L;

			@Override
			public void paint(Graphics g) {
				Graphics g2 = g.create();
				super.paint(g2);
				String str = "Continue";
				g.setColor(Color.white);
				g.drawString(str,(this.getWidth()/2)-(g.getFontMetrics().stringWidth(str)/2),(this.getHeight()/2)+(g.getFontMetrics().getHeight()/4));
				g.dispose();
			}
		};	
		this.add(continueButton);
		
		createAccountButton = new RoundedButton(null, curvatureRadius, transparent, transparent, Color.gray) {

			private static final long serialVersionUID = 4840455254538045312L;

			@Override
			public void paint(Graphics g) {
				Graphics g2 = g.create();
				super.paint(g2);
				String str = "Create Account";
				g.setColor(colorLight);
				g.drawString(str,(this.getWidth()-g.getFontMetrics().stringWidth(str))/2,(this.getHeight()/2)+(g.getFontMetrics().getHeight()/4));
				g.dispose();
			}
		};	
		this.add(createAccountButton);
	}
	
	/**
	 * Adds the YUSU Logo to LoginScene
	 * @throws IOException
	 */
	private void createLogo() throws IOException {
		logo = ImageIO.read(new File("./assets/Yusu Logo 14.png"));
		logoPanel = new JPanel() {

			private static final long serialVersionUID = 6961538361558408979L;

			@Override
			public void paint(Graphics g) {
				Graphics2D g2 = (Graphics2D)g;
				int imageSize = Math.min(this.getHeight(), this.getWidth());
				g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
				g2.drawImage(logo, (this.getWidth()-imageSize)/2, (this.getHeight()-imageSize)/2, imageSize, imageSize, null);
			}
		};
		this.add(logoPanel);
	}
	
	public boolean loginButtonPressed() {		
		user = new User(usernameField.getText());
		String password = String.valueOf(passwordField.getPassword());
		
		if (user.getUsername().equals("") || password.equals("")) {
			feedbackLabel.setText("Please enter your login credentials");
			feedbackLabel.setVisible(true);
		}
		
		if (checkAccessToken()==true) {
			feedbackLabel.setText("You are already logged in!");
			feedbackLabel.setVisible(true);
			return true;
		}
		else {
			if (userService.login(user, password)==403) {
				System.out.println("User login unsuccessful");
				feedbackLabel.setText("Invalid login credentials");
				feedbackLabel.setVisible(true);
				passwordField.setText("");
				userLoggedIn = false;
				return false;
			} else if (userService.login(user, password)==200) {
				System.out.println("User successfully logged in");
				changeScene();
				userLoggedIn = true;
				return true;
			} else {
				System.out.println("Returned invalid status code");
				return false;
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
	
	public void createAccount() {
		usernameField.setText("The Create Account Button Works");
		System.out.println("The Create Account Button Works");
	}
	
	public boolean isOpen() {
		return isOpen;
	}
	
	@Override
	public void paint(Graphics g) {
		g.setColor(colorDark);
		g.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), curvatureRadius, curvatureRadius);
		super.paint(g);
	}
	
	public RoundedButton getLoginButton() {
		return loginButton;
	}
	
	public RoundedButton getContinueButton() {
		return continueButton;
	}

	@Override
	public void addLayoutComponent(String name, Component comp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void layoutContainer(Container parent) {
		int w = getWidth();
		int h = getHeight();
		
		usernamePanel.setBounds(w/2-w/3, h/2 - h/16, 2*w/3, h/16);
		usernameField.setBounds(curvatureRadius/2, 0, usernamePanel.getWidth()-20, usernamePanel.getHeight());
		
		passwordPanel.setBounds((w-((w/6)*4))/2, (h/2)+(h/16), (w/6)*4, h/16);
		passwordField.setBounds(curvatureRadius/2, 0, passwordPanel.getWidth()-20, passwordPanel.getHeight());
		
		feedbackPanel.setBounds((w-((w/6)*4))/2, (h/2)+(6*h/16), (w/6)*4, h/16);
		feedbackLabel.setBounds(curvatureRadius/2, 0, feedbackPanel.getWidth()-20, feedbackPanel.getHeight());

		continueButton.setBounds(passwordPanel.getX(), (h/2)+(3*(h/16)), (passwordPanel.getWidth()/12)*5, passwordPanel.getHeight());
		loginButton.setBounds(passwordPanel.getX()+(passwordPanel.getWidth()/12)*7, (h/2)+(3*(h/16)), (passwordPanel.getWidth()/12)*5, passwordPanel.getHeight());
		createAccountButton.setBounds(passwordPanel.getX(), (h/2)+(5*(h/16)), passwordPanel.getWidth(), passwordPanel.getHeight());

		logoPanel.setBounds(passwordPanel.getX(), 0, passwordPanel.getWidth(), (h/2)-(h/16));
	}
	
}

