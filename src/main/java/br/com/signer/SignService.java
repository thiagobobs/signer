package br.com.signer;

import java.io.FileOutputStream;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.StampingProperties;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.extgstate.PdfExtGState;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.signatures.BouncyCastleDigest;
import com.itextpdf.signatures.DigestAlgorithms;
import com.itextpdf.signatures.IExternalDigest;
import com.itextpdf.signatures.IExternalSignature;
import com.itextpdf.signatures.PdfSignatureAppearance;
import com.itextpdf.signatures.PdfSigner;
import com.itextpdf.signatures.PrivateKeySignature;

public class SignService {

	public void sign(PrivateKey pk, Provider provider, Certificate[] chain) {
		Security.addProvider(provider);
		try {
	        PdfReader reader = new PdfReader("/home/thiago/Downloads/InfoDental/teste.pdf");
	        PdfSigner signer = new PdfSigner(reader, new FileOutputStream("/home/thiago/Downloads/InfoDental/teste_signed.pdf"), new StampingProperties());
	        PdfDocument pdfDocument = signer.getDocument();
	        
	        // Create the signature appearance
	        Rectangle rect = new Rectangle(30, 80, 530, 35);
//	        Rectangle rect = new Rectangle(350, 410, 130, 40);
	        PdfSignatureAppearance appearance = signer.getSignatureAppearance();
	        
	        appearance
//	                .setReason("")
//	                .setLocation("")
	 
	                // Specify if the appearance before field is signed will be used
	                // as a background for the signed field. The "false" value is the default value.
//	                .setReuseAppearance(false)
	                .setPageRect(rect)
//	                .setLayer2Text("Receituário assinado digitalmente por " + this.getCN((X509Certificate)chain[0]))
//	                .setRenderingMode(PdfSignatureAppearance.RenderingMode.GRAPHIC_AND_DESCRIPTION)
//	                .setLayer2FontSize(10)
	                .setPageNumber(1);
	        signer.setFieldName("sig");

	        // Get the background layer and draw a gray rectangle as a background.
	        PdfFormXObject n0 = appearance.getLayer0();

	        float x = n0.getBBox().toRectangle().getLeft();
	        float y = n0.getBBox().toRectangle().getBottom();
	        float width = n0.getBBox().toRectangle().getWidth();
	        float height = n0.getBBox().toRectangle().getHeight();

	        PdfCanvas canvas_n0 = new PdfCanvas(n0, pdfDocument);
	        canvas_n0.setFillColor(ColorConstants.GRAY);
	        canvas_n0.setExtGState(new PdfExtGState().setFillOpacity(0.1f));
	        canvas_n0.rectangle(x, y, width, height);
	        canvas_n0.fill();
	        
	        // Set the signature information on layer 2
	        PdfFormXObject n2 = appearance.getLayer2();
	        Paragraph p = new Paragraph("Receituário assinado digitalmente por " + this.getCN((X509Certificate)chain[0]) + " em " + new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime()));

//	        p.setHeight(60);
//	        p.setWidth(530);
	        p.setMargin(10);
	        p.setFontColor(ColorConstants.BLACK);
	        p.setFontSize(10);
	        
	        Canvas canvas_n2 = new Canvas(n2, pdfDocument);
	        canvas_n2.add(p);
	        canvas_n2.close();
	 
	        IExternalDigest digest = new BouncyCastleDigest();
	        IExternalSignature pks = new PrivateKeySignature(pk, DigestAlgorithms.SHA256, provider.getName());
	 
	        // Sign the document using the detached mode, CMS or CAdES equivalent.
	        signer.signDetached(digest, pks, chain, null, null, null, 0, PdfSigner.CryptoStandard.CMS);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private String getCN(X509Certificate cert) {
		Principal principal = cert.getSubjectDN();
		int start = principal.getName().indexOf("CN");
		String tmpName, name = "";
		if (start > 0) {
			tmpName = principal.getName().substring(start + 3);
			int end = tmpName.indexOf(",");
			if (end > 0) {
				name = tmpName.substring(0, end);
			} else {
				name = tmpName;
			}
		}
		return name;
	}

}
