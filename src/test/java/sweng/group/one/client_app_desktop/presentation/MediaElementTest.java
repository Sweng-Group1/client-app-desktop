package sweng.group.one.client_app_desktop.presentation;

import static org.junit.Assert.assertArrayEquals;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;

import javax.imageio.ImageIO;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import sweng.group.one.client_app_desktop.media.MediaElement;

public class MediaElementTest {
	
	private Slide slide;
	private final static String TEST_ASSET_PATH = "./src/test/java/sweng/group/one/client_app_desktop/presentation/testAssets/";
	
	@Before
	public void setup() {
		Slide slide = new Slide(100, 100);
	}
	
	@Test
	public void imageDownloadTest() throws IOException {
		Point pos = new Point(10, 10);
		int width = 50;
		int height = 50;
		float duration = 1;
		URL sampleImage = null;
		//using a URL to a file that wont get deleted
		try {
			sampleImage = new URL("https://getsamplefiles.com/download/png/sample-1.png");
					
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		MediaElement mediaElement = Mockito.mock(MediaElement.class, 
													Mockito.withSettings()
													.useConstructor(pos, width, height, duration, slide, sampleImage)
													.defaultAnswer(Mockito.CALLS_REAL_METHODS));
		
		//Read expected image and downloaded image to byte arrays
		
		BufferedImage expected = ImageIO.read(new File(TEST_ASSET_PATH + "pngTest.png"));
		byte[] expectedArray = ((DataBufferByte) expected.getData().getDataBuffer()).getData();
		
		BufferedImage test = ImageIO.read(new File(mediaElement.getLocalPath()));
		byte[] testArray = ((DataBufferByte) test.getData().getDataBuffer()).getData();
		
		assertArrayEquals(expectedArray, testArray);
	}
	
	@Test
	public void videoDownloadTest() throws IOException {
		Point pos = new Point(10, 10);
		int width = 50;
		int height = 50;
		float duration = 1;
		URL samplemp4 = null;
		//using a URL to a file that wont get deleted
		try {
			samplemp4 = new URL("https://getsamplefiles.com/download/mp4/sample-1.mp4");
					
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		MediaElement mediaElement = Mockito.mock(MediaElement.class, 
													Mockito.withSettings()
													.useConstructor(pos, width, height, duration, slide, samplemp4)
													.defaultAnswer(Mockito.CALLS_REAL_METHODS));
		
		File expected = new File(TEST_ASSET_PATH + "mp4Test.mp4");
		byte[] expectedArray = Files.readAllBytes(expected.toPath());
		
		File test = new File(mediaElement.getLocalPath());
		byte[] testArray = Files.readAllBytes(test.toPath());
		assertArrayEquals(expectedArray, testArray);
	}
	
	@Test
	public void audioDownloadTest() throws IOException {
		Point pos = new Point(10, 10);
		int width = 50;
		int height = 50;
		float duration = 1;
		URL samplemp4 = null;
		//using a URL to a file that wont get deleted
		try {
			samplemp4 = new URL("https://getsamplefiles.com/download/wav/sample-1.wav");
					
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		MediaElement mediaElement = Mockito.mock(MediaElement.class, 
													Mockito.withSettings()
													.useConstructor(pos, width, height, duration, slide, samplemp4)
													.defaultAnswer(Mockito.CALLS_REAL_METHODS));
		
		File expected = new File(TEST_ASSET_PATH + "wavTest.wav");
		byte[] expectedArray = Files.readAllBytes(expected.toPath());
		
		File test = new File(mediaElement.getLocalPath());
		byte[] testArray = Files.readAllBytes(test.toPath());
		assertArrayEquals(expectedArray, testArray);
	}
	
	@AfterClass
	public static void cleanup() {
		File assetFolder = new File(System.getProperty("java.io.tmpdir") + "WhatsOn\\assets\\");
		File tempFolder = new File(System.getProperty("java.io.tmpdir") + "WhatsOn\\");
		
		for (File file:assetFolder.listFiles()) {
			file.delete();
		}
		assetFolder.delete();
		tempFolder.delete();
	}
}
