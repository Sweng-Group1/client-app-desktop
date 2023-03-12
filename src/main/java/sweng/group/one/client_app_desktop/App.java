package sweng.group.one.client_app_desktop;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import sweng.group.one.client_app_desktop.sceneControl.LoginScene;

/**
 * Hello world!
 *
 */
public class App extends JFrame
{
	static final Dimension screenSize= Toolkit.getDefaultToolkit().getScreenSize();
    private static final long serialVersionUID = 1L;

	public App() throws IOException
    {
    	setLayout(new BorderLayout());
    	LoginScene loginScene = new LoginScene();
    	add(loginScene, BorderLayout.CENTER);
    	loginScene.setSize(450, 500);
    	loginScene.setLocation((int)screenSize.getWidth()/3,(int)screenSize.getHeight()/6);
		//loginScene.setBackground(new Color(28,34,56));
    }
	
	public static void main(String[] args) throws IOException {
		JFrame frame = new App();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);	
		frame.setLayout(null);
		frame.setSize((int)screenSize.getWidth(), (int) screenSize.getHeight());
		frame.setVisible(true);
	}
}
