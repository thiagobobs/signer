package br.com.signer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import org.apache.commons.lang3.StringUtils;

public class LoginPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JLabel instanceLabel;
	private JTextField instanceField;

	private JLabel userLabel;
	private JTextField userFiled;

	private JLabel passwordLabel;
	private JPasswordField passwordField;

	private JButton validateButton;

	private LoginListener loginListener;

	public LoginPanel() {
		super();

		setLayout(new GridBagLayout());

		this.instanceLabel = new JLabel("Instância:");
		this.instanceField = new JTextField();
		this.instanceField.setPreferredSize(new Dimension(200, 25));

		this.userLabel = new JLabel("Usuário:");
		this.userFiled = new JTextField();
		this.userFiled.setPreferredSize(new Dimension(200, 25));

		this.passwordLabel = new JLabel("Senha:");
		this.passwordField = new JPasswordField();
		this.passwordField.setPreferredSize(new Dimension(200, 25));

		this.validateButton = new JButton("Validar");

		GridBagConstraints gc = new GridBagConstraints();

		gc.gridx = 0;
		gc.gridy = 0;
		gc.weightx = 1;
		gc.weighty = 1;
		gc.fill = GridBagConstraints.NONE;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = new Insets(5, 5, 5, 5);

		add(this.instanceLabel, gc);

		gc.gridx = 0;
		gc.gridy = 1;

		add(this.instanceField, gc);

		gc.gridx = 1;
		gc.gridy = 0;

		add(this.userLabel, gc);

		gc.gridx = 1;
		gc.gridy = 1;

		add(this.userFiled, gc);

		gc.gridx = 2;
		gc.gridy = 0;

		add(this.passwordLabel, gc);

		gc.gridx = 2;
		gc.gridy = 1;

		add(this.passwordField, gc);

		gc.gridx = 3;
		gc.gridy = 0;
		gc.gridheight = 2;
		gc.anchor = GridBagConstraints.LAST_LINE_END;

		add(this.validateButton, gc);

		Border innerBorder = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

		this.validateButton.addActionListener(event -> {

			if (StringUtils.isBlank(this.instanceField.getText())) {
				this.instanceLabel.setForeground(Color.RED);
			} else {
				this.instanceLabel.setForeground(null);
			}

			if (StringUtils.isBlank(this.userFiled.getText())) {
				this.userLabel.setForeground(Color.RED);
			} else {
				this.userLabel.setForeground(null);
			}

			if (this.passwordField.getPassword().length == 0) {
				this.passwordLabel.setForeground(Color.RED);
			} else {
				this.passwordLabel.setForeground(null);
			}

			if (StringUtils.isNotBlank(this.instanceField.getText()) && StringUtils.isNotBlank(this.userFiled.getText()) && this.passwordField.getPassword().length != 0) {
				this.loginListener.loginSuccess(new LoginSuccessEvent(this));
			}

		});
	}

	public void setFocus() {
		this.instanceField.requestFocus();
	}

	public void addLoginListener(LoginListener loginListener) {
		this.loginListener = loginListener;
	}

}
