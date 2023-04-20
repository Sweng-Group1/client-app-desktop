package sweng.group.one.client_app_desktop.uiElements;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class CustomTagBox extends UploadSceneComponent{
	JPanel upperPanel;
	JPanel lowerPanel;
	
	JTextField tagInput;
	JScrollPane tagCollection;
	ArrayList<String>tags;
	ArrayList<Tag>tagObjects;

	
	public CustomTagBox() {
		initialise();
	}
	private void initialise() {
		this.setLayout(null);
		
		tags= new ArrayList<String>();
		tagObjects= new ArrayList<Tag>();
		
		upperPanel= new JPanel() {
			public void paint(Graphics g) {
				Graphics2D g2= (Graphics2D)g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setColor(new Color(secondary.getRed(),secondary.getGreen(),secondary.getBlue(),100));
				//g2.setColor(new Color(255,255,255,100));
				g2.fillArc(0,0, curvatureRadius, curvatureRadius, 90, 90);
				g2.fillArc(this.getWidth()-curvatureRadius, 0, curvatureRadius, curvatureRadius, 0, 90);
				g2.fillRect(curvatureRadius/2, 0, this.getWidth()-(curvatureRadius),curvatureRadius/2);
				g2.fillRect(0,curvatureRadius/2, this.getWidth(),this.getHeight()-(curvatureRadius/2));
				g2.dispose();
				super.paint(g);
			}
		};
		
		lowerPanel= new JPanel();
		lowerPanel.setLayout(null);
		this.add(upperPanel);
		this.add(lowerPanel);
		upperPanel.setOpaque(false);
		lowerPanel.setOpaque(false);
		
		tagInput = new JTextField();
		upperPanel.add(tagInput);
		tagInput.setCaretColor(Color.white);
		tagInput.setText("Add tags...");
		tagInput.setSelectedTextColor(Color.white);
		tagInput.setDisabledTextColor(Color.white);
		tagInput.setForeground(Color.white);
		tagInput.setOpaque(false);
		tagInput.setBorder(null);
		upperPanel.setLayout(null);
		tagInput.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				tagInput.setText("");
				
			}

			@Override
			public void focusLost(FocusEvent e) {
				tagInput.setText("Add tags...");
				
			}
			
		});
		tagInput.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyChar()==KeyEvent.VK_ENTER) {
					if((tagInput.getText()!="")||(tagInput.getText()!="Add tags...")) {
						Tag tag= new Tag(secondary,tagInput.getText());
						tagObjects.add(tag);
						lowerPanel.add(tag);
						resizeTags();
						System.out.println("ADD TAG");
						tagInput.setText("");
					}
				}
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
	}
	private void resizeTags() {
		int rowHeight= this.getHeight()/6;
		int row=0;
		int xVal=0;
		int gap= 3;
		for(int i=0;i<tagObjects.size();i++) {
			Tag tag= tagObjects.get(i);
			tag.setSize(this.getWidth()/2,this.getHeight()/8);
			if(xVal+tag.getWidth()+(2*gap)>this.getWidth()) {
				row++;
				xVal=0;
			}
			tag.setLocation(xVal+gap, row*rowHeight+gap);
			xVal= tag.getX()+ tag.getWidth();
		}
		
	}
	
	
	//OVERIDED METHODS
	public void setMarginBounds(int r, int t, int l, int b) {
		super.setMarginBounds(r,t,l,b);
		upperPanel.setBounds(r, t, this.getWidth()-l-r, curvatureRadius*3);
		tagInput.setBounds(curvatureRadius/2,curvatureRadius/2,upperPanel.getWidth()-(curvatureRadius), curvatureRadius+(curvatureRadius/2));
		lowerPanel.setBounds(r, t+(curvatureRadius*3), this.getWidth()-l-r, this.getHeight()-(t+(curvatureRadius*3)));
	}
	//SETTERS AND GETTERS
	public ArrayList<String> getTags(){
		tags.clear();
		for(int i=0;i<tagObjects.size();i++) {
			tags.add(tagObjects.get(i).getTag());
		}
		return tags;
	}
	public void addTag(String tag) {
		tags.add(tag);
	}
}

class Tag extends JPanel{
	String tagName;
	CircleButton deleteTagButton;
	Color background;
	Font font;
	public Tag(Color background,String tagName) {
		this.tagName=tagName;
		this.background=background;
		this.setOpaque(false);
		deleteTagButton= new CircleButton();
		try {
			deleteTagButton.setImageIcon(ImageIO.read(new File("./Assets/cross.png")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.add(deleteTagButton);
	}
	public void paint(Graphics g) {
		Graphics2D g2= (Graphics2D)g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(background);
		g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), this.getHeight()/2, this.getHeight()/2);
		g2.setColor(Color.white);
		g2.setFont(font);
		g2.drawString("#"+tagName, this.getHeight()/2, this.getHeight()-(this.getHeight()/4));
	}
	public void setSize(int w, int h) {
		font= new Font("",Font.PLAIN,h-(h/4));
		String title= "#"+tagName;
		int newWidth=getFontMetrics(font).stringWidth(title)+h;
	 
		super.setSize(newWidth,h);
	}
	public String getTag() {
		return tagName;
	}
}
