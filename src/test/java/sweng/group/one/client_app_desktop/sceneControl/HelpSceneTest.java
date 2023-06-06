package sweng.group.one.client_app_desktop.sceneControl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.swing.edt.GuiActionRunner.execute;

import java.awt.Component;
import java.awt.image.BufferedImage;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JPanelFixture;
import org.assertj.swing.fixture.JScrollBarFixture;
import org.assertj.swing.fixture.JScrollPaneFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sweng.group.one.client_app_desktop.sceneControl.HelpScene.ImagePanel;

public class HelpSceneTest {

    private FrameFixture window;
    private HelpScene helpScene;
    private JPanelFixture helpSceneFixture;
    private JScrollPaneFixture scrollPaneFixture;

    @Before
    public void setUp() {
        // Create a new JFrame instance
        JFrame frame = GuiActionRunner.execute(() -> new JFrame());
        
        // Set up the test frame size and visibility
        frame.setSize(800, 400);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the FrameFixture using the JFrame instance
        window = new FrameFixture(frame);
    }

    @After
    public void tearDown() {
        window.cleanUp();
    }

    @Test
    public void testHelpSceneCreation() {
        // Make a new helpScene test and make sure it exists.
        helpScene = execute(() -> new HelpScene("./assets/User_Manual.pdf"));
        assertThat(helpScene)
                .isNotNull();

        // Make test fixtures for the helpScene Panel and Scrollpane
        helpSceneFixture = new JPanelFixture(window.robot(), helpScene);
        scrollPaneFixture = helpSceneFixture.scrollPane();

        // Check the Custom Scrollbar is visible
        JScrollBarFixture verticalScrollBar = scrollPaneFixture.verticalScrollBar();
        verticalScrollBar.requireVisible();
        
        // Check the scrollPane's own scrollbars are not visible
        JScrollPane scrollPane = scrollPaneFixture.target();
        assertThat(scrollPane.getVerticalScrollBarPolicy())
                .isEqualTo(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        assertThat(scrollPane.getHorizontalScrollBarPolicy())
                .isEqualTo(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);


        JPanel scrollView = scrollPaneFixture.target(JPanel.class);
        assertThat(scrollView).isInstanceOf(JPanel.class);
        scrollView.requireLayout(new BoxLayout(scrollView, BoxLayout.Y_AXIS));

        JScrollBar scrollBar = verticalScrollBar.target();
        assertThat(scrollBar).isNotNull();

        JPanelFixture scrollViewFixture = new JPanelFixture(window.robot(), scrollView);
        scrollViewFixture.requireEmpty();
    }
    
    



    @Test
    public void testAddPageToScrollView() {
        // Create a new HelpScene and get the scroll view
        helpScene = GuiActionRunner.execute(() -> new HelpScene("path/to/file.pdf"));
        JPanelFixture scrollView = new JScrollPaneFixture(window.robot(), helpScene.getComponent(0)).panel();

        // Add a page to the scroll view
        BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        GuiActionRunner.execute(() -> helpScene.addPageToScrollView(image));

        // Assert the scroll view contains the added page
        scrollView.requireComponentCount(1);
        JPanelFixture imagePanel = scrollView.panel(0);
        imagePanel.requireInstanceOf(ImagePanel.class);
        assertThat(imagePanel.target().getImage()).isEqualTo(image);
    }
}
