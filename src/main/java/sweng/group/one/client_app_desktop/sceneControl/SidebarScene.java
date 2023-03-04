package sweng.group.one.client_app_desktop.sceneControl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
	private JButton search;
	private JScrollPane presScroll;
	
	public SidebarScene(ActionListener searchAction) {
		// Init the top
		
		top = new JPanel();
		top.setLayout(new BoxLayout(top, BoxLayout.X_AXIS));
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
		
		search = new JButton("Search");
		search.setName("SearchButton");
		search.addActionListener(searchAction);
		
		// Add them
		top.add(searchBar);
		top.add(search);
		top.add(minimise);
		
		// Set the colour (Mostly for demonstration atm)
		top.setBackground(Color.blue);
		
		presPanel = new JPanel();
		presPanel.setLayout(new BoxLayout(presPanel, BoxLayout.Y_AXIS));
		presScroll = new JScrollPane(presPanel);
		this.setLayout(new BorderLayout());
		this.add(top, BorderLayout.NORTH);
		this.add(presScroll, BorderLayout.CENTER);
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
		// What we might want to do is make a presentationPanel for the presentation being viewed
		// At the moment we just put this one to the top of the list, and then move to the top of 
		// the scrollpane.
		List<Presentation> newPresentations = new ArrayList<Presentation>();
		presentations.remove(p); // Remove p from the list.
		newPresentations.add(p);
		newPresentations.addAll(presentations);
		
		replacePres(newPresentations);
		
		presScroll.getVerticalScrollBar().setValue(0);
		
	}
	/* Replaces the current presentation list with p */
	public void replacePres(List<Presentation> p) {
		presPanel.removeAll();
		presentations = p;
		for (Presentation pres : p) {
			presPanel.add(pres);
			pres.setEnabled(true);
		}
	}
	
	/* Gets the content of the searchbar */
	public String getSearchText() {
		return searchBar.getText();
	}
}
