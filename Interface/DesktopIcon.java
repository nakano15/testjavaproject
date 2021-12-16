package TestProject.Interface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import TestProject.ObjectCreator;

public class DesktopIcon extends JButton {
	private static final long serialVersionUID = 5564442056242421284L;

	public JInternalFrame frame;
	
	public DesktopIcon(String Text, BufferedImage Icon) {
		this.setToolTipText(Text);
		this.setHorizontalAlignment(CENTER);
		this.setVerticalAlignment(CENTER);
		this.setHorizontalAlignment(CENTER);
		this.setVerticalTextPosition(BOTTOM);
		if(Icon != null)
			this.setIcon(new ImageIcon(Icon));
		//this.setOpaque(false);
		//this.setContentAreaFilled(false);
		this.setBorderPainted(true);
		this.setForeground(Color.white);
		this.setBackground(Color.white);
		this.setFocusable(false);
		this.setIconTextGap(0);
		Dimension dim = new Dimension(64, 64);
		this.setSize(dim);
		//this.setBorder(BorderFactory.createEmptyBorder());
	}
}
