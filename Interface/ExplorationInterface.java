package TestProject.Interface;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import TestProject.Battle;
import TestProject.Character;
import TestProject.GameDesktop;
import TestProject.Main;
import TestProject.Map;
import TestProject.Map.MapCharacter;
import TestProject.Monster;
import TestProject.ObjectCreator;
import TestProject.Player;

public class ExplorationInterface extends JInternalFrame {
	private static final long serialVersionUID = 8187408352433075850L;
	private static Image[] ExploreIcons = new Image[10];
	public JLabel MapNameLbl, MapLevelLbl;
	public JRadioButton[] BehaviorRdb = new JRadioButton[10];
	public JLabel ActionNameLbl, ActionProgressLbl;
	public JTabbedPane ContentExploreTab;
	public JList NpcTabPanel, MonsterTabPanel;
	public JProgressBar ActionProgressBar;
	public final int NPC_Tab_Index = 0, Monster_Tab_Index = 1;
	public static byte CurrentAction = (byte) 255;
	public final byte Action_MoveTopLeft = 0, Action_MoveTop = 1, Action_MoveTopRight = 2,
			Action_MoveLeft = 3, Action_Explore = 4, Action_MoveRight = 5,
			Action_MoveLowerLeft = 6, Action_MoveLower = 7, Action_MoveLowerRight = 8,
			Action_Rest = 9, Action_Npc = (byte) 200, Action_Monster = (byte) 201;
	public byte SubActionValue = 0;
	public float PosX = 0, PosY = 0;
	public static int CurrentMapID = 0;
	public float CurrentActionTime = 0f, CurrentActionMaxTime = 1f;
	private byte UpdateDelay = 0;
	private static byte ListIndexLastValue = 0;
	
	public ExplorationInterface()
	{
		for(int i = 0; i < 10; i++)
		{
			ExploreIcons[i] = GameDesktop.Desktop.LoadImage("src/TestProject/Content/Interface/ExploreIcons/ExploreIcon" + (i + 1) + ".png");
		}
		this.setTitle("Exploration");
		this.setSize(500, 360);
		Point ElementPosition = new Point(6, 6);
		JPanel panel = new JPanel();
		panel.setSize(300,  66);
		panel.setBorder(BorderFactory.createLineBorder(Color.black));
		panel.setLayout(null);
		this.add(panel);
		MapNameLbl = ObjectCreator.CreateLabel("Map Name", ElementPosition, 300);
		//MapNameLbl.setSize(300 - 12, 60);
		MapNameLbl.setHorizontalTextPosition(SwingConstants.CENTER);
		MapNameLbl.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(MapNameLbl);
		ElementPosition.y += 30;
		ElementPosition.x += 200 - 3;
		MapLevelLbl = ObjectCreator.CreateLabel("Level: 99", ElementPosition, 75);
		MapLevelLbl.setHorizontalTextPosition(SwingConstants.CENTER);
		MapLevelLbl.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(MapLevelLbl);
		ElementPosition.x = 0;
		ElementPosition.y += 30;
		SetupRadioButtons(ElementPosition);
		this.setLayout(null);
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.setClosable(true);
		this.setResizable(true);
		//
		ElementPosition.x = 300;
		ElementPosition.y = 6;
		panel = new JPanel();
		panel.setSize(199, 60); //359);
		panel.setLocation(300, 0);
		panel.setBorder(BorderFactory.createLineBorder(Color.black));
		panel.setLayout(null);
		this.add(panel);
		ActionNameLbl = ObjectCreator.CreateLabel("Action Name", new Point(4, ElementPosition.y), 200);
		ActionNameLbl.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(ActionNameLbl);
		ElementPosition.y += 30;
		ActionProgressBar = ObjectCreator.CreateProgressBar(new Point(4, ElementPosition.y), 200, 30, 10000);
		ActionProgressBar.setValue(5000);
		panel.add(ActionProgressBar);
		ActionProgressLbl = ObjectCreator.CreateLabel("Action Progress", new Point(4, ElementPosition.y), 200);
		ActionProgressLbl.setBackground(new Color(0,0,0,0));
		ActionProgressLbl.setHorizontalAlignment(SwingConstants.CENTER);
		ElementPosition.y += 30;
		panel.add(ActionProgressLbl);
		//
		ElementPosition.y = 60;
		ContentExploreTab = ObjectCreator.CreateTabbedPane(ElementPosition, 199, 299);
		NpcTabPanel = new JList();
		NpcTabPanel.setSize(200, 400);
		BoxLayout NpcLayout = new BoxLayout(NpcTabPanel, BoxLayout.Y_AXIS);
		NpcTabPanel.setLayout(NpcLayout);
		ContentExploreTab.addTab("Npc", NpcTabPanel);
		MonsterTabPanel = new JList();
		BoxLayout MonsterLayout = new BoxLayout(MonsterTabPanel, BoxLayout.Y_AXIS);
		MonsterTabPanel.setLayout(MonsterLayout);
		ContentExploreTab.addTab("Monster", MonsterTabPanel);
		this.add(ContentExploreTab);
		ChangeMap(0);
		this.UponChangingAction();
		//
	}
	
	public Map GetMap()
	{
		return Map.GetMap(CurrentMapID);
	}
	
	public void ChangeMap(int MapID)
	{
		if(MapID < 0 || MapID >= Map.Maps.length)
			return;
		CurrentMapID = MapID;
		Map m = GetMap();
		MapNameLbl.setText(m.Name);
		MapLevelLbl.setText("Level " + m.Level + "~");
		for(int i = 0; i < 8; i++) {
			int SlotID = i;
			if(SlotID >= 4)
				SlotID ++;
			BehaviorRdb[SlotID].setVisible(m.MapConnections[i] > -1);
			if(m.MapConnections[i] > -1) {
				Map connection = Map.GetMap(m.MapConnections[i]);
				String MapName = connection.Name;
				switch(connection.morality) {
				default:
					MapName += " Lv " + connection.Level + "~";
					break;
				case SafeZone:
					
					break;
				}
				BehaviorRdb[SlotID].setText(MapName);
				BehaviorRdb[SlotID].setToolTipText(MapName);
			}
		}
		BehaviorRdb[Action_Explore].setSelected(true);
		NpcTabPanel.removeAll();
		MonsterTabPanel.removeAll();
		ListIndexLastValue = 0;
		for(Map.MapCharacter npc : m.NpcsList)
		{
			NpcList n = new NpcList(npc.character);
			n.SetFound(npc.Found);
			NpcTabPanel.add(n);
		}
		ListIndexLastValue = 0;
		for(Map.MapCharacter mob : m.MonsterList)
		{
			NpcList n = new NpcList(mob.character);
			n.SetFound(mob.Found);
			MonsterTabPanel.add(n);
		}
	}
	
	private void SetupRadioButtons(Point ElementPosition) {
		ButtonGroup behaviorgroup = new ButtonGroup();
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createLineBorder(Color.black));
		panel.setLayout(null);
		panel.setLocation(0, ElementPosition.y);
		panel.setSize(300, 359 - ElementPosition.y);
		ElementPosition.y = 2;
		this.add(panel);
		for(int y = 0; y < 3; y++)
		{
			ElementPosition.x = 0;
			for(int x = 0; x < 3; x++) {
				int i = x + y * 3;
				JRadioButton element = ObjectCreator.CreateRadioButton("Move#"+i, ElementPosition, 100);
				element.setSize(100, 60);
				element.setIcon(new ImageIcon(ExploreIcons[i]));
				element.setHorizontalTextPosition(SwingConstants.CENTER);
				element.setHorizontalAlignment(SwingConstants.CENTER);
				element.setVerticalTextPosition(SwingConstants.TOP);
				element.setMargin(new Insets(0,0,0,0));
				if(i != Action_Explore)
					element.setVisible(false);
				BehaviorRdb[i] = element;
				panel.add(element);
				behaviorgroup.add(element);
				ElementPosition.x += 100;
			}
			ElementPosition.y += 60;
		}
		ElementPosition.x = 100;
		{
			final int i = 9;
			JRadioButton element = ObjectCreator.CreateRadioButton("Move#"+i, ElementPosition, 100);
			element.setIcon(new ImageIcon(ExploreIcons[i]));
			element.setSize(100, 60);
			element.setHorizontalTextPosition(SwingConstants.CENTER);
			element.setHorizontalAlignment(SwingConstants.CENTER);
			element.setVerticalTextPosition(SwingConstants.TOP);
			BehaviorRdb[i] = element;
			panel.add(element);
			behaviorgroup.add(element);
		}
		ElementPosition.x = 6;
		ElementPosition.y += 60;
		for(byte b = 0; b < BehaviorRdb.length; b++)
		{
			BehaviorRdb[b].addActionListener(new BehaviorButtonActions(b));
		}
		BehaviorRdb[Action_Rest].setSelected(true);
		BehaviorRdb[Action_Explore].setText("Explore");
		BehaviorRdb[Action_Rest].setText("Rest");
	}
		
	public void UpdateExploration() {
		if(!this.isVisible())
			return;
		Map map = GetMap();
		if(GameDesktop.Desktop.CurrentBattle.isVisible())
		{
			return;
		}
		CurrentActionTime += map.NavigationRate;
		int Group = 0;
		if(CurrentActionTime >= CurrentActionMaxTime)
		{
			CurrentActionTime = 0;
			switch(CurrentAction) {
				case 0:
				case 1:
				case 2:
				case 3:
				case 5:
				case 6:
				case 7:
				case 8: //Moving to another map.
					
					break;
				case Action_Explore:
					if(Main.random.nextFloat() < 0.35f) {
						if(Main.random.nextFloat() < 0.5f)
						{
							if(map.NpcsList.size() > 0) {
								int Selected = Main.random.nextInt(map.NpcsList.size());
								if(!map.NpcsList.get(Selected).Found)
								{
									map.NpcsList.get(Selected).Found = true;
									NpcList n = (NpcList)NpcTabPanel.getComponent(Selected);
									n.SetFound(true);
								}
							}
						}
						else
						{
							if(map.MonsterList.size() > 0) {
								int Selected = Main.random.nextInt(map.MonsterList.size());
								MapCharacter mobInMap = map.MonsterList.get(Selected);
								if(!mobInMap.Found)
								{
									mobInMap.Found = true;
									NpcList n = (NpcList)MonsterTabPanel.getComponent(Selected);
									n.SetFound(true);
								}
								Monster m = new Monster(0);
								m.Race = mobInMap.character.Race;
								m.Name = m.Race.Name;
								m.SetLevel(mobInMap.character.Level);
								GameDesktop.StartBattle(Player.groups.get(Group).GetCharacters(), new Character[] {m});
							}
						}
					}
					break;
				case Action_Rest:
					for(Character character: Player.groups.get(0).Members)
					{
						int HPRecovered = (int)(character.GetMHP() * (1f / 16));
						if(HPRecovered < 1)
							HPRecovered = 1;
						character.HP += HPRecovered;
						character.HP = character.GetMHP();
					}
					break;
			}
			this.UponChangingAction();
		}
		UpdateDelay++;
		if(UpdateDelay >= 8) {
			UpdateProgress();
			UpdateDelay = 0;
		}
	}
	
	private void UponChangingAction()
	{
		CurrentActionTime = 0;
		if(ActionNameLbl == null)
			return;
		Map m = GetMap();
		final float FPS60 = 1f / 60;
		switch(CurrentAction)
		{
		case 0:
		case 1:
		case 2:
		case 3:
		case 5:
		case 6:
		case 7:
		case 8:
			{
				int ConnectionID = CurrentAction;
				if(ConnectionID > 4)
					ConnectionID--;
				Map otherMap = Map.GetMap(m.MapConnections[ConnectionID]);
				ActionNameLbl.setText("Moving to " + otherMap.Name + "...");
				CurrentActionMaxTime = (m.Width * m.Height) * FPS60 * 0.05f;
			}
			break;
		case Action_Explore:
			ActionNameLbl.setText("Exploring...");
			CurrentActionMaxTime = (5 + Main.random.nextInt(20)) * FPS60 * 0.01f;
			break;
		case Action_Rest:
			ActionNameLbl.setText("Resting...");
			CurrentActionMaxTime = 6 * FPS60;
			break;
		}
		ActionProgressLbl.setText("[0.0%]");
	}
	
	private void UpdateProgress()
	{
		ActionProgressLbl.setText("["+((int)(CurrentActionTime * 100 / CurrentActionMaxTime))+"%]");
	}
	
	public class BehaviorButtonActions implements ActionListener{
		private byte ButtonActionID = 0;
		
		public BehaviorButtonActions(byte Id) {
			ButtonActionID = Id;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub

			boolean Changed = ButtonActionID != CurrentAction;
			CurrentAction = ButtonActionID;
			if(Changed)
				UponChangingAction();
		}
	}
	
	public class NpcList extends JPanel{
		private static final long serialVersionUID = -3302684366171424612L;
		public Character NpcInfo;
		public BufferedImage NpcImage;
		public JLabel Text;
		public Display ImageDisplay;
		public boolean Found = false;
		public byte Index = 0;
		
		public NpcList(Character characterInfo) {
			Index = ListIndexLastValue ++;
			this.setSize(200, 30);
			this.setLayout(null);
			NpcInfo = characterInfo;
			ImageDisplay = new Display();
			ImageDisplay.setLocation(4, 4);
			ImageDisplay.setSize(32, 32);
			Text = new JLabel();
			Text.setLocation(40, 10);
			Text.setSize(160, 20);
			this.add(ImageDisplay);
			this.add(Text);
			this.setBorder(BorderFactory.createLineBorder(Color.black));
			SetFound(false);
		}
		
		public void SetFound(boolean IsFound)
		{
			if(IsFound)
			{
				if(NpcInfo.getClass() == Monster.class)
				{
					Monster m = (Monster)NpcInfo;
					Text.setText(m.Name);
					NpcImage = m.GetCharacterFace();
				}
				else
				{
					Text.setText(NpcInfo.Name);
					NpcImage = NpcInfo.GetCharacterFace();
				}
			}
			else
			{
				Text.setText("Unknown");
				NpcImage = GameDesktop.UnknownIcon;
			}
			Found = IsFound;
			ImageDisplay.repaint();
		}
		
		public class Display extends JPanel{
			@Override
			protected void paintComponent(Graphics graphics) {
				super.paintComponent(graphics);
				graphics.drawImage(NpcImage, 0, 0, 32, 32, null);
			}
		}
	}
	
	public enum CurrentExploreAction{
		Exploring,
		OpeningAChest
	}
}
