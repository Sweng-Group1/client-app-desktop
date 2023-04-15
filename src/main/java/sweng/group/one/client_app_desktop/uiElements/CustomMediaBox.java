package sweng.group.one.client_app_desktop.uiElements;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;

public class CustomMediaBox extends UploadSceneComponent{
	CustomGraphicsBox graphicsBox;
	boolean addMediaIconIsVisible;	
	CustomMediaBox mediaBox= this;
	JPopupMenu popup;
	JFileChooser fileExplorer;
	ArrayList<MediaElementIcon>icons;
	JButton addMediaButton;
	
	boolean isElementSelected;
	MediaElementIcon selectedMediaElement;
	URL selectedMediaURL;
	
	public CustomMediaBox(CustomGraphicsBox graphicsBox) {
		this.graphicsBox= graphicsBox;
		this.setLayout(null);
		addMediaIconIsVisible=true;
		icons= new ArrayList<MediaElementIcon>();
		fileExplorer= new JFileChooser();
		popup= new JPopupMenu();
		popup.add(fileExplorer);
		popup.setVisible(false);
		isElementSelected=false;
		//fileExplorer.showOpenDialog(popup);
		
		
		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if(icons.size()==0) {
					int returnVal= fileExplorer.showOpenDialog(popup);
					if(returnVal==JFileChooser.APPROVE_OPTION) {
						File selectedFile= fileExplorer.getSelectedFile();
						if(selectedFile.getAbsolutePath().endsWith(".png")||
								(selectedFile.getAbsolutePath().endsWith(".jpeg"))) {
							System.out.println("File selected");
							try {
								System.out.println("File A");
								icons.add(new ImageElementIcon(selectedFile.toURI().toURL(),ImageIO.read(selectedFile)));
								System.out.println("File B");
								setSizeOfIcons(r.x+curvatureRadius,r.y+curvatureRadius,
										mediaBox.getWidth()-r.x-r.width-(2*curvatureRadius),
										mediaBox.getHeight()-r.y-r.height-(2*curvatureRadius));
								mediaBox.repaint();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					}
				}
				for(int i=0;i<icons.size();i++) {
					MediaElementIcon icon= icons.get(i);
					if((e.getX()>icon.getX()) && (e.getX()<(icon.getX()+icon.getWidth()))) {
						if((e.getY()>icon.getY())&&(e.getY()<(icon.getY()+icon.getHeight()))) {
							selectedMediaElement=icons.get(i);
							selectedMediaURL= icons.get(i).getURL();
							isElementSelected=true;
							System.out.println(i+" Element is selected");
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
		addMediaButton= new JButton() {
			public void paint(Graphics g) {
				Graphics2D g2= (Graphics2D)g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setColor(Color.white);	
				g2.fillRect(this.getWidth()/4,(this.getHeight()/2)-2, this.getWidth()/2, 4);
				g2.fillRect((this.getWidth()/2)-2, this.getHeight()/4, 4, this.getHeight()/2);
			}
		};
		addMediaButton.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				int returnVal= fileExplorer.showOpenDialog(popup);
				if(returnVal==JFileChooser.APPROVE_OPTION) {
					File selectedFile= fileExplorer.getSelectedFile();
					if(selectedFile.getAbsolutePath().endsWith(".png")||
							(selectedFile.getAbsolutePath().endsWith(".jpeg"))) {
						System.out.println("File selected");
						try {
							System.out.println("File A");
							icons.add(new ImageElementIcon(selectedFile.toURI().toURL(),ImageIO.read(selectedFile)));
							System.out.println("File B");
							setSizeOfIcons(r.x+curvatureRadius,r.y+curvatureRadius,
									mediaBox.getWidth()-r.x-r.width-(2*curvatureRadius),
									mediaBox.getHeight()-r.y-r.height-(2*curvatureRadius));
							mediaBox.repaint();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
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
	public boolean isIconSelected() {
		return isElementSelected;
	}
	public Image getSelectedIcon() {
		return selectedMediaElement.getImageIcon();
	}
	public String getSelectedMediaType() {
		return selectedMediaElement.getType();
	}
	public BufferedImage getSelectedMediaImage() {
		return ((ImageElementIcon)selectedMediaElement).getRawImage();
	}
	public void turnOffMediaSelected() {
		selectedMediaElement=null;
		isElementSelected=false;
	}
	
	private void setSizeOfIcons(int xContainer, int yContainer, int widthOfContainer, int heightOfContainer) {
		this.removeAll();
		
		int diameterOfIcons= (widthOfContainer-(2*r.x))/2;
		
		/*
		 *  Icons are placed two a row: { 0 , 1 }
		 */
		int columnNum=0;
		int rowNum=0;
		for(int i=0;i<icons.size();i++) {
			this.add(icons.get(i));
			
			icons.get(i).setBounds(xContainer+(r.x/2)+(columnNum*(diameterOfIcons+r.x)), yContainer+(rowNum*diameterOfIcons), diameterOfIcons, diameterOfIcons);
			
			if(columnNum==0) {
				columnNum=1;
			}else {
				columnNum=0;
				rowNum++;
			}	
		}
		if(icons.size()>0) {
			this.add(addMediaButton);		
			addMediaButton.setBounds(xContainer+(r.x/2)+(columnNum*(diameterOfIcons+r.x)), yContainer+(rowNum*diameterOfIcons), diameterOfIcons, diameterOfIcons);
		}
	}
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2= (Graphics2D)g.create();
		if(icons.size()>0) {
			addMediaIconIsVisible=false;
		}
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		if(addMediaIconIsVisible==true) {
			g.setColor(Color.white);
			int height= this.getHeight()/2;
			int width= this.getWidth()/2;
			g.fillRect(width-25, height, 50, 8);
			g.fillRect(width-4, height-21, 8, 50);
			g.drawString("Upload media...", width-50, height+60);
		}else {
			System.out.println("Icon present");
		}
		g2.dispose();
	}
	

}

class MediaElementIcon extends JLabel{
	Image scaledImage;
	String type;
	URL url;
	
	public Image getImageIcon() {
		return scaledImage;
	}
	public String getType() {
		return type;
	}
	public URL getURL() {
		return url;
	}
	
}
class ImageElementIcon extends MediaElementIcon{
	BufferedImage rawImage;
	Image image;

	public ImageElementIcon(URL url, BufferedImage media)  {
		this.url=url;
		rawImage=media;
		type= "IMAGE";
	}
	public void setBounds(int x,int y, int width,int height) {
		super.setBounds(x, y, width, height);
		resizeImage(width,height);
	}
	private void resizeImage(int width, int height) {
		if(rawImage.getWidth()>rawImage.getHeight()){
			BufferedImage background= new BufferedImage(rawImage.getWidth(),rawImage.getWidth(),BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2 = (Graphics2D)background.getGraphics().create();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.drawImage(rawImage, 0, (background.getHeight()-rawImage.getHeight())/2, null);
			scaledImage=background.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
		}else {
			BufferedImage background= new BufferedImage(rawImage.getHeight(),rawImage.getHeight(),BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2 = (Graphics2D)background.getGraphics().create();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.drawImage(rawImage, (background.getWidth()-rawImage.getWidth())/2,0, null);
			scaledImage=background.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
		}
	}
	public BufferedImage getRawImage() {
		return rawImage;
	}
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2= (Graphics2D)g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.drawImage(scaledImage, 0, 0, null);
		g2.dispose();
	}
	
}
