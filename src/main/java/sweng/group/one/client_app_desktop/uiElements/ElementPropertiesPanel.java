package sweng.group.one.client_app_desktop.uiElements;

import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.Scrollbar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import sweng.group.one.client_app_desktop.graphics.Circle;
import sweng.group.one.client_app_desktop.graphics.Rectangle;
import sweng.group.one.client_app_desktop.media.GraphicsElement;
import sweng.group.one.client_app_desktop.media.TextElement;
import sweng.group.one.client_app_desktop.presentation.PresElement;
import sweng.group.one.client_app_desktop.sceneControl.ComponentInterface;

/**
 * @author sophiemaw
 *Panel that displays and allows user to manipulate, properties 
 *of a presElement that is selected within the 
 *ElementsPanel
 *
 *This Panel contains a tabbedPane, and  'tabs' are added when a new element is selected 
 *in the elementsPanel. 
 *
 *Implemented: 
 *	The two tabs currently implemented are elementTab and colourTab()
 *	These two tabs are added when a new element is selected
 *
 *Not Implemented:
 *	elementTab does not display the correct selected PresElement properties. This is because 
 *	im using the component's properties within the elemebt and not the stored fields within it
 *	colorTab does not change the toolBar paintColour- unfortunately I havn't had time to work out why this is 
 *
 *Notes: Need to include new slide method pxToPt() to achieve correct positioning of elements
 *		  user input needs to be in pixels 
 */
public class ElementPropertiesPanel extends UploadSceneComponent implements ComponentInterface {
	private ElementTab elementTab;
	private ElementColorManipulator colorTab;
	private TextManipulater textTab;
	private ShadowTab shadowTab;
	private PresElement element;
	JTabbedPane tabbedPane;
	
	public ElementPropertiesPanel() {
		initialise();
	}
	private void initialise() {
		this.setLayout(null);
		tabbedPane= new JTabbedPane();
		this.add(tabbedPane);
		this.main= colorLight;
		this.secondary= colorDark;
		elementTab= new ElementTab();
		textTab= new TextManipulater();
		shadowTab= new ShadowTab();
	}
	
	/**
	 * @param toolBar
	 * Not the cleanest way to get toolBar in this scene but I've realised 
	 *  it's needed to implement the colorChooser, due to hierarchy this 
	 *  cannot be passed through the constructor. 
	 */
	public void setToolBarColorChanger(CustomToolBar toolBar) {
		colorTab= new ElementColorManipulator(toolBar);
	}
	
	/**
	 * @param element
	 * Method thats called within elementsPanel to change tabs to 
	 * specific presElement tabs
	 * 
	 * Notes: 
	 * Reason for removing tabs instead of updating values, is I want to implement properties
	 * specific tabs, i.e 'circleTab': lets you change radius, fill colour, shadow ect. which is 
	 * obviously different to what you can do to an image element. 
	 */
	public void setManipulatorFor(PresElement element) {
		this.element=element;
		String type= element.getType();
		switch(type){
		case "GRAPHIC":
			tabbedPane.removeAll();
			elementTab.setValuesForElement(element);
			tabbedPane.addTab(" Element ",elementTab);
			colorTab.setColorManipulatorFor(element);
			tabbedPane.addTab(" Colour ",colorTab);
			break;
		case "IMAGE":
			tabbedPane.removeAll();
			elementTab.setValuesForElement(element);
			tabbedPane.addTab(" Element ",elementTab);
			colorTab.setColorManipulatorFor(element);
			tabbedPane.addTab(" Colour ",colorTab);
			break;
		case "CIRCLE":
			tabbedPane.removeAll();
			elementTab.setValuesForElement(element);
			tabbedPane.addTab(" Element ",elementTab);
			colorTab.setColorManipulatorFor(element);
			tabbedPane.addTab(" Colour ",colorTab);
			shadowTab.setManipulaterFor(element);
			tabbedPane.add(shadowTab);
			break;
		case "RECTANGLE":
			tabbedPane.removeAll();
			elementTab.setValuesForElement(element);
			tabbedPane.addTab(" Element ",elementTab);
			colorTab.setColorManipulatorFor(element);
			tabbedPane.addTab(" Colour ",colorTab);
			break;
		case "TEXT":
			tabbedPane.removeAll();
			elementTab.setValuesForElement(element);
			tabbedPane.addTab(" Element ",elementTab);
			colorTab.setColorManipulatorFor(element);
			tabbedPane.addTab("Colour", colorTab);
			textTab.setTextManipulaterFor(element);
			tabbedPane.addTab("Text", textTab);
			break;
		default:
			break;
		}
	}
	
		
	/**
	 * Explained in setMarginBounds() diagram
	 */
	public void setMarginBounds(int r,int t, int l, int b) {
		super.setMarginBounds(r, t, l, b);
		tabbedPane.setBounds(r, t+curvatureRadius/2, this.getWidth()-(r+l),this.getHeight()-(curvatureRadius)-t-b );
		elementTab.setSize(tabbedPane.getWidth(), tabbedPane.getHeight());
		colorTab.getColorChooser().setPreferredSize(elementTab.getSize());
	}
	
}



	
/**
 * @author sophiemaw
 * Element tab: contains 5 panels with titles to the textFields, and 5 textFields
 * Implemented:
 *  Font height changes to fit these components within this panel  
 *  Enter key listeners are implemented to listen for user input to update presElement
 * Not Implemented:
 *  Font width does not change to fit within panel
 *  Values are not updated to presElement
 */
class ElementTab extends JPanel implements ComponentInterface{
	private static final long serialVersionUID = 1L;
	Color transparent= new Color(255,255,255,0);
	JPanel xPosPane;
	JTextField xPos;
	
	JPanel yPosPane;
	JTextField yPos;
	
	JPanel widthPane;
	JTextField widthPos;
	
	JPanel heightPane;
	JTextField heightPos;
	
	JPanel durationPane;
	JTextField duration;
	
	Font font;
	int curvatureRadius;
	
	PresElement element;

	public ElementTab() {

		this.setOpaque(false);
		this.setLayout(new GridLayout());
		
		this.curvatureRadius=curvatureRadius;
		xPosPane= new JPanel() {
			public void paint(Graphics g) {
				Graphics2D g2= (Graphics2D)g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setFont(font);
				g2.setColor(Color.white);
				g2.drawString("X pos: ", 0, this.getHeight()-(this.getHeight()/4));
				g2.dispose();
				super.paint(g);
			}
		};
		xPosPane.setOpaque(false);
		xPos= new JTextField();
		xPos.setOpaque(false);
		xPos.setBackground(transparent);
		xPos.setForeground(Color.white);
		xPos.setCaretColor(Color.white);
		this.add(xPosPane);
		this.add(xPos);
		xPos.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				if(element!=null) {
					if(e.getKeyChar()==KeyEvent.VK_ENTER) {
						element.setX(Integer.valueOf(xPos.getText()));
						xPos.setText(String.valueOf(element.getPosPoint().x));
						element.getComponent().setLocation(Integer.valueOf(xPos.getText()),element.getComponent().getY());
						element.getComponent().validate();
						element.getComponent().repaint();
					}
				}
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		
		yPosPane= new JPanel() {
			public void paint(Graphics g) {
				Graphics2D g2= (Graphics2D)g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setFont(font);
				g2.setColor(Color.white);
				g2.drawString("Y pos: ", 0, this.getHeight()-(this.getHeight()/4));
				g2.dispose();
				super.paint(g);
			}
		};
		yPosPane.setOpaque(false);
		yPos= new JTextField();
		yPos.setOpaque(false);
		yPos.setBackground(transparent);
		yPos.setForeground(Color.white);
		yPos.setCaretColor(Color.white);
		this.add(yPosPane);
		this.add(yPos);
		yPos.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				if(element!=null) {
					if(e.getKeyChar()==KeyEvent.VK_ENTER) {
						element.setY(Integer.valueOf(yPos.getText()));
						yPos.setText(String.valueOf(element.getPosPoint().y));
						element.getComponent().setLocation(element.getComponent().getY(),Integer.valueOf(yPos.getText()));
						element.getComponent().validate();
						element.getComponent().repaint();
					}
				}
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
	
		
		
		widthPane= new JPanel() {
			public void paint(Graphics g) {
				Graphics2D g2= (Graphics2D)g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setFont(font);
				g2.setColor(Color.white);
				g2.drawString("Width: ", 0, this.getHeight()-(this.getHeight()/4));
				g2.dispose();
				super.paint(g);
			}
		};
		widthPane.setOpaque(false);
		widthPos= new JTextField();
		widthPos.setOpaque(false);
		widthPos.setBackground(transparent);
		widthPos.setForeground(Color.white);
		widthPos.setCaretColor(Color.white);
		this.add(widthPane);
		this.add(widthPos);
		widthPos.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				if(element!=null) {
					if(e.getKeyChar()==KeyEvent.VK_ENTER) {
						element.setWidth(Integer.valueOf(widthPos.getText()));
						widthPos.setText(String.valueOf(element.getWidth()));
						element.getComponent().setSize(Integer.valueOf(widthPos.getText()),element.getComponent().getWidth());
						element.getComponent().validate();
						element.getComponent().repaint();
					}
				}
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});

		heightPane= new JPanel() {
			public void paint(Graphics g) {
				Graphics2D g2= (Graphics2D)g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setFont(font);
				g2.setColor(Color.white);
				g2.drawString("Height: ", 0, this.getHeight()-(this.getHeight()/4));
				g2.dispose();
				super.paint(g);
			}
		};
		heightPane.setOpaque(false);
		heightPos= new JTextField();
		heightPos.setOpaque(false);
		heightPos.setBackground(transparent);
		heightPos.setForeground(Color.white);
		heightPos.setCaretColor(Color.white);
		this.add(heightPane);
		this.add(heightPos);
		heightPos.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				if(element!=null) {
					if(e.getKeyChar()==KeyEvent.VK_ENTER) {
						element.setHeight(Integer.valueOf(heightPos.getText()));
						heightPos.setText(String.valueOf(element.getHeight()));
						element.getComponent().setSize(element.getComponent().getWidth(),Integer.valueOf(heightPos.getText()));
						element.getComponent().validate();
						element.getComponent().repaint();
					}
				}
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		
		durationPane= new JPanel() {
			public void paint(Graphics g) {
				Graphics2D g2= (Graphics2D)g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setFont(font);
				g2.setColor(Color.white);
				g2.drawString("Duration: ", 0, this.getHeight()-(this.getHeight()/4));
				g2.dispose();
				super.paint(g);
			}
		};
		durationPane.setOpaque(false);
		duration= new JTextField();
		duration.setOpaque(false);
		duration.setBackground(transparent);
		duration.setForeground(Color.white);
		duration.setCaretColor(Color.white);
		this.add(durationPane);
		this.add(duration);
		duration.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				if(element!=null) {
					if(e.getKeyChar()==KeyEvent.VK_ENTER) {
						element.setDuration(Float.valueOf(duration.getText()));
						System.out.println(Float.valueOf(duration.getText()));
						System.out.println(element.getDuration());
						duration.setText(String.valueOf(element.getDuration()));
						element.getComponent().validate();
						element.getComponent().repaint();
						
					}
				}
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
	}
	public void setValuesForElement(PresElement element) {
		this.element=element;
		xPos.setText(String.valueOf(element.getComponent().getX()));
		yPos.setText(String.valueOf(element.getComponent().getY()));
		widthPos.setText(String.valueOf(element.getComponent().getWidth()));
		heightPos.setText(String.valueOf(element.getComponent().getHeight()));
		duration.setText(String.valueOf(element.getDuration()));
	}
	/*
	 *  This will probably need to be changed to a standard layout 
	 *  and then font changed to fit within newly sized components
	 */
	public void setSize(int width, int height) {
		super.setSize(width,height);
		int row= height/11;
		this.setLayout(new FlowLayout());
		//this.setLayout(new BoxLayout(this,BoxLayout.));
		font= new Font("",Font.PLAIN,height/11);
		int w= width;
		Dimension componentLeftSize= new Dimension(width/2,height/8);
		Dimension componentRightSize= new Dimension(width/4,height/8);
		xPosPane.setPreferredSize(componentLeftSize);
		xPos.setPreferredSize(componentRightSize);
		yPosPane.setPreferredSize(componentLeftSize);
		yPos.setPreferredSize(componentRightSize);
		widthPane.setPreferredSize(componentLeftSize);
		widthPos.setPreferredSize(componentRightSize);
		heightPane.setPreferredSize(componentLeftSize);
		heightPos.setPreferredSize(componentRightSize);
		durationPane.setPreferredSize(componentLeftSize);
		duration.setPreferredSize(componentRightSize);
		
		
		
		this.validate();
	}
	
}
/**
 * @author sophiemaw
 * ColourTab contains a colourChooser, which needs to change the paintColour field within 
 * toolBar- 
 * Notes: when shape elements are implemented, to get fillColour they need to pull whatever c
 * colour is stored in the paintColor field in toolBar
 * 
 * Implemented: 
 * 	colourChooser is placed in a scrollPane (because it has a minimum size, and any smaller wont 
 * 	show the full colourchooser)
 * Not implemented:
 *  the listeners to change the toolBar paint field don't work, not sure at to why
 * 
 */
class ElementColorManipulator extends JPanel implements ComponentInterface{
	PresElement element;
	JColorChooser colorChooser;
	CustomToolBar toolBar;
	public ElementColorManipulator(CustomToolBar toolBar) {
	
		this.setBackground(colorLight);
		this.setLayout(new GridLayout());
		JPanel pane= new JPanel();
		pane.setBackground(colorLight);
		JScrollPane scrollPane= new JScrollPane(pane);
		this.add(scrollPane);
		scrollPane.setBackground(colorLight);
		colorChooser= new JColorChooser();
		pane.add(colorChooser);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		colorChooser.getSelectionModel().addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				if(element!=null) {
					switch(element.getType()){
					case "GRAPHIC":
							toolBar.setPaintColor(colorChooser.getColor());
							GraphicsElement gEl= (GraphicsElement)element;
							gEl.setColor(colorChooser.getColor());
						break;
					case "CIRCLE":
						toolBar.setPaintColor(colorChooser.getColor());
						Circle cEl= (Circle)element;
						cEl.setColour(colorChooser.getColor());
						break;
					case "RECTANGLE":
						toolBar.setPaintColor(colorChooser.getColor());
						Rectangle rEl= (Rectangle)element;
						rEl.setColour(colorChooser.getColor());
						break;
					case "TEXT":
							toolBar.setTextColor(colorChooser.getColor());
							TextElement tEl= (TextElement) element;
							tEl.setColour(colorChooser.getColor());
							tEl.getComponent().repaint();
						break;
					default:
						break;
					}
				}
				
			}
			
		});
	
	}
	public void setColorManipulatorFor(PresElement element) {
		this.element= element;
	}
	/*
	 *  Called within setBounds in elementsPropertiesPanel to resize
	 *  colour chooser.
	 */
	public JColorChooser getColorChooser() {
		return colorChooser;
	}
}

class TextManipulater extends JPanel implements ComponentInterface{
	TextElement textElement;
	CustomToolBar toolBar;
	JTextField textField;
	 ArrayList<String> list;
	
	public TextManipulater() {
		this.setBackground(colorLight);
		this.setLayout(new GridLayout());
		JPanel pane= new JPanel();
		pane.setBackground(colorLight);
		textField= new JTextField();
		pane.add(textField);
		this.add(pane);
		
		textField.setBackground(colorLight);
		textField.setCaretColor(Color.white);
		textField.setForeground(Color.white);
		textField.setSelectedTextColor(Color.white);
		textField.setDisabledTextColor(Color.white);
		textField.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				textField.enableInputMethods(true);
				
			}

			@Override
			public void focusLost(FocusEvent e) {
				if(textElement!=null) {
					textElement.setText(textField.getText());
				}
				
			}
			
		});
		
		JPanel paneLower= new JPanel();
		paneLower.setOpaque(false);
		pane.add(paneLower);
		JLabel lbl = new JLabel("Fonts:");
	    lbl.setVisible(true);

	    paneLower.add(lbl);
	   
	    String[] choices = { "Ariel","Arial Bold","Ariel Italic","Sans Serif","Serif", "Mono spaced","Dialog","True Type", "Type 1", "Roman"};
	    list= new ArrayList<String>();
	    for(String s:choices) {
	    	list.add(s);
	    }
	   
	    final JComboBox<String> cb = new JComboBox<String>(choices);

	    cb.setVisible(true);
	    paneLower.add(cb);

	    JButton btn = new JButton("OK");
	    paneLower.add(btn);
	    btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(textElement!=null) {
					textElement.setFont(list.get(cb.getSelectedIndex()));
					textElement.getComponent().repaint();
				}
				
			
			}
	    });
	}
	public void setTextManipulaterFor(PresElement element) {
		if(element.getType()=="TEXT") {
			textElement= (TextElement)element;
			textField.setText(textElement.getText());
		}else {
			textElement=null;
		}
	}
}

class ShadowTab  extends JPanel implements ComponentInterface{
	PresElement element;
	public ShadowTab() {
			this.setBackground(colorLight);
			this.setLayout(new GridLayout());
			JPanel pane= new JPanel();
			pane.setBackground(colorLight);
			this.add(pane);
			JPanel one= new JPanel();
			pane.add(one);
			JLabel shadowRadius= new JLabel("Shadow radius:");
			one.add(shadowRadius);
			JTextField shadowRadiusIn= new JTextField();
			one.add(shadowRadiusIn);
			shadowRadiusIn.addFocusListener(new FocusListener() {

				@Override
				public void focusGained(FocusEvent e) {
					
				}

				@Override
				public void focusLost(FocusEvent e) {
					if(element!=null) {
						if(shadowRadiusIn.getText()!="") {
						switch(element.getType()) {
						case "TEXT":
							TextElement tEl= (TextElement) element;
							
							break;
						case "CIRCLE":
							Circle cEl= (Circle) element;
							
							cEl.setShadowRadius(Integer.valueOf(shadowRadiusIn.getText()));
							break;
							default:
								break;
						}
					}}
					
					
				}
				
			});
	}
	public void setManipulaterFor(PresElement element) {
		this.element=element;
	}
	
	
}


