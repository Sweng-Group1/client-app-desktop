package sweng.group.one.client_app_desktop.sceneControl;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;

import sweng.group.one.client_app_desktop.presentation.Presentation;

public class SidebarScene extends JPanel {
	
	// -------------------------------------------------------------- //
	// --------------------- Initialisations ------------------------ //
	// -------------------------------------------------------------- //
	
	private static final long serialVersionUID = 1L;
	private boolean isOpen;
	
	// The sidebar itself. Packing a JPanel into a JPanel isn't great, but it means
	// We can quickly hide the sidebar as a whole.
	
	private JPanel background;
	private JPanel sideBar;
	private JPanel searchBar;
	
	// Lower portion, containing presentations
	private JPanel presPanel;
	private List<Presentation> presentations;
	
	private JTextField searchTextField;
	private JButton minimiseButton;
	private JButton maximiseButton;
	private JButton searchButton;
	private JScrollPane presScroll;
	
	private GridBagConstraints gbc;
	
	// -------------------------------------------------------------- //
	// ----------------------- CONSTRUCTOR -------------------------- //
	// -------------------------------------------------------------- //
	
	public SidebarScene(ActionListener searchAction) {
		// Initialise
		gbc = new GridBagConstraints();          
		this.setLayout(new GridBagLayout());
		
		setUpSearchBar(searchAction);
		setUpSideBar(searchAction);
		setUpBackground();
		
		isOpen = true;
	}
	
	// -------------------------------------------------------------- //
	// -------------------------- LAYOUT ---------------------------- //
	// -------------------------------------------------------------- //
	
	private void setUpBackground() {
		// Initialise
        
        // The whole sidebar object
        // Can be in 'minimised' or 'maximised' mode
        background = new JPanel();
        background.setLayout(new GridBagLayout());  
        background.setName("SideBar");	

        // The maximise button 
     	// Appears when the sidebar is minimised
     	maximiseButton = new JButton(">>");

     	maximiseButton.addActionListener(new ActionListener() {
     		// Maximise on button click
     		@Override
     		public void actionPerformed(ActionEvent e) {
     			open();			
     		}
     	});

     	maximiseButton.setName("Maximise");
     	maximiseButton.setVisible(false); // Hide when maximised        
        
        // ---------- LAYOUT --------- //
     	
        // searchBar
        gbc.gridx = 0;
     	gbc.gridy = 0;
     	gbc.gridwidth = 1;
     	gbc.gridheight = 1;
     	gbc.weightx = 1;
		gbc.weighty = 0;
     	gbc.fill = GridBagConstraints.HORIZONTAL;
     	gbc.anchor = GridBagConstraints.ABOVE_BASELINE_LEADING;
     	background.add(searchBar, gbc);
     	
     	// sideBar
     	gbc.gridx = 0;
     	gbc.gridy = 1;
     	gbc.gridwidth = 1;
     	gbc.gridheight = 1;
     	gbc.weightx = 1;
		gbc.weighty = 1;
     	gbc.fill = GridBagConstraints.BOTH;
     	gbc.anchor = GridBagConstraints.ABOVE_BASELINE_LEADING;
     	background.add(sideBar, gbc);
     	
     	// maximiseButton
     	gbc.gridx = 0;
     	gbc.gridy = 1;
     	gbc.fill = GridBagConstraints.VERTICAL;
     	gbc.anchor = GridBagConstraints.ABOVE_BASELINE_LEADING;
     	background.add(maximiseButton, gbc);
     	background.setBorder(BorderFactory.createLineBorder(Color.black));
     	
     	// add background to JPanel
     	gbc.gridx = 0;
     	gbc.gridy = 0;
     	gbc.gridwidth = 0;
     	gbc.gridheight = 1;
     	gbc.weightx = 1;
		gbc.weighty = 1;
     	gbc.fill = GridBagConstraints.BOTH;
     	gbc.anchor = GridBagConstraints.ABOVE_BASELINE_LEADING;
     	this.add(background, gbc);
	}
	
	private void setUpSearchBar(ActionListener searchAction) {
		// --------- GENERATE ELEMENTS -------------- //
		
		searchBar = new JPanel();
        searchBar.setLayout(new GridBagLayout());  
        searchBar.setName("SideBar");	
		
		// Search text input box
		searchTextField = new JTextField();
		searchTextField.setName("Searchbar");
		
		// Search confirm button
		searchButton = new JButton("S");
		searchButton.setName("Search");
		// Search occurs when clicked
		searchButton.addActionListener(searchAction);
		
		// The minimise button
        // Appears when the sidebar is maximised
        minimiseButton = new JButton("<<");
		
        minimiseButton.addActionListener(new ActionListener() {
        	// Minimise on button click
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		close();	
        	}
		});
		minimiseButton.setName("Minimise");
		
		// ------------ LAYOUT ------------- //
		
		// Search text bar
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1;
		gbc.ipadx = 10;
		searchTextField.setMinimumSize(new Dimension(200, searchButton.getHeight()));
		searchBar.add(searchTextField, gbc);
		
		// Search button
		gbc.gridx = 1;
		gbc.weightx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.NONE;
		searchBar.add(searchButton, gbc);
		
		// Minimise button
		gbc.gridx = 2;
		gbc.gridy = 0;
		searchBar.add(minimiseButton, gbc);
	}
	
	private void setUpSideBar(ActionListener searchAction) {
		sideBar = new JPanel();
        sideBar.setLayout(new GridBagLayout());  
        sideBar.setName("SideBar");	
				
		// Presentation Panel
		presPanel = new JPanel();
		presPanel.setLayout(new GridBagLayout());
		presPanel.setName("Presentations");
				
		// Scroll Bar
		presScroll = new JScrollPane(presPanel);
				
		//---------------------- LAYOUT --------------------------//
				
		// Scroll bar
		gbc.gridy = 1;
		gbc.gridx = 0;
		gbc.gridwidth = 1;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.BOTH;
		//sideBar.add(presScroll, gbc);
		sideBar.setBorder(BorderFactory.createLineBorder(Color.black));
		sideBar.setPreferredSize(new Dimension(350, 100));
	}
	
	// -------------------------------------------------------------- //
	// ----------------------- WINDOW BEHAVIOUR --------------------- //
	// -------------------------------------------------------------- //
	
	public void open() {
		isOpen = true;
		maximiseButton.setVisible(false);
		minimiseButton.setVisible(true);
		sideBar.setVisible(true);
	}
	
	public void close() {
		isOpen = false;
		maximiseButton.setVisible(true);
		minimiseButton.setVisible(false);
		sideBar.setVisible(false);
	}
	
	public boolean isOpen() {
		return isOpen;
	}
	
	// -------------------------------------------------------------- //
	// ----------------------- PRESENTATIONS ------------------------ //
	// -------------------------------------------------------------- //
	
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
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1;
		gbc.weighty = 1;
		presPanel.removeAll();
		presentations = p;
		for (int i = 0; i < p.size(); i++) {
			Presentation pres = p.get(i);
			gbc.gridy = i;
			presPanel.add(pres, gbc);
			pres.setEnabled(true);
		}
	}
	
	// -------------------------------------------------------------- //
	// ----------------------- SEARCH ------------------------------- //
	// -------------------------------------------------------------- //
	
	/* Gets the content of the searchbar */
	public String getSearchText() {
		return searchTextField.getText();
	}
}
