package sweng.group.one.client_app_desktop.sceneControl;

import java.awt.Color;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.junit.Before;
import org.junit.Test;


public class UploadSceneTest {
	JFrame frame; 
	JPanel background;
	Color colourD;
	Color colourL;
	
	@Before
	public void SetUpSceneTest() throws IOException{
		frame = new JFrame();
    	background= new JPanel();
    	background.setLayout(null);
    	background.setBackground(new Color(0,0,0));
    	frame.add(background);
    	frame.setSize(1920, 1080);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);	
		frame.add(background);
		colourD= new Color(46,71,117);
		colourL= new Color(78,106,143);
	}
	@Test
	public void testUploadScene() throws IOException {
		/*//TAB BAR TEST
		TabBar tabBar= new TabBar(Color.white,100);
		background.add(tabBar);
		tabBar.setSize(900, 200);
		tabBar.setLocation(100, 100);
		*/
		
		
		
		//Upload Scene TEST
		UploadScene upload = new UploadScene();
		background.add(upload);
		upload.setSize(1000,600);
		upload.setLocation(100,100);
		
		
		//Circle Button TEST
		//CircleButton button = new CircleButton();
		//background.add(button);
		//button.setSize(100);
		//button.setLocation(400, 400);
		//button.setText("THIS IS A BUTTON");
		//button.setMainBackground(Color.blue);
		//button.setBorder(Color.GREEN, 5);
		
		//Text box TEST
		//TextBox textBox = new TextBox(Color.black,Color.blue,20);
		//background.add(textBox);
		//textBox.setSize(200,200);
		//textBox.setLocation(600, 200);
		
		/*CircleTab tab= new CircleTab(Color.white,1,100,Color.white);
		background.add(tab);
		tab.setSize(300, 100);
		tab.setLocation(300, 300);
		tab.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if(tab.isOnTop()==true) {
					tab.setOnTop(false);
				}else {
					tab.setOnTop(true);
				}
				
				tab.repaint();
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		}); */
		
		while (true) {
			//do nothing
		
		}
		
		
		
		
		//UploadScene uploadScene= new UploadScene();
		
		
	}
	public static void main(String args[]) {
		
	}
	
    	
}
