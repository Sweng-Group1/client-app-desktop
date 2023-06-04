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
 * @author Srikanth Jakka & Luke George. Sophie Maw, Fraser Todd & Jonathan Cooke.
 * @since 29/05/2023
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
	private JPanel logoPanel;
	private InputTextPanel passwordPanel;
	private InputTextPanel usernamePanel;
	private JPanel feedbackPanel;
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
		
		usernamePanel = new InputTextPanel("Username", false);	
		this.add(usernamePanel);
		
		passwordPanel = new InputTextPanel("Password", true);
		this.add(passwordPanel);
		
		createFeedbackMessage();
		createButtons();
		createLogo();
		//checkAccessToken();
		
		
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
	
	private class FeedbackMessageDisplay extends JPanel {
		
	}
	
	private void createFeedbackMessage() {
		feedbackPanel = new JPanel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 4422159517991702385L;

			public void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D)g;
				RenderingHints qualityHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				qualityHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
				g2.setRenderingHints(qualityHints);
				g2.setColor(colorLight);
				g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), curvatureRadius/2, curvatureRadius/2);
				super.paintComponent(g);
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
	
	
//	/**
//	 * Creates the buttons that the user interacts with to submit their information, and adds them to LoginScene
//	 */
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
		createAccountButton = new LoginSceneButton("Create Account", transparent, transparent, Color.gray);
		
		this.add(loginButton);
		this.add(continueButton);
		this.add(createAccountButton);
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
	
	public boolean loginButtonPressed() {		
		user = new User(usernamePanel.inputTextField.getText());
		String password = String.valueOf(((JPasswordField) passwordPanel.inputTextField).getPassword());
		
		
		if (user.getUsername().equals("") || password.equals("")) {
			feedbackMessage.setText("Enter your details");
			feedbackMessage.setVisible(true);
		}
		
		if (checkAccessToken()==true) {
			feedbackMessage.setText("You're already logged in!");
			feedbackMessage.setVisible(true);
			return true;
		}
		else {
			if (userService.login(user, password)==403) {
				System.out.println("User login unsuccessful");
				feedbackMessage.setText("Invalid login credentials");
				feedbackMessage.setVisible(true);
				passwordPanel.inputTextField.setText("");
				return false;
			} else if (userService.login(user, password)==200) {
				System.out.println("User successfully logged in");
				changeScene();
				return true;
			} else {
				System.out.println("Returned invalid status code");
				feedbackMessage.setText("Cannot reach the server");
				feedbackMessage.setVisible(true);
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
		return usernamePanel.inputTextField.getText();
	}
	
	public void createAccount() {
		usernamePanel.inputTextField.setText("The Create Account Button Works");
		System.out.println("The Create Account Button Works");
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
		int w = getWidth();
		int h = getHeight();
		
		usernamePanel.setBounds(w/2-w/3, h/2 - h/16, 2*w/3, h/16);
		usernamePanel.inputTextField.setBounds(curvatureRadius/2, 0, usernamePanel.getWidth()-20, usernamePanel.getHeight());
		
		passwordPanel.setBounds((w-((w/6)*4))/2, (h/2)+(h/16), (w/6)*4, h/16);
		passwordPanel.inputTextField.setBounds(curvatureRadius/2, 0, passwordPanel.getWidth()-20, passwordPanel.getHeight());
		
		feedbackPanel.setBounds((w-((w/6)*4))/2, (h/2)+(6*h/16), (w/6)*4, h/16);
		feedbackMessage.setBounds(curvatureRadius/2, 0, feedbackPanel.getWidth()-20, feedbackPanel.getHeight());

		loginButton.setBounds(passwordPanel.getX(), (h/2)+(3*(h/16)), (passwordPanel.getWidth()/12)*5, passwordPanel.getHeight());
		continueButton.setBounds(passwordPanel.getX()+(passwordPanel.getWidth()/12)*7, (h/2)+(3*(h/16)), (passwordPanel.getWidth()/12)*5, passwordPanel.getHeight());
		createAccountButton.setBounds(passwordPanel.getX(), (h/2)+(5*(h/16)), passwordPanel.getWidth(), passwordPanel.getHeight());

		logoPanel.setBounds(passwordPanel.getX(), 0, passwordPanel.getWidth(), (h/2)-(h/16));
	}
	
}

