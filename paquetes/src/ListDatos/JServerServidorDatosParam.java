/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ListDatos;

public class JServerServidorDatosParam implements java.io.Serializable{
    private boolean mbSoloLectura;
    private boolean mbEliminarEspaciosDerechaSiempre=false;
    private Object moTAG;
    private int mlNumeroMaximoRegistros=0;

    /**
     * @return the mlNumeroMaximoRegistros
     */
    public int getNumeroMaximoRegistros() {
        return mlNumeroMaximoRegistros;
    }

    /**
     * @param mlNumeroMaximoRegistros the mlNumeroMaximoRegistros to set
     */
    public void setNumeroMaximoRegistros(int mlNumeroMaximoRegistros) {
        this.mlNumeroMaximoRegistros = mlNumeroMaximoRegistros;
    }
    /**
     * @return the mbSoloLectura
     */
    public boolean isSoloLectura() {
        return mbSoloLectura;
    }

    /**
     * @param mbSoloLectura the mbSoloLectura to set
     */
    public void setSoloLectura(boolean mbSoloLectura) {
        this.mbSoloLectura = mbSoloLectura;
    }

    /**
     * @return the mbEliminarEspaciosDerechaSiempre
     */
    public boolean isEliminarEspaciosDerechaSiempre() {
        return mbEliminarEspaciosDerechaSiempre;
    }

    /**
     * @param mbEliminarEspaciosDerechaSiempre the mbEliminarEspaciosDerechaSiempre to set
     */
    public void setEliminarEspaciosDerechaSiempre(boolean mbEliminarEspaciosDerechaSiempre) {
        this.mbEliminarEspaciosDerechaSiempre = mbEliminarEspaciosDerechaSiempre;
    }

    /**
     * @return the moTAG
     */
    public Object getTAG() {
        return moTAG;
    }

    /**
     * @param moTAG the moTAG to set
     */
    public void setTAG(Object moTAG) {
        this.moTAG = moTAG;
    }
}
