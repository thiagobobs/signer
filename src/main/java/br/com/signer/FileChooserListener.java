package br.com.signer;

import java.util.EventListener;

public interface FileChooserListener extends EventListener {
	
	public void certAdded(CertAddedEvent event);

	public void certRemoved(CertRemovedEvent event);

	public void certSelected(CertSelectedEvent event);

}
