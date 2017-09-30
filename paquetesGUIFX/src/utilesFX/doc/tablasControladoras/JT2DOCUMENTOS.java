/*
* JT2DOCUMENTOS.java
*
* Creado el 19/10/2016
*/
package utilesFX.doc.tablasControladoras;


import ListDatos.*;
import java.io.File;
import java.util.List;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import utiles.JCadenas;

import utilesGUIx.ActionEventCZ;
import utilesGUIx.formsGenericos.*;
import utilesGUIx.formsGenericos.busqueda.*;
import utilesGUIx.cargaMasiva.ICargaMasiva;

import utilesDoc.tablas.*;
import utilesDoc.tablasExtend.*;
import utilesDoc.consultas.*;
import utilesDoc.*;
import utilesFX.doc.JAccionesSeleccionarFotoPanel;
import utilesFX.formsGenericos.JT2GENERALBASE2;
import utilesGUIx.JGUIxConfigGlobalModelo;
import utilesGUIx.aplicacion.IGestionProyecto;
import utilesGUIx.panelesGenericos.JPanelBusquedaParametros;
import utilesx.JEjecutar;

public class JT2DOCUMENTOS  extends  JT2GENERALBASE2 implements ICargaMasiva {
    private final JTFORMDOCUMENTOS moConsulta;
    private final IServerServidorDatos moServer;
    private IFilaDatos moFilaDatosRelac;
    private String msNomTabRelac = "";
    private String msGrupo;
    private String msGrupoIden;

    /** Crea una nueva instancia de JT2DOCUMENTOS
     * @param poServidorDatos
     * @param poMostrarPantalla */
    public JT2DOCUMENTOS(final IServerServidorDatos poServidorDatos, final IMostrarPantalla poMostrarPantalla) {
        this(poServidorDatos, poMostrarPantalla, "Aplicacion", "General");
    }
    public JT2DOCUMENTOS(final IServerServidorDatos poServidorDatos, final IMostrarPantalla poMostrarPantalla
    ,String psGrupo, String psGrupoIden) {
        super();
        moServer = poServidorDatos;
        moConsulta = new JTFORMDOCUMENTOS(poServidorDatos);
        moConsulta.crearSelect(psGrupo, psGrupoIden);
        addBotones();
        moParametros.setPlugInPasados(true);
        moParametros.setLongitudCampos(getLongitudCampos());
        moParametros.setNombre(JDocDatosGeneralesModelo.getTextosForms().getTexto(JTEEDOCUMENTOS.msCTabla));
        getParametros().setMostrarPantalla(poMostrarPantalla);
        msGrupo=psGrupo;
        msGrupoIden=psGrupoIden;
    }

    public JT2DOCUMENTOS(
            IServerServidorDatos poServer,
            IMostrarPantalla poMostrar,
            String psNomTabRelac,
            IFilaDatos poDatosRelac) {
        this(poServer, poMostrar);
        setTablaRelacionada(psNomTabRelac, poDatosRelac);
    }

    public void setTablaRelacionada(
            String psNomTabRelac,
            IFilaDatos poDatosRelac){
        msNomTabRelac = psNomTabRelac;
        moFilaDatosRelac = poDatosRelac;
        moConsulta.crearSelect(msNomTabRelac, moFilaDatosRelac);
    }
    @Override
    public IConsulta getConsulta() {
        return moConsulta;
    }
//
    @Override
    public void mostrarFormPrinci() throws Exception {
         getParametros().getMostrarPantalla().mostrarFormPrinci(this, 800,600);
    }
//    public void mostrarFormPrinci(int plTipoMostrar) throws Exception {
//        
//        JPanelGenericoGaleria loGaleria = new JPanelGenericoGaleria();
//        loGaleria.setControlador(this, null
//                ,  JDocDatosGeneralesGUIx.getInstance().getImagenBasicaFactory()
//        );
//        JMostrarPantallaParam loParam = new JMostrarPantallaParam(loGaleria, 800, 600, plTipoMostrar
//                , moParametros.getTitulo() + " " + msGrupo + " " + msGrupoIden
//        );
//        getParametros().getMostrarPantalla().mostrarForm(loParam);
//    }

    @Override
    public void anadir() throws Exception {
        
        JAccionesSeleccionarFotoPanel loAccioonesFotoPanel;
        loAccioonesFotoPanel = new JAccionesSeleccionarFotoPanel(
                JGUIxConfigGlobalModelo.getInstancia().getGestorDocumental().getDatosGeneralesXML()
        );
        
        FileChooser loSelec = new FileChooser();
        if(!JCadenas.isVacio(loAccioonesFotoPanel.getRutaDefecto())
                && new File(loAccioonesFotoPanel.getRutaDefecto()).exists()
                && new File(loAccioonesFotoPanel.getRutaDefecto()).isDirectory()
                ){
            loSelec.setInitialDirectory(new File(loAccioonesFotoPanel.getRutaDefecto()));
        }
        List<File> loFiles = loSelec.showOpenMultipleDialog(new Stage());                
        if(loFiles != null && loFiles.size()>0){
            loAccioonesFotoPanel.setRutaDefecto(loFiles.get(0).getParent());
            
            for(File loFile : loFiles){
                JTEEDOCUMENTOS loDOCUMENTOS = new JTEEDOCUMENTOS(moServer);
                loDOCUMENTOS.moList.addNew();
                valoresDefecto(loDOCUMENTOS);
                loDOCUMENTOS.setDatosDefecto(loFile);
                loDOCUMENTOS.validarCampos();
                IResultado loResult=loDOCUMENTOS.guardar(this);
                if(!loResult.getBien()){
                    throw new Exception(loResult.getMensaje());
                }
                IFilaDatos loFila = loDOCUMENTOS.moList.moFila();
                loFila.setTipoModif(JListDatos.mclNuevo);
                datosactualizados(loFila);
            }
        }
        
//        
//        loAccioonesFotoPanel.seleccionarImagen();
//        //si no ha cancelado
//        if(!loAccioonesFotoPanel.isCancelado()){
//            JTEEDOCUMENTOS loDOCUMENTOS = new JTEEDOCUMENTOS(moServer);
//            loDOCUMENTOS.moList.addNew();
//            valoresDefecto(loDOCUMENTOS);
//            
//            File loFile = loAccioonesFotoPanel.getArchivo();
//            loDOCUMENTOS.setDatosDefecto(loFile);
//            
//            JPanelDOCUMENTOS loPanel = new JPanelDOCUMENTOS();
//            loPanel.setDatos(loDOCUMENTOS, this);
//
//            getParametros().getMostrarPantalla().mostrarEdicion(loPanel, null, loPanel, JMostrarPantalla.mclEdicionDialog);
//
//            if(loAccioonesFotoPanel.isLimpiarDirectorio() && loDOCUMENTOS.moveFirst()){
//                loAccioonesFotoPanel.limpiarDirectorio();
//            }
//        }
    }

    @Override
    public void valoresDefecto(final JSTabla poTabla) throws Exception {
        if(poTabla.moList.getModoTabla() == JListDatos.mclNuevo) {
            poTabla.moList.getFields(JTDOCUMENTOS.lPosiGRUPO).setValue(
                    msGrupo
            );
            poTabla.moList.getFields(JTDOCUMENTOS.lPosiGRUPOIDENT).setValue(
                    msGrupoIden
            );
            if(msNomTabRelac!=null && !msNomTabRelac.equals("")){
                if (msNomTabRelac.equals(JTDOCUMCLASIF.msCTabla)) {
                    poTabla.moList.getFields(JTDOCUMENTOS.lPosiCODCLASIF).setValue(
                            moFilaDatosRelac.msCampo(JTDOCUMCLASIF.lPosiCODIGOCLASIFDOC)
                    );
                }

                if (msNomTabRelac.equals(JTDOCUMTIPOS.msCTabla)) {
                    poTabla.moList.getFields(JTDOCUMENTOS.lPosiCODTIPODOCUMENTO).setValue(
                            moFilaDatosRelac.msCampo(JTDOCUMTIPOS.lPosiCODIGOTIPODOC)
                    );
                }

            }
            ((JTEEDOCUMENTOS) poTabla).valoresDefecto();
        }
    }

    @Override
    public void borrar(final int plIndex) throws Exception {
        moConsulta.moList.setIndex(plIndex);
        JTEEDOCUMENTOS loDOCUMENTOS = JTEEDOCUMENTOS.getTabla(
                moConsulta.moList.getFields(moConsulta.lPosiGRUPO).getString()
                , moConsulta.moList.getFields(moConsulta.lPosiGRUPOIDENT).getString()
                , moConsulta.moList.getFields(moConsulta.lPosiCODIGODOCUMENTO).getString()
                ,moServer);
        
        IFilaDatos loFila = (IFilaDatos)loDOCUMENTOS.moList.moFila().clone();
        IResultado loResult = loDOCUMENTOS.borrar();
        if(!loResult.getBien()){
            throw new Exception(loResult.getMensaje());
        }
        loFila.setTipoModif (JListDatos.mclBorrar);
        datosactualizados(loFila);
    }

    @Override
    public void editar(final int plIndex) throws Exception {
        moConsulta.moList.setIndex(plIndex);

        JTEEDOCUMENTOS loDOCUMENTOS = JTEEDOCUMENTOS.getTabla(
                moConsulta.moList.getFields(moConsulta.lPosiGRUPO).getString(),
                moConsulta.moList.getFields(moConsulta.lPosiGRUPOIDENT).getString(),
                moConsulta.moList.getFields(moConsulta.lPosiCODIGODOCUMENTO).getString(),
                moServer);
        
        JEjecutar.abrirDocumento(loDOCUMENTOS.getFicheroLocal().getAbsolutePath());
        
    }

    

    
    public String getNombre() {
        return JDocDatosGeneralesModelo.getTextosForms().getTexto(JTEEDOCUMENTOS.msCTabla);
    }

    public int[] getLongitudCampos() {
//        int[] loInt = new int[JTFORMDOCUMENTOS.mclNumeroCampos];

//        loInt[JTFORMDOCUMENTOS.lPosiGRUPO]=80;
//        loInt[JTFORMDOCUMENTOS.lPosiGRUPOIDENT]=80;
//        loInt[JTFORMDOCUMENTOS.lPosiCODIGODOCUMENTO]=80;
//        loInt[JTFORMDOCUMENTOS.lPosiNOMBRE]=80;
//        loInt[JTFORMDOCUMENTOS.lPosiDESCRIPCION]=80;
//        loInt[JTFORMDOCUMENTOS.lPosiAUTOR]=80;
//        loInt[JTFORMDOCUMENTOS.lPosiFECHA]=80;
//        loInt[JTFORMDOCUMENTOS.lPosiUSUARIO]=80;
//        loInt[JTFORMDOCUMENTOS.lPosiFECHAMODIF]=80;
//        loInt[JTFORMDOCUMENTOS.lPosiCODTIPODOCUMENTO]=80;
//        loInt[JTFORMDOCUMENTOS.lPosiCODCLASIF]=80;
//        loInt[JTFORMDOCUMENTOS.lPosiRUTA]=80;
//        loInt[JTFORMDOCUMENTOS.lPosiIDENTIFICADOREXTERNO]=80;
//        loInt[JTFORMDOCUMENTOS.lPosiIDENTIFICADOROTRO]=80;
//        return loInt;
        return null;
    }

    @Override
    public void actionPerformed(ActionEventCZ e, int[] plIndex) throws Exception {
            if(plIndex.length>0){
                for(int i = 0 ; i < plIndex.length; i++){
                    moConsulta.moList.setIndex(plIndex[i]);
                }
            }else{
                throw new Exception("No existe una fila actual");
            }
    }


    public void addBotones() {
        JPanelGeneralBotones retValue;
        
        retValue = getParametros().getBotonesGenerales();
        retValue.getEditar().setCaption("Ver");
        
    }

    public static class GestionProyectoTabla implements IGestionProyecto {

        public JSTabla getTabla(IServerServidorDatos poServer, String psTabla) throws Exception {
            return new JTEEDOCUMENTOS(poServer);
        }

        public IPanelControlador getControlador(IServerServidorDatos poServer, IMostrarPantalla poMostrar, String psTabla, String psTablaRelac, IFilaDatos poDatosRelac) throws Exception {
        IPanelControlador loResult = null;
        
            loResult = new JT2DOCUMENTOS(poServer, poMostrar,psTablaRelac, poDatosRelac);
            return loResult;
        }

        public JPanelBusquedaParametros getParamPanelBusq(IServerServidorDatos poServer, IMostrarPantalla poMostrar, String psTabla) throws Exception {
            return JTEEDOCUMENTOS.getParamPanelBusq(poServer, poMostrar);
        }

        public void mostrarEdicion(IServerServidorDatos poServer, IMostrarPantalla poMostrar, String psTabla, IFilaDatos poFila) throws Exception {

        }

        public IFilaDatos buscar(IServerServidorDatos poServer, IMostrarPantalla poMostrar, String psTabla) throws Exception {
//            IConsulta loConsulta = null;
//            JSTabla loTabla = null;
//            boolean lbCache = false;
//
//            loTabla = new JTEEDOCUMENTOS(poServer);
//            lbCache = JTEEDOCUMENTOS.getPasarACache();
//            if(lbCache){
//                loTabla.recuperarTodosNormal(lbCache);
//                loConsulta = new JConsulta(loTabla, true);
//            }else{
//                JTFORMDOCUMENTOS loConsulta1 = new JTFORMDOCUMENTOS(poServer);
//                loConsulta1.crearSelectSimple();
//                loConsulta = loConsulta1;
//            }
//            return JDocDatosGeneralesGUIx.mostrarBusqueda(loConsulta, loTabla);
            return null;
       }
    
    }

    public JListDatos getTablaBase() {
        return new JTEEDOCUMENTOS(moServer).getList();
    }
}