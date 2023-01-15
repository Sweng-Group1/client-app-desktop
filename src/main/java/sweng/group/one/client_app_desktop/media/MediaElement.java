package sweng.group.one.client_app_desktop.media;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import sweng.group.one.client_app_desktop.presentation.PresElement;

@SuppressWarnings("serial")
public abstract class MediaElement extends PresElement {
	
	private static final String DOWNLOAD_URL = "example.com/";
	private String localPath;
	private URL fileURL;
	
	MediaElement(int mediaID) throws MalformedURLException{
	}
	
	public String downloadFromURL() throws IOException {
		return null;
	}
	
	public abstract void loadFile();
}
