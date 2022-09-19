package trail;

public class Runner {

    
    private int pettorale;
	private String name;
	private String surname;

	public Runner(int pettorale, String name, String surname) {
		this.pettorale = pettorale;
		this.name = name;
		this.surname = surname;
	}

	public int getBibNumber(){
        return pettorale;
    }

    public String getName(){
        return name;
    }

    public String getSurname(){
        return surname;
    }

	@Override
	public String toString() {
		return "Runner [pettorale=" + pettorale + ", name=" + name + ", surname=" + surname + "]";
	}

    
}

