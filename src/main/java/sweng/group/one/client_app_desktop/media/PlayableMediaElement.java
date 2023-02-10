package sweng.group.one.client_app_desktop.media;

import java.awt.Point;
import java.net.URL;

import sweng.group.one.client_app_desktop.presentation.Slide;

public abstract class PlayableMediaElement extends MediaElement {
	
	protected Boolean loops;

	protected PlayableMediaElement(Point pos, 
									int width, 
									int height, 
									float duration, 
									Slide slide, 
									URL fileURL,
									boolean loops){
		super(pos, width, height, duration, slide, fileURL);
		this.loops = loops;
	}
	
	public abstract void togglePlaying();
	
	public abstract boolean getPlaying();
}
