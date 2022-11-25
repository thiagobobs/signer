package br.com.signer;

import java.io.File;
import java.security.KeyStore;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class KeyStoreManager {

	public KeyStore getKeyStore(File file) throws Exception {
		KeyStore.Builder kb = KeyStore.Builder.newInstance("PKCS12", new BouncyCastleProvider(), file, new KeyStore.CallbackHandlerProtection(new PasswordCallbackHandler()));
		return kb.getKeyStore();
	}

}
