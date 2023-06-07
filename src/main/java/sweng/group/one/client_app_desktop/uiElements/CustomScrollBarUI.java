package sweng.group.one.client_app_desktop.uiElements;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class CustomScrollBarUI extends BasicScrollBarUI {
	protected Color baseThumb;
	protected Color highlightThumb;
	protected Color shadowThumb;
	
	public CustomScrollBarUI() {
		baseThumb= new Color(100,100,100,100);
		highlightThumb = new Color(150,150,150,100);
		shadowThumb = new Color(200,200,200,150);
		thumbDarkShadowColor= shadowThumb;
		thumbLightShadowColor= new Color(75,75,75,150);
		thumbHighlightColor = highlightThumb;
	}
	
    @Override
	public void paintTrack(Graphics g, JComponent c, Rectangle r) {
		//Dont paint track
	}
    
    @Override
	public void paintThumb(Graphics g, JComponent c, Rectangle r) {
		g.setColor(shadowThumb);
		g.fillRoundRect(r.x, r.y, r.width, r.height, r.width/2, r.width/2);
	}
}
