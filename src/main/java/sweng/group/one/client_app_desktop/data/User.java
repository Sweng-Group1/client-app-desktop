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
		
	public String readAccessToken() throws IOException {
		Path filepath = Paths.get("temp/access_token.txt");
		return Files.readString(filepath);
	}
	
	public void saveRefreshToken(String token) throws IOException {
		Path directoryPath = Paths.get("temp");
		Path filepath = Paths.get("temp/refresh_token.txt");
		// Checking if temp folder already exists, if not create one. 
		if(!Files.exists(directoryPath)) {
			Files.createDirectory(directoryPath, null);
			System.out.println("Temp directory for tokens created at: " + directoryPath.toAbsolutePath());
		}

		Files.write(filepath, token.getBytes());
	}
		
	public String readRefreshToken() throws IOException {
		Path filepath = Paths.get("temp/refresh_token.txt");
		return Files.readString(filepath);
	}

	public void refreshAccessToken() {
		
		String refreshToken = new String();
		try {
			refreshToken = readRefreshToken();
		} catch (IOException e) {
			System.err.println("Error reading refresh token");
			e.printStackTrace();
			return;
		}
		
		HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:8080/api/v1/login"))
				.header("Authorization", "Bearer " + refreshToken)
				.GET()
				.build();
		
		
		 try {
	            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

	            // Save the status code
	            int statusCode = response.statusCode();

	            // Save the response body as a String
	            String responseBody = response.body();
	            
	            // Print the status code and response body
	            System.out.println("Status code: " + statusCode);
	            System.out.println("Response body: " + responseBody);
	            
	        } catch (IOException e) {
	            // Handle IOException (e.g., network error)
	            e.printStackTrace();
	        } catch (InterruptedException e) {
	            // Handle InterruptedException (e.g., the request was cancelled)
	            e.printStackTrace();
	        }

	}
	
	
	//TODO: TBD where this functionality will go.
	// E.g., a "loginScreen" class with a login method may make more sense?
	// Code can be easily moved if needed.
public int login(String password) {
		
		int statusCode = 0;

		// Username and password encoded for transfer. 
        String key1 = "username";
        String value1 = getUsername();
        String key2 = "password";
        String value2 = password;
        
        // Ensures non-alphanumeric characters are handled properly. 
        String encodedValue1 = URLEncoder.encode(value1, StandardCharsets.UTF_8);
        String encodedValue2 = URLEncoder.encode(value2, StandardCharsets.UTF_8);

        String requestBody = key1 + "=" + encodedValue1 + "&" + key2 + "=" + encodedValue2;
		
		
		HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:8080/api/v1/login"))
				.header("Content-Type", "application/x-www-form-urlencoded")
				.header("Cache-Control", "no-cache")
				.POST(HttpRequest.BodyPublishers.ofString(requestBody))
				.build();
		System.out.println(request.toString());
		
		
		 try {
	            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

	            // Handling the response.
	            statusCode = response.statusCode();
	            
	            if (statusCode == 403) {
	            	return statusCode;
	            }
	            // Save the response body as a JSON for easier parsing
	            JSONObject json = new JSONObject(response.body());
	            
	            // Print the status code and response body
	            System.out.println("Status code: " + statusCode);
	            System.out.println("Response body: " + json.toString());
	            
	            
	            saveAccessToken(json.getString("access_token"));
	            saveRefreshToken(json.getString("refresh_token"));
	            
	            
	        } catch (IOException e) {
	            // Handle IOException (e.g., network error)
	            e.printStackTrace();
	        } catch (InterruptedException e) {
	            // Handle InterruptedException (e.g., the request was cancelled)
	            e.printStackTrace();
	        }
		 
		 return statusCode;

	}
	
}
