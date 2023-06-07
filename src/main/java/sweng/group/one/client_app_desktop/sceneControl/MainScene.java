package sweng.group.one.client_app_desktop.sceneControl;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.WindowConstants;
import javax.xml.parsers.ParserConfigurationException;

import org.mapsforge.core.graphics.Bitmap;
import org.mapsforge.map.awt.graphics.AwtBitmap;
import org.xml.sax.SAXException;

import sweng.group.one.client_app_desktop.mapping.EventMarker;
import sweng.group.one.client_app_desktop.presentation.Presentation;
import sweng.group.one.client_app_desktop.data.AuthenticationException;
import sweng.group.one.client_app_desktop.data.HashtagService;
import sweng.group.one.client_app_desktop.data.PostService;

public class MainScene extends JFrame implements LayoutManager{

	private static final long serialVersionUID = 7994175471538717547L;
	private MapScene mapScene;
	private SidebarScene sidebarScene;
	private LoginScene login;
	private HelpScene help;
	private OptionsScene options;
	
	public MainScene() {
		super("What's On: YUSU");
		
		this.setSize(800, 500);
		this.setMinimumSize(new Dimension(800, 500));
		this.setMaximumSize(new Dimension(1920, 1080));
		
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setVisible(true); 
		
		//instantiate all scenes
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
			this.setIconImage(ImageIO.read(new File("./assets/Yusu Logo 14.png")));
			sidebarScene = new SidebarScene() {
				private static final long serialVersionUID = -7428381636891994724L;

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
					help.setVisible(true);
					sidebarScene.close();
					login.setVisible(false);
				}
				
				@Override
				public void accountPressed() {
					sidebarScene.close();
					login.setVisible(true);
					help.setVisible(false);
				}
				
				@Override
				public void closePressed() {
					sidebarScene.setVisible(true);
					sidebarScene.close();
					login.setVisible(false);
					help.setVisible(false);
				}
			};
			login= new LoginScene();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Missing Assets");
		}
		
		help = new HelpScene("./assets/User_Manual.pdf");
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

		//add scenes and set appropiate draw order
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
		login.setVisible(false);
		help.setVisible(false);
		
		//allow use of layoutComponents method
		this.setLayout(this);
		
		//Load mapfile for York
		mapScene.loadMapFile(new File("./assets/map/york.map"));
		
		try {
			BufferedImage poiImage = ImageIO.read(new File("./assets/map/marker.png"));
			Bitmap markerImage = new AwtBitmap(poiImage);
			ArrayList<EventMarker> events = HashtagService.retrieveHashtagsAsEventMarkers(mapScene, markerImage);
			
			for(EventMarker e : events) {
				Thread downloadThread = new Thread() {
					@Override
					public void run() {
						ArrayList<Presentation> pres = new ArrayList<>();
						try {
							pres = PostService.retrievePostsByHashtagAsPresentations(e.getName());
						} catch (SAXException | ParserConfigurationException e1){
							new RuntimeException("The post downloaded was not approved by What's On: \n Exiting the program to prevent cyber attacks...");
						} catch (IOException| AuthenticationException e1) {
							System.err.println("Sorry, we cannot reach the server :(");
						}
						for(Presentation p : pres) {
							e.addPost(p);
						}
						mapScene.addEventMarker(e);
					}
				};
				downloadThread.start();
			}
			
		} catch (AuthenticationException e1) {
			System.err.println("Sorry, we cannot reach the server :(");
		} catch (IOException e2) {
			e2.printStackTrace();
			throw new RuntimeException("Missing Assets");
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
}