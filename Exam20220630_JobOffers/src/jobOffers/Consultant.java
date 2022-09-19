package jobOffers;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Consultant {
	
	private String name;
	private List<Skill> skills  = new LinkedList<>();
	private Map<Candidate, Map<Skill, Integer>> ratings = new TreeMap<>();

	public Consultant(String name) {
		this.name = name;
	}

	public void addSkills(Skill skill) {
		skills.add(skill);
	}
	
	public String getName() {
		return name;
	}

	public List<Skill> getSkills() {
		return skills;
	}
	
	public void addRating(Candidate candidate, Map<Skill, Integer> skill) {
		ratings.put(candidate, skill);
	}
	
	public boolean containsSkill(Skill s) {
		return skills.contains(s);
	}
}
