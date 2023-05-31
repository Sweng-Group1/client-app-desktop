package sweng.group.one.client_app_desktop.media;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

import sweng.group.one.client_app_desktop.presentation.PresElement;
import sweng.group.one.client_app_desktop.presentation.Slide;

public class GraphicsElement extends PresElement{

	ArrayList<BufferedImage>backgroundImageList;
	ArrayList<BufferedImage>backgroundImageListFuture;
	
	public GraphicsElement(Point pos, 
			int width, 
			int height, 
			float duration, 
			Slide slide) {
					

	super(pos, width, height, duration, slide);
	
		type= "GRAPHIC";
		backgroundImageList= new ArrayList<BufferedImage>();
		backgroundImageListFuture= new ArrayList<BufferedImage>();
		
		//backgroundImageList.add(new BufferedImage(width,height, BufferedImage.TYPE_INT_ARGB));
		
		component= new JPanel() {
			public void paint(Graphics g) {
				Graphics2D g2= (Graphics2D)g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				if(backgroundImageList.size()>0) {
					System.out.println("drawingIMage");
					g2.drawImage(backgroundImageList.get(backgroundImageList.size()-1).getScaledInstance(this.getWidth(), this.getHeight(), java.awt.Image.SCALE_SMOOTH),0,0,this);
				}
				g2.dispose();
				super.paint(g);
			}
		};
		component.setOpaque(false);
	}
	public void goBackInImageList(int i) {
		if((backgroundImageList.size()-1)>i) {
			for(int j=0;j<i;j++) {
				System.out.println("Total images: "+ (backgroundImageList.size()+backgroundImageListFuture.size()));
				System.out.println("Current image to draw: "+ (backgroundImageList.size()-1));	
				BufferedImage imageToMove= backgroundImageList.get(backgroundImageList.size()-1);	
				backgroundImageList.remove(backgroundImageList.size()-1);
				backgroundImageListFuture.add(imageToMove);
				component.repaint();
				
			}
			
		}
	}
	public void moveInImageList(int i) {
		if(i>=1) {
			if(backgroundImageListFuture.size()>i) {
				BufferedImage imageToMove= backgroundImageListFuture.get(backgroundImageListFuture.size()-1);	
				backgroundImageListFuture.remove(imageToMove);
				backgroundImageList.add(imageToMove);
				component.repaint();
				
			}
		}else if(i<=(-1)) {
			if(backgroundImageList.size()>(1+i)) {
				for(int j=0;j<i;j++) {
					System.out.println("Total images: "+ (backgroundImageList.size()+backgroundImageListFuture.size()));
					System.out.println("Current image to draw: "+ (backgroundImageList.size()-1));	
				BufferedImage imageToMove= backgroundImageList.get(backgroundImageList.size()-1);	
				backgroundImageList.remove(imageToMove);
				backgroundImageListFuture.add(imageToMove);
			//	System.out.println("Total images: "+ (backgroundImageList.size()+backgroundImageListFuture.size()));
				
				component.repaint();
			}}
		}
	}
	public BufferedImage getCurrentImage() {
		if(backgroundImageList.size()==0) {
			backgroundImageList.add(new BufferedImage(component.getWidth(),component.getHeight(),BufferedImage.TYPE_INT_ARGB));
		}
		return backgroundImageList.get(backgroundImageList.size()-1);
	}
	public void addBufferedImageToGraphicsList(BufferedImage im) {
		backgroundImageList.add(im);
		component.repaint();
	}
	
	public void setDuration(long duration) {
		this.duration=duration;
	}
	public void setColor(Color color) {
		int[] pixels= null;
		BufferedImage currentImage= getCurrentImage();
		for (int x = 0; x < currentImage.getWidth(); x++) {
		    for (int y = 0; y < currentImage.getHeight(); y++) {
		    	Color colorVal = new Color(currentImage.getRGB(x, y), true);
		    	if(colorVal.getAlpha()>0) {
		    		currentImage.setRGB(x, y,color.getRGB());
		    	}
		    }
		}
		
		addBufferedImageToGraphicsList(currentImage);
	}
}
