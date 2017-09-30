/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package impresionXML.impresion.word.combinar.plugin;

import ListDatos.JListDatos;

/**
 *
 * @author eduardo
 */
public interface IPlantillasControlador {
    public JListDatos getListDatosPlantilla(int[] palIndex) throws Exception;
    public JPlantillaControladorParam getParamPlantilla();
}
