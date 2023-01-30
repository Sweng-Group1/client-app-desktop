package sweng.group.one.client_app_desktop.sceneControl;

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
import sweng.group.one.client_app_desktop.uiElements.RoundedPanel;
import sweng.group.one.client_app_desktop.uiElements.Tab;

public class TabBar extends RoundedPanel{
	List<Tab>tabs;
	
	JPanel tabsPanel;
	
	CircleButton addTabButton;
	
	MouseListener meDeleteButton;
	MouseListener meAddButton;
	MouseListener meTabBar;
	
	

	
	public TabBar() {
		this.setLayout(null);
		this.setCurvatureRadius(20);
		createMouseListeners();
		createTabsPanel();

	
		try {
			createAddTabButton();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		tabs= Collections.synchronizedList(new ArrayList<>());
		addNewTab();

	}
	private void createAddTabButton() throws IOException {
		addTabButton= new CircleButton();
		this.add(addTabButton);
		addTabButton.setMainBackground(Color.LIGHT_GRAY);
		addTabButton.setImageIcon(ImageIO.read(new File("./Assets/plus-small.png")));
		addTabButton.addMouseListener(meAddButton);
		
	}
	
	private void createTabsPanel() {
		tabsPanel= new JPanel();
		this.add(tabsPanel);
		tabsPanel.setLayout(new BoxLayout(tabsPanel, BoxLayout.X_AXIS));
	}

	private void addNewTab() {

		int tabNumber= tabs.size()+1;
		Tab tab= new Tab(tabNumber);
		tabs.add(tab);
		tabsPanel.add(tab);
		tab.getDeleteButton().addMouseListener(meDeleteButton);
		tab.setCurvatureRadius(30);
		tab.setBackground(Color.white);;
		tab.addMouseListener(meTabBar);
		for(int i=0;i<tabs.size();i++) {
			setTabColour(tabs.get(i),Color.LIGHT_GRAY);
		}
		setTabColour(tab, Color.white);
		
		
	}
	private void setTabColour(Tab tab, Color color) {
		tab.setBackground(color);
	}
	private void createMouseListeners() {
		meDeleteButton= new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if(tabs.size()>1) {
					tabsPanel.remove(tabs.get(tabs.size()-1));
					tabs.remove(tabs.get(tabs.size()-1));
					resizeTabs();
					tabsPanel.validate();
				}	
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if(tabs.size()>1) {
					tabsPanel.remove(tabs.get(tabs.size()-1));
					tabs.remove(tabs.get(tabs.size()-1));
					resizeTabs();
					tabsPanel.validate();
				}	
				
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
				addNewTab();
				resizeTabs();
				tabsPanel.validate();
			}

			@Override
			public void mousePressed(MouseEvent e) {
				addNewTab();
				resizeTabs();
				tabsPanel.validate();
				
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
		meTabBar= new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				
					for(int j=0;j<tabs.size();j++) {
						if(tabs.get(j).checkClicked()==true) {
							tabs.get(j).setBackground(Color.white);
							tabs.get(j).setClicked(false);
						}else {
							tabs.get(j).setBackground(Color.LIGHT_GRAY);
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
			
		};
		//this.addMouseListener(meTabBar);
	}
	public void setSize(int width, int height) {
		super.setSize(width, height);
		//innerPanel.setSize(width,height);
		//innerPanel.setLocation(width, height/2);
		addTabButton.setSize(height);
		addTabButton.setLocation(width-height, 0);
		tabsPanel.setSize(width-height,height);
		tabsPanel.setLocation(0, 0);
		resizeTabs();
	
	}

	private void resizeTabs() {
		int tabWidth;
		if(tabs.size()<5) {
			tabWidth=  (this.getWidth()- addTabButton.getWidth())/5;
		}
		else{
			tabWidth= (this.getWidth()- addTabButton.getWidth())/tabs.size();
		}
		for(int i=0;i<tabs.size();i++) {
			tabs.get(i).setSize(tabWidth,this.getHeight());
		}
	}
	public void setVisible(boolean bool) {
		super.setVisible(bool);
		tabsPanel.setVisible(bool);
	}
	public CircleButton getAddTabButton() {
		return addTabButton;
	}
}
