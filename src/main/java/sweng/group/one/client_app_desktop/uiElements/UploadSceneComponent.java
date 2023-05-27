package sweng.group.one.client_app_desktop.uiElements;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import sweng.group.one.client_app_desktop.sceneControl.ComponentInterface;

/**
 * @author sophiemaw
 *
 */
public class UploadSceneComponent extends JPanel implements ComponentInterface{
	protected Color main;
	protected Color secondary;

	protected Rectangle r;

	
	public UploadSceneComponent() {
		this.setOpaque(false);
		r= new Rectangle();
	}

	public void setMarginBounds(int left, int top, int right, int bottom) {
		r.setBounds(left, top, right,bottom);
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2= (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

		int w = getWidth();

		Color color1 = main;
		Color color2;
		if(main==colorDark) {
			color2 = colorDarkHighlight;
		}else {
			color2 = colorLightHighlight;
		}

		GradientPaint gp = new GradientPaint(w/2, 0, color2, w/2, 100, color1);

		g2.setPaint(gp);
		g2.fillRoundRect(r.x, r.y, this.getWidth()-r.width-r.x, this.getHeight()-(r.height)-r.y, curvatureRadius, curvatureRadius);
		g2.dispose();
		super.paintComponent(g);
	}

}
