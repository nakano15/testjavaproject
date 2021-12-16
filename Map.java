package TestProject;

import java.awt.Color;
import java.awt.List;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Map {
	public static final int MaxMaps = 2;
	public static Map[] Maps = new Map[MaxMaps];
	
	public String Name = "";
	public int Width = 80, Height = 80;
	public int Level = 1;
	public MapMorality morality = MapMorality.UnsafeZone;
	public short[] MapConnections = new short[] {-1,-1,-1,-1,-1,-1,-1,-1};
	public final byte Connection_UpLeft = 0, Connection_UpCenter = 1, Connection_UpRight = 2, 
			Connection_LeftCenter = 3, Connection_RightCenter = 4,
			Connection_LowerLeft = 5, Connection_LowerCenter = 6, Connection_LowerRight = 7;
	public float NavigationRate = 0.1f;
	public ArrayList<MapCharacter> NpcsList = new ArrayList<MapCharacter>(), MonsterList = new ArrayList<MapCharacter>();
	
	public Map(String MapName, int Width, int Height) {
		this.Name = MapName;
		this.Width = Width;
		this.Height = Height;
		NavigationRate = 4f / (Width * Height);
	}
	
	public void SetConnection(byte Orientation, short MapID)
	{
		MapConnections[Orientation] = MapID;
	}
	
	public static Map GetMap(int MapID)
	{
		if(MapID < 0 || MapID >= MaxMaps)
			return null;
		if(Maps[MapID] != null)
		{
			return Maps[MapID];
		}
		return LoadMap(MapID);
	}
	
	private static Map LoadMap(int MapID) {
		Map map = null;
		switch(MapID)
		{
		case 0:
			map = new TestProject.Maps.CrystolpearField01();
			break;
		case 1:
			map = new TestProject.Maps.CrystolpearCity();
			break;
		}
		if(MapID >= 0 && MapID < MaxMaps)
			Maps[MapID] = map;
		return map;
	}
	
	public void AddNpc(Character character)
	{
		MapCharacter m = new MapCharacter(character, 1);
		NpcsList.add(m);
	}
	
	public void AddMonster(Monster mob, int Level)
	{
		MapCharacter m = new MapCharacter(mob, Level);
		MonsterList.add(m);
	}
	
	public class MapCharacter{
		public Character character;
		public boolean Found = false;
		
		public MapCharacter(Character character, int Level)
		{
			this.character = character;
			character.Level = Level;
		}
	}
	
	public enum MapMorality{
		UnsafeZone,
		SafeZone
	}
}
