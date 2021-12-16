package TestProject;

public class MaxExpHandler {
	public static int[] MaxExpTable = new int[100];
	public static final int MaxLevel = 99, InitialMaxExp = 400, ExpBonusPerLevel = 123, ExpPotencePerLevel = 87;
	
	public static void Initialize() {
		int CurrentMaxExp = InitialMaxExp;
		for(int i = 0; i < 100; i++) {
			MaxExpTable[i] = CurrentMaxExp;
			CurrentMaxExp += (ExpBonusPerLevel * i) + (ExpPotencePerLevel * ExpPotencePerLevel * i);//(int)((CurrentMaxExp + ExpBonusPerLevel) * ExpBonus);
		}
	}
	
	public static int GetMaxExpForLevel(int Level) {
		if(Level <= 0)
			return 0;
		if(Level >= 100)
			return Integer.MAX_VALUE;
		return MaxExpTable[Level - 1];
	}
}
