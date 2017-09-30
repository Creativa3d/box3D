/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utiles.red;

import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;

/**
 *
 * @author eduardo
 */
public class JOpenConnectionDefault implements IOpenConnection {

    public URLConnection getConnection(URL poURL) throws Exception {
        URLConnection connection = poURL.openConnection();
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
        
        return connection;
//        if (ProxyConfig.getProxy()==null || ProxyConfig.getProxy().type() == Proxy.Type.DIRECT) {
//            return poURL.openConnection(Proxy.NO_PROXY);
//        } else {
//            return poURL.openConnection(ProxyConfig.getProxy());
//        }
    }
}
