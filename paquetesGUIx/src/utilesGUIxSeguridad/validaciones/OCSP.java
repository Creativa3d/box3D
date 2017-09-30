/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesGUIxSeguridad.validaciones;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import org.bouncycastle.asn1.ocsp.OCSPResponseStatus;
import org.bouncycastle.ocsp.*;
import utiles.JDepuracion;
import utiles.red.*;

/**
 *
 * En la clase OCSP se define el método OCSP_validation() cuyo propósito es el
 * de verificar la validez de un certificado X.509 a través de un servicio
 * remoto de validación de certificados del DNIe. En concreto el servicio en el
 * que se basa es http://ocsp.dnie.es Para realizar este ejemplo, se utilizan
 * las librerías de código abierto Bouncy Castle. Estas librerías proporcionan
 * una API con todo tipo de funciones relacionadas con la seguridad
 * computacional.
 */
public class OCSP {

    private static IOpenConnection msoOpenConnection = new JOpenConnectionDefault();
    public static final String mcsCertificadoValido = "Certificado Válido";
    public static final String mcsCertificadoRevocado = "Certificado Revocado";
    public static final String mcsCertificadoDesconocido = "Certificado Desconocido";
    public static final String mcsPeticiónNoValida = "Petición no válida";
    
    public String msURLValidacion = "http://ocsp.dnie.es";
    public String msACDNIE001_SHA1 = "ACDNIE001-SHA1.crt";
    public String msACDNIE002_SHA1 = "ACDNIE001-SHA2.crt";
    public String msACDNIE003_SHA1 = "ACDNIE001-SHA3.crt";

//##uso en tomcat
//OCSP ocsp = new OCSP();
//X509Certificate[] certs = (X509Certificate[]) request.getAttribute("javax.servlet.request.X509Certificate");
//if (certs != null)
//out.println(ocsp.OCSP_validation(certs[0])); //El primer certificado es el de Autenticación en el caso del DNIe
    
    public String OCSP_validation(X509Certificate certUsuario) throws Exception {

        /*
         * Se carga el proveedor necesario para la petición OCSP
         */

        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        InputStream inStreamIssuer1 = new FileInputStream(msACDNIE001_SHA1);
//        X509Certificate certImportadoIssuer1 = null;
        InputStream inStreamIssuer2 = new FileInputStream(msACDNIE002_SHA1);
//        X509Certificate certImportadoIssuer2 = null;
        InputStream inStreamIssuer3 = new FileInputStream(msACDNIE003_SHA1);
//        X509Certificate certImportadoIssuer3 = null;

        X509Certificate certImportadoIssuer = null;
        try {

            /*
             * Se extrae el nombre de la entidad emisora del certificado del
             * DNIe
             */
            String issuerCN = certUsuario.getIssuerX500Principal().getName("CANONICAL");
            CertificateFactory cfIssuer = CertificateFactory.getInstance("X.509");

            /*
             * En la validación OCSP se tendrá que usar el certificado de la CA
             * subordinada que emitió el certificado
             */

            if (issuerCN.contains("cn=ac dnie 001")) {
                certImportadoIssuer = (X509Certificate) cfIssuer.generateCertificate(inStreamIssuer1);
            } else if (issuerCN.contains("cn=ac dnie 002")) {
                certImportadoIssuer = (X509Certificate) cfIssuer.generateCertificate(inStreamIssuer2);
            } else if (issuerCN.contains("cn=ac dnie 003")) {
                certImportadoIssuer = (X509Certificate) cfIssuer.generateCertificate(inStreamIssuer3);
            }
        } catch (CertificateException ex) {
            JDepuracion.anadirTexto(getClass().getName(), ex);
        }
        inStreamIssuer1.close();
        inStreamIssuer2.close();
        inStreamIssuer3.close();

        /*
         * Se genera la petición OCSP con el certificado de la entidad emisora y
         * con el número de serie del certificado del titular del DNIe
         */

        OCSPReqGenerator ocspReqGen = new OCSPReqGenerator();
        ocspReqGen.addRequest(new CertificateID(CertificateID.HASH_SHA1, certImportadoIssuer, certUsuario.getSerialNumber()));
        OCSPReq ocspReq = ocspReqGen.generate();

        URL url = new URL(msURLValidacion);
        HttpURLConnection con = (HttpURLConnection) msoOpenConnection.getConnection(url);

        con.setRequestProperty("Content-Type", "application/ocsp-request");
        con.setRequestProperty("Accept", "application/ocsp-response");
        con.setDoOutput(true);
        OutputStream out = con.getOutputStream();
        DataOutputStream dataOut = new DataOutputStream(new BufferedOutputStream(out));

        /*
         * Se escribe la petición
         */

        dataOut.write(ocspReq.getEncoded());
        dataOut.flush();
        dataOut.close();

        /*
         * Se recoge la respuesta del servidor OCSP
         */

        InputStream in = (InputStream) con.getContent();
        OCSPResp ocspResponse = new OCSPResp(in);

        String resp = "";

        /*
         * Si la respuesta OCSP es correcta, se verifica el estado del
         * certificado
         */

        if (ocspResponse.getStatus() == OCSPResponseStatus.SUCCESSFUL) {
            CertificateID certID = new CertificateID(CertificateID.HASH_SHA1, certImportadoIssuer, certUsuario.getSerialNumber());
            BasicOCSPResp basicResp = (BasicOCSPResp) ocspResponse.getResponseObject();
            SingleResp[] singleResps = basicResp.getResponses();
            for (SingleResp singResp : singleResps) {
                CertificateID respCertID = singResp.getCertID();
                if (certID.equals(respCertID)) {
                    Object status = singResp.getCertStatus();
                    if (status == CertificateStatus.GOOD) {
                        resp = mcsCertificadoValido;
                    } else if (status instanceof org.bouncycastle.ocsp.RevokedStatus) {
                        resp = mcsCertificadoRevocado;
                    } else {
                        resp = mcsCertificadoDesconocido;
                    }
                }
            }
        } else {
            resp = mcsPeticiónNoValida;
        }
        return resp;
    }
}
