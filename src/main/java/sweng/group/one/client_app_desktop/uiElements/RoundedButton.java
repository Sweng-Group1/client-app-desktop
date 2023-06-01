package sweng.group.one.client_app_desktop.uiElements;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;


/**
 * Modified JButton with custom graphics
 * 
 * @author Sophie Maw & Luke George 
 * @since 28/05/2023
 * 
 */
public class RoundedButton extends JButton{
	
	// -------------------------------------------------------------- //
	// --------------------- INITIALISATIONS ------------------------ //
	// -------------------------------------------------------------- //
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Image image;
	private Color backgroundColour;
	private Color backgroundWhenPressed;
	private Color backgroundWhenHover;
	private int curveRadius;
	
	// -------------------------------------------------------------- //
	// ----------------------- CONSTRUCTOR -------------------------- //
	// -------------------------------------------------------------- //
	
	public RoundedButton(Image image, int curveRadius, Color mainColour, Color pressedColour, Color hoverColour) {	
		
		this.image = image;
		this.curveRadius = curveRadius;
		this.backgroundColour = mainColour;
		this.backgroundWhenPressed = pressedColour;
		this.backgroundWhenHover = hoverColour;
		
		
		MouseAdapter mouseListener = new MouseAdapter () {
			/**
			 * Sets the status of the button to if it is currently pressed or not
			 * Changes the background colour whilst the button is held down
			 */
			public void mousePressed(MouseEvent me) {
				setBackground(backgroundWhenPressed);
				buttonPressed();
			}
			/**
			 * Changes the background colour when the button is released
			 */
			public void mouseReleased(MouseEvent me) {
				setBackground(backgroundWhenHover);
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
	
	
	@Override
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
			int imageWidth = diameter/2;
			int imageHeight = diameter/2;
			g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g2.drawImage(image,
					(getWidth()-imageWidth)/2,
					(getHeight()-imageHeight)/2,
					imageWidth,
					imageHeight,
					null);
		}
	}
	
	public void setCurveRadius(int r) {
		this.curveRadius = r;
	}
	
	public void buttonPressed() {
		//override this function in implementation
	}
	
}