package sweng.group.one.client_app_desktop.data;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import sweng.group.one.client_app_desktop.data.User;

public class UserService {
	
	public int login(User user, String password) {
		
		int statusCode = 0;
			
		OkHttpClient client = new OkHttpClient();
	
		// Username and password encoded for transfer.
		String key1 = "username";
		String value1 = user.getUsername();
		String key2 = "password";
		String value2 = password;
	
		// Ensures non-alphanumeric characters are handled properly.
		String encodedValue1 = URLEncoder.encode(value1, StandardCharsets.UTF_8);
		String encodedValue2 = URLEncoder.encode(value2, StandardCharsets.UTF_8);
	
		String requestBody = key1 + "=" + encodedValue1 + "&" + key2 + "=" + encodedValue2;
	
		Request request = new Request.Builder()
		        .url("http://localhost:8080/api/v1/login")
		        .header("Content-Type", "application/x-www-form-urlencoded")
		        .header("Cache-Control", "no-cache")
		        .post(RequestBody.create(requestBody, MediaType.parse("application/x-www-form-urlencoded")))
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
	
		    saveAccessToken(json.getString("access_token"));
		    saveRefreshToken(json.getString("refresh_token"));
	
		} catch (IOException e) {
		    // Handle IOException (e.g., network error)
		    e.printStackTrace();
		}
			 
			 return statusCode;
	
		}

	public int refreshAccessToken() {
	    String refreshToken;
	    try {
	        refreshToken = readRefreshToken();
	    } catch (IOException e) {
	        System.err.println("Error reading refresh token");
	        e.printStackTrace();
	        return 0;
	    }
	
	    OkHttpClient client = new OkHttpClient();
	
	    Request request = new Request.Builder()
	            .url("http://localhost:8080/api/v1/token/refresh")
	            .header("Authorization", "Bearer " + refreshToken)
	            .build();
	
	    try {
	        Response response = client.newCall(request).execute();
	
	        // Save the status code
	        int statusCode = response.code();
	
	        // Save the response body as a JSON for easier parsing
	        String responseBody = response.body().string();
	        JSONObject json = new JSONObject(responseBody);
	
	        // Print the status code
	        System.out.println("Refresh access token request finished. Status code: " + statusCode);
	
	        if (statusCode == 200) {
	            saveAccessToken(json.getString("access_token"));
	            return statusCode;
	        }
	
	        else if (statusCode == 403) {
	            System.out.println("Error: server returned 403 forbidden. Try logging in again");
	            return statusCode;
	        }
	
	        else {
	            return 0;
	        }
	
	    } catch (IOException e) {
	        // Handle IOException (e.g., network error)
	        e.printStackTrace();
	        return 0;
	    }
	}




	public void saveAccessToken(String token) throws IOException {
		
		Path directoryPath = Paths.get("temp");
		Path filepath = Paths.get("temp/access_token.txt");
		// Checking if temp folder already exists, if not create one. 
		
		if(!Files.exists(directoryPath)) {
			//TODO: Fix this warning. 
			Files.createDirectory(directoryPath, null);
			System.out.println("Temp directory for tokens created at: " + directoryPath.toAbsolutePath());
		}
		
		Files.write(filepath, token.getBytes());
	}
		

	//TODO: Double check best practice on the visibility here - test was complaining if these two were private. 
	private void saveRefreshToken(String token) throws IOException {
		Path directoryPath = Paths.get("temp");
		Path filepath = Paths.get("temp/refresh_token.txt");
		// Checking if temp folder already exists, if not create one. 
		if(!Files.exists(directoryPath)) {
			Files.createDirectory(directoryPath, null);
			System.out.println("Temp directory for tokens created at: " + directoryPath.toAbsolutePath());
		}

	Files.write(filepath, token.getBytes());
}
	
	protected String readRefreshToken() throws IOException {
		Path filepath = Paths.get("temp/refresh_token.txt");
		return Files.readString(filepath);
	}
	protected String readAccessToken() throws IOException {
		Path filepath = Paths.get("temp/access_token.txt");
		return Files.readString(filepath);
	}
	

}

