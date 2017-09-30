/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesGUIxSeguridad;

import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfPKCS7;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSignatureAppearance;
import com.itextpdf.text.pdf.PdfStamper;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.Signature;
import java.security.UnrecoverableEntryException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;
import utiles.JCadenas;
import utiles.JDepuracion;

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
//        if(moPrivKey==null) {
            if(msPass==null || msPass.equalsIgnoreCase("")){
                moPrivKey=(PrivateKey) getKeyStore().getKey(getAlias(), null);
            }else{
                moPrivKey=(PrivateKey) getKeyStore().getKey(getAlias(), msPass.toCharArray());
            }
//        }
        return moPrivKey;
    }
    public KeyStore.PrivateKeyEntry getKeyEntry(final String alias,
    		                                    final String psPass) throws KeyStoreException,
    		                                                                               NoSuchAlgorithmException,
    		                                                                               UnrecoverableEntryException {

        if (this.ks == null) {
            throw new IllegalStateException("Se han pedido claves a un almacen no inicializado"); //$NON-NLS-1$
        }

        // El llavero de Mac OS X no responde al getKeyEntry(), solo al getKey(), pero
        // obligartoriamente hay que proporcionarle una cadena de texto no vacia y no nula
        // como contrasena. Esta cadena puede contener cualquier texto, no se comprueba.
        // Esta cadena de texto debe contener unicamente caracteres ASCII.
        if ("KeychainStore".equals(this.ks.getType())) { //$NON-NLS-1$
            JDepuracion.anadirTexto(this.getClass().getName(),"Detectado almacen Llavero de Mac OS X, se trataran directamente las claves privadas"); //$NON-NLS-1$
            Certificate[] certChain = this.ks.getCertificateChain(alias);
            if (certChain == null) {
                JDepuracion.anadirTexto(this.getClass().getName(),
                   "El certificado " + alias + " no tiene su cadena de confianza instalada en el Llavero de Mac OS X, se insertara solo este certificado" //$NON-NLS-1$ //$NON-NLS-2$
                );
                certChain = new Certificate[] {
                    this.ks.getCertificate(alias)
                };
            }
            try {
                return new KeyStore.PrivateKeyEntry((PrivateKey) this.ks.getKey(alias, "dummy".toCharArray()), certChain); //$NON-NLS-1$
            }
            catch(final UnrecoverableKeyException e) {
                throw new UnrecoverableEntryException(e.toString());
            }
        }
        return (KeyStore.PrivateKeyEntry) this.ks.getEntry(alias, new KeyStore.PasswordProtection(psPass.toCharArray()) );
    }    
    public Provider getProvider(){
        return ks.getProvider();
    }

     public void fimarPDF(InputStream poPDFOriginal, OutputStream poPDFSalida, String psRazon, String psLocalizacion, Rectangulo poRect, int plNumeroPagina) throws Exception{
        String alias = getAlias();
        PrivateKey key = getPrivateKey();
        Certificate[] chain = ks.getCertificateChain(alias);
        PdfReader reader = new PdfReader(poPDFOriginal);
        PdfStamper stp = PdfStamper.createSignature(reader, poPDFSalida, '\0');
        PdfSignatureAppearance sap = stp.getSignatureAppearance();
        sap.setCrypto(key, chain, null, PdfSignatureAppearance.WINCER_SIGNED);
       
        if(psRazon.length()>0){sap.setReason(psRazon);}
        if(psLocalizacion.length()>0){sap.setLocation(psLocalizacion);}
        // rectangulo de la firma en el pdf, si null no se ve nada de la firma
        if(poRect!=null) {
            sap.setVisibleSignature(
                new Rectangle(
                    poRect.x, poRect.y,
                    poRect.x+poRect.width, poRect.y+poRect.height), (plNumeroPagina>0?plNumeroPagina:1), null);
        }
        stp.close();

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
           System.out.println("Subject: " + PdfPKCS7.getSubjectFields(pk.getSigningCertificate()));
           System.out.println("Document modified: " + !pk.verify());
           Object fails[] = PdfPKCS7.verifyCertificates(pkc, ks, null, cal);
           if (fails != null){
               throw new Exception("Verificación incorrecta: " + fails[1]);
            }
        }

    }


    

    /*
    @param args the command line arguments
     */

    public static void main(String[] args) throws Exception {
//        final String psPassword ="suse90";
//        final String psPath = "/home/d/DocuTrabajo/otros/cer/fnmt.p12";
//        JCertificadosSUN loCert = new JCertificadosSUN();
//        loCert.setPassword(psPassword);
//        es.gob.afirma.keystores.main.common.AOKeyStoreManager ksm = new es.gob.afirma.keystores.main.common.AOKeyStoreManager();
//        ksm.init( es.gob.afirma.keystores.main.common.AOKeyStore.PKCS12, new FileInputStream(psPath)
//                , new javax.security.auth.callback.PasswordCallback("nada", false){
//                        @Override
//                        public char[] getPassword() {
//                            return psPassword.toCharArray();
//                        }
//                    }
//                , null );
//        loCert.setKeyStore((KeyStore) ksm.getKeyStores().get(0));
//        loCert.setX509Certificate((X509Certificate) loCert.getListaCertificados().get(0));   
//        
//        loCert.fimarPDF(
//                new FileInputStream("/home/d/Tenis -Ejercicios-de-entrenamiento-mental-en-pista.pdf")
//                , new FileOutputStream("/home/d/Tenis -Ejercicios-de-entrenamiento-mental-en-pistafirma.pdf")
//                , "razonmaiento ", "localizacion", new Rectangulo(100, 100));
        
        
//        Vector loVector = loCert.getListaCertificados();
//        loCert.mostrarInformacionCertificados(loVector);
//        loCert.setX509Certificate((X509Certificate) loVector.get(0));
//        byte[] lab = loCert.sign("pepe".getBytes());
//
//        System.out.println(loCert.getTransFormarABASE64(lab));
//
//        System.out.println("    La verificación resultó:  " + loCert.verificar("pepe".getBytes(), lab) + "!!!\n");
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

