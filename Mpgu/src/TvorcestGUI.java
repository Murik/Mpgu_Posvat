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
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: murik
 * Date: 04.10.12
 * Time: 7:17
 */

public class TvorcestGUI extends JDialog{
	private JPanel panel1;
	JTable jtabOrders;

	TableModel tm;



	public TvorcestGUI(final List<Comand> comands) {
		setContentPane(panel1);
		setModal(true);
		String[] headings = { "Команда",
				"Конкурс 1",
				"Конкурс 2",
				"Конкурс 3",
				"Сумма",
				"Место"
		};

		Object[][] data = new Object[comands.size()][6];
		HashMap<Integer,Integer> forSort = new HashMap<Integer, Integer>();
		for (int i=0;i<comands.size();i++){
			int summ = 0;
			for(int ball:comands.get(i).getTvorcestvo()){summ+=ball;}
			data[i][0] = comands.get(i).getName();
			data[i][1] = comands.get(i).getTvorcestvo()[0];
			data[i][2] = comands.get(i).getTvorcestvo()[1];
			data[i][3] = comands.get(i).getTvorcestvo()[2];
			data[i][4] = summ;
			forSort.put(i,summ);
		}

		Sort.SortHM[] sortHM= Sort.sortHM(forSort);
		Integer ball =-1;
		HashSet hs = new HashSet();
		hs.addAll(forSort.values());
		int place=hs.size()+1;
		for (Sort.SortHM cin:sortHM){
			if(cin.ball>ball) {ball=cin.ball; place--;}
			data[cin.comand][5] = place;
			comands.get(cin.comand).setTvorcestvoPlace(place);
		}


		panel1.setLayout(new FlowLayout());
		jtabOrders = new JTable(data, headings);
		JScrollPane jscrlp = new JScrollPane(jtabOrders);
		jtabOrders.setPreferredScrollableViewportSize(new Dimension(1000, 200));
		tm = jtabOrders.getModel();
		tm.addTableModelListener(new TableModelListener()
		{
			public void tableChanged(TableModelEvent tme)
			{
				if(tme.getType() == TableModelEvent.UPDATE)
				{
					Comand comand = comands.get(tme.getFirstRow());
					if(tme.getColumn() == 0) return;
					if(tme.getColumn() == 5) {
						comand.setTvorcestvoPlace(Integer.parseInt(tm.getValueAt(tme.getFirstRow(),
								tme.getColumn()).toString()));
						return;
					}
					comand.getTvorcestvo()[tme.getColumn() - 1] = Integer.parseInt(tm.getValueAt(tme.getFirstRow(),
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
					for(int ball:comands.get(i).getTvorcestvo()){summ+=ball;}
					forSort.put(i,summ);
				}

				Sort.SortHM[] sortHM= Sort.sortHM(forSort);
				Integer ball =-1;
				HashSet hs = new HashSet();
				hs.addAll(forSort.values());
				int place=hs.size()+1;
				for (Sort.SortHM cin:sortHM){
					if(cin.ball>ball) {ball=cin.ball; place--;}
					comands.get(cin.comand).setTvorcestvoPlace(place);
				}

				DataWorker.saveData(comands, Comand.class.getName());
				System.out.println("save");
			}
		});
	}


}
