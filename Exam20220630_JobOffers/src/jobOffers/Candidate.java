package jobOffers;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Candidate implements Comparable<Candidate>{

	String name;
	List <Position> positions  = new LinkedList<>();
	Map <Skill, Skill> skills  = new HashMap<>();
    Map<Skill, Integer> skillRates = new HashMap<>(); 

	public Candidate(String name) {
		this.name = name;
	}
	
	public void addSkills(Skill skill) {
		skills.put(skill, skill);
	}
	
	public void addSkillRates(Skill skill, int level) {
		skillRates.put(skill, level);
	}
	
	public void addPosition(Position p) {
		positions.add(p);
	}

	public List <Position> getPositions() {
		return positions;
	}
	
	public String getPositionName() {
		return positions.toString();
	}

	public String getName() {
		return name;
	}

	public Map<Skill, Skill> getSkills() {
		return skills;
	}
	
	public boolean containsSkill(Skill s) {
		return skills.containsKey(s);
	}

	public int compareTo(Candidate altro) {
		return this.getName().compareTo(altro.getName());
	}
}
