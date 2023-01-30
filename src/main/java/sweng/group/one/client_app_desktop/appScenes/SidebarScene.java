package sweng.group.one.client_app_desktop.appScenes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SidebarScene extends JPanel {
	private static final long serialVersionUID = 1L;
	private boolean isOpen;
	
	// Top of the side bar, containing search bar and minimise button
	private JPanel top;
	
	// Lower portion, containing presentation thumbnails
	private JPanel thumbs;
	
	private JTextField searchBar;
	private JButton minimise;
	
	public SidebarScene() {
		// Init the top
		
		top = new JPanel(new FlowLayout());
		
		// Init its elements
		minimise = new JButton("_");
		minimise.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				close();
				
			}
		});
		minimise.setName("Minimise");
		
		searchBar = new JTextField();
		searchBar.setName("Searchbar");
		searchBar.setSize(100, 500);
		
		// Add them
		
		top.add(minimise);
		top.add(searchBar);
		
		// Set the colour (Mostly for demonstration atm)
		top.setBackground(Color.blue);
		
		thumbs = new JPanel();
		thumbs.setBackground(Color.white);
		
		
		this.setLayout(new BorderLayout());
		this.add(top, BorderLayout.NORTH);
		this.add(thumbs, BorderLayout.CENTER);
		this.setName("Sidebar");
	}
	
	public void open() {
		isOpen = true;
		this.setVisible(true);
	}
	public void close() {
		isOpen = false;
		this.setVisible(false);
	}
	public boolean search(String search) {
		return true;
	}
	public boolean isOpen() {
		return isOpen;
	}
}
