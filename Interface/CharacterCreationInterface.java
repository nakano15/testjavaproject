package TestProject.Interface;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import TestProject.ObjectCreator;
import TestProject.Races;
import TestProject.Character;

public class CharacterCreationInterface extends JInternalFrame {
	private static final long serialVersionUID = -7352535066059878778L;
	public static CharacterCreationInterface CCI;
	public JTextField NameBox;
	@SuppressWarnings("rawtypes")
	public JComboBox RaceBox;
	public PortraitDrawer Portrait;
	public JLabel MHPlbl, STRINTlbl, AGIDEXlbl, VITLUKlbl, HairStylelbl;
	public static Character character;
	public JButton CreateButton, HairChangeButtonLeft, HairChangeButtonRight;
	public JRadioButton HairButtonRadio, SkinButtonRadio, EyeButtonRadio, MaleRadioButton, FemaleRadioButton;
	public JSlider RedSlider, GreenSlider, BlueSlider, ToneSlider;
	public static boolean Finished = false;
	public Action WhenCharacterCreationIsFinished;
	private static byte ColorBeingChanged = 0;
	private static boolean UpdatingHudElements = false;
	private final byte Hair_Color = 0, Skin_Color = 1, Eye_Color = 2;
	private static Color SavedToneColor;
	private static float ToneStrength = 1f;
	
	public CharacterCreationInterface(Action ResultAction) {
		final int ElementXMinValue = 6;
		if(ResultAction == null)
		{
			WhenCharacterCreationIsFinished = new DefaultCharacterCreationEndAction();
		}else {
			WhenCharacterCreationIsFinished = ResultAction;
		}
		CCI = this;
		character = new Character();
		super.setTitle("Character Creator");
		this.setLayout(null);
		Point ElementPosition = new Point(ElementXMinValue, 6);
		Portrait = new PortraitDrawer(2);
		Portrait.setLocation(100 - (16) * 2 - 1, ElementPosition.y); // - 17
		this.add(Portrait);
		ElementPosition.y += 64; //40
		this.add(ObjectCreator.CreateLabel("Input Name", ElementPosition, 188));
		ElementPosition.y += 20;
		NameBox = ObjectCreator.CreateTextBox(ElementPosition, 188);
		this.add(NameBox);
		ElementPosition.y += 30;
		this.add(ObjectCreator.CreateLabel("Select Character Race", ElementPosition, 188));
		ElementPosition.y += 20;		
		RaceBox = ObjectCreator.CreateComboBox(ElementPosition, Races.StarterRaces.keySet().toArray(String[]::new), 188);
		RaceBox.addActionListener(new RaceBoxFunction());
		this.add(RaceBox);
		ElementPosition.y += 30;
		this.add(ObjectCreator.CreateLabel("Select Character Gender", ElementPosition, 188));
		ElementPosition.y += 20;
		//
		ButtonGroup GenderGroup = new ButtonGroup();
		MaleRadioButton = ObjectCreator.CreateRadioButton("Male", ElementPosition, 96);
		MaleRadioButton.addItemListener(new GenderRadioButtonFunction(true));
		GenderGroup.add(MaleRadioButton);
		this.add(MaleRadioButton);
		ElementPosition.x = 100;
		FemaleRadioButton = ObjectCreator.CreateRadioButton("Female", ElementPosition, 96);
		FemaleRadioButton.addItemListener(new GenderRadioButtonFunction(false));
		GenderGroup.add(FemaleRadioButton);
		this.add(FemaleRadioButton);
		ElementPosition.x = ElementXMinValue;
		ElementPosition.y += 30;
		JPanel panel = new JPanel();
		panel.setLocation(ElementPosition);
		panel.setBorder(BorderFactory.createLineBorder(Color.black));
		panel.setSize(188, 40);
		panel.setLayout(new FlowLayout());
		this.add(panel);
		ButtonGroup group = new ButtonGroup();
		HairButtonRadio = ObjectCreator.CreateRadioButton("Hair", ElementPosition, 90);
		HairButtonRadio.addItemListener(new ColorChoiceRadioButtonFunction(Hair_Color));
		UpdatingHudElements = true;
		HairButtonRadio.setSelected(true);
		UpdatingHudElements = false;
		group.add(HairButtonRadio);
		panel.add(HairButtonRadio);
		EyeButtonRadio = ObjectCreator.CreateRadioButton("Eyes", ElementPosition, 90);
		EyeButtonRadio.addItemListener(new ColorChoiceRadioButtonFunction(Eye_Color));
		group.add(EyeButtonRadio);
		panel.add(EyeButtonRadio);		
		SkinButtonRadio = ObjectCreator.CreateRadioButton("Skin", ElementPosition, 90);
		SkinButtonRadio.addItemListener(new ColorChoiceRadioButtonFunction(Skin_Color));
		panel.add(SkinButtonRadio);
		group.add(SkinButtonRadio);
		ElementPosition.y += 40;
		//
		this.add(HairStylelbl = ObjectCreator.CreateLabel("Hair Style", ElementPosition, 188));
		ElementPosition.x +=94;
		HairChangeButtonLeft = ObjectCreator.CreateButton("<<", ElementPosition, 50);
		HairChangeButtonLeft.addActionListener(new HairStyleChangeButtonFunction(true));
		HairChangeButtonLeft.setHorizontalTextPosition(SwingConstants.CENTER);
		this.add(HairChangeButtonLeft);
		ElementPosition.x +=50;
		HairChangeButtonRight = ObjectCreator.CreateButton(">>", ElementPosition, 50);
		HairChangeButtonRight.addActionListener(new HairStyleChangeButtonFunction(false));
		HairChangeButtonRight.setHorizontalTextPosition(SwingConstants.CENTER);
		this.add(HairChangeButtonRight);
		ElementPosition.x = ElementXMinValue;
		ElementPosition.y += 30;		
		//
		this.add(ObjectCreator.CreateLabel("Red: ", ElementPosition, 100));
		ElementPosition.x += 80;
		RedSlider = ObjectCreator.CreateSlider(ElementPosition, 0, 255, 100);
		RedSlider.setForeground(Color.red);
		RedSlider.addChangeListener(new ColorChangeSliderFunction((byte) 0));
		this.add(RedSlider);
		ElementPosition.x = ElementXMinValue;
		ElementPosition.y += 20;
		this.add(ObjectCreator.CreateLabel("Green: ", ElementPosition, 100));
		ElementPosition.x += 80;
		GreenSlider = ObjectCreator.CreateSlider(ElementPosition, 0, 255, 100);
		GreenSlider.setForeground(Color.green);
		GreenSlider.addChangeListener(new ColorChangeSliderFunction((byte) 1));
		this.add(GreenSlider);
		ElementPosition.x = ElementXMinValue;
		ElementPosition.y += 20;
		this.add(ObjectCreator.CreateLabel("Blue: ", ElementPosition, 100));
		ElementPosition.x += 80;
		BlueSlider = ObjectCreator.CreateSlider(ElementPosition, 0, 255, 100);
		BlueSlider.setForeground(Color.blue);
		BlueSlider.addChangeListener(new ColorChangeSliderFunction((byte) 2));
		this.add(BlueSlider);
		ElementPosition.x = ElementXMinValue;
		ElementPosition.y += 30;
		this.add(ObjectCreator.CreateLabel("Tone: ", ElementPosition, 100));
		ElementPosition.x += 80;
		ToneSlider = ObjectCreator.CreateSlider(ElementPosition, 0, 255, 100);
		ToneSlider.setForeground(Color.gray);
		ToneSlider.addChangeListener(new ColorChangeSliderFunction((byte)3));
		this.add(ToneSlider);
		ElementPosition.x = ElementXMinValue;
		ElementPosition.y += 30;
		//	
		MHPlbl = ObjectCreator.CreateLabel("MHP", ElementPosition, 188);
		this.add(MHPlbl);
		ElementPosition.y += 20;
		STRINTlbl = ObjectCreator.CreateLabel("Str Int", ElementPosition, 188);
		STRINTlbl.setToolTipText("Strength and Intelect values. Growth in parenthesis.");
		this.add(STRINTlbl);
		ElementPosition.y += 20;
		AGIDEXlbl = ObjectCreator.CreateLabel("Agi Dex", ElementPosition, 188);
		AGIDEXlbl.setToolTipText("Agility and Dexterity values. Growth in parenthesis.");
		this.add(AGIDEXlbl);
		ElementPosition.y += 20;
		VITLUKlbl = ObjectCreator.CreateLabel("Vit Luk", ElementPosition, 188);
		VITLUKlbl.setToolTipText("Vitality and Luck values. Growth in parenthesis.");
		this.add(VITLUKlbl);
		ElementPosition.y += 30;
		CreateButton = ObjectCreator.CreateButton("Create", ElementPosition, 188);
		CreateButton.addActionListener(new CreateButtonFunction());
		this.add(CreateButton);
		ElementPosition.y += 20;
		setSize(200+8, ElementPosition.y + 40);
		UpdateRace();
		setVisible(true);
	}
	
	public void UpdateToneSlider() {
		switch(ColorBeingChanged)
		{
		case 0:
			SavedToneColor = new Color(character.HairColor.getRGB());
			break;
		case 1:
			SavedToneColor = new Color(character.SkinColor.getRGB());
			break;
		}
		UpdatingHudElements = true;
		float Until255 = 1f;
		int Highest = 0;
		if(SavedToneColor.getRed() > SavedToneColor.getGreen() && SavedToneColor.getRed() > SavedToneColor.getBlue())
		{
			Highest = SavedToneColor.getRed();
			Until255 = 255f / Highest;
		}
		else if(SavedToneColor.getGreen() > SavedToneColor.getBlue())
		{
			Highest = SavedToneColor.getGreen();
			Until255 = 255f / Highest;
		}
		else
		{
			Highest = SavedToneColor.getBlue();
			Until255 = 255f / Highest;
		}
		SavedToneColor = 
				new Color((int)(SavedToneColor.getRed() * Until255),
				(int)(SavedToneColor.getGreen() * Until255),
				(int)(SavedToneColor.getBlue() * Until255));
		ToneStrength = (float)Highest / 255;
		ToneSlider.setValue((int)(ToneStrength * 255));
		UpdatingHudElements = false;
	}
	
	public void UpdateHairLabel() {
		switch(ColorBeingChanged)
		{
		case 0:
			HairStylelbl.setText("Hair Style ["+(character.Hair + 1) + "/" + character.Race.MaxHairs +"]");
			break;
		case 1:
			HairStylelbl.setText("Head Style ["+(character.Head + 1) + "/" + character.Race.MaxHeads +"]");
			break;
		case 2:
			HairStylelbl.setText("Eye Style ["+(character.Eye + 1) + "/" + character.Race.MaxEyes +"]");
			break;
		}
	}
	
	public void UpdateRace() {
		if(character.Race == null)
		{
			String[] Keys = Races.StarterRaces.keySet().toArray(String[]::new);
			character.ChangeRace(Races.StarterRaces.get(Keys[new Random().nextInt(Keys.length)]));
		}else
		{
			String[] Keys = Races.StarterRaces.keySet().toArray(String[]::new);
			character.ChangeRace(Races.StarterRaces.get(Keys[RaceBox.getSelectedIndex()]));
		}
		character.Randomize();
		character.Name = "";
		character.SetLevel(1);
		MHPlbl.setText("MHP: " + character.GetMHP());
		STRINTlbl.setText("STR: " + character.GetSTR() + " ("+Math.round(character.Race.GrowthSTR * 1000) * 0.1f+"%)" + "  INT: " + character.GetINT() + " ("+Math.round(character.Race.GrowthINT * 1000) * 0.1f+"%)");
		AGIDEXlbl.setText("AGI: " + character.GetAGI() + " ("+Math.round(character.Race.GrowthAGI * 1000) * 0.1f+"%)" + "  DEX: " + character.GetDEX() + " ("+Math.round(character.Race.GrowthDEX * 1000) * 0.1f+"%)");
		VITLUKlbl.setText("VIT: " + character.GetVIT()  + " ("+Math.round(character.Race.GrowthVIT * 1000) * 0.1f+"%)"+ "  LUK: " + character.GetLUK() + " ("+Math.round(character.Race.GrowthLUK * 1000) * 0.1f+"%)");
		ChangeColorToBeAltered(ColorBeingChanged);
		UpdatingHudElements = true;
		if(character.Male)
			MaleRadioButton.setSelected(true);
		else
			FemaleRadioButton.setSelected(true);
		UpdatingHudElements = false;
		//ChangeGender(character.Male);
		UpdatePortrait();
		UpdateHairLabel();
	}
	
	public void ChangeGender(boolean Male) {
		if(UpdatingHudElements)
			return;
		UpdatingHudElements = true;
		character.Male = Male;
		UpdatingHudElements = false;
		UpdatePortrait();
	}
	
	public void ChangeColorToBeAltered(byte NewColor) {
		if(UpdatingHudElements) return;
		UpdatingHudElements = true;
		ColorBeingChanged = NewColor;
		Color color = Color.white;
		switch(NewColor)
		{
		case Hair_Color:
			color = character.HairColor;
			RedSlider.setValue(color.getRed());
			GreenSlider.setValue(color.getGreen());
			BlueSlider.setValue(color.getBlue());
			break;
		case Skin_Color:
			color = character.SkinColor;
			RedSlider.setValue(color.getRed());
			GreenSlider.setValue(color.getGreen());
			BlueSlider.setValue(color.getBlue());
			break;
		case Eye_Color:
			color = character.EyeColor;
			RedSlider.setValue(color.getRed());
			GreenSlider.setValue(color.getGreen());
			BlueSlider.setValue(color.getBlue());			
			break;
		}
		UpdateHairLabel();
		UpdateToneSlider();
		UpdatingHudElements = false;
	}
	
	public void UpdatePortrait() {
		Portrait.ReplacePortrait(character.GetCharacterFace());
	}
	
	private class GenderRadioButtonFunction implements ItemListener{
		private boolean IsMaleButton = false;
		
		public GenderRadioButtonFunction(boolean MaleButton) {
			IsMaleButton = MaleButton;
		}

		@Override
		public void itemStateChanged(ItemEvent e) {
			ChangeGender(IsMaleButton);
		}
	}
	
	private class ColorChoiceRadioButtonFunction implements ItemListener{
		private byte MyColorChoice = 0;		
		
		public ColorChoiceRadioButtonFunction(byte Color) {
			MyColorChoice = Color;
		}
		
		@Override
		public void itemStateChanged(ItemEvent e) {
			// TODO Auto-generated method stub
			CCI.ChangeColorToBeAltered(MyColorChoice);
		}		
	}
	
	private class ColorChangeSliderFunction implements ChangeListener{
		private byte ColorIndex = 0;
		
		public ColorChangeSliderFunction(byte ColorID) {
			ColorIndex = ColorID;
		}

		@Override
		public void stateChanged(ChangeEvent e) {
			// TODO Auto-generated method stub
			JSlider slider = (JSlider)e.getSource();
			if(!CCI.UpdatingHudElements)
			{
				switch(ColorBeingChanged) {
				case Hair_Color:
					switch(ColorIndex)
					{
					case 0:
						character.HairColor = new Color(slider.getValue(), character.HairColor.getGreen(), character.HairColor.getBlue());
						UpdateToneSlider();
						break;
					case 1:
						character.HairColor = new Color(character.HairColor.getRed(), slider.getValue(), character.HairColor.getBlue());
						UpdateToneSlider();
						break;
					case 2:
						character.HairColor = new Color(character.HairColor.getRed(), character.HairColor.getGreen(), slider.getValue());
						UpdateToneSlider();
						break;
					case 3:
						float Tone = (float)slider.getValue() / 255;
						character.HairColor = new Color((int)(SavedToneColor.getRed() * Tone),
								(int)(SavedToneColor.getGreen() * Tone),
								(int)(SavedToneColor.getBlue() * Tone));
						CCI.UpdatingHudElements = true;
						RedSlider.setValue(character.HairColor.getRed());
						GreenSlider.setValue(character.HairColor.getGreen());
						BlueSlider.setValue(character.HairColor.getBlue());
						CCI.UpdatingHudElements = false;
						break;
					}
					break;
				case Skin_Color:
					switch(ColorIndex)
					{
					case 0:
						character.SkinColor = new Color(slider.getValue(), character.SkinColor.getGreen(), character.SkinColor.getBlue());
						UpdateToneSlider();
						break;
					case 1:
						character.SkinColor = new Color(character.SkinColor.getRed(), slider.getValue(), character.SkinColor.getBlue());
						UpdateToneSlider();
						break;
					case 2:
						character.SkinColor = new Color(character.SkinColor.getRed(), character.SkinColor.getGreen(), slider.getValue());
						UpdateToneSlider();
						break;
					case 3:
						float Tone = (float)slider.getValue() / 255;
						character.SkinColor = new Color((int)(SavedToneColor.getRed() * Tone),
								(int)(SavedToneColor.getGreen() * Tone),
								(int)(SavedToneColor.getBlue() * Tone));
						CCI.UpdatingHudElements = true;
						RedSlider.setValue(character.SkinColor.getRed());
						GreenSlider.setValue(character.SkinColor.getGreen());
						BlueSlider.setValue(character.SkinColor.getBlue());
						CCI.UpdatingHudElements = false;
						break;
					}
					break;
				case Eye_Color:
					switch(ColorIndex)
					{
					case 0:
						character.EyeColor = new Color(slider.getValue(), character.EyeColor.getGreen(), character.EyeColor.getBlue());
						UpdateToneSlider();
						break;
					case 1:
						character.EyeColor = new Color(character.EyeColor.getRed(), slider.getValue(), character.EyeColor.getBlue());
						UpdateToneSlider();
						break;
					case 2:
						character.EyeColor = new Color(character.EyeColor.getRed(), character.EyeColor.getGreen(), slider.getValue());
						UpdateToneSlider();
						break;
					case 3:
						float Tone = (float)slider.getValue() / 255;
						character.EyeColor = new Color((int)(SavedToneColor.getRed() * Tone),
								(int)(SavedToneColor.getGreen() * Tone),
								(int)(SavedToneColor.getBlue() * Tone));
						CCI.UpdatingHudElements = true;
						RedSlider.setValue(character.EyeColor.getRed());
						GreenSlider.setValue(character.EyeColor.getGreen());
						BlueSlider.setValue(character.EyeColor.getBlue());
						CCI.UpdatingHudElements = false;
						break;
					}
					break;
				}
				UpdatePortrait();
			}
		}
		
	}
	
	private class HairStyleChangeButtonFunction implements ActionListener{
		public boolean MovingLeft = false;
		
		public HairStyleChangeButtonFunction(boolean MovingLeft) {
			this.MovingLeft = MovingLeft;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			switch(ColorBeingChanged)
			{
				case 0:
				if(CCI.character.Race.MaxHairs > 0) {
					if(MovingLeft) {
						if(CCI.character.Hair == 0)
							CCI.character.Hair = (byte)(CCI.character.Race.MaxHairs -1);
						else
							CCI.character.Hair--;
					}else{
						if(CCI.character.Hair == CCI.character.Race.MaxHairs - 1)
							CCI.character.Hair = 0;
						else
							CCI.character.Hair++;
					}
				}
				break;
				case 1:
					if(CCI.character.Race.MaxHeads > 0) {
						if(MovingLeft) {
							if(CCI.character.Head == 0)
								CCI.character.Head = (byte)(CCI.character.Race.MaxHeads - 1);
							else
								CCI.character.Head--;
						}else
						{
							if(CCI.character.Head == CCI.character.Race.MaxHeads - 1)
								CCI.character.Head = 0;
							else
								CCI.character.Head++;
						}
					}
					break;
				case 2:
					if(CCI.character.Race.MaxEyes > 0) {
						if(MovingLeft) {
							if(CCI.character.Eye == 0)
								CCI.character.Eye = (byte)(CCI.character.Race.MaxEyes - 1);
							else
								CCI.character.Eye--;
						}else
						{
							if(CCI.character.Eye == CCI.character.Race.MaxEyes - 1)
								CCI.character.Eye = 0;
							else
								CCI.character.Eye++;
						}
					}
					break;
			}
			CCI.UpdateHairLabel();
			CCI.UpdatePortrait();
		}
	}
	
	private class CreateButtonFunction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(CCI.NameBox.getText().length() < 2)
			{
				JOptionPane.showMessageDialog(CCI, "That name is too short!");
			}else {
				character.Name = CCI.NameBox.getText();
				JOptionPane.showMessageDialog(CCI, "Character creation complete!");
				CCI.WhenCharacterCreationIsFinished.actionPerformed(new ActionEvent(CCI, 0, null));
				CCI.setVisible(false);
			}
		}
	}
	
	private class RaceBoxFunction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			CCI.UpdateRace();
		}
	}
	
	private class DefaultCharacterCreationEndAction extends AbstractAction{
		private static final long serialVersionUID = -4806015750935725191L;

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}		
	}
}
