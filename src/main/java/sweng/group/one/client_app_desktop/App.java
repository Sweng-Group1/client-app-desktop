package sweng.group.one.client_app_desktop;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import sweng.group.one.client_app_desktop.sceneControl.UploadScene;

/**
 * Hello world!
 *
 */
public class App 
{
	private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private JFrame frame;
	private JPanel background;
	public App() throws IOException {
		setUpBackground();
		addUploadScene();
	}
	private void setUpBackground() {
		frame = new JFrame();
		frame.setSize(screenSize);
		frame.setVisible(true);
		frame.setLayout(null);
		background= new JPanel();
		frame.add(background);
		background.setSize(screenSize);
		background.setLocation(0, 0);
		background.setBackground(Color.black);
		background.setLayout(null);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	private void addUploadScene() throws IOException {
		UploadScene upload = new UploadScene(Color.DARK_GRAY,Color.LIGHT_GRAY);
		background.add(upload);
		upload.setSize((screenSize.width/5)*4, (screenSize.height/6)*4);
		upload.setLocation(screenSize.width/10, screenSize.height/6);
		upload.setVisible(true);
	}
	
	
    public static void main( String[] args ) throws IOException
    {
    	App app= new App();
    	
    	
    }
}
