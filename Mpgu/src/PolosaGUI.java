import Data.Comand;
import Data.Facultet;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: murik
 * Date: 04.10.12
 * Time: 19:04
 */

public class PolosaGUI extends JDialog{
	private JPanel comandPanel;
	private JButton StartButton;
	private JButton StopButton;
	private JPanel contentPanel;
	private JPanel facPanel;
	JTable polosaComandTable;
	JTable polosaFacultetTable;


	public PolosaGUI() {
		setTitle(MpguMetaInfo.polosaTitle);
		setContentPane(contentPanel);
		setModal(true);
		redraw();
		contentPanel.setVisible(true);

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				DataWorker.saveData(Mpgu_slet.comanda, Comand.class.getName());
				DataWorker.saveData(Mpgu_slet.facultet, Facultet.class.getName());
			}
		});

		StartButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				int[] rows = polosaComandTable.getSelectedRows();
				if (rows.length==0){ JOptionPane.showMessageDialog(null, "Не выбрано"); return;}
				Mpgu_slet.comanda.get(rows[0]).setStartPolosa(new Timestamp(System.currentTimeMillis()));
				redraw();
			}
		});


		StopButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				int[] rows = polosaComandTable.getSelectedRows();
				if (rows.length==0){ JOptionPane.showMessageDialog(null, "Не выбрано"); return;}
				Mpgu_slet.comanda.get(rows[0]).setFinishPolosa(new Timestamp(System.currentTimeMillis()));
				redraw();
			}
		});
	}


	private void redraw(){
		comandPanel.removeAll();
		comandPanel.revalidate();

		Object[][] data = new Object[Mpgu_slet.comanda.size()][7];
		HashMap<Integer,Integer> forSort = new HashMap<Integer, Integer>();
		for (int i=0;i<Mpgu_slet.comanda.size();i++){
			Comand comand = Mpgu_slet.comanda.get(i);
			int minutesToPolosa =(int) TimeUnit.MILLISECONDS.toMinutes(comand.getFinishPolosa().getTime()-comand.getStartPolosa().getTime());
			int minutesToPolosaPlusBall =(int)(minutesToPolosa + (long)(comand.getBallsPolosa()*0.5));
			data[i][0] = comand;
			data[i][1] = comand.getStartPolosa();
			data[i][2] = comand.getFinishPolosa();
			data[i][3] = comand.getBallsPolosa();
			data[i][4] = minutesToPolosa;
			data[i][5] = minutesToPolosaPlusBall;
			forSort.put(i,minutesToPolosaPlusBall);
		}

		for(Facultet facultet:Mpgu_slet.facultet){
				facultet.setPolosaBestComandPlace(Mpgu_slet.facultet.size()+1);
		}

		Sort.SortHM[] sortHM= Sort.sortHM(forSort);
		Integer ball =-1;
		int place =0;
		for (Sort.SortHM cin:sortHM){
			if(cin.ball>ball) {ball=cin.ball; place++;}
			data[cin.key][6] = place;
			Mpgu_slet.comanda.get(cin.key).setPolosaPlace(place);
			String facultetName = Mpgu_slet.comanda.get(cin.key).getFacultetName();
			for(Facultet facultet:Mpgu_slet.facultet){
				if(facultet.getName().equals(facultetName)){
					facultet.setPolosaBestComandPlace(place);
				}
			}
		}

		comandPanel.setLayout(new FlowLayout());
		polosaComandTable = new JTable(data, MpguMetaInfo.headingsPolosa);
		polosaComandTable.setColumnSelectionAllowed(false);
		polosaComandTable.setRowSelectionAllowed(true);


		JScrollPane jscrlp = new JScrollPane(polosaComandTable);
		polosaComandTable.setPreferredScrollableViewportSize(
				new Dimension(1000, 200));

		final TableModel tm = polosaComandTable.getModel();
		tm.addTableModelListener(new TableModelListener()
		{
			public void tableChanged(TableModelEvent tme)
			{
				if(tme.getType() == TableModelEvent.UPDATE)
				{
					Comand comand = Mpgu_slet.comanda.get(tme.getFirstRow());
					if(tme.getColumn() != 3) { JOptionPane.showMessageDialog(null, "Нельзя править"); return;}
					comand.setBallsPolosa(Integer.parseInt(tm.getValueAt(tme.getFirstRow(),
							tme.getColumn()).toString()));
					redraw();
				}
			}
		});
		comandPanel.add(jscrlp);
		DataWorker.saveData(Mpgu_slet.comanda, Comand.class.getName());
		redrawFacultet();
	}

	private void redrawFacultet(){
		facPanel.removeAll();
		facPanel.revalidate();

		Object[][] data = new Object[Mpgu_slet.facultet.size()][3];
		HashMap<Integer,Integer> forSort = new HashMap<Integer, Integer>();
		for (int i=0;i<Mpgu_slet.facultet.size();i++){
			Facultet facultet = Mpgu_slet.facultet.get(i);
			data[i][0] = facultet;
			data[i][1] = facultet.getPolosaBestComandPlace();
//			data[i][2] = facultet.getPolosaPlace();
			forSort.put(i,facultet.getPolosaBestComandPlace());
		}

		Sort.SortHM[] sortHM= Sort.sortHM(forSort);
		Integer ball =-1;
		int place =0;
		for (Sort.SortHM cin:sortHM){
			if(cin.ball>ball) {ball=cin.ball; place++;}
			data[cin.key][2] = place;
			Mpgu_slet.facultet.get(cin.key).setPolosaPlace(place);
		}

		facPanel.setLayout(new FlowLayout());
		polosaFacultetTable = new JTable(data, MpguMetaInfo.headingsPolosaFac);
		polosaFacultetTable.setColumnSelectionAllowed(false);
		polosaFacultetTable.setRowSelectionAllowed(true);


		JScrollPane jscrlp = new JScrollPane(polosaFacultetTable);
		polosaFacultetTable.setPreferredScrollableViewportSize(
				new Dimension(300, 100));

		final TableModel tm = polosaFacultetTable.getModel();
		tm.addTableModelListener(new TableModelListener()
		{
			public void tableChanged(TableModelEvent tme)
			{
				if(tme.getType() == TableModelEvent.UPDATE)
				{
					Facultet facultet = Mpgu_slet.facultet.get(tme.getFirstRow());
					if(tme.getColumn() != 2) return;
					facultet.setPolosaPlace(Integer.parseInt(tm.getValueAt(tme.getFirstRow(),
							tme.getColumn()).toString()));
				}
			}
		});
		facPanel.add(jscrlp);
		DataWorker.saveData(Mpgu_slet.facultet, Facultet.class.getName());
	}
}
