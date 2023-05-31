package sweng.group.one.client_app_desktop.sceneControl;

import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.mapsforge.core.model.LatLong;
import org.mapsforge.map.controller.FrameBufferController;
import org.mapsforge.map.layer.Layer;
import org.mapsforge.map.layer.TileLayer;
import org.mapsforge.map.layer.renderer.TileRendererLayer;

import sweng.group.one.client_app_desktop.graphics.Circle;
import sweng.group.one.client_app_desktop.mapping.EventMarker;
import sweng.group.one.client_app_desktop.media.ImageViewer;
import sweng.group.one.client_app_desktop.media.VideoPlayer;
import sweng.group.one.client_app_desktop.presentation.DemoElement;
import sweng.group.one.client_app_desktop.presentation.Presentation;
import sweng.group.one.client_app_desktop.presentation.Slide;
import sweng.group.one.client_app_desktop.text.TextElement;

public class MainScene extends JFrame{

	private JLayeredPane mainFrame;
	private MapScene mapScene;
	private SidebarScene sidebarScene;
//	private LoginScene login;
//	private UploadScene upload;
//	private OptionsScene options;
	
	// Window size
//	private Dimension windowSize = new Dimension(800, 600);
	private Dimension windowSize = new Dimension(800, 600);

	
	// Colours
	private static final Color colorLight= new Color(78,106,143);
	private static final Color colorDark= new Color(46,71,117);
	
	JPanel waitingScreen;
	private int gapWidth;
	private int curvatureRadius;
	
	public MainScene() {
		super();
		
		this.setSize(windowSize);
		
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		gapWidth= windowSize.height/48;
		curvatureRadius= 20;
		this.setVisible(true);
		
		sidebarScene = new SidebarScene(null);
		
		mapScene = new MapScene();
		
//		options = new OptionsScene();
//		login = new LoginScene();
			
		resizeComponents();
		setZOrder();
		
		mapScene.setVisible(true);
		sidebarScene.setVisible(true);
//		options.setVisible(true);
//		upload.setVisible(false);
//		login.setVisible(false);
		
	
	}
	
	public void setZOrder() {
		mainFrame = this.getLayeredPane();
		mainFrame.setLayer(mapScene,0);
		mainFrame.add(mapScene);
		
		mapScene.loadMapFile(new File("./assets/map/york.map"), new File("./assets/map/theme.xml"));
	
		try {
			addDemoMarkers();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mapScene.repaint();
		
	
		mainFrame.setLayer(sidebarScene, 1);
		mainFrame.add(sidebarScene);
//		panel.setLayer(options, 2);
//		panel.add(options);
//		panel.setLayer(login, 3);
//		panel.add(login);
//		panel.setLayer(upload, 4);
//		panel.add(upload);
		
	}
	
	public void resizeComponents() {
		windowSize= this.mainFrame.getSize();
		mapScene.setBounds(0, 0, windowSize.width,windowSize.height);
		
		
		sidebarScene.setSize(windowSize.width/3,windowSize.height);
		sidebarScene.setLocation(0,0);
//		options.setSize(windowSize.height/4, windowSize.height/4);
//		options.setLocation(windowSize.width-gapWidth-(windowSize.height/4), gapWidth);
//		login.setSize(windowSize.width/4, windowSize.height/2);
//		login.setLocation((windowSize.width- windowSize.width/4)/2, windowSize.height/4);
//		upload.setBounds(windowSize.width/10, windowSize.height/10, 4*(windowSize.width/5), 4*(windowSize.height/5));
	}
	
	
	public void addDemoMarkers() throws MalformedURLException {
		int slideX = 100;
		int slideY = 50;
		mapScene.addEventMarker(new LatLong(53.94565858032622, -1.0555302805175937));
		for (int i = 0; i < 3; i++) {
			//create slide and add to frame
	    	Slide slide = new Slide(slideX, slideY);
	    	ArrayList<Slide> slides = new ArrayList<>();
	    	slides.add(slide);
	    	
	    	VideoPlayer vp = new VideoPlayer(new Point(0, 0), slideX, slideY, slide, new URL("https://getsamplefiles.com/download/mp4/sample-5.mp4"), true);
	    	slide.add(vp);
	    	
	    	Presentation pres = new Presentation(slides);
			mapScene.getMarkers().get(0).addPost(pres);
		}
		
		mapScene.addEventMarker(new LatLong(53.945945888223186, -1.0515230602493442));
		for (int i = 0; i < 7; i++) {
			//create slide and add to frame
	    	Slide slide = new Slide(slideX, slideY);
	    	ArrayList<Slide> slides = new ArrayList<>();
	    	slides.add(slide);
	    	
	    	ImageViewer ie = new ImageViewer(new Point(0, 0), slideX, slideY, 0, slide, new URL("https://getsamplefiles.com/download/png/sample-1.png"));
	    	slide.add(ie);
	    	/*
	    	 *  Testing sideBar prevslide/ next slide 
	    	 *  
	    	 */
	    	Slide slideB = new Slide(slideX, slideY);
	    	slides.add(slideB);
	    	slideB.setBackground(Color.green);
	    	//slideB.add(new Circle(new Point(1, 1), slideY/2, 0, slideB,Color.black,null,null));
	    	
	    	Presentation pres = new Presentation(slides);
			mapScene.getMarkers().get(1).addPost(pres);
		}
		
		mapScene.addEventMarker(new LatLong(53.94536495592503, -1.0537439293136752));
		
		String[] fontNames = {"Times New Roman", "Calibri", "Comic Sans MS", "Cooper Black", "Complex"};
		
		for (int i = 0; i < 5; i++) {
			slideX = i%2 != 0 ? 9 : 16;
			slideY = i%2 == 0 ? 9 : 16;
			
			//create slide and add to frame
	    	Slide slideA = new Slide(slideX, slideY);
	    	ArrayList<Slide> slides = new ArrayList<>();
	    	slides.add(slideA);
	    	
	    	TextElement te = new TextElement("This is demonstration of how text will look", fontNames[i], 10*i + 1, new Color(0, 0, 255*i/5), 0, new Point(0, 0), slideX, slideY, slideA);
	    	slideA.add(te);
	    	slideA.add(new DemoElement(new Point(0, 0), slideX, slideY, 0, slideA));
	    	
	    	/*
	    	 *  Testing sideBar prevslide/ next slide 
	    	 *  
	    	 */
	    	Slide slideB = new Slide(slideX, slideY);
	    	slides.add(slideB);
	    	slideB.setBackground(colorDark.MAGENTA);
	    	//slideB.add(new Circle(new Point(0, 0), slideY/2, 0, slideB,Color.black,null,null));
	    	
	    	Presentation pres = new Presentation(slides);
			mapScene.getMarkers().get(2).addPost(pres);
		}
		
	}
	

	public static void main(String[] args) {
		new MainScene();
	}
	
	

}
