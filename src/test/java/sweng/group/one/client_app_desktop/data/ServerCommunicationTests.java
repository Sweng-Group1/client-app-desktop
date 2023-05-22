package sweng.group.one.client_app_desktop.data;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.time.Duration;

import org.junit.Test;
import org.junit.runner.OrderWith;

// These are integration tests that will communicate with the actual server software. 
// As such they require the server to be running. 

public class ServerCommunicationTests {
	
	private String defaultAdminUsername = "sid";
	private String defaultAdminPass = "password123";
	private User userTest = new User(defaultAdminUsername);
	
	@Test
	public void testLoginUsingDefaultAdminCredentialsReturns200OkCode() {
		System.out.println("Running login test for correct credentials: ");
		int statusCode = userTest.login(defaultAdminPass);
		System.out.println("Status Code: " + statusCode);
		assertThat(statusCode).isEqualTo(200);
	}
	
	@Test
	public void CanRefreshAccessToken() {
		System.out.println("Running login test for correct credentials: ");
		userTest.login(defaultAdminPass); // Ensure refresh token is valid. 
		int statusCode = userTest.refreshAccessToken();
		System.out.println("Status Code: " + statusCode);
		assertThat(statusCode).isEqualTo(200);
	}
	
	
	@Test
	public void LoggingInWithIncorrectPasswordReturns403Forbidden() {
		System.out.println("Running login test for incorrect credentials: ");
		int statusCode = userTest.login("ThisPasswordIsIncorrect");
		System.out.println("Status Code: " + statusCode);
		assertThat(statusCode).isEqualTo(403);
	}
	
	// Valid tokens will consist of a long seemingly random set of characters. 
	// Failure to save these strings will likely result in an empty file.
	// Checking length of line in file is reasonably big should reliably check the tokens exist.
	// TODO: Use mocking to eliminate actual server from this interaction.
	@Test
	public void tokensAreSavedAndAreRetrieveableFromFile() {
		System.out.println("Running test to ensure tokens are saved correctly:  ");
		
		userTest.login(defaultAdminPass);
		String accessToken = new String();
		String refreshToken = new String();
		try {
			accessToken = userTest.readAccessToken();
		} catch (IOException e) {
			System.out.println("Error reading file");
			assert(false);
		}
		try {
			refreshToken = userTest.readRefreshToken();
		} catch (IOException e) {
			System.out.println("Error reading file");
			assert(false);
		}

		assertThat(accessToken.length()).isGreaterThan(100);
		assertThat(refreshToken.length()).isGreaterThan(100);
	}
	
	

}
