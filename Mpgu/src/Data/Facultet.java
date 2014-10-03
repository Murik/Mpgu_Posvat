package Data;

/**
 * Created with IntelliJ IDEA.
 * User: murik
 * Date: 04.10.12
 * Time: 3:36
 */

public class Facultet {
	String Name;

	int[] bivak = new int[]{0,0,0,0,0};
	int bivakSummBalls =0; //чем меньше балов тем лучше
	int bivakPlace = 0;




	int prometei = 0;
	int prometeiPlace = 0;

	int[] tvorcestvo = new int[] {0,0,0};
	int tvorcestvoPlace =0;

	int polosaBestComandPlace = 100;
	int polosaPlace = 0;


	int konkursnajaProg = 0;

	int orientirovaniePlace = 0;

	int finalPlace = 0;

	public Facultet(String name) {
		Name = name;
	}

	public int[] getBivak() {
		if(bivak==null)bivak = new int[]{0,0,0,0,0};
		return bivak;
	}

	public int[] getTvorcestvo() {
		if(tvorcestvo==null) tvorcestvo = new int[] {0,0,0};
		return tvorcestvo;
	}

	public void setBivak(int bivakProxod,int ball) {
		this.bivak[bivakProxod] = ball;
		this.bivakSummBalls =0;
		for (int i : this.bivak) {
			this.bivakSummBalls += i;
		}
	}

	public int getBivakSummBalls() {
		return bivakSummBalls;
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

	public int getPrometei() {
		return prometei;
	}

	public void setPrometei(int prometei) {
		this.prometei = prometei;
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

	public int getPolosaBestComandPlace() {
		return polosaBestComandPlace;
	}

	public void setPolosaBestComandPlace(int polosaBestComandPlace) {
		if (polosaBestComandPlace < this.polosaBestComandPlace) {
			this.polosaBestComandPlace = polosaBestComandPlace;
		}
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

	public int getOrientirovaniePlace() {
		return orientirovaniePlace;
	}

	public void setOrientirovaniePlace(int orientirovaniePlace) {
		this.orientirovaniePlace = orientirovaniePlace;
	}

	public int getFinalPlace() {
		return finalPlace;
	}

	public void setFinalPlace(int finalPlace) {
		this.finalPlace = finalPlace;
	}

	@Override
	public String toString() {
		return Name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Facultet facultet = (Facultet) o;

		if (!Name.equals(facultet.Name)) return false;

		return true;
	}

	@Override
	public int hashCode() {
		return Name.hashCode();
	}
}
