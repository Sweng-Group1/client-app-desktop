package sweng.group.one.client_app_desktop;

import javax.swing.JFrame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.io.File;
import java.io.IOException;

import java.net.MalformedURLException;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import sweng.group.one.client_app_desktop.media.VideoPlayer;
import sweng.group.one.client_app_desktop.presentation.Slide;
import sweng.group.one.client_app_desktop.presentation.DemoElement;
import sweng.group.one.client_app_desktop.presentation.Presentation;
import sweng.group.one.client_app_desktop.sceneControl.SidebarScene;

/**
 * Hello world!
 *
 */
public class App extends JFrame
{

		private static Dimension minSize = new Dimension(400, 600);
		private static Dimension maxSize = new Dimension(2000, 600);
		private ArrayList<Presentation> presentations;

		SidebarScene sidebar = new SidebarScene(null);
		
		public App() throws MalformedURLException
	    {	
			int slideX = 50;
			int slideY = 100;
			presentations = new ArrayList<Presentation>();
			for (int i = 0; i < 3; i++) {
				//create slide and add to frame
		    	Slide slide = new Slide(slideX, slideY);
		    	ArrayList<Slide> slides = new ArrayList<>();
		    	slides.add(slide);
		    	
		    	slide.add(new DemoElement(new Point(0, 0), 1, 1, 0, slide));
		    	slide.add(new DemoElement(new Point(slideX-1, slideY-1), 1, 1, 0, slide));
		    	slide.add(new DemoElement(new Point(slideX/2-1, slideY/2-1), 10, 10, 0, slide));
		    	VideoPlayer vp = new VideoPlayer(new Point(0, 0), slideX, slideY, slide, new URL("https://getsamplefiles.com/download/mp4/sample-5.mp4"), true);
		    	slide.add(vp);
		    	
		    	Presentation pres = new Presentation(slides);
				presentations.add(pres);
				presentations.get(i).setVisible(true);
				presentations.get(i).nextSlide();
			}
			
			this.add(sidebar);
	    	sidebar.replacePres(presentations);
	    }
		
		public static void main(String[] args) throws MalformedURLException {
			JFrame frame = new App();
			frame.setMinimumSize(minSize);
			frame.validate();
			frame.setVisible(true);
			frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		}
	}

