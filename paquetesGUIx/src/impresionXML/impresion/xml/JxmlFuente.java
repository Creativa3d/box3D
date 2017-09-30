/*
 * JxmlFuente.java
 *
 * Created on 25 de enero de 2007, 9:00
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package impresionXML.impresion.xml;

import java.awt.Font;
import java.io.Serializable;

public class JxmlFuente  extends JxmlAbstract implements IxmlObjetos {
    public static final String mcsNombreXml = "fuente";
    private static final long serialVersionUID = 1L;

    private String msNombre;
    private String msFuente="SansSerif";
    private float mdSize=10;
    private boolean mbUnderline=false;
    private boolean mbBold=false;
    private boolean mbCursiva=false;
    private boolean mbTachado=false;
    
    private Font moFuente = null;
    
    /**
     * Creates a new instance of JxmlFuente
     */
    public JxmlFuente() {
        super();
    }
    
    public Font getFont(double pdZoom){
        Font loFuente;
            int lEstilo=Font.PLAIN;
            if(isBold()){
                lEstilo=Font.BOLD;
            }
            if(isCursiva()){
                if(lEstilo==Font.BOLD){
                    lEstilo|=Font.ITALIC;
                }else{
                    lEstilo=Font.ITALIC;
                }
            }
//            if(isUnderline()){
//                if(isCursiva() || isBold() ){
//                    lEstilo|=Font.ROMAN_BASELINE;->alineacion
//                }else{
//                    lEstilo=Font.ROMAN_BASELINE;
//                }
//            }
            loFuente = new Font(getFuente(), lEstilo, (int)(getSize()*pdZoom) );

            //no sirve, font no almacena el subrayado
//            HashMap loMap = new HashMap();
//            loMap.put(TextAttribute.FAMILY, getFuente());
//            loMap.put(TextAttribute.SIZE, new Float(getSize()));
//            if(isUnderline()){
//                loMap.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
//            }
//            if(isBold()){
//                loMap.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD);
//            }else{
//                loMap.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_REGULAR);
//            }
//            if(isCursiva()){
//                loMap.put(TextAttribute.POSTURE, TextAttribute.POSTURE_OBLIQUE);
//            }else{
//                loMap.put(TextAttribute.POSTURE, TextAttribute.POSTURE_REGULAR);
//            }
//            loMap.put(TextAttribute.FAMILY, getFuente());
//            moFuente = new Font(loMap);
        
        return loFuente;
    }
    public Font getFont(){
        if(moFuente == null){
            moFuente=getFont(1);
        }
        return moFuente;
    }
    
    public String toString() {
        return getNombre();
    }

    public String getFuente() {
        return msFuente;
    }

    public void setFuente(final String msFuente) {
        moFuente = null;
        Object lsOld=this.msFuente ;
        this.msFuente = msFuente;
        firePropertyChange("fuente", lsOld, msFuente);
    }

    public float getSize() {
        return mdSize;
    }

    public void setSize(final float mdSize) {
        moFuente = null;
        float ldOld=this.mdSize;
        this.mdSize = mdSize;
        firePropertyChange("size", ldOld, mdSize);
    }

    public boolean isUnderline() {
        return mbUnderline;
    }

    public void setUnderline(final boolean mbUnderline) {
        moFuente = null;
        boolean lbOld=this.mbUnderline;
        this.mbUnderline = mbUnderline;
        firePropertyChange("underline", lbOld, mbUnderline);
    }

    public boolean isBold() {
        return mbBold;
    }

    public void setBold(final boolean mbBold) {
        moFuente = null;
        boolean lbOld=this.mbBold ;
        this.mbBold = mbBold;
        firePropertyChange("bold", lbOld, mbBold);
    }

    public boolean isCursiva() {
        return mbCursiva;
    }

    public void setCursiva(final boolean mbCursiva) {
        moFuente = null;
        boolean lbOld=this.mbCursiva ;
        this.mbCursiva = mbCursiva;
        firePropertyChange("cursiva", lbOld, mbCursiva);
    }

    public boolean isTachado() {
        return mbTachado;
    }

    public void setTachado(final boolean mbTachado) {
        moFuente = null;
        boolean lbOld=this.mbTachado;
        this.mbTachado = mbTachado;
        firePropertyChange("tachado", lbOld, mbTachado);
    }
    public String getNombre() {
        return msNombre;
    }
    public Object clone() throws CloneNotSupportedException {
        JxmlFuente retValue;
        
        retValue = (JxmlFuente)super.clone();
        
        return retValue;
    }

    public void setNombre(final String msNombre) {
        String lsOld = this.msNombre;
        this.msNombre = msNombre;
        firePropertyChange("nombre", lsOld, msNombre);
        
    }

    public void imprimir(JxmlBanda poBanda, JxmlInforme poInforme) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getNombreXML() {
        return mcsNombreXml;
    }

    public void visitar(IVisitorOperacion poOperador) throws Throwable {
        poOperador.operar(this);
    }
}
