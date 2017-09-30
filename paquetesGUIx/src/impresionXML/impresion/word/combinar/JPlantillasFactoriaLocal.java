/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package impresionXML.impresion.word.combinar;

/**
 *
 * @author eduardo
 */
public class JPlantillasFactoriaLocal implements IPlantillasFactoria{

    private final String msRutaTrabajo;

    public JPlantillasFactoriaLocal(String psRutaTrabajo){
        msRutaTrabajo=psRutaTrabajo;
    }
    
    
    @Override
    public IPlantillas getNuevaPlantilla(String psGrupo) {
        return new JWord(psGrupo, msRutaTrabajo);
    }
    
}
