import Data.Comand;
import Data.Facultet;
import Data.PaarPeople;
import Data.People;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class team extends JDialog {
	private JPanel contentPane;
	private JButton buttonClose;
	private JButton AddComButton;
	private JButton AddFacButton;
	private JButton buttonOrient;
	private JButton бивакButton;
	private JButton prometeiButton;
	private JButton TvorcestvoButton;
	private JPanel tablePanel;
	private JButton AddPeopleButton;
	private JButton redrawButton;
	private JButton PolosaButton;
	private JButton PaarOrientButton;
	private JPanel tablePanel2;
	private JTable konkursTable;

	private JTable fullTable;


	private FacGUI facGUI;
	private ComandGUI comandGUI;
//	private team dialog;

	public List<Facultet> facultet = new ArrayList<Facultet>();
	public  List<Comand> comanda = new ArrayList<Comand>();
	public List<People> peoples = new ArrayList<People>();
	public List<PaarPeople> paarPeoples = new ArrayList<PaarPeople>();


	public team() {
		setTitle("МПГУ Неофициальный слет");
		setContentPane(contentPane);
		setModal(true);
		getRootPane().setDefaultButton(buttonClose);

		buttonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {onClose();}
		});


// call onCancel() when cross is clicked
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				onClose();
			}
		});

// call onCancel() on ESCAPE
		contentPane.registerKeyboardAction(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onClose();
			}
		}, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

		facultet =  DataWorker.loadFacData();
		comanda = DataWorker.loadComData();
		peoples = DataWorker.loadPeopleData();
		paarPeoples = DataWorker.loadPaarPeopleData();

		redraw();
		redrawFull();

		AddFacButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				facGUI = new FacGUI(facultet);
				facGUI.pack();
				facGUI.setVisible(true);
			}
		});
		AddComButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				comandGUI = new ComandGUI(comanda, facultet);
				comandGUI.pack();
				comandGUI.setVisible(true);
			}
		});
		бивакButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				BivakGUI bivakGUI = new BivakGUI(comanda);
				bivakGUI.pack();
				bivakGUI.setVisible(true);
			}
		});
		prometeiButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				PrometeiGUI prometeiGUI = new PrometeiGUI(comanda);
				prometeiGUI.pack();
				prometeiGUI.setVisible(true);
			}
		});
		TvorcestvoButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				TvorcestGUI tvorcestGUI =new TvorcestGUI(comanda);
				tvorcestGUI.pack();
				tvorcestGUI.setVisible(true);
			}
		});



		AddPeopleButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				PeopleGUI peopleGUI =new PeopleGUI(facultet,comanda,peoples);
				peopleGUI.pack();
				peopleGUI.setVisible(true);
			}
		});
		redrawButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				redraw();redrawFull();
			}
		});
		PolosaButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				PolosaGUI polosaGUI = new PolosaGUI(comanda);
				polosaGUI.pack();
				polosaGUI.setVisible(true);
			}
		});
		PaarOrientButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				PaarPeopleGUI paarPeopleGUI = new PaarPeopleGUI(peoples,comanda,paarPeoples);
				paarPeopleGUI.pack();
				paarPeopleGUI.setVisible(true);
			}
		});
		buttonOrient.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				OrientirovanieGUI orientirovanieGUI = new OrientirovanieGUI(paarPeoples,comanda);
				orientirovanieGUI.pack();
				orientirovanieGUI.setVisible(true);
			}
		});
	}

	private void onClose() {
		DataWorker.saveData(comanda,Comand.class.getName());
		dispose();
	}

	public void redraw(){
		tablePanel.removeAll();
		tablePanel.revalidate();
		String[] headings = {
				"Факультет",
				"Команда",
				"Место бивак",
				"Место прометей",
				"Место творчество",
				"Сумма",
				"Место Конкурсы"
		};

		Object[][] data = new Object[comanda.size()][7];
		HashMap<Integer,Integer> forSort = new HashMap<Integer, Integer>();
		for (int i=0;i<comanda.size();i++){
			int summ = comanda.get(i).getBivakPlace() + comanda.get(i).getPrometeiPlace() + comanda.get(i).getTvorcestvoPlace();
			data[i][0] = comanda.get(i).getFacultet().getName();
			data[i][1] = comanda.get(i).getName();
			data[i][2] = comanda.get(i).getBivakPlace();
			data[i][3] = comanda.get(i).getPrometeiPlace();
			data[i][4] = comanda.get(i).getTvorcestvoPlace();
			data[i][5] = summ;
			data[i][6] = comanda.get(i).getKonkursnajaProg();
			forSort.put(i,summ);
		}

		Sort.SortHM[] sortHM= Sort.sortHM(forSort);
		Integer ball =-1;
		int place =0;
		for (Sort.SortHM cin:sortHM){
			if(cin.ball>ball) {ball=cin.ball; place++;}
			data[cin.comand][6] = place;
			comanda.get(cin.comand).setKonkursnajaProg(place);
		}


		tablePanel.setLayout(new FlowLayout());
		konkursTable = new JTable(data, headings);
		JScrollPane jscrlp = new JScrollPane(konkursTable);
		konkursTable.setPreferredScrollableViewportSize(new Dimension(500, 100));
		final TableModel tm = konkursTable.getModel();
		tm.addTableModelListener(new TableModelListener()
		{
			public void tableChanged(TableModelEvent tme)
			{
				if(tme.getType() == TableModelEvent.UPDATE)
				{
					Comand comand = comanda.get(tme.getFirstRow());
					if(tme.getColumn() < 6) return;
					comand.setKonkursnajaProg(Integer.parseInt(tm.getValueAt(tme.getFirstRow(),
							tme.getColumn()).toString()));
				}
			}
		});
		tablePanel.add(jscrlp);
		tablePanel.setVisible(true);



	}



	public void redrawFull(){
		tablePanel2.removeAll();
		tablePanel2.revalidate();
		String[] headings = {
				"Факультет",
				"Команда",
				"Место Конкурс",
				"Место Полоса",
				"Место Ориентирование",
				"Сумма",
				"Место Финал"
		};

		Object[][] data = new Object[comanda.size()][7];
		HashMap<Integer,Integer> forSort = new HashMap<Integer, Integer>();
		for (int i=0;i<comanda.size();i++){
			int summ = comanda.get(i).getKonkursnajaProg() + comanda.get(i).getPolosaPlace() + comanda.get(i).getOrientirovanie();
			data[i][0] = comanda.get(i).getFacultet().getName();
			data[i][1] = comanda.get(i).getName();
			data[i][2] = comanda.get(i).getKonkursnajaProg();
			data[i][3] = comanda.get(i).getPolosaPlace();
			data[i][4] = comanda.get(i).getOrientirovanie();
			data[i][5] = summ;
//			data[i][6] = comanda.get(i).get();
			forSort.put(i,summ);
		}


//		Sort.SortHM[] sortHM= Sort.sortHM(forSort);
//		Integer ball =-1;
//		HashSet hs = new HashSet();
//		hs.addAll(forSort.values());
//		int place=hs.size()+1;
//		for (Sort.SortHM cin:sortHM){
//			if(cin.ball>ball) {ball=cin.ball; place--;}
//			data[cin.comand][6] = place;
//		}

		Sort.SortHM[] sortHM= Sort.sortHM(forSort);
		Integer ball =-1;
		int place =0;
		for (Sort.SortHM cin:sortHM){
			if(cin.ball>ball) {ball=cin.ball; place++;}
			data[cin.comand][6] = place;
		}


		tablePanel2.setLayout(new FlowLayout());
		fullTable = new JTable(data, headings);
		JScrollPane jscrlp = new JScrollPane(fullTable);
		fullTable.setPreferredScrollableViewportSize(new Dimension(500, 100));
		tablePanel2.add(jscrlp);
		tablePanel2.setVisible(true);

	}



	public static void main(String[] args) {
		team dialog = new team();
		dialog.pack();
		dialog.setVisible(true);
		System.exit(0);
	}
}
