package sweng.group.one.client_app_desktop.appScenes;

import java.awt.BorderLayout;
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
	static JPanel top;
	
	// Lower portion, containing presentation thumbnails
	static JPanel thumbs;
	
	static JTextField searchBar;
	static JButton minimise;
	
	public SidebarScene() {
		top = new JPanel(new FlowLayout());
		
		minimise = new JButton("_");
		minimise.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				close();
				
			}
		});
		
		top.add(minimise);
		top.add(searchBar);
		
		this.setLayout(new BorderLayout());
		this.add(top, BorderLayout.NORTH);
		this.add(thumbs, BorderLayout.CENTER);
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
