package br.com.signer;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import org.apache.commons.lang3.StringUtils;

import br.com.signer.model.CredentialModel;

public class PreferencesManager {

	private static PreferencesManager instance;
	private Preferences preferences;

	private PreferencesManager() {
		this.preferences = Preferences.userRoot().node("signer");
	}

	public static PreferencesManager getInstance() {
		if (instance == null) {
			instance = new PreferencesManager();
		}

		return instance;
	}

	public List<String> getCertFiles() {
		List<String> certFiles = new LinkedList<>();

		Arrays.asList(CertTypeEnum.values()).forEach(t -> certFiles.addAll(getCertFiles(t.name())));

		return certFiles;
	}

	public List<String> getCertFiles(String type) {
		List<String> certFiles = new LinkedList<>();
		int size = this.preferences.node(type).getInt("size", 0);

		if (size != 0) {
			for (int i = 0; i < size; i++) {
				certFiles.add(this.preferences.node(type).get("file_" + i, null));
			}
		}

		return certFiles;
	}

	public void addCertFile(String file, String type) {
		int size = this.preferences.node(type).getInt("size", 0);

		this.preferences.node(type).put("file_" + size, file);
		this.preferences.node(type).putInt("size", ++size);
	}

	public void removeCertFile(int index, String type) {
		List<String> files = getCertFiles(type);
		int size = this.preferences.node(type).getInt("size", 0);

		try {
			this.preferences.node(type).removeNode();
		} catch (BackingStoreException e) {
			// Ops...
		}

		files.forEach(f -> {
			if (index != files.indexOf(f)) {
				addCertFile(f, type);
			}
		});

		this.preferences.node(type).putInt("size", --size);
	}

	public CredentialModel getCredential() {
		return new CredentialModel(this.preferences.node("credential").get("instance", StringUtils.EMPTY),
				this.preferences.node("credential").get("document", StringUtils.EMPTY), null);
	}

	public void addCredential(CredentialModel credential) {
		this.preferences.node("credential").put("instance", credential.getInstance());
		this.preferences.node("credential").put("document", credential.getDocument());
	}

}
