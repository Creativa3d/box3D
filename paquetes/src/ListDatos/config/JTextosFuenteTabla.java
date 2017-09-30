/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ListDatos.config;

import ListDatos.ECampoError;
import ListDatos.JListDatos;


public class JTextosFuenteTabla implements ITextosFuente {
    public static final int mclNombre = 0;
    public static final int mclValor = 1;
    
    private JListDatos moTextos=null;
    
    /** Creates a new instance of JDevolverTextos */
    public JTextosFuenteTabla(JListDatos poTextos) {
        moTextos = poTextos;
        moTextos.ordenar(mclNombre);
    }
    
    public JTextosFuenteTabla(JListDatos poTextos, boolean pbTabla_Campo, int plPosiTabla, int plPosiCampo, int plPosiValor) throws ECampoError {
        if(pbTabla_Campo){
            moTextos = new JListDatos(null, null, new String[]{"",""}, new int[]{JListDatos.mclTipoCadena,JListDatos.mclTipoCadena}, new int[]{0});
            if(poTextos.moveFirst()) {
                do{
                    moTextos.addNew();
                    moTextos.getFields(mclNombre).setValue(poTextos.getFields(plPosiTabla).getString() + "_" + poTextos.getFields(plPosiCampo).getString() );
                    moTextos.getFields(mclValor).setValue(poTextos.getFields(plPosiValor).getString());
                    moTextos.update(false);
                }while(poTextos.moveNext());
            }
        }else{
            moTextos = poTextos;
        }
        moTextos.ordenar(mclNombre);
    }
    public String getString(String psKey){
        String lsValor="";
        try{
            if(moTextos.buscarBinomial(new int[]{mclNombre}, new String[]{psKey})) {
                lsValor=moTextos.getFields(mclValor).getString();
            }
        }catch(Exception e){}
        return lsValor;
    }
    public String getKeySinRaros(String psKey){
        return psKey.replace(' ', '-');
    }
}
