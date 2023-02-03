package sweng.group.one.client_app_desktop.presentation;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

public class PresentationTest {

	private static ArrayList<Slide> slides;
	
	@BeforeClass
	public static void setup() {
		slides = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			Slide newSlide = new Slide(400, 300);
			slides.add(newSlide);
		}
	}
	
	@Test
	public void nextSlideTest() {
		Presentation pres = new Presentation(slides);
		pres.nextSlide();
		assertEquals(slides.get(1), pres.getCurrentSlide());
		
		ArrayList<Slide> retrievedSlides = pres.getSlides();
		assertEquals(false, retrievedSlides.get(0).isVisible());
		assertEquals(true, retrievedSlides.get(1).isVisible());
		assertEquals(false, retrievedSlides.get(2).isVisible());
	}
	
	@Test
	public void prevSlideTest() {
		Presentation pres = new Presentation(slides);
		pres.prevSlide();
		assertEquals(slides.get(2), pres.getCurrentSlide());
		
		ArrayList<Slide> retrievedSlides = pres.getSlides();
		assertEquals(false, retrievedSlides.get(0).isVisible());
		assertEquals(false, retrievedSlides.get(1).isVisible());
		assertEquals(true, retrievedSlides.get(2).isVisible());
	}
	
	@Test
	public void addSlideTest() {
		Presentation pres = new Presentation(slides);
		Slide newSlide = new Slide(400, 300);
		
		pres.addSlide(newSlide);
		ArrayList<Slide> retrievedSlides = pres.getSlides();
		
		assertEquals(4, retrievedSlides.size());
	}

}
