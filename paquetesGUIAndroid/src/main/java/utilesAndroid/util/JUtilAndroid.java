/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesAndroid.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 *
 * @author eduardo
 */
public class JUtilAndroid {
    public static boolean verificaConexion(Context ctx) {
        boolean bConectado = false;
        ConnectivityManager connec = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        // No sólo wifi, también GPRS
        NetworkInfo[] redes = connec.getAllNetworkInfo();
        for (int i = 0; i < redes.length && !bConectado; i++) {
            // ¿Tenemos conexión? ponemos a true
            if (redes[i]!=null && redes[i].getState() == NetworkInfo.State.CONNECTED) {
                bConectado = true;
            }
        }
        return bConectado;
    }    
    public static boolean isConexionWIFI(Context ctx) {
        boolean bConectado = false;
        ConnectivityManager connec = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (mWifi.isConnected()) {
            bConectado = true;
        }
        return bConectado;
    }      
}
