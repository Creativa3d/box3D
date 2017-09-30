/*
 * JTablaConfigTabla.java
 *
 * Created on 12 de julio de 2007, 9:05
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utilesGUIx.formsGenericos;

public class JTablaConfigColumna implements Cloneable {
    private String msNombre="";
    private String msCaption="";
    private int mlOrden;
    private int mlLong;
    
    /** Creates a new instance of JTablaConfigTabla */
    public JTablaConfigColumna() {
    }
    public JTablaConfigColumna(final String psNombre, final int plOrden, final int plLong, final String psCaption) {
        msNombre = psNombre;
        mlOrden = plOrden;
        mlLong = plLong;
        setCaption(psCaption);
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
    
}
