package sweng.group.one.client_app_desktop.sceneControl;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import sweng.group.one.client_app_desktop.uiElements.RoundedButton;



public class OptionsScene extends JPanel implements ComponentInterface, LayoutManager{
		

		private static final long serialVersionUID = 1L;
		private RoundedButton accountButton, addPostButton, helpButton, closeButton, optionsButton;
		JPanel multipleOptionsPanel;
		
		private static Color MAIN_COLOUR = Color.white;
		private static Color PRESSED_COLOUR = new Color(203,203,203);
		private static Color HOVER_COLOUR = new Color(239,238,238);
		

		
		public OptionsScene() throws IOException { 
			super();
			this.setLayout(this);
			this.setOpaque(false);
			
			//Set up the multiple options panel
			multipleOptionsPanel= new JPanel() {
				
				private static final long serialVersionUID = 1L;

				public void paint(Graphics g) {
					Graphics2D g2 = (Graphics2D)g.create();
					RenderingHints qualityHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
					qualityHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
					g2.setRenderingHints(qualityHints);
					g2.setColor(Color.white);
					g2.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), curvatureRadius,curvatureRadius);
					g2.dispose();
					super.paint(g);
				}
			};
			this.add(multipleOptionsPanel);
			multipleOptionsPanel.setVisible(false);
			multipleOptionsPanel.setOpaque(false);
			multipleOptionsPanel.setLayout(null);
			
			//add buttons to multiple options scene
			accountButton = new RoundedButton(ImageIO.read(new File("./assets/user.png")), 
					curvatureRadius, 
					MAIN_COLOUR, 
					PRESSED_COLOUR, 
					HOVER_COLOUR) {
				
				private static final long serialVersionUID = 119279111416231659L;

				@Override
				public void buttonPressed() {
					accountPressed();
					multipleOptionsPanel.setVisible(false);
				}
			};
			multipleOptionsPanel.add(accountButton);
			
			addPostButton= new RoundedButton(ImageIO.read(new File("./assets/plus.png")), 
					curvatureRadius, 
					MAIN_COLOUR, 
					PRESSED_COLOUR, 
					HOVER_COLOUR) {
				
				private static final long serialVersionUID = 734090059680252680L;

				@Override
				public void buttonPressed() {
					multipleOptionsPanel.setVisible(false);
					addPostPressed();
				}
			};
			multipleOptionsPanel.add(addPostButton);
			
			helpButton= new RoundedButton(ImageIO.read(new File("./assets/question.png")), 
					curvatureRadius, 
					MAIN_COLOUR, 
					PRESSED_COLOUR, 
					HOVER_COLOUR) {
				
				private static final long serialVersionUID = -7831122631719107034L;

				@Override
				public void buttonPressed() {
					helpPressed();
				}
			};
			multipleOptionsPanel.add(helpButton);
			
			closeButton= new RoundedButton(ImageIO.read(new File("./assets/crossBlack.png")), 
					curvatureRadius, 
					MAIN_COLOUR, 
					PRESSED_COLOUR, 
					HOVER_COLOUR) {
				
				private static final long serialVersionUID = -5748520949039980009L;

				@Override
				public void buttonPressed() {
					multipleOptionsPanel.setVisible(false);
					closePressed();
				}
			};
			multipleOptionsPanel.add(closeButton);

			//Create options button with custom graphics
			optionsButton= new RoundedButton(null, 
					curvatureRadius, 
					MAIN_COLOUR, 
					PRESSED_COLOUR, 
					HOVER_COLOUR) {
				
				private static final long serialVersionUID = 1L;

				@Override
				public void paint(Graphics g) {
					Graphics g2 = g.create();
					super.paint(g2);
					
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
					g2.dispose();
				}
				
				@Override
				public void buttonPressed() {
					boolean visible = multipleOptionsPanel.isVisible();
					multipleOptionsPanel.setVisible(!visible);
					closeButton.setVisible(!visible);
				}
			};
			this.add(optionsButton);
		}
		
		public void helpPressed() {
			
		}
		
		public void addPostPressed() {
			
		}
		
		public void accountPressed() {
			
		}
		
		public void closePressed() {
			
		}
		
		@Override
		public void addLayoutComponent(String name, Component comp) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void removeLayoutComponent(Component comp) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public Dimension preferredLayoutSize(Container parent) {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public Dimension minimumLayoutSize(Container parent) {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public void layoutContainer(Container parent) {
			int minDimension = Math.min(this.getWidth(), this.getHeight());
		    int wholePanelIntervalMeasure = minDimension / 22;

		    int optionPanelWidth = wholePanelIntervalMeasure * 6;
		    int multipleOptionWidth = wholePanelIntervalMeasure * 12;
		    int wholeGapWidth = wholePanelIntervalMeasure;
		    int optionsPanelX = minDimension - optionPanelWidth;
		    int multiplePanelX = minDimension - multipleOptionWidth;
		    int multiplePanelY = optionPanelWidth + wholeGapWidth;


		    optionsButton.setBounds(optionsPanelX, 0, optionPanelWidth, optionPanelWidth);

		    multipleOptionsPanel.setBounds(multiplePanelX, multiplePanelY, multipleOptionWidth, multipleOptionWidth);

		    int intervalMeasurement = multipleOptionsPanel.getWidth() / 11;
		    int gapWidth = intervalMeasurement;
		    int buttonWidth = intervalMeasurement * 4;

		    int buttonPosOne = gapWidth;
		    int buttonPosTwo = (multipleOptionsPanel.getWidth() - gapWidth) - buttonWidth;

		    accountButton.setBounds(buttonPosOne, buttonPosOne, buttonWidth, buttonWidth);

		    helpButton.setBounds(buttonPosOne, buttonPosTwo, buttonWidth, buttonWidth);

		    addPostButton.setBounds(buttonPosTwo, buttonPosOne, buttonWidth, buttonWidth);

		    closeButton.setBounds(buttonPosTwo, buttonPosTwo, buttonWidth, buttonWidth);
		}
		
	}