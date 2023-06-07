package sweng.group.one.client_app_desktop.data;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * This class handles logging in, refreshing access and authentication tokens, 
 * storing them, and storing the user's username. 
 * @author Paul Pickering
**/
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

	/**
	 * Saves the access token locally, and also saves it to the User field.
	 * 
	 * @param token the access token to the saved.
	 */
	public void saveAccessToken(String token) throws IOException {
		// Get the system temp directory
		String directory = System.getProperty("java.io.tmpdir") + "/WhatsOn/tokens/";
		 Path directoryPath = Path.of(directory);

;
		// Checking if temp folder already exists, if not create one.
		if (!Files.exists(directoryPath)) {
			Files.createDirectory(directoryPath);
			System.out.println("Temp directory for tokens created at: " + directoryPath);
		}
	   
		this.accessToken = token;
		Files.write(Path.of(directory + username + "/access_token.txt"), token.getBytes());
		Files.write(Path.of(directory + "last_login.txt"), username.getBytes());
	}

	/**
	 * Saves the refresh token locally, and also saves it to the User field.
	 * 
	 * @param token the refresh token to be saved.
	 */
	public void saveRefreshToken(String token) throws IOException {
		// Get the system temp directory
		String directory = System.getProperty("java.io.tmpdir") + "/WhatsOn/tokens/";
		Path directoryPath = Path.of(directory);

		// Checking if temp folder already exists, if not create one.
		if (!Files.exists(directoryPath)) {
			Files.createDirectory(directoryPath);
			System.out.println("Temp directory for tokens created at: " + directoryPath);
		}
	   
		this.accessToken = token;
		Files.write(Path.of(directory + username + "/refresh_token.txt"), token.getBytes());
		Files.write(Path.of(directory + "last_login.txt"), username.getBytes());
		
	}

	/**
	 * Reads the refresh token from disk.
	 */
	public String readRefreshToken() throws IOException {
		String filePath = System.getProperty("java.io.tmpdir") + "/WhatsOn/tokens/" + username + "/";
		Path filepath = Paths.get(filePath + "refresh_token.txt");
		return Files.readString(filepath);
	}

	/**
	 * Reads the access token from disk.
	 */
	public String readAccessToken() throws IOException {
		String filePath = System.getProperty("java.io.tmpdir") + "/WhatsOn/tokens/" + username + "/";
		Path filepath = Paths.get(filePath + "access_token.txt");
		return Files.readString(filepath);
	}
	
	/**
	 * Allows us to remember the last logged in user before sessions, so we can attempt to login automatically.
	 * @return username of the last logged in user on this device.
	 * @throws IOException
	 */
	
	public String readLastLogin() throws IOException {
		String filePath = System.getProperty("java.io.tmpdir") + "/WhatsOn/tokens/";
		Path filepath = Paths.get(filePath + "last_login.txt");
		return Files.readString(filepath);
	}

}