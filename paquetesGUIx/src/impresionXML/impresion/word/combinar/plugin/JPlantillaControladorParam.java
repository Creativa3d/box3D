/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package impresionXML.impresion.word.combinar.plugin;

import ListDatos.JListDatos;
import ListDatos.JUtilTabla;

/**
 *
 * @author eduardo
 */
public class JPlantillaControladorParam {
    public int lPosiEMAIL;
    public int lPosiIDENTIFICAR;
    
    public JPlantillaControladorParam (){
        
    }   
    public JPlantillaControladorParam (int plPosiEmail, int plPosiIdentificar){
        lPosiEMAIL=plPosiEmail;
        lPosiIDENTIFICAR=plPosiIdentificar;
    }

    public static JListDatos getSoloSeLect(JListDatos poList,int[] plIndex) throws Exception{
        return JUtilTabla.getListDatosDeIndices(poList, plIndex);
    }
    
}
