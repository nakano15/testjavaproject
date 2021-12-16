package TestProject;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Character {
	public String Name = "";
	public Races Race = Races.Human;
	public int Level = 1;
	public int Exp = 0;
	public int MaxExp = 10;
	public int HP = 10, MHP = 0;
	public int STR = 0, AGI = 0, VIT = 0, 
			   INT = 0, DEX = 0, LUK = 0;
	public int StatusPoints = 0;
	public boolean Male = true;
	public CharacterType charType = CharacterType.Hero;
	public byte Hair = 0, Head = 0, Eye = 0;
	public Color SkinColor = Color.white, HairColor = Color.yellow, EyeColor = Color.blue;
	public List<SkillProgress> Skills = new ArrayList<SkillProgress>();
	
	public Character() {
		SkinColor = new Color(Race.BasicSkinColor.getRGB());
		HairColor = new Color(Race.BasicHairColor.getRGB());
	}
	
	public Character(Races Race) {
		ChangeRace(Race);
	}
	
	public void ChangeRace(Races Race) {
		Skills.clear();
		this.Race = Race;
		SkinColor = new Color(Race.BasicSkinColor.getRGB());
		HairColor = new Color(Race.BasicHairColor.getRGB());
		Randomize();
		for(int s: Race.Skills)
		{
			Skills.add(new SkillProgress(s));
		}
	}
	
	public void Randomize() {
		Random rand = Main.random;
		Name = "";
		Male = rand.nextFloat() < 0.5f;
		if(Race.NamesPieces.length > 0) {
			while(true) {
				float Counter = Name.length() - 4;
				if(Counter <= 0 || rand.nextFloat() < 1f - Counter / (Counter + 2)) {
					String Piece = Race.NamesPieces[rand.nextInt(Race.NamesPieces.length)];
					if(Name.length() > 0)
						Piece = Piece.toLowerCase();
					Name += Piece;
				}else {
					break;
				}
			}
		}else {
			for(int c = 0; c < 8; c++) {
				Name += (char)('a' + rand.nextInt('z' - 'a'));
			}
		}
		Hair = (byte)rand.nextInt(Race.MaxHairs);
		Eye = (byte)rand.nextInt(Race.MaxEyes);
		Head = (byte)rand.nextInt(Race.MaxHeads);
		HairColor = new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
		float SkinColorVariation = rand.nextFloat() * 0.7f + 0.2f;
		SkinColor = new Color(
				(int)(Race.BasicSkinColor.getRed() * SkinColorVariation), 
				(int)(Race.BasicSkinColor.getGreen() * SkinColorVariation), 
				(int)(Race.BasicSkinColor.getBlue() * SkinColorVariation));
		
	}
	
	public int GetMHP() {
		return MHP + Race.MHP;
	}
	
	public int GetSTR() {
		return STR + Race.STR;
	}
	
	public int GetAGI() {
		return AGI + Race.AGI;
	}
	
	public int GetVIT() {
		return VIT + Race.VIT;
	}
	
	public int GetINT() {
		return INT + Race.INT;
	}
	
	public int GetDEX() {
		return DEX + Race.DEX;
	}
	
	public int GetLUK() {
		return LUK + Race.LUK;
	}
	
	public int GetAttack() {
		return STR * 2 + Race.BasicAttackDamage;
	}
	
	public int GetDefense() {
		return VIT * 2 + Race.BasicDefense;
	}
	
	public int GetAccuracy() {
		return (int)(DEX * 0.2f) + Race.BasicAccuracy;
	}
	
	public int GetEvasion() {
		return (int)(AGI * 0.2f) + Race.BasicEvasion;
	}
	
	public int GetExpReward() {
		return (int)((GetMHP() * 0.16f + (GetSTR() + GetAGI() + GetVIT() + GetINT() + GetDEX() + GetLUK()) * 2) * Level * (1f + 0.04f));
	}
	
	public void SetLevel(int Level) {
		this.StatusPoints += Level - this.Level;
		if(this.StatusPoints < 0) {
			STR = AGI = VIT = INT = DEX = LUK = 0;
			StatusPoints = Level - 1;
		}
		this.Level = Level;
		DoCharacterGrowth();
		this.HP = this.GetMHP();
		this.MaxExp = MaxExpHandler.GetMaxExpForLevel(Level);
	}
	
	public void UpdateStatus() {
		
	}

	
	public void DoCharacterGrowth() {
		float MaxGrowthValue = Race.GrowthSTR + Race.GrowthAGI + Race.GrowthVIT + Race.GrowthINT + Race.GrowthDEX + Race.GrowthLUK;
		Random MyGrowthRand = new Random();
		while(StatusPoints > 0) {
			float PickedGrowthValue = MyGrowthRand.nextFloat() * MaxGrowthValue;
			float GrowthStack = 0;
			for(int i = 0; i < 6; i++) {
				switch(i) {
				case 0:
					if(PickedGrowthValue >= GrowthStack && PickedGrowthValue < GrowthStack + Race.GrowthSTR) {
						STR++;
						StatusPoints--;
						break;
					}
					GrowthStack += Race.GrowthSTR;
					break;
				case 1:
					if(PickedGrowthValue >= GrowthStack && PickedGrowthValue < GrowthStack + Race.GrowthAGI) {
						AGI++;
						StatusPoints--;
						break;
					}
					GrowthStack += Race.GrowthAGI;
					break;
				case 2:
					if(PickedGrowthValue >= GrowthStack && PickedGrowthValue < GrowthStack + Race.GrowthVIT) {
						VIT++;
						StatusPoints--;
						break;
					}
					GrowthStack += Race.GrowthVIT;
					break;
				case 3:
					if(PickedGrowthValue >= GrowthStack && PickedGrowthValue < GrowthStack + Race.GrowthINT) {
						INT++;
						StatusPoints--;
						break;
					}
					GrowthStack += Race.GrowthINT;
					break;
				case 4:
					if(PickedGrowthValue >= GrowthStack && PickedGrowthValue < GrowthStack + Race.GrowthDEX) {
						DEX++;
						StatusPoints--;
						break;
					}
					GrowthStack += Race.GrowthDEX;
					break;
				case 5:
					if(PickedGrowthValue >= GrowthStack && PickedGrowthValue < GrowthStack + Race.GrowthLUK) {
						LUK++;
						StatusPoints--;
						break;
					}
					GrowthStack += Race.GrowthLUK;
					break;
				}
			}
		}
	}

	public BufferedImage GetCharacterFace() {
		return GetCharacterSprite(true);
	}
	
	public BufferedImage GetCharacterSprite(boolean Portrait)
	{
		return GetCharacterSprite(Portrait, Color.white);
	}
	
	public BufferedImage GetCharacterSprite(boolean Portrait, Color BackgroundColor) {
		BufferedImage finalimage;
		int ImageWidth = 32, ImageHeight = 32;
		if(!Race.IsMonster || Portrait)
		{
			finalimage = new BufferedImage(32, 32, BufferedImage.TRANSLUCENT);
		}
		else
		{
			ImageWidth = Race.HeadImage.getWidth();
			ImageHeight = Race.HeadImage.getHeight();
			finalimage = new BufferedImage(ImageWidth, ImageHeight, BufferedImage.TRANSLUCENT);
		}
		Graphics2D graphic = finalimage.createGraphics();
		graphic.setColor(BackgroundColor);
		graphic.fillRect(0, 0, ImageWidth, ImageHeight);
		if(!Race.IsMonster)
		{
			if(Race.HeadImage != null)
			{
				graphic.setColor(SkinColor);
				graphic.drawImage(GetColoredImage(Race.HeadImage, SkinColor, new Rectangle(32 * Head, 0, 32, 32)), 0, 0, 32, 32, null);
			}
			if(Race.EyeWhiteImage != null)
			{
				graphic.setColor(Color.white);
				graphic.drawImage(GetColoredImage(Race.EyeWhiteImage, Color.white, new Rectangle(32 * Eye, 0, 32, 32)), 0, 0, 32, 32, null);
			}
			if(Race.EyeImage != null)
			{
				graphic.setColor(EyeColor);
				graphic.drawImage(GetColoredImage(Race.EyeImage, EyeColor, new Rectangle(32 * Eye, 0, 32, 32)), 0, 0, 32, 32, null);
			}
			if(Race.HairImage != null)
			{
				graphic.setColor(HairColor);
				graphic.drawImage(GetColoredImage(Race.HairImage, HairColor, new Rectangle(32 * Hair, 32 * (Male ? 0 : 1), 32, 32)), 0, 0, 32, 32, null);
			}
		}
		else
		{
			if(Race.HeadImage != null)
			{
				graphic.setColor(SkinColor);
				Rectangle rect = new Rectangle(0,0,ImageWidth, ImageHeight);
				if(Portrait)
				{
					rect.x = Race.AvatarCenter.x - 16;
					rect.width = 32;
					rect.y = Race.AvatarCenter.y - 16;
					rect.height = 32;
				}
				graphic.drawImage(GetColoredImage(Race.HeadImage, SkinColor, rect), 0, 0, rect.width, rect.height, null);
			}
		}
		graphic.dispose();
		return finalimage;
	}

	private BufferedImage GetColoredImage(BufferedImage original, Color color, Rectangle Rect) {
		if(Rect == null)
		{
			Rect = new Rectangle(0, 0, original.getWidth(), original.getHeight());
		}
		BufferedImage newImage = new BufferedImage(Rect.width, Rect.height, BufferedImage.TRANSLUCENT);
		final float colorDepth = 1f / 255;
		for(int x = 0; x < Rect.width; x++) {
			for(int y = 0; y < Rect.width; y++) {
				Color pixelColor = new Color(original.getRGB(Rect.x + x, Rect.y + y), true);
				int red = (int)(pixelColor.getRed() * (float)color.getRed() * colorDepth),
					green = (int)(pixelColor.getGreen() * (float)color.getGreen() * colorDepth),
					blue = (int)(pixelColor.getBlue() * (float)color.getBlue() * colorDepth),
					alpha = (int)pixelColor.getAlpha();
				int rgba = (alpha << 24) | (red << 16) | (green << 8) | blue;
				//if(alpha > 0)
				//	System.out.println("["+x + "-"+y+"] Original: " + pixelColor.getRed() + "-" + pixelColor.getGreen() + "-" + pixelColor.getBlue() + "-" + pixelColor.getAlpha() + " Result:" + red + "-" + green + "-" + blue + "-" + alpha + ".");
				newImage.setRGB(x, y, rgba);
			}
		}
		return newImage;
	}
	
	public enum CharacterType
	{
		Hero,
		Creature,
		Villain,
		Citizen
	}
}
