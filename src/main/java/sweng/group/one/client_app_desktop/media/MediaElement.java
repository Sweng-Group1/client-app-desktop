package sweng.group.one.client_app_desktop.media;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import sweng.group.one.client_app_desktop.presentation.PresElement;
import sweng.group.one.client_app_desktop.presentation.Slide;

@SuppressWarnings("serial")
public abstract class MediaElement extends PresElement {
	
	private String localPath;
	private URL fileURL;
	
	protected MediaElement(int xPoint, 
						int yPoint, 
						int pointWidth, 
						int pointHeight, 
						float duration, 
						Slide slide, 
						URL fileURL,
						String pathExtension){
		super(xPoint, yPoint, pointWidth, pointHeight, duration, slide);
		this.fileURL = fileURL;
		this.localPath = System.getProperty("java.io.tmpdir") + pathExtension;
	}
	
	public void downloadFromURL() throws IOException {
		try {
			InputStream inp = fileURL.openStream();
			ReadableByteChannel rbc = Channels.newChannel(inp);
			FileOutputStream out = new FileOutputStream(new File(localPath));
			out.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			out.close();
			rbc.close();
		}catch(IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public abstract void loadFile();
}
