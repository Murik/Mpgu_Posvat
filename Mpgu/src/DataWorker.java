import Data.Comand;
import Data.Facultet;
import Data.PaarPeople;
import Data.People;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: murik
 * Date: 04.10.12
 * Time: 3:15
 */
public class DataWorker {


	public static List<Facultet> loadFacData(){
		List<Facultet> messages = new ArrayList<Facultet>();
		File ff = new File(Facultet.class.getName()+".json");
		if (!ff.exists())return messages;
		try {
			FileInputStream in = new FileInputStream(Facultet.class.getName()+".json");
			JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));

			reader.beginArray();
			while (reader.hasNext()) {
				Facultet data = new Gson().fromJson(reader, Facultet.class);
				messages.add(data);
			}
			reader.endArray();
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return messages;
	}


	public static List<Comand> loadComData(){
		List<Comand> messages = new ArrayList<Comand>();
		File ff = new File(Comand.class.getName()+".json");
		if (!ff.exists())return messages;
		try {
			FileInputStream in = new FileInputStream(Comand.class.getName()+".json");
			JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));

			reader.beginArray();
			while (reader.hasNext()) {
				Comand data = new Gson().fromJson(reader, Comand.class);
				messages.add(data);
			}
			reader.endArray();
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return messages;
	}


	public static void saveData(Object data,String className){
		try {
			OutputStreamWriter file =
					new OutputStreamWriter( new FileOutputStream(className+".json"),"UTF-8");

//			FileWriter file = new FileWriter(className+".json");
			file.write(new Gson().toJson(data));
			file.flush();
			file.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static List<People> loadPeopleData() {
		List<People> messages = new ArrayList<People>();
		File ff = new File(People.class.getName()+".json");
		if (!ff.exists())return messages;
		try {
			FileInputStream in = new FileInputStream(People.class.getName()+".json");
			JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));  //utf8 cp1251

			reader.beginArray();
			while (reader.hasNext()) {
				People data = new Gson().fromJson(reader, People.class);
				messages.add(data);
			}
			reader.endArray();
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return messages;
	}


	public static List<PaarPeople> loadPaarPeopleData() {
		List<PaarPeople> messages = new ArrayList<PaarPeople>();
		File ff = new File(PaarPeople.class.getName()+".json");
		if (!ff.exists())return messages;
		try {
			FileInputStream in = new FileInputStream(PaarPeople.class.getName()+".json");
			JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));

			reader.beginArray();
			while (reader.hasNext()) {
				PaarPeople data = new Gson().fromJson(reader, PaarPeople.class);
				messages.add(data);
			}
			reader.endArray();
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return messages;
	}
}
