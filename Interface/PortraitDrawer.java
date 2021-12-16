package TestProject.Interface;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class PortraitDrawer extends JPanel{
	private static final long serialVersionUID = 658049413058123602L;
	private BufferedImage Portrait;
	
	public PortraitDrawer() {
		this.setSize(34, 34);
	}
	
	public PortraitDrawer(int Size) {
		this.setSize(32 * Size + 2, 32 * Size + 2);
	}
	
	public void ReplacePortrait(BufferedImage NewPortrait) {
		Portrait = NewPortrait;
		super.repaint();
	}
	
	@Override
	protected void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		graphics.setColor(Color.black);
		graphics.fillRect(0, 0, this.getWidth() + 1, this.getHeight() + 1);
		if(Portrait != null) {
			graphics.drawImage(Portrait, 1, 1, this.getWidth() - 2, this.getHeight() - 2, null);
		}
	}
}
