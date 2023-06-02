package sweng.group.one.client_app_desktop;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import sweng.group.one.client_app_desktop.sceneControl.MainScene;
import sweng.group.one.client_app_desktop.sceneControl.MapScene;
import sweng.group.one.client_app_desktop.sceneControl.OptionsScene;
import sweng.group.one.client_app_desktop.sceneControl.SidebarScene;

public class App {
	
	public App() throws IOException {
		
		JFrame frame = new JFrame();
		frame.setSize(800, 500);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setVisible(true);	
		frame.getContentPane().setBackground(Color.red);
		
		OptionsScene os = new OptionsScene();
		
		frame.add(os);
		
		frame.validate();
	}

	public static void main(String[] args) throws IOException {
		App app= new App();

	}

}
