package sweng.group.one.client_app_desktop.uiElements;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.JPanel;

public class UploadSceneComponent extends JPanel implements ComponentInterface{
	 protected Color main;
	 protected Color secondary;
	 
	 
	 Rectangle r;
	 
	 public UploadSceneComponent() {
		 this.setOpaque(false);
		 r= new Rectangle();
		
	 }
	
	public void setMarginBounds(int left, int top, int right,int bottom) {
		r.setBounds(left, top, right,bottom);
	
	}
	
	//OVERIDED METHODS:
	public void paintComponent(Graphics g) {
		Graphics2D g2= (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(main);
		g2.fillRoundRect(r.x, r.y, this.getWidth()-r.width-r.x, this.getHeight()-(r.height)-r.y, curvatureRadius, curvatureRadius);
		g2.dispose();
		super.paintComponent(g);
	}

}
