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

	public EventMarker(MapScene mapView, LatLong latLong, Bitmap bitmap) {
		super(latLong, bitmap, 0, -bitmap.getHeight()/2);
		int height = bitmap.getHeight();
		int width = bitmap.getWidth();
		this.tapRadius = height >= width ? height : width; //make whole image clickable
		this.mapView = mapView;
		this.posts = new ArrayList<>();
		this.setSelected(false);
		this.tag = ""; //TODO: add tag to constructor
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