package sweng.group.one.client_app_desktop.presentation;

import static org.junit.Assert.*;

import org.junit.Test;

import java.awt.Point;

public class SlideTest {

	@Test
	public void pxToPtTest() {
		Slide s = new Slide(100, 100);
		s.setSize(200, 200);
		assertEquals(new Point(50, 50), s.pxToPt(new Point(100, 100)));
	}

}
