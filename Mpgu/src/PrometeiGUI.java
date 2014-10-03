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
 * Time: 6:55
 */

public class PrometeiGUI extends JDialog{
	private JPanel panel1;
	private JPanel facPanel;
	private JPanel comPanel;
	private JTable promComandTable;
	private JTable promFacultetTable;

	public PrometeiGUI() {
		setContentPane(panel1);
		setModal(true);
		redrawCommand();
		redrawFacultet();
		panel1.setVisible(true);

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {

				DataWorker.saveData(Mpgu_slet.comanda, Comand.class.getName());
				DataWorker.saveData(Mpgu_slet.facultet, Facultet.class.getName());
				System.out.println("save");
			}
		});
	}


	private void redrawCommand(){
		comPanel.removeAll();
		comPanel.revalidate();
		Object[][] data = new Object[Mpgu_slet.comanda.size()][3];
		HashMap<Integer,Integer> forSort = new HashMap<Integer, Integer>();
		for (int i=0;i<Mpgu_slet.comanda.size();i++){
			data[i][0] = Mpgu_slet.comanda.get(i);
			data[i][1] = Mpgu_slet.comanda.get(i).getPrometei();
			forSort.put(i,Mpgu_slet.comanda.get(i).getPrometei());
		}

		Sort.SortHM[] sortHM= Sort.sortHM(forSort);
		Integer ball =-1;
		HashSet hs = new HashSet(forSort.values());
		int place=hs.size()+1;
		for (Sort.SortHM cin:sortHM){
			if(cin.ball>ball) {ball=cin.ball; place--;}
			data[cin.key][2] = place;
			Mpgu_slet.comanda.get(cin.key).setPrometeiPlace(place);
		}

		comPanel.setLayout(new FlowLayout());
		promComandTable = new JTable(data, MpguMetaInfo.headingsPrometeiCommand);
		JScrollPane jscrlp = new JScrollPane(promComandTable);
		promComandTable.setPreferredScrollableViewportSize(
				new Dimension(400, 150));

		final TableModel tm = promComandTable.getModel();
		tm.addTableModelListener(new TableModelListener()
		{
			public void tableChanged(TableModelEvent tme)
			{
				if(tme.getType() == TableModelEvent.UPDATE)
				{
					Comand comand = Mpgu_slet.comanda.get(tme.getFirstRow());
					if(tme.getColumn() == 0) { JOptionPane.showMessageDialog(null, "Нельзя править"); return;}
					if(tme.getColumn() == 2) {
						comand.setPrometeiPlace(Integer.parseInt(tm.getValueAt(tme.getFirstRow(),
								tme.getColumn()).toString()));
						return;
					}
					comand.setPrometei(Integer.parseInt(tm.getValueAt(tme.getFirstRow(),
							tme.getColumn()).toString()));
					redrawCommand();
				}
			}
		});

		comPanel.add(jscrlp);
		DataWorker.saveData(Mpgu_slet.comanda, Comand.class.getName());
	}

	private void redrawFacultet(){
		facPanel.removeAll();
		facPanel.revalidate();
		Object[][] data = new Object[Mpgu_slet.facultet.size()][3];
		HashMap<Integer,Integer> forSort = new HashMap<Integer, Integer>();
		for (int i=0;i<Mpgu_slet.facultet.size();i++){
			data[i][0] = Mpgu_slet.facultet.get(i);
			data[i][1] = Mpgu_slet.facultet.get(i).getPrometei();
			forSort.put(i,Mpgu_slet.facultet.get(i).getPrometei());
		}

		Sort.SortHM[] sortHM= Sort.sortHM(forSort);
		Integer ball =-1;
		HashSet hs = new HashSet(forSort.values());
		int place=hs.size()+1;
		for (Sort.SortHM cin:sortHM){
			if(cin.ball>ball) {ball=cin.ball; place--;}
			data[cin.key][2] = place;
			Mpgu_slet.facultet.get(cin.key).setPrometeiPlace(place);
		}

		facPanel.setLayout(new FlowLayout());
		promFacultetTable = new JTable(data, MpguMetaInfo.headingsPrometeiFacultet);
		JScrollPane jscrlp = new JScrollPane(promFacultetTable);
		promFacultetTable.setPreferredScrollableViewportSize(
				new Dimension(400, 150));

		final TableModel tm = promFacultetTable.getModel();
		tm.addTableModelListener(new TableModelListener()
		{
			public void tableChanged(TableModelEvent tme)
			{
				if(tme.getType() == TableModelEvent.UPDATE)
				{
					Facultet facultet = Mpgu_slet.facultet.get(tme.getFirstRow());
					if(tme.getColumn() == 0) { JOptionPane.showMessageDialog(null, "Нельзя править"); return;}
					if(tme.getColumn() == 2) {
						facultet.setPrometeiPlace(Integer.parseInt(tm.getValueAt(tme.getFirstRow(),
								tme.getColumn()).toString()));
						return;
					}
					facultet.setPrometei(Integer.parseInt(tm.getValueAt(tme.getFirstRow(),
							tme.getColumn()).toString()));
					redrawFacultet();
				}
			}
		});

		facPanel.add(jscrlp);
		DataWorker.saveData(Mpgu_slet.facultet, Facultet.class.getName());
	}


}
