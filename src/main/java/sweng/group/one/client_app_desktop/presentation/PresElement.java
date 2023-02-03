package sweng.group.one.client_app_desktop.presentation;

import java.awt.Point;

import javax.swing.JComponent;

@SuppressWarnings("serial")
public abstract class PresElement extends JComponent{
	protected Point pos;
	protected int pointWidth;
	protected int pointHeight;
	protected float duration;
	protected Slide slide;
	
	protected PresElement(Point pos, 
						int pointWidth, 
						int pointHeight, 
						float duration, 
						Slide slide) {
		
		this.pos = pos;
		this.pointWidth = pointWidth;
		this.pointHeight = pointHeight;
		this.duration = duration;
		this.slide = slide;
	}
}
