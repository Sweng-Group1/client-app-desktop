package sweng.group.one.client_app_desktop.uiElements;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

import sweng.group.one.client_app_desktop.presentation.PresElement;
import sweng.group.one.client_app_desktop.presentation.Presentation;
import sweng.group.one.client_app_desktop.presentation.Slide;
import sweng.group.one.client_app_desktop.sceneControl.ComponentInterface;



/**
 * @author sophiemaw
 *	This class stores all information about the slides within the presentation
 *	being made. It tells all other components within the upload scene which 
 *	slide is visible at any time, so as to know which slide to add presElements to
 */
public class CustomTabPanel extends UploadSceneComponent implements ComponentInterface{
	int[] landscape= {191,100};
	int[] portrait= {400,500};
	int[] sqaure= {500,500};
 
	CustomTabbedPane tabPane;
	CircleButton addTabButton;
	CircleButton removeTabButton;
	
	boolean paintMode;
	boolean eraserMode;
	
	ElementsPanel graphicsBox;
	CustomTimeProgressBar timeBar;
	MovingObject movingObject;
	
	
		
	/**
	 * @param graphicsBox      ElementsPanel
	 * @param timeBar			CustomTimeProgressBar
	 * ElementsPanel is a panel with a list of all components within a slide
	 * this class tells the elementsPanel what slide is visible so it knows what components 
	 * to display 
	 * CustomTimeProgressBar is a panel containing a progress bar which, when pressed, will 
	 * 'play' the slide, displaying the presElements, while progress bar is incrementing. 
	 * This class tells the CustomProgressBar what slide is currently visible, so it can 
	 * play that slide and set its time to the maximum time of a presElement on that slide
	 */
	public CustomTabPanel(ElementsPanel graphicsBox, CustomTimeProgressBar timeBar) {
		this.graphicsBox= graphicsBox;
		this.timeBar=timeBar;
		initialise();
		
	}
	/*
	 *  Initialise components
	 */
	
	private void initialise() {

		this.setLayout(null);
		paintMode=false;
		eraserMode=false;
		
		tabPane= new CustomTabbedPane();
		this.add(tabPane);
		this.main= transparent;
		this.secondary= colorDark;
		
		//BUTTONS:
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
		this.setComponentZOrder(removeTabButton, 0);
	
		addTabButton.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				createNewSlide();
				//initialStartUp();
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
		tabPane.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
			
				//graphicsBox.setCurrentSlide(tabPane.getSlides().get(tabPane.getSelectedIndex()));
				
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
		
		movingObject= new MovingObject();
		
		
		initialStartUp();
	}
	
	/**
	 * @param p  presElement that movingObject is added to
	 *
	 */
	public void setMovingElement(PresElement p) {
		movingObject.attatchToPresElement(p);
		tabPane.getSlides().get(tabPane.getSelectedIndex()).add(movingObject);
		movingObject.setVisible(true);
		tabPane.getSlides().get(tabPane.getSelectedIndex()).setComponentZOrder(movingObject, 0);
		movingObject.repaint();
	}
	
	/**
	 *  Removes moving object to whichever pres element it is added to
	 */
	public void exitMovingElement() {
		movingObject.setVisible(false);
		tabPane.getSlides().get(tabPane.getSelectedIndex()).remove(movingObject);
	}

	
	/**
	 * @param int[]     slide layout orientation list of point dimensions
	 * @return Slide   
	 * Creates a new slide
	 * @author sophiemaw
	 * When slide error has been fixed, then i can implement the above params
	 */
	public Slide createNewSlide() {
		Slide slide=null;
		if((tabPane.getUI().getTabBounds(tabPane, tabPane.getTabCount()-1).x
				+(2*tabPane.getUI().getTabBounds(tabPane, tabPane.getTabCount()-1).width))<tabPane.getWidth()) {
	
			slide =tabPane.addNewSlide();
			
			
			
			setSizeOfSlide(slide);
			setAddTabButtonLocation();
			if((tabPane.getUI().getTabBounds(tabPane, tabPane.getTabCount()-1).x
					+(2*tabPane.getUI().getTabBounds(tabPane, tabPane.getTabCount()-1).width))>tabPane.getWidth()) {
				addTabButton.setVisible(false);
			}
			tabPane.setSelectedIndex(tabPane.getSlides().size()-1);
			graphicsBox.addPanelForSlide(slide);
			graphicsBox.setElementPanelFor(slide, tabPane.getSlides().size()-1);
		}else {
			addTabButton.setVisible(false);
		}
		return slide;
	}
	

	
	/**
	 * @author sophiemaw
	 * This method changes the location of the addTab button to right side of last tab
	 */
	private void setAddTabButtonLocation() {
		if(tabPane.getTabCount()>0) {
		Rectangle lastTab= tabPane.getUI().getTabBounds(tabPane, tabPane.getTabCount()-1);
		addTabButton.setSize(lastTab.height);
		addTabButton.setLocation(lastTab.x+ lastTab.width+ r.x, lastTab.y+ r.x);
		}
	}
	
	/**
	 * @author sophiemaw
	 * Method to change removeTabButton size
	 */
	private void setRemoveTabButtonSize() {
		if(tabPane.getTabCount()>0) {
		Rectangle tabBounds= tabPane.getUI().getTabBounds(tabPane, 0);
		removeTabButton.setSize(tabBounds.height/2);
		}
	}
	

	/**
	 * @author sophiemaw
	 * This method ensures on start up that a tab and slide object is pre loaded  
	 */
	private void initialStartUp() {
		Slide slide = tabPane.addNewSlide();
		setSizeOfSlide(slide);
		slide.paint(slide.getGraphics());
		graphicsBox.addPanelForSlide(slide);
		tabPane.setSelectedIndex(tabPane.getSlides().size()-1);
		graphicsBox.setElementPanelFor(getCurrentSlide(), tabPane.getSlides().size()-1);
		setAddTabButtonLocation();
		this.repaint();
	}
	
	/**
	 * @param slide
	 * Method to change bounds of slide to fill tabPane viewPort
	 * NOTE: slides are not loading, error may be occuring within this method
	 */
	private void setSizeOfSlide(Slide slide) {
		slide.setPointWidth(landscape[0]);
		slide.setPointHeight(landscape[1]);
		
		slide.setSize(slide.preferredLayoutSize(slide.getParent()));
		slide.setVisible(true);
		int w= tabPane.getWidth();
		int h= tabPane.getHeight();
		slide.setLocation(0, 0);
		slide.paint(slide.getGraphics());
		
		
		slide.setBounds(0, 0, w, h);
		System.out.println(slide.getWidth());
		System.out.println(slide.getHeight());
		slide.repaint();
	}
	

	
	/**
	 *@author sophiemaw
	 * @Overided UploadSceneComponent.setMarginMethods() 
	 */
	public void setMarginBounds(int left, int top, int right ,int bottom) {
		super.setMarginBounds(left, top, right, bottom);
		tabPane.setBounds(r.x,r.y,this.getWidth()-(r.x+r.width),this.getHeight()-(r.y+r.height));
		setAddTabButtonLocation();
		setRemoveTabButtonSize();
		this.setComponentZOrder(addTabButton, 0);
		this.setComponentZOrder(removeTabButton, 1);
		this.setComponentZOrder(tabPane, 2);
		if(tabPane.getSlides().size()>0) {
			for(int i=0;i<tabPane.getSlides().size();i++) {
				setSizeOfSlide(tabPane.getSlides().get(i));
			}
			tabPane.changeBackgroundSize(tabPane.getWidth(), tabPane.getHeight());
		}
		
	}
	
	
	
	/*
	 *  Setters and getters:
	 */
	public JButton getAddTabButton() {
		return addTabButton;
	}
	public JButton getRemoveTabButton() {
		return removeTabButton;
	}	
	
	/**
	 * @return current visible slide in tabPane 
	 * Note: Once everything is refactored, this method shouldn't be needed
	 * 		 as all components needing to know this are either created or passed into this 
	 * 		 class when it's instantiated
	 */
	public Slide getCurrentSlide() {
		return tabPane.getSlides().get(tabPane.getSelectedIndex());
	}
	
	/**
	 * @return
	 * Note: Again, will not be necessary when refactored.
	 */
	public Component getCurrentComponent() {
		return tabPane.getSelectedComponent();
	}
	
	
	

}
/**
 * @author Class created by: sophiemaw
 * Notes: 
 * 	Class contains list of slides, provides information on where location of deleteTabButon 
 * 	should be and paints custom graphics by installing a customTabbedPaneUI 
 */
class CustomTabbedPane extends JTabbedPane implements ComponentInterface{
	Color mainColor;
	Color secondaryColor;
	
	JButton addTabButton;

	CustomTabUI ui;
	protected ArrayList<Slide>slides;
	JTabbedPane tp= this;
	int removeButtonIndex;
	
	BufferedImage editingBackground;
	ArrayList<JLabel>editingBackgroundLabels;
	ImageIcon editingIcon;
	
	
	CustomTabbedPane(){
		mainColor= colorLight;
		secondaryColor= transparent;
		ui= new CustomTabUI();
		this.setUI(ui);
		this.setBorder(null);
		//this.setOpaque(false);

		slides= new ArrayList<Slide>();
		editingBackgroundLabels= new ArrayList<JLabel>();
		removeButtonIndex=0;
		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				
				
				
				
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
		try {
			editingBackground= ImageIO.read(new File("./Assets/editingBackground.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		editingIcon= new ImageIcon(editingBackground);
	
	}
	
	/**
	 * @author sophiemaw
	 * @return Slide   			new slide
	 * Notes: 
	 * 	There is an error as slide is not appearing in tabPane viewPort 
	 * 
	 * This method: 
	 * 1. Creates a container JPanel,'backPanel', which is passed into the tabbedPane's 'addTab()' method
	 * 		adding it to the tabbedPane
	 * 2. Creates a JLabel for the editing background to be added to
	 * 3. Creates a NEW slide (values are random at the moment (100,200), but will be using the above 
	 * 		layout orientation int[] point params to instatiate it with correct point size
	 * 4. Adds both components to the backPanel and sets Z order so slide is on top: label(1) 
	 * 		slide(0)
	 */
	protected Slide addNewSlide() {
			JPanel backPanel= new JPanel();
			this.addTab(" Slide "+slides.size()+" ", backPanel);
			
			JLabel label= new JLabel();
			editingBackgroundLabels.add(label);
			label.setIcon(editingIcon);		
			label.setLocation(0, 0);
			label.setSize(this.getWidth(),this.getHeight());
			backPanel.add(label);
			backPanel.setBackground(mainColor);
			backPanel.setLayout(null);
			
			Slide slide= new Slide(100,200);
			slides.add(slide);
			backPanel.add(slide);

			backPanel.setComponentZOrder(slide, 0);
			backPanel.setComponentZOrder(label, 1);
			/* 
			 *  I dont think the following methods are necessary but trying everything 
			 *  to debug
			 */
			slide.setVisible(true);
			slide.setOpaque(true);
			slide.displaySlide();
			return slide;
	}
	
	/**
	 * @author sophiemaw
	 * When a tab is deleted, this method changes tab titles to be sequencial 
	 * example. 4 tabs
	 * Slide 1, Slide 2, Slide 4, Slide 5 --> Slide 1, Slide 2, Slide 3, Slide 4
	 */
	private void resetTabTitles(){
		for(int i=0;i<getTabCount();i++) {
			this.setTitleAt(i, " Slide "+(i+1)+" ");
		}
	}
	
	/**
	 * @param removeTabButton 	  button that is created in CustomTabPanel class
	 * 
	 * Adds a listener to the removeTabButton, so when clicked, the tab that matches 
	 * the removeTabButton index is deleted, and tab titles are reset 
	 * Adds a mouse motion listener to this, when a tab contains the mouse position
	 * then the removeTabButton's index is set to that tab, and button becomes visible
	 * - when mouse position isnt within the bounds of a tab, index is set to 0 and 
	 * button's visibility is set to false
	 * 
	 */
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
					if(tp.getSelectedIndex()>-1) {
						//graphicsBox.setCurrentSlideIndex(tp.getSelectedIndex()+1);
					}
					
					
				}
		});
	}
	
	

	/**
	 *@author sophiemaw
	 *Method overides this.paint()
	 *Note: slide is not being drawn, this method could be another reason for the error
	 *This method cycles through the tabs
	 *1. If tab is the one currently selected, tab is drawn with an additional rect below it,
	 *		the tabPane's current selected component is repainted and slide with an index in slides 
	 *		of i is painted (although it isnt right now)
	 *2. If tab is not selected, tab is drawn without rect 
	 */
	public void paint(Graphics g) {
		Graphics2D g2= (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		for(int i=0;i<this.getTabCount();i++) {
		if(mainColor!=null) {
			g2.setColor(mainColor);
			Rectangle r= ui.getTabBounds(this, i);
			int padding= 10;
			Rectangle rt= new Rectangle(r.x+padding/2,r.y,r.width-padding,r.height-padding/4);
			
			if((this.getSelectedIndex())==i) {
				g2.fillRoundRect(rt.x, rt.y, rt.width, rt.height, rt.height, rt.height);
				g2.fillRect(rt.x, rt.y+(rt.height/2), rt.width, rt.height);
				g2.setColor(Color.white);
				g2.drawString(this.getTitleAt(i),rt.x+padding/2, rt.height);
				if(slides.size()>0) {
					this.getSelectedComponent().repaint();
					//g2.setColor(slides.get(i).getBackground());
					slides.get(i).repaint();
				}
			
			}
			else {
				
				g2.fillRoundRect(rt.x, rt.y, rt.width, rt.height, rt.height, rt.height-padding/4);
				g2.setColor(Color.white);
				g2.drawString(this.getTitleAt(i), rt.x+padding/2,rt.height-padding/4);
			
			}
		}}
		g2.dispose();
	}
	
	/**
	 * @author sophiemaw
	 * @return a list of all slides held within tabPane 
	 */
	public ArrayList<Slide> getSlides(){
		return slides;
	}
	
	/**
	 * @param width
	 * @param height
	 * This method makes sure when CustomTabPanel is resized, editingBackground is not 
	 * stretched as it scaled properly
	 */
	public void changeBackgroundSize(int width, int height) {
		editingIcon= new ImageIcon(editingBackground.getSubimage(0, 0, width, height));
		for(int i=0;i<editingBackgroundLabels.size();i++) {
			editingBackgroundLabels.get(i).setIcon(editingIcon);
			editingBackgroundLabels.get(i).setSize(width, height);
			editingBackgroundLabels.get(i).setLocation(0, 0);
		}
	}


}





/**
 * @author sophiemaw
 * BasicTabbedPaneUi which sets all default colours to transparent
 * Makes sure tabs are not drawn automatically, as they are drawn in 
 * CustomTabbedPane class
 *
 */
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


/**
 * @author sophiemaw
 * MovingObject is a panel which is layered on top of a presElement
 * It contains buttons to resize and move the presElement it is added to 
 */
class MovingObject extends JPanel implements ComponentInterface{
	Color main;
	Color secondary;
	JButton top,left,bottom,right;
	JButton topLeft, bottomLeft,topRight,bottomRight;
	PresElement presElement;
	Point mousePos;
	MovingObject(){
		main= colorDark;
		this.setOpaque(false);
		this.setLayout(null);
		top= new JButton() {
			public void paint(Graphics g) {
				Graphics2D g2= (Graphics2D)g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setColor(main);
				Path2D p = new Path2D.Double();
				p.moveTo(0, this.getHeight());
				p.lineTo(this.getWidth()/2,0);
				p.lineTo(this.getWidth(), this.getHeight());
				p.lineTo(this.getHeight(), 0);
				g2.fill(p);
				g2.dispose();
			}
		};
		this.add(top);
		bottom= new JButton() {
			public void paint(Graphics g) {
				Graphics2D g2= (Graphics2D)g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setColor(main);
				Path2D p = new Path2D.Double();
				p.moveTo(0,0);
				p.lineTo(this.getWidth()/2,this.getHeight());
				p.lineTo(this.getWidth(), 0);
				p.lineTo(0, 0);
				g2.fill(p);
				g2.dispose();			
			}
		};
		this.add(bottom);
		right= new JButton() {
			public void paint(Graphics g) {
				Graphics2D g2= (Graphics2D)g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setColor(main);
				Path2D p = new Path2D.Double();
				p.moveTo(0,0);
				p.lineTo(this.getWidth(),this.getHeight()/2);
				p.lineTo(0, this.getHeight());
				p.lineTo(0, 0);
				g2.fill(p);
				g2.dispose();
			}
		};
		this.add(right);
		left= new JButton() {
			public void paint(Graphics g) {
				Graphics2D g2= (Graphics2D)g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setColor(main);
				Path2D p = new Path2D.Double();
				p.moveTo(this.getWidth(),0);
				p.lineTo(0,this.getHeight()/2);
				p.lineTo(this.getWidth(), this.getHeight());
				p.lineTo(this.getWidth(), 0);
				g2.fill(p);
				g2.dispose();
				
			}
		};
		this.add(left);
		topRight= new JButton() {
			public void paint(Graphics g) {
				Graphics2D g2= (Graphics2D)g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setColor(main);
				Path2D p = new Path2D.Double();
				p.moveTo(0,0);
				p.lineTo(this.getWidth(),0);
				p.lineTo(this.getWidth(), this.getHeight());
				p.lineTo(0,0);
				g2.fill(p);
				g2.dispose();	
			}
		};
		this.add(topRight);
		bottomRight= new JButton() {
			public void paint(Graphics g) {
				Graphics2D g2= (Graphics2D)g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setColor(main);
				Path2D p = new Path2D.Double();
				p.moveTo(this.getWidth(),0);
				p.lineTo(this.getWidth(),this.getHeight());
				p.lineTo(0, this.getHeight());
				p.lineTo(this.getWidth(),0);
				g2.fill(p);
				g2.dispose();	
			}
		};
		this.add(bottomRight);
		bottomLeft= new JButton() {
			public void paint(Graphics g) {
				Graphics2D g2= (Graphics2D)g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setColor(main);
				Path2D p = new Path2D.Double();
				p.moveTo(0,0);
				p.lineTo(0,this.getHeight());
				p.lineTo(this.getWidth(), this.getHeight());
				p.lineTo(0,0);
				g2.fill(p);
				g2.dispose();	
			}
		};
		this.add(bottomLeft);
		topLeft= new JButton() {
			public void paint(Graphics g) {
				Graphics2D g2= (Graphics2D)g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setColor(main);
				Path2D p = new Path2D.Double();
				p.moveTo(0,0);
				p.lineTo(this.getWidth(),0);
				p.lineTo(0, this.getHeight());
				p.lineTo(0,0);
				g2.fill(p);
				g2.dispose();	
			}
		};
		this.add(topLeft);
		bottomRight.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
				
					if(presElement!=null) {
						changeSize(0,0,e.getX()-mousePos.x,e.getY()-mousePos.y);
					}
				
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				mousePos=e.getPoint();
				
			}
			
		});
		
		
	}
	public void paint(Graphics g) {
		Graphics2D g2= (Graphics2D)g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(Color.white);
		g2.drawRect(15, 15, this.getWidth()-30, this.getHeight()-30);
		g2.setColor(main);
		g2.drawRect(14, 14, this.getWidth()-28, this.getHeight()-28);
		g2.dispose();
		super.paint(g);
	}
	public void attatchToPresElement(PresElement presElement) {
		this.setSize(presElement.getComponent().getWidth()+30, presElement.getComponent().getHeight()+30);
		this.setLocation(presElement.getComponent().getX()-15, presElement.getComponent().getY()-15);
		this.presElement=presElement;
		topRight.setBounds(this.getWidth()-10,0,10,10);
		topLeft.setBounds(0,0,10,10);
		bottomRight.setBounds(this.getWidth()-10, this.getHeight()-10, 10, 10);
		bottomLeft.setBounds(0, this.getHeight()-10, 10, 10);
		
	}
	public void setBackground(Color color) {
		super.setBackground(color);
		main= color;
	}
	private void changeSize(int deltaX, int deltaY, int deltaWidth, int deltaHeight ) {
		JComponent c= presElement.getComponent();
		c.setBounds(c.getX()+deltaX, c.getY()+deltaY, c.getWidth()-deltaWidth, c.getHeight()+deltaHeight);
		this.setBounds(this.getX()+deltaX, this.getY()+deltaY, this.getWidth()+deltaWidth, this.getHeight()+deltaHeight);
		topRight.setBounds(this.getWidth()-10,0,10,10);
		topLeft.setBounds(0,0,10,10);
		bottomRight.setBounds(this.getWidth()-10, this.getHeight()-10, 10, 10);
		bottomLeft.setBounds(0, this.getHeight()-10, 10, 10);
		this.repaint();
		c.repaint();
	}
}


