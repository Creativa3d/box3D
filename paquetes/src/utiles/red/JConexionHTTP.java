/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utiles.red;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HttpsURLConnection;

/**
 *
 * @author daniel
 */
public class JConexionHTTP {

    private static IOpenConnection msoOpenConnection = new JOpenConnectionDefault();
    protected static String msCharset = "ISO-8859-1";
    protected static int miTimeout = 0;
    protected String msURL;
    protected String msParametros;
    protected String msProtocolo;

    public static int getPort(String psConexion) {
        int retVal;
        String lsHost = psConexion.split("/")[0];
        String lsPuerto = lsHost.split(":")[1];
        retVal = Integer.parseInt(lsPuerto);
        return retVal;
    }

    public JConexionHTTP(String psRequest, String psDatos, String psProtocolo) {
        setURL(psRequest);
        setDatos(psDatos);
        msProtocolo = psProtocolo;
    }

    public JConexionHTTP(String psRequest) {
        setURL(psRequest);
        setDatos("");
    }

    public final void setURL(String psURL) {
        msURL = psURL;
    }

    public final void addDatos(String psParametro) {
        if (!"".equals(msParametros)) {
            msParametros += "&";
        }
        msParametros += psParametro;
    }

    public final void addDatos(String psNombre, String psValor) {
        addDatos(psNombre + "=" + psValor);
    }

    public final void setDatos(String psParametros) {
        msParametros = psParametros;
    }

    public static boolean checkConexion(String psIP, int piPort) {
        return checkConexion(psIP, piPort, 1000);
    }

    public static boolean checkConexion(String psIP, int piPort, int piTimeout) {
        boolean retVal;
        String lsHost = "http://" + psIP + ":" + String.valueOf(piPort);
        BufferedReader loReader = null;
        URLConnection conn;
        try {
            URL url = new URL(lsHost + "/NoDebeEstarOSiDaIgual.html");
            conn = msoOpenConnection.getConnection(url);
            conn.setReadTimeout(piTimeout);
            loReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            System.out.println("[OK]Servidor: " + lsHost + " - Esta vivo y responde");
            retVal = true;
        } catch (MalformedURLException e) {     // No es una URL valida
            System.out.println("[ERR]Servidor: " + lsHost + " - No es una URL valida");
            retVal = false;
        } catch (ConnectException e) {          // El servidor no esta
            System.out.println("[ERR]Servidor: " + lsHost + " - No esta disponible");
            retVal = false;
        } catch (FileNotFoundException e) {     // El servidor responde, pero no existe el archivo
            System.out.println("[OK]Servidor: " + lsHost + " - Esta vivo");
            retVal = true;
        } catch (SocketTimeoutException e) {    // Tarda mucho en contestar
            System.out.println("[ERR]Servidor: " + lsHost + " - Tarda demasiado en responder");
            retVal = false;
        } catch (Exception e) {                 // Cualquier otra cosa
            System.out.println("[ERR]Servidor: " + lsHost + " - " + e.getMessage());
            retVal = false;
        } finally {
            if (loReader != null) {
                try {
                    loReader.close();
                } catch (IOException ex) {
                    System.out.println("Problema al cerrar el objeto lector");
                }
            }
        }
        return retVal;
    }

    public String conexionGET() {
        return conexionGET(miTimeout, msCharset);
    }

    public String conexionGET(int piTimeout) {
        return conexionGET(piTimeout, msCharset);
    }

    public String conexionGET(String psCharset) {
        return conexionGET(miTimeout, psCharset);
    }

    public String conexionGET(int piTimeout, String psCharset) {
        String retVal = "";
        String lsHost = msURL + "?" + msParametros;
        BufferedReader loReader = null;
        try {
            URL url = new URL(lsHost);
            System.out.println("Conectando (GET) con: " + url.toString());
            URLConnection conn;
            if ("HTTPS".equals(msProtocolo)) {
                conn = (HttpsURLConnection) msoOpenConnection.getConnection(url);
            } else {
                conn = msoOpenConnection.getConnection(url);
            }
            conn.setReadTimeout(piTimeout);
            loReader = new BufferedReader(new InputStreamReader(conn.getInputStream(), psCharset));

            String line;
            while ((line = loReader.readLine()) != null) {
                retVal += line;
            }

        } catch (ConnectException e) {          // El servidor no esta
            System.out.println("[ERR]Servidor: " + lsHost + " - No esta disponible");
        } catch (FileNotFoundException e) {     // El servidor responde, pero no existe el archivo
            System.out.println("[ERR]Servidor: " + lsHost + " - No tiene esa ruta");
        } catch (SocketTimeoutException e) {    // Tarda mucho en contestar
            System.out.println("[ERR]Servidor: " + lsHost + " - Tarda demasiado en responder");
        } catch (Exception e) {                  // Cualquier otra cosa
            System.out.println("[ERR]Servidor: " + lsHost + " - " + e.getMessage());
        } finally {
            try {
                if (loReader != null) {
                    loReader.close();
                }
            } catch (IOException ex) {
                System.out.println("Problema al cerrar el objeto lector");
            }

        }
        return retVal;
    }

    public String conexionPOST() {
        return conexionPOST(miTimeout, msCharset);
    }

    public String conexionPOST(int piTimeout) {
        return conexionPOST(piTimeout, msCharset);
    }

    public String conexionPOST(String psCharset) {
        return conexionPOST(miTimeout, psCharset);
    }

    public boolean conexionPOST(File pfSalida) {
        return conexionPOST(miTimeout, msCharset, pfSalida);
    }

    public boolean conexionPOST(int piTimeout, File pfSalida) {
        return conexionPOST(piTimeout, msCharset, pfSalida);
    }

    public boolean conexionPOST(String psCharset, File pfSalida) {
        return conexionPOST(miTimeout, psCharset, pfSalida);
    }

    public boolean conexionPOST(int piTimeout, String psCharset, File pfSalida) {
        boolean retVal;
        BufferedReader loReader = conexionPOSTprivada(piTimeout, psCharset);
        FileOutputStream loWriter = null;
        if (loReader != null && pfSalida.canWrite()) {
            //Escritura
            try {
                loWriter = new FileOutputStream(pfSalida);
                int lectura = 0;
                while (lectura != -1) {
                    lectura = loReader.read();
                    if (lectura != -1) {
                        loWriter.write(lectura); //escribimos en el archivo
                    }
                }
                loWriter.close();
                retVal = true;
                loReader.close();
            } catch (FileNotFoundException e) {
                retVal = false;
                Logger.getLogger(JConexionHTTP.class.getName()).log(Level.SEVERE, null, e);
            } catch (IOException e) {
                retVal = false;
                Logger.getLogger(JConexionHTTP.class.getName()).log(Level.SEVERE, null, e);
            } finally {
                try {
                    if (loWriter != null) {
                        loWriter.close();
                    }
                } catch (IOException e) {
                    System.out.println("Exception al cerrar el escritor");
                }
                try {
                    if (loReader != null) {
                        loReader.close();
                    }
                } catch (IOException ex) {
                    System.out.println("Exception al cerrar el lector");
                }
            }
        } else {
            retVal = false;
        }
        return retVal;
    }

    public String conexionPOST(int piTimeout, String psCharset) {
        BufferedReader loReader = conexionPOSTprivada(piTimeout, psCharset);
        String retVal = "";
        String line;
        try {
            while ((line = loReader.readLine()) != null) {
                retVal += line;
            }
        } catch (IOException e) {
            Logger.getLogger(JConexionHTTP.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {       // Cerramos el Reader
                if (loReader != null) {
                    loReader.close();
                }
            } catch (IOException e) {
                System.out.println("Problema al cerrar el objeto lector");
            }

        }
        return retVal;
    }

    private BufferedReader conexionPOSTprivada(int piTimeout, String psCharset) {
        String lsHost = msURL;
        BufferedReader retVal = null;
        OutputStreamWriter loWriter = null;
        try {
            URL url = new URL(lsHost);
            System.out.println("Conectando (POST) con: " + url.toString());
            URLConnection conn;
            if ("HTTPS".equals(msProtocolo)) {
                conn = (HttpsURLConnection) msoOpenConnection.getConnection(url);
            } else {
                conn = msoOpenConnection.getConnection(url);
            }
            conn.setReadTimeout(piTimeout);

            //Escribir los parametros en el mensaje
            conn.setDoOutput(true);
            loWriter = new OutputStreamWriter(conn.getOutputStream());
            loWriter.write(msParametros);
            loWriter.flush();

            //Recibir respuesta
            retVal = new BufferedReader(new InputStreamReader(conn.getInputStream(), psCharset));

        } catch (ConnectException e) {          // El servidor no esta
            System.out.println("[ERR]Servidor: " + lsHost + " - No esta disponible");
        } catch (FileNotFoundException e) {     // El servidor responde, pero no existe el archivo
            System.out.println("[ERR]Servidor: " + lsHost + " - No tiene esa ruta");
        } catch (SocketTimeoutException e) {    // Tarda mucho en contestar
            System.out.println("[ERR]Servidor: " + lsHost + " - Tarda demasiado en responder");
        } catch (Exception e) {                  // Cualquier otra cosa
            System.out.println("[ERR]Servidor: " + lsHost + " - " + e.getMessage());
        } finally {
            // Cerramos el Writer
            try {
                if (loWriter != null) {
                    loWriter.close();
                }
            } catch (IOException ex) {
                System.out.println("Problema al cerrar el objeto escritor");
            }
        }
        return retVal;
    }
}
