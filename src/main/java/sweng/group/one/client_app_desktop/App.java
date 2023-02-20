package sweng.group.one.client_app_desktop;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import sweng.group.one.client_app_desktop.sceneControl.MapScene;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	final JFrame frame = new JFrame();
		frame.setTitle("Map Demo");
		
		MapScene ms = new MapScene();
	    frame.add(ms);
		frame.pack();
		frame.setSize(1024, 768);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
       frame.setVisible(true);
    }
}
