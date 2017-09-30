/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ListDatos;

import java.io.Serializable;

/**
 *
 * @author eduardo
 */
public class JServerServidorDatosConexion implements Cloneable, Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * Variable de conexion(Leida de fichero directo): Password
     */
    private String msPASSWORD="";
    /**
     * Variable de conexion(Leida de fichero directo):
     */
    private String msUSUARIO="";
    /**
     * Variable de conexion(Leida de fichero directo):
     */
    private String msClase="";
    /**
     * Variable de conexion(Leida de fichero directo):
     */
    private String msURL="";
    /**
     * Variable de conexion(Leida de fichero directo):
     */
    private int mlTipoBD=JSelectMotor.mclSqlServer;
    /**
     * Variable de tipo de conexion (Leida de fichero directo):
     */
    private int mlTipoConexion=JServerServidorDatos.mclTipoBD;    

    /**
     * @return the msPASSWORD
     */
    public String getPASSWORD() {
        return msPASSWORD;
    }

    /**
     * @param msPASSWORD the msPASSWORD to set
     */
    public void setPASSWORD(String msPASSWORD) {
        this.msPASSWORD = msPASSWORD;
    }

    /**
     * @return the msUSUARIO
     */
    public String getUSUARIO() {
        return msUSUARIO;
    }

    /**
     * @param msUSUARIO the msUSUARIO to set
     */
    public void setUSUARIO(String msUSUARIO) {
        this.msUSUARIO = msUSUARIO;
    }

    /**
     * @return the msClase
     */
    public String getClase() {
        return msClase;
    }

    /**
     * @param msClase the msClase to set
     */
    public void setClase(String msClase) {
        this.msClase = msClase;
    }

    /**
     * @return the msURL
     */
    public String getURL() {
        return msURL;
    }

    /**
     * @param msURL the msURL to set
     */
    public void setURL(String msURL) {
        this.msURL = msURL;
    }

    /**
     * @return the mlTipoBD
     */
    public int getTipoBD() {
        return mlTipoBD;
    }

    /**
     * @param mlTipoBD the mlTipoBD to set
     */
    public void setTipoBD(int mlTipoBD) {
        this.mlTipoBD = mlTipoBD;
    }

    /**
     * @return the mlTipoConexion
     */
    public int getTipoConexion() {
        return mlTipoConexion;
    }

    /**
     * @param mlTipoConexion the mlTipoConexion to set
     */
    public void setTipoConexion(int mlTipoConexion) {
        this.mlTipoConexion = mlTipoConexion;
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }
    
}
