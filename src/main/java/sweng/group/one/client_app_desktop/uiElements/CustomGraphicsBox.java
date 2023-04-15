package sweng.group.one.client_app_desktop.uiElements;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import sweng.group.one.client_app_desktop.media.GraphicsElement;
import sweng.group.one.client_app_desktop.media.ImageElement;
import sweng.group.one.client_app_desktop.media.TextElement;
import sweng.group.one.client_app_desktop.presentation.PresElement;
import sweng.group.one.client_app_desktop.presentation.Slide;

public class CustomGraphicsBox extends UploadSceneComponent {
	Slide currentSlide;
	//ArrayList<PresElement>presElements;
	ArrayList<SlidePresElementsView>slides;	
	JScrollPane scrollPane;

	
	JPanel upperPanel;
	JPanel lowerPanel;
	
	JButton addLayer;
	JButton deleteLayer;
	
	public CustomGraphicsBox() {
		this.setLayout(null);
		slides= new ArrayList<SlidePresElementsView>();
		/*
		 *  These panels seperate the scroll view and the buttons
		 */
		upperPanel = new JPanel();
		//upperPanel.setOpaque(false);
		upperPanel.setBackground(Color.BLUE);

		this.add(upperPanel);
		lowerPanel= new JPanel();
		lowerPanel.setOpaque(false);
		
		this.add(lowerPanel);
		lowerPanel.setLayout(null);
		upperPanel.setLayout(null);
		
		/*
		 *  scroll stuff
		 */
		
		scrollPane= new  JScrollPane();
		upperPanel.add(scrollPane);
		
	
		//scrollPane.setBorder(null);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setVisible(true);
		
		/*
		 *  buttons
		 */
		addLayer= new JButton("ADD");
		lowerPanel.add(addLayer);
		addLayer.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				addGraphicLayer();
				
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
		deleteLayer=new JButton("DELETE");
		lowerPanel.add(deleteLayer);	
		
		deleteLayer.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				System.out.println("A");
				if(getSelectedLayer()!=null) {
					System.out.println("B");
					for(int i=0;i<slides.size();i++) {
						System.out.println("C");
						if(slides.get(i).getSlide()==currentSlide) {
							System.out.println("D");
							slides.get(i).removeLayer(getSelectedLayer());
							currentSlide.repaint();
							//crollPane.getViewport().repaint();
						}
					}
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
	}
	
	/*
	 *  ADD LAYER TYPES:
	 */
	public void addGraphicLayer() {
		Slide slide= currentSlide;
		Point p=new Point(1,1);
		int width= slide.getPointWidth()-2;
		int height= slide.getPointHeight()-2;
		int duration =0;
		for(int i=0;i<slides.size();i++) {
			if(slides.get(i).getSlide()==slide) {
				GraphicsElement g= new GraphicsElement(p,width,height,duration,slide);
				slides.get(i).addLayer(g);
			}
		}
		//this.repaint();
	}
	public void addImageLayer(BufferedImage image) {
		Slide slide= currentSlide;
		Point p=new Point(1,1);
		int width= slide.getPointWidth()-2;
		int height= slide.getPointHeight()-2;
		if(width>height) {
			width=height;
		}else {
			height=width;
		}
		int duration =0;
		for(int i=0;i<slides.size();i++) {
			if(slides.get(i).getSlide()==slide) {			
				slides.get(i).addLayer(new ImageElement(p,width,height,duration,slide,image));		
			}
		}
	}
	public void addTextLayer(String text, String fontName, int fontSizePt, Color colour, float duration, Point pos, int width,
			int height, Slide slide) {
		for(int i=0;i<slides.size();i++) {
			if(slides.get(i).getSlide()==slide) {
				slides.get(i).addLayer(new TextElement(text, fontName,fontSizePt,colour,duration, pos,width,height,slide));
			}
		}
	}

	public void addNewSlide(Slide slide, int[] layoutRatio) {
		if(slide!=null) {
			SlidePresElementsView newPresView= new SlidePresElementsView(slide, layoutRatio);
			newPresView.setColours(main,secondary);
			slides.add(newPresView);
		}
	}
	public void removeSlide(Slide slide) {
		for(int i=0;i<slides.size();i++) {
			if(slides.get(i).getSlide()==slide) {
				slides.remove(i);
			}
		}
	}
	public void setCurrentSlide(Slide slide) {
		for(int i=0;i<slides.size();i++) {
			if(slides.get(i).getSlide()==slide) {
				this.currentSlide= slide;
				scrollPane.setViewportView(slides.get(i));
				scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
				slides.get(i).setVisible(true);
				
				slides.get(i).setColours(main,secondary);
				
			}
		}	
	}
	public void addPresElementTo(PresElement element, Slide slide) {
		for(int i=0;i<slides.size();i++) {
			if(slides.get(i).getSlide()==slide) {
				slides.get(i).addLayer(element);
			}
		}	
	}
	
	
	//OVERIDED METHODS:
	public void setMainAndSecondaryColor(Color mainC, Color secondaryC) {
		super.setMainAndSecondaryColor(mainC, secondaryC);
		scrollPane.setBackground(mainC);
		for(int i=0;i<slides.size();i++) {
			slides.get(i).setColours(mainC,secondaryC);
		}
		
		scrollPane.repaint();
		
	}
	public void setMarginBounds(int x,int y, int width, int height) {
		super.setMarginBounds(x, y, width, height);
		int newX= x+curvatureRadius;
		int newY= y+curvatureRadius
				;
		int newWidth= this.getWidth()-x-width-(2*curvatureRadius);
		int newHeight= this.getHeight()-y-height-(2*curvatureRadius);
		upperPanel.setBounds(newX, newY, newWidth, 4*(newHeight/5));
		lowerPanel.setBounds(newX, newY+(4*(newHeight/5)), newWidth, newHeight/5);
		scrollPane.setBounds(0, 0, upperPanel.getWidth(), upperPanel.getHeight());
		addLayer.setBounds(0, 0, lowerPanel.getWidth()/2, lowerPanel.getHeight());
		deleteLayer.setBounds(addLayer.getWidth(), 0,addLayer.getWidth(), addLayer.getHeight());
		
	}
	public void paint(Graphics g) {
		super.paint(g);
	}
	//SETTERS AND GETTERS
	public JButton getAddLayerButton() {
		return addLayer;
	}
	public JButton getDeleteLayerButton() {
		return deleteLayer;
	}

	public int[] getSlideLayout(Slide slide) {
		for(int i=0;i<slides.size();i++) {
			if(slides.get(i).getSlide()==slide) {
				return slides.get(i).getLayoutRatio();
			}
		}
		return null;
	}
	public SlidePresElementsView getScrollPaneViewPort() {
		for(int i=0;i<slides.size();i++) {
			if(currentSlide==slides.get(i).getSlide()) {
				return slides.get(i);
			}
		}
		return null;
	}
	public PresElement getSelectedLayer() {
		for(int i=0;i<slides.size();i++) {
			if(currentSlide==slides.get(i).getSlide()) {
				if(slides.get(i).getSelectedLayer()!=null) {
					return slides.get(i).getSelectedLayer();
				}else {
					return null;
				}
			}
		}
		return null;
	}
	
}




class SlidePresElementsView extends JPanel{
	Slide slide;
	ArrayList<ViewRect>viewRects;
	Color boxColors;
	int[] layoutRatio;
	JPanel panel= this;
	ViewRect selectedLayer;
	public SlidePresElementsView(Slide slide, int[] ratio) {
		this.slide= slide;;
		layoutRatio= ratio;
		viewRects= new ArrayList<ViewRect>();
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
	}
	public void addLayer(PresElement element) {
		slide.add(element);
		ViewRect viewRect= new ViewRect(slide,element);
		for(int i=0;i<viewRects.size();i++) {
			viewRects.get(i).setClicked(false);
		}
		viewRect.setClicked(true);
		selectedLayer= viewRect;
		viewRects.add(viewRect);
		this.add(viewRect);
		
		viewRect.setPreferredSize(new Dimension(this.getWidth(),50));
		viewRect.setColours(boxColors, Color.white);
		this.repaint();
		this.validate();
	
			viewRect.addMouseListener(new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mousePressed(MouseEvent e) {
				
					
					for(int i=0;i<viewRects.size();i++) {
						if(e.getComponent()==viewRects.get(i)) {
							
							if(viewRects.get(i).isClicked()==false) {
								viewRects.get(i).setClicked(true);
								selectedLayer= viewRects.get(i);
							}else {
								viewRects.get(i).setClicked(false);
								selectedLayer= null;
							}
							
						}else {
							viewRects.get(i).setClicked(false);
							
						}
						viewRects.get(i).repaint();
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
			
		}
	public void removeLayer(PresElement el) {
		ViewRect vr=null;
		int index=0;
		for(int i=0;i<viewRects.size();i++) {
			if(viewRects.get(i).getByPresElement()==el) {
				vr= viewRects.get(i);
				index=i;
			}
		}
		if(vr!=null) {
			System.out.println("AAAAAAAAAAA");
			viewRects.remove(vr);
			this.remove(vr);
			this.repaint();
			slide.getElements().remove(el);
			slide.validate();
			slide.remove(index);
			slide.repaint();
			if(viewRects.size()>0) {
				selectedLayer= viewRects.get(viewRects.size()-1);
			}
		}
	}
	
	public Slide getSlide() {
		return slide;
	}
	public ArrayList<ViewRect> getViewRects() {
		return viewRects;
	}
	public int[] getLayoutRatio() {
		return layoutRatio;
	}
	public void setColours(Color main,Color secondary) {
		boxColors= secondary;
		this.setBackground(main);
	}
	public PresElement getSelectedLayer() {
		if(selectedLayer.getByPresElement()==null) {
			return null;
		}else {
			return selectedLayer.getByPresElement();
		}
	}
	
}





class ViewRect extends JPanel{
	Color background;
	Color clickedColor;
	Color text;
	String type;
	String title;
	PresElement presElement;
	Slide slide;
	Font font;
	boolean clicked;
	public ViewRect(Slide slide,PresElement presEl) {
		this.presElement= presEl;
		type= presEl.getType();
		this.setOpaque(false);
		this.setBorder(null);
		int counter=0;
		for(int i=0;i<slide.getElements().size();i++) {
			if(slide.getElements().get(i).getType()==type){
				counter++;
			}
		}
		title= type.toLowerCase()+ " "+counter;
		clicked=false;
	}
	//OVERiDED:
	public void paint(Graphics g) {
		Graphics2D g2= (Graphics2D)g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		if(clicked==true) {
			g2.setColor(clickedColor);
		}else {
			g2.setColor(background);
		}
		g2.fillRoundRect(0, 5, this.getWidth(),this.getHeight()-5,10,10);
		g2.setColor(text);
		g2.setFont(font);
		g2.drawString(title, 5, 3*(this.getHeight()/4));
		g2.dispose();
		super.paint(g);
	}
	//SETTERS AND GETTERS
	public void setColours(Color backgroundColor, Color textColor) {
		background= backgroundColor;
		text=textColor;
		clickedColor= new Color(background.getBlue()+20,background.getGreen()+20,background.getRed()+20);
		repaint();
	}
	public String getType() {
		return type;
	}
	public void setClicked(boolean bool) {
		clicked= bool;
	}
	public PresElement getByPresElement() {
		return presElement;
	}
	public void setPreferredSize(Dimension d) {
		super.setPreferredSize(d);
		font = new Font(null, Font.PLAIN,
				d.height/2);
	}
	public boolean isClicked() {
		return clicked;
	}
	public String getTitle() {
		return title;
	}
}
