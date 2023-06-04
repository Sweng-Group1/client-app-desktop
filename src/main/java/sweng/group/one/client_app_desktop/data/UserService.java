package sweng.group.one.client_app_desktop.data;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.net.URLEncoder;

import java.nio.charset.StandardCharsets;

import java.util.Properties;

import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
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
    private String userURL;
    
    
    // The constructors combined with the loadProperties method allow us to store 
    // the URLs in an external file. 
    public UserService() {
        this("server-urls.properties");
    }

    public UserService(String urlsPath) {
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
        
        this.loginURL = urlProps.getProperty("loginURL");
        this.refreshURL = urlProps.getProperty("refreshURL");
    }


	/**
     * Logs the user in to the application. If successful, 
     * will save the access and refresh tokens for the user locally (per-user files). 
     * @param user The user you are attempting to login as. 
     * @param password User's password.
     * @return Returns the status code (standard HTTP codes, e.g. 200 success, 403 incorrect credentials),
     *  or 0 if an error occurs. 
	 * @throws AuthenticationException Occurs when the user logs in with incorrect credentials. 
	 * @throws IOException Can occur due to  file system errors saving the tokens, or network errors with the server.
     */
	public int login(User user, String password) throws AuthenticationException, IOException {
		
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

	    Response response = client.newCall(request).execute();

	    // Handling the response.
	    statusCode = response.code();

	    if (statusCode == 403) {
	    	throw new AuthenticationException("Check your login credentials are correct.");
	    }
	    else if (statusCode == 500) {
	    	throw new RuntimeException("Unknown server error - check server logs");
	    }

	    // Save the response body as a JSON for easier parsing
	    String responseBody = response.body().string();
	    JSONObject json = new JSONObject(responseBody);

	    // Print the status code and response body
	    System.out.println("Status code: " + statusCode);
	    System.out.println("Response body: " + json.toString());

	    user.saveAccessToken(json.getString("access_token"));
	    user.saveRefreshToken(json.getString("refresh_token"));
	    
	    return statusCode;
	
	}
	
	//TODO: Test and comment. 
	public int createUser(String username, String password, String firstName, String lastName, String email) throws IOException {
		OkHttpClient client = new OkHttpClient();
		int statusCode = 0;
		RequestBody body = new MultipartBody.Builder()
				.addFormDataPart("username", username)
				.addFormDataPart("password", password)
				.addFormDataPart("firstname", firstName)
				.addFormDataPart("lastname", lastName)
				.addFormDataPart("email", email)
				.build();
		
		Request request = new Request.Builder()
				.url(userURL)
		        .post(body)
		        .build();
		
		Response response  = client.newCall(request).execute();
		statusCode = response.code();
		
		if (statusCode == 200) {
			return statusCode;
		} else if (statusCode == 500) {
			throw new RuntimeException("500 server response - server error. Check the server code / constraints. ");
		} else if (statusCode == 400) {
			throw new RuntimeException("400 server response, bad request - check the request is valid");
		} else {
			throw new RuntimeException(statusCode + "server response, unknown error - check code and debug.");
		}	
	}
	
	/**
     * Refreshes the access token for the user. 
     * Intended to be ran when an access token is expired, but will still refresh if the token is in-date.
     * Will update the saved access 
     * @param user The user whose access token is to be refreshed. 
     * @return Returns the status code (standard HTTP codes, e.g. 200 success, 403 incorrect credentials),
     *  or 0 if an error occurs.  
	 * @throws AuthenticationException Occurs when the refresh token has expired - try logging in. 
	 * @throws IOException Most likely to occur when no refresh token exists, as the user is logging in for the first time. 
	 * Could occur due to network errors - check server. 
     */
	public int refreshAccessToken(User user) throws IOException, AuthenticationException {
		
		
	    String refreshToken;
	    try {
	        refreshToken = user.readRefreshToken();
	    } catch (IOException e) {
	    	// This may occur when the user tries to login for the first time. 
	    	throw new IOException("Refresh token file doesn't exist - try logging in.");
	    	
	    }
	
	    OkHttpClient client = new OkHttpClient();
	    Request request = new Request.Builder()
	            .url(refreshURL)
	            .header("Authorization", "Bearer " + refreshToken)
	            .build();
	
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
            throw new AuthenticationException("Error: server returned 403 forbidden. Refresh token possibly expired - try logging in.");
        }
        else if (statusCode == 500) {
        	throw new RuntimeException("Unknown server error - check server logs");
        }
        else {
            return 0;
        }
	}	
}

