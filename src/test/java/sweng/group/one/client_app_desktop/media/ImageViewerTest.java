package sweng.group.one.client_app_desktop.media;

import java.awt.Point;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JFrame;

import org.junit.Before;
import org.junit.Test;

import sweng.group.one.client_app_desktop.presentation.Slide;

public class ImageViewerTest {

	private ImageViewer testImageViewer;
	private Slide testSlide;
	private JFrame testFrame;
	
	private Point pos;
	private int width;
	private int height;
	private float duration;
	private URL sampleImage;
	
	@Before
	public void setup() {
		testFrame = new JFrame();  
		testFrame.setSize(800, 400);  
        testFrame.setVisible(true);    
        testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        testSlide = new Slide(800, 400);
	}
	
	public void loadFileTest() {
		pos = new Point(10, 10);
		width = 50;
		height = 50;
		duration = 1;
		try {
			sampleImage = new URL("https://getsamplefiles.com/download/png/sample-3.png");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		testImageViewer = new ImageViewer(pos, width, height, duration, testSlide, sampleImage);
		testSlide.add(testImageViewer);
		testFrame.add(testSlide);
		testFrame.setVisible(true);
	}
	
	@Test
	public void viewImageTest() throws IOException {		
		loadFileTest();
		System.out.println("Image loaded successfully.");
	}
	
}
