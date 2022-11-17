package br.com.signer;

import java.io.File;
import java.security.KeyStore;

public class KeyStoreManager {

	public KeyStore getKeyStore(File file) throws Exception {
		KeyStore.Builder kb = KeyStore.Builder.newInstance("PKCS12", null, file, new KeyStore.CallbackHandlerProtection(new PasswordCallbackHandler()));
		return kb.getKeyStore();
	}

}
