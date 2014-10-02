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
//	Comand comand;
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
}
