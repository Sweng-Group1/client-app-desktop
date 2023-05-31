package sweng.group.one.client_app_desktop.presentation;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.Attributes;

import javax.imageio.ImageIO;
import javax.print.attribute.Attribute;
import javax.swing.JPanel;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;
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

import sweng.group.one.client_app_desktop.graphics.Circle;
import sweng.group.one.client_app_desktop.graphics.Rectangle;
import sweng.group.one.client_app_desktop.media.GraphicsElement;
import sweng.group.one.client_app_desktop.media.ImageElement;
import sweng.group.one.client_app_desktop.media.TextElement;
import sweng.group.one.client_app_desktop.media.VideoPlayer;

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
					
					
					
					
					switch (slideItemName) {
					case "image":
						System.out.println(varDict.get("url") + " " + 
											varDict.get("width") + " " +
											varDict.get("height") + " " +
											varDict.get("rotation") + " " +
											varDict.get("delay") + " " +
											varDict.get("xCoordinate") + " " +
											varDict.get("yCoordinate") + " " +
											varDict.get("timeOnScreen"));
						break;
					case "video":
						System.out.println(varDict.get("url") + " " + 
											varDict.get("width") + " " +
											varDict.get("height") + " " +
											varDict.get("xCoordinate") + " " +
											varDict.get("yCoordinate") + " " +
											varDict.get("loops"));
						break; 
					case "audio":
						System.out.println(varDict.get("url") + " " + 
								varDict.get("width") + " " +
								varDict.get("height") + " " +
								varDict.get("xCoordinate") + " " +
								varDict.get("yCoordinate") + " " +
								varDict.get("loops"));
						break;
					case "rectangle":
						System.out.println(varDict.get("borderColour") + " " +
											varDict.get("borderWidth"));
						System.out.println(varDict.get("shadowColour") + " " +
											varDict.get("shadowDx") + " " +
											varDict.get("shadowDy") + " " +
											varDict.get("shadowRadius"));
						System.out.println(varDict.get("width") + " " +
										varDict.get("height") + " " +
										varDict.get("xCoordinate") + " " +
										varDict.get("yCoordinate") + " " +
										varDict.get("colour") + " " +
										varDict.get("timeOnScreen"));
						break;
					case "circle":
						break;
					case "line":
						break;
					case "text":
						break;
					}
				}
			}
			
        }
	}
	public Document getXMLDoc(File fileChoose) throws SAXException, ParserConfigurationException, IOException {
		Document doc;
		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Source schemaSource = new StreamSource(new File(XML_SCHEMA_PATH));
        Schema schema = schemaFactory.newSchema(schemaSource);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        doc = builder.newDocument();
        
        /*
         *  Presentation: author, date, time, location
         */
        Element presentation = doc.createElement("presentation");
        doc.appendChild(presentation);
        // Author 
        Element author = doc.createElement("author");   
        author.setAttribute("sm2208", "Sophie");
        presentation.appendChild(author);
      
     
        /*
         *  Slides + elements:
         */
        for(int i=0;i<slides.size();i++) {
        	Element slide= doc.createElement("slide");
        	slide.setAttribute("width", Integer.toString(slides.get(i).getPointWidth()));
        	slide.setAttribute("height", Integer.toString(slides.get(i).getPointHeight()));
        	slide.setAttribute("title", Integer.toString(i+1));
        	presentation.appendChild(slide);
        	
        	ArrayList<PresElement>elements= slides.get(i).getElements();
        	for(int j=0;j<elements.size();j++) {
        		PresElement p= elements.get(j);
        		Element el= null;
        		switch (p.getType()){
        		case "TEXT":
        			TextElement t= (TextElement)p;
        			el= doc.createElement("text");
        			el.setAttribute("text",t.getText());
        			el.setAttribute("fontName",t.getFontName());
        			el.setAttribute("fontSize","#"+Integer.toString(t.getFontSize()));
        			el.setAttribute("colour",Integer.toHexString(t.getColour().getRGB()));
        			el.setAttribute("xCordinate",Integer.toString(t.getPosPoint().x));
        			el.setAttribute("yCoordinate",Integer.toString(t.getPosPoint().y));
        			el.setAttribute("width",Integer.toString(t.getWidth()));
        			el.setAttribute("height",Integer.toString(t.getHeight()));
        			break;
        		case "CIRCLE":
        			Circle c= (Circle)p;		
        			el= doc.createElement("circle");
        			el.setAttribute("radius",Integer.toString(c.getRadius()));
        			el.setAttribute("xCordinate",Integer.toString(c.getPosPoint().x));
        			el.setAttribute("yCoordinate",Integer.toString(c.getPosPoint().y));
        			el.setAttribute("colour",Integer.toHexString(c.getColour().getRGB()));
        			if(c.getBorder()!=null) {
        			el.setAttribute("borderColour",Integer.toHexString(c.getBorder().getBorderColour().getRGB()));
        			el.setAttribute("borderWidth",Integer.toString(c.getBorder().getBorderWidth()));
        			}if(c.getShadow()!=null) {
        			el.setAttribute("shadowRadius",Integer.toString(c.getShadow().getShadowBlurRadius()));
        			el.setAttribute("shadowDx",Float.toString(c.getShadow().getShadowDx()));
        			el.setAttribute("shadowDy",Float.toString(c.getShadow().getShadowDy()));
        			el.setAttribute("shadowColour",Integer.toHexString(c.getShadow().getShadowColour().getRGB()));
        			}
        			el.setAttribute("timeOnScreen",Float.toString(c.getDuration()));
        			break;
        		case "RECTANGLE":
        			Rectangle r= (Rectangle)p;		
        			el= doc.createElement("rectangle");
        			el.setAttribute("width",Integer.toString((int) r.getWidth()));
        			el.setAttribute("height",Integer.toString((int) r.getHeight()));
        			el.setAttribute("xCoordinate",Integer.toString(r.getPosPoint().x));
        			el.setAttribute("yCoordinate",Integer.toString(r.getPosPoint().y));
        			el.setAttribute("colour",Integer.toHexString(r.getFillColor().getRGB()));
        			el.setAttribute("borderColour",Integer.toHexString(r.getBorder().getBorderColour().getRGB()));
        			el.setAttribute("borderWidth",Integer.toString(r.getBorder().getBorderWidth()));
        			el.setAttribute("shadowRadius",Integer.toString(r.getShadow().getShadowBlurRadius()));
        			el.setAttribute("shadowDx",Float.toString(r.getShadow().getShadowDx()));
        			el.setAttribute("shadowDy",Float.toString(r.getShadow().getShadowDy()));
        			el.setAttribute("shadowColour",Integer.toHexString(r.getShadow().getShadowColour().getRGB()));
        			el.setAttribute("timeOnScreen",Float.toString(r.getDuration()));
        			break;
        		case "IMAGE":
        			ImageElement im= (ImageElement)p;
        			el= doc.createElement("image");
        			
        			BufferedImage imgIm = im.getImage();
        			 ByteArrayOutputStream baosIm = new ByteArrayOutputStream();
        			 ImageIO.write(imgIm, "png", baosIm);    
        			 baosIm.flush();
        			 String encodedImageIm;
        			 
					try {
						encodedImageIm = Base64.getEncoder().encodeToString(baosIm.toByteArray());
						 baosIm.close(); // should be inside a finally block
	        			// el.setTextContent(encodedImageIm); // store it inside node
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					ImageElement imEl= (ImageElement)p;
					el.setAttribute("url", String.valueOf(imEl.getURL()));
        			el.setAttribute("xCordinate",Integer.toString(imEl.getPosPoint().x));
        			el.setAttribute("yCoordinate",Integer.toString(imEl.getPosPoint().y));
        			el.setAttribute("width",Integer.toString(imEl.getWidth()));
        			el.setAttribute("height",Integer.toString(imEl.getHeight()));
        			break;
        		case "GRAPHIC":
        			GraphicsElement g= (GraphicsElement)p;
        			el= doc.createElement("graphic");
        			
        			BufferedImage img = g.getCurrentImage(); 
        			 ByteArrayOutputStream baos = new ByteArrayOutputStream();
        			 ImageIO.write(img, "png", baos);    
        			 baos.flush();
        			 String encodedImage;
        			 
					try {
						encodedImage = Base64.getEncoder().encodeToString(baos.toByteArray());
						 baos.close(); // should be inside a finally block
	        			 el.setTextContent(encodedImage); // store it inside node
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
        		
        			el.setAttribute("xCordinate",Integer.toString(g.getPosPoint().x));
        			el.setAttribute("yCoordinate",Integer.toString(g.getPosPoint().y));
        			el.setAttribute("width",Integer.toString(g.getWidth()));
        			el.setAttribute("height",Integer.toString(g.getHeight()));
        			break;
        		case "AUDIO":
        			break;
        		case "VIDEO":
        			VideoPlayer v= (VideoPlayer)p;
        			el= doc.createElement("video");
        			el.setAttribute("url",v.getLocalPath());
        			el.setAttribute("xCordinate",Integer.toString(v.getPosPoint().x));
        			el.setAttribute("yCoordinate",Integer.toString(v.getPosPoint().y));
        			el.setAttribute("width",Integer.toString(v.getWidth()));
        			el.setAttribute("height",Integer.toString(v.getHeight()));
        			break;
        		default:
        			break;
        		}
        		slide.appendChild(el);
        	
        	
        	}
        }
        
        return doc;
		
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
	public void setSlides(ArrayList<Slide>slides) {
		this.slides=slides;
	}
	
}
