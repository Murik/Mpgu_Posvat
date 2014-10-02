import Data.Comand;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: murik
 * Date: 04.10.12
 * Time: 6:55
 */

public class PrometeiGUI extends JDialog{
	private JPanel panel1;
	JTable jtabOrders;

	TableModel tm;

	public PrometeiGUI(final List<Comand> comands) {
		setContentPane(panel1);
		setModal(true);
		String[] headings = { "Команда",
				"Прометей",
				"Место"
		};

		Object[][] data = new Object[comands.size()][3];
		HashMap<Integer,Integer> forSort = new HashMap<Integer, Integer>();
		for (int i=0;i<comands.size();i++){
			data[i][0] = comands.get(i);
			data[i][1] = comands.get(i).getPrometei();
			data[i][2] = comands.get(i).getPrometeiPlace();
			forSort.put(i,comands.get(i).getPrometei());
		}

		Sort.SortHM[] sortHM= Sort.sortHM(forSort);
		Integer ball =-1;
		HashSet hs = new HashSet();
		hs.addAll(forSort.values());
		int place=hs.size()+1;
		for (Sort.SortHM cin:sortHM){
			if(cin.ball>ball) {ball=cin.ball; place--;}
			data[cin.comand][2] = place;
			comands.get(cin.comand).setPrometeiPlace(place);
		}

		panel1.setLayout(new FlowLayout());
		jtabOrders = new JTable(data, headings);
		JScrollPane jscrlp = new JScrollPane(jtabOrders);
		jtabOrders.setPreferredScrollableViewportSize(
				new Dimension(400, 150));

		tm = jtabOrders.getModel();
		tm.addTableModelListener(new TableModelListener()
		{
			public void tableChanged(TableModelEvent tme)
			{
				if(tme.getType() == TableModelEvent.UPDATE)
				{
					Comand comand = comands.get(tme.getFirstRow());
					if(tme.getColumn() == 0) return;
					if(tme.getColumn() == 2) {
						comand.setPrometeiPlace(Integer.parseInt(tm.getValueAt(tme.getFirstRow(),
								tme.getColumn()).toString()));
						return;
					}
					comand.setPrometei(Integer.parseInt(tm.getValueAt(tme.getFirstRow(),
							tme.getColumn()).toString()));
				}
			}
		});

		panel1.add(jscrlp);
		panel1.setVisible(true);

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {

				HashMap<Integer,Integer> forSort = new HashMap<Integer, Integer>();
				for (int i=0;i<comands.size();i++){
					forSort.put(i,comands.get(i).getPrometei());
				}
				Sort.SortHM[] sortHM= Sort.sortHM(forSort);
				Integer ball =-1;
				HashSet hs = new HashSet();
				hs.addAll(forSort.values());
				int place=hs.size()+1;
				for (Sort.SortHM cin:sortHM){
					if(cin.ball>ball) {ball=cin.ball; place--;}
					comands.get(cin.comand).setPrometeiPlace(place);
				}
				DataWorker.saveData(comands, Comand.class.getName());
				System.out.println("save");
			}
		});
	}


}
