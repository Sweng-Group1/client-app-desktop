package sweng.group.one.client_app_desktop.uiElements;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

import sweng.group.one.client_app_desktop.presentation.PresElement;
import sweng.group.one.client_app_desktop.presentation.Presentation;
import sweng.group.one.client_app_desktop.presentation.Slide;



public class CustomTabPanel extends UploadSceneComponent{
	int[] landscape= {191,100};
	int[] portrait= {400,500};
	int[] sqaure= {500,500};

	CustomTabbedPane tabPane;
	Presentation presentation;
	CircleButton addTabButton;
	CircleButton removeTabButton;
	
	boolean paintMode;
	boolean eraserMode;
	
	CustomGraphicsBox graphicsBox;
	MovingObject movingObject;
		
	public CustomTabPanel(CustomGraphicsBox graphicsBox) {
		this.graphicsBox= graphicsBox;
		initialise();
		
	}
	
	private void initialise() {
		presentation= new Presentation();
		this.setLayout(null);
		paintMode=false;
		eraserMode=false;
		
		tabPane= new CustomTabbedPane();
		this.add(tabPane);
		
		//BUTTONS:
		addTabButton= new CircleButton();
		try {
			addTabButton.setImageIcon(ImageIO.read(new File("./Assets/plus-small.png")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		removeTabButton= new CircleButton();
		this.add(addTabButton);
		try {
			removeTabButton.setImageIcon(ImageIO.read(new File("./Assets/cross.png")));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		this.add(removeTabButton);
		this.setComponentZOrder(removeTabButton, 0);
	
		addTabButton.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				createNewSlide();
				//initialStartUp();
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
		tabPane.addRemoveTabButton(removeTabButton);
		removeTabButton.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				setAddTabButtonLocation();
				
				addTabButton.setVisible(true);
				
				
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
		tabPane.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
			
				graphicsBox.setCurrentSlide(tabPane.getSlides().get(tabPane.getSelectedIndex()));
				
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
		
		movingObject= new MovingObject();
		
		
		initialStartUp();
	}
	public void setMovingElement(PresElement p) {
		movingObject.attatchToPresElement(p);
		tabPane.getSlides().get(tabPane.getSelectedIndex()).add(movingObject);
		movingObject.setVisible(true);
		tabPane.getSlides().get(tabPane.getSelectedIndex()).setComponentZOrder(movingObject, 0);
		movingObject.repaint();
	}
	public void exitMovingElement() {
		movingObject.setVisible(false);
		tabPane.getSlides().get(tabPane.getSelectedIndex()).remove(movingObject);
	}

	
	public Slide createNewSlide() {
		Slide slide=null;
		if((tabPane.getUI().getTabBounds(tabPane, tabPane.getTabCount()-1).x
				+(2*tabPane.getUI().getTabBounds(tabPane, tabPane.getTabCount()-1).width))<tabPane.getWidth()) {
	
			slide =tabPane.addNewSlide();
			graphicsBox.addNewSlide(slide, portrait);
			
			setSizeOfSlide(slide);
			setAddTabButtonLocation();
			if((tabPane.getUI().getTabBounds(tabPane, tabPane.getTabCount()-1).x
					+(2*tabPane.getUI().getTabBounds(tabPane, tabPane.getTabCount()-1).width))>tabPane.getWidth()) {
				addTabButton.setVisible(false);
			}
			tabPane.setSelectedIndex(tabPane.getSlides().size()-1);
			graphicsBox.setCurrentSlide(slide);
		}else {
			addTabButton.setVisible(false);
		}
		return slide;
	}
	

	//BUTTONS:
	private void setAddTabButtonLocation() {
		if(tabPane.getTabCount()>0) {
		Rectangle lastTab= tabPane.getUI().getTabBounds(tabPane, tabPane.getTabCount()-1);
		addTabButton.setSize(lastTab.height);
		addTabButton.setLocation(lastTab.x+ lastTab.width+ r.x, lastTab.y+ r.x);
		}
	}
	private void setRemoveTabButtonSize() {
		if(tabPane.getTabCount()>0) {
		Rectangle tabBounds= tabPane.getUI().getTabBounds(tabPane, 0);
		removeTabButton.setSize(tabBounds.height/2);
		}
	}
	private void addNewSlideTo() {
		
	}

	private void initialStartUp() {
		presentation=new Presentation();
		graphicsBox.addNewSlide(tabPane.addNewSlide(),landscape);
		tabPane.setSelectedIndex(tabPane.getSlides().size()-1);
		graphicsBox.setCurrentSlide(tabPane.getSlides().get(tabPane.getSlides().size()-1));
		setAddTabButtonLocation();
		this.repaint();
	}
	private void setSizeOfSlide(Slide slide) {
		
		
		slide.setPointWidth(graphicsBox.getSlideLayout(slide)[0]);
		slide.setPointHeight(graphicsBox.getSlideLayout(slide)[1]);
		
		slide.setSize(slide.preferredLayoutSize(tabPane));
		
		int w= tabPane.getWidth();
		int h= tabPane.getHeight();
		slide.setLocation((w-slide.getWidth())/2, (h-slide.getHeight())/2);
	}
	

	//OVERIDED METHODS:
	public void setMarginBounds(int left, int top, int right ,int bottom) {
		super.setMarginBounds(left, top, right, bottom);
		tabPane.setBounds(r.x,r.y,this.getWidth()-(r.x+r.width),this.getHeight()-(r.y+r.height));
		setAddTabButtonLocation();
		setRemoveTabButtonSize();
		this.setComponentZOrder(addTabButton, 0);
		this.setComponentZOrder(removeTabButton, 1);
		this.setComponentZOrder(tabPane, 2);
		if(tabPane.getSlides().size()>0) {
			for(int i=0;i<tabPane.getSlides().size();i++) {
				setSizeOfSlide(tabPane.getSlides().get(i));
			}
			tabPane.changeBackgroundSize(tabPane.getWidth(), tabPane.getHeight());
		}
		
	}
	public void setMainAndSecondaryColor(Color mainC, Color secondaryC) {
		super.setMainAndSecondaryColor(new Color(255,255,255,0),secondaryC);
		tabPane.setMainAndBackgroundColor(mainC,secondaryC);
		movingObject.setBackground(secondaryC);
		
	}
	public Presentation getPresentation() {
		return presentation;
	}
	public void removeLastGraphicsOnCurrentSlide() {
		//tabPane.removeLastGraphicOnCurrentLayer();
		//tabPane.slides.get(tabPane.getSelectedIndex()).removeLastGraphicOnCurrentLayer();
		
	}
	public void replaceLastGraphicsOnCurrentSlide() {
		//tabPane.slides.get(tabPane.getSelectedIndex()).replaceLastGraphicsOnCurrentLayer();	
	}
	
	//GETTERS AND SETTERS:
	public JButton getAddTabButton() {
		return addTabButton;
	}
	public JButton getRemoveTabButton() {
		return removeTabButton;
	}	
	public Slide getCurrentSlide() {
		return tabPane.getSlides().get(tabPane.getSelectedIndex());
	}
	public Component getCurrentComponent() {
		return tabPane.getSelectedComponent();
	}
	/*
	public void paint(Graphics g) {
		tabPane.repaint();
		
	}*/
	

}
class CustomTabbedPane extends JTabbedPane{
	Color mainColor;
	Color secondaryColor;
	
	JButton addTabButton;

	CustomTabUI ui;
	protected ArrayList<Slide>slides;
	JTabbedPane tp= this;
	int removeButtonIndex;
	
	BufferedImage editingBackground;
	ArrayList<JLabel>editingBackgroundLabels;
	ImageIcon editingIcon;
	
	
	CustomTabbedPane(){
		ui= new CustomTabUI();
		this.setUI(ui);
		this.setBorder(null);
		//this.setOpaque(false);
		//slides= new ArrayList<Slide>();
		slides= new ArrayList<Slide>();
		editingBackgroundLabels= new ArrayList<JLabel>();
		removeButtonIndex=0;
		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
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
			
		});
		try {
			editingBackground= ImageIO.read(new File("./Assets/editingBackground.png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		editingIcon= new ImageIcon(editingBackground);
	
	}
	
	protected Slide addNewSlide() {
			Slide p= new Slide(this.getWidth(),this.getHeight());
			slides.add(p);
			JPanel pane= new JPanel();
			JLabel label= new JLabel();
			editingBackgroundLabels.add(label);
			label.setIcon(editingIcon);
			
			label.setLocation(0, 0);
			label.setSize(this.getWidth(),this.getHeight());
			pane.add(label);
			pane.setBackground(mainColor);
			pane.setLayout(null);
			pane.add(p);
			this.addTab(" Slide "+slides.size()+" ", pane);
			pane.setComponentZOrder(p, 0);
			pane.setComponentZOrder(label, 1);
			
			return p;
	}
	private void resetTabTitles(){
		for(int i=0;i<getTabCount();i++) {
			this.setTitleAt(i, " Slide "+(i+1)+" ");
		}
	}
	protected void addRemoveTabButton(CircleButton removeTabButton) {
		removeTabButton.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
		
					if((removeButtonIndex>0)&&(getTabCount()>1) ){
						tp.removeTabAt(removeButtonIndex-1);
						slides.remove(removeButtonIndex-1);
						tp.repaint();
						resetTabTitles();
						removeTabButton.setVisible(false);
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
			
		});
		this.addMouseMotionListener(new MouseMotionListener() {



				@Override
				public void mouseDragged(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseMoved(MouseEvent e) {
					boolean bool= false;
		
					for(int j=0;j<getTabCount();j++) {
						if(getUI().getTabBounds(tp,j).contains(e.getPoint())) {
							Rectangle tab= getUI().getTabBounds(tp, j);
							int x= tab.x+tab.width;
							int y= tab.y+tab.height;
							removeTabButton.setLocation(x, y);
							removeTabButton.setVisible(true);
							bool=true;
							removeButtonIndex=j+1;
						}
					}
					if(bool==false) {
						removeTabButton.setVisible(false);
						removeButtonIndex=0;
					}
					if(tp.getSelectedIndex()>-1) {
						//graphicsBox.setCurrentSlideIndex(tp.getSelectedIndex()+1);
					}
					
					
				}
		});
	}
	public void setMainAndBackgroundColor(Color mainColor, Color secondaryColor) {
		this.mainColor= mainColor;
		this.secondaryColor= secondaryColor;
		this.repaint();
	}
	

	public void paint(Graphics g) {
		Graphics2D g2= (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		for(int i=0;i<this.getTabCount();i++) {
		if(mainColor!=null) {
			g2.setColor(mainColor);
			Rectangle r= ui.getTabBounds(this, i);
			int padding= 10;
			Rectangle rt= new Rectangle(r.x+padding/2,r.y,r.width-padding,r.height-padding/4);
			
			if((this.getSelectedIndex())==i) {
				g2.fillRoundRect(rt.x, rt.y, rt.width, rt.height, rt.height, rt.height);
				g2.fillRect(rt.x, rt.y+(rt.height/2), rt.width, rt.height);
				g2.setColor(Color.white);
				g2.drawString(this.getTitleAt(i),rt.x+padding/2, rt.height);
				if(slides.size()>0) {
					this.getSelectedComponent().repaint();
					slides.get(i).repaint();
				}
			
			}
			else {
				
				g2.fillRoundRect(rt.x, rt.y, rt.width, rt.height, rt.height, rt.height-padding/4);
				g2.setColor(Color.white);
				g2.drawString(this.getTitleAt(i), rt.x+padding/2,rt.height-padding/4);
			
			}
		}}
		g2.dispose();
	}
	public ArrayList<Slide> getSlides(){
		return slides;
	}
	public void changeBackgroundSize(int width, int height) {
		editingIcon= new ImageIcon(editingBackground.getSubimage(0, 0, width, height));
		for(int i=0;i<editingBackgroundLabels.size();i++) {
			editingBackgroundLabels.get(i).setIcon(editingIcon);
			editingBackgroundLabels.get(i).setSize(width, height);
			editingBackgroundLabels.get(i).setLocation(0, 0);
		}
	}


}





class CustomTabUI extends BasicTabbedPaneUI{
	Color transparent= new Color(255,255,255,255);
	CustomTabUI(){
	
	}
	public void installDefaults() {
		super.installDefaults();
		this.darkShadow= transparent;
		this.highlight=transparent;
		this.lightHighlight= transparent;
		this.shadow= transparent;
		this.createScrollButton(EAST);
	}
	
	
	public void paintTabArea(Graphics g, int tabPlacement,int selectedIndex) {
		//super.paintTabArea( g,  tabPlacement, selectedIndex);
		
	}
}


class MovingObject extends JPanel{
	Color main;
	Color secondary;
	JButton top,left,bottom,right;
	JButton topLeft, bottomLeft,topRight,bottomRight;
	PresElement presElement;
	Point mousePos;
	MovingObject(){
		this.setOpaque(false);
		this.setLayout(null);
		top= new JButton() {
			public void paint(Graphics g) {
				Graphics2D g2= (Graphics2D)g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setColor(main);
				Path2D p = new Path2D.Double();
				p.moveTo(0, this.getHeight());
				p.lineTo(this.getWidth()/2,0);
				p.lineTo(this.getWidth(), this.getHeight());
				p.lineTo(this.getHeight(), 0);
				g2.fill(p);
				g2.dispose();
			}
		};
		this.add(top);
		bottom= new JButton() {
			public void paint(Graphics g) {
				Graphics2D g2= (Graphics2D)g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setColor(main);
				Path2D p = new Path2D.Double();
				p.moveTo(0,0);
				p.lineTo(this.getWidth()/2,this.getHeight());
				p.lineTo(this.getWidth(), 0);
				p.lineTo(0, 0);
				g2.fill(p);
				g2.dispose();			
			}
		};
		this.add(bottom);
		right= new JButton() {
			public void paint(Graphics g) {
				Graphics2D g2= (Graphics2D)g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setColor(main);
				Path2D p = new Path2D.Double();
				p.moveTo(0,0);
				p.lineTo(this.getWidth(),this.getHeight()/2);
				p.lineTo(0, this.getHeight());
				p.lineTo(0, 0);
				g2.fill(p);
				g2.dispose();
			}
		};
		this.add(right);
		left= new JButton() {
			public void paint(Graphics g) {
				Graphics2D g2= (Graphics2D)g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setColor(main);
				Path2D p = new Path2D.Double();
				p.moveTo(this.getWidth(),0);
				p.lineTo(0,this.getHeight()/2);
				p.lineTo(this.getWidth(), this.getHeight());
				p.lineTo(this.getWidth(), 0);
				g2.fill(p);
				g2.dispose();
				
			}
		};
		this.add(left);
		topRight= new JButton() {
			public void paint(Graphics g) {
				Graphics2D g2= (Graphics2D)g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setColor(main);
				Path2D p = new Path2D.Double();
				p.moveTo(0,0);
				p.lineTo(this.getWidth(),0);
				p.lineTo(this.getWidth(), this.getHeight());
				p.lineTo(0,0);
				g2.fill(p);
				g2.dispose();	
			}
		};
		this.add(topRight);
		bottomRight= new JButton() {
			public void paint(Graphics g) {
				Graphics2D g2= (Graphics2D)g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setColor(main);
				Path2D p = new Path2D.Double();
				p.moveTo(this.getWidth(),0);
				p.lineTo(this.getWidth(),this.getHeight());
				p.lineTo(0, this.getHeight());
				p.lineTo(this.getWidth(),0);
				g2.fill(p);
				g2.dispose();	
			}
		};
		this.add(bottomRight);
		bottomLeft= new JButton() {
			public void paint(Graphics g) {
				Graphics2D g2= (Graphics2D)g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setColor(main);
				Path2D p = new Path2D.Double();
				p.moveTo(0,0);
				p.lineTo(0,this.getHeight());
				p.lineTo(this.getWidth(), this.getHeight());
				p.lineTo(0,0);
				g2.fill(p);
				g2.dispose();	
			}
		};
		this.add(bottomLeft);
		topLeft= new JButton() {
			public void paint(Graphics g) {
				Graphics2D g2= (Graphics2D)g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setColor(main);
				Path2D p = new Path2D.Double();
				p.moveTo(0,0);
				p.lineTo(this.getWidth(),0);
				p.lineTo(0, this.getHeight());
				p.lineTo(0,0);
				g2.fill(p);
				g2.dispose();	
			}
		};
		this.add(topLeft);
		bottomRight.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
				
					if(presElement!=null) {
						changeSize(0,0,e.getX()-mousePos.x,e.getY()-mousePos.y);
					}
				
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				mousePos=e.getPoint();
				
			}
			
		});
		
		
	}
	public void paint(Graphics g) {
		Graphics2D g2= (Graphics2D)g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(Color.white);
		g2.drawRect(15, 15, this.getWidth()-30, this.getHeight()-30);
		g2.setColor(main);
		g2.drawRect(14, 14, this.getWidth()-28, this.getHeight()-28);
		g2.dispose();
		super.paint(g);
	}
	public void attatchToPresElement(PresElement presElement) {
		this.setSize(presElement.getComponent().getWidth()+30, presElement.getComponent().getHeight()+30);
		this.setLocation(presElement.getComponent().getX()-15, presElement.getComponent().getY()-15);
		this.presElement=presElement;
		topRight.setBounds(this.getWidth()-10,0,10,10);
		topLeft.setBounds(0,0,10,10);
		bottomRight.setBounds(this.getWidth()-10, this.getHeight()-10, 10, 10);
		bottomLeft.setBounds(0, this.getHeight()-10, 10, 10);
		
	}
	public void setBackground(Color color) {
		super.setBackground(color);
		main= color;
	}
	private void changeSize(int deltaX, int deltaY, int deltaWidth, int deltaHeight ) {
		JComponent c= presElement.getComponent();
		c.setBounds(c.getX()+deltaX, c.getY()+deltaY, c.getWidth()-deltaWidth, c.getHeight()+deltaHeight);
		this.setBounds(this.getX()+deltaX, this.getY()+deltaY, this.getWidth()+deltaWidth, this.getHeight()+deltaHeight);
		topRight.setBounds(this.getWidth()-10,0,10,10);
		topLeft.setBounds(0,0,10,10);
		bottomRight.setBounds(this.getWidth()-10, this.getHeight()-10, 10, 10);
		bottomLeft.setBounds(0, this.getHeight()-10, 10, 10);
		this.repaint();
		c.repaint();
	}
}
