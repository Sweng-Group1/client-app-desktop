package sweng.group.one.client_app_desktop.uiElements;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
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
		boolean isPainting;
		boolean isAddingText;
		boolean isMoving;
		
		MouseListener paintButtonListener;
		MouseMotionListener paintMouseMotionListener;
		MouseListener paintMouseListener;
		
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
			
			isPainting=false;
			isAddingText=false;
			isMoving=false;
			
		}
		public void turnOffPaintMode() {
			if(isPainting==true) {
				isPainting=false;
				paintButton.resetBackground();
			}
		}
		
		private void addMouseListeners() {
			eraserButton.addMouseListener(new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					
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
			
			graphicsBox.getDeleteLayerButton().addMouseListener(new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mousePressed(MouseEvent e) {
					if(isPainting==true) {
						isPainting=false;
						paintButton.resetBackground();
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
			graphicsBox.getAddLayerButton().addMouseListener(new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mousePressed(MouseEvent e) {
					if(isPainting==true) {
						isPainting=false;
						paintButton.resetBackground();
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
			
			paintButtonListener= new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mousePressed(MouseEvent e) {
					if(isPainting==false) {
						paintButton.setBackgroundToHover();
						isPainting=true;
						if((tabPane.getCurrentSlide().getElements().size()==0)||(graphicsBox.getSelectedLayer().getType()!="GRAPHIC")) {
							graphicsBox.addGraphicLayer();
						}
						graphicsBox.getSelectedLayer().getComponent().addMouseMotionListener(paintMouseMotionListener);
						graphicsBox.getSelectedLayer().getComponent().addMouseListener(paintMouseListener);
					}else {
						isPainting=false;
						paintButton.resetBackground();
						graphicsBox.getSelectedLayer().getComponent().removeMouseMotionListener(paintMouseMotionListener);
						graphicsBox.getSelectedLayer().getComponent().removeMouseListener(paintMouseListener);
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
			};
			paintButton.addMouseListener(paintButtonListener);
			paintMouseListener = new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent e) {
					
					
				}

				@Override
				public void mousePressed(MouseEvent e) {
					isCurrentlyPainting=true;
					mousePos= e.getPoint();
				}

				@Override
				public void mouseReleased(MouseEvent e) {
					mousePos=null;
					isCurrentlyPainting=false;
					
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
			paintMouseMotionListener= new MouseMotionListener() {

				@Override
				public void mouseDragged(MouseEvent e) {
					if(isPainting==true) {
					if(graphicsBox.getSelectedLayer().getType()=="GRAPHIC") {
						System.out.println("PAINTING");
						GraphicsElement el= (GraphicsElement)graphicsBox.getSelectedLayer();
						if(el.getCurrentImage().getWidth()>0) {
						BufferedImage im= el.getCurrentImage();
						
						Graphics2D g2= (Graphics2D)im.getGraphics().create();
						g2.setStroke(paintStroke);
						g2.setColor(paintColor);
						g2.drawLine(mousePos.x, mousePos.y, e.getX(), e.getY());
						//g2.drawImage(im, 0, 0, null);
						el.addBufferedImageToGraphicsList(im);
						el.getComponent().validate();
						mousePos= e.getPoint();
						}
					}
					}	
				}
					
				@Override
				public void mouseMoved(MouseEvent e) {
					
					
				}
			};
			moveButton.addMouseListener(new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent e) {
					if(isMoving==false) {
						isMoving=true;
						moveButton.setBackgroundToHover();
						tabPane.setMovingElement(graphicsBox.getSelectedLayer());
					}else {
						isMoving=false;
						moveButton.resetBackground();
						tabPane.exitMovingElement();
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
	

