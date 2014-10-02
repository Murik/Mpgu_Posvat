import Data.Facultet;
import com.google.gson.Gson;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: murik
 * Date: 04.10.12
 * Time: 1:21
 */

public class FacGUI extends JDialog{
	private JTextField textField1;
	private JList list1;
	private JButton delFacButton;
	private JButton addFacButton;
	private JPanel contentPane;


	public FacGUI(final List<Facultet> facultet) {
		setTitle(MpguMetaInfo.facultetTitle);
		setContentPane(contentPane);
		setModal(true);
		list1.setVisibleRowCount(10);
		DefaultListSelectionModel m = new DefaultListSelectionModel();
		m.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		m.setLeadAnchorNotificationEnabled(false);
		list1.setSelectionModel(m);
		final DefaultListModel info = new DefaultListModel();
		list1.setModel(info);

		for (Facultet fac:facultet) {
			info.addElement(fac);
		}

		addFacButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				String facName = textField1.getText();
				if(facName.length()!=0 && !info.contains(facName)){
				info.addElement(facName);
				facultet.add(new Facultet(facName));
					textField1.setText("");
				}
			}
		});

		delFacButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				facultet.remove(list1.getSelectedIndex());
				info.remove(list1.getSelectedIndex());
			}
		});

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				DataWorker.saveData(facultet,Facultet.class.getName());
			}
		});
	}
}
