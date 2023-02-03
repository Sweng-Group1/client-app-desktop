package sweng.group.one.client_app_desktop.presentation;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Slide extends JPanel {
	
	private int pointWidth;
	private int pointHeight;

	//example constructor
	public Slide(int PtWidth, int PtHeight){
		super();
		this.setPointWidth(PtWidth);
		this.setPointHeight(PtHeight);
	}

	public int getPointWidth() {
		return pointWidth;
	}

	public void setPointWidth(int pointWidth) {
		this.pointWidth = pointWidth;
	}

	public int getPointHeight() {
		return pointHeight;
	}

	public void setPointHeight(int pointHeight) {
		this.pointHeight = pointHeight;
	}
}
