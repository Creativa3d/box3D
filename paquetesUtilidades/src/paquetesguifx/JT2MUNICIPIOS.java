/*
* JT2MUNICIPIOS.java
*
* Creado el 5/10/2015
*/
package paquetesguifx;


import ListDatos.*;
import impresionJasper.plugin.JPlugInListadosJasper;
import javafx.scene.paint.Color;
import main.JDatosGeneralesP;
import utilesFX.JFXConfigGlobal;
import utilesFX.formsGenericos.JMostrarPantalla;
import utilesFX.formsGenericos.JT2GENERALBASE2;
import utilesFX.formsGenericos.edicion.JPanelEdicionNuevoRapido;
import utilesFX.msgbox.JMsgBox;
import utilesGUIx.ActionEventCZ;
import utilesGUIx.ActionListenerCZ;
import utilesGUIx.ColorCZ;
import utilesGUIx.aplicacion.IGestionProyecto;
import utilesGUIx.aplicacion.avisos.JAviso;
import utilesGUIx.cargaMasiva.ICargaMasiva;
import utilesGUIx.formsGenericos.*;
import utilesGUIx.formsGenericos.boton.JBotonRelacionado;
import utilesGUIx.formsGenericos.busqueda.*;
import utilesGUIx.formsGenericos.colores.JPanelGenericoColores;
import utilesGUIx.panelesGenericos.JConsulta;
import utilesGUIx.panelesGenericos.JPanelBusquedaParametros;

public class JT2MUNICIPIOS  extends  JT2GENERALBASE2 implements ICargaMasiva {
private final JTEEMUNICIPIOS moConsulta;
    private final IServerServidorDatos moServer;
    private IFilaDatos moFilaDatosRelac;
    private String msNomTabRelac = "";

    /** Crea una nueva instancia de JT2MUNICIPIOS */
    public JT2MUNICIPIOS(final IServerServidorDatos poServidorDatos, final IMostrarPantalla poMostrarPantalla) {
        super();
        moServer = poServidorDatos;
        moConsulta = new JTEEMUNICIPIOS(poServidorDatos);
        addBotones();
        moParametros.setLongitudCampos(getLongitudCampos());
        moParametros.setNombre(JTEEMUNICIPIOS.msCTabla);
        getParametros().setMostrarPantalla(poMostrarPantalla);
//EJEMPLOS de coloreo        
        JListDatosFiltroConj loElem = new JListDatosFiltroConj();
        loElem.addCondicion(JListDatosFiltroConj.mclAND,
                JListDatos.mclTMayorIgual, JTEEMUNICIPIOS.lPosiCODPROV, "02");
        loElem.inicializar(moConsulta.moList);
        ((JPanelGenericoColores) getParametros().getColoresTabla()).addCondicion(
                loElem,
                new ColorCZ(JFXConfigGlobal.toRGB(Color.OLIVE.brighter())),
                new ColorCZ(JFXConfigGlobal.toRGB(Color.RED)));
        loElem = new JListDatosFiltroConj();
        loElem.addCondicion(JListDatosFiltroConj.mclAND,
                JListDatos.mclTMenor, JTEEMUNICIPIOS.lPosiCODPROV, "02");
        loElem.inicializar(moConsulta.moList);
        ((JPanelGenericoColores) getParametros().getColoresTabla()).addCondicion(
                loElem,
                null,
                new ColorCZ(JFXConfigGlobal.toRGB(Color.BLUE)));        
    }

    public IConsulta getConsulta() {
        return moConsulta;
    }

    public void mostrarFormPrinci() throws Exception {
        getParametros().getMostrarPantalla().mostrarFormPrinci(this, 800,600);
    }

    public void anadir() throws Exception {
        JTEEMUNICIPIOS loMUNICIPIOS = new JTEEMUNICIPIOS(moServer);
        loMUNICIPIOS.moList.addNew();
        valoresDefecto(loMUNICIPIOS);

        JPanelMUNICIPIOS loPanel = new JPanelMUNICIPIOS();
        loPanel.setDatos(loMUNICIPIOS, this);

        //creamos el panel de añadir rapido
        final JPanelEdicionNuevoRapido loPanel2 = new JPanelEdicionNuevoRapido();
        loPanel2.setPanel(loPanel, loPanel);
        //lo mostramos
        JFXConfigGlobal.getInstancia().getMostrarPantalla().mostrarFormPrinci(
                loPanel2, 
                loPanel.getTanano().width, 
                loPanel.getTanano().height,
                JMostrarPantalla.mclEdicionInternal);  
    }

    public void valoresDefecto(final JSTabla poTabla) throws Exception {
        
    }

    public void borrar(final int plIndex) throws Exception {
        moConsulta.moList.setIndex(plIndex);
        JTEEMUNICIPIOS loMUNICIPIOS = JTEEMUNICIPIOS.getTabla(
                moConsulta.getField(moConsulta.lPosiCODPROV).getString()
                , moConsulta.getField(moConsulta.lPosiCODHACIENDA).getString()
                , moServer);

        loMUNICIPIOS.moList.moFila().setTipoModif(JListDatos.mclNada);
        IFilaDatos loFila = (IFilaDatos)loMUNICIPIOS.moList.moFila().clone();
        IResultado loResult = loMUNICIPIOS.borrar();
        if(!loResult.getBien()){
            throw new Exception(loResult.getMensaje());
        }
        loFila.setTipoModif (JListDatos.mclBorrar);
        datosactualizados(loFila);
    }

    public void editar(final int plIndex) throws Exception {
        moConsulta.moList.setIndex(plIndex);

        JTEEMUNICIPIOS loMUNICIPIOS = JTEEMUNICIPIOS.getTabla(
                moConsulta.getField(moConsulta.lPosiCODPROV).getString()
                , moConsulta.getField(moConsulta.lPosiCODHACIENDA).getString()
                , moServer);

        valoresDefecto(loMUNICIPIOS);
        JPanelMUNICIPIOS loPanel = new JPanelMUNICIPIOS();
        loPanel.setDatos(loMUNICIPIOS, this);

        getParametros().getMostrarPantalla().mostrarEdicion(loPanel, loPanel);
    }

    public String getNombre() {
        return JTEEMUNICIPIOS.msCTabla;
    }

    public int[] getLongitudCampos() {
//        int[] loInt = new int[JTEEMUNICIPIOS.mclNumeroCampos];

//        loInt[JTEEMUNICIPIOS.lPosiCODPROV]=80;
//        loInt[JTEEMUNICIPIOS.lPosiCODHACIENDA]=80;
//        loInt[JTEEMUNICIPIOS.lPosiMUNICIPIO]=80;
//        loInt[JTEEMUNICIPIOS.lPosiCPM]=80;
//        loInt[JTEEMUNICIPIOS.lPosiESCAPITALSN]=80;
//        loInt[JTEEMUNICIPIOS.lPosiFECHAACT]=80;
//        loInt[JTEEMUNICIPIOS.lPosiCODIGOINE]=80;
//        return loInt;
        return null;
    }

    public void actionPerformed(ActionEventCZ e, int[] plIndex) throws Exception {
            if(plIndex.length>0){
                for(int i = 0 ; i < plIndex.length; i++){
                    moConsulta.moList.setIndex(plIndex[i]);
                    
                }
            }else{
                throw new Exception("No existe una fila actual");
            }
            if(e.getActionCommand().equalsIgnoreCase("notificar")){
                JDatosGeneralesP.getDatosGenerales().getAvisos().add(new JAviso("notificacion", new ActionListenerCZ() {
                    @Override
                    public void actionPerformed(ActionEventCZ e) {
                        moParametros.getMostrarPantalla().mensajeFlotante(null, "pulsado");
                    }
                }));
            }
            if(e.getActionCommand().equalsIgnoreCase("mensaje flotante")){
                JDatosGeneralesP.getDatosGenerales().mensajeFlotante(null, "hola mundo flotante 3 segundos");
            }
    }


    public void addBotones() {
        JPanelGeneralBotones retValue;
        
        retValue = getParametros().getBotonesGenerales();
        
        retValue.addBoton(new JBotonRelacionado(
                "notificar", "notificar", JFXConfigGlobal.getImagenCargada("/images/Print16.gif"), null,  this, null, "otros"));
        retValue.addBoton(new JBotonRelacionado(
                "mensaje flotante", "mensaje flotante", JFXConfigGlobal.getImagenCargada("/images/Print16.gif"), null, this, null, "otros"));
 
        JPlugInListadosJasper loPlugIn = new JPlugInListadosJasper();
//        loPlugIn.msImageICON = "/images/Print16.gif";
        loPlugIn.procesarControlador(JDatosGeneralesP.getDatosGenerales(), this);
        
    }

    public JListDatos getTablaBase() {
        return new JTEEMUNICIPIOS(moServer).getList();
    }



    public static class GestionProyectoTabla implements IGestionProyecto {

        public JSTabla getTabla(IServerServidorDatos poServer, String psTabla) throws Exception {
            return new JTEEMUNICIPIOS(poServer);
        }

        public IPanelControlador getControlador(IServerServidorDatos poServer, IMostrarPantalla poMostrar, String psTabla, String psTablaRelac, IFilaDatos poDatosRelac) throws Exception {
        IPanelControlador loResult = null;
            return loResult;
        }

        public JPanelBusquedaParametros getParamPanelBusq(IServerServidorDatos poServer, IMostrarPantalla poMostrar, String psTabla) throws Exception {
            return JTEEMUNICIPIOS.getParamPanelBusq(poServer, poMostrar);
        }

        public void mostrarEdicion(IServerServidorDatos poServer, IMostrarPantalla poMostrar, String psTabla, IFilaDatos poFila) throws Exception {
            JTEEMUNICIPIOS loObj = JTEEMUNICIPIOS.getTabla(poFila, poServer);
//            JPanelMUNICIPIOS loPanel = new JPanelMUNICIPIOS();
//            loPanel.setDatos(loObj, null, null);
//            poMostrar.mostrarEdicion(loPanel, loPanel);
        }

        public IFilaDatos buscar(IServerServidorDatos poServer, IMostrarPantalla poMostrar, String psTabla) throws Exception {
            return null;
       }
    
    }

}