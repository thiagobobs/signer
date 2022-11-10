package br.com.signer;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

public class CertInfoPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JTextArea infoTextArea;

	public CertInfoPanel() {
		super();

		setLayout(new BorderLayout());

		this.infoTextArea = new JTextArea();
		this.infoTextArea.setEditable(Boolean.FALSE);

		add(new JScrollPane(this.infoTextArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED), BorderLayout.CENTER);

		Border innerBorder = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Informações do Certificado");
		Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

		Dimension dimension = getPreferredSize();
		dimension.height = 100;

		setPreferredSize(dimension);
	}

}
