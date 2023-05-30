package sweng.group.one.client_app_desktop.media;

import java.awt.Point;
import java.net.URL;

import sweng.group.one.client_app_desktop.presentation.Slide;

public abstract class PlayableMediaElement extends MediaElement {
	
	protected Boolean loops;
	
	/*
	 * Creates a new PlayableMediaElement object with the specified parameters.
	 *
	 * @param pos      the position of the playable media element
	 * @param width    the width of the playable media element
	 * @param height   the height of the playable media element
	 * @param duration the duration of the playable media element
	 * @param slide    the slide to which the playable media element belongs
	 * @param fileURL  the URL of the media file
	 * @param loops    specifies whether the media should loop when played
	 */
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
	
	/**
	 * Toggles the playing state of the media element.
	 * Subclasses of this class must implement this method to provide the specific behaviour for toggling the playing state.
	 */
	public abstract void togglePlaying();
	
	/**
	 * Returns the playing state of the media element.
	 *
	 * @return true if the media element is currently playing, false otherwise
	 */
	public abstract boolean getPlaying();
}
