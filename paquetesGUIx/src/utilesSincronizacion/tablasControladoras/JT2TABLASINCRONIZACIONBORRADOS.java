/*
* JT2TABLASINCRONIZACIONBORRADOS.java
*
* Creado el 2/10/2008
*/
package utilesSincronizacion.tablasControladoras;

import utilesSincronizacion.forms.JPanelTABLASINCRONIZACIONBORRADOS;
import utilesSincronizacion.consultas.JTFORMTABLASINCRONIZACIONBORRADOS;
import java.awt.event.*;

import ListDatos.*;
import utilesGUIx.ActionEventCZ;

import utilesGUIx.formsGenericos.*;
import utilesGUIx.formsGenericos.busqueda.*;

import utilesSincronizacion.tablasExtend.*;
import utilesSincronizacion.forms.*;
import utilesSincronizacion.consultas.*;
import utilesSincronizacion.*;

public class JT2TABLASINCRONIZACIONBORRADOS  extends JT2GENERALBASE2 {
    private final JTFORMTABLASINCRONIZACIONBORRADOS moConsulta;
    private final IServerServidorDatos moServer;

    /** Crea una nueva instancia de JT2TABLASINCRONIZACIONBORRADOS */
    public JT2TABLASINCRONIZACIONBORRADOS(final IServerServidorDatos poServidorDatos, final IMostrarPantalla poMostrarPantalla) {
        super();
        moServer = poServidorDatos;
        moConsulta = new JTFORMTABLASINCRONIZACIONBORRADOS(poServidorDatos);
        moConsulta.crearSelectSimple();
        addBotones();
        moParametros.setLongitudCampos(getLongitudCampos());
        moParametros.setNombre(JTEETABLASINCRONIZACIONBORRADOS.msCTabla);
        getParametros().setMostrarPantalla(poMostrarPantalla);
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

    public IConsulta getConsulta() {
        return moConsulta;
    }

    public void mostrarFormPrinci() throws Exception {
        getParametros().getMostrarPantalla().mostrarFormPrinci(this, 530,300);
    }

    public void anadir() throws Exception {
        JTEETABLASINCRONIZACIONBORRADOS loTABLASINCRONIZACIONBORRADOS = new JTEETABLASINCRONIZACIONBORRADOS(moServer);
        loTABLASINCRONIZACIONBORRADOS.moList.addNew();
        valoresDefecto(loTABLASINCRONIZACIONBORRADOS);

        JPanelTABLASINCRONIZACIONBORRADOS loPanel = new JPanelTABLASINCRONIZACIONBORRADOS();
        loPanel.setDatos(loTABLASINCRONIZACIONBORRADOS, this, moConsulta);

        getParametros().getMostrarPantalla().mostrarEdicion(loPanel, loPanel);
    }

    public void valoresDefecto(final JSTabla poTabla) throws Exception {
    }

    public void borrar(final int plIndex) throws Exception {
        moConsulta.moList.setIndex(plIndex);
        JTEETABLASINCRONIZACIONBORRADOS loTABLASINCRONIZACIONBORRADOS = new JTEETABLASINCRONIZACIONBORRADOS(moServer);
        loTABLASINCRONIZACIONBORRADOS.moList.addNew();
        loTABLASINCRONIZACIONBORRADOS.getCODIGOBORRADO().setValue(moConsulta.moList.getFields(moConsulta.lPosiCODIGOBORRADO).getValue());
        loTABLASINCRONIZACIONBORRADOS.moList.update(false);

        loTABLASINCRONIZACIONBORRADOS.moList.moFila().setTipoModif(JListDatos.mclNada);
        IFilaDatos loFila = (IFilaDatos)moConsulta.moList.moFila().clone();
        IResultado loResult = loTABLASINCRONIZACIONBORRADOS.moList.borrar(true);
        if(!loResult.getBien()){
            throw new Exception(loResult.getMensaje());
        }
        loFila.setTipoModif (JListDatos.mclBorrar);
        datosactualizados(loFila);
    }

    public void editar(final int plIndex) throws Exception {
        moConsulta.moList.setIndex(plIndex);

        JTEETABLASINCRONIZACIONBORRADOS loTABLASINCRONIZACIONBORRADOS = new JTEETABLASINCRONIZACIONBORRADOS(moServer);

        JListDatosFiltroConj loFiltro = new JListDatosFiltroConj();
        loFiltro.addCondicion(JListDatosFiltroConj.mclAND,JListDatos.mclTIgual, 
            new int[]{
                JTEETABLASINCRONIZACIONBORRADOS.lPosiCODIGOBORRADO,
            },
            new String[]{
                moConsulta.moList.getFields(moConsulta.lPosiCODIGOBORRADO).getString(),
            });
        loTABLASINCRONIZACIONBORRADOS.recuperarFiltrados(loFiltro,false,false);

        valoresDefecto(loTABLASINCRONIZACIONBORRADOS);
        JPanelTABLASINCRONIZACIONBORRADOS loPanel = new JPanelTABLASINCRONIZACIONBORRADOS();
        loPanel.setDatos(loTABLASINCRONIZACIONBORRADOS, this, moConsulta);

        getParametros().getMostrarPantalla().mostrarEdicion(loPanel, loPanel);
    }

    public String getNombre() {
        return JTEETABLASINCRONIZACIONBORRADOS.msCTabla;
    }

    public int[] getLongitudCampos() {
        int[] loInt = new int[JTFORMTABLASINCRONIZACIONBORRADOS.mclNumeroCampos];

        loInt[JTFORMTABLASINCRONIZACIONBORRADOS.lPosiCODIGOBORRADO]=80;
        loInt[JTFORMTABLASINCRONIZACIONBORRADOS.lPosiTABLA]=80;
        loInt[JTFORMTABLASINCRONIZACIONBORRADOS.lPosiREGISTRO]=80;
        loInt[JTFORMTABLASINCRONIZACIONBORRADOS.lPosiNUMEROTRANSACSINCRO]=80;
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
