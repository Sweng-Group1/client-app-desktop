package sweng.group.one.client_app_desktop.media;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JToggleButton;

import sweng.group.one.client_app_desktop.presentation.Slide;
import uk.co.caprica.vlcj.player.component.AudioPlayerComponent;

/** 
 * AudioPlayer creates a button on screen that can download and
 * play a predetermined file from a hyperlink. Playing can be 
 * toggled by clicking the button. The Audio processing is handled
 * by VLCJ package
 * 
 * @author Oli Partridge & Jonathan Cooke
*/
public class AudioPlayer extends PlayableMediaElement{
	
	private final AudioPlayerComponent audioPlayer;
	private Image icon;
	private JToggleButton toggleB;
	private File onIcon;
	private File offIcon;
	
	/**	AudioPlayer class to place button on screen at a given position. Can be resized with panel.
	 * 
	 * @param pos	Button location (Cartesian coordinates)
	 * @param pointWidth Relative width between 0 and 1
	 * @param pointHeight Relative height between 0 and 1 
	 * @param duration Duration for icon to stay on the slide when run
	 * @param slide slide Object on which the AudioPlayer will appear
	 * @param fileURL Audio file to be played, WAV or MP3 recommended.
	 * @param loops True if the audio should loop play-back.
	 */
	public AudioPlayer(Point pos, int pointWidth, int pointHeight,
						float duration, Slide slide, URL fileURL, boolean loops) {
		
		super(pos, pointWidth, pointHeight, duration, slide, fileURL, loops);
		this.audioPlayer = new AudioPlayerComponent();
		
		// Images for Button on and Off
		onIcon = new File(".//assets//audioON.png");
		offIcon = new File(".//assets//audioOFF.png");
		
		//Image icon;
		try {
			icon = ImageIO.read(offIcon);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		toggleB = new JToggleButton() {
			
			private static final long serialVersionUID = -8855412922464758819L;

			@Override
			public void paint(Graphics g) {
				Graphics2D g2d = (Graphics2D)g;
				
				int w = this.getWidth();
				int h = this.getHeight();
				int x = (int)((float)pos.x / slide.getPointWidth() * w);
				int y = (int)((float)pos.y / slide.getPointHeight() * h);
				
				g2d.setColor(java.awt.Color.white);
				g2d.fillRect(x-w/2, y-h/2, w, h);
				g2d.drawImage(icon, x-w/2, y-h/2, w, h, null);

			    g2d.dispose();  // Dispose the Graphics2D object
			}
		};
		
		component = toggleB;
		component.setPreferredSize(new Dimension(pointWidth, pointHeight));
		component.setOpaque(false);
		
		loadFile();
		toggleB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				togglePlaying();
			}
			
		});
	}
	
	/**
	 * Loads a given image file as the icon for audio JToggleButton objects
	 * 
	 * @param icon Image file to be used for button icon. PNG recommended.
	 */
	public void changeButtonIcon(File icon) {
		//Image icon;
		try {
			this.icon = ImageIO.read(icon);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	
	//	Toggles audio playing through mediaPlayer controls
	@Override
	public void togglePlaying() {
		if(getPlaying()) {
			audioPlayer.mediaPlayer().controls().pause();
			changeButtonIcon(offIcon);
		}
		else {
			audioPlayer.mediaPlayer().controls().play();
			changeButtonIcon(onIcon);
		}
	}
	
	//	Checks if audio is playing
	@Override
	public boolean getPlaying() {
		return audioPlayer.mediaPlayer().status().isPlaying();
	}
	
	// Load audio file for AudioPlayer and pause upon loading.
	@Override
	protected void loadFile() {
		String AudioLocalPath = getLocalPath();
		audioPlayer.mediaPlayer().media().startPaused(AudioLocalPath);
		
	}

}
