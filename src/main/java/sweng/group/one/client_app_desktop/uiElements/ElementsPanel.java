package sweng.group.one.client_app_desktop.uiElements;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.ScrollPaneConstants;
import javax.swing.plaf.basic.BasicScrollBarUI;

import graphics.Circle;
import graphics.Rectangle;
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

	JPanel upperPanel;
	JPanel lowerPanel;
	
	CircleButton addElement, deleteElement;
	
	JScrollPane scrollPane;
	JPanel scrollView;
	JViewport viewPort;
	
	GridBagLayout gridBagLayout;
	
	Presentation presentation;
	
	int currentSlideIndex; // Indicates what rect is selected
	MouseListener elementRectMouseListener;

	 ArrayList<SlideElementRectsPanel>slides;
	 Slide currentSlide;
	
	
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
		
		toolBar= new CustomToolBar() {
			public void setMainAndSecondaryColor(Color main, Color secondary) {
				this.main= secondary;
				this.secondary=main;
			}
		};
		mediaPanel= new CustomMediaBox() {
			public void setMainAndSecondaryColor(Color main, Color secondary) {
			this.main= secondary;
			this.secondary=main;
			
			}
		};
		
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
				CircleButton b= new CircleButton();
				try {
					b.setImageIcon(ImageIO.read(new File("./Assets/down.png")));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				b.setMainBackground(transparent);
						
				return b;
			}
			public JButton createDecreaseButton(int o) {				
				CircleButton b= new CircleButton();
				try {
					b.setImageIcon(ImageIO.read(new File("./Assets/up.png")));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				b.setMainBackground(transparent);
				
				return b;
			}
				
		};
		scrollPane.getVerticalScrollBar().setUI(scrollBarUI);
		scrollPane.getVerticalScrollBar().setOpaque(false);
	
		
		/*
		 *  Delete and add element Buttons
		 *  
		 */
		
		addElement= new CircleButton();
		addElement.setImageIcon(ImageIO.read(new File("./Assets/plus-small.png")));
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
						case "CIRCLE":
							element= new Circle(defaultPoint, (int)Math.min(defaultWidth, defaultHeight), 0, currentSlide, Color.black, null, null);
							break;
						case "RECTANGLE":
							element= new Rectangle(defaultPoint,(int) defaultWidth,(int) defaultHeight, 0, currentSlide, Color.black, null, null);
							break;
						}
						for(SlideElementRectsPanel s:slides) {
							if((s.getSlide()==currentSlide)&&(element!=null)) {
								s.addElement(element);
								toolBar.setSelectedElement(element);
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
		deleteElement=new CircleButton();
		deleteElement.setImageIcon(ImageIO.read(new File("./Assets/cross.png")));
		lowerPanel.add(deleteElement);	
		deleteElement.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				for(SlideElementRectsPanel s:slides) {
					if((s.getSlide()==currentSlide)&&(s.getSelectedElement()!=null)) {
						s.removeElement(s.getSelectedElement());
						scrollView.validate();
						scrollView.repaint();
					}
				}
				
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
		
		
		
		/*
		 *  List of SlideElementRectsPanels
		 */
		slides=new ArrayList<SlideElementRectsPanel>();
		presentation= new Presentation();
	}
	
	/* 
	 *  Adding, removing and setting current visible element view panel
	 */
	public void addNewPanelForSlide(Slide slide) {
		SlideElementRectsPanel elementRectPanel= new SlideElementRectsPanel(slide);
		slides.add(elementRectPanel);
		//presentation.addSlide(slide);
	}
	public void removePanelForSlide(Slide slide) {
		for(int i=0;i<slides.size();i++) {
			if(slides.get(i).getSlide()==slide) {
				slides.remove(i);
				//presentation.getSlides().remove(i);
				
			}
		}
	}
	public void setVisibleElementPanelFor(Slide slide) {
		boolean slideIsFound=false;
		for(SlideElementRectsPanel e:slides) {
			if(e.getSlide()==slide) {
				scrollView.removeAll();
				scrollView.setLayout(new GridLayout());
				scrollView.add(e);
				
				slideIsFound=true;
				currentSlide=slide;
			}
		}
		if(slideIsFound==false) {
			System.out.println("Slide is not found in ElementViewRects");
		}
	}
	
	/*
	 *  Adding, removing pres Elements to current slide
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
	public void addTextElement(String text,Point pixelPos, int pixelWidth, int pixelHeight, Slide slide, int slideNumber) {
		float textPointWidth= pixelWidth*(slide.getPointWidth()/slide.getWidth());
		float textPointHeight= pixelHeight*(slide.getPointHeight()/slide.getHeight());
		Point textPointPos= new Point(pixelPos.x*(slide.getPointWidth()/slide.getWidth()),pixelPos.y*(slide.getPointHeight()/slide.getHeight()));
	
		TextElement t= new TextElement(text,"Arial",(int)textPointHeight,Color.black,0,textPointPos,(int)textPointWidth,(int)textPointHeight,slide);
		for(SlideElementRectsPanel e:slides) {
			if(e.getSlide()==slide) {
				e.addElement(t);
				toolBar.setSelectedElement(t);
				propertiesPanel.setManipulatorFor(t);
			}
		}
	}
	public void addShapeElement(String shapeType,Point pixelPos, int pixelWidth, int pixelHeight, Slide slide, int slideNumber ) {
		float shapePointWidth= pixelWidth*(slide.getPointWidth()/slide.getWidth());
		float shapePointHeight= pixelHeight*(slide.getPointHeight()/slide.getHeight());
		Point shapePointPos= new Point(pixelPos.x*(slide.getPointWidth()/slide.getWidth()),pixelPos.y*(slide.getPointHeight()/slide.getHeight()));	
		for(SlideElementRectsPanel e:slides) {
			if(e.getSlide()==slide) {
				switch(shapeType) {
				case "CIRCLE":
					PresElement c= new Circle(shapePointPos, (int)Math.min(shapePointWidth, shapePointHeight), 0, slide, Color.black, null, null);
					e.addElement(c);
					toolBar.setSelectedElement(c);	
					propertiesPanel.setManipulatorFor(c);
					break;
				case "RECTANGLE":
					PresElement r= new Rectangle(shapePointPos,(int) shapePointWidth,(int) shapePointHeight, 0, slide, Color.black, null, null);
					e.addElement(r);
					toolBar.setSelectedElement(r);
					propertiesPanel.setManipulatorFor(r);
					break;			
				}
			}
		}
	}
	public void addImageElement(File file, BufferedImage image,String text,Point pixelPos, int pixelWidth, int pixelHeight, Slide slide, int slideNumber) {
		float imagePointWidth= pixelWidth*(slide.getPointWidth()/slide.getWidth());
		float imagePointHeight= pixelHeight*(slide.getPointHeight()/slide.getHeight());
		Point imagePointPos= new Point(pixelPos.x*(slide.getPointWidth()/slide.getWidth()),pixelPos.y*(slide.getPointHeight()/slide.getHeight()));
		ImageElement i= new ImageElement(imagePointPos,(int)imagePointWidth,(int)imagePointHeight,0, slide,image);
		for(SlideElementRectsPanel e:slides) {
			if(e.getSlide()==slide) {
				e.addElement(i);
				toolBar.setSelectedElement(i);
				propertiesPanel.setManipulatorFor(i);
			}
		}
	}
	
	/*
	 *  Setters and getters 
	 */
	public CustomMediaBox getMediaBox() {
		return mediaPanel;
	}
	public CustomToolBar getToolBar() {
		return toolBar;
	}
	/*
	 * Overided methods 
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
		addElement.setSize(buttonSize);
		deleteElement.setSize(buttonSize);
		addElement.setLocation((width/4)-(buttonSize/2),buttonSize/2);
		deleteElement.setLocation(((3*width)/4)-(buttonSize/2),buttonSize/2);
		scrollPane.setBounds(curvatureRadius/2, 0, upperPanel.getWidth()-curvatureRadius/2, upperPanel.getHeight());
	}
}

class SlideElementRectsPanel extends JPanel implements ComponentInterface{
	private static final long serialVersionUID = 1L;
	Slide slide;
	ArrayList<ViewRect>viewRects;
	Color boxColors;
	int[] layoutRatio;
	JPanel panel= this;
	ViewRect selectedLayer;

	public SlideElementRectsPanel(Slide slide) {
		this.slide= slide;

		viewRects= new ArrayList<ViewRect>();
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
	}
	public void addElement(PresElement element) {
		slide.add(element);
		System.out.println("B");
		ViewRect viewRect= new ViewRect(slide,element);
		for(int i=0;i<viewRects.size();i++) {
			viewRects.get(i).setClicked(false);
		}
		viewRect.setClicked(true);
		selectedLayer= viewRect;
		viewRects.add(viewRect);
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
				
					
					for(int i=0;i<viewRects.size();i++) {
						if(e.getComponent()==viewRects.get(i)) {
							
							if(viewRects.get(i).isClicked()==false) {
								viewRects.get(i).setClicked(true);
								selectedLayer= viewRects.get(i);
								
							}else {
								viewRects.get(i).setClicked(false);
								//selectedLayer= null;
							}
							
						}else {
							viewRects.get(i).setClicked(false);
							
						}
						viewRects.get(i).repaint();
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
	
	public Slide getSlide() {
		return slide;
	}
	public ArrayList<ViewRect> getViewRects() {
		return viewRects;
	}
	public int[] getLayoutRatio() {
		return layoutRatio;
	}
	public void setColours(Color main,Color secondary) {
		boxColors= secondary;
		this.setBackground(main);
	}
	public PresElement getSelectedElement() {
		if(selectedLayer.getByPresElement()==null) {
			return null;
		}else {
			return selectedLayer.getByPresElement();
		}
	}
	
}





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
		clickedColor= new Color(255,255,255,100);
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
		g2.setColor(background);
		g2.fillRoundRect(0, 5, this.getWidth(),this.getHeight()-5,10,10);
		if(clicked==true) {
			g2.setColor(clickedColor);
			g2.fillRoundRect(0, 5, this.getWidth(),this.getHeight()-5,10,10);
		}
		g2.fillRoundRect(0, 5, this.getWidth(),this.getHeight()-5,10,10);
		g2.setColor(text);
		g2.setFont(font);
		g2.drawString(title, 5, 3*(this.getHeight()/4));
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