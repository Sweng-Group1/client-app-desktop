package sweng.group.one.client_app_desktop.mapping;

import org.mapsforge.core.graphics.Bitmap;
import org.mapsforge.core.model.LatLong;
import org.mapsforge.core.model.Point;
import org.mapsforge.map.layer.overlay.Marker;
import org.mapsforge.map.model.Model;

import sweng.group.one.client_app_desktop.sceneControl.MapScene;

/**
 * @author flt515
 *
 */
public class EventMarker extends Marker{
	
	private final int tapRadius;
	private MapScene mapView;
	private int numPosts;
	private boolean selected;
	//TODO: Add posts

	public EventMarker(MapScene mapView, LatLong latLong, Bitmap bitmap) {
		super(latLong, bitmap, 0, -bitmap.getHeight()/2);
		int height = bitmap.getHeight();
		int width = bitmap.getWidth();
		this.tapRadius = height >= width ? height : width; //make whole image clickable
		this.mapView = mapView;
		this.setSelected(false);
		//TODO: Sort this out when posts are added
		numPosts = (int)(Math.random() * 15);
	}
	
	@Override
	public boolean onTap(LatLong tapLatLong, Point layerXY, Point tapXY) {
		if (layerXY.distance(tapXY) < tapRadius) {
			mapView.selectMarker(this);
			Model model = mapView.getModel();
			model.mapViewPosition.animateTo(this.getLatLong());
			return true;
		}
		else {
			mapView.selectMarker(null);
			return false;
		}
	}
	
	public int getNumPosts() {
		return numPosts;
		
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
		this.requestRedraw();
	}

}
