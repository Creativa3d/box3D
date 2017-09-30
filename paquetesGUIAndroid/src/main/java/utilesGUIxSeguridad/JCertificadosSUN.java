/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesGUIxSeguridad;

import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSignatureAppearance;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.security.BouncyCastleDigest;
import com.itextpdf.text.pdf.security.CertificateInfo;
import com.itextpdf.text.pdf.security.CertificateVerification;
import com.itextpdf.text.pdf.security.ExternalDigest;
import com.itextpdf.text.pdf.security.ExternalSignature;
import com.itextpdf.text.pdf.security.MakeSignature;
import com.itextpdf.text.pdf.security.MakeSignature.CryptoStandard;
import com.itextpdf.text.pdf.security.PdfPKCS7;
import com.itextpdf.text.pdf.security.PrivateKeySignature;
import com.itextpdf.text.pdf.security.VerificationException;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;

import utiles.JCadenas;

public class JCertificadosSUN  extends JCertificadosAbstract {

    protected KeyStore ks;
    
    protected PrivateKey moPrivKey;

    public JCertificadosSUN() {
    }

    public void setKeyStore(KeyStore pk){
        ks=pk;
        moPrivKey=null;
    }
    
    public void setKeyStore(String psTipo, String psRuta, String psPassword) throws Exception{
        msPass=psPassword;
        if(JCadenas.isVacio(psTipo)){
            psTipo="pkcs12";
        }
        ks = KeyStore.getInstance(psTipo);
        ks.load(new FileInputStream(psRuta), psPassword.toCharArray());
    }

    public KeyStore getKeyStore() throws Exception{
        if(ks == null){
            ks = KeyStore.getInstance("Windows-MY");
            // Note: When a security manager is installed,
            // the following call requires SecurityPermission
            // "authProvider.SunMSCAPI".
            if(msPass!=null && !msPass.equals("")) {
                ks.load(null, msPass.toCharArray());
            }else{
                ks.load(null, null);
            }
            moPrivKey=null;
        }
        return ks;
    }
    /**Devuelve un vector de X509Certificate*/
    public Vector getListaCertificados() throws Exception {
        Vector loVec = new Vector();
        java.util.Enumeration en = getKeyStore().aliases() ;

	while (en.hasMoreElements()) {
            String aliasKey = (String)en.nextElement() ;
            Object c = getKeyStore().getCertificate(aliasKey) ;
            if(ks.isKeyEntry(aliasKey) && c instanceof X509Certificate){
                loVec.add(c);
            }
	}

        return loVec;
    }

    public String getCertificateAlias(X509Certificate poCert) throws Exception {
        return getKeyStore().getCertificateAlias(poCert);
    }

    /**Establece el certificado a traves del alias*/
    public void setX509Certificate(String psAlias) throws Exception {
        setX509Certificate((X509Certificate)getKeyStore().getCertificate(psAlias));

        if(moX509 == null){
            throw new Exception("Alias ("+psAlias+") no encontrado");
        }
    }
    /**Establece el certificado X509Certificate*/
    public void setX509Certificate(X509Certificate poParam)  throws Exception {
        moX509 = poParam;
        moPrivKey=null;
    }


    /**Devuelve la firma de los datos pasados por parametro*/
    public byte[] sign(byte[] labDatos) throws Exception {
        Provider p = getKeyStore().getProvider();
        Signature sig = Signature.getInstance("SHA1withRSA", p);
        PrivateKey loPrivKey = getPrivateKey();
        sig.initSign(loPrivKey);
        sig.update(labDatos);
        return sig.sign();
    }
    public PrivateKey getPrivateKey() throws Exception {
        if(moPrivKey==null) {
            if(msPass==null || msPass.equalsIgnoreCase("")){
                moPrivKey=(PrivateKey) getKeyStore().getKey(getAlias(), null);
            }else{
                moPrivKey=(PrivateKey) getKeyStore().getKey(getAlias(), msPass.toCharArray());
            }
        }
        return moPrivKey;
    }
    
    public Provider getProvider(){
        return ks.getProvider();
    }

    public void fimarPDF(InputStream poPDFOriginal, OutputStream poPDFSalida, String psRazon, String psLocalizacion, Rectangulo poRect, int plPagina) throws Exception{
        String alias = getAlias();
        PrivateKey key = getPrivateKey();
        Certificate[] chain = ks.getCertificateChain(alias);
        
        PdfReader reader = new PdfReader(poPDFOriginal);
        PdfStamper stamper = PdfStamper.createSignature(reader, poPDFSalida, '\0');
        // appearance
        PdfSignatureAppearance appearance = stamper .getSignatureAppearance();
        appearance.setReason(psRazon);
        appearance.setLocation(psLocalizacion);
        // rectangulo de la firma en el pdf, si null no se ve nada de la firma
        if(poRect!=null) {
        	appearance.setVisibleSignature(
                new Rectangle(
                    poRect.x, poRect.y,
                    poRect.x+poRect.width, poRect.y+poRect.height), 1, null);
        }
        
        // digital signature
        ExternalSignature es = new PrivateKeySignature(key, "SHA-256", "BC");
        ExternalDigest digest = new BouncyCastleDigest();
        MakeSignature.signDetached(appearance, digest, es, chain, null, null, null, 0, CryptoStandard.CMS);        
    }

    public void verificarPDF(InputStream poPDFFirmado, OutputStream poPDFRevision) throws Exception{
        PdfReader reader = new PdfReader(poPDFFirmado);
        AcroFields af = reader.getAcroFields();
        ArrayList names = af.getSignatureNames();
        for (int k = 0; k < names.size(); ++k) {
           String name = (String)names.get(k);
           System.out.println("Signature name: " + name);
           System.out.println("Signature covers whole document: " + af.signatureCoversWholeDocument(name));
           System.out.println("Document revision: " + af.getRevision(name) + " of " + af.getTotalRevisions());
           //extraer la revision
//           poPDFRevision = new FileOutputStream("revision_" + af.getRevision(name) + ".pdf");

           if(poPDFRevision!=null){
               byte bb[] = new byte[8192];
               InputStream ip = af.extractRevision(name);
               int n = 0;
               while ((n = ip.read(bb)) > 0){
                  poPDFRevision.write(bb, 0, n);
               }
               poPDFRevision.close();
               ip.close();
            }
//           // End revision extraction
           PdfPKCS7 pk = af.verifySignature(name);
           Calendar cal = pk.getSignDate();
           Certificate pkc[] = pk.getCertificates();
           System.out.println("Subject: " + CertificateInfo.getSubjectFields(pk.getSigningCertificate()));
           System.out.println("Document modified: " + !pk.verify());
           List<VerificationException> errors = CertificateVerification.verifyCertificates(pkc, ks, null, cal);
           if (errors.size() != 0)
        	   throw new Exception("Verificación incorrecta: " + errors.get(0));
        }

    }


    

    /*
    @param args the command line arguments
     */

    public static void main(String[] args) throws Exception {
        ICertificados loAux = new JCertificadosSUN();
        Vector loVector = loAux.getListaCertificados();
        loAux.mostrarInformacionCertificados(loVector);
        loAux.setX509Certificate((X509Certificate) loVector.get(0));
        byte[] lab = loAux.sign("pepe".getBytes());

        System.out.println(loAux.getTransFormarABASE64(lab));

        System.out.println("    La verificación resultó:  " + loAux.verificar("pepe".getBytes(), lab) + "!!!\n");
    }



}
//
// /**Consigue el privatekey y x509certificate, es decir, el certificado y la clave privada asociada*/
//    public void initSignIE() throws Exception {
//
//
//        Vector<X509Certificate> listCertificates = null;
//        // Accedemos al almacén de certificados de internet explorer
//        InterfazFirma si = UtilidadFirmaElectronica.getSignatureInstance(EnumAlmacenCertificados.ALMACEN_EXPLORER);
//
//        listCertificates = si.getAllCertificates("My");
//
//        // Accedemos al almacén de certificados de Mozilla Firefox
//        //InterfazFirma si = UtilidadFirmaElectronica.getSignatureInstance(EnumAlmacenCertificados.ALMACEN_MOZILLA);
//        //listCertificates = si.getAllCertificates("Poner aqui la ruta al perfil de Mozilla");
//
//        System.out.println("Hay " + listCertificates.size() + " certificados");
////		mostrarInformacionCertificados(listCertificates);
//        //recogemos el certificado para firmar
////        moCerts = (X509Certificate[]) listCertificates.toArray();
//        //Seleccionamos el primero de los certificados para firmar
//        moX509 = (X509Certificate) listCertificates.get(0);
////        // Consigue la clave privada
////        moPriv = (PrivateKey) keystore.getKey(alias, pwd);
////
////        if (moPriv == null) {
////            throw new Exception(alias + " could not be accessed");
////        }
//    }
//    //internet explorer
//    public void signIE() throws Exception {
//        byte[] labDatos = "perico de los palotes".getBytes();
//        FirmaMSBridge loBrige = new FirmaMSBridge();
//        loBrige.setBinaryCertificate(moX509.getSignature());
//        loBrige.setToSign(labDatos);
//        loBrige.engineSetParameter(
//                ParametrosFirma.getInstance(
//                    moX509.getSerialNumber(),
//                    moX509.getIssuerDN().toString()));
//        byte[] labFirma = loBrige.engineSign();
//
//
//        System.out.println();
//        System.out.println(new String(labFirma));
//        System.out.println();
//
//        BASE64Encoder base64Encoder = new BASE64Encoder();
//        String lsFirmaBase64 = base64Encoder.encode(labFirma);
//
//        System.out.println();
//        System.out.println(lsFirmaBase64);
//        System.out.println();
//
//        BASE64Decoder base64Decoder = new BASE64Decoder();
//        byte[] b= base64Decoder.decodeBuffer(lsFirmaBase64);
//        byte[] datos= labDatos;
//
//        //Verification:
//        System.out.println("    Verificando la firma ...");
//        Signature rsa_vfy = Signature.getInstance( "SHA1withRSA");
//        rsa_vfy.initVerify(moX509.getPublicKey());
//        rsa_vfy.update(datos);
//        System.out.println("    La verificación resultó:  " + rsa_vfy.verify(b) + "!!!\n");
//
//
//    }

