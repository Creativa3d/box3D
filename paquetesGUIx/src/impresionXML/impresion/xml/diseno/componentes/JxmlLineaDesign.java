/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package impresionXML.impresion.xml.diseno.componentes;

import impresionXML.impresion.xml.IxmlObjetos;
import impresionXML.impresion.xml.JxmlFuente;
import impresionXML.impresion.xml.JxmlInforme;
import impresionXML.impresion.xml.JxmlLinea;
import impresionXML.impresion.xml.diseno.IXMLDesign;
import javax.swing.JPanel;
import utilesGUIx.formsGenericos.edicion.IFormEdicion;

/**
 *
 * @author eduardo
 */
public class JxmlLineaDesign  extends JxmlBase {
    private JxmlLinea moObjeto;
    private JxmlInforme moInforme;
    private JPanelLineaVIEW moPanel=new JPanelLineaVIEW();

    public JxmlLineaDesign() {
        moEdicion = new JPanelLineaPROPER();
    }
    public void setDatos(IxmlObjetos poObj, JxmlInforme poInforme)throws Exception{
        moObjeto = (JxmlLinea) poObj;
        moInforme = poInforme;
        moPanel.setDatos(moObjeto, poInforme);
        ((JPanelLineaPROPER)moEdicion).setDatos(moObjeto, poInforme);
    }


    public JPanelBASE getVisualizacion() {
        return moPanel;
    }
    public IFormEdicion getPropiedades() throws Exception {
        ((JPanelLineaPROPER)moEdicion).setDatos(moObjeto, moInforme);
        return super.getPropiedades(); 
    }
        
      protected Object clone() throws CloneNotSupportedException {
         JxmlLineaDesign loT = (JxmlLineaDesign) super.clone();
         loT.moPanel=new JPanelLineaVIEW();
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
