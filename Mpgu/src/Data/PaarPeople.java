package Data;

import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: murik
 * Date: 04.10.12
 * Time: 20:28
 */

public class PaarPeople {
	int ID;
	People people1;
	People people2;

	Timestamp startOrient = new Timestamp(0);
	Timestamp finishOrient = new Timestamp(0);

	int minutesToPolosa = 0;

	int ballsOrientir = 0;



	int orientPlace = 0;


//	public PaarPeople(People people1, People people2) {
//		this.people1 = people1;
//		this.people2 = people2;
//	}

	public PaarPeople(int ID, People people1, People people2) {
		this.ID = ID;
		this.people1 = people1;
		this.people2 = people2;
	}

//	public String formatName() {
//		return people1.getName()+" "+people1.getSurname() +" | " + people2.getName() + " " +  people2.getSurname()
//				+ " / " + people1.getComandName() + " / " + people1.getFacultetName();
//	}

	@Override
	public String toString() {
		return people1.getName()+" "+people1.getSurname() +" | " + people2.getName() + " " +  people2.getSurname()
				+ " / " + people1.getComandName() + " / " + people1.getFacultetName();
	}

	public People getPeople1() {
		return people1;
	}

	public People getPeople2() {
		return people2;
	}

	public int getID() {
		return ID;
	}

	public Timestamp getStartOrient() {
		return startOrient;
	}

	public Timestamp getFinishOrient() {
		return finishOrient;
	}


	public void setStartOrient(Timestamp startOrient) {
		this.startOrient = startOrient;
		updateMinutesToPolosa();
	}

	public void setFinishOrient(Timestamp finishOrient) {
		this.finishOrient = finishOrient;
		updateMinutesToPolosa();
	}

	public int getBallsOrientir() {
		return ballsOrientir;
	}

	public void setBallsOrientir(int ballsOrientir) {
		this.ballsOrientir = ballsOrientir;
	}

	public int getOrientPlace() {
		return orientPlace;
	}

	public void setOrientPlace(int orientPlace) {
		this.orientPlace = orientPlace;
	}

	public int getMinutesToPolosa() {
		return minutesToPolosa;
	}

	public void setMinutesToPolosa(int minutesToPolosa) {
		this.minutesToPolosa = minutesToPolosa;
	}

	private void updateMinutesToPolosa(){
		minutesToPolosa =(int) TimeUnit.MILLISECONDS.toMinutes(finishOrient.getTime()-startOrient.getTime());
	}
}
