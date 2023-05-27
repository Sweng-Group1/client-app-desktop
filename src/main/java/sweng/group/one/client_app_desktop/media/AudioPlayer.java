package sweng.group.one.client_app_desktop.media;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import sweng.group.one.client_app_desktop.presentation.Slide;
import uk.co.caprica.vlcj.player.component.AudioPlayerComponent;


public class AudioPlayer extends PlayableMediaElement{
	
	JPanel audioPanel;
	ImageIcon icon;
	JToggleButton toggleB;
	
	public AudioPlayer(Point pos, int pointWidth, int pointHeight,
						float duration, Slide slide, URL fileURL, boolean loops) {
		
		super(pos, pointWidth, pointHeight, duration, slide, fileURL, loops);
		this.AudioPlayer = new AudioPlayerComponent();
		
//		audioPanel = new JPanel();
//		audioPanel.setBounds(pointHeight, pointHeight, pointWidth, pointHeight);
//		audioPanel.setBackground(Color.blue);
//		slide.add(audioPanel);
//		
		icon = new ImageIcon(".//assets//speaker_icon.png");
		Image img = icon.getImage();
		BufferedImage bi = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		Graphics g = bi.createGraphics();
		g.drawImage(img, 0, 0, pointWidth+10, pointHeight+10, null);
		ImageIcon newIcon = new ImageIcon(bi);
		
		
		toggleB = new JToggleButton(newIcon);
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
	private final AudioPlayerComponent AudioPlayer;

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








