package TestProject;

public class Bag {
	public byte BagSlots = 0;
	
	public Bag(byte Slots)
	{
		if(Slots < 0)
			Slots = 0;
		BagSlots = Slots;
		item = new Item[BagSlots];
		for(int i = 0; i < BagSlots; i++)
		{
			item[i] = new Item();
		}
	}
	
	public Item[] item = new Item[0];
}
