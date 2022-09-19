package jobOffers;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


public class Position {

	List <Skill> skills  = new LinkedList<>();
	List <String> candidates  = new LinkedList<>();
	
	private String name;
	
	public Position(String name) {
		this.name = name;
	}
	
	public void addSkills(Skill s) {
		skills.add(s);
	}

	public List <Skill> getSkills() {
		return skills;
	}
	

	public void addCandidates(String name) {
		candidates.add(name);
	}
	
	public List<String> getCandidates() {
		Collections.sort(candidates);
		return candidates;
	}

	public String getName() {
		return name;
	}

}

