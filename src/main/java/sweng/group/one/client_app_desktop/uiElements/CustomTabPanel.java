package sweng.group.one.client_app_desktop.uiElements;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.plaf.TabbedPaneUI;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

import sweng.group.one.client_app_desktop.presentation.Presentation;
import sweng.group.one.client_app_desktop.presentation.Slide;



public class CustomTabPanel extends JPanel{
	CustomTabbedPane tabPane;
	Presentation presentation;
	CircleButton addTabButton;
	CircleButton removeTabButton;
	
	int padding;
	
	public CustomTabPanel() {
		initialise();
		
	}
	private void initialise() {
		this.setLayout(null);
		tabPane= new CustomTabbedPane();
		this.add(tabPane);
		this.setVisible(true);
		
		addTabButton= new CircleButton();
		try {
			addTabButton.setImageIcon(ImageIO.read(new File("./Assets/plus-small.png")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		removeTabButton= new CircleButton();
		this.add(addTabButton);
		try {
			removeTabButton.setImageIcon(ImageIO.read(new File("./Assets/cross.png")));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		this.add(removeTabButton);
		
		//tabPane.setBackground(Color.green);
		padding=0;
		addTabButton.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				
				if((tabPane.getUI().getTabBounds(tabPane, tabPane.getTabCount()-1).x
						+(2*tabPane.getUI().getTabBounds(tabPane, tabPane.getTabCount()-1).width))<tabPane.getWidth()) {
			
					tabPane.addNewSlide();
					setAddTabButtonLocation();
					if((tabPane.getUI().getTabBounds(tabPane, tabPane.getTabCount()-1).x
							+(2*tabPane.getUI().getTabBounds(tabPane, tabPane.getTabCount()-1).width))>tabPane.getWidth()) {
						addTabButton.setVisible(false);
					}
				}else {
					addTabButton.setVisible(false);
				}
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
		tabPane.addRemoveTabButton(removeTabButton);
		removeTabButton.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				setAddTabButtonLocation();
				
					addTabButton.setVisible(true);
				
				
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
		padding= width/50;
		tabPane.setSize(width-(padding*2), height-(2*padding));
		tabPane.setLocation(padding, padding);
		if(presentation==null) {
			initialStartUp(width,height);
		}
		setAddTabButtonLocation();
		setRemoveTabButtonSize();
		this.setComponentZOrder(addTabButton, 0);
		this.setComponentZOrder(removeTabButton, 1);
		this.setComponentZOrder(tabPane, 2);
	}
	private void setAddTabButtonLocation() {
		Rectangle lastTab= tabPane.getUI().getTabBounds(tabPane, tabPane.getTabCount()-1);
		int x= lastTab.x+ lastTab.width+ padding;
		int y= lastTab.y+ padding;
		int w= lastTab.height;
		int h= lastTab.height;
		
		addTabButton.setSize(h);
		addTabButton.setLocation(x, y);
	}
	private void setRemoveTabButtonSize() {
		Rectangle tabBounds= tabPane.getUI().getTabBounds(tabPane, 0);
		removeTabButton.setSize(tabBounds.height/2);
	}

	private void initialStartUp(int width, int height) {
		presentation=new Presentation();
		tabPane.addNewSlide();
	}

	public void addTab(String title, JComponent c) {
	
	
	}
	public void setBackgroundColors(Color light, Color dark) {
		this.setBackground(dark);
		addTabButton.setMainBackground(light);
	}
	public Presentation getPresentation() {
		return presentation;
	}
	
}
class CustomTabbedPane extends JTabbedPane{
	JButton addTabButton;
	CustomTabUI ui;
	ArrayList<Slide>slides;
	JTabbedPane tp= this;
	int removeButtonIndex;
	CustomTabbedPane(){
	
		ui= new CustomTabUI();
		this.setUI(ui);
		this.setBorder(null);
		this.setBackground(new Color(78,106,143));
		slides= new ArrayList<Slide>();
		removeButtonIndex=0;
	}
	protected void addNewSlide() {
		
			Slide newSlide= new Slide(this.getWidth(),this.getHeight());
			slides.add(newSlide);
			this.addTab(" Slide "+slides.size()+" ", newSlide);
		
	}
	public void addSlide(String title, Slide panel) {
		JPanel newPanel= new JPanel();
		newPanel.setBackground(getBackground());
		
		this.addTab(title, newPanel);
		newPanel.setLayout(null);
		newPanel.add(panel);
		
		slides.add(panel);
	}
	private void resetTabTitles(){
		for(int i=0;i<getTabCount();i++) {
			this.setTitleAt(i, " Slide "+(i+1)+" ");
		}
	}
	protected void addRemoveTabButton(CircleButton removeTabButton) {
		removeTabButton.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
		
					if((removeButtonIndex>0)&&(getTabCount()>1) ){
						tp.removeTabAt(removeButtonIndex-1);
						slides.remove(removeButtonIndex-1);
						tp.repaint();
						resetTabTitles();
						removeTabButton.setVisible(false);
					}
					
				
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
		this.addMouseMotionListener(new MouseMotionListener() {



				@Override
				public void mouseDragged(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseMoved(MouseEvent e) {
					boolean bool= false;
		
					for(int j=0;j<getTabCount();j++) {
						if(getUI().getTabBounds(tp,j).contains(e.getPoint())) {
							Rectangle tab= getUI().getTabBounds(tp, j);
							int x= tab.x+tab.width;
							int y= tab.y+tab.height;
							removeTabButton.setLocation(x, y);
							removeTabButton.setVisible(true);
							bool=true;
							removeButtonIndex=j+1;
						}
					}
					if(bool==false) {
						removeTabButton.setVisible(false);
						removeButtonIndex=0;
					}
					
				}
		});
	}
	

	public void paint(Graphics g) {
		Graphics2D g2= (Graphics2D) g.create();
		
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		for(int i=0;i<this.getTabCount();i++) {
		
			g2.setColor(new Color(78,106,143));
			Rectangle r= ui.getTabBounds(this, i);
			int padding= 10;
			Rectangle rt= new Rectangle(r.x+padding/2,r.y,r.width-padding,r.height-padding/4);
			
			if((this.getSelectedIndex())==i) {
				g2.fillRoundRect(rt.x, rt.y, rt.width, rt.height, rt.height, rt.height);
				g2.fillRect(rt.x, rt.y+(rt.height/2), rt.width, rt.height);
				g2.setColor(Color.white);
				g2.drawString(this.getTitleAt(i),rt.x+padding/2, rt.height);
				if(slides.size()>0) {
					slides.get(i).repaint();
				}
			
			}
			else {
				
				g2.fillRoundRect(rt.x, rt.y, rt.width, rt.height, rt.height, rt.height-padding/4);
				g2.setColor(Color.white);
				g2.drawString(this.getTitleAt(i), rt.x+padding/2,rt.height-padding/4);
			
			}
		}
	}

}
class CustomTabUI extends BasicTabbedPaneUI{
	Color transparent= new Color(255,255,255,255);
	CustomTabUI(){
	
	}
	public void installDefaults() {
		super.installDefaults();
		this.darkShadow= transparent;
		this.highlight=transparent;
		this.lightHighlight= transparent;
		this.shadow= transparent;
		this.createScrollButton(EAST);
	}
	
	
	public void paintTabArea(Graphics g, int tabPlacement,int selectedIndex) {
		//super.paintTabArea( g,  tabPlacement, selectedIndex);
		
	}
}
