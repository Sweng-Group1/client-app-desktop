package sweng.group.one.client_app_desktop.sceneControl;

import java.awt.Dimension;
import java.awt.Rectangle;
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
public class SidebarScene extends JPanel implements ComponentInterface{
	
	private static final long serialVersionUID = 1L;
	private boolean isOpen;
	
	private CustomPanel sidebarMainPanel;
	private JTextField searchBarTextField;
	private Header header;
	
	// GUI Components
	private JTextField searchTextField;
	private JButton minimiseButton;
	private JButton maximiseButton;
	private JButton searchButton;
	private JScrollBar scrollBar;
	
	// Scrollpane to show presentations
	private JScrollPane scrollPane;
	// This is the viewport for the scrollpane
	private JPanel scrollView;
			
	private final static int searchBarInset = 10;
	private final static int rectCurveRadius = 20;

	public SidebarScene(ActionListener searchAction) {
		this.setLayout(null);
		this.setOpaque(false);

		// Sets up search bar, back button, etc.
		setUpBackground();
		// Sets up the main Presentation scroll pane
		setUpScrollPane();
		// 
		configureComponents();
		// Button click animations
		setMouseListeners();
		isOpen=true;
	}
	
	private void setUpBackground() {
		sidebarMainPanel = new CustomPanel();
		header= new Header();
		
		this.add(sidebarMainPanel);
		this.add(header);

		searchBarTextField= header.getSearchBarTextField();
		searchButton= header.getSearchButton();
		maximiseButton= header.getMaximiseButton();
		minimiseButton= header.getMinimiseButton();
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
		scrollPane.setBackground(colorDark);
		scrollView.setBackground(colorDark);
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
	
	// To allow animations of closing/opening scrollpane,
	// search bar opens sidebar, etc.
	private void setMouseListeners() {
		minimiseButton.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				close(500L);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		maximiseButton.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				open(500L);
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		searchButton.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if(isOpen==false) {
					open(500L);
				}
				String inputText= header.getSearchBarTextField().getText();
				String newText= inputText.replace(inputText.charAt(0), Character.toUpperCase(inputText.charAt(0)));
				header.getSearchBarTextField().setText(newText);
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}

	public void setSize(int width, int height) {
		super.setSize(width, height);
		int gapWidth= 10;
		int curveRadius= 30;
		
		Rectangle rPanel= new Rectangle(gapWidth,gapWidth, width-gapWidth, height-(2*gapWidth));

		sidebarMainPanel.setBounds(rPanel,curveRadius);
		Rectangle rHeader= new Rectangle(gapWidth, gapWidth, width-gapWidth,(height-gapWidth)/15);
		header.setBounds(rHeader,curveRadius);
		
		scrollBar.setSize(10, height-rHeader.height);
		scrollBar.setLocation(width-10, rHeader.height);
		
		scrollPane.setSize(rPanel.width, rPanel.height-rHeader.height);
		scrollPane.setLocation(rPanel.x, rPanel.y+rHeader.height);	
		scrollPane.setVisible(true);
		scrollView.setVisible(true);
		
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
		
	/* Gets the content of the searchbar */
	public String getSearchText() {
		return searchTextField.getText();
	}
	
	
}
