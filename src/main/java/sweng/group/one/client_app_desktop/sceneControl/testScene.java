package sweng.group.one.client_app_desktop.sceneControl;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Point;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.mapsforge.core.model.LatLong;

import sweng.group.one.client_app_desktop.media.ImageViewer;
import sweng.group.one.client_app_desktop.media.VideoPlayer;
import sweng.group.one.client_app_desktop.presentation.DemoElement;
import sweng.group.one.client_app_desktop.presentation.Presentation;
import sweng.group.one.client_app_desktop.presentation.Slide;
import sweng.group.one.client_app_desktop.text.TextElement;

public class testScene extends JFrame implements LayoutManager {
	
    private HelpScene help;

    public testScene() {
        setTitle("PDF Viewer");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        String filePath = "./assets/User_Manual.pdf";
        help = new HelpScene(filePath);

        add(help);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            testScene mainGUI = new testScene();
            mainGUI.setVisible(true);
        });
    }
    

	@Override
	public void addLayoutComponent(String name, Component comp) {
	}
	
	@Override
	public void removeLayoutComponent(Component comp) {
	}
	
	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return null;
	}
	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return null;
	}
	@Override
	public void layoutContainer(Container parent) {
		int w = parent.getWidth();
		int h = parent.getHeight();
		
		help.setBounds(0, 0, w, h);
		help.layoutContainer(getContentPane());
	}
}
