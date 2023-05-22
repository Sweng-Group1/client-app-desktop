package sweng.group.one.client_app_desktop.uiElements;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
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
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

import sweng.group.one.client_app_desktop.graphics.Circle;
import sweng.group.one.client_app_desktop.media.TextElement;
import sweng.group.one.client_app_desktop.presentation.PresElement;
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
	CircularButton addTabButton;
	CircularButton removeTabButton;
	CustomToolBar toolBar;
	
	boolean paintMode;
	boolean eraserMode;
	
	ElementsPanel graphicsBox;
	CustomTimeProgressBar timeBar;
	MovingObject movingObject;
	
	Slide currentSlide;
	
		
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
	public CustomTabPanel(ElementsPanel graphicsBox, CustomTimeProgressBar timeBar,CustomToolBar toolBar) {
		this.graphicsBox= graphicsBox;
		this.timeBar=timeBar;
		this.toolBar=toolBar;
		toolBar.setTabPane(this);
		initialise();	
	}
	/*
	 *  Initialise components
	 */
	
	private void initialise() {

		this.setLayout(null);
		paintMode=false;
		eraserMode=false;	
		
		this.main= transparent;
		this.secondary= colorDark;
		
		//BUTTONS:
		addTabButton= new CircularButton();
		try {
			addTabButton.setImageIcon(ImageIO.read(new File("./assets/plus-small.png")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		addTabButton.setMainBackground(Color.white);
		removeTabButton= new CircularButton();
		this.add(addTabButton);
		try {
			removeTabButton.setImageIcon(ImageIO.read(new File("./assets/cross.png")));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		removeTabButton.setMainBackground(Color.white);
		tabPane= new CustomTabbedPane(graphicsBox,removeTabButton);
		this.add(tabPane);
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
		//tabPane.addRemoveTabButton(removeTabButton);
		removeTabButton.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if(tabPane.getTabCount()>1) {
					tabPane.remove(tabPane.removeButtonIndex);
				}
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
				graphicsBox.setVisibleElementPanelFor(tabPane.getSlides().get(tabPane.getSelectedIndex()));
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				
				graphicsBox.setVisibleElementPanelFor(tabPane.getSlides().get(tabPane.getSelectedIndex()));
				
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
		
	

	}
	
	/**
	 * @param p  presElement that movingObject is added to
	 *
	 */
	
	public void setMovingElement(PresElement p) {
		Slide slide= tabPane.getSlides().get(tabPane.getSelectedIndex());
		movingObject.attatchToPresElement(slide,p);
		slide.add(movingObject);
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
	public void createNewSlide() {
		Slide slide= new Slide(landscape[0],landscape[1]);
		tabPane.addNewSlide(slide);
		tabPane.setSelectedIndex(tabPane.getTabCount()-1);
		graphicsBox.addNewPanelForSlide(slide);
		graphicsBox.setVisibleElementPanelFor(slide);
		setAddTabButtonLocation();
		/*
		 *  Remove tools listener from current slide, adds to new slide
		 */
		toolBar.setGeneralToolBarListenersForSlide(graphicsBox, getCurrentSlide());
	}
	
	

	
	/**
	 * @author sophiemaw
	 * This method changes the location of the addTab button to right side of last tab
	 */
	private void setAddTabButtonLocation() {
		if(tabPane.getTabCount()>0) {
		Rectangle lastTab= tabPane.getUI().getTabBounds(tabPane, tabPane.getTabCount()-1);
		addTabButton.setSize(lastTab.height,lastTab.height,lastTab.height);
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
		removeTabButton.setSize(tabBounds.height/2,tabBounds.height/2,tabBounds.height/2);
		}
	}
	
	/**
	 *@author sophiemaw
	 * @Overided UploadSceneComponent.setMarginMethods() 
	 */
	public void setMarginBounds(int left, int top, int right ,int bottom) {
		super.setMarginBounds(left, top, right, bottom);
		//this.setLayout(new GridLayout());
		tabPane.setBounds(r.x,r.y,this.getWidth()-(r.x+r.width),this.getHeight()-(r.y+r.height));
		setAddTabButtonLocation();
		setRemoveTabButtonSize();
		this.setComponentZOrder(addTabButton, 0);
		this.setComponentZOrder(removeTabButton, 1);
		this.setComponentZOrder(tabPane, 2);
		
		if(tabPane.getSlides().size()==0) {
			createNewSlide();
		}
		tabPane.resizeSlides();
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
	public Slide getCurrentSlide() {
		return tabPane.getSlides().get(tabPane.getSelectedIndex());
		
	}
	public Component getCurrentComponent() {
		return tabPane.getSelectedComponent();
	}
}
/*
 *  
 */
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
	protected ArrayList<Slide>slides;
	protected ArrayList<JLabel>labels;
	JTabbedPane tp= this;
	int removeButtonIndex;
	BufferedImage editingBackground;
	Image scaledEditingBackground;

	
	
	
	CustomTabbedPane(ElementsPanel elementsPanel,CircularButton removeTabButton){
		mainColor= colorLight;
		secondaryColor= colorDark;
		this.setUI(new CustomTabPaneUI(this,elementsPanel,removeTabButton));
		this.setBorder(null);
		slides= new ArrayList<Slide>();
		labels= new ArrayList<JLabel>();

		removeButtonIndex=0;
		try {
			editingBackground= ImageIO.read(new File("./assets/editingBackground.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
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
	public void resizeSlides() {
		for(int i=0;i<slides.size();i++) {
			Slide slide= slides.get(i);
			slide.setSize(slide.preferredLayoutSize(this));
			slide.setLocation((this.getWidth()-slide.getWidth())/2, (this.getHeight()-slide.getHeight())/2);
			JLabel label= labels.get(i);
			label.setSize(this.getWidth(), this.getHeight());
			label.setLocation(0, 0);
		}
	}
	protected void addNewSlide(Slide slide) {
		
		JPanel background= new JPanel();
		background.setLayout(null);	
		this.addTab(" Slide "+(slides.size()+1), background);
		
		JLabel label= new JLabel();
		labels.add(label);
		label.setIcon(new ImageIcon(editingBackground));
		background.add(label);
		label.setSize(this.getWidth(), this.getHeight());
		label.setLocation(0, 0);
		slide.setSize(slide.preferredLayoutSize(this));
		slide.setBackground(Color.white);
		background.add(slide);
		slide.setLocation((this.getWidth()-slide.getWidth())/2, (this.getHeight()-slide.getHeight())/2);
		background.validate();
		slides.add(slide);
		
		background.setComponentZOrder(label, 1);
		background.setComponentZOrder(slide, 0);
		background.validate();
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
	 * @author sophiemaw
	 * @return a list of all slides held within tabPane 
	 */
	public ArrayList<Slide> getSlides(){
		return slides;
	}

}

class CustomTabPaneUI extends BasicTabbedPaneUI implements ComponentInterface{
		CustomTabbedPane tb;
		CircularButton removeTabButton;
		ElementsPanel elementsPanel;
	/**
	 * @param tabPane
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
	CustomTabPaneUI(CustomTabbedPane tabPane,ElementsPanel elementsPanel,CircularButton removeTabButton){
		this.removeTabButton=removeTabButton;
		this.tb=tabPane;
		this.elementsPanel=elementsPanel;
		
	}
	public void installDefaults() {
		super.installDefaults();
		this.lightHighlight=colorLight;
		this.darkShadow=colorDark;
		this.shadow=colorDark;
		this.focus=colorLight;
		this.textIconGap=10;
		tb.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				/*
				 *  Method to change slide
				 */
				
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
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		
		tb.addMouseMotionListener(new MouseMotionListener() {



			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				boolean bool=false;
				for(int i=0;i<tb.getTabCount();i++) {
					if(getTabBounds(tabPane, i).contains(e.getPoint())) {
						int xPos= (int)getTabBounds(tabPane,i).getX();
						int yPos= (int)getTabBounds(tabPane,i).getY();
						int width= (int)getTabBounds(tabPane,i).getWidth();
						int height= (int)getTabBounds(tabPane,i).getHeight();
						removeTabButton.setSize(height,height,height/2);
						removeTabButton.setLocation(xPos+width-textIconGap/2-height/2,yPos);
						removeTabButton.setVisible(true);
						System.out.println("AA");
						bool=true;
					}
				}
				if(bool==false) {
					removeTabButton.setVisible(false);
				}
				
			}
			
		});
		
	}
	
	private void changeSlide() {
		/*
		 *  Slide change is done automatically 
		 */
		elementsPanel.setVisibleElementPanelFor(((CustomTabbedPane) tabPane).getSlides().get(tabPane.getSelectedIndex()));
	}
	
	public void paintTab(Graphics g,int tabPlacement, Rectangle[] rects,int tabIndex,Rectangle iconRect,Rectangle textRect) {
	
		Graphics2D g2= (Graphics2D)g.create();
		g2.setColor(colorLight);
		int xPos= rects[tabIndex].x +textIconGap/2;
		int width= rects[tabIndex].width- textIconGap;
		
		g2.fillRoundRect(xPos, rects[tabIndex].y, width, rects[tabIndex].height,rects[tabIndex].height/2,rects[tabIndex].height/2);
		if(tb.getSelectedIndex()==tabIndex) {
			g2.fillRect(xPos,rects[tabIndex].y+rects[tabIndex].height/4, width,rects[tabIndex].height+rects[tabIndex].height/4);
			System.out.println("text x: "+iconRect.x+","+iconRect.y+","+iconRect.width+","+iconRect.height);
		}
		g2.setColor(Color.white);
		g2.setFont(getFont(rects[tabIndex].height/2));
	
		String title=" Slide "+(tabIndex+1)+" ";
		int xPosText=rects[tabIndex].x + ((rects[tabIndex].width - g2.getFontMetrics().stringWidth(title))/2);
		g2.drawString(title, xPosText,rects[tabIndex].y+(3*rects[tabIndex].height/4)
				);
		g2.dispose();
	
	}
	public void paintContentBorder(Graphics g,int tabPlacement, int selectedRun) {	
	}
	public void paintIcon(Graphics g, int tabPlacement,int tabIndex,Icon icon,Rectangle rect, boolean isSelected) {	
	}	
	public void paintFocusIndicator(Graphics g,int tabPlacement, Rectangle[] rects,int tabIndex,Rectangle iconRect,Rectangle textRect,boolean isSelected) {			
	}
	
	public void setRemoveTabButton(CircularButton removeTabButton) {
		this.removeTabButton=removeTabButton;
	}

	
};



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
	Slide slide;
	Point mousePos;
	boolean isTextEditing;
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
		topLeft.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {

				if(presElement!=null) {
					int deltaX=e.getX()-mousePos.x;
					int deltaY= e.getY()-mousePos.y;
					
					changeBounds(deltaX,deltaY,-deltaX,-deltaY);
				}
				
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				mousePos=e.getPoint();
				
			}
			
		});
		bottomLeft.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent e) {

				if(presElement!=null) {
					int deltaX=e.getX()-mousePos.x;
					int deltaY= e.getY()-mousePos.y;
					
					changeBounds(deltaX,0,-deltaX,deltaY);
				}
				
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				mousePos=e.getPoint();
				
			}
			
		});
		topRight.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
				
					if(presElement!=null) {
						int deltaX=e.getX()-mousePos.x;
						int deltaY= e.getY()-mousePos.y;
						
						changeBounds(0,deltaY,deltaX,-deltaY);
					}
				
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				mousePos=e.getPoint();
				
			}
			
		});
		bottomRight.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
				if(presElement!=null) {
					int deltaX=e.getX()-mousePos.x;
					int deltaY= e.getY()-mousePos.y;
					
					changeBounds(0,0,deltaX,deltaY);
				}
				
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				mousePos=e.getPoint();
				
			}
			
		});
		this.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
				if(presElement!=null) {
					changeBounds(e.getX()-mousePos.x,e.getY()-mousePos.y,0,0);
					//changeSize(0,0,e.getX()-mousePos.x,e.getY()-mousePos.y);
				}
				
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				mousePos=e.getPoint();
				
			}
			
		});
		isTextEditing=false;
		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if(presElement.getType()=="TEXT") {
					((TextElement)presElement).setTextEditing(true);
				}else {
					((TextElement)presElement).setTextEditing(false);
				}
				
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
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
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
	public void attatchToPresElement(Slide slide,PresElement presElement) {
		this.setSize(presElement.getComponent().getWidth()+30, presElement.getComponent().getHeight()+30);
		this.setLocation(presElement.getComponent().getX()-15, presElement.getComponent().getY()-15);
		this.presElement=presElement;
		this.slide= slide;
		topRight.setBounds(this.getWidth()-10,0,10,10);
		topLeft.setBounds(0,0,10,10);
		bottomRight.setBounds(this.getWidth()-10, this.getHeight()-10, 10, 10);
		bottomLeft.setBounds(0, this.getHeight()-10, 10, 10);
		
	}
	public void setBackground(Color color) {
		super.setBackground(color);
		main= color;
	}
	private void changeBounds(int deltaX, int deltaY,int deltaWidth, int deltaHeight) {
		Point oldPixelPoint= presElement.getComponent().getLocation();
		Point newPixelPoint= new Point(oldPixelPoint.x+deltaX,oldPixelPoint.y+deltaY);
		Point ptPos= slide.pxToPt(newPixelPoint);
		
		
		Point changeInWHPt= slide.pxToPt(new Point(deltaWidth, deltaHeight));
		
		
		if(presElement.getType()=="CIRCLE") {
			int radiusInPxW= presElement.getComponent().getWidth()+deltaWidth;
			int radiusInPxH= presElement.getComponent().getHeight()+deltaHeight;
			int radiusPx;
			if(radiusInPxW>radiusInPxH) {
				radiusPx= radiusInPxH/2;
			}else {
				radiusPx= radiusInPxW/2;
			}
			int radiusPt= slide.pxToPt(new Point(radiusPx,radiusPx)).x;
			Circle element= (Circle)presElement;
			element.setRadius(radiusPt);
			element.setWidth(2*radiusPt);
			element.setHeight(2*radiusPt);
			
		
			element.getComponent().setBounds(newPixelPoint.x, newPixelPoint.y, radiusPx*2,radiusPx*2);
			this.setBounds(newPixelPoint.x-15, newPixelPoint.y-15, (radiusPx*2)+30, (radiusPx*2)+30);
			
		}else {
			presElement.setWidth(presElement.getWidth()+changeInWHPt.x);
			presElement.setHeight(presElement.getHeight()+changeInWHPt.y);
			presElement.getComponent().setBounds(newPixelPoint.x, newPixelPoint.y, presElement.getWidth()+changeInWHPt.x, presElement.getHeight()+changeInWHPt.y);
			this.setBounds(newPixelPoint.x-15, newPixelPoint.y-15, presElement.getComponent().getWidth()+changeInWHPt.x+30, presElement.getComponent().getHeight()+changeInWHPt.y+30);
		}
		presElement.setX(ptPos.x);
		presElement.setY(ptPos.y);

		
		//this.setBounds(this.getX()+deltaX, this.getY()+deltaY, this.getWidth()+deltaWidth,this.getHeight()+deltaHeight);
		topRight.setBounds(this.getWidth()-10,0,10,10);
		topLeft.setBounds(0,0,10,10);
		bottomRight.setBounds(this.getWidth()-10, this.getHeight()-10, 10, 10);
		bottomLeft.setBounds(0, this.getHeight()-10, 10, 10);
		this.repaint();
		presElement.getComponent().validate();
		presElement.getComponent().repaint();
		
		
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


