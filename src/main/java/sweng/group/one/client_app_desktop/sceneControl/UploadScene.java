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

	public UploadScene() throws IOException {
		createBackground();
		createPanels();
		addTabBar();	
		addPresentationBox();
		addToolBar();
	
	}	
	private void createPanels() {
		this.setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
		leftPanel = new JPanel();
		middlePanel= new JPanel();
		rightPanel= new JPanel();;
		
		leftPanel.setLayout(new BoxLayout(leftPanel,BoxLayout.Y_AXIS));
		//middlePanel.setLayout(new BoxLayout(middlePanel,BoxLayout.Y_AXIS));
		middlePanel.setLayout(null);
		rightPanel.setLayout(new BoxLayout(rightPanel,BoxLayout.Y_AXIS));
		
		this.add(leftPanel);
		this.add(middlePanel);
		this.add(rightPanel);
	}
	
	private void createBackground() {
		this.setCurvatureRadius(20);
		this.setBackground(Color.gray);
	}
	public void addPresentationBox() {
		presentation= new PresentationBox();
		middlePanel.add(presentation);
	}
	public void addToolBar() throws IOException {
		toolBar = new ToolBar();
		middlePanel.add(toolBar);
		toolBar.addButton(ImageIO.read(new File("./Assets/pencil.png")));
	//	toolBar.addButton(ImageIO.read(new File("./Assets/.png")));
		toolBar.addButton(ImageIO.read(new File("./Assets/arrow-small-left.png")));
		toolBar.addButton(ImageIO.read(new File("./Assets/arrow-small-right.png")));
		toolBar.addButton(ImageIO.read(new File("./Assets/check.png")));
		toolBar.addButton(ImageIO.read(new File("./Assets/cross.png")));
		toolBar.setBackground(Color.LIGHT_GRAY);
		
	}
	public void addTabBar() {
		tabBar= new TabBar();
		middlePanel.add(tabBar);
		tabBar.setBackground(Color.LIGHT_GRAY);
		tabBar.getAddTabButton().setBackgroundMainColour(Color.white);
	}
	private void setComponentPositions(int width,int height) {
		presentation.setLocation(0,(height/2)-(presentation.getHeight()/2));
		tabBar.setLocation(0, presentation.getY()-tabBar.getHeight());
		toolBar.setLocation(0, presentation.getY()+presentation.getHeight());
	}
	public void setSize(int width, int height) {
		super.setSize(width,height);
		int presentationBoxWidth= 3*(width/4);
		//this method will scale the presentation box to be 16:9 
		presentation.setSize(presentationBoxWidth,height);
		int toolBarHeight= 50;
		toolBar.setSize(presentationBoxWidth, toolBarHeight);
		tabBar.setSize(presentationBoxWidth, toolBarHeight);
		setComponentPositions(width,height);
		this.validate();
	}
}

