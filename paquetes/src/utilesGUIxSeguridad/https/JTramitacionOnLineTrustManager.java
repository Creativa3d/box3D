/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesGUIxSeguridad.https;

import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import javax.net.ssl.X509TrustManager;

/**
 * Comprueba que el certificado del servidor servidor este en nuestra cadena de confianza
 * ,es decir, para conexion segura el servidor devuelve un certificado, ese certificado debe de estar
 * en nuestro almacen de certificados o tener una cadena de confianza en nuestro almacen de certificados
 * Tiene una propiedad para pasarse por el ... toda esa comprobacion
 */
public class JTramitacionOnLineTrustManager implements X509TrustManager{

   
    
    private Set certs = new HashSet ();
    private boolean mbAllTrusted = false;

    public JTramitacionOnLineTrustManager(){

    }
    public JTramitacionOnLineTrustManager(boolean pbAllTrusted){
        mbAllTrusted=pbAllTrusted;
    }


    public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
    }

    public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
        //siempre chekea a cierto
        if(!mbAllTrusted){
            
            try {
                KeyStore trustore = KeyStore.getInstance("Windows-ROOT");
                trustore.load(null);
                Enumeration alias = trustore.aliases();
                while (alias.hasMoreElements()) {
                    certs.add((X509Certificate) trustore.getCertificate((String)alias.nextElement()));
                }
                for (int i = 0; i < arg0.length; i++) {
                    if (certs.contains(arg0[i])) {
                        System.out.println("checkea el servidor");
                        return;
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new CertificateException(ex.getMessage());
            }
            throw new CertificateException("No se encuentra el certificado del servidor");
        }
    }

    public X509Certificate[] getAcceptedIssuers() {
        return null;
    }

}
