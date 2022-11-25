package br.com.signer;

import java.io.FileOutputStream;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.Security;
import java.security.cert.Certificate;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.StampingProperties;
import com.itextpdf.signatures.BouncyCastleDigest;
import com.itextpdf.signatures.DigestAlgorithms;
import com.itextpdf.signatures.IExternalDigest;
import com.itextpdf.signatures.IExternalSignature;
import com.itextpdf.signatures.OcspClientBouncyCastle;
import com.itextpdf.signatures.PdfSignatureAppearance;
import com.itextpdf.signatures.PdfSigner;
import com.itextpdf.signatures.PrivateKeySignature;

public class SignService {

	public void sign(PrivateKey pk, Provider provider, Certificate[] chain) {
		Security.addProvider(provider);
		try {
	        PdfReader reader = new PdfReader("/home/thiago/Downloads/InfoDental/teste.pdf");
	        PdfSigner signer = new PdfSigner(reader, new FileOutputStream("/home/thiago/Downloads/InfoDental/teste_signed.pdf"), new StampingProperties());
	        
	        // Create the signature appearance
	        Rectangle rect = new Rectangle(36, 648, 200, 100);
	        PdfSignatureAppearance appearance = signer.getSignatureAppearance();
	        appearance
	//                .setReason("reason")
	//                .setLocation("location")
	 
	                // Specify if the appearance before field is signed will be used
	                // as a background for the signed field. The "false" value is the default value.
	                .setReuseAppearance(false)
	                .setPageRect(rect)
	                .setPageNumber(1);
	        signer.setFieldName("sig");
	 
	        IExternalDigest digest = new BouncyCastleDigest();
	        IExternalSignature pks = new PrivateKeySignature(pk, DigestAlgorithms.SHA256, provider.getName());
	 
	        // Sign the document using the detached mode, CMS or CAdES equivalent.
	        signer.signDetached(digest, pks, chain, null, null, null, 0, PdfSigner.CryptoStandard.CMS);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
