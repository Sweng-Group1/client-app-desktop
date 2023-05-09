package sweng.group.one.client_app_desktop.presentation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
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
import org.w3c.dom.Element;
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
	
	public Presentation() {
		super();
		this.slides = new ArrayList<>();
		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				resizeCurrentSlide();
			}
		});
		currentSlideNo = 0;
	}
	
	public Presentation(List<Slide> slides){
		this();
		
		for (Slide s : slides) {
			addSlide(s);
		}
		
		showCurrentSlide();
	}
	
	public Presentation(File xml) throws SAXException, IOException, ParserConfigurationException {
		this();
		
		//validate the XML file with the schema
		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Source schemaSource = new StreamSource(new File(XML_SCHEMA_PATH));
        Schema schema = schemaFactory.newSchema(schemaSource);

        Validator validator = schema.newValidator();
        validator.validate(new StreamSource(xml));
        //TODO: Handle the error thrown when the XML is not valid
		
        
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
						//TODO: Complete this
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
				System.out.println("New Slide");
				
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
							case "shadowRadius":
							case "shadowDx":
							case "shadowDy":
							case "fontSize":
							case "radius":
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
								getCurrentSlide(),
								(URL) varDict.get("url"));
						getCurrentSlide().add(imageViewer);
						break;
					case "video":
						pos = new Point((Integer) varDict.get("xCoordinate"), (Integer)varDict.get("yCoordinate"));
						if (varDict.get("loops") != null) {
							loops = (boolean) varDict.get("loops");
						}
						VideoPlayer videoPlayer = new VideoPlayer(pos,
								(Integer) varDict.get("width"),
								(Integer) varDict.get("height"),
								getCurrentSlide(),
								(URL) varDict.get("url"),
								loops);
						getCurrentSlide().add(videoPlayer);
						videoPlayer.displayElement();
						break; 
					case "audio":
						pos = new Point((Integer) varDict.get("xCoordinate"), (Integer)varDict.get("yCoordinate"));
						if (varDict.get("loops") != null) {
							loops = (boolean) varDict.get("loops");
						}
						AudioPlayer audioPlayer = new AudioPlayer(pos,
								(Integer) varDict.get("width"),
								(Integer) varDict.get("height"),
								(float) varDict.get("timeOnScreen"),
								getCurrentSlide(),
								(URL) varDict.get("url"),
								loops);
						getCurrentSlide().add(audioPlayer);
						break;
					case "rectangle":
						pos = new Point((Integer) varDict.get("xCoordinate"), (Integer)varDict.get("yCoordinate"));
						border = new Border((Color) varDict.get("borderColour"), (Integer) varDict.get("borderWidth"));
						shadow = new Shadow((Color) varDict.get("shadowColour"), (Integer) varDict.get("shadowDx"), (Integer) varDict.get("shadowDy"), (Integer) varDict.get("shadowRadius"));
						Rectangle rectangle = new Rectangle(pos,
								(Integer) varDict.get("width"),
								(Integer) varDict.get("height"),
								(float) varDict.get("timeOnScreen"),
								getCurrentSlide(),
								(Color) varDict.get("colour"),
								border,
								shadow);
						getCurrentSlide().add(rectangle);
						break;
					case "circle":
						pos = new Point((Integer) varDict.get("xCoordinate"), (Integer)varDict.get("yCoordinate"));
						border = new Border((Color) varDict.get("borderColour"), (Integer) varDict.get("borderWidth"));
						shadow = new Shadow((Color) varDict.get("shadowColour"), (Integer) varDict.get("shadowDx"), (Integer) varDict.get("shadowDy"), (Integer) varDict.get("shadowRadius"));
						Circle circle = new Circle(pos,
								(Integer) varDict.get("radius"),
								(float) varDict.get("timeOnScreen"),
								getCurrentSlide(),
								(Color) varDict.get("colour"),
								border,
								shadow);
						getCurrentSlide().add(circle);
						break;
					case "line":
						pos = new Point((Integer) varDict.get("xCoordinate"), (Integer)varDict.get("yCoordinate"));
						Point from = new Point((Integer) varDict.get("fromX"), (Integer) varDict.get("fromY"));
						Point to = new Point((Integer) varDict.get("toX"), (Integer) varDict.get("toY"));
						Line line = new Line(pos,
								(Integer) varDict.get("width"),
								(Integer) varDict.get("height"),
								(float) varDict.get("timeOnScreen"),
								getCurrentSlide(),
								(Integer) varDict.get("thickness"),
								from,
								to,
								(Color) varDict.get("colour"));
						getCurrentSlide().add(line);
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
								getCurrentSlide());
						getCurrentSlide().add(text);
						break;
					}
				}
			}
			
        }
	}
	
	public void addSlide(Slide newSlide) {
		slides.add(newSlide);
		this.add(newSlide);
		newSlide.setVisible(false);
	}
	
	private void showCurrentSlide() {
		if (slides.isEmpty()) {
			return;
		}
		
		Slide desiredSlide = getCurrentSlide();
		for (Slide slide:slides) {
			slide.setVisible(slide == desiredSlide);
		}
		desiredSlide.displaySlide();
		resizeCurrentSlide();
		desiredSlide.validate();
	}
	
	public void nextSlide() {
		if (slides.isEmpty()) {
			return;
		}
		
		int maxSlide = slides.size()-1;
		currentSlideNo++;
		// Using a modulo here causes a div/0 if slides.size = 1
		// We're just gonna loop around anyway, so just use a ternary
		currentSlideNo = currentSlideNo >= maxSlide ? 0 : currentSlideNo;
		showCurrentSlide();
	}
	
	public void prevSlide() {
		if (slides.isEmpty()) {
			return;
		}
		
		int maxSlide = slides.size()-1;
		currentSlideNo--;
		currentSlideNo = currentSlideNo > 0 ? currentSlideNo : maxSlide;
		showCurrentSlide();
	}
	
	public Slide getCurrentSlide() {
		return slides.isEmpty() ? null : slides.get(currentSlideNo);
	}
	
	public List<Slide> getSlides() {
		return slides;
	}
	
	
	/*
	 * Resize currentSlide to keep a fixed aspect ratio with reference to
	 * the size of the Presentation
	 */
	private void resizeCurrentSlide() {
		if (slides.isEmpty()) {
			return;
		}
		
		Slide currentSlide = getCurrentSlide();
		Dimension preferredLayout = currentSlide.preferredLayoutSize(this);
		currentSlide.setPreferredSize(preferredLayout);
	}
}
