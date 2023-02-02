package sweng.group.one.client_app_desktop.uiElements;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CircleTab extends JPanel{
	private JLabel numberLabel;
	private int curvatureRadius;
	
	private Color tabColour;
	private boolean tabOnTop;
	private CircleButton deleteButton;
	private boolean isClicked;

	
	
	public CircleTab(Color tabStartColour, int tabNumber, int curvatureRadius, Color deleteButtonColour) throws IOException {
		create(tabStartColour,curvatureRadius);
		createNumberLabel(tabNumber);
		createDeleteButton(deleteButtonColour);
		addMouseListeners();
		

	}
	public void create(Color tabStartColour, int curvatureRadius) {
		this.tabColour=tabStartColour;
		this.curvatureRadius=curvatureRadius;
		this.setLayout(null);
		tabOnTop= true;
		setOpaque(false);

		
	}
	public void createNumberLabel(int tabNumber) {
		numberLabel= new JLabel("Slide " + String.valueOf(tabNumber));
		this.add(numberLabel);
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
				// TODO Auto-generated method stub
				
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
		g2.fillRoundRect(0,height/4,width, height/2, curvatureRadius, curvatureRadius);
		
		if(tabOnTop==true) {
			g2.fillRect(0, height/2,width,height/2);
		}
		super.paint(g);
	}
	public void setSize(int width, int height) {
		super.setSize(width,height);
		
		//Set tab number size and placement
		int fontSize= height/4;
		numberLabel.setFont(Fonts.tabFont(fontSize));
		int textWidth= numberLabel.getFontMetrics(numberLabel.getFont()).stringWidth(numberLabel.getText());
		numberLabel.setSize(textWidth, fontSize);
		numberLabel.setLocation((width-numberLabel.getWidth())/2, (height-numberLabel.getHeight())/2);
		
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
	
}
