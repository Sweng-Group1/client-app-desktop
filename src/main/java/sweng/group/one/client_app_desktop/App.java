package sweng.group.one.client_app_desktop;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import sweng.group.one.client_app_desktop.sceneControl.OptionsScene;

/**
 * Hello world!
 *
 */
public class App extends JFrame
{
    private static final long serialVersionUID = 1L;

	public App()
    {
    	//setLayout(new BorderLayout());
		setSize(1280, 720);
		
    	OptionsScene optionsScene = new OptionsScene() {
    		@Override
    		public void accountButtonCreate () {
    			
    		}
    		@Override
    		public void addPostButtonCreate() {
    			
    		}
    		@Override
    		public void helpButtonCreate() {
    			
    		}    	
    		@Override
    		public void closeButton() {
    			
    		}
    	};    		
    	add(optionsScene, BorderLayout.LINE_END);
    }
	
	public static void main(String[] args) {
		JFrame frame = new App();
		frame.setVisible(true);
	}
	
}
