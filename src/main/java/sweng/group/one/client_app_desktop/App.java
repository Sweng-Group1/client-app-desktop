package sweng.group.one.client_app_desktop;
import java.awt.Color;
import java.io.IOException;

import javax.swing.JFrame;

import javax.swing.WindowConstants;

import sweng.group.one.client_app_desktop.sceneControl.LoginScene;
import sweng.group.one.client_app_desktop.sceneControl.OptionsScene;
import sweng.group.one.client_app_desktop.sideBarUIElements.SearchBar;

public class App {
	
	public App() throws IOException {
		
		JFrame frame = new JFrame();
		frame.setSize(800, 500);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setVisible(true);	
		frame.getContentPane().setBackground(Color.red);
		
		//OptionsScene os = new OptionsScene();
		LoginScene ls = new LoginScene();
		
		SearchBar sb = new SearchBar();
		
		
		frame.add(sb);
		
		frame.validate();
	}

	public static void main(String[] args) throws IOException {
		App app= new App();

	}

}

