package sweng.group.one.client_app_desktop;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import sweng.group.one.client_app_desktop.sceneControl.MainScene;
import sweng.group.one.client_app_desktop.sceneControl.MapScene;
import sweng.group.one.client_app_desktop.sceneControl.SidebarScene;

public class App {
	
	static final Dimension screenD= Toolkit.getDefaultToolkit().getScreenSize();
	JPanel backPanel;
	JPanel midPanel;
	JPanel topPanel;
	
	public App() {
		
		JFrame frame = new JFrame();
		frame.setSize(screenD);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setVisible(true);	
		frame.setLayout(null);
		
		
		JLayeredPane layered= frame.getLayeredPane();
		layered.setLayout(null);
		
		SidebarScene sb= new SidebarScene(null);
		layered.setLayer(sb, 0);
		
		layered.add(sb);
		sb.setLocation(0, 0);
		sb.setSize(screenD.width/3, screenD.height);
		
		frame.validate();
	
	}

	public static void main(String[] args) {
		App app= new App();

	}

}
