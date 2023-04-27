package sweng.group.one.client_app_desktop.uiElements;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
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
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.plaf.basic.BasicScrollBarUI;

import graphics.Circle;
import graphics.Rectangle;
import graphics.Shape;
import sweng.group.one.client_app_desktop.media.GraphicsElement;
import sweng.group.one.client_app_desktop.media.ImageElement;
import sweng.group.one.client_app_desktop.media.TextElement;
import sweng.group.one.client_app_desktop.presentation.PresElement;
import sweng.group.one.client_app_desktop.presentation.Presentation;
import sweng.group.one.client_app_desktop.presentation.Slide;
import sweng.group.one.client_app_desktop.sceneControl.ComponentInterface;

public class ElementsPanel extends UploadSceneComponent implements ComponentInterface{
	Slide currentSlide;
	ElementPropertiesPanel elementPropertiesPanel;
	CustomToolBar toolBar;
	CustomMediaBox mediaPanel;
	JPanel upperPanel;
	JPanel lowerPanel;

	JButton addElement;
	JButton deleteElement;
	JScrollPane scrollPane;
	JPanel scrollView;
	GridBagLayout gridBagLayout;
	
	Presentation presentation;
	
	ArrayList<ArrayList<ElementRect>>slideElements;
	int currentSlideIndex; // this is so listeners know which rects to listen to
	MouseListener elementRectMouseListener;
	
	public ElementsPanel(ElementPropertiesPanel elementPropertiesPanel) {
		this.main= colorLight;
		this.secondary= colorDark;
		this.setOpaque(false);
		this.elementPropertiesPanel= elementPropertiesPanel;
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
		/*
		 *  These panels seperate the scroll view and the buttons
		 */
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
		 *  scroll stuff
		 */	
		scrollView= new JPanel();
		gridBagLayout= new GridBagLayout();
		scrollView.setLayout(gridBagLayout);
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
			public void paintIncreaseHighlight(Graphics g) {
		
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
		 *  buttons
		 */
		addElement= new JButton() {
			public void paint(Graphics g) {
				Graphics2D g2= (Graphics2D)g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				try {
					g2.drawImage(ImageIO.read(new File("./Assets/plus-small.png")).getScaledInstance(this.getHeight(), this.getHeight(), java.awt.Image.SCALE_SMOOTH),0,0,null);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		lowerPanel.add(addElement);
		addElement.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				switch(toolBar.getSelectedElementType()){
				case "GRAPHIC":
					
					break;
				case "TEXT":
					break;
				case "CIRCLE":
					break;
				case "RECTANGLE":
					break;
				default:
					break;
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
		deleteElement=new JButton() {
			public void paint(Graphics g) {
				Graphics2D g2= (Graphics2D)g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				try {
					g2.drawImage(ImageIO.read(new File("./Assets/cross.png")).getScaledInstance(this.getHeight(), this.getHeight(), java.awt.Image.SCALE_SMOOTH),0,0,null);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		lowerPanel.add(deleteElement);	
		deleteElement.addMouseListener(new MouseListener() {

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
		mediaPanel= new CustomMediaBox();
		toolBar= new CustomToolBar();
		slideElements=new ArrayList<ArrayList<ElementRect>>();
		presentation= new Presentation();
		elementRectMouseListener= new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				for(ElementRect r:slideElements.get(currentSlideIndex)) {
					if(r.contains(e.getPoint())) {
						r.setSelected(true);
					}else {
						r.setSelected(false);
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
			
		};
		currentSlideIndex=0;
	}
	
	/*
	 *  These methods below are to do with slide ElementPanels'
	 */
	
	/**
	 * @param slide
	 * Called from tabPane to add a elementPanel for a corresponding slide
	 */
	public void addPanelForSlide(Slide slide) {
		slideElements.add(new ArrayList<ElementRect>());
		presentation.addSlide(slide);
	}

	/**
	 * @param slide
	 * Called from tabPane to remove an elementPanel for a corresponding slide
	 */
	public void removePanelForSlide(Slide slide) {
		for(int i=0;i<presentation.getSlides().size();i++) {
			if(presentation.getSlides().get(i)==slide) {
				slideElements.remove(i);
				presentation.getSlides().remove(i);
			}
		}	
	}
	/**
	 * @param slide
	 *  @param slideNumber
	 * Called from tabPane to set the current Element panel in scrollVoiew, sets currentSlideIndex so ElementRectMouseListner 
	 * listens to correct rects.
	 */
	public void setElementPanelFor(Slide slide, int slideNumber) {			
			setPositionOfElementRects(slideNumber);
			currentSlideIndex= slideNumber;
	}
	/**
	 * @param slideNumber
	 *  This method resizes and sets location of elementsRects for a specific elementPanel with index 'slideNumber'
	 */
	private void setPositionOfElementRects(int slideNumber) {	
		int elementNumber= slideElements.get(slideNumber).size();
		scrollView.removeAll();
		GridBagConstraints gbc = new GridBagConstraints();
		for(int i=0;i<elementNumber;i++) {
			gbc.gridx = 0;
			gbc.gridy = i;
			gbc.fill = GridBagConstraints.BOTH ;
			gbc.weightx = 1.0 ;
			gbc.weighty = 1.0 ;
			gridBagLayout.setConstraints(scrollView, gbc);
			scrollView.add(slideElements.get(slideNumber).get(i), gbc);	
		}
	}
	/*
	 *  These methods below are to do with elements on a slide
	 */
	
	/**
	 * @param slide
	 * @param slideNumber
	 * Creates a graphic element of the same size as slide, for slide and and adds it to slide.
	 * Creates a corrosponding elementRect and then sets it's position on scrollView
	 */
	public void addGraphicElement(Slide slide,int slideNumber) {
		GraphicsElement g= new GraphicsElement(new Point(0,0),slide.getPointWidth(),slide.getPointHeight(),0,slide);
		slide.add(g);
		ElementRect r= new ElementRect(slide,g);
		r.addMouseListener(elementRectMouseListener);
		slideElements.get(slideNumber).add(r);
		setPositionOfElementRects(slideNumber);
	}
	/**
	 * 
	 * @param text       	text to be displayed.
	 * @param pixelPos   	position in pixels
	 * @param pixelWidth    width in pixels
	 * @param pixelHeight   height in pixels
	 * @param slide      	slide to add textElement to             
	 * @param slideNumber   the slide index in presentation
	 */
	public void addTextElement(String text,Point pixelPos, int pixelWidth, int pixelHeight, Slide slide, int slideNumber) {
		float textPointWidth= pixelWidth*(slide.getPointWidth()/slide.getWidth());
		float textPointHeight= pixelHeight*(slide.getPointHeight()/slide.getHeight());
		Point textPointPos= new Point(pixelPos.x*(slide.getPointWidth()/slide.getWidth()),pixelPos.y*(slide.getPointHeight()/slide.getHeight()));
	
		TextElement t= new TextElement(text,"Arial",(int)textPointHeight,Color.black,0,textPointPos,(int)textPointWidth,(int)textPointHeight,slide);
		slide.add(t);
		ElementRect r= new ElementRect(slide,t);
		r.addMouseListener(elementRectMouseListener);
		slideElements.get(slideNumber).add(r);
		setPositionOfElementRects(slideNumber);
	}
	/**
	 * @param shapeType     "CIRCLE","RECTANGLE"
	 * @param pixelPos		position in pixels
	 * @param pixelWidth	width in pixels
	 * @param pixelHeight	height in pixels
	 * @param slide			slide to add shape element to 
	 * @param slideNumber	the slide index in presentation
	 */
	public void addShapeElement(String shapeType,Point pixelPos, int pixelWidth, int pixelHeight, Slide slide, int slideNumber ) {
		float shapePointWidth= pixelWidth*(slide.getPointWidth()/slide.getWidth());
		float shapePointHeight= pixelHeight*(slide.getPointHeight()/slide.getHeight());
		Point shapePointPos= new Point(pixelPos.x*(slide.getPointWidth()/slide.getWidth()),pixelPos.y*(slide.getPointHeight()/slide.getHeight()));
		PresElement e;
		ElementRect r;
		switch(shapeType) {
		case "CIRCLE":
			e= new Circle(shapePointPos, (int)Math.min(shapePointWidth, shapePointHeight), 0, slide, Color.black, null, null);
			slide.add(e);
			r= new ElementRect(slide,e);
			r.addMouseListener(elementRectMouseListener);
			slideElements.get(slideNumber).add(r);
			setPositionOfElementRects(slideNumber);
			break;
		case "RECTANGLE":
			e= new Rectangle(shapePointPos,(int) shapePointWidth,(int) shapePointHeight, 0, slide, Color.black, null, null);
			slide.add(e);
			r= new ElementRect(slide,e);
			r.addMouseListener(elementRectMouseListener);
			slideElements.get(slideNumber).add(r);
			setPositionOfElementRects(slideNumber);
			break;
			
		}
	}
	/**
	 * @param shapeType     "CIRCLE","RECTANGLE"
	 * @param pixelPos		position in pixels
	 * @param pixelWidth	width in pixels
	 * @param pixelHeight	height in pixels
	 * @param slide			slide to add shape element to 
	 * @param slideNumber	the slide index in presentation
	 */
	public void addImmageElement(File file, BufferedImage image,String text,Point pixelPos, int pixelWidth, int pixelHeight, Slide slide, int slideNumber) {
		float imagePointWidth= pixelWidth*(slide.getPointWidth()/slide.getWidth());
		float imagePointHeight= pixelHeight*(slide.getPointHeight()/slide.getHeight());
		Point imagePointPos= new Point(pixelPos.x*(slide.getPointWidth()/slide.getWidth()),pixelPos.y*(slide.getPointHeight()/slide.getHeight()));
		ImageElement i= new ImageElement(imagePointPos,(int)imagePointWidth,(int)imagePointHeight,0, slide,image);
		slide.add(i);
		ElementRect r= new ElementRect(slide,i);
		r.addMouseListener(elementRectMouseListener);
		slideElements.get(slideNumber).add(r);
		setPositionOfElementRects(slideNumber);
	}
	
	/**
	 * @param slide
	 * @param slideNumber
	 * @param presElement
	 */
	public void removeElement(Slide slide, int slideNumber,PresElement presElement) {
		if(presElement==null) {
			for(int i=0;i<slideElements.get(slideNumber).size();i++) {
				if(slideElements.get(slideNumber).get(i).isSelected()==true) {
					presElement= slideElements.get(slideNumber).get(i).getElement();
				}
			}
		}
		if(presElement!=null) {
			for(int j=0;j<slide.getElements().size();j++) {
				if(slide.getElements().get(j)==presElement) {
					slide.getElements().remove(j);
					slideElements.get(slideNumber).remove(j);
					setPositionOfElementRects(slideNumber);
				}
			}
		}
	}
	
	
	/*
	 * Overided methods:
	 */
	public void setMainAndSecondaryColor(Color main, Color secondary) {
		this.main= secondary;
		this.secondary=main;
		scrollPane.setBackground(secondary);
	}
	public void setMarginBounds(int r, int t, int l, int b) {
		super.setMarginBounds(r, t, l, b);
		int width= this.getWidth()- (r+l);
		int height= this.getHeight()-(t+b);
		int xPos= r;
		int yPos= t;
		upperPanel.setBounds(xPos,yPos+curvatureRadius/2,width,height- (curvatureRadius+(curvatureRadius/2)));
		lowerPanel.setBounds(xPos, (height+yPos)-curvatureRadius, width, (curvatureRadius));

		int buttonSize= lowerPanel.getHeight()/2;
		addElement.setBounds((width/4)-(buttonSize/2),buttonSize/2,buttonSize, buttonSize);
		deleteElement.setBounds(((3*width)/4)-(buttonSize/2),buttonSize/2,buttonSize, buttonSize);
		scrollPane.setBounds(0, 0, upperPanel.getWidth(), upperPanel.getHeight());
	}
	//Setters and getters:
	public CustomMediaBox getMediaBox() {
		return mediaPanel;
	}
	public CustomToolBar getToolBar() {
		return toolBar;
	}
	public JButton getAddElementButton() {
		return addElement;
	}
	public JButton getDeleteElementButton() {
		return deleteElement;
	}
}

class ElementRect extends UploadSceneComponent implements ComponentInterface{
	Slide slide;
	PresElement presElement;
	String elementType;
	String rectTitle;
	boolean isSelected;

	/*
	 *  Title is 'element type' + 'current number of elements of that type on the slide
	 *  ex. Graphic 3
	 */
	public ElementRect(Slide slide, PresElement presElement) {
		this.presElement= presElement;
		slide.add(presElement);
		int numberOfTypeElements=0;
		for(PresElement e:slide.getElements()) {
			if(e.getType().equalsIgnoreCase(presElement.getType())) {
				numberOfTypeElements++;
			}
		}
		rectTitle= presElement.getType()+" "+numberOfTypeElements;
	}
	/*
	 *  Overided Methods:
	 */
	public void paint(Graphics g) {
		Graphics2D g2= (Graphics2D)g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		if(isSelected==true) {
			g2.setColor(getColorDarkAdjustedOpacity(100));
		}else {
			g2.setColor(colorDark);
		}
		g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), curvatureRadius, curvatureRadius);
		g2.setColor(textColor);
		g2.setFont(getFont(curvatureRadius));
		g2.drawString(rectTitle, curvatureRadius, this.getHeight()/2);
		g2.dispose();
		super.paint(g);
	}
	/*
	 * Setters and getters
	 */
	public void setSelected(boolean bool) {
		isSelected=bool;
	}
	public boolean isSelected() {
		return isSelected;
	}
	public PresElement getElement() {
		return presElement;
	}
}





