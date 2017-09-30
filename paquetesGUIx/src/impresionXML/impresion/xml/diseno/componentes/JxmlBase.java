/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package impresionXML.impresion.xml.diseno.componentes;

import impresionXML.impresion.xml.diseno.IXMLDesign;
import utilesGUIx.formsGenericos.edicion.IFormEdicion;

/**
 *
 * @author eduardo
 */
public abstract class JxmlBase implements IXMLDesign {
    protected IFormEdicion moEdicion;
    

    public IFormEdicion getPropiedades() throws Exception{
        moEdicion.rellenarPantalla();
        moEdicion.ponerTipoTextos();
        moEdicion.mostrarDatos();
        moEdicion.habilitarSegunEdicion();
        return moEdicion;
    }    
    
}
