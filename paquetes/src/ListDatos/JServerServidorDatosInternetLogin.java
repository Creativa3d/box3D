/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ListDatos;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class JServerServidorDatosInternetLogin implements IServerServidorDatosInternetLogin {
    private static final long serialVersionUID = 1L;

    private String msLogin;
    private String msPassword;
    private JServerServidorDatosInternet moServer;
    private String msServlet;

    public JServerServidorDatosInternetLogin(String psLogin, String psPassword) {
        this(psLogin, psPassword, "loginAplicacion.ctrl");
    }

    public JServerServidorDatosInternetLogin(String psLogin, String psPassword, String psServlet) {
        msLogin = psLogin;
        msPassword = psPassword;
        msServlet = psServlet;
    }

    public void setServidorInternet(JServerServidorDatosInternet poServer) {
        moServer = poServer;
    }

    public boolean autentificar() throws Exception {
        boolean lbBien = true;

        //conectamos con url
        URLConnection connection = null;
        URL url = new URL(moServer.getURLBase1() + msServlet);
        connection = moServer.getOpenConnection().getConnection(url);
//        try{
//            //solo esta para la version 1.5
//            connection.setConnectTimeout(5000);//tiempo de respuesta maximo en milisegundos
//        }catch(Throwable e){}//por si el metodo no existe

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
        try {

            String inputLine = in.readLine();

            if (inputLine == null || (!inputLine.equals("0") && !inputLine.equals("1"))){
//                  &&
//                (HttpURLConnection.class.isAssignableFrom(connection.getClass())  &&
//                (
//                ((HttpURLConnection)connection).getResponseCode() >= HttpURLConnection.HTTP_BAD_REQUEST
//                ))
                throw new Exception("Parece ser que el servidor no esta disponible " + moServer.getURLBase1() + msServlet);
            }

            if (inputLine != null && inputLine.equals("1")) {
                inputLine = in.readLine();
                moServer.setIDSession(inputLine);
            } else {
//                while(inputLine != null){
//                    System.out.println(inputLine);
//                    inputLine = in.readLine();
//                }
                lbBien = false;
            }
        } finally {
            if (in != null) {
                in.close();
            }
        }
        return lbBien;

    }
}
