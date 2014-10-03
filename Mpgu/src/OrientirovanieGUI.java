import Data.Comand;
import Data.Facultet;
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

/**
 * Created with IntelliJ IDEA.
 * User: murik
 * Date: 05.10.12
 * Time: 18:52
 */


public class OrientirovanieGUI extends JDialog {
	private JButton StartButton;
	private JPanel paarPanel;
	private JButton StopButton;
	private JPanel contentPanel;
	private JPanel comandPlace;
	private JCheckBox byPlacesCheckBox;
	private JPanel facultetPlace;


	JTable orientirPaarTable;
	JTable comandRezultTable;
	JTable facultetRezultTable;




	public OrientirovanieGUI() {
		setTitle(MpguMetaInfo.polosaTitle);
		setContentPane(contentPanel);
		setModal(true);
		redraw();
		contentPanel.setVisible(true);

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {

				DataWorker.saveData(Mpgu_slet.paarPeoples, PaarPeople.class.getName());
				DataWorker.saveData(Mpgu_slet.comanda, Comand.class.getName());
				DataWorker.saveData(Mpgu_slet.facultet, Facultet.class.getName());
//				System.out.println("save");
			}
		});

		StartButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				int[] rows = orientirPaarTable.getSelectedRows();
				if (rows.length==0){ JOptionPane.showMessageDialog(null, "Не выбрано"); return;}
				Mpgu_slet.paarPeoples.get(rows[0]).setStartOrient(new Timestamp(System.currentTimeMillis()));
				redraw();
			}
		});


		StopButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				int[] rows = orientirPaarTable.getSelectedRows();
				if (rows.length==0){ JOptionPane.showMessageDialog(null, "Не выбрано"); return;}
				Mpgu_slet.paarPeoples.get(rows[0]).setFinishOrient(new Timestamp(System.currentTimeMillis()));
				redraw();
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
				redraw();
			}
		});
	}


	private Sort.SortHM[] sortForBall(){
		HashMap<Integer,Integer> forSortBall = new HashMap<Integer, Integer>();
		HashMap<Integer,Integer> forSortTime = new HashMap<Integer, Integer>();
		HashMap<Integer,Integer> forSortPlace = new HashMap<Integer, Integer>();
		for (int i=0;i<Mpgu_slet.paarPeoples.size();i++){
			forSortBall.put(i,Mpgu_slet.paarPeoples.get(i).getBallsOrientir());
			forSortTime.put(i,Mpgu_slet.paarPeoples.get(i).getMinutesToPolosa());
		}
		Sort.SortHM[] sortHMBall= Sort.sortHM(forSortBall);
		Integer ball =-1000;
		HashSet<Integer> hs = new HashSet<Integer>(forSortBall.values()); //штрафные баллы (чем больше КП тем меньше 1000)
		int placeBall=(hs.size()+1)*1000;
		for (Sort.SortHM cin:sortHMBall){
			if(cin.ball>ball) {ball=cin.ball; placeBall =placeBall-1000;}
			forSortPlace.put(cin.key, placeBall); // key на самом деле id пар
		}

		Sort.SortHM[] sortHMTime= Sort.sortHM(forSortTime);
		Integer ballForTime =-1;
		int placeTime =0;
		for (Sort.SortHM cin:sortHMTime){
			if(cin.ball>ballForTime) {ballForTime=cin.ball; placeTime++;} // по времени во возрастанию
			forSortPlace.put(cin.key, forSortPlace.get(cin.key) + placeTime); // key на самом деле id пар
		}

		Sort.SortHM[] sortHMPlace= Sort.sortHM(forSortPlace);
		Integer ballForPlace =-1;
		int place =0;
		for (Sort.SortHM cin:sortHMPlace){
			if(cin.ball>ballForPlace) {ballForPlace=cin.ball; place++;}
			Mpgu_slet.paarPeoples.get(cin.key).setOrientPlace(place);
		}
		return sortHMPlace;
	}



	private void redraw(){
		paarPanel.removeAll();
		paarPanel.revalidate();

		Object[][] data = new Object[Mpgu_slet.paarPeoples.size()][7];
		Sort.SortHM[] sortHM= sortForBall();
		if (!byPlacesCheckBox.isSelected()) {
			for (int i = 0; i < Mpgu_slet.paarPeoples.size(); i++) {
				data[i][0] = Mpgu_slet.paarPeoples.get(i).getID();
				data[i][1] = Mpgu_slet.paarPeoples.get(i);
				data[i][2] = Mpgu_slet.paarPeoples.get(i).getStartOrient();
				data[i][3] = Mpgu_slet.paarPeoples.get(i).getFinishOrient();
				data[i][4] = Mpgu_slet.paarPeoples.get(i).getBallsOrientir();
				data[i][5] = Mpgu_slet.paarPeoples.get(i).getMinutesToPolosa();
				data[i][6] = Mpgu_slet.paarPeoples.get(i).getOrientPlace();
			}
		}else {
			int pos=0;
			for (Sort.SortHM cin:sortHM){
				int i = cin.key;
				data[pos][0] = Mpgu_slet.paarPeoples.get(i).getID();
				data[pos][1] = Mpgu_slet.paarPeoples.get(i);
				data[pos][2] = Mpgu_slet.paarPeoples.get(i).getStartOrient();
				data[pos][3] = Mpgu_slet.paarPeoples.get(i).getFinishOrient();
				data[pos][4] = Mpgu_slet.paarPeoples.get(i).getBallsOrientir();
				data[pos][5] = Mpgu_slet.paarPeoples.get(i).getMinutesToPolosa();
				data[pos][6] = Mpgu_slet.paarPeoples.get(i).getOrientPlace();
				pos++;
			}

		}

		paarPanel.setLayout(new FlowLayout());
		orientirPaarTable = new JTable(data, MpguMetaInfo.headingsPaar);
		orientirPaarTable.setColumnSelectionAllowed(false);
		orientirPaarTable.setRowSelectionAllowed(true);


		JScrollPane jscrlp = new JScrollPane(orientirPaarTable);
		orientirPaarTable.setPreferredScrollableViewportSize(
				new Dimension(1000, 300));

		final TableModel tm = orientirPaarTable.getModel();
		tm.addTableModelListener(new TableModelListener()
		{
			public void tableChanged(TableModelEvent tme)
			{
				if(tme.getType() == TableModelEvent.UPDATE)
				{
					PaarPeople paarPeople = Mpgu_slet.paarPeoples.get(tme.getFirstRow());
					if(tme.getColumn() != 4) { JOptionPane.showMessageDialog(null, "Нельзя править"); return;}
					paarPeople.setBallsOrientir(Integer.parseInt(tm.getValueAt(tme.getFirstRow(),
							tme.getColumn()).toString()));
					redraw();
				}
			}
		});
		paarPanel.add(jscrlp);
		redrawCommandPlaces(sortHM);
		redrawFacultetPlaces(sortHM);
	}

	private void redrawCommandPlaces(Sort.SortHM[] sortHM){
		comandPlace.removeAll();
		comandPlace.revalidate();

		Object[][] data = new Object[Mpgu_slet.comanda.size()][3];


		HashMap<Integer,Integer> forSortPlace = new HashMap<Integer, Integer>();

		for(int i=0;i<Mpgu_slet.comanda.size();i++){

			Comand comand = Mpgu_slet.comanda.get(i);
			int count=2;
			int pos = 0;
			forSortPlace.put(i,0);
			for (Sort.SortHM cin:sortHM){
				pos = cin.key;
				if (comand.getName().equals(Mpgu_slet.paarPeoples.get(pos).getPeople1().getComandName()) && count!=0){
					forSortPlace.put(i,forSortPlace.get(i)+Mpgu_slet.paarPeoples.get(pos).getOrientPlace());
					count--;
				}
			}
			while (count!=0) {
				forSortPlace.put(i,forSortPlace.get(i)+Mpgu_slet.paarPeoples.get(pos).getOrientPlace()+1);
				count--;
			}
		}

		Sort.SortHM[] sortHMPlace= Sort.sortHM(forSortPlace);
		Integer ballForPlace =-1;
		int place =0;
		for (Sort.SortHM cin:sortHMPlace){
			if(cin.ball>ballForPlace) {ballForPlace=cin.ball; place++;}
			Mpgu_slet.comanda.get(cin.key).setOrientirovaniePlace(place);
		}

		for(int i=0;i<Mpgu_slet.comanda.size();i++){
			data[i][0] = Mpgu_slet.comanda.get(i);
			data[i][1] = forSortPlace.get(i);
			data[i][2] = Mpgu_slet.comanda.get(i).getOrientirovaniePlace();
		}

		comandPlace.setLayout(new FlowLayout());
		comandRezultTable = new JTable(data, MpguMetaInfo.headingsCommandPlacesOrient);
		comandRezultTable.setColumnSelectionAllowed(false);
		comandRezultTable.setRowSelectionAllowed(true);


		JScrollPane jscrlp = new JScrollPane(comandRezultTable);
		comandRezultTable.setPreferredScrollableViewportSize(
				new Dimension(200, 100));

		final TableModel tm = comandRezultTable.getModel();
		tm.addTableModelListener(new TableModelListener() {
			public void tableChanged(TableModelEvent tme) {
				if (tme.getType() == TableModelEvent.UPDATE) {
					Comand comand = Mpgu_slet.comanda.get(tme.getFirstRow());
					if (tme.getColumn() != 2) {
						JOptionPane.showMessageDialog(null, "Нельзя править");
						return;
					}
					comand.setOrientirovaniePlace(Integer.parseInt(tm.getValueAt(tme.getFirstRow(),
							tme.getColumn()).toString()));
				}
			}
		});
		comandPlace.add(jscrlp);
	}

	private void redrawFacultetPlaces(Sort.SortHM[] sortHM){
		facultetPlace.removeAll();
		facultetPlace.revalidate();

		Object[][] data = new Object[Mpgu_slet.facultet.size()][3];


		HashMap<Integer,Integer> forSortPlace = new HashMap<Integer, Integer>();

		for(int i=0;i<Mpgu_slet.facultet.size();i++){

			Facultet facultet = Mpgu_slet.facultet.get(i);
			int count=3;
			int pos = 0;
			forSortPlace.put(i,0);
			for (Sort.SortHM cin:sortHM){
				pos = cin.key;
				if (facultet.getName().equals(Mpgu_slet.paarPeoples.get(pos).getPeople1().getFacultetName()) && count!=0){
					forSortPlace.put(i,forSortPlace.get(i)+Mpgu_slet.paarPeoples.get(pos).getOrientPlace());
					count--;
				}
			}
			while (count!=0) {
				forSortPlace.put(i,forSortPlace.get(i)+Mpgu_slet.paarPeoples.get(pos).getOrientPlace()+1);
				count--;
			}
		}

		Sort.SortHM[] sortHMPlace= Sort.sortHM(forSortPlace);
		Integer ballForPlace =-1;
		int place =0;
		for (Sort.SortHM cin:sortHMPlace){
			if(cin.ball>ballForPlace) {ballForPlace=cin.ball; place++;}
			Mpgu_slet.facultet.get(cin.key).setOrientirovaniePlace(place);
		}

		for(int i=0;i<Mpgu_slet.facultet.size();i++){
			data[i][0] = Mpgu_slet.facultet.get(i);
			data[i][1] = forSortPlace.get(i);
			data[i][2] = Mpgu_slet.facultet.get(i).getOrientirovaniePlace();
		}

		facultetPlace.setLayout(new FlowLayout());
		facultetRezultTable = new JTable(data, MpguMetaInfo.headingsFacultetPlacesOrient);
		facultetRezultTable.setColumnSelectionAllowed(false);
		facultetRezultTable.setRowSelectionAllowed(true);


		JScrollPane jscrlp = new JScrollPane(facultetRezultTable);
		facultetRezultTable.setPreferredScrollableViewportSize(
				new Dimension(200, 100));

		final TableModel tm = facultetRezultTable.getModel();
		tm.addTableModelListener(new TableModelListener() {
			public void tableChanged(TableModelEvent tme) {
				if (tme.getType() == TableModelEvent.UPDATE) {
					Comand comand = Mpgu_slet.comanda.get(tme.getFirstRow());
					if (tme.getColumn() != 2) {
						JOptionPane.showMessageDialog(null, "Нельзя править");
						return;
					}
					comand.setOrientirovaniePlace(Integer.parseInt(tm.getValueAt(tme.getFirstRow(),
							tme.getColumn()).toString()));
				}
			}
		});
		facultetPlace.add(jscrlp);
	}


}



