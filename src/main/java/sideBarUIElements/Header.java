package sideBarUIElements;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class Header extends JPanel{
	//variables
	int curveRadius;
	Color light;
	Color dark;
	int opacityValue;
	Rectangle rSearchBarMin;
	Rectangle rSearchBarMax;
	
	// Components
	SearchBar searchBar;
	JButton minimiseButton;
	JButton maximiseButton;
	JButton filterList;
	
	//Booleans
	boolean isMinimised;
	
	public Header() {
		create();
	}
	private void create() {
		this.setLayout(null);
		this.setOpaque(false);
		opacityValue= 100;
		isMinimised=false;
		/*
		 *  Search bar: get maximiseButton
		 */
		searchBar= new SearchBar();
		this.add(searchBar);
		searchBar.setOpaque(false);
		maximiseButton= searchBar.getMaximiseButton();
		/*
		 *  Minimise button 
		 */
		minimiseButton= new JButton() {
			public void paint(Graphics g) {
				Graphics2D g2= (Graphics2D) g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				if(isMinimised==false) {
				g2.setColor(this.getBackground());
				g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(),15,15);
				Image im;
				
					try {
						im = ImageIO.read(new File("./assets/backBlack.png")).getScaledInstance(this.getWidth()/2, this.getHeight()/2, java.awt.Image.SCALE_SMOOTH);
						g2.drawImage(im, this.getWidth()/4, this.getWidth()/4, null);
					} catch (IOException e) {
						e.printStackTrace();
					}		
					//super.paint(g)
			}}
		};
		minimiseButton.setBackground(Color.white);
		this.add(minimiseButton);
		minimiseButton.setOpaque(false);
		minimiseButton.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
			
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				minimiseButton.setBackground(Color.gray);
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				minimiseButton.setBackground(Color.LIGHT_GRAY);
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				minimiseButton.setBackground(Color.LIGHT_GRAY);
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				minimiseButton.setBackground(Color.white);
				
			}
		});
		/*
		 * Filter drop list (placeholder button)
		 */
		filterList = new JButton() {
			public void paint(Graphics g) {
				g.setColor(Color.white);
				g.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 15, 15);
			}
		};
		this.add(filterList);
		
	}
	public void setColours(Color light, Color dark) {
		this.light=light;
		this.dark=dark;
	}
	public void paint(Graphics g) {
		Graphics2D g2= (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		if(isMinimised==false) {	
			g2.setColor(new Color(light.getRed(),light.getGreen(),light.getBlue(),opacityValue));
			g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), curveRadius, curveRadius);
		//	g2.fillArc(0, 0, curveRadius, curveRadius, 90, 90);
		//	g2.fillArc(this.getWidth()-curveRadius,0, curveRadius, curveRadius, 0, 90);
		//	g2.fillRect((curveRadius/2), 0, this.getWidth()-curveRadius, curveRadius/2);
		//	g2.fillRect(0, (curveRadius/2), this.getWidth(), this.getHeight()-(curveRadius)/2);
		}
		super.paint(g2);
	}
	public void setBounds(Rectangle bounds, int curveRadius) {
		this.setLocation(bounds.x, bounds.y);
		this.setSize(bounds.width, bounds.height);
		this.curveRadius= curveRadius;
		
		
		int gapWidth= bounds.height/4;
		minimiseButton.setSize(bounds.height/2, bounds.height/2);
		filterList.setSize(bounds.height, bounds.height/2);
		rSearchBarMax = new Rectangle(gapWidth, gapWidth, bounds.width-(2*gapWidth),bounds.height/2);
		rSearchBarMin= new Rectangle(gapWidth,gapWidth,bounds.width-(gapWidth*4)-minimiseButton.getWidth()-filterList.getWidth(),bounds.height/2);
		
		
		filterList.setLocation(bounds.width-gapWidth-filterList.getWidth(), gapWidth);
		minimiseButton.setLocation(bounds.width-gapWidth- filterList.getWidth()-gapWidth-minimiseButton.getWidth(), bounds.height/4);
		
		/*
		 *  Search bar: search bar gapWidth = this.height/4
		 */
		
	
		searchBar.setMinimumStateSize(rSearchBarMin.width,rSearchBarMin.height);
		searchBar.setMaximumStateSize(rSearchBarMax.width, rSearchBarMax.height);
		searchBar.setLocation(gapWidth, gapWidth);
	}
	public JButton getMaximiseButton() {
		return maximiseButton;
	}
	public JButton getMinimiseButton() {
		return minimiseButton;
	}
	public void setMinimise(long timeToMinimise) {
		isMinimised=false;
		searchBar.minimise(timeToMinimise);
		minimiseButton.setVisible(true);
		maximiseButton.setVisible(false);
		minimiseButton.setBackground(Color.white);
		filterList.setVisible(true);
		repaint();
	}
	public void setMaximise(long timeToMaximise) {
		isMinimised=true;
		searchBar.maximise(timeToMaximise);
		
		minimiseButton.setVisible(false);
		maximiseButton.setVisible(true);
		maximiseButton.setBackground(Color.white);
		filterList.setVisible(false);
		repaint();
	}
	public JButton getSearchButton() {
		return searchBar.getSearchButton();
	}
	public JTextField getSearchBarTextField() {
		return searchBar.getSearchBarTextField();
	}

}
