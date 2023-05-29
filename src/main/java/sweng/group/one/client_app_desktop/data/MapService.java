package sweng.group.one.client_app_desktop.data;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @author Paul Pickering
 *  *Service class for handling all map related server tasks, i.e. uploading, retrieving, deleting, etc. 
 */
public class MapService {
	//TODO: Do we want to change these URLs to constant specified elsewhere perhaps?
	private final static String MAP_URL = "http://localhost:8080/api/v1/map/";
	private static final String MAP_FOLDER = "./assets/map/";
	/**
     * Gets a map file from the server. 
     * @param name The name of the map file, i.e. "York". 
     * @return Returns the path to the map file. 
     */
	public Path retrieveMap(String name) {
		int statusCode = 0;
	
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder()
				.url(MAP_URL + name)
				.get()
				.build();
		
		try {
		    Response response = client.newCall(request).execute();
	
		    // Handling the response.
		    statusCode = response.code();
	
		    if (statusCode == 403) {
		        System.out.println("Error: Server returned 403 forbidden");
		        return null;
		    }
		    
		    else if (statusCode == 417) {
		    	System.out.println("Error: server returned 417 expectation failed.");
		    	return null;
		    }
	
		    System.out.println("Status code: " + statusCode);
		    
		    // Save the map locally. 
		    Path mapPath = Paths.get(MAP_FOLDER + name + ".map");
		    Files.createFile(mapPath);
		    ResponseBody body = response.body();
		    Files.write(mapPath,body.bytes());
		    return mapPath;
		    
		} catch (FileAlreadyExistsException e) {
			Path mapPath = Paths.get(MAP_FOLDER + name + ".map");
			return mapPath;
		} catch (IOException e) {
		    // Handle IOException (e.g., network error)
		    e.printStackTrace();
		    return null;
		}
	}
	
	/**
     * Uploads a map file to the server. 
     * @param map The map file. 
     * @param accessToken the authorisation token. 
     * @return Returns the status code (e.g. 200 success, 403 forbidden). Returns 0 if exception occurred.  
     */
	public int uploadMap(Map map, String accessToken) {
		String filepath = map.getFile().getPath();
		byte[] fileAsBytes;
		try {
			fileAsBytes = Files.readAllBytes(map.getFile().toPath());
		} catch (IOException e) {
			//TODO: Where should we be printing error messages?
			e.printStackTrace();
			return 0;
		}
		
		OkHttpClient client = new OkHttpClient();
		String nameKey = "name";
		String nameValue = map.getName();
        
        // Ensures non-alphanumeric characters are handled properly. 
        String encodedNameValue = URLEncoder.encode(nameValue, StandardCharsets.UTF_8);

        // Here we build the body of the request, specifying the MultiPartFile "file" 
        // and String "name" parameters. 
        RequestBody requestBody = new MultipartBody.Builder()
        		.setType(MultipartBody.FORM)
        		.addFormDataPart("file", filepath,
        		RequestBody.create(fileAsBytes, MediaType.parse("application/octet-stream")))
        		.addFormDataPart(nameKey, encodedNameValue)
				.build();
        
        Request request = new Request.Builder()
        		.url(MAP_URL)
        		.header("Content-Type", "application/x-www-form-urlencoded")
        		.header("Authorization", "Bearer " + accessToken)
        		.post(requestBody)
        		.build();
 
        try {
			Response response = client.newCall(request).execute();
			System.out.println("Attempted to upload map! Response Code: " + response.code()
			+ "Response Body: " + response.body().toString());
			return response.code();
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}
	}
}
