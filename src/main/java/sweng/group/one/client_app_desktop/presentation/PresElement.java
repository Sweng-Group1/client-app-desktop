package sweng.group.one.client_app_desktop.presentation;

import java.awt.Graphics2D;

import javax.swing.JComponent;

public abstract class PresElement extends JComponent{
	private int xPoint;
	private int yPoint;
	private int pointWidth;
	private int pointHeight;
	private float duration;
	
	public abstract void draw(Graphics2D g, int slidePointWidth, int slidePointHeight);
}
