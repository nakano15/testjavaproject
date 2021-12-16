package TestProject;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

public class Battle extends JInternalFrame {
	public static List<Battle> ActiveBattles = new ArrayList<Battle>();
	
	private static final long serialVersionUID = -4022954433203527703L;
	public CharacterInfo[] GroupA = new CharacterInfo[0];
	public BattleOponentPanel[] GroupB = new BattleOponentPanel[0];
	public JToolBar ActionBar;
	public JButton AttackButton, MagicButton, ItemButton, RunButton, AutoButton;
	public BackgroundRenderer BattleBackground;
	public JLabel BattleActionLabel;
	public JList LootList;
	public JLabel ExpLbl;
	final int BattleSceneWidth = 400, BattleSceneHeight = 300;
	public BattleCharacter[] TurnOrder = new BattleCharacter[0];
	public int CurrentCharacter = 0;
	public int Delay = 0;
	public boolean IsAutoBattle = false;
	public boolean RewardMessage = false;
	
	public Battle(Character[] GroupA, Character[] GroupB) {
		if(GroupA.length == 0 || GroupB.length == 0) {
			return;
		}
		SetupInternalFrame();
		this.GroupA = new CharacterInfo[GroupA.length];
		for(int i = 0; i < GroupA.length; i++)
			this.GroupA[i] = new CharacterInfo(GroupA[i]);
		this.GroupB = new BattleOponentPanel[GroupB.length];
		for(int i = 0; i < GroupB.length; i++)
			this.GroupB[i] = new BattleOponentPanel(GroupB[i]);
		this.setTitle(GroupA[0].Name+ "'s Battle");
		SetBattleActionsPosition();
		ArrangeMonstersPositioning();
		SetBattleOrder();
		ActiveBattles.add(this);
		ShowBattleMessage(GroupB[0].Name+" has appeared!");
	}
	
	public void ShowBattleMessage(String Text) {
		BattleActionLabel.setText(Text);
		Delay = 100 + Text.length();
	}
	
	public void SetBattleOrder() {
		List<BattleCharacter> NewOrder = new ArrayList<BattleCharacter>();
		List<BattleCharacter> ToCheck = new ArrayList<BattleCharacter>();
		for(CharacterInfo character: GroupA)
		{
			ToCheck.add(character.character);
		}
		for(BattleOponentPanel character: GroupB)
		{
			ToCheck.add(character.character);
		}
		while(ToCheck.size() > 0)
		{
			int FastestSpeed = Integer.MIN_VALUE;
			BattleCharacter FastestCharacter = null;
			for(BattleCharacter character: ToCheck)
			{
				int Speed = character.character.GetAGI() - 1 + Main.random.nextInt(3);
				if(FastestCharacter == null || Speed > FastestSpeed)
				{
					FastestSpeed = Speed;
					FastestCharacter = character;
				}
			}
			ToCheck.remove(FastestCharacter);
			NewOrder.add(FastestCharacter);
		}
		TurnOrder = NewOrder.toArray(new BattleCharacter[0]);
	}
	
	public void SetBattleActionsPosition() {
		ActionBar = new JToolBar();
		ActionBar.setFloatable(false);
		ActionBar.setBackground(Color.BLUE);
		AttackButton = ObjectCreator.CreateButton("Attack", new Point(0,0), 60);
		MagicButton = ObjectCreator.CreateButton("Magic", new Point(0,0), 60);
		ItemButton = ObjectCreator.CreateButton("Item", new Point(0,0), 60);
		RunButton = ObjectCreator.CreateButton("Run", new Point(0,0), 60);
		AutoButton = ObjectCreator.CreateButton("Auto", new Point(0,0), 60);
		ActionBar.add(AttackButton);
		ActionBar.add(MagicButton);
		ActionBar.add(ItemButton);
		ActionBar.add(RunButton);
		ActionBar.add(AutoButton);
		this.add(ActionBar, BorderLayout.PAGE_START);
		BattleActionLabel = ObjectCreator.CreateLabel("", new Point(0, (int)(BattleSceneHeight * 0.4f) - 45), 0);
		BattleActionLabel.setSize(BattleSceneWidth, 90);
		BattleActionLabel.setHorizontalAlignment(SwingConstants.CENTER);
		BattleActionLabel.setVerticalAlignment(SwingConstants.CENTER);
		BattleActionLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		BattleActionLabel.setVerticalTextPosition(SwingConstants.CENTER);
		BattleBackground.add(BattleActionLabel);
	}
	
	public void SetupInternalFrame() {
		this.setSize(BattleSceneWidth, BattleSceneHeight);
		this.setClosable(false);
		this.setIconifiable(true);
		this.setMaximizable(false);
		BattleBackground = new BackgroundRenderer();
		BattleBackground.setSize(BattleSceneWidth, BattleSceneHeight);
		BattleBackground.setLayout(null);
		this.add(BattleBackground);
	}
	
	public void ArrangeMonstersPositioning() {
		for(int i = 0; i < GroupB.length; i++) {
			Point Position = new Point((int)(BattleSceneWidth * 0.5f), (int)(BattleSceneHeight * 0.4f - Main.random.nextFloat() * BattleSceneHeight * 0.2f));
			int XPosMod = this.getWidth() / (GroupB.length + 2);
			Position.x += -XPosMod * (int)(GroupB.length * 0.5f) + XPosMod * i - (int)(GroupB[i].getWidth() * 0.5f);
			Position.y -= GroupB[i].getHeight();
			GroupB[i].setLocation(Position);
			BattleBackground.add(GroupB[i]);
		}
		int StartY = BattleSceneHeight - (40 + 34+32);
		if(GroupA.length >= 3)
			StartY -= 40;
		for(int i = 0; i < GroupA.length; i++) {
			Point Position = new Point((i % 3) * (BattleSceneWidth / 3), StartY + (int)(i * 0.34f) * 40);
			GroupA[i].setLocation(Position);
			BattleBackground.add(GroupA[i]);
		}
	}
	
	public void Update() {
		if(Delay > 0)
		{
			Delay--;
			return;
		}
		BattleCharacter character = TurnOrder[CurrentCharacter];
		if(character.character.HP <= 0)
		{
			CurrentCharacter++;
			if(CurrentCharacter >= TurnOrder.length)
				CurrentCharacter = 0;
			return;
		}
		List<Character> Targets = new ArrayList<Character>();
		//List<Character> Allies = new ArrayList<Character>(); //Useless for now
		if(character.IsGroupB) {
			for(CharacterInfo target: GroupA)
			{
				if(target.character.character.HP > 0)
					Targets.add(target.character.character);
			}
		}
		else
		{
			for(BattleOponentPanel target: GroupB)
			{
				if(target.character.character.HP > 0)
					Targets.add(target.character.character);
			}
		}
		if(Targets.size() == 0)
		{
			if(character.IsGroupB)
			{
				ShowBattleMessage("Defeated...");
			}
			else
			{
				ShowBattleMessage("<html>Victory!</html>");
			}
			RewardMessage = true;
		}
		else
		{
			Character Target = Targets.get(Main.random.nextInt(Targets.size()));
			Attack(character.character, Target);
			UpdateBattlersState();
			CurrentCharacter++;
			if(CurrentCharacter >= TurnOrder.length)
				CurrentCharacter = 0;
		}
	}
	
	public void Attack(Character Attacker, Character Target)
	{
		int DodgeRateCalculation = Attacker.GetAccuracy() - 5 + Main.random.nextInt(5) - Target.GetEvasion();
		if(-Main.random.nextInt(10) < DodgeRateCalculation) {
			ShowBattleMessage("<html>"+Target.Name + " dodged " + Attacker.Name + " attack!</html>");
		}else {
			int Damage = (int)(Attacker.GetAttack() - Target.GetDefense() * 0.5f);
			if(Damage < 1)
				Damage = 1;
			Target.HP -= Damage;
			ShowBattleMessage("<html>"+Attacker.Name + " attacked " + Target.Name + "!<br>    "+Damage+" damage inflicted!</html>");
		}
	}
	
	@SuppressWarnings("deprecation")
	public void UpdateBattlersState()
	{
		for(CharacterInfo character: GroupA) {
			character.UpdateCharacter();
		}
		for(BattleOponentPanel character: GroupB) {
			character.show(character.character.character.HP > 0);
		}
	}
	
	public class BattleCharacter
	{
		public Character character;
		public Image CharacterImage;
		public boolean IsGroupB;
		
		public BattleCharacter(Character character, boolean GroupB) {
			IsGroupB = GroupB;
			this.character = character;
			if(GroupB && character instanceof Monster)
			{
				CharacterImage = character.GetCharacterSprite(false, new Color(1f,1f,1f,0));
			}
			else
			{
				CharacterImage = character.GetCharacterFace();
			}
		}		
	}
	
	public class CharacterInfo extends JPanel
	{
		private static final long serialVersionUID = 2508578813896785880L;
		public BattleCharacter character;
		public JLabel NameLabel, HealthLabel;
		public PortraitDrawPanel PortraitPositionPanel;
		
		public CharacterInfo(Character character) {
			this.setLayout(null);
			this.character = new BattleCharacter(character, false);
			this.setBorder(BorderFactory.createLineBorder(Color.black));
			this.setSize(BattleSceneWidth / 3, 40);
			PortraitPositionPanel= new PortraitDrawPanel();
			PortraitPositionPanel.setLocation(2, 4);
			PortraitPositionPanel.setSize(32,32);
			PortraitPositionPanel.setBorder(BorderFactory.createLineBorder(Color.black));
			NameLabel = ObjectCreator.CreateLabel(character.Name, new Point(36, 0), 80);
			HealthLabel = ObjectCreator.CreateLabel(character.HP + "", new Point(36, 20), 80);
			HealthLabel.setForeground(Color.red);
			this.add(PortraitPositionPanel);
			this.add(NameLabel);
			this.add(HealthLabel);
		}
		
		public void UpdateCharacter() {
			HealthLabel.setText(character.character.HP+"");
			PortraitPositionPanel.repaint();
		}
		
		public class PortraitDrawPanel extends JPanel {
			
			private static final long serialVersionUID = 6477742540163594605L;

			@Override
			protected void paintComponent(Graphics graphics) {
				if(character.character.HP <= 0)
				{
					graphics.drawImage(GameDesktop.DeadAvatar, 0, 0, null);
				}else {
					graphics.drawImage(character.CharacterImage, 0, 0, null);
				}
			}
		}
	}
	
	public class BattleOponentPanel extends JPanel{
		private static final long serialVersionUID = -5069528229507698905L;
		
		public BattleCharacter character;
		
		public BattleOponentPanel(Character character)
		{
			this.character = new BattleCharacter(character, true);
			this.setSize(this.character.CharacterImage.getWidth(null), this.character.CharacterImage.getHeight(null));
			this.setBackground(new Color(1f,1f,1f,0));
			//this.setBorder(BorderFactory.createLineBorder(Color.black));
		}

		@Override
		protected void paintComponent(Graphics graphics) {
			graphics.drawImage(character.CharacterImage, 0, 0, null);
		}
	}
	
	public class BackgroundRenderer extends JPanel{
		private static final long serialVersionUID = -2435162487007162059L;

		@Override
		protected void paintComponent(Graphics graphics) {
			graphics.setColor(Color.green);
			graphics.fillRect(0, 0, this.getWidth(), this.getHeight());
		}
	}
	
	public class RewardWindow extends JPanel{
		private static final long serialVersionUID = -4787458076611598080L;
		public int ExpReward = 0;
		public Item[] ItemRewards = new Item[0];
	}

	public class AttackButtonAction implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			JOptionPane.showInternalOptionDialog((JButton)e.getSource(), "Mes", "Title", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, new String[] {""},0);
		}
	}
	
	public class LootInfo extends JPanel
	{
		private static final long serialVersionUID = -4013553052965395388L;
		public JPanel ItemIcon;
		public JLabel ItemName;
		
		public LootInfo(int ItemID, int Stack) {
			setSize(160, 20);
			ItemIcon = new JPanel();
			ItemIcon.setLocation(4, 4);
			ItemIcon.setSize(32, 32);
			ItemName = ObjectCreator.CreateLabel("", new Point(40, 6), 120);
			if(ItemID > 0) {
				
			}
		}
	}
}
