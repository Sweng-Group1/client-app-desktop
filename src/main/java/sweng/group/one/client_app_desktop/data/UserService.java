package sweng.group.one.client_app_desktop.data;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.net.URLEncoder;

import java.nio.charset.StandardCharsets;

import java.util.Properties;

import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author Paul Pickering
 * 
 * Service class for interfacing with the "User" entity endpoints on the server. 
 */

// TODO: Add method for creating a new user on the server. 

public class UserService {
    private String loginURL;
    private String refreshURL;
    
    
    // The constructors combined with the loadProperties method allow us to store 
    // the URLs in an external file. 
    public UserService() {
        this("server-urls.properties");
    }

    public UserService(String urlsPath) {
        loadProperties(urlsPath);
    }

    private void loadProperties(String urlsPath) {
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
        
        this.loginURL = urlProps.getProperty("loginURL");
        this.refreshURL = urlProps.getProperty("refreshURL");
    }


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
				.url(loginURL)
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
	
		    user.saveAccessToken(json.getString("access_token"));
		    user.saveRefreshToken(json.getString("refresh_token"));
	
		} catch (IOException e) {
		    // Handle IOException (e.g., network error)
		    e.printStackTrace();
		}
			 
			 return statusCode;
	
		}

	public int refreshAccessToken(User user) {
		
		
	    String refreshToken;
	    try {
	        refreshToken = user.readRefreshToken();
	    } catch (IOException e) {
	        System.err.println("Error reading refresh token");
	        e.printStackTrace();
	        return 0;
	    }
	
	    OkHttpClient client = new OkHttpClient();
	    Request request = new Request.Builder()
	            .url(refreshURL)
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
	            user.saveAccessToken(json.getString("access_token"));
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

	
	

}

