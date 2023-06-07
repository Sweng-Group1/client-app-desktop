package sweng.group.one.client_app_desktop.data;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.json.JSONArray;
import org.junit.Before;
import org.junit.Test;

/*
 * TEST STRATEGY
 * These are integration tests that verify communication with the server. 
 * As such they require the server to be running.  
 * @author Paul Pickering
 */ 
public class HashtagServerIntegrationTests {
	
	// Need these for authorisation as altering hashtags is limited to admins. 
	private String defaultAdminUsername = "sid";
	private String defaultAdminPass = "password123";
	private User userTest = new User(defaultAdminUsername);
	private UserService userService = new UserService();
	
	
	@Before
	public void setup() throws AuthenticationException, IOException {
		userService.login(userTest, defaultAdminPass);
	}
	@Test
	public void testDownloadingHashtag() throws IOException, AuthenticationException {
		JSONArray hashtags = HashtagService.retrieveHashtagsAsJSON();
		System.out.println(hashtags.get(0).toString());
		assertThat(hashtags).isNotNull();
	}
}
