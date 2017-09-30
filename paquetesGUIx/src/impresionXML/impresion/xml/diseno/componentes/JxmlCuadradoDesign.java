/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package impresionXML.impresion.xml.diseno.componentes;

import impresionXML.impresion.xml.IxmlObjetos;
import impresionXML.impresion.xml.JxmlCuadrado;
import impresionXML.impresion.xml.JxmlInforme;
import impresionXML.impresion.xml.diseno.IXMLDesign;
import utilesGUIx.formsGenericos.edicion.IFormEdicion;

/**
 *
 * @author eduardo
 */
public class JxmlCuadradoDesign  extends JxmlBase {
    private JxmlCuadrado moObjeto;
    private JxmlInforme moInforme;
    private JPanelCuadradoVIEW moPanel=new JPanelCuadradoVIEW();

    public JxmlCuadradoDesign() {
        moEdicion = new JPanelCuadradoPROPER();
    }
    public void setDatos(IxmlObjetos poObj, JxmlInforme poInforme)throws Exception{
        moObjeto = (JxmlCuadrado) poObj;
        moInforme = poInforme;
        moPanel.setDatos(moObjeto, poInforme);
        ((JPanelCuadradoPROPER)moEdicion).setDatos(moObjeto, poInforme);
    }


    public JPanelBASE getVisualizacion() {
        return moPanel;
    }
    public IFormEdicion getPropiedades() throws Exception {
        ((JPanelCuadradoPROPER)moEdicion).setDatos(moObjeto, moInforme);
        return super.getPropiedades(); 
    }
        
      protected Object clone() throws CloneNotSupportedException {
         JxmlCuadradoDesign loT = (JxmlCuadradoDesign) super.clone();
         loT.moPanel=new JPanelCuadradoVIEW();
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
