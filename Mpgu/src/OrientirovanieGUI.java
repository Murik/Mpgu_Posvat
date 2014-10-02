import Data.Comand;
import Data.PaarPeople;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: murik
 * Date: 05.10.12
 * Time: 18:52
 */


public class OrientirovanieGUI extends JDialog {
	private JButton StartButton;
	private JPanel panel1;
	private JButton StopButton;
	private JButton redrawButton;
	private JPanel contentPanel;
	private JButton redrawByPlacesButton;
	private JPanel comandPlace;
	private JCheckBox byPlacesCheckBox;


	JTable jtabOrders;
	JTable jTable;

	TableModel tm;
	TableModel tm1;


	String[] headingsCommandPlaces = { "Команда",
			"Сумма",
			"Место"
	};

	String[] headingsPaar = {"Номер",
			"Пара",
			"Время старта",
			"Время финиша",
			"Баллы",
			"Зачетное время",
			"Место"
	};

	public OrientirovanieGUI(final List<PaarPeople> paarPeoples, final List<Comand> comands) {
		setContentPane(contentPanel);
		setModal(true);
		redraw(paarPeoples);
		redrawCommandPlaces(paarPeoples,comands);
		contentPanel.setVisible(true);

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {

				DataWorker.saveData(paarPeoples, PaarPeople.class.getName());
				DataWorker.saveData(comands,Comand.class.getName());
//				System.out.println("save");
			}
		});
		StartButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				int[] rows = jtabOrders.getSelectedRows();
				if (rows.length==0)return;
				paarPeoples.get(rows[0]).setStartOrient(new Timestamp(System.currentTimeMillis()));
				DataWorker.saveData(paarPeoples, PaarPeople.class.getName());
				redrawAll(paarPeoples, comands);
			}
		});


		StopButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				int[] rows = jtabOrders.getSelectedRows();
				if (rows.length==0)return;
				paarPeoples.get(rows[0]).setFinishOrient(new Timestamp(System.currentTimeMillis()));
				DataWorker.saveData(paarPeoples, PaarPeople.class.getName());
				redrawAll(paarPeoples, comands);
			}
		});
//		redrawButton.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent actionEvent) {
//				redraw(paarPeoples);
//				redrawCommandPlaces(paarPeoples,comands);
//			}
//		});
//		redrawByPlacesButton.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent actionEvent) {
//				redrawByPlaces(paarPeoples);
//
//			}
//		});


		byPlacesCheckBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				redrawAll(paarPeoples,comands);
			}
		});
	}

	private void redrawAll(final List<PaarPeople> paarPeoples, final List<Comand> comands){
		if (byPlacesCheckBox.isSelected()){
			redrawByPlaces(paarPeoples);
		}else {
			redraw(paarPeoples);
		}
		redrawCommandPlaces(paarPeoples,comands);
	}


	private void redraw(final List<PaarPeople> paarPeoples){
		panel1.removeAll();
		panel1.revalidate();

		Object[][] data = new Object[paarPeoples.size()][7];

		HashMap<Integer,Integer> forSortBall = new HashMap<Integer, Integer>();
		HashMap<Integer,Integer> forSortTime = new HashMap<Integer, Integer>();
		HashMap<Integer,Integer> forSortPlace = new HashMap<Integer, Integer>();

		for (int i=0;i<paarPeoples.size();i++){
//			int minutesToPolosaPlusBall =(int)(minutesToPolosa + (long)(paarPeoples.get(i).getBallsOrientir()*0.5));
			data[i][0] = paarPeoples.get(i).getID();
			data[i][1] = paarPeoples.get(i);
			data[i][2] = paarPeoples.get(i).getStartOrient();
			data[i][3] = paarPeoples.get(i).getFinishOrient();
			data[i][4] = paarPeoples.get(i).getBallsOrientir();
			data[i][5] = paarPeoples.get(i).getMinutesToPolosa();
//			data[i][5] = minutesToPolosaPlusBall;

			forSortBall.put(i,paarPeoples.get(i).getBallsOrientir());
			forSortTime.put(i,paarPeoples.get(i).getMinutesToPolosa());
		}


		Sort.SortHM[] sortHMBall= Sort.sortHM(forSortBall);
		Integer ball =-1000;
		HashSet<Integer> hs = new HashSet<Integer>(forSortBall.values()); //штрафные баллы (чем больше ПК тем меньше 1000)
		int placeBall=(hs.size()+1)*1000;
		for (Sort.SortHM cin:sortHMBall){
			if(cin.ball>ball) {ball=cin.ball; placeBall =placeBall-1000;}
			forSortPlace.put(cin.comand, placeBall); // comand на самом деле id пар
		}

		Sort.SortHM[] sortHMTime= Sort.sortHM(forSortTime);
		Integer ballForTime =-1;
		int placeTime =0;
		for (Sort.SortHM cin:sortHMTime){
			if(cin.ball>ballForTime) {ballForTime=cin.ball; placeTime++;}  //штрафные баллы (чем больше ПК тем меньше 1000)
			forSortPlace.put(cin.comand, forSortPlace.get(cin.comand) + placeTime); // comand на самом деле id пар
		}

		Sort.SortHM[] sortHMPlace= Sort.sortHM(forSortPlace);
		Integer ballForPlace =-1;
		int place =0;
		for (Sort.SortHM cin:sortHMPlace){
			if(cin.ball>ballForPlace) {ballForPlace=cin.ball; place++;}
			data[cin.comand][6] = place;
			paarPeoples.get(cin.comand).setOrientPlace(place);
		}


		panel1.setLayout(new FlowLayout());
		jtabOrders = new JTable(data, headingsPaar);
		jtabOrders.setColumnSelectionAllowed(false);
		jtabOrders.setRowSelectionAllowed(true);


		JScrollPane jscrlp = new JScrollPane(jtabOrders);
		jtabOrders.setPreferredScrollableViewportSize(
				new Dimension(1000, 300));

		tm = jtabOrders.getModel();
		tm.addTableModelListener(new TableModelListener()
		{
			public void tableChanged(TableModelEvent tme)
			{
				if(tme.getType() == TableModelEvent.UPDATE)
				{
					PaarPeople paarPeople = paarPeoples.get(tme.getFirstRow());
					if(tme.getColumn() != 3) return;
					paarPeople.setBallsOrientir(Integer.parseInt(tm.getValueAt(tme.getFirstRow(),
							tme.getColumn()).toString()));
				}
			}
		});
		panel1.add(jscrlp);
		panel1.setVisible(true);
	}




	private void redrawByPlaces(final List<PaarPeople> paarPeoples){
		panel1.removeAll();
		panel1.revalidate();

		Object[][] data = new Object[paarPeoples.size()][7];

		HashMap<Integer,Integer> forSortPlace = new HashMap<Integer, Integer>();
		for (int i=0;i<paarPeoples.size();i++){
			 forSortPlace.put(i, paarPeoples.get(i).getOrientPlace());
		}

		Sort.SortHM[] sortHM= Sort.sortHM(forSortPlace);
		int pos=0;
		for (Sort.SortHM cin:sortHM){
			int i = cin.comand;
			int minutesToPolosa =(int) TimeUnit.MILLISECONDS.toMinutes(paarPeoples.get(i).getFinishOrient().getTime()-paarPeoples.get(i).getStartOrient().getTime());
			data[pos][0] = paarPeoples.get(i).getID();
			data[pos][1] = paarPeoples.get(i);
			data[pos][2] = paarPeoples.get(i).getStartOrient();
			data[pos][3] = paarPeoples.get(i).getFinishOrient();
			data[pos][4] = paarPeoples.get(i).getBallsOrientir();
			data[pos][5] = minutesToPolosa;
			data[pos][6] = paarPeoples.get(i).getOrientPlace();
			pos++;
		}

		panel1.setLayout(new FlowLayout());
		jtabOrders = new JTable(data, headingsPaar);
		jtabOrders.setColumnSelectionAllowed(false);
		jtabOrders.setRowSelectionAllowed(true);


		JScrollPane jscrlp = new JScrollPane(jtabOrders);
		jtabOrders.setPreferredScrollableViewportSize(
				new Dimension(1000, 300));

		tm = jtabOrders.getModel();
		tm.addTableModelListener(new TableModelListener()
		{
			public void tableChanged(TableModelEvent tme)
			{
				if(tme.getType() == TableModelEvent.UPDATE)
				{
					PaarPeople paarPeople = paarPeoples.get(tme.getFirstRow());
					if(tme.getColumn() != 3) return;
					paarPeople.setBallsOrientir(Integer.parseInt(tm.getValueAt(tme.getFirstRow(),
							tme.getColumn()).toString()));
				}
			}
		});
		panel1.add(jscrlp);
		panel1.setVisible(true);
	}




	private void redrawCommandPlaces(final List<PaarPeople> paarPeoples, final List<Comand> comands){
		comandPlace.removeAll();
		comandPlace.revalidate();

		Object[][] data = new Object[comands.size()][3];
		HashMap<Integer,Integer> forSortPlace = new HashMap<Integer, Integer>();

		for(int i=0;i<comands.size();i++){
			HashMap<Integer,Integer> forSortData = new HashMap<Integer, Integer>();
			for (int j=0;j<paarPeoples.size();j++){
				forSortData.put(j, paarPeoples.get(j).getOrientPlace());
			}
			Sort.SortHM[] sortHM= Sort.sortHM(forSortData);
			Comand comand = comands.get(i);
			int count=3;
			int pos = 0;
			forSortPlace.put(i,0);
//			comand.setOrientirovanie(0);
			for (Sort.SortHM cin:sortHM){
				pos = cin.comand;
				if (comand.getName().equals(paarPeoples.get(pos).getPeople1().getComandName()) && count!=0){
//					comand.setOrientirovanie(comand.getOrientirovanie()+paarPeoples.get(pos).getOrientPlace());
					forSortPlace.put(i,forSortPlace.get(i)+paarPeoples.get(pos).getOrientPlace());
					count--;
				}
			}
			while (count!=0) {
//				comand.setOrientirovanie(comand.getOrientirovanie()+paarPeoples.get(pos).getOrientPlace()+1);
				forSortPlace.put(i,forSortPlace.get(i)+paarPeoples.get(pos).getOrientPlace()+1);
				count--;
			}
		}

		Sort.SortHM[] sortHMPlace= Sort.sortHM(forSortPlace);
		Integer ballForPlace =-1;
		int place =0;
		for (Sort.SortHM cin:sortHMPlace){
			if(cin.ball>ballForPlace) {ballForPlace=cin.ball; place++;}
			comands.get(cin.comand).setOrientirovanie(place);
		}

		for(int i=0;i<comands.size();i++){
			data[i][0] = comands.get(i).getName();
			data[i][1] = forSortPlace.get(i);
			data[i][2] = comands.get(i).getOrientirovanie();
		}

		comandPlace.setLayout(new FlowLayout());
		jTable = new JTable(data, headingsCommandPlaces);
		jTable.setColumnSelectionAllowed(false);
		jTable.setRowSelectionAllowed(true);


		JScrollPane jscrlp = new JScrollPane(jTable);
		jTable.setPreferredScrollableViewportSize(
				new Dimension(200, 200));

		tm1 = jTable.getModel();
		tm1.addTableModelListener(new TableModelListener()
		{
			public void tableChanged(TableModelEvent tme)
			{
				if(tme.getType() == TableModelEvent.UPDATE)
				{
					Comand comand = comands.get(tme.getFirstRow());
					if(tme.getColumn() != 1) return;
					comand.setOrientirovanie(Integer.parseInt(tm.getValueAt(tme.getFirstRow(),
							tme.getColumn()).toString()));
				}
			}
		});
		comandPlace.add(jscrlp);
		comandPlace.setVisible(true);
	}

}



