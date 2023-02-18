package sweng.group.one.client_app_desktop.uiElements;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CircleTab extends JPanel{
	private int number;
	private String tabNumber;

	private int curvatureRadius;
	
	private Color tabColour;
	private Color textColour;
	
	private boolean tabOnTop;
	private CircleButton deleteButton;
	private boolean deleteClick;
	private boolean isClicked;

	private int gapWidth;
	
	public CircleTab(int gapWidth,Color tabStartColour, int tabNumber, int curvatureRadius, Color deleteButtonColour) throws IOException {
		create(tabNumber,gapWidth,tabStartColour,curvatureRadius);
		createDeleteButton(deleteButtonColour);
		addMouseListeners();
		

	}
	public void create(int tabNumber,int gapWidth,Color tabStartColour, int curvatureRadius) {
		this.tabColour=tabStartColour;
		setTextColour();
		this.curvatureRadius=curvatureRadius;
		this.setLayout(null);
		tabOnTop= true;
		setOpaque(false);
		this.gapWidth= gapWidth;
		number= tabNumber;
		this.tabNumber= String.valueOf(tabNumber);
		
	}
	//public void createNumberLabel() {
	//	numberLabel= new JLabel("Slide " + String.valueOf(tabNumber));
	//	this.add(numberLabel);

	//}

	private void setTextColour() {
		int R= tabColour.getRed();
		int G= tabColour.getGreen();
		int B= tabColour.getBlue();
		
		//Average value
		int avV= (R+G+B)/3;
		if(avV<125) {
			textColour=Color.white;
			
		}else {
			textColour= Color.black;
		}
	}
	public void createDeleteButton(Color deleteButtonColour) throws IOException {
		deleteButton= new CircleButton();
		deleteButton.setImageIcon(ImageIO.read(new File("./Assets/cross.png")));
		deleteButton.setMainBackground(deleteButtonColour);
		this.add(deleteButton);
		deleteButton.setVisible(true);
		deleteButton.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				deleteClick= true;
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				deleteButton.setVisible(true);
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				//deleteButton.setVisible(false);
				
			}
			
		});
			
		
	}
	
	public void addMouseListeners() {
		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				isClicked=true;
				System.out.println("Tab Clicked: " + tabNumber);
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				if(tabOnTop==true) {
					deleteButton.setVisible(true);
				}
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if(tabOnTop) {
					deleteButton.setVisible(false);
				}
				
			}
			
		});
		
	}
	
	
	
	public void paint(Graphics g) {
		Graphics2D g2= (Graphics2D) g.create();
		int width= this.getWidth();
		int height= this.getHeight();
		g2.setColor(tabColour);
		g2.fillRoundRect(gapWidth/2,gapWidth,width-gapWidth, height-(2*gapWidth), curvatureRadius, curvatureRadius);
		
		if(tabOnTop==true) {
			g2.fillRect(gapWidth/2,height- (gapWidth*2),width-gapWidth, gapWidth*2);
		}
		//add text
		g2.setColor(textColour);
		g2.drawString(tabNumber, this.getWidth()/2, 2*this.getHeight()/3);
		
		super.paint(g2);
	}
	public void setSize(int width, int height) {
		super.setSize(width,height);
		
		//Set tab number size and placement
		int fontSize= height/4;
		
		
		
		//set delete button size and placement
		int deleteButtonDiameter= fontSize;
		int deleteButtonXPosition= width-(3*(deleteButtonDiameter/2));
		deleteButton.setSize(deleteButtonDiameter);
		deleteButton.setLocation(deleteButtonXPosition,(height-deleteButtonDiameter)/2);
		
	
	}

	public void setOnTop(boolean bool) {
		if(tabOnTop==true) {
			tabOnTop=false;
		}else {
			tabOnTop=true;
		}
	}
	public boolean getDeleteClick() {
		return deleteClick;
	}
	public boolean isOnTop() {
		return tabOnTop;
	}
	public void setClicked(boolean bool) {
		isClicked=bool;
	}
	public boolean getIsClicked() {
		return isClicked;
	}
	public CircleButton getDeleteButton() {
		return deleteButton;
	}
	public void setGapWidth(int gapWidth) {
		this.gapWidth= gapWidth;
	}
	public int getTabNumber() {
		return number;
	}
	
	public void setTabColour(Color tabColour) {
		this.tabColour=tabColour; 
	}
	public Color getTabColour() {
		return tabColour;
	}
	
}
