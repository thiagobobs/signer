package br.com.signer;

import java.util.EventListener;

public interface LoginListener extends EventListener {

	public void loginSuccess(LoginSuccessEvent event);

}
