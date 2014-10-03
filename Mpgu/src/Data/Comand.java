package Data;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: murik
 * Date: 04.10.12
 * Time: 3:37
 */

public class Comand {
	String facultetName;
	String Name;
	int[] bivak = new int[]{0,0,0,0,0};
	int bivakSummBalls =0; //чем меньше балов тем лучше
	int bivakPlace = 0;


	int prometei = 0;
	int prometeiPlace = 0;


	int[] tvorcestvo = new int[] {0,0,0};
	int tvorcestvoPlace =0;


	Timestamp startPolosa = new Timestamp(0);
	Timestamp finishPolosa = new Timestamp(0);
	int ballsPolosa = 0;
	int polosaPlace = 0;


	int konkursnajaProg = 0;

	int orientirovaniePlace = 0;

	int finalPlace = 0;

	public Comand(String facultetName, String name) {
		this.facultetName = facultetName;
		Name = name;
	}

	public String getFacultetName() {
		return facultetName;
	}

	public void setFacultetName(String facultetName) {
		this.facultetName = facultetName;
	}

	public String getName() {
		return Name;
	}

	public int[] getBivak() {
		return bivak;
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

	public int getPrometei() {
		return prometei;
	}

	public int[] getTvorcestvo() {
		return tvorcestvo;
	}

	public int getTvorcestvoPlace() {
		return tvorcestvoPlace;
	}

	public int getKonkursnajaProg() {
		return konkursnajaProg;
	}

	public void setPrometei(int prometei) {
		this.prometei = prometei;
	}

	public void setTvorcestvoPlace(int tvorcestvoPlace) {
		this.tvorcestvoPlace = tvorcestvoPlace;
	}

	public void setKonkursnajaProg(int konkursnajaProg) {
		this.konkursnajaProg = konkursnajaProg;
	}

	public void setStartPolosa(Timestamp startPolosa) {
		this.startPolosa = startPolosa;
	}

	public void setBallsPolosa(int ballsPolosa) {
		this.ballsPolosa = ballsPolosa;
	}

	public Timestamp getStartPolosa() {
		return startPolosa;
	}

	public int getBallsPolosa() {
		return ballsPolosa;
	}

	public Timestamp getFinishPolosa() {
		return finishPolosa;
	}

	public void setFinishPolosa(Timestamp finishPolosa) {
		this.finishPolosa = finishPolosa;
	}

	public int getPolosaPlace() {
		return polosaPlace;
	}

	public void setPolosaPlace(int polosaPlace) {
		this.polosaPlace = polosaPlace;
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

//	public List<PaarPeople> getPaarPeoples() {
//		return paarPeoples;
//	}
//
//	public void setPaarPeoples(List<PaarPeople> paarPeoples) {
//		this.paarPeoples = paarPeoples;
//	}
}

