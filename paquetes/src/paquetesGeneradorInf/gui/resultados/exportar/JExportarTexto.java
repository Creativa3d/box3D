/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package paquetesGeneradorInf.gui.resultados.exportar;

import ListDatos.JListDatos;
import ListDatos.JServerServidorDatosFichero;
import java.io.File;




public class JExportarTexto implements IExportar {
    private final boolean mbCVS;
    private char mcCaracter;
//    private String[] masOpciones;

    public JExportarTexto(boolean pbCVS, char pcCaracter) {
        mbCVS = pbCVS;
        mcCaracter = pcCaracter;
    }

    public void exportar(JListDatos poList, File poFile) throws Throwable {
        JServerServidorDatosFichero loServer = new JServerServidorDatosFichero(
                poFile.getParent(),
                mcCaracter,
                true);
        poList.msTabla = poFile.getName();
        loServer.guardar(poList);

    }

    public String getNombre() {
        if(mbCVS){
            return "Formato CVS";
        }else{
            return "Formato Texto";
        }
    }

    public String[] getOpcionesNombre() {
        if(mbCVS){
            return null;
        }else{
            return new String[]{"Separación"};
        }
    }
    public String[] getOpciones() {
        if(mbCVS){
            return null;
        }else{
            return new String[]{String.valueOf(mcCaracter)};
        }
    }

    public void setOpciones(String[] pasOpciones) {
//        masOpciones = pasOpciones;
        if(pasOpciones!=null && pasOpciones.length>0){
            mcCaracter = pasOpciones[0].charAt(0);
        }
    }
    public String[] getExtensiones() {
        if(mbCVS){
            return new String[]{"cvs"};
        }else{
            return new String[]{"txt"};
        }

    }
}
