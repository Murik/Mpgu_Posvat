import Data.Comand;
import Data.Facultet;
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
 * Time: 7:42
 */

public class PeopleGUI extends JDialog {
	private JTextField nameField;
	private JTextField surnameField;
	private JButton addPeopleButton;
	private JComboBox ComanBox;
	private JList listPeople;
	private JButton deletePeopleButton;
	private JPanel contentPane;

	public PeopleGUI(final List<Facultet> facultet, final List<Comand> comanda, final List<People> peoples) {
		setTitle(MpguMetaInfo.peopleTitle);
		setContentPane(contentPane);
		setModal(true);
		listPeople.setVisibleRowCount(10);
		final DefaultListSelectionModel m = new DefaultListSelectionModel();
		m.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		m.setLeadAnchorNotificationEnabled(false);
		listPeople.setSelectionModel(m);
		final DefaultListModel info = new DefaultListModel();
		listPeople.setModel(info);

//		for(Facultet fac : facultet) {
//			facBox.addItem(fac.getName());
//		}
		for(Comand com : comanda) {
			ComanBox.addItem(com);
		}

		for(People people : peoples) {
			info.addElement(people);
		}

		addPeopleButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				if(ComanBox.getSelectedIndex()==-1) return;
					String name = nameField.getText();
					String surname = surnameField.getText();
					if (name.length()==0 || surname.length()==0) return;
				   	Comand com = comanda.get(ComanBox.getSelectedIndex());
					if(!info.contains(format(name,surname,com)))  {
						info.addElement(format(name,surname,com));
						Comand comand = comanda.get(ComanBox.getSelectedIndex());
						peoples.add(new People(info.size()+1,name,surname,comand.getName(), comand.getFacultet().getName()));
						nameField.setText("");
						surnameField.setText("");
					}
			}
		});

		deletePeopleButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				if(listPeople.getSelectedIndex()==-1) return;
				peoples.remove(listPeople.getSelectedIndex());
				info.remove(listPeople.getSelectedIndex());
			}
		});

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				DataWorker.saveData(peoples, People.class.getName());
				System.out.println("save");
			}
		});

	}

	private String format(String name, String surname, Comand comand){
		return name + " "+surname + " / " + comand.getName() + " / " + comand.getFacultet().getName();
	}
}