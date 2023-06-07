package sweng.group.one.client_app_desktop.media;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

import sweng.group.one.client_app_desktop.presentation.PresElement;
import sweng.group.one.client_app_desktop.presentation.Slide;

public class ImageElement extends PresElement{
	BufferedImage image;
	public ImageElement(Point pos, int width,int height, float duration, Slide slide,BufferedImage image) {
		super(pos, height, height, duration, slide);
		this.image=image;
		component = new JComponent() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 4004719683804642451L;

			public void paint(Graphics g) {
				Graphics2D g2= (Graphics2D) g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.drawImage(resizeImageToFitComponent(),0,0,this);
				g2.dispose();
				super.paint(g);
			}
		};
		component.setOpaque(false);
	}
	private Image resizeImageToFitComponent() {
		int newImWidth=0;
		int newImHeight=0;
		if((component.getWidth()/component.getHeight())<(image.getWidth()/image.getHeight())) {
			newImWidth= (component.getHeight()*image.getWidth())/image.getHeight();
			newImHeight= component.getHeight();
		}else {
			newImHeight= (component.getWidth()*image.getHeight())/image.getWidth();
			newImWidth= component.getWidth();
		}
		component.setSize(newImWidth, newImHeight);
		return image.getScaledInstance(newImWidth, newImHeight, java.awt.Image.SCALE_SMOOTH);
	}
	
}
