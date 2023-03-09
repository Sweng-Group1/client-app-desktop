package sweng.group.one.client_app_desktop.sceneControl;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;

import sweng.group.one.client_app_desktop.presentation.Presentation;

public class SidebarScene extends JPanel {
	private static final long serialVersionUID = 1L;
	private boolean isOpen;
	
	// The sidebar itself. Packing a JPanel into a JPanel isn't great, but it means
	// We can quickly hide the sidebar as a whole.
	private JPanel sidebar;
	private JPanel sidebarHeader;
	private JPanel sidebarBody;
	
	// Lower portion, containing presentations
	private JPanel presPanel;
	private List<Presentation> presentations;
	
	private JTextField searchBar;
	private JButton minimise;
	private JButton maximise;
	private JButton search;
	private JScrollPane presScroll;
	
	public SidebarScene(ActionListener searchAction) {
		GridBagConstraints gbc = new GridBagConstraints();  
        
        this.setLayout(new GridBagLayout());
        
        // The whole sidebar object
        // Can be in 'minimised' or 'maximised' mode
        sidebar = new JPanel();
        sidebar.setLayout(new GridBagLayout());  
        sidebar.setName("Sidebar");
        
        sidebarHeader = new JPanel();
        sidebarHeader.setLayout(new GridBagLayout());  
        sidebarHeader.setName("Sidebar");
        
        sidebarBody = new JPanel();
        sidebarHeader.setLayout(new GridBagLayout());  
        sidebarHeader.setName("Sidebar");
        
        // The minimise button
        // Appears when the sidebar is maximised
        minimise = new JButton("<<");
		minimise.addActionListener(new ActionListener() {
			// Minimise on button click
			@Override
			public void actionPerformed(ActionEvent e) {
				close();	
			}
		});
		minimise.setName("Minimise");
		
		// The maximise button 
		// Appears when the sidebar is minimised
		maximise = new JButton(">>");
		maximise.addActionListener(new ActionListener() {
			// Maximise on button click
			@Override
			public void actionPerformed(ActionEvent e) {
				open();
				
			}
		});
		maximise.setName("Maximise");
		maximise.setVisible(false); // Hide when maximised
		
		// Search text input box
		searchBar = new JTextField();
		searchBar.setName("Searchbar");
		
		// Search confirm button
		search = new JButton("Search");
		search.setName("Search");
		// Search occurs when clicked
		search.addActionListener(searchAction);
		
		presPanel = new JPanel();
		presPanel.setLayout(new GridBagLayout());
		presPanel.setName("Presentations");
		
		presScroll = new JScrollPane(presPanel);
		
		// Add them
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1;
		sidebarHeader.add(searchBar, gbc);
		gbc.gridx = 1;
		gbc.weightx = 0;
		gbc.fill = GridBagConstraints.NONE;
		sidebarHeader.add(search, gbc);
		gbc.gridx = 2;
		sidebarHeader.add(minimise, gbc);
		gbc.gridy = 1;
		gbc.gridx = 0;
		gbc.gridwidth = 3;
		gbc.gridheight = 1;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.BOTH;
		sidebarBody.add(presScroll, gbc);
		sidebarBody.setBorder(BorderFactory.createLineBorder(Color.black));
		sidebarBody.setPreferredSize(new Dimension(350, 100));
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		sidebar.add(sidebarHeader, gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		sidebar.add(sidebarBody, gbc);
		
		gbc.gridx = 0;
		gbc.fill = GridBagConstraints.VERTICAL;
		this.add(sidebar, gbc);
		this.add(maximise, gbc);
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		
		isOpen = true;
	}
	
	public void open() {
		isOpen = true;
		maximise.setVisible(false);
		sidebar.setVisible(true);
	}
	public void close() {
		isOpen = false;
		maximise.setVisible(true);
		sidebar.setVisible(false);
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
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
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
	
	/* Gets the content of the searchbar */
	public String getSearchText() {
		return searchBar.getText();
	}
}
