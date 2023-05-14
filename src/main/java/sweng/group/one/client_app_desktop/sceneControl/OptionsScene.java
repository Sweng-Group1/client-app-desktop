package sweng.group.one.client_app_desktop.sceneControl;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import sweng.group.one.client_app_desktop.optionsSceneUIElements.CircularButton;
import javax.swing.JButton;
import javax.swing.JPanel;



	public class OptionsScene extends JPanel implements ComponentInterface{
		
		private Boolean isOpen;
		private CircularButton accountButton, addPostButton, helpButton, closeButton,optionsButton;
		JPanel optionsButtonPanel,multipleOptionsPanel;

		
		public OptionsScene() throws IOException { 
			super();
			this.setLayout(null);
			this.setOpaque(false);
			
			setupMultipleOptionsPanel();
			setupOptionsButton();
			createMultipleOptionsButtons();
			//setupGUI();
			addListeners();
			
			
		}
		private void createMultipleOptionsButtons() throws IOException {
			multipleOptionsPanel.setLayout(null);
			
			accountButton= new CircularButton();
			accountButton.setImageIcon(ImageIO.read(new File("./assers/user.png")));
			accountButton.setMainBackgroundColor(Color.white,new Color(239,238,238),new Color(203,203,203));
			
			addPostButton= new CircularButton();
			addPostButton.setImageIcon(ImageIO.read(new File("./assers/plus.png")));
			addPostButton.setMainBackgroundColor(Color.white,new Color(239,238,238),new Color(203,203,203));
			
			helpButton= new CircularButton();
			helpButton.setImageIcon(ImageIO.read(new File("./assers/question.png")));
			helpButton.setMainBackgroundColor(Color.white,new Color(239,238,238),new Color(203,203,203));
		
			closeButton= new CircularButton();
			closeButton.setImageIcon(ImageIO.read(new File("./assers/crossBlack.png")));
			closeButton.setMainBackgroundColor(Color.white,new Color(239,238,238),new Color(203,203,203));
		
			multipleOptionsPanel.add(accountButton);
			multipleOptionsPanel.add(addPostButton);
			multipleOptionsPanel.add(helpButton);
			multipleOptionsPanel.add(closeButton);
			
			
		}
		private void setupOptionsButton() {
			optionsButtonPanel= new JPanel();
			optionsButton= new CircularButton() {
				public void paint(Graphics g) {
					super.paint(g);
					
					int intervalsMeasurement= this.getWidth()/12;
					int gapWidth= intervalsMeasurement*2;
					int buttonWidth= (intervalsMeasurement*3)+1;
					int posOne= gapWidth;
					int posTwo= this.getWidth()- gapWidth - buttonWidth;
					g.setColor(new Color(224,222,222));
					g.fillRect(posOne, posOne, buttonWidth, buttonWidth);
					g.fillRect(posTwo, posOne, buttonWidth, buttonWidth);
					g.fillRect(posOne, posTwo, buttonWidth, buttonWidth);
					g.fillRect(posTwo, posTwo, buttonWidth, buttonWidth);
				}
			};
			optionsButton.setMainBackgroundColor(Color.white,new Color(239,238,238),new Color(203,203,203));
			optionsButtonPanel.add(optionsButton);
			optionsButtonPanel.setOpaque(false);
			optionsButtonPanel.setLayout(null);
			this.add(optionsButtonPanel);
			optionsButton.setBorder(null);
			
		
		}
		private void setupMultipleOptionsPanel() {
			multipleOptionsPanel= new JPanel() {
				
				public void paint(Graphics g) {
					g.setColor(Color.white);
					g.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), curvatureRadius,curvatureRadius);
					super.paint(g);
				}
				
				
			};
			this.add(multipleOptionsPanel);
			multipleOptionsPanel.setOpaque(false);
			multipleOptionsPanel.setVisible(false);
			isOpen=false;
		}
			
		
		
		
		
		public JButton getAccountButton() {
			return accountButton;
		}
		
		public JButton getAddPostButton() {
			return addPostButton;
		}
		
		public JButton getHelpButton() {
			return helpButton;
		}
		
		public JButton getCloseButtonPressed() {
			return closeButton;
		}
		private void addListeners() {
			optionsButton.addMouseListener(new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent e) {
					
				}

				@Override
				public void mousePressed(MouseEvent e) {
					if(multipleOptionsPanel.isVisible()) {
						multipleOptionsPanel.setVisible(false);
						isOpen=false;
					}else {
						multipleOptionsPanel.setVisible(true);
						isOpen=true;
					}
					
				}

				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
			});
		}
		
		
		public void setSize(int width, int height) {
			int minDimension= Math.min(width, height);
			super.setSize(minDimension, minDimension);
			int wholePanelIntervalMeasure= minDimension/22;
			
			int optionPanelWidth= wholePanelIntervalMeasure*6;
			int multipleOptionWidth= wholePanelIntervalMeasure*12;
			int wholeGapWidth= wholePanelIntervalMeasure;
			int optionsPanelX= minDimension- optionPanelWidth;;
			int multiplePanelX= minDimension-multipleOptionWidth;
			int multiplePanelY= optionPanelWidth+wholeGapWidth;
			
			optionsButtonPanel.setSize(optionPanelWidth, optionPanelWidth);
			optionsButtonPanel.setLocation(optionsPanelX, 0);
			
		
			optionsButton.setSize(optionPanelWidth, optionPanelWidth,curvatureRadius);
			optionsButton.setLocation(0, 0);
		
			
			multipleOptionsPanel.setSize(multipleOptionWidth,multipleOptionWidth);
			multipleOptionsPanel.setLocation(multiplePanelX,multiplePanelY);
			
			int intervalMeasurement= multipleOptionsPanel.getWidth()/11;
			int gapWidth= intervalMeasurement*1;
			int buttonWidth= intervalMeasurement*4;
			
			int buttonPosOne= gapWidth;
			int buttonPosTwo= (multipleOptionsPanel.getWidth()- gapWidth)- buttonWidth;
			
			
			accountButton.setSize(buttonWidth,buttonWidth,curvatureRadius);
			accountButton.setLocation(buttonPosOne,buttonPosOne);
			
			helpButton.setSize(buttonWidth,buttonWidth,curvatureRadius);
			helpButton.setLocation(buttonPosOne,buttonPosTwo);
			
			addPostButton.setSize(buttonWidth,buttonWidth,curvatureRadius);
			addPostButton.setLocation(buttonPosTwo,buttonPosOne);
			
			closeButton.setSize(buttonWidth,buttonWidth,curvatureRadius);
			closeButton.setLocation(buttonPosTwo,buttonPosTwo);
			
			
		}
	}