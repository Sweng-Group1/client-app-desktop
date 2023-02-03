package sweng.group.one.client_app_desktop.presentation;

import java.awt.Graphics2D;

import javax.swing.JComponent;

public abstract class PresElement extends JComponent{
	protected int xPoint;
	protected int yPoint;
	protected int pointWidth;
	protected int pointHeight;
	protected float duration;
	protected Slide slide;
	
	protected PresElement(int xPoint, 
						int yPoint, 
						int pointWidth, 
						int pointHeight, 
						float duration, 
						Slide slide) {
		
		this.xPoint = xPoint;
		this.yPoint = yPoint;
		this.pointWidth = pointWidth;
		this.pointHeight = pointHeight;
		this.duration = duration;
		this.slide = slide;
	}
}
