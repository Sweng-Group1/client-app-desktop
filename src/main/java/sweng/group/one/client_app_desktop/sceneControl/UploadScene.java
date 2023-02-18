package sweng.group.one.client_app_desktop.sceneControl;

import javax.swing.JPanel;
import javax.swing.JTextField;


import java.awt.Color;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JPanel;



public class UploadScene extends sweng.group.one.client_app_desktop.uiElements.RoundedPanel{
	JPanel leftPanel;
	JPanel middlePanel;	
	JPanel rightPanel;
	
	PresentationBox presentation;
	ToolBar toolBar;
	TabBar tabBar;
	
	TextBox boxOne;
	GraphicsBox boxTwo;
	TextBox boxThree;
	TextBox boxFour;

	
	int curvatureRadius;
	int gapWidth;
	
	Color colourDark;
	Color colourLight;


	public UploadScene(Color colourDark, Color colourLight) throws IOException {	
		create(colourDark,colourLight);
		createPanels();
		addTabBar();	
		addPresentationBox();
		addToolBar();
		addTextBoxes();
		//createMouseListeners();
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
		toolBar.addButtonLtoR(ImageIO.read(new File("./Assets/text.png")));
		toolBar.addButtonLtoR(ImageIO.read(new File("./Assets/pencil.png")));
		toolBar.addButtonLtoR(ImageIO.read(new File("./Assets/shapes.png")));
		
		toolBar.addButtonRtoL(ImageIO.read(new File("./Assets/tick.png")));
		toolBar.addButtonRtoL(ImageIO.read(new File("./Assets/cross.png")));
		toolBar.addButtonRtoL(ImageIO.read(new File("./Assets/back.png")));
		toolBar.addButtonRtoL(ImageIO.read(new File("./Assets/download.png")));
	}
	public void addTabBar() throws IOException {
		tabBar= new TabBar(colourLight);
		middlePanel.add(tabBar);
	}
	private void setComponentPositions(int width,int height,int gapWidth) {
		presentation.setLocation(0,(height/2)-(presentation.getHeight()/2));
		tabBar.setLocation(0, presentation.getY()-tabBar.getHeight());
		toolBar.setLocation(0, presentation.getY()+presentation.getHeight()+(gapWidth/2));
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
		presentation.setSize(middlePanel.getWidth(),height);
		
		int toolBarHeight= presentation.getHeight()/10;
		toolBar.setSize(middlePanel.getWidth(), toolBarHeight);
		tabBar.setSize(middlePanel.getWidth(), toolBarHeight);
		setComponentPositions(width,height,gapWidth);
		this.validate();
		
	}
}

