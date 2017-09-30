/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesGUIxSeguridad.https;

//import com.sun.javaws.Globals;
//import com.sun.javaws.JAuthenticator;
//import com.sun.javaws.util.JavawsDialogListener;
import java.io.OutputStreamWriter;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;


import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import utilesGUIxSeguridad.ICertificados;
//import utilesGUIxSeguridad.JCertificadosMITyC;
import utilesGUIxSeguridad.JCertificadosSUN;
import utilesGUIxSeguridad.JFrameSeleccionarCertificado;



/**
 *
 * @author eduardo
 */
public class JTest {
//
//    private static final long serialVersionUID = 1L;
//    private static ICertificados moCertificado;
//
//    public static void main(String[] args) throws Exception {
//        moCertificado = new JCertificadosSUN();
////        moCertificado.setPassword("A30050629");
//        new JFrameSeleccionarCertificado(null, moCertificado).setVisible(true);
//        URLConnection loout = enviarObjetoSeguro("https://aplcr.dgt.es/WEB_ATEX/index.jsf", "",null);
//        InputStream in = loout.getInputStream();
//        int l;
//        while((l =in.read())>=0 ) {
//            System.out.print((char)l);
//        }
//
//
//
//
//    }
//    public static HttpsURLConnection enviarObjetoSeguro(final String psNombreServlet, String psParametros, String psMetodo) throws Exception{
//        //conectamos con url
//        HttpsURLConnection connection=null;
//        URL url=null;
//
//        SSLContext sc = SSLContext.getInstance("SSLv3");
//
//        sc.init(new KeyManager[]{new JTramitacionOnLineKeyManager(moCertificado)}, new TrustManager[] { new JTramitacionOnLineTrustManager() }, null);
//        SSLSocketFactory ssf = sc.getSocketFactory();
//
//        url = new URL(psNombreServlet);
//        connection = (HttpsURLConnection)url.openConnection();
//        connection.setHostnameVerifier(new JTramitacionOnLineHostNameVerifier());
//        connection.setSSLSocketFactory(ssf);
//
//
////        connection.setRequestMethod(psMetodo);
//        connection.setUseCaches(false);
//        connection.setDoInput(true);
//        connection.setDoOutput(true);
//
//        OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
//
//        out.write(psParametros);
//        out.close();
//
//        return connection;
//    }


}
