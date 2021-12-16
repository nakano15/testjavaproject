package TestProject;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;

public class Races {
	public static Races Human;
	public static Map<String, Races> StarterRaces = new HashMap<String, Races>();
	
	public String Name = "Unknown";
	public int MHP = 0, STR = 0, AGI = 0, VIT = 0, 
			   INT = 0, DEX = 0, LUK = 0;
	public float GrowthSTR = 1f, GrowthAGI = 1f, GrowthVIT = 1f,
			     GrowthINT = 1f, GrowthDEX = 1f, GrowthLUK = 1f;
	public int BasicAttackDamage = 5, BasicDefense = 0, BasicAccuracy = 0, BasicEvasion = 0;
	public byte MaxHairs = 1, MaxHeads = 1, MaxEyes = 1;
	public BufferedImage HeadImage, HairImage, EyeImage, EyeWhiteImage;
	private boolean BufferedImageExists = false;
	public String[] NamesPieces = new String[0];
	public Color BasicSkinColor = Color.white, BasicHairColor = Color.white, BasicEyeColor = Color.blue;
	public boolean IsMonster = false;
	public Point AvatarCenter = new Point(16,16);
	public int[] Skills = new int[0];
	
	public static void Initialize() {
		Human = new Races();
		Human.Name = "Human";
		Human.MaxHairs = Human.MaxHeads = Human.MaxEyes = 9;
		Human.BasicSkinColor = new Color(255, 204, 156);
		Human.BasicHairColor = new Color(26, 20, 16);
		Human.MHP = 50;
		Human.SetAllStatusToSameValue(5);
		Human.LoadCharacterImages();
		Human.NamesPieces = new String[] {
				"A",
				"Be",
				"Ca",
				"La",
				"Re",
				"Ro",
				"Da",
				"Do",
				"Nis",
				"Lu",
				"Jo"
		};
		Human.Skills = new int[] {
				Skill.Skill_Combat, Skill.Skill_WoodCutting, Skill.Skill_Carpentry, Skill.Skill_Fishing, Skill.Skill_Cooking,
				Skill.Skill_Mining, Skill.Skill_Smithing, Skill.Skill_Questing, Skill.Skill_Hunting, Skill.Skill_Medicine	
		};
		AddStarterRace(Human);
	}
	
	public void SetAllStatusToSameValue(int Value)
	{
		STR = AGI = VIT = INT = DEX = LUK = Value;
	}
	
	private static void AddStarterRace(Races Race) {
		StarterRaces.put(Race.Name, Race);
	}
	
	public boolean CharacterImageExists() {
		return BufferedImageExists;
	}
	
	public void LoadCharacterImages()
	{
		String FileLocation = "";
		for(char c : Name.toCharArray())
		{
			if(c != ' ')
				FileLocation += c;
		}
		String TexturesLocation = "src/TestProject/Content/"+(IsMonster ? "Monster" : "Character")+"/" + FileLocation + "/";
		try
		{
			if(!IsMonster) {
			HeadImage = ImageIO.read(new File(TexturesLocation + "Head.png"));
			HairImage = ImageIO.read(new File(TexturesLocation + "Hair.png"));
			EyeImage = ImageIO.read(new File(TexturesLocation + "Eye.png"));
			EyeWhiteImage = ImageIO.read(new File(TexturesLocation + "EyeWhite.png"));
			}else {
				HeadImage = ImageIO.read(new File(TexturesLocation + "Body.png"));
			}
			BufferedImageExists = true;
		}
		catch (IOException e)
		{
			BufferedImageExists = false;
		}
	}
}
