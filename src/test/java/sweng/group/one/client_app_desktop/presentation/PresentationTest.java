package sweng.group.one.client_app_desktop.presentation;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.SAXException;

import sweng.group.one.client_app_desktop.graphics.Rectangle;
import sweng.group.one.client_app_desktop.text.TextElement;

public class PresentationTest {

	private static ArrayList<Slide> slides;
	private final static int NUM_SLIDES = 5;
	
	@BeforeClass
	public static void setup() {
		slides = new ArrayList<>();
		for (int i = 0; i < NUM_SLIDES; i++) {
			Slide newSlide = new Slide(400, 300);
			slides.add(newSlide);
		}
	}
	
	@Test
	public void nextSlideTest() {
		Presentation pres = new Presentation(slides);
		pres.nextSlide();
		assertEquals(slides.get(1), pres.getCurrentSlide());
		
		ArrayList<Slide> retrievedSlides = (ArrayList<Slide>) pres.getSlides();
		assertEquals(false, retrievedSlides.get(0).isVisible());
		assertEquals(true, retrievedSlides.get(1).isVisible());
		assertEquals(false, retrievedSlides.get(2).isVisible());
	}
	
	@Test
	public void prevSlideTest() {
		Presentation pres = new Presentation(slides);
		pres.prevSlide();
		assertEquals(slides.get(NUM_SLIDES-1), pres.getCurrentSlide());
		
		ArrayList<Slide> retrievedSlides = (ArrayList<Slide>) pres.getSlides();
		for (Slide s : retrievedSlides) {
			 assertEquals(s == pres.getCurrentSlide(), s.isVisible());
		}
	}
	
	@Test
	public void addSlideTest() {
		Presentation pres = new Presentation(slides);
		Slide newSlide = new Slide(400, 300);
		
		pres.addSlide(newSlide);
		ArrayList<Slide> retrievedSlides = (ArrayList<Slide>) pres.getSlides();
		
		assertEquals(NUM_SLIDES + 1, retrievedSlides.size());
	}
	
	@Test
	/* Runs some checks to see if we can parse Penelope's
	 * Sample XML. 
	 * Checking that elements parse correctly is best done manually */
	public void parseEval() throws Exception {
		Presentation pres = new Presentation(new File("assets/xml/samples/evaluation.xml"));
		
		List<Slide> s = pres.getSlides();
		
		assertEquals("Incorrect number of slides", s.size(), 2);
		assertEquals("Incorrect number of elements in slide 1",s.get(0).getElements().size(), 4);
		assertEquals("Incorrect number of elements in slide 2",s.get(1).getElements().size(), 4);
	}
	
	@Test(expected = IOException.class)
	/* Does the XML parser reject bad paths?*/
	public void badPath() throws Exception {
		Presentation pres = new Presentation(new File("this/does/not/exist"));
	}

	@Test(expected = SAXException.class)
	/* Does the XML parser reject bad files? */
	public void nonsense() throws Exception{
		Presentation pres = new Presentation(new File("assets/xml/samples/nonsense.xml"));
	}
	
	@Test(expected = SAXException.class)
	/* Does the XML parser reject files with missing mandatory attributes? */
	public void badElements() throws Exception{
		Presentation pres = new Presentation(new File("assets/xml/samples/badElements.xml"));
	}

	@Test(expected = NullPointerException.class)
	/* Does the XML parser reject files with missing URLs */
	public void noURL() throws Exception {
		Presentation pres = new Presentation(new File("assets/xml/samples/nourl.xml"));
	}
	
}
