package sweng.group.one.client_app_desktop.sceneControl;

import java.awt.Color;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.junit.Before;
import org.junit.Test;

import sweng.group.one.client_app_desktop.uiElements.CircleButton;


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
				
	}
	@Test
	public void testUploadScene() throws IOException {
		UploadScene upload = new UploadScene();
		background.add(upload);
		upload.setSize(1000,600);
		upload.setLocation(100,100);
		//CircleButton button = new CircleButton();
		//background.add(button);
		//button.setSize(100);
		//button.setLocation(400, 400);
		//button.setText("THIS IS A BUTTON");
		//button.setMainBackground(Color.blue);
		//button.setBorder(Color.GREEN, 5);
		
		while (true) {
			//do nothing
		
		}
		
		
		
		
		//UploadScene uploadScene= new UploadScene();
		
		
	}
	public static void main(String args[]) {
		
	}
	
    	
}
