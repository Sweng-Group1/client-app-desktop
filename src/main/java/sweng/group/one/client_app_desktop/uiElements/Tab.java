package sweng.group.one.client_app_desktop.uiElements;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;

public class Tab extends RoundedPanel{
	
	private String tabNumber;
	private JLabel number;
	private CircleButton deleteButton;
	private boolean clicked;


	

	
	public Tab(int tabNumber) {
		clicked=false;
		
		
		this.tabNumber= String.valueOf(tabNumber);
		this.setLayout(null);		
		MouseListener mouseListener= new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				clicked=true;
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
				//setBottomLineVisible(true);
				setDeleteButtonVisibleTo(true);
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				setDeleteButtonVisibleTo(false);
				
			}
			
		};
		this.addMouseListener(mouseListener);
		//this.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		
		number= new JLabel();
		this.add(number);
		deleteButton= new CircleButton();
		this.add(deleteButton);
		deleteButton.setVisible(false);
	}
	public void paint(Graphics g) {
		
		Graphics2D g2= (Graphics2D) g.create();
		g2.setColor(getBackground());
		g2.fillRect(0, getHeight()/2, getWidth(), getHeight()/2);
		super.paint(g);
		
	}

	public void setSize(int width, int height) {
		super.setSize(width, height);
			number.setSize(height, height);
			deleteButton.setSize(height);
			number.setText(tabNumber);
			
			
			//number.setFont(Fonts.italic(height/2));	
			number.setLocation(width/2, 0);
			
			
			//deleteButton.setLocation(this.getWidth()-deleteButton.getWidth(),0);
		//	try {
				//deleteButton.setImageIcon(ImageIO.read(new File("./Assets/cross.png")));
			//	deleteButton.setMainBackground(Color.lightGray);	
			//	deleteButton.addMouseListener(new MouseListener() {
				
				

			//	@Override
			//public void mouseClicked(MouseEvent e) {
			//		// this needs to notify another component that is has been clicked, i.e tab bar
					
			//	}

			//	@Override
			//	public void mousePressed(MouseEvent e) {
					
					
			//	}

			//	@Override
			//	public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
					
			//	}

			//	@Override
			//	public void mouseEntered(MouseEvent e) {
			//		setDeleteButtonVisibleTo(true);
					
					
					
			//	}
//
//				@Override
//				public void mouseExited(MouseEvent e) {
//					setDeleteButtonVisibleTo(false);
					
//				}
				
//			});
//			//	deleteButton
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
			}
	public boolean checkClicked() {
		return clicked;
	}
	public void setClicked(boolean bool) {
		clicked=bool;
	}

	private void setDeleteButtonVisibleTo(boolean visibleBool) {
		
		if(visibleBool==true) {
			
			deleteButton.setVisible(true);
		}
		else {
			
			deleteButton.setVisible(false);
		}
		this.validate();
	}
	public CircleButton getDeleteButton() {
		return deleteButton;
	}
}
