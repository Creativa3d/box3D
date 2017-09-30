/*
 * JxmlTexto.java
 *
 * Created on 25 de enero de 2007, 9:07
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package impresionXML.impresion.xml;

import impresionXML.impresion.estructura.ITextoLibre;
import impresionXML.impresion.estructura.JParamTextoLibre;
import java.awt.Color;
import java.awt.geom.Rectangle2D;

public class JxmlTexto  extends JxmlAbstract  implements IxmlObjetos {
    private static final long serialVersionUID = 1L;
    public static final String mcsNombreXml = "texto";

    private String msNombre;
    private Rectangle2D moPosicionDestino = new Rectangle2D.Double(0,0,4,1);
    private int mlAlineacion = ITextoLibre.mclAlineacionIzquierda;
    private String msTexto="";
    private boolean mbMultilinea=false;
    private Color moColor = Color.black;
    private String msFuente;
    private double mdAngulo;

    /** Creates a new instance of JxmlTexto */
    public JxmlTexto() {
    }
    
    public String toString() {
        return getNombre();
    }

    public void imprimir(final JxmlBanda poBanda, final JxmlInforme poInforme) {
        poBanda.getTexto().insertarTexto(
                new JParamTextoLibre(
                    getNombre(),
                    getProcesarTexto(), getPosicionDestino(),
                    poInforme.getFuente(getFuente()).getFont(),
                    isMultilinea(), getAlineacion(),
                    getColor(), getAngulo(),
                    poInforme.getFuente(getFuente()).isUnderline()
                    )
                );
    }
    private String getProcesarTexto(){
        return msTexto.replaceAll("<br>", String.valueOf('\n'));
    }
    public String getNombre() {
        return msNombre;
    }
    public Object clone() throws CloneNotSupportedException {
        JxmlTexto retValue;
        
        retValue = (JxmlTexto)super.clone();
        
        retValue.setPosicionDestino((Rectangle2D) getPosicionDestino().clone());
        
        return retValue;
    }

    public void setNombre(String msNombre) {
        String lsOld = this.msNombre;
        this.msNombre = msNombre;
        firePropertyChange("nombre", lsOld, msNombre);
    }

    public Rectangle2D getPosicionDestino() {
        return moPosicionDestino;
    }

    public void setPosicionDestino(Rectangle2D poPosicionDestino) {
        Rectangle2D loOld = this.moPosicionDestino;
        this.moPosicionDestino = poPosicionDestino;
        firePropertyChange("posicionDestino", loOld, poPosicionDestino);
    }
//    public void setRect(float x, float y, float w, float h){
//        moPosicionDestino.setRect(x, y, w, h);
//    }

    public int getAlineacion() {
        return mlAlineacion;
    }

    public void setAlineacion(int mlAlineacion) {
        int lOld = this.mlAlineacion;
        this.mlAlineacion = mlAlineacion;
        firePropertyChange("alineacion", lOld, mlAlineacion);
    }

    public String getTexto() {
        return msTexto;
    }

    public void setTexto(String psTexto) {
        String lsOld = this.msTexto;
        if(psTexto==null){
            this.msTexto = "";
        }else{        
            this.msTexto = psTexto;
        }
        firePropertyChange("texto", lsOld, psTexto);
    }

    public boolean isMultilinea() {
        return mbMultilinea;
    }

    public void setMultilinea(boolean mbMultilinea) {
        boolean lbOld=this.mbMultilinea;
        this.mbMultilinea = mbMultilinea;
        firePropertyChange("multilinea", lbOld, mbMultilinea);
    }

    public Color getColor() {
        return moColor;
    }

    public void setColor(Color moColor) {
        Object loColor = this.moColor;
        this.moColor = moColor;
        firePropertyChange("color", loColor, moColor);
    }

    public String getFuente() {
        return msFuente;
    }

    public void setFuente(String msFuente) {
        Object lsOld=this.msFuente;
        this.msFuente = msFuente;
        firePropertyChange("fuente", lsOld, msFuente);
    }
    /**
    @Deprecated
    * se usa setFuente
    */ 
    public void setMsFuente(String msFuente) {
        setFuente(msFuente);
    }

    public double getAngulo() {
        return mdAngulo;
    }
    public void setAngulo(final double pdAngulo) {
        double ldOld = mdAngulo;
        mdAngulo = pdAngulo;
        firePropertyChange("angulo", ldOld, mdAngulo);
    }
    public String getNombreXML(){
        return mcsNombreXml;
    }
    public void visitar(IVisitorOperacion poOperador) throws Throwable {
        poOperador.operar(this);
    }
    
    
}
