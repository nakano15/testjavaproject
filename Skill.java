package TestProject;

import java.util.List;
import java.util.ArrayList;

public class Skill {
	public String Name = "";
	public static final int MaxLevel = 100;
	public static final int Skill_Combat = 0, Skill_WoodCutting = 1, Skill_Carpentry = 2, Skill_Fishing = 3, Skill_Cooking = 4,
			Skill_Mining = 5, Skill_Smithing = 6, Skill_Questing = 7, Skill_Hunting = 8, Skill_Medicine = 9;
	private static Skill Combat, WoodCutting, Carpentry, Fishing, Cooking, Mining, Smithing, Questing, Hunting, Medicine;
	
	public static Skill GetSkill(int ID)
	{
		switch(ID) {
		case Skill_Combat:
			return Combat;
		case Skill_WoodCutting:
			return WoodCutting;
		case Skill_Carpentry:
			return Carpentry;
		case Skill_Fishing:
			return Fishing;
		case Skill_Cooking:
			return Cooking;
		case Skill_Mining:
			return Mining;
		case Skill_Smithing:
			return Smithing;
		case Skill_Questing:
			return Questing;
		case Skill_Hunting:
			return Hunting;
		case Skill_Medicine:
			return Medicine;
		}
		return new Skill();
	}
	
	public static void InitializeSkills() {
		Skill s = new Skill();
		s.Name = "Combat";
		Combat = s;
		s = new Skill();
		s.Name = "Wood Cutting";
		WoodCutting = s;
		s = new Skill();
		s.Name = "Carpentry";
		Carpentry = s;
		s = new Skill();
		s.Name = "Fishing";
		Fishing = s;
		s = new Skill();
		s.Name = "Cooking";
		Cooking = s;
		s = new Skill();
		s.Name = "Mining";
		Mining = s;
		s = new Skill();
		s.Name = "Smithing";
		Smithing = s;
		s = new Skill();
		s.Name = "Questing";
		Questing = s;
		s = new Skill();
		s.Name = "Hunting";
		Hunting = s;
		s = new Skill();
		s.Name = "Medicine";
		Medicine = s;
	}
	
	public int GetLevelMaxExp(int Level) {
		if(Level > 100)
			return Integer.MAX_VALUE;
		int MaxExp = 100 + (int)(68 * Level * 0.5);
		if(Level > 10)
			MaxExp += 173 * (Level - 10);
		if(Level > 20)
		{
			int Level2 = Level - 20;
			MaxExp += 264 * (Level2 * Level2);
		}
		if(Level > 40)
		{
			int Level2 = Level - 40;
			MaxExp += (int)(497 * (Level2 * (Level2 * 0.5)));
		}
		if(Level > 60)
		{
			int Level2 = Level - 60;
			MaxExp += (int)(674 * (Level2 * Level2));
		}
		if(Level > 80)
		{
			int Level2 = Level - 80;
			MaxExp += (int)(965 * (Level2 * Level2 * Level2));
		}
		if(Level > 90)
		{
			int Level2 = Level - 90;
			MaxExp += (int)(1239 * (Level2 * Level2 * Level2 * Level));
		}
		return MaxExp;
	}
}
