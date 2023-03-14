package sweng.group.one.client_app_desktop;

import javax.swing.JFrame;

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
			presentations = new ArrayList<Presentation>();
			for (int i = 0; i < 10; i++) {
				Slide s = new Slide(300, 300);
				ArrayList<Slide> slides = new ArrayList<>();
				slides.add(s);
				VideoPlayer vp = new VideoPlayer(new Point(0, 0), 
						300, 300, s, 
						new URL("https://getsamplefiles.com/download/mp4/sample-5.mp4"), true);
				s.add(vp);
				presentations.add(new Presentation(slides));
				presentations.get(i).setVisible(true);
				presentations.get(i).add(s);
				presentations.get(i).nextSlide();
			}
			
	    	setLayout(new GridBagLayout());
	    	GridBagConstraints gbc = new GridBagConstraints();
	    	
	    	gbc.gridheight = 1;
	    	gbc.gridwidth = 1;
	    	gbc.gridx = 0;
	    	gbc.gridy = 0;
	    	gbc.weightx = 1;
	    	gbc.weighty = 1;
	    	gbc.fill = GridBagConstraints.VERTICAL;
	    	gbc.anchor = GridBagConstraints.BASELINE_LEADING;
	    	
	    	this.add(sidebar, gbc);
	    	sidebar.replacePres(presentations);
	    }
		
		public static void main(String[] args) throws MalformedURLException {
			JFrame frame = new App();
			frame.setMinimumSize(minSize);
			frame.setVisible(true);
		}
	}

