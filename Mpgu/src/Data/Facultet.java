package Data;

/**
 * Created with IntelliJ IDEA.
 * User: murik
 * Date: 04.10.12
 * Time: 3:36
 */

public class Facultet {
	String Name;


	int bivakPlace = 0;
	int prometeiPlace = 0;
	int tvorcestvoPlace =0;

	int polosaPlace = 0;
	int konkursnajaProg = 0;
	int orientirovanie = 0;

	public Facultet(String name) {
		Name = name;
	}

	public String getName() {
		return Name;
	}

	public int getBivakPlace() {
		return bivakPlace;
	}

	public void setBivakPlace(int bivakPlace) {
		this.bivakPlace = bivakPlace;
	}

	public int getPrometeiPlace() {
		return prometeiPlace;
	}

	public void setPrometeiPlace(int prometeiPlace) {
		this.prometeiPlace = prometeiPlace;
	}

	public int getTvorcestvoPlace() {
		return tvorcestvoPlace;
	}

	public void setTvorcestvoPlace(int tvorcestvoPlace) {
		this.tvorcestvoPlace = tvorcestvoPlace;
	}

	public int getPolosaPlace() {
		return polosaPlace;
	}

	public void setPolosaPlace(int polosaPlace) {
		this.polosaPlace = polosaPlace;
	}

	public int getKonkursnajaProg() {
		return konkursnajaProg;
	}

	public void setKonkursnajaProg(int konkursnajaProg) {
		this.konkursnajaProg = konkursnajaProg;
	}

	public int getOrientirovanie() {
		return orientirovanie;
	}

	public void setOrientirovanie(int orientirovanie) {
		this.orientirovanie = orientirovanie;
	}

	@Override
	public String toString() {
		return Name;
	}
}
