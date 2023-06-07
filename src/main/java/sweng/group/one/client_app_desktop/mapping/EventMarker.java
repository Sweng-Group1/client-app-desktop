package sweng.group.one.client_app_desktop.mapping;

import java.util.ArrayList;
import java.util.List;

import org.mapsforge.core.graphics.Bitmap;
import org.mapsforge.core.model.LatLong;
import org.mapsforge.core.model.Point;
import org.mapsforge.map.layer.overlay.Marker;
import org.mapsforge.map.model.Model;

import sweng.group.one.client_app_desktop.presentation.Presentation;
import sweng.group.one.client_app_desktop.sceneControl.MapScene;

/**
 * @author flt515
 *
 */
public class EventMarker extends Marker{
	
	private final int tapRadius;
	private MapScene mapView;
	private boolean selected;
	private String tag;
	private ArrayList<Presentation> posts;

	
	/**
	 * Creates an EventMarker object with the given parameters.
	 *
	 * @param mapView   The MapScene object representing the map view.
	 * @param latLong   The LatLong object specifying the marker's position.
	 * @param bitmap    The Bitmap object representing the marker's image.
	 */
	public EventMarker(MapScene mapView, LatLong latLong, Bitmap bitmap) {
		super(latLong, bitmap, 0, -bitmap.getHeight()/2);
		int height = bitmap.getHeight();
		int width = bitmap.getWidth();
		this.tapRadius = height >= width ? height : width; //make whole image clickable
		this.mapView = mapView;
		this.posts = new ArrayList<>();
		this.setSelected(false);
		this.tag = "";
	}
	
	@Override
	/**
	 * Handles the onTap event for the EventMarker.
	 *
	 * @param tapLatLong The LatLong position of the tap event.
	 * @param layerXY    The Point representing the layer's position.
	 * @param tapXY      The Point representing the tap's position.
	 * @return True if the marker was tapped, false otherwise.
	 */
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
	
	public void addPost(Presentation post) {
		posts.add(post);
	}
	
	public List<Presentation> getPosts(){
		return posts;
	}
	
	public int getNumPosts() {
		return posts.size();
		
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
		this.requestRedraw();
	}

	public String getName() {
		return tag;
	}

	public void setName(String name) {
		this.tag = name;
	}

}