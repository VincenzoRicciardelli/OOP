package journals;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Journal {
	
	String name;
	Double impactFactor;
	
	Map<String, Paper> papers = new HashMap<>();
	
	public Journal(String name, Double impactFactor)
	{
		this.name = name;
		this.impactFactor = impactFactor;
	}
	
	public String getName() {
		return name;
	}
	
	public Double getImpactFactors() {
		return impactFactor;
	}

	public void addPaper(String paperTitle, Paper p)
	{
		papers.put(paperTitle, p);
	}
	
	public void setPapersFactor()
	{
		List<Paper> pList = new LinkedList<>(papers.values());
		for(int i=0; i<pList.size(); i++)
		{
			Paper p = pList.get(i);
			p.setImpactFactor(getImpactFactors());
		}
	}
	
	public int getPapersNumber() {
		return papers.size();
	}
}
