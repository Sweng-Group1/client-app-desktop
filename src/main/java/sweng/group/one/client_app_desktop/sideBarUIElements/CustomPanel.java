package sweng.group.one.client_app_desktop.sideBarUIElements;

import java.awt.Color;
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
 * @author joncooke
 * editted sophiemaw
 *
 */
public class CustomPanel extends JPanel implements ComponentInterface{
	private int gapWidth;
	private Rectangle r;
	private int rMax;
	private int minPosition;
	private int maxPosition;
	private boolean isMoving;
	private Timer timer;
	JScrollPane scrollPane;
	JScrollBar scrollBar;
	int maxWidth;
	
	public CustomPanel() {
		gapWidth=0;
		isMoving= false;
		timer= new Timer();
		this.setOpaque(false);
	}
	
	@Override
	public void setBounds(int x, int y, int w, int h) {
		super.setBounds(x, y, w, h);
		if(maxWidth <= 0) {
			maxWidth = w;
		}
	}
	
	@Override
	public void setBounds(Rectangle r) {
		super.setBounds(r);
		if(maxWidth <= 0) {
			maxWidth = r.width;
		}
	}
	
	/**
	 *@author sophiemaw
	 */
	public void paint(Graphics g) {
		Graphics2D g2= (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(colorDark);
		g2.fillRoundRect(0,0,getWidth(),getHeight(), curvatureRadius, curvatureRadius);
		g2.dispose();
		super.paint(g);
	}
	/**
	 *@author sophiemaw
	 */
	public void minimise(long timeToMinimise) {
		int timeInterval= (int) (timeToMinimise/maxWidth);
		for(int i=0; i<maxWidth;i++) {
			timer.schedule(new TimerTask() {

				@Override
				public void run() {
					setBounds(getX(), getY(), getWidth()-1, getHeight());
					repaint();
					if(getWidth() <= 0) {
						isMoving=false;
					}
				}
				
			}, timeInterval*i);
		}
	}
	/**
	 *@author sophiemaw
	 */
	public void maximise(long timeToMaximise) {

		int timeInterval= (int) (timeToMaximise/maxWidth);
		
		for(int i=0; i<maxWidth;i++) {
			timer.schedule(new TimerTask() {

				@Override
				public void run() {
					setBounds(getX(), getY(), getWidth()+1, getHeight());
					repaint();
					if(getWidth() >= maxWidth) {
						isMoving=false;
						scrollPane.setVisible(true);
						scrollBar.setVisible(true);
					}
				}
				
			}, timeInterval*i);
		}
	}

	public void setBackground(Color light, Color dark) {
		
		
	}
	public void setCurveRadius(int curveRadius) {
		
	}
	public void setGapWidth(int gapWidth) {
		this.gapWidth=gapWidth;
	}
	public boolean isMoving() {
		return isMoving;
	}
	public void setScrollPane(JScrollPane scrollPane) {
		this.scrollPane= scrollPane;
	}
	public void setScrollBar(JScrollBar scrollBar) {
		this.scrollBar = scrollBar;
	}

}
