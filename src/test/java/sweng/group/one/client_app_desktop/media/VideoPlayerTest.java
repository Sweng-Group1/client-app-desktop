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
	
	/* By rights this should be part of setup, but the current design of VideoPlayer
	 * makes it difficult to parameterise tests */
	private void initPlayer(String str) throws MalformedURLException {
		Point pos = new Point(0, 0);
		int width = 400;
		int height = 400;
		URL url = new URL(str);
		testVideoPlayer = new VideoPlayer(pos, width, height, testSlide, url, false);
		testSlide = new Slide(width, height);
		testSlide.add(testVideoPlayer);
		testFrame.add(testSlide);
	}
	
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
	public void loadFileTest(String url) throws Exception {
		initPlayer(url);
		testVideoPlayer.loadFile();
	}
	
	@Test
	public void togglePlayingTest() throws Exception {
		initPlayer("https://getsamplefiles.com/download/mp4/sample-5.mp4");
		testVideoPlayer.loadFile();
		assertFalse("Video player starts running", testVideoPlayer.getPlaying());
		testVideoPlayer.togglePlaying();
		Thread.sleep(1000);
		assertTrue("Video player does not unpause", testVideoPlayer.getPlaying());
		testVideoPlayer.togglePlaying();
		Thread.sleep(1000);
		assertFalse("Video player pauses", testVideoPlayer.getPlaying());
	}
	
	/* This test doesnt tell us anything about videoplayer, but gives us 
	 * an easy way to detect if VLC isnt working on the host machine. */
	@Test
	public void detectLibsTest() throws MalformedURLException {
		initPlayer("https://getsamplefiles.com/download/mp4/sample-5.mp4");
		assertTrue("Native libraries not found", testVideoPlayer.nativeLibs());
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
