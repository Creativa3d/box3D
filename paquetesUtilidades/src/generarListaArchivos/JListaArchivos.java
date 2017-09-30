/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generarListaArchivos;

import java.io.File;

/**
 *
 * @author eduardo
 */
public class JListaArchivos {
    private StringBuffer msListaArchivos;
    private final File moFile;
    
    public JListaArchivos(File poFile){
        moFile = poFile;

    }

    public void generarListaArchivos(){
        msListaArchivos = new StringBuffer();
        generarListaArchivos(moFile);
    }
    public void generarListaArchivos(File poFile ){
        if(poFile.isDirectory()){
            //lista de ficheros
            File[] files = poFile.listFiles();
            int i = 0;
            File loFile;
            for (; i<files.length;i++){
                loFile = files[i];
                generarListaArchivos(loFile);
                
            }

            System.gc();

        }else{
            msListaArchivos.append("<jar href=\""+poFile.getAbsolutePath().substring(moFile.getAbsolutePath().length()+1)+"\"/>");
            msListaArchivos.append('\n');
        }
    }
    public String getLista(){
        return msListaArchivos.toString();
    }
    public String getListaArchivos(){
        StringBuffer lsLinea = new StringBuffer();
        lsLinea.append("<?xml version=\"1.0\" encoding=\"ISO-8859-15\"?>");lsLinea.append('\n');
        lsLinea.append("<root>");lsLinea.append('\n');
        lsLinea.append("    <resources>");lsLinea.append('\n');
        lsLinea.append(msListaArchivos.toString());
        lsLinea.append("    </resources>");lsLinea.append('\n');
        lsLinea.append("</root>");lsLinea.append('\n');
        return lsLinea.toString();
    }
    public static void main(String[] pas){
        JListaArchivos loLista = new JListaArchivos(new File("/home/eduardo/d/entregas/dpa/BDA/instalacion20150825/aplicacion/aplicacion/"));
        loLista.generarListaArchivos();
        System.out.println(loLista.getListaArchivos());
        
    }
}
