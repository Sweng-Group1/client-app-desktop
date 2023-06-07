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
import sweng.group.one.client_app_desktop.sceneControl.MapScene;
import org.mapsforge.core.graphics.Bitmap;

/* TEST STRATEGY
 * Here we verify the client post interactions with the server work correctly. 
 * Must test we can upload and retrieve posts, and that the information contained
 * is as we expect. 
 * These are integration tests that verify communication with the server. 
 * As such they require the server to be running. 
 * @author Paul Pickering
 */
public class PostServerIntegrationTests {
	
	private static final String MARKER_IMAGE_PATH = "./assets/map/marker.png";
	
	// Needed for authorisation aspects of tests. 
	private String defaultAdminUsername = "sid";
	private String defaultAdminPass = "password123";
	private User userTest = new User(defaultAdminUsername);
	private UserService userService = new UserService();
	
	@Before
	public void setup() throws IOException, AuthenticationException {
	// Ensure we have a valid access token for the tests. 
	int statusCode = userService.login(userTest, defaultAdminPass);
	assertThat(statusCode).isEqualTo(200);
	System.out.println("Setup for map integration tests: Login status code is " + statusCode);
	}
	
	@Test
	public void canUploadAndDownloadAPost() throws SAXException, ParserConfigurationException, IOException, AuthenticationException {
	    // Get the system temp directory
	    String tempDirectoryPath = System.getProperty("java.io.tmpdir") + "/WhatsOn/assets/";

	    // Ensure the directory exists
	    File tempDirectory = new File(tempDirectoryPath);
	    if (!tempDirectory.exists()) {
	        tempDirectory.mkdirs();
	    }

	    // Create a temp file in the specified directory
	    Path tempFilePath = Files.createTempFile(tempDirectory.toPath(), "temp", "evaluation.xml");
	    File testXML = tempFilePath.toFile();

	    // Write some text to it
	    Files.write(tempFilePath,("Some text").getBytes());

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
	    marker.setName("#MovieSociety");
	    
	    int statusCode = PostService.uploadPost(tempFilePath, 10, marker, userTest.getAccessToken());
	    assertThat(statusCode).isEqualTo(200);
	    ArrayList<Path> posts = PostService.retrievePostsXMLs(userTest.getAccessToken());
	    
	    assertThat(Files.isSameFile(tempFilePath, posts.get(0)));

	    // Delete the temp file when the test is done
	    testXML.deleteOnExit();
	}

	@Test
	public void canRetrieveAPostWithAPaticularHashtag() throws SAXException, ParserConfigurationException, IOException, AuthenticationException {
	    // Get the system temp directory
	    String tempDirectoryPath = System.getProperty("java.io.tmpdir") + "/WhatsOn/assets/";

	    // Ensure the directory exists
	    File tempDirectory = new File(tempDirectoryPath);
	    if (!tempDirectory.exists()) {
	        tempDirectory.mkdirs();
	    }

	    // Create a temp file in the specified directory
	    Path tempFilePath = Files.createTempFile(tempDirectory.toPath(), "temp", "evaluation-hashtag.xml");
	    File testXML = tempFilePath.toFile();

	    Files.write(tempFilePath, "#MovieSociety".getBytes());

	    MapScene mapScene = new MapScene();
	    LatLong markerPos = new LatLong(50, 50);
	    BufferedImage markerImage = null;
	    markerImage = ImageIO.read(new File(MARKER_IMAGE_PATH));
	    
	    Bitmap markerBitmap = new AwtBitmap(markerImage);
	    EventMarker marker = new EventMarker(mapScene, markerPos, markerBitmap);
	    marker.setName("#MovieSociety");
	    
	    int statusCode = PostService.uploadPost(tempFilePath, 10, marker, userTest.getAccessToken());
	    assertThat(statusCode).isEqualTo(200);
	    ArrayList<Path> posts = PostService.retrievePostsWithHastagXMLs("#MovieSociety", userTest.getAccessToken());
	    
	    assertThat(Files.isSameFile(tempFilePath, posts.get(0)));

	    // Delete the temp file when the test is done
	    testXML.deleteOnExit();
	}
	
	@Test
	public void RetrievePostsByHashtagWillIgnorePostsWithoutTag() throws SAXException, ParserConfigurationException, IOException, AuthenticationException {
	    // Get the system temp directory
	    String tempDirectoryPath = System.getProperty("java.io.tmpdir") + "/WhatsOn/assets/";

	    // Ensure the directory exists
	    File tempDirectory = new File(tempDirectoryPath);
	    if (!tempDirectory.exists()) {
	        tempDirectory.mkdirs();
	    }

	    // Create a temp file in the specified directory
	    Path tempFilePath = Files.createTempFile(tempDirectory.toPath(), "temp", "evaluation-hashtag.xml");
	    File testXML = tempFilePath.toFile();

	    Files.write(tempFilePath, "#MovieSociety".getBytes());

	    MapScene mapScene = new MapScene();
	    LatLong markerPos = new LatLong(50, 50);
	    BufferedImage markerImage = null;
	    try {
	        markerImage = ImageIO.read(new File(MARKER_IMAGE_PATH));
	    } catch (IOException e) {
	        throw new IOException("Error reading the marker image");
	    }
	    
	    Bitmap markerBitmap = new AwtBitmap(markerImage);
	    EventMarker marker = new EventMarker(mapScene, markerPos, markerBitmap);
	    marker.setName("#MovieSociety");
	    
	    int statusCode = PostService.uploadPost(tempFilePath, 10, marker, userTest.getAccessToken());
	    assertThat(statusCode).isEqualTo(200);
	    ArrayList<Path> posts = PostService.retrievePostsWithHastagXMLs("#NotInTheDatabase", userTest.getAccessToken());   
	    assertThat(posts.isEmpty());

	    // Delete the temp file when the test is done
	    testXML.deleteOnExit();
	}
}

