/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package impresionXML.impresion.xml.diseno;

import impresionXML.impresion.xml.diseno.componentes.JPanelBASE;
import impresionXML.impresion.xml.IxmlObjetos;
import impresionXML.impresion.xml.JxmlInforme;
import utilesGUIx.formsGenericos.edicion.IFormEdicion;

/**
 *
 * @author eduardo
 */
public interface IXMLDesign extends Cloneable {
    public IFormEdicion getPropiedades() throws Exception;
    public JPanelBASE getVisualizacion();
    public void setDatos(IxmlObjetos poObj, JxmlInforme poInforme)throws Exception;
    public IXMLDesign Clone() throws CloneNotSupportedException;
    public  IxmlObjetos getObjetoXML();
}
