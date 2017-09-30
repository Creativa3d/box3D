/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesGUIxSeguridad.https;

import java.net.URLConnection;
import java.util.Hashtable;
import java.util.StringTokenizer;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.methods.PostMethod;

public class SesionYCookies {
    private Hashtable moCookies = new Hashtable();
//    private String msSerie;

    /**
     * @return the moCookies
     */
    public synchronized  Hashtable getCookies() {
        return moCookies;
    }

    /**
     * @param moCookies the moCookies to set
     */
    public synchronized  void setCookies(Hashtable moCookies) {
        this.moCookies = moCookies;
    }

    public void saveCookies(URLConnection urlconn) {
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

                getCookies().put(host, cookie);
            } catch (Exception ioe) {
            }

        }
    }

    public void setCookies(URLConnection urlconn) {
        String headerfield = "";
        String host = "";

// get the host
        host = urlconn.getURL().getHost();

// get saved cookies from hashtable
        headerfield = (String) getCookies().get(host);

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
    public void setCookies(HttpMethodBase request) {
        String headerfield = "";
        String host = "";

// get the host

        host = request.getHostConfiguration().getHost();

// get saved cookies from hashtable
        headerfield = (String) getCookies().get(host);

// check if there are any saved cookies
        if (headerfield != null) {
//            System.out.println("Setting cookies: " + headerfield);

// set cookie string as request property
            request.addRequestHeader("Cookie", headerfield);
        }

    }
    public void setCookies(PostMethod request) {
        String headerfield = "";
        String host = "";

// get the host

        host = request.getHostConfiguration().getHost();

// get saved cookies from hashtable
        headerfield = (String) getCookies().get(host);

// check if there are any saved cookies
        if (headerfield != null) {
//            System.out.println("Setting cookies: " + headerfield);

// set cookie string as request property
            request.addRequestHeader("Cookie", headerfield);
        }

    }


}
