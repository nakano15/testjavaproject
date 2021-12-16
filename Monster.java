package TestProject;

public class Monster extends Character{
	public Monster(int RaceID)
	{
		Race = MonsterRace.GetMonster(RaceID);
		Name = Race.Name;
		SkinColor = Race.BasicSkinColor;
	}
}
