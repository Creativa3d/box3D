/*
* JT2GUIXCALENDARIO.java
*
* Creado el 15/3/2012
*/
package utilesGUIxAvisos.tablasControladoras;

import ListDatos.*;
import utilesGUIx.ActionEventCZ;
import utilesGUIx.formsGenericos.JPanelGeneralBotones;
import utilesGUIx.formsGenericos.JT2GENERALBASE2;
import utilesGUIx.formsGenericos.busqueda.IConsulta;
import utilesGUIx.formsGenericos.edicion.IFormEdicion;
import utilesGUIxAvisos.calendario.JDatosGenerales;
import utilesGUIxAvisos.consultas.JTFORMGUIXCALENDARIO;
import utilesGUIxAvisos.forms.JPanelGUIXCALENDARIO;
import utilesGUIxAvisos.tablasExtend.JTEEGUIXCALENDARIO;

public class JT2GUIXCALENDARIO  extends JT2GENERALBASE2 {
    private final JTFORMGUIXCALENDARIO moConsulta;
    private final IServerServidorDatos moServer;
    private IFilaDatos moFilaDatosRelac;
    private String msNomTabRelac = "";
    private final JDatosGenerales moDatosGenerales;

    /** Crea una nueva instancia de JT2GUIXCALENDARIO */
    public JT2GUIXCALENDARIO(JDatosGenerales poDatosGenerales) {
        super();
        moDatosGenerales=poDatosGenerales;
        
        moServer = poDatosGenerales.getServer();
        moConsulta = new JTFORMGUIXCALENDARIO(moServer);
        moConsulta.crearSelectSimple();
        addBotones();
        moParametros.setLongitudCampos(getLongitudCampos());
        moParametros.setNombre(moDatosGenerales.getTextosForms().getTexto(JTEEGUIXCALENDARIO.msCTabla));
        getParametros().setMostrarPantalla(poDatosGenerales.getMostrarPantalla());
//        inicializarPlugIn(JDatosGeneralesP.getDatosGeneralesPlugIn());
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
//                Color.red);
    }

    public JT2GUIXCALENDARIO(
            JDatosGenerales poDatosGenerales,
            String psNomTabRelac,
            IFilaDatos poDatosRelac) {
        this(poDatosGenerales);
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
        JTEEGUIXCALENDARIO loGUIXCALENDARIO = new JTEEGUIXCALENDARIO(moServer);
        loGUIXCALENDARIO.moList.addNew();
        valoresDefecto(loGUIXCALENDARIO);

        JPanelGUIXCALENDARIO loPanel = new JPanelGUIXCALENDARIO();
        loPanel.setDatos(moDatosGenerales,loGUIXCALENDARIO, this, moConsulta);

        getParametros().getMostrarPantalla().mostrarEdicion((IFormEdicion)loPanel, loPanel);
    }

    public void valoresDefecto(final JSTabla poTabla) throws Exception {
        if(poTabla.moList.getModoTabla()== JListDatos.mclNuevo){
            JTEEGUIXCALENDARIO loBajas = (JTEEGUIXCALENDARIO)poTabla;
            loBajas.valoresDefecto();
        }
        
    }

    public void borrar(final int plIndex) throws Exception {
        moConsulta.moList.setIndex(plIndex);
        JTEEGUIXCALENDARIO loGUIXCALENDARIO = JTEEGUIXCALENDARIO.getTabla(moConsulta.moList.getFields(moConsulta.lPosiCALENDARIO).getString(), moServer);
        IFilaDatos loFila = (IFilaDatos)loGUIXCALENDARIO.moList.moFila().clone();
        IResultado loResult = loGUIXCALENDARIO.borrar();
        if(!loResult.getBien()){
            throw new Exception(loResult.getMensaje());
        }
        loFila.setTipoModif (JListDatos.mclBorrar);
        datosactualizados(loFila);
    }

    public void editar(final int plIndex) throws Exception {
        moConsulta.moList.setIndex(plIndex);

        JTEEGUIXCALENDARIO loGUIXCALENDARIO = JTEEGUIXCALENDARIO.getTabla(moConsulta.moList.getFields(moConsulta.lPosiCALENDARIO).getString(), moServer);

        valoresDefecto(loGUIXCALENDARIO);
        JPanelGUIXCALENDARIO loPanel = new JPanelGUIXCALENDARIO();
        loPanel.setDatos(moDatosGenerales,loGUIXCALENDARIO, this, moConsulta);

        getParametros().getMostrarPantalla().mostrarEdicion((IFormEdicion)loPanel, loPanel);
    }

    public String getNombre() {
        return moDatosGenerales.getTextosForms().getTexto(JTEEGUIXCALENDARIO.msCTabla);
    }

    public int[] getLongitudCampos() {
        int[] loInt = new int[JTFORMGUIXCALENDARIO.mclNumeroCampos];

        loInt[JTFORMGUIXCALENDARIO.lPosiCALENDARIO]=0;
        loInt[JTFORMGUIXCALENDARIO.lPosiNOMBRE]=80;
        return loInt;
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


}