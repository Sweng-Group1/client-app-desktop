package sweng.group.one.client_app_desktop.presentation;

import java.awt.Point;

import javax.swing.JComponent;

public abstract class PresElement{
	protected Point pos;
	protected int width;
	protected int height;
	protected float duration;
	protected Slide slide;
	protected JComponent component;
	
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
}
