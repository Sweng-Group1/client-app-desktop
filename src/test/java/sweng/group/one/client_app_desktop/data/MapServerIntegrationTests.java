package sweng.group.one.client_app_desktop.data;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.Before;
import org.junit.Test;

// As these are integration tests, they require the server to be running. 
// TODO: Create unit tests for these methods with mocked server interactions. 

public class MapServerIntegrationTests {
	
	private String testMapName = "York";
	private Map testMap = new Map(testMapName);
	private MapService testMapService = new MapService();
	
	// Need these for authorisation as uploading maps is restricted to Admins. 
	private String defaultAdminUsername = "sid";
	private String defaultAdminPass = "password123";
	private User userTest = new User(defaultAdminUsername);
	private UserService userService = new UserService();

	
	// Create a map file for the tests. 
	@Before
	public void setup() throws IOException {
	File tempFile = File.createTempFile("york", ".map");
	
    // Write some test data
	String testData = "some test data";
	Files.write(tempFile.toPath(), testData.getBytes());
	testMap.setFile(tempFile);
	
	// Ensure we have a valid access token for the tests. 
	int statusCode = userService.login(userTest, defaultAdminPass);
	
	System.out.println("Setup for map integration tests: Login status code is " + statusCode);
	}

	@Test
	public void testUploadingMap() {
	int statusCode = testMapService.uploadMap(testMap, userTest.getAccessToken());
	assertThat(statusCode).isEqualTo(200);
	}

	@Test
	public void canDownloadMap() {
	Path mapPath = testMapService.retrieveMap(testMapName);
	assertThat(mapPath).isNotEmptyFile();
	}


}
