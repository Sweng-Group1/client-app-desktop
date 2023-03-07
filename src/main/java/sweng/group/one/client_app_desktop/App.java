package sweng.group.one.client_app_desktop;

import javax.swing.JFrame;

import java.awt.BorderLayout;
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

		private ArrayList<Presentation> presentations;

		public App() throws MalformedURLException
	    {
			presentations = new ArrayList<Presentation>();
			for (int i = 0; i < 10; i++) {
				Slide s = new Slide(50, 40);
				ArrayList<Slide> slides = new ArrayList<>();
				slides.add(s);
				VideoPlayer vp = new VideoPlayer(new Point(0, 0), 
						50, 40, s, 
						new URL("https://getsamplefiles.com/download/mp4/sample-5.mp4"), true);
				s.add(vp);
				presentations.add(new Presentation(slides));
				presentations.get(i).setVisible(true);
				presentations.get(i).add(s);
				presentations.get(i).nextSlide();
			}
	    	setLayout(new BorderLayout());
	    	SidebarScene sidebar = new SidebarScene(null);
	    	add(sidebar, BorderLayout.WEST);
	    	sidebar.replacePres(presentations);
	    }
		
		public static void main(String[] args) throws MalformedURLException {
			JFrame frame = new App();
			frame.setVisible(true);
		}
	}

