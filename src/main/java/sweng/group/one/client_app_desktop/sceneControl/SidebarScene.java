package sweng.group.one.client_app_desktop.sceneControl;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
	
	// -------------------------------------------------------------- //
	// ----------------------- CONSTRUCTOR -------------------------- //
	// -------------------------------------------------------------- //
	
	public SidebarScene(ActionListener searchAction) {
		
		setUpSideBar(searchAction)
		isOpen = true;
	
	}
	
	// -------------------------------------------------------------- //
	// -------------------------- LAYOUT ---------------------------- //
	// -------------------------------------------------------------- //
	
	private void setUpSideBar(ActionListener searchAction) {
		// Initialise
				GridBagConstraints gbc = new GridBagConstraints();  
		        
		        this.setLayout(new GridBagLayout());
		        
		        // The whole sidebar object
		        // Can be in 'minimised' or 'maximised' mode
		        sideBar = new JPanel();
		        sideBar.setLayout(new GridBagLayout());  
		        sideBar.setName("SideBar");
		        
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
				
				// Search text input box
				searchTextField = new JTextField();
				searchTextField.setName("Searchbar");
				
				// Search confirm button
				searchButton = new JButton("Search");
				searchButton.setName("Search");
				// Search occurs when clicked
				searchButton.addActionListener(searchAction);
				
				// Presentation Panel
				presPanel = new JPanel();
				presPanel.setLayout(new GridBagLayout());
				presPanel.setName("Presentations");
				
				// Scroll Bar
				presScroll = new JScrollPane(presPanel);
				
				//---------------------------------------------------//
				//------------- ADD ELEMENTS TO LAYOUT --------------//
				//---------------------------------------------------//
				
				// Search text bar
				gbc.gridx = 0;
				gbc.gridy = 0;
				gbc.fill = GridBagConstraints.BOTH;
				gbc.weightx = 1;
				sidebar.add(searchTextField, gbc);
				
				// Search button
				gbc.gridx = 1;
				gbc.weightx = 0;
				gbc.gridy = 0;
				gbc.fill = GridBagConstraints.NONE;
				sidebar.add(searchButton, gbc);
				
				// Minimise button
				gbc.gridx = 2;
				gbc.gridy = 0;
				sidebar.add(minimiseButton, gbc);
				
				// Scroll bar
				gbc.gridy = 1;
				gbc.gridx = 0;
				gbc.gridwidth = 0;
				gbc.weighty = 1;
				gbc.fill = GridBagConstraints.BOTH;
				sidebar.add(presScroll, gbc);
				sidebar.setBorder(BorderFactory.createLineBorder(Color.black));
				sidebar.setPreferredSize(new Dimension(350, 100));
				
				// Add the whole sidebar to the class
				gbc.gridx = 0;
				gbc.gridy = 0;
				gbc.gridwidth = 0;
				gbc.fill = GridBagConstraints.VERTICAL;
				this.add(sidebar, gbc);
				gbc.gridx = 0;
				gbc.gridy = 1;
				this.add(maximiseButton, gbc);
				this.setBorder(BorderFactory.createLineBorder(Color.black));
		
	}
	
	// -------------------------------------------------------------- //
	// ----------------------- WINDOW BEHAVIOUR --------------------- //
	// -------------------------------------------------------------- //
	
	public void open() {
		isOpen = true;
		maximiseButton.setVisible(false);
		sideBar.setVisible(true);
	}
	
	public void close() {
		isOpen = false;
		maximiseButton.setVisible(true);
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
