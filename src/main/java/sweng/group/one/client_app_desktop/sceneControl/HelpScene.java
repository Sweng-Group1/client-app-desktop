package sweng.group.one.client_app_desktop.sceneControl;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HelpScene extends JScrollPane {
    private List<BufferedImage> pdfImages;
    private ExecutorService executorService;
    private JPanel scrollView;

    public HelpScene(String filePath) {
        try {
            File pdfFile = new File(filePath);
            pdfImages = new ArrayList<>();
            executorService = Executors.newFixedThreadPool(1);

            SwingUtilities.invokeLater(() -> {
                scrollView = new JPanel();
                scrollView.setLayout(new BoxLayout(scrollView, BoxLayout.Y_AXIS));
                setViewportView(scrollView);
            });

            executorService.execute(() -> {
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
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addPageToScrollView(BufferedImage image) {
        ImagePanel imagePanel = new ImagePanel(image);
        scrollView.add(imagePanel);
        scrollView.revalidate();
        scrollView.repaint();
    }

    private class ImagePanel extends JPanel {
        private BufferedImage image;

        public ImagePanel(BufferedImage image) {
            this.image = image;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (pdfImages != null) {
                int scrollViewWidth = getParent().getParent().getWidth(); // Get the width of the scroll view
                int totalHeight = 0; // Initialize the total height of the content
                
                List<Image> imagesCopy = new ArrayList<>(pdfImages); // Create a copy of the list
                
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
}