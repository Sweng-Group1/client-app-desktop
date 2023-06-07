package sweng.group.one.client_app_desktop.data;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.Test;

/*
 * TEST STRATEGY
 * These are integration tests that verify communication with the server. 
 * As such they require the server to be running.  
 * @author Paul Pickering
 */ 

public class LoginIntegrationTests {
	
	private String defaultAdminUsername = "sid";
	private String defaultAdminPass = "password123";
	private User userTest = new User(defaultAdminUsername);
	private UserService serviceTest = new UserService();
	
	@Test
	public void testLoginUsingDefaultAdminCredentialsReturns200OkCode() throws AuthenticationException, IOException {
		System.out.println("Running login test for correct credentials: ");
		int statusCode = serviceTest.login(userTest, defaultAdminPass);
		System.out.println("Status Code: " + statusCode);
		assertThat(statusCode).isEqualTo(200);
	}
	
	
	
	@Test
	public void CanRefreshAccessToken() throws IOException, AuthenticationException {
		System.out.println("Running login test for correct credentials: ");
		int statusCode = serviceTest.login(userTest, defaultAdminPass); // Ensure refresh token is valid. 
		statusCode = serviceTest.refreshAccessToken(userTest);
		System.out.println("Status Code: " + statusCode);
		assertThat(statusCode).isEqualTo(200);
	}
	
	
	@Test(expected = AuthenticationException.class)
	public void LoggingInWithIncorrectPasswordThrowsAuthenticationException() throws AuthenticationException, IOException {
		System.out.println("Running login test for incorrect credentials: ");
		serviceTest.login(userTest, "ThisPasswordIsIncorrect");
	}
	
	
	// Valid tokens will consist of a long seemingly random set of characters. 
	// Failure to save these strings will likely result in an empty file.
	// Checking length of line in file is reasonably big should reliably check the tokens exist.
	// TODO: These are unit tests for the User class now, move to another file.  
	@Test
	public void tokensAreSavedAndAreRetrieveableFromFile() throws AuthenticationException, IOException {
		System.out.println("Running test to ensure tokens are saved correctly:  ");
		
		serviceTest.login(userTest, defaultAdminPass);
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
