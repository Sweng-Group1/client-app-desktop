package sweng.group.one.client_app_desktop.uiElements;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JButton;

import sweng.group.one.client_app_desktop.media.GraphicsElement;
import sweng.group.one.client_app_desktop.presentation.PresElement;
import sweng.group.one.client_app_desktop.presentation.Slide;
import sweng.group.one.client_app_desktop.sceneControl.ComponentInterface;



/**
 * @author sophiemaw
 * This class creates a panel with toolBar buttons
 * When a button is clicked, the selectedElementTyoe gets updated to that type
 * i.e "MOVE","GRAPHIC","ERASE","CIRCLE","RECTANGLE" and the button thats pressed's border 
 * is painted
 * When clicked, all other buttons are reset (borders set to false)
 * TabPane will tell this class what slide is visible, and then this class will add a mouseListener 
 * and mouseMotionListener to that slide. These listeners allow elements to be placed and 
 * graphicsElements to be manipulated.
 * The elementsPanel will tell this class what element is selected and then if it's type 
 * is "GRAPHIC" and the selectedElementType is "GRAPHIC" that user can paint onto element
 * 
 *
 */
public class CustomToolBar extends UploadSceneComponent implements ComponentInterface{
	String selectedElementType;
	List<CircleButton>buttons;
	CircleButton moveButton,paintButton,eraserButton, textButton, shapesButton, confirmButton,
					exitButton,forwardButton,backButton, downloadButton;
	
	int strokeThickness;
	Stroke paintStroke;
	Color paintColor;
	Color textElementColor;
	Point mousePos;

	PresElement selectedElement;
	
	
	public CustomToolBar() {
		this.setOpaque(false);	
		this.setLayout(null);
		this.main= colorLight;
		this.secondary= colorDark;
		buttons= Collections.synchronizedList(new ArrayList<>());
		selectedElementType=new String();
		paintColor=Color.black;
				
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
			moveButton.setImageIcon(ImageIO.read(new File("./assets/cursor.png")));
			paintButton.setImageIcon(ImageIO.read(new File("./assets/pencil.png")));
			eraserButton.setImageIcon(ImageIO.read(new File("./assets/eraser.png")));
			textButton.setImageIcon(ImageIO.read(new File("./assets/text.png")));
			shapesButton.setImageIcon(ImageIO.read(new File("./assets/shapes.png")));
			confirmButton.setImageIcon(ImageIO.read(new File("./assets/tick.png")));
			exitButton.setImageIcon(ImageIO.read(new File("./assets/cross.png")));
			forwardButton.setImageIcon(ImageIO.read(new File("./assets/forward.png")));
			backButton.setImageIcon(ImageIO.read(new File("./assets/back.png")));
			downloadButton.setImageIcon(ImageIO.read(new File("./assets/download.png")));
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
		
		paintButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(paintButton.isBorderPainted()==false) {
					resetButtonPresses();
					selectedElementType= "GRAPHIC";
					paintButton.setBorderPainted(true);
				}else {
					selectedElementType= "";
					paintButton.setBorderPainted(false);
				}
				
			}
			
		});
		eraserButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(eraserButton.isBorderPainted()==false) {
					resetButtonPresses();
					selectedElementType= "ERASER";
					eraserButton.setBorderPainted(true);
				}else {
					selectedElementType= "";
					eraserButton.setBorderPainted(false);
				}			
			}
			
		});
		shapesButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(shapesButton.isBorderPainted()==false) {
					resetButtonPresses();
					selectedElementType= "CIRCLE";
					shapesButton.setBorderPainted(true);
				}else {
					selectedElementType= "";
					shapesButton.setBorderPainted(false);
				}			
			}
			
		});
		textButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(textButton.isBorderPainted()==false) {
					resetButtonPresses();
					selectedElementType= "TEXT";
					textButton.setBorderPainted(true);
				}else {
					selectedElementType= "";
					textButton.setBorderPainted(false);
				}	
				
			}
			
		});
		moveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(moveButton.isBorderPainted()==false) {
					resetButtonPresses();
					selectedElementType= "MOVE";
					moveButton.setBorderPainted(true);
				}else {
					selectedElementType= "";
					moveButton.setBorderPainted(false);
				}	
				
			}
			
		});
	
		
		selectedElement= null;
		setPaintThickness(10);
	}
	private void resetButtonPresses() {
		paintButton.setBorderPainted(false);
		textButton.setBorderPainted(false);
		shapesButton.setBorderPainted(false);
		eraserButton.setBorderPainted(false);
		moveButton.setBorderPainted(false);
	}
	
	
	/**
	 * @param presElement 
	 * Called within the elementsPanel to set the selectedElement to specific element
	 */
	public void setSelectedElement(PresElement presElement) {
		selectedElement= presElement;
	}

	/**
	 * @param elementPanel - when slide is clicked, whichever selectedElement from the buttons 
	 * 							is selected, a new Element is created within elementsPanel 
	 * @param slide - adds a listener to slide
	 * Called within tabPane 
	 */
	public void setGeneralToolBarListenersForSlide(ElementsPanel elementPanel,Slide slide) {
		
			slide.addMouseListener(new MouseListener() {
	
				@Override
				public void mouseClicked(MouseEvent e) {
					mousePos= e.getPoint();
					
				}
	
				@Override
				public void mousePressed(MouseEvent e) {
					mousePos= e.getPoint();
					if(selectedElementType=="CIRCLE") {
						System.out.println("pixel point: "+e.getX()+","+e.getY());
						elementPanel.addShapeElement("CIRCLE",e.getPoint(),100,100,null,0);
					}else if(selectedElementType=="TEXT") {
						elementPanel.addTextElement("Text", e.getPoint(), 100, 100, null, 0);
					}
					slide.repaint();
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
			slide.addMouseMotionListener(new MouseMotionListener() {
	
				@Override
				public void mouseDragged(MouseEvent e) {
					if(selectedElement!=null) {
						if(selectedElement.getType()=="GRAPHIC"){
							if(selectedElementType=="GRAPHIC") {
								System.out.println("PAINTING");
								BufferedImage im= ((GraphicsElement)selectedElement).getCurrentImage();
								Graphics2D g2= (Graphics2D)im.getGraphics().create();
								g2.setStroke(paintStroke);
								g2.setColor(paintColor);
								g2.drawLine(mousePos.x, mousePos.y, e.getX(), e.getY());
								((GraphicsElement)selectedElement).addBufferedImageToGraphicsList(im);
								selectedElement.getComponent().validate();
							}
							else if(selectedElementType=="ERASER"){
								System.out.println("erasing");
								BufferedImage im= ((GraphicsElement)selectedElement).getCurrentImage();
								int width= im.getWidth();
								int height = im.getHeight();
							    int[] pixels = new int[strokeThickness * strokeThickness];
							    for (int i = 0; i < pixels.length; i++) {
							    	pixels[i] = 0x00ffffff;				        
							    }
							    im.setRGB(e.getX()-strokeThickness/2, e.getY()-strokeThickness/2, strokeThickness, strokeThickness, pixels, 0, 0);

							    for (int i = 0; i < pixels.length; i++) {
							        // I used capital F's to indicate that it's the alpha value.
							        if (pixels[i] == 0xFFffffff) {
							            // We'll set the alpha value to 0 for to make it fully transparent.
							            pixels[i] = 0x00ffffff;
							        }
							    }
						
								((GraphicsElement)selectedElement).addBufferedImageToGraphicsList(im);
								selectedElement.getComponent().validate();
							}		
						}	
						mousePos= e.getPoint();	
					}
				}
	
				@Override
				public void mouseMoved(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
			});
		}
	

	/**
	 * Explained in setMarginBounds() diagram
	 */
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
			((CircleButton)buttons.get(i)).setBorder(Color.white,w/10);
		}
	
	}
	
	/*
	 * Setters and getters
	 */
	public void setPaintThickness(int thickness) {
		paintStroke= new BasicStroke(thickness);
		strokeThickness= thickness;
	}
	public void setTextColor(Color textElementColor) {
		this.textElementColor= textElementColor;
	}
	public void setPaintColor(Color paintColor) {
		this.paintColor= paintColor;
	}
	public String getSelectedElementType() {
		return selectedElementType;
	}
	public JButton getPaintButton() {
		return paintButton;
	}
	public JButton getEraserButton() {
		return eraserButton;
	}
	public JButton getTextButton() {
		return textButton;
	}
	public JButton getShapesButton() {
		return shapesButton;
	}
	public JButton getMoveButton() {
		return moveButton;
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

}
