package sweng.group.one.client_app_desktop.uiElements;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class RoundedPanel extends JPanel{
	
	private int curvedRadius;
	private boolean bottomLine;
	private boolean isATab;
	
	public RoundedPanel() {
		setOpaque(false);
		bottomLine=false;
		isATab=false;
	}
	public void setSize(int width, int height, int curvatureRadius) {
		super.setSize(width, height);
		curvedRadius= curvatureRadius;
		
	}

	public void paint(Graphics g) {
		Graphics2D g2= (Graphics2D) g;
		g2.setColor(getBackground());	
		g2.fillRoundRect(0, 0, getWidth(), getHeight(), curvedRadius, curvedRadius);
		
		
		//if(isATab==true) {
			
		//	g2.fillRect(0, getHeight()/2, getWidth(), getHeight()/2);
		//}
		//if(bottomLine==true) {
		//	g2.setColor(Color.black);
		//	g2.fillRect(0, getHeight()-2, getWidth(), 2);
		//}
		super.paint(g);	
	}
	public void setAsATab(boolean bool) {
		isATab= bool;
	}
	public void setBottomLineVisible(boolean bool) {
		bottomLine= bool;
	}

	//setters and getters
	public void setCurvatureRadius(int curveValue) {
		curvedRadius= curveValue;
	}
	public int getCurvatureRadius() {
		return curvedRadius;
	}
	
}
