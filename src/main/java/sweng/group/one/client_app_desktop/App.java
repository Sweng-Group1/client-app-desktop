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
		setSize(800, 600);
    	OptionsScene optionsScene = new OptionsScene();
    	add(optionsScene, BorderLayout.LINE_END);
    }
	
	public static void main(String[] args) {
		JFrame frame = new App();
		frame.setVisible(true);
	}
}
