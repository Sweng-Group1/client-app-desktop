package sweng.group.one.client_app_desktop.presentation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import sweng.group.one.client_app_desktop.graphics.Border;
import sweng.group.one.client_app_desktop.graphics.Circle;
import sweng.group.one.client_app_desktop.graphics.Line;
import sweng.group.one.client_app_desktop.graphics.Rectangle;
import sweng.group.one.client_app_desktop.graphics.Shadow;
import sweng.group.one.client_app_desktop.media.AudioPlayer;
import sweng.group.one.client_app_desktop.media.ImageViewer;
import sweng.group.one.client_app_desktop.media.VideoPlayer;
import sweng.group.one.client_app_desktop.text.TextElement;

/**
 * @author flt515
 *
 */
@SuppressWarnings("serial")
public class Presentation extends JPanel {
	
	private static final String XML_SCHEMA_PATH = "assets/xml/standard.xsd";
	
	private ArrayList<Slide> slides;
	private int currentSlideNo;
	private int numSlides;
	private String title;
	private String author;
	private LocalDate date;
	private boolean isMouseHovered;
	
	/**
	 * Creates a new empty Presentation object.
	 */
	public Presentation() {
		super();
		this.slides = new ArrayList<>();
		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				resizeCurrentSlide();
			}
		});
		
		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseEntered(MouseEvent e) {
				isMouseHovered = true;
				repaint();
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getX() > getWidth()/2) {
					nextSlide();
				}
				else {
					prevSlide();
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				isMouseHovered = false;
				repaint();
			}
			
		});
		numSlides = 0;
		currentSlideNo = 0;
		title = "";
		author = "";
		date = LocalDate.EPOCH;
	}
	
	/**
	 * Creates a new Presentation object with the given list of slides.
	 *
	 * @param slides the list of slides for the presentation
	 */
	public Presentation(List<Slide> slides){
		this();
		
		for (Slide s : slides) {
			addSlide(s);
		}
		
		showCurrentSlide();
	}
	
	/**
	 * Creates a new Presentation object by parsing the given XML file.
	 *
	 * @param xml the XML file representing the presentation
	 * @throws SAXException                 if there is an error during XML parsing
	 * @throws IOException                  if an I/O error occurs while reading the XML file
	 * @throws ParserConfigurationException if a parser cannot be created with the specified configuration
	 */
	public Presentation(File xml) throws SAXException, IOException, ParserConfigurationException {
		this();
		
		//validate the XML file with the schema
		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Source schemaSource = new StreamSource(new File(XML_SCHEMA_PATH));
        Schema schema = schemaFactory.newSchema(schemaSource);

        Validator validator = schema.newValidator();
        validator.validate(new StreamSource(xml));
        
        //load xml file as a document
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(xml);

        NodeList presentation = document.getDocumentElement().getChildNodes();
        for (int p = 0; p < presentation.getLength(); p++) {
			Node node = presentation.item(p);
			
			String nodeName = node.getNodeName();
			
			switch (nodeName) {
			
			case "info":
				NodeList info = node.getChildNodes();
				for(int i = 0; i < info.getLength(); i++) {
					Node infoItem = info.item(i);
					String infoItemName = infoItem.getNodeName();
					
					switch (infoItemName) {
					case "title":
						this.title = infoItem.getTextContent();
						break;
					case "author":
						this.author = infoItem.getTextContent();
						break;
					case "date":
						//is in format YYYY-MM-DDD
						this.date = LocalDate.parse(infoItem.getTextContent());
						break;
					default:
						break;
					}
				}
				break;
				
			case "slide":
				NodeList slideXML = node.getChildNodes();
				
				int width = Integer.parseInt(node.getAttributes().getNamedItem("width").getNodeValue());
				int height = Integer.parseInt(node.getAttributes().getNamedItem("height").getNodeValue());
				
				Slide newSlide = new Slide(width, height);
				this.addSlide(newSlide);
				
				for(int s = 0; s < slideXML.getLength(); s++) {
					final String[] varNames = {"width", 
												"height", 
												"xCoordinate", 
												"yCoordinate",
												"colour",
												"borderWidth",
												"borderColour",
												"shadowRadius",
												"shadowDx",
												"shadowDy",
												"shadowColour",
												"timeOnScreen",
												"fontName",
												"fontSize",
												"url",
												"loop",
												"rotation",
												"delay",
												"radius",
												"thickness",
												"fromX",
												"fromY",
												"toX", 
												"toY"};
					
					Map<String, Object> varDict = new HashMap<String, Object>();
					
					//set float values to 0 as floats cannot be null
					varDict.put("delay", 0.0f);
					varDict.put("timeOnScreen", 0.0f);
					varDict.put("rotation", 0.0f);
					
					Node slideItem = slideXML.item(s);
					String slideItemName = slideItem.getNodeName();
					
					//skips non essential items e.g text elements
					if (slideItemName.contains("#")) {
						continue;
					}
					
					NamedNodeMap itemAttributes = slideItem.getAttributes();
					
					for (String var : varNames) {
						Node attribute = itemAttributes.getNamedItem(var);
						if(attribute != null) {
							Object value = attribute.getNodeValue();
							
							//format and cast values to correct types
							switch (var) {
							case "timeOnScreen":
								String t = (String)value;
								String[] time = t.split(":");
								
								value = Float.parseFloat(time[0]) * 360 //hrs to sec
								+ Float.parseFloat(time[1]) * 60 //min to sec
								+ Float.parseFloat(time[2]);
								break;
							
							//cast these to integers
							case "width":
							case "height":
							case "xCoordinate":
							case "yCoordinate":
							case "borderWidth":
							case "thickness":
							case "shadowRadius":
							case "shadowDx":
							case "shadowDy":
							case "fontSize":
							case "radius":
							case "fromX":
							case "fromY":
							case "toX":
							case "toY":
							case "rotation":
								value = Integer.parseInt((String) value);
								break;
							
							//cast these to color
							case "colour":
							case "borderColour":
							case "shadowColour":
								String c = (String)value;
								long rgba = Long.decode(c);
								
								int r = (int) (rgba>>24);
								int g = (int) ((rgba & 0x00FF0000)>>16);
								int b = (int) ((rgba & 0x0000FF00)>>8);
								int a = (int) (rgba & 0x000000FF);
								
								value = new Color(r, g, b, a);
								break;
								
							//cast to Boolean
							case "loop":
								value = Boolean.parseBoolean((String) value);
								break;
								
							//cast to float
							case "delay":
								value = Float.parseFloat((String) value);
								break;								
							
							//cast to URL
							case "url":
								try {
									value = new URL((String)value);
								} catch (MalformedURLException e) {
									value = null;
								}
								break;
							}
							
							varDict.put(var, value);
						}
					}
					
					
					Point pos;
					boolean loops = false;
					Border border;
					Shadow shadow;
					
					switch (slideItemName) {
					case "image":
						pos = new Point((Integer) varDict.get("xCoordinate"), (Integer)varDict.get("yCoordinate"));
						ImageViewer imageViewer = new ImageViewer(pos,
								(Integer) varDict.get("width"),
								(Integer) varDict.get("height"),
								(float) varDict.get("timeOnScreen"),
								(Integer) varDict.get("rotation"),
								(float) varDict.get("delay"),
								newSlide,
								(URL) varDict.get("url"));
						newSlide.add(imageViewer);
						break;
					case "video":
						pos = new Point((Integer) varDict.get("xCoordinate"), (Integer)varDict.get("yCoordinate"));
						if (varDict.get("loops") != null) {
							loops = (boolean) varDict.get("loops");
						}
						VideoPlayer videoPlayer = new VideoPlayer(pos,
								(Integer) varDict.get("width"),
								(Integer) varDict.get("height"),
								newSlide,
								(URL) varDict.get("url"),
								loops);
						newSlide.add(videoPlayer);
						break; 
					case "audio":
						pos = new Point((Integer) varDict.get("xCoordinate"), (Integer)varDict.get("yCoordinate"));
						if (varDict.get("loops") != null) {
							loops = (boolean) varDict.get("loops");
						}
						AudioPlayer audioPlayer = new AudioPlayer(pos,
								50,
								50,
								(float) varDict.get("timeOnScreen"),
								newSlide,
								(URL) varDict.get("url"),
								loops);
						newSlide.add(audioPlayer);
						break;
					case "rectangle":
						pos = new Point((Integer) varDict.get("xCoordinate"), (Integer)varDict.get("yCoordinate"));
						border = new Border((Color) varDict.get("borderColour"), (Integer) varDict.get("borderWidth"));
						shadow = new Shadow((Color) varDict.get("shadowColour"), (Integer) varDict.get("shadowDx"), (Integer) varDict.get("shadowDy"), (Integer) varDict.get("shadowRadius"));
						Rectangle rectangle = new Rectangle(pos,
								(Integer) varDict.get("width"),
								(Integer) varDict.get("height"),
								(float) varDict.get("timeOnScreen"),
								newSlide,
								(Color) varDict.get("colour"),
								border,
								shadow);
						newSlide.add(rectangle);
						break;
					case "circle":
						pos = new Point((Integer) varDict.get("xCoordinate"), (Integer)varDict.get("yCoordinate"));
						border = new Border((Color) varDict.get("borderColour"), (Integer) varDict.get("borderWidth"));
						shadow = new Shadow((Color) varDict.get("shadowColour"), (Integer) varDict.get("shadowDx"), (Integer) varDict.get("shadowDy"), (Integer) varDict.get("shadowRadius"));
						Circle circle = new Circle(pos,
								(Integer) varDict.get("radius"),
								(float) varDict.get("timeOnScreen"),
								newSlide,
								(Color) varDict.get("colour"),
								border,
								shadow);
						newSlide.add(circle);
						break;
					case "line":
						Point from = new Point((Integer) varDict.get("fromX"), (Integer) varDict.get("fromY"));
						Point to = new Point((Integer) varDict.get("toX"), (Integer) varDict.get("toY"));
						pos = new Point(Math.min(from.x, to.x), Math.min(from.y, to.y));
						Line line = new Line(pos,
								to.x - from.x,
								to.y - from.y,
								(float) varDict.get("timeOnScreen"),
								newSlide,
								(Integer) varDict.get("thickness"),
								from,
								to,
								(Color) varDict.get("colour"));
						newSlide.add(line);
						break;
					case "text":
						pos = new Point((Integer) varDict.get("xCoordinate"), (Integer) varDict.get("yCoordinate"));				
						TextElement text = new TextElement(slideItem.getTextContent(),
								(String) varDict.get("fontName"),
								(Integer) varDict.get("fontSize"),
								(Color) varDict.get("colour"),
								(float) varDict.get("timeOnScreen"),
								pos,
								(Integer) varDict.get("width"),
								(Integer) varDict.get("height"),
								newSlide);
						newSlide.add(text);
						break;
					}
				}
			}
        }
        showCurrentSlide();
	}
	
	@Override
	public void paint(Graphics g) {
	    super.paint(g);
	   
		// Draw arrows if the mouse is hovered
		if (isMouseHovered) {
			Slide current = slides.get(currentSlideNo);
		    int width = getWidth();
		    int height = getHeight();
		    int arrowSizeX = width/3;
		    int arrowSizeY = height/5;
		    
		    Graphics2D g2d = (Graphics2D) g.create();
		    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		    g2d.setColor(new Color(0.0f, 0.0f, 0.0f, 0.5f));
		
		    //Draw left arrow
		    int[] leftArrowXPoints = {arrowSizeX, arrowSizeX, 0};
	        int[] leftArrowYPoints = {(height / 2) - arrowSizeY, (height / 2) + arrowSizeY, height / 2};
	        g2d.fillPolygon(leftArrowXPoints, leftArrowYPoints, 3);
	
	        //Draw right arrow
	        int[] rightArrowXPoints = {width - arrowSizeX, width - arrowSizeX, width};
	        int[] rightArrowYPoints = {(height / 2) - arrowSizeY, (height / 2) + arrowSizeY, height / 2};
	        g2d.fillPolygon(rightArrowXPoints, rightArrowYPoints, 3);
	        
	        //Draw Text Box
	        g2d.setColor(new Color(1.0f, 1.0f, 1.0f, 0.5f));
	        int fontSize = g2d.getFont().getSize();
	        g2d.fillRect(0, 0, width, fontSize*4);
	        g2d.setColor(Color.black);
	        
	        //Draw Text
	        g2d.drawString(title, 10, 20);
	        g2d.drawString("Author: " + author, 10, 20 + fontSize);
	        g2d.drawString("Date: " + date, 10, 20 + fontSize*2);
	        
	        g2d.dispose();
	    }
	}
	
	/**
	 * Adds a new slide to the presentation.
	 *
	 * @param newSlide the slide to be added
	 */
	public void addSlide(Slide newSlide) {
		slides.add(newSlide);
		this.add(newSlide);
		numSlides++;
		newSlide.setVisible(false);
	}
	
	/**
	 * Shows the current slide and hides other slides.
	 * Resizes the current slide to maintain a fixed aspect ratio relative to the presentation size.
	 */
	public void showCurrentSlide() {
		if (slides.isEmpty()) {
			return;
		}
		
		Slide desiredSlide = getCurrentSlide();
		for (Slide slide:slides) {
			slide.displaySlide(slide == desiredSlide);
		}
		resizeCurrentSlide();
		this.validate();
	}
	
	/**
	 * Moves to the next slide in the presentation.
	 * If there are no slides, this method does nothing.
	 */
	public void nextSlide() {
		if (slides.isEmpty()) {
			return;
		}
		
		int maxSlide = slides.size()-1;
		currentSlideNo++;
		currentSlideNo = currentSlideNo > maxSlide ? 0 : currentSlideNo;
		showCurrentSlide();
	}
	
	/**
	 * Moves to the previous slide in the presentation.
	 * If there are no slides, this method does nothing.
	 */
	public void prevSlide() {
		if (slides.isEmpty()) {
			return;
		}
		
		int maxSlide = slides.size()-1;
		currentSlideNo--;
		currentSlideNo = currentSlideNo >= 0 ? currentSlideNo : maxSlide;
		showCurrentSlide();
	}
	
	/**
	 * Returns the current slide in the presentation.
	 * Returns null if there are no slides.
	 *
	 * @return the current slide
	 */
	public Slide getCurrentSlide() {
		return slides.isEmpty() ? null : slides.get(currentSlideNo);
	}
	
	/**
	 * Returns a list of all slides in the presentation.
	 *
	 * @return the list of slides
	 */
	public List<Slide> getSlides() {
		return slides;
	}
	
	
	/*
	 * Resizes the current slide to maintain a fixed aspect ratio with reference to the size of the Presentation.
	 */
	private void resizeCurrentSlide() {
		if (slides.isEmpty()) {
			return;
		}
		
		Slide currentSlide = getCurrentSlide();
		Dimension preferredLayout = currentSlide.preferredLayoutSize(this);
		currentSlide.setPreferredSize(preferredLayout);
		currentSlide.layoutContainer(this);
	}
	
	@Override
	public Dimension getPreferredSize() {
        Slide currentSlide = getCurrentSlide();
		return currentSlide.preferredLayoutSize(this);
    }
}
