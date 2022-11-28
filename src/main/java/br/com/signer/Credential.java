package br.com.signer;

public class Credential {

	private String instance;
	private String document;

	public Credential(String instance, String document) {
		super();
		this.instance = instance;
		this.document = document;
	}

	public String getInstance() {
		return instance;
	}

	public String getDocument() {
		return document;
	}

}
