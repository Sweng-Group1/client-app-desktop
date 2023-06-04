package sweng.group.one.client_app_desktop.mapping;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

import org.mapsforge.core.graphics.Canvas;
import org.mapsforge.core.model.BoundingBox;
import org.mapsforge.core.model.LatLong;
import org.mapsforge.core.model.Point;
import org.mapsforge.core.util.MercatorProjection;
import org.mapsforge.map.awt.graphics.AwtBitmap;
import org.mapsforge.map.awt.view.MapView;
import org.mapsforge.map.layer.Layer;
/**
 * @author flt515
 *
 */
public class HeatMap extends Layer {
    private static final String CIRCLEPIC_PATH = "./assets/map/bolilla.png";
    private static final String GRADIENT_PATH = "./assets/map/colors.png";
    private static final int HEAT_RADIUS_METERS = 200;
    private MapView mapView;
    private BufferedImage image;
    private BufferedImage gradient;
    private ArrayList<EventMarker> markers;

    public HeatMap(ArrayList<EventMarker> markers, MapView mapView) {
    	super();
    	try {
    		image = ImageIO.read(new File(CIRCLEPIC_PATH));
    		gradient = ImageIO.read(new File(GRADIENT_PATH));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	this.setDisplayModel(mapView.getModel().displayModel);
    	this.mapView = mapView;
    	this.markers = markers;
    }
    
    @Override
    public synchronized void draw(BoundingBox boundingBox, byte zoomLevel, Canvas canvas, Point topLeftPoint) {
    	int width = canvas.getWidth();
    	int height = canvas.getHeight();
    	BufferedImage heatmap = new BufferedImage(width, height, 2); //type 2 is ARGB
        Graphics2D g = heatmap.createGraphics();
        
        //scale image relative to size of the map
        long mapSize = MercatorProjection.getMapSize(zoomLevel, this.displayModel.getTileSize());
        double radius = MercatorProjection.metersToPixels(HEAT_RADIUS_METERS, boundingBox.getCenterPoint().latitude, mapSize);
        radius = (int)radius*2 > 1 ? radius : 1; //ensure a size of atleast 1px
        Image scaledImage = this.image.getScaledInstance((int)radius*2, (int)radius*2, java.awt.Image.SCALE_SMOOTH);
        
        ArrayList<EventMarker> visableMarkers = new ArrayList<>();
        int maxNumPosts = 1;
        
        for(EventMarker m : markers) {
        	Point pxPos = mapView.getMapViewProjection().toPixels(m.getLatLong());
        	
        	boolean heatPointVisable = pxPos.x+radius > 0 || pxPos.x-radius < width || pxPos.y+radius > 0 || pxPos.y+radius < height;
        	
        	if (heatPointVisable) {
        		int numPosts = m.getNumPosts();
            	maxNumPosts = numPosts > maxNumPosts ? numPosts : maxNumPosts;
            	visableMarkers.add(m);
        	}
        	
        }
    	
    	for(EventMarker m: visableMarkers) {
    		LatLong point = m.getLatLong();
	        double pixelX = MercatorProjection.longitudeToPixelX(point.longitude, mapSize);
	        double pixelY = MercatorProjection.latitudeToPixelY(point.latitude, mapSize);
	        int left = (int) (pixelX - topLeftPoint.x - radius);
	        int top = (int) (pixelY - topLeftPoint.y - radius);
	        
	        //draw the shape on the map with opacity proportional to the number of posts
	        float alpha =  m.getNumPosts()/(float)(maxNumPosts*1.1);
	        
	        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha-(int)alpha)); //set alpha to floor
	        g.drawImage(scaledImage, left, top, null);
    	}
    	
    	//process colour to the heatmap
    	for (int x = 0; x < width; x++) {
    		for (int y = 0; y < height; y++) {
    			final int pixel = heatmap.getRGB(x, y);
    			if(pixel == 0) { //if the pixel is blank, move on
    				continue;
    			}
    			//new colour based on pixel alpha
    			final int gradColour = (int)((pixel >>> 24)/255f * (gradient.getHeight()-1)); 
    			final int newRGB = gradient.getRGB(1, gradColour) & 0x00ffffff; //mask to have no alpha content
    			heatmap.setRGB(x, y, newRGB | pixel); //give pixel the same alpha as before
    		}
    	}
    	canvas.drawBitmap(new AwtBitmap(heatmap), 0, 0, width, height, 0, 0, width, height);
    }
}
