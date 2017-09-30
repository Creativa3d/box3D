/*
 * JSelectCampo.java
 *
 * Created on 6 de octubre de 2003, 14:29
 */

package ListDatos;

import java.io.Serializable;

/**
 * Campo de una select
 */
public final class JSelectCampo implements Serializable, Cloneable {
    private static final long serialVersionUID = 33333316L;
    
    /** Constante no hay funcion de campo */
    public static final int mclFuncionNada = -1;
    /** Constante funcion de campo count*/
    public static final int mclFuncionCount = 0;
    /** Constante funcion de campo avg*/
    public static final int mclFuncionAvg = 1;
    /** Constante funcion de campo max*/
    public static final int mclFuncionMax = 2;
    /** Constante funcion de campo min*/
    public static final int mclFuncionMin = 3;
    /** Constante funcion de campo sum*/
    public static final int mclFuncionSum = 4;
    /** Constante funcion de campo tal cual*/
    public static final int mclFuncionTalCual = 5;
    /** Constante orden descendente*/
    public static final int mclOrdenDesc = 6;


    /**Alias*/
    private String msNombre = null;
    private String msTabla = null;
    private int mlFuncion = mclFuncionNada;
    private String msCaption = null;
    
    /**
     * Constructor
     * @param psNombre nombre del campo
     */
    public JSelectCampo(final String psNombre) {
        super();
        msNombre = psNombre;
        msTabla = null;
        mlFuncion = mclFuncionNada;
        
    }
    /**
     * Constructor
     * @param psTabla tabla del campo
     * @param psNombre nombre del campo
     */
    public JSelectCampo(final String psTabla, final String psNombre) {
        super();
        msNombre = psNombre;
        msTabla = psTabla;
        mlFuncion = mclFuncionNada;
    }
    /**
     * Constructor
     * @param plFuncion funcion del campo
     * @param psTabla tabla del campo
     * @param psNombre nombre del campo
     */
    public JSelectCampo(final int plFuncion, final String psTabla, final String psNombre) {
        super();
        msNombre = psNombre;
        msTabla = psTabla;
        mlFuncion = plFuncion;
    }
    /**
     * Devuelve el campo en formato SQL en funcion del poSelect
     * @param poSelect motor de sql segun tipo de base de datos
     * @return sql del campo
     */
    public String msSQL(final ISelectMotor poSelect){
      return poSelect.msCampo(getFuncion(), getTabla(), getNombre());
    }
    /**
     * devulve el campo en texto
     * @return cadena
     */
    public String toString(){
        return msSQL(JSelectMotorFactory.getInstance().getSelectMotorDefecto());
    }
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
   }

    /**
     * @return the msNombre
     */
    public String getNombre() {
        return msNombre;
    }

    /**
     * @param msNombre the msNombre to set
     */
    public void setNombre(String msNombre) {
        this.msNombre = msNombre;
    }

    /**
     * @return the msTabla
     */
    public String getTabla() {
        return msTabla;
    }

    /**
     * @param msTabla the msTabla to set
     */
    public void setTabla(String msTabla) {
        this.msTabla = msTabla;
    }

    /**
     * @return the mlFuncion
     */
    public int getFuncion() {
        return mlFuncion;
    }

    /**
     * @param mlFuncion the mlFuncion to set
     */
    public void setFuncion(int mlFuncion) {
        this.mlFuncion = mlFuncion;
    }

    /**
     * @return the msCaption
     */
    public String getCaption() {
        return msCaption;
    }

    /**
     * @param msCaption the msCaption to set
     */
    public void setCaption(String msCaption) {
        this.msCaption = msCaption;
    }
 }
