/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesGUIx.plugin.seguridad;

import java.util.HashMap;
import utiles.JDepuracion;
import utilesGUIx.formsGenericos.*;
import utilesGUIx.plugin.IPlugIn;
import utilesGUIx.plugin.IPlugInConsulta;
import utilesGUIx.plugin.IPlugInContexto;
import utilesGUIx.plugin.IPlugInFrame;

public class JPlugInSeguridad implements IPlugIn {
    private HashMap moListaPermisosObjeto = new HashMap();
    private IPlugInSeguridadRW moSeguridadRW;


    public JPlugInSeguridad(final IPlugInSeguridadRW poSeguridadRW){
        moSeguridadRW = poSeguridadRW;
    }

    protected JTPlugInListaPermisos getPermisosdeIndentificador(IPlugInContexto poContexto, String psIdentificador) throws Exception{
        JTPlugInListaPermisos loPermisos =
                (JTPlugInListaPermisos) moListaPermisosObjeto.get(
                    psIdentificador
                    );
        if(loPermisos==null){
            loPermisos = JTPlugInListaPermisosUtil.getPermisosDeObjeto(
                        moSeguridadRW,
                        poContexto,
                        psIdentificador);
            if(loPermisos!=null){
                moListaPermisosObjeto.put(
                    psIdentificador,
                    loPermisos);
            }
        }
        return loPermisos;
    }

    public void procesarInicial(final IPlugInContexto poContexto) {
        try {
            JTPlugInListaPermisos loPermisos =
                    getPermisosdeIndentificador(
                        poContexto,
                        poContexto.getFormPrincipal().getIdentificador()
                        );
           if(loPermisos!=null){
            JTPlugInListaPermisosUtil.aplicarPermisos(loPermisos, poContexto.getFormPrincipal());
           }
        } catch (Exception ex) {
            JDepuracion.anadirTexto(getClass().getName(), ex);
        }

    }

    public void procesarConsulta(IPlugInContexto poContexto, IPlugInConsulta poConsulta) {
        try {
            JTPlugInListaPermisos loPermisos =
                    getPermisosdeIndentificador(
                        poContexto,
                        poConsulta.getIdentificador()
                        );

            if(loPermisos!=null){
                JTPlugInListaPermisosUtil.aplicarPermisos(loPermisos, poConsulta);
            }
        } catch (Exception ex) {
            JDepuracion.anadirTexto(getClass().getName(), ex);
        }
    }

    public void procesarEdicion(IPlugInContexto poContexto, IPlugInFrame poFrame) {
         try {
            JTPlugInListaPermisos loPermisos =
                    getPermisosdeIndentificador(
                        poContexto,
                        poFrame.getIdentificador()
                        );
            if(loPermisos!=null){
                JTPlugInListaPermisosUtil.aplicarPermisos(loPermisos, poFrame);
            }
        } catch (Exception ex) {
            JDepuracion.anadirTexto(getClass().getName(), ex);
        }
   }

    public void procesarControlador(IPlugInContexto poContexto, IPanelControlador poControlador) {
         try {
             if(poControlador.getParametros()!=null){
                JTPlugInListaPermisos loPermisos =
                        getPermisosdeIndentificador(
                            poContexto,
                            poControlador.getClass().getName()
                            );

                if(loPermisos!=null){
                    JTPlugInListaPermisosUtil.aplicarPermisos(loPermisos, poControlador);
                }
             }
        } catch (Exception ex) {
            JDepuracion.anadirTexto(getClass().getName(), ex);
        }

    }
    public void procesarFinal(IPlugInContexto poContexto) {
    }

}

