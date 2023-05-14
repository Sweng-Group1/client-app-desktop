package sweng.group.one.client_app_desktop.sideBarUIElements;



import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;



import sweng.group.one.client_app_desktop.presentation.Presentation;
import sweng.group.one.client_app_desktop.presentation.Slide;


public class PresentationPanel extends JPanel{
	private int width;
	private int height;
	
	private Presentation p;
	private Slide currentSlide;
	private String description; 
	private String author;
	private String date;
	private String location;
	
	//Overlay stuff:
	JPanel upperPane; // to register mouse listener
	private JPanel overlay;
	private JLabel descriptionLabel;
	private JButton reportButton;
	private JLabel activityStatus; 
	
	/*
	 * This is the circles and forward/back buttons to track where in presentation you are:
	 *   						 < o o o o >
	 */
	private JPanel lowerPane;
	private JLabel positionInSlidesTrack; // o o o o o
	private JButton nextSlideButton; // >
	private JButton previousSlideButton; // <
	
	
	public PresentationPanel(Presentation p) {
		this.p=p;
		create();
		createOverlay();
		createMouseListener();
	}
	public void create() {
		this.setOpaque(false);
		this.setLayout(null);
		currentSlide= p.getCurrentSlide();
		this.add(currentSlide);
		p.getCurrentSlide().setVisible(true);
		
		/*	There needs to be getters for these: 
		 * 
		 *   description = p.getDescription();
		 *   date= p.getDate();
		 *   author= p.getAuthor();
		 */
		
		// PLACEHOLDER:
		description = "This is the description of this slide";
		author= "Electronic Engineering Society";
		date= "24th March 2023";
		location= "P/L/001";
	}
	public void createOverlay() {
		upperPane= new JPanel();
		upperPane.setOpaque(false);
		upperPane.setVisible(true);	
		upperPane.setLayout(null);
		this.add(upperPane);
		
		overlay= new JPanel();
		upperPane.add(overlay);
	
		this.setComponentZOrder(currentSlide, 1);
		this.setComponentZOrder(upperPane, 0);
		
		overlay.setLayout(new BorderLayout());
		overlay.setVisible(false);
		overlay.setBackground(new Color(0,0,0,200));
		
		
		descriptionLabel= new JLabel() {
			public void paint(Graphics g) {
				g.setColor(Color.white);
				g.drawString(description, this.getWidth()/4, this.getHeight()/2);
				// need to use font metrics to centre this properly ^^
				super.paint(g);
			}
		};
		
		descriptionLabel.setOpaque(false);
		overlay.add(descriptionLabel, BorderLayout.SOUTH);
		descriptionLabel.setVisible(true);
		
		lowerPane= new JPanel();
		overlay.add(lowerPane);
		lowerPane.setLayout(new FlowLayout());
		lowerPane.setOpaque(false);
		nextSlideButton= new JButton("F");
		nextSlideButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				p.nextSlide();
				overlay.setVisible(false);
				System.out.println("NEXT");
			}
			
		});
		previousSlideButton= new JButton("B");
		previousSlideButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				p.prevSlide();
				System.out.println("BACK");
			}
			
		});
		lowerPane.add(previousSlideButton);
		lowerPane.add(nextSlideButton);
		
	}
	public void createMouseListener() {
		
		MouseListener overlayVisibleWhenIn= new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				//overlay.setVisible(false);
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				overlay.setVisible(true);
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				//overlay.setVisible(false);
				
			}
			
		};
		MouseListener overlayVisibleWhenInInvisibleWhenOut= new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				//overlay.setVisible(false);
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				overlay.setVisible(true);
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				overlay.setVisible(false);
				
			}
			
		};
		
		overlay.addMouseListener(overlayVisibleWhenIn);
		lowerPane.addMouseListener(overlayVisibleWhenIn);
		upperPane.addMouseListener(overlayVisibleWhenIn);
		this.addMouseListener(overlayVisibleWhenInInvisibleWhenOut);
		
		
	}
	public void setBackground(Color color) {
		super.setBackground(color);
		//p.setBackground(color);
	}
	
	public void setPreferredSize(Dimension d) {
		
		int slidesPointWidth=currentSlide.getPointWidth();
		int slidesPointHeight= currentSlide.getPointHeight();
	
		int ratio = d.width/slidesPointWidth;
		int width = d.width;
		int height= slidesPointHeight* ratio;

		currentSlide.setSize(width,height);
		currentSlide.setLocation(0, 0);
		resizeOverlay(width,height);
		super.setPreferredSize(new Dimension(width,height));
		this.width=width;
		this.height=height;	
		
	}
	private void resizeOverlay(int width, int height) {
		upperPane.setSize(width, height);
		upperPane.setLocation(0, 0);
		
		overlay.setSize(width,height);
		overlay.setLocation(0, 0);
		descriptionLabel.setSize(width,height);
		descriptionLabel.setLocation(0,0);
	}
	private void nextSlide() {
		if(p.getSlides().size()==1) {
			System.out.println("Presentation only has one slide");
		}else {
			p.nextSlide();
			this.remove(currentSlide);
			this.add(p.getCurrentSlide());
			currentSlide= p.getCurrentSlide();
			this.setComponentZOrder(currentSlide, 1);
			this.setComponentZOrder(upperPane, 0);
			this.validate();
			this.repaint();
		}
	}
	private void previousSlide() {
		
	}
	public void overlaySetVisible(boolean bool) {
		overlay.setVisible(bool);
	}
	public int getPanesWidth() {
		return width;
	}
	public int getPanesHeight() {
		return height;
	}
	public Presentation getPresentation() {
		return p;
	}
}
