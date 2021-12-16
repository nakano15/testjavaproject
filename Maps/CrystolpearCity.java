package TestProject.Maps;

import TestProject.Map;

public class CrystolpearCity extends Map {

	public CrystolpearCity() {
		super("Crystolpear City", 120, 120);
		this.morality = MapMorality.SafeZone;
		this.MapConnections[Connection_LowerCenter] = 0;
		this.Level = 1;
	}
	
}
