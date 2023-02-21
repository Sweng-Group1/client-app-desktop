package sweng.group.one.client_app_desktop.sceneControl;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Test;
import org.mapsforge.core.graphics.Bitmap;
import org.mapsforge.core.model.LatLong;
import org.mapsforge.map.awt.graphics.AwtBitmap;

import sweng.group.one.client_app_desktop.mapping.EventMarker;

public class MapSceneTest {

	private static final String MARKER_IMAGE_PATH = "./assets/map/marker.png";
	
	@Test
	public void addEventMarkerTest() {
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
		
		assertEquals(true, mapScene.getMarkers().contains(marker));
		
		mapScene.addEventMarker(markerPos);
		assertEquals(true, mapScene.getMarkers().size() == 2);
	}

}
