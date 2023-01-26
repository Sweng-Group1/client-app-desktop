package sweng.group.one.client_app_desktop;

import javax.swing.JFrame;

import sweng.group.one.client_app_desktop.appScenes.SidebarScene;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	JFrame f=new JFrame("Unnamed App");
    	SidebarScene sidebar = new SidebarScene();
    	f.add(sidebar);
    }
}
