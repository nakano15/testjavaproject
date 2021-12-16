package TestProject.Maps;

import TestProject.Map;
import TestProject.Monster;
import TestProject.Character;

public class CrystolpearField01 extends Map{
	
	public CrystolpearField01() {
		super("Crystolpear Field 01", 80, 80);
		Level = 1;
		this.MapConnections[Connection_UpCenter] = 1;
		
		Character chara = new Character();
		chara.Name = "Rasha";
		chara.Male = false;
		chara.Hair = 3;
		chara.Head = 0;
		this.AddNpc(chara);
		
		Monster mob = new Monster(0);
		this.AddMonster(mob, 1);
	}
	
}
