import Data.Comand;
import Data.Facultet;

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
 * Time: 1:53
 */

public class ComandGUI extends JDialog {
	private JTextField comField;
	private JComboBox facBox;
	private JButton addComButton;
	private JButton delComButton;
	private JList comList;
	private JPanel contentPane;


	public ComandGUI() {
		setTitle(MpguMetaInfo.comandTitle);
		setContentPane(contentPane);
		setModal(true);
		comList.setVisibleRowCount(10);
		final DefaultListSelectionModel m = new DefaultListSelectionModel();
		m.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		m.setLeadAnchorNotificationEnabled(false);
		comList.setSelectionModel(m);
		final DefaultListModel info = new DefaultListModel();
		comList.setModel(info);

		for(Facultet fac:Mpgu_slet.facultet){
			facBox.addItem(fac);
		}
		for (Comand com:Mpgu_slet.comanda) {
			info.addElement(com + " / " + com.getFacultetName());
		}

		addComButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				if(facBox.getSelectedIndex() != -1) {
					String fac = facBox.getSelectedItem().toString();
					String com = comField.getText();
					if(!info.contains(com + " / " + fac) && com.length()>0) {
						info.addElement(com + " / " + fac);
						Mpgu_slet.comanda.add(new Comand(Mpgu_slet.facultet.get(facBox.getSelectedIndex()).getName(),com));
					}
				}
			}
		});

		delComButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				Mpgu_slet.comanda.remove(comList.getSelectedIndex());
				info.remove(comList.getSelectedIndex());
			}
		});

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				DataWorker.saveData(Mpgu_slet.comanda,Comand.class.getName());
//				System.out.println("save");
			}
		});
	}


}
