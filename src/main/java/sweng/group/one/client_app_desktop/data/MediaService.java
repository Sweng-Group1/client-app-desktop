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
	
	private static String mediaURL;
	/**
     * Reads in the URL from the default properties file. 
     * Another file can be specified with the loadURLs method. 
     */
	static 
	{
		try {
			loadURLs("server-urls.properties");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("URLs file could not be found or loaded");
		}
	}
	
	 private static void loadURLs(String urlsPath) throws FileNotFoundException {
	        Properties urlProps = new Properties();
	        try {
	            urlProps.load(new FileInputStream(urlsPath));
	        } catch (FileNotFoundException e) {
	        	e.printStackTrace();
	        	throw new FileNotFoundException("URL file not found");
	        } catch (IOException e) {
	        	e.printStackTrace();
	            throw new RuntimeException("URLs file not found.");
	        }
	        
	        MediaService.mediaURL = urlProps.getProperty("mediaURL");
	    }
	
	/**
     * Uploads a media file to the server. 
     * @param media The media file to be uploaded. 
     * @param accessToken the authentication token of the user. Must correspond to logged in user. 
     * @return Returns the status code (standard HTTP codes, e.g. 200 success), or 0 if an error occurs. 
	 * @throws IOException 
     */
	public static int uploadMedia(File media, String accessToken) throws IOException {
		
		OkHttpClient client = new OkHttpClient();
		Path filepath = media.toPath();
		byte[] fileAsBytes = null;
		
		try {
			fileAsBytes = Files.readAllBytes(filepath);
		} catch (IOException e) {
			e.printStackTrace();
			throw new IOException("Can't find/read media file.");
		}
		
		// This method is apparently OS dependent, so worth checking if platform specific issues occur. 
		String fileType = new String();
		try {
			fileType = Files.probeContentType(filepath);
			System.out.println("Filetype is " + fileType);
		} catch (IOException e) {
			throw new IOException("Couldn't read fileType from file.");
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
			e.printStackTrace();
			throw new IOException("Couldn't communicate with server");
		}
	}
	
	/**
     * Downloads a media file from the server. 
     * @param id the database ID of the media being retrieved. 
     * @return Path to downloaded (temporary) file.
	 * @throws IOException 
     */
	public static Path retrieveMedia(int id) throws IOException {
		
		int statusCode = 0;
		OkHttpClient client = new OkHttpClient();
		
		// Builds the request - simple get request, ID of file is sent in URL. 
		Request request = new Request.Builder()
				.url(mediaURL + "/" + id)
				.get()
				.build();
		
		// Get the system temp directory
	    String tempDirectoryPath = System.getProperty("java.io.tmpdir") + "/WhatsOn/media/";

	    // Ensure the directory exists
	    File tempDirectory = new File(tempDirectoryPath);
	    if (!tempDirectory.exists()) {
	        tempDirectory.mkdirs();
	    }
		
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
	        
	        ResponseBody body = response.body();
	        Path mediaPath = Files.createTempFile(tempDirectoryPath + extension, "media");

		    Files.write(mediaPath, body.bytes());
		    return mediaPath;

		} catch (IOException e) {
			e.printStackTrace();
			throw new IOException("Error communicating with server");
		}
	}
	
	
	/**
     * Deletes a media file from the server. 
     * @param id the database ID of the media being retrieved.
     * @param accessToken accessToken of user - requires verified or admin role. 
	 * @throws IOException 
	 * @throws AuthenticationException 
     */
	public static int deleteMedia(int id, String accessToken) throws IOException, AuthenticationException {
		
		int statusCode = 0;
		OkHttpClient client = new OkHttpClient();
		
		// Builds the request - simple get request, ID of file is sent in URL. 
		Request request = new Request.Builder()
				.url(mediaURL + "/" + id)
				.header("Authorization", "Bearer " + accessToken)
				.delete()
				.build();
		
		// Sends the request. 
	    Response response = client.newCall(request).execute();

	    // Handling the response.
	    statusCode = response.code();
	    
	    if (statusCode == 200) {
	    	return statusCode;
	    }
	    else if  (statusCode == 403) {
	    	throw new AuthenticationException("403 code - check access Token");
	    }
	    else {
	    	return 0;
	    }
	}
}
