/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesGUIxSeguridad.https;

import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import utiles.red.*;
import utilesGUIxSeguridad.JCertificadosSUN;
import utilesGUIxSeguridad.JFrameSeleccionarCertificado;

/**
 *
 * @author eduardo
 */
public class JTest4 {
    private static IOpenConnection msoOpenConnection = new JOpenConnectionDefault();
    public static void main(String[] args) throws Exception {
//        excutePost("http://fichasitv.carm.es/fichasitv/servlet/TestTokenServlet","sesion=A09723DAD2670FA2DEEF8D6E2D946F76", new SesionYCookies());
//        HttpURLConnection loout = (HttpURLConnection) new URL("http://fichasitv.carm.es/fichasitv/servlet/TestTokenServlet?sesion=A09723DAD2670FA2DEEF8D6E2D946F76").openConnection();
//        URLConnection loout = new URL("http://fichasitv.carm.es/fichasitv/servlet/RecibeTokenServlet?ERROR=000 NO HAY ERRORES&token=Rq9z2k4eULUWe%2B7Go%2BObQBzBwwCDeYdkrZACUa3%2B08yUl6GlfMjyMJQrgAOiMsxhUJxPc17IlFXBHlUpzVgubhn17LizJ%2F9Xr%2B%2Fsvh2aQHnN6FNXFJgA9Q%3D%3D").openConnection();
//        URLConnection loout = enviarObjetoSeguro("http://fichasitv.carm.es/fichasitv/servlet/TestTokenServlet?sesion=AE897B6D74F3BEA631F8BF9BA0CAAA40", "",null);
//        URLConnection loout = enviarObjetoSeguro("https://esmur100081:8443/servidorDPABuena/", "",null);
        URLConnection loout = enviarObjetoSeguro("https://172.16.0.4:8443/", "",null);
        InputStream in = loout.getInputStream();
        int l;
        while((l =in.read())>=0 ) {
            System.out.print((char)l);
        }

//
//
//        moSesionYCookies = new SesionYCookies();
//
//
//        consultar(lsMatr, moSesionYCookies);


    }
    public static HttpsURLConnection enviarObjetoSeguro(final String psNombreServlet, String psParametros, String psMetodo) throws Exception{
        //conectamos con url
        HttpsURLConnection connection=null;
        URL url=null;
        JCertificadosSUN loCert = new JCertificadosSUN();
        new JFrameSeleccionarCertificado(null, loCert).setVisible(true);
        SSLContext sc = SSLContext.getInstance("SSLv3");

        sc.init(new KeyManager[]{
            new JTramitacionOnLineKeyManager(loCert)},
            new TrustManager[] { new JTramitacionOnLineTrustManager(true) },
            null);
        SSLSocketFactory ssf = sc.getSocketFactory();

        url = new URL(psNombreServlet);

        connection = (HttpsURLConnection) msoOpenConnection.getConnection(url);
        connection.setHostnameVerifier(new JTramitacionOnLineHostNameVerifier(true));
        connection.setSSLSocketFactory(ssf);
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestProperty("User-Agent", "Mozilla/4.05 [en] (WinNT; I)");
        connection.setFollowRedirects(true);
        connection.setInstanceFollowRedirects(true);
        connection.setUseCaches(false);
        connection.setDoInput(true);
        connection.setDoOutput(true);

        OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());

        out.write(psParametros);
        out.close();

        System.err.println(connection.getResponseCode());

        return connection;
    }

}
