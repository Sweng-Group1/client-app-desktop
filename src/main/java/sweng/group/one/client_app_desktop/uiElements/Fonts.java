package sweng.group.one.client_app_desktop.uiElements;


import java.awt.Color;
import java.awt.Font;

public interface Fonts {
	
	public static Font bold(int size) {
		return new Font("BOLD",Font.BOLD,size);
	}
	public static Font italic(int size) {
		return new Font("ITALIC",Font.ITALIC,size);
	}
	public static Font tabFont(int size) {
		return new Font("TAB",Font.PLAIN,size);
	}
}