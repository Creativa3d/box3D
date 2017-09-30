/*
 * JOpcionesGlobal.java
 *
 * Created on 30 de agosto de 2007, 13:51
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package generadorClases.opciones;

import generadorClases.JUtiles;

/**
 *
 * @author chema
 */
public class JOpcionesGlobal {
    
    private boolean mbIncluirTabForm = false;
    private boolean mbBorrarEnCascada = false;
    private boolean mbPKInvisibles = false;
    private boolean mbEdicionNavegador=true;
    private boolean mbEdicionFX=false;
    private boolean mbMayusculas = true;
    private boolean mbUsarJTEE=true;


    private int mlCamposMinimosTodosModulos = 3;
    
    /** Creates a new instance of JOpcionesGlobal */
    public JOpcionesGlobal() {
        mbIncluirTabForm = false;
    }
    
    public boolean getIncluirTabForm() {
        return mbIncluirTabForm;
    }

    public void setIncluirTabForm(boolean pbIncluirTabForm) {
        mbIncluirTabForm = pbIncluirTabForm;
    }
    
    public boolean getBorrarEnCascada() {
        return mbBorrarEnCascada;
    }

    public void setBorrarEnCascada(boolean pbBorrarEnCascada) {
        mbBorrarEnCascada = pbBorrarEnCascada;
    }
    
    public boolean getPKInvisibles() {
        return mbPKInvisibles;
    }

    public void setPKInvisibles(boolean pbPKInvisibles) {
        mbPKInvisibles = pbPKInvisibles;
    }

    /**
     * @return the mlCamposMinimos
     */
    public int getCamposMinimosTodosModulos() {
        return mlCamposMinimosTodosModulos;
    }

    /**
     * @param mlCamposMinimos the mlCamposMinimos to set
     */
    public void setCamposMinimosTodosModulos(int plCamposMinimosTodosModulos) {
        mlCamposMinimosTodosModulos = plCamposMinimosTodosModulos;
    }

    /**
     * @return the mbEdicionNavegador
     */
    public boolean isEdicionNavegador() {
        return mbEdicionNavegador;
    }

    /**
     * @param mbEdicionNavegador the mbEdicionNavegador to set
     */
    public void setEdicionNavegador(boolean mbEdicionNavegador) {
        this.mbEdicionNavegador = mbEdicionNavegador;
    }

    /**
     * @return the mbEdicionFX
     */
    public boolean isEdicionFX() {
        return mbEdicionFX;
    }

    /**
     * @param mbEdicionFX the mbEdicionFX to set
     */
    public void setEdicionFX(boolean mbEdicionFX) {
        this.mbEdicionFX = mbEdicionFX;
    }

    /**
     * @return the mbMayusculas
     */
    public boolean isMayusculas() {
        return mbMayusculas;
    }

    /**
     * @param mbMayusculas the mbMayusculas to set
     */
    public void setMayusculas(boolean mbMayusculas) {
        this.mbMayusculas = mbMayusculas;
    }

    /**
     * @return the mbUsarJTEE
     */
    public boolean isUsarJTEE() {
        return mbUsarJTEE;
    }

    /**
     * @param mbUsarJTEE the mbUsarJTEE to set
     */
    public void setUsarJTEE(boolean mbUsarJTEE) {
        this.mbUsarJTEE = mbUsarJTEE;
    }
    
}
