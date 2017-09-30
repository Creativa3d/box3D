/*
* JT2GUIXMENSAJESSEND.java
*
* Creado el 8/9/2012
*/
package utilesGUIxAvisos.tablasControladoras;

import utilesGUIxAvisos.consultas.JTFORMGUIXMENSAJESBD;
import utilesGUIxAvisos.tablasExtend.JTEEGUIXMENSAJESBD;
import utilesGUIx.ActionEventCZ;
import utilesGUIxAvisos.calendario.JDatosGenerales;
import java.awt.event.*;

import ListDatos.*;
import utilesGUIx.JGUIxConfigGlobalModelo;


import utilesGUIx.formsGenericos.*;
import utilesGUIx.formsGenericos.busqueda.*;

import utilesGUIxAvisos.tablasExtend.*;
import utilesGUIxAvisos.forms.*;
import utilesGUIxAvisos.consultas.*;

import utilesGUIx.formsGenericos.edicion.IFormEdicion;

public class JT2GUIXMENSAJESBD  extends JT2GENERALBASE2 {
    private final JTFORMGUIXMENSAJESBD moConsulta;
    private final IServerServidorDatos moServer;
    private IFilaDatos moFilaDatosRelac;
    private String msNomTabRelac = "";
    private final JDatosGenerales moDatosGenerales;

    /** Crea una nueva instancia de JT2GUIXMENSAJESSEND */
    public JT2GUIXMENSAJESBD(JDatosGenerales poDatosGenerales) {
        super();
        moDatosGenerales=poDatosGenerales;
        
        moServer = poDatosGenerales.getServer();
        moConsulta = new JTFORMGUIXMENSAJESBD(moServer);
        moConsulta.crearSelectSimple();
        addBotones();
        moParametros.setLongitudCampos(getLongitudCampos());
        moParametros.setNombre(moDatosGenerales.getTextosForms().getTexto(JTEEGUIXMENSAJESBD.msCTabla));
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

    public JT2GUIXMENSAJESBD(
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
        JTEEGUIXMENSAJESBD loGUIXMENSAJESSEND = new JTEEGUIXMENSAJESBD(moServer);
        loGUIXMENSAJESSEND.moList.addNew();
        valoresDefecto(loGUIXMENSAJESSEND);
        JPanelMensaje  loPanel = new JPanelMensaje();
        loPanel.setDatos(loGUIXMENSAJESSEND.getMensaje(), null, JGUIxConfigGlobalModelo.getInstancia().getDatosGeneralesAvisos().getPathPlantilla()
                , null, null, true
        );

        getParametros().getMostrarPantalla().mostrarEdicion((IFormEdicion)loPanel, loPanel);
    }

    public void valoresDefecto(final JSTabla poTabla) throws Exception {
    }

    public void borrar(final int plIndex) throws Exception {
        moConsulta.moList.setIndex(plIndex);
        JTEEGUIXMENSAJESBD loGUIXMENSAJESSEND = new JTEEGUIXMENSAJESBD(moServer);
        loGUIXMENSAJESSEND.moList.addNew();
        loGUIXMENSAJESSEND.getCODIGO().setValue(moConsulta.moList.getFields(moConsulta.lPosiCODIGO).getValue());
        loGUIXMENSAJESSEND.moList.update(false);

        loGUIXMENSAJESSEND.moList.moFila().setTipoModif(JListDatos.mclNada);
        IFilaDatos loFila = (IFilaDatos)loGUIXMENSAJESSEND.moList.moFila().clone();
        IResultado loResult = loGUIXMENSAJESSEND.borrar();
        if(!loResult.getBien()){
            throw new Exception(loResult.getMensaje());
        }
        loFila.setTipoModif (JListDatos.mclBorrar);
        datosactualizados(loFila);
    }

    public void editar(final int plIndex) throws Exception {
        moConsulta.moList.setIndex(plIndex);

        JTEEGUIXMENSAJESBD loGUIXMENSAJESSEND = new JTEEGUIXMENSAJESBD(moServer);

        JListDatosFiltroConj loFiltro = new JListDatosFiltroConj();
        loFiltro.addCondicion(JListDatosFiltroConj.mclAND,JListDatos.mclTIgual, 
            new int[]{
                JTEEGUIXMENSAJESBD.lPosiCODIGO
            },
            new String[]{
                moConsulta.moList.getFields(moConsulta.lPosiCODIGO).getString()
            });
        loGUIXMENSAJESSEND.recuperarFiltrados(loFiltro,false,false);

        valoresDefecto(loGUIXMENSAJESSEND);
        JPanelGUIXMENSAJESSEND loPanel = new JPanelGUIXMENSAJESSEND();
        loPanel.setDatos(moDatosGenerales,loGUIXMENSAJESSEND, this, moConsulta);
        loPanel.getParametros().setSoloLectura(true);
        getParametros().getMostrarPantalla().mostrarEdicion((IFormEdicion)loPanel, loPanel);
    }

    public String getNombre() {
        return moDatosGenerales.getTextosForms().getTexto(JTEEGUIXMENSAJESBD.msCTabla);
    }

    public int[] getLongitudCampos() {
//        int[] loInt = new int[JTFORMGUIXMENSAJESSEND.mclNumeroCampos];

//        loInt[JTFORMGUIXMENSAJESSEND.lPosiCODIGO]=80;
//        loInt[JTFORMGUIXMENSAJESSEND.lPosiGRUPO]=80;
//        loInt[JTFORMGUIXMENSAJESSEND.lPosiUSUARIO]=80;
//        loInt[JTFORMGUIXMENSAJESSEND.lPosiFECHA]=80;
//        loInt[JTFORMGUIXMENSAJESSEND.lPosiEMAILTO]=80;
//        loInt[JTFORMGUIXMENSAJESSEND.lPosiASUNTO]=80;
//        loInt[JTFORMGUIXMENSAJESSEND.lPosiTEXTO]=80;
//        loInt[JTFORMGUIXMENSAJESSEND.lPosiADJUNTOS]=80;
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
        retValue.getNuevo().setActivo(false);
        retValue.getBorrar().setActivo(false);
//        retValue.addBotonPrincipal(new JBotonRelacionado(mcsImpListado, mcsImpListado, "/images/Print16.gif", this));
        
    }


}