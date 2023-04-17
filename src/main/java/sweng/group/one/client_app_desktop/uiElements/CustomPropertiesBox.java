package sweng.group.one.client_app_desktop.uiElements;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BoxLayout;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import sweng.group.one.client_app_desktop.presentation.PresElement;



public class CustomPropertiesBox extends UploadSceneComponent {
	CustomGraphicsBox graphicsBox;
	
	JTabbedPane tabbedPane;
	
	ElementTab elementTab;
	PresElement element;
	//ImageTab imageTab;
	//GraphicTab graphicTab;
	//TextTab textTab;
	
	public CustomPropertiesBox() {
		
		initialise();
	}
	private void initialise() {
		this.setLayout(null);
		tabbedPane= new JTabbedPane();
		this.add(tabbedPane);
		elementTab= new ElementTab();
	
	}
	public void setGraphicsBoxListener(CustomGraphicsBox graphicsBox) {
		graphicsBox.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				setManipulatorFor(graphicsBox.getSelectedLayer());
				
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
	public void setManipulatorFor(PresElement element) {
		this.element=element;
		String type= element.getType();
		switch(type){
		case "GRAPHIC":
			tabbedPane.removeAll();
			tabbedPane.addTab("Element",elementTab);
			elementTab.setSize(tabbedPane.getWidth(), tabbedPane.getHeight());
			elementTab.getXPosText().setText(String.valueOf(element.getPosPoint().y));
			elementTab.getYPosText().setText(String.valueOf(element.getPosPoint().x));
			elementTab.getWidthText().setText(String.valueOf(element.getWidth()));
			elementTab.getHeightText().setText(String.valueOf(element.getHeight()));
			elementTab.getDurationText().setText(String.valueOf(element.getDuration()));
			elementTab.setElementToChange(element);
			break;
		case "IMAGE":
			tabbedPane.removeAll();
			tabbedPane.addTab("Element",elementTab);
			elementTab.setSize(tabbedPane.getWidth(), tabbedPane.getHeight());
			elementTab.getXPosText().setText(String.valueOf(element.getPosPoint().y));
			elementTab.getYPosText().setText(String.valueOf(element.getPosPoint().x));
			elementTab.getWidthText().setText(String.valueOf(element.getWidth()));
			elementTab.getHeightText().setText(String.valueOf(element.getHeight()));
			elementTab.getDurationText().setText(String.valueOf(element.getDuration()));
			System.out.println("ABC");
			break;
		case "CIRCLE":
			tabbedPane.removeAll();
			tabbedPane.addTab("Element",elementTab);
			elementTab.setSize(tabbedPane.getWidth(), tabbedPane.getHeight());
			elementTab.getXPosText().setText(String.valueOf(element.getPosPoint().y));
			elementTab.getYPosText().setText(String.valueOf(element.getPosPoint().x));
			elementTab.getWidthText().setText(String.valueOf(element.getWidth()));
			elementTab.getHeightText().setText(String.valueOf(element.getHeight()));
			elementTab.getDurationText().setText(String.valueOf(element.getDuration()));
			break;
		case "RECTANGLE":
			tabbedPane.removeAll();
			tabbedPane.addTab("Element",elementTab);
			elementTab.setSize(tabbedPane.getWidth(), tabbedPane.getHeight());
			elementTab.getXPosText().setText(String.valueOf(element.getPosPoint().y));
			elementTab.getYPosText().setText(String.valueOf(element.getPosPoint().x));
			elementTab.getWidthText().setText(String.valueOf(element.getWidth()));
			elementTab.getHeightText().setText(String.valueOf(element.getHeight()));
			elementTab.getDurationText().setText(String.valueOf(element.getDuration()));
			break;
		
		}
	}
	
	//OVERIDED METHODS
	public void setMarginBounds(int r,int t,int l,int b) {
		super.setMarginBounds(r, t, l, b);
		tabbedPane.setBounds(r,t, this.getWidth()-r-l, this.getHeight()-(curvatureRadius/2));
		elementTab.setCurvatureRadius(curvatureRadius);
	}
	public void paint(Graphics g) {
		Graphics2D g2= (Graphics2D)g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(main);
		g2.fillRect(r.x, r.y, this.getWidth()-r.x-r.width, (curvatureRadius*2)-r.y);
		g2.dispose();
		super.paint(g);
	}
}
class ElementTab extends JPanel{
	Color transparent= new Color(255,255,255,0);
	JPanel xPosPane;
	JTextField xPos;
	
	JPanel yPosPane;
	JTextField yPos;
	
	JPanel widthPane;
	JTextField widthPos;
	
	JPanel heightPane;
	JTextField heightPos;
	
	JPanel durationPane;
	JTextField duration;
	
	Font font;
	int curvatureRadius;
	
	PresElement element;
	public ElementTab() {
		this.setOpaque(false);
		this.setLayout(null);
		
		this.curvatureRadius=curvatureRadius;
		xPosPane= new JPanel() {
			public void paint(Graphics g) {
				Graphics2D g2= (Graphics2D)g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setFont(font);
				g2.setColor(Color.white);
				g2.drawString("X pos: ", 0, this.getHeight()-(this.getHeight()/4));
				g2.dispose();
				super.paint(g);
			}
		};
		xPosPane.setOpaque(false);
		xPos= new JTextField();
		xPos.setOpaque(false);
		xPos.setBackground(transparent);
		xPos.setForeground(Color.white);
		xPos.setCaretColor(Color.white);
		this.add(xPosPane);
		this.add(xPos);
		xPos.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				if(element!=null) {
					if(e.getKeyChar()==KeyEvent.VK_ENTER) {
						element.setX(Integer.valueOf(xPos.getText()));
						xPos.setText(String.valueOf(element.getPosPoint().x));
						element.getComponent().setLocation(Integer.valueOf(xPos.getText()),element.getComponent().getY());
						element.getComponent().validate();
						element.getComponent().repaint();
					}
				}
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		
		yPosPane= new JPanel() {
			public void paint(Graphics g) {
				Graphics2D g2= (Graphics2D)g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setFont(font);
				g2.setColor(Color.white);
				g2.drawString("Y pos: ", 0, this.getHeight()-(this.getHeight()/4));
				g2.dispose();
				super.paint(g);
			}
		};
		yPosPane.setOpaque(false);
		yPos= new JTextField();
		yPos.setOpaque(false);
		yPos.setBackground(transparent);
		yPos.setForeground(Color.white);
		yPos.setCaretColor(Color.white);
		this.add(yPosPane);
		this.add(yPos);
		yPos.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				if(element!=null) {
					if(e.getKeyCode()==KeyEvent.VK_ENTER) {
						element.setY(Integer.valueOf(yPos.getText()));
						yPos.setText(String.valueOf(element.getPosPoint().y));
						element.getComponent().setLocation(element.getComponent().getY(),Integer.valueOf(yPos.getText()));
						element.getComponent().validate();
						element.getComponent().repaint();
					}
				}
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
	
		
		
		widthPane= new JPanel() {
			public void paint(Graphics g) {
				Graphics2D g2= (Graphics2D)g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setFont(font);
				g2.setColor(Color.white);
				g2.drawString("Width: ", 0, this.getHeight()-(this.getHeight()/4));
				g2.dispose();
				super.paint(g);
			}
		};
		widthPane.setOpaque(false);
		widthPos= new JTextField();
		widthPos.setOpaque(false);
		widthPos.setBackground(transparent);
		widthPos.setForeground(Color.white);
		widthPos.setCaretColor(Color.white);
		this.add(widthPos);
		this.add(widthPane);
		widthPos.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				if(element!=null) {
					if(e.getKeyCode()==KeyEvent.VK_ENTER) {
						element.setWidth(Integer.valueOf(widthPos.getText()));
						widthPos.setText(String.valueOf(element.getWidth()));
						element.getComponent().setSize(Integer.valueOf(widthPos.getText()),element.getComponent().getWidth());
						element.getComponent().validate();
						element.getComponent().repaint();
					}
				}
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});

		heightPane= new JPanel() {
			public void paint(Graphics g) {
				Graphics2D g2= (Graphics2D)g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setFont(font);
				g2.setColor(Color.white);
				g2.drawString("Height: ", 0, this.getHeight()-(this.getHeight()/4));
				g2.dispose();
				super.paint(g);
			}
		};
		heightPane.setOpaque(false);
		heightPos= new JTextField();
		heightPos.setOpaque(false);
		heightPos.setBackground(transparent);
		heightPos.setForeground(Color.white);
		heightPos.setCaretColor(Color.white);
		this.add(heightPos);
		this.add(heightPane);
		heightPos.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				if(element!=null) {
					if(e.getKeyCode()==KeyEvent.VK_ENTER) {
						element.setHeight(Integer.valueOf(heightPos.getText()));
						heightPos.setText(String.valueOf(element.getHeight()));
						element.getComponent().setSize(element.getComponent().getWidth(),Integer.valueOf(heightPos.getText()));
						element.getComponent().validate();
						element.getComponent().repaint();
					}
				}
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		
		durationPane= new JPanel() {
			public void paint(Graphics g) {
				Graphics2D g2= (Graphics2D)g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setFont(font);
				g2.setColor(Color.white);
				g2.drawString("Duration: ", 0, this.getHeight()-(this.getHeight()/4));
				g2.dispose();
				super.paint(g);
			}
		};
		durationPane.setOpaque(false);
		duration= new JTextField();
		duration.setOpaque(false);
		duration.setBackground(transparent);
		duration.setForeground(Color.white);
		duration.setCaretColor(Color.white);
		this.add(durationPane);
		this.add(duration);
		duration.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				if(element!=null) {
					if(e.getKeyCode()==KeyEvent.VK_ENTER) {
						element.setDuration(Integer.valueOf(duration.getText()));
						duration.setText(String.valueOf(element.getDuration()));
						element.getComponent().validate();
						element.getComponent().repaint();
					}
				}
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
	}
	public JTextField getXPosText() {
		return xPos;
	}
	public JTextField getYPosText() {
		return yPos;
	}
	public JTextField getWidthText() {
		return widthPos;
	}
	public JTextField getHeightText() {
		return heightPos;
	}
	public JTextField getDurationText() {
		return duration;
	}
	public void setCurvatureRadius(int curvatureRadius) {
		this.curvatureRadius=curvatureRadius;
	}
	public void setElementToChange(PresElement element) {
		this.element=element;
	}
	public void setSize(int width, int height) {
		super.setSize(width,height);
		System.out.println("AAAAA");
		int w= this.getWidth();
		int h= this.getHeight();
		font= new Font("",Font.PLAIN,curvatureRadius);
		int row= (h-(curvatureRadius*10))/6;
		
		xPosPane.setBounds(0,0,w/2,curvatureRadius*2);
		xPos.setBounds(w/2,0,w/2,curvatureRadius*2);
		
		yPosPane.setBounds(0,row,w/2,curvatureRadius*2);
		yPos.setBounds(w/2,row,w/2,curvatureRadius*2);
		
		
		widthPane.setBounds(0,row*2,w/2,curvatureRadius*2);
		widthPos.setBounds(w/2,row*2,w/2,curvatureRadius*2);
		
		heightPane.setBounds(0,row*3,w/2,curvatureRadius*2);
		heightPos.setBounds(w/2,row*3,w/2,curvatureRadius*2);
		
		durationPane.setBounds(0,row*4,w/2,curvatureRadius*2);
		duration.setBounds(w/2,row*4,w/2,curvatureRadius*2);
		this.validate();
	}
	
}
class ElementColorManipulator extends JPanel{
	JColorChooser colorChooser;
	public ElementColorManipulator() {
		this.setOpaque(false);
		colorChooser= new JColorChooser();
		this.add(colorChooser);
	}
}



