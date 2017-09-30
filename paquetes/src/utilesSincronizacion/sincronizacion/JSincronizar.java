/*
 * JSincronizar.java
 *
 * Created on 2 de octubre de 2008, 18:04
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package utilesSincronizacion.sincronizacion;

import utilesSincronizacion.sincronizacion.conflictos.JListaElementosConflictos;
import utilesSincronizacion.sincronizacion.conflictos.JConflicto;
import ListDatos.IResultado;
import ListDatos.IServerServidorDatos;
import ListDatos.JListDatos;
import ListDatos.JListDatosFiltroConj;
import ListDatos.JSelect;
import ListDatos.JXMLSelectMotor;
import ListDatos.estructuraBD.JFieldDef;
import ListDatos.estructuraBD.JFieldDefs;
import ListDatos.estructuraBD.JTableDef;
import ListDatos.estructuraBD.JTableDefs;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import utiles.IListaElementos;
import utiles.JConversiones;
import utiles.JDateEdu;
import utiles.JDepuracion;
import utilesBD.comparadorBD.IDiferencia;
import utilesBD.comparadorBD.JComparadorBD;
import utilesGUI.procesar.IProcesoAccion;
import utilesGUI.procesar.JProcesoAccionParam;
import utilesGUIx.JGUIxConfigGlobalModelo;
import utilesGUIx.formsGenericos.IMostrarPantalla;
import utilesSincronizacion.sincronizacion.conflictos.IFormConflictos;
import utilesSincronizacion.sincronizacion.consultas.JCConflictos;
import utilesSincronizacion.tablas.JTTABLASINCRONIZACIONBORRADOS;
import utilesSincronizacion.tablas.JTTABLASINCRONIZACIONGENERAL;
import utilesSincronizacion.tablasExtend.JTEETABLASINCRONIZACIONBORRADOS;
import utilesSincronizacion.tablasExtend.JTEETABLASINCRONIZACIONGENERAL;

public class JSincronizar implements IProcesoAccion {

    public static final String mcsCampoNumeroTransaccion = "NumeroTransacSincro";
    //seguimiento del proceso IProcesoAccion
    private int lTabla = 0;
    private int lTablas = -1;
    private String msRegistroActual = "Recopilando Datos";
    private JProcesoAccionParam moParametros = new JProcesoAccionParam();
    private boolean mbFin = false;
    private boolean mbCancelado = false;
    //servidores
    private IServerServidorDatos moServerCliente;
    private IServerServidorDatos moServerServidor;
    //mensajes varios
    private StringBuilder moMensajes = new StringBuilder();
    private StringBuilder moMensajesCompletos = new StringBuilder();
    //lista conflictos
    private JListaElementosConflictos moListaConlictos = new JListaElementosConflictos();
    //contadores
    private int mlActualizadosServidor = 0;
    private int mlBorradosServidor = 0;
    private int mlActualizadosCliente = 0;
    private int mlBorradosCliente = 0;
    public int mlConflictosResueltos = 0;
    int mlContadorPasada = 0;
    private int mlPasadasGenerales = 0;
    private boolean mbAMedio;
    private String msMensajeFinal;
    private boolean mbError = false;
    private JSincronizarParam moParam;

    /** Creates a new instance of JSincronizar */
    public JSincronizar(
            final IServerServidorDatos poServerCliente,
            final IServerServidorDatos poServerServidor,
            final JSincronizarParam poParam) {
        moServerCliente = poServerCliente;
        moServerServidor = poServerServidor;
        moParam = poParam;
    }

    private void addMensaje(Exception e) {
        JDepuracion.anadirTexto(getClass().getName(), e.toString());
        moMensajes.append(e.toString());
        moMensajes.append('\n');
    }

    public JSincronizarParam getParam() {
        return moParam;
    }

    private void addMensaje(String psMensaje) {
        JDepuracion.anadirTexto(getClass().getName(), psMensaje);
        moMensajes.append(psMensaje);
        moMensajes.append('\n');

    }

    private int getNumero(String psValor) {
        return (int) JConversiones.cdbl(psValor);
    }

    public void sincronizar() throws Throwable {
        igualarEstructura();

        msRegistroActual = "Sincronizando Nº Transaccciones";
        //Recuperamos valores y actualizamos NumeroTransaccion Servidor Y Cliente
        JTEETABLASINCRONIZACIONGENERAL loGeneralCliente = new JTEETABLASINCRONIZACIONGENERAL(moServerCliente);
        loGeneralCliente.inicializar();
        JTEETABLASINCRONIZACIONGENERAL loGeneralServidor = new JTEETABLASINCRONIZACIONGENERAL(moServerServidor);
        loGeneralServidor.inicializar();
        int lNumeroTransaccionCliente = getNumero(loGeneralCliente.getAtributo(mcsCampoNumeroTransaccion));
        int lNumeroTransaccionServidor = getNumero(loGeneralServidor.getAtributo(mcsCampoNumeroTransaccion));
        int lNumeroTransaccionMinima = (lNumeroTransaccionCliente > lNumeroTransaccionServidor ? lNumeroTransaccionServidor : lNumeroTransaccionCliente);
        int lNumeroTransaccionMaxima = (lNumeroTransaccionCliente > lNumeroTransaccionServidor ? lNumeroTransaccionCliente : lNumeroTransaccionServidor);

        loGeneralCliente.setAtributo(mcsCampoNumeroTransaccion, String.valueOf(lNumeroTransaccionMaxima + 1));
        loGeneralServidor.setAtributo(mcsCampoNumeroTransaccion, String.valueOf(lNumeroTransaccionMaxima + 1));
        try {

            msRegistroActual = "Sincronizando datos";
            JTableDefs loTablas = moServerServidor.getTableDefs();
            lTablas = loTablas.getListaTablas().size();
            mlContadorPasada = 1;
            //mientras se haya añadido un registro repetir el bucle (por los relacionados)
            while (mlContadorPasada > 0) {
                moMensajes.setLength(0);
                mbAMedio = false;
                mlContadorPasada = 0;
                for (lTabla = 0; lTabla < loTablas.getListaTablas().size() && !mbCancelado; lTabla++) {
                    JTableDef loTablaServidor = (JTableDef) loTablas.getListaTablas().get(lTabla);
                    JTableDef loTablaCliente = loTablaServidor;//se supone q despues de igualar estructura son exactamente iguales
                    if (loTablaServidor.getTipo() == loTablaServidor.mclTipoTabla
                            && !loTablaServidor.getNombre().equalsIgnoreCase(JTTABLASINCRONIZACIONBORRADOS.msCTabla)
                            && !loTablaServidor.getNombre().equalsIgnoreCase(JTTABLASINCRONIZACIONGENERAL.msCTabla)) {
                        msRegistroActual = "Sincronizando datos " + loTablaServidor.getNombre();
                        sincronizarTabla(loTablaServidor, loTablaCliente, lNumeroTransaccionMinima, lNumeroTransaccionMaxima);
                    } else {
                        addMensaje("Se excluye la tabla " + loTablaServidor.getNombre() + " por no ser una tabla normal");
                    }
                }
                mlPasadasGenerales++;
                //si no ha habido ninguna modificacion
                if (mlContadorPasada == 0 && moListaConlictos.hayPendientes()) {
                    //se resuelven los conflictos
                    if (moParam.mbVisual) {
                        resolverComplictosVisual();
                    } else {
                        JCConflictos loConsultaConflictos = new JCConflictos();
                        loConsultaConflictos.setDatos(moListaConlictos, getParam().mbConflictosDefectoGanaCliente);
                        loConsultaConflictos.refrescar(false, false);
                        resolverConflictosReal(loConsultaConflictos);
                    }
                }
                moMensajesCompletos.append(moMensajes);
            }
            //si se ha quedado a medio se devuelve la situacion del numero transac. anterior hasta q se resuelva 
            if (mbAMedio || moListaConlictos.hayPendientes()) {
                moListaConlictos.actualizarTransacion(lNumeroTransaccionMaxima + 1);
            }
        } catch (Throwable e) {
            //si ha habido algun error no contralabe dejamos como estaban el numero de transac, para q no
            //se pierdan sincronizaciones pendientes
            loGeneralCliente.setAtributo(mcsCampoNumeroTransaccion, String.valueOf(lNumeroTransaccionCliente));
            loGeneralServidor.setAtributo(mcsCampoNumeroTransaccion, String.valueOf(lNumeroTransaccionServidor));
            throw new Exception(e);
        }
        mbFin = true;
    }

    private void guardarFicherosLogs(String psMensajeFinal) throws FileNotFoundException, IOException {
        JDateEdu loDate = new JDateEdu();
        guardarFicherosLog("logCompleto" + loDate.msFormatear("yyyyMMddHHmmss") + ".txt", moMensajesCompletos);
        guardarFicherosLog("logUltimaPasada" + loDate.msFormatear("yyyyMMddHHmmss") + ".txt", moMensajes);
        guardarFicherosLog("logMensajeFinal" + loDate.msFormatear("yyyyMMddHHmmss") + ".txt", new StringBuilder(psMensajeFinal));
    }

    private void guardarFicherosLog(String psNombre, StringBuilder poMensajes) throws FileNotFoundException, IOException {
        PrintWriter loOut = new PrintWriter(psNombre);
        try {
            loOut.write(poMensajes.toString());
        } finally {
            loOut.close();
        }
    }

    private void sincronizarTabla(JTableDef loTablaServidor, JTableDef loTablaCliente, int lNumeroTransaccionMinima, int lNumeroTransaccionMaxima) throws Exception {
        JListDatos loListServer = getDiferencias(moServerServidor, loTablaServidor, lNumeroTransaccionMinima, lNumeroTransaccionMaxima);
        JListDatos loListCliente = getDiferencias(moServerCliente, loTablaCliente, lNumeroTransaccionMinima, lNumeroTransaccionMaxima);
        JTEETABLASINCRONIZACIONBORRADOS loBorradosCliente = getTablaBorrados(moServerCliente, loTablaCliente.getNombre(), lNumeroTransaccionMinima, lNumeroTransaccionMaxima);
        JTEETABLASINCRONIZACIONBORRADOS loBorradosServidor = getTablaBorrados(moServerServidor, loTablaServidor.getNombre(), lNumeroTransaccionMinima, lNumeroTransaccionMaxima);

        sincronizarTablaADD_UPDATE(loTablaServidor, loTablaCliente, lNumeroTransaccionMinima, lNumeroTransaccionMaxima, loListServer, loListCliente, loBorradosServidor, loBorradosCliente);

        sincronizarTablaBorrados(moServerServidor, moServerCliente, loTablaServidor, lNumeroTransaccionMinima, lNumeroTransaccionMaxima, true, loBorradosServidor);
        sincronizarTablaBorrados(moServerCliente, moServerServidor, loTablaCliente, lNumeroTransaccionMinima, lNumeroTransaccionMaxima, false, loBorradosCliente);

    }

    public void resolverConflictosReal(JCConflictos poConsultaConflictos) {
        //se inicializa con los exitos prebvios 
        //(realmante cuando llega aqui no debe haber exitos previos, pero por si las moscas)
        int lContadorGeneralActu = mlContadorPasada;
        //cuando se llega a resolver los confilctos se supone q no ha habido exito en actualizaciones previas
        mlContadorPasada = 1;
        //mientras se haya actualizado algun conflicto con exito
        while (mlContadorPasada > 0) {
            //inicializamos el contador de actu. a 0
            mlContadorPasada = 0;
            //para cada conflicto
            if (poConsultaConflictos.moList.moveFirst()) {
                do {
                    //recuperamos el conflicto
                    JConflicto loConflicto = (JConflicto) moListaConlictos.get(poConsultaConflictos.getField(JCConflictos.lPosiIndiceBase).getInteger());
                    if (loConflicto.mbSinHacer) {
                        try {
                            //ejecutamos
                            loConflicto.ejecutar(poConsultaConflictos.getField(JCConflictos.lPosiGanaServidor).getBoolean());
                            //si llega aqui es q no ha tenido error la actu. por lo q incremen. las actu.
                            mlContadorPasada++;
                            lContadorGeneralActu++;
                            //borramos el conflicto pq ha tenido exito la actualizacion
                            loConflicto.mbSinHacer = false;
                        } catch (Exception e) {
                        }
                    }
                } while (poConsultaConflictos.moList.moveNext());
            }
        }
        mlConflictosResueltos = lContadorGeneralActu;
        mlContadorPasada = lContadorGeneralActu;
        moListaConlictos.borrarHechos();
    }

    private void resolverComplictosVisual() throws Exception {
        moParam.moConflictos.setDatos(moListaConlictos, this);
        moParam.moConflictos.setVisible(true);
    }

    private void sincronizarTablaBorrados(
            IServerServidorDatos poServer, IServerServidorDatos poServerDestino,
            JTableDef loTabla,
            int lNumeroTransaccionMinima, int lNumeroTransaccionMaxima,
            boolean pbDestinoCliente, JTEETABLASINCRONIZACIONBORRADOS loBorrados) throws Exception {
        if (loBorrados.moList.moveFirst()) {
            JFieldDefs loCamposC = loTabla.getCampos().Clone();
            do {
                //rellenamos los campos con el registro borrado
                rellenarCampos(loCamposC, loBorrados.getREGISTRO().getString());
                //comprobamos q el registro no existe en la BD Real
                JListDatos loListDatosAux = loTabla.getListDatos();
                loListDatosAux.moServidor = poServer;
                JSelect loSelect = loListDatosAux.getSelect();
                loSelect.getWhere().addCondicion(
                        JListDatosFiltroConj.mclAND,
                        JListDatos.mclTIgual,
                        loCamposC.malCamposPrincipales(),
                        loCamposC.masCamposPrincipales());
                loSelect.getWhere().inicializar(loListDatosAux.msTabla, loListDatosAux.getFields().malTipos(), loListDatosAux.getFields().msNombres());
                loListDatosAux.recuperarDatosNoCacheNormal(loSelect);
                //si no existe en el origen se procesa en el destino, si existe es pq se añadio despues y estara e n modificados
                if (!loListDatosAux.moveFirst()) {
                    //se recupera el registro en el destino
                    loListDatosAux = loTabla.getListDatos();
                    loListDatosAux.moServidor = poServerDestino;
                    int lCampoTransac = loListDatosAux.getFields().getIndiceDeCampo(mcsCampoNumeroTransaccion);
                    loSelect = loListDatosAux.getSelect();
                    loSelect.getWhere().addCondicion(
                            JListDatosFiltroConj.mclAND,
                            JListDatos.mclTIgual,
                            loCamposC.malCamposPrincipales(),
                            loCamposC.masCamposPrincipales());
                    loSelect.getWhere().inicializar(loListDatosAux.msTabla, loListDatosAux.getFields().malTipos(), loListDatosAux.getFields().msNombres());
                    loListDatosAux.recuperarDatosNoCacheNormal(loSelect);
                    if (loListDatosAux.moveFirst()) {
                        //si existe el registro en el destino, 2 casos
                        //caso 1: NO se ha modificado->se borra
                        if (loListDatosAux.getFields(lCampoTransac).getInteger() <= lNumeroTransaccionMinima) {
                            IResultado loResult = loListDatosAux.borrar(true);
                            if (!loResult.getBien()) {
                                addMensaje(loResult.getMensaje());
                                mbAMedio = true;
                            } else {
                                //incrementamos el numero de registros actualizados con exito
                                mlContadorPasada++;
                                if (pbDestinoCliente) {
                                    mlBorradosCliente++;
                                } else {
                                    mlBorradosServidor++;
                                }
                            }
                        } else {
                            //caso 2: SI se ha modificado->se añade a conflictos
                            if (mlPasadasGenerales == 0) {
                                if (pbDestinoCliente) {
                                    moListaConlictos.add(
                                            new JConflicto(
                                            loListDatosAux.msTabla,
                                            null,
                                            loListDatosAux.getFields().Clone(),
                                            poServer,
                                            poServerDestino));
                                } else {
                                    moListaConlictos.add(
                                            new JConflicto(
                                            loListDatosAux.msTabla,
                                            loListDatosAux.getFields().Clone(),
                                            null,
                                            poServerDestino,
                                            poServer));
                                }
                            }
                        }
                    } else {
                        //si no existe el registro en el destino -> puta madre
                    }
                }
            } while (loBorrados.moList.moveNext());
        }

    }

    private void rellenarCampos(JFieldDefs poCamposC, String psRegistro) throws Exception {
        JFieldDefs loCamposAux = JXMLSelectMotor.getCamposDesdeXML(psRegistro);
        for (int i = 0; i < poCamposC.size(); i++) {
            JFieldDef loCampo = loCamposAux.get(poCamposC.get(i).getNombre());
            if (loCampo != null) {
                poCamposC.get(i).setValue(loCampo.getString());
            }
        }
    }

    private JTEETABLASINCRONIZACIONBORRADOS getTablaBorrados(IServerServidorDatos poServer, String psNombreTabla, int lNumeroTransaccionMinima, int lNumeroTransaccionMaxima) throws Exception {
        JTEETABLASINCRONIZACIONBORRADOS loBorrados = new JTEETABLASINCRONIZACIONBORRADOS(poServer);

        JListDatosFiltroConj loFiltro = new JListDatosFiltroConj();
        loFiltro.addCondicion(
                JListDatosFiltroConj.mclAND,
                JListDatos.mclTIgual,
                JTEETABLASINCRONIZACIONBORRADOS.lPosiTABLA,
                psNombreTabla);
        loFiltro.addCondicion(
                JListDatosFiltroConj.mclAND,
                JListDatos.mclTMayorIgual,
                JTEETABLASINCRONIZACIONBORRADOS.lPosiNUMEROTRANSACSINCRO,
                String.valueOf(lNumeroTransaccionMinima + 1));
//        loFiltro.addCondicion(
//                JListDatosFiltroConj.mclAND,
//                JListDatos.mclTMenorIgual,
//                JTEETABLASINCRONIZACIONBORRADOS.lPosiNUMEROTRANSACSINCRO,
//                String.valueOf(lNumeroTransaccionMaxima+1)
//                );

        loBorrados.recuperarFiltradosNormal(loFiltro, false);

        return loBorrados;

    }

    //compara todos los campos menos el numeroTransac
    private boolean isIgualCamposMenosTransac(JFieldDefs poCamposC, JFieldDefs poCamposS) {
        boolean lbResult = poCamposC.size() == poCamposS.size();
        for (int i = 0; i < poCamposC.size() && lbResult; i++) {
            if (!poCamposC.get(i).toString().equals(
                    poCamposS.get(poCamposC.get(i).getNombre()).toString())) {
                if (!poCamposC.get(i).getNombre().equalsIgnoreCase(mcsCampoNumeroTransaccion)) {
                    lbResult = false;
                }
            }
        }
        return lbResult;
    }

    private void sincronizarTablaADD_UPDATE(
            JTableDef loTablaServidor, JTableDef loTablaCliente,
            int lNumeroTransaccionMinima, int lNumeroTransaccionMaxima,
            JListDatos loListServer, JListDatos loListCliente,
            JTEETABLASINCRONIZACIONBORRADOS loBorradosServidor, JTEETABLASINCRONIZACIONBORRADOS loBorradosCliente) throws Exception {
        JListDatos loBorradosClienteList = loBorradosCliente.getListDatosBorrados();
        JListDatos loBorradosServidorList = loBorradosServidor.getListDatosBorrados();

        //Cambios desde el servidor
        if (loListServer.moveFirst()) {
            //se supone q la estruc. de ambas es la misma, y es importante para el orden de los campos
            JListDatos loAux = loListCliente.Clone();
            JSelect loSelectAux = loAux.getSelect();
            do {
                boolean lbActualizar = true;
                //si se encuentra en el cliente es q ha habido un conflicto, ya q en estos listdatos solo
                //se recuperan las actualizaciones/añadidos
                if (loListCliente.buscar(
                        JListDatos.mclTIgual,
                        loListServer.getFields().malCamposPrincipales(),
                        loListServer.getFields().masCamposPrincipales())) {
                    lbActualizar = false;
                    //los conflictos se añaden todos en la pasada inicial
                    if ((mlPasadasGenerales == 0
                            || loListCliente.getFields().get(mcsCampoNumeroTransaccion).getInteger() > (lNumeroTransaccionMaxima + 1)
                            || loListServer.getFields().get(mcsCampoNumeroTransaccion).getInteger() > (lNumeroTransaccionMaxima + 1))
                            && !loListServer.getFields().moFilaDatos().toString().equals(loListCliente.getFields().moFilaDatos().toString())) {
                        if (!isIgualCamposMenosTransac(loListCliente.getFields(), loListServer.getFields())) {
                            moListaConlictos.add(
                                    new JConflicto(
                                    loListServer.msTabla,
                                    loListServer.getFields().Clone(), loListCliente.getFields().Clone(),
                                    moServerServidor, moServerCliente));
                        } else {
                            lbActualizar =
                                    loListCliente.getFields().get(mcsCampoNumeroTransaccion).getInteger()
                                    != loListServer.getFields().get(mcsCampoNumeroTransaccion).getInteger();
                        }
                    }
                }
                if (lbActualizar) {
                    //recuperamos el registro 
                    loAux.clear();
                    loSelectAux.getWhere().Clear();
                    loSelectAux.getWhere().addCondicion(
                            JListDatosFiltroConj.mclAND,
                            JListDatos.mclTIgual,
                            loAux.getFields().malCamposPrincipales(),
                            loListServer.getFields().masCamposPrincipales());
                    loSelectAux.getWhere().inicializar(loAux.msTabla, loAux.getFields().malTipos(), loAux.getFields().msNombres());
                    loAux.recuperarDatosNoCacheNormal(
                            loSelectAux);
                    //actualizamos si existe o añadimos si no existe
                    boolean lbContinuar = true;
                    if (!loAux.moveFirst()) {
                        loAux.addNew();
                        //si esta en la tabla de borrados es q hay un conflicto, q se añade en el proceso de borrados
                        if (loBorradosClienteList != null
                                && loBorradosClienteList.buscar(
                                JListDatos.mclTIgual,
                                loListServer.getFields().malCamposPrincipales(),
                                loListServer.getFields().masCamposPrincipales())) {
                            lbContinuar = false;
                        }
                    }
                    if (lbContinuar) {
                        loAux.getFields().cargar(loListServer.getFields().moFilaDatos());
                        IResultado loResult = loAux.update(true);
                        if (!loResult.getBien()) {
                            addMensaje(loResult.getMensaje());
                            mbAMedio = true;
                        } else {
                            //incrementamos el numero de registros actualizados con exito
                            mlContadorPasada++;
                            mlActualizadosCliente++;
                        }
                    }

                }
            } while (loListServer.moveNext());
        }
        //Cambios desde el cliente
        if (loListCliente.moveFirst()) {
            //se supone q la estruc. de ambas es la misma, y es importante para el orden de los campos
            JListDatos loAux = loListServer.Clone();
            JSelect loSelectAux = loAux.getSelect();
            do {
                //ya se ha añadido el conflicto en el bucle anterior (deben de existir en los dos)
                if (!loListServer.buscar(
                        JListDatos.mclTIgual,
                        loListCliente.getFields().malCamposPrincipales(),
                        loListCliente.getFields().masCamposPrincipales())) {
                    //actualizamos el servidor
                    //recuperamos el registro 
                    loAux.clear();
                    loSelectAux.getWhere().Clear();
                    loSelectAux.getWhere().addCondicion(
                            JListDatosFiltroConj.mclAND,
                            JListDatos.mclTIgual,
                            loAux.getFields().malCamposPrincipales(),
                            loListCliente.getFields().masCamposPrincipales());
                    loSelectAux.getWhere().inicializar(loAux.msTabla, loAux.getFields().malTipos(), loAux.getFields().msNombres());
                    loAux.recuperarDatosNoCacheNormal(
                            loSelectAux);
                    //actualizamos si existe o añadimos si no existe
                    boolean lbContinuar = true;
                    if (!loAux.moveFirst()) {
                        loAux.addNew();
                        //si esta en la tabla de borrados es q hay un conflicto, q se añade en el proceso de borrados
                        if (loBorradosServidorList != null
                                && loBorradosServidorList.buscar(
                                JListDatos.mclTIgual,
                                loListCliente.getFields().malCamposPrincipales(),
                                loListCliente.getFields().masCamposPrincipales())) {
                            lbContinuar = false;
                        }
                    }
                    if (lbContinuar) {
                        loAux.getFields().cargar(loListCliente.getFields().moFilaDatos());
                        IResultado loResult = loAux.update(true);
                        if (!loResult.getBien()) {
                            addMensaje(loResult.getMensaje());
                            mbAMedio = true;
                        } else {
                            //incrementamos el numero de registros actualizados con exito
                            mlContadorPasada++;
                            mlActualizadosServidor++;
                        }
                    }

                }
            } while (loListCliente.moveNext());
        }
    }

    private JListDatos getDiferencias(IServerServidorDatos poServer, JTableDef loTablaServidor, int lNumeroTransaccionMinima, int lNumeroTransaccionMaxima) throws Exception {
        JListDatos loListServer = loTablaServidor.getListDatos();
        loListServer.moServidor = poServer;

        int lCampoTransacServidor = loListServer.getFields().getIndiceDeCampo(mcsCampoNumeroTransaccion);
        if (lCampoTransacServidor >= 0) {
            JSelect loSelectServer = loListServer.getSelect();

            loSelectServer.getWhere().addCondicion(
                    JListDatosFiltroConj.mclAND,
                    JListDatos.mclTMayorIgual,
                    lCampoTransacServidor,
                    String.valueOf(lNumeroTransaccionMinima + 1));
            loSelectServer.getWhere().addCondicion(
                    JListDatosFiltroConj.mclOR,
                    JListDatos.mclTIgual,
                    lCampoTransacServidor,
                    "");
            loSelectServer.getWhere().inicializar(
                    loListServer.msTabla,
                    loListServer.getFields().malTipos(),
                    loListServer.getFields().msNombres());

            loListServer.recuperarDatosNoCacheNormal(loSelectServer);
        }
        return loListServer;
    }

    private void igualarEstructura() throws Throwable {
        msRegistroActual = "Tuneando estructura";
        JSincronizacionCrear loEstru = new JSincronizacionCrear(moServerServidor);
        loEstru.procesar();

        msRegistroActual = "Recuperando estructura";
        JComparadorBD moCom = new JComparadorBD(
                moServerServidor.getTableDefs(),
                moServerCliente.getTableDefs(),
                moServerServidor,
                moServerCliente);
        msRegistroActual = "Recuperando diferencias de estructura";
        moCom.ejecutar();
        IListaElementos loLista = moCom.getDiferencias();

        msRegistroActual = "Arreglando diferencias de estructura TABLAS";
        //tablas, solo añadimos y modificamos, nunca borramos
        for (int i = 0; i < loLista.size() && !mbCancelado; i++) {
            IDiferencia loDiferencia = (IDiferencia) loLista.get(i);
            if (loDiferencia.getTipoEstructura() == IDiferencia.mclTabla
                    && (loDiferencia.getTipoModificacion() == IDiferencia.mclTipoModificacion
                    || loDiferencia.getTipoModificacion() == IDiferencia.mclTipoNoExiste)) {
                try {
                    loDiferencia.arreglarDiferencia();
                } catch (Exception ex) {
                    JDepuracion.anadirTexto(getClass().getName(), ex);
                    addMensaje(ex);
                }
            }
        }
        msRegistroActual = "Arreglando diferencias de estructura CAMPOS";
        //campos, solo añadimos y modificamos, nunca borramos
        for (int i = 0; i < loLista.size() && !mbCancelado; i++) {
            IDiferencia loDiferencia = (IDiferencia) loLista.get(i);
            if (loDiferencia.getTipoEstructura() == IDiferencia.mclCampo
                    && (loDiferencia.getTipoModificacion() == IDiferencia.mclTipoModificacion
                    || loDiferencia.getTipoModificacion() == IDiferencia.mclTipoNoExiste)) {
                try {
                    loDiferencia.arreglarDiferencia();
                } catch (Exception ex) {
                    JDepuracion.anadirTexto(getClass().getName(), ex);
                    addMensaje(ex);
                }
            }
        }

        msRegistroActual = "Arreglando diferencias de estructura INDICES";
        //indices, para los indices deben ser exactamente iguales, aunque si no lo son tampoco pasa nada
        for (int i = 0; i < loLista.size() && !mbCancelado; i++) {
            IDiferencia loDiferencia = (IDiferencia) loLista.get(i);
            if (loDiferencia.getTipoEstructura() == IDiferencia.mclIndice) {
                try {
                    loDiferencia.arreglarDiferencia();
                } catch (Exception ex) {
                    JDepuracion.anadirTexto(getClass().getName(), ex);
                    addMensaje(ex);
                }
            }
        }

        msRegistroActual = "Arreglando diferencias de estructura RELACCIONES";
        //relaciones, para las relaciones deben ser exactamente iguales, aunque si no lo son tampoco pasa nada
        for (int i = 0; i < loLista.size() && !mbCancelado; i++) {
            IDiferencia loDiferencia = (IDiferencia) loLista.get(i);
            if (loDiferencia.getTipoEstructura() == IDiferencia.mclRelacion) {
                try {
                    loDiferencia.arreglarDiferencia();
                } catch (Exception ex) {
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
        mbFin = true;
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

    private String getMensaje(String psMensaje) {
        msMensajeFinal = String.valueOf('\n');
        if (mbAMedio || moListaConlictos.hayPendientes()) {
            msMensajeFinal += "HAN QUEDADO REGISTROS SIN RESOLVER(+o- " + String.valueOf(moListaConlictos.size()) + ")\n";
        }
        msMensajeFinal += "Actualizados servidor " + String.valueOf(mlActualizadosServidor) + '\n';
        msMensajeFinal += "Borrados servidor " + String.valueOf(mlBorradosServidor) + '\n';
        msMensajeFinal += "Actualizados cliente " + String.valueOf(mlActualizadosCliente) + '\n';
        msMensajeFinal += "Borrados cliente " + String.valueOf(mlBorradosCliente) + '\n';
        msMensajeFinal += "Conflictos resueltos " + String.valueOf(mlConflictosResueltos) + '\n';
        msMensajeFinal = psMensaje + msMensajeFinal;
        return msMensajeFinal;
    }

    public void mostrarMensaje(String psMensaje) {
        String lsMensaje = getMensaje(psMensaje);
        if (moParam.mbVisual) {
            JGUIxConfigGlobalModelo.getInstancia().getMostrarPantalla().mensaje(null, psMensaje, IMostrarPantalla.mclMensajeInformacion, null);
        }
    }

    public void mostrarError(Throwable e) {
        String lsMensaje = getMensaje(e.toString());
        mbError = true;
        if (moParam.mbVisual) {
            JGUIxConfigGlobalModelo.getInstancia().getMostrarPantalla().mensajeErrorYLog(null, e, null);
        }
    }

    public void finalizar() {
        try {
            guardarFicherosLogs(msMensajeFinal);
        } catch (Exception ex) {
            JDepuracion.anadirTexto(getClass().getName(), ex);
        }
        if (mbError || mbAMedio || moListaConlictos.hayPendientes()) {
            //mandar correo
        }
    }
}
