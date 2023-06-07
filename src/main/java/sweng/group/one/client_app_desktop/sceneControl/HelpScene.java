package sweng.group.one.client_app_desktop.sceneControl;

import org.icepdf.ri.common.ComponentKeyBinding;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;

import sweng.group.one.client_app_desktop.uiElements.UIConstants;
import sweng.group.one.client_app_desktop.uiElements.YUSUColours;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * JPanel for displaying a help scene that contains a PDF document.
 * The PDF document is loaded and rendered with the ICEPDF viewer (with markup features)!
 * 
 * @author Srikanth Jakka
 * @since 04/06/2023
 * @version 1.1
 */
public class HelpScene extends JPanel{

	// -------------------------------------------------------------- //
	// --------------------- Initialisations ------------------------ //
	// -------------------------------------------------------------- //
    
	// ICEPDF GUI Components
	private SwingController controller;
	private SwingViewBuilder factory;
	private JPanel viewerComponentPanel;
	
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
     * Sets basic properties of this JPanel.
     *
     */
 	private void setUpGUIElements() {
 		
 		// -------------- OBJECTS AND APPEARANCE ------------------
 		// Set Layout and border.
		this.setLayout(new BorderLayout());
        this.setBorder(new EmptyBorder(GAP_WIDTH, GAP_WIDTH, GAP_WIDTH, GAP_WIDTH));
		
		// Make the main panel transparent
		this.setOpaque(false);
 	}
 	
    
    /**
     * Creates the ICEPDF component and adds to this JPanel, and loads the PDF file.
     *
     * @param filePath the path to the PDF file
     */
    private void loadPDF(String filePath) {
    	// From https://github.com/pcorless/icepdf
    	// Build a controller
    	controller = new SwingController();

    	// Build a SwingViewFactory configured with the controller
    	factory = new SwingViewBuilder(controller);
    	
    	// Use the factory to build a JPanel that is pre-configured
    	//with a complete, active Viewer UI.
    	viewerComponentPanel = factory.buildViewerPanel();

    	// add copy keyboard command
    	ComponentKeyBinding.install(controller, viewerComponentPanel);

    	// add interactive mouse link annotation support via callback
    	controller.getDocumentViewController().setAnnotationCallback(
    	     new org.icepdf.ri.common.MyAnnotationCallback(
    	            controller.getDocumentViewController()));
    	
    	// Set the size of the viewer correctly
    	viewerComponentPanel.setBounds(getVisibleRect());

    	// Add the icepdf panel to the JPanel
    	add(viewerComponentPanel, BorderLayout.CENTER);

    	// Open the PDF document to view
    	controller.openDocument(filePath);

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

	// -------------------------------------------------------------- //
	// ------------------------- TESTING ---------------------------- //
	// -------------------------------------------------------------- //
    // These getters are required to test proper functionailty of the 
    // ICEPDF loader. (package-private classes only)
    JPanel getViewerComponentPanel() {
		// TODO Auto-generated method stub
		return viewerComponentPanel;
	}

    SwingController getController() {
		// TODO Auto-generated method stub
		return controller;
	}
    
}