package sweng.group.one.client_app_desktop.uiElements;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.ScrollPaneConstants;
import javax.swing.plaf.basic.BasicScrollBarUI;

import sweng.group.one.client_app_desktop.graphics.Circle;
import sweng.group.one.client_app_desktop.graphics.Rectangle;
import sweng.group.one.client_app_desktop.media.GraphicsElement;
import sweng.group.one.client_app_desktop.media.ImageElement;
import sweng.group.one.client_app_desktop.media.TextElement;
import sweng.group.one.client_app_desktop.presentation.PresElement;
/*
* Edited: 27 Apr 2023 sophiemaw
*/
import sweng.group.one.client_app_desktop.presentation.Presentation;
import sweng.group.one.client_app_desktop.presentation.Slide;
import sweng.group.one.client_app_desktop.sceneControl.ComponentInterface;

public class ElementsPanel extends UploadSceneComponent implements ComponentInterface{
	/*
	 *  Components
	 */
	private ElementPropertiesPanel propertiesPanel;
	private CustomMediaBox mediaPanel;
	private CustomToolBar toolBar;
	private ElementsPanel thisPanel= this;

	JPanel upperPanel;
	JPanel lowerPanel;
	
	CircularButton addElement, deleteElement;
	
	JScrollPane scrollPane;
	JPanel scrollView;
	JViewport viewPort;
	
	GridBagLayout gridBagLayout;
	
	Presentation presentation;

	
	int currentSlideIndex; // Indicates what rect is selected
	MouseListener elementRectMouseListener;

	 ArrayList<SlideElementRectsPanel>slides;
	 Slide currentSlide;
	
	
	/**
	 * @param propertiesPanel
	 * Description:
	 * 	This class is the central class to contain all presElement information, it tells all other 
	 * upload scene components what element is selected.
	 *  This class displays elements of the current visible slide as a list of rects (ViewRects) wthin 
	 *  a panel for a specific slide (SlideElementRectsPanel)
	 *  When a rect is clicked, it's colour changes and the presElement contained within that rect is sent to
	 *  toolBar to change it's selected elementField and to the ptopertiesPanel to change values in it.

	 *  The presentation is also stored in this class, when tabPane adds a slide to it, the slide is passed into this class and a new 
	 *  SlideElementRectsPanel is created for that slide. When current visible slide changes, (tabPane changes), the new visible slide is
	 *  sent to this and it's corresponding SlideElementRectsPanel is made visible. 
	 *  
	 *  The SlideElementRectsPanel for a specific slide contains a list of ViewRects, which are the presElements 
	 *  on that slide. 
	 *  
	 *  Implemented: 
	 *   1. viewRects are added when new elements are added to the slide
	 *   2. when addButton is clicked, whichever selectedElementType is stored in the toolBar, a 
	 *   	an element is added to the currentSlide of that type. When element properties are not defined
	 *   	i.e when addButton is clicked, default property values are created making element apear in the centre
	 *   	of slide. 
	 *   3. When delete button is pressed, if there is a selcted element (i.e a rect is selected) the element stored in the 
	 *   	rect is removed from the list of elements, removed from the slide and everything is updated.
	 *   
	 *   
	 *  Not Implemented:
	 *   1. SlideElementsRectPanel does not update when slide changes
	 *   2. propertiesPanel does not update values when a new element is selected
	 *   
	 *  Notes: When a graphicElement is selected, the toolBar's selected element is updated to allow
	 *   to allow the slide mouselistener stored in it to add graphics 
	 *  
	 */
	public ElementsPanel(ElementPropertiesPanel propertiesPanel) {
		this.propertiesPanel=propertiesPanel;
		try {
			initialise();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	};
	private void initialise() throws IOException {
		this.main=colorLight;
		this.secondary=colorDark;
		
		toolBar= new CustomToolBar();
		propertiesPanel.setToolBarColorChanger(toolBar);
		mediaPanel= new CustomMediaBox();
		
		upperPanel = new JPanel();
		upperPanel.setOpaque(false);
		this.add(upperPanel);
		lowerPanel= new JPanel();
		lowerPanel.setOpaque(false);
		this.add(lowerPanel);	
		this.setLayout(null);
		lowerPanel.setLayout(null);
		upperPanel.setLayout(null);	
		
		/*
		 *  Scrollpane and scroll bar
		 */
		
		
		scrollView= new JPanel();
		//gridBagLayout= new GridBagLayout();
		scrollPane= new  JScrollPane(scrollView);
		
		scrollView.setBackground(colorLight);
		scrollPane.setBackground(colorLight);
		upperPanel.add(scrollPane);
		scrollPane.setBorder(null);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setVisible(true);
		
		BasicScrollBarUI scrollBarUI= new BasicScrollBarUI() {
			public void installDefaults() {
				super.installDefaults();
				this.thumbColor= colorDark;
				this.trackColor= transparent;
				this.thumbHighlightColor= getColorLightAdjustedOpacity(100);
				
			}
			
			
			public JButton createIncreaseButton(int o) {				
				CircularButton b= new CircularButton();
				try {
					b.setIconImage(ImageIO.read(new File("./Assets/down.png")));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				b.setMainBackgroundColour(transparent);
						
				return b;
			}
			public JButton createDecreaseButton(int o) {				
				CircularButton b= new CircularButton();
				try {
					b.setIconImage(ImageIO.read(new File("./Assets/up.png")));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				b.setMainBackgroundColour(transparent);
				
				return b;
			}
				
		};
		scrollPane.getVerticalScrollBar().setUI(scrollBarUI);
		scrollPane.getVerticalScrollBar().setOpaque(false);
	
		
		/*
		 *  Delete and add element Buttons
		 *  
		 */
		
		addElement= new CircularButton();
		addElement.setIconImage(ImageIO.read(new File("./Assets/plus-small.png")));
		addElement.setMainBackgroundColour(Color.white);
		lowerPanel.add(addElement);
		addElement.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				
				if(currentSlide!=null) {
					
					/*
					 *  Default size and positions 
					 */
		
					if(toolBar.getSelectedElementType()=="GRAPHIC") {
						addGraphicElementToCurrentSlide();	
					}else {
						PresElement element= null;
						int defaultHeight= currentSlide.getPointHeight()/10;
						int defaultWidth= currentSlide.getPointWidth()/10;
						Point defaultPoint= new Point((currentSlide.getPointWidth()/2)-defaultWidth/2,(currentSlide.getPointHeight()/2)-defaultHeight/2);
						
						switch(toolBar.getSelectedElementType()){
						case "TEXT":	
							element= new TextElement("TEXT","Arial",defaultHeight,Color.black,0,defaultPoint,defaultWidth,defaultHeight,currentSlide);
							break;
						case "SHAPE":
							element= new Circle(defaultPoint, (int)Math.min(defaultWidth, defaultHeight), 0, currentSlide, Color.black, null, null);
							break;
							/*
						case "RECTANGLE":
							element= new Rectangle(defaultPoint,(int) defaultWidth,(int) defaultHeight, 0, currentSlide, Color.black, null, null);
							break;
							*/
						}
						for(SlideElementRectsPanel s:slides) {
							if((s.getSlide()==currentSlide)&&(element!=null)) {
								s.addElement(element);
								toolBar.setSelectedElement(element);
								toolBar.setGeneralToolBarListenersForSlide(thisPanel, currentSlide);
								propertiesPanel.setManipulatorFor(element);
								scrollView.validate();
								scrollView.repaint();
							}
						}
					}
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
		deleteElement=new CircularButton();
		deleteElement.setIconImage(ImageIO.read(new File("./Assets/cross.png")));
		lowerPanel.add(deleteElement);	
		deleteElement.setMainBackgroundColour(Color.white);
		deleteElement.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				for(SlideElementRectsPanel s:slides) {
					if(s.getSelectedElement()!=null){
					  if(s.getSlide()==currentSlide) {
						s.removeElement(s.getSelectedElement());
						toolBar.setSelectedElement(s.getSelectedElement());
						scrollView.validate();
						scrollView.repaint();
					}
				}
				
			}}

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
		
		
		
		/*
		 *  List of SlideElementRectsPanels
		 */
		slides=new ArrayList<SlideElementRectsPanel>();
		presentation= new Presentation();
	}
	

	/**
	 * @param slide
	 * Called within tabPane to create a slide specific SlideElementRectsPanel 
	 */
	public void addNewPanelForSlide(Slide slide) {
		SlideElementRectsPanel elementRectPanel= new SlideElementRectsPanel(slide,toolBar,propertiesPanel);
		slides.add(elementRectPanel);
		//presentation.addSlide(slide);
	}
	
	/**
	 * @param slide
	 * Called within tabPane to remove a slide specific SlideElementRectsPanel 
	 */
	public void removePanelForSlide(Slide slide) {
		for(int i=0;i<slides.size();i++) {
			if(slides.get(i).getSlide()==slide) {
				slides.remove(i);
				//presentation.getSlides().remove(i);
				
			}
		}
	}
	
	/**
	 * @param slide
	 * Called within tabPane to set current SlideElementRectsPanel to specific slide's
	 * Sets currentSlide to slide
	 */
	public void setVisibleElementPanelFor(Slide slide) {
		boolean slideIsFound=false;
		for(SlideElementRectsPanel e:slides) {
			if(e.getSlide()==slide) {
				/*
				 *  Set selected viewRect to 0
				 */
				if(currentSlide!=slide) {
					e.setAllViewRectUnselected();
				}
			
				scrollView.removeAll();
				scrollView.setLayout(new GridLayout());
				scrollView.add(e);
				
				slideIsFound=true;
				currentSlide=slide;
				scrollView.repaint();
			}
		}
		if(slideIsFound==false) {
			System.out.println("Slide is not found in ElementViewRects");
		}
	}
	

	/**
	 * Creates a new Graphic Elememt of same size as current SLide
	 * Sends this element to the current visible slideElementRectsPanel
	 * ToolBar's selectedElement is set to this new element
	 * PropertiesPanel is set to display properties of new element
	 */
	public void addGraphicElementToCurrentSlide() {
		if(currentSlide!=null) {
			Slide slide= currentSlide;
			GraphicsElement g= new GraphicsElement(new Point(0,0),slide.getPointWidth(),slide.getPointHeight(),0,slide);
			for(SlideElementRectsPanel e:slides) {
				if(e.getSlide()==slide) {
					System.out.println("A");
					e.addElement(g);
					toolBar.setSelectedElement(g);
					propertiesPanel.setManipulatorFor(g);
				}
			}
		}
	}
	/**
	 * @param text
	 * @param pixelPos
	 * @param pixelWidth
	 * @param pixelHeight
	 * @param slide
	 * @param slideNumber
	 * 
	 * Creates a new textElement, property values are converted to pts from pixels 
	 * Sends this element to the current visible slideElementRectsPanel
	 * ToolBar's selectedElement is set to this new element
	 * PropertiesPanel is set to display properties of new element
	 */
	public void addTextElement(String text,Point pixelPos, int pixelWidth, int pixelHeight, Slide slide, int slideNumber) {
		if(slide==null) {
			slide=currentSlide;
			for(int i=0;i<slides.size();i++) {
				if(slides.get(i).getSlide()==currentSlide) {
					slideNumber=i;
				}
			}
		}
		Point textWH= slide.pxToPt(new Point(pixelWidth,pixelHeight));
		float textPointWidth=(int) textWH.getX();
		float textPointHeight=(int) textWH.getY();
		Point textPointPos= slide.pxToPt(pixelPos);
		TextElement t= new TextElement(text,"Arial",(int)textPointHeight,Color.black,0,textPointPos,(int)textPointWidth,(int)textPointHeight,slide);
		for(SlideElementRectsPanel e:slides) {
			if(e.getSlide()==slide) {
				e.addElement(t);
				toolBar.setSelectedElement(t);
				propertiesPanel.setManipulatorFor(t);
				System.out.println("text pw: "+textPointWidth+" slide pw: "+slide.getPointWidth());
				e.validate();
				slide.validate();
				slide.repaint();
			}
		}
	}
	/**
	 * @param shapeType - "CIRCLE" "RECTANGLE"
	 * @param pixelPos -  midpoint of shape
	 * @param pixelWidth
	 * @param pixelHeight
	 * @param slide
	 * @param slideNumber
	 * 
	 *  Creates a new shapeElement, property values are converted to pts from pixels- to ensure shape
	 *  fits on slide, getMaxBoundsForObjectSizetWithinContainer() is called to obtain it's maximum size
	 *  given the slideSize, shape aspect ratio and pixelPoint being the midPoint of shape. getMaxBoundsForObjectSizetWithinContainer()
	 *  returns Bounds of new shape size and new top left coordinate within slide cordinate system
	 * Sends this element to the current visible slideElementRectsPanel
	 * ToolBar's selectedElement is set to this new element
	 * PropertiesPanel is set to display properties of new element
	 */
	public void addShapeElement(String shapeType,Point pixelPos, int pixelWidth, int pixelHeight, Slide slide, int slideNumber ) {
		if(slide==null) {
			slide=currentSlide;
			for(int i=0;i<slides.size();i++) {
				if(slides.get(i).getSlide()==currentSlide) {
					slideNumber=i;
				}
			}
		}
		Point shapeWHpt;
		if(pixelWidth==0 || pixelHeight==0) {
			int defaultHeight= currentSlide.getPointHeight()/10;
			int defaultWidth= currentSlide.getPointWidth()/10;
			shapeWHpt= new Point(defaultWidth,defaultHeight);
		}else {
			shapeWHpt= new Point(new Point(pixelWidth,pixelHeight));
		}
		
		Point shapePosPt= currentSlide.pxToPt(pixelPos);
		
		System.out.println("Pt point: "+shapePosPt.x+ ","+shapePosPt.y);
		System.out.println("Pt size: "+shapeWHpt.x+ ","+shapeWHpt.y);
		for(SlideElementRectsPanel e:slides) {
			if(e.getSlide()==slide) {
				switch(shapeType) {
				case "CIRCLE":
					PresElement c= new Circle(shapePosPt, (int)Math.min(shapeWHpt.x, shapeWHpt.y), 0, slide, Color.black, null, null);
					e.addElement(c);
					toolBar.setSelectedElement(c);	
					propertiesPanel.setManipulatorFor(c);
					break;
				case "RECTANGLE":
					PresElement r= new Rectangle(shapePosPt,(int) shapeWHpt.x,(int) shapeWHpt.y, 0, slide, Color.black, null, null);
					e.addElement(r);
					toolBar.setSelectedElement(r);
					propertiesPanel.setManipulatorFor(r);
					break;			
				}
			}
		}
	}
	
	/**
	 * @param file
	 * @param image
	 * @param pixelPos - midPoint of Image
	 * @param pixelWidth
	 * @param pixelHeight
	 * @param slide
	 * @param slideNumber
	 *  Creates a new imageElement, property values are converted to pts from pixels- to ensure image
	 *  fits on slide, getMaxBoundsForObjectSizetWithinContainer() is called to obtain it's maximum size
	 *  given the slideSize, image aspect ratio and pixelPoint being the midPoint of the image. getMaxBoundsForObjectSizetWithinContainer()
	 *  returns Bounds of new image size and new top left coordinate within slide cordinate system
	 * Sends this element to the current visible slideElementRectsPanel
	 * ToolBar's selectedElement is set to this new element
	 * PropertiesPanel is set to display properties of new element
	 */
	public void addImageElement(File file, BufferedImage image,Point pixelPos, int pixelWidth, int pixelHeight, Slide slide, int slideNumber) {
		if(slide==null) {
			slide=currentSlide;
			for(int i=0;i<slides.size();i++) {
				if(slides.get(i).getSlide()==currentSlide) {
					slideNumber=i;
				}
			}
		}
		java.awt.Rectangle pixelBounds=getMaxBoundsForObjectSizetWithinContainer(new Dimension(pixelWidth,pixelHeight),slide,pixelPos);
		/*
		 * Converting to pixels
		 */
		Point imageWH= slide.pxToPt(new Point(pixelBounds.width,pixelBounds.height));
		float imagePointWidth=(int) imageWH.getX();
		float imagePointHeight=(int) imageWH.getY();
		Point imagePointPos= slide.pxToPt(new Point(pixelBounds.x,pixelBounds.y));
		
		
		ImageElement i= new ImageElement(imagePointPos,(int)imagePointWidth,(int)imagePointHeight,0, slide,image);
		for(SlideElementRectsPanel e:slides) {
			if(e.getSlide()==slide) {
				e.addElement(i);
				toolBar.setSelectedElement(i);
				toolBar.setGeneralToolBarListenersForSlide(this, slide);
				propertiesPanel.setManipulatorFor(i);
			}
		}
	}
	
/* 
 * These getters are called from upload scene to resize components 
 */
	public CustomMediaBox getMediaBox() {
		return mediaPanel;
	}
	public CustomToolBar getToolBar() {
		return toolBar;
	}
	public Presentation getPresentation() {
		return presentation;
	}
	public Slide getCurrentSlide() {
		return currentSlide;
	}
	/*
	 * Explained is setMarginBounds() diagram  
	 */
	public void setMarginBounds(int r, int t, int l, int b) {
		super.setMarginBounds(r, t, l, b);
		int width= this.getWidth()- (r+l);
		int height= this.getHeight()-(t+b);
		int xPos= r;
		int yPos= t;
		upperPanel.setBounds(xPos+scrollPane.getVerticalScrollBar().getWidth(),yPos+curvatureRadius/2,width-scrollPane.getVerticalScrollBar().getWidth(),height- (curvatureRadius+(curvatureRadius/2)));
		lowerPanel.setBounds(xPos, (height+yPos)-curvatureRadius, width, (curvatureRadius));

		int buttonSize= lowerPanel.getHeight()/2;
		addElement.setSize(buttonSize,buttonSize,buttonSize);
		deleteElement.setSize(buttonSize,buttonSize,buttonSize);
		addElement.setLocation((width/4)-(buttonSize/2),buttonSize/2);
		deleteElement.setLocation(((3*width)/4)-(buttonSize/2),buttonSize/2);
		scrollPane.setBounds(curvatureRadius/2, 0, upperPanel.getWidth()-curvatureRadius/2, upperPanel.getHeight());
	}
}


/**
 * @author sophiemaw
 *	This Panel is asocciated within a specific slide, it provides a visual representation of all 
 *  the presElements on that slide by creating viewRects 
 */
class SlideElementRectsPanel extends JPanel implements ComponentInterface{
	private static final long serialVersionUID = 1L;
	Slide slide;
	ArrayList<ViewRect>viewRects;
	Color boxColors;
	int[] layoutRatio;
	JPanel panel= this;
	ViewRect selectedLayer;
	CustomToolBar toolBar;
	ElementPropertiesPanel propertiesBox;
	/**
	 * @param slide - to add elements to slide
	 * @param toolBar - to set selected element field
	 */
	public SlideElementRectsPanel(Slide slide, CustomToolBar toolBar, ElementPropertiesPanel propertiesBox) {
		this.slide= slide;
		this.toolBar=toolBar;
		this.propertiesBox=propertiesBox;
		viewRects= new ArrayList<ViewRect>();
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
	}
	
	public void setAllViewRectUnselected() {
		for(ViewRect w:viewRects) {
			w.setClicked(false);
		}
	}
	
	/**
	 * @param element - new presElement 
	 * This adds the element onto the slide stored within this class 
	 * Creates a viewRect of that element and adds it to this panel of viewRects
	 *  Adds a listener to new viewRect, which, when clicked, will turn all other 
	 *  rects contained within this panel to not clicked (colour set to default) and 
	 *  new clicked view rect set to clicked (clicked colour)
	 */
	public void addElement(PresElement element) {
		slide.add(element);
		ViewRect viewRect= new ViewRect(slide,element);
		for(int i=0;i<viewRects.size();i++) {
			viewRects.get(i).setClicked(false);
		}
		viewRect.setClicked(true);
		selectedLayer= viewRect;
		viewRects.add(viewRect);
		this.setBackground(colorLight);
		this.add(viewRect);
		viewRect.setMinimumSize(new Dimension(this.getWidth(),curvatureRadius));
		viewRect.setPreferredSize(new Dimension(this.getWidth(),curvatureRadius));
		this.repaint();
		this.validate();
	
			viewRect.addMouseListener(new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mousePressed(MouseEvent e) {
				
					boolean bool= false;
					for(int i=0;i<viewRects.size();i++) {
						if(e.getComponent()==viewRects.get(i)) {
							
							if(viewRects.get(i).isClicked()==false) {
								viewRects.get(i).setClicked(true);
								selectedLayer= viewRects.get(i);
								toolBar.setSelectedElement(selectedLayer.presElement);
								propertiesBox.setManipulatorFor(selectedLayer.presElement);
								bool=true;
								
							}else {
								viewRects.get(i).setClicked(false);
								//selectedLayer= null;
							}
							
						}else {
							viewRects.get(i).setClicked(false);
							
						}
						viewRects.get(i).repaint();
					}
					if(bool==false) {
						selectedLayer=null;
						toolBar.setSelectedElement(null);
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
			
		}
	
	/**
	 * @param el
	 * Removes an element from the slide and corropsonding rect from this panel 
	 */
	public void removeElement(PresElement el) {
		ViewRect vr=null;
		int index=0;
		for(int i=0;i<viewRects.size();i++) {
			if(viewRects.get(i).getByPresElement()==el) {
				vr= viewRects.get(i);
				index=i;
			}
		}
		if(vr!=null) {
			viewRects.remove(vr);
			this.remove(vr);
			this.repaint();
			slide.getElements().remove(el);
			slide.validate();
			slide.remove(index);
			slide.repaint();
			if(viewRects.size()>0) {
				selectedLayer= viewRects.get(viewRects.size()-1);
			}
		}
	}
	/*
	 *  Gets the slide stored in this class
	 */
	public Slide getSlide() {
		return slide;
	}
	/*
	 *  Gets the list of viewRects stored in this class
	 */
	public ArrayList<ViewRect> getViewRects() {
		return viewRects;
	}
	
	
	/**
	 * @return whichever element is currently selected
	 */
	public PresElement getSelectedElement() {
		return selectedLayer.getByPresElement();
		
	}
	
}


/**
 * @author sophiemaw
 *	This class is the corrosponding presElement rect, with the title: 
 *			element.getType()  + number of elements of that type currently on the slide, ex. "Graphic 4"
 *  Contains methods to set clicked to a boolean value 
 */
class ViewRect extends JPanel implements ComponentInterface{
	Color background;
	Color clickedColor;
	Color text;
	String type;
	String title;
	PresElement presElement;
	Slide slide;
	Font font;
	boolean clicked;
	public ViewRect(Slide slide,PresElement presEl) {
		background= colorDark;
		text=textColor;
		clickedColor= new Color(255,255,255,50
				);
		repaint();
		this.presElement= presEl;
		type= presEl.getType();
		this.setOpaque(false);
		this.setBorder(null);
		int counter=0;
		for(int i=0;i<slide.getElements().size();i++) {
			if(slide.getElements().get(i).getType()==type){
				counter++;
			}
		}
		title= type.toLowerCase()+ " "+counter;
		clicked=false;
	}
	//OVERiDED:
	public void paint(Graphics g) {
		Graphics2D g2= (Graphics2D)g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		GradientPaint gp= new GradientPaint(this.getWidth()/2,0,colorLightHighlight,this.getWidth()/2,this.getHeight()/2,colorDark
				);
		g2.setPaint(gp);
		g2.fillRoundRect(5, 5, this.getWidth()-10,this.getHeight()-5,10,10);
		if(clicked==true) {
			g2.setColor(clickedColor);
			g2.fillRoundRect(5, 5, this.getWidth()-10,this.getHeight()-5,10,10);
		}
		g2.setColor(text);
		g2.setFont(font);
		g2.drawString(title, 10, 3*(this.getHeight()/4));
		g2.dispose();
		super.paint(g);
	}
	//SETTERS AND GETTERS
	
	public String getType() {
		return type;
	}
	public void setClicked(boolean bool) {
		clicked= bool;
	}
	public PresElement getByPresElement() {
		return presElement;
	}
	public void setPreferredSize(Dimension d) {
		super.setPreferredSize(d);
		font = new Font(null, Font.PLAIN,
				d.height/2);
	}
	public boolean isClicked() {
		return clicked;
	}
	public String getTitle() {
		return title;
	}
}

/*
* Created: 27 Apr 2023 sophiemaw
*/