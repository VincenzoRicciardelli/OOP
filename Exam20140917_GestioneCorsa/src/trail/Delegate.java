package trail;

import java.util.LinkedList;
import java.util.List;

public class Delegate {

	private String name;
	private String surname;
	private String id;
	
	List<Location> locations = new LinkedList<>();

	public Delegate(String name, String surname, String id) {
		this.name = name;
		this.surname = surname;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	public String getId() {
		return id;
	}

	@Override
	public String toString() {
		return  surname + ", " + name + ", " + id;
	}

	public void addLocation(Location l) {
		locations.add(l);
	}

	
	
}
