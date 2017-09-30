/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 
package utilesGUIx.plugin.seguridad;

import ListDatos.ECampoError;
import ListDatos.IFilaDatos;
import ListDatos.JListDatos;
import ListDatos.JListDatosFiltroConj;
import ListDatos.JUtilTabla;
import utiles.IListaElementos;
import utilesGUIx.formsGenericos.IPanelControladorConsulta;
import utilesGUIx.formsGenericos.IPanelControlador;
import utilesGUIx.formsGenericos.JPanelGeneralBotones;
import utilesGUIx.formsGenericos.boton.IBotonRelacionado;
import utilesGUIx.plugin.IPlugInContexto;
import utilesGUIx.plugin.IPlugInFrame;
import utilesGUIx.plugin.toolBar.IComponenteAplicacion;


public class JTPlugInListaPermisosUtil {


    private JTPlugInListaPermisosUtil (){

    }

    public static JTPlugInListaPermisos getPermisosDeObjeto(IPlugInSeguridadRW poSeguridadRW, String psUsuario, String psObjeto) throws Exception{
        JTPlugInListaPermisos loResult = null;
        JTPlugInListaPermisos loUsuarioTODOSPermisos = poSeguridadRW.getListaPermisosUsuario(psUsuario);
        JTPlugInUsuariosGrupos loUsuariosGrupos = poSeguridadRW.getUsuariosGrupos();

        if(loUsuarioTODOSPermisos!=null && loUsuariosGrupos!=null){
//            loUsuariosTODOSPermisos.moList.getFiltro().Clear();
//            loUsuariosTODOSPermisos.moList.filtrarNulo();
//            loGruposTodosPermisos.moList.getFiltro().Clear();
//            loGruposTodosPermisos.moList.filtrarNulo();
            loUsuariosGrupos.moList.getFiltro().Clear();
            loUsuariosGrupos.moList.filtrarNulo();
            try{
                //averiguamos los permisos del usuario para el objeto
                loUsuarioTODOSPermisos.moList.getFiltro().addCondicion(
                        JListDatosFiltroConj.mclAND,
                        JListDatos.mclTIgual,
                        loUsuarioTODOSPermisos.lPosiOBJETO,
                        psObjeto
                        );
                loUsuarioTODOSPermisos.moList.filtrar();

                //conseguimos los permisos de todos los grupos asociados al usuario sin duplicados
                JTPlugInListaPermisos loPermisosDeGruposDelUsuario =
                        JTPlugInListaPermisosUtil.getPermisosGrupos(
                            poSeguridadRW, loUsuariosGrupos, psUsuario
                        );

                loPermisosDeGruposDelUsuario.moList.getFiltro().addCondicion(
                        JListDatosFiltroConj.mclAND,
                        JListDatos.mclTIgual,
                        loPermisosDeGruposDelUsuario.lPosiOBJETO,
                        psObjeto
                        );
                loPermisosDeGruposDelUsuario.moList.filtrar();

                //borramos los permisos de los grupos q ya esten en los usuarios
                if(loUsuarioTODOSPermisos.moList.moveFirst()){
                    do{
                        if(loPermisosDeGruposDelUsuario.moList.buscar(
                                JListDatos.mclTIgual,
                                loPermisosDeGruposDelUsuario.malCamposPrincipales,
                                loUsuarioTODOSPermisos.moList.getFields().masCamposPrincipales())){
                            loPermisosDeGruposDelUsuario.moList.borrar(false);
                        }
                    }while(loUsuarioTODOSPermisos.moList.moveNext());
                }

                loResult = JTPlugInListaPermisosUtil.getUnionPermisos(loUsuarioTODOSPermisos, loPermisosDeGruposDelUsuario);


            }finally{
                loUsuarioTODOSPermisos.moList.getFiltro().Clear();
                loUsuarioTODOSPermisos.moList.filtrarNulo();
                loUsuariosGrupos.moList.getFiltro().Clear();
                loUsuariosGrupos.moList.filtrarNulo();
            }
        }

        return loResult;
    }
    public static JTPlugInListaPermisos getPermisosDeObjeto(IPlugInSeguridadRW poSeguridadRW,IPlugInContexto poContexto, String psObjeto) throws Exception{
        return getPermisosDeObjeto(poSeguridadRW, poContexto.getPARAMETROS().getUsuario(), psObjeto);
    }

    public static JTPlugInListaPermisos getPermisosGrupos(final IPlugInSeguridadRW poSeguridadRW,final JTPlugInUsuariosGrupos poUsuariosGrupos, final String psUsuario) throws Exception{
        //averiguamos los grupos del usuario
        poUsuariosGrupos.moList.getFiltro().addCondicion(
                JListDatosFiltroConj.mclAND,
                JListDatos.mclTIgual,
                poUsuariosGrupos.lPosiCODIGOUSUARIO,
                psUsuario
                );
        poUsuariosGrupos.moList.filtrar();
        JTPlugInListaPermisos loPermisosGrupo = new JTPlugInListaPermisos();
        try{
            //recuperamos los permisos de todos los grupos del usuario
            if(poUsuariosGrupos.moList.moveFirst()){
                do{
                    JTPlugInListaPermisos loAux = poSeguridadRW.getListaPermisosGrupo(poUsuariosGrupos.getCODIGOGRUPO().getString());
                    loPermisosGrupo.moList = JUtilTabla.moUnion(loPermisosGrupo.moList, loAux.moList);
                }while(poUsuariosGrupos.moList.moveNext());
            }
        }finally{
            poUsuariosGrupos.moList.getFiltro().Clear();
            poUsuariosGrupos.moList.filtrarNulo();
        }

        return loPermisosGrupo.getPermisosEliminarDuplicadosMenosPermisivos();
    }
    public static JTPlugInListaPermisos getUnionPermisos(JTPlugInListaPermisos po1, JTPlugInListaPermisos po2 ) throws ECampoError{
        JTPlugInListaPermisos loResult = new JTPlugInListaPermisos();
        //unimos los permisos de grupos y usuarios
        if(po1.moList.moveFirst()){
            do{
                loResult.moList.addNew();
                loResult.moList.getFields().cargar(po1.moList.moFila());
                loResult.moList.update(false);
            }while(po1.moList.moveNext());
        }
        if(po2.moList.moveFirst()){
            do{
                loResult.moList.addNew();
                loResult.moList.getFields().cargar(po2.moList.moFila());
                loResult.moList.update(false);
            }while(po2.moList.moveNext());
        }
        return loResult;
    }

    public static JTPlugInListaPermisos getPermisosEliminarDuplicadosMenosPermisivos(final JTPlugInListaPermisos poGrupos) throws Exception{

        //clonamos para el resultado
        JTPlugInListaPermisos loGrupos = new JTPlugInListaPermisos();
        loGrupos.moList = JUtilTabla.clonarFilasListDatos(poGrupos.moList, true);
        //ordemos
        loGrupos.moList.ordenar(loGrupos.malCamposPrincipales);

        //para cada permiso vemos is hay un duplicado y cojemos el mas
        //permisivo
        if(loGrupos.moList.moveFirst()){
            IFilaDatos lasCamposPAux=null;
            do{
                String[] lasCamposPA = loGrupos.moList.getFields().masCamposPrincipales();
                boolean lbCoinciden = false;
                if(lasCamposPAux!=null ){
                    lbCoinciden = true;
                    int i = 0;
                    while(i < lasCamposPA.length && lbCoinciden){
                        if(!lasCamposPAux.msCampo(i).equals(lasCamposPA[i])){
                            lbCoinciden = false;
                        }
                        i++;
                    }
                }
                if(lbCoinciden){
                    //si el registro anterior esta activado borramos el actual
                    //si no borramos el anterior
                    if(lasCamposPAux.msCampo(loGrupos.lPosiACTIVOSN).equals(JListDatos.mcsTrue)){
                        loGrupos.moList.borrar(false);
                        loGrupos.moList.movePrevious();
                    }else{
                        loGrupos.moList.movePrevious();
                        loGrupos.moList.borrar(false);
                    }
                }
                lasCamposPAux = loGrupos.moList.getFields().moFilaDatos();
            }while(loGrupos.moList.moveNext());
        }

        return loGrupos;
    }

    public static boolean aplicarPermisosPrincipal(JTPlugInListaPermisos poPermisos, IListaElementos poBotones) {
        boolean lbAlgunaAccion = false;
        for(int i = 0 ; i < poBotones.size(); i++){
            IComponenteAplicacion loBoton = (IComponenteAplicacion)poBotones.get(i);
            if(loBoton.getNombre()!=null){
                if(poPermisos.moList.buscar(JListDatos.mclTIgual, JTPlugInListaPermisos.lPosiACCION, loBoton.getNombre())){
                    loBoton.setActivo(poPermisos.getACTIVOSN().getBoolean());
                    lbAlgunaAccion = true;
                }
            }
            if(loBoton.getListaBotones()!=null){
                lbAlgunaAccion |= aplicarPermisosPrincipal(poPermisos, loBoton.getListaBotones());
            }
        }
        return lbAlgunaAccion;
    }
    public static void aplicarPermisosPrincipal(JTPlugInListaPermisos poPermisos, IPlugInFrame poFrameP) {
        if(poFrameP.getListaComponentesAplicacion()!=null){
            boolean lbAlgunaAccion = aplicarPermisosPrincipal(poPermisos, poFrameP.getListaComponentesAplicacion().getListaBotones());
            if(lbAlgunaAccion){
                poFrameP.aplicarListaComponentesAplicacion();
            }
        }
    }
    private static void aplicarPermisos(JTPlugInListaPermisos poPermisos, IBotonRelacionado poBoton) {
        if(poPermisos.moList.buscar(JListDatos.mclTIgual, JTPlugInListaPermisos.lPosiACCION, poBoton.getNombre())){
            poBoton.setActivoSeguridad(poPermisos.getACTIVOSN().getBoolean());
        }
    }
    private static void aplicarPermisos(JTPlugInListaPermisos poPermisos, JPanelGeneralBotones poBotonesGenerales) {
        aplicarPermisos(poPermisos, poBotonesGenerales.getAceptar());
        aplicarPermisos(poPermisos, poBotonesGenerales.getCancelar());
        aplicarPermisos(poPermisos, poBotonesGenerales.getCopiarTabla());
        aplicarPermisos(poPermisos, poBotonesGenerales.getEditar());
        aplicarPermisos(poPermisos, poBotonesGenerales.getFiltro());
        aplicarPermisos(poPermisos, poBotonesGenerales.getNuevo());
        aplicarPermisos(poPermisos, poBotonesGenerales.getRefrescar());
        aplicarPermisos(poPermisos, poBotonesGenerales.getBorrar());

        for(int i = 0 ; i < poBotonesGenerales.getListaBotones().size(); i++){
            IBotonRelacionado loBoton = (IBotonRelacionado)poBotonesGenerales.getListaBotones().get(i);
            if(poPermisos.moList.buscar(JListDatos.mclTIgual, JTPlugInListaPermisos.lPosiACCION, loBoton.getNombre())){
                loBoton.setActivo(poPermisos.getACTIVOSN().getBoolean());
            }
        }
    }
//    public static void aplicarPermisos(JTPlugInListaPermisos poPermisos, Component poComponente){
//        if(poPermisos.moList.buscar(JListDatos.mclTIgual, JTPlugInListaPermisos.lPosiACCION, poComponente.getName())){
//            poComponente.setEnabled(poPermisos.getACTIVOSN().getBoolean());
//        }
//    }
//    public static void aplicarPermisos(JTPlugInListaPermisos poPermisos, AbstractButton poComponente){
//        if(poPermisos.moList.buscar(JListDatos.mclTIgual, JTPlugInListaPermisos.lPosiACCION, poComponente.getActionCommand())){
//            poComponente.setEnabled(poPermisos.getACTIVOSN().getBoolean());
//        }
//    }
//    public static void aplicarPermisos(JTPlugInListaPermisos poPermisos, JMenuItem poMenuItem){
//        if(poPermisos.moList.buscar(JListDatos.mclTIgual, JTPlugInListaPermisos.lPosiACCION, poMenuItem.getActionCommand() )){
//            poMenuItem.setVisible(poPermisos.getACTIVOSN().getBoolean());
//        }
//    }
//    public static void aplicarPermisos(JTPlugInListaPermisos poPermisos, JMenu poMenu){
//        if(poMenu!=null){
//            if(poPermisos.moList.buscar(JListDatos.mclTIgual, JTPlugInListaPermisos.lPosiACCION, poMenu.getActionCommand())){
//                poMenu.setVisible(poPermisos.getACTIVOSN().getBoolean());
//            }
//            for(int i = 0 ; i < poMenu.getMenuComponentCount(); i++){
//                Component loComp = poMenu.getMenuComponent(i);
//                if(loComp instanceof JMenu){
//                    aplicarPermisos(poPermisos, (JMenu)loComp);
//                }else if(loComp instanceof JMenuItem){
//                    aplicarPermisos(poPermisos, (JMenuItem)loComp);
//                } else{
//                    aplicarPermisos(poPermisos, loComp);
//                }
//            }
//        }
//    }
//    public static void aplicarPermisos(JTPlugInListaPermisos poPermisos, JMenuBar poMenuBar){
//        if(poPermisos.moList.buscar(JListDatos.mclTIgual, poPermisos.lPosiACCION, poMenuBar.getName())){
//            poMenuBar.setVisible(poPermisos.getACTIVOSN().getBoolean());
//        }
//        for(int i = 0 ; i < poMenuBar.getMenuCount(); i++){
//            JMenu loMenu = poMenuBar.getMenu(i);
//            aplicarPermisos(poPermisos, loMenu);
//        }
//    }
    public static void aplicarPermisos(JTPlugInListaPermisos poPermisos, IPlugInFrame poFrame){
        poPermisos.moList.getFiltro().Clear();
        poPermisos.moList.filtrarNulo();
        poPermisos.moList.getFiltro().addCondicion(
                JListDatosFiltroConj.mclAND,
                JListDatos.mclTIgual,
                poPermisos.lPosiOBJETO,
                poFrame.getIdentificador()
                );
        poPermisos.moList.filtrar();

        try{
            if(poFrame.getParametros() != null &&
               poPermisos.moList.buscar(JListDatos.mclTIgual, JTPlugInListaPermisos.lPosiACCION, "")){
                poFrame.getParametros().setSoloLectura(!poPermisos.getACTIVOSN().getBoolean());
            }
            aplicarPermisosPrincipal(poPermisos, poFrame);
        }finally{
            poPermisos.moList.getFiltro().Clear();
            poPermisos.moList.filtrarNulo();
        }


    }
    public static void aplicarPermisos(JTPlugInListaPermisos poPermisos, IPanelControlador poControlador){
        poPermisos.moList.getFiltro().Clear();
        poPermisos.moList.filtrarNulo();
        poPermisos.moList.getFiltro().addCondicion(
                JListDatosFiltroConj.mclAND,
                JListDatos.mclTIgual,
                poPermisos.lPosiOBJETO,
                poControlador.getClass().getName()
                );
        poPermisos.moList.filtrar();
        try{
            if(poControlador.getParametros() != null &&
               poPermisos.moList.buscar(JListDatos.mclTIgual, poPermisos.lPosiACCION, "")){
                poControlador.getParametros().setActivado(poPermisos.getACTIVOSN().getBoolean());
            }
            if(poControlador.getParametros()!=null &&
               poControlador.getParametros().getBotonesGenerales()!=null){
                aplicarPermisos(poPermisos, poControlador.getParametros().getBotonesGenerales());
            }
        }finally{
            poPermisos.moList.getFiltro().Clear();
            poPermisos.moList.filtrarNulo();
        }
    }
    public static void aplicarPermisos(JTPlugInListaPermisos poPermisos, IPanelControladorConsulta poControladorConsulta){
        //OJO Faltan los persisos propios de la consulta
    }

}
