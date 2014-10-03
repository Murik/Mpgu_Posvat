package Data;

/**
 * Created with IntelliJ IDEA.
 * User: murik
 * Date: 03.10.12
 * Time: 20:35
 */

public class People {
	private int ID;
	private String Name;
	private String Surname;
//	Facultet facultet;
//	Comand key;
	String comandName;
	String facultetName;

	public People() {
	}

	public People(int ID, String name, String surname, String comandName, String facultetName) {
		this.ID = ID;
		Name = name;
		Surname = surname;
//		this.facultet = facultet;
		this.comandName = comandName;
		this.facultetName = facultetName;
	}

	public String getName() {
		return Name;
	}

	public String getSurname() {
		return Surname;
	}

	public int getID() {
		return ID;
	}

	public String getComandName() {
		return comandName;
	}

	public String getFacultetName() {
		return facultetName;
	}

//	public String format(){
//		return this.getName() + " "+this.getSurname() + " / " + this.getComandName() + " / " + this.getFacultetName();
//	}

	@Override
	public String toString() {
		return this.getName() + " "+this.getSurname() + " / " + this.getComandName() + " / " + this.getFacultetName();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		People people = (People) o;

		if (!Name.equals(people.Name)) return false;
		if (!Surname.equals(people.Surname)) return false;
		if (!comandName.equals(people.comandName)) return false;
		if (!facultetName.equals(people.facultetName)) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = Name.hashCode();
		result = 31 * result + Surname.hashCode();
		result = 31 * result + comandName.hashCode();
		result = 31 * result + facultetName.hashCode();
		return result;
	}
}
