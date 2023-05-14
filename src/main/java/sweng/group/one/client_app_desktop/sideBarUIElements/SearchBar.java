package sweng.group.one.client_app_desktop.sideBarUIElements;

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
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
/**
 *@author sophiemaw
 */
public class SearchBar extends JPanel {	
	/*
		int curveRadius;
		
		public SearchBar() {
			this.setOpaque(false);
			this.setLayout(null);
			;
		}
		
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
};*/
	public boolean isMoving;
	public boolean isMaxState;
	JButton searchButton;
	
	int gapWidth;
	Rectangle r;
	Rectangle rMin;
	Rectangle rMax;
	Rectangle rButton;
	Rectangle rTextField;
	Rectangle rMaxButton;
	
	JTextField textField;
	JButton maximisePanelButton;
	
	Timer timer;
	
	public SearchBar() {
		this.setOpaque(false);
		this.setLayout(null);
		isMaxState=false;
		isMoving=false;
		createSearchButton();
		createTextField();
		createMaximisePanelButton();
		createTimers();
	}
	public void createTimers() {
		timer = new Timer();
	}
	private void createMaximisePanelButton() {
		maximisePanelButton= new JButton() {
			
			public void paint(Graphics g) {
				Graphics2D g2= (Graphics2D) g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setColor(this.getBackground());
				//g2.fillOval(0, 0, this.getWidth(), this.getHeight());
				g2.fillOval(0,0,this.getWidth(), this.getWidth());
				Image im;
				
					try {
						im = ImageIO.read(new File("./assets/forwardBlack.png")).getScaledInstance(this.getWidth()/2,this.getHeight()/2, java.awt.Image.SCALE_SMOOTH);
						g2.drawImage(im,this.getWidth()/4,this.getWidth()/4, null);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}		
					//super.paint(g);
			}
		};
		this.add(maximisePanelButton);
		maximisePanelButton.setVisible(false);
		maximisePanelButton.setBackground(new Color(255,255,255,255));
		maximisePanelButton.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				maximisePanelButton.setBackground(Color.gray);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				maximisePanelButton.setBackground(Color.LIGHT_GRAY);
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				maximisePanelButton.setBackground(Color.LIGHT_GRAY);
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				maximisePanelButton.setBackground(new Color(255,255,255,0));
				
			}
			
		});
		
	}
	public void paint(Graphics g) {
		Graphics2D g2= (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		if(isMaxState==true) {
			//g2.setColor(new Color(255,255,255,100));
			//g2.fillRoundRect(rMaxButton.x,rMaxButton.y-5,rMin.width,rMin.height,rMin.height,rMin.height);
			//maximisePanelButton.setBounds(rMaxButton.x+5,rMaxButton.y,rMaxButton.width,rMaxButton.height);
		}
		int shadowHeight=2;
		int shadowWidth=1;
		
		g2.setColor(Color.white);
		g2.fillRoundRect(r.x, r.y, r.width, r.height, r.height, r.height);
		super.paint(g);
	}	
	private void createTextField() {
		textField= new JTextField();
		this.add(textField);
		textField.setOpaque(false);
		textField.setVisible(true);
		textField.setBorder(null);
	}
	private void createSearchButton() {
		searchButton= new JButton() {
			
			public void paint(Graphics g) {
				Graphics2D g2= (Graphics2D) g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setColor(this.getBackground());
				//g2.fillOval(0, 0, this.getWidth(), this.getHeight());
				g2.fillOval(0,0,this.getWidth(), this.getHeight());
				Image im;
				
					try {
				
						im = ImageIO.read(new File("./assets/searchBlack.png")).getScaledInstance(this.getWidth()/2,this.getHeight()/2, java.awt.Image.SCALE_SMOOTH);
						g2.drawImage(im,this.getWidth()/4,this.getWidth()/4, null);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}		
					//super.paint(g);
			}
		};
		searchButton.setBackground(Color.white);
		searchButton.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				searchButton.setBackground(new Color(0,0,0,100));
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				searchButton.setBackground(Color.white);
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				searchButton.setBackground(new Color(0,0,0,50));
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				searchButton.setBackground(Color.white);
				
			}
			
		});
		searchButton.setOpaque(false);
		this.add(searchButton);
		searchButton.setVisible(true);
		
	}
	
	public boolean isMoving() {
		return isMoving;
	}
	
	
	public JButton getMaximiseButton() {
		return maximisePanelButton;
	}
	public void setMinimumStateSize(int width, int height) {
		rButton= new Rectangle(width-height-5,5,height-10,height-10);
		rTextField= new Rectangle(5,0,width-10-height,height);
		rMaxButton= new Rectangle(0,0,height,height);
		
		searchButton.setBounds(rButton);
		textField.setBounds(rTextField);
		maximisePanelButton.setBounds(rMaxButton);
		
		rMin= new Rectangle(0,0,width,height);
		r= new Rectangle(0,0,width,height);
	}
	public void setMaximumStateSize(int width,int height) {
		rMax= new Rectangle(rMin.x,0,width-rMaxButton.width,height);
		this.setSize(width,height);
	}
	public void minimise(long timeToMinimise) {
		
		int i=1;
		int timeInterval= (int) (timeToMinimise/(rMax.width-rMin.width));
		
		for(int j= rMax.width;j>=rMin.width;j--) {
			timer.schedule(new TimerTask() {

				@Override
				public void run() {
					r.setBounds(r.x,r.y,r.width-1,r.height);
					searchButton.setBounds(r.x+r.width-rButton.width-10,rButton.y,rButton.width,rButton.width);
					textField.setBounds(r.x+10,r.y,r.width-40-rButton.width-rMaxButton.width,r.height);
					repaint();
				}
				
			}, i*timeInterval);
			i++;
		}
	}
	
	public void maximise(long timeToMaximise) {
		int i=1;
		int timeInterval= (int) (timeToMaximise/(rMax.width-rMin.width));
		
	
		for(int j= rMin.width;j<=rMax.width;j++) {
			timer.schedule(new TimerTask() {

				@Override
				public void run() {
					r.setBounds(r.x,r.y,r.width+1,r.height);
					searchButton.setBounds(r.x+r.width-rButton.width-10,rButton.y,rButton.width,rButton.width);
					maximisePanelButton.setBounds(r.x+10,rMaxButton.y,rMaxButton.width,rMaxButton.width);
					textField.setBounds(r.x+10+rMaxButton.width+10,r.y,r.width-40-rButton.width-rMaxButton.width,r.height);
					repaint();
				}
				
			}, i*timeInterval);
			i++;
		}
	}
	public JButton getSearchButton() {
		return searchButton;
	}
	public JTextField getSearchBarTextField() {
		return textField;
	}
}


