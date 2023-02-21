package sweng.group.one.client_app_desktop.sceneControl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;
import java.util.prefs.Preferences;

import javax.imageio.ImageIO;

import org.mapsforge.core.graphics.Bitmap;
import org.mapsforge.core.graphics.GraphicFactory;
import org.mapsforge.core.model.BoundingBox;
import org.mapsforge.core.model.LatLong;
import org.mapsforge.core.model.MapPosition;
import org.mapsforge.core.model.Point;
import org.mapsforge.core.util.LatLongUtils;
import org.mapsforge.map.awt.graphics.AwtBitmap;
import org.mapsforge.map.awt.graphics.AwtGraphicFactory;
import org.mapsforge.map.awt.util.AwtUtil;
import org.mapsforge.map.awt.util.JavaPreferences;
import org.mapsforge.map.awt.view.MapView;
import org.mapsforge.map.datastore.MultiMapDataStore;
import org.mapsforge.map.layer.Layers;
import org.mapsforge.map.layer.cache.TileCache;
import org.mapsforge.map.layer.renderer.TileRendererLayer;
import org.mapsforge.map.model.Model;
import org.mapsforge.map.model.common.PreferencesFacade;
import org.mapsforge.map.reader.MapFile;
import org.mapsforge.map.rendertheme.InternalRenderTheme;

import sweng.group.one.client_app_desktop.mapping.EventMarker;
import sweng.group.one.client_app_desktop.mapping.HeatMap;

@SuppressWarnings("serial")
public class MapScene extends MapView{
	
	private static final GraphicFactory GRAPHIC_FACTORY = AwtGraphicFactory.INSTANCE;
	private static final String MARKER_IMAGE_PATH = "./assets/map/marker.png";
	private final PreferencesFacade preferencesFacade;
	private HeatMap heatmap;
	private ArrayList<EventMarker> markers;
	private BoundingBox boundingBox;
	
	public MapScene() {
		super();
		this.getMapScaleBar().setVisible(true);
		preferencesFacade = new JavaPreferences(Preferences.userNodeForPackage(MapScene.class));
		markers = new ArrayList<>();
		heatmap = new HeatMap(markers, this);
	}
	
	public void loadMapFile(File mapFile) {
		Layers layers = this.getLayerManager().getLayers();
		layers.clear();
		int tileSize = 256;
		this.getModel().displayModel.setFixedTileSize(tileSize);
		
		TileCache tileCache = AwtUtil.createTileCache(
				tileSize, 
				this.getModel().frameBufferModel.getOverdrawFactor(), 
				1024, 
				new File(System.getProperty("java.io.tmpdir") + "/WhatsOn/map", UUID.randomUUID().toString()));
		
		MultiMapDataStore mapDataStore = new MultiMapDataStore(MultiMapDataStore.DataPolicy.RETURN_ALL);
		mapDataStore.addMapDataStore(new MapFile(mapFile), false, false);
		
		TileRendererLayer tileRendererLayer = new TileRendererLayer(
				tileCache, 
				mapDataStore, 
				this.getModel().mapViewPosition, 
				false, 
				true, 
				false, 
				GRAPHIC_FACTORY, 
				null) {
			//TODO: Remove this
				@Override //print co-ords when map is clicked
	            public boolean onTap(LatLong tapLatLong, Point layerXY, Point tapXY) {
	                addEventMarker(tapLatLong);
	                return true;
	            }
	        };
	        
		tileRendererLayer.setXmlRenderTheme(InternalRenderTheme.DEFAULT);
		layers.add(tileRendererLayer);
		layers.add(heatmap);
		
		boundingBox =  mapDataStore.boundingBox();
		System.out.println(boundingBox.maxLatitude + " " + boundingBox.maxLongitude);
		
		//zoom into the centre of the map
		Model model = this.getModel();
		model.init(preferencesFacade);
		byte zoomLevel = (byte) (LatLongUtils.zoomForBounds(model.mapViewDimension.getDimension(), boundingBox, model.displayModel.getTileSize()) + 1);
		this.setZoomLevelMin(zoomLevel);
        model.mapViewPosition.setMapPosition(new MapPosition(boundingBox.getCenterPoint(), zoomLevel));
        model.mapViewPosition.setMapLimit(boundingBox);
	}
	
	public void addEventMarker(LatLong position) {
		BufferedImage poiImage = null;
		try {
			poiImage = ImageIO.read(new File(MARKER_IMAGE_PATH));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	 	Bitmap markerImage = new AwtBitmap(poiImage);
		Layers layers = getLayerManager().getLayers();
		EventMarker marker = new EventMarker(this, position, markerImage);
		layers.add(marker);
		markers.add(marker);
		this.getLayerManager().redrawLayers();
	}
	
	public void addEventMarker(EventMarker marker) {
		Layers layers = getLayerManager().getLayers();
		layers.add(marker);
		markers.add(marker);
		this.getLayerManager().redrawLayers();
	}
	
	public void selectMarker(EventMarker selected) {
		for (EventMarker m : markers) {
			m.setSelected(m == selected);
		}
	}
	
	public ArrayList<EventMarker> getMarkers() {
		return markers;
	}
}
