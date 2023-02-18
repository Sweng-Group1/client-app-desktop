package sweng.group.one.client_app_desktop.sceneControl;

import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import sweng.group.one.client_app_desktop.uiElements.CircleButton;
import sweng.group.one.client_app_desktop.uiElements.RoundedPanel;

public class ToolBar extends JPanel{
	List<CircleButton>buttonsLtoR;
	List<CircleButton>buttonsRtoL;
	Color colour1;
	Color colour2;
	public ToolBar(Color background) {
		createBackground();
		
		
	}
	private void createBackground() {
		this.setOpaque(false);	
		this.setLayout(null);
		buttonsLtoR= Collections.synchronizedList(new ArrayList<>());
		buttonsRtoL= Collections.synchronizedList(new ArrayList<>());
	}
	public void addButtonLtoR(Image icon) {
		CircleButton button = new CircleButton();
		button.setMainBackground(Color.white);
		button.setImageIcon(icon);
		buttonsLtoR.add(button);
		this.add(button);
	}
	public void addButtonRtoL(Image icon) {
		CircleButton button = new CircleButton();
		button.setMainBackground(Color.white);
		button.setImageIcon(icon);
		buttonsRtoL.add(button);
		this.add(button);
	}
	public void setSize(int width, int height) {
		super.setSize(width, height);
		
		for(int i=0;i<buttonsLtoR.size();i++) {
			buttonsLtoR.get(i).setSize(height);
			buttonsLtoR.get(i).setLocation(i*height,0);
		}
		
		for(int i=0;i<buttonsRtoL.size();i++) {
			buttonsRtoL.get(i).setSize(height);
			buttonsRtoL.get(i).setLocation(this.getWidth()-(i*height)-height,0);
		}
	}

}

	

