/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesAndroid.util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import utiles.IDepuracionIMPL;
import utiles.JDateEdu;
import utiles.JDepuracion;

public class JDepuracionANDROID implements IDepuracionIMPL {
    private Context moContext;
    
    public void setAplicacionContext(Context poContext){
        moContext = poContext;
    }
    
    public void anadirTexto(int plNivel, String psGrupo, Throwable e, Object poExtra) {
        anadirTexto(plNivel, psGrupo, e.toString(), null);
        Log.e(psGrupo,"Error", e);

    }

    public void anadirTexto(int plNivel, String psGrupo, String psTexto, Object poExtra) {
        JDateEdu loDate = new JDateEdu();
        String lsTExto = loDate.toString() + " " + JDepuracion.getNivel(plNivel) + ":" + psGrupo + "->" + psTexto;
        switch(plNivel){
            case JDepuracion.mclCRITICO:
                Log.e(psGrupo, lsTExto);
                break;
            case JDepuracion.mclINFORMACION:
                Log.i(psGrupo, lsTExto);
                break;
            case JDepuracion.mclWARNING:
                Log.w(psGrupo, lsTExto);
                break;
            default:
                Log.v(psGrupo, lsTExto);
        }
    }

}
