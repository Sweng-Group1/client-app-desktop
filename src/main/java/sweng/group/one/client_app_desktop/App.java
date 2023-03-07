package sweng.group.one.client_app_desktop;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;
import java.net.MalformedURLException;

import java.util.ArrayList;

import java.net.URL;
import javax.swing.JFrame;
import javax.swing.JPanel;

import sweng.group.one.client_app_desktop.presentation.Slide;
import sweng.group.one.client_app_desktop.media.AudioPlayer;
import sweng.group.one.client_app_desktop.presentation.DemoElement;
import sweng.group.one.client_app_desktop.presentation.Presentation;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	//Set up JFrame
    	JFrame frame = new JFrame();
    	URL samplemp4 = null;
		try {
			samplemp4 = new URL("https://getsamplefiles.com/download/wav/sample-1.wav");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    	frame.setSize(800, 400);
    	frame.setVisible(true);
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	
    	int slideX = 50;
    	int slideY = 40;
    	
    	//create slide and add to frame
    	Slide slide = new Slide(slideX, slideY);
    	ArrayList<Slide> slides = new ArrayList<>();
    	slides.add(slide);
    	
    	slide.add(new DemoElement(new Point(0, 0), 1, 1, 0, slide));
    	slide.add(new DemoElement(new Point(slideX-1, slideY-1), 1, 1, 0, slide));
    	slide.add(new DemoElement(new Point(slideX/2-1, slideY/2-1), 10, 10, 0, slide));
    	
    	
		slide.add(new AudioPlayer(new Point(50, 50), 2, 2, 0, slide, samplemp4, false));
    	
    	Presentation pres = new Presentation(slides);
    	
    	frame.add(pres, BorderLayout.CENTER);
    	frame.validate();
    	pres.validate();
    	slide.validate();
    }
}
