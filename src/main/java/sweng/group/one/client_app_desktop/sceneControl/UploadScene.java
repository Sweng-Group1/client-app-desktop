package sweng.group.one.client_app_desktop.sceneControl;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.xml.sax.SAXException;

import sweng.group.one.client_app_desktop.presentation.Presentation;
import sweng.group.one.client_app_desktop.uiElements.CustomDescriptionBox;
import sweng.group.one.client_app_desktop.uiElements.CustomMediaBox;
import sweng.group.one.client_app_desktop.uiElements.CustomTabPanel;
import sweng.group.one.client_app_desktop.uiElements.CustomTagBox;
import sweng.group.one.client_app_desktop.uiElements.CustomTimeProgressBar;
import sweng.group.one.client_app_desktop.uiElements.CustomToolBar;
import sweng.group.one.client_app_desktop.uiElements.ElementPropertiesPanel;
import sweng.group.one.client_app_desktop.uiElements.ElementsPanel;
import sweng.group.one.client_app_desktop.uiElements.UploadSceneComponent;

/**
 * @author sophiemaw
 *Main Upload scene:
 *Methods on set up:
 * 	1. setBounds()
 * Methods on close:
 *  1. getPresentation()
 *  2. getTags()
 *  2. getDescription()
 *  
 *  Implemented:
 *   allows images To be dropped onto slide, and creates ImageElement
 *   Not implemented:
 *    videos+audio 
 *    
 *  Notes: I havn't implemented a layout, so ive created a UploadSceneComponent method
 *  		setMarginBounds that is called when you setBounds, which sets bounds of children 
 *  		components and then changes their gapWidths
 *  *I will add a detailed layout diagram of upload scene so marginBounds is easier to understand 
 *  and easier to change to layout manager*
 */
public class UploadScene extends UploadSceneComponent implements ComponentInterface{
	
	UploadScene uploadScene= this;
	JButton confirmButton;
	JButton closeButton;
	
	CustomTabPanel tabPane;
	CustomToolBar toolBar;
	ElementsPanel graphicsBox;
	CustomMediaBox mediaBox;
	CustomTagBox tagBox;
	ElementPropertiesPanel propertiesBox;
	CustomDescriptionBox descriptionBox;
	CustomTimeProgressBar timeBar;
	
	ArrayList<UploadSceneComponent>components;
	boolean elementIsBeingMoved;
	MouseListener movingElementToListener;
	
	JFileChooser fileChooser;
	
	public UploadScene() {
		super();
		this.setLayout(null);
		this.main= colorDark;
		this.secondary= colorLight;
	
		
		/*
		 *  Initialising components 
		 */
		components= new ArrayList<UploadSceneComponent>();

		// Description box
		descriptionBox= new CustomDescriptionBox();
		components.add(descriptionBox);
		// Tags box
		tagBox = new CustomTagBox();
		components.add(tagBox);
		// PropertiesBox
		propertiesBox= new ElementPropertiesPanel();
		components.add(propertiesBox);
		// Graphics Box
		graphicsBox= new ElementsPanel(propertiesBox);
		components.add(graphicsBox);
		// Media box
		mediaBox= graphicsBox.getMediaBox();
		components.add(mediaBox);
		// Tool bar
		toolBar= graphicsBox.getToolBar();
		components.add(toolBar);
		// Progress time bar
		timeBar= new CustomTimeProgressBar();
		components.add(timeBar);
		// Tab pane
		tabPane= new CustomTabPanel(graphicsBox,timeBar,toolBar);
		components.add(tabPane);
		
		
		
		for(UploadSceneComponent c:components) {
			this.add(c);
		}
		
		
	
		/* 
		 *  Initialising moving media
		 */
		elementIsBeingMoved=false;
		mediaBox.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if((mediaBox.isIconSelected()==true)&&(elementIsBeingMoved==false)) {
					Image elementIcon= mediaBox.getSelectedIcon();
					Cursor c = Toolkit.getDefaultToolkit().createCustomCursor(elementIcon , new Point( 
					           0,0), "img");
					uploadScene.setCursor (c);
					elementIsBeingMoved=true;
					tabPane.getCurrentComponent().addMouseListener(movingElementToListener);
					tabPane.getCurrentSlide().addMouseListener(movingElementToListener);
					if(tabPane.getCurrentSlide().getElements().size()>0) {
						tabPane.getCurrentSlide().getElements().get(tabPane.getCurrentSlide().getElements().size()-1).getComponent().addMouseListener(movingElementToListener);
					}
					//toolBar.resetMode();
				}else if (elementIsBeingMoved==true) {
					mediaBox.turnOffMediaSelected();
					uploadScene.setCursor(Cursor.getDefaultCursor());
					elementIsBeingMoved=false;
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
		movingElementToListener= new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				/*
				 *  Call a method in tabPane to get coordinates of mouse realease
				 *  Then call addImageElement in graphicsBox 
				 */
				System.out.println("TRYING TO PLACE");
				if(elementIsBeingMoved==true) {
					if(mediaBox.getSelectedMediaType()=="IMAGE") {
						BufferedImage image= mediaBox.getSelectedMediaImage();
						graphicsBox.addImageElement(mediaBox.getSelectedFile(),image,e.getPoint(),image.getWidth(),image.getHeight(),null,0,mediaBox.getSelectedURL());
						
					}
					mediaBox.turnOffMediaSelected();
					uploadScene.setCursor(Cursor.getDefaultCursor());
					elementIsBeingMoved=false;
					System.out.println("Element is placed!");
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
		toolBar.getConfirmButton().addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				 
			}

			@Override
			public void mousePressed(MouseEvent e) {
				fileChooser= new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter ("XML-File","xml");
				fileChooser.setFileFilter((javax.swing.filechooser.FileFilter) filter);
				
				int userSelection = fileChooser.showSaveDialog(fileChooser.getParent());
				if (userSelection == JFileChooser.APPROVE_OPTION) {
				    File fileToSave = fileChooser.getSelectedFile();
				    try {
						saveToXL(fileToSave);
					} catch (SAXException | ParserConfigurationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (TransformerException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				   
				}
				
				
				//fileChooser.getCurrentDirectory().renameTo(graphicsBox.getPresentation().getXML(TOOL_TIP_TEXT_KEY));
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

	
	//GETTERS 
	/*
	 *  So the mouseListeners for these could be created in this scene, so when 
	 *  confirm is pressed, presentation, description and tags are sent to server 
	 *  via code in this scene and then turns this scene invisible
	 *  To make then scene visible when postButton is pressed, there may still need
	 *  to be a mouseListener in mainScene or you pass a listener into this scene from mainScene
	 *  
	 */
	private void saveToXL(File file) throws TransformerException, IOException, SAXException, ParserConfigurationException {
		graphicsBox.getPresentation().setSlides(tabPane.getSlides());
		org.w3c.dom.Document doc= graphicsBox.getPresentation().getXMLDoc(file);
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		Result output = new StreamResult(new File(file.getName()+".xml"));
		Source input = new DOMSource(doc);
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			
		transformer.transform(input, output);
	     
	}
	public CustomToolBar getToolBar() {
		return toolBar;
	}
	public ElementsPanel getElementPanel() {
		return graphicsBox;
	}
	public ElementPropertiesPanel getElementPropertiesPanel() {
		return propertiesBox;
	}
	public CustomTimeProgressBar getTimeBar() {
		return timeBar;
	}
	public CustomTagBox getTagsBox() {
		return tagBox;
	}
	public CustomTabPanel getTabPanel() {
		return tabPane;
	}
	public CustomDescriptionBox getDescriptionBox() {
		return descriptionBox;
	}
	public CustomMediaBox getMediaBox() {
		return mediaBox;
	}

	/**
	 * @return exitButton to be to added to a mouseListener in mainScene
	 */
	public JButton getCloseButton() {
		return toolBar.getExitButton();
	}
	/**
	 * @return confirmButton to be added to a mouseListener in mainScene
	 */
	public JButton getConfirmButton() {
		return toolBar.getConfirmButton();
	}
	/*
	 *  Server getters:
	 */
	public ArrayList<String> getTags(){
		return tagBox.getTags();
	}
	public String getDescription() {
		return descriptionBox.getDescription();
	}
	public Presentation getPresentation() {
		return graphicsBox.getPresentation();
	}
	
	

	/**
	 * So this is the method that will have to be turned into a layoutManager
	 * I've essentially created a null layout gridLayout so components can be placed in a grid 
	 * of 6 by 16.
	 * GapWidth is set to 10, but it would be great if this value could be value 
	 * associated with its width and height. 
	 */
	public void setBounds(int x, int y,int width, int height) {
		super.setBounds(x,y,width, height);
		int gridWidth= 6;
		int gridHeight=16;
		double cellWidth= width/gridWidth;
		double cellHeight= height/gridHeight;
		
		//GRID BOUNDS:
		int mb[]= {0,0,1,16};
		int tab[]= {1,0,5,14};
		int time[]= {1,14,5,15};
		int tool[]= {1,15,5,16};
		int gb[]= {5,0,6,5};
		int tag[]= {5,10,6,13};
		int pb[]= {5,5,6,10};
		int db[]= {5,13,6,16};
		
		mediaBox.setBounds((int)(mb[0]*cellWidth),(int)(mb[1]*cellHeight),
				(int)(mb[2]*cellWidth-(mb[0]*cellWidth)),(int)(mb[3]*cellHeight-(mb[1]*cellHeight)));
		tabPane.setBounds((int)(tab[0]*cellWidth),(int)(tab[1]*cellHeight),
				(int)(tab[2]*cellWidth-(tab[0]*cellWidth)),(int)(tab[3]*cellHeight-(tab[1]*cellHeight)));
		toolBar.setBounds((int)(tool[0]*cellWidth),(int)(tool[1]*cellHeight),
				(int)(tool[2]*cellWidth-(tool[0]*cellWidth)),(int)(tool[3]*cellHeight-(tool[1]*cellHeight)));
		graphicsBox.setBounds((int)(gb[0]*cellWidth),(int)(gb[1]*cellHeight),
				(int)(gb[2]*cellWidth-(gb[0]*cellWidth)),(int)(gb[3]*cellHeight-(gb[1]*cellHeight)));
		tagBox.setBounds((int)(tag[0]*cellWidth),(int)(tag[1]*cellHeight),
				(int)(tag[2]*cellWidth-(tag[0]*cellWidth)),(int)(tag[3]*cellHeight-(tag[1]*cellHeight)));
		propertiesBox.setBounds((int)(pb[0]*cellWidth),(int)(pb[1]*cellHeight),
				(int)(pb[2]*cellWidth-(pb[0]*cellWidth)),(int)(pb[3]*cellHeight-(pb[1]*cellHeight)));
		descriptionBox.setBounds((int)(db[0]*cellWidth),(int)(db[1]*cellHeight),
				(int)(db[2]*cellWidth-(db[0]*cellWidth)),(int)(db[3]*cellHeight-(db[1]*cellHeight)));
		timeBar.setBounds((int)(time[0]*cellWidth),(int)(time[1]*cellHeight),
				(int)(time[2]*cellWidth-(time[0]*cellWidth)),(int)(time[3]*cellHeight-(time[1]*cellHeight)));
		
		//Setting component margins:
		int gapWidth= 10;
		mediaBox.setMarginBounds((int)gapWidth,(int) gapWidth, (int)gapWidth/2,(int) gapWidth);
		tabPane.setMarginBounds((int)gapWidth/2, (int)gapWidth,(int) gapWidth/2, 0);
		timeBar.setMarginBounds((int)gapWidth/2, 0,(int) gapWidth/2, (int)gapWidth/2);
		toolBar.setMarginBounds((int)gapWidth/2, (int)gapWidth/2, (int)gapWidth/2,(int) gapWidth);
		graphicsBox.setMarginBounds((int)gapWidth/2, (int)gapWidth,(int) gapWidth, (int)gapWidth/2);
		propertiesBox.setMarginBounds((int)gapWidth/2, (int)gapWidth/2,(int) gapWidth, (int)gapWidth/2);
		tagBox.setMarginBounds((int)gapWidth/2, (int)gapWidth/2,(int) gapWidth, (int)gapWidth/2);
		descriptionBox.setMarginBounds((int)gapWidth/2, (int)gapWidth/2,(int) gapWidth, (int)gapWidth);	
	}
}
