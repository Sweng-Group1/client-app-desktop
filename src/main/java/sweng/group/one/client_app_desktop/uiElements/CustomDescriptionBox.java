package sweng.group.one.client_app_desktop.uiElements;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTextArea;
import javax.swing.JTextField;

import sweng.group.one.client_app_desktop.sceneControl.ComponentInterface;

/**
 * @author sophiemaw
 * Panel that contains a textField for user to write a description that is added to the
 * presentation
 *
 */
public class CustomDescriptionBox extends UploadSceneComponent implements ComponentInterface{
	JTextArea descriptionTextField;
	String placeHolder;
	String description;
	
	public CustomDescriptionBox() {
		initialise();
	}
	private void initialise() {
		descriptionTextField= new JTextArea();
		this.setLayout(null);
		this.main= colorLight;
		this.secondary= colorDark;
		this.add(descriptionTextField);
		
		descriptionTextField.setBackground(transparent);
		descriptionTextField.setForeground(Color.white);
		descriptionTextField.setCaretColor(Color.white);
		descriptionTextField.setBorder(null);
		descriptionTextField.setOpaque(false);
		placeHolder= "Add a description...";
		descriptionTextField.setText(placeHolder);
		
		descriptionTextField.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				System.out.println(descriptionTextField.getText());
				if(descriptionTextField.getText().equalsIgnoreCase(placeHolder)) {
					descriptionTextField.removeAll();
					descriptionTextField.setText("");
				}
				
			}

			@Override
			public void focusLost(FocusEvent e) {
				if(descriptionTextField.getText()=="") {
					descriptionTextField.setText(placeHolder);
				}
				
			}
			
		});
		
		descriptionTextField.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				if(e.getKeyChar()==KeyEvent.VK_ENTER) {
					if(descriptionTextField.getText()!=placeHolder) {
						 description=descriptionTextField.getText();				
						descriptionTextField.setText(description);
					}
				}
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		
	}
	public void setMarginBounds(int r,int t, int l, int b) {
		super.setMarginBounds(r, t, l, b);
		descriptionTextField.setBounds(r+curvatureRadius/2,t+curvatureRadius/4,this.getWidth()-(curvatureRadius)-l-r,this.getHeight()-t-b-(curvatureRadius/2));
		descriptionTextField.setLineWrap(true);
	}
	public String getDescription() {
		if(descriptionTextField.getText().equalsIgnoreCase(description)) {
			return description;
		}else {
			if(descriptionTextField.getText().equalsIgnoreCase(placeHolder)) {
				return "";
			}else {
				return descriptionTextField.getText();
			}
		}
	}
}
