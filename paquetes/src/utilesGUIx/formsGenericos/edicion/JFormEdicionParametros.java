/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesGUIx.formsGenericos.edicion;

import java.util.HashMap;
import utiles.IListaElementos;
import utiles.JListaElementos;
import utilesGUIx.aplicacion.avisos.JAvisosConj;
import utilesGUIx.formsGenericos.IMostrarPantalla;
import utilesGUIx.formsGenericos.JMostrarPantallaParam;

public class JFormEdicionParametros {
    private Object moIcono;
    private boolean mbSoloLectura = false;
    private boolean mbPlugInPasados = false;
    private Object moComponenteDefecto=null;
    private IMostrarPantalla moMostrarPantalla;
    private JAvisosConj moAvisos = new JAvisosConj();
    private IListaElementos moBotones = new JListaElementos();
    private JMostrarPantallaParam moMostrarParam;
    
    
    private HashMap<String, Object> moAtributos = new HashMap<String, Object>();

    
    public Object getIcono() {
        return moIcono;
    }

    public void setIcono(Object moIcono) {
        this.moIcono = moIcono;
    }

    /**
     * @return si el form es de solo lectura
     */
    public boolean isSoloLectura() {
        return mbSoloLectura;
    }

    /**
     * @param pbSoloLectura Establece q el form es de solo lectura
     */
    public void setSoloLectura(boolean pbSoloLectura) {
        mbSoloLectura = pbSoloLectura;
    }
    /**
     * @return the mbPlugInPasados
     */
    public boolean isPlugInPasados() {
        return mbPlugInPasados;
    }

    /**
     * @param mbPlugInPasados the mbPlugInPasados to set
     */
    public void setPlugInPasados(boolean mbPlugInPasados) {
        this.mbPlugInPasados = mbPlugInPasados;
    }

    /**
     * @return the moComponenteDefecto
     */
    public Object getComponenteDefecto() {
        return moComponenteDefecto;
    }

    /**
     * @param moComponenteDefecto the moComponenteDefecto to set
     */
    public void setComponenteDefecto(Object moComponenteDefecto) {
        this.moComponenteDefecto = moComponenteDefecto;
    }

    /**
     * @return the moMostrarPantalla
     */
    public IMostrarPantalla getMostrarPantalla() {
        return moMostrarPantalla;
    }

    /**
     * @param moMostrarPantalla the moMostrarPantalla to set
     */
    public void setMostrarPantalla(IMostrarPantalla moMostrarPantalla) {
        this.moMostrarPantalla = moMostrarPantalla;
    }

    /**
     * @return the moAvisos
     */
    public JAvisosConj getAvisos() {
        return moAvisos;
    }

    /**
     * @param moAvisos the moAvisos to set
     */
    public void setAvisos(JAvisosConj moAvisos) {
        this.moAvisos = moAvisos;
    }

    /**
     * @return Lista de botones q añade el formulaio, como aceptar /cancelar
     */
    public IListaElementos getBotones() {
        return moBotones;
    }

    /**
     * @return the moMostrarParam
     */
    public JMostrarPantallaParam getMostrarParam() {
        return moMostrarParam;
    }

    /**
     * @param moMostrarParam the moMostrarParam to set
     */
    public void setMostrarParam(JMostrarPantallaParam moMostrarParam) {
        this.moMostrarParam = moMostrarParam;
    }

    /**
     * @return the moAtributos
     */
    public HashMap<String, Object> getAtributos() {
        return moAtributos;
    }

    /**
     * @param moAtributos the moAtributos to set
     */
    public void setAtributos(HashMap<String, Object> moAtributos) {
        this.moAtributos = moAtributos;
    }
    
    
}
