package br.com.signer;

import java.awt.BorderLayout;

import javax.swing.JDialog;
import javax.swing.JFrame;

public class ConfigDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	private CertInfoPanel certInfoPanel;
	private CertConfigPanel certConfigPanel;

	public ConfigDialog(JFrame parent) {
		super(parent, "Configuração de Certificados A1/A3", Boolean.TRUE);

		setLayout(new BorderLayout());

		this.certInfoPanel = new CertInfoPanel();
		this.certConfigPanel = new CertConfigPanel();

		add(this.certInfoPanel, BorderLayout.PAGE_START);
		add(this.certConfigPanel, BorderLayout.CENTER);

		setSize(650, 400);

		Utils.centralizeWindow(this);
//		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
//		setLocation(dimension.width / 2 - this.getSize().width / 2, dimension.height / 2 - this.getSize().height / 2);

		setResizable(Boolean.FALSE);
	}

}
