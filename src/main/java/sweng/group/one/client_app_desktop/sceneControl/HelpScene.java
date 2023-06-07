package sweng.group.one.client_app_desktop.sceneControl;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

import sweng.group.one.client_app_desktop.uiElements.CustomScrollBarUI;
import sweng.group.one.client_app_desktop.uiElements.UIConstants;
import sweng.group.one.client_app_desktop.uiElements.YUSUColours;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * JPanel for displaying a help scene that contains a PDF document.
 * The PDF document is loaded and rendered as images, which are displayed in a scrollable view.
 * The layout is automatically adjusted to fit the size of the scroll view.
 * 
 * @author Srikanth Jakka
 * @since 04/06/2023
 * @version 0.5
 */
public class HelpScene extends JPanel {

	// -------------------------------------------------------------- //
	// --------------------- Initialisations ------------------------ //
	// -------------------------------------------------------------- //
    
	// GUI Components
    private JScrollPane scrollPane;
    private JPanel scrollView;
	private JScrollBar scrollBar;
	
	// ----------- CONSTANTS -------------
	private static final long serialVersionUID = 1L;
	// Static Design Declarations
	private static final int GAP_WIDTH= 10;
	private static final int PRESENTATION_SCROLL_SPEED = 20;


	// -------------------------------------------------------------- //
	// ----------------------- CONSTRUCTOR -------------------------- //
	// -------------------------------------------------------------- //
	/**
     * Constructs a HelpScene with the specified PDF file.
     *
     * @param filePath the path to the PDF file
     */
    public HelpScene(String filePath) {
        try {
        	// Setup GUI Design
        	setUpGUIElements();
    		
        	// Load the PDF from the filepath
    		loadPDF(filePath);
			
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // -------------------------------------------------------------- //
 	// -------------------------- LAYOUT ---------------------------- //
 	// -------------------------------------------------------------- //
 	
    /**
     * Creates a scrollPane with viewport scrollView, and custom scrollbar
     * design as per the unified app UI spec.
     *
     */
 	private void setUpGUIElements() {
 		
 		// -------------- OBJECTS AND APPEARANCE ------------------
 		this.setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
        this.setBorder(new EmptyBorder(GAP_WIDTH, GAP_WIDTH, GAP_WIDTH, GAP_WIDTH));
        
		// Init scrollPane with scrollView JPanel viewport.
        scrollView = new JPanel();
		// Use BoxLayout with Y_AXIS orientation
        scrollView.setLayout(new BoxLayout(scrollView, BoxLayout.Y_AXIS));
    	scrollPane = new JScrollPane(scrollView);
    	
    	// UI Settings
    	scrollPane.setOpaque(false);
		scrollPane.setBackground(YUSUColours.DARK);
		scrollPane.setBorder(null);
    	
		// Custom Scrollbar setup
		scrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollBar = scrollPane.getVerticalScrollBar();
		scrollBar.setUI(new CustomScrollBarUI());
		scrollBar.setOpaque(false);
		
		// Default mouse scroll speed is too slow, set to a better value:
		scrollPane.getVerticalScrollBar().setUnitIncrement(PRESENTATION_SCROLL_SPEED);
		
		// Add scrollbar and scrollPane to JPanel
		add(scrollPane);
		add(scrollBar);
		
		// Make the main panel transparent
		this.setOpaque(false);
 	}
 	
    
    /**
     * Loads the PDF file using apache PDF Renderer.
     *
     * @param filePath the path to the PDF file
     */
    private void loadPDF(String filePath) {
    	// Sets up a File object for the pdf location
        File pdfFile = new File(filePath);
        
        // Load PDF on new thread
        Thread loadPDFThread = new Thread() {
			@Override
			public void run() {
				try {
					// Make the Apache PDF objects to handle the PDF File
                    PDDocument document = PDDocument.load(pdfFile);
                    PDFRenderer pdfRenderer = new PDFRenderer(document);

                    // For each page, render the page as an image at 300 dpi and add to scrollView
                    for (int pageIndex = 0; pageIndex < document.getNumberOfPages(); pageIndex++) {
                        BufferedImage image = pdfRenderer.renderImageWithDPI(pageIndex, 300, ImageType.ARGB);
                        SwingUtilities.invokeLater(() -> addPageToScrollView(image));
                    }
                    
                    // Close document
                    document.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
			}
		};
		loadPDFThread.start();
    }
    
    /**
     * Adds an image of a page to the scroll view.
     *
     * @param image the image to add
     */
    private void addPageToScrollView(BufferedImage image) {
        ImagePanel imagePanel = new ImagePanel(image);
        scrollView.add(imagePanel);
        scrollView.revalidate();
        scrollView.repaint();
    }

    /**
     * ImagePanel class to hold BufferedImages in JPanel for correct 
     * scaling of design.
     *
     */
    private class ImagePanel extends JPanel {
		private static final long serialVersionUID = 1L;
		private BufferedImage image;

        public ImagePanel(BufferedImage image) {
            this.image = image;
        }

        // Custom paint function to scale the images of the PDF to size.
        @Override
        protected void paintComponent(Graphics g) {
    		super.paintComponent(g);
    		// Get the width of the main JPanel
            int scrollViewWidth = getParent().getParent().getParent().getWidth(); 
            
            int imageWidth = image.getWidth(null); // Get the original width of the image
            int imageHeight = image.getHeight(null); // Get the original height of the image
            
        	// Set the scaled width to the width of the scroll view
            int scaledWidth = scrollViewWidth; 
        	// Calculate the proportional scaled height		
            int scaledHeight = (int) (((double) scrollViewWidth / imageWidth) * imageHeight); 
            
            // Draw the image with the scaled dimensions
            g.drawImage(image, 0, 0, scaledWidth, scaledHeight, null);
            
            // Set the preferred size of the scroll view
            setPreferredSize(new Dimension(scrollViewWidth, scaledHeight)); 
            revalidate(); // Revalidate the layout
        }
    }
    
    // Custom UI design to display in a coloured roundrect
    @Override
	public void paint(Graphics g) {
		Graphics2D g2= (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(YUSUColours.DARK);
		g2.fillRoundRect(0,0,this.getWidth(),this.getHeight(), UIConstants.CURVE_RADIUS, UIConstants.CURVE_RADIUS);
		g2.dispose();
		super.paint(g);
	}
    
}