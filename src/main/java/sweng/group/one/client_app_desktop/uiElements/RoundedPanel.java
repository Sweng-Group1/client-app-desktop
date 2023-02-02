package sweng.group.one.client_app_desktop.uiElements;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

/* 
 This Class instantiates a rounded panel object, set the curvature radius size to change it's curved corners
 
 */

public class RoundedPanel extends JPanel{
	
	protected int curvedRadius;
	private Color border;
	private int borderSize;
	
	public RoundedPanel() {
		setOpaque(false);

	}
	public void setSize(int width, int height, int curvatureRadius) {
		super.setSize(width, height);
		curvedRadius= curvatureRadius;
		borderSize= 0;
		border= this.getBackground();
		
	}

	public void paint(Graphics g) {
		Graphics2D g2= (Graphics2D) g;
		
		if(borderSize>0) {
			g2.setColor(border);
			g2.fillRoundRect(0, 0, getWidth(), getHeight(), curvedRadius, curvedRadius);
			g2.setColor(getBackground());
			g2.fillRoundRect(borderSize, borderSize, getWidth()-(2*borderSize), getHeight()-(2*borderSize), curvedRadius, curvedRadius);
		}else {
			g2.setColor(getBackground());	
			g2.fillRoundRect(0, 0, getWidth(), getHeight(), curvedRadius, curvedRadius);
		}
		super.paint(g);	
	}


	//setters and getters
	public void setOpacityValue(int value) {
		/* 0 = opaque, 255 = transparent */
		/* Alpha value cannot exceed 255, or go below 0 */
		Color backgroundColour= getBackground();
		
		int alphaValue;
		if(value>255) {
			alphaValue= 255;
		}else if(value<0){
			alphaValue= 0;
		}else {
			alphaValue= value;
		}
		this.setBackground(new Color(backgroundColour.getRed(),
				backgroundColour.getGreen(),backgroundColour.getBlue(),alphaValue));
	}
	public void setBorder(Color colour, int borderSize) {
		border= colour;
		this.borderSize= borderSize;
	}
	public Color getBorderColour() {
		return border; 
	}
	public int getBorderSize() {
		return borderSize;
	}
	public void setCurvatureRadius(int curveValue) {
		curvedRadius= curveValue;
	}
	public int getCurvatureRadius() {
		return curvedRadius;
	}
	
}
