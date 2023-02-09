package sweng.group.one.client_app_desktop.media;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.awt.Point;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import sweng.group.one.client_app_desktop.presentation.Slide;
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;


@RunWith(JUnitParamsRunner.class)
public class VideoPlayerTest {
	
	private VideoPlayer testVideoPlayer;
	private JFrame testFrame;
	private Slide testSlide;
	
	@Before
	public void setup() {
		testFrame = new JFrame();  
        testFrame.setSize(400,400);    
        testFrame.setVisible(true);
	}
	
	@Test
	@Parameters({
         "https://getsamplefiles.com/download/mp4/sample-5.mp4",
         "https://getsamplefiles.com/download/mkv/sample-3.mkv",
         "https://getsamplefiles.com/download/wmv/sample-2.wmv",
         "https://getsamplefiles.com/download/vob/sample-2.vob",
         "https://getsamplefiles.com/download/avi/sample-3.avi",
         "https://getsamplefiles.com/download/flv/sample-3.flv",
         "https://getsamplefiles.com/download/mov/sample-2.mov",
         "https://getsamplefiles.com/download/webm/sample-3.webm"
	})
	public void loadFileTest(String str) throws MalformedURLException {
		Point pos = new Point(0, 0);
		int width = 400;
		int height = 400;
		float duration = 1;
		URL url = new URL(str);
		testVideoPlayer = new VideoPlayer(pos, width, height, duration, testSlide, url, false);
		testFrame.add(testVideoPlayer);
		testVideoPlayer.setVisible(true);
		testVideoPlayer.loadFile();
	}
	
	@Test
	public void togglePlayingTest() {
		
		System.out.println("Video loaded successfully.");
		assertFalse("Video player starts running", testVideoPlayer.getPlaying());
		testVideoPlayer.togglePlaying();
		assertTrue("Video player does not unpause", testVideoPlayer.getPlaying());
	}
	
	
	
	/*@Test
	public void getPlayingTest() {
		
		Point pos = new Point(10, 10);
		int width = 50;
		int height = 50;
		float duration = 1;
		URL samplemp4 = null;
		//using a URL to a file that wont get deleted
		try {
			samplemp4 = new URL("https://getsamplefiles.com/download/mp4/sample-5.mp4");
					
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		VideoPlayer TestVideoPlayer = new VideoPlayer(pos, width, height, duration, slide, samplemp4, true);
		
		try {
				TestVideoPlayer.loadFile();
				System.out.println("Video loaded successfully.");
				assertFalse(TestVideoPlayer.getPlaying());
				TestVideoPlayer.togglePlaying();
				assertTrue(TestVideoPlayer.getPlaying());
			 } catch (IllegalStateException exception) {
				 System.out.println("Failed to load Video.");
			 }  
		
	} */
		
	@After
	public void teardown() {
		testFrame.dispose();
	}
}
