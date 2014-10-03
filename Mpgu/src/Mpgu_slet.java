import Data.Comand;
import Data.Facultet;
import Data.PaarPeople;
import Data.People;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Mpgu_slet extends JDialog {
	private JPanel contentPane;
	private JButton buttonClose;
	private JButton addComButton;
	private JButton addFacButton;
	private JButton orientirovanieButton;
	private JButton bivakButton;
	private JButton prometeiButton;
	private JButton tvorcestvoButton;
	private JButton addPeopleButton;
	private JButton polosaButton;
	private JButton paarOrientButton;
	private JPanel konkursFullComandTablePanel;
	private JPanel konkursFullFacultetTablePanel;
	private JPanel fullCommandPanel;
	private JPanel fullFacultetPanel;


	private FacGUI facGUI;
	private ComandGUI comandGUI;
//	private team dialog;

	public static List<Facultet> facultet = new ArrayList<Facultet>();
	public static List<Comand> comanda = new ArrayList<Comand>();
	public static List<People> peoples = new ArrayList<People>();
	public static List<PaarPeople> paarPeoples = new ArrayList<PaarPeople>();


	public Mpgu_slet() {
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

			@Override
			public void windowActivated(WindowEvent e) {
				super.windowActivated(e);
				redraw();
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

		addFacButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				facGUI = new FacGUI();
				facGUI.pack();
				facGUI.setVisible(true);
			}
		});
		addComButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				comandGUI = new ComandGUI();
				comandGUI.pack();
				comandGUI.setVisible(true);
			}
		});
		bivakButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				BivakGUI bivakGUI = new BivakGUI();
				bivakGUI.pack();
				bivakGUI.setVisible(true);
			}
		});
		prometeiButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				PrometeiGUI prometeiGUI = new PrometeiGUI();
				prometeiGUI.pack();
				prometeiGUI.setVisible(true);
			}
		});
		tvorcestvoButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				TvorcestGUI tvorcestGUI =new TvorcestGUI();
				tvorcestGUI.pack();
				tvorcestGUI.setVisible(true);
			}
		});

		addPeopleButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				PeopleGUI peopleGUI =new PeopleGUI();
				peopleGUI.pack();
				peopleGUI.setVisible(true);
			}
		});
		polosaButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				PolosaGUI polosaGUI = new PolosaGUI();
				polosaGUI.pack();
				polosaGUI.setVisible(true);
			}
		});
		paarOrientButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				PaarPeopleGUI paarPeopleGUI = new PaarPeopleGUI();
				paarPeopleGUI.pack();
				paarPeopleGUI.setVisible(true);
			}
		});
		orientirovanieButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				OrientirovanieGUI orientirovanieGUI = new OrientirovanieGUI();
				orientirovanieGUI.pack();
				orientirovanieGUI.setVisible(true);
			}
		});
	}

	private void onClose() {
		DataWorker.saveData(comanda,Comand.class.getName());
		DataWorker.saveData(facultet,Facultet.class.getName());
		dispose();
	}

	private void redraw(){
		redrawKonkursCommand();
		redrawKonkursFacultet();
		redrawFullCommand();
		redrawFullFacultet();
	}

	private void redrawKonkursCommand(){
		konkursFullComandTablePanel.removeAll();
		konkursFullComandTablePanel.revalidate();

		Object[][] data = new Object[comanda.size()][7];
		HashMap<Integer,Integer> forSort = new HashMap<Integer, Integer>();
		for (int i=0;i<comanda.size();i++){
			int summ = comanda.get(i).getBivakPlace() + comanda.get(i).getPrometeiPlace() + comanda.get(i).getTvorcestvoPlace();
			data[i][0] = comanda.get(i).getFacultetName();
			data[i][1] = comanda.get(i).getName();
			data[i][2] = comanda.get(i).getBivakPlace();
			data[i][3] = comanda.get(i).getPrometeiPlace();
			data[i][4] = comanda.get(i).getTvorcestvoPlace();
			data[i][5] = summ;
//			data[i][6] = comanda.get(i).getKonkursnajaProg();
			forSort.put(i,summ);
		}

		Sort.SortHM[] sortHM= Sort.sortHM(forSort);
		Integer ball =-1;
		int place =0;
		for (Sort.SortHM cin:sortHM){
			if(cin.ball>ball) {ball=cin.ball; place++;}
			data[cin.key][6] = place;
			comanda.get(cin.key).setKonkursnajaProg(place);
		}


		konkursFullComandTablePanel.setLayout(new FlowLayout());
		JTable konkursTableComanda = new JTable(data, MpguMetaInfo.headingsKonkursFullCommand);
		JScrollPane jscrlp = new JScrollPane(konkursTableComanda);
		konkursTableComanda.setPreferredScrollableViewportSize(new Dimension(500, 100));
//		final TableModel tm = konkursTableComanda.getModel();
//		tm.addTableModelListener(new TableModelListener()
//		{
//			public void tableChanged(TableModelEvent tme)
//			{
//				if(tme.getType() == TableModelEvent.UPDATE)
//				{
//					Comand key = comanda.get(tme.getFirstRow());
//					if(tme.getColumn() < 6) return;
//					key.setKonkursnajaProg(Integer.parseInt(tm.getValueAt(tme.getFirstRow(),
//							tme.getColumn()).toString()));
//				}
//			}
//		});
		konkursFullComandTablePanel.add(jscrlp);
	}

	private void redrawKonkursFacultet(){
		konkursFullFacultetTablePanel.removeAll();
		konkursFullFacultetTablePanel.revalidate();

		Object[][] data = new Object[facultet.size()][6];
		HashMap<Integer,Integer> forSort = new HashMap<Integer, Integer>();
		for (int i=0;i<facultet.size();i++){
			int summ = facultet.get(i).getBivakPlace() + facultet.get(i).getPrometeiPlace() + facultet.get(i).getTvorcestvoPlace();
			data[i][0] = facultet.get(i).getName();
			data[i][1] = facultet.get(i).getBivakPlace();
			data[i][2] = facultet.get(i).getPrometeiPlace();
			data[i][3] = facultet.get(i).getTvorcestvoPlace();
			data[i][4] = summ;
			forSort.put(i,summ);
		}

		Sort.SortHM[] sortHM= Sort.sortHM(forSort);
		Integer ball =-1;
		int place =0;
		for (Sort.SortHM cin:sortHM){
			if(cin.ball>ball) {ball=cin.ball; place++;}
			data[cin.key][5] = place;
			facultet.get(cin.key).setKonkursnajaProg(place);
		}


		konkursFullFacultetTablePanel.setLayout(new FlowLayout());
		JTable konkursTableFacultet = new JTable(data, MpguMetaInfo.headingsKonkursFullFacultet);
		JScrollPane jscrlp = new JScrollPane(konkursTableFacultet);
		konkursTableFacultet.setPreferredScrollableViewportSize(new Dimension(500, 100));
//		final TableModel tm = konkursTable.getModel();
//		tm.addTableModelListener(new TableModelListener()
//		{
//			public void tableChanged(TableModelEvent tme)
//			{
//				if(tme.getType() == TableModelEvent.UPDATE)
//				{
//					Comand key = comanda.get(tme.getFirstRow());
//					if(tme.getColumn() < 6) return;
//					key.setKonkursnajaProg(Integer.parseInt(tm.getValueAt(tme.getFirstRow(),
//							tme.getColumn()).toString()));
//				}
//			}
//		});
		konkursFullFacultetTablePanel.add(jscrlp);
	}



	private void redrawFullCommand(){
		fullCommandPanel.removeAll();
		fullCommandPanel.revalidate();

		Object[][] data = new Object[comanda.size()][7];
		HashMap<Integer,Integer> forSort = new HashMap<Integer, Integer>();
		for (int i=0;i<comanda.size();i++){
			int summ = comanda.get(i).getKonkursnajaProg() + comanda.get(i).getPolosaPlace() + comanda.get(i).getOrientirovaniePlace();
			data[i][0] = comanda.get(i).getFacultetName();
			data[i][1] = comanda.get(i).getName();
			data[i][2] = comanda.get(i).getKonkursnajaProg();
			data[i][3] = comanda.get(i).getPolosaPlace();
			data[i][4] = comanda.get(i).getOrientirovaniePlace();
			data[i][5] = summ;
			forSort.put(i,summ);
		}

		Sort.SortHM[] sortHM= Sort.sortHM(forSort);
		Integer ball =-1;
		int place =0;
		for (Sort.SortHM cin:sortHM){
			if(cin.ball>ball) {ball=cin.ball; place++;}
			data[cin.key][6] = place;
			comanda.get(cin.key).setFinalPlace(place);
		}

		fullCommandPanel.setLayout(new FlowLayout());
		JTable fullTable = new JTable(data, MpguMetaInfo.headingsFullCommand);
		JScrollPane jscrlp = new JScrollPane(fullTable);
		fullTable.setPreferredScrollableViewportSize(new Dimension(500, 100));
		fullCommandPanel.add(jscrlp);
	}

	public void redrawFullFacultet(){
		fullFacultetPanel.removeAll();
		fullFacultetPanel.revalidate();

		Object[][] data = new Object[facultet.size()][6];
		HashMap<Integer,Integer> forSort = new HashMap<Integer, Integer>();
		for (int i=0;i<facultet.size();i++){
			int summ = facultet.get(i).getKonkursnajaProg() + facultet.get(i).getPolosaPlace() + facultet.get(i).getOrientirovaniePlace();
			data[i][0] = facultet.get(i).getName();
			data[i][1] = facultet.get(i).getKonkursnajaProg();
			data[i][2] = facultet.get(i).getPolosaPlace();
			data[i][3] = facultet.get(i).getOrientirovaniePlace();
			data[i][4] = summ;
			forSort.put(i,summ);
		}

		Sort.SortHM[] sortHM= Sort.sortHM(forSort);
		Integer ball =-1;
		int place =0;
		for (Sort.SortHM cin:sortHM){
			if(cin.ball>ball) {ball=cin.ball; place++;}
			data[cin.key][5] = place;
			facultet.get(cin.key).setFinalPlace(place);
		}

		fullFacultetPanel.setLayout(new FlowLayout());
		JTable fullTable = new JTable(data, MpguMetaInfo.headingsFullFacultet);
		JScrollPane jscrlp = new JScrollPane(fullTable);
		fullTable.setPreferredScrollableViewportSize(new Dimension(500, 100));
		fullFacultetPanel.add(jscrlp);
	}



	public static void main(String[] args) {
		Mpgu_slet dialog = new Mpgu_slet();
		dialog.pack();
		dialog.setVisible(true);
		System.exit(0);
	}
}
