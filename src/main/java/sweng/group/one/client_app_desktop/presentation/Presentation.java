package sweng.group.one.client_app_desktop.presentation;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Presentation extends JPanel {
	private ArrayList<Slide> slides;
	private int currentSlide;
	
	public Presentation(ArrayList<Slide> slides){
		this.slides = slides;
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
	
	public ArrayList<Slide> getSlides() {
		return slides;
	}
}
