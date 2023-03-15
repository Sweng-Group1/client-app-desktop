package sweng.group.one.client_app_desktop.sceneControl;

import java.io.File;

import javax.swing.JFrame;

public class MainScene extends JFrame{
	
	private MapScene mapScene;
	
	public MainScene() {
		super();
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		mapScene = new MapScene();
		this.add(mapScene);

		this.pack();
		this.setSize(1200, 800);
		
		mapScene.loadMapFile(new File("./assets/map/york.map"));
	}

	public static void main(String[] args) {
		MainScene ms = new MainScene();
		
	}

}
