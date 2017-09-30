/*
* JT2GUIXPRIORIDAD.java
*
* Creado el 18/2/2012
*/
package utilesGUIxAvisos.tablasControladoras;

import utilesGUIxAvisos.consultas.JTFORMGUIXEVENTOSPRIORIDAD;
import utilesGUIxAvisos.tablasExtend.JTEEGUIXEVENTOSPRIORIDAD;
import utilesGUIx.ActionEventCZ;
import utilesGUIxAvisos.calendario.JDatosGenerales;
import java.awt.event.*;

import ListDatos.*;


import utilesGUIx.formsGenericos.*;
import utilesGUIx.formsGenericos.busqueda.*;

import utilesGUIxAvisos.tablasExtend.*;
import utilesGUIxAvisos.forms.*;
import utilesGUIxAvisos.consultas.*;

import utilesGUIx.formsGenericos.edicion.IFormEdicion;

public class JT2GUIXEVENTOSPRIORIDAD  extends JT2GENERALBASE2 {
    private final JTFORMGUIXEVENTOSPRIORIDAD moConsulta;
    private final IServerServidorDatos moServer;
    private IFilaDatos moFilaDatosRelac;
    private String msNomTabRelac = "";
    private final JDatosGenerales moDatosGenerales;

    /** Crea una nueva instancia de JT2GUIXPRIORIDAD */
    public JT2GUIXEVENTOSPRIORIDAD(JDatosGenerales poDatosGenerales) {
        super();
        moDatosGenerales=poDatosGenerales;
        
        moServer = poDatosGenerales.getServer();
        moConsulta = new JTFORMGUIXEVENTOSPRIORIDAD(moServer);
        moConsulta.crearSelectSimple();
        addBotones();
        moParametros.setLongitudCampos(getLongitudCampos());
        moParametros.setNombre(moDatosGenerales.getTextosForms().getTexto(JTEEGUIXEVENTOSPRIORIDAD.msCTabla));
        getParametros().setMostrarPantalla(poDatosGenerales.getMostrarPantalla());
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

    public JT2GUIXEVENTOSPRIORIDAD(
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
        JTEEGUIXEVENTOSPRIORIDAD loGUIXPRIORIDAD = new JTEEGUIXEVENTOSPRIORIDAD(moServer);
        loGUIXPRIORIDAD.moList.addNew();
        valoresDefecto(loGUIXPRIORIDAD);

        JPanelGUIXEVENTOSPRIORIDAD loPanel = new JPanelGUIXEVENTOSPRIORIDAD();
        loPanel.setDatos(moDatosGenerales,loGUIXPRIORIDAD, this, moConsulta);

        getParametros().getMostrarPantalla().mostrarEdicion((IFormEdicion)loPanel, loPanel);
    }

    public void valoresDefecto(final JSTabla poTabla) throws Exception {
    }

    public void borrar(final int plIndex) throws Exception {
        moConsulta.moList.setIndex(plIndex);
        JTEEGUIXEVENTOSPRIORIDAD loGUIXPRIORIDAD = new JTEEGUIXEVENTOSPRIORIDAD(moServer);
        loGUIXPRIORIDAD.moList.addNew();
        loGUIXPRIORIDAD.getGUIXPRIORIDAD().setValue(moConsulta.moList.getFields(moConsulta.lPosiGUIXPRIORIDAD).getValue());
        loGUIXPRIORIDAD.moList.update(false);

        loGUIXPRIORIDAD.moList.moFila().setTipoModif(JListDatos.mclNada);
        IFilaDatos loFila = (IFilaDatos)loGUIXPRIORIDAD.moList.moFila().clone();
        IResultado loResult = loGUIXPRIORIDAD.borrar();
        if(!loResult.getBien()){
            throw new Exception(loResult.getMensaje());
        }
        loFila.setTipoModif (JListDatos.mclBorrar);
        datosactualizados(loFila);
    }

    public void editar(final int plIndex) throws Exception {
        moConsulta.moList.setIndex(plIndex);

        JTEEGUIXEVENTOSPRIORIDAD loGUIXPRIORIDAD = new JTEEGUIXEVENTOSPRIORIDAD(moServer);

        JListDatosFiltroConj loFiltro = new JListDatosFiltroConj();
        loFiltro.addCondicion(JListDatosFiltroConj.mclAND,JListDatos.mclTIgual, 
            new int[]{
                JTEEGUIXEVENTOSPRIORIDAD.lPosiGUIXPRIORIDAD
            },
            new String[]{
                moConsulta.moList.getFields(moConsulta.lPosiGUIXPRIORIDAD).getString()
            });
        loGUIXPRIORIDAD.recuperarFiltrados(loFiltro,false,false);

        valoresDefecto(loGUIXPRIORIDAD);
        JPanelGUIXEVENTOSPRIORIDAD loPanel = new JPanelGUIXEVENTOSPRIORIDAD();
        loPanel.setDatos(moDatosGenerales,loGUIXPRIORIDAD, this, moConsulta);

        getParametros().getMostrarPantalla().mostrarEdicion((IFormEdicion)loPanel, loPanel);
    }

    public String getNombre() {
        return moDatosGenerales.getTextosForms().getTexto(JTEEGUIXEVENTOSPRIORIDAD.msCTabla);
    }

    public int[] getLongitudCampos() {
//        int[] loInt = new int[JTFORMGUIXPRIORIDAD.mclNumeroCampos];

//        loInt[JTFORMGUIXPRIORIDAD.lPosiGUIXPRIORIDAD]=80;
//        loInt[JTFORMGUIXPRIORIDAD.lPosiNOMBRE]=80;
//        loInt[JTFORMGUIXPRIORIDAD.lPosiCOLOR]=80;
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


}
