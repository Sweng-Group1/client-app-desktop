package sweng.group.one.client_app_desktop.data;

import static org.assertj.core.api.Assertions.assertThat;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.imageio.ImageIO;

import org.junit.Test;

/* TEST STRATEGY
 * Here we verify the client media interactions with the server work correctly. 
 * Must test we can upload and retrieve media, and the media retrieved is as expected. 
 * 
 * These are integration tests that verify communication with the server. 
 * As such they require the server to be running. 
 * @author Paul Pickering
 */
public class MediaServerIntegrationTests {
	@Test
	public void canUploadMedia() throws AuthenticationException, IOException {
		
		// Must be logged in for some tests.
		String defaultAdminUsername = "sid";
		String defaultAdminPass = "password123";
		User userTest = new User(defaultAdminUsername);
		UserService userService = new UserService();
		// Ensure we have a valid access token for the tests. 
		userService.login(userTest, defaultAdminPass);
		File testFile = null;
		
        int width = 100;
        int height = 100;

        // Create a buffered image in which to draw
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        java.awt.Graphics2D g2d = bufferedImage.createGraphics();

        // Draw a green rectangle
        g2d.setColor(java.awt.Color.GREEN);
        g2d.fillRect(0, 0, width, height);

        // Graphics context no longer needed so dispose it
        g2d.dispose();

        // Save as JPEG
        testFile = new File("test.jpg");
        ImageIO.write(bufferedImage, "jpg", testFile);

		
		int statusCode = MediaService.uploadMedia(testFile, userTest.getAccessToken());
		assertThat(statusCode).isEqualTo(200);	
	}
	
	@Test
	public void canRetrieveMedia() throws IOException {
		Path media = MediaService.retrieveMedia(5);
		assertThat(Files.exists(media)).isTrue();
	}
}
