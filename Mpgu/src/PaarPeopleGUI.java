import Data.Comand;
import Data.Facultet;
import Data.PaarPeople;
import Data.People;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: murik
 * Date: 04.10.12
 * Time: 20:27
 */

public class PaarPeopleGUI extends JDialog{
	private JList listPeople1;
	private JList listPaar;
	private JButton addPaarButton;
	private JList listPeople2;
	private JButton delPaarButton;
	private JPanel contentPane;
	private JComboBox comandaComboBox;


	public PaarPeopleGUI() {
		setTitle(MpguMetaInfo.paarTitle);
		setContentPane(contentPane);
		setModal(true);
		listPeople1.setVisibleRowCount(200);
		listPeople2.setVisibleRowCount(200);
		listPaar.setVisibleRowCount(100);
		final DefaultListSelectionModel m1 = new DefaultListSelectionModel();
		m1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		m1.setLeadAnchorNotificationEnabled(false);
		final DefaultListSelectionModel m2 = new DefaultListSelectionModel();
		m2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		m2.setLeadAnchorNotificationEnabled(false);
		final DefaultListSelectionModel m3 = new DefaultListSelectionModel();
		m3.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		m3.setLeadAnchorNotificationEnabled(false);
		listPeople1.setSelectionModel(m1);
		listPeople2.setSelectionModel(m2);
		listPaar.setSelectionModel(m3);
		final DefaultListModel infoPeople1 = new DefaultListModel();
		listPeople1.setModel(infoPeople1);
		final DefaultListModel infoPeople2 = new DefaultListModel();
		listPeople2.setModel(infoPeople2);
		final DefaultListModel infoPaar = new DefaultListModel();
		listPaar.setModel(infoPaar);

		for(Comand com : Mpgu_slet.comanda) {
			comandaComboBox.addItem(com);
		}
		redrawPeopleLists();

		addPaarButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				if(listPeople1.getSelectedIndex() == -1 || listPeople2.getSelectedIndex()==-1) {JOptionPane.showMessageDialog(null, "Никто не выбран"); return;}
				People people1 = (People) listPeople1.getSelectedValue();
				People people2 = (People) listPeople2.getSelectedValue();
				if (people1.equals(people2)) {JOptionPane.showMessageDialog(null, "Это тот же человек"); return;}
				if (!people1.getComandName().equals(people2.getComandName())) {JOptionPane.showMessageDialog(null, "Разные команды"); return;}

				PaarPeople paarPeople = new PaarPeople(Mpgu_slet.paarPeoples.size()+1, people1, people2);
				Mpgu_slet.paarPeoples.add(paarPeople);
//				key.getPaarPeoples().add(paarPeople);
				infoPaar.addElement(paarPeople);
				redrawPeopleLists();
				DataWorker.saveData(Mpgu_slet.paarPeoples,PaarPeople.class.getName());
			}
		});

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				DataWorker.saveData(Mpgu_slet.paarPeoples,PaarPeople.class.getName());
			}
		});

		delPaarButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				if(listPaar.getSelectedIndex() == -1) {JOptionPane.showMessageDialog(null, "Пара для удаления не выбрана"); return;}
				PaarPeople paarPeople = Mpgu_slet.paarPeoples.remove(listPaar.getSelectedIndex());
				infoPaar.remove(listPaar.getSelectedIndex());
				redrawPeopleLists();
			}
		});


		comandaComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				redrawPeopleLists();
			}
		});
	}


	private void redrawPeopleLists(){
		DefaultListModel infoPeople1 = (DefaultListModel) listPeople1.getModel();
		DefaultListModel infoPeople2 = (DefaultListModel) listPeople2.getModel();
		infoPeople1.clear();
		infoPeople2.clear();
		final DefaultListModel infoPaar = (DefaultListModel) listPaar.getModel();
		infoPaar.clear();
//		if (comandaComboBox.getSelectedIndex()==-1) {
//			for (People people : peoples) {
//				infoPeople1.addElement(people);
//				infoPeople2.addElement(people);
//			}
//		}else {
			Comand comand = Mpgu_slet.comanda.get(comandaComboBox.getSelectedIndex());

			for (People people:Mpgu_slet.peoples) {
				if(people.getComandName().equals(comand.getName())) {
					infoPeople1.addElement(people);
					infoPeople2.addElement(people);
				}
			}
//		}
		for (PaarPeople paarPeople:Mpgu_slet.paarPeoples){
			infoPaar.addElement(paarPeople);
			infoPeople1.removeElement(paarPeople.getPeople1());
			infoPeople1.removeElement(paarPeople.getPeople2());
			infoPeople2.removeElement(paarPeople.getPeople1());
			infoPeople2.removeElement(paarPeople.getPeople2());
		}
		DataWorker.saveData(Mpgu_slet.paarPeoples,PaarPeople.class.getName());
	}

//	private People findPeople(String peopleID,List<People> peoples){
//		for (People people:peoples){
//			if (people.format().equals(peopleID))return people;
//		}
//		return new People(0,"error","error","error","error");
//	}



//	private Comand findComand(String comandID,List<Comand> comands){
//		for (Comand key:comands){
//			if (key.getName().equals(comandID))return key;
//		}
//		return new Comand(new Facultet("error"),"error");
//	}
}


