package sweng.group.one.client_app_desktop;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import sweng.group.one.client_app_desktop.presentation.Slide;
import sweng.group.one.client_app_desktop.presentation.DemoElement;
import sweng.group.one.client_app_desktop.presentation.Presentation;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws SAXException, IOException, ParserConfigurationException
    {
    	//Set up JFrame
//    	JFrame frame = new JFrame();
//    	frame.setSize(800, 400);
//    	frame.setVisible(true);
//    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    	
//    	int slideX = 50;
//    	int slideY = 40;
//    	
//    	//create slide and add to frame
//    	Slide slide = new Slide(slideX, slideY);
//    	ArrayList<Slide> slides = new ArrayList<>();
//    	slides.add(slide);
//    	
//    	slide.add(new DemoElement(new Point(0, 0), 1, 1, 0, slide));
//    	slide.add(new DemoElement(new Point(slideX-1, slideY-1), 1, 1, 0, slide));
//    	slide.add(new DemoElement(new Point(slideX/2-1, slideY/2-1), 10, 10, 0, slide));
//    	
//    	Presentation pres = new Presentation(slides);
//    	
//    	frame.add(pres, BorderLayout.CENTER);
//    	
//    	frame.add(pres);
//    	frame.validate();
//    	pres.validate();
//    	slide.validate();
    	
    	Presentation pFile = new Presentation(new File("assets/xml/evaluation.xml"));
    }
}
