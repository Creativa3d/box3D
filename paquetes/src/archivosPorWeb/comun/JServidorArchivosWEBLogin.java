/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package archivosPorWeb.comun;

import utiles.red.IOpenConnection;
import utiles.red.JOpenConnectionDefault;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class JServidorArchivosWEBLogin  {

    private static IOpenConnection msoOpenConnection = new JOpenConnectionDefault();
    private String msLogin;
    private String msPassword;
    private JServidorArchivosWeb moServer;
    private String msServlet;

    public JServidorArchivosWEBLogin(String psLogin, String psPassword) {
        this(psLogin, psPassword, "loginAplicacion.ctrl");
    }
    public JServidorArchivosWEBLogin(String psLogin, String psPassword, String psServlet) {
        msLogin = psLogin;
        msPassword = psPassword;
        msServlet = psServlet;
    }

    public void setServidorInternet(JServidorArchivosWeb poServer) {
        moServer = poServer;
    }

    public boolean autentificar() throws Exception {
        boolean lbBien = true;
        //conectamos con url
        URLConnection connection = null;
        URL url = new URL(moServer.getURLBase1() + msServlet);
        connection = msoOpenConnection.getConnection(url);
        //para evitar paginas estaticas
        connection.setUseCaches(false);
        //forzamos a que se ejecute el script
        connection.setDoOutput(true);
        //paso de parametros
        connection.getOutputStream().write(("login=" + msLogin + "&pass=" + msPassword).getBytes());


        BufferedReader in = null;
        in = (new BufferedReader(
                new InputStreamReader(
                connection.getInputStream())));
        String inputLine = in.readLine();

        if (inputLine != null && inputLine.equals("1")) {
            moServer.setIDSession(in.readLine());
        } else {
            lbBien = false;
        }
        return lbBien;

    }
}
