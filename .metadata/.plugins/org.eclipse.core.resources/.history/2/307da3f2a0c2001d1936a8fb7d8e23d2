package sweng.group.one.client_app_desktop;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import sweng.group.one.client_app_desktop.sceneControl.OptionsScene;

public class App extends JFrame
{
    private static final long serialVersionUID = 1L;

	public App()
    {
    	//setLayout(new BorderLayout());
		setSize(1280, 720);
		
    	OptionsScene optionsScene = new OptionsScene() {
    		@Override
    		public void accountButtonPressed () {
    			System.out.println("Account Button Pressed");
    		}
    		@Override
    		public void addPostButtonPressed() {
    			System.out.println("Add Post Button Presssed");
    		}
    		@Override
    		public void helpButtonPressed() {
    			System.out.println("Help Button Pressed");
    		}    	
    		@Override
    		public void closeButtonPressed() {
    			System.out.println("Close Button Pressed");
    		}
    	};    		
    	add(optionsScene, BorderLayout.LINE_END);
    }
	
	public static void main(String[] args) {
		JFrame frame = new App();
		frame.setVisible(true);
	}
	
}
