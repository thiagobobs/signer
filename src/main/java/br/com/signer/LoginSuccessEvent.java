package br.com.signer;

import java.util.EventObject;

public class LoginSuccessEvent extends EventObject {

	private static final long serialVersionUID = 1L;

	public LoginSuccessEvent(Object source) {
		super(source);
	}

}
