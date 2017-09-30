

package utilesEjecutar;

import utiles.JDepuracion;


public class JEjecutarActualizadoSinGUI {
    public static void main(final String[] pasARRAY){
        //nivel depuracion= informacion para sacar todo lo q hace
        JDepuracion.mlNivelDepuracion = JDepuracion.mclINFORMACION;
        //creamos la fabrica de clases
        final JAbstractFactoryEjecutar loFac = new JAbstractFactoryEjecutar();
        //creamos un coordinador
        final JControladorCoordinadorEjecutar loCoor = new JControladorCoordinadorEjecutar();

        //lo ejecutamos
        if(pasARRAY.length>0){
            for(int i = 1 ; i < pasARRAY.length;i++){
                String lsParam = pasARRAY[i];
                int lPosi = lsParam.indexOf('=');
                if(lPosi>=0 && lPosi < (lsParam.length()-1)){
                    loCoor.addVariable(
                        lsParam.substring(0, lPosi),
                        lsParam.substring(lPosi+1, lsParam.length()));
                }else{
                    loCoor.addVariable(lsParam, "");
                }
            }
            loCoor.ejecutarCompleto(pasARRAY[0], loFac);
        }else{
            loCoor.ejecutarCompleto("ejecucion.xml", loFac);
        }


    }
}
