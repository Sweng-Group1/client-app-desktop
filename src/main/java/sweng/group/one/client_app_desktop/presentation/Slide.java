package sweng.group.one.client_app_desktop.presentation;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Slide extends JPanel {
	
	private int pointWidth;
	private int pointHeight;
	private ArrayList<PresElement> elements;

	//example constructor
	public Slide(int PtWidth, int PtHeight){
		super(new GridBagLayout());
		
		this.elements = new ArrayList<>();
		
		this.setPointWidth(PtWidth);
		this.setPointHeight(PtHeight);
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		
		//fill the grid with glue -> allows empty cells
		for (int x = 0; x < pointWidth; x++) {
			for (int y = 0; y < pointHeight; y++) {
				gbc.gridx = x;
				gbc.gridy = y;
				this.add(Box.createGlue(), gbc);
			}
		}
		this.validate();
	}
	
	public void add(PresElement element) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.gridx = element.pos.x;
		gbc.gridy = element.pos.y;
		gbc.gridwidth = element.width;
		gbc.gridheight = element.height;
		
		this.add(element.component, gbc);
		this.elements.add(element);
	}

	public int getPointWidth() {
		return pointWidth;
	}

	public void setPointWidth(int pointWidth) {
		this.pointWidth = pointWidth;
	}

	public int getPointHeight() {
		return pointHeight;
	}

	public void setPointHeight(int pointHeight) {
		this.pointHeight = pointHeight;
	}
}
