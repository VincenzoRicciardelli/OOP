package journals;

import java.util.*;
import java.util.stream.Collectors;

public class Journals {
	
	Map<String, Journal> journals = new TreeMap<>();
	
	Map<String, Author> authors = new TreeMap<>();
	
	
	//R1 
	/**
	 * inserts a new journal with name and impact factor. 
	 * 
	 * @param name	name of the journal
	 * @param impactFactor relative impact factor
	 * @return  the number of characters of the name
	 * @throws JException if the journal (represented by its name) already exists
	 */
	public int addJournal (String name, double impactFactor) throws JException {
		if(journals.containsKey(name)) throw new JException();
		Journal j = new Journal(name, impactFactor);
		journals.put(name, j);
		
		return name.length();
	}

	/**
	 * retrieves the impact factor of the journal indicated by the name
	 * 
	 * @param name the journal name
	 * @return the journal IF
	 * @throws JException if the journal does not exist
	 */
	public double getImpactFactor (String name) throws JException {
		if(!journals.containsKey(name)) throw new JException();
		return journals.get(name).getImpactFactors();
	}

	/**
	 * groups journal names by increasing impact factors. 
	 * Journal names are listed in alphabetical order
	 * 
	 * @return the map of IF to journal
	 */
	public SortedMap<Double, List<String>> groupJournalsByImpactFactor () {
		SortedMap<Double, List<String>> res = journals.values().stream()
							.sorted(Comparator.comparing(Journal::getName))		
							.collect(Collectors.groupingBy(Journal::getImpactFactors, TreeMap::new, 
									Collectors.mapping(Journal::getName,Collectors.toList())));
		return res;
	}

	
	//R2
	/**
	 * adds authors. 
	 * Duplicated authors are ignored.
	 * 
	 * @param authorNames names of authors to be added
	 * @return the number of authors entered so far
	 * @throws JException 
	 */
	public int registerAuthors (String... authorNames) {
		for(String name : authorNames)
		{
			if(!authors.containsKey(name))
			{
				Author a = new Author(name);
				authors.put(name, a);
			}
			
		}
		return authors.size();
	}
	
	/**
	 * adds a paper to a journal. 
	 * The journal is indicated by its name; 
	 * the paper has a title that must be unique in the specified journal,
	 * the paper can have one or more authors.
	 * 
	 * @param journalName
	 * @param paperTitle
	 * @param authorNames
	 * @return the journal name followed by ":" and the paper title.
	 * @throws JException if the journal does not exist or the title is not unique within the journal or not all authors have been registered
	 */
	public String addPaper (String journalName, String paperTitle, String... authorNames) throws JException {
		if(!journals.containsKey(journalName)) throw new JException();
		Journal j = journals.get(journalName);
		if(j.papers.containsKey(paperTitle)) throw new JException();
		Paper p = new Paper(paperTitle);
		for(String name : authorNames)
		{
			if(!authors.containsKey(name)) throw new JException();
			Author a = authors.get(name);
			p.addAuthorsToPaper(authors.get(name));
			j.addPaper(paperTitle ,p);
			a.addPaper(paperTitle, p);
		}
		//System.out.println(j.papers);
		j.setPapersFactor();
		return journalName+":"+paperTitle;
	}
	
	/**
	 * gives the number of papers for each journal. 
	 * Journals are sorted alphabetically. 
	 * Journals without papers are ignored.
	 * 
	 * @return the map journal to count of papers
	 */
	public SortedMap<String, Integer> giveNumberOfPapersByJournal () { //serve toMap
		
		List<Journal> journalsList = new LinkedList<>(journals.values());
		SortedMap<String, Integer> res = new TreeMap<>();
		for(int i=0; i<journalsList.size(); i++)
		{
			Journal j = journalsList.get(i);
			//System.out.println(j.getName());
			//System.out.println(j.papers);
			if(!j.papers.isEmpty())
			{
				res.put(j.getName(), j.papers.size());
			}
		}
		return res;
	}
	
	//R3
	/**
	 * gives the impact factor for the author indicated.
	 * The impact factor of an author is obtained by adding 
	 * the impact factors of his/her papers. 
	 * The impact factor of a paper is equal to that of the 
	 * journal containing the paper.
	 * If the author has no papers the result is 0.0.
	 *
	 * @param authorName
	 * @return author impact factor
	 * @throws JException if the author has not been registered
	 */
	public double getAuthorImpactFactor (String authorName) throws JException {
		if(!authors.containsKey(authorName)) throw new JException();
		Author a = authors.get(authorName);
		return a.getImpactFactor();
	}
	
	/**
	 * groups authors (in alphabetical order) by increasing impact factors.
	 * Authors without papers are ignored.
	 * 
	 * @return the map IF to author list
	 */
	public SortedMap<Double, List<String>> getImpactFactorsByAuthors () {
		SortedMap<Double, List<String>> res = authors.values().stream()
				.filter(a -> !a.papers.isEmpty())
				.sorted(Comparator.comparing(Author::getName))		
				.collect(Collectors.groupingBy(Author::getImpactFactor, TreeMap::new, 
						Collectors.mapping(Author::getName,Collectors.toList())));
		//System.out.println(res);
		return res;
	}
	
	
	//R4 
	/**
	 * gives the number of papers by author; 
	 * authors are sorted alphabetically. 
	 * Authors without papers are ignored.
	 * 
	 * @return
	 */
	public SortedMap<String, Integer> getNumberOfPapersByAuthor() {
		SortedMap<String, Integer> res = authors.values().stream()
				.filter(a -> !a.papers.isEmpty())
				.sorted((a1, a2) -> a1.getPapersNumber() - a2.getPapersNumber())
				.collect(Collectors.groupingBy(Author::getName, TreeMap::new, 
							Collectors.summingInt(Author::getPapersNumber)));
		return res;
	}
	
	/**
	 * gives the name of the journal having the largest number of papers.
	 * If the largest number of papers is common to two or more journals 
	 * the result is the name of the journal which is the first in 
	 * alphabetical order.
	 * 
	 * @return journal with more papers
	 */
	public String getJournalWithTheLargestNumberOfPapers() {
		 List<String> res = journals.values().stream()
				.sorted((j1, j2) -> j2.getPapersNumber() - j1.getPapersNumber())
				.map(Journal::getName)
				.collect(Collectors.toList());
		 //System.out.println(res);
		 String nome = res.get(0);
		 //System.out.println(nome+":"+journals.get(nome).getPapersNumber());
		 return nome+":"+journals.get(nome).getPapersNumber();
	}

}

