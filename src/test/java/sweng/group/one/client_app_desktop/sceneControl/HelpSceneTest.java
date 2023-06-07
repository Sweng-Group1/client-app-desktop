package sweng.group.one.client_app_desktop.sceneControl;

import org.icepdf.ri.common.SwingController;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;
import java.lang.ModuleLayer.Controller;

import static org.junit.Assert.*;

public class HelpSceneTest {

    private HelpScene helpScene;

    @Before
    public void setUp() {
        // Create an instance of HelpScene with a sample PDF file.
    	// In this case, it is the User Manual it is written to load
    	// in this project.
        String filePath = "./assets/User_Manual.pdf";
        helpScene = new HelpScene(filePath);

        // Set up the parent container for testing
        JFrame frame = new JFrame();
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());
        frame.add(helpScene);
        frame.setVisible(true);
    }

    @Test
    public void testHelpSceneCreation() {
        // Verify that the HelpScene is not null
        assertNotNull(helpScene);
    }

    @Test
    public void testHelpSceneLayout() {
        // Verify that the HelpScene uses BorderLayout
        assertTrue(helpScene.getLayout() instanceof BorderLayout);
    }

    @Test
    public void testHelpSceneBackground() {
        // Verify that the HelpScene is transparent
        assertFalse(helpScene.isOpaque());
    }

    @Test
    public void testPDFLoading() {
        // Verify that the PDF document is loaded successfully
        assertNotNull(helpScene.getViewerComponentPanel());
        SwingController controller = helpScene.getController();
        assertNotNull(controller.getDocument());
    }
    
    @Test
    public void testPDFZooming() {
        SwingController controller = helpScene.getController();

        // Set the initial zoom level
        float initialZoom = controller.getDocumentViewController().getZoom();

        // Zoom in
        controller.getDocumentViewController().setZoomIn();
        float zoomIn = controller.getDocumentViewController().getZoom();

        // Zoom out
        controller.getDocumentViewController().setZoomOut();
        float zoomOut = controller.getDocumentViewController().getZoom();
        
        // Verify zoom levels
        assertEquals(initialZoom, zoomOut, 0.001f); // Ensure zooming out restores the initial zoom level
        assertEquals(initialZoom + 0.2f, zoomIn, 0.001f); // Ensure zooming in increases the zoom level by 0.2
    }
    
}
