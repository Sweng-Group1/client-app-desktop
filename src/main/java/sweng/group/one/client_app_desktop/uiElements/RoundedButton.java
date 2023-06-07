package sweng.group.one.client_app_desktop.uiElements;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;


/**
 * Modified JButton with custom graphics
 * 
 * @author Sophie Maw & Luke George & Fraser Todd
 * @since 28/05/2023
 * 
 */
public class RoundedButton extends JPanel{
	
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
	private Color currentColour;
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
		
		this.currentColour = backgroundColour;
		this.setOpaque(false);
		
		
		MouseAdapter mouseListener = new MouseAdapter () {
			/**
			 * Sets the status of the button to if it is currently pressed or not
			 * Changes the background colour whilst the button is held down
			 */
			public void mousePressed(MouseEvent me) {
				currentColour = backgroundWhenPressed;
				buttonPressed();
				repaint();
			}
			/**
			 * Changes the background colour when the button is released
			 */
			public void mouseReleased(MouseEvent me) {
				currentColour = backgroundWhenHover;
				repaint();
			}
			/**
			 * Changes the background colour when the button is exited
			 */
			public void mouseExited(MouseEvent me) {
				currentColour = backgroundColour;
				repaint();
			}
			/**
			 * Changes the background colour when the button is hovered over
			 */
			public void mouseMoved(MouseEvent me) {
				currentColour = backgroundWhenHover;
				repaint();
			}
		};
		addMouseListener(mouseListener);
		addMouseMotionListener(mouseListener);
	}
	
	public RoundedButton(Image image) {
		this(image, UIConstants.CURVE_RADIUS, YUSUColours.UI_MAIN, YUSUColours.UI_PRESSED, YUSUColours.UI_HOVER);
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
		currentColour = normalColour;
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
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		int radius = Math.min(getWidth(), getHeight())/2;
		
		RenderingHints qualityHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		qualityHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2.setRenderingHints(qualityHints);
		
		g2.setColor(this.currentColour);
		g.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), curveRadius, curveRadius);
		
		if(image!=null) {
			g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g2.drawImage(image,
					(getWidth()-radius)/2,
					(getHeight()-radius)/2,
					radius,
					radius,
					null);
		}
		g2.dispose();
	}
	
	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if (visible) {
			currentColour = backgroundColour;
		}
	}
	
	public void setCurveRadius(int r) {
		this.curveRadius = r;
	}
	
	public void buttonPressed() {
		//override this function in implementation
	}
	
}