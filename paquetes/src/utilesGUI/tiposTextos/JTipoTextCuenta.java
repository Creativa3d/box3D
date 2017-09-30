/*
 * JTipoTextoEstandar.java
 *
 * Created on 16 de noviembre de 2006, 9:56
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utilesGUI.tiposTextos;

import utiles.JCuentaBancaria;
import utiles.JDepuracion;

public class JTipoTextCuenta extends JTipoTextoBaseAbstract {
    public static final int mclTextCuenta = 6;
    private final JCuentaBancaria moCuenta = new JCuentaBancaria();
    
    public JTipoTextCuenta() {
        mlTipo = mclTextCuenta;
    }
    
    @Override
    public boolean isTipoCorrecto(final String psTexto) {
       boolean lbCorrecto = true;
        try{
            String lsTexto = psTexto.trim();
           if(lsTexto != null && !lsTexto.equals("")){
               switch(mlTipo){
                   case mclTextCuenta:
                       if(psTexto.length()==24){
                            lbCorrecto = moCuenta.validarIBAN(lsTexto);
                       }else{
                            lbCorrecto = moCuenta.validarCuenta(lsTexto);
                       }
                       break;
               }
            }
        }catch(Throwable e){
            JDepuracion.anadirTexto(getClass().getName(), e);
        }       
       return lbCorrecto;
    }

    @Override
    public String getTextoModificado(final String psTexto) {
        String lsTexto = psTexto;
        if (lsTexto != null && !lsTexto.equals("")) {
            lsTexto = lsTexto.toUpperCase();            
        }
        return lsTexto;

    }

    @Override
    public String getTextoError(final String psTexto) {
        String lsTextoError=null;
        try{
            String lsTexto = psTexto.trim();
            switch(mlTipo){

               case mclTextCuenta:
                   if(!isTipoCorrecto(lsTexto)){
                        lsTextoError= "El número de cuenta no es válido";
                    }
                   break;
               default:

           }    
        }catch(Throwable e){
            JDepuracion.anadirTexto(getClass().getName(), e);
        }
        return lsTextoError;
    }
}
