/*
* JT2TABLASINCRONIZACIONGENERAL.java
*
* Creado el 2/10/2008
*/
package utilesSincronizacion.tablasControladoras;

import utilesSincronizacion.forms.JPanelTABLASINCRONIZACIONGENERAL;
import utilesSincronizacion.consultas.JTFORMTABLASINCRONIZACIONGENERAL;
import java.awt.event.*;

import ListDatos.*;
import utilesGUIx.ActionEventCZ;

import utilesGUIx.formsGenericos.*;
import utilesGUIx.formsGenericos.edicion.*;
import utilesGUIx.formsGenericos.boton.*;
import utilesGUIx.formsGenericos.busqueda.*;

import utilesSincronizacion.tablasExtend.*;
import utilesSincronizacion.forms.*;
import utilesSincronizacion.consultas.*;
import utilesSincronizacion.*;

public class JT2TABLASINCRONIZACIONGENERAL  extends JT2GENERALBASE2 {
    private final JTFORMTABLASINCRONIZACIONGENERAL moConsulta;
    private final IServerServidorDatos moServer;

    /** Crea una nueva instancia de JT2TABLASINCRONIZACIONGENERAL */
    public JT2TABLASINCRONIZACIONGENERAL(final IServerServidorDatos poServidorDatos, final IMostrarPantalla poMostrarPantalla) {
        super();
        moServer = poServidorDatos;
        moConsulta = new JTFORMTABLASINCRONIZACIONGENERAL(poServidorDatos);
        moConsulta.crearSelectSimple();
        addBotones();
        moParametros.setLongitudCampos(getLongitudCampos());
        moParametros.setNombre(JTEETABLASINCRONIZACIONGENERAL.msCTabla);
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
        JTEETABLASINCRONIZACIONGENERAL loTABLASINCRONIZACIONGENERAL = new JTEETABLASINCRONIZACIONGENERAL(moServer);
        loTABLASINCRONIZACIONGENERAL.moList.addNew();
        valoresDefecto(loTABLASINCRONIZACIONGENERAL);

        JPanelTABLASINCRONIZACIONGENERAL loPanel = new JPanelTABLASINCRONIZACIONGENERAL();
        loPanel.setDatos(loTABLASINCRONIZACIONGENERAL, this, moConsulta);

        getParametros().getMostrarPantalla().mostrarEdicion(loPanel, loPanel);
    }

    public void valoresDefecto(final JSTabla poTabla) throws Exception {
    }

    public void borrar(final int plIndex) throws Exception {
        moConsulta.moList.setIndex(plIndex);
        JTEETABLASINCRONIZACIONGENERAL loTABLASINCRONIZACIONGENERAL = new JTEETABLASINCRONIZACIONGENERAL(moServer);
        loTABLASINCRONIZACIONGENERAL.moList.addNew();
        loTABLASINCRONIZACIONGENERAL.getNOMBRE().setValue(moConsulta.moList.getFields(moConsulta.lPosiNOMBRE).getValue());
        loTABLASINCRONIZACIONGENERAL.moList.update(false);

        loTABLASINCRONIZACIONGENERAL.moList.moFila().setTipoModif(JListDatos.mclNada);
        IFilaDatos loFila = (IFilaDatos)moConsulta.moList.moFila().clone();
        IResultado loResult = loTABLASINCRONIZACIONGENERAL.moList.borrar(true);
        if(!loResult.getBien()){
            throw new Exception(loResult.getMensaje());
        }
        loFila.setTipoModif (JListDatos.mclBorrar);
        datosactualizados(loFila);
    }

    public void editar(final int plIndex) throws Exception {
        moConsulta.moList.setIndex(plIndex);

        JTEETABLASINCRONIZACIONGENERAL loTABLASINCRONIZACIONGENERAL = new JTEETABLASINCRONIZACIONGENERAL(moServer);

        JListDatosFiltroConj loFiltro = new JListDatosFiltroConj();
        loFiltro.addCondicion(JListDatosFiltroConj.mclAND,JListDatos.mclTIgual, 
            new int[]{
                JTEETABLASINCRONIZACIONGENERAL.lPosiNOMBRE,
            },
            new String[]{
                moConsulta.moList.getFields(moConsulta.lPosiNOMBRE).getString(),
            });
        loTABLASINCRONIZACIONGENERAL.recuperarFiltrados(loFiltro,false,false);

        valoresDefecto(loTABLASINCRONIZACIONGENERAL);
        JPanelTABLASINCRONIZACIONGENERAL loPanel = new JPanelTABLASINCRONIZACIONGENERAL();
        loPanel.setDatos(loTABLASINCRONIZACIONGENERAL, this, moConsulta);

        getParametros().getMostrarPantalla().mostrarEdicion(loPanel, loPanel);
    }

    public String getNombre() {
        return JTEETABLASINCRONIZACIONGENERAL.msCTabla;
    }

    public int[] getLongitudCampos() {
        int[] loInt = new int[JTFORMTABLASINCRONIZACIONGENERAL.mclNumeroCampos];

        loInt[JTFORMTABLASINCRONIZACIONGENERAL.lPosiNOMBRE]=80;
        loInt[JTFORMTABLASINCRONIZACIONGENERAL.lPosiVALOR]=80;
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
