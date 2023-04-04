package sweng.group.one.client_app_desktop.uiElements;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import sweng.group.one.client_app_desktop.presentation.Presentation;

	public class PresentationBox extends JPanel{	
		
		private JPanel interactionPanel;
		private JPanel cursorPanel;
		private Presentation presentation;
		private Canvas canvas;
		private ArrayList<Layer>layers;
		private Layer current;
		/*
		 *  PopUps:
		 */
		private JButton popupButton;
		private Rectangle popupBounds;
		private JPanel paintPopup;
		private JSlider brushSizeSlider;
		private JColorChooser colorChooser;
		
		/*
		 *  MODES:
		 */
		private boolean paintMode;
		private int numberOfPaintLayers;
		private boolean eraserMode;
		
		private int brushSize; 
		private Color paintColor;
		
		private boolean sizeChanged;
		ArrayList<BufferedImage>graphics;
		
		private BufferedImage currentLayer;
		private BasicStroke stroke;
		private Point mousePos;
		
		public PresentationBox(Color background) {
			create(background);
			createPopups();
		}
		private void create(Color background) {
			this.setLayout(null);
			this.setBackground(background);
			graphics= new ArrayList<BufferedImage>();
			layers= new ArrayList<Layer>();
			sizeChanged=false;
			
			/*
			 *  New Presentation
			 */
			presentation = new Presentation() {
				public void paint(Graphics g) {
					super.paint(g);
					Graphics2D g2= (Graphics2D) g.create();
					for(int i=0;i<layers.size();i++) {
						g2.drawImage(layers.get(i).getActualPaintedImage(),0,0,null);
				
					}
					popupButton.repaint();
				}
			};
			this.add(presentation);
			//presentation.setOpaque(false);
			presentation.setBackground(Color.white);
			paintMode= false;
			numberOfPaintLayers=0;
			eraserMode= false;
			brushSize=10;
			paintColor= Color.black;
			interactionPanel= new JPanel();
			interactionPanel.setOpaque(false);
			interactionPanel.setLayout(null);
			this.add(interactionPanel);
			interactionPanel.addMouseListener(new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mousePressed(MouseEvent e) {
					mousePos= e.getPoint();
					setPopupsVisible(false);
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
			interactionPanel.addMouseMotionListener(new MouseMotionListener() {

				@Override
				public void mouseDragged(MouseEvent e) {
					if(paintMode==true) {
						if(mousePos==null) {
							mousePos= e.getPoint();
						}
						cursorPanel.setVisible(false);
						//	Graphics2D g2 = (Graphics2D) graphics.get(graphics.size()-1).createGraphics();
						BufferedImage imToPaintOn= current.getNewPaintedImage();
						Graphics2D g2 =(Graphics2D) imToPaintOn.createGraphics();
						g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
						paintColor= colorChooser.getColor();	
						g2.setPaint(paintColor);
						brushSize= brushSizeSlider.getValue();
						stroke =new BasicStroke(brushSize, BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND);
							g2.setStroke(stroke);
							g2.drawLine((int)mousePos.getX(),(int)mousePos.getY(),e.getX(), e.getY());
							presentation.repaint();
							mousePos= e.getPoint();
							current.addImage(imToPaintOn);
							g2.dispose();
					}else if(eraserMode==true) {
						if(mousePos==null) {
							mousePos= e.getPoint();
						}				cursorPanel.setVisible(false);
							BufferedImage imToPaintOn= current.getActualPaintedImage();
							Graphics2D g2 = (Graphics2D)imToPaintOn.createGraphics();
							g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
						//Draws a transulcant square around mouse pos
							for(int y= e.getY()-(brushSize/2);y<e.getY()+(brushSize/2);y++) {
								for(int x= e.getX()-(brushSize/2);x<e.getX()+(brushSize/2);x++) {
									current.getActualPaintedImage().setRGB(x, y, Color.TRANSLUCENT);
								}
							}
							presentation.repaint();
							mousePos= e.getPoint();
							
							g2.dispose();
					}
					
				}
		

				@Override
				public void mouseMoved(MouseEvent e) {

				}
				
			});
		
			cursorPanel= new JPanel();
			cursorPanel.setOpaque(false);
			cursorPanel.setLayout(null);
			this.add(cursorPanel);
			cursorPanel.setVisible(false);
			cursorPanel.addMouseListener(new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mousePressed(MouseEvent e) {
					if(cursorPanel.isVisible()==true) {
						cursorPanel.setVisible(false);
						System.out.println("cursor invisible");
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
			
			
			
			this.setComponentZOrder(cursorPanel, 0);
			this.setComponentZOrder(interactionPanel, 1);
			this.setComponentZOrder(presentation, 2);
		
		}
		private void createPopups() {
			paintPopup= new JPanel(){
				public void paint(Graphics g) {
					Graphics2D g2= (Graphics2D) g.create();
					g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);;
					g2.setColor(new Color(0,0,0));
					g2.drawRoundRect(0, 0, this.getWidth(), this.getHeight(), 10, 10);
					super.paint(g);
				}
			};
			paintPopup.setOpaque(false);
			paintPopup.setLayout(null);
			colorChooser = new JColorChooser();
			brushSizeSlider = new JSlider();
			paintPopup.add(colorChooser);
			paintPopup.add(brushSizeSlider);
			colorChooser.setVisible(true);
			colorChooser.setBackground(new Color(0,0,0,255));
			colorChooser.setOpaque(false);
			
			brushSizeSlider.setVisible(true);
			brushSizeSlider.setOpaque(false);
			brushSizeSlider.setBackground(new Color(0,0,0,255));
			
			brushSizeSlider.addChangeListener(new ChangeListener() {

				@Override
				public void stateChanged(ChangeEvent e) {
					cursorPanel.setVisible(true);
					System.out.println("cursor visible");
				}
				
			});
			brushSizeSlider.addMouseMotionListener(new MouseMotionListener() {

				@Override
				public void mouseDragged(MouseEvent e) {
					cursorPanel.repaint();
					Graphics g = cursorPanel.getGraphics();
					int x= e.getX()+ brushSizeSlider.getX()+ paintPopup.getX();
					int y= brushSizeSlider.getY()+paintPopup.getY()+brushSizeSlider.getHeight()/2;
					//g.clearRect(0, 0, cursorPanel.getWidth(), cursorPanel.getHeight());
					g.setColor(colorChooser.getColor());
					g.fillOval(x-(brushSizeSlider.getValue()/2),y- (brushSizeSlider.getValue()/2),
							brushSizeSlider.getValue(), brushSizeSlider.getValue());
					
					
				}

				@Override
				public void mouseMoved(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
			});
			interactionPanel.add(paintPopup);
			paintPopup.setVisible(false);
			popupButton= new JButton() {
				public void paint(Graphics g) {
					Graphics2D g2= (Graphics2D)g.create();
					g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
					g2.setColor(new Color(100,100,100));
					g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 10, 10);
					int thickness= 2;
					g2.setColor(new Color(200,200,200));
					g2.fillRect((this.getWidth()-thickness)/2, thickness, thickness, this.getHeight()-(2*thickness));
					g2.fillRect(thickness, (this.getHeight()-thickness)/2, this.getWidth()-(2*thickness), thickness);
				}
			};
			popupButton.setOpaque(false);
			interactionPanel.add(popupButton);
			popupButton.addMouseListener(new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mousePressed(MouseEvent e) {
					if(paintMode==true) {
						paintPopup.setVisible(true);
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
	
			
		}
		
		public void setSize(int width,int height) {
			
			//must be 16:9 ratio so
			int widthFactor= 16;
			int heightFactor= 9;
			
			int newHeight= (width/widthFactor)*heightFactor;

			super.setSize(width, newHeight);
			presentation.setSize(width,newHeight);
			presentation.setLocation(0,0);

			interactionPanel.setSize(width, newHeight);
			interactionPanel.setLocation(0, 0);
			
			popupBounds= new Rectangle(width/2,0,width/2,newHeight);
			paintPopup.setBounds(popupBounds);
			colorChooser.setBounds(new Rectangle(0,0,popupBounds.width,(popupBounds.height/5)*3));
			brushSizeSlider.setBounds(new Rectangle(0,(popupBounds.height/5)*3,popupBounds.width,(popupBounds.height/5)*2));
			
			popupButton.setSize(20, 20);
			popupButton.setLocation(width-20, 0);
			
			cursorPanel.setSize(width, newHeight);
			cursorPanel.setLocation(0, 0);
		
		}
		private void setPopupsVisible(boolean bool) {
			paintPopup.setVisible(bool);
		}
		public void paint(Graphics g) {
			super.paint(g);	
		}
		public void goToLayer(int layerNumber) {
			currentLayer= graphics.get(layerNumber);
		}
		public void newLayer(String layerName) {
			BufferedImage emptyImage = new BufferedImage(presentation.getWidth(),presentation.getHeight(),BufferedImage.TYPE_INT_ARGB);
			Layer lay=  new Layer();
			lay.create(layerName,emptyImage);
			layers.add(lay);
			current= lay;
			
		}
		public void removeLastGraphicOnCurrentLayer() {
			current.removeLastGraphic();
			presentation.repaint();
		}
		public void replaceLastGraphicsOnCurrentLayer() {
			current.replaceLastGraphic();
			presentation.repaint();
		}
		public void removeLayer(int layerNumber) {
			graphics.remove(layerNumber);
		}
		public void moveLayerTo(int oldPosition, int newPosition) {
			BufferedImage layer= graphics.get(oldPosition);
			graphics.set(newPosition, layer);
		}
		public void refreshCanvas() {
			canvas.validate();
		}
		public void setPaintMode(boolean bool) {
			if(bool==true) {
				addNewPaintLayer();
				popupButton.setVisible(true);
			}
			paintMode=bool;
		}
		public void addNewPaintLayer() {
			numberOfPaintLayers++;
			newLayer("Paint "+ numberOfPaintLayers);
		}
		public boolean isPaintMode() {
			return paintMode;
		}
		public void setEraserMode(boolean bool) {
			eraserMode=bool;

		}
		public boolean isEraserMode() {
			return eraserMode;
		}
	}
	
	 class Layer {
		 BufferedImage emptyImage;
		 BufferedImage paintedImage;
	
		 ArrayList<BufferedImage>imageList;
		 int positionInList;
		 
		 int layerNumber;
		 String layerName;
		public void create(String layerName, BufferedImage emptyImage) {
			this.layerName= layerName;
			this.emptyImage= emptyImage;
			paintedImage= emptyImage;
			imageList= new ArrayList<BufferedImage>();
			imageList.add(emptyImage);
			positionInList= imageList.size()-1;
	
		}
		public void addImage(BufferedImage im) {
			imageList.add(im);
			positionInList= imageList.size()-1;
		}

		public  ArrayList<BufferedImage> getImageList() {
			return imageList;
		}
		public String getLayerName() {
			return layerName;
		}
		
		public void removeLastGraphic() {
			if((imageList.size()!=0)&&(positionInList>0)) {
				positionInList= positionInList-1;
			}
		}
		public void replaceLastGraphic() {
			if(positionInList<(imageList.size()-1)) {
				positionInList= positionInList+1;
				System.out.print("REPLACE");
			}
		}
		public BufferedImage getNewPaintedImage() {
			BufferedImage newIm = new BufferedImage(emptyImage.getWidth(),emptyImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2= newIm.createGraphics();
			g2.drawImage(imageList.get(positionInList), 0, 0, null);
			return newIm;
		}
		public BufferedImage getActualPaintedImage() {
			return imageList.get(positionInList);
		}
		
		
	}

