package sweng.group.one.client_app_desktop.sceneControl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import sweng.group.one.client_app_desktop.uiElements.CircleButton;
import sweng.group.one.client_app_desktop.uiElements.CircleTab;
import sweng.group.one.client_app_desktop.uiElements.RoundedPanel;
import sweng.group.one.client_app_desktop.uiElements.Tab;

public class TabBar extends RoundedPanel{
	List<CircleTab>tabs;
	JPanel tabsHolder;
	CircleButton addTabButton;
	
	MouseListener meDeleteButton;
	MouseListener meAddButton;
	MouseListener meTab;
	
	Color colour1;
	
	public TabBar(Color colour1, int curvatureRadius) throws IOException {
		create(colour1,curvatureRadius);	
		createMouseListeners();
		createAddTabButton();	
		tabs= Collections.synchronizedList(new ArrayList<>());
		addNewTab();

	}
	private void create(Color colour1,int curvatureRadius) {
		this.colour1=colour1;
		this.curvedRadius= curvatureRadius;
		this.setLayout(null);
		tabsHolder= new JPanel();
		this.add(tabsHolder);
		tabsHolder.setLayout(new BoxLayout(tabsHolder,BoxLayout.X_AXIS));
	}
	
	private void createAddTabButton() throws IOException {
		addTabButton= new CircleButton();
		this.add(addTabButton);
		addTabButton.setMainBackground(colour1);
		addTabButton.setImageIcon(ImageIO.read(new File("./Assets/plus-small.png")));
		addTabButton.addMouseListener(meAddButton);
		
	}


	private void addNewTab() throws IOException {
		for(int i=0;i<tabs.size();i++) {
			tabs.get(i).setOnTop(false);
		}
		int tabNumber= tabs.size()+1;
		CircleTab tab= new CircleTab(Color.white,tabNumber, curvedRadius,colour1);
		tabs.add(tab);
		tabsHolder.add(tab);	
		tab.addMouseListener(meDeleteButton);
		tab.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				for(int i=0;i<tabs.size();i++) {
					if(tabs.get(i).getIsClicked()==false) {
						tabs.get(i).setOnTop(false);
					}else {
						tabs.get(i).setOnTop(true);
						tabs.get(i).setClicked(false);
					}
					
				}
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
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}

	private void createMouseListeners() {
		meDeleteButton= new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if(tabs.size()>1) {
					tabs.get(tabs.size()-1).setVisible(false);
					tabs.remove(tabs.get(tabs.size()-1));
					resizeTabs();
					
				}	
			}

			@Override
			public void mousePressed(MouseEvent e) {

				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
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
		meAddButton= new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					addNewTab();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				resizeTabs();
				//tabsPanel.validate();
			}

			@Override
			public void mousePressed(MouseEvent e) {
				
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
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
	public void setSize(int width, int height) {
		curvedRadius= curvedRadius/height;
		super.setSize(width, height);
		
		int gapWidth= width/10;
		addTabButton.setSize(height);
		
		tabsHolder.setSize(width-(2*gapWidth)-addTabButton.getWidth(), height);
		tabsHolder.setLocation(gapWidth, 0);
		resizeTabs();
		addTabButton.setLocation(width-(height/2), height/4);
		addTabButton.setVisible(true);
	}

	private void resizeTabs() {
		int width= tabsHolder.getWidth();
		int height= this.getHeight();
		int tabWidth;
		if(tabs.size()<5) {
			tabWidth=  (width)/5;
		}
		else{
			tabWidth= (width)/tabs.size();
		}
		for(int i=0;i<tabs.size();i++) {
			tabs.get(i).setSize(tabWidth,height);
			tabs.get(i).setLocation(i*tabWidth, 0);
		}
	}
	
	public CircleButton getAddTabButton() {
		return addTabButton;
	}
}
