/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesGUIx.aplicacion;

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


/**
 * Clase proxy del servidor datos, pero a traves de jdatosgeneralesaplicacion
 * , por si cambia en jdatosgeneralesaplicacion esta clase sigue funcionando
 * @author eduardo
 */
public class JServerServidorDatosProxy implements IServerServidorDatos {
    private final JDatosGeneralesAplicacionModelo moDatosGene;

    public JServerServidorDatosProxy(JDatosGeneralesAplicacionModelo poDatosGene){
        moDatosGene=poDatosGene;
    }
    
    public IResultado borrar(String psSelect, String psTabla, JFieldDefs poCampos) {
        return moDatosGene.getServer().borrar(psSelect, psTabla, poCampos);
    }

    public IResultado modificar(String psSelect, String psTabla, JFieldDefs poCampos) {
        return moDatosGene.getServer().modificar(psSelect, psTabla, poCampos);
    }

    public IResultado anadir(String psSelect, String psTabla, JFieldDefs poCampos) {
        return moDatosGene.getServer().anadir(psSelect, psTabla, poCampos);
    }

    public IResultado modificarEstructura(ISelectEjecutarSelect poEstruc) {
        return moDatosGene.getServer().modificarEstructura(poEstruc);
                
    }

    public IResultado ejecutarSQL(ISelectEjecutarSelect poEstruc) {
        return moDatosGene.getServer().modificarEstructura(poEstruc);
    }

    public IResultado actualizar(String psSelect, JActualizar poActualizar) {
        return moDatosGene.getServer().actualizar(psSelect, poActualizar);
    }

    public IResultado ejecutarServer(IServerEjecutar poEjecutar) {
        return moDatosGene.getServer().ejecutarServer(poEjecutar);
    }

    public void recuperarDatos(JListDatos v, JSelect poSelect, String psTabla, boolean pbPasarACache, boolean pbRefrescarACache, int plOpciones) throws Exception {
        moDatosGene.getServer().recuperarDatos(v, poSelect, psTabla, pbPasarACache, pbRefrescarACache, plOpciones);
    }

    public void clearCache() {
        moDatosGene.getServer().clearCache();
    }

    public void actualizarDatosCacheConj(IListaElementos poResult, String psSelect) {
        moDatosGene.getServer().actualizarDatosCacheConj(poResult, psSelect);
    }

    public JListDatos getEnCache(String psSelect) {
        return moDatosGene.getServer().getEnCache(psSelect);
    }

    public JListDatos getEnCache(JListDatos poDatos) {
        return moDatosGene.getServer().getEnCache(poDatos);
    }

    public JListDatos borrarEnCache(String psSelect) {
        return moDatosGene.getServer().borrarEnCache(psSelect);
    }

    public void close() throws Exception {
        moDatosGene.getServer().close();
    }

    public void addFiltro(String psTabla, IListDatosFiltro poFiltro) {
        moDatosGene.getServer().addFiltro(psTabla, poFiltro);
    }

    public void addFiltro(int plTipo, String psNombre, String psTabla, IListDatosFiltro poFiltro) {
        moDatosGene.getServer().addFiltro(plTipo, psNombre, psTabla, poFiltro);
    }

    public IListDatosFiltro getFiltro(int i) {
        return moDatosGene.getServer().getFiltro(i);
    }

    public String getFiltroTabla(int i) {
        return moDatosGene.getServer().getFiltroTabla(i);
    }

    public void borrarFiltrosTodos() {
        moDatosGene.getServer().borrarFiltrosTodos();
    }
    public IListaElementos<JElementoFiltro> getFiltros(){
        return moDatosGene.getServer().getFiltros();
    }
    public JServerServidorDatosParam getParametros() {
        return moDatosGene.getServer().getParametros();
    }

    public JTableDefs getTableDefs() throws Exception {
        return moDatosGene.getServer().getTableDefs();
    }
    
}
