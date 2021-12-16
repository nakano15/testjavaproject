package TestProject;

public class SkillProgress {
	private Skill _skill = null;
	
	public Skill GetSkill() {
		if(_skill == null)
			_skill = Skill.GetSkill(ID);
		return _skill;
	}
	
	public String Name() {
		return GetSkill().Name;
	}
	
	public SkillProgress(int ID) {
		this.ID = ID;
		MaxExp = GetMaxExp();
	}
	
	public int ID = 0;
	public int Level = 0;
	public int Exp = 0;
	public int MaxExp = 0;
	
	public int GetCurrentExp()
	{
		return Exp;
	}
	
	public int GetMaxExp() {
		return MaxExp;
	}
	
	public boolean Learned()
	{
		return Level > 0;
	}
}
