package journals;

import java.util.LinkedList;
import java.util.List;

public class Paper {

	double impactFactor;
	
	List<Author> authors = new LinkedList<>();
	
	private String title;

	public Paper(String title) {
		this.title = title;
		impactFactor = 0.0;
	}

	public void addAuthorsToPaper(Author a) {
		authors.add(a);
	}

	public String getTitle() {
		return title;
	}
	
	public double getImpactFactor() {
		return impactFactor;
	}

	public void setImpactFactor(double impactFactor) {
		this.impactFactor = impactFactor;
	}
}
