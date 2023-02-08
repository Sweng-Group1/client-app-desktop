package sweng.group.one.client_app_desktop.appScenes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import sweng.group.one.client_app_desktop.presentation.Presentation;

public class SidebarScene extends JPanel {
	private static final long serialVersionUID = 1L;
	private boolean isOpen;
	
	// Top of the side bar, containing search bar and minimise button
	private JPanel top;
	
	// Lower portion, containing presentations
	private JPanel presPanel;
	private List<Presentation> presentations;
	
	private JTextField searchBar;
	private JButton minimise;
	
	public SidebarScene() {
		// Init the top
		
		top = new JPanel(new BorderLayout());
		
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
		
		// Add them
		top.add(minimise, BorderLayout.WEST);
		top.add(searchBar, BorderLayout.CENTER);
		
		// Set the colour (Mostly for demonstration atm)
		top.setBackground(Color.blue);
		
		//presPanel = new JPanel(new BoxLayout(presPanel, BoxLayout.Y_AXIS));
		
		this.setLayout(new BorderLayout());
		this.add(top, BorderLayout.NORTH);
		//this.add(new JScrollPane(presPanel), BorderLayout.CENTER);
		this.setName("Sidebar");
		this.setPreferredSize(new Dimension(200,200));
	}
	
	public void open() {
		isOpen = true;
		this.setVisible(true);
	}
	public void close() {
		isOpen = false;
		this.setVisible(false);
	}
	public boolean isOpen() {
		return isOpen;
	}
	/* Moves P to the top of the presentation stack */
	public void goTo(Presentation p) {
		
	}
	/* Replaces the current presentation list with p */
	public void replacePres(List<Presentation> p) {
		
	}
}
