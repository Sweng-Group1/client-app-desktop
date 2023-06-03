package sweng.group.one.client_app_desktop.presentation;

import java.awt.Point;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JComponent;

/**
 * @author flt515
 *
 */
public abstract class PresElement{
	protected Point pos;
	protected int width;
	protected int height;
	protected float duration;
	protected Slide slide;
	protected JComponent component;
	protected Timer displayTimer;
	
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
		this.displayTimer = new Timer();
	}
	
	/**
	 * Sets the visibility of the component based on the provided boolean value.
	 * If the component is set to be displayed and a positive duration is specified,
	 * it will automatically hide the component after the specified duration in seconds.
	 * @param displaying true if the component should be displayed, false otherwise
	 */
	protected void displayElement(boolean displaying) {
		component.setVisible(displaying);
		if (duration > 0 && displaying) {
			displayTimer = new Timer();
			displayTimer.schedule( 
			        new TimerTask() {
			            @Override
			            public void run() {
			                component.setVisible(false);
			            }
			        }, 
			        (long) (duration*1000) 
			);
		}
		else {
			displayTimer.cancel();
			displayTimer.purge();
		}
	}
	
	protected void displayElement() {
		this.displayElement(true);
	}
}