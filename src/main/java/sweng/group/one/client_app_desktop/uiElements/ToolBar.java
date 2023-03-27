package sweng.group.one.client_app_desktop.uiElements;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JPanel;
import javax.swing.JSlider;

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
	public JButton getPaintButton() {
		return buttonsLtoR.get(1);
	}
	public JButton getEraserButton() {
		return buttonsLtoR.get(2);
	}
	public JButton getBackButton() {
		return buttonsRtoL.get(3);
	}
	public JButton getForwardButton() {
		return buttonsRtoL.get(2);
	}
	

}

	

