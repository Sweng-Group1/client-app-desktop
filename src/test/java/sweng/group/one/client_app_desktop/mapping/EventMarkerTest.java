package sweng.group.one.client_app_desktop.mapping;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Test;
import org.mapsforge.core.graphics.Bitmap;
import org.mapsforge.core.model.LatLong;
import org.mapsforge.core.model.Point;
import org.mapsforge.map.awt.graphics.AwtBitmap;

import sweng.group.one.client_app_desktop.sceneControl.MapScene;

public class EventMarkerTest {
	
	private static final String MARKER_IMAGE_PATH = "./assets/map/marker.png";
	
	@Test
	public void onTapTest() {
		MapScene mapScene = new MapScene();
		LatLong markerPos = new LatLong(50, 50);
		BufferedImage markerImage = null;
		try {
			markerImage = ImageIO.read(new File(MARKER_IMAGE_PATH));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	 	Bitmap markerBitmap = new AwtBitmap(markerImage);
		
		EventMarker marker = new EventMarker(mapScene, markerPos, markerBitmap);
		mapScene.addEventMarker(marker);
		Point onMarker = new Point(0, 0);
		boolean tapped = marker.onTap(null, onMarker, onMarker);
		
		assertEquals(true, tapped);
		assertEquals(true, marker.isSelected());
		
		Point offMarker = new Point(markerImage.getWidth()*2, markerImage.getHeight()*2);
		tapped = marker.onTap(null, onMarker, offMarker);
		
		assertEquals(false, tapped);
		assertEquals(false, marker.isSelected());
	}

}
