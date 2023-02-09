package sweng.group.one.client_app_desktop.sceneControl;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	private JButton optionsButton, accountButton, addPostButton, helpButton, closeButton;
	private JPanel optionsPanel, optionsBox;
	private Icon buttonImage = new ImageIcon("optionsicon.png"); //Add a file to github here of the options icon or like yk draw it here which imma work on it prolly n do sum graphics thing instead of using images?
	private Icon buttonImage2 = new ImageIcon("optionsicon.png");
	
	public OptionsScene() { 
		super();
		setupGUI();
		
	}
	
	private void setupGUI() {	
		
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
		
		optionsButton = new JButton(buttonImage);
				
		optionsPanel.add(optionsButton);
		optionsPanel.add(optionsBox);
		
		add(optionsPanel, BorderLayout.PAGE_START);
			
		open();
		
		setVisible(true);	
	}
	
	public void open() {
		optionsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(optionsButton.getIcon() == buttonImage) {
					isOpen = true;
					optionsBox.setVisible(isOpen);
					optionsButton.setIcon(buttonImage2);
				} else {
					close();
				}
			}
		});
	}
	
	public void close() {
		if(optionsButton.getIcon() == buttonImage2) {
			isOpen = false;
			optionsBox.setVisible(isOpen);
			optionsButton.setIcon(buttonImage);
		}
	}
	/*
	public void accountButtonCreate () {
		ImageIcon accountImage = new ImageIcon("icon file name");
		accountButton = new JButton(accountImage);	
	}
	
	public void addPostButtonCreate() {
		ImageIcon addPostImage = new ImageIcon("icon file name");
		addPostButton = new JButton(addPostImage);	
	}
	
	public void helpButtonCreate() {
		ImageIcon helpButtonImage = new ImageIcon("icon file name");
		helpButton = new JButton(helpButtonImage);
	}
	
	public void closeButton() {
		ImageIcon closeButtonImage = new ImageIcon("icon file name");
		closeButton = new JButton(closeButtonImage);
	}
	*/
	
}
