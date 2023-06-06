package sweng.group.one.client_app_desktop.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Service class for interfacing with the "Media" entity endpoints on the server. 
 * @author Paul Pickering
 */

public class MediaService {
	
	//TODO: Do we want to change these URLs to constant specified elsewhere perhaps?
	private static String mediaURL;
	
	public MediaService() {
		this("server-urls.properties");
	}
	
	public MediaService(String urlsPath) {
		loadURLs(urlsPath);
	}
	
	 private void loadURLs(String urlsPath) {
	        Properties urlProps = new Properties();
	        try {
	            urlProps.load(new FileInputStream(urlsPath));
	        } catch (FileNotFoundException e) {
	            System.out.println("Error - server url properties file not found.");
	            e.printStackTrace();
	            // TODO: Is this how we want exceptions handled? Or propogate up?
	        } catch (IOException e) {
	            System.out.println("Error - server url properties can't be read.");
	            e.printStackTrace();
	            // TODO: Is this how we want exceptions handled? Or propogate up?
	        }
	        
	        MediaService.mediaURL = urlProps.getProperty("mediaURL");
	    }
	
	/**
     * Uploads a media file to the server. 
     * @param media The media file to be uploaded. 
     * @param accessToken the authentication token of the user. Must correspond to logged in user. 
     * @return Returns the status code (standard HTTP codes, e.g. 200 success), or 0 if an error occurs. 
     */
	public static int uploadMedia(File media, String accessToken) {
		
		OkHttpClient client = new OkHttpClient();
		Path filepath = media.toPath();
		byte[] fileAsBytes = null;
		
		try {
			fileAsBytes = Files.readAllBytes(filepath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// TODO: This method is apparently OS dependent, so need to check it's working correctly on Windows/Mac. 
		String fileType = new String();
		try {
			fileType = Files.probeContentType(filepath);
			System.out.println("Filetype is " + fileType);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Body of the request - server expects file as multipart, 
		// expects mimetype (file type) as parameter. 
		RequestBody requestBody = new MultipartBody.Builder()
				.setType(MultipartBody.FORM)
				.addFormDataPart("file", filepath.toString(),
						RequestBody.create(fileAsBytes, MediaType.parse("application/octet-stream")))
						.addFormDataPart("mime", fileType)
						.build();
		
		// Header of the request - expects authorisation token. 
		Request request = new Request.Builder()
				.url(mediaURL)
				.header("Content-Type", "application/x-www-form-urlencoded")
				.header("Authorization", "Bearer " + accessToken)
				.post(requestBody)
				.build();
		
		// Sends the request. 
		try {
			Response response = client.newCall(request).execute();
			return response.code();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	
	
	/**
     * Downloads a media file from the server. 
     * @param id the database ID of the media being retrieved. 
     * @return Path to downloaded (temporary) file.
     */
	public static Path retrieveMedia(int id) {
		
		int statusCode = 0;
		OkHttpClient client = new OkHttpClient();
		
		// Builds the request - simple get request, ID of file is sent in URL. 
		Request request = new Request.Builder()
				.url(mediaURL + "/" + id)
				.get()
				.build();
		
		// Sends the request. 
		try { 
		    Response response = client.newCall(request).execute();
	
		    // Handling the response.
		    statusCode = response.code();
		    
		    if (statusCode == 403) {
		        System.out.println("Error: Server returned 403 forbidden");
		        return null;
		    }
		    String mimeType = response.header("Content-Type");
		    String extension = "";

	        // Get the extension from the mimeType
	        if(mimeType.equalsIgnoreCase("image/jpeg")) {
	            extension = ".jpg";
	        } else if (mimeType.equalsIgnoreCase("image/png")) {
	            extension = ".png";
	        } // TODO: add more else if statements here for other mime types as needed
	        
	        ResponseBody body = response.body();
	        Path mediaPath = Files.createTempFile("media", extension);
		    Files.write(mediaPath, body.bytes());
		    return mediaPath;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	
	/**
     * Deletes a media file from the server. 
     * @param id the database ID of the media being retrieved.
     * @param accessToken accessToken of user - requires verified or admin role. 
     * @return Path to downloaded (temporary) file.
     */
	public static Path deleteMedia(int id, String accessToken) {
		
		int statusCode = 0;
		OkHttpClient client = new OkHttpClient();
		
		// Builds the request - simple get request, ID of file is sent in URL. 
		Request request = new Request.Builder()
				.url(mediaURL + "/" + id)
				.header("Authorization", "Bearer " + accessToken)
				.delete()
				.build();
		
		// Sends the request. 
		try { 
		    Response response = client.newCall(request).execute();
	
		    // Handling the response.
		    statusCode = response.code();
		    if (statusCode == 403) {
		        System.out.println("Error: Server returned 403 forbidden");
		        return null;
		    }
		    
		    String mimeType = response.header("Content-Type");
		    String extension = "";
	        // Get the extension from the mimeType
	        if(mimeType.equalsIgnoreCase("image/jpeg")) {
	            extension = ".jpg";
	        } else if (mimeType.equalsIgnoreCase("image/png")) {
	            extension = ".png";
	        } // add more else if statements here for other mime types as needed

	        Path mediaPath = Files.createTempFile("media", extension);
		    Files.write(mediaPath, response.body().bytes());
		    return mediaPath;
		    
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
