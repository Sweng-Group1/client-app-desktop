package sweng.group.one.client_app_desktop.sideBarUIElements;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import sweng.group.one.client_app_desktop.sceneControl.ComponentInterface;

/**
 * Custom JPanel to allow themed ui elements and animations
 * 
 * @author Jonathan Cooke, Sophie Maw and Srikanth Jakka
 * @since 24/05/2023
 * @version 0.3
 *
 */
public class CustomPanel extends JPanel implements ComponentInterface {

	// Local Variables
	private Rectangle r;
	private boolean isMoving;
	private Timer timer;
	JScrollPane scrollPane;
	JScrollBar scrollBar;
	int maxWidth;

	// Main Constructor
	public CustomPanel() {
		isMoving = false;
		timer = new Timer();
		this.setOpaque(false);
	}

	// Set bounds for the panel(?)
	public void setBounds(Rectangle r, int curveRadius) {
		this.r = new Rectangle(0, 0, r.width, r.height);
		this.setLocation(r.x, r.y);
		this.setSize(r.width, r.height);
		maxWidth = r.width;
	}

	// Custom graphics paint function for this panel
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(colorDark);
		g2.fillRoundRect(0, 0, r.width, r.height, curvatureRadius, curvatureRadius);
		super.paint(g2);
	}

	// Minimize the panel
	public void minimise(long timeToMinimise) {
		int timeInterval = (int) (timeToMinimise / maxWidth);
		for (int i = 0; i < maxWidth; i++) {
			timer.schedule(new TimerTask() {

				@Override
				public void run() {
					r.setBounds(r.x, r.y, r.width - 1, r.height);
					repaint();
					if(r.x+r.width==0) {
						isMoving=false;
					}
				}

			}, timeInterval * i);
		}
	}

	// Maximize the panel
	public void maximise(long timeToMaximise) {
		int timeInterval = (int) (timeToMaximise / maxWidth);
		for (int i = 0; i < maxWidth; i++) {
			timer.schedule(new TimerTask() {

				@Override
				public void run() {
					r.setBounds(r.x, r.y, r.width + 1, r.height);
					repaint();
					if (r.width == maxWidth) {
						isMoving = false;
						scrollPane.setVisible(true);
						scrollBar.setVisible(true);
					}
				}
			}, timeInterval * i);
		}
	}

	public void setScrollPane(JScrollPane scrollPane) {
		this.scrollPane = scrollPane;
	}

	public void setScrollBar(JScrollBar scrollBar) {
		this.scrollBar = scrollBar;
	}

}
