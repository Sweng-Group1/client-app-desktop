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
	TextBox boxTwo;
	TextBox boxThree;
	TextBox boxFour;

	
	int curvatureRadius;
	int gapWidth;
	
	Color colour1;
	Color colour2;
	Color colour3;

	public UploadScene(Color colour1, Color colour2) throws IOException {	
		create(colour1,colour2);
		createPanels();
		addTabBar();	
		addPresentationBox();
		addToolBar();
		addTextBoxes();
	}	
	
	private void create(Color colour1, Color colour2) {
		this.colour1=colour1;
		this.colour2= colour2;
		this.setLayout(null);
		curvatureRadius=25;
		setBackground(colour1);
	}
	private void createPanels() {
		leftPanel = new JPanel();
		middlePanel= new JPanel();
		rightPanel= new JPanel();;
		
		leftPanel.setLayout(new BoxLayout(leftPanel,BoxLayout.Y_AXIS));
		middlePanel.setLayout(null);
		rightPanel.setLayout(new BoxLayout(rightPanel,BoxLayout.Y_AXIS));
		
		leftPanel.setBackground(new Color(255,255,255,0));
		middlePanel.setBackground(new Color(255,255,255,0));
		rightPanel.setBackground(new Color(255,255,255,0));
		
		
		this.add(leftPanel);
		this.add(middlePanel);
		this.add(rightPanel);
	}
	private void addTextBoxes() {
		Color textBoxInnerColour= new Color(colour1.getRed(),colour1.getGreen(),colour1.getBlue(),100);
		boxOne= new TextBox(textBoxInnerColour,colour2,curvatureRadius);
		boxTwo= new TextBox(textBoxInnerColour,colour2,curvatureRadius);
		boxThree= new TextBox(textBoxInnerColour,colour2,curvatureRadius);
		boxFour= new TextBox(textBoxInnerColour,colour2,curvatureRadius);
		
		rightPanel.setLayout(null);
		leftPanel.add(boxOne);
		rightPanel.add(boxTwo);
		rightPanel.add(boxThree);
		rightPanel.add(boxFour);
		
	}

	public void addPresentationBox() {
		presentation= new PresentationBox();
		middlePanel.add(presentation);
	}
	public void addToolBar() throws IOException {
		toolBar = new ToolBar();
		middlePanel.add(toolBar);
		toolBar.addButton(ImageIO.read(new File("./Assets/pencil.png")));
		toolBar.addButton(ImageIO.read(new File("./Assets/resources.png")));
		toolBar.addButton(ImageIO.read(new File("./Assets/fill_editor.png")));
		toolBar.addButton(ImageIO.read(new File("./Assets/shapes_editor.png")));
		toolBar.addButton(ImageIO.read(new File("./Assets/text_editor.png")));
		toolBar.addButton(ImageIO.read(new File("./Assets/center_allign.png")));
		toolBar.addButton(ImageIO.read(new File("./Assets/settings-sliders.png")));
		
		toolBar.addButton(ImageIO.read(new File("./Assets/arrow-small-left.png")));
		toolBar.addButton(ImageIO.read(new File("./Assets/arrow-small-right.png")));
		toolBar.addButton(ImageIO.read(new File("./Assets/check.png")));
		toolBar.addButton(ImageIO.read(new File("./Assets/cross.png")));
		toolBar.setBackground(Color.LIGHT_GRAY);
		
	}
	public void addTabBar() throws IOException {
		tabBar= new TabBar(colour1,curvatureRadius);
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
		boxThree.setLocation(0, boxTwo.getHeight()+gapWidth);
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
		int toolBarHeight= presentation.getHeight()/15;
		toolBar.setSize(middlePanel.getWidth(), toolBarHeight);
		tabBar.setSize(middlePanel.getWidth(), toolBarHeight);
		setComponentPositions(width,height,gapWidth);
		this.validate();
		
	}
}

