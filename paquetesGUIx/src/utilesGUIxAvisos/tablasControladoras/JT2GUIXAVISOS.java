/*
* JT2GUIXAVISOS.java
*
* Creado el 3/11/2011
*/
package utilesGUIxAvisos.tablasControladoras;

import ListDatos.*;
import utilesGUIx.ActionEventCZ;
import utilesGUIx.formsGenericos.*;
import utilesGUIx.formsGenericos.busqueda.*;
import utilesGUIx.formsGenericos.edicion.IFormEdicion;
import utilesGUIxAvisos.calendario.JDatosGenerales;
import utilesGUIxAvisos.consultas.JTFORMGUIXAVISOS;
import utilesGUIxAvisos.forms.*;
import utilesGUIxAvisos.tablasExtend.JTEEGUIXAVISOS;
import utilesGUIxAvisos.tablasExtend.JTEEGUIXEVENTOS;

public class JT2GUIXAVISOS  extends JT2GENERALBASE2 {
    private final JTFORMGUIXAVISOS moConsulta;
    private final IServerServidorDatos moServer;
    private IFilaDatos moFilaDatosRelac;
    private String msNomTabRelac = "";
    private final JDatosGenerales moDatosGenerales;

    /** Crea una nueva instancia de JT2GUIXAVISOS
     * @param poDatosGenerales 
     */
    public JT2GUIXAVISOS(JDatosGenerales poDatosGenerales) {
        super();
        moDatosGenerales=poDatosGenerales;
        
        moServer = poDatosGenerales.getServer();
        moConsulta = new JTFORMGUIXAVISOS(moServer);
        moConsulta.crearSelectSimple();
        addBotones();
        moParametros.setLongitudCampos(getLongitudCampos());
        boolean[] labColumnasVisibles = new boolean[moConsulta.getFields().size()];
        for(int i = 0 ; i < labColumnasVisibles.length; i++){
            labColumnasVisibles[i]=true;
        }
        if(!moDatosGenerales.isSMS()){
            labColumnasVisibles[moConsulta.lPosiSENDER]=false;
            labColumnasVisibles[moConsulta.lPosiTELF]=false;
        }
        if(!moDatosGenerales.iseMail()){
            labColumnasVisibles[moConsulta.lPosiEMAIL]=false;
        }
        moParametros.setColumnasVisiblesConfig(labColumnasVisibles);
        moParametros.setNombre(moDatosGenerales.getTextosForms().getTexto(JTEEGUIXAVISOS.msCTabla));
        getParametros().setMostrarPantalla(poDatosGenerales.getMostrarPantalla());

    }

    public JT2GUIXAVISOS(
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
    @Override
    public IConsulta getConsulta() {
        return moConsulta;
    }

    @Override
    public void mostrarFormPrinci() throws Exception {
        getParametros().getMostrarPantalla().mostrarFormPrinci(this, 800,600);
    }

    @Override
    public void anadir() throws Exception {
        JTEEGUIXAVISOS loGUIXAVISOS = new JTEEGUIXAVISOS(moServer);
        loGUIXAVISOS.moList.addNew();
        valoresDefecto(loGUIXAVISOS);

        JPanelGUIXAVISOS loPanel = new JPanelGUIXAVISOS();
        loPanel.setDatos(moDatosGenerales,loGUIXAVISOS, this, moConsulta);

        getParametros().getMostrarPantalla().mostrarEdicion((IFormEdicion)loPanel, loPanel);
    }

    @Override
    public void valoresDefecto(final JSTabla poTabla) throws Exception {
        if(poTabla.moList.getModoTabla()== JListDatos.mclNuevo){
            JTEEGUIXAVISOS loBajas = (JTEEGUIXAVISOS)poTabla;
            loBajas.valoresDefecto();
            if(JTEEGUIXEVENTOS.msCTabla.equalsIgnoreCase(msNomTabRelac)){
                loBajas.getCALENDARIO().setValue(moFilaDatosRelac.msCampo(JTEEGUIXEVENTOS.lPosiCALENDARIO));
                loBajas.getCODIGOEVENTO().setValue(moFilaDatosRelac.msCampo(JTEEGUIXEVENTOS.lPosiCODIGO));
            }
        }
    }

    @Override
    public void borrar(final int plIndex) throws Exception {
        moConsulta.moList.setIndex(plIndex);
        JTEEGUIXAVISOS loGUIXAVISOS = new JTEEGUIXAVISOS(moServer);
        loGUIXAVISOS.moList.addNew();
        loGUIXAVISOS.getCALENDARIO().setValue(moConsulta.moList.getFields(moConsulta.lPosiCALENDARIO).getValue());
        loGUIXAVISOS.getCODIGOEVENTO().setValue(moConsulta.moList.getFields(moConsulta.lPosiCODIGOEVENTO).getValue());
        loGUIXAVISOS.getCODIGO().setValue(moConsulta.moList.getFields(moConsulta.lPosiCODIGO).getValue());
        loGUIXAVISOS.moList.update(false);

        loGUIXAVISOS.moList.moFila().setTipoModif(JListDatos.mclNada);
        IFilaDatos loFila = (IFilaDatos)loGUIXAVISOS.moList.moFila().clone();
        IResultado loResult = loGUIXAVISOS.borrar();
        if(!loResult.getBien()){
            throw new Exception(loResult.getMensaje());
        }
        loFila.setTipoModif (JListDatos.mclBorrar);
        datosactualizados(loFila);
    }

    @Override
    public void editar(final int plIndex) throws Exception {
        moConsulta.moList.setIndex(plIndex);

        JTEEGUIXAVISOS loGUIXAVISOS = new JTEEGUIXAVISOS(moServer);

        JListDatosFiltroConj loFiltro = new JListDatosFiltroConj();
        loFiltro.addCondicion(JListDatosFiltroConj.mclAND,JListDatos.mclTIgual, 
            new int[]{
                JTEEGUIXAVISOS.lPosiCALENDARIO
                ,JTEEGUIXAVISOS.lPosiCODIGOEVENTO
                ,JTEEGUIXAVISOS.lPosiCODIGO
            },
            new String[]{
                moConsulta.moList.getFields(moConsulta.lPosiCALENDARIO).getString()
                ,moConsulta.moList.getFields(moConsulta.lPosiCODIGOEVENTO).getString()
                ,moConsulta.moList.getFields(moConsulta.lPosiCODIGO).getString()
            });
        loGUIXAVISOS.recuperarFiltrados(loFiltro,false,false);

        valoresDefecto(loGUIXAVISOS);
        JPanelGUIXAVISOS loPanel = new JPanelGUIXAVISOS();
        loPanel.setDatos(moDatosGenerales,loGUIXAVISOS, this, moConsulta);

        getParametros().getMostrarPantalla().mostrarEdicion((IFormEdicion)loPanel, loPanel);
    }

    public String getNombre() {
        return moDatosGenerales.getTextosForms().getTexto(JTEEGUIXAVISOS.msCTabla);
    }

    public int[] getLongitudCampos() {
//        int[] loInt = new int[JTFORMGUIXAVISOS.mclNumeroCampos];

//        loInt[JTFORMGUIXAVISOS.lPosiCODIGO]=80;
//        loInt[JTFORMGUIXAVISOS.lPosiCODIGOEVENTO]=80;
//        loInt[JTFORMGUIXAVISOS.lPosiFECHACONCRETA]=80;
//        loInt[JTFORMGUIXAVISOS.lPosiPANTALLASN]=80;
//        loInt[JTFORMGUIXAVISOS.lPosiTELF]=80;
//        loInt[JTFORMGUIXAVISOS.lPosiSENDER]=80;
//        loInt[JTFORMGUIXAVISOS.lPosiEMAIL]=80;
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
        
//        retValue.addBotonPrincipal(new JBotonRelacionado(mcsImpListado, mcsImpListado, "/images/Print16.gif", this));
        
    }


}