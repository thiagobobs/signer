package test;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

public class TestApp extends JFrame {

	public TestApp() {
		String[] columnNames = { "First Name", "Last Name", "" };
		Object[][] data = { { "Homer", "Simpson", "delete Homer" }, { "Madge", "Simpson", "delete Madge" },
				{ "Bart", "Simpson", "delete Bart" }, { "Lisa", "Simpson", "delete Lisa" }, };

		DefaultTableModel model = new DefaultTableModel(data, columnNames);
		JTable table = new JTable(model);
		table.setCellSelectionEnabled(true);

		Action delete = new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JTable table = (JTable) e.getSource();
				int modelRow = Integer.valueOf(e.getActionCommand());
				((DefaultTableModel) table.getModel()).removeRow(modelRow);

			}
		};

		ButtonColumn buttonColumn = new ButtonColumn(table, delete, 2);
		buttonColumn.setMnemonic(KeyEvent.VK_D);
		
		JScrollPane scrollPane = new JScrollPane(table);
		add(scrollPane, BorderLayout.CENTER);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setSize(400, 300);
		setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(TestApp::new);
	}

}
