package br.com.signer;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.prefs.Preferences;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.table.TableColumnModel;

public class PrescriptionPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private ApiClient apiClient = new ApiClient();

	private Preferences preferences;
	private PrescriptionTableModel prescriptionTableModel;

	private JTable prescriptionTable;
	private JButton signButton;

	public PrescriptionPanel() {
		super();

		this.preferences = Preferences.userRoot().node("signer");

		setName("prescriptionPanel");

		this.prescriptionTableModel = new PrescriptionTableModel();
		this.prescriptionTableModel.setTableItems(this.apiClient.getFiles());

		this.prescriptionTable = new JTable(this.prescriptionTableModel);
		this.prescriptionTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		this.signButton = new JButton("Assinar");

		TableColumnModel columnModel = this.prescriptionTable.getColumnModel();
		columnModel.getColumn(0).setMaxWidth(200);

		setLayout(new GridBagLayout());

		GridBagConstraints gc = new GridBagConstraints();

		gc.gridx = 0;
		gc.gridy = 0;
		gc.weightx = 1;
		gc.weighty = 1;
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.insets = new Insets(5, 5, 5, 5);

		add(new JScrollPane(this.prescriptionTable), gc);

		gc.gridx = 0;
		gc.gridy = 1;
		gc.fill = GridBagConstraints.NONE;
		gc.anchor = GridBagConstraints.LAST_LINE_END;
		add(this.signButton, gc);

		Border innerBorder = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

		signButton.addActionListener(event -> {
			ListSelectionModel listSelectionModel = this.prescriptionTable.getSelectionModel();
			if (listSelectionModel.isSelectionEmpty()) {
				JOptionPane.showMessageDialog(null, "Necessário selecionar pelo menos um receituário para assinatura", null, JOptionPane.WARNING_MESSAGE);
			} else {
				List<String> certFiles = getCertFiles();

				if (certFiles.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Nenhum certificado cadastrado", null, JOptionPane.WARNING_MESSAGE);					
				} else {
					int minSelectionIndex = listSelectionModel.getMinSelectionIndex();
					int maxSelectionIndex = listSelectionModel.getMaxSelectionIndex();

					for (int i = minSelectionIndex; i <= maxSelectionIndex; i++) {
						System.out.println(((PrescriptionTableModel)prescriptionTable.getModel()).getTableItems().get(i));
					}

					String selectedCertFile = null;
			
					if (certFiles.size() == 1) {
						selectedCertFile = certFiles.get(0);
					} else {
						selectedCertFile = (String)JOptionPane.showInputDialog(null, "Certificados cadastrados", "Escolha um certificado", JOptionPane.INFORMATION_MESSAGE, null, certFiles.toArray(), null);
					}
					
					if (selectedCertFile != null) {
						JOptionPane.showMessageDialog(null, "Operação realizada com sucesso", null, JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		});
	}

	private List<String> getCertFiles() {
		String[] types = {"A1", "A3"};
		List<String> files = new LinkedList<>();

		Arrays.asList(types).forEach(t -> {
			int size = this.preferences.node(t).getInt("size", 0);

			if (size != 0) {
				for (int i = 0; i < size; i++) {
					files.add(this.preferences.node(t).get("file_" + i, null));
				}
			}
		});

		return files;
	}

}
