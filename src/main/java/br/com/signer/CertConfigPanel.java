package br.com.signer;

import java.awt.BorderLayout;
import java.util.LinkedList;
import java.util.List;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class CertConfigPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private Preferences preferences;
	private JTabbedPane tabPane;
	private FileChooserPanel certA1FileChooserPanel;
	private FileChooserPanel certA3FileChooserPanel;

	public CertConfigPanel() {
		super();

		this.preferences = Preferences.userRoot().node("signer");

		setLayout(new BorderLayout());

		this.tabPane = new JTabbedPane();

		this.certA1FileChooserPanel = new FileChooserPanel(new String[] { "Certificado" }, this.getFiles("A1"), "Certificado (*.pfx, *.p12)", new String[] { "pfx", "p12" });
		this.certA3FileChooserPanel = new FileChooserPanel(new String[] { "Driver" }, this.getFiles("A3"), "Driver (*.lib, *.dylib)", new String[] { "lib", "dylib" });

		tabPane.addTab("A1", this.certA1FileChooserPanel);
		tabPane.addTab("A3", this.certA3FileChooserPanel);

		add(tabPane, BorderLayout.CENTER);

		setBorder(BorderFactory.createEmptyBorder(5, 8, 5, 8));

		this.certA1FileChooserPanel.addFileChooserListener(new FileChooserListener() {

			@Override
			public void certAdded(CertAddedEvent event) {
				addFileName(event.getFileName(), "A1");
			}

			@Override
			public void certRemoved(CertRemovedEvent event) {
				removeFilename(event.getIndex(), "A1");
			}

		});

		this.certA3FileChooserPanel.addFileChooserListener(new FileChooserListener() {

			@Override
			public void certAdded(CertAddedEvent event) {
				addFileName(event.getFileName(), "A3");
			}

			@Override
			public void certRemoved(CertRemovedEvent event) {
				removeFilename(event.getIndex(), "A3");
			}

		});
	}

	private List<String> getFiles(String type) {
		List<String> files = new LinkedList<>();
		int size = this.preferences.node(type).getInt("size", 0);

		if (size != 0) {
			for (int i = 0; i < size; i++) {
				files.add(this.preferences.node(type).get("file_" + i, null));
			}
		}

		return files;
	}

	private void addFileName(String fileName, String type) {
		int size = preferences.node(type).getInt("size", 0);

		preferences.node(type).put("file_" + size, fileName);
		preferences.node(type).putInt("size", ++size);
	}

	private void removeFilename(int index, String type) {
		List<String> files = getFiles(type);
		int size = preferences.node(type).getInt("size", 0);

		try {
			preferences.node(type).removeNode();
		} catch (BackingStoreException e) {
			// Ops...
		}

		files.forEach(f -> {
			if (index != files.indexOf(f)) {
				addFileName(f, type);
			}
		});

		preferences.node(type).putInt("size", --size);
	}

}
