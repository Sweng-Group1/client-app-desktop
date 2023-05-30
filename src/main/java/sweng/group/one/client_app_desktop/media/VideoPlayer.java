package sweng.group.one.client_app_desktop.media;

import java.awt.Point;
import java.net.URL;

import javax.swing.JTextArea;

import sweng.group.one.client_app_desktop.presentation.Slide;
import uk.co.caprica.vlcj.factory.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;

public class VideoPlayer extends PlayableMediaElement {
	
	private final EmbeddedMediaPlayerComponent mediaPlayer;
	private Boolean nativeLib;
	
	
	/**
	 * Creates a new VideoPlayer object with the specified parameters.
	 *
	 * @param pos         the position of the video player
	 * @param pointWidth  the width of the video player
	 * @param pointHeight the height of the video player
	 * @param slide       the slide to which the video player belongs
	 * @param fileURL     the URL of the video file
	 * @param loops       specifies whether the video should loop when played
	 */
	public VideoPlayer(Point pos, 
						  int pointWidth, 
						  int pointHeight,
						  Slide slide, 
						  URL fileURL,
						  boolean loops) {
		
		super(pos, pointWidth, pointHeight, 0, slide, fileURL, loops);
		nativeLib = new NativeDiscovery().discover();
		this.mediaPlayer = new EmbeddedMediaPlayerComponent();
		
		if (Boolean.TRUE.equals(nativeLib)) {
			this.component = mediaPlayer;
			this.duration = (float)mediaPlayer.mediaPlayer().status().length()/1000;
			loadFile();
			mediaPlayer.mediaPlayer().controls().setRepeat(loops);
		}
		else {
			this.component = new JTextArea("VLC is required for media to be used in this application");
		}
	}
	
	@Override
	public void togglePlaying() {
				
		if ( getPlaying()) {
			mediaPlayer.mediaPlayer().controls().pause(); 
		}
		// This check should prevent it from throwing a nasty exception
		else if (mediaPlayer.mediaPlayer().media().isValid()) {
			mediaPlayer.mediaPlayer().controls().play();
		}
		else {
			System.out.println("Not playable");
		}
		
	}
	
	@Override
	public boolean getPlaying() {
		return mediaPlayer.mediaPlayer().status().isPlaying();
	}
	
	@Override
	public void displayElement() {
		mediaPlayer.mediaPlayer().controls().stop();
		if(component.isDisplayable()) {
			mediaPlayer.mediaPlayer().controls().play();
		}
	}
	
	@Override
	protected void loadFile() {
		mediaPlayer.mediaPlayer().media().prepare(localPath);
	}
	
	/**
	 * Tests if the user has native libraries installed for VLC.
	 *
	 * @return true if the native libraries are installed, false otherwise
	 */
	public boolean nativeLibsInstalled() {
		return nativeLib;
	}
}
