package sweng.group.one.client_app_desktop;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

import sweng.group.one.client_app_desktop.sceneControl.SidebarScene;

/**
 * Hello world!
 *
 */
public class App extends JFrame
{
    private static final long serialVersionUID = 1L;

	public App()
    {
    	setLayout(new BorderLayout());
    	SidebarScene sidebar = new SidebarScene();
    	add(sidebar, BorderLayout.WEST);
    }
	
	public static void main(String[] args) {
		JFrame frame = new App();
		frame.setVisible(true);
	}
}
