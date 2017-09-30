/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesGUIxSeguridad.https;

import java.net.Socket;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.X509KeyManager;
import utiles.JDepuracion;
import utilesGUIxSeguridad.ICertificados;


/**
 * Clase muy importante si el servidor nos pide que nos autentifiquemos con un certificado digital
 */
public class JTramitacionOnLineKeyManager implements X509KeyManager{

    private ICertificados moCertificado;
    
    public JTramitacionOnLineKeyManager() throws Exception{
        moCertificado=null;
    }
    public JTramitacionOnLineKeyManager(ICertificados poCertificado) throws Exception{
        moCertificado=poCertificado;
    }
//    public JTramitacionOnLineKeyManager(String psAlias) throws Exception{
//        moCertificado = new JCertificadosSUN();
//        moCertificado.setX509Certificate(psAlias);
//    }


    public String[] getClientAliases(String arg0, Principal[] arg1) {
        try {
            JDepuracion.anadirTexto(getClass().getName(), "ClientAliases");
            return new String[]{moCertificado.getAlias()};
        } catch (Exception ex) {
            JDepuracion.anadirTexto(getClass().getName(), ex);
        }
        return null;
    }

    public String chooseClientAlias(String[] arg0, Principal[] arg1, Socket arg2) {
        if(moCertificado!=null){
            try {
                return moCertificado.getAlias();
            } catch (Exception ex) {
                JDepuracion.anadirTexto(getClass().getName(), ex);
            }
        }
        return null;
    }

    public String[] getServerAliases(String arg0, Principal[] arg1) {
        return null;
    }

    public String chooseServerAlias(String arg0, Principal[] arg1, Socket arg2) {
        return null;
    }

    public X509Certificate[] getCertificateChain(String arg0) {
        if(moCertificado!=null){
            try {
//                X509Certificate[] certificados = new X509Certificate[]{moCertificado.getX509Certificate()};
                return moCertificado.getCertificateChain(arg0);
            } catch (Exception ex) {
                JDepuracion.anadirTexto(getClass().getName(), ex);
            }
        }
        return null;
    }

    public PrivateKey getPrivateKey(String arg0) {
        PrivateKey key = null;
        if(moCertificado!=null){
            try {
                key =  (PrivateKey) moCertificado.getPrivateKey();
                System.out.println("Obtiene la clave privada");
                System.out.println("argumento" + arg0);
            } catch (Exception ex) {
                JDepuracion.anadirTexto(getClass().getName(), ex);
            }
        }
        return key;
    }

   
}
