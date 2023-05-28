import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JFrame;

import sweng.group.one.client_app_desktop.sceneControl.MapScene;

public class App {

	public static void main(String[] args) throws FileNotFoundException {
		JFrame f = new JFrame();
		f.setSize(1000, 600);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		MapScene ms = new MapScene();
		
		f.add(ms);
		f.setVisible(true);
		
		File map = new File("./assets/map/york.map");
		File theme = new File("./assets/map/theme.xml");
		
		ms.loadMapFile(map, theme);
		
		
		

	}

}