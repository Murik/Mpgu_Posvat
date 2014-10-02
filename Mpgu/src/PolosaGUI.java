import Data.Comand;

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
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: murik
 * Date: 04.10.12
 * Time: 19:04
 */

public class PolosaGUI extends JDialog{
	private JPanel panel1;
	private JButton StartButton;
	private JButton StopButton;
	private JPanel contentPanel;
	private JButton redrawButton;

	JTable jtabOrders;

	TableModel tm;

	public PolosaGUI(final List<Comand> comands) {
		setTitle(MpguMetaInfo.polosaTitle);
		setContentPane(contentPanel);
		setModal(true);
		redraw(comands);
		contentPanel.setVisible(true);

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {

				DataWorker.saveData(comands, Comand.class.getName());
				System.out.println("save");
			}
		});
		StartButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				int[] rows = jtabOrders.getSelectedRows();
				if (rows.length==0)return;
				comands.get(rows[0]).setStartPolosa(new Timestamp(System.currentTimeMillis()));
				redraw(comands);
			}
		});


		StopButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				int[] rows = jtabOrders.getSelectedRows();
				if (rows.length==0)return;
				comands.get(rows[0]).setFinishPolosa(new Timestamp(System.currentTimeMillis()));
				redraw(comands);
			}
		});
		redrawButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				redraw(comands);
			}
		});
	}
	private void redraw(final List<Comand> comands){
		panel1.removeAll();
		panel1.revalidate();

		Object[][] data = new Object[comands.size()][7];
		HashMap<Integer,Integer> forSort = new HashMap<Integer, Integer>();
		for (int i=0;i<comands.size();i++){
			int minutesToPolosa =(int) TimeUnit.MILLISECONDS.toMinutes(comands.get(i).getFinishPolosa().getTime()-comands.get(i).getStartPolosa().getTime());
			int minutesToPolosaPlusBall =(int)(minutesToPolosa + (long)(comands.get(i).getBallsPolosa()*0.5));
			data[i][0] = comands.get(i);
			data[i][1] = comands.get(i).getStartPolosa();
			data[i][2] = comands.get(i).getFinishPolosa();
			data[i][3] = comands.get(i).getBallsPolosa();
			data[i][4] = minutesToPolosa;
			data[i][5] = minutesToPolosaPlusBall;
			forSort.put(i,minutesToPolosaPlusBall);
		}

		Sort.SortHM[] sortHM= Sort.sortHM(forSort);
		Integer ball =-1;
		int place =0;
		for (Sort.SortHM cin:sortHM){
			if(cin.ball>ball) {ball=cin.ball; place++;}
			data[cin.comand][6] = place;
			comands.get(cin.comand).setPolosaPlace(place);
		}

		panel1.setLayout(new FlowLayout());
		jtabOrders = new JTable(data, MpguMetaInfo.headingsPolosa);
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
					Comand comand = comands.get(tme.getFirstRow());
					if(tme.getColumn() != 3) return;
					comand.setBallsPolosa(Integer.parseInt(tm.getValueAt(tme.getFirstRow(),
							tme.getColumn()).toString()));
				}
			}
		});
		panel1.add(jscrlp);
		panel1.setVisible(true);
	}


}
