import Data.Comand;
import Data.Facultet;
import Data.People;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: murik
 * Date: 03.10.12
 * Time: 20:33
 */

public class Mpgu_start {

	public static void main(String[] args) {
		Facultet fac = new Facultet("qq");
		Comand com = new Comand(fac,"comab");
		People pp1 = new People(1,"Ivan","fuck",com.getName(),fac.getName());
		People pp2 = new People(2,"Ivan1","fuck1",com.getName(),fac.getName());
		People pp3 = new People(3,"Ivan2","fuck2",com.getName(),fac.getName());
		People pp4 = new People(4,"Ivan3","fuck3",com.getName(),fac.getName());
		People pp5 = new People(5,"Ivan4","fuck4",com.getName(),fac.getName());

		List<People>  peopleList = new ArrayList<People>();
		peopleList.add(pp1);
		peopleList.add(pp2);
		peopleList.add(pp3);
		peopleList.add(pp4);
		peopleList.add(pp5);

		String result = new Gson().toJson(peopleList);

		try {

			FileWriter file = new FileWriter("test.json");
			file.write(new Gson().toJson(peopleList));
			file.flush();
			file.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			List<Data.People> sslist = readJsonStream(new FileInputStream("test.json"));

			for(Data.People pp : sslist) {
				System.out.println(pp.getID() + "/" + pp.getName() + "/" + pp.getSurname());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public static List<Data.People> readJsonStream(FileInputStream in) throws IOException {
		JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
		List<Data.People> messages = new ArrayList<Data.People>();
		reader.beginArray();
		while (reader.hasNext()) {
			Data.People message = new Gson().fromJson(reader, Data.People.class);
			messages.add(message);
		}
		reader.endArray();
		reader.close();
		return messages;
	}
}
