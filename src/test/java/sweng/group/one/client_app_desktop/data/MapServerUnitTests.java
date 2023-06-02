package sweng.group.one.client_app_desktop.data;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

/**
 * @author Paul Pickering 
 * TEST STRATEGY
 * The results of the upload method can only be properly verified by integration testing, 
 * but we can verify the download method saves the file correctly with a mocked server response. 
 */

@RunWith(MockitoJUnitRunner.class)
public class MapServerUnitTests {

	private MockWebServer server;
	private MapService serviceTest = new MapService();
	private File tempFile; 
	// This needs to the same as the MAP_FOLDER specified in MapService. 
	private static final String MAP_FOLDER = "./assets/map/";
	
	@Mock
	private Map mockedMap;
	
	@Before
	public void setup() throws IOException {
		server = new MockWebServer();
		server.start(8080);
	}
	
	@After
	public void cleanup() {
		tempFile.delete();
	}
	
	@Test
	public void RetrieveMapReturnsCorrectPathAndSavesFile() throws IOException {
		JSONObject body = new JSONObject();
		body.put("id", "1");
		body.put("name", "MockedMap");
		body.put("filepath", "/mockserver/test");
		
		server.enqueue(new MockResponse()
				.setResponseCode(200)
				.setBody(body.toString()));
		
		Path foundMap = serviceTest.retrieveMap("MockedMap");
		tempFile = foundMap.toFile();
		Path expectedMap = Paths.get(MAP_FOLDER + "MockedMap" + ".map");
		
		assertThat(expectedMap).isEqualTo(foundMap);
		assertThat(tempFile.exists()).isTrue();
	}
}
