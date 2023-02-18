package sweng.group.one.client_app_desktop.sceneControl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;

import sweng.group.one.client_app_desktop.uiElements.RoundedPanel;

public class GraphicsBox extends TextBox {
	List<Graphics2D>graphicsList;
	List<RoundedPanel>graphicButtons;
	
	MouseListener meMoveButton;

	public GraphicsBox(Color background, int curvatureRadius) {
		super(background, curvatureRadius);
		createMouseListeners();
		create();

	}
	private void create() {
		graphicsList= Collections.synchronizedList(new ArrayList<>());
		graphicButtons= Collections.synchronizedList(new ArrayList<>());
	
	}
	private void addGraphic(String graphicName) {
		
		RoundedPanel newButton= new RoundedPanel();
		graphicButtons.add(newButton);

		newButton.add(new JLabel(graphicName));
		this.add(newButton);
	
		newButton.addMouseListener(meMoveButton);
		
	}
	public void setSize(int width, int height) {
		super.setSize(width,height);
		for(int i=0;i<graphicButtons.size();i++) {
			graphicButtons.get(i).setSize(this.getWidth(), this.getWidth()/4,3);
			graphicButtons.get(i).setLocation(0, (i*(this.getWidth()/4))+i*5);
			
		}
	}
	private void createMouseListeners() {
		


		meMoveButton= new MouseListener() {
			boolean press;
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				int eOx= e.getX();
				int eOy= e.getY();
				press=true;
				if(press=true) {
				for(int i=0;i<graphicButtons.size();i++) {
					RoundedPanel button= graphicButtons.get(i);
					if((e.getX()>button.getX())& (e.getX()<(button.getX()+button.getWidth()))){
						if((e.getY()>button.getY())& (e.getY()<(button.getY()+button.getHeight()))){
							textPanel.addMouseMotionListener(new MouseMotionListener() {

								@Override
								public void mouseDragged(MouseEvent a) {
									int buttonOGX= button.getX();
									int buttonOGY= button.getY();
									int changeInPositionX= buttonOGX+(eOx-a.getX());
									int changeInPositionY= buttonOGY+ (eOy- a.getY());
									button.setLocation(changeInPositionX, changeInPositionY);
									
								}

								@Override
								public void mouseMoved(MouseEvent e) {
									button.setLocation(e.getX(), e.getY());
									// TODO Auto-generated method stub
									
								}
								
							});
							button.setLocation(e.getX(),e.getY());
						}
					}
				}}
				
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				press=false;
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
	}

}
