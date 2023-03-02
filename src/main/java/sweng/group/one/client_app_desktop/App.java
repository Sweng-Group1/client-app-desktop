package sweng.group.one.client_app_desktop;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import sweng.group.one.client_app_desktop.sceneControl.OptionsScene;

/**
 * Hello world!
 *
 */
public class App extends JFrame
{
    private static final long serialVersionUID = 1L;

	public App()
    {
    	//setLayout(new BorderLayout());
		setSize(1280, 720);
		
    	OptionsScene optionsScene = new OptionsScene() {
    		@Override
    		public void accountButtonCreate () {
    			accountButton.addActionListener(new ActionListener() {
    				public void actionPerformed(ActionEvent e) {    		
        				
        			}
    			});
    		}
    		@Override
    		public void addPostButtonCreate() {
    			addPostButton.addActionListener(new ActionListener() {
    				public void actionPerformed(ActionEvent e) {    		
        				
        			}
    			});
    		}
    		@Override
    		public void helpButtonCreate() {
    			helpButton.addActionListener(new ActionListener() {
    				public void actionPerformed(ActionEvent e) {    		
        				
        			}
    			});
    		}    	
    		@Override
    		public void closeButton() {
    			helpButton.addActionListener(new ActionListener() {
    				public void actionPerformed(ActionEvent e) {    		
        				
        			}
    			});
    		}
    	};    		
    	add(optionsScene, BorderLayout.LINE_END);
    }
	
	public static void main(String[] args) {
		JFrame frame = new App();
		frame.setVisible(true);
	}
	
}
