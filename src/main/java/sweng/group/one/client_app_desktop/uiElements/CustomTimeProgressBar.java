package sweng.group.one.client_app_desktop.uiElements;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.BoundedRangeModel;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JProgressBar;
import javax.swing.Timer;
import javax.swing.event.ChangeListener;

import sweng.group.one.client_app_desktop.presentation.Slide;

/**
 * @author sophiemaw
 * This class 'plays' the slide by displaying the presElements contained in it
 *
 */
public class CustomTimeProgressBar extends UploadSceneComponent implements ComponentInterface{
	CustomProgressBar bar;
	Slide currentPlayingSlide;

	JButton playButton;	
	JButton audioButton; 
	
	Image playIcon;
	Image pauseIcon;
	Image audioOnIcon;
	Image audioOffIcon;
	
	boolean isPlaying;
	boolean audioIsOn;
	
	
	public CustomTimeProgressBar() {
		this.setLayout(null);
		bar = new CustomProgressBar();
		this.main= colorLight;
		this.secondary= colorDark;

		
		this.add(bar);
		
		isPlaying=false;
		audioIsOn=false;
		
		playButton = new JButton() {
			public void paint(Graphics g) {
				Graphics2D g2= (Graphics2D)g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				if(isPlaying==true) {
					g2.drawImage(pauseIcon, 0, 0, this);
				}else {
					g2.drawImage(playIcon, 0, 0, this);
				}
				g2.dispose();
				//super.paint(g);
			}
		};
		this.add(playButton);
		playButton.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if(isPlaying==true) {
					isPlaying=false;
					bar.pause();
					
				}else {
					isPlaying=true;
					bar.start();
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
		audioButton= new JButton() {
			public void paint(Graphics g) {
				Graphics2D g2= (Graphics2D)g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				if(audioIsOn==true) {
					g2.drawImage(audioOffIcon, 0, 0, this);
				}else {
					g2.drawImage(audioOnIcon, 0, 0, this);
				}
			
				g2.dispose();
				//super.paint(g);
			}
		};
		this.add(audioButton);
		audioButton.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if(audioIsOn==true) {
					audioIsOn=false;
				}else {
					audioIsOn=true;
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
	public void setTimerFor(Slide slide) {
		bar.updateDuration(slide);
		currentPlayingSlide= slide;
	}
	private void start() {
		if(currentPlayingSlide!=null) {
			bar.start();
			(currentPlayingSlide).displaySlide();
		}
	}
	private void pause() {
		if(currentPlayingSlide!=null) {
			bar.start();
			
		}
	}
	
	//OVERIDED:

	public void setMarginBounds(int r, int t, int l, int b){
		super.setMarginBounds(r, t, l, b);
		int height= this.getHeight()- curvatureRadius- t - b;
		
		playButton.setBounds(r+curvatureRadius/2,t+curvatureRadius/2,height,height);
		audioButton.setBounds(playButton.getX()+height+r+r, t+curvatureRadius/2, height, height);
		bar.setBounds(audioButton.getX()+height+r+r, t+ curvatureRadius/2, this.getWidth()-r-r-l-(curvatureRadius)-(audioButton.getX()+height+r), height);
		
		try {
			playIcon= ImageIO.read(new File("./Assets/play.png")).getScaledInstance(height, height, java.awt.Image.SCALE_SMOOTH);
			pauseIcon= ImageIO.read(new File("./Assets/pause.png")).getScaledInstance(height, height, java.awt.Image.SCALE_SMOOTH);;
			audioOnIcon= ImageIO.read(new File("./Assets/audioON.png")).getScaledInstance(height, height, java.awt.Image.SCALE_SMOOTH);;
			audioOffIcon= ImageIO.read(new File("./Assets/audioOFF.png")).getScaledInstance(height, height, java.awt.Image.SCALE_SMOOTH);;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void paint(Graphics g) {
		super.paint(g);
		if(currentPlayingSlide!=null) {
			//bar.updateDuration(currentPlayingSlide);
		}
	}
	public void updateDuration() {
		if(currentPlayingSlide!=null) {
			bar.updateDuration(currentPlayingSlide);
		}
		
	}

}

class CustomProgressBar extends JProgressBar{
	Thread progressing;
	int incrState;
	int incrementSize;
	int incrementAmount;
	int incrementTime;
	
	float maximumTime;
	
	java.util.Timer timer;
	TimerTask timerTask;
	CustomProgressBar progressBar= this;
	
	public CustomProgressBar() {
		timer= new java.util.Timer();
	}
	public void start() {
		
		if(maximumTime!=0) {
			incrState=0;
			timer= new java.util.Timer();
			for(int i=0;i<maximumTime*10;i++) {
				
				timer.schedule(new TimerTask() {
	
					@Override
					public void run() {
						incrState++;
						progressBar.setValue((int)(progressBar.getWidth()/(maximumTime*10))*incrState);
						System.out.println("Progress bar value: "+progressBar.getValue());
					}
					
				}, i*100);
			}
		}
	}
	public void pause() {
	
		timer.cancel();
	}

	public void updateDuration(Slide slide) {
		maximumTime=0;
		for(int i=0;i<slide.getElements().size();i++) {
			if(slide.getElements().get(i).getDuration()>maximumTime) {
				maximumTime= slide.getElements().get(i).getDuration(); // to ms
				this.setMinimum(0);
				this.setMaximum(this.getWidth());
			}
		}
	}
}

