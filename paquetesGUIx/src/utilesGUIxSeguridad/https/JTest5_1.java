/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesGUIxSeguridad.https;


import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyStore;
import java.security.Provider;
import java.security.Security;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import utiles.red.*;
import utilesGUIxSeguridad.JCertificadosSUN;
import utilesGUIxSeguridad.JFrameSeleccionarCertificado;

/**
 *
 * @author eduardo
 */
public class JTest5_1 {
    private static IOpenConnection msoOpenConnection = new JOpenConnectionDefault();
    private static SesionYCookies moSesionYCookies;

    public static void main(String[] args) throws Exception {
        //conectamos con url
        HttpsURLConnection connection = null;
        URL url = null;
        //config. para leer la dll PKCS11 directamente (en este caso la de la Universidad de Murcia)
        String ls =
                "name = DNIe" + "\n"
                + "slot = 1" + "\n"
                + "library = c:/WINDOWS/system32/UMU_PKCS11_v1_02.dll" + "\n"
                + "showInfo=true" + "\n";
        InputStream loConf = new ByteArrayInputStream(ls.getBytes());
        //Creamos el Provider con la clase SunPKCS11
        Provider nss = new sun.security.pkcs11.SunPKCS11(loConf);
        Security.addProvider(nss);
        //creamos el keystore
        KeyStore ks = KeyStore.getInstance("PKCS11", nss);
        ks.load(null, "3009".toCharArray());
//        //recorremos todos los certificados e imprimimos el titulo
//        Enumeration aliases = ks.aliases();
//        String alias = null;
//        while (aliases.hasMoreElements()) {
//            alias = (String) aliases.nextElement();
//            System.out.println(alias);
//        }

        //creamos el controlador de seguridad
        JCertificadosSUN loCert = new JCertificadosSUN();
        //establecemos el keystore
        loCert.setKeyStore(ks);
        //seleccionamos un certificado
        new JFrameSeleccionarCertificado(null, loCert).setVisible(true);

        SSLContext sc = SSLContext.getInstance("SSLv3");

        url = new URL(
                "http://fichasitv.carm.es/fichasitv/servlet/AutenticaTokenServlet");

        connection = (HttpsURLConnection) msoOpenConnection.getConnection(url);
        //establecemos el verificador de nombres, la funcion de esta clase es comprobar que el certificado del servidor
        //contiene la direccion del servidor, si se construye con true ignora esta comprobacion, util para pruebas
        connection.setHostnameVerifier(new JTramitacionOnLineHostNameVerifier(true));
        //establecemos el conexto ssl, con nuestro controlador de certificados que ya tiene el certificado a usar
        sc.init(
                //controlador de certificados que ya tiene el certificado a usar
                new KeyManager[]{
                    new JTramitacionOnLineKeyManager(loCert)
                },
                //TrustManager:comprueba la cadena de confianza del certificado del servidor
                //cuando se crea a true no comprueba la cadena de confianza del certificado del servidor, es util cuando somos nosotros los q creamos los certificados
                new TrustManager[]{new JTramitacionOnLineTrustManager(true)},
                null);
        connection.setSSLSocketFactory(sc.getSocketFactory());
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestProperty("User-Agent", "Mozilla/4.05 [en] (WinNT; I)");
        connection.setFollowRedirects(true);
        connection.setInstanceFollowRedirects(true);
        connection.setUseCaches(false);
        connection.setDoInput(true);
        connection.setDoOutput(true);

        OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());

        out.write("");
        out.close();

        System.err.println(connection.getResponseCode());

        InputStream in = connection.getInputStream();
        int l;
        StringBuffer lasBuffer = new StringBuffer(100);
        while ((l = in.read()) >= 0) {
            lasBuffer.append((char) l);
        }
        String lsURL = getLocalizarHref(lasBuffer);
        System.out.println(lsURL);
        lsURL = getNormalizarURL(lsURL);
        System.out.println(lsURL);



        moSesionYCookies = new SesionYCookies();
        ls = excutePost(lsURL, "", moSesionYCookies);
        System.out.println(ls);
        
    }

    private static String getLocalizarHref(StringBuffer pasBuffer){
        String lsResult = "";
        int lPosi = pasBuffer.indexOf("href=");
        int lPosi2 = pasBuffer.indexOf("\"", lPosi + 7);
        if(lPosi>=0){
            lsResult = pasBuffer.substring(lPosi+6, lPosi2);
        }
        return lsResult;
    }

    private static String getNormalizarURL(String lsURL) {
        lsURL = lsURL.replaceAll("ERROR=000 NO HAY ERRORES", "ERROR=000+NO+HAY+ERRORES");
        lsURL = lsURL.replaceAll("&amp;", "&");
        lsURL = lsURL.replaceAll("&#37;", "%");
        return lsURL;

    }
    private static String excutePost(String targetURL, String urlParameters, SesionYCookies loSesionYCookies) {
        URL url;
        HttpURLConnection connection = null;
        System.out.println(targetURL);
        try {
            //Create connection
            url = new URL(targetURL);
            connection = (HttpURLConnection) msoOpenConnection.getConnection(url);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("User-Agent", "Mozilla/4.05 [en] (WinNT; I)");
            connection.setRequestProperty("Content-Length", "" +
                    Integer.toString(urlParameters.getBytes().length));


            connection.setFollowRedirects(true);
            connection.setUseCaches(false);
            try {
                connection.setDoInput(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                connection.setDoOutput(true);
            } catch (Exception e) {
                e.printStackTrace();
            }

            loSesionYCookies.setCookies(connection);

//            connection.connect();
            if(urlParameters!=null && !urlParameters.equals("")){
                //Send request
                DataOutputStream wr = new DataOutputStream(
                        connection.getOutputStream());
                wr.writeBytes(URLEncoder.encode(urlParameters));
                wr.flush();
                wr.close();
            }
            loSesionYCookies.saveCookies(connection);

            //Get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            return response.toString();

        } catch (Exception e) {

            e.printStackTrace();
            return null;

        } finally {

            if (connection != null) {
                connection.disconnect();
            }
        }
    }

}


//<html><head><title>302 Moved Temporarily</title></head>
//<body bgcolor="#FFFFFF">
//<p>This document you requested has moved temporarily.</p>
//<p>It's now at <a href="http://fichasitv.carm.es/fichasitv/servlet/RecibeTokenServlet?ERROR=000 NO HAY ERRORES&amp;token=Rq9z2k4eULUWe&#37;2B7Go&#37;2BObQBzBwwCDeYdkrZACUa3&#37;2B08yUl6GlfMjyMJQrgAOiMsxhUJxPc17IlFXBHlUpzVgubqGZV3nVYvzFw&#37;2FzZjqlIKfwfUHkdISEjpQ&#37;3D&#37;3D">http://fichasitv.carm.es/fichasitv/servlet/RecibeTokenServlet?ERROR=000 NO HAY ERRORES&amp;token=Rq9z2k4eULUWe&#37;2B7Go&#37;2BObQBzBwwCDeYdkrZACUa3&#37;2B08yUl6GlfMjyMJQrgAOiMsxhUJxPc17IlFXBHlUpzVgubqGZV3nVYvzFw&#37;2FzZjqlIKfwfUHkdISEjpQ&#37;3D&#37;3D</a>.</p>
//</body></html>

//<html><head><title>Recibir token</title></head>                <body><div align="center">
//        <table border="1" width="70%" id="table3">                <tr>                        <td>
//        <table border="0" width="100%" cellspacing="0" cellpadding="0" bgcolor="#EEEEEE" id="table1">
//        <tr>                <td width="155">&nbsp;</td>                <td width="466">&nbsp;</td>
//        <td>&nbsp;</td>        </tr>        <tr>                <td width="155">&nbsp;</td>
//        <td width="466"><b><font face="Verdana" size="2">Sesión establecida con                 éxito</font></b>
//        </td>                <td>&nbsp;</td>        </tr>        <tr>                <td width="155">&nbsp;</td>
//        <td width="466"><font face="Verdana" size="2">NIF: 22980037R</font></td>
//        <td>&nbsp;</td>        </tr>        <tr>                <td width="155">&nbsp;</td>
//        <td width="466"><font face="Verdana" size="2">Nombre: Antonio González Carpena</font></td>
//        <td>&nbsp;</td>        </tr>        <tr>                <td width="155">&nbsp;</td>
//        <td width="466"><font face="Verdana" size="2">Fecha de última                 conexión anterior: 26/11/2010</font></td>
//        <td>&nbsp;</td>                        </tr>        <tr>                <td width="155">&nbsp;</td>
//        <td width="466">&nbsp;</td>                <td>&nbsp;</td>        </tr>        <tr>
//        <td width="155">&nbsp;</td>                <td width="466"><font face="Verdana" size="2">
//        <a href="/fichasitv/buscar_form.jsp">Continuar</a></font></td>                <td>&nbsp;</td>
//        </tr>        <tr>                <td width="155">&nbsp;</td>                <td width="466">&nbsp;</td>
//        <td>&nbsp;</td>        </tr></table>                        </td>                </tr>        </table>
//        </div></body></html>


//<form name="form1" method="POST" action="upload.jsp" enctype="multipart/form-data">
//
//<div id="ff0">
//			<td width="14%" align="left"><font face="Verdana" size="2">
//
//			Fichero(*):</font></td>
//			<td width="74%" align="left"><input type="file" name="F1" size="60"></td>
//
//
//				<td width="17%" align="left"><button class="clBoton" name="B3" type="submit"  onclick="if (valida()) return true; else return false;"  >
//				Cargar </button>
//
//				</td>
//				<td width="51%" align="left"><button class="clBoton" name="B4" type="reset">Limpiar</button>
//				</td>
//</form>
