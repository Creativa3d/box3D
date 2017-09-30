/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package impresionXML.impresion.xml.diseno.componentes;

import impresionXML.impresion.xml.IxmlObjetos;
import impresionXML.impresion.xml.JxmlBanda;
import impresionXML.impresion.xml.JxmlInforme;
import impresionXML.impresion.xml.diseno.IXMLDesign;
import javax.swing.JPanel;

/**
 *
 * @author eduardo
//// */
public class JxmlBandaDesign extends JxmlBase {
    private JxmlBanda moObjeto;
    private JPanelBASE moPanel;
    private JxmlInforme moInforme;
    
    public JxmlBandaDesign() {
    }
    public void setDatos(IxmlObjetos poObj, JxmlInforme poInforme)throws Exception{
        moObjeto = (JxmlBanda) poObj;
        moInforme = poInforme;
    }
    public void setDatos(JxmlBanda poBanda){
        moObjeto=poBanda;
    }

    public JPanelBASE getVisualizacion() {
        return moPanel;
    }
      protected Object clone() throws CloneNotSupportedException {
         JxmlTextoDesign loT = (JxmlTextoDesign) super.clone();
//         loT.moPanel=new JPanelTextoVIEW();
//         loT.moObjeto=null;
        return loT; 
    }

    public IXMLDesign Clone() throws CloneNotSupportedException{
            return (IXMLDesign) clone();
    }   

    public IxmlObjetos getObjetoXML() {
        return moObjeto;
    }
}
