package sweng.group.one.client_app_desktop.sceneControl;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import sweng.group.one.client_app_desktop.presentation.Presentation;
import sweng.group.one.client_app_desktop.sideBarUIElements.CustomScrollBarUI;
import sweng.group.one.client_app_desktop.uiElements.RoundedButton;

/**
 * @author joncooke
 * Refactored sophiemaw
 * Re-refactored srikanthj
 */
public class SidebarScene extends JPanel implements ComponentInterface, LayoutManager{
	
	private static final long serialVersionUID = 1L;
	private static final int GAP_WIDTH= 10;
	private static final int ANIMATION_TIME_MS = 200;
	private static final int ANIMATION_FRAME_TIME_MS = 16;
	private static final int PRESENTATION_SCROLL_SPEED = 20;
	private boolean isOpen;
	
	private JPanel sidebarMainPanel;
	private JTextField searchField;
	private RoundedButton minimizeButton;
	private RoundedButton searchButton;
	
	private Image backArrow, forwardsArrow;
	
	// GUI Components
	private JScrollBar scrollBar;
	private JPanel searchPanel;
	
	// Scrollpane to show presentations
	private JScrollPane scrollPane;
	// This is the viewport for the scrollpane
	private JPanel scrollView;

	public SidebarScene() throws IOException {
		super();
		this.setOpaque(false);
		setUpBackground();
		setUpSearchBar();
		setUpScrollPane();
		this.isOpen = false;
		this.setLayout(this);
	}
	
	private void setUpSearchBar() throws IOException {
		searchField = new JTextField();
		searchPanel = new JPanel() {
			private static final long serialVersionUID = -343751050506935570L;

			public void paint(Graphics g) {
				Graphics2D g2 = (Graphics2D)g;
				g2.setColor(Color.white);
				RenderingHints qualityHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				qualityHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
				g2.setRenderingHints(qualityHints);
				g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), curvatureRadius, curvatureRadius);
				if(searchField.getText().isBlank()) {
					String str = "Search";
					g2.setColor(Color.black);
					g2.drawString(str, curvatureRadius/2,(this.getHeight()/2)+(g.getFontMetrics().getHeight()/4));
				}
				
				super.paint(g);
			}
		};
		
		searchPanel.add(searchField);
		searchPanel.setLayout(null);
		searchPanel.setOpaque(false);
		
		searchField.setOpaque(false);
		searchField.setBackground(transparent);
		searchField.setBorder(null);
		
		searchButton = new RoundedButton(ImageIO.read(new File("./assets/searchBlack.png")), curvatureRadius, Color.white, Color.darkGray, Color.gray);
		
		backArrow = ImageIO.read(new File("./assets/backBlack.png"));
		forwardsArrow = ImageIO.read(new File("./assets/forwardBlack.png"));
		
		minimizeButton = new RoundedButton(forwardsArrow, curvatureRadius, Color.white, Color.darkGray, Color.gray) {
			
			private static final long serialVersionUID = -4821983731589305576L;

			@Override
			public void buttonPressed() {
				if (isOpen) {
				    close();
				} else {
				    open();
				}
			}
		};
		
		searchPanel.add(searchButton);
		this.add(searchPanel);
		this.setComponentZOrder(searchPanel, 0);
		this.add(minimizeButton);
		this.setComponentZOrder(minimizeButton, 0);
	}
	
	private void setUpBackground() {
		sidebarMainPanel = new JPanel() {

			private static final long serialVersionUID = -5344802688141891908L;

			@Override
			public void paint(Graphics g) {
				Graphics2D g2= (Graphics2D) g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setColor(colorDark);
				g2.fillRoundRect(0,0,this.getWidth(),this.getHeight(), curvatureRadius, curvatureRadius);
				g2.dispose();
				super.paint(g);
			}
		};
		sidebarMainPanel.setOpaque(false);
		sidebarMainPanel.setLayout(null);
		this.add(sidebarMainPanel);
		this.setComponentZOrder(sidebarMainPanel, 0);

	}
	
	// The ScrollPane is the Presentation View
	private void setUpScrollPane(){
		// The viewport to have a BoxLayout to order vertically
		scrollView = new JPanel();
		scrollView.setOpaque(false);
		// Use BoxLayout with Y_AXIS orientation
		scrollView.setLayout(new BoxLayout(scrollView, BoxLayout.Y_AXIS)); 
		scrollView.setBackground(colorDark);
		// Init scrollPane with scrollView JPanel viewport and add to sidebar jpanel
		scrollPane = new JScrollPane(scrollView);
		scrollPane.setOpaque(false);
		scrollPane.setBackground(colorDark);
		scrollPane.setBorder(null);
		
		// Custom Scrollbar setup
		scrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollBar = scrollPane.getVerticalScrollBar();
		scrollBar.setUI(new CustomScrollBarUI());
		scrollBar.setOpaque(false);
		
		// Default mouse scroll speed is too slow, set to a better value:
		scrollPane.getVerticalScrollBar().setUnitIncrement(PRESENTATION_SCROLL_SPEED);
		
		// Add scrollbar to JPanel
		sidebarMainPanel.add(scrollBar);
		sidebarMainPanel.add(scrollPane);
	}
	
	
	// Called to open the sidebar
	public void open() {
		if(isOpen) {
			return;
		}
		
		Timer openTimer = new Timer();
		int widthInc = (this.getWidth() - GAP_WIDTH)*ANIMATION_FRAME_TIME_MS/ANIMATION_TIME_MS;

		Rectangle bounds = new Rectangle(GAP_WIDTH, GAP_WIDTH, 1, getHeight()-(2*GAP_WIDTH));
		sidebarMainPanel.setBounds(bounds);
		minimizeButton.setIconImage(backArrow);
		
		openTimer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				bounds.width += widthInc;
				sidebarMainPanel.setBounds(bounds);
			}
			
		}, 0, ANIMATION_FRAME_TIME_MS);
		
		openTimer.schedule(new TimerTask() {

			@Override
			public void run() {
				isOpen = true;
				bounds.width = getWidth() - GAP_WIDTH;
				sidebarMainPanel.setBounds(bounds);
				openTimer.cancel();
				openTimer.purge();
			}
			
		}, ANIMATION_TIME_MS);
		
		scrollPane.setVisible(true);
		scrollBar.setVisible(true);
	}
	
	// Called to close the sidebar
	public void close() {
		if(!isOpen) {
			return;
		}
		
		minimizeButton.setIconImage(forwardsArrow);
		Timer closeTimer = new Timer();
		int widthDecr = sidebarMainPanel.getWidth()*ANIMATION_FRAME_TIME_MS/ANIMATION_TIME_MS;
		closeTimer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				Rectangle b = sidebarMainPanel.getBounds();
				b.width -= widthDecr;
				sidebarMainPanel.setBounds(b);
			}
			
		}, 0, ANIMATION_FRAME_TIME_MS);
		
		closeTimer.schedule(new TimerTask() {

			@Override
			public void run() {
				isOpen = false;
				scrollPane.setVisible(false);
				scrollBar.setVisible(false);
				closeTimer.cancel();
				closeTimer.purge();
			}
			
		}, ANIMATION_TIME_MS);
		
		
	}
	
	public boolean isOpen() {
		return isOpen;
	}
	
	/* Replaces the current presentation pane with p */
	public void replacePres(List<Presentation> p) {

		// Update the content of the containerPanel
		scrollView.removeAll(); // Clear the existing panels from the containerPanel
		for (Presentation panel : p) {
			scrollView.add(panel); // Add the updated panels to the containerPanel
			panel.showCurrentSlide(); // Show the first slide
			panel.setBackground(colorDark); // Same colour as sideBar for seamless look
			scrollView.doLayout();
			scrollView.validate();
		}
		scrollView.revalidate(); // Update the layout of the containerPanel
		scrollView.repaint(); // Refresh the visuals of the containerPanel
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
		int w = this.getWidth();
		int h = this.getHeight();
		
		int mainPanelWidth = w-GAP_WIDTH;
		int mainPanelHeight = h-(2*GAP_WIDTH);
		int headerHeight = 30;
		
		
		sidebarMainPanel.setBounds(GAP_WIDTH, GAP_WIDTH, isOpen ? mainPanelWidth : 0, mainPanelHeight);
		minimizeButton.setBounds(mainPanelWidth-headerHeight, 2*GAP_WIDTH, headerHeight, headerHeight);
		searchPanel.setBounds(2*GAP_WIDTH, 2*GAP_WIDTH, mainPanelWidth-3*GAP_WIDTH-headerHeight, headerHeight);
		searchButton.setBounds(searchPanel.getWidth()-headerHeight, 0, headerHeight, headerHeight);
		searchField.setBounds(GAP_WIDTH, 0, searchPanel.getWidth()-GAP_WIDTH-headerHeight, headerHeight);
		
		scrollBar.setBounds(mainPanelWidth-GAP_WIDTH, 2*GAP_WIDTH+headerHeight, GAP_WIDTH, mainPanelHeight-headerHeight-3*GAP_WIDTH);
		scrollPane.setBounds(GAP_WIDTH, 2*GAP_WIDTH+headerHeight, mainPanelWidth-2*GAP_WIDTH, mainPanelHeight-headerHeight-3*GAP_WIDTH);
	}
	
	
}
