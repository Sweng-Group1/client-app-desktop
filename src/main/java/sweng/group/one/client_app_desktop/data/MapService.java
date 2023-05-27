package sweng.group.one.client_app_desktop.data;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MapService {
	
	//TODO: Do we want to change these URLs to constant specified elsewhere perhaps?
	private final static String MAP_URL = "http://localhost:8080/api/v1/map";
	
	
	public int retrieveMap(String name) {
		
		int statusCode = 0;
		
		OkHttpClient client = new OkHttpClient();
		
		Request request = new Request.Builder()
				.url(MAP_URL + name)
				.get()
				.build();
		
		System.out.println(request.toString());
		
		try {
		    Response response = client.newCall(request).execute();
	
		    // Handling the response.
		    statusCode = response.code();
	
		    if (statusCode == 403) {
		        System.out.println("Error: Server returned 403 forbidden");
		        return statusCode;
		    }
	
		    // Save the response body as a JSON for easier parsing
		    String responseBody = response.body().string();
		    JSONObject json = new JSONObject(responseBody);
	
		    // Print the status code and response body
		    System.out.println("Status code: " + statusCode);
		    System.out.println("Response body: " + json.toString());
		    
		    //TODO: Handle what to do with the Map. Do we want this stored in a "temp" folder?
		    // Or in a permanant one to avoid redownloads? If so, where?
	
	
		} catch (IOException e) {
		    // Handle IOException (e.g., network error)
		    e.printStackTrace();
		}
			 
			 return statusCode;
	
	}
		
	
	// Uploads a map to the server. Returns 200 if successful, 
	// 403 for incorrect authorisation, 
	// 400 for an issue with the request parameters, 
	// or 0 for IO exceptions. 
	
	public int uploadMap(Map map, String authToken) {
		
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
        		.header("Authorization", "Bearer " + authToken)
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
