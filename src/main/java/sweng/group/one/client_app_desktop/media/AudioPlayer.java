package sweng.group.one.client_app_desktop.media;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JToggleButton;

import org.mapsforge.core.graphics.Color;

import sweng.group.one.client_app_desktop.presentation.Slide;
import uk.co.caprica.vlcj.player.component.AudioPlayerComponent;


public class AudioPlayer extends PlayableMediaElement{
	
	private final AudioPlayerComponent audioPlayer;
	private Image icon;
	private JToggleButton toggleB;
	private File onIcon;
	private File offIcon;
	
	public AudioPlayer(Point pos, int pointWidth, int pointHeight,
						float duration, Slide slide, URL fileURL, boolean loops) {
		
		super(pos, pointWidth, pointHeight, duration, slide, fileURL, loops);
		this.audioPlayer = new AudioPlayerComponent();
		
		onIcon = new File(".//assets//audioON.png");
		offIcon = new File(".//assets//audioOFF.png");
		
		//Image icon;
		try {
			icon = ImageIO.read(offIcon);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		toggleB = new JToggleButton() {
			
			public void paintComponent(Graphics g) {
				Graphics2D g2d = (Graphics2D)g;
		
				//g2d.draw(getBounds());
				
				int w = this.getWidth();
				int h = this.getHeight();
				int x = (int)((float)pos.x / slide.getPointWidth() * w);
				int y = (int)((float)pos.y / slide.getPointHeight() * h);
					
			    g2d.drawImage(icon, x-w/2, y-w/4, w, h, java.awt.Color.red, null);

			    g2d.dispose();  // Dispose the Graphics2D object
			    
			    super.paintComponent(g);
			}
		};
		
		component = toggleB;
		component.setPreferredSize(new Dimension(pointWidth, pointHeight));
		
		loadFile();
		toggleB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				togglePlaying();
			}
			
		});
	}
		
	public void changeButtonIcon(File icon) {
		//Image icon;
		try {
			this.icon = ImageIO.read(icon);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	

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
	@Override
	public boolean getPlaying() {
		return audioPlayer.mediaPlayer().status().isPlaying();
	}
	@Override
	protected void loadFile() {
		String AudioLocalPath = getLocalPath();
		audioPlayer.mediaPlayer().media().startPaused(AudioLocalPath);
		
	}

}