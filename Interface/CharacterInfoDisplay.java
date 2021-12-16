package TestProject.Interface;
import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.*;
import TestProject.Character;

import TestProject.ObjectCreator;
import TestProject.SkillProgress;

public class CharacterInfoDisplay extends JPanel {
	private static final long serialVersionUID = 7224526464833758383L;
	private Character character;
	private JLabel NameLbl, RaceLbl, LevelLbl, ExpLbl, HealthLbl, StatusRow1, StatusRow2, StatusRow3;
	private PortraitDrawer PortraitPanel;
	private final int InfoDisplayWidth = 280;
	private int InfoPanelDisplayHeight = 0;
	
	public CharacterInfoDisplay(Character character, BufferedImage Portrait, boolean ShowBorder){
		this.setLayout(null);
		this.character = character;
		Point ElementPosition = new Point(6, 12);
		if(ShowBorder)
			this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black, 1, true), character.charType.toString()));
		else
			ElementPosition.y = 6;
		final int PortraitPosition = 70;
		if(Portrait != null) {
			PortraitPanel = new PortraitDrawer();
			PortraitPanel.setLocation(PortraitPosition / 2 - 17, 12);
			ElementPosition.y += 40;
			this.add(PortraitPanel);
			ChangePortrait(Portrait);
		}else
		{
			PortraitPanel = null;
		}
		NameLbl = ObjectCreator.CreateLabel("[" + character.Name + "] " + (character.Male ? "♂" : "♀"), ElementPosition, 120);
		this.add(NameLbl);
		ElementPosition.y += 22;
		RaceLbl = ObjectCreator.CreateLabel("Race [" + character.Race.Name + "]", ElementPosition, 120);
		this.add(RaceLbl);
		ElementPosition.y += 22;
		HealthLbl = ObjectCreator.CreateLabel("HP", ElementPosition, 120);
		HealthLbl.setForeground(Color.red);
		this.add(HealthLbl);
		ElementPosition.y += 22;
		LevelLbl = ObjectCreator.CreateLabel("Level [" + character.Level + "]", ElementPosition, 120);
		this.add(LevelLbl);
		ElementPosition.y += 22;
		ExpLbl = ObjectCreator.CreateLabel("Exp [" + character.Exp + "/" + character.MaxExp + "]", ElementPosition, 120);
		this.add(ExpLbl);
		ElementPosition.y += 22;
		StatusRow1 = ObjectCreator.CreateLabel("STR and INT", ElementPosition, 120);
		this.add(StatusRow1);
		ElementPosition.y += 22;
		StatusRow2 = ObjectCreator.CreateLabel("AGI and DEX", ElementPosition, 120);
		this.add(StatusRow2);
		ElementPosition.y += 22;
		StatusRow3 = ObjectCreator.CreateLabel("VIT and LUK", ElementPosition, 120);
		this.add(StatusRow3);
		ElementPosition.y += 22;
		this.setSize(InfoDisplayWidth, InfoPanelDisplayHeight = (ElementPosition.y + (ShowBorder ? 6 : 0) + (PortraitPanel != null ? 40 : 0)));
		UpdateStatus();
		UpdateHealth();
		CreateSkillList();
	}
	
	public void CreateSkillList() {
		Point ElementPosition = new Point(140, 0);
		JPanel p = new JPanel();
		p.setLocation(ElementPosition);
		p.setSize(InfoDisplayWidth / 2, InfoPanelDisplayHeight);
		p.setBackground(Color.gray);
		this.add(p);
		ElementPosition.x += 6;
		for(SkillProgress skill: character.Skills)
		{
			JLabel lbl = ObjectCreator.CreateLabel(skill.Name() + " Lv:" + skill.Level + "\nExp: " + skill.Exp + "/" + skill.GetMaxExp(), ElementPosition, InfoDisplayWidth / 2);
			p.add(lbl);
			ElementPosition.y += 36;
		}
	}
	
	public void ChangePortrait(BufferedImage image)
	{
		if(PortraitPanel != null)
		{
			PortraitPanel.ReplacePortrait(image);	
		}
	}
	
	public void UpdateStatus() {
		StatusRow1.setText("STR: " + character.GetSTR() + "  INT: " + character.GetINT());
		StatusRow2.setText("AGI: " + character.GetAGI() + "  DEX: " + character.GetDEX());
		StatusRow3.setText("VIT: " + character.GetVIT() + "  LUK: " + character.GetLUK());
	}
	
	public void UpdateHealth() {
		HealthLbl.setText("HP [" + character.HP + "/" + character.GetMHP() + "]");
	}
}
