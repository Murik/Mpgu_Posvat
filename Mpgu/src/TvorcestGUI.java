import Data.Comand;
import Data.Facultet;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created with IntelliJ IDEA.
 * User: murik
 * Date: 04.10.12
 * Time: 7:17
 */

public class TvorcestGUI extends JDialog{
	private JPanel panel1;
	private JPanel comPanel;
	private JPanel facPanel;


	public TvorcestGUI() {
		setTitle(MpguMetaInfo.tvorTitle);
		setContentPane(panel1);
		setModal(true);
		redrawCommand();
		redrawFacultet();
		panel1.setVisible(true);


		panel1.setVisible(true);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				DataWorker.saveData(Mpgu_slet.comanda, Comand.class.getName());
				DataWorker.saveData(Mpgu_slet.facultet, Facultet.class.getName());
			}
		});
	}



	private void redrawCommand(){
		comPanel.removeAll();
		comPanel.revalidate();
		Object[][] data = new Object[Mpgu_slet.comanda.size()][6];
		HashMap<Integer,Integer> forSort = new HashMap<Integer, Integer>();
		for (int i=0;i<Mpgu_slet.comanda.size();i++){
			int summ = 0;
			for(int ball:Mpgu_slet.comanda.get(i).getTvorcestvo()){summ+=ball;}
			data[i][0] = Mpgu_slet.comanda.get(i).getName();
			data[i][1] = Mpgu_slet.comanda.get(i).getTvorcestvo()[0];
			data[i][2] = Mpgu_slet.comanda.get(i).getTvorcestvo()[1];
			data[i][3] = Mpgu_slet.comanda.get(i).getTvorcestvo()[2];
			data[i][4] = summ;
			forSort.put(i,summ);
		}

		Sort.SortHM[] sortHM= Sort.sortHM(forSort);
		Integer ball =-1;
		HashSet hs = new HashSet(forSort.values());
		int place=hs.size()+1;
		for (Sort.SortHM cin:sortHM){
			if(cin.ball>ball) {ball=cin.ball; place--;}
			data[cin.key][5] = place;
			Mpgu_slet.comanda.get(cin.key).setTvorcestvoPlace(place);
		}


		comPanel.setLayout(new FlowLayout());
		JTable tvorComandTable = new JTable(data, MpguMetaInfo.headingsTvorCommand);
		JScrollPane jscrlp = new JScrollPane(tvorComandTable);
		tvorComandTable.setPreferredScrollableViewportSize(new Dimension(1000, 200));
		final TableModel tm = tvorComandTable.getModel();
		tm.addTableModelListener(new TableModelListener()
		{
			public void tableChanged(TableModelEvent tme)
			{
				if(tme.getType() == TableModelEvent.UPDATE)
				{
					Comand comand = Mpgu_slet.comanda.get(tme.getFirstRow());
					if(tme.getColumn() == 0) { JOptionPane.showMessageDialog(null, "Нельзя править"); return;}
					if(tme.getColumn() == 5) {
						comand.setTvorcestvoPlace(Integer.parseInt(tm.getValueAt(tme.getFirstRow(),
								tme.getColumn()).toString()));
						return;
					}
					comand.getTvorcestvo()[tme.getColumn() - 1] = Integer.parseInt(tm.getValueAt(tme.getFirstRow(),
							tme.getColumn()).toString());
					redrawCommand();
				}
			}
		});
		comPanel.add(jscrlp);
		DataWorker.saveData(Mpgu_slet.comanda, Comand.class.getName());
	}

	private void redrawFacultet() {
		facPanel.removeAll();
		facPanel.revalidate();
		Object[][] data = new Object[Mpgu_slet.facultet.size()][6];
		HashMap<Integer,Integer> forSort = new HashMap<Integer, Integer>();
		for (int i=0;i<Mpgu_slet.facultet.size();i++){
			int summ = 0;
			for(int ball:Mpgu_slet.facultet.get(i).getTvorcestvo()){summ+=ball;}
			data[i][0] = Mpgu_slet.facultet.get(i).getName();
			data[i][1] = Mpgu_slet.facultet.get(i).getTvorcestvo()[0];
			data[i][2] = Mpgu_slet.facultet.get(i).getTvorcestvo()[1];
			data[i][3] = Mpgu_slet.facultet.get(i).getTvorcestvo()[2];
			data[i][4] = summ;
			forSort.put(i,summ);
		}

		Sort.SortHM[] sortHM= Sort.sortHM(forSort);
		Integer ball =-1;
		HashSet hs = new HashSet(forSort.values());
		int place=hs.size()+1;
		for (Sort.SortHM cin:sortHM){
			if(cin.ball>ball) {ball=cin.ball; place--;}
			data[cin.key][5] = place;
			Mpgu_slet.facultet.get(cin.key).setTvorcestvoPlace(place);
		}


		facPanel.setLayout(new FlowLayout());
		JTable tvorFacultetTable = new JTable(data, MpguMetaInfo.headingsTvorFacultet);
		JScrollPane jscrlp = new JScrollPane(tvorFacultetTable);
		tvorFacultetTable.setPreferredScrollableViewportSize(new Dimension(1000, 200));
		final TableModel tm = tvorFacultetTable.getModel();
		tm.addTableModelListener(new TableModelListener()
		{
			public void tableChanged(TableModelEvent tme)
			{
				if(tme.getType() == TableModelEvent.UPDATE)
				{
					Facultet facultet = Mpgu_slet.facultet.get(tme.getFirstRow());
					if(tme.getColumn() == 0) { JOptionPane.showMessageDialog(null, "Нельзя править"); return;}
					if(tme.getColumn() == 5) {
						facultet.setTvorcestvoPlace(Integer.parseInt(tm.getValueAt(tme.getFirstRow(),
								tme.getColumn()).toString()));
						return;
					}
					facultet.getTvorcestvo()[tme.getColumn() - 1] = Integer.parseInt(tm.getValueAt(tme.getFirstRow(),
							tme.getColumn()).toString());
					redrawFacultet();
				}
			}
		});
		facPanel.add(jscrlp);
		DataWorker.saveData(Mpgu_slet.facultet, Facultet.class.getName());

	}

}
