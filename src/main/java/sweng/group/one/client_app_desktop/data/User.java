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

/*
 * This class handles logging in, refreshing access and authentication tokens, 
 * storing them, and storing the user's username. 
 * @author Paul Pickering
*/


//TODO: Refactor to have constructor retrieve access and refresh tokens? Decide if we need access and refresh token fields. 
public class User {
	
	
	private String username = new String();
	private String accessToken;
	private String refreshToken;
	
	public User(String username) {
		this.username = username;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public void setUsername(String username) {
		this.username = username;
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

		/* Redundant - Following code (Files.write) should handle this already. TODO: Confirm this is the case. 
		 * 
		 * // checking if temp file already exists, if not create one. if
		 * (!Files.exists(filepath)) { try { Path tempFile =
		 * Files.createTempFile(filepath, "access_token", ".txt");
		 * System.out.println("Temporary file created at: " +
		 * tempFile.toAbsolutePath()); } catch (IOException e) {
		 * System.err.println("Failed to create temporary file."); e.printStackTrace();
		 * } }
		 */
		
		Files.write(filepath, token.getBytes());
	}
		
	private String readAccessToken() throws IOException {
		Path filepath = Paths.get("temp/access_token.txt");
		return Files.readString(filepath);
	}
	

		
	private String readRefreshToken() throws IOException {
		Path filepath = Paths.get("temp/refresh_token.txt");
		return Files.readString(filepath);
	}
	

	public int refreshAccessToken() {
		
		String refreshToken = new String();
		try {
			refreshToken = readRefreshToken();
		} catch (IOException e) {
			System.err.println("Error reading refresh token");
			e.printStackTrace();
			return 0;
		}
		
		HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:8080/api/v1/token/refresh"))
				.header("Authorization", "Bearer " + refreshToken)
				.GET()
				.build();
		
		 try {
	            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

	            // Save the status code
	            int statusCode = response.statusCode();
	            
	         // Save the response body as a JSON for easier parsing
	            JSONObject json = new JSONObject(response.body());
	            
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
	        } catch (InterruptedException e) {
	            // Handle InterruptedException (e.g., the request was cancelled)
	            e.printStackTrace();
	            return 0;
	        }

	}
	
}
