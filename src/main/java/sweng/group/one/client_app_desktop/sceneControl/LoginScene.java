package sweng.group.one.client_app_desktop.sceneControl;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import sweng.group.one.client_app_desktop.data.AuthenticationException;
import sweng.group.one.client_app_desktop.data.User;
import sweng.group.one.client_app_desktop.data.UserService;
import sweng.group.one.client_app_desktop.uiElements.RoundedButton;

/**
 * Modified JPanel that allows users to login or create an account using a chosen username and password
 * 
 * @author Luke George, Srikanth Jakka, Sophie Maw, Fraser Todd & Jonathan Cooke.
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
	private LoginSceneButton continueButton;
	private LoginSceneButton createAccountButton;
	private LoginSceneButton loginButton;
	private LoginSceneButton registerButton;
	private JPanel logoPanel;
	private InputTextPanel usernamePanel;
	private InputTextPanel passwordPanel;
	private InputTextPanel emailPanel;
	private InputTextPanel firstNamePanel;
	private InputTextPanel lastNamePanel;
	private JPanel feedbackPanel;
	private JLabel feedbackLabel;
	
	private UserService userService = new UserService();
	private User user = new User("Default");
	
	public boolean userLoggedIn = false;
	private boolean createAccount = false;
	
	// -------------------------------------------------------------- //
	// ----------------------- CONSTRUCTOR -------------------------- //
	// -------------------------------------------------------------- //
	
	/**
	 * 
	 * @throws IOException
	 */
	public LoginScene() throws IOException {
		this.setOpaque(false);
		
		usernamePanel = new InputTextPanel("Username", false);	
		this.add(usernamePanel);
		
		passwordPanel = new InputTextPanel("Password", true);
		this.add(passwordPanel);

		emailPanel = new InputTextPanel("e-mail", false);
		emailPanel.setVisible(false);
		this.add(emailPanel);
		
		firstNamePanel = new InputTextPanel("First Name", false);	
		firstNamePanel.setVisible(false);
		this.add(firstNamePanel);
		
		lastNamePanel = new InputTextPanel("Last Name", false);	
		lastNamePanel.setVisible(false);
		this.add(lastNamePanel);
		
		createFeedbackLabel();
		createButtons();
		createLogo();
		checkAccessToken();
		
		this.setLayout(this);
	}
	
	/**
	 * Custom text input box. Follows branded layout and colour scheme.
	 * 
	 * @author Jonathan Cooke, Sophie Maw and Fraser Todd
	 *
	 */
	private class InputTextPanel extends JPanel {

		private static final long serialVersionUID = 1L;
		protected JTextField inputTextField;
		
		/**
		 * 
		 * @param prompt The text to appear in the input box when empty (usually a prompt for the user).
		 * @param passwordField Set to true to hide user input from the display when typing. False otherwise.
		 */
		InputTextPanel(String prompt, Boolean passwordField) {
			super();
			
			if (passwordField) {
				this.inputTextField = new JPasswordField();	
			}
			else {
				this.inputTextField = new JTextField();
			}
			this.add(inputTextField);
			this.setLayout(null);
			this.setOpaque(false);
			this.setBackground(transparent);
			
			inputTextField.setOpaque(false);
			// Background colour of input text box
			inputTextField.setBackground(transparent);
			inputTextField.setBorder(null);
			// Cursor colour
			inputTextField.setCaretColor(Color.white);
			// Input Text Colour When highlighted by the user
			inputTextField.setSelectedTextColor(Color.white);
			inputTextField.setDisabledTextColor(Color.white);
			// Display Text Colour When input by the user
			inputTextField.setForeground(Color.white);
			inputTextField.setName(prompt);
			inputTextField.setVisible(true);
			}
		
		// Better to use this than overriding paint, as it preserves layering automatically
		public void paintComponent(Graphics g) {
			// Advanced 2D geometry
			Graphics2D g2 = (Graphics2D)g;
			// Add graphics line smoothing
			RenderingHints qualityHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			// Set to render based on high quality over performance
			qualityHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			g2.setRenderingHints(qualityHints);
			
			// Set drawing colour to one the textbox background colour
			g2.setColor(colorLight);
			
			// Button geometry
			g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), curvatureRadius/2, curvatureRadius/2);
			
			if(inputTextField.getText().isBlank()) {
				String str = inputTextField.getName();
				g2.setColor(colorDark);
				g2.drawString(str, curvatureRadius/2,(this.getHeight()/2)+(g.getFontMetrics().getHeight()/4));
			}
	
			// Set colour to one of the project defaults
			g2.setColor(colorLight);
			
			super.paintComponent(g);
			 
		}
	}
	
	private class LoginSceneButton extends RoundedButton {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String buttonName;
		
		public LoginSceneButton(String buttonName, Image image, int curveRadius, Color mainColour, Color pressedColour,
				Color hoverColour) {
			super(image, curveRadius, mainColour, pressedColour, hoverColour);
			this.buttonName = buttonName;
		}
		
		/**
		 * Defines LoginScene buttons with different colours to the default scheme.
		 * 
		 * @author Jonathan Cooke, Sophie Maw and Fraser Todd
		 * 
		 * @param buttonName Will appear as text within the button.
		 * @param mainColour Primary background colour.
		 * @param pressedColour Background colour when clicked on.
		 * @param hoverColour Background colour when moused over.
		 */
		public LoginSceneButton(String buttonName, Color mainColour, Color pressedColour,
				Color hoverColour) {
			super(null, curvatureRadius, mainColour, pressedColour, hoverColour);
			this.buttonName = buttonName;
		}
		
		/**
		 * LoginScene button that adheres to the default colour scheme.
		 * 
		 * @param buttonName Will appear as text within the button.
		 */
		public LoginSceneButton(String buttonName) {
			super(null, curvatureRadius, colorLight, colorDark, Color.gray);
			this.buttonName = buttonName;
		}
		
		public void setButtonName(String buttonName) {
			this.buttonName = buttonName;
		}

		// Overriding paint is bad practice, however, this is necessary due to the use of paint overrides in RoundedButton
		@Override
		public void paint(Graphics g) {
			Graphics g2 = g.create();
			super.paint(g2);
			String str = buttonName;
			g.setColor(Color.white);
			g.drawString(str,(this.getWidth()/2)-(g.getFontMetrics().stringWidth(str)/2),(this.getHeight()/2)+(g.getFontMetrics().getHeight()/4));
			g.dispose();
		}
	}
	
	private class FeedbackLabelDisplay extends JPanel {
		
	}
	
	private void createFeedbackLabel() {
		feedbackPanel = new JPanel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 4422159517991702385L;

			public void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D)g;
				super.paintComponent(g);
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
		loginButton = new LoginSceneButton("Log In") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 5617284310683026116L;

			@Override
			public void buttonPressed() {
				loginButtonPressed();
			}
		};
		
		continueButton = new LoginSceneButton("Cancel");
		
		createAccountButton = new LoginSceneButton("Create Account", transparent, transparent, Color.gray) {
			@Override
			public void buttonPressed() {
				createAccountButtonPressed();
			}
		};
		
		registerButton = new LoginSceneButton("Register") {
			@Override
			public void buttonPressed() {
				registerButtonPressed();
			}
		};
		registerButton.setVisible(false);
		
		this.add(loginButton);
		this.add(continueButton);
		this.add(createAccountButton);
		this.add(registerButton);
	}
	
	
	/**
	 * Adds the YUSU Logo to LoginScene
	 * @throws IOException
	 */
	private void createLogo() throws IOException {
		logo = ImageIO.read(new File("./assets/Yusu Logo 14.png"));
		logoPanel = new JPanel() {

			//private static final long serialVersionUID = 6961538361558408979L;

			@Override
			public void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D)g;
				int imageSize = Math.min(this.getHeight(), this.getWidth());
				g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
				g2.drawImage(logo, (this.getWidth()-imageSize)/2, (this.getHeight()-imageSize)/2, imageSize, imageSize, null);
			}
		};
		this.add(logoPanel);
	}
	
	/**
	 * Checks the validity of the users login details with the server and attempts to log them in
	 * Informs the user whether or not their attempt was successful
	 * 
	 * @return Indicates if the login was a success
	 */
	public boolean loginButtonPressed() {		
		user = new User(usernamePanel.inputTextField.getText());
		String password = String.valueOf(((JPasswordField) passwordPanel.inputTextField).getPassword());
		
		if (user.getUsername().equals("") || password.equals("")) {
			feedbackLabel.setText("Enter your details");
			feedbackLabel.setVisible(true);
			return false;
		}
		
		if (checkAccessToken()==true) {
			feedbackLabel.setText("You're already logged in!");
			feedbackLabel.setVisible(true);
			return true;
		}
		else {
			try {
				if (userService.login(user, password)==200) {
					System.out.println("User successfully logged in");
					changeScene();
					userLoggedIn = true;
					return true;
				} else {
					System.out.println("Returned invalid status code");
					feedbackLabel.setText("Cannot reach the server");
					feedbackLabel.setVisible(true);
					return false;
				}
			} catch (AuthenticationException e) {
				System.out.println("User login unsuccessful");
				feedbackLabel.setText("Invalid login credentials");
				feedbackLabel.setVisible(true);
				passwordPanel.inputTextField.setText("");
				userLoggedIn = false;
				e.printStackTrace();
				return false;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}
	
	/**
	 * Checks whether the user has a valid access token 
	 * 
	 * @return
	 */
	public boolean checkAccessToken() {
		try {
			if (userService.refreshAccessToken(user)==200) {
				System.out.println("User has a valid access token");
				return true;
			}
			else {
				System.out.println("User does not have a valid access token");
				return false;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AuthenticationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Alters the appearance and functionality of LoginScene dependent on its current state
	 * Either allows for logging in, or for account creation
	 */
	public void createAccountButtonPressed() {
		if (createAccount==false) {
			logoPanel.setVisible(false);
			emailPanel.setVisible(true);
			firstNamePanel.setVisible(true);
			lastNamePanel.setVisible(true);
			loginButton.setVisible(false);
			registerButton.setVisible(true);
			createAccountButton.setButtonName("Back to Log In");
			createAccount = true;
			passwordPanel.inputTextField.setText("");
		}
		else {
			logoPanel.setVisible(true);
			emailPanel.setVisible(false);
			firstNamePanel.setVisible(false);
			lastNamePanel.setVisible(false);
			loginButton.setVisible(true);
			registerButton.setVisible(false);
			createAccountButton.setButtonName("Create Account");
			createAccount = false;
			passwordPanel.inputTextField.setText("");
			emailPanel.inputTextField.setText("");
			firstNamePanel.inputTextField.setText("");
			lastNamePanel.inputTextField.setText("");
		}
		feedbackLabel.setVisible(false);
	}
	
	/**
	 * Sends valid user details to the server in order to setup a new user account
	 */
	public void registerButtonPressed() {
		String email = emailPanel.inputTextField.getText();
		String firstName = firstNamePanel.inputTextField.getText();
		String lastName = lastNamePanel.inputTextField.getText();
		user = new User(usernamePanel.inputTextField.getText());
		String password = String.valueOf(((JPasswordField) passwordPanel.inputTextField).getPassword());
		
		try {
			if (userService.createUser(user.getUsername(), password, firstName, lastName, email)==0) {
				feedbackLabel.setText("Please check your details and try again");
			}
			else {
				feedbackLabel.setText("Thank you for registering " + firstName + "!");
				System.out.println("Registering");
			}
			feedbackLabel.setVisible(true);
		} catch (IOException e) {
			feedbackLabel.setText("Cannot reach the server");
			feedbackLabel.setVisible(true);
			e.printStackTrace();
		}
	}
	
	/**
	 * Removes LoginScene from the view
	 */
	public void changeScene() {
		isOpen = false;
		this.setVisible(false);
	}
	
	public String getUsername() {
		return usernamePanel.inputTextField.getText();
	}
	
	public boolean isOpen() {
		return isOpen;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.setColor(colorDark);
		g.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), curvatureRadius, curvatureRadius);
		super.paintComponent(g);
	}
	
	public RoundedButton getLoginButton() {
		return loginButton;
	}
	
	public RoundedButton getContinueButton() {
		return continueButton;
	}

	/**
	 * Needed by LayoutManager
	 */
	@Override
	public void addLayoutComponent(String name, Component comp) {}

	/**
	 * Needed by LayoutManager
	 */
	@Override
	public void removeLayoutComponent(Component comp) {}

	/**
	 * Needed by LayoutManager
	 */
	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return null;
	}

	/**
	 * Needed by LayoutManager
	 */
	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return null;
	}

	@Override
	public void layoutContainer(Container parent) {
		int sceneWidth = getWidth();
		int sceneHeight = getHeight();
		int fieldWidth = 2*sceneWidth/3;
		int fieldHeight = sceneHeight/16;
		int fieldX = (sceneWidth-fieldWidth)/2;
		int fieldY = sceneHeight/2+sceneHeight/16;
		int smallButtonWidth = 5*fieldWidth/12;
		
		usernamePanel.setBounds(fieldX, fieldY-sceneHeight/8, fieldWidth, fieldHeight);
		usernamePanel.inputTextField.setBounds(curvatureRadius/2, 0, usernamePanel.getWidth()-20, usernamePanel.getHeight());
		
		passwordPanel.setBounds(fieldX, fieldY, fieldWidth, fieldHeight);
		passwordPanel.inputTextField.setBounds(curvatureRadius/2, 0, passwordPanel.getWidth()-20, passwordPanel.getHeight());
		
		emailPanel.setBounds(fieldX, fieldY-4*sceneHeight/8, fieldWidth, fieldHeight);
		emailPanel.inputTextField.setBounds(curvatureRadius/2, 0, emailPanel.getWidth()-20, emailPanel.getHeight());
		
		firstNamePanel.setBounds(fieldX, fieldY-3*sceneHeight/8, fieldWidth, fieldHeight);
		firstNamePanel.inputTextField.setBounds(curvatureRadius/2, 0, firstNamePanel.getWidth()-20, firstNamePanel.getHeight());
		
		lastNamePanel.setBounds(fieldX, fieldY-2*sceneHeight/8, fieldWidth, fieldHeight);
		lastNamePanel.inputTextField.setBounds(curvatureRadius/2, 0, lastNamePanel.getWidth()-20, lastNamePanel.getHeight());
		
		feedbackPanel.setBounds(fieldX, fieldY+(11*sceneHeight/32), fieldWidth, fieldHeight);
		feedbackLabel.setBounds(curvatureRadius/2, 0, feedbackPanel.getWidth()-20, feedbackPanel.getHeight());

		loginButton.setBounds(fieldX, fieldY+2*sceneHeight/16, smallButtonWidth, fieldHeight);
		continueButton.setBounds(fieldX+fieldWidth-smallButtonWidth, fieldY+2*sceneHeight/16, smallButtonWidth, fieldHeight);
		createAccountButton.setBounds(fieldX, fieldY+sceneHeight/4, fieldWidth, fieldHeight);
		registerButton.setBounds(fieldX, fieldY+2*sceneHeight/16, smallButtonWidth, fieldHeight);

		logoPanel.setBounds(fieldX, 0, fieldWidth, fieldY-sceneHeight/8);
	}
	
}

