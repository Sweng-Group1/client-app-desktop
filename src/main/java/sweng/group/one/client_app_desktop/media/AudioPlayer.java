package sweng.group.one.client_app_desktop.media;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import sweng.group.one.client_app_desktop.presentation.Slide;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.component.AudioPlayerComponent;
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;


public class AudioPlayer extends PlayableMediaElement{
	
	JPanel audioPanel;
	Icon icon;
	JToggleButton toggleB;
	//ButtonHandler bHandler = new ButtonHandler();
	
	public AudioPlayer(Point pos, int pointWidth, int pointHeight,
						float duration, Slide slide, URL fileURL, boolean loops) {
		
		super(pos, pointWidth, pointHeight, duration, slide, fileURL, loops);
		this.AudioPlayer = new EmbeddedMediaPlayerComponent();
		
		audioPanel = new JPanel();
		audioPanel.setBounds(pointHeight, pointHeight, pointWidth, pointHeight);
		audioPanel.setBackground(Color.blue);
		slide.add(audioPanel);
		
		icon = new ImageIcon(".//assets//speaker_icon.png");
		
		toggleB = new JToggleButton(icon);
		toggleB.setFocusPainted(false);
		//toggleB.addActionListener(bHandler);
		audioPanel.add(toggleB);
	}
	private final EmbeddedMediaPlayerComponent AudioPlayer;

	@Override
	public void togglePlaying() {
		if(getPlaying()) {
			AudioPlayer.mediaPlayer().controls().pause();
		}
		else {
			AudioPlayer.mediaPlayer().controls().play();
		}
	}
	@Override
	public boolean getPlaying() {
		return AudioPlayer.mediaPlayer().status().isPlaying();
	}
	@Override
	protected void loadFile() {
		String AudioLocalPath = getLocalPath();
		AudioPlayer.mediaPlayer().media().startPaused(AudioLocalPath);
		
	}
	
	

}

//public class ButtonHandler implements ActionListener{
//	
//	public void actionPerformed(ActionEvent event){
//		
//		AudioButton.loadFile();
//		AudioButton.togglePlaying();
//							
//	}
//}






