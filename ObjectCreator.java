package TestProject;
import java.awt.*;
import javax.swing.*;

public class ObjectCreator {
	public static JLabel CreateLabel(String Text, Point Position, int Width) {
		JLabel label = new JLabel();
		label.setText(Text);
		label.setLocation(Position);
		label.setSize(Width, 20);
		return label;
	}
	
	public static JTextField CreateTextBox(Point Position, int Width) {
		JTextField text = new JTextField();
		text.setLocation(Position);
		text.setSize(Width, 20);
		return text;
	}
	
	public static JComboBox<String> CreateComboBox(Point Position, String[] Alternatives, int Width)
	{
		JComboBox<String> combo = new JComboBox<String>();
		combo.setLocation(Position);
		combo.setSize(Width, 20);
		for (String Alternative : Alternatives){
			combo.addItem(Alternative);
		}
		return combo;
	}
	
	public static JButton CreateButton(String Text, Point Position, int Width) {
		JButton button = new JButton();
		button.setLocation(Position);
		button.setText(Text);
		button.setSize(Width, 20);
		return button;
	}
	
	public static JRadioButton CreateRadioButton(String Text, Point Position, int Width) {
		JRadioButton radio = new JRadioButton();
		radio.setLocation(Position);
		radio.setText(Text);
		radio.setSize(Width, 20);
		return radio;
	}
	
	public static JSlider CreateSlider(Point Position, int MinValue, int MaxValue, int Width) {
		JSlider slider = new JSlider();
		slider.setLocation(Position);
		slider.setMaximum(MaxValue);
		slider.setMinimum(MinValue);
		slider.setSize(Width, 20);
		return slider;
	}
	
	public static JTabbedPane CreateTabbedPane(Point Position, int Width, int Height) {
		JTabbedPane pane = new JTabbedPane();
		pane.setLocation(Position);
		pane.setSize(Width, Height);
		return pane;
	}
	
	public static JList CreateList(Point Position, int Width, int Height) {
		JList list = new JList();
		list.setLocation(Position);
		list.setSize(Width, Height);
		return list;
	}
	
	public static JProgressBar CreateProgressBar(Point Position, int Width, int Height, int MaxValue)
	{
		JProgressBar progress = new JProgressBar();
		progress.setLocation(Width, Height);
		progress.setMaximum(MaxValue);
		return progress;
	}
}
