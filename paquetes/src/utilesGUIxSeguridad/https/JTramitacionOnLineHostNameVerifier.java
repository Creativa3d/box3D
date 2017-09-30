/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesGUIxSeguridad.https;

import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
 * Comprueba que el nombre del servidor que esta en el cerificado del mismo servidor sea igual que el servidor
 * ,es decir, para conexion segura el servidor devuelve un certificado, ese certificado tiene una propiedad sujeto
 * esta clase comprueba q ese sujeto sea igual al nombre del servidor (comparacion de cadenas llana y simplemente)
 * Tiene una propiedad para pasarse por el ... toda esa comprobacion
 */
public class JTramitacionOnLineHostNameVerifier implements HostnameVerifier {
    private boolean mbAllTrusted = false;

    public JTramitacionOnLineHostNameVerifier(){

    }
    public JTramitacionOnLineHostNameVerifier(boolean pbAllTrusted){
        mbAllTrusted=pbAllTrusted;
    }

    public boolean verify(String arg0, SSLSession arg1) {
        if(!mbAllTrusted){
            try {
                X509Certificate cert = (X509Certificate)arg1.getPeerCertificates()[0];
                check(arg0, cert);
                System.out.println("verifica servidor");
                return true;
            }
            catch (Exception e) {
                return false;
            }
        }else{
            return true;
        }
    }
    
    public void check(String host, X509Certificate cert)  throws Exception {
        if(!mbAllTrusted){
            System.out.println("chechea host");
            String subjetName = cert.getSubjectX500Principal().getName();
            String hostCertificado = subjetName.substring(
                    subjetName.indexOf("CN=")+"CN=".length(),
                    subjetName.indexOf(",", subjetName.indexOf("CN="))
                    );
            if (!hostCertificado.equalsIgnoreCase(host))
                throw new Exception("No se ha podido verificar el host");
        }
    }

}
