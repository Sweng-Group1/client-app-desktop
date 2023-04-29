package sweng.group.one.client_app_desktop.sceneControl;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;

import javax.swing.JComponent;

import graphics.Rectangle;

/**
 * @author sophiemaw
 * Interface for every component, all scene components 
 * The colours in this aren't correct but this can be changed and then change all scene 
 * component colours. 
 * Similiar to gapWidth in upload scene, the curvature Radius needs to be uniform 
 * across all components but realistically be calculated from max frame height (h/30 perhaps)
 */
public interface ComponentInterface {
	public static final int curvatureRadius=30;
	
	public static final Color colorLight= new Color(66,91,136); //H218:51:53
	public static final Color colorLightHighlight= new Color(89,114,159);
	public static final Color colorLightShadow= new Color(43,78,113);
	
	public static final Color colorDark= new Color(46,71,118); //
	public static final Color colorDarkHighlight= new Color(66,91,136); //H214:S53:V48
	public static final Color colorDarkShadow= new Color(13,22,39); //H216:S66:V39
	
	public static final Color textColor= Color.white;
	public static final Color transparent= new Color(255,255,255,0);
	

	/**
	 * @param curvatureRadiusOfPanels
	 * @return font with curvatureRadius height
	 * This method is so text fits within headers of descriptionBox, tagsBox ect
	 * Although shouldn't need any input as cR is in this interface
	 */
	public default Font getFont(int curvatureRadiusOfPanels) {
		return  new Font("",Font.PLAIN,curvatureRadiusOfPanels);
	}
	public default Color getColorLightAdjustedOpacity(int opacity) {
		return new Color(78,106,143,opacity);
	}
	public default Color getColorDarkAdjustedOpacity(int opacity) {
		return new Color(46,71,117,opacity);
	}

	/**
	 * @param child                           current size of child that is to be resized and repositioned
	 * @param parent						  component that child is to be set in
	 * @param midPoint	  midpoint of child within parent coordinate system
	 * @return								  Bounds of child within parent
	 * Notes: this method will return a maximum size that can be added to the child 
	 * 		  so that is retains it's aspect ratio while keeping the midpoint in it's centre 
	 * 		  and not going out of the bounds of the parent. Returns point within rect is it's
	 * 		  new top left coordinate within parent.
	 * 
	 * Note for Fraser: this isn't working completely, i think the maths is off
	 */
	public default java.awt.Rectangle getMaxBoundsForObjectSizetWithinContainer(Dimension child, JComponent parent,Point midPoint) {
		Dimension maxSize= new Dimension(0,0); 
		/*
		 * Find maximum size of child within parent keeping midPoint
		 */
		if(parent.getWidth()/2>midPoint.getX()) {
			/*
			 *  find max image width: width/2 = 0 to pixelPos.getX()
			 *  
			 */
			maxSize.setSize(2*(midPoint.getX()),maxSize.getHeight());
		}else {
			/*
			 *  min image width/2 in other direction: pixelPos.getX() to slide.width()
			 */
			maxSize.setSize(2*(parent.getWidth()-midPoint.getX()),maxSize.getHeight());
		}
		if(parent.getHeight()/2>midPoint.getY()) {
			/*
			 *  min image height/2: 0 to pixelPos.getY()
			 */
			maxSize.setSize(maxSize.getWidth(),2*(midPoint.getY()));
		}else {
			/*
			 *  min image height/2: pixelPos.getY() to slide.width()
			 */
			maxSize.setSize(maxSize.getWidth(),2*(parent.getHeight()-midPoint.getY()));
		}
		/*
		 *  Find actual image size by using image's aspect ratio with the max image size
		 *  Using SLIDE code, might be worth creating an interface for this maths as its used a lot
		 */
	
		
		float childAspectRatio = (float)child.width/child.height;
		float maxSizeAspectRatio = (float)maxSize.width/maxSize.height;
		
		Dimension layoutSize = null;
		if(childAspectRatio >= maxSizeAspectRatio) {
			layoutSize = new Dimension(maxSize.width, (int)(maxSize.width/childAspectRatio));
		}
		else {
			layoutSize = new Dimension((int) (maxSize.height* childAspectRatio), maxSize.height);
		}
		/*
		 *  New point to make mouse click centre of image 
		 */
		Point newPoint= new Point(midPoint.x-layoutSize.width/2,midPoint.y-layoutSize.height/2);
		java.awt.Rectangle rect= new java.awt.Rectangle(newPoint.x,newPoint.y,layoutSize.width,layoutSize.height);
		return rect;
	}
}
