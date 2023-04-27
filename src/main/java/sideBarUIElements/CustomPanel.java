package sideBarUIElements;

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
	public void setBounds(Rectangle r, int curveRadius) {
		//super.setBounds(r);
		this.r = new Rectangle(0,0,r.width,r.height);
		this.setLocation(r.x, r.y);
		this.setSize(r.width, r.height);
		maxWidth= r.width;
	}
	public void paint(Graphics g) {
		Graphics2D g2= (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(colorDark);
		 g2.fillRoundRect(0,0,r.width,r.height, curvatureRadius, curvatureRadius);
		super.paint(g2);
	}
	public void minimise(long timeToMinimise) {
		int timeInterval= (int) (timeToMinimise/maxWidth);
		for(int i=0; i<maxWidth;i++) {
			timer.schedule(new TimerTask() {

				@Override
				public void run() {
					r.setBounds(r.x,r.y, r.width-1, r.height);
					repaint();
					if(r.x+r.width==maxPosition) {
						isMoving=false;
					}
				    //System.out.println(r.width);
				}
				
			}, timeInterval*i);
		}
	}
	public void maximise(long timeToMaximise) {
		int timeInterval= (int) (timeToMaximise/maxWidth);
		for(int i=0; i<maxWidth;i++) {
			timer.schedule(new TimerTask() {

				@Override
				public void run() {
					r.setBounds(r.x,r.y, r.width+1, r.height);
					repaint();
					if(r.width==maxWidth) {
						isMoving=false;
						scrollPane.setVisible(true);
						scrollBar.setVisible(true);
					}
				    //System.out.println(r.width);
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
