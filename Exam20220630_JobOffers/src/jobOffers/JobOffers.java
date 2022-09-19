package jobOffers; 
import java.util.*;

import static java.util.stream.Collectors.*;

public class JobOffers  {

	public Map<String, Skill> capacita = new HashMap<>();
	public Map<String, Position> posizioni = new TreeMap<>();
	public Map<String, Candidate> candidates = new TreeMap<>();
	public Map<String, Consultant> consultants = new TreeMap<>();
	boolean fail;
	
//R1
	public int addSkills (String... skills) {
		for(String skill : skills) {
			if(capacita.get(skill)==null) {
				Skill s = new Skill(skill);
				capacita.put(skill, s);
			}
		}
		return capacita.size();
	}
	
	public int addPosition (String position, String... skillLevels) throws JOException {
		int sum = 0;
		int dim = 0;
		if(posizioni.get(position)!=null) throw new JOException("Posto gia' presente");
		Position p = new Position(position);
		for(String level : skillLevels)
		{
			String[] Items = level.split(":");
			String cap = Items[0];
			if(capacita.get(cap)==null) throw new JOException("Capacita' non presente");
			int l = Integer.parseInt(Items[1]);
			if(l<4 || l>8) throw new JOException("Livello non rispetta il range");
			Skill s = capacita.get(cap);
			s.setLevel(l);
			p.addSkills(s);
			sum += l;
			++dim;
		}
		posizioni.put(position, p);
		return sum/dim;
	}
	
//R2	
	public int addCandidate (String name, String... skills) throws JOException {
		if(candidates.get(name) != null) throw new JOException("Candidato gia' aggiunto");
		Candidate c = new Candidate(name);
		for(String skill : skills) {
			if(capacita.get(skill)==null) throw new JOException("Capacità non aggiunta");
			c.addSkills(capacita.get(skill));
		}
		candidates.put(name, c);
		return c.getSkills().size();
	}
	
	public List<String> addApplications (String candidate, String... positions) throws JOException {
		SortedSet<String> candidature = new TreeSet<>();
		Candidate c = candidates.get(candidate);
		if(c==null) throw new JOException("Candidato non presente");
		for(String position : positions)
		{
			Position p = posizioni.get(position);
			if(p == null) throw new JOException("Posizione non presente");
			for(Skill s : p.getSkills()) { 
				if(!c.containsSkill(s)) throw new JOException("Il candidato non ha le capacita' necessarie");
			}
			c.addPosition(p);
			candidature.add(candidate+ ":" +position);
			p.addCandidates(c.getName());
		} 
		List<String> candidatureList = new LinkedList<>(candidature);
		return	candidatureList;
	} 
	
	public TreeMap<String, List<String>> getCandidatesForPositions() {
	 	List<Position> posizioniList = new LinkedList<>(posizioni.values());
	 	TreeMap<String, List<String>> candidatesForPositions = new TreeMap<>();
		for(int i=0; i<posizioniList.size(); i++)
		{
			Position p = posizioniList.get(i);
			candidatesForPositions.put(p.getName(), p.getCandidates());
			
		}
		return candidatesForPositions;
	} 
	
	
//R3
	public int addConsultant (String name, String... skills) throws JOException {
		if(consultants.containsKey(name)) throw new JOException("Candidato gia' aggiunto");
		Consultant c = new Consultant(name);
		for(String skill : skills) {
			if(capacita.get(skill)==null) throw new JOException("Capacità non aggiunta");
			c.addSkills(capacita.get(skill));
		}
		consultants.put(name, c);
		return c.getSkills().size();
	}
	
	public Integer addRatings (String consultant, String candidate, String... skillRatings)  throws JOException {
		int sum = 0;
		int dim = 0;
		
		Consultant c = consultants.get(consultant);
		if(c==null) throw new JOException("Consulente non presente");
		Candidate candidato = candidates.get(candidate);
		if(candidato==null) throw new JOException("Candidato non presente");
		for(String rating : skillRatings)
		{
			String[] Items = rating.split(":");
			String cap = Items[0];
			Skill s = capacita.get(cap);
			if(!c.containsSkill(s)) throw new JOException("Capacita' non presente");
			int l = Integer.parseInt(Items[1]);
			if(l<4 || l>10) throw new JOException("Livello non rispetta il range");
			sum += l;
			++dim;
			candidato.addSkillRates(capacita.get(cap), l);
		}
        //System.out.println(candidato.skillRates);
		return sum/dim;
	}
	
//R4
	public List<String> discardApplications() {
		List<Position> listaPosizioni = new LinkedList<>(posizioni.values());
		Map<String, String> discarded = new TreeMap<>();
		
		for(int i=0; i<listaPosizioni.size(); i++)
		{
			Position p = listaPosizioni.get(i);
			List<String> candidati = p.getCandidates();
			//System.out.println(candidati);
			for(int j=0; j<candidati.size(); j++)
			{
				//System.out.println(candidati.size());
				Candidate c = candidates.get(candidati.get(j));
				//System.out.println(candidati.get(j));
				List<Skill> skillss = p.getSkills();
				for(int k=0; k<skillss.size(); k++)
				{
					Skill s = skillss.get(k);
					//System.out.println(c.getName()+":"+c.skills.get(s).getName()+":"+c.skillRates.get(s)+" ----->"+ s.getName()+":"+s.getLevel());
					if(c.skillRates.get(s)!=null) {
						if(c.skillRates.get(s) < s.getLevel())
						{
							discarded.put(c.getName()+":"+p.getName(), c.getName()+":"+p.getName());	
							break;
						}
					}
					else
					{
						discarded.put(c.getName()+":"+p.getName(), c.getName()+":"+p.getName());
						break;
					}
				}
			}
		}
		List<String> discardedList = new LinkedList<>(discarded.values());
		System.out.println(discardedList);
		return discardedList;
	}
	
	 
	public List<String> getEligibleCandidates(String position) {
		List<Position> listaPosizioni = new LinkedList<>(posizioni.values());
		Map<String, String> eligible = new TreeMap<>();
		
		for(int i=0; i<listaPosizioni.size(); i++)
		{
			Position p = listaPosizioni.get(i);
			//System.out.println(p.getName());
			List<String> candidati = p.getCandidates();
			//System.out.println(candidati);
			for(int j=0; j<candidati.size(); j++)
			{
				//System.out.println(candidati.size());
				Candidate c = candidates.get(candidati.get(j));
				//System.out.println(candidati.get(j));
				List<Skill> skillss = p.getSkills();
				for(int k=0; k<skillss.size(); k++)
				{
					fail = false;
					Skill s = skillss.get(k);
					//System.out.println(c.getName()+":"+c.skills.get(s).getName()+":"+c.skillRates.get(s)+" ----->"+ s.getName()+":"+s.getLevel());
					if(c.skillRates.get(s) >= s.getLevel() && fail==false)
					{
					//	System.out.println(c.getName()+":"+c.skills.get(s).getName()+":"+c.skillRates.get(s)+" ----->"+ s.getName()+":"+s.getLevel());		
					//	System.out.println(eligible);
					}
					else
						fail=true;
				}
				if(fail==false)
				{
					eligible.put(c.getName(), c.getName());	
				}
			}
		}
		List<String> eligibleList = new LinkedList<>(eligible.values());
		System.out.println(eligibleList);
		return eligibleList;
	}
	
	
}

		
