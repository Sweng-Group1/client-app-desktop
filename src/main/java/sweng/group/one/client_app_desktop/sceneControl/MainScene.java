package sweng.group.one.client_app_desktop.sceneControl;

//import org.assertj.core.api.Assertions.assertThatIllegalStateException;

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

	private JLayeredPane panel;
	private MapScene mapScene;
	private SidebarScene sidebarScene;
	private LoginScene login;
	private UploadScene upload;
	private OptionsScene options;
	
	private Dimension screenSize= Toolkit.getDefaultToolkit().getScreenSize();
	
	private static final Color colorLight= new Color(78,106,143);
	private static final Color colorDark= new Color(46,71,117);
	
	JPanel waitingScreen;
	private int gapWidth;
	private int curvatureRadius;
	
	public MainScene() {
		super();
		
		
		this.setSize(screenSize);
		
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		gapWidth= screenSize.height/48;
		curvatureRadius= 20;
		this.setVisible(true); 
		waitingScreen= new JPanel();
		waitingScreen.setBackground(Color.yellow);
		this.getLayeredPane().add(waitingScreen, 5);
		
		
		sidebarScene = new SidebarScene(null);
		
		mapScene = new MapScene() {
			@Override
			public void selectMarker(EventMarker selected) {
				super.selectMarker(selected);
				
				if (selected != null) {
					sidebarScene.replacePres(selected.getPosts());
				}
			}
		};
		try {
			options = new OptionsScene();
			login= new LoginScene();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		upload= new UploadScene();
		options.getAccountButton().addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				sidebarScene.setVisible(false);
				options.setVisible(false);
				login.setVisible(true);
				
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
		login.getContinueButton().addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				login.setVisible(false);
				sidebarScene.setVisible(true);
				options.setVisible(true);		
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
		options.getAddPostButton().addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
			
				sidebarScene.setVisible(false);
				options.setVisible(false);	
				upload.setVisible(true);
		
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
		upload.getConfirmButton().addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				upload.setVisible(false);
				sidebarScene.setVisible(true);
				options.setVisible(true);	
			
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
		this.addComponentListener(new ComponentListener() {

			@Override
			public void componentResized(ComponentEvent e) {
				resizeComponents();
				
			}

			@Override
			public void componentMoved(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void componentShown(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void componentHidden(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		
		
		resizeComponents();
		setZOrder();
		
		mapScene.setVisible(true);
		options.setVisible(true);
		sidebarScene.setVisible(true);
		upload.setVisible(false);
		login.setVisible(false);
		
		this.remove(waitingScreen);
	
	};
	public void setZOrder() {
		panel= this.getLayeredPane();
		panel.setLayer(mapScene,0);
		panel.add(mapScene);
		
		mapScene.loadMapFile(new File("./assets/map/york.map"));
	
		/*
		try {
			addDemoMarkers();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mapScene.repaint();
		*/
		
	
		panel.setLayer(sidebarScene, 1);
		panel.add(sidebarScene);
		panel.setLayer(options, 2);
		panel.add(options);
		panel.setLayer(login, 3);
		panel.add(login);
		panel.setLayer(upload, 4);
		panel.add(upload);
		
		
	}
	public void resizeComponents() {
		screenSize= this.getSize();
		mapScene.setBounds(0, 0, screenSize.width,screenSize.height);
		
		
		sidebarScene.setSize(screenSize.width/3,screenSize.height);
		sidebarScene.setLocation(0,0);
		options.setSize(screenSize.height/4, screenSize.height/4);
		options.setLocation(screenSize.width-gapWidth-(screenSize.height/4), gapWidth);
		login.setSize(screenSize.width/4, screenSize.height/2);
		login.setLocation((screenSize.width- screenSize.width/4)/2, screenSize.height/4);
		upload.setBounds(screenSize.width/10, screenSize.height/10, 4*(screenSize.width/5), 4*(screenSize.height/5));
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
		MainScene ms = new MainScene();
		
	}
	
	

}
