package sweng.group.one.client_app_desktop.sceneControl;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Dimension;
import java.awt.Font;
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
import javax.swing.border.LineBorder;
import javax.swing.plaf.ScrollPaneUI;

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
	
	// ----------- CONSTANTS -------------
	
	private final static int searchBarInset = 10;
	private final static int rectCurveRadius = 20;
	
	private final static Color sideBarBlue = new Color(32,41,57);
	
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
	// -------------------- ANONYMOUS CLASSES ----------------------- //
	// -------------------------------------------------------------- //
	
	/** Search text field bubble */
	class SearchBar extends JPanel {
		
		// Initialisations
		int curveRadius;
		
		// ----------------------------------------------- //
		// ------------------ CONSTRUCTION --------------- //
		// ----------------------------------------------- //
		
		public SearchBar() {
			this.setOpaque(false);
			this.setLayout(null);
			//this.setBackground(Color.white);
		}
		
		// ----------------------------------------------- //
		// ----------------- VISUALISATION --------------- //
		// ----------------------------------------------- //
		
		public void paint(Graphics g) {
			Graphics2D g2= (Graphics2D) g.create();
			g2.setColor(Color.white);
			g2.fillRoundRect(0,0, this.getWidth(), this.getHeight(), curveRadius,curveRadius);
		}
		
		public void minimise(long timeToMinimise) {
			System.out.println("Search bar minimise");
		}
		
		public void maximise(long timeToMaximise) {
			System.out.println("Search bar");
		}
		
		// ---------------- SETTERS AND GETTERS -----------
		
		public void setSize(int width, int height, int curveRadius) {
			super.setSize(width, height);
			this.curveRadius= curveRadius;
		}
	};
	
	/** Creates the visual sidebar with animations */
	class SideBar extends JPanel {

		// ----------------------------------------------------------------
		// -------------------- INITIALISATIONS ---------------------------
		// ----------------------------------------------------------------
		
		int sideBarDrawnX; //this is moveable with maximising and minimising 
		int gapWidth;
		int maximisePos;
		int minimisePos;
		boolean isMoving;
		boolean isMaximised;
		int curveRadius;
		
		// ----------------------------------------------------------------
		// ----------------------- CONSTRUCTOR ----------------------------
		// ----------------------------------------------------------------
		
		public SideBar() {

			this.setOpaque(false);
			this.setLayout(null);
			
		}
		
		// -----------------------------------------------------------------
		// ---------------------- WINDOW BEHAVIOUR -------------------------
		// -----------------------------------------------------------------
		
		public void maximise(long timeToMaximise) {
			isMoving=true;
			long timeIntervals= timeToMaximise/(maximisePos-minimisePos);
			int timeIncrement=1;
			Timer timer= new Timer();
			for(int i=minimisePos;i<maximisePos;i++) {
				timer.schedule(new TimerTask() {

					@Override
					public void run() {
						sideBarDrawnX= sideBarDrawnX+1;
						repaintThis();
						if(sideBarDrawnX==maximisePos) {
							isMoving=false;
							isMaximised=true;
						}
						
					}
					
				}, (timeIntervals*timeIncrement));	
				timeIntervals++;
			}
		}
		
		public void minimise(int timeToMinimise) {
			isMoving=true;
			long timeIntervals= timeToMinimise/(maximisePos-minimisePos);
			int timeIncrement=1;
			///int distanceToMove= this.getWidth();
			Timer timer= new Timer();
			for(int i=minimisePos;i<maximisePos;i++) {
				timer.schedule(new TimerTask() {

					@Override
					public void run() {
						sideBarDrawnX= sideBarDrawnX-1;
						repaintThis();
						if(sideBarDrawnX==minimisePos) {
							isMoving=false;
							isMaximised=false;
						}
						
					}
					
				}, (timeIntervals*timeIncrement));	
				timeIntervals++;
			}
			
		}
		
		// -----------------------------------------------------------------
		// ---------------------- GRAPHICS ---------------------------------
		// -----------------------------------------------------------------
		
		public void paint(Graphics g) {
			int recX= sideBarDrawnX;
			int recY= gapWidth;
			int recWidth= this.getWidth()-(2*gapWidth);
			int recHeight= this.getHeight()- (2*gapWidth);
				
			g.setColor(new Color(32,41,57));
			g.fillRoundRect(recX,recY,recWidth,recHeight, curveRadius,curveRadius);	
		}
		
		private void repaintThis() {
			this.repaint();
		}
		
		// -----------------------------------------------------------------
		// ---------------------- WINDOW BEHAVIOUR -------------------------
		// -----------------------------------------------------------------
		
		public boolean getMovingStatus() {
			return isMoving;
		}
		
		public boolean getMaximisedStatus() {
			return isMaximised;
		}
		
		public void setSize(int width, int height,int gapWidth, int curveRadius) {
			super.setSize(width, height);
			this.curveRadius = curveRadius;
			this.gapWidth = gapWidth;
			maximisePos = gapWidth;
			minimisePos = maximisePos - width;
			//start with maximised
			isMaximised = true;
			isMoving = false;
			sideBarDrawnX = maximisePos;
			
		}
	}

	// -------------------------------------------------------------- //
	// -------------------------- LAYOUT ---------------------------- //
	// -------------------------------------------------------------- //
	
	private void setUpBackground() {
		// -------------- OBJECTS AND APPEARANCE ------------------
        
        // The whole sidebar object
        // Can be in 'minimised' or 'maximised' mode
		
		background = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				
				int recWidth;
				if (isOpen) {
					recWidth = this.getWidth();
				}
				else {
					recWidth = 0;
				}
				
				int recHeight = this.getHeight();
					
				g.setColor(sideBarBlue);
				g.fillRoundRect(0,0,recWidth,recHeight,rectCurveRadius,rectCurveRadius);
			}
		};
		
        background.setLayout(new GridBagLayout());  
        background.setName("SideBarScene");

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
     	gbc = new GridBagConstraints();
     	
        // searchBar
        gbc.gridx = 0;
     	gbc.gridy = 0;
     	gbc.gridwidth = 1;
     	gbc.gridheight = 1;
     	gbc.weightx = 1;
		gbc.weighty = 0;
     	gbc.fill = GridBagConstraints.HORIZONTAL;
     	gbc.anchor = GridBagConstraints.CENTER;
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
     	//background.setBorder(BorderFactory.createLineBorder(Color.black));
     	
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
		
		searchBar = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				int recWidth = this.getWidth() - searchBarInset*2;
				int recHeight = this.getHeight() - searchBarInset;
					
				g.setColor(Color.WHITE);
				g.fillRoundRect(	searchBarInset,searchBarInset,recWidth,recHeight,
									rectCurveRadius,rectCurveRadius);
			}
		};
        searchBar.setLayout(new GridBagLayout());  
        searchBar.setName("SideBar");	
		
		// Search text input box
		searchTextField = new JTextField() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
			}
		};
		searchTextField.setName("Searchbar");
		searchTextField.setBorder(new LineBorder(Color.WHITE));
		searchTextField.setFont(new Font("Ariel", Font.PLAIN, 14));
		searchTextField.setHorizontalAlignment(JTextField.CENTER);
		
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
		gbc = new GridBagConstraints();
		int inset = searchBarInset;
		
		// Search text bar
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1;
		gbc.ipadx = 300;
		gbc.insets = new Insets(inset*2, inset*2, inset, 0);
		searchTextField.setMinimumSize(new Dimension(200, searchButton.getHeight()));
		searchBar.add(searchTextField, gbc);
		
		// Search button
		gbc.gridx = 1;
		gbc.weightx = 0;
		gbc.gridy = 0;
		gbc.ipadx = 0;
		gbc.insets = new Insets(inset*2, 0,inset, 0);
		gbc.fill = GridBagConstraints.NONE;
		searchBar.add(searchButton, gbc);
		
		// Minimise button
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.insets = new Insets(inset*2, 0, inset, inset*2);
		searchBar.add(minimiseButton, gbc);
	}
	
	private void setUpSideBar(ActionListener searchAction) {
		
		sideBar = new JPanel();
		/*
		{
			@Override
			public void paint(Graphics g) {
				int recWidth= this.getWidth();
				int recHeight= this.getHeight();
					
				g.setColor(sideBarBlue);
				g.fillRoundRect(0,0,recWidth,recHeight,70,70);
			}
		};
		*/
		
        sideBar.setLayout(new GridBagLayout());  
        sideBar.setName("SideBar");	
        sideBar.setOpaque(false);		
        
		// Presentation Panel
		presPanel = new JPanel();
		presPanel.setLayout(new GridBagLayout());
		presPanel.setName("Presentations");
		presPanel.setBackground(sideBarBlue);
				
		// Scroll Bar
		presScroll = new JScrollPane(presPanel);
		presScroll.setBackground(sideBarBlue);
		
		//---------------------- LAYOUT --------------------------//
		gbc = new GridBagConstraints();		
		
		// Scroll bar
		gbc.gridy = 1;
		gbc.gridx = 0;
		gbc.gridwidth = 1;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.BOTH;
		sideBar.setBackground(sideBarBlue);
		sideBar.add(presScroll, gbc);
		//sideBar.setBorder(BorderFactory.createLineBorder(Color.black));
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
