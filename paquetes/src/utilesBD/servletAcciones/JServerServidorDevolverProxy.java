/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesBD.servletAcciones;

import ListDatos.IListDatosFiltro;
import ListDatos.IResultado;
import ListDatos.ISelectEjecutarSelect;
import ListDatos.IServerEjecutar;
import ListDatos.IServerServidorDatos;
import ListDatos.JActualizar;
import ListDatos.JElementoFiltro;
import ListDatos.JListDatos;
import ListDatos.JSelect;
import ListDatos.JServerServidorDatosParam;
import ListDatos.estructuraBD.JFieldDefs;
import ListDatos.estructuraBD.JTableDefs;
import utiles.IListaElementos;
import utiles.JDepuracion;


public class JServerServidorDevolverProxy implements IServerServidorDatos {
    private final IAccionDevolver moDatosGene;
    private IServerServidorDatos moServer;

    public JServerServidorDevolverProxy(IAccionDevolver poDatosGene){
        moDatosGene=poDatosGene;
    }
    
    private synchronized IServerServidorDatos getServer(){
        
        try {
            if(moServer==null){
                moServer = moDatosGene.getServidor(false);
            }
            return moServer;
        } catch (Throwable ex) {
            JDepuracion.anadirTexto(JServerServidorDevolverProxy.class.getName(), ex);
            return null;
        }
    }
    
    @Override
    public IResultado borrar(String psSelect, String psTabla, JFieldDefs poCampos) {
        try{
            return getServer().borrar(psSelect, psTabla, poCampos);
        }finally{
            try {
                close();
            } catch (Exception ex) {
                JDepuracion.anadirTexto(JServerServidorDevolverProxy.class.getName(), ex);  
            }
        }
    }

    @Override
    public IResultado modificar(String psSelect, String psTabla, JFieldDefs poCampos) {
        try{
        return getServer().modificar(psSelect, psTabla, poCampos);
        }finally{
            try {
                close();
            } catch (Exception ex) {
                JDepuracion.anadirTexto(JServerServidorDevolverProxy.class.getName(), ex);  
            }
        }
    }

    @Override
    public IResultado anadir(String psSelect, String psTabla, JFieldDefs poCampos) {
        try{
        return getServer().anadir(psSelect, psTabla, poCampos);
        }finally{
            try {
                close();
            } catch (Exception ex) {
                JDepuracion.anadirTexto(JServerServidorDevolverProxy.class.getName(), ex);  
            }
        }
    }

    @Override
    public IResultado modificarEstructura(ISelectEjecutarSelect poEstruc) {
        try{
        return getServer().modificarEstructura(poEstruc);
        }finally{
            try {
                close();
            } catch (Exception ex) {
                JDepuracion.anadirTexto(JServerServidorDevolverProxy.class.getName(), ex);  
            }
        }
                
    }

    @Override
    public IResultado ejecutarSQL(ISelectEjecutarSelect poEstruc) {
        try{
        return getServer().modificarEstructura(poEstruc);
        }finally{
            try {
                close();
            } catch (Exception ex) {
                JDepuracion.anadirTexto(JServerServidorDevolverProxy.class.getName(), ex);  
            }
        }
    }

    @Override
    public IResultado actualizar(String psSelect, JActualizar poActualizar) {
        try{
        return getServer().actualizar(psSelect, poActualizar);
        }finally{
            try {
                close();
            } catch (Exception ex) {
                JDepuracion.anadirTexto(JServerServidorDevolverProxy.class.getName(), ex);  
            }
        }
    }

    @Override
    public IResultado ejecutarServer(IServerEjecutar poEjecutar) {
        try{
        return getServer().ejecutarServer(poEjecutar);
        }finally{
            try {
                close();
            } catch (Exception ex) {
                JDepuracion.anadirTexto(JServerServidorDevolverProxy.class.getName(), ex);  
            }
        }
    }

    public void recuperarDatos(JListDatos v, JSelect poSelect, String psTabla, boolean pbPasarACache, boolean pbRefrescarACache, int plOpciones) throws Exception {
        try{
        getServer().recuperarDatos(v, poSelect, psTabla, pbPasarACache, pbRefrescarACache, plOpciones);
        }finally{
            try {
                close();
            } catch (Exception ex) {
                JDepuracion.anadirTexto(JServerServidorDevolverProxy.class.getName(), ex);  
            }
        }
    }

    public void clearCache() {
        getServer().clearCache();
    }

    public void actualizarDatosCacheConj(IListaElementos poResult, String psSelect) {
        getServer().actualizarDatosCacheConj(poResult, psSelect);
    }

    public JListDatos getEnCache(String psSelect) {
        return getServer().getEnCache(psSelect);
    }


    @Override
    public synchronized void close() throws Exception {
        if(moServer!=null){
            moServer.close();
            moServer=null;
        }
    }


    public JListDatos getEnCache(JListDatos poDatos) {
        return getServer().getEnCache(poDatos);
    }
    public JListDatos borrarEnCache(String psSelect) {
        return getServer().borrarEnCache(psSelect);
    }
    public void addFiltro(String psTabla, IListDatosFiltro poFiltro) {
        getServer().addFiltro(psTabla, poFiltro);
    }

    public void addFiltro(int plTipo, String psNombre, String psTabla, IListDatosFiltro poFiltro) {
        getServer().addFiltro(plTipo, psNombre, psTabla, poFiltro);
    }

    public IListDatosFiltro getFiltro(int i) {
        return getServer().getFiltro(i);
    }

    public String getFiltroTabla(int i) {
        return getServer().getFiltroTabla(i);
    }

    public void borrarFiltrosTodos() {
        getServer().borrarFiltrosTodos();
    }
    public IListaElementos<JElementoFiltro> getFiltros(){
        return getServer().getFiltros();
    }
    public JServerServidorDatosParam getParametros() {
        return getServer().getParametros();
    }

    public JTableDefs getTableDefs() throws Exception {
        return getServer().getTableDefs();
    }
    
}