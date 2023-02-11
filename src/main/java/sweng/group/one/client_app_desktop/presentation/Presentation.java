package sweng.group.one.client_app_desktop.presentation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Presentation extends JPanel {
	private ArrayList<Slide> slides;
	private int currentSlide;
	
	public Presentation(List<Slide> slides){
		this.slides = (ArrayList<Slide>) slides;
		currentSlide = 0;
		showCurrentSlide();
	}
	
	public void addSlide(Slide newSlide) {
		slides.add(newSlide);
		this.add(newSlide);
		newSlide.setVisible(false);
	}
	
	private void showCurrentSlide() {
		Slide desiredSlide = slides.get(currentSlide);
		for (Slide slide:slides) {
			slide.setVisible(slide == desiredSlide);
		}
		desiredSlide.displaySlide();
	}
	
	public void nextSlide() {
		int maxSlide = slides.size()-1;
		currentSlide = (currentSlide + 1) % maxSlide;
		showCurrentSlide();
	}
	
	public void prevSlide() {
		int maxSlide = slides.size()-1;
		currentSlide = (currentSlide + maxSlide - 1) % maxSlide;
		showCurrentSlide();
	}
	
	public Slide getCurrentSlide() {
		return slides.get(currentSlide);
	}
	
	public List<Slide> getSlides() {
		return slides;
	}
}
