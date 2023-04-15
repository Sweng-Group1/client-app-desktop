package sweng.group.one.client_app_desktop.sceneControl;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import sweng.group.one.client_app_desktop.uiElements.CustomGraphicsBox;
import sweng.group.one.client_app_desktop.uiElements.CustomMediaBox;
import sweng.group.one.client_app_desktop.uiElements.CustomTabPanel;
import sweng.group.one.client_app_desktop.uiElements.CustomToolBar;
import sweng.group.one.client_app_desktop.uiElements.UploadSceneComponent;

public class UploadScene extends UploadSceneComponent{
	
	UploadScene uploadScene= this;
	JButton confirmButton;
	JButton closeButton;
	
	CustomTabPanel tabPane;
	CustomToolBar toolBar;
	CustomGraphicsBox graphicsBox;
	CustomMediaBox mediaBox;
	
	boolean elementIsBeingMoved;
	MouseListener movingElementToListener;
	
	
	public UploadScene() {
		super();
		this.setLayout(null);
		
		//Create graphicsBox
		graphicsBox= new CustomGraphicsBox();
		this.add(graphicsBox);
		//Create mediaBox
		mediaBox= new CustomMediaBox(graphicsBox);
		this.add(mediaBox);
		//Create TabPane
		tabPane= new CustomTabPanel(graphicsBox);
		this.add(tabPane);
		//Create ToolBar
		toolBar= new CustomToolBar(graphicsBox,tabPane);
		this.add(toolBar);
		
		elementIsBeingMoved=false;
		mediaBox.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if((mediaBox.isIconSelected()==true)&&(elementIsBeingMoved==false)) {
					Image elementIcon= mediaBox.getSelectedIcon();
					Cursor c = Toolkit.getDefaultToolkit().createCustomCursor(elementIcon , new Point( 
					           0,0), "img");
					uploadScene.setCursor (c);
					elementIsBeingMoved=true;
					tabPane.getCurrentComponent().addMouseListener(movingElementToListener);
					tabPane.getCurrentSlide().addMouseListener(movingElementToListener);
					tabPane.getCurrentSlide().getElements().get(tabPane.getCurrentSlide().getElements().size()-1).getComponent().addMouseListener(movingElementToListener);
					toolBar.turnOffPaintMode();
				}else if (elementIsBeingMoved==true) {
					mediaBox.turnOffMediaSelected();
					uploadScene.setCursor(Cursor.getDefaultCursor());
					elementIsBeingMoved=false;
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
		movingElementToListener= new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				System.out.println("TRYING TO PLACE");
				if(elementIsBeingMoved==true) {
					if(mediaBox.getSelectedMediaType()=="IMAGE") {
						BufferedImage image= mediaBox.getSelectedMediaImage();
						graphicsBox.addImageLayer(image);
						
					}
					mediaBox.turnOffMediaSelected();
					uploadScene.setCursor(Cursor.getDefaultCursor());
					elementIsBeingMoved=false;
					System.out.println("Element is placed!");
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
	}
	
	//SETTERS 
	public void setBackgroundColours(Color colorLight, Color colorDark) {
		this.setMainAndSecondaryColor(colorDark, colorLight);
		tabPane.setMainAndSecondaryColor(colorLight,colorDark);
		toolBar.setMainAndSecondaryColor(colorLight,colorDark);
		graphicsBox.setMainAndSecondaryColor(colorLight,colorDark);
		mediaBox.setMainAndSecondaryColor(colorLight,colorDark);
	}
	public void setCurvatureRadius(int curvatureRadius) {
		super.setCurvatureRadius(curvatureRadius);
		tabPane.setCurvatureRadius(curvatureRadius);
		toolBar.setCurvatureRadius(curvatureRadius);
		graphicsBox.setCurvatureRadius(curvatureRadius);
		mediaBox.setCurvatureRadius(curvatureRadius);
	}
	//GETTERS 
	public JButton getCloseButton() {
		return closeButton;
	}
	public JButton getConfirmButton() {
		return confirmButton;
	}
	//public Presentation getPresentation() {
		//return tabPane.getPresentation();
	//}
	
	//OVERIDED METHODS:
	public void setBounds(int x, int y,int width, int height) {
		super.setBounds(x,y,width, height);
		int gridWidth= 6;
		int gridHeight=16;
		double cellWidth= width/gridWidth;
		double cellHeight= height/gridHeight;
		
		//GRID BOUNDS:
		int mb[]= {0,0,1,16};
		int tab[]= {1,0,5,15};
		int tool[]= {1,15,5,16};
		int gb[]= {5,0,6,5};
		
		mediaBox.setBounds((int)(mb[0]*cellWidth),(int)(mb[1]*cellHeight),
				(int)(mb[2]*cellWidth-(mb[0]*cellWidth)),(int)(mb[3]*cellHeight-(mb[1]*cellHeight)));
		tabPane.setBounds((int)(tab[0]*cellWidth),(int)(tab[1]*cellHeight),
				(int)(tab[2]*cellWidth-(tab[0]*cellWidth)),(int)(tab[3]*cellHeight-(tab[1]*cellHeight)));
		toolBar.setBounds((int)(tool[0]*cellWidth),(int)(tool[1]*cellHeight),
				(int)(tool[2]*cellWidth-(tool[0]*cellWidth)),(int)(tool[3]*cellHeight-(tool[1]*cellHeight)));
		graphicsBox.setBounds((int)(gb[0]*cellWidth),(int)(gb[1]*cellHeight),
				(int)(gb[2]*cellWidth-(gb[0]*cellWidth)),(int)(gb[3]*cellHeight-(gb[1]*cellHeight)));
		
		//Setting component margins:
		int gapWidth= 10;
		mediaBox.setMarginBounds((int)gapWidth,(int) gapWidth, (int)gapWidth/2,(int) gapWidth);
		tabPane.setMarginBounds((int)gapWidth/2, (int)gapWidth,(int) gapWidth/2, (int)gapWidth/2);
		toolBar.setMarginBounds((int)gapWidth/2, (int)gapWidth/2, (int)gapWidth/2,(int) gapWidth);
		graphicsBox.setMarginBounds((int)gapWidth/2, (int)gapWidth,(int) gapWidth, (int)gapWidth/2);
	}
}
