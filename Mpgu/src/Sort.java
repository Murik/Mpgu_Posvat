import Data.PaarPeople;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: murik
 * Date: 04.10.12
 * Time: 16:01
 */
public class Sort {

//	private static void swap(int[] arr, int i, int j) {
//		int t = arr[i];
//		arr[i] = arr[j];
//		arr[j] = t;
//	}

//	public static void bubblesort(int[] arr){
//		for(int i = arr.length-1 ; i >= 0 ; i--){
//				for(int j = 0; j < i; j++) {
//					if(arr[j] > arr[j + 1])
//						swap(arr, j, j + 1);
//				}
//		}
//		for (int el:arr){
//
//		}
//	}

	public static void main(String[] args) {
		int[] arr = {0, 1, 1, 3, 4, 2, 3, 2, 4, -1};
//		bubblesort(arr);
//		System.out.println(Arrays.toString(arr));
		HashMap<Integer, Integer> hm = new HashMap<Integer, Integer>();
		for (int i = 0; i < arr.length; i++) {
			hm.put(i, arr[i]);
		}
		Sort.SortHM[] sortHMBall = Sort.sortHM(hm, "asc");
		for (Sort.SortHM cin : sortHMBall) {
			System.out.println(cin.comand + " | " + cin.ball);
		}
		System.out.println("!_-------------------------_!");
		Integer ball = -1000;
		HashSet<Integer> hs = new HashSet<Integer>(hm.values()); //todo unigue balls
		int placeBall = (hs.size() + 1) * 1000;
		for (Sort.SortHM cin : sortHMBall) {
			if (cin.ball > ball) {
				ball = cin.ball;
				placeBall = placeBall - 1000;
			}
//			forSortPlace.put(cin.comand, placeBall);
			System.out.println(cin.comand + " | " + placeBall);
		}

		System.out.println("!_-------------------------_!");

		sortHMBall = Sort.sortHM(hm, "desc");
		for (Sort.SortHM cin : sortHMBall) {
			System.out.println(cin.comand + " | " + cin.ball);
		}
		System.out.println("!_-------------------------_!");
		for (Sort.SortHM cin : sortHMBall) {
			System.out.println(cin.comand + " | " + cin.ball);
		}
}


//	private static void swap(PaarPeople[] arr, int i, int j) {
//		PaarPeople t = arr[i];
//		arr[i] = arr[j];
//		arr[j] = t;
//	}
//
//	public static void bubblesort(PaarPeople[] arr){
//		for(int i = arr.length-1 ; i >= 0 ; i--){
//			for(int j = 0 ; j < i ; j++){
//				if( arr[j].getPeople1().getComandName().length() > arr[j+1].getPeople1().getComandName().length())
//				{
//					swap(arr, j, j+1);
//				}
//			}
//		}
//	}


	public static SortHM[] sortHM(HashMap<Integer,Integer> forSort){
		return sortHM(forSort,"asc");
	}

	public static SortHM[] sortHM(HashMap<Integer,Integer> forSort, String sortOrder){
		int index = forSort.size();
		SortHM[] ci = new SortHM[index];

		index = 0;
		for (Map.Entry<Integer, Integer> entry : forSort.entrySet()) {
			if (sortOrder.equals("asc")) {
				ci[index++] = new SortHM_Asc(entry.getKey(), entry.getValue());
			} else {
				ci[index++] = new SortHM_Desc(entry.getKey(), entry.getValue());
			}
		}
		Arrays.sort(ci);
		return ci;
	}


	public static class SortHM {
		public Integer comand;
		public Integer ball;

		private SortHM(Integer comand, Integer ball) {
			this.comand = comand;
			this.ball = ball;
		}
	}

	public static class SortHM_Asc extends SortHM implements Comparable {


		private SortHM_Asc(Integer comand, Integer ball) {
			super(comand, ball);
		}

		public int compareTo(Object o) {
			if (o instanceof SortHM) {
				final int diff = ball - ((SortHM_Asc) o).ball;
				return diff < 0 ? -1 : (diff > 0 ? 1 : 0);
			} else {
				return 0;
			}
		}
	}

	public static class SortHM_Desc extends SortHM implements Comparable {


		private SortHM_Desc(Integer comand, Integer ball) {
			super(comand, ball);
		}

		public int compareTo(Object o) {
			if (o instanceof SortHM) {
				final int diff = ((SortHM_Desc) o).ball - ball;
				return diff < 0 ? -1 : (diff > 0 ? 1 : 0);
			} else {
				return 0;
			}
		}
	}
}
