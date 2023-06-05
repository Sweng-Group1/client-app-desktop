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
import java.util.UUID;

import sweng.group.one.client_app_desktop.presentation.PresElement;
import sweng.group.one.client_app_desktop.presentation.Slide;

public abstract class MediaElement extends PresElement {
	
	protected String localPath;
	private URL fileURL;
	
	/**
	 * Creates a new MediaElement object with the specified parameters.
	 *
	 * @param pos      the position of the media element
	 * @param width    the width of the media element
	 * @param height   the height of the media element
	 * @param duration the duration of the media element
	 * @param slide    the slide to which the media element belongs
	 * @param fileURL  the URL of the media file
	 */
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
			fileName = UUID.randomUUID() + fileName;
			fileName = fileName.substring(fileName.lastIndexOf("/")+1); //prevent new folders from being made
			this.localPath = System.getProperty("java.io.tmpdir") + "/WhatsOn/assets/" + fileName;
			
			downloadFromURL();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Downloads the media file from the specified URL and saves it to the local path.
	 *
	 * @throws IOException if an I/O error occurs during the download process
	 */
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
	
	/**
	 * Loads the media file.
	 * Subclasses of this class must implement this method to handle the specific loading process.
	 */
	protected abstract void loadFile();
	
	/**
	 * Returns the local path of the media file.
	 *
	 * @return the local path of the media file
	 */
	public String getLocalPath() {
		return this.localPath;
	}
	
	/**
	 * Overrides the finalize method to delete the local file associated with the media element.
	 * This method is called by the garbage collector when the object is no longer in use.
	 */
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
