/*
 * JServerDatosSocket.java
 *
 * Created on 30 de enero de 2004, 18:28
 */

package ListDatos;

import ListDatos.estructuraBD.*;

import java.io.*;
import java.net.*;
import utiles.JDepuracion;
/** 
 *   Servidor de datos que transmite la informacion a traves de un socket
 */
public class JServerDatosSocket extends JServidorDatosAbtrac {
    private static final long serialVersionUID = 33333321L;
    private String msIP;
    private int mlPuerto;
    private ISelectMotor moSelectMotor = JSelectMotorFactory.getInstance().getSelectMotorDefecto();

    /**
     * Constructor
     * @param psIP ip del servidor
     * @param plPuerto puerto del servidor
     * @param poSelectMotor select motor
     */
    public JServerDatosSocket(final String psIP, final int plPuerto, final ISelectMotor poSelectMotor) {
        super();
        msIP = psIP;
        moSelectMotor = poSelectMotor;
        mlPuerto = plPuerto;
    }
    
    public IResultado crearTabla(final String psTabla, final JFieldDefs poCampos) {
        return null;
    }
    
    protected void recuperarInformacion(final JListDatos v, final JSelect poSelect, final String psTabla) throws Exception{
        Socket echoSocket = null;
        DataOutputStream os = null;
        DataInputStream is = null;
     
        try{
            try {
                echoSocket = new Socket(getIP(), getPuerto());
                os = new DataOutputStream(echoSocket.getOutputStream());
                is = new DataInputStream(echoSocket.getInputStream());
            } catch (UnknownHostException e) {
                JDepuracion.anadirTexto(JDepuracion.mclCRITICO,this.getClass().getName(),"No existe el host: " + getIP());
            } catch (IOException e) {
                JDepuracion.anadirTexto(JDepuracion.mclCRITICO,this.getClass().getName(),"Si existe el host pero No puedo conseguir la I/O para la conexion : " + getIP() + " y puerto " + String.valueOf(getPuerto()));
            }

            if (echoSocket != null && os != null && is != null) {
                try {

                    os.writeUTF("@INICIO@#"+poSelect.msSQL(getSelectMotor()) +"#@FIN@#");

                    String inputLine = is.readLine();
                    if(inputLine!=null){
                        if(inputLine.compareTo("")!=0) {
                            throw new Exception(inputLine);
                        }
                    }
                    while ( (inputLine!=null)) {
                        inputLine = is.readLine();
                        if(inputLine!=null) {
                            if(inputLine.compareTo("@FIN@")==0){
                                break;
                            }
                            IFilaDatos loFila = JFilaCrearDefecto.getFilaCrear().getFilaDatos(psTabla);
                            loFila.setArray(JFilaDatosDefecto.moArrayDatos(inputLine, JFilaDatosDefecto.mccSeparacion1));
                            v.add(loFila);
                        }
                        try{
                            wait(100);
                        }catch(Exception e){
                            //
                        }
                    }

                    os.close();
                    is.close();
                    echoSocket.close();
                } catch (IOException e) {
                    JDepuracion.anadirTexto(JDepuracion.mclCRITICO,this.getClass().getName(),"I/O falla sobre la conexion de: " + getIP());
                }
            }
        }finally{
            if(os!=null){
                os.close();
            }
            if(is!=null){
                is.close();
            }
            if(echoSocket!=null){
                echoSocket.close();
            }
        }
        
    }

    public IResultado ejecutarServer(final IServerEjecutar poEjecutar) {
        return null;
    }
    public IResultado modificarEstructura(final ISelectEjecutarSelect  poEstruc) {
        return null;
    }
    public JTableDefs getTableDefs() throws ExceptionNoImplementado {
        throw new ExceptionNoImplementado("No se ha asignado el constructor de definicion de campos");
    }

    /**
     * @return the msIP
     */
    public String getIP() {
        return msIP;
    }

    /**
     * @param msIP the msIP to set
     */
    public void setIP(String msIP) {
        this.msIP = msIP;
    }

    /**
     * @return the mlPuerto
     */
    public int getPuerto() {
        return mlPuerto;
    }

    /**
     * @param mlPuerto the mlPuerto to set
     */
    public void setPuerto(int mlPuerto) {
        this.mlPuerto = mlPuerto;
    }

    /**
     * @return the moSelectMotor
     */
    public ISelectMotor getSelectMotor() {
        return moSelectMotor;
    }

    /**
     * @param moSelectMotor the moSelectMotor to set
     */
    public void setSelectMotor(ISelectMotor moSelectMotor) {
        this.moSelectMotor = moSelectMotor;
    }

    public IResultado actualizar(String psSelect, JActualizar poActualizar) {
        throw new RuntimeException("Not supported yet.");
    }
}
