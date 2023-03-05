package sweng.group.one.client_app_desktop.media;

import java.awt.Point;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JFrame;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import sweng.group.one.client_app_desktop.presentation.Slide;

public class ImageViewerTest {
	
	private ImageViewer viewerNormalUse;
	private ImageViewer viewerEdgeOfSlide;
	private ImageViewer viewerOutsideSlide;
	private ImageViewer viewerNegativePosition;
	private ImageViewer viewerLargerThanSlide;;
	
	private Slide testSlide;
	private JFrame testFrame;
	
	private int slideX;
	private int slideY;
	
	private Point posDefault;
	private Point posEdgeOfSlide;
	private Point posOutsideSlide;
	private Point posNegative;
	
	private int widthHalfSlide;
	private int widthTwiceSlide;
	private int heightHalfSlide;
	private int heightTwiceSlide;
	
	private float duration;
	
	private URL sampleImage1;
	private URL sampleImage2;
	private URL sampleImage3;
	
	@Before
	public void setup() {
		slideX = 200;
		slideY = 200;
		duration  = 1;
		
		posDefault = new Point(10, 10);
		posEdgeOfSlide = new Point(slideX, slideY);
		posOutsideSlide = new Point(slideX+1,slideY+1);
		posNegative = new Point(-1, -1);
		
		widthHalfSlide = slideX/2;
		widthTwiceSlide = slideX*2;
		heightHalfSlide = slideY/2;
		heightTwiceSlide = slideY*2;
		
		testFrame = new JFrame();  
		testFrame.setSize(800, 400);  
        testFrame.setVisible(true);    
        testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        testSlide = new Slide(slideX, slideY);
		
		try {
			sampleImage1 = new URL("https://getsamplefiles.com/download/png/sample-1.png");
			sampleImage2 = new URL("https://getsamplefiles.com/download/png/sample-2.png");
			sampleImage3 = new URL("https://getsamplefiles.com/download/png/sample-3.png");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testNormalUse() {
		viewerNormalUse = new ImageViewer(posDefault, widthHalfSlide, heightHalfSlide, duration, testSlide, sampleImage1);
		testSlide.add(viewerNormalUse);
	}
	
	@Test
	public void testEdgeOfSlide() {
		viewerEdgeOfSlide = new ImageViewer(posEdgeOfSlide, widthHalfSlide, heightHalfSlide, duration, testSlide, sampleImage1);
		testSlide.add(viewerEdgeOfSlide);
	}
	
	@Test
	public void testOutsideSlide() {
		viewerOutsideSlide = new ImageViewer(posOutsideSlide, widthHalfSlide, heightHalfSlide, duration, testSlide, sampleImage1);
		testSlide.add(viewerOutsideSlide);
	}
	
	@Test
	public void testNegativePosition() {
		viewerNegativePosition = new ImageViewer(posNegative, widthHalfSlide, heightHalfSlide, duration, testSlide, sampleImage1);
		testSlide.add(viewerNegativePosition);
	}
	
	@Test
	public void testLargerThanSlide() {
		viewerLargerThanSlide = new ImageViewer(posDefault, widthTwiceSlide, heightTwiceSlide, duration, testSlide, sampleImage1);
		testSlide.add(viewerLargerThanSlide);
	}
	
	@After
	public void draw() {
		testFrame.add(testSlide);
		testSlide.validate();
		testFrame.validate();
	}
	
	@AfterClass
	public static void sleep() throws InterruptedException {
		Thread.sleep(10000);
	}
	
}
