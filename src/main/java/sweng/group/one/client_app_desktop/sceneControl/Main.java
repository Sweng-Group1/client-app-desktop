package sweng.group.one.client_app_desktop.sceneControl;

import javax.swing.JFrame;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import sweng.group.one.client_app_desktop.presentation.Presentation;

/**
 * Hello world!
 *
 */
public class Main extends JFrame {
		
		public Main() throws SAXException, IOException, ParserConfigurationException
	    {	
	    }
		
		public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException {
			JFrame frame = new Main();	
			frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
			
			
			File xml = new File("./assets/xml/WheresSlater.xml");
			Presentation presentation = new Presentation(xml);
			
			
			frame.add(presentation);
			frame.setVisible(true);
		
			frame.validate();
		}
	}