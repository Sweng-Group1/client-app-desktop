package sweng.group.one.client_app_desktop.uiElements;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import sweng.group.one.client_app_desktop.presentation.PresElement;



/**
 * @author sophiemaw
 * Class thats instatiates all the buttons 
 * 
 *
 */
public class CustomToolBar extends UploadSceneComponent implements ComponentInterface{
	String selectedElementType;
	List<CircleButton>buttons;
	CircleButton moveButton,paintButton,eraserButton, textButton, shapesButton, confirmButton,
					exitButton,forwardButton,backButton, downloadButton;
	
	Stroke paintStroke;
	Color paintColor;
	Point mousePos;
	public CustomToolBar() {
		this.setOpaque(false);	
		this.setLayout(null);
		this.main= colorLight;
		this.secondary= colorDark;
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
		
		paintButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				selectedElementType= "GRAPHIC";
				
			}
			
		});
		shapesButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		textButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				selectedElementType= "TEXT";
				
			}
			
		});
	
	}
	
	/**
	 * @param presElement 
	 * This method adds a listener to add graphics to the current Image of a 
	 * GraphicsElement
	 */
	public void addGraphicsListener(PresElement presElement) {
		if(presElement.getType()=="GRAPHIC") {
			presElement.getComponent().addMouseListener(new MouseListener() {
	
				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
	
				@Override
				public void mousePressed(MouseEvent e) {
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
				
			});
			presElement.getComponent().addMouseMotionListener(new MouseMotionListener() {
	
				@Override
				public void mouseDragged(MouseEvent e) {
					BufferedImage im= ((GraphicsElement)presElement).getCurrentImage();
					Graphics2D g2= (Graphics2D)im.getGraphics().create();
					g2.setStroke(paintStroke);
					g2.setColor(paintColor);
					g2.drawLine(mousePos.x, mousePos.y, e.getX(), e.getY());
					((GraphicsElement)presElement).addBufferedImageToGraphicsList(im);
					presElement.getComponent().validate();
					mousePos= e.getPoint();
					
				}
	
				@Override
				public void mouseMoved(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
			});
		}
	}
	/*
	 * Overided methods:
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
		}
	
	}
	
	/*
	 * Setters and getters
	 */
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
