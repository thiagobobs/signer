package br.com.signer;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.security.KeyStore;
import java.security.Provider;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class KeyStoreManager {

	public KeyStore getKeyStore(CertTypeEnum certType, File file) throws Exception {
		KeyStore.Builder kb = null;

		if (CertTypeEnum.A1.equals(certType)) {
			kb = KeyStore.Builder.newInstance("PKCS12", null, file, new KeyStore.CallbackHandlerProtection(new PasswordCallbackHandler()));
		} else {
			Class<?> classe = Class.forName("sun.security.pkcs11.SunPKCS11");
			Constructor<?> constructor = classe.getConstructor(InputStream.class);
			Provider provider = (Provider) constructor.newInstance(this.getConfigFile("desktopID", file.getAbsolutePath()));

			kb = KeyStore.Builder.newInstance("PKCS11", provider, new KeyStore.CallbackHandlerProtection(new PasswordCallbackHandler()));
		}

		return kb != null ? kb.getKeyStore() : null;
	}

	private ByteArrayInputStream getConfigFile(String providerName, String path) {
		StringBuilder content = new StringBuilder("name=").append(providerName).append("\n").append("library=").append(path);
		return new ByteArrayInputStream(content.toString().getBytes());
	}

}
