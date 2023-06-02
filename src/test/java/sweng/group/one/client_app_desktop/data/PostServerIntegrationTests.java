package sweng.group.one.client_app_desktop.data;

import static org.assertj.core.api.Assertions.assertThat;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.Test;
import org.mapsforge.core.model.LatLong;
import org.mapsforge.map.awt.graphics.AwtBitmap;
import org.xml.sax.SAXException;

import sweng.group.one.client_app_desktop.mapping.EventMarker;
import sweng.group.one.client_app_desktop.presentation.Presentation;
import sweng.group.one.client_app_desktop.sceneControl.MapScene;
import org.mapsforge.core.graphics.Bitmap;

public class PostServerIntegrationTests {
	
	private PostService underTest = new PostService();
	private static final String MARKER_IMAGE_PATH = "./assets/map/marker.png";
	
	// Need these for authorisation as uploading maps is restricted to Admins. 
	private String defaultAdminUsername = "sid";
	private String defaultAdminPass = "password123";
	private User userTest = new User(defaultAdminUsername);
	private UserService userService = new UserService();

	
	@Before
	public void setup() throws IOException {
	// Ensure we have a valid access token for the tests. 
	int statusCode = userService.login(userTest, defaultAdminPass);
	assertThat(statusCode).isEqualTo(200);
	System.out.println("Setup for map integration tests: Login status code is " + statusCode);
	}
	
	@Test
	public void canUploadAndDownloadAPost() throws SAXException, ParserConfigurationException, IOException, AuthenticationException {
		File testXML = new File("temp/evaluation.xml");
		
		MapScene mapScene = new MapScene();
		LatLong markerPos = new LatLong(50, 50);
		BufferedImage markerImage = null;
		try {
			markerImage = ImageIO.read(new File(MARKER_IMAGE_PATH));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	 	Bitmap markerBitmap = new AwtBitmap(markerImage);
		
		EventMarker marker = new EventMarker(mapScene, markerPos, markerBitmap);
		marker.setHashtag("#MovieSociety");
		
		int statusCode = underTest.uploadPost(testXML.toPath(), 10, marker, userTest.getAccessToken());
		assertThat(statusCode).isEqualTo(200);
		ArrayList<Path> posts = underTest.retrievePostsXMLs(userTest.getAccessToken());
		File postFile = posts.get(0).toFile();
		
		assertThat(Files.isSameFile(testXML.toPath(), posts.get(0)));
	}
	
	
}

