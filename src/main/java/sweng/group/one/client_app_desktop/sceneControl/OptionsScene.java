package sweng.group.one.client_app_desktop.sceneControl;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class OptionsScene extends JPanel {
	private Boolean isOpen;
	
	
	// change these to boolean to test, can be made into JButton
	// When functions are ready
	private JButton optionsButton;


	protected JButton accountButton, addPostButton, helpButton, closeButton;
	
	private JPanel optionsPanel, optionsBox;
	private ImageIcon buttonIcon, buttonIcon2;
	private BufferedImage buttonImage, buttonImage2;
	
	
	public OptionsScene() { 
		super();
		try {
			setupGUI();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void setupGUI() throws IOException {	
		buttonImage = ImageIO.read(new File("./Assets/optionsicon.png"));
		buttonImage2 = ImageIO.read(new File("./Assets/optionsicon.png"));
		
		buttonIcon = resizeIcon(new ImageIcon(buttonImage), 75, 75);
		buttonIcon2 = resizeIcon(new ImageIcon(buttonImage2), 75, 75);
		
		isOpen = false;
		
		setLayout(new BorderLayout());
		
		optionsPanel = new JPanel(new GridLayout(2, 1));
		
		optionsBox = new JPanel(new GridBagLayout());	
		GridBagConstraints gbc = new GridBagConstraints();
		
		optionsBox.setVisible(isOpen);
		
		accountButton = new JButton("User");
		gbc.gridx = 0;
		gbc.gridy = 0;
		optionsBox.add(accountButton, gbc);
		
		addPostButton = new JButton("New Post");
		gbc.gridx = 1;
		gbc.gridy = 0;
		optionsBox.add(addPostButton, gbc);
		
		helpButton = new JButton("Help");
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		optionsBox.add(helpButton, gbc);
		
		closeButton = new JButton("Exit");
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		optionsBox.add(closeButton, gbc);
		
		optionsButton = new JButton(buttonIcon);
		optionsButton.setBorder(BorderFactory.createEmptyBorder());
		optionsButton.setContentAreaFilled(false);
				
		optionsPanel.add(optionsButton);
		optionsPanel.add(optionsBox);
		
		add(optionsPanel, BorderLayout.PAGE_START);
			
		open();
		
		setVisible(true);	
	}
	
	public ImageIcon resizeIcon(ImageIcon icon, int x, int y) {
		Image img = icon.getImage();
		img = img.getScaledInstance(x, y, java.awt.Image.SCALE_SMOOTH);
		return icon = new ImageIcon(img);
	}
	
	public void open() {
		optionsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(optionsButton.getIcon() == buttonIcon) {
					isOpen = true;
					optionsBox.setVisible(isOpen);
					optionsButton.setIcon(buttonIcon2);
				} else {
					close();
				}
			}
		});
	}
	
	public void close() {
		if(optionsButton.getIcon() == buttonIcon2) {
			isOpen = false;
			optionsBox.setVisible(isOpen);
			optionsButton.setIcon(buttonIcon);
		}
	}
	
	public void accountButtonCreate () {
		// Overridden in main
	}
	
	public void addPostButtonCreate() {
		
	}
	
	public void helpButtonCreate() {
		
	}
	
	public void closeButton() {
		
	}
	
	
}
