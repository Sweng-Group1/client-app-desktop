package sweng.group.one.client_app_desktop.sceneControl;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JPanel;

	public class PresentationBox extends JPanel{
		
		private Canvas canvas;

		private int xMousePos;
		private int yMousePos;
		
		public PresentationBox(Color background) {
		
			this.setLayout(null);
			canvas= new Canvas();
			this.setBackground(background);

			this.addMouseListener(new MouseListener(){

				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mousePressed(MouseEvent e) {
					xMousePos= e.getX();
					yMousePos= e.getY();
				
					addMouseMotionListener(new MouseMotionListener() {

						@Override
						public void mouseDragged(MouseEvent e) {
							// TODO Auto-generated method stub
							
						}

						@Override
						public void mouseMoved(MouseEvent e) {
							xMousePos= e.getX();
							yMousePos= e.getY();
				
							
						}
						
					});
					
				}

				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
			
				
			});
		
		//	layer= new BufferedImage(getWidth(),getHeight(),BufferedImage.TYPE_INT_ARGB);
		}
		public void setSize(int width,int height) {
			
			//must be 16:9 ratio so
			int widthFactor= 16;
			int heightFactor= 9;
			
			int newHeight= (width/widthFactor)*heightFactor;
			//int newWidth= 1* widthFactor;
			//int newHeight= 1* heightFactor;
			
			/*
			//Increases new width and height by the factors until one cant increase anymore
			while((newWidth+widthFactor<width) && (newHeight+heightFactor<height)) {
				newWidth= newWidth+ widthFactor;
				newHeight= newHeight+ heightFactor;
			}
			*/
			super.setSize(width, newHeight);
		}
		public void paint(Graphics g) {
			super.paint(g);

		
			
			
		}
		public void draw(int layerNumber,Color colour, int strokeSize) {

			
		}
		public void refreshCanvas() {
			canvas.validate();
		}
	}



	///
	//What we need from this class:
	//It needs to check for button presses- it does this by changing a value in a list to true
	//When button is registered, is then uses listeners to do what button wants

