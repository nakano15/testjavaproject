package TestProject;
import java.util.Map;

import javax.imageio.ImageIO;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class ItemType {
	private static Map<Integer, ItemType> ItemDB = new HashMap<Integer, ItemType>();
	
	public String Name = "Unknown";
	public byte MaxStack = 1;
	public ItemTypes itemType = ItemTypes.Misc;
	public int Value = 0;
	public int Price = 1;
	public ItemRarity rarity = ItemRarity.Common;
	public Image ItemIcon;
	
	public static ItemType GetItem(int ID)
	{
		if(!ItemDB.containsKey(ID)) {
			ItemType i = GetItemType(ID);
			ItemDB.put(ID, i);
			return i;
		}
		return ItemDB.get(ID);
	}
	
	private static ItemType GetItemType(int ID) {
		ItemType i = new ItemType();
		switch(ID) {
			case 0:
				i.Name = "Apple";
				i.itemType = ItemTypes.RestoreHealth;
				i.Value = 15;
				i.Price = 3;
				i.rarity = ItemRarity.Common;
				break;
			case 1:
				i.Name = "Dagger";
				i.itemType = ItemTypes.Weapon;
				i.Value = 10;
				i.Price = 40;
				break;
		}
		//Load Item Sprite
		try {
			BufferedImage image = ImageIO.read(new File("src/TestProject/Content/Items/Item_" + ID + ".png"));
			i.ItemIcon = image;
		} catch (IOException e) {
			
		}
		return i;
	}
	
	public static float GetBoost(ItemRarity rarity)
	{
		float Boost = 0;
		switch(rarity)
		{
		case Trash:
			Boost = 0.35f;
			break;
		case Uncommon:
			Boost = 0.10f;
			break;
		case Rare:
			Boost = 0.25f;
			break;
		case Epic:
			Boost = 0.40f;
			break;
		case Legendary:
			Boost = 0.65f;
			break;
		}
		return Boost;
	}
	
	public static Color GetColorByRarity(ItemRarity rarity) {
		switch(rarity)
		{
		case Trash:
			return new Color(0.4f, 0.4f, 0.4f);
		case Uncommon:
			return new Color(80,250,50);
		case Rare:
			return new Color(50,50,250);
		case Epic:
			return new Color(250,50,250);
		case Legendary:
			return new Color(250,250,50);
		}
		return new Color(1f, 1f, 1f);
	}
	
	public enum ItemTypes
	{
		Misc,
		RestoreHealth,
		Weapon,
		Armor,
		Accessory,
		Bags
	}
	
	public enum ItemRarity
	{
		Trash,
		Common,
		Uncommon,
		Rare,
		Epic,
		Legendary
	}
}
