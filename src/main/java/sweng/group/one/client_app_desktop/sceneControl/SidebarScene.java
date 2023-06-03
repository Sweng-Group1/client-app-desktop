package sweng.group.one.client_app_desktop.sceneControl;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import sweng.group.one.client_app_desktop.presentation.Presentation;
import sweng.group.one.client_app_desktop.sideBarUIElements.CustomPanel;
import sweng.group.one.client_app_desktop.sideBarUIElements.CustomScrollBarUI;
import sweng.group.one.client_app_desktop.sideBarUIElements.Header;
import sweng.group.one.client_app_desktop.sideBarUIElements.PresentationPanel;
import sweng.group.one.client_app_desktop.data.AuthenticationException;
import sweng.group.one.client_app_desktop.data.PostService;

/**
 * Modified JPanel sliding viewer to hold presentation slides which can overlay other JPanels
 * Includes a search field which can be used to fetch posts by hashtag
 * 
 * Added 03/06/2023
 * 
 * @author Will Hinton, Jonathan Cooke, Sophie Maw & Luke George
 * 
 *
 */
public class SidebarScene extends JPanel implements ComponentInterface{
	
	// -------------------------------------------------------------- //
	// --------------------- INITIALISATIONS ------------------------ //
	// -------------------------------------------------------------- //
	
	private static final long serialVersionUID = 1L;
	private boolean isOpen;
	
	private CustomPanel panel;
	private Header header;	
	private JButton minimiseButton;
	private JButton maximiseButton;
	private JButton searchButton;
	private JPanel scrollView;
	private JScrollBar scrollBar;
	private JScrollPane scrollPane;
	private JTextField searchBarTextField;
	private JTextField searchTextField;	
	private ArrayList<PresentationPanel>presentationPanels;
	
	// -------------------------------------------------------------- //
	// ----------------------- CONSTRUCTOR -------------------------- //
	// -------------------------------------------------------------- //
	
	/**
	 * 
	 * @param searchAction
	 */
	public SidebarScene(ActionListener searchAction) {
		this.setLayout(null);
		this.setOpaque(false);

		setUpBackground();
		setUpScrollPane();
		configureComponents();
		setMouseListeners();
		isOpen=true;
	}
	
	// -------------------------------------------------------------- //
	// -------------------------- LAYOUT ---------------------------- //
	// -------------------------------------------------------------- //
	
	/**
	 * Creates the background of the sidebar
	 */
	private void setUpBackground() {
		panel = new CustomPanel();
		header = new Header();
		
		this.add(panel);
		this.add(header);

		searchBarTextField = header.getSearchBarTextField();
		searchButton = header.getSearchButton();
		maximiseButton = header.getMaximiseButton();
		minimiseButton = header.getMinimiseButton();
	}
	
	/**
	 * Creates and implements scrolling functionality of the sidebar
	 */
	private void setUpScrollPane(){
		scrollView = new JPanel();
		scrollPane = new JScrollPane(scrollView);
		this.add(scrollPane);
		
		scrollView.setLayout(new GridBagLayout());
		scrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);	
		scrollBar = scrollPane.getVerticalScrollBar();
		scrollBar.setUI(new CustomScrollBarUI());
		scrollBar.setOpaque(false);
		this.add(scrollBar);	
	}
	
	/**
	 * Configures the individual components that make up the sidebar
	 * Sets colours, visibility and order
	 */
	private void configureComponents() {
		header.setColours(colorDark, colorDark);
		panel.setBackground(colorDark, colorLight);
		scrollPane.setBackground(colorDark);
		scrollView.setBackground(colorDark);
		scrollPane.setBorder(null);
		
		panel.setScrollBar(scrollBar);
		panel.setScrollPane(scrollPane);
		
		panel.setVisible(true);
		header.setVisible(true);
		scrollPane.setVisible(true);
		
		this.setComponentZOrder(header, 0);
		this.setComponentZOrder(scrollBar, 1);
		this.setComponentZOrder(scrollPane, 2);
		this.setComponentZOrder(panel, 3);
	}

	/**
	 * Resizes the sidebar and its components
	 * 
	 * @param width Desired width of the sidebar
	 * @param height Desired height of the sidebar
	 */
	public void setSize(int width, int height) {
		super.setSize(width, height);
		int gapWidth = 10;
		int curveRadius = 30;
		
		Rectangle rPanel = new Rectangle(gapWidth,gapWidth, width-gapWidth, height-(2*gapWidth));

		panel.setBounds(rPanel,curveRadius);
		Rectangle rHeader = new Rectangle(gapWidth, gapWidth, width-gapWidth,(height-gapWidth)/15);
		header.setBounds(rHeader,curveRadius);
		
		scrollBar.setSize(10, height-rHeader.height);
		scrollBar.setLocation(width-10, rHeader.height);
		
		scrollPane.setSize(rPanel.width, rPanel.height-rHeader.height);
		scrollPane.setLocation(rPanel.x, rPanel.y+rHeader.height);	
		scrollPane.setVisible(true);
		scrollView.setVisible(true);
		
		if(presentationPanels!=null) {
			for(int i=0;i<presentationPanels.size();i++) {
				presentationPanels.get(i).setPreferredSize(new Dimension(width,width));
			}
		}
	}
	
	// -------------------------------------------------------------- //
	// ------------------------ OPERATION --------------------------- //
	// -------------------------------------------------------------- //
	
	/**
	 * Opens the sidebar
	 * 
	 * @param timeTo How long it takes to open the sidebar
	 */
	public void open(long timeTo) {
		isOpen = true;
		panel.maximise(timeTo);
		header.setMinimise(timeTo);
	}
	
	/**
	 * Closes the sidebar
	 * 
	 * @param timeTo How long it takes to close the sidebar
	 */
	public void close(long timeTo) {
		isOpen = false;
		panel.minimise(timeTo);
		header.setMaximise(timeTo);
		scrollPane.setVisible(false);
		scrollBar.setVisible(false);
	}
	
	/**
	 * Navigates to selected slide
	 * 
	 * @param p Desired slide
	 */
	public void goTo(Presentation p) {
		for(int i=0;i<presentationPanels.size();i++) {
			if(presentationPanels.get(i).getPresentation()==p) {
				int y= presentationPanels.get(i).getY();
				scrollBar.setValue(y);
			}
		}		
	}
	
	/**
	 * Replaces the current presentation list with p
	 * 
	 * @param p Updated presentation list
	 */
	public void replacePres(List<Presentation> p) {
		scrollView.removeAll();
		presentationPanels= new ArrayList<PresentationPanel>();
		scrollView.setLayout(new BoxLayout(scrollView, BoxLayout.Y_AXIS));
		for(int i=0;i<p.size();i++) {
			PresentationPanel presentationPanel = new PresentationPanel(p.get(i));
			presentationPanel.setPreferredSize(new Dimension(scrollPane.getWidth(),scrollPane.getWidth()));
			scrollView.add(presentationPanel);
			presentationPanels.add(presentationPanel);
		}
	}
	
	
	/**
	 * Defines actions for buttons on the sidebar
	 */
	private void setMouseListeners() {
		minimiseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				close(800L);
				
			}
		});
		maximiseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				open(800L);
			}
		});
		
		/* Whenever the search button is clicked:
		 * 1. The sidebar will be maximised
		 * 2. Any text in the search field will be used to search the server for hashtags
		 * 3. Refresh the presentation panel to show presentations with that hashtag
		 */  
		searchButton.addActionListener(new ActionListener() {
			// 1. Maximise the sidebar
			@Override
			public void actionPerformed(ActionEvent e) {
				if(isOpen==false) {
					open(800L);
				}
				// 2. Searchfield text searches server for hashtags
				String inputText= header.getSearchBarTextField().getText();
				String newText= inputText.replace(inputText.charAt(0), Character.toUpperCase(inputText.charAt(0)));
				header.getSearchBarTextField().setText(newText);
				System.out.println(newText);
				
				try {
					// 3. Replace presentations with presentations from search
					replacePres(searchPosts(inputText));
				} catch (SAXException | ParserConfigurationException | IOException | AuthenticationException e1) {
					e1.printStackTrace();
				}
			}
		});
	}
	
	
	// -------------------------------------------------------------- //
	// ------------------------- POST SEARCH ------------------------ //
	// -------------------------------------------------------------- //
	
	/**
	 * Search the server for Posts with hashtags matching the input string.
	 * 
	 * @author joncooke
	 * @param searchString A hashtag string, intended to be a searchbar output
	 * @throws AuthenticationException 
	 * @throws IOException 
	 * @throws ParserConfigurationException 
	 * @throws SAXException 
	 */
	public ArrayList<Presentation> searchPosts(String searchString) throws SAXException, ParserConfigurationException, IOException, AuthenticationException {
		ArrayList<Presentation>searchResultPresentations;
		
		// Fetch relevant posts from the server
		PostService retrievedPosts = new PostService();
		searchResultPresentations = retrievedPosts.retrievePostsByHashtagAsPresentations(searchString);
		
		// Return found posts to the client
		return searchResultPresentations;
	}
	
	
	// -------------------------------------------------------------- //
	// ------------------------- CHECKS ----------------------------- //
	// -------------------------------------------------------------- //
	
	/**
	 * Checks if the sidebar is open
	 * 
	 * @return
	 */
	public boolean isOpen() {
		return isOpen;
	}
		
	/**
	 *  Gets the content of the searchbar
	 *  
	 * @return
	 */
	public String getSearchText() {
		return searchTextField.getText();
	}
}
