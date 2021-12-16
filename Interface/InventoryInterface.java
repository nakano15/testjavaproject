package TestProject.Interface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Insets;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.*;

import TestProject.Item;
import TestProject.ObjectCreator;
import TestProject.Player;

public class InventoryInterface extends JInternalFrame {
	private static final long serialVersionUID = -7879703900908564137L;
	public JButton Bag1Button, Bag2Button, Bag3Button, Bag4Button, Bag5Button;
	public JToolBar BagsToolbar;
	public JButton[] InventorySlotButton = new JButton[30];
	public int SelectedBag = 0, SelectedItem = 0;
	public Item GetSelectedItem() {
		return Player.Bags[SelectedBag].item[SelectedItem];
	}
	
	public InventoryInterface() {
		this.setSize(300, 300);
		this.setTitle("Inventory");
		this.setClosable(true);
		this.setMaximizable(true);
		this.setResizable(true);
		this.setIconifiable(true);
		BorderLayout layout = new BorderLayout();
		layout.setHgap(4);
		layout.setVgap(4);
		BagsToolbar = new JToolBar("Bags");
		BagsToolbar.setFloatable(false);
		this.add(BagsToolbar, BorderLayout.PAGE_START);
		Bag1Button = ObjectCreator.CreateButton("Bag 1", new Point(0,0), 60);
		Bag2Button = ObjectCreator.CreateButton("Bag 2", new Point(0,0), 60);
		Bag3Button = ObjectCreator.CreateButton("Bag 3", new Point(0,0), 60);
		Bag4Button = ObjectCreator.CreateButton("Bag 4", new Point(0,0), 60);
		Bag5Button = ObjectCreator.CreateButton("Bag 5", new Point(0,0), 60);
		BagsToolbar.add(Bag1Button);
		BagsToolbar.add(Bag2Button);
		BagsToolbar.add(Bag3Button);
		BagsToolbar.add(Bag4Button);
		BagsToolbar.add(Bag5Button);
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.addComponentListener(new ComponentListener() {

			@Override
			public void componentResized(ComponentEvent e) {
			}

			@Override
			public void componentMoved(ComponentEvent e) {
			}

			@Override
			public void componentShown(ComponentEvent e) {
				// TODO Auto-generated method stub
				RefreshInventory();
			}

			@Override
			public void componentHidden(ComponentEvent e) {
			}			
		});
		JPanel SlotsPanel = new JPanel();
		SlotsPanel.setBackground(Color.gray);
		SlotsPanel.setLayout(null);
		this.add(SlotsPanel);
		for(int i = 0; i < 30; i++)
		{
			InventorySlotButton[i] = new JButton();
			InventorySlotButton[i].setSize(40,40);
			InventorySlotButton[i].setText("");
			InventorySlotButton[i].setMargin(new Insets(0,0,0,0));
			InventorySlotButton[i].setLocation(2 + 42 * (i % 5), 2 + 42 * (i / 5));
			InventorySlotButton[i].addActionListener(new InventoryButtonClick(i));
			InventorySlotButton[i].setHorizontalTextPosition(SwingConstants.RIGHT);
			InventorySlotButton[i].setVerticalTextPosition(SwingConstants.BOTTOM);
			SlotsPanel.add(InventorySlotButton[i]);
		}
	}
	
	public void RefreshInventory() {
		for(int i = 0; i < 30; i++) {
			if(i >= Player.Bags[SelectedBag].BagSlots)
			{
				InventorySlotButton[i].setVisible(false);
			}else {
				InventorySlotButton[i].setVisible(true);
				if(Player.Bags[SelectedBag].item[i].Stack == 0)
				{
					InventorySlotButton[i].setIcon(null);
					InventorySlotButton[i].setText("");
					InventorySlotButton[i].setToolTipText("");
					InventorySlotButton[i].setBorder(BorderFactory.createLineBorder(Color.black, 2));
				}else {
					Item item = Player.Bags[SelectedBag].item[i];
					InventorySlotButton[i].setIcon(new ImageIcon(item.GetBase().ItemIcon));
					InventorySlotButton[i].setText("" + item.Stack);
					InventorySlotButton[i].setToolTipText(item.Name() + "x" + item.Stack);
					InventorySlotButton[i].setBorder(BorderFactory.createLineBorder(item.GetRarityColor(), 2));
				}
			}
			
		}
	}
	
	public class InventoryButtonClick implements ActionListener{
		public int InventorySlot = 0;
		
		public InventoryButtonClick(int Slot)
		{
			InventorySlot = Slot;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			SelectedItem = InventorySlot;
			Item item = Player.Bags[SelectedBag].item[InventorySlot];
			if(item.Stack == 0)
				return;
			JPopupMenu itemAction = new JPopupMenu();
			JMenuItem option;
			switch(item.GetBase().itemType)
			{
			case Misc:
				
				break;
			case RestoreHealth:
				
				break;
			case Weapon:
				
				break;
			case Armor:
				
				break;
			case Accessory:
				
				break;
			case Bags:
				
				break;
			}
			option = new JMenuItem();
			option.setText("Drop");
			option.addActionListener(new DropButtonAction());
			itemAction.add(option);
			option = new JMenuItem();
			option.setText("Close");
			itemAction.add(option);
			Point MouseLocation = new Point();
			MouseLocation.x = (int) (MouseInfo.getPointerInfo().getLocation().getX() - ((JButton)e.getSource()).getLocationOnScreen().getX());
			MouseLocation.y = (int) (MouseInfo.getPointerInfo().getLocation().getY() - ((JButton)e.getSource()).getLocationOnScreen().getY());
			itemAction.show((JButton)e.getSource(), MouseLocation.x, MouseLocation.y);
		}		
	}
	
	public class DropButtonAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			Item item = GetSelectedItem();
			int Choice = JOptionPane.showConfirmDialog((JMenuItem)e.getSource(), "Wait! You're about to drop " + item.Name() + "x" + item.Stack + "!\n"
					+ "Are you sure that you want to do that?", "Think before choosing", JOptionPane.YES_NO_OPTION);
			if(Choice == 0) {
				JOptionPane.showMessageDialog((JMenuItem)e.getSource(), "You tossed " + item.Name() + "x" + item.Stack+ " away...");
				item.Stack = 0;
				RefreshInventory();
			}
		}
		
	}
}
