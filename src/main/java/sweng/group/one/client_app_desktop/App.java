package sweng.group.one.client_app_desktop;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import sweng.group.one.client_app_desktop.sceneControl.UploadScene;


/**
 * Hello world!
 *
 */
public class App 
{
	private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private JFrame frame;
	private JPanel background;
	private UploadScene upload;
	double ratioGapWidth;
	public App() throws IOException {
		setUpBackground();
		addUploadScene();
	}
	private void setUpBackground() {
		frame = new JFrame();
		frame.setSize(screenSize);
		frame.setVisible(true);
		frame.setLayout(null);
		background= new JPanel();
		frame.add(background);
		background.setSize(screenSize);
		background.setLocation(0, 0);
		background.setBackground(Color.black);
		background.setLayout(null);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.addComponentListener(new ComponentListener() {

			@Override
			public void componentResized(ComponentEvent e) {
				background.setSize(frame.getSize());
				upload.setBounds((int)(frame.getWidth()*ratioGapWidth),(int)(frame.getHeight()*ratioGapWidth),
						(int)(frame.getWidth()*(1-(2*ratioGapWidth))),(int)(frame.getHeight()*(1-(2*ratioGapWidth))));
			}

			@Override
			public void componentMoved(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void componentShown(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void componentHidden(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
	private void addUploadScene() throws IOException {
		Color colorLight= new Color(78,106,143);
		Color colorDark= new Color(46,71,117);
		upload = new UploadScene();
		upload.setVisible(false);
		background.add(upload);
	
		ratioGapWidth= 0.1;

		upload.setBounds((int)(screenSize.width*ratioGapWidth),(int)(screenSize.height*ratioGapWidth),
				(int)(screenSize.width*(1-(2*ratioGapWidth))),(int)(screenSize.height*(1-(2*ratioGapWidth))));
		upload.validate();
		upload.setVisible(true);
		upload.getConfirmButton().addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				upload.setVisible(false);
				System.out.println("Tags: ");
				System.out.println(upload.getTags());
				System.out.println("Description: ");
				System.out.println(upload.getDescription());
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
	
	
    public static void main( String[] args ) throws IOException
    {
    	App app= new App();
    		
    }
}
