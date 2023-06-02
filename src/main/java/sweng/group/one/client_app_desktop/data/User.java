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

//TODO: Refactor to
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

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	
	public void saveAccessToken(String token) throws IOException {
		Path directoryPath = Paths.get("temp");
		Path filepath = Paths.get("temp/" + username + "-access_token.txt");
		// Checking if temp folder already exists, if not create one. 
		
		if(!Files.exists(directoryPath)) {
			//TODO: Fix this warning. 
			Files.createDirectory(directoryPath);
			System.out.println("Temp directory for tokens created at: " + directoryPath.toAbsolutePath());
		}
		
		this.accessToken = token;
		Files.write(filepath, token.getBytes());
	}

	//TODO: Double check best practice on the visibility here - test was complaining if these two were private. 
	public void saveRefreshToken(String token) throws IOException {
		Path directoryPath = Paths.get("temp");
		Path filepath = Paths.get("temp/" + username + "-refresh_token.txt");
		// Checking if temp folder already exists, if not create one. 
		if(!Files.exists(directoryPath)) {
			Files.createDirectory(directoryPath);
			System.out.println("Temp directory for tokens created at: " + directoryPath.toAbsolutePath());
		}
		
	this.refreshToken = token;
	Files.write(filepath, token.getBytes());
	}	
	
	public String readRefreshToken() throws IOException {
		Path filepath = Paths.get("temp/" + username + "-refresh_token.txt");
		return Files.readString(filepath);
	}
	public String readAccessToken() throws IOException {
		Path filepath = Paths.get("temp/" + username + "-access_token.txt");
		return Files.readString(filepath);
	}
	
}
