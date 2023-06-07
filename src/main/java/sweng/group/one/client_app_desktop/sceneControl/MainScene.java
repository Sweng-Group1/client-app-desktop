package sweng.group.one.client_app_desktop.sceneControl;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.xml.parsers.ParserConfigurationException;

import org.mapsforge.core.graphics.Bitmap;
import org.mapsforge.core.model.LatLong;
import org.mapsforge.map.awt.graphics.AwtBitmap;
import org.xml.sax.SAXException;

import sweng.group.one.client_app_desktop.mapping.EventMarker;
import sweng.group.one.client_app_desktop.media.ImageViewer;
import sweng.group.one.client_app_desktop.media.VideoPlayer;
import sweng.group.one.client_app_desktop.presentation.DemoElement;
import sweng.group.one.client_app_desktop.presentation.Presentation;
import sweng.group.one.client_app_desktop.presentation.Slide;
import sweng.group.one.client_app_desktop.text.TextElement;
import sweng.group.one.client_app_desktop.data.AuthenticationException;
import sweng.group.one.client_app_desktop.data.HashtagService;
import sweng.group.one.client_app_desktop.data.PostService;
import sweng.group.one.client_app_desktop.data.UserService;

public class MainScene extends JFrame implements LayoutManager{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7994175471538717547L;
	private MapScene mapScene;
	private SidebarScene sidebarScene;
	private LoginScene login;
	private HelpScene help;
	//private UploadScene upload;
	private OptionsScene options;
	
	public MainScene() throws IOException {
		super();
		
		this.setSize(800, 500);
		this.setMinimumSize(new Dimension(800, 500));
		this.setMaximumSize(new Dimension(1920, 1080));
		
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setVisible(true); 
		
		mapScene = new MapScene() {
			private static final long serialVersionUID = 1443164343428729852L;

			@Override
			public void selectMarker(EventMarker selected) {
				super.selectMarker(selected);
				
				if (selected != null) {
					sidebarScene.open();
					sidebarScene.replacePres(selected.getPosts());
				}
			}
		};
		try {
			sidebarScene = new SidebarScene() {
				@Override
				public void search(String text) {
					for (EventMarker e : mapScene.getMarkers()) {
						if(e.getName().contains(text)) {
							mapScene.selectMarker(e);
						}
					}
				}
			};
			options = new OptionsScene() {
				private static final long serialVersionUID = -4506870977494750646L;

				@Override
				public void helpPressed() {
					if (help.isVisible()) {
						help.setVisible(false);
					} else {				
						help.setVisible(true);
					}
				}
				
				@Override
				public void addPostPressed() {
					sidebarScene.setVisible(false);
					//upload.setVisible(true);
				}
				
				@Override
				public void accountPressed() {
					sidebarScene.close();
					login.setVisible(true);
				}
				
				@Override
				public void closePressed() {
					sidebarScene.setVisible(true);
					sidebarScene.close();
					login.setVisible(false);
					help.setVisible(false);
				}
			};
			login= new LoginScene() {
				@Override
				public boolean loginButtonPressed() {
					boolean loggedIn = super.loginButtonPressed();
					if(loggedIn) {
						Thread dlThread = new Thread() {
							@Override
							public void run() {
								
							
								try {
									ArrayList<Presentation> newPres = PostService.retrievePostsByHashtagAsPresentations("#Rocketry", user.getAccessToken());
									for(EventMarker e : mapScene.getMarkers()) {
										if(e.getName().contains("#Rocketry")) {
											for(Presentation p : newPres) {
												e.addPost(p);
											}
										}
									}
								} catch (SAXException | ParserConfigurationException | IOException
										| AuthenticationException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						};
						dlThread.start();
					}
					return loggedIn;
				}
			};
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Missing Assets");
		}
		
		help = new HelpScene("./assets/User_Manual.pdf");
		
		//upload= new UploadScene();
		
		login.getContinueButton().addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {}

			@Override
			public void mousePressed(MouseEvent e) {
				login.setVisible(false);
				sidebarScene.setVisible(true);
				options.setVisible(true);		
			}

			@Override
			public void mouseReleased(MouseEvent e) {}

			@Override
			public void mouseEntered(MouseEvent e) {}

			@Override
			public void mouseExited(MouseEvent e) {}
			
		});

		JLayeredPane pane = this.getLayeredPane();
		
		pane.setLayer(mapScene,0);
		pane.add(mapScene);
		pane.setLayer(sidebarScene, 1);
		pane.add(sidebarScene);
		pane.setLayer(options, 2);
		pane.add(options);
		pane.setLayer(login, 3);
		pane.add(login);
		pane.setLayer(help, 4);
		pane.add(help);
		
		
		mapScene.setVisible(true);
		options.setVisible(true);
		sidebarScene.setVisible(true);
		//upload.setVisible(false);
		login.setVisible(false);
		help.setVisible(false);
		
		this.setLayout(this);
		
		mapScene.loadMapFile(new File("./assets/map/york.map"));
		
		BufferedImage poiImage = ImageIO.read(new File("./assets/map/marker.png"));
		Bitmap markerImage = new AwtBitmap(poiImage);
		
		try {
			ArrayList<EventMarker> events = HashtagService.retrieveHashtagsAsEventMarkers(mapScene, markerImage);
			
			for(EventMarker e : events) {
				Thread downloadThread = new Thread() {
					@Override
					public void run() {
						ArrayList<Presentation> pres = new ArrayList<>();
						try {
							pres = PostService.retrievePostsByHashtagAsPresentations(e.getName());
						} catch (SAXException | ParserConfigurationException | IOException
								| AuthenticationException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						for(Presentation p : pres) {
							e.addPost(p);
						}
						mapScene.addEventMarker(e);
					}
				};
				downloadThread.start();
			}
			
		} catch (IOException | AuthenticationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
//		
//		Thread thread = new Thread() {
//			@Override
//			public void run() {
//				try {
//					//addDemoMarkers();
//				} catch (MalformedURLException e) {
//					e.printStackTrace();
//				}
//			}
//		};
//        thread.start();
	};
	
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
	    	
	    	ImageViewer ie = new ImageViewer(new Point(0, 0), slideX, slideY, 0, 0, 0, slide, new URL("https://getsamplefiles.com/download/png/sample-1.png"));
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
	    	slideB.setBackground(Color.MAGENTA);
	    	
	    	Presentation pres = new Presentation(slides);
			mapScene.getMarkers().get(2).addPost(pres);
		}
		
	}

	@Override
	public void addLayoutComponent(String name, Component comp) {
	}
	
	@Override
	public void removeLayoutComponent(Component comp) {
	}
	
	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return null;
	}
	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return null;
	}
	@Override
	public void layoutContainer(Container parent) {
		int w = parent.getWidth();
		int h = parent.getHeight();
		
		final int optionsGap = 20;
		
		mapScene.setBounds(0, 0, w, h);
		sidebarScene.setBounds(0, 0, w/3, h);
		sidebarScene.layoutContainer(getContentPane());
		options.setBounds(w-optionsGap-h/3, optionsGap, h/3, h/3);
		options.layoutContainer(getContentPane());
		login.setBounds(3*w/8, h/4, w/4, h/2);
		login.layoutContainer(getContentPane());
		help.setBounds(w/3 + optionsGap/2, optionsGap, w/2, h - optionsGap * 2);
	}
	
	public static void main(String[] args) {
		try {
			new MainScene();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
