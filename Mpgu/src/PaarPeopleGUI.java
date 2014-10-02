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


	public PaarPeopleGUI(final List<People> peoples, final List<Comand> comands, final List<PaarPeople> paarPeoples) {
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

		for(Comand com : comands) {
			comandaComboBox.addItem(com);
		}

		for (People people:peoples) {
			infoPeople1.addElement(people);
			infoPeople2.addElement(people);
		}

		for (PaarPeople paarPeople:paarPeoples){
			infoPaar.addElement(paarPeople);
			infoPeople1.removeElement(paarPeople.getPeople1());
			infoPeople1.removeElement(paarPeople.getPeople2());
			infoPeople2.removeElement(paarPeople.getPeople1());
			infoPeople2.removeElement(paarPeople.getPeople2());
		}


		addPaarButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				if(listPeople1.getSelectedIndex() == -1 || listPeople2.getSelectedIndex()==-1) return;
//				People people1 = findPeople((String)listPeople1.getSelectedValue(),peoples);//todo
//				People people2 = findPeople((String)listPeople2.getSelectedValue(),peoples);//todo
				People people1 = (People) listPeople1.getSelectedValue();
				People people2 = (People) listPeople2.getSelectedValue();
//				Comand comand = findComand(people1.getName(),comands);
				if (people1.equals(people2)) return;
				if (!people1.getComandName().equals(people2.getComandName())) return;

				PaarPeople paarPeople = new PaarPeople(paarPeoples.size()+1, people1, people2);
				paarPeoples.add(paarPeople);
//				comand.getPaarPeoples().add(paarPeople);
				infoPaar.addElement(paarPeople);
				infoPeople1.removeElement(paarPeople.getPeople1());
				infoPeople1.removeElement(paarPeople.getPeople2());
				infoPeople2.removeElement(paarPeople.getPeople1());
				infoPeople2.removeElement(paarPeople.getPeople2());
			}
		});

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				DataWorker.saveData(paarPeoples,PaarPeople.class.getName());
				System.out.println("save");
			}
		});

		delPaarButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				if(listPaar.getSelectedIndex() == -1) return;
				PaarPeople paarPeople = paarPeoples.remove(listPaar.getSelectedIndex());
//				Comand comand = findComand(paarPeople.getPeople1().getComandName(),comands);
//				int i=0;
//				for (PaarPeople paarPeople1: comand.getPaarPeoples()){
//					if (paarPeople1.formatName().equals(paarPeople.formatName())){comand.getPaarPeoples().remove(i);}
//					i++;
//				}
				infoPaar.remove(listPaar.getSelectedIndex());
//				infoPeople1.addElement(paarPeople.getPeople1().format());
//				infoPeople2.addElement(paarPeople.getPeople1().format());
//				infoPeople1.addElement(paarPeople.getPeople2().format());
//				infoPeople2.addElement(paarPeople.getPeople2().format());
				redrawPeopleLists(peoples, comands, paarPeoples);
			}
		});


		comandaComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				redrawPeopleLists(peoples,comands,paarPeoples);
			}
		});
	}


	private void redrawPeopleLists(final List<People> peoples, final List<Comand> comands, final List<PaarPeople> paarPeoples){
		DefaultListModel infoPeople1 = (DefaultListModel) listPeople1.getModel();
		DefaultListModel infoPeople2 = (DefaultListModel) listPeople2.getModel();
		infoPeople1.clear();
		infoPeople2.clear();
		if (comandaComboBox.getSelectedIndex()==-1) {
			for (People people : peoples) {
				infoPeople1.addElement(people);
				infoPeople2.addElement(people);
			}
		}else {
			Comand comand = comands.get(comandaComboBox.getSelectedIndex());

			for (People people:peoples) {
				if(people.getComandName().equals(comand)) {
					infoPeople1.addElement(people);
					infoPeople2.addElement(people);
				}
			}
		}
	}

//	private People findPeople(String peopleID,List<People> peoples){
//		for (People people:peoples){
//			if (people.format().equals(peopleID))return people;
//		}
//		return new People(0,"error","error","error","error");
//	}



//	private Comand findComand(String comandID,List<Comand> comands){
//		for (Comand comand:comands){
//			if (comand.getName().equals(comandID))return comand;
//		}
//		return new Comand(new Facultet("error"),"error");
//	}
}


