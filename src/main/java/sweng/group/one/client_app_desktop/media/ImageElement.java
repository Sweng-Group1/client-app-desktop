package sweng.group.one.client_app_desktop.media;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.swing.JComponent;

import sweng.group.one.client_app_desktop.presentation.PresElement;
import sweng.group.one.client_app_desktop.presentation.Slide;

public class ImageElement extends PresElement{
	BufferedImage image;
	Image scaledImage;
	URL url;
	public ImageElement(Point pos, int width,int height, float duration, Slide slide,BufferedImage image,URL url) {
		super(pos, height, height, duration, slide);
		this.image=image;
	
		
		type= "IMAGE";
		this.url= url;
		component = new JComponent() {
			public void paint(Graphics g) {
				Graphics2D g2= (Graphics2D) g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.drawImage(scaledImage,0,0,this);
				g2.dispose();
				super.paint(g);
			}
		};
		component.setOpaque(false);
		this.scaledImage=resizeImageToFitWidthHeight(slide.getPointWidth(),slide.getPointHeight());
	}
	private Image resizeImageToFitWidthHeight(int width, int height) {
		int newImWidth=0;
		int newImHeight=0;
		if((width/height)<(image.getWidth()/image.getHeight())) {
			newImWidth= (height*image.getWidth())/image.getHeight();
			newImHeight= height;
		}else {
			newImHeight= (width*image.getHeight())/image.getWidth();
			newImWidth= width;
		}
		component.setSize(newImWidth, newImHeight);
		return image.getScaledInstance(newImWidth, newImHeight, java.awt.Image.SCALE_SMOOTH);
	}
	public BufferedImage getImage() {
		return image;
	}
	public void setWidth(int width) {
		this.width=width;
		resizeImageToFitWidthHeight(width,height);
		this.getComponent().repaint();
	}
	public void setHeight(int height) {
		this.height=height;
		resizeImageToFitWidthHeight(width,height);
		this.getComponent().repaint();
	}
	public URL getURL() {
		return url;
	}
	
}
