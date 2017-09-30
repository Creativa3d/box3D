/*
 * JSincronizacionCrear.java
 *
 * Created on 2 de octubre de 2008, 20:38
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utilesSincronizacion.sincronizacion;

import ListDatos.IResultado;
import ListDatos.IServerServidorDatos;
import ListDatos.JListDatos;
import ListDatos.estructuraBD.JActualizarEstructura;
import ListDatos.estructuraBD.JFieldDef;
import ListDatos.estructuraBD.JTableDef;
import ListDatos.estructuraBD.JTableDefs;
import utiles.IListaElementos;
import utiles.JDepuracion;
import utilesBD.comparadorBD.IDiferencia;
import utilesBD.comparadorBD.JComparadorBD;
import utilesGUI.procesar.IProcesoAccion;
import utilesGUI.procesar.JProcesoAccionParam;
import utilesGUIx.JGUIxConfigGlobalModelo;
import utilesGUIx.formsGenericos.IMostrarPantalla;
import utilesSincronizacion.tablas.JTTABLASINCRONIZACIONBORRADOS;
import utilesSincronizacion.tablas.JTTABLASINCRONIZACIONGENERAL;
import utilesSincronizacion.tablasExtend.JTEETABLASINCRONIZACIONGENERAL;

public class JSincronizacionCrear implements IProcesoAccion {
    
    private int lTabla=0;
    private int lTablas=-1;
    private String msRegistroActual="Recopilando Datos";
    private JProcesoAccionParam moParametros = new JProcesoAccionParam();
    private boolean mbFin=false;
    private boolean mbCancelado=false;
    
    private IServerServidorDatos moServerServidor;
    private StringBuilder moMensajes = new StringBuilder();
    
    /** Creates a new instance of JSincronizar */
    public JSincronizacionCrear(IServerServidorDatos poServerServidor) {
        moServerServidor = poServerServidor;
    }
    private void addMensaje(Exception e){
        JDepuracion.anadirTexto(getClass().getName(), e);
        moMensajes.append(e.toString());
        moMensajes.append('\n');
    }
    
    private void addMensaje(String psMensaje){
        JDepuracion.anadirTexto(getClass().getName(), psMensaje);
        moMensajes.append(psMensaje);
        moMensajes.append('\n');
        
    }
    public void sincronizar() throws Exception{
        igualarEstructura();

        JTEETABLASINCRONIZACIONGENERAL loTablaGeneral = new JTEETABLASINCRONIZACIONGENERAL(moServerServidor);
        loTablaGeneral.inicializar();
        
        msRegistroActual = "Tuneando Tablas";
        JTableDefs loTablas = moServerServidor.getTableDefs();
        lTablas  = loTablas.getListaTablas().size();
        for(lTabla = 0 ; lTabla < loTablas.getListaTablas().size(); lTabla++){
            JTableDef loTablaServidor = (JTableDef)loTablas.getListaTablas().get(lTabla);
            if(loTablaServidor.getTipo()==loTablaServidor.mclTipoTabla &&
               !loTablaServidor.getNombre().equalsIgnoreCase(JTTABLASINCRONIZACIONBORRADOS.msCTabla)  &&  
               !loTablaServidor.getNombre().equalsIgnoreCase(JTTABLASINCRONIZACIONGENERAL.msCTabla)     
               ){
                msRegistroActual = "Tuneando Tablas " + loTablaServidor.getNombre();
                sincronizarTabla(
                        loTablaServidor, 
                        Integer.valueOf(loTablaGeneral.getAtributo(JSincronizar.mcsCampoNumeroTransaccion)).intValue() );
            }else{
                addMensaje("Se excluye la tabla " + loTablaServidor.getNombre() + " por no ser una tabla normal");
            }
        }
    }

    private void sincronizarTabla(JTableDef loTablaServidor, int plNumTransac) throws CloneNotSupportedException, Exception {
        JFieldDef loCampo = loTablaServidor.getCampos().get(JSincronizar.mcsCampoNumeroTransaccion);
        if(loCampo==null){
            loCampo = new JFieldDef(
                    JListDatos.mclTipoNumero,
                    JSincronizar.mcsCampoNumeroTransaccion, 
                    "",
                    false);
            moServerServidor.modificarEstructura(
                    new JActualizarEstructura(
                        loCampo, 
                        loTablaServidor, 
                        JListDatos.mclNuevo,
                        "","","")
                    );
            loTablaServidor.getCampos().addField(loCampo);
            JListDatos loList = loTablaServidor.getListDatos();
            loList.moServidor=moServerServidor;
            loList.recuperarDatosNoCacheNormal(loList.getSelect());
            if(loList.moveFirst()){
                do{
                    loList.getFields().get(JSincronizar.mcsCampoNumeroTransaccion).setValue(plNumTransac+1);
                    IResultado loResult = loList.update(true);
                    if(!loResult.getBien()){
                        JDepuracion.anadirTexto(
                                JDepuracion.mclWARNING,
                                getClass().getName(), 
                                loResult.getMensaje()
                                );
                    }
                }while(loList.moveNext());
            }
            
        }
    }
    
    private JTableDefs getTablasOrigen(){
        JTableDefs loTablasOrigen = new JTableDefs();
        loTablasOrigen.add(new JTableDef(
                JTTABLASINCRONIZACIONBORRADOS.msCTabla, JTableDef.mclTipoTabla, 
                JTTABLASINCRONIZACIONBORRADOS.masNombres, JTTABLASINCRONIZACIONBORRADOS.malCamposPrincipales, 
                JTTABLASINCRONIZACIONBORRADOS.malTipos, JTTABLASINCRONIZACIONBORRADOS.malTamanos)
                );
        loTablasOrigen.add(new JTableDef(
                JTTABLASINCRONIZACIONGENERAL.msCTabla, JTableDef.mclTipoTabla, 
                JTTABLASINCRONIZACIONGENERAL.masNombres, JTTABLASINCRONIZACIONGENERAL.malCamposPrincipales, 
                JTTABLASINCRONIZACIONGENERAL.malTipos, JTTABLASINCRONIZACIONGENERAL.malTamanos)
                );
        
        return loTablasOrigen;
    }

    private void igualarEstructura() throws Exception {
        msRegistroActual = "Recuperando estructura";

        
        JComparadorBD moCom = new JComparadorBD(
                getTablasOrigen(),
                moServerServidor.getTableDefs(), 
                null, 
                moServerServidor
                );
        msRegistroActual = "Recuperando diferencias de estructura";
        moCom.ejecutar();
        IListaElementos loLista = moCom.getDiferencias();

        msRegistroActual = "Arreglando diferencias de estructura TABLAS";
        //tablas
        for(int i = 0 ; i < loLista.size(); i++){
            IDiferencia loDiferencia = (IDiferencia)loLista.get(i);
            if(loDiferencia.getTipoEstructura() == IDiferencia.mclTabla &&
               (loDiferencia.getTipoModificacion()==  IDiferencia.mclTipoModificacion ||
                loDiferencia.getTipoModificacion()==  IDiferencia.mclTipoNoExiste) ){
                try {
                    loDiferencia.arreglarDiferencia();
                } catch (Exception ex) {
                    JDepuracion.anadirTexto(getClass().getName(), ex);
                    addMensaje(ex);
                }
            }
        }
        msRegistroActual = "Arreglando diferencias de estructura CAMPOS";
        //campos
        for(int i = 0 ; i < loLista.size(); i++){
            IDiferencia loDiferencia = (IDiferencia)loLista.get(i);
            if(loDiferencia.getTipoEstructura() == IDiferencia.mclCampo &&
               (loDiferencia.getTipoModificacion()==  IDiferencia.mclTipoModificacion ||
                loDiferencia.getTipoModificacion()==  IDiferencia.mclTipoNoExiste) ){
                try {
                    loDiferencia.arreglarDiferencia();
                } catch (Exception ex) {
                    JDepuracion.anadirTexto(getClass().getName(), ex);
                    addMensaje(ex);
                }
            }
        }
    }

    public JProcesoAccionParam getParametros() {
        return moParametros;
    }

    public String getTitulo() {
        return "Sincronizando";
    }

    public int getNumeroRegistros() {
        return lTablas;
    }

    public void procesar() throws Throwable {
        sincronizar();
        mbFin=true;
    }

    public int getNumeroRegistro() {
        return lTabla;
    }

    public String getTituloRegistroActual() {
        return msRegistroActual;
    }

    public boolean isFin() {
        return mbFin;
    }

    public void setCancelado(boolean pbCancelado) {
        mbCancelado = pbCancelado;
    }

    public void mostrarMensaje(String psMensaje) {
        JGUIxConfigGlobalModelo.getInstancia().getMostrarPantalla().mensaje(null, psMensaje, IMostrarPantalla.mclMensajeInformacion, null);
    }

    public void mostrarError(Throwable e) {
        JGUIxConfigGlobalModelo.getInstancia().getMostrarPantalla().mensajeErrorYLog(null, e, null);
    }

    public void finalizar() {
    }
}
