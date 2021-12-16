package TestProject;

import java.awt.Color;

public class Item {	
	public int ID = 0;
	public byte Stack = 0;
	public int Level = 1;

	public ItemType GetBase()
	{
		return ItemType.GetItem(ID);
	}
	
	public Item() {
		
	}
	
	public String Name()
	{
		return GetBase().Name;
	}
	
	public ItemType.ItemRarity Rarity()
	{
		return GetBase().rarity;
	}
	
	public ItemType.ItemTypes Type(){
		return GetBase().itemType;
	}
	
	public byte MaxStack() {
		return GetBase().MaxStack;
	}
	
	public Color GetRarityColor() {
		return ItemType.GetColorByRarity(Rarity());
	}
	
	public Item(int ID, int Level)
	{
		this.ID = ID;
		this.Level = Level;
		this.Stack = 1;
	}
	
	public Item(int ID, int Level, byte Stack)
	{
		if(Stack < 1)
			Stack = 0;
		this.ID = ID;
		this.Level = Level;
		this.Stack = Stack;
	}
	
	public int GetDamage() {
		return (int)(GetBase().Value * (1f + Level * ItemType.GetBoost(GetBase().rarity)));
	}
	
	public int GetDefense() {
		return (int)(GetBase().Value * (1f + Level * ItemType.GetBoost(GetBase().rarity)));
	}
}
