package br.com.signer;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.util.Enumeration;
import java.util.List;

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
	
//	private ApiClient apiClient = new ApiClient();
	private SignService signService = new SignService();

	private JTable prescriptionTable;
	private JButton signButton;

	public PrescriptionPanel() {
		super();

		setName("prescriptionPanel");

		this.prescriptionTable = new JTable();
		this.prescriptionTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		this.signButton = new JButton("Assinar");

//		TableColumnModel columnModel = this.prescriptionTable.getColumnModel();
//		columnModel.getColumn(0).setMaxWidth(200);

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

//		addComponentListener(new ComponentAdapter() {
//
//			@Override
//			public void componentShown(ComponentEvent e) {
//				PrescriptionTableModel model = new PrescriptionTableModel();
//				model.setTableItems(apiClient.getFiles());
//
//				prescriptionTable.setModel(model);
//			}
//
//		});

		signButton.addActionListener(event -> {
			ListSelectionModel listSelectionModel = this.prescriptionTable.getSelectionModel();
			if (listSelectionModel.isSelectionEmpty()) {
				JOptionPane.showMessageDialog(null, "Necessário selecionar pelo menos um receituário para assinatura", null, JOptionPane.WARNING_MESSAGE);
			} else {
				List<String> certFiles = PreferencesManager.getInstance().getCertFiles();

				if (certFiles.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Nenhum certificado cadastrado", null, JOptionPane.WARNING_MESSAGE);
				} else {
					String selectedCertFile = null;
			
					if (certFiles.size() == 1) {
						selectedCertFile = certFiles.get(0);
					} else {
						selectedCertFile = (String)JOptionPane.showInputDialog(null, "Escolha um certificado", null, JOptionPane.INFORMATION_MESSAGE, null, certFiles.toArray(), null);
					}
					
					if (selectedCertFile != null) {
						int minSelectionIndex = listSelectionModel.getMinSelectionIndex();
						int maxSelectionIndex = listSelectionModel.getMaxSelectionIndex();

						for (int i = minSelectionIndex; i <= maxSelectionIndex; i++) {
							System.out.println(((PrescriptionTableModel)prescriptionTable.getModel()).getTableItems().get(i));
						}

//						this.signService.sign(((PrescriptionTableModel)prescriptionTable.getModel()).getTableItems().get(0).getName());
						
						try {
							KeyStore keyStore = new KeyStoreManager().getKeyStore(new File("/home/thiago/Development/certificates/cnj/new2/cert.p12"));
							String aliase = keyStore.aliases().nextElement();
							
							this.signService.sign((PrivateKey)keyStore.getKey(aliase, "123456".toCharArray()), keyStore.getProvider(), keyStore.getCertificateChain(aliase));
						} catch (Exception ex) {
							
						}

						JOptionPane.showMessageDialog(null, "Operação realizada com sucesso", null, JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		});
	}

	public void setTableModel(List<FileModel> files) {
		PrescriptionTableModel model = new PrescriptionTableModel();
		model.setTableItems(files);
//		model.setTableItems(apiClient.getFiles());

		this.prescriptionTable.setModel(model);
	}

}
