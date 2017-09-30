/*
 * JxmlImagen.java
 *
 * Created on 25 de enero de 2007, 9:26
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package impresionXML.impresion.xml;

import impresionXML.impresion.motorImpresion.JPagina;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeSupport;
import java.io.InputStream;
import javax.swing.ImageIcon;
import utiles.JDepuracion;
import utilesGUIx.imgTrata.JIMGTrata;

public class JxmlImagen extends JxmlAbstract implements IxmlObjetos {
    private static final long serialVersionUID = 1L;
    public static final String mcsNombreXml = "imagen";
    public static final int mclRedimensionProporcional = 0;
    public static final int mclRedimensionNoProporcional = 1;
    public static final int mclRedimensionTamanoRealOProporcinalSiNoCabe = 2;

    private String msNombre;
    private Image moImagen;
    private String msCamino;
    private Rectangle2D moPosicionDestino = new Rectangle2D.Double(0,0,2,2);
    private int mlRedimensionTipo = mclRedimensionProporcional;


    /** Creates a new instance of JxmlImagen */
    public JxmlImagen() {
        super();
    }
    
    public String toString() {
        return getNombre();
    }
    private Image cargarImagen(Image loImagen){
        try{
            if(!JIMGTrata.getIMGTrata().imagenEsperar(loImagen)){
                loImagen=null;
            }
        }catch(Error e){
            JDepuracion.anadirTexto(JDepuracion.mclWARNING, getClass().getName(), e.toString());
        }catch(Throwable e){
            JDepuracion.anadirTexto(JDepuracion.mclWARNING, getClass().getName(), e.toString());
        }
        return loImagen;
    }
    public void setImagen(final Image poImage){
        moImagen = poImage;
        
    }
    public Image getImagen(){
        if(moImagen==null){
            try{
                Image loImage = null;
                if(!(msCamino==null || msCamino.equalsIgnoreCase(""))){
                    InputStream loIn = JxmlLectorInforme.class.getResourceAsStream(msCamino);
                    try{
                        if(loIn!=null){
                            loImage = Toolkit.getDefaultToolkit().getImage(JxmlLectorInforme.class.getResource(msCamino));
                            loImage = cargarImagen(loImage);
                        }
                    }finally{
                        if(loIn!=null){
                            loIn.close();
                        }
                    }
                    if(loImage==null){
//se quita por q si no no tiene en cuenta las URL                        
//                        File loFile = new File(msCamino);
//                        if(loFile.exists()){
                            ImageIcon loIcon = JIMGTrata.getIMGTrata().getImagenCargada(msCamino);
                            if(loIcon!=null){
                                loImage = loIcon.getImage();
                            }
//                        }
                    }
                }
                if(loImage==null){
                    JDepuracion.anadirTexto(JDepuracion.mclWARNING, getClass().getName(), "Imagen no encontrada de nombre " + msNombre + " en el camino "+ msCamino );
                }else{
                    moImagen = loImage;
                }
            }catch(Exception e){
                moImagen=null;
                JDepuracion.anadirTexto(JDepuracion.mclWARNING, getClass().getName(), e.toString() + "("+msCamino+")");
            }
                
        }
        return moImagen;
    }
    public void imprimir(final JxmlBanda poBanda, final JxmlInforme poInforme) {
        Image loImage = getImagen();
        if(loImage!=null){
            Rectangle2D loRectImpresion = (Rectangle2D) getPosicionDestino().clone();
            switch(mlRedimensionTipo){
                case mclRedimensionTamanoRealOProporcinalSiNoCabe:
                    addImagenTamanoRealOProporcinalSiNoCabe(poBanda, loImage, loRectImpresion);
                    break;
                case mclRedimensionNoProporcional:
                    poBanda.getImagen().insertarImagen(loImage, loRectImpresion);
                    break;
                default:
                    addImagenProporcional(poBanda, loImage, loRectImpresion);
            }
           
        }
    }
    private void addImagenTamanoRealOProporcinalSiNoCabe(final JxmlBanda poBanda, Image loImage, Rectangle2D loRectImpresion){

        if(JPagina.mdConvertirACM(loImage.getWidth(null))<loRectImpresion.getWidth() &&
           JPagina.mdConvertirACM(loImage.getHeight(null))<loRectImpresion.getHeight()){

            loRectImpresion.setRect(
                    getPosicionDestino().getX(),  getPosicionDestino().getY(),
                    JPagina.mdConvertirACM(loImage.getWidth(null)), JPagina.mdConvertirACM(loImage.getHeight(null)));
            poBanda.getImagen().insertarImagen(loImage, loRectImpresion);
        }else{
            addImagenProporcional(poBanda, loImage, loRectImpresion);
        }

    }
    private void addImagenProporcional(final JxmlBanda poBanda, Image loImage, Rectangle2D loRectImpresion){
        //se pone en la posicion destino pero conservando la proporcion
        double ldHeight = getPosicionDestino().getWidth() * loImage.getHeight(null) / loImage.getWidth(null);
        if(ldHeight > getPosicionDestino().getHeight()){
            double ldWidth = getPosicionDestino().getHeight() * loImage.getWidth(null) / loImage.getHeight(null);
            loRectImpresion.setRect(
                    getPosicionDestino().getX(),  getPosicionDestino().getY(),
                    ldWidth, getPosicionDestino().getHeight()
                    );
        }else{
            loRectImpresion.setRect(
                    getPosicionDestino().getX(),  getPosicionDestino().getY(),
                    getPosicionDestino().getWidth(), ldHeight
                    );
        }
        poBanda.getImagen().insertarImagen(loImage, loRectImpresion);

    }

    public void setRedimensionTipo(int plTipo){
        int lOld = this.mlRedimensionTipo;
        this.mlRedimensionTipo = plTipo;
        firePropertyChange("redimensionTipo", lOld, mlRedimensionTipo);
        
    }
    public int getRedimensionTipo(){
        return mlRedimensionTipo;
    }
    public String getCamino() {
        return msCamino;
    }

    public void setCamino(final String psCamino) {
        String lsOld = this.msCamino;
        this.msCamino = psCamino;
        firePropertyChange("camino", lsOld, this.msCamino);        
        moImagen=null;
    }
    public String getNombre() {
        return msNombre;
    }
    public Object clone() throws CloneNotSupportedException {
        JxmlImagen retValue;
        
        retValue = (JxmlImagen)super.clone();
        
        retValue.setPosicionDestino((Rectangle2D) getPosicionDestino().clone());
        
        return retValue;
    }

    public void setNombre(final String msNombre) {
        this.msNombre = msNombre;
    }

    public Rectangle2D getPosicionDestino() {
        return moPosicionDestino;
    }
    public void setPosicionDestino(Rectangle2D poPosicionDestino) {
        Rectangle2D loOld = this.moPosicionDestino;
        this.moPosicionDestino = poPosicionDestino;
        firePropertyChange("posicionDestino", loOld, poPosicionDestino);
    }
    public String getNombreXML(){
        return mcsNombreXml;
    }
    public void visitar(IVisitorOperacion poOperador) throws Throwable {
        poOperador.operar(this);
    }
}
