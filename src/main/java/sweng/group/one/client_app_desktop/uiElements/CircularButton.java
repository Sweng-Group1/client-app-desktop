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

public class CircularButton extends JButton{
	private Image image;
	private Image scaledImage;
	private Color backgroundColour;
	private Color backgroundWhenPressed;
	private Color backgroundWhenHover;
	
	private int borderThickness;
	private Color border;
	private boolean pressed;
	
	private int curveRadius;
	
	public CircularButton() {	

		setOpaque(false);
		setBorderPainted(false);
		setFocusPainted(false);	
		//setMainBackgroundColor(getBackground());
		borderThickness=0;
		pressed=false;
		
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
	public void setSize(int width, int height, int curveRadius) {
		super.setSize(width,height);	
		if(image!=null) {
			scaledImage= image.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
		}
		this.curveRadius=curveRadius;
		
		
	}
	
	public void paint(Graphics g) {
		int diameter=Math.min(getWidth(), getHeight());
	
	
		Graphics2D g2= (Graphics2D) g;
		
		if(this.isSelected()==true) {
			g2.setColor(backgroundWhenHover);
		}else {
			g2.setColor(this.getBackground());
		}
		g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), curveRadius, curveRadius);
		//g2.fillOval(0,0,diameter-1,diameter-1);
		
		if(image!=null) {
			
			//scaledImage= image.getScaledInstance(diameter/2, diameter/2, java.awt.Image.SCALE_SMOOTH);
		
			BufferedImage masking;
			Graphics2D g2d;
			masking= new BufferedImage(diameter,diameter,BufferedImage.TYPE_INT_ARGB);
			g2d= masking.createGraphics();
			g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			int imageWidth= diameter/2;
			int imageHeight= diameter/2;
			g2d.drawImage(scaledImage,diameter/4,diameter/4,imageWidth,imageHeight,null);
			g2d.dispose();
			
			Icon iconIm= new ImageIcon(masking);
			Image imageIcon= ((ImageIcon)iconIm).getImage();
			
			g2.drawImage(imageIcon, 0, 0, null);
			g2.dispose();
		}	
		
		if(borderThickness>0) {
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setColor(border);
			g2.setStroke(new BasicStroke(borderThickness));
			g2.drawRoundRect(0, 0, this.getWidth(), this.getHeight(), curveRadius, curveRadius);
			//g2.drawOval(0,0,diameter-1,diameter-1);
			
		}
		validate();	
		super.paint(g);
	}
	public void setMainBackgroundColor(Color normal, Color hover, Color click) {
		
		//Button will have 33% opaque normally, 66% when hovered, 100% when pressed
	
		
		backgroundColour= normal;
		backgroundWhenHover= hover;
		backgroundWhenPressed= click;
		
		super.setBackground(backgroundColour);
	
	}
	public void setMainBackground(Color colour) {
		
		//Button will have 33% opaque normally, 66% when hovered, 100% when pressed
		int colourR= colour.getRed();
		int colourG= colour.getGreen();
		int colourB= colour.getBlue();
	
		int alphaNormal= 0;
		int alphaHover= 255/6;
		int alphaPressed= alphaHover*2;
		
		backgroundColour= new Color(colourR,colourG,colourB,alphaNormal);
		backgroundWhenHover= new Color(colourR,colourG,colourB,alphaHover);
		backgroundWhenPressed= new Color(colourR,colourG,colourB,alphaPressed);
		
		super.setBackground(backgroundColour);
	
	}
	public void setBorder(Color colour, int thickness) {
		border= colour;
		borderThickness= thickness;
		
	}
	public int getBorderThickness() {
		return borderThickness;
	}
	public Color getBorderColour() {
		return border;
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