package sweng.group.one.client_app_desktop.media;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Point;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JFrame;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import sweng.group.one.client_app_desktop.presentation.PresElement;
import sweng.group.one.client_app_desktop.presentation.Slide;

@RunWith(JUnitParamsRunner.class)
public class ImageViewerTest {
	
	private ImageViewer testImageViewer;
	
	private Slide testSlide;
	private JFrame testFrame;
	
	private int slideX;
	private int slideY;
	
	private Point pos;	
	private int width;
	private int height;	
	private float duration;
	private URL sampleImage;
	
	@Before
	public void setup() {
		slideX = 200;
		slideY = 200;
		
		pos = new Point(10, 10);		
		width = slideX/2;
		height = slideY/2;
		duration  = 1;
		
		testFrame = new JFrame();  
		testFrame.setSize(800, 400);  
        testFrame.setVisible(true);    
        testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        testSlide = new Slide(slideX, slideY);
	}
	
	private void initViewer (String url) throws MalformedURLException {
		sampleImage = new URL(url);
		testImageViewer = new ImageViewer(pos, width, height, duration, 0, 0, testSlide, sampleImage);
		testSlide.add(testImageViewer);
	}
	
	/*
	 * Tests a range of file types
	 */
	@Test
	@Parameters({
		"https://getsamplefiles.com/download/png/sample-1.png",
		"https://getsamplefiles.com/download/jpg/sample-1.jpg",
		"https://getsamplefiles.com/download/gif/sample-1.gif",
		"https://getsamplefiles.com/download/tiff/sample-1.tiff",
		"https://getsamplefiles.com/download/psd/sample-1.psd",
		"https://getsamplefiles.com/download/svg/sample-1.svg",
		"https://getsamplefiles.com/download/eps/sample-1.eps",
		"https://getsamplefiles.com/download/bmp/sample-1.bmp",
		"https://getsamplefiles.com/download/webp/sample-1.webp"
	})
	public void loadFileTest(String url) throws Exception {
		initViewer(url);
	}
	
	@Test
	public void isImageVisibleTest() throws MalformedURLException {
		initViewer("https://getsamplefiles.com/download/png/sample-1.png");
		assertTrue("Image is not visible", testImageViewer.getComponent().isVisible());
	}
	
	@After
	public void draw() {
		testFrame.add(testSlide);
		testSlide.validate();
		testFrame.validate();
	}
}
