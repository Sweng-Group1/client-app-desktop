package sweng.group.one.client_app_desktop.uiElements;
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

public class CircleButton extends JButton{
	private Image image;
	private Image scaledImage;
	private Color backgroundColour;
	private Color backgroundWhenPressed;
	private Color backgroundWhenHover;
	private boolean pressed;
	
	public CircleButton() {	

		setOpaque(false);
		setBorderPainted(false);
		setFocusPainted(false);	
		setBackgroundMainColour(getBackground());
		
		
		MouseAdapter mouseListener= new MouseAdapter () {
		
			public void mousePressed(MouseEvent me) {
				setBackground(backgroundWhenPressed);
				if(pressed==true) {
					pressed= false;
				}else {
					pressed=true;
				}
				
			}
			public void mouseReleased(MouseEvent me) {
				setBackground(backgroundColour);
			}
			public void mouseExited(MouseEvent me) {
				setBackground(backgroundColour);
			}
			public void mouseMoved(MouseEvent me) {
				setBackground(backgroundWhenHover);
			}
		};
		addMouseListener(mouseListener);
		addMouseMotionListener(mouseListener);
	
		
	}
	public void setSize(int diameter) {
		super.setSize(diameter,diameter);	
		
		
	}
	
	public void paint(Graphics g) {
		int diameter=Math.min(getWidth(), getHeight());
	
	
		Graphics2D g2= (Graphics2D) g;
		g2.setColor(this.getBackground());
		g2.fillOval(0,0,diameter-1,diameter-1);
		
		
		if(image!=null) {
			
			scaledImage= image.getScaledInstance(diameter/2, diameter/2, java.awt.Image.SCALE_SMOOTH);
		
			BufferedImage masking;
			Graphics2D g2d;
			masking= new BufferedImage(diameter,diameter,BufferedImage.TYPE_INT_ARGB);
			g2d= masking.createGraphics();
			g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			int imageWidth= diameter/2;
			int imageHeight= diameter/2;
			g2d.drawImage(image,diameter/4,diameter/4,imageWidth,imageHeight,null);
			g2d.dispose();
			
			Icon iconIm= new ImageIcon(masking);
			Image imageIcon= ((ImageIcon)iconIm).getImage();
			g2.setColor(Color.black);
			g2.drawImage(imageIcon, 0, 0, null);
		
		}	
		validate();	
		super.paint(g);
	}
	public void setBackgroundMainColour(Color colour) {
		//Button will have 33% opaque normally, 66% when hovered, 100% when pressed
		int colourR= colour.getRed();
		int colourG= colour.getGreen();
		int colourB= colour.getBlue();
	
		int alphaNormal= 255/3;
		int alphaHover= alphaNormal*2;
		int alphaPressed= 255;
		
		backgroundColour= new Color(colourR,colourG,colourB,alphaNormal);
		backgroundWhenHover= new Color(colourR,colourG,colourB,alphaHover);
		backgroundWhenPressed= new Color(colourR,colourG,colourB,alphaPressed);
		
		super.setBackground(backgroundColour);
	
	}
	public void setPressed(boolean trueFalse) {
		pressed= trueFalse;
	}
	public boolean checkIfPressed() {
		return pressed;
	}

	public void setImageIcon(Image im) {
		image=im;
	}
	
}

