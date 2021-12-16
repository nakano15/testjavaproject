package TestProject;
import java.util.Map;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MonsterRace extends Races{
	private static Map<Integer, MonsterRace> MobDB = new HashMap<Integer, MonsterRace>();
	public List<Color> SkinColors = new ArrayList<Color>();
	
	public MonsterRace() {
		IsMonster = true;
	}
	
	public void AddColor(Color color)
	{
		if(SkinColors.size() == 0)
			this.BasicSkinColor = color;
		SkinColors.add(color);
	}
	
	public static MonsterRace GetMonster(int ID) {
		if(!MobDB.containsKey((Integer)ID))
		{
			MonsterRace m = GetMonsterByID(ID);
			MobDB.put(ID, m);
			return m;
		}
		return MobDB.get(ID);
	}
	
	private static MonsterRace GetMonsterByID(int ID) {
		MonsterRace m = new MonsterRace();
		switch(ID)
		{
		case 0:
			m.Name = "Forest Mender";
			m.AddColor(new Color(255,255,255,255));
			m.MHP = 20;
			m.BasicAttackDamage = 4;
			m.BasicDefense = 2;
			m.BasicEvasion = 4;
			m.SetAllStatusToSameValue(3);
			m.STR++;
			m.INT += 2;
			m.DEX--;
			m.SetupGrowthByStatus();
			m.LoadCharacterImages();
			break;
		}
		if(m.SkinColors.size() == 0)
			m.SkinColors.add(new Color(255, 255, 255, 255));
		return m;
	}
	
	public void SetupGrowthByStatus() {
		float GrowthPoint = 1f / (STR + AGI + VIT + INT + DEX + LUK);
		this.GrowthSTR = GrowthPoint * STR;
		this.GrowthAGI = GrowthPoint * AGI;
		this.GrowthVIT = GrowthPoint * VIT;
		this.GrowthINT = GrowthPoint * INT;
		this.GrowthDEX = GrowthPoint * DEX;
		this.GrowthLUK = GrowthPoint * LUK;
	}
}
