package sweng.group.one.client_app_desktop.media;

import java.awt.Point;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import sweng.group.one.client_app_desktop.presentation.PresElement;
import sweng.group.one.client_app_desktop.presentation.Slide;

public abstract class MediaElement extends PresElement {
	
	protected String localPath;
	private URL fileURL;
	
	protected MediaElement(Point pos, 
						int width, 
						int height, 
						float duration, 
						Slide slide, 
						URL fileURL){
		super(pos, width, height, duration, slide);
		
		this.fileURL = fileURL;
		this.localPath = "DEFAULT_PATH";
		
		try {
			//connect to a URL & check if there is a file there
			URLConnection con = fileURL.openConnection();
			String fieldValue = con.getHeaderField("Content-Disposition");
			if (fieldValue == null || ! fieldValue.contains("filename=")) {
				throw new IOException("Given URL does not contain a file");
			}
			String fileName = fieldValue.substring(fieldValue.indexOf("filename=") + 9, fieldValue.length());
			fileName = fileName.substring(fileName.lastIndexOf("/")+1); //prevent new folders from being made
			this.localPath = System.getProperty("java.io.tmpdir") + "WhatsOn/assets/" + fileName;
			
			downloadFromURL();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void downloadFromURL() throws IOException {
		InputStream inp = fileURL.openStream();
		ReadableByteChannel rbc = Channels.newChannel(inp);
		File outputFile = new File(localPath);
		outputFile.getParentFile().mkdirs();
		FileOutputStream out = new FileOutputStream(outputFile);
		out.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		out.close();
		rbc.close();
		outputFile.deleteOnExit();
	}
	
	protected abstract void loadFile();
	
	public String getLocalPath() {
		return this.localPath;
	}
	
	@Override
	public void finalize() {
		File file;
		try {
			file = new File(localPath);
			file.delete();
		} catch (Exception e) {
			//this means that the item is already deleted
		}
		
	}
}
