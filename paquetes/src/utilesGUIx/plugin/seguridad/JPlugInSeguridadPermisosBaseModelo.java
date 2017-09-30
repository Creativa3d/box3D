/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesGUIx.plugin.seguridad;

import ListDatos.ECampoError;
import ListDatos.JListDatos;
import utiles.IListaElementos;
import utiles.JListaElementos;
import utilesGUIx.JGUIxConfigGlobalModelo;
import utilesGUIx.formsGenericos.*;
import utilesGUIx.formsGenericos.boton.IBotonRelacionado;
import utilesGUIx.formsGenericos.edicion.IFormEdicion;
import utilesGUIx.formsGenericos.edicion.IFormEdicionLista;
import utilesGUIx.plugin.*;
import utilesGUIx.plugin.toolBar.IComponenteAplicacion;

public class JPlugInSeguridadPermisosBaseModelo {
    public static final String mcsTablas = "Tablas";
    public static final String mcsControlador = "Listado";
    public static final String mcsFrame = "Ficha";
    public static final String mcsFramePrincipal = "Principal";
    public static final String mcsEditar = "Editar";
    public static final String mcsNuevo = "Nuevo";
    public static final String mcsBorrar = "Borrar";
    public static final String mcsEditable = "Editable";
    private IListaElementos moLista;
    
    public JPlugInSeguridadPermisosBaseModelo (){
        moLista = new JListaElementos();
    }

    public IListaElementos getLista(){
        return moLista;
    }
    public void addPermisoBase(String psObjeto, String psCaption, String psAccion, boolean pbActivo) throws ECampoError {
        JTPlugInListaPermisos loPermisos = new JTPlugInListaPermisos();
        loPermisos.moList.addNew();
        loPermisos.getOBJETO().setValue(psObjeto);
        loPermisos.getCAPTIONOBJETO().setValue(psCaption);
        loPermisos.getACCION().setValue(psAccion);
        loPermisos.getACTIVOSN().setValue(true);
        loPermisos.moList.update(false);
        moLista.add(loPermisos);
    }    
    public void addTabla(final String psTabla){
        moLista.add(psTabla);
    }
    public void addControlador(final IPanelControlador poControlador){
        JGUIxConfigGlobalModelo.getInstancia().getPlugInFactoria().getPlugInManager().procesarControlador(
                JGUIxConfigGlobalModelo.getInstancia().getPlugInFactoria().getPlugInContexto(),
                poControlador
                );
        moLista.add(poControlador);
    }
    public void addFrame(final IPlugInFrame poFrame){
        JGUIxConfigGlobalModelo.getInstancia().getPlugInFactoria().getPlugInManager().procesarEdicion(
                JGUIxConfigGlobalModelo.getInstancia().getPlugInFactoria().getPlugInContexto(),
                poFrame
                );
        moLista.add(poFrame);
    }
    public void addConsulta(final IPlugInConsulta poFrame){
        JGUIxConfigGlobalModelo.getInstancia().getPlugInFactoria().getPlugInManager().procesarConsulta(
                JGUIxConfigGlobalModelo.getInstancia().getPlugInFactoria().getPlugInContexto(),
                poFrame
                );
        moLista.add(poFrame);
    }

    public JTPlugInListaPermisos getListaPermisosBase() throws ECampoError{
        JTPlugInListaPermisos loPermisos = new JTPlugInListaPermisos();

        for(int i = 0 ; i < moLista.size(); i++){
            Object loObj = moLista.get(i);
            if(loObj instanceof IPanelControlador){
                IPanelControlador loPanelControlador = (IPanelControlador) loObj;
                rellenarPermisos(loPermisos, loPanelControlador);
            } else if(loObj instanceof IPlugInConsulta){
                IPlugInConsulta loPlugFrame = (IPlugInConsulta) loObj;
                rellenarPermisosC(loPermisos, loPlugFrame);
                //OJO: falta los propios de la consulta

            } else if(loObj instanceof IPlugInFrame){
                IPlugInFrame loPlugFrame = (IPlugInFrame) loObj;
                rellenarPermisos(loPermisos, loPlugFrame);
            } else if(loObj instanceof String){
                rellenarPermisos(loPermisos, (String)loObj);
            } else if(loObj instanceof JTPlugInListaPermisos){
                JTPlugInListaPermisos loPermisosAux = (JTPlugInListaPermisos) loObj;
                if(loPermisosAux.moveFirst()){
                    do{
                        addPermiso(loPermisos, loPermisosAux.getOBJETO().getString(), loPermisosAux.getCAPTIONOBJETO().getString(), loPermisosAux.getACCION().getString());
                    }while(loPermisosAux.moveNext());
                }
            }

        }

        return loPermisos;
    }

    public static void rellenarPermisos(JTPlugInListaPermisos poPermisos, IPlugInFrame poFrame, IListaElementos poBotones) throws ECampoError {
        for(int lb = 0 ; lb < poBotones.size(); lb++){
            IComponenteAplicacion loComp = (IComponenteAplicacion) poBotones.get(lb);
            addPermisoSiAccion(
                    poPermisos,
                    poFrame.getIdentificador(),
                    getIdenbotnito(poFrame),
                    loComp.getNombre());
            if(loComp.getListaBotones()!=null){
                rellenarPermisos(poPermisos, poFrame, loComp.getListaBotones());
            }
        }
        
    }
    public static void rellenarPermisos(JTPlugInListaPermisos poPermisos, String psTabla) throws ECampoError {
        addPermiso(poPermisos, mcsTablas, mcsTablas, psTabla + "." + mcsEditar);
        addPermiso(poPermisos, mcsTablas, mcsTablas, psTabla + "." + mcsNuevo);
        addPermiso(poPermisos, mcsTablas, mcsTablas, psTabla + "." + mcsBorrar);
    }
    public static void addPermisoSiAccion(JTPlugInListaPermisos poPermisos, String psObjeto, String psCaption, String psAccion) throws ECampoError {
        if(psAccion!=null && !psAccion.equals("")){
            addPermiso(poPermisos, psObjeto, psCaption, psAccion);
        }

    }
    public static void addPermiso(JTPlugInListaPermisos poPermisos, String psObjeto, String psCaption, String psAccion) throws ECampoError {
        if(!poPermisos.moList.buscar(JListDatos.mclTIgual , new int[] {
                poPermisos.lPosiOBJETO, poPermisos.lPosiACCION
            }, new String[]{
                psObjeto, psAccion
            })){
                poPermisos.moList.addNew();
                poPermisos.getOBJETO().setValue(psObjeto);
                poPermisos.getCAPTIONOBJETO().setValue(psCaption);
                poPermisos.getACCION().setValue(psAccion);
                poPermisos.getACTIVOSN().setValue(true);
                poPermisos.moList.update(false);
        }
    }
    public static void rellenarPermisos(JTPlugInListaPermisos poPermisos, IPanelControlador loPanelControlador) throws ECampoError {
        addPermiso(poPermisos, loPanelControlador.getClass().getName(), mcsControlador + " " +  loPanelControlador.getParametros().getNombre(), "");
        for(int lB = 0 ; lB < loPanelControlador.getParametros().getBotonesGenerales().getListaBotones().size(); lB++){
            IBotonRelacionado loBoton = (IBotonRelacionado) loPanelControlador.getParametros().getBotonesGenerales().getListaBotones().get(lB);
            addPermisoSiAccion(poPermisos, loPanelControlador.getClass().getName(), mcsControlador + " " + loPanelControlador.getParametros().getNombre(), loBoton.getNombre());
        }
        addPermiso(poPermisos, loPanelControlador.getClass().getName(), mcsControlador + " " +  loPanelControlador.getParametros().getNombre(), JPanelGeneralBotones.mcsAceptar);
        addPermiso(poPermisos, loPanelControlador.getClass().getName(), mcsControlador + " " +  loPanelControlador.getParametros().getNombre(), JPanelGeneralBotones.mcsBorrar);
        addPermiso(poPermisos, loPanelControlador.getClass().getName(), mcsControlador + " " +  loPanelControlador.getParametros().getNombre(), JPanelGeneralBotones.mcsCancelar);
        addPermiso(poPermisos, loPanelControlador.getClass().getName(), mcsControlador + " " +  loPanelControlador.getParametros().getNombre(), JPanelGeneralBotones.mcsCopiarTabla);
        addPermiso(poPermisos, loPanelControlador.getClass().getName(), mcsControlador + " " +  loPanelControlador.getParametros().getNombre(), JPanelGeneralBotones.mcsEditar);
        addPermiso(poPermisos, loPanelControlador.getClass().getName(), mcsControlador + " " +  loPanelControlador.getParametros().getNombre(), JPanelGeneralBotones.mcsFiltro);
        addPermiso(poPermisos, loPanelControlador.getClass().getName(), mcsControlador + " " +  loPanelControlador.getParametros().getNombre(), JPanelGeneralBotones.mcsNuevo);
        addPermiso(poPermisos, loPanelControlador.getClass().getName(), mcsControlador + " " +  loPanelControlador.getParametros().getNombre(), JPanelGeneralBotones.mcsRefrescar);
    }
    public static void rellenarPermisosC(JTPlugInListaPermisos poPermisos, IPlugInConsulta poFrame) throws ECampoError {
        rellenarPermisos(poPermisos, (IPlugInFrame)poFrame);
    }

    public static String getIdenbotnito(IPlugInFrame poObjeto){
        String lsIdentB = "";
        String psIdent = poObjeto.getIdentificador();
        int i = psIdent.length()-1;
        while(i>=0 && psIdent.charAt(i)!='.'){
            lsIdentB=psIdent.charAt(i)+lsIdentB;
            i--;
        }

        if(poObjeto instanceof IPlugInFrame){
            lsIdentB = mcsFrame + " " + lsIdentB;
        }


        return lsIdentB;
    }
    public static void rellenarPermisos(JTPlugInListaPermisos poPermisos, IPlugInFrame poPlugFrame) throws ECampoError {
        if(poPlugFrame instanceof IFormEdicion || poPlugFrame instanceof IFormEdicionLista){
            addPermiso(poPermisos, poPlugFrame.getIdentificador(), getIdenbotnito(poPlugFrame), mcsEditable);
        }
        rellenarPermisos(poPermisos, poPlugFrame, poPlugFrame.getListaComponentesAplicacion().getListaBotones());

    }
}
