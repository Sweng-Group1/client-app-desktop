package sweng.group.one.client_app_desktop.presentation;

import java.awt.Point;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

public abstract class PresElement{
	protected Point pos;
	protected int width;
	protected int height;
	protected float duration;
	protected Slide slide;
	protected JComponent component;
	protected String type;

	
	protected PresElement(Point pos, 
						int width, 
						int height, 
						float duration, 
						Slide slide) {
		
		this.pos = pos;
		this.width = width;
		this.height = height;
		this.duration = duration;
		this.slide = slide;
		
	}
	
	/**
	 * Method to which makes the element visible for a given time
	 * specified as "duration"
	 * The element will not disappear if the duration > 0
	 *
	 */
	protected void displayElement() {
		component.setVisible(true);
		if (duration > 0) {
			new java.util.Timer().schedule( 
			        new java.util.TimerTask() {
			            @Override
			            public void run() {
			                component.setVisible(false);
			            }
			        }, 
			        (long) (duration*1000) 
			);
		}
	}
	public String getType() {
		return type;
	}
	public JComponent getComponent() {
		return component;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public float getDuration() {
		return duration;
	}
	public Point getPosPoint() {
		return pos;
	}
	public void setX(int x) {
		pos.setLocation(x, pos.y);
	}
	public void setY(int y) {
		pos.setLocation(pos.x, y);
	}
	public void setWidth(int width) {
		this.width=width;
	}
	public void setHeight(int height) {
		this.height=height;
	}
	public void setDuration(float duration) {
		this.duration=duration;
	}
	
}
