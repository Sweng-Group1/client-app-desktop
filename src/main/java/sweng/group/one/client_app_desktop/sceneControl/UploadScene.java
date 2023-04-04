package sweng.group.one.client_app_desktop.sceneControl;

import javax.swing.JPanel;
import javax.swing.JTextField;

import sweng.group.one.client_app_desktop.presentation.Presentation;

import sweng.group.one.client_app_desktop.uiElements.CustomTabPanel;
import sweng.group.one.client_app_desktop.uiElements.GraphicsBox;
import sweng.group.one.client_app_desktop.uiElements.PresentationBox;
import sweng.group.one.client_app_desktop.uiElements.RoundedPanel;
import sweng.group.one.client_app_desktop.uiElements.TextBox;
import sweng.group.one.client_app_desktop.uiElements.ToolBar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;



public class UploadScene extends sweng.group.one.client_app_desktop.uiElements.RoundedPanel{
	JPanel leftPanel;
	JPanel middlePanel;	
	JPanel rightPanel;
	
	
	PresentationBox presentation;
	ToolBar toolBar;
	CustomTabPanel tabPane;
	
	TextBox boxOne;
	GraphicsBox boxTwo;
	TextBox boxThree;
	TextBox boxFour;

	
	int curvatureRadius;
	int gapWidth;
	
	Color colourDark;
	Color colourLight;
	
	JPanel paintPopup;


	public UploadScene(Color colourDark, Color colourLight) throws IOException {	
		create(colourDark,colourLight);
		createPanels();
		addTabPane();
		addToolBar();
		addTextBoxes();
		createMouseListeners();
	}	

	private void create(Color colourDark, Color colourLight) {
		this.colourDark=colourDark;
		this.colourLight= colourLight;
		this.setLayout(null);
		curvatureRadius=25;
		setBackground(colourDark);
	}
	private void createPanels() {
		leftPanel = new JPanel();
		middlePanel= new JPanel();
		rightPanel= new JPanel();;
		
		leftPanel.setLayout(new BoxLayout(leftPanel,BoxLayout.Y_AXIS));
		middlePanel.setLayout(null);
		rightPanel.setLayout(new BoxLayout(rightPanel,BoxLayout.Y_AXIS));
		
		leftPanel.setOpaque(false);
		middlePanel.setOpaque(false);
		rightPanel.setOpaque(false);
		
		this.add(leftPanel);
		this.add(middlePanel);
		this.add(rightPanel);
	}
	private void addTabPane() {
		tabPane= new CustomTabPanel();
		middlePanel.add(tabPane);
		tabPane.setBackgroundColors(colourLight, colourDark);
	}
	private void addTextBoxes() {

		boxOne= new TextBox(colourLight,curvatureRadius);
		boxOne.setAddMediaIconVisible(true);
		boxTwo= new GraphicsBox(colourLight,curvatureRadius);
		boxTwo.setAddMediaElementsToAjustIsVisible(true);
		//.setBackground(colourLight);
		boxThree= new TextBox(colourLight,curvatureRadius);
		boxThree.setAddTagsIconIsVisible(true);
		boxThree.setSearchLocationHeaderIsVisible(true);
		
		boxFour= new TextBox(colourLight,curvatureRadius);
		boxFour.setAddDescriptionIconIsVisible(true);
		
		rightPanel.setLayout(null);
		leftPanel.add(boxOne);
		rightPanel.add(boxTwo);
		rightPanel.add(boxThree);
		rightPanel.add(boxFour);
		
	}
	public void addPresentationBox() {
		presentation= new PresentationBox(colourLight);
		middlePanel.add(presentation);
	}
	public void addToolBar() throws IOException {
		toolBar = new ToolBar(colourLight);
		middlePanel.add(toolBar);
		toolBar.addButtonLtoR(ImageIO.read(new File("./Assets/cursor.png")));
		toolBar.addButtonLtoR(ImageIO.read(new File("./Assets/pencil.png")));
		toolBar.addButtonLtoR(ImageIO.read(new File("./Assets/eraser.png")));
		toolBar.addButtonLtoR(ImageIO.read(new File("./Assets/text.png")));
		toolBar.addButtonLtoR(ImageIO.read(new File("./Assets/shapes.png")));
		
		toolBar.addButtonRtoL(ImageIO.read(new File("./Assets/tick.png")));
		toolBar.addButtonRtoL(ImageIO.read(new File("./Assets/cross.png")));
		toolBar.addButtonRtoL(ImageIO.read(new File("./Assets/forward.png")));
		toolBar.addButtonRtoL(ImageIO.read(new File("./Assets/back.png")));
		toolBar.addButtonRtoL(ImageIO.read(new File("./Assets/download.png")));
		
	}



	private void createMouseListeners() {
		toolBar.getPaintButton().addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if(presentation.isPaintMode()==true) {
					presentation.setPaintMode(false);
					
				}else {
					presentation.setPaintMode(true);
					
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
		toolBar.getEraserButton().addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
	
				if(presentation.isEraserMode()==true) {
					presentation.setEraserMode(false);
				}else {
					presentation.setEraserMode(true);
					presentation.setPaintMode(false);
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
		toolBar.getBackButton().addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				presentation.removeLastGraphicOnCurrentLayer();
				
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
		toolBar.getForwardButton().addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				presentation.replaceLastGraphicsOnCurrentLayer();
				
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
	private void setSizeAndPositionOfTextBoxes(int textBoxWidth,int uploadSceneHeight, int gapWidth ) {
		boxOne.setSize(leftPanel.getWidth(), leftPanel.getHeight());
		int rightTextBoxesRatio= (uploadSceneHeight- (4*gapWidth))/7;
		boxTwo.setSize(textBoxWidth, rightTextBoxesRatio*4);
		boxThree.setSize(textBoxWidth,rightTextBoxesRatio);
		boxFour.setSize(textBoxWidth, rightTextBoxesRatio*2);
		
		boxTwo.setLocation(0, 0);
		boxThree.setLocation(0,boxTwo.getHeight()+ gapWidth);
		boxFour.setLocation(0, boxThree.getHeight()+boxThree.getY()+(gapWidth));
		
	}
	public void setSize(int width, int height) {
		super.setSize(width,height, curvatureRadius);
		gapWidth= width/100;
		int spaceRatio= width/6;
		
		leftPanel.setSize(spaceRatio-(2*gapWidth),height-(2*gapWidth));
		rightPanel.setSize(spaceRatio-(2*gapWidth), height-(2*gapWidth));
		middlePanel.setSize((spaceRatio*4), height-(2*gapWidth));
		
		leftPanel.setLocation(gapWidth,gapWidth );
		middlePanel.setLocation(spaceRatio, gapWidth);
		rightPanel.setLocation((spaceRatio*5)+gapWidth,gapWidth);
		
		setSizeAndPositionOfTextBoxes(leftPanel.getWidth(),height,gapWidth);
		
		//boxOne.setLocation(0,0);
		
		//this method will scale the presentation box to be 16:9 
	//	presentation.setSize(middlePanel.getWidth(),height);
		
		int toolBarHeight= height/20;
		toolBar.setSize(middlePanel.getWidth(), toolBarHeight);
		//tabBar.setSize(middlePanel.getWidth(), toolBarHeight);
		tabPane.setSize(middlePanel.getWidth(),middlePanel.getHeight()-toolBarHeight);
		//setComponentPositions(width,height,gapWidth);
		toolBar.setLocation(0, middlePanel.getHeight()-toolBarHeight);
		tabPane.setLocation(0, 0);
		this.validate();
		
		
	}
	public JButton getConfirmButton() {
		return toolBar.getConfirmButton();
	}
	public JButton getExitButton() {
		return toolBar.getExitButton();
	}
	public Presentation getPresentation() {
		return tabPane.getPresentation();
	}
}

