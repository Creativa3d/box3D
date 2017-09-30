/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package impresionXML.impresion.xml.diseno.componentes;

import impresionXML.impresion.xml.IxmlObjetos;
import impresionXML.impresion.xml.JxmlCuadrado;
import impresionXML.impresion.xml.JxmlImagen;
import impresionXML.impresion.xml.JxmlInforme;
import impresionXML.impresion.xml.diseno.IXMLDesign;
import utilesGUIx.formsGenericos.edicion.IFormEdicion;

/**
 *
 * @author eduardo
 */
public class JxmlImagenDesign  extends JxmlBase  {
    private JxmlImagen moObjeto;
    private JxmlInforme moInforme;
    private JPanelImagenVIEW moPanel=new JPanelImagenVIEW();

    public JxmlImagenDesign() {
        moEdicion = new JPanelImagenPROPER();
    }
    public void setDatos(IxmlObjetos poObj, JxmlInforme poInforme)throws Exception{
        moObjeto = (JxmlImagen) poObj;
        moInforme = poInforme;
        moPanel.setDatos(moObjeto, poInforme);
        ((JPanelImagenPROPER)moEdicion).setDatos(moObjeto, poInforme);
    }
    
    public JPanelBASE getVisualizacion() {
        return moPanel;
    }
    public IFormEdicion getPropiedades() throws Exception {
        ((JPanelImagenPROPER)moEdicion).setDatos(moObjeto, moInforme);
        return super.getPropiedades(); 
    }
        
      protected Object clone() throws CloneNotSupportedException {
         JxmlImagenDesign loT = (JxmlImagenDesign) super.clone();
         loT.moPanel=new JPanelImagenVIEW();
         loT.moObjeto=null;
        return loT; 
    }

    public IXMLDesign Clone() throws CloneNotSupportedException{
            return (IXMLDesign) clone();
    }   
    public IxmlObjetos getObjetoXML() {
        return moObjeto;
    }
}
