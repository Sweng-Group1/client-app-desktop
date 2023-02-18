package sweng.group.one.client_app_desktop.sceneControl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import sweng.group.one.client_app_desktop.uiElements.RoundedPanel;

public class TextBox extends RoundedPanel{
	JPanel textPanel;
	boolean addMediaIconIsVisible;
	boolean addTagsIconIsVisible;
	boolean addDescriptionIconIsVisible;
	boolean addMediaElementsToAjustIsVisible;
	boolean searchLocationHeaderIsVisible;
	
	
	
	public TextBox(Color background,int curvatureRadius) {
		
		create(background, curvatureRadius);

	}
	private void create(Color background, int curvatureRadius) {
		this.setBackground(background);
		this.setCurvatureRadius(curvatureRadius);
		addMediaIconIsVisible=false;
	}
	public void paint(Graphics g) {
		super.paint(g);
		if(addMediaIconIsVisible==true) {
			g.setColor(Color.white);
			int height= this.getHeight()/2;
			int width= this.getWidth()/2;
			g.fillRect(width-25, height, 50, 8);
			g.fillRect(width-4, height-21, 8, 50);
			g.drawString("Upload media...", width-50, height+60);
		}
		if(addTagsIconIsVisible==true) {
			g.setColor(Color.white);

			if(searchLocationHeaderIsVisible==true) {
				g.setColor(new Color(102,132,162));
				g.fillRoundRect(0, 0,this.getWidth(),  this.getHeight()/3, this.getCurvatureRadius(),this.getCurvatureRadius());
				g.fillRect(0, this.getHeight()/6, this.getWidth(), this.getHeight()/6);
				
				try {
					//add search icon
					BufferedImage im= ImageIO.read(new File("./Assets/search.png"));
					int diameter= this.getHeight()/6;
					g.drawImage(im.getScaledInstance(diameter, diameter, java.awt.Image.SCALE_SMOOTH),this.getWidth()-diameter-(diameter/2),(diameter/3)*2,null);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				// g.setClip(0, this.getHeight()/3, this.getWidth(), (this.getHeight()/3));
				g.setColor(Color.white);				
				g.drawString("Search location...", 10,20);
				g.drawString("Add tags...", 10, 2*(getHeight()/3));
			}else {
				g.drawString("Add tags...", 10,20);
			}
		}
		if(addDescriptionIconIsVisible==true) {
			g.setColor(Color.white);
			g.drawString("Add description...", 10, 20);
		}
		if(addMediaElementsToAjustIsVisible==true) {
			g.setColor(Color.white);
			g.drawString("Add media to adjust...", 5, 20);
		}
		
	}
	//setters and getters
	public void setAddMediaIconVisible(boolean val) {
		addMediaIconIsVisible=val;
	}
	public void setAddTagsIconIsVisible(boolean val) {
		addTagsIconIsVisible=val;
	}
	public void setAddDescriptionIconIsVisible(boolean val) {
		addDescriptionIconIsVisible=val;
	}
	public void setAddMediaElementsToAjustIsVisible(boolean val) {
		addMediaElementsToAjustIsVisible=val;
	}
	public void setSearchLocationHeaderIsVisible(boolean val) {
		searchLocationHeaderIsVisible=val;
	}
	
}
