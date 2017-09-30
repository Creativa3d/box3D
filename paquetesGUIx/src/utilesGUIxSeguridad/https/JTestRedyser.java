/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesGUIxSeguridad.https;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import utiles.red.*;

/**
 *
 * @author eduardo
 */
public class JTestRedyser {
    private static IOpenConnection msoOpenConnection = new JOpenConnectionDefault();
    public static void main(String[] args) throws Exception {
        try {
            SSLContext localSSLContext = SSLContext.getInstance("SSL");
            HttpsURLConnection.setDefaultHostnameVerifier((HostnameVerifier) new utilesGUIxSeguridad.https.JTramitacionOnLineHostNameVerifier(true));
            localSSLContext.init(new KeyManager[]{new utilesGUIxSeguridad.https.JTramitacionOnLineKeyManager()}, new TrustManager[]{new utilesGUIxSeguridad.https.JTramitacionOnLineTrustManager(true)}, null);
            HttpsURLConnection.setDefaultSSLSocketFactory(localSSLContext.getSocketFactory());
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        String lsURL = "https://192.168.30.99/cas/proxyValidate?service="
                + URLEncoder.encode("http://192.168.30.235:8080/redy/index.jsp") + "&ticket=" +
                  URLEncoder.encode("ST-3-29qNIN9FenHSb9mZUHef-cas");
        System.out.println(lsURL);
        URLConnection connection1 = msoOpenConnection.getConnection(new URL(lsURL));

        InputStream in = connection1.getInputStream();
        int l;
        while ((l = in.read()) >= 0) {
            System.out.print((char) l);
        }
    }
}
