package sweng.group.one.client_app_desktop.uiElements;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * Modified JButton with custom graphics
 * 
 * @author Sophie Maw & Luke George 
 * @since 28/05/2023
 * 
 */
public class CircularButton extends JButton{
	
	// -------------------------------------------------------------- //
	// --------------------- INITIALISATIONS ------------------------ //
	// -------------------------------------------------------------- //
	
	private Image image;
	private Image scaledImage;
	private Color backgroundColour;
	private Color backgroundWhenPressed;
	private Color backgroundWhenHover;
	private Color borderColour;
	private int borderThickness;
	private int curveRadius;
	private boolean pressed;
	
	private int defaultBorderThickness = 0;
	
	// -------------------------------------------------------------- //
	// ----------------------- CONSTRUCTOR -------------------------- //
	// -------------------------------------------------------------- //
	
	public CircularButton() {	
		// Initialise
		setOpaque(false);
		setBorderPainted(false);
		setFocusPainted(false);	
		borderThickness = defaultBorderThickness;
		pressed = false;
		
		MouseAdapter mouseListener = new MouseAdapter () {
			/**
			 * Sets the status of the button to if it is currently pressed or not
			 * Changes the background colour whilst the button is held down
			 */
			public void mousePressed(MouseEvent me) {
				setBackground(backgroundWhenPressed);
				if(pressed==true) {
					pressed = false;
				} else {
					pressed = true;
				}
			}
			/**
			 * Changes the background colour when the button is released
			 */
			public void mouseReleased(MouseEvent me) {
				setBackground(backgroundColour);
			}
			/**
			 * Changes the background colour when the button is exited
			 */
			public void mouseExited(MouseEvent me) {
				setBackground(backgroundColour);
			}
			/**
			 * Changes the background colour when the button is hovered over
			 */
			public void mouseMoved(MouseEvent me) {
				setBackground(backgroundWhenHover);
			}
		};
		addMouseListener(mouseListener);
		addMouseMotionListener(mouseListener);
	}
	
	// -------------------------------------------------------------- //
	// ----------------------- APPEARANCE --------------------------- //
	// -------------------------------------------------------------- //
	
	/**
	 * Sets the size of the button in terms of its width and height
	 * Sets the radius of the buttons curves (how rounded the edges are)
	 * 
	 * @param width Buttons width in pixels
	 * @param height Buttons height in pixels
	 * @param curveRadius Buttons curve radius in pixels
	 */
	public void setSize(int width, int height, int curveRadius) {
		super.setSize(width,height);	
		if(image!=null) {
			scaledImage = image.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
		}
		this.curveRadius=curveRadius;
	}
	
	/**
	 * Sets the main colour of the buttons background
	 * Adjusts the buttons opacity when the user interacts with it
	 * 
	 * @param colour Background colour of the button
	 */
	public void setMainBackgroundColour(Color colour) {
		int colourR = colour.getRed();
		int colourG = colour.getGreen();
		int colourB = colour.getBlue();
	
		int alphaPressed = 255;
		int alphaNormal = alphaPressed/3;
		int alphaHover = 2*alphaNormal;
		
		backgroundColour = new Color(colourR,colourG,colourB,alphaNormal);
		backgroundWhenHover = new Color(colourR,colourG,colourB,alphaHover);
		backgroundWhenPressed = new Color(colourR,colourG,colourB,alphaPressed);
		
		super.setBackground(backgroundColour);
	}
	
	/**
	 * This version of the function only seems to be used for the buttons in OptionsScene
	 * Sets the main colour of the buttons background
	 * Adjusts the buttons opacity when the user interacts with it
	 * 
	 * @param normalColour Standard background colour of the button
	 * @param hoverColour Background colour of the button when hovered over
	 * @param pressedColour Background colour of the button when pressed
	 */
	public void setMainBackgroundColour(Color normalColour, Color hoverColour, Color pressedColour) {	
		backgroundColour = normalColour;
		backgroundWhenHover = hoverColour;
		backgroundWhenPressed = pressedColour;
		
		super.setBackground(backgroundColour);
	}
	
	/**
	 * 
	 * @param newImage Image to be set
	 */
	public void setIconImage(Image newImage) {
		image = newImage;
	}
	
	/**
	 * 
	 * @param colour Colour of the border
	 * @param thickness Thickness the border in pixels
	 */
	public void setBorder(Color colour, int thickness) {
		borderColour = colour;
		borderThickness = thickness;
	}
	
	public int getBorderThickness() {
		return borderThickness;
	}
	
	public Color getBorderColour() {
		return borderColour;
	}
	
	// -------------------------------------------------------------- //
	// --------------------- UPDATE GRAPHICS ------------------------ //
	// -------------------------------------------------------------- //
	
	public void paint(Graphics g) {
		int diameter = Math.min(getWidth(), getHeight());
	
		Graphics2D g2 = (Graphics2D) g;
		
		if(this.isSelected()==true) {
			g2.setColor(backgroundWhenHover);
		}else {
			g2.setColor(this.getBackground());
		}
		g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), curveRadius, curveRadius);
		
		if(image!=null) {	
			BufferedImage masking;
			Graphics2D g2d;
			
			masking = new BufferedImage(diameter,diameter,BufferedImage.TYPE_INT_ARGB);
			g2d = masking.createGraphics();
			g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			int imageWidth = diameter/2;
			int imageHeight = diameter/2;
			g2d.drawImage(scaledImage,diameter/4,diameter/4,imageWidth,imageHeight,null);
			g2d.dispose();
			
			Icon icon = new ImageIcon(masking);
			Image iconImage = ((ImageIcon)icon).getImage();
			
			g2.drawImage(iconImage, 0, 0, null);
			g2.dispose();
		}	
		
		if(borderThickness>0) {
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setColor(borderColour);
			g2.setStroke(new BasicStroke(borderThickness));
			g2.drawRoundRect(0, 0, this.getWidth(), this.getHeight(), curveRadius, curveRadius);
		}
		validate();	
		super.paint(g);
	}
	
	// -------------------------------------------------------------- //
	// -------------------------- STATUS ---------------------------- //
	// -------------------------------------------------------------- //
	
	/**
	 * Sets the status of the button to either true (pressed) or false (not pressed)
	 * 
	 * @param pressedStatus Is the button currently pressed or not?
	 */
	public void setPressed(boolean pressedStatus) {
		pressed = pressedStatus;
	}
	
	public boolean checkIfPressed() {
		return pressed;
	}
	
}