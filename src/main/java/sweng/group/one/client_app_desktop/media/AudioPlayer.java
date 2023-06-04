package sweng.group.one.client_app_desktop.media;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JToggleButton;

import sweng.group.one.client_app_desktop.presentation.Slide;
import uk.co.caprica.vlcj.player.component.AudioPlayerComponent;


public class AudioPlayer extends PlayableMediaElement{
	
	private final AudioPlayerComponent audioPlayer;
	private ImageIcon icon;
	private JToggleButton toggleB;
	private ImageIcon onIcon;
	private ImageIcon offIcon;
	
	public AudioPlayer(Point pos, int pointWidth, int pointHeight,
						float duration, Slide slide, URL fileURL, boolean loops) {
		
		super(pos, pointWidth, pointHeight, duration, slide, fileURL, loops);
		this.audioPlayer = new AudioPlayerComponent();
		
		onIcon = new ImageIcon(".//assets//audioON.png");
		offIcon = new ImageIcon(".//assets//audioOFF.png");
		
		toggleB = new JToggleButton(offIcon);
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
		
	public void changeButtonIcon(ImageIcon icon) {
			toggleB.setIcon(icon);
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