package br.com.signer;

import java.util.List;

public class LoginEvent {

	private List<FileModel> files;
	private String error;

	public LoginEvent(List<FileModel> files) {
		this.files = files;
	}

	public LoginEvent(String error) {
		this.error = error;
	}

	public String getError() {
		return error;
	}

	public List<FileModel> getFiles() {
		return files;
	}

}
