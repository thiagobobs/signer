package br.com.signer;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

public class MessagePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JLabel messageLabel;

	public MessagePanel() {
		super();

		setName("messagePanel");

		this.messageLabel = new JLabel("Aqui serão apresentados os receituários para assinatura.");
		this.messageLabel.setHorizontalAlignment(JLabel.CENTER);

		setLayout(new BorderLayout());

		add(this.messageLabel, BorderLayout.CENTER);

		Border innerBorder = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
	}

}
