/*
* JT2AUXILIARES.java
*
* Creado el 17/10/2014
*/
package utilesGUIx.aplicacion.auxiliares.tablasControladoras;


import utilesGUIx.aplicacion.auxiliares.forms.JPanelAUXILIARES;
import ListDatos.*;

import utilesGUIx.ActionEventCZ;
import utilesGUIx.JGUIxConfigGlobal;
import utilesGUIx.formsGenericos.*;
import utilesGUIx.formsGenericos.busqueda.*;

import utilesGUIx.aplicacion.IGestionProyecto;
import utilesGUIx.aplicacion.auxiliares.consultas.JTFORMAUXILIARES;

import utilesGUIx.panelesGenericos.JConsulta;
import utilesGUIx.panelesGenericos.JPanelBusquedaParametros;
import utilesGUIx.formsGenericos.edicion.IFormEdicion;
import utilesGUIx.aplicacion.auxiliares.tablasExtend.JTEEAUXILIARES;

public class JT2AUXILIARES  extends  JT2GENERALBASE2 {
    private final JTFORMAUXILIARES moConsulta;
    private final IServerServidorDatos moServer;
    private IFilaDatos moFilaDatosRelac;
    private String msNomTabRelac = "";

    /** Crea una nueva instancia de JT2AUXILIARES */
    public JT2AUXILIARES(final IServerServidorDatos poServidorDatos, final IMostrarPantalla poMostrarPantalla) {
        super();
        moServer = poServidorDatos;
        moConsulta = new JTFORMAUXILIARES(poServidorDatos);
        moConsulta.crearSelectSimple();
        addBotones();
        moParametros.setLongitudCampos(getLongitudCampos());
        moParametros.setNombre(JTEEAUXILIARES.msCTabla);
        moParametros.setTitulo(JGUIxConfigGlobal.getInstancia().getTextosForms().getTexto(JTEEAUXILIARES.msCTabla));
        getParametros().setMostrarPantalla(poMostrarPantalla);
        inicializarPlugIn(JGUIxConfigGlobal.getInstancia().getPlugInFactoria());
//EJEMPLOS de coloreo        
//        JListDatosFiltroConj loElem = new JListDatosFiltroConj();
//        loElem.addCondicion(JListDatosFiltroConj.mclAND,
//                JListDatos.mclTMayorIgual, JTEEARTICULOS.lPosiDESCRIPCION, "*");
//        loElem.addCondicion(JListDatosFiltroConj.mclAND,
//                JListDatos.mclTMenorIgual, JTEEARTICULOS.lPosiDESCRIPCION, "*ZZZZZZZZZ");
//        loElem.inicializar(JTEEARTICULOS.msCTabla, JTEEARTICULOS.malTipos, JTEEARTICULOS.masNombres);
//        ((JPanelGenericoColores) getParametros().getColoresTabla()).addCondicion(
//                loElem,
//                null,
//                new ColorCZ(Color.red));
    }

    public JT2AUXILIARES(
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
    public IConsulta getConsulta() {
        return moConsulta;
    }

    public void mostrarFormPrinci() throws Exception {
        getParametros().getMostrarPantalla().mostrarFormPrinci(this, 800,600);
    }

    public void anadir() throws Exception {
        JTEEAUXILIARES loAUXILIARES = new JTEEAUXILIARES(moServer);
        loAUXILIARES.moList.addNew();
        valoresDefecto(loAUXILIARES);

        JPanelAUXILIARES loPanel = new JPanelAUXILIARES();
        loPanel.setDatos(loAUXILIARES, this);

        getParametros().getMostrarPantalla().mostrarEdicion(loPanel, loPanel);
    }

    public void valoresDefecto(final JSTabla poTabla) throws Exception {
    }

    public void borrar(final int plIndex) throws Exception {
        moConsulta.moList.setIndex(plIndex);
        JTEEAUXILIARES loAUXILIARES = new JTEEAUXILIARES(moServer);
        loAUXILIARES.moList.addNew();
        loAUXILIARES.getCODIGOAUXILIAR().setValue(moConsulta.moList.getFields(moConsulta.lPosiCODIGOAUXILIAR).getValue());
        loAUXILIARES.moList.update(false);

        loAUXILIARES.moList.moFila().setTipoModif(JListDatos.mclNada);
        IFilaDatos loFila = (IFilaDatos)loAUXILIARES.moList.moFila().clone();
        IResultado loResult = loAUXILIARES.borrar();
        if(!loResult.getBien()){
            throw new Exception(loResult.getMensaje());
        }
        loFila.setTipoModif (JListDatos.mclBorrar);
        datosactualizados(loFila);
    }

    public void editar(final int plIndex) throws Exception {
        moConsulta.moList.setIndex(plIndex);

        JTEEAUXILIARES loAUXILIARES = JTEEAUXILIARES.getTabla(
                moConsulta.moList.getFields(moConsulta.lPosiCODIGOAUXILIAR).getString(),
                moServer);
        valoresDefecto(loAUXILIARES);
        JPanelAUXILIARES loPanel = new JPanelAUXILIARES();
        loPanel.setDatos(loAUXILIARES, this);

        if(moConsulta.moList.size()>1){
            getParametros().getMostrarPantalla().mostrarEdicion(loPanel, loPanel);
        }else{
            getParametros().getMostrarPantalla().mostrarEdicion((IFormEdicion)loPanel, loPanel);
        }            
    }

    public int[] getLongitudCampos() {
//        int[] loInt = new int[JTFORMAUXILIARES.mclNumeroCampos];

//        loInt[JTFORMAUXILIARES.lPosiCODIGOAUXILIAR]=80;
//        loInt[JTFORMAUXILIARES.lPosiCODIGOGRUPOAUX]=80;
//        loInt[JTFORMAUXILIARES.lPosiACRONIMO]=80;
//        loInt[JTFORMAUXILIARES.lPosiDESCRIPCION]=80;
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
    }


    public void addBotones() {
        JPanelGeneralBotones retValue;
        
        retValue = getParametros().getBotonesGenerales();
        
//        retValue.addBotonPrincipal(new JBotonRelacionado(mcsImpListado, mcsImpListado, "/images/Print16.gif", this));
        
    }

    public static class GestionProyectoTabla implements IGestionProyecto {

        public JSTabla getTabla(IServerServidorDatos poServer, String psTabla) throws Exception {
            return new JTEEAUXILIARES(poServer);
        }

        public IPanelControlador getControlador(IServerServidorDatos poServer, IMostrarPantalla poMostrar, String psTabla, String psTablaRelac, IFilaDatos poDatosRelac) throws Exception {
        IPanelControlador loResult = null;
        
            loResult = new JT2AUXILIARES(poServer, poMostrar,psTablaRelac, poDatosRelac);
            return loResult;
        }

        public JPanelBusquedaParametros getParamPanelBusq(IServerServidorDatos poServer, IMostrarPantalla poMostrar, String psTabla) throws Exception {
            return JTEEAUXILIARES.getParamPanelBusq(poServer, poMostrar);
        }

        public void mostrarEdicion(IServerServidorDatos poServer, IMostrarPantalla poMostrar, String psTabla, IFilaDatos poFila) throws Exception {
            JTEEAUXILIARES loObj = JTEEAUXILIARES.getTabla(poFila, poServer);
            JPanelAUXILIARES loPanel = new JPanelAUXILIARES();
            loPanel.setDatos(loObj, null);
            poMostrar.mostrarEdicion(loPanel, loPanel);
        }

        public IFilaDatos buscar(IServerServidorDatos poServer, IMostrarPantalla poMostrar, String psTabla) throws Exception {
            IConsulta loConsulta = null;
            JSTabla loTabla = null;
            boolean lbCache = false;

            loTabla = new JTEEAUXILIARES(poServer);
            lbCache = JTEEAUXILIARES.getPasarACache();
            if(lbCache){
                loTabla.recuperarTodosNormal(lbCache);
                loConsulta = new JConsulta(loTabla, true);
            }else{
                JTFORMAUXILIARES loConsulta1 = new JTFORMAUXILIARES(poServer);
                loConsulta1.crearSelectSimple();
                loConsulta = loConsulta1;
            }
//            return JGUIxConfigGlobal.getInstancia().getPlugInFactoria().getPlugInContexto().mostrarBusqueda(loConsulta, loTabla);
            return null;
            
       }
    
    }

}