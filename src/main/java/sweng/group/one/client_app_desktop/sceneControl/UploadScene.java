package sweng.group.one.client_app_desktop.sceneControl;

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
		previewPanel = new JPanel();
		
		describePostPanel = new JPanel();
		describePostTextField = new JTextField();
		
		tagPanel = new JPanel();
		
	}
}
