/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package paquetesGeneradorInf.gui.resultados.exportar;

import ListDatos.JListDatos;
import java.io.File;

/**
 *
 * @author eduardo
 */
public interface IExportar {
    public void exportar(JListDatos poList, File poFile) throws Throwable;
    public String getNombre();
    public String[] getOpcionesNombre();
    public String[] getOpciones();
    public void setOpciones(String[] pasOpciones);
    public String[] getExtensiones();


}
