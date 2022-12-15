package br.com.signer;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyStore;
import java.security.Provider;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class KeyStoreManager {

	public KeyStore getKeyStore(CertTypeEnum certType, File file) throws Exception {
		KeyStore.Builder kb = null;

		if (CertTypeEnum.A1.equals(certType)) {
			kb = KeyStore.Builder.newInstance("PKCS12", new BouncyCastleProvider(), file, new KeyStore.CallbackHandlerProtection(new PasswordCallbackHandler()));
		} else {
			Class<?> clazz = Class.forName("sun.security.pkcs11.SunPKCS11");
			Constructor<?> constructor = clazz.getConstructor();
			Object object = constructor.newInstance();
			Method method = object.getClass().getMethod("configure", String.class);

			Provider provider = (Provider) method.invoke(object, this.getConfigFile("desktopID", file.getAbsolutePath()).getAbsolutePath());

			kb = KeyStore.Builder.newInstance("PKCS11", provider, new KeyStore.CallbackHandlerProtection(new PasswordCallbackHandler()));
		}

		return kb != null ? kb.getKeyStore() : null;
	}

	private File getConfigFile(String providerName, String path) throws IOException {
		StringBuilder content = new StringBuilder("name=").append(providerName).append("\n").append("library=").append(path);

		Path configFile = Files.createTempFile("pkcs11", "cfg");
		Files.write(configFile, content.toString().getBytes(StandardCharsets.UTF_8));

		return configFile.toFile();
	}

}
