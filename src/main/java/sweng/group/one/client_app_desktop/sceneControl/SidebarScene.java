package sweng.group.one.client_app_desktop.sceneControl;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import org.assertj.core.error.ShouldBeUpperCase;

import sweng.group.one.client_app_desktop.presentation.Presentation;
import sweng.group.one.client_app_desktop.presentation.Slide;
import sweng.group.one.client_app_desktop.sideBarUIElements.CustomPanel;
import sweng.group.one.client_app_desktop.sideBarUIElements.CustomScrollBarUI;
import sweng.group.one.client_app_desktop.sideBarUIElements.Header;
import sweng.group.one.client_app_desktop.sideBarUIElements.PresentationPanel;
import sweng.group.one.client_app_desktop.sideBarUIElements.SearchBar;

/**
 * @author joncooke
 * Refactored sophiemaw
 *
 */
public class SidebarScene extends JPanel implements ComponentInterface{
	
	private static final long serialVersionUID = 1L;
	private boolean isOpen;
	
	private CustomPanel panel;
	private JTextField searchBarTextField;
	private Header header;
	
	private List<Presentation> presentations;
	
	private JTextField searchTextField;
	private JButton minimiseButton;
	private JButton maximiseButton;
	private JButton searchButton;
	private JScrollBar scrollBar;
	
	private JScrollPane scrollPane;
	private JPanel scrollView;
	
	private boolean presScrollEnabled = true;
	
	private GridBagConstraints gbc;
	
	private final static int searchBarInset = 10;
	private final static int rectCurveRadius = 20;
	


	/*
	 * 	Sophie: I've put in another way the presentations can appear on the scrollView
	 * 			I haven't been able to get the gridbag layout to work 
	 * 				Set this value to false to use GridBag instead of my null layout:
	 */
				private boolean sophiesLayout= true;
				private ArrayList<PresentationPanel>presentationPanels;
				private ArrayList<JPanel>panes;

	public SidebarScene(ActionListener searchAction) {
		this.setLayout(null);
		this.setOpaque(false);

		setUpBackground();
		setUpScrollPane();
		configureComponents();
		setMouseListeners();
		isOpen=true;
	}
	private void setUpBackground() {
		panel = new CustomPanel();
		header= new Header();
		
		this.add(panel);
		this.add(header);

		searchBarTextField= header.getSearchBarTextField();
		searchButton= header.getSearchButton();
		maximiseButton= header.getMaximiseButton();
		minimiseButton= header.getMinimiseButton();
	}
	private void setUpScrollPane(){
		scrollView= new JPanel();
		scrollPane= new JScrollPane(scrollView);
		this.add(scrollPane);
		scrollView.setLayout(new GridBagLayout());
		scrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);	
		scrollBar= scrollPane.getVerticalScrollBar();
		scrollBar.setUI(new CustomScrollBarUI());
		scrollBar.setOpaque(false);
		
		this.add(scrollBar);
	
		
	}
	private void configureComponents() {
		header.setColours(colorDark, colorDark);
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
		//searchBar.setVisible(true);
	}
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

		panel.setBounds(rPanel,curveRadius);
		Rectangle rHeader= new Rectangle(gapWidth, gapWidth, width-gapWidth,(height-gapWidth)/15);
		header.setBounds(rHeader,curveRadius);
		
		scrollBar.setSize(10, height-rHeader.height);
		scrollBar.setLocation(width-10, rHeader.height);
		
		scrollPane.setSize(rPanel.width, rPanel.height-rHeader.height);
		scrollPane.setLocation(rPanel.x, rPanel.y+rHeader.height);	
		scrollPane.setVisible(true);
		scrollView.setVisible(true);
		
		if(sophiesLayout==true) {
			if(presentationPanels!=null) {
				for(int i=0;i<presentationPanels.size();i++) {
					presentationPanels.get(i).setPreferredSize(new Dimension(width,width));
				}
			}
		}
		
	}
	
	public void open(long timeTo) {
		isOpen = true;
		panel.maximise(timeTo);
		header.setMinimise(timeTo);
	}
	
	public void close(long timeTo) {
		isOpen = false;
		panel.minimise(timeTo);
		header.setMaximise(timeTo);
		scrollPane.setVisible(false);
		scrollBar.setVisible(false);
		
	}
	
	public boolean isOpen() {
		return isOpen;
	}

	public void goTo(Presentation p) {
		if(sophiesLayout==true) {
		
			for(int i=0;i<presentationPanels.size();i++) {
				if(presentationPanels.get(i).getPresentation()==p) {
					int y= presentationPanels.get(i).getY();
					scrollBar.setValue(y);
				}
			}
		}else {
			// What we might want to do is make a presentationPanel for the presentation being viewed
			// At the moment we just put this one to the top of the list, and then move to the top of 
			// the scrollpane.
			List<Presentation> newPresentations = new ArrayList<Presentation>();
			presentations.remove(p); // Remove p from the list.
			newPresentations.add(p);
			newPresentations.addAll(presentations);
			
			replacePres(newPresentations);
			
			scrollPane.getVerticalScrollBar().setValue(0);
		}
		
		
	}
	
	/* Replaces the current presentation list with p */
	public void replacePres(List<Presentation> p) {

		if(sophiesLayout==true) {
			scrollView.removeAll();
			presentationPanels= new ArrayList<PresentationPanel>();
			scrollView.setLayout(new BoxLayout(scrollView, BoxLayout.Y_AXIS));
			for(int i=0;i<p.size();i++) {
				PresentationPanel presentationPanel = new PresentationPanel(p.get(i));
				presentationPanel.setPreferredSize(new Dimension(scrollPane.getWidth(),scrollPane.getWidth()));
				scrollView.add(presentationPanel);
				presentationPanels.add(presentationPanel);

			}

		}else {
			scrollView.setLayout(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.fill = GridBagConstraints.BOTH;
			gbc.gridwidth = 1;
			gbc.gridheight = 1;
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.weightx = 1;
			gbc.weighty = 1;
			scrollView.removeAll();
			presentations = p;
			for (int i = 0; i < p.size(); i++) {
				Presentation pres = p.get(i);
				gbc.gridy = i;
				scrollView.add(pres, gbc);
				pres.setEnabled(true);
				pres.setBackground(colorLight);
				pres.setVisible(true);
			}	
		}
	
	}
		
	/* Gets the content of the searchbar */
	public String getSearchText() {
		return searchTextField.getText();
	}
	public JTextField getSearchBarTextField() {
		return header.getSearchBarTextField();
	}
	public JScrollBar getScrollBar() {
		return scrollBar;
	}
}
