/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package impresionXML.impresion.xml.diseno.componentes;

import impresionXML.impresion.xml.IxmlObjetos;
import impresionXML.impresion.xml.JxmlCuadrado;
import impresionXML.impresion.xml.JxmlFuente;
import impresionXML.impresion.xml.JxmlInforme;
import impresionXML.impresion.xml.diseno.IXMLDesign;
import javax.swing.JPanel;
import utilesGUIx.formsGenericos.edicion.IFormEdicion;

/**
 *
 * @author eduardo
 */
public class JxmlFuenteDesign  extends JxmlBase {
    private JxmlFuente moObjeto;
    private JxmlInforme moInforme;

    public JxmlFuenteDesign() {
        moEdicion = new JPanelFuentePROPER();
    }
    public void setDatos(IxmlObjetos poObj, JxmlInforme poInforme)throws Exception{
        moObjeto = (JxmlFuente) poObj;
        moInforme = poInforme;
        ((JPanelFuentePROPER)moEdicion).setDatos(moObjeto, poInforme);
    }
    public JPanelBASE getVisualizacion() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
        
      protected Object clone() throws CloneNotSupportedException {
         JxmlFuenteDesign loT = (JxmlFuenteDesign) super.clone();
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
