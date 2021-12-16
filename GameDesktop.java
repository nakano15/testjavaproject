package TestProject;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.*;
import TestProject.Interface.*;

public class GameDesktop extends JDesktopPane{
	public static GameDesktop Desktop;
	public static BufferedImage DesktopWallpaper, UnknownIcon, DeadAvatar;
	public static JPanel desktopIcons;
	public static DesktopIcon CreateCharacterIcon, CharacterInfoIcon, ExplorationIcon, InventoryIcon;
	public static JInternalFrame MyCharacterInfoPanel;
	public static ExplorationInterface explorationInterface;
	public static InventoryInterface inventoryInterface;
	public static Battle CurrentBattle;
	
	static final long serialVersionUID = 12312;
	public GameDesktop() {
		CurrentBattle = new Battle(new Character[0], new Character[0]);
		UnknownIcon = LoadImage("src/TestProject/Content/Icons/UnknownIcon.png");
		DeadAvatar = LoadImage("src/TestProject/Content/Icons/DeadAvatar.png");
		LoadWallpaper();
		Desktop = this;
		SetupIconsLayer();
		this.setVisible(true);
		this.setBackground(Color.blue);
		Main.GameFrame.addWindowStateListener(new WindowStateListener() {
			@Override
			public void windowStateChanged(WindowEvent e) {
				// TODO Auto-generated method stub
				Desktop.ReadjustDesktopIcons();
			}
		});
		explorationInterface = new ExplorationInterface();
		this.add(explorationInterface);
		inventoryInterface = new InventoryInterface();
		this.add(inventoryInterface);
		/*Battle b = new Battle(new Character[] {new Character(Races.Human), new Character(Races.Human), new Character(Races.Human), new Character(Races.Human)}, 
				new Character[] { new Monster(0), new Monster(0), new Monster(0) });
		this.add(b);
		b.show();*/
	}
	
	public static void StartBattle(Character[] GroupA, Character[] GroupB) {
		for(Component comp: Desktop.getComponents())
		{
			if(comp instanceof Battle)
				Desktop.remove(comp);
		}
		CurrentBattle = new Battle(GroupA, GroupB);
		Desktop.add(CurrentBattle);
		CurrentBattle.show();
		CurrentBattle.requestFocus();
	}
	
	public BufferedImage LoadImage(String Directory) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(Directory));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
	
	private void SetupIconsLayer() {
		desktopIcons = new JPanel();
		desktopIcons.setSize(800, 600);
		desktopIcons.setOpaque(false);
		desktopIcons.setLayout(null); //Must set icons to top-left, without resizing them.
		this.add(desktopIcons);
		SetupIcons();
		ReadjustDesktopIcons();
	}
	
	private void SetupIcons() {
		CreateCharacterIcon = new DesktopIcon("Create Character", LoadImage("src/TestProject/Content/Icons/CreateCharacterIcon.png"));
		CreateCharacterIcon.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					CharacterCreationInterface cci = new CharacterCreationInterface(new AbstractAction() {
						@Override
						public void actionPerformed(ActionEvent e) {
							//Show other desktop icons.
							Player.MyCharacter = CharacterCreationInterface.character;
							Player.UponCreatingMyCharacter();
							ToggleIconVisibility(CharacterInfoIcon, true);
							ToggleIconVisibility(ExplorationIcon, true);
						}
					});
					Desktop.add(cci);
					ToggleIconVisibility(CreateCharacterIcon, false);
					cci.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					SetWindowToCenter(cci);
					//cci.setLocation((Desktop.getWidth() - cci.getWidth()) / 2, (Desktop.getHeight() - cci.getHeight()) / 2);
				}
		});
		AddIcon(CreateCharacterIcon);
		//
		CharacterInfoIcon = new DesktopIcon("Your Information", LoadImage("src/TestProject/Content/Icons/CharacterInfo.png"));
		CharacterInfoIcon.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(MyCharacterInfoPanel != null && MyCharacterInfoPanel.isVisible())
					return;
				JInternalFrame charInfoPanel = new JInternalFrame();
				charInfoPanel.setClosable(true);
				charInfoPanel.setTitle("Your Infos");
				CharacterInfoDisplay CiD = new CharacterInfoDisplay(Player.MyCharacter, Player.MyCharacter.GetCharacterFace(), false);
				//charInfoPanel.setLocation(0, 0);
				charInfoPanel.add(CiD);
				charInfoPanel.setSize(CiD.getWidth(), CiD.getHeight());
				charInfoPanel.setVisible(true);
				charInfoPanel.requestFocus();
				Desktop.add(charInfoPanel);
				charInfoPanel.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				SetWindowToCenter(charInfoPanel);
				MyCharacterInfoPanel = charInfoPanel;
			}			
		});
		CharacterInfoIcon.setVisible(false);
		AddIcon(CharacterInfoIcon);
		//
		ExplorationIcon = new DesktopIcon("Exploration", LoadImage("src/TestProject/Content/Icons/ExploreIcon.png"));
		ExplorationIcon.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!explorationInterface.isVisible()) {
					explorationInterface.setVisible(true);
					SetWindowToCenter(explorationInterface);
				}
			}			
		});
		ExplorationIcon.setVisible(false);
		AddIcon(ExplorationIcon);
		//
		InventoryIcon = new DesktopIcon("Inventory", LoadImage("src/TestProject/Content/Icons/InventoryIcon.png"));
		InventoryIcon.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!inventoryInterface.isVisible()) {
					inventoryInterface.setVisible(true);
					SetWindowToCenter(inventoryInterface);
				}
			}
		});
		InventoryIcon.setVisible(true);
		AddIcon(InventoryIcon);
	}
	
	public void SetWindowToCenter(Component component)
	{
		component.setLocation((Desktop.getWidth() - component.getWidth()) / 2, (Desktop.getHeight() - component.getHeight()) / 2);
	}
	
	public void AddIcon(DesktopIcon icon) {
		desktopIcons.add(icon);
		ReadjustDesktopIcons();
	}
	
	public void ToggleIconVisibility(DesktopIcon icon, boolean Visible) {
		icon.setVisible(Visible);
		ReadjustDesktopIcons();
	}
	
	public void ReadjustDesktopIcons() {
		desktopIcons.setSize(Main.GameFrame.getSize());
		Point ElementPos = new Point(4,4);
		for(Component compo : desktopIcons.getComponents())
		{
			if(!compo.isEnabled() || !compo.isVisible())
				continue;
			compo.setLocation(ElementPos.x, ElementPos.y);
			ElementPos.y += 72;
			if(ElementPos.y + 72 >= desktopIcons.getHeight() - 20 - 20) {
				ElementPos.x += 72;
				ElementPos.y = 4;
			}
		}
	}
	
	public void LoadWallpaper() {
		try {
			DesktopWallpaper = ImageIO.read(new File("src/TestProject/Content/Wallpaper.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.repaint();
	}
	
	@Override
	protected void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		//Draw Wallpaper
		if(DesktopWallpaper != null)
			graphics.drawImage(DesktopWallpaper,0 ,0 , this.getWidth(), this.getHeight(), null);
	}
}
