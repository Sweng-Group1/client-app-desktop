package sweng.group.one.client_app_desktop.sceneControl;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import sweng.group.one.client_app_desktop.presentation.Presentation;
import sweng.group.one.client_app_desktop.sideBarUIElements.CustomPanel;
import sweng.group.one.client_app_desktop.sideBarUIElements.CustomScrollBarUI;
import sweng.group.one.client_app_desktop.sideBarUIElements.Header;

/**
 * @author joncooke
 * Refactored sophiemaw
 * Re-refactored srikanthj
 */
public class SidebarScene extends JPanel implements ComponentInterface, LayoutManager{
	
	private static final long serialVersionUID = 1L;
	private boolean isOpen;
	
	private CustomPanel sidebarMainPanel;
	private JTextField searchBarTextField;
	private Header header;
	
	// GUI Components
	private JScrollBar scrollBar;
	private JPanel searchPanel;
	
	// Scrollpane to show presentations
	private JScrollPane scrollPane;
	// This is the viewport for the scrollpane
	private JPanel scrollView;
			
	private final static int searchBarInset = 10;
	private final static int rectCurveRadius = 20;

	public SidebarScene(ActionListener searchAction) {
		this.setOpaque(false);

		// Sets up search bar, back button, etc.
		setUpBackground();
		setUpSearchBar();
//		// Sets up the main Presentation scroll pane
//		//setUpScrollPane();
//		// 
//		//configureComponents();
//		// Button click animations
//		setUpSearchBar();
		isOpen=true;
		this.setLayout(this);
	}
	
	private void setUpSearchBar() {
		searchPanel = new JPanel() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void paint(Graphics g) {
				Graphics2D g2= (Graphics2D) g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setColor(Color.white);
				g2.fillRoundRect(this.getX(), this.getY(), this.getWidth(), this.getHeight(), this.getHeight(), this.getHeight());
				g2.dispose();
				super.paint(g);
			}
		};
		searchPanel.setOpaque(false);
		sidebarMainPanel.add(searchPanel);
	}
	
	private void setUpBackground() {
		sidebarMainPanel = new CustomPanel();
		header= new Header();
		sidebarMainPanel.setLayout(null);
		
		this.add(sidebarMainPanel);
		sidebarMainPanel.add(header);

	}
	
	// The ScrollPane is the Presentation View
	private void setUpScrollPane(){
		// The viewport to have a BoxLayout to order vertically
		scrollView = new JPanel();
		// Use BoxLayout with Y_AXIS orientation
		scrollView.setLayout(new BoxLayout(scrollView, BoxLayout.Y_AXIS)); 
		// Init scrollPane with scrollView JPanel viewport and add to sidebar jpanel
		scrollPane = new JScrollPane(scrollView);
		
		// Custom Scrollbar setup
		scrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);	
		scrollBar = scrollPane.getVerticalScrollBar();
		scrollBar.setUI(new CustomScrollBarUI());
		scrollBar.setOpaque(false);
		
		// Add scrollbar to JPanel
		this.add(scrollBar);
	
	}
	
	private void configureComponents() {
		header.setColours(colorDark, colorDark);
		scrollPane.setBackground(Color.red);
		scrollView.setBackground(Color.blue);
		scrollPane.setBorder(null);
		
		sidebarMainPanel.setScrollBar(scrollBar);
		sidebarMainPanel.setScrollPane(scrollPane);
		
		sidebarMainPanel.setVisible(true);
		header.setVisible(true);
		scrollPane.setVisible(true);
		
		this.setComponentZOrder(header, 0);
		this.setComponentZOrder(scrollBar, 1);
		this.setComponentZOrder(scrollPane, 2);
		this.setComponentZOrder(sidebarMainPanel, 3);

	}
	
	
	// Called to open the sidebar
	public void open(long timeTo) {
		isOpen = true;
		// Both run at the same time
		sidebarMainPanel.maximise(timeTo);
		header.setMinimise(timeTo);
	}
	
	// Called to close the sidebar
	public void close(long timeTo) {
		isOpen = false;
		// Both run at the same time
		sidebarMainPanel.minimise(timeTo);
		header.setMaximise(timeTo);
		// Hide the presentation elements
		scrollPane.setVisible(false);
		scrollBar.setVisible(false);
		
	}
	
	public boolean isOpen() {
		return isOpen;
	}
	
	/* Replaces the current presentation pane with p */
	public void replacePres(List<Presentation> p) {

		// Update the content of the containerPanel
		scrollView.removeAll(); // Clear the existing panels from the containerPanel
		System.out.println(scrollView.getComponentCount());
		for (Presentation panel : p) {
			scrollView.add(panel); // Add the updated panels to the containerPanel
			panel.showCurrentSlide(); // Show the first slide
			panel.setBackground(colorDark); // Same colour as sideBar for seamless look
		}
		scrollView.revalidate(); // Update the layout of the containerPanel
		scrollView.repaint(); // Refresh the visuals of the containerPanel
	}

	@Override
	public void addLayoutComponent(String name, Component comp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void layoutContainer(Container parent) {
		int gapWidth= 10;
		
		int w = this.getWidth();
		int h = this.getHeight();
		
		sidebarMainPanel.setBounds(gapWidth, gapWidth, w-gapWidth, h-(2*gapWidth));
		searchPanel.setBounds(gapWidth, gapWidth, sidebarMainPanel.getWidth()-4*gapWidth,(h)/15);
//		scrollBar.setBounds(w-10, header.getHeight(), gapWidth, sidebarMainPanel.getHeight()-searchPanel.getHeight());
//		scrollView.setBounds(gapWidth, gapWidth+header.getHeight(), w-gapWidth, sidebarMainPanel.getHeight()-searchPanel.getHeight()-gapWidth);
	}
	
	
}
