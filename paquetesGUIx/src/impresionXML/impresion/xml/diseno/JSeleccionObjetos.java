/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package impresionXML.impresion.xml.diseno;

import impresionXML.impresion.xml.IVisitorOperacion;
import impresionXML.impresion.xml.IxmlObjetos;
import impresionXML.impresion.xml.JxmlCuadrado;
import impresionXML.impresion.xml.JxmlFuente;
import impresionXML.impresion.xml.JxmlImagen;
import impresionXML.impresion.xml.JxmlInforme;
import impresionXML.impresion.xml.JxmlLinea;
import impresionXML.impresion.xml.JxmlTexto;
import impresionXML.impresion.xml.diseno.componentes.JCompSeleccion;
import impresionXML.impresion.xml.diseno.operaciones.JOperacionDimensiones;
import impresionXML.impresion.xml.diseno.operaciones.JOperacionMover;
import java.awt.Color;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.SwingUtilities;
import utiles.IListaElementos;
import utiles.JConversiones;
import utiles.JCopiarObjetos;
import utiles.JDepuracion;
import utiles.JListaElementos;

/**
 *
 * @author eduardo
 */
public class JSeleccionObjetos implements ClipboardOwner, PropertyChangeListener {
    public IListaElementos moLista = new JListaElementos();
    private final JPanelDESIGN moPanelDesign;
    private boolean mbOperacion=false;
    
    public JSeleccionObjetos(JPanelDESIGN poPanelDesgn){
        moPanelDesign=poPanelDesgn;
    }
    
    public void add(IxmlObjetos poXml){
        add(moPanelDesign.get(poXml));
    }
    public void add(final IXMLDesign poDesign){
        if(getElem(poDesign)==null){
            moLista.add(new JSeleccionObjElem(moPanelDesign, poDesign, this));
        }
        poDesign.getVisualizacion().mostrarPropiedades();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                poDesign.getVisualizacion().requestFocus();
            }
        });

    }
    public void clear(){
        for(int i = 0 ; i < moLista.size(); i++ ){
            JSeleccionObjElem loD = (JSeleccionObjElem) moLista.get(i);
            loD.liberar();
        }
        moLista.clear();
    }
    public void visitar(IVisitorOperacion poOperador, Object poExcep) throws Throwable{
        for(int i = 0 ; i < moLista.size();i++){
            JSeleccionObjElem loD2 = (JSeleccionObjElem) moLista.get(i);
            IXMLDesign loD = loD2.moXmlDesign;
            if(poExcep!=loD && poExcep != loD.getObjetoXML()){
                if(loD.getObjetoXML() instanceof JxmlTexto){
                    poOperador.operar((JxmlTexto) loD.getObjetoXML());
                }
                if(loD.getObjetoXML() instanceof JxmlCuadrado){
                    poOperador.operar((JxmlCuadrado) loD.getObjetoXML());
                }
                if(loD.getObjetoXML() instanceof JxmlFuente){
                    poOperador.operar((JxmlFuente) loD.getObjetoXML());
                }
                if(loD.getObjetoXML() instanceof JxmlLinea){
                    poOperador.operar((JxmlLinea) loD.getObjetoXML());
                }
                if(loD.getObjetoXML() instanceof JxmlImagen){
                    poOperador.operar((JxmlImagen) loD.getObjetoXML());
                }
                if(loD.getObjetoXML() instanceof JxmlInforme){
                    poOperador.operar((JxmlInforme) loD.getObjetoXML());
                }
            }
        }
    }
    
    public void copiar(){
        JCopiarObjetos contents = new JCopiarObjetos(IListaElementos.class);
        IListaElementos loLista = new JListaElementos();
        for(int i = 0 ; i < moLista.size(); i++ ){
                JSeleccionObjElem loD = (JSeleccionObjElem) moLista.get(i);
                loLista.add(loD.moXmlDesign.getObjetoXML());
        }
        contents.setObjeto(loLista);
        java.awt.Toolkit.getDefaultToolkit().getSystemClipboard().setContents(contents, this);
        
    }
    public void pegar() throws Exception{
        Transferable clipboardContent = java.awt.Toolkit.getDefaultToolkit().getSystemClipboard().getContents(this);
        JCopiarObjetos contents = new JCopiarObjetos(IListaElementos.class);
        clear();
        if ((clipboardContent != null) && (clipboardContent.isDataFlavorSupported (contents.getTransferDataFlavors()[0]))) {
            IListaElementos loObj = (IListaElementos) clipboardContent.getTransferData (contents.getTransferDataFlavors()[0]);
            for(int i = 0 ; i < loObj.size();i++){
                IxmlObjetos loD = (IxmlObjetos) loObj.get(i);
                moPanelDesign.getInforme().getBanda(0).add(loD);
                add(loD);
            }
        }
    }
    private JSeleccionObjElem getElem(IXMLDesign poObject){
        for(int i =0; i<moLista.size();i++){
            JSeleccionObjElem loE = (JSeleccionObjElem) moLista.get(i);
            if(loE.moXmlDesign == poObject){
                return loE;
            }
        }
        return null;
    }
    public boolean isSelected(IXMLDesign poObject) {
        return getElem(poObject)!=null;
    }    

    public void lostOwnership(Clipboard clipboard, Transferable contents) {
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if(!mbOperacion){
            boolean lbOld = mbOperacion;
            mbOperacion=true;        
            try{

            if(evt.getNewValue() instanceof Rectangle2D){
                Rectangle2D loNew =  (Rectangle2D) evt.getNewValue();
                Rectangle2D loOld =  (Rectangle2D) evt.getOldValue();

                double x=loNew.getX()-loOld.getX();
                double y=loNew.getY()-loOld.getY();
                double width=loNew.getWidth()-loOld.getWidth();
                double height=loNew.getHeight()-loOld.getHeight();

                if(JConversiones.mlComparaDoubles(x, 0, 0.01)!=0
                        || JConversiones.mlComparaDoubles(y, 0, 0.01)!=0){
                    try {
                        JOperacionMover loMover = new JOperacionMover(moPanelDesign.getInforme(), x, y, 0, 0);
                        visitar(loMover, evt.getSource());
                    } catch (Throwable ex) {
                        JDepuracion.anadirTexto(getClass().getName(), ex);
                    }
                }
                if(JConversiones.mlComparaDoubles(width, 0, 0.01)!=0
                        || JConversiones.mlComparaDoubles(height, 0, 0.01)!=0){
                    try {
                        JOperacionDimensiones loDim = new JOperacionDimensiones(moPanelDesign.getInforme(), width, height, false);
                        visitar(loDim, evt.getSource());
                    } catch (Throwable ex) {
                        JDepuracion.anadirTexto(getClass().getName(), ex);
                    }
                }

            }
            }finally{
            mbOperacion=lbOld;
            }            
        }            
        
    }


}
class JSeleccionObjElem {
    IXMLDesign moXmlDesign;
    JCompSeleccion moSelect;
    JSeleccionObjetos moSelectObj;
    
    JSeleccionObjElem(JPanelDESIGN poPanelDe,IXMLDesign poXmlDesign, JSeleccionObjetos poSelectObj){
        moXmlDesign = poXmlDesign;
        moSelectObj=poSelectObj;
        moSelect = new JCompSeleccion(poPanelDe, poXmlDesign);
        poXmlDesign.getVisualizacion().setBackground(Color.red);
        poXmlDesign.getVisualizacion().getParent().setComponentZOrder(poXmlDesign.getVisualizacion(), 0);
    
        poXmlDesign.getObjetoXML().addPropertyChangeListener(moSelectObj);
        
    }

    void liberar() {
        moSelect.liberar();
        moXmlDesign.getVisualizacion().setBackground(Color.white);
        moXmlDesign.getObjetoXML().removePropertyChangeListener(moSelectObj);
    }
}
