package sweng.group.one.client_app_desktop;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import sweng.group.one.client_app_desktop.presentation.Presentation;
import sweng.group.one.client_app_desktop.presentation.Slide;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws SAXException, IOException, ParserConfigurationException
    {
    	//Set up JFrame
    	JFrame frame = new JFrame();
    	
    	frame.setVisible(true);
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.pack();
    	frame.setSize(800, 400);
    	
    	Presentation pFile = new Presentation(new File("assets/xml/evaluation.xml"));
    	frame.add(pFile);
    	
    	for (int i = 0; i < 10; i++) {
    		pFile.add(new Slide(100, 100));
    	}
    	
    	
    	
    	frame.validate();
    	pFile.validate();
    	
    	frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
            	pFile.validate();
            	frame.validate();
            }
    	});
    }
}