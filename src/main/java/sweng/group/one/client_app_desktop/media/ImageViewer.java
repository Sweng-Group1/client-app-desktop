package sweng.group.one.client_app_desktop.media;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JPanel;

import sweng.group.one.client_app_desktop.presentation.Slide;

public class ImageViewer extends MediaElement {
	private BufferedImage image; 
	private int rotation;
	private float delay;

	/**
	 * Constructs an ImageViewer with the specified parameters.
	 *
	 * @param pos         The position of the ImageViewer.
	 * @param pointWidth  The width of the ImageViewer.
	 * @param pointHeight The height of the ImageViewer.
	 * @param duration    The duration of the ImageViewer, including delay.
	 * @param rot         The rotation angle of the ImageViewer.
	 * @param delay       The delay before the ImageViewer starts.
	 * @param slide       The Slide object associated with the ImageViewer.
	 * @param fileURL     The URL of the image file to load.
	 */
	public ImageViewer(Point pos, int pointWidth, int pointHeight, float duration, int rot, float delay, Slide slide, URL fileURL) {
		super(pos, pointWidth, pointHeight, duration+delay, slide, fileURL);
		this.rotation = rot;
		this.delay = delay;
		loadFile();
		component = new JPanel() {
			private static final long serialVersionUID = 1L;
		
			 // Draws the whole of the loaded image at the specified dimensions
			@Override
			public void paint(Graphics g) {
				super.paint(g);
				Graphics2D g2d = (Graphics2D)g;
		
				int w = this.getWidth();
				int h = this.getHeight();
				int x = (int)((float)pos.x / slide.getPointWidth() * w);
				int y = (int)((float)pos.y / slide.getPointHeight() * h);
				double radians = Math.toRadians(rotation);
			    double centerX = x + w / 2.0;
			    double centerY = y + h / 2.0;

			    // Translate to the center of the image and rotate around it
			    g2d.rotate(radians, centerX, centerY);

			    g2d.drawImage(image, x, y, w, h, null);  // Draw the rotated image

			    g2d.dispose();  // Dispose the Graphics2D object
			}
		};
	}

	

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
	
	//account for delay
	@Override
	protected void displayElement(boolean displaying) {
		super.displayElement(displaying);
		if (delay > 0 && displaying){
			component.setVisible(false);
			displayTimer.schedule( 
			        new TimerTask() {
			            @Override
			            public void run() {
			            	component.setVisible(true);
			            }
			        }, 
			        (long) (delay*1000));
		}
	}
	
	public JComponent getComponent() {
		return component;
	}
}