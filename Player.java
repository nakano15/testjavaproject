package TestProject;

import java.util.ArrayList;
import java.util.List;

public class Player {
	public static Bag[] Bags = new Bag[5];
	public static Character MyCharacter = new Character();
	public static Group MyGroup;
	public static List<Group> groups = new ArrayList<Group>();
	
	public static void Initialize() {
		for(int i = 0; i < 5; i++) {
			if(i == 0) {
				Bags[i] = new Bag((byte) 20);
				//Bags[i].item[1] = new Item(0,5);
			}
			else
				Bags[i] = new Bag((byte) 0);
		}
	}
	
	public static void UponCreatingMyCharacter() {
		groups.add(MyGroup = new Group(MyCharacter));
	}
}
