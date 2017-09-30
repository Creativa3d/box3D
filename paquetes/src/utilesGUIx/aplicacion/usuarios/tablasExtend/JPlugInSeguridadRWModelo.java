/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesGUIx.aplicacion.usuarios.tablasExtend;


import ListDatos.ECampoError;
import ListDatos.IFilaDatos;
import ListDatos.IResultado;
import ListDatos.IServerServidorDatos;
import ListDatos.JActualizarConj;
import ListDatos.JListDatos;
import ListDatos.JListDatosFiltroElem;
import ListDatos.JUtilTabla;
import utilesGUIx.JGUIxConfigGlobalModelo;
import utilesGUIx.plugin.seguridad.IPlugInSeguridadRW;
import utilesGUIx.plugin.seguridad.JTPlugInGrupos;
import utilesGUIx.plugin.seguridad.JTPlugInListaPermisos;
import utilesGUIx.plugin.seguridad.JTPlugInListaPermisosUtil;
import utilesGUIx.plugin.seguridad.JTPlugInUsuarios;
import utilesGUIx.plugin.seguridad.JTPlugInUsuariosGrupos;

public abstract class JPlugInSeguridadRWModelo implements IPlugInSeguridadRW {

    public static final String mcsAccionNula = "----";
    public static final String mcsBaseDatos = "Base datos";
    public static final String mcsEscrituraSN = "¿Escritura?";
    public static final String mcsTODOS = "TODOS";
    public static final String mcsSeguridad = "Seguridad";
    
    public JTPlugInUsuariosGrupos moUsuGrupos;
    public JTPlugInGrupos moGrupos;
    public JTPlugInUsuarios moUsuarios;
    public JTPlugInListaPermisos moListaPermisosGrupo;
    public JTPlugInListaPermisos moListaPermisosUsuario;
    protected JTPlugInListaPermisos moPermisosBase;
    protected JTEEUSUARIOS moUsuariosBD;
    protected JTEEUGRUPOS moGruposBD;
    protected boolean mbAnular = false;
    protected IServerServidorDatos moServer;
    protected JTPlugInListaPermisos moTiposPermisosBase;
    protected String msCodUsuario;
    private boolean mbSoloLectura=false;

    public JPlugInSeguridadRWModelo() {
    }

    private void setServer(IServerServidorDatos poServer) throws Exception {
        moServer = poServer;

    }

    @Override
    public synchronized JTPlugInUsuariosGrupos getUsuariosGrupos() throws Exception {
        if (moUsuGrupos == null && !mbAnular) {
            moUsuGrupos = new JTPlugInUsuariosGrupos();
            JTEEUSUARIOSGRUPOS loUsuariosGruposBD = new JTEEUSUARIOSGRUPOS(moServer);
            loUsuariosGruposBD.recuperarTodosNormalSinCache();
            if (loUsuariosGruposBD.moList.moveFirst()) {
                do {
                    moUsuGrupos.moList.addNew();
                    moUsuGrupos.getCODIGOGRUPO().setValue(loUsuariosGruposBD.getCODIGOGRUPO().getString());
                    moUsuGrupos.getCODIGOUSUARIO().setValue(loUsuariosGruposBD.getCODIGOUSUARIO().getString());
                    moUsuGrupos.moList.update(false);
                } while (loUsuariosGruposBD.moList.moveNext());
            }
        }
        return moUsuGrupos;
    }

    @Override
    public synchronized JTPlugInGrupos getGrupos() throws Exception {
        if (moGrupos == null && !mbAnular) {
            moGrupos = new JTPlugInGrupos();
            moGruposBD = new JTEEUGRUPOS(moServer);
            moGruposBD.recuperarTodosNormalSinCache();
            if (moGruposBD.moList.moveFirst()) {
                do {
                    moGrupos.moList.addNew();
                    moGrupos.getCODIGOGRUPO().setValue(moGruposBD.getCODIGOGRUPO().getString());
                    moGrupos.getNOMBRE().setValue(moGruposBD.getNOMBRE().getString());
                    moGrupos.moList.update(false);
                } while (moGruposBD.moList.moveNext());
            }
        }
        return moGrupos;
    }

    @Override
    public synchronized JTPlugInUsuarios getUsuarios() throws Exception {
        if (moUsuarios == null && !mbAnular) {
            moUsuarios = new JTPlugInUsuarios();
            moUsuariosBD = new JTEEUSUARIOS(moServer);
            moUsuariosBD.recuperarTodosNormalSinCache();
            if (moUsuariosBD.moList.moveFirst()) {
                do {
                    moUsuarios.moList.addNew();
                    moUsuarios.getCODIGOUSUARIO().setValue(moUsuariosBD.getCODIGOUSUARIO().getString());
                    moUsuarios.getACTIVOSN().setValue(moUsuariosBD.getACTIVO().getString());
                    moUsuarios.getNOMBRE().setValue(moUsuariosBD.getLOGIN().getString());
                    moUsuarios.getNOMBRECOMPLETO().setValue(moUsuariosBD.getNOMBRE().getString());
                    moUsuarios.getPASSWORD().setValue(moUsuariosBD.getCLAVE().getString());
                    moUsuarios.moList.update(false);
                } while (moUsuariosBD.moList.moveNext());
            }
        }
        return moUsuarios;
    }

    @Override
    public synchronized JTPlugInListaPermisos getListaPermisosGrupo(String psGrupo) throws Exception {
        if (moListaPermisosGrupo == null && !mbAnular) {
            moListaPermisosGrupo = new JTPlugInListaPermisos();
            JTEEUGRUPOLISTAPERMISOS loGruposListaPermisosBD = new JTEEUGRUPOLISTAPERMISOS(moServer);
            loGruposListaPermisosBD.recuperarTodosNormalSinCache();
            if (loGruposListaPermisosBD.moList.moveFirst()) {
                do {
                    moListaPermisosGrupo.moList.addNew();
                    moListaPermisosGrupo.getCODIGOGRUPOUSUARIO().setValue(loGruposListaPermisosBD.getCODIGOGRUPO().getString());
                    if (loGruposListaPermisosBD.getACCION().getString().equals(mcsAccionNula)) {
                        moListaPermisosGrupo.getACCION().setValue("");
                    } else {
                        moListaPermisosGrupo.getACCION().setValue(loGruposListaPermisosBD.getACCION().getString());
                    }
                    moListaPermisosGrupo.getACTIVOSN().setValue(loGruposListaPermisosBD.getACTIVOSN().getString());
                    moListaPermisosGrupo.getOBJETO().setValue(loGruposListaPermisosBD.getOBJETO().getString());


                    moListaPermisosGrupo.moList.update(false);
                    moListaPermisosGrupo.moList.moFila().setTipoModif(JListDatos.mclNada);
                } while (loGruposListaPermisosBD.moList.moveNext());
            }
            moListaPermisosGrupo.moList.ordenar(new int[]{
                        moListaPermisosGrupo.lPosiOBJETO,
                        moListaPermisosGrupo.lPosiACCION,
                        moListaPermisosGrupo.lPosiCODIGOGRUPOUSUARIO
                    });

        }


        JTPlugInListaPermisos loAux = new JTPlugInListaPermisos();
        try {
            moListaPermisosGrupo.moList.getFiltro().addCondicionAND(
                    JListDatos.mclTIgual,
                    moListaPermisosGrupo.lPosiCODIGOGRUPOUSUARIO,
                    psGrupo);
            moListaPermisosGrupo.moList.filtrar();
            loAux.moList = JUtilTabla.clonarFilasListDatos(moListaPermisosGrupo.moList, false);
        } finally {
            moListaPermisosGrupo.moList.getFiltro().clear();
            moListaPermisosGrupo.moList.filtrarNulo();
        }



        return loAux;
    }

    @Override
    public synchronized JTPlugInListaPermisos getListaPermisosUsuario(String psUsu) throws Exception {
        if (moListaPermisosUsuario == null && !mbAnular) {
            moListaPermisosUsuario = new JTPlugInListaPermisos();
            JTEEUSUARIOSLISTAPERMISOS loUsuariosListaPermisosBD = new JTEEUSUARIOSLISTAPERMISOS(moServer);
            loUsuariosListaPermisosBD.recuperarTodosNormalSinCache();
            if (loUsuariosListaPermisosBD.moList.moveFirst()) {
                do {
                    moListaPermisosUsuario.moList.addNew();
                    moListaPermisosUsuario.getCODIGOGRUPOUSUARIO().setValue(loUsuariosListaPermisosBD.getCODIGOUSUARIO().getString());
                    if (loUsuariosListaPermisosBD.getACCION().getString().equals(mcsAccionNula)) {
                        moListaPermisosUsuario.getACCION().setValue("");
                    } else {
                        moListaPermisosUsuario.getACCION().setValue(loUsuariosListaPermisosBD.getACCION().getString());
                    }
                    moListaPermisosUsuario.getACTIVOSN().setValue(loUsuariosListaPermisosBD.getACTIVOSN().getString());
                    moListaPermisosUsuario.getOBJETO().setValue(loUsuariosListaPermisosBD.getOBJETO().getString());


                    moListaPermisosUsuario.moList.update(false);
                    moListaPermisosUsuario.moList.moFila().setTipoModif(JListDatos.mclNada);
                } while (loUsuariosListaPermisosBD.moList.moveNext());
            }
        }
        JTPlugInListaPermisos loAux = new JTPlugInListaPermisos();
        try {
            moListaPermisosUsuario.moList.getFiltro().addCondicionAND(
                    JListDatos.mclTIgual,
                    moListaPermisosUsuario.lPosiCODIGOGRUPOUSUARIO,
                    psUsu);
            moListaPermisosUsuario.moList.filtrar();
            loAux.moList = JUtilTabla.clonarFilasListDatos(moListaPermisosUsuario.moList, false);
        } finally {
            moListaPermisosUsuario.moList.getFiltro().clear();
            moListaPermisosUsuario.moList.filtrarNulo();
        }

        return loAux;
    }

    @Override
    public synchronized JTPlugInListaPermisos getListaPermisosBase() throws Exception {
        if (moPermisosBase == null) {
            mbAnular = true;
            try {
                moPermisosBase=getListaPermisosBasePersonalizado();
            } finally {
                mbAnular = false;
            }

            addPermiso(moPermisosBase, mcsBaseDatos, mcsBaseDatos, mcsEscrituraSN);


            if (moServer == null) {
                setServer(JGUIxConfigGlobalModelo.getInstancia().getPlugInFactoria().getPlugInContexto().getServer());
            }
            moPermisosBase.moList.ordenar(new int[]{moPermisosBase.lPosiOBJETO, moPermisosBase.lPosiACCION});

        }


        return moPermisosBase;
    }

    @Override
    public String getSuperUsuario() {
        return "0";
    }

    @Override
    public void guardarGrupo(IFilaDatos poFila) throws Exception {
        //guardamos en la cache
        guardarDato(poFila, moGrupos.moList);
        JTEEUGRUPOS loObjBD = new JTEEUGRUPOS(moServer);
        loObjBD.getCODIGOGRUPO().setValue(
                poFila.msCampo(moGrupos.lPosiCODIGOGRUPO));
        loObjBD.getNOMBRE().setValue(
                poFila.msCampo(moGrupos.lPosiNOMBRE));
        //guardamos en BD
        IFilaDatos loFila = loObjBD.moList.getFields().moFilaDatos();
        loFila.setTipoModif(poFila.getTipoModif());
        guardarDato(loFila, moGruposBD.moList);
    }

    @Override
    public void guardarUsuario(IFilaDatos poFila) throws Exception {
        //guardamos en la cache
        guardarDato(poFila, moUsuarios.moList);
        JTEEUSUARIOS loObjBD = JTEEUSUARIOS.getTabla(poFila.msCampo(moUsuarios.lPosiCODIGOUSUARIO), moServer);
        loObjBD.getCODIGOUSUARIO().setValue(
                poFila.msCampo(moUsuarios.lPosiCODIGOUSUARIO));
        loObjBD.getLOGIN().setValue(
                poFila.msCampo(moUsuarios.lPosiNOMBRE));
        loObjBD.getNOMBRE().setValue(
                poFila.msCampo(moUsuarios.lPosiNOMBRECOMPLETO));

        loObjBD.getACTIVO().setValue(
                poFila.msCampo(moUsuarios.lPosiACTIVOSN));
        loObjBD.getCLAVE().setValue(
                poFila.msCampo(moUsuarios.lPosiPASSWORD));
        //guardamos en BD
        IFilaDatos loFila = loObjBD.moList.getFields().moFilaDatos();
        loFila.setTipoModif(poFila.getTipoModif());
        guardarDato(loFila, moUsuariosBD.moList);
    }

    @Override
    public void guardareUsuariosGrupos(JTPlugInUsuariosGrupos poUsuariosGrupos) throws Exception {
        moUsuGrupos = poUsuariosGrupos;
        JTEEUSUARIOSGRUPOS loUsuariosGruposBD = new JTEEUSUARIOSGRUPOS(moServer);
        loUsuariosGruposBD.recuperarTodosNormalSinCache();
        //borramos todos
        while (loUsuariosGruposBD.moList.moveFirst()) {
            IResultado loResult = loUsuariosGruposBD.moList.borrar(false);
            if (!loResult.getBien()) {
                throw new Exception(loResult.getMensaje());
            }
        }
        //a?adimos todos
        if (moUsuGrupos.moList.moveFirst()) {
            do {
                loUsuariosGruposBD.moList.addNew();
                loUsuariosGruposBD.getCODIGOGRUPO().setValue(moUsuGrupos.getCODIGOGRUPO().getString());
                loUsuariosGruposBD.getCODIGOUSUARIO().setValue(moUsuGrupos.getCODIGOUSUARIO().getString());
                IResultado loResult = loUsuariosGruposBD.moList.update(false);
                if (!loResult.getBien()) {
                    throw new Exception(loResult.getMensaje());
                }
            } while (moUsuGrupos.moList.moveNext());
        }
        JActualizarConj loAct = new JActualizarConj("", "", "");
        loAct.crearUpdateAPartirList(loUsuariosGruposBD.moList);

        IResultado loResult = moServer.ejecutarServer(loAct);
        if (!loResult.getBien()) {
            throw new Exception(loResult.getMensaje());
        }

    }

    @Override
    public void guardarListaPermisosGrupo(String psGrupo, JTPlugInListaPermisos poPermisos) throws Exception {
        moListaPermisosGrupo = null;
        JTEEUGRUPOLISTAPERMISOS loGruposListaPermisosBD = new JTEEUGRUPOLISTAPERMISOS(moServer);
        loGruposListaPermisosBD.recuperarFiltradosNormal(
                new JListDatosFiltroElem(
                JListDatos.mclTIgual,
                JTEEUGRUPOLISTAPERMISOS.lPosiCODIGOGRUPO,
                psGrupo),
                false);
        //borramos todos
        while (loGruposListaPermisosBD.moList.moveFirst()) {
            IResultado loResult = loGruposListaPermisosBD.moList.borrar(false);
            if (!loResult.getBien()) {
                throw new Exception(loResult.getMensaje());
            }
        }

        //a?adimos todos
        if (poPermisos.moList.moveFirst()) {
            do {
                String lsAccion = "";
                if (poPermisos.getACCION().isVacio()) {
                    lsAccion = (mcsAccionNula);
                } else {
                    lsAccion = (poPermisos.getACCION().getString());
                }
                if (!loGruposListaPermisosBD.moList.buscar(
                        JListDatos.mclTIgual,
                        new int[]{
                            loGruposListaPermisosBD.lPosiCODIGOGRUPO,
                            loGruposListaPermisosBD.lPosiOBJETO,
                            loGruposListaPermisosBD.lPosiACCION
                        },
                        new String[]{
                            poPermisos.getCODIGOGRUPOUSUARIO().getString(),
                            poPermisos.getOBJETO().getString(),
                            lsAccion
                        })) {
                    loGruposListaPermisosBD.moList.addNew();
                    loGruposListaPermisosBD.getCODIGOGRUPO().setValue(poPermisos.getCODIGOGRUPOUSUARIO().getString());
                    loGruposListaPermisosBD.getOBJETO().setValue(poPermisos.getOBJETO().getString());
                    loGruposListaPermisosBD.getACCION().setValue(lsAccion);
                    loGruposListaPermisosBD.getACTIVOSN().setValue(poPermisos.getACTIVOSN().getString());
                    IResultado loResult = loGruposListaPermisosBD.moList.update(false);
                    if (!loResult.getBien()) {
                        throw new Exception(loResult.getMensaje());
                    }
                }
            } while (poPermisos.moList.moveNext());
        }
        JActualizarConj loAct = new JActualizarConj("", "", "");
        loAct.crearUpdateAPartirList(loGruposListaPermisosBD.moList);

        IResultado loResult = moServer.ejecutarServer(loAct);
        if (!loResult.getBien()) {
            throw new Exception(loResult.getMensaje());
        }
    }

    @Override
    public void guardarListaPermisosUsuario(String psUsu, JTPlugInListaPermisos poPermisos) throws Exception {
        moListaPermisosUsuario = null;
        JTEEUSUARIOSLISTAPERMISOS loUsuariosListaPermisosBD = new JTEEUSUARIOSLISTAPERMISOS(moServer);
        loUsuariosListaPermisosBD.recuperarFiltradosNormal(
                new JListDatosFiltroElem(
                JListDatos.mclTIgual,
                JTEEUSUARIOSLISTAPERMISOS.lPosiCODIGOUSUARIO,
                psUsu),
                false);
        //borramos todos
        while (loUsuariosListaPermisosBD.moList.moveFirst()) {
            IResultado loResult = loUsuariosListaPermisosBD.moList.borrar(false);
            if (!loResult.getBien()) {
                throw new Exception(loResult.getMensaje());
            }
        }

        //a?adimos todos
        if (poPermisos.moList.moveFirst()) {
            do {
                loUsuariosListaPermisosBD.moList.addNew();
                loUsuariosListaPermisosBD.getCODIGOUSUARIO().setValue(poPermisos.getCODIGOGRUPOUSUARIO().getString());
                if (poPermisos.getACCION().isVacio()) {
                    loUsuariosListaPermisosBD.getACCION().setValue(mcsAccionNula);
                } else {
                    loUsuariosListaPermisosBD.getACCION().setValue(poPermisos.getACCION().getString());
                }
                loUsuariosListaPermisosBD.getACTIVOSN().setValue(poPermisos.getACTIVOSN().getString());
                loUsuariosListaPermisosBD.getOBJETO().setValue(poPermisos.getOBJETO().getString());
                IResultado loResult = loUsuariosListaPermisosBD.moList.update(false);
                if (!loResult.getBien()) {
                    throw new Exception(loResult.getMensaje());
                }
            } while (poPermisos.moList.moveNext());
        }
        JActualizarConj loAct = new JActualizarConj("", "", "");
        loAct.crearUpdateAPartirList(loUsuariosListaPermisosBD.moList);

        IResultado loResult = moServer.ejecutarServer(loAct);
        if (!loResult.getBien()) {
            throw new Exception(loResult.getMensaje());
        }

    }

    private void guardarDato(IFilaDatos poFila, JListDatos poList) throws Exception {
        String[] lasCampos = new String[poList.getFields().malCamposPrincipales().length];
        for (int i = 0; i < lasCampos.length; i++) {
            lasCampos[i] = poFila.msCampo(poList.getFields().malCamposPrincipales()[i]);
        }
        boolean lbEncon = poList.buscar(
                JListDatos.mclTIgual,
                poList.getFields().malCamposPrincipales(),
                lasCampos);
        switch (poFila.getTipoModif()) {
            case JListDatos.mclBorrar:
                if (lbEncon) {
                    IResultado loResult = poList.borrar(true);
                    if (!loResult.getBien()) {
                        throw new Exception(loResult.getMensaje());
                    }
                } else {
                    throw new Exception("Registro no encontrado");
                }
                break;
            case JListDatos.mclEditar:
                if (lbEncon) {
                    poList.getFields().cargar(poFila);
                    IResultado loResult = poList.update(true);
                    if (!loResult.getBien()) {
                        throw new Exception(loResult.getMensaje());
                    }
                } else {
                    throw new Exception("Registro no encontrado");
                }
                break;
            case JListDatos.mclNuevo:
                poList.addNew();
                poList.getFields().cargar(poFila);
                IResultado loResult = poList.update(true);
                if (!loResult.getBien()) {
                    throw new Exception(loResult.getMensaje());
                }
                break;
            default:
                throw new Exception("Tipo modificación incorrecto");
        }
    }

    public void aplicarPermisosServidor(IServerServidorDatos poServer, String psCodUsuario) throws Exception {
        setServer(poServer);
        
        //recuperamos los permisos de la base datos, para ver si la BD es de solo lectura
        if(msCodUsuario==null || !msCodUsuario.equals(psCodUsuario)){
            JTPlugInListaPermisos loPermisos =
                    JTPlugInListaPermisosUtil.getPermisosDeObjeto(
                    this,
                    psCodUsuario,
                    mcsBaseDatos);
            mbSoloLectura = false;
            if (loPermisos.moList.buscar(JListDatos.mclTIgual, loPermisos.lPosiACCION, mcsEscrituraSN)) {
                poServer.getParametros().setSoloLectura(!loPermisos.getACTIVOSN().getBoolean());
                mbSoloLectura = !loPermisos.getACTIVOSN().getBoolean();
            }
            msCodUsuario=psCodUsuario;
        }else{
            poServer.getParametros().setSoloLectura(mbSoloLectura);
        }
        
    }

    public void addPermiso(String psObjeto, String psCaption, String psAccion) throws Exception {
        addPermiso(psObjeto, psCaption, psAccion, true);
    }

    public void addPermiso(String psObjeto, String psCaption, String psAccion, boolean pbActivo) throws Exception {
        JTPlugInListaPermisos loPermisosBase = getListaPermisosBase();
        addPermiso(loPermisosBase, psObjeto, psCaption, psAccion, pbActivo);

    }

    protected void addPermiso(JTPlugInListaPermisos poPermisos, String psObjeto, String psCaption, String psAccion) throws ECampoError {
        addPermiso(poPermisos, psObjeto, psCaption, psAccion, true);
    }

    protected void addPermiso(JTPlugInListaPermisos poPermisos, String psObjeto, String psCaption, String psAccion, boolean pbActivo) throws ECampoError {
        poPermisos.moList.addNew();
        poPermisos.getOBJETO().setValue(psObjeto);
        poPermisos.getCAPTIONOBJETO().setValue(psCaption);
        poPermisos.getACCION().setValue(psAccion);
        poPermisos.getACTIVOSN().setValue(pbActivo);
        poPermisos.moList.update(false);

    }

    public boolean isActivo(String psCodUser,String psGrupo, String psAccion) throws Exception {
        boolean lbActivo = false;
        if(moServer!=null){
            JTPlugInListaPermisos loListaPer =
                    JTPlugInListaPermisosUtil.getPermisosDeObjeto(
                    this,
                    psCodUser,
                    psGrupo);
            if (loListaPer.moList.buscar(JListDatos.mclTIgual,
                    loListaPer.lPosiACCION,
                    psAccion)) {
                lbActivo = loListaPer.getACTIVOSN().getBoolean();
            }
        }
        return lbActivo;
    }

    public abstract JTPlugInListaPermisos getListaPermisosBasePersonalizado() throws Exception;

}
