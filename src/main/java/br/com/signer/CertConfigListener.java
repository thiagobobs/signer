package br.com.signer;

import java.util.EventListener;

public interface CertConfigListener extends EventListener {

	public void configSelected(String text);

}
