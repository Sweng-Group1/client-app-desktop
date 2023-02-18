package sweng.group.one.client_app_desktop.sceneControl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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

public class TabBar extends JPanel{
	List<CircleTab>tabs;
	JPanel tabsHolder;
	CircleButton addTabButton;
	
	MouseListener meDeleteButton;
	MouseListener meAddButton;
	MouseListener meTabListener;
	
	Color colour1;
	
	int gapWidth;
	Dimension tabSize;
	
	public TabBar(Color colour1) throws IOException {
		create(colour1);	
		createMouseListeners();
		createAddTabButton();	
		tabs= Collections.synchronizedList(new ArrayList<>());
		addNewTab();

	}
	private void create( Color colour1) {
		this.colour1=colour1;
		this.setOpaque(false);
		this.setLayout(null);
		tabsHolder= new JPanel();
		tabsHolder.setOpaque(false);
		this.add(tabsHolder);
		tabsHolder.setLayout(new BoxLayout(tabsHolder,BoxLayout.X_AXIS));
		gapWidth=0;
	}
	
	private void createAddTabButton() throws IOException {
		addTabButton= new CircleButton();
		this.add(addTabButton);
		addTabButton.setMainBackground(Color.white);
		addTabButton.setImageIcon(ImageIO.read(new File("./Assets/plus-small.png")));
		addTabButton.addMouseListener(meAddButton);
		
	}


	private void addNewTab() throws IOException {
		for(int i=0;i<tabs.size();i++) {
			tabs.get(i).setOnTop(false);
		}
		int tabNumber;
		if(tabs.size()==0) {
			tabNumber=1;
		}else {
			tabNumber= tabs.get(tabs.size()-1).getTabNumber()+1;
		}
		CircleTab tab= new CircleTab(gapWidth,colour1,tabNumber, 10,Color.white);
		tabs.add(tab);
		tabsHolder.add(tab);
		setTabOnTop(tab);
		
		tab.getDeleteButton().addMouseListener(meDeleteButton);
		tab.addMouseListener(meTabListener); 
	}

	private void createMouseListeners() {
		meDeleteButton= new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if(tabs.size()>1) {
					for(int i=0; i<tabs.size();i++) {
						CircleTab tab= tabs.get(i);
						if(tab.getDeleteClick()==true) {
							
							if(tab.isOnTop()==true) {
								setTabOnTop(tabs.get(0));
							}
							tab.setVisible(false);
							tabs.remove(tab);
							
						}
						
					}
					
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
		meTabListener= new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				for(int i=0;i<tabs.size();i++) {
					CircleTab tab= tabs.get(i);
					if(tab.getIsClicked()==true) {
						//if(tab.isOnTop()==false) {
							setTabOnTop(tab);
						//}
					}
					tab.setClicked(false);
					tab.repaint();
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
			
		
	}
	private void resizeTabs() {
		int tabWidth= tabsHolder.getWidth()/tabs.size();
		if(gapWidth!=0) {
			for(int i=0;i<tabs.size();i++) {
				tabs.get(i).setSize(tabWidth, tabsHolder.getHeight());
				tabs.get(i).setLocation(i*tabWidth, 0);
			}	
		}
	}
	public void setSize(int width, int height) {
		super.setSize(width, height);
		
		
		gapWidth= width/100;
		setTabsGapWidthSize(gapWidth);
		
		addTabButton.setSize(height-(2*gapWidth));
		tabsHolder.setSize(width-(3*gapWidth)-addTabButton.getWidth(), height);
		resizeTabs();
		
		tabsHolder.setLocation(gapWidth, 0);
		addTabButton.setLocation(width-gapWidth-addTabButton.getWidth(), gapWidth);
		addTabButton.setVisible(true);
		
		
	}
	private void setTabsGapWidthSize(int gapWidth) {
		for(int i=0;i<tabs.size();i++) {
			tabs.get(i).setGapWidth(gapWidth);
		}
	}
	private void setTabOnTop(CircleTab tab) {
		for(int i=0;i<tabs.size();i++) {
			tabs.get(i).setOnTop(false);
		}
		tab.setOnTop(true);
	}
	
	public CircleButton getAddTabButton() {
		return addTabButton;
	}
}
