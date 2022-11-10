package br.com.signer;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ApiClient implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final List<FileModel> FILES;

	static {
		FILES = new LinkedList<>();

		for (int i = 1; i <= 50; i++) {
			FILES.add(new FileModel(Long.valueOf(i), "Receituario_" + i + ".pdf", "Fulano Beltrano da Silva"));
		}
	}

	public List<FileModel> getFiles() {
		return Collections.unmodifiableList(FILES);
	}

}
