package sweng.group.one.client_app_desktop.media;

import java.net.MalformedURLException;

@SuppressWarnings("serial")
public abstract class PlayableMediaElement extends MediaElement {
	
	private Boolean loops;

	public PlayableMediaElement(int mediaID) throws MalformedURLException {
		super(mediaID);
		loops = false;
	}
	
	public PlayableMediaElement(int mediaID, Boolean loops) throws MalformedURLException {
		super(mediaID);
		this.loops = loops;
	}
	
	public abstract void togglePlaying();
	
	public abstract void getPlaying();
}
