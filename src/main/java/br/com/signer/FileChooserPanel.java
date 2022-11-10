package br.com.signer;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileChooserPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JTable table;
	private JButton addButton;
	private JButton removeButton;
	private JFileChooser fileChooser;

	private FileChooserListener fileChooserListener;
	
	public FileChooserPanel(String[] tableColumnsName, List<String> items, String fileNameExtensionDescription, String[] fileNamesExtensions) {
		super();
		
		setLayout(new GridBagLayout());		
		
		this.table = new JTable(new CertTableModel(tableColumnsName, items));
		this.table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		this.addButton = new JButton("Adicionar");
		this.removeButton = new JButton("Remover");

		this.fileChooser = new JFileChooser();
		this.fileChooser.setFileFilter(new FileNameExtensionFilter(fileNameExtensionDescription, fileNamesExtensions));

		JPanel panel = new JPanel();

		GridLayout layout = new GridLayout(2, 1);
		layout.setVgap(10);

		panel.setLayout(layout);
		panel.add(this.addButton);
		panel.add(this.removeButton);
		
		GridBagConstraints gc = new GridBagConstraints();

		gc.gridx = 0;
		gc.gridy = 0;
		gc.weightx = 100;
		gc.weighty = 1;
		gc.fill = GridBagConstraints.BOTH;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.insets = new Insets(5, 5, 5, 5);

		add(new JScrollPane(this.table), gc);

		gc.gridx = 1;
		gc.gridy = 0;
		gc.weightx = 1;
		gc.weighty = 1;
		gc.fill = GridBagConstraints.HORIZONTAL;
		add(panel, gc);

		this.addButton.addActionListener(event -> {
			if (this.fileChooser.showOpenDialog(FileChooserPanel.this) == JFileChooser.APPROVE_OPTION) {
				String fileName = this.fileChooser.getSelectedFile().toString();

				((CertTableModel) this.table.getModel()).addItem(fileName);
				this.fileChooserListener.certAdded(new CertAddedEvent(this, fileName));
			}
		});

		this.removeButton.addActionListener(event -> {
			if (this.table.getSelectedRow() != -1) {
				int selectedRow = this.table.getSelectedRow();

				((CertTableModel) this.table.getModel()).removeItem(selectedRow);
				this.fileChooserListener.certRemoved(new CertRemovedEvent(this, selectedRow));
			}
		});

	}

	public void addFileChooserListener(FileChooserListener fileChooserListener) {
		this.fileChooserListener = fileChooserListener;
	}

}
