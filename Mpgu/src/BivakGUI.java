import Data.Comand;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: murik
 * Date: 04.10.12
 * Time: 4:55
 */
public class BivakGUI extends JDialog {
	private JPanel panel1;
	JTable jtabOrders;

	TableModel tm;

	public BivakGUI(final List<Comand> comands) {
		setTitle(MpguMetaInfo.bivakTitle);
		setContentPane(panel1);
		setModal(true);

//		String[] columnNames = {

//		};
//
//		TableColumnModel info = new DefaultTableColumnModel();
//		TableColumn column1 = new TableColumn();
//		column1.setHeaderValue("Проход 1");
//		info.addColumn(column1);
//		jTable1.setColumnModel(info);

//	}


		Object[][] data = new Object[comands.size()][8];
		HashMap<Integer,Integer> forSort = new HashMap<Integer, Integer>();
		for (int i=0;i<comands.size();i++){
			int summ = 0;
			for(int ball:comands.get(i).getBivak()){summ+=ball;}
			data[i][0] = comands.get(i);
			data[i][1] = comands.get(i).getBivak()[0];
			data[i][2] = comands.get(i).getBivak()[1];
			data[i][3] = comands.get(i).getBivak()[2];
			data[i][4] = comands.get(i).getBivak()[3];
			data[i][5] = comands.get(i).getBivak()[4];
			data[i][6] = summ;
			forSort.put(i,summ);
		}

		Sort.SortHM[] sortHM= Sort.sortHM(forSort);
		Integer ball =-1;
		int place =0;
		for (Sort.SortHM cin:sortHM){
			if(cin.ball>ball) {ball=cin.ball; place++;}
			data[cin.comand][7] = place;
			comands.get(cin.comand).setBivakPlace(place);
		}


		panel1.setLayout(new FlowLayout());
		jtabOrders = new JTable(data, MpguMetaInfo.headingsBivak);
		JScrollPane jscrlp = new JScrollPane(jtabOrders);
		jtabOrders.setPreferredScrollableViewportSize(
				new Dimension(1000, 200));

		tm = jtabOrders.getModel();
		tm.addTableModelListener(new TableModelListener() {
			public void tableChanged(TableModelEvent tme) {
				if(tme.getType() == TableModelEvent.UPDATE) {
					Comand comand = comands.get(tme.getFirstRow());
					if(tme.getColumn() == 0) return;
					if(tme.getColumn() == 7) {
						comand.setBivakPlace(Integer.parseInt(tm.getValueAt(tme.getFirstRow(),
								tme.getColumn()).toString()));
						return;
					}
					comand.getBivak()[tme.getColumn() - 1] = Integer.parseInt(tm.getValueAt(tme.getFirstRow(),
							tme.getColumn()).toString());
				}
			}
		});


		panel1.add(jscrlp);

		panel1.setVisible(true);

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				HashMap<Integer,Integer> forSort = new HashMap<Integer, Integer>();
				for (int i=0;i<comands.size();i++){
					int summ = 0;
					for(int ball:comands.get(i).getBivak()){summ+=ball;}
					forSort.put(i,summ);
				}
				Sort.SortHM[] sortHM= Sort.sortHM(forSort);
				Integer ball =-1;
				int place =0;
				for (Sort.SortHM cin:sortHM){
					if(cin.ball>ball) {ball=cin.ball; place++;}
					comands.get(cin.comand).setBivakPlace(place);
				}

				DataWorker.saveData(comands, Comand.class.getName());
				System.out.println("save");
			}
		});
	}




}
