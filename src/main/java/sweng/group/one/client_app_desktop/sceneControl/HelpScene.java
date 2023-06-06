package sweng.group.one.client_app_desktop.sceneControl;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.xml.sax.SAXException;

import sweng.group.one.client_app_desktop.data.AuthenticationException;
import sweng.group.one.client_app_desktop.data.PostService;
import sweng.group.one.client_app_desktop.mapping.EventMarker;
import sweng.group.one.client_app_desktop.presentation.Presentation;
import sweng.group.one.client_app_desktop.sideBarUIElements.CustomScrollBarUI;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HelpScene extends JPanel implements LayoutManager, ComponentInterface {

	// Static Design Declarations
	private static final int GAP_WIDTH= 10;
	private static final int PRESENTATION_SCROLL_SPEED = 20;
	
	// PDF Pages as list of buffered images
    private static List<BufferedImage> pdfImages;
    // For multi-threading to improve PDF Loading performance
    private ExecutorService executorService;
    
	// GUI Components
    private JPanel helpMainPanel;
    private JScrollPane scrollPane;
    private JPanel scrollView;
	private JScrollBar scrollBar;


    public HelpScene(String filePath) {
        try {
            File pdfFile = new File(filePath);
            pdfImages = new ArrayList<>();

            scrollView = new JPanel();
            scrollView.setLayout(new BoxLayout(scrollView, BoxLayout.Y_AXIS));
        	scrollPane = new JScrollPane(scrollView);
        	
        	scrollPane.setOpaque(false);
    		scrollPane.setBackground(colorDark);
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
    		
    		this.setOpaque(false);
			
			// Call layoutContainer to perform the initial layout
            SwingUtilities.invokeLater(() -> {
                layoutContainer(HelpScene.this);
                validate();
            });
    		
            // Load PDF on new thread
            Thread loadPDFThread = new Thread() {
				@Override
				public void run() {
					try {
	                    PDDocument document = PDDocument.load(pdfFile);
	                    PDFRenderer pdfRenderer = new PDFRenderer(document);

	                    for (int pageIndex = 0; pageIndex < document.getNumberOfPages(); pageIndex++) {
	                        BufferedImage image = pdfRenderer.renderImageWithDPI(pageIndex, 300, ImageType.RGB);
	                        pdfImages.add(image);
	                        SwingUtilities.invokeLater(() -> addPageToScrollView(image));
	                    }

	                    document.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
				}
			};
			loadPDFThread.start();
        	
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void addPageToScrollView(BufferedImage image) {
        ImagePanel imagePanel = new ImagePanel(image);
        scrollView.add(imagePanel);
        scrollView.revalidate();
        scrollView.repaint();
    }

    class ImagePanel extends JPanel {
        private BufferedImage image;

        public ImagePanel(BufferedImage image) {
            this.image = image;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (pdfImages != null) {
                int scrollViewWidth = getParent().getParent().getParent().getWidth(); // Get the width of the main JPanel
                int totalHeight = 0; // Initialize the total height of the content
                
                List<Image> imagesCopy = new ArrayList<>(pdfImages); // Create a copy of the list to avoid repaint issues
                
                for (Image image : imagesCopy) {
                    int imageWidth = image.getWidth(null); // Get the original width of the image
                    int imageHeight = image.getHeight(null); // Get the original height of the image
                    
                    int scaledWidth = scrollViewWidth; // Set the scaled width to the width of the scroll view
                    int scaledHeight = (int) (((double) scrollViewWidth / imageWidth) * imageHeight); // Calculate the proportional scaled height
                    
                    totalHeight += scaledHeight; // Update the total height

                    // Draw the image with the scaled dimensions
                    g.drawImage(image, 0, totalHeight - scaledHeight, scaledWidth, scaledHeight, null);
                }
                
                setPreferredSize(new Dimension(scrollViewWidth, totalHeight)); // Set the preferred size of the scroll view
                revalidate(); // Revalidate the layout
            }
        }
    }
    
    @Override
	public void paint(Graphics g) {
		Graphics2D g2= (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(colorDark);
		g2.fillRoundRect(0,0,this.getWidth(),this.getHeight(), curvatureRadius, curvatureRadius);
		g2.dispose();
		super.paint(g);
	}
    
    @Override
    public void layoutContainer(Container parent) {
        int w = this.getWidth();
        int h = this.getHeight();

        int mainPanelWidth = w - GAP_WIDTH;
        int mainPanelHeight = h - (2 * GAP_WIDTH);

        scrollPane.setBounds(GAP_WIDTH, GAP_WIDTH, mainPanelWidth - scrollBar.getWidth(), mainPanelHeight);
        scrollBar.setBounds(mainPanelWidth - GAP_WIDTH/2 - 1, GAP_WIDTH, GAP_WIDTH, mainPanelHeight);
    }


	// Unused Layout Overrides
	@Override
	public void addLayoutComponent(String name, Component comp) {		
	}

	@Override
	public void removeLayoutComponent(Component comp) {		
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return null;
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return null;
	}
}