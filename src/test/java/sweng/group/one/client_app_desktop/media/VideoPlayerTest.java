package sweng.group.one.client_app_desktop.media;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.awt.Point;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JFrame;

import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JTextComponentFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import sweng.group.one.client_app_desktop.presentation.Slide;
import uk.co.caprica.vlcj.factory.discovery.NativeDiscovery;


@RunWith(JUnitParamsRunner.class)
public class VideoPlayerTest {
	
	@Mock
	private NativeDiscovery mockNativeDiscovery;
	
	private VideoPlayer testVideoPlayer;
	private JFrame testFrame;
	private Slide testSlide;
	
	private FrameFixture testFrameFix;
	
	/* By rights this should be part of setup, but the current design of VideoPlayer
	 * makes it difficult to parameterise tests */

	private void initPlayer(String str) throws MalformedURLException {
		// Use an actual native discovery by default.
		initPlayer(str, new NativeDiscovery());
	}
	
	private void initPlayer(String str, NativeDiscovery nd) throws MalformedURLException {
		Point pos = new Point(0, 0);
		int width = 400;
		int height = 400;
		URL url = new URL(str);
		testVideoPlayer = new VideoPlayer(pos, width, height, testSlide, url, false, nd);
		testSlide = new Slide(width, height);
		testSlide.add(testVideoPlayer);
		testFrame.add(testSlide);
	}
	
	@BeforeClass
	public static void setupOnce() {
		// Causes tests to fail if they aren't executing on the EDT
		// They'll probably fail anyways, but this will throw an 
		// EdtViolationException so you know WHY its failing.
		FailOnThreadViolationRepaintManager.install();
	}
	
	@Before
	public void setup() {
		GuiActionRunner.execute( () -> {
			MockitoAnnotations.openMocks(this);
			testFrame = new JFrame();  
	        testFrame.setSize(400,400);    
	        testFrame.setVisible(true);
		});
		testFrameFix = new FrameFixture(testFrame);
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
		GuiActionRunner.execute( () -> {
			initPlayer(url);
		});
	}
	
	@Test
	public void togglePlayingTest() throws Exception {
		GuiActionRunner.execute( () -> {
			initPlayer("https://getsamplefiles.com/download/mp4/sample-5.mp4");
			assertFalse("Video player starts running", testVideoPlayer.getPlaying());
			testVideoPlayer.togglePlaying();
			assertTrue("Video player does not unpause", testVideoPlayer.getPlaying());
			testVideoPlayer.togglePlaying();
			assertFalse("Video player pauses", testVideoPlayer.getPlaying());
		});
	}
	
	/* This test doesnt tell us anything about videoplayer, but gives us 
	 * an easy way to detect if VLC isnt working on the host machine. */
	@Test
	public void detectLibsTest() throws MalformedURLException {
<<<<<<< HEAD
		GuiActionRunner.execute( () -> {
			initPlayer("https://getsamplefiles.com/download/mp4/sample-5.mp4");
			assertTrue("Native libraries not found", testVideoPlayer.nativeLibs());
		});
	}
	
	/* Tests to see if the no VLC error message is shown */
	@Test
	public void noLibraryError() {
		GuiActionRunner.execute( () -> {
			when(mockNativeDiscovery.discover()).thenReturn(Boolean.FALSE);
			
			initPlayer("https://getsamplefiles.com/download/mp4/sample-5.mp4", mockNativeDiscovery);
		});
		JTextComponentFixture errMsg = testFrameFix.textBox("VLC Error Message");
		errMsg.requireVisible();
=======
		initPlayer("https://getsamplefiles.com/download/mp4/sample-5.mp4");
		assertTrue("Native libraries not found", testVideoPlayer.nativeLibsInstalled());
>>>>>>> 5bcb77688f6bda7cc7c54c41fd991d67bf8238e9
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
		GuiActionRunner.execute( () -> {
			testFrame.dispose();
		});
	}
}
