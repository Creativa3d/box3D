/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package impresionJasper;

import ar.com.fdvs.dj.domain.constants.HorizontalAlign;

public class JInfConfigColumnaJasper implements Cloneable {
    private final int mlColumna;
    private byte mlAlineacion=-1;
    private String msFormato=null;
    private String msNombre="";
    private String msCaption="";
    private int mlOrden;
    private int mlLong;

    public JInfConfigColumnaJasper(int plColumna){
        mlColumna = plColumna;
        mlOrden = plColumna;
    }
    public JInfConfigColumnaJasper(int plColumna, byte plAlineacion){
        this(plColumna);
        mlAlineacion=plAlineacion;
    }
    public JInfConfigColumnaJasper(int plColumna, final String psNombre, final int plOrden, final int plLong, final String psCaption) {
        this(plColumna);
        msNombre = psNombre;
        mlOrden = plOrden;
        mlLong = plLong;
        msCaption= psCaption;
    }

    HorizontalAlign getAlineacionHorizontal(){
        HorizontalAlign loResult = null;
        switch(getAlineacion()){
            case JInfGeneralJasper.mclAlineacionCentro:
                loResult = HorizontalAlign.CENTER;
                break;
            case JInfGeneralJasper.mclAlineacionDerecha:
                loResult = HorizontalAlign.RIGHT;
                break;
            case JInfGeneralJasper.mclAlineacionIzquierda:
                loResult = HorizontalAlign.LEFT;
                break;
            case JInfGeneralJasper.mclAlineacionJustificada:
                loResult = HorizontalAlign.JUSTIFY;
                break;

        }
        return loResult;
    }

    /**
     * @return the mlColumna
     */
    public int getColumna() {
        return mlColumna;
    }

    /**
     * @return the mlAlineacion
     */
    public byte getAlineacion() {
        return mlAlineacion;
    }

    /**
     * @param mlAlineacion the mlAlineacion to set
     */
    public void setAlineacion(byte mlAlineacion) {
        this.mlAlineacion = mlAlineacion;
    }

    /**
     * @return the msFormato
     */
    public String getFormato() {
        return msFormato;
    }

    /**
     * @param msFormato the msFormato to set
     */
    public void setFormato(String msFormato) {
        this.msFormato = msFormato;
    }

    public String getNombre() {
        return msNombre;
    }

    public void setNombre(final String msNombre) {
        this.msNombre = msNombre;
    }

    public int getOrden() {
        return mlOrden;
    }

    public void setOrden(final int mlOrden) {
        this.mlOrden = mlOrden;
    }

    public int getLong() {
        return mlLong;
    }

    public void setLong(final int mlLong) {
        this.mlLong = mlLong;
    }

    public String getCaption() {
        return msCaption;
    }

    public void setCaption(final String msCaption) {
        this.msCaption = msCaption;
    }

    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }

    public String toString() {
        return String.valueOf(mlColumna) + " ;Orden: " + String.valueOf(mlOrden) + " ;Long: " + String.valueOf(mlLong) + " " + msCaption;
    }
    
}
    
