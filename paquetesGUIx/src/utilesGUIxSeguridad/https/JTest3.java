/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesGUIxSeguridad.https;



import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.StringTokenizer;
import utiles.red.*;



/**
 *
 * @author eduardo
 */
public class JTest3 {

    private static IOpenConnection msoOpenConnection = new JOpenConnectionDefault();
    private static final long serialVersionUID = 1L;
    private static String mcsConst = "com.sun.faces.VIEW";
    private static String mcsFIRMAFICHERO = "firmafichero";
    private static String mcsFIRMAPASSWORD = "firmapassword";
//    private static String mcsproxyIP = "proxyIP";
//    private static String mcsproxyPort = "proxyPort";

    private static SesionYCookies moSesionYCookies= new SesionYCookies();

    public static void main(String[] args) throws Exception {
//        excutePost("http://fichasitv.carm.es/fichasitv/servlet/TestTokenServlet","sesion=A09723DAD2670FA2DEEF8D6E2D946F76", new SesionYCookies());
//        HttpURLConnection loout = (HttpURLConnection) new URL("http://fichasitv.carm.es/fichasitv/servlet/TestTokenServlet?sesion=A09723DAD2670FA2DEEF8D6E2D946F76").openConnection();
//        URLConnection loout = new URL("http://fichasitv.carm.es/fichasitv/servlet/RecibeTokenServlet?ERROR=000 NO HAY ERRORES&token=Rq9z2k4eULUWe%2B7Go%2BObQBzBwwCDeYdkrZACUa3%2B08yUl6GlfMjyMJQrgAOiMsxhUJxPc17IlFXBHlUpzVgubhn17LizJ%2F9Xr%2B%2Fsvh2aQHnN6FNXFJgA9Q%3D%3D").openConnection();
//        URLConnection loout = enviarObjetoSeguro("http://fichasitv.carm.es/fichasitv/servlet/TestTokenServlet?sesion=AE897B6D74F3BEA631F8BF9BA0CAAA40", "",null);


        moSesionYCookies = new SesionYCookies();
        String ls = excutePost(
                "http://fichasitv.carm.es/fichasitv/servlet/RecibeTokenServlet"
                + "?ERROR="
                + URLEncoder.encode("000 NO HAY ERRORES", "ISO-8859-1")
                + "&token="
                + "Rq9z2k4eULUWe%2B7Go%2BObQBzBwwCDeYdkrZACUa3%2B08yUl6GlfMjyMJQrgAOiMsxhUJxPc17IlFXBHlUpzVgubvaEstcMoSfFW8x0DqFNozQYCxK5wRhBLw%3D%3D",
                "",
                moSesionYCookies
                );
        System.out.println(ls);

//
//
//        moSesionYCookies = new SesionYCookies();
//
//
//        consultar(lsMatr, moSesionYCookies);


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

            setCookies(connection, loSesionYCookies);
//            connection.connect();
//            //Send request
//            DataOutputStream wr = new DataOutputStream(
//                    connection.getOutputStream());
//            wr.writeBytes(URLEncoder.encode(urlParameters));
//            wr.flush();
//            wr.close();

            saveCookies(connection, loSesionYCookies);

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

    private static void saveCookies(URLConnection urlconn, SesionYCookies loSesionYCookies) {
        int i = 0;
        String key = null;
        String cookie = null;
        String host = null;
        String headerfield = null;
        StringTokenizer tok = null;

// get the host
        host = urlconn.getURL().getHost();

// forward pass any starting null values
        while (urlconn.getHeaderFieldKey(i) == null) {
            i++;
        }

// check all the headerfields until there are no more
        while (urlconn.getHeaderFieldKey(i) != null) {
            key = urlconn.getHeaderFieldKey(i);

// check if it is a Set-Cookie header
            if (key.equalsIgnoreCase("set-cookie")) {
                headerfield = urlconn.getHeaderField(i);
                if (headerfield != null) // can the headerfield be null here ?
                {
// parse out only the name=value pair and ignore the rest
                    tok = new StringTokenizer(headerfield, ";", false);

// if this is anything but the first cookie add a semicolon and a space
// before we add the next cookie.
                    cookie = (cookie != null ? cookie + "; " + tok.nextToken() : tok.nextToken());
                }
            }
            i++;
        }

// save the cookies in our cookie collection with the hostname as key
        if (cookie != null) {
            try {

//                System.out.println("Saving cookies: " + cookie);
//                System.in.read();
//                System.in.read();

                loSesionYCookies.getCookies().put(host, cookie);
            } catch (Exception ioe) {
            }

        }
    }

    private static void setCookies(URLConnection urlconn, SesionYCookies loSesionYCookies) {
        String headerfield = "";
        String host = "";

// get the host
        host = urlconn.getURL().getHost();

// get saved cookies from hashtable
        headerfield = (String) loSesionYCookies.getCookies().get(host);

// check if there are any saved cookies
        if (headerfield != null) {
//            System.out.println("Setting cookies: " + headerfield);

// set cookie string as request property
            urlconn.setUseCaches(false);
            urlconn.setRequestProperty("Connection", "Keep-Alive");
//            urlconn.setRequestProperty("Host", "localhost:8100");
            urlconn.setRequestProperty("Accept-Encoding", "gzip, deflate");
            urlconn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows 98; DigExt)");
            urlconn.setRequestProperty("Accept-Language", "en-us");
//            urlconn.setRequestProperty("Referer", "http://192.168.0.101/pm/old/anutest.asp");
            urlconn.setRequestProperty("Cookie", headerfield);
            urlconn.setRequestProperty("Content-Language", "en-US");
        }

    }




}
