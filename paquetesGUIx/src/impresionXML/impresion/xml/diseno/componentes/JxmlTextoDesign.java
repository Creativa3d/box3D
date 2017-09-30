/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package impresionXML.impresion.xml.diseno.componentes;

import impresionXML.impresion.xml.IxmlObjetos;
import impresionXML.impresion.xml.JxmlInforme;
import impresionXML.impresion.xml.JxmlTexto;
import impresionXML.impresion.xml.diseno.IXMLDesign;
import javax.swing.JPanel;
import utilesGUIx.formsGenericos.edicion.IFormEdicion;

/**
 *
 * @author eduardo
 */
public class JxmlTextoDesign  extends JxmlBase {
    private JxmlTexto moObjeto;
    private JPanelTextoVIEW moPanel=new JPanelTextoVIEW();
    private JxmlInforme moInforme;

    public JxmlTextoDesign() {
        moEdicion = new JPanelTextoPROPER();
    }
    public void setDatos(IxmlObjetos poTexto, JxmlInforme poInforme) throws Exception{
        moObjeto=(JxmlTexto) poTexto;
        moInforme = poInforme;
        moPanel.setDatos(moObjeto, poInforme);
        ((JPanelTextoPROPER)moEdicion).setDatos(moObjeto, poInforme);

    }

    public IFormEdicion getPropiedades() throws Exception {
        ((JPanelTextoPROPER)moEdicion).setDatos(moObjeto, moInforme);
        return super.getPropiedades(); 
    }
    
    public JPanelBASE getVisualizacion() {
        return moPanel;
    }
     protected Object clone() throws CloneNotSupportedException {
         JxmlTextoDesign loT = (JxmlTextoDesign) super.clone();
         loT.moPanel=new JPanelTextoVIEW();
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
