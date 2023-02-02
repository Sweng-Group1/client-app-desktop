package sweng.group.one.client_app_desktop.sceneControl;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;

import sweng.group.one.client_app_desktop.uiElements.RoundedPanel;

public class TextBox extends RoundedPanel{
	JPanel textPanel;
	public TextBox(Color textPanelColour,Color background,int curvatureRadius) {
		this.setBackground(background);
		this.setCurvatureRadius(curvatureRadius);
		this.setLayout(null);
		textPanel= new JPanel();
		this.add(textPanel);
		textPanel.setBackground(textPanelColour);
	}
	
	//setters and getters
	
	public void setSize(int width, int height) {
		super.setSize(width, height);
		textPanel.setSize(9*(width/10),9*(height/10));
		textPanel.setLocation(width/20, height/20);
		this.validate();
	}
	
}
