package journals;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Author {

	private String name;
	
	Map<String, Paper> papers = new HashMap<>();

	public Author(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public void addPaper(String title, Paper p)
	{
		papers.put(title, p);
	}

	public double getImpactFactor() {
		double cont = 0.0;
		if(papers.isEmpty()) return cont;
		else
		{
			List<Paper> pList = new LinkedList<>(papers.values());
			//System.out.println(pList);
			for(int i=0; i<pList.size(); i++)
			{
				Paper p = pList.get(i);
				//System.out.println(p.getTitle());
				//System.out.println(p.getImpactFactor());
				cont += p.getImpactFactor();
			}
			//System.out.println("ciao:" + cont);
			return cont;
		}
		
	}
	
	public int getPapersNumber() {
		return papers.size();
	}
	
	

}
