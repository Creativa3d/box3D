/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesGUIxSeguridad;


import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.CRL;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.Vector;
import javax.security.auth.x500.X500Principal;
import org.apache.commons.codec.binary.Base64;
import utiles.JDepuracion;
/**
 *
 * @author eduardo
 */
public abstract class JCertificadosAbstract implements ICertificados{
    private Base64 moBase64Encoder = new Base64();
    private Base64 moBase64Decoder = new Base64();
    protected transient String msPass;
    protected X509Certificate moX509 = null;

    public String getTransFormarABASE64(byte[] labFirma) {
        return new String(moBase64Encoder.encodeBase64(labFirma));
    }
    public X509Certificate[] getCertificateChain(String psAlias){
            try {
                
                X509Certificate[] certificados = new X509Certificate[]{getX509Certificate()};
                return certificados;
//                return ks.getCertificateChain(psAlias);
            } catch (Exception ex) {
                JDepuracion.anadirTexto(getClass().getName(), ex);
            }
            return null;
    }
    public byte[] getDesTransFormarABASE64(String lsFirmaBase64) throws IOException {
        return moBase64Decoder.decodeBase64(lsFirmaBase64.getBytes());
    }

    public void setPassword(String psPass) throws Exception {
        msPass = psPass;
    }
    public String getPassword(){
        return msPass;
    }

    public X509Certificate getX509Certificate()  throws Exception {
        return moX509;
    }

    /**Devuelve el alias*/
    public String getAlias() throws Exception{
        if(moX509!=null){
            return getCertificateAlias(moX509);
        }else{
            return null;
        }
    }
    
    public String toString(){
        try {
            return getAlias();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }
    public static void parseX509Certificate(X509Certificate x509cert) throws Exception {
                                                    
        // Comenzó con un certificado de la API de extracción de la información pertinente: 
        // Leer el número de versión de este certificado de reconocimiento de certificados, el número de versión de versión de la norma de este certificado x.509, información que puede utilizarse para la influencia que pueda especificar el certificado
        // Hasta la fecha, ha definido tres versiones
        int version = x509cert.getVersion();
        JDepuracion.anadirTexto(JCertificadosAbstract.class.getName(), "N el número de versión para el certificado: " + version);
        // Leer el número de serie del certificado
        BigInteger serialNumber = x509cert.getSerialNumber();
        JDepuracion.anadirTexto(JCertificadosAbstract.class.getName(), "Número de secuencia n Certificado:" + (new Base64()).encode(serialNumber.toByteArray()));
        // Leer el nombre de la firma el certificado CA con este algoritmo para el certificado de firma
        String algName = x509cert.getSigAlgName();
        JDepuracion.anadirTexto(JCertificadosAbstract.class.getName(), "Certificado de firma n llamado: " + algName);
        // Un emisor de los certificados, la adopción de normas cuyo nombre X.500 y dar información
        // El certificado del emisor es normalmente un certificado CA, significa que el uso de la confianza de las entidades firmar el certificado
        X500Principal issuerPrincipal = x509cert.getIssuerX500Principal();
        JDepuracion.anadirTexto(JCertificadosAbstract.class.getName(), "N para la emisión del certificado:" + issuerPrincipal.getName());
        // Período de validez del certificado de leer
        Date notAfter = x509cert.getNotAfter();
        Date notBefore = x509cert.getNotBefore();
        JDepuracion.anadirTexto(JCertificadosAbstract.class.getName(), "Período de validez del certificado como: después de " + notBefore.toLocaleString () 
                +" antes de " + notAfter.toLocaleString () +"");
        // El tema de la lectura de los certificados, que representa la clave pública de las entidades siguen utilizando criterios X.500, su nombre
        X500Principal subjectPrincipal = x509cert.getSubjectX500Principal();
        JDepuracion.anadirTexto(JCertificadosAbstract.class.getName(), "Certificado de tema:" + subjectPrincipal.getName());
        // Leer el certificado de clave pública
        PublicKey publicKey = x509cert.getPublicKey();
        JDepuracion.anadirTexto(JCertificadosAbstract.class.getName(), "N información clave pública de obtención del certificado");
        JDepuracion.anadirTexto(JCertificadosAbstract.class.getName(), "Algoritmo de certificado de clave pública:" + publicKey.getAlgorithm());
        JDepuracion.anadirTexto(JCertificadosAbstract.class.getName(), "El formato del certificado de clave pública:" + publicKey.getFormat());
        // Obtener el Array de bytes de clave pública
        byte[] publicKeyBytes = publicKey.getEncoded();
        JDepuracion.anadirTexto(JCertificadosAbstract.class.getName(), "Certificado de clave pública:" + (new Base64()).encode(publicKeyBytes));
        // Limitaciones básicas de lectura de certificado
        JDepuracion.anadirTexto(JCertificadosAbstract.class.getName(), "La longitud de la ruta del certificado en N:" + x509cert.getBasicConstraints());
        // La función de los servicios de certificación o el contenido de la clave pública que puede terminar
        boolean[] keyUsages = x509cert.getKeyUsage();
        // KeyUsage ::= BIT STRING {
        // digitalSignature (0),
        // nonRepudiation (1),
        // keyEncipherment (2),
        // dataEncipherment (3),
        // keyAgreement (4),
        // keyCertSign (5),
        // cRLSign (6),
        // encipherOnly (7),
        // decipherOnly (8) }
        if (keyUsages[0])
            JDepuracion.anadirTexto(JCertificadosAbstract.class.getName(), "La clave pública para el certificado de firma digital");
        if (keyUsages[1])
            JDepuracion.anadirTexto(JCertificadosAbstract.class.getName(), "La clave pública este certificado es innegable");
        if (keyUsages[2])
            JDepuracion.anadirTexto(JCertificadosAbstract.class.getName(), "Este certificado de cifrado de clave pública puede");
        if (keyUsages[3])
            JDepuracion.anadirTexto(JCertificadosAbstract.class.getName(), "La clave pública de este certificado de cifrado de los datos del usuario");
        if (keyUsages[4])
            JDepuracion.anadirTexto(JCertificadosAbstract.class.getName(), "Este certificado es clave para el Acuerdo de clave pública");
        if (keyUsages[5])
            JDepuracion.anadirTexto(JCertificadosAbstract.class.getName(), "Este certificado de clave pública para verificar la firma en el certificado");
        if (keyUsages[6])
            JDepuracion.anadirTexto(JCertificadosAbstract.class.getName(), "La clave pública de este certificado de revocación para verificar las noticias");
        if (keyUsages[7])
            JDepuracion.anadirTexto(JCertificadosAbstract.class.getName(), "La clave pública sólo puede utilizarse para este certificado de cifrado, y el Acuerdo de cumplimiento de la clave");
        if (keyUsages[8])
            JDepuracion.anadirTexto(JCertificadosAbstract.class.getName(), "La clave pública este certificado sólo puede utilizarse para descifrar la clave, y el Acuerdo de cumplimiento");
        // La mayor cadena de leer el certificado de firma
        String algOIDString = x509cert.getSigAlgOID();
        JDepuracion.anadirTexto(JCertificadosAbstract.class.getName(), "La cadena de certificados para la mayor firma el algoritmo de N: " + algOIDString);
        x509cert.getSigAlgParams();
        // El valor de leer el certificado de firma
        byte[] certSignature = x509cert.getSignature();
        JDepuracion.anadirTexto(JCertificadosAbstract.class.getName(), "Certificado de firma de valor n: " + (new Base64()).encode(certSignature));
        x509cert.getSubjectAlternativeNames();
        // Información del certificado de codificación binaria der leer el certificado
        byte[] tbsCertificate = x509cert.getTBSCertificate();
        JDepuracion.anadirTexto(JCertificadosAbstract.class.getName(), "Certificado de código binario der n el certificado de la información: " + (new Base64()).encode(tbsCertificate));
                                                   
    }
                                                
    /**
     * Obtener la lista de revocación de certificados
     * @param certificateName
     * @return
     * @throws Exception
     */
    public static CRL getCRLForCertifate(String certificateName) throws Exception {
        //Un certificado, y especifica el tipo de certificado x.509
        CertificateFactory certifateFactory = CertificateFactory.getInstance("X.509");
        //El flujo de entrada de obtención de certificados
        FileInputStream in = new FileInputStream(certificateName);
                                                    
        //Obtener la lista de revocación de certificados
        CRL crl = certifateFactory.generateCRL(in);
                                                    
        in.close();
                                                    
        return crl;
    }
         
    /**
     * Método que muestra por consola la informacion sobre los certificados pasados
     * @param listCertificates
     */
    public void mostrarInformacionCertificados(Vector listCertificates) {

        for (int a = 0; a < listCertificates.size(); a++) {
            X509Certificate certTemp = (X509Certificate) listCertificates.get(a);
            JDepuracion.anadirTexto(this.getClass().getName(), "INICIO certificado--------------------");
            try {
                parseX509Certificate(certTemp);                
            } catch (Exception ex) {
                JDepuracion.anadirTexto(getClass().getName(), ex);
            }
            try {
                JDepuracion.anadirTexto(this.getClass().getName(), "Alias -->" + getCertificateAlias(certTemp));
            } catch (Exception ex) {
                JDepuracion.anadirTexto(JCertificadosAbstract.class.getName(), ex);
            }            
            JDepuracion.anadirTexto(this.getClass().getName(), "FIN certificado--------------");
        }
    }
    /**Verifica datos y firma*/
    public boolean verificar(byte[] pabDatos, byte[] pabFirma) throws Exception {
        Signature rsa_vfy = Signature.getInstance("SHA1withRSA");
        rsa_vfy.initVerify(getX509Certificate().getPublicKey());
        rsa_vfy.update(pabDatos);

        boolean lbResult = rsa_vfy.verify(pabFirma);

        return lbResult;
    }




}
