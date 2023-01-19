package sweng.group.one.client_app_desktop.sceneControl;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class UploadScene extends JPanel {
	private JPanel uploadMediaPanel;
	private JPanel previewPanel;
	
	private JPanel describePostPanel;
	private JTextField describePostTextField;
	
	private JPanel tagPanel;
	private JTextField tagTextField;
	
	public UploadScene() {
		super();
		uploadMediaPanel = new JPanel();
		this.add(uploadMediaPanel, BorderLayout.WEST);
		
		previewPanel = new JPanel();
		this.add(previewPanel);
		
		describePostPanel = new JPanel();
		describePostTextField = new JTextField();
		describePostPanel.add(describePostTextField);
		this.add(describePostPanel, BorderLayout.EAST);
		
		tagPanel = new JPanel();
		tagTextField = new JTextField();
		tagPanel.add(tagTextField);
		this.add(tagPanel, BorderLayout.EAST);
	}
}
