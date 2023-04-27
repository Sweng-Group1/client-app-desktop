package sweng.group.one.client_app_desktop.uiElements;

import java.awt.Color;
import java.awt.Font;

public interface ComponentInterface {
	public static final int curvatureRadius=30;
	public static final Color colorLight= new Color(78,106,143);
	
	public static final Color colorDark= new Color(46,71,117);

	public static final Color textColor= Color.white;
	public static final Color transparent= new Color(255,255,255,0);
	//public static final Font textFont= new Font("",Font.PLAIN,curvatureRadius);
	public default Font getFont(int curvatureRadiusOfPanels) {
		return  new Font("",Font.PLAIN,curvatureRadiusOfPanels);
	}
	public default Color getColorLightAdjustedOpacity(int opacity) {
		return new Color(78,106,143,opacity);
	}
	public default Color getColorDarkAdjustedOpacity(int opacity) {
		return new Color(46,71,117,opacity);
	}
}
