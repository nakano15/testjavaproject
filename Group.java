package TestProject;

import java.util.ArrayList;
import java.util.List;

public class Group {
	public Character[] Members = new Character[6];
	
	public Group()
	{
	}
	
	public Group(Character Leader)
	{
		Members[0] = Leader;
	}
	
	public Character[] GetCharacters() {
		List<Character> members = new ArrayList<Character>();
		for(Character Member: Members) {
			if(Member != null)
				members.add(Member);
		}
		return members.toArray(new Character[0]);
	}
}
