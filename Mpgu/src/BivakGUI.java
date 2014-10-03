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

/**
 * Created with IntelliJ IDEA.
 * User: murik
 * Date: 04.10.12
 * Time: 4:55
 */
public class BivakGUI extends JDialog {
	private JPanel panel1;
	private JPanel comPanel;
	private JPanel facPanel;


	public BivakGUI() {
		setTitle(MpguMetaInfo.bivakTitle);
		setContentPane(panel1);
		setModal(true);
		redrawCommand();
		redrawFacultet();
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
		Object[][] data = new Object[Mpgu_slet.comanda.size()][8];
		HashMap<Integer,Integer> forSort = new HashMap<Integer, Integer>();
		for (int i=0;i<Mpgu_slet.comanda.size();i++){
			data[i][0] = Mpgu_slet.comanda.get(i);
			data[i][1] = Mpgu_slet.comanda.get(i).getBivak()[0];
			data[i][2] = Mpgu_slet.comanda.get(i).getBivak()[1];
			data[i][3] = Mpgu_slet.comanda.get(i).getBivak()[2];
			data[i][4] = Mpgu_slet.comanda.get(i).getBivak()[3];
			data[i][5] = Mpgu_slet.comanda.get(i).getBivak()[4];
			data[i][6] = Mpgu_slet.comanda.get(i).getBivakSummBalls();
			forSort.put(i,Mpgu_slet.comanda.get(i).getBivakSummBalls());
		}

		Sort.SortHM[] sortHM= Sort.sortHM(forSort);
		Integer ball =-1;
		int place =0;
		for (Sort.SortHM cin:sortHM){
			if(cin.ball>ball) {ball=cin.ball; place++;}  //если баллы одинаковые
			data[cin.key][7] = place;
			Mpgu_slet.comanda.get(cin.key).setBivakPlace(place);
		}

		comPanel.setLayout(new FlowLayout());
		JTable bivakComandTable = new JTable(data, MpguMetaInfo.headingsBivakCommand);
		JScrollPane jscrlp = new JScrollPane(bivakComandTable);
		bivakComandTable.setPreferredScrollableViewportSize(
				new Dimension(1000, 200));

		final TableModel tm = bivakComandTable.getModel();
		tm.addTableModelListener(new TableModelListener() {
			public void tableChanged(TableModelEvent tme) {
				if(tme.getType() == TableModelEvent.UPDATE) {

					if(tme.getColumn() == 0) { JOptionPane.showMessageDialog(null, "Нельзя править"); return;}
					Comand comand = Mpgu_slet.comanda.get(tme.getFirstRow());
					if(tme.getColumn() == 7) {
						comand.setBivakPlace(Integer.parseInt(tm.getValueAt(tme.getFirstRow(),
								tme.getColumn()).toString()));
						return;
					}
					comand.setBivak(tme.getColumn() - 1,Integer.parseInt(tm.getValueAt(tme.getFirstRow(),
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
		Object[][] data = new Object[Mpgu_slet.facultet.size()][8];
		HashMap<Integer,Integer> forSort = new HashMap<Integer, Integer>();
		for (int i=0;i<Mpgu_slet.facultet.size();i++){
			data[i][0] = Mpgu_slet.facultet.get(i);
			data[i][1] = Mpgu_slet.facultet.get(i).getBivak()[0];
			data[i][2] = Mpgu_slet.facultet.get(i).getBivak()[1];
			data[i][3] = Mpgu_slet.facultet.get(i).getBivak()[2];
			data[i][4] = Mpgu_slet.facultet.get(i).getBivak()[3];
			data[i][5] = Mpgu_slet.facultet.get(i).getBivak()[4];
			data[i][6] = Mpgu_slet.facultet.get(i).getBivakSummBalls();
			forSort.put(i,Mpgu_slet.comanda.get(i).getBivakSummBalls());
		}

		Sort.SortHM[] sortHM= Sort.sortHM(forSort);
		Integer ball =-1;
		int place =0;
		for (Sort.SortHM cin:sortHM){
			if(cin.ball>ball) {ball=cin.ball; place++;}  //если баллы одинаковые
			data[cin.key][7] = place;
			Mpgu_slet.facultet.get(cin.key).setBivakPlace(place);
		}

		facPanel.setLayout(new FlowLayout());
		JTable bivakFacultetTable = new JTable(data, MpguMetaInfo.headingsBivakFacultet);
		JScrollPane jscrlp = new JScrollPane(bivakFacultetTable);
		bivakFacultetTable.setPreferredScrollableViewportSize(
				new Dimension(1000, 200));

		final TableModel tm = bivakFacultetTable.getModel();
		tm.addTableModelListener(new TableModelListener() {
			public void tableChanged(TableModelEvent tme) {
				if(tme.getType() == TableModelEvent.UPDATE) {

					if(tme.getColumn() == 0) { JOptionPane.showMessageDialog(null, "Нельзя править"); return;}
					Facultet facultet = Mpgu_slet.facultet.get(tme.getFirstRow());
					if(tme.getColumn() == 7) {
						facultet.setBivakPlace(Integer.parseInt(tm.getValueAt(tme.getFirstRow(),
								tme.getColumn()).toString()));
						return;
					}
					facultet.setBivak(tme.getColumn() - 1,Integer.parseInt(tm.getValueAt(tme.getFirstRow(),
							tme.getColumn()).toString()));
					redrawFacultet();
				}
			}
		});
		facPanel.add(jscrlp);
		DataWorker.saveData(Mpgu_slet.facultet, Facultet.class.getName());
	}

}
