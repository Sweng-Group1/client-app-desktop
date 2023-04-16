package sweng.group.one.client_app_desktop.uiElements;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JButton;

import sweng.group.one.client_app_desktop.media.GraphicsElement;


public class CustomToolBar extends UploadSceneComponent{
		List<CircleButton>buttons;
		CircleButton moveButton,paintButton,eraserButton, textButton, shapesButton, confirmButton,
						exitButton,forwardButton,backButton, downloadButton;
			
		CustomTabPanel tabPane;
		CustomGraphicsBox graphicsBox;
		
		boolean paintMode;
		boolean eraseMode;
		boolean textMode;
		boolean moveMode;
		boolean shapeMode;
		
		MouseListener slideMouseListener;
		MouseMotionListener slideMouseMotionListener;
		
		
		
		int paintSize;
		Color paintColor;
		BasicStroke paintStroke;
		boolean isCurrentlyPainting;
		
		Point mousePos;
		public CustomToolBar(CustomGraphicsBox graphicsBox,CustomTabPanel tabPane) {
			this.graphicsBox= graphicsBox;
			this.tabPane=tabPane;
			initialise();
			addMouseListeners();
			
		}
		private void initialise() {
			this.setOpaque(false);	
			this.setLayout(null);
			/*
			 *  variables set for now:
			 */
			paintSize= 10;
			paintColor=Color.black;
			paintStroke= new BasicStroke(paintSize,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND);
			isCurrentlyPainting=false;
			
			buttons= Collections.synchronizedList(new ArrayList<>());
			
			
			//Create buttons
			moveButton = new CircleButton();
			paintButton = new CircleButton();
			eraserButton = new CircleButton();
			textButton = new CircleButton();
			shapesButton = new CircleButton();
			confirmButton = new CircleButton();
			exitButton = new CircleButton();
			forwardButton = new CircleButton();
			backButton = new CircleButton();
			downloadButton = new CircleButton();
			
			moveButton.setMainBackground(Color.white);
			paintButton.setMainBackground(Color.white);
			eraserButton.setMainBackground(Color.white);
			textButton.setMainBackground(Color.white);
			shapesButton.setMainBackground(Color.white);
			confirmButton.setMainBackground(Color.white);
			exitButton.setMainBackground(Color.white);
			forwardButton.setMainBackground(Color.white);
			backButton.setMainBackground(Color.white);
			downloadButton.setMainBackground(Color.white);
			
			
			try {
				moveButton.setImageIcon(ImageIO.read(new File("./Assets/cursor.png")));
				paintButton.setImageIcon(ImageIO.read(new File("./Assets/pencil.png")));
				eraserButton.setImageIcon(ImageIO.read(new File("./Assets/eraser.png")));
				textButton.setImageIcon(ImageIO.read(new File("./Assets/text.png")));
				shapesButton.setImageIcon(ImageIO.read(new File("./Assets/shapes.png")));
				confirmButton.setImageIcon(ImageIO.read(new File("./Assets/shapes.png")));
				exitButton.setImageIcon(ImageIO.read(new File("./Assets/tick.png")));
				forwardButton.setImageIcon(ImageIO.read(new File("./Assets/cross.png")));
				backButton.setImageIcon(ImageIO.read(new File("./Assets/forward.png")));
				downloadButton.setImageIcon(ImageIO.read(new File("./Assets/download.png")));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
			buttons.add(moveButton);
			this.add(moveButton);	
			buttons.add(paintButton);
			this.add(paintButton);	
			buttons.add(eraserButton);
			this.add(eraserButton);	
			buttons.add(textButton);
			this.add(textButton);	
			buttons.add(shapesButton);
			this.add(shapesButton);	
			buttons.add(confirmButton);
			this.add(confirmButton);	
			buttons.add(exitButton);
			this.add(exitButton);	
			buttons.add(forwardButton);
			this.add(forwardButton);	
			buttons.add(backButton);
			this.add(backButton);	
			buttons.add(downloadButton);
			this.add(downloadButton);	
			
			resetMode();
			tabPane.getCurrentSlide().addMouseListener(slideMouseListener);
			tabPane.getCurrentSlide().addMouseMotionListener(slideMouseMotionListener);
			
		}
		public void resetMode() {
			paintMode=false;
			paintButton.resetBackground();
			paintButton.repaint();
			eraseMode=false;
			eraserButton.resetBackground();
			eraserButton.repaint();
			textMode=false;
			textButton.resetBackground();
			textButton.repaint();
			moveMode=false;
			moveButton.resetBackground();
			moveButton.repaint();
			shapeMode=false;
			shapesButton.resetBackground();
			shapesButton.repaint();
			
			this.validate();
			this.repaint();
		}
		
		private void addMouseListeners() {
			slideMouseListener = new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mousePressed(MouseEvent e) {
					if(textMode==true) {
						float duration=0;
						int width= tabPane.getCurrentSlide().getPointWidth();
						int height= tabPane.getCurrentSlide().getPointHeight();
						
						graphicsBox.addTextLayer("Text", null, paintSize, paintColor, duration, e.getPoint(), width/6, height/4 ,tabPane.getCurrentSlide());
						System.out.println("add text");
					}
					if(shapeMode==true) {
						graphicsBox.addShapeLayer("CIRCLE", e.getPoint());
						System.out.println("add shape");
					}
				
					mousePos= e.getPoint();
					
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
				
			};
			slideMouseMotionListener = new MouseMotionListener() {

				@Override
				public void mouseDragged(MouseEvent e) {
					if(paintMode==true) {
						if(graphicsBox.getSelectedLayer().getType()=="GRAPHIC") {
							GraphicsElement el= (GraphicsElement) graphicsBox.getSelectedLayer();
							if(el.getCurrentImage().getWidth()>0) {
								BufferedImage im= el.getCurrentImage();	
								Graphics2D g2= (Graphics2D)im.getGraphics().create();
								g2.setStroke(paintStroke);
								g2.setColor(paintColor);
								g2.drawLine(mousePos.x, mousePos.y, e.getX(), e.getY());
								el.addBufferedImageToGraphicsList(im);
								el.getComponent().validate();
								mousePos= e.getPoint();
							}
						}
					}
					if(eraseMode==true) {
						if(graphicsBox.getSelectedLayer().getType()=="GRAPHIC") {	
							GraphicsElement el= (GraphicsElement)graphicsBox.getSelectedLayer();
							if(el.getCurrentImage().getWidth()>0) {
								BufferedImage im= el.getCurrentImage();
								BufferedImage newIm = new BufferedImage(im.getWidth(),im.getHeight(),BufferedImage.TYPE_INT_ARGB);
								Graphics2D g2= (Graphics2D)newIm.getGraphics().create();
								Rectangle entireImage =new Rectangle(im.getWidth(), im.getHeight());
								Area clip = new Area(entireImage);
								clip.subtract(new Area(new Ellipse2D.Float(e.getX()-(paintSize/2), e.getY(), paintSize, paintSize)));
								g2.clip(clip);
							    g2.drawImage(im, 0, 0, null);
								g2.dispose();
							
								el.addBufferedImageToGraphicsList(newIm);
								el.getComponent().validate();
								mousePos= e.getPoint();
							}
						}
					}
					
				}

				@Override
				public void mouseMoved(MouseEvent e) {
					mousePos= e.getPoint();
					
				}
				
			};
			
			/*
			 *  Button listeners:
			 */
			shapesButton.addMouseListener(new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mousePressed(MouseEvent e) {
					if(shapeMode==false) {
						resetMode();
						shapeMode=true;
						shapesButton.setBackgroundToHover();
					}else {
						resetMode();
					}
					
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
			backButton.addMouseListener(new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mousePressed(MouseEvent e) {
					resetMode();
					if(graphicsBox.getSelectedLayer().getType()=="GRAPHIC") {
						GraphicsElement g= (GraphicsElement) graphicsBox.getSelectedLayer();
						g.goBackInImageList(2);
						g.getComponent().validate();
						g.getComponent().repaint();
					}
					
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
			textButton.addMouseListener(new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent e) {
					
					
				}

				@Override
				public void mousePressed(MouseEvent e) {
					if(textMode==false) {
						resetMode();
						textMode=true;
						textButton.setBackgroundToHover();
					}else {
						resetMode();
					}
					
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
			eraserButton.addMouseListener(new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mousePressed(MouseEvent e) {
			
					if(eraseMode==false) {
						resetMode();
						eraseMode=true;
						eraserButton.setBackgroundToHover();
					}
					else {
						resetMode();
					}
					
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
			graphicsBox.getDeleteLayerButton().addMouseListener(new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mousePressed(MouseEvent e) {
					resetMode();
					
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
			graphicsBox.getAddLayerButton().addMouseListener(new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mousePressed(MouseEvent e) {
					resetMode();	
					graphicsBox.getSelectedLayer().getComponent().addMouseListener(slideMouseListener);
					graphicsBox.getSelectedLayer().getComponent().addMouseMotionListener(slideMouseMotionListener);
					
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
			paintButton.addMouseListener(new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mousePressed(MouseEvent e) {
					
					if(paintMode==false) {
						resetMode();
						paintMode=true;
						paintButton.setBackgroundToHover();
						
					}else {
						resetMode();
					}
					
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
			moveButton.addMouseListener(new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent e) {
					if(moveMode==false) {
						resetMode();
						moveMode=true;
						moveButton.setBackgroundToHover();
						tabPane.setMovingElement(graphicsBox.getSelectedLayer());
					}else {
						resetMode();
					}
					
					
				}

				@Override
				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub
					
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
			tabPane.getAddTabButton().addMouseListener(new MouseListener() {
				
			

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				tabPane.getCurrentSlide().addMouseListener(slideMouseListener);
				tabPane.getCurrentSlide().addMouseMotionListener(slideMouseMotionListener);
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
			
	}
		
		//OVERIDED METHODS:
		public void setBounds(int x, int y,int width, int height) {
			super.setBounds(x,y,width, height);
		}
		public void setMarginBounds(int x,int y, int wi, int he) {
			super.setMarginBounds(x, y, wi, he);
			
			int width= this.getWidth()- x- wi;
			int height= this.getHeight()- y- he;
			
			int widthOfSections= width/10;
			int w=0;
			int gw=0;
			int gh=0;
			if(widthOfSections<height) {
				w= (widthOfSections/10)*8;
				gw= widthOfSections/10;
				gh= (height-w)/2;
			}else {
				w= (height/10)*8;
				gh= height/10;
				gw= (widthOfSections-w)/2;
			}
			
			for(int i=0;i<buttons.size();i++) {
				buttons.get(i).setSize(w);
				buttons.get(i).setLocation(x+gw+(i*(widthOfSections)), y+gh);
			}
			
		
		}
		public JButton getPaintButton() {
			return paintButton;
		}
		public JButton getEraserButton() {
			return eraserButton;
		}
		public JButton getBackButton() {
			return backButton;
		}
		public JButton getForwardButton() {
			return forwardButton;
		}
		public JButton getConfirmButton() {
			return confirmButton;
		}
		public JButton getExitButton() {
			return exitButton;
		}
		public JButton getShapeButton() {
			return shapesButton;
		}

	}

		


	
	//SETTERS:
	

