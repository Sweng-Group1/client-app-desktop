package sweng.group.one.client_app_desktop.media;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
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
				// TODO Auto-generated method stub
				g.drawImage(image, 0, 0, (int)g.getClipBounds().getWidth(), (int)(g.getClipBounds().getHeight()), null);
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
		// If the image does not fit in the slide at the desired position, resize the image to fit
		if (width > slide.getPointWidth()-pos.x)  {
			width = slide.getPointWidth()-pos.x;
		}
		if (height > slide.getPointHeight()-pos.y) {
			height = slide.getPointHeight()-pos.y;
		}
	}
}
