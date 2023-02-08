package sweng.group.one.client_app_desktop.media;

import java.awt.Point;
import java.net.URL;

import sweng.group.one.client_app_desktop.presentation.Slide;
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;

public class VideoPlayer extends PlayableMediaElement {

	protected VideoPlayer(Point pos, 
						  int pointWidth, 
						  int pointHeight, 
						  float duration, 
						  Slide slide, 
						  URL fileURL,
						  boolean loops) {
		
		super(pos, pointWidth, pointHeight, duration, slide, fileURL, loops);
		this.VideoPlayer = new EmbeddedMediaPlayerComponent();
		
		// TODO Auto-generated constructor stub
	}
	private static final long serialVersionUID = 1L;
	private final EmbeddedMediaPlayerComponent VideoPlayer;
	
	@Override
	public void togglePlaying() {
				
		if ( getPlaying()) {
			VideoPlayer.mediaPlayer().controls().pause(); 
		}
		else {
			VideoPlayer.mediaPlayer().controls().play();
		} 			
	}
	
	@Override
	public boolean getPlaying() {
		
		return VideoPlayer.mediaPlayer().status().isPlaying();
		
	}
	@Override
	protected void loadFile() {
		
		String VideoLocalPath = getLocalPath();
		VideoPlayer.mediaPlayer().media().startPaused(VideoLocalPath); 
		
	}
	
}
