package sweng.group.one.client_app_desktop.data;

import static org.assertj.core.api.Assertions.assertThat;

import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.imageio.ImageIO;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class MediaServerIntegrationTests {
	
	private MediaService testMediaService = new MediaService();
	
	@BeforeClass
	public static void setup() throws IOException {
	
	}
	
	@Test
	public void canUploadMedia() {
		
		
		// Must be logged in for some tests.
		String defaultAdminUsername = "sid";
		String defaultAdminPass = "password123";
		User userTest = new User(defaultAdminUsername);
		UserService userService = new UserService();
		// Ensure we have a valid access token for the tests. 
		userService.login(userTest, defaultAdminPass);
		File testFile = null;
		
		try {
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
            
        } catch (IOException ie) {
            ie.printStackTrace();
        }
		
		int statusCode = testMediaService.uploadMedia(testFile, userTest.getAccessToken());
		assertThat(statusCode).isEqualTo(200);	
	}
	
	@Test
	public void canRetrieveMedia() {
		
		Path media = testMediaService.retrieveMedia(5);
		try {
			Desktop.getDesktop().open(media.toFile());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(media.toString());
		assertThat(Files.exists(media)).isTrue();
		
	}
	
	
		

}
