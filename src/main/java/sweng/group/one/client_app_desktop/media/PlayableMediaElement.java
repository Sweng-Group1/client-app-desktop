package sweng.group.one.client_app_desktop.media;

import java.awt.Point;
import java.net.URL;

import sweng.group.one.client_app_desktop.presentation.Slide;

@SuppressWarnings("serial")
public abstract class PlayableMediaElement extends MediaElement {
	
	protected Boolean loops;

	protected PlayableMediaElement(Point pos, 
									int pointWidth, 
									int pointHeight, 
									float duration, 
									Slide slide, 
									URL fileURL,
									String pathExtension,
									boolean loops){
		super(pos, pointWidth, pointHeight, duration, slide, fileURL, pathExtension);
		this.loops = loops;
	}
	
	public abstract void togglePlaying();
	
	public abstract void getPlaying();
}
