package sweng.group.one.client_app_desktop.sceneControl;

import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.junit.Before;
import org.junit.Test;


public class UploadSceneTest {
	JFrame frame; 
	JPanel background;

	
	@Before
	public void SetUpSceneTest() throws IOException{
		frame = new JFrame();
    	background= new JPanel();
    	background.setLayout(null);
    	frame.add(background);
    	frame.setSize(1920, 1080);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);	
		frame.add(background);
		
		//upload = new UploadScene();
		//background.add(upload);
		//upload.setSize(1000,600);
		//upload.setLocation(100,100);
		
	}
	@Test
	public void testUploadScene() throws IOException {
		UploadScene upload = new UploadScene();
		background.add(upload);
		upload.setSize(1000,600);
		upload.setLocation(100,100);
		while (true) {
			//do nothing
		}
		
		
		
		
		//UploadScene uploadScene= new UploadScene();
		
		
	}
	public static void main(String args[]) {
		
	}
	
    	
}
