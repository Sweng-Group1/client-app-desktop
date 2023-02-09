package sweng.group.one.client_app_desktop.media;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.awt.Point;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import sweng.group.one.client_app_desktop.presentation.Slide;
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;

public class VideoPlayerTest {
	
	private Slide slide;
	private final static String TEST_ASSET_PATH = "./src/test/java/sweng/group/one/client_app_desktop/media/testAssets/";
	private VideoPlayer testVideoPlayer;
	
	
	@Before
	public void setup() {
		Slide slide = new Slide(400, 400); 
		JFrame f= new JFrame("Panel Example");    
        JPanel panel=new JPanel();  
        //panel.setBounds(40,80,200,200);     
        f.setSize(400,400);    
        //f.setLayout(null);    
        //f.setVisible(true);  
		Point pos = new Point(0, 0);
		int width = 400;
		int height = 400;
		float duration = 1;
		URL samplemp4 = null;
		//using a URL to a file that wont get deleted
		try {
			samplemp4 = new URL("https://getsamplefiles.com/download/mp4/sample-5.mp4");
					
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		testVideoPlayer = new VideoPlayer(pos, width, height, duration, slide, samplemp4, true);
		  
		panel.add(testVideoPlayer);
		testVideoPlayer.setVisible(true);
		panel.setVisible(true);
		f.add(panel);  
		f.setVisible(true);
	}
	
	@Test
	public void togglePlayingTest() {
		testVideoPlayer.loadFile();
		System.out.println("Video loaded successfully.");
		assertFalse("Video player starts running", testVideoPlayer.getPlaying());
		testVideoPlayer.togglePlaying();
		assertTrue("Video player does not unpause", testVideoPlayer.getPlaying());
	}
	
	/*	@Test
	public void LoadFileTest() {
		
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
			 } catch (IllegalStateException exception) {
				 System.out.println("Failed to load Video.");
			 }  
	} */
	
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
		
/*	
	@AfterClass
	public static void cleanup() {
		File assetFolder = new File(System.getProperty("java.io.tmpdir") + "WhatsOn\\assets\\");
		File tempFolder = new File(System.getProperty("java.io.tmpdir") + "WhatsOn\\");
		
		for (File file:assetFolder.listFiles()) {
			try {
				file.delete();
			} finally {
				
			}
		}
		assetFolder.delete();
		tempFolder.delete();
	}
	*/
}
