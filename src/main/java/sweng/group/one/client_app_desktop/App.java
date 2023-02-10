package sweng.group.one.client_app_desktop;

import java.awt.BorderLayout;
import java.awt.Point;

import javax.swing.JFrame;

import sweng.group.one.client_app_desktop.presentation.Slide;
import sweng.group.one.client_app_desktop.presentation.TestElement;

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
    	frame.setSize(800, 400);
    	frame.setVisible(true);
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	
    	//create slide and add to frame
    	Slide slide = new Slide(80, 40);
    	frame.add(slide, BorderLayout.CENTER);
    	
    	//create testElement and add to slide
    	TestElement test = new TestElement(new Point(40, 20), 40, 20, 0, slide);
    	slide.add(test);
    	
    	frame.validate();
    }
}
