package sweng.group.one.client_app_desktop.sceneControl;

import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.BoxLayout;

import sweng.group.one.client_app_desktop.uiElements.CircleButton;
import sweng.group.one.client_app_desktop.uiElements.RoundedPanel;

public class ToolBar extends RoundedPanel{
	List<CircleButton>buttons;
	
	public ToolBar() {
		createBackground();
		buttons= Collections.synchronizedList(new ArrayList<>());
		
	}
	private void createBackground() {
		this.setCurvatureRadius(10);
		//this.setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
		this.setLayout(null);
	}
	public void addButton(Image icon) {
		CircleButton button = new CircleButton();
		button.setMainBackground(Color.white);
		button.setImageIcon(icon);
		buttons.add(button);
		this.add(button);
	}
	public void setSize(int width, int height) {
		super.setSize(width, height);
		
		for(int i=0;i<buttons.size();i++) {
			buttons.get(i).setSize(height);
			buttons.get(i).setLocation(i*height,0);
		}
	}

}

	

