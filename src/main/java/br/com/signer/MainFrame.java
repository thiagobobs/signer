package br.com.signer;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private LoginPanel loginPanel;
	private MessagePanel messagePanel;
	private PrescriptionPanel prescriptionPanel;
	private ConfigDialog configDialog;

	public MainFrame() {
		super("Título");

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.out.println("Fail to load and set look and feel.");
		}

		setLayout(new BorderLayout());

		setJMenuBar(this.createMenuBar());

		this.loginPanel = new LoginPanel();
		this.messagePanel = new MessagePanel();
		this.prescriptionPanel = new PrescriptionPanel();
		this.configDialog = new ConfigDialog(this);

		add(this.loginPanel, BorderLayout.PAGE_START);

		JPanel overlaidPanel = new JPanel(new CardLayout());

		overlaidPanel.add(this.messagePanel, this.messagePanel.getName());
		overlaidPanel.add(this.prescriptionPanel, this.prescriptionPanel.getName());

		add(overlaidPanel, BorderLayout.CENTER);

		setSize(800, 650);

		Utils.centralizeWindow(this);
//		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
//		setLocation(dimension.width / 2 - this.getSize().width / 2, dimension.height / 2 - this.getSize().height / 2);

//		setResizable(Boolean.FALSE);
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setVisible(Boolean.TRUE);

		this.loginPanel.addLoginListener(loginSuccessEvent -> {
			((CardLayout)overlaidPanel.getLayout()).show(overlaidPanel, this.prescriptionPanel.getName());
		});

		addWindowListener(new WindowAdapter() {

			@Override
			public void windowOpened(WindowEvent event) {
				loginPanel.setFocus();
			}

		});
	}

	private JMenuBar createMenuBar() {
		JMenuItem addCertItem = new JMenuItem("Certificado A1/A3");

		JMenu configMenu = new JMenu("Configuração");
		configMenu.add(addCertItem);

		JMenuBar menuBar = new JMenuBar();
		menuBar.add(configMenu);

		addCertItem.addActionListener(event -> {
			configDialog.setVisible(Boolean.TRUE);
		});

		return menuBar;
	}

}
