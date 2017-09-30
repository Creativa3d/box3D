/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utiles.red;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author daniel
 */
public class ProxyConfig {

    private static Proxy moProxy;
    private static boolean mbBusqudaHecha = false;

    private static void setProxy() {
        try {
            System.setProperty("java.net.useSystemProxies", "true");
            Proxy proxy = (Proxy) ProxySelector.getDefault().select(new URI("http://www.redyser.com/")).iterator().next();
            System.out.println("proxy Type : " + proxy.type());
            InetSocketAddress addr = (InetSocketAddress) proxy.address();
            if (addr == null) {
                System.out.println("No Proxy");
            } else {
                System.out.println("proxy hostname : " + addr.getHostName());
                System.out.println("proxy port : " + addr.getPort());
                // No sirve para nada!
                System.setProperty("http.proxyHost", addr.getHostName());
                System.setProperty("http.proxyPort", String.valueOf(addr.getPort()));
            }
            moProxy = proxy;

        } catch (Exception ex) {
            moProxy = null;
            Logger.getLogger(ProxyConfig.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static synchronized Proxy getProxy() {
        if( !mbBusqudaHecha ){
            mbBusqudaHecha = true;
            setProxy();
        }
//        System.out.println("proxy port : " + ((InetSocketAddress)moProxy.address()).getHostName());
        return moProxy;
    }
}
