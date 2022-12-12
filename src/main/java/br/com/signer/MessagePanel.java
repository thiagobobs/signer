package br.com.signer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.IOException;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MessagePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LogManager.getLogger(MessagePanel.class);

	private JLabel messageLabel;

	public MessagePanel() {
		super();

		setName("messagePanel");

		String message = StringUtils.EMPTY;
		ImageIcon imageIcon = null;

		try {
			imageIcon = new ImageIcon(new URL("https://cdn.shopify.com/s/files/1/0513/1226/3362/products/Asset2_1024x1024.jpg?v=1619802039"));
		} catch (IOException ex) {
			message = "Falha ao carregar imagem";
			LOGGER.error("Fail to load image. Reason: {}", ex.getLocalizedMessage());
		}

		this.messageLabel = new JLabel(message, imageIcon, SwingConstants.LEFT);
		this.messageLabel.setHorizontalAlignment(JLabel.CENTER);

		setLayout(new BorderLayout());
		setBackground(Color.WHITE);

		add(this.messageLabel, BorderLayout.CENTER);

		Border innerBorder = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
	}

}
