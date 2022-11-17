package br.com.signer;

import java.util.EventObject;

public class CertSelectedEvent extends EventObject {

	private static final long serialVersionUID = 1L;

	private String text;

	public CertSelectedEvent(Object source, String text) {
		super(source);
		this.text = text;
	}

	public String getText() {
		return text;
	}

}
