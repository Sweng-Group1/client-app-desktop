package sweng.group.one.client_app_desktop.media;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JPanel;

import sweng.group.one.client_app_desktop.presentation.Slide;

public class ImageViewer extends MediaElement {

	public ImageViewer(Point pos, int pointWidth, int pointHeight, float duration, Slide slide, URL fileURL) {
		super(pos, pointWidth, pointHeight, duration, slide, fileURL);
		loadFile();
		component = new JPanel() {
			private static final long serialVersionUID = 1L;
		
			 // Draws the whole of the loaded image at the specified dimensions
			@Override
			public void paint(Graphics g) {
				super.paint(g);
				int w = this.getWidth();
				int h = this.getHeight();
				int x = (int)((float)pos.x / width * w);
				int y = (int)((float)pos.y / height * h);
				g.drawImage(image, x, y, w+x, h+y, null);
			}
		};
	}

	private BufferedImage image; 

	// Loads the image stored at the local path to a BufferedImage type
	@Override
	protected void loadFile() {
		String imageLocalPath = getLocalPath();
		try {
			image = ImageIO.read(new File(imageLocalPath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public JComponent getComponent() {
		return component;
	}
}
