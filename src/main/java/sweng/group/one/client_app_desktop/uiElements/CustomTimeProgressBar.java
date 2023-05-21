package sweng.group.one.client_app_desktop.uiElements;

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
import javax.swing.JButton;
import javax.swing.JProgressBar;

import sweng.group.one.client_app_desktop.presentation.Slide;
import sweng.group.one.client_app_desktop.sceneControl.ComponentInterface;

/**
 * @author sophiemaw
 * This class 'plays' the slide by displaying the presElements contained in it
 * Notes fir Fraser: After refactoring, I havent had a chance to fix this class
 * 					  if you have time it'd be super to have this fixed
 * Implemented:
 * This class gets updated when a new element is added to a slide, or current visible slide changes
 * It takes the maximum duration of all elements on the slide, and the progress bar
 * is set to that as it's maximum
 * When the playButton is pressed and there are elements on slide with non-zero durations, max duration 
 * 	mX, playButton icon is set to it's pauseIcon- progress bar will increase to a maximum in time
 * 	mX, - if button is pressed before progress bar is at a maximum, timeBar "pauses"- pressed agaiin 
 * 	and timeBar is reset and plays from beginning 
 * When audioButton is pressed audioButton icon is set to audioOFF
 * When audioButton is Pressed again audioButton icon is set to audioON 
 * 
 * Not Implemented:
 * 	When audioButton is pressed audio of all slides is turned on
 *  When audioButton is Pressed again audio of all slide elements is turned Off 
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
	

	/**
	 * Explained in setMarginBounds() diagram
	 */
	public void setMarginBounds(int r, int t, int l, int b){
		super.setMarginBounds(r, t, l, b);
		int height= this.getHeight()- curvatureRadius- t - b;
		
		playButton.setBounds(r+curvatureRadius/2,t+curvatureRadius/2,height,height);
		audioButton.setBounds(playButton.getX()+height+r+r, t+curvatureRadius/2, height, height);
		bar.setBounds(audioButton.getX()+height+r+r, t+ curvatureRadius/2, this.getWidth()-r-r-l-(curvatureRadius)-(audioButton.getX()+height+r), height);
		
		try {
			playIcon= ImageIO.read(new File("./assets/play.png")).getScaledInstance(height, height, java.awt.Image.SCALE_SMOOTH);
			pauseIcon= ImageIO.read(new File("./assets/pause.png")).getScaledInstance(height, height, java.awt.Image.SCALE_SMOOTH);;
			audioOnIcon= ImageIO.read(new File("./assets/audioON.png")).getScaledInstance(height, height, java.awt.Image.SCALE_SMOOTH);;
			audioOffIcon= ImageIO.read(new File("./assets/audioOFF.png")).getScaledInstance(height, height, java.awt.Image.SCALE_SMOOTH);;
			
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
/*
 * Implemented:
 *  This class creates a timer of maxDuration of slide elements, and increases steadily 
 *  in that time. 
 *  
 * Not Implemented:
 *  From what I can remember there is a bug with this, where elements with a tiny duration
 *  don't work, I think it's to do with variable types and rounding errors 
 */
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

