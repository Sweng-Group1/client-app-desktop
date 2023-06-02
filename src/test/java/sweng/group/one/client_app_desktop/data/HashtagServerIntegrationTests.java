package sweng.group.one.client_app_desktop.data;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.json.JSONArray;
import org.junit.Before;
import org.junit.Test;

public class HashtagServerIntegrationTests {
	
	// Need these for authorisation as altering hashtags is limited to admins. 
	private String defaultAdminUsername = "sid";
	private String defaultAdminPass = "password123";
	private User userTest = new User(defaultAdminUsername);
	private UserService userService = new UserService();
	//private PostService postService = new PostService();
	private HashtagService underTest = new HashtagService();
	
	
	@Before
	public void setup() {
		userService.login(userTest, defaultAdminPass);
	}
	@Test
	public void testDownloadingHashtag() throws IOException, AuthenticationException {
		JSONArray hashtags = underTest.retrieveHashtagsAsJSON();
		System.out.println(hashtags.get(0).toString());
		assertThat(hashtags).isNotNull();
	}
}
