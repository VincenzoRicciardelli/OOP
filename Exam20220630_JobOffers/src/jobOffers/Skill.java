package jobOffers;

public class Skill {
	
	String name;
    int level;

	public Skill(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
}
