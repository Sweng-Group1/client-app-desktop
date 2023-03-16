package sweng.group.one.client_app_desktop.sceneControl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.JFrame;

import org.mapsforge.core.model.LatLong;

import sweng.group.one.client_app_desktop.mapping.EventMarker;
import sweng.group.one.client_app_desktop.media.ImageViewer;
import sweng.group.one.client_app_desktop.media.VideoPlayer;
import sweng.group.one.client_app_desktop.presentation.DemoElement;
import sweng.group.one.client_app_desktop.presentation.Presentation;
import sweng.group.one.client_app_desktop.presentation.Slide;
import sweng.group.one.client_app_desktop.text.TextElement;

public class MainScene extends JFrame{
	
	private MapScene mapScene;
	private SidebarScene sidebarScene;
	
	public MainScene() {
		super();
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		mapScene = new MapScene() {
			@Override
			public void selectMarker(EventMarker selected) {
				super.selectMarker(selected);
				
				if (selected != null) {
					sidebarScene.replacePres(selected.getPosts());
				}
			}
		};
		this.add(mapScene, BorderLayout.CENTER);
		
		sidebarScene = new SidebarScene(null);
		this.add(sidebarScene, BorderLayout.WEST);
		sidebarScene.revalidate();

		this.pack();
		this.setSize(1200, 800);
		this.revalidate();
		
		mapScene.loadMapFile(new File("./assets/map/york.map"));
		try {
			addDemoMarkers();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.setVisible(true);
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
	    	
	    	Presentation pres = new Presentation(slides);
			mapScene.getMarkers().get(1).addPost(pres);
		}
		
		mapScene.addEventMarker(new LatLong(53.94536495592503, -1.0537439293136752));
		
		String[] fontNames = {"Times New Roman", "Calibri", "Comic Sans MS", "Cooper Black", "Complex"};
		
		for (int i = 0; i < 5; i++) {
			slideX = i%2 != 0 ? 9 : 16;
			slideY = i%2 == 0 ? 9 : 16;
			
			//create slide and add to frame
	    	Slide slide = new Slide(slideX, slideY);
	    	ArrayList<Slide> slides = new ArrayList<>();
	    	slides.add(slide);
	    	
	    	TextElement te = new TextElement("This is demonstration of how text will look", fontNames[i], 10*i + 1, new Color(0, 0, 255*i/5), 0, new Point(0, 0), slideX, slideY, slide);
	    	slide.add(te);
	    	slide.add(new DemoElement(new Point(0, 0), slideX, slideY, 0, slide));
	    	
	    	Presentation pres = new Presentation(slides);
			mapScene.getMarkers().get(2).addPost(pres);
		}
	}

	public static void main(String[] args) {
		MainScene ms = new MainScene();
	}
	
	

}
