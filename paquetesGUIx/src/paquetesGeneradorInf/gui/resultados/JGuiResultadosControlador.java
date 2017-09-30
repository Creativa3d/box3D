/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package paquetesGeneradorInf.gui.resultados;

import java.awt.event.*;

import ListDatos.*;

import ListDatos.estructuraBD.JFieldDef;
import ListDatos.estructuraBD.JFieldDefs;
import ListDatos.estructuraBD.JTableDef;
import impresionJasper.JInfGeneralJasper;
import java.util.logging.Level;
import java.util.logging.Logger;
import paquetesGeneradorInf.gui.JGuiConsultaDatos;
import paquetesGeneradorInf.gui.resultados.exportar.IExportar;
import paquetesGeneradorInf.gui.resultados.exportar.JExportarDBF;
import paquetesGeneradorInf.gui.resultados.exportar.JExportarExcel;
import paquetesGeneradorInf.gui.resultados.exportar.JExportarTexto;
import paquetesGeneradorInf.gui.resultados.exportar.JPanelExportar;
import utiles.IListaElementos;
import utiles.JDepuracion;
import utilesBD.filasPorColumnas.JTEEATRIBUTOS;
import utilesGUIx.ActionEventCZ;
import utilesGUIx.cargaMasiva.ICargaMasiva;
import utilesGUIx.cargaMasiva.JPlugInCargaMasiva;
import utilesGUIx.formsGenericos.*;
import utilesGUIx.formsGenericos.boton.JBotonRelacionado;
import utilesGUIx.formsGenericos.busqueda.*;
import utilesGUIx.formsGenericos.edicion.JPanelEDICIONGENERICO;
import utilesGUIx.msgbox.JMsgBox;

public class JGuiResultadosControlador  extends JT2GENERALBASE2 implements ICargaMasiva {
    private static final String mcsListado = "Listado";
    private static final String mcsListadoConfig = "Configurar Listado";
    private static final String mcsExportar = "Exportar";

    private IConsulta moConsulta;
    private JGuiConsultaDatos moConsultaDatos;

    /** Crea una nueva instancia de JT2PROV */
    public JGuiResultadosControlador(final JGuiConsultaDatos poConsulta) {
        super();
        moConsultaDatos = poConsulta;
        getParametros().setMostrarPantalla(poConsulta.getMostrarPantalla());
    }
    public void inicializar() {
        moConsulta = new IConsulta(){
            private JListDatos moList;

            public void refrescar(boolean pbPasarACache, boolean pbLimpiarCache) throws Exception {
                moList = new JListDatos();
                JListDatos loListAux = new JListDatos();
                moList.moServidor = moConsultaDatos.getServer();
                moList.msTabla = moConsultaDatos.getSelect().getFrom().getTablaUnion(0).getTabla2();
                loListAux.moServidor = moConsultaDatos.getServer();
                loListAux.msTabla = moList.msTabla;
                JSelect loSelect = (JSelect) moConsultaDatos.getSelect().clone();
                for(int i = 0 ; i < loSelect.getCampos().size(); i++){
                    JSelectCampo loCampo = (JSelectCampo) loSelect.getCampos().get(i);
                    JTableDef loTabla = moList.moServidor.getTableDefs().get(loCampo.getTabla());
                    if(loTabla==null){
                        moList.getFields().addField(
                                crearField(loCampo, moConsultaDatos.getCamposRapidosListaTablasExtra())
                                );
                        loSelect.getCampos().remove(loCampo);
                        i--;
                    }else{
                        if(loCampo.getNombre().equals(JSelect.mcsTodosCampos)){
                            for(int ii = 0 ; loTabla!=null && ii < loTabla.getCampos().size(); ii++){
                                JFieldDef loCampoF = loTabla.getCampos().get(ii);
                                loCampoF.setCaption( moConsultaDatos.getTextosForms().getCaption(loTabla.getNombre(), loCampoF.getNombre())   );
                                moList.getFields().addField(loCampoF.Clone());
                                loListAux.getFields().addField(loCampoF.Clone());
                            }
                        }else{
                            JFieldDef loField = loTabla.getCampos().get(loCampo.getNombre()).Clone();
                            if(loCampo.getCaption()!=null && !loCampo.getCaption().equals("")){
                                loField.setCaption(loCampo.getCaption());
                                moList.getFields().addField(loField);
                                loListAux.getFields().addField(loField.Clone());
    //                        }else{
    //                            moList.getFields().addField(new JFieldDef(loCampo.getNombre()));
                            }
                        }
                    }
                }
                recuperarDatos(loListAux,moConsultaDatos, loSelect);
            }
            private JTEEATRIBUTOS getAtrib(String psTabla, IListaElementos poListaAtrib){
                JTEEATRIBUTOS loResult = null;
                for(int i = 0 ; i < poListaAtrib.size(); i++){
                    JTEEATRIBUTOS loAux = (JTEEATRIBUTOS) poListaAtrib.get(i);
                    if(loAux.getList().msTabla.equalsIgnoreCase(psTabla)){
                        loResult = loAux;
                    }
                }
                return loResult;
            }
            private JFieldDef crearField(JSelectCampo loCampo, IListaElementos poListaAtrib) throws CloneNotSupportedException{
                JTEEATRIBUTOS loAtrib = getAtrib(loCampo.getTabla(), poListaAtrib);
                JFieldDef loField = loAtrib.getField(loCampo.getNombre()).Clone();
                loField.setTabla(loCampo.getTabla());
                return loField;
            }
            private void recuperarDatos(JListDatos poListAux, JGuiConsultaDatos poConsultaDatos, JSelect poSelectAux) throws Exception {
                poListAux.recuperarDatos(poSelectAux, false, poListAux.mclSelectNormal);
                if(poListAux.moveFirst()){
                    do{
                        moList.addNew();
                        int lIndexCons = 0;
                        for(int i = 0 ; i < moList.getFields().size(); i++){
                            JFieldDef loCampo = moList.getFields(i);
                            JFieldDef loCampoAux = poListAux.getFields().get(lIndexCons);//moList.getFields(i).getNombre()
                            //si es de bd directa//
                            if(loCampoAux!=null && loCampoAux.getTabla().equals(loCampo.getTabla()) &&
                               loCampoAux.getNombre().equalsIgnoreCase(loCampo.getNombre())
                               ){
                                loCampo.setValue(loCampoAux.getString());
                                lIndexCons++;
                            }else{
                                //si es de atributos//
                                //recuperamos la tabla de atributos
                                JTEEATRIBUTOS loAtrib = getAtrib(loCampo.getTabla(), poConsultaDatos.getCamposRapidosListaTablasExtra());
                                //buscamos la fila concreta
                                String[] lasValores = new String[loAtrib.getParametros().malPosiCodigos.length];
                                int[] lalCampos = new int[lasValores.length];
                                for(int lCampo = 0 ; lCampo < lasValores.length; lCampo++){
                                    lalCampos[lCampo] = lCampo;
                                    lasValores[lCampo] = poListAux.getFields().get(loAtrib.getField(lCampo).getNombre()).getString();
                                }
                                if(loAtrib.getList().buscar(
                                        JListDatos.mclTIgual,
                                        lalCampos,
                                        lasValores)){
                                    //establecemos el valor
                                    loCampoAux = loAtrib.getField(loCampo.getNombre());
                                    loCampo.setValue(loCampoAux.getString());

                                }
                                
                            }
                        }
                        moList.update(false);
                    }while(poListAux.moveNext());
                }
            }

            public JListDatos getList() {
                return moList;
            }

            public void addFilaPorClave(IFilaDatos poFila) throws Exception {
                try{
                    switch(poFila.getTipoModif()){
                        case JListDatos.mclBorrar:
                            moList.borrar(false);
                            break;
                        case JListDatos.mclEditar:
                            moList.getFields().cargar(poFila);
                            moList.update(false);
                            break;
                        case JListDatos.mclNuevo:
                            moList.addNew();
                            moList.getFields().cargar(poFila);
                            moList.update(false);
                            break;
                        default:
                            throw new Exception("Tipo modificación incorrecto");
                    }
                }catch(Exception e){
                    JDepuracion.anadirTexto(getClass().getName(), e);
                    JMsgBox.mensajeError(null, "Bien Guardado pero error al actualizar listadi " +  e.toString());
                }
            }

            public boolean getPasarCache() {
                return false;
            }


        };
        addBotones();
        moParametros.setLongitudCampos(getLongitudCampos());
        moParametros.setNombre(getNombre());
        new JPlugInCargaMasiva().procesarControlador(null, this);
        moParametros.setPlugInPasados(true);
//        getParametros().setMostrarPantalla(poMostrarPantalla);
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


    public IConsulta getConsulta() {
        return moConsulta;
    }

    public void mostrarFormPrinci() throws Exception {
        getParametros().getMostrarPantalla().mostrarFormPrinci(this, 800,600);
    }

    public void anadir() throws Exception {
        JTableDef loTable = moConsulta.getList().moServidor.getTableDefs().get(moConsulta.getList().msTabla);
        JListDatos loList = loTable.getListDatos();
        loList.moServidor=moConsulta.getList().moServidor;
        loList.addNew();

        JPanelEDICIONGENERICO loPanel = new JPanelEDICIONGENERICO();
        loPanel.setDatos(loList, this);
        getParametros().getMostrarPantalla().mostrarEdicion(loPanel, loPanel);
    }

    public void valoresDefecto(final JSTabla poTabla) throws Exception {
    }

    public void borrar(final int plIndex) throws Exception {
        moConsulta.getList().setIndex(plIndex);
        
        JTableDef loTable = moConsulta.getList().moServidor.getTableDefs().get(moConsulta.getList().msTabla);
        
        JListDatos loList = loTable.getListDatos();
        loList.moServidor=moConsulta.getList().moServidor;
        
        JSelect loSelect = loList.getSelect();
        JListDatosFiltroConj loFiltro = new JListDatosFiltroConj();
        
        JFieldDefs loCampos = moConsulta.getList().getFields();
        for(int i = 0 ; i < loCampos.size(); i++){
            if(loCampos.get(i).getPrincipalSN() && loCampos.get(i).getTabla().equalsIgnoreCase(loList.msTabla)){
                loFiltro.addCondicionAND(JListDatos.mclTIgual, loList.getFields().getIndiceDeCampo(loCampos.get(i).getNombre()), loCampos.get(i).getString());
            }
        }
        
        loFiltro.inicializar(loList);
        loSelect.getWhere().addCondicion(JListDatosFiltroConj.mclAND, loFiltro);
        loList.recuperarDatosNoCacheNormal(loSelect);
        if(loList.size()>1){
            throw new Exception("En la consulta falta algun campo principal de la tabla " + loList.msTabla);
        } else if(loList.size()<1){
            throw new Exception("No existe el registro de la " + loList.msTabla);
        }

        IFilaDatos loFila = (IFilaDatos)moConsulta.getList().moFila().clone();
        IResultado loResult = loList.borrar(true);
        if(!loResult.getBien()){
            throw new Exception(loResult.getMensaje());
        }
        loFila.setTipoModif (JListDatos.mclBorrar);
        datosactualizados(loFila);
    }

    private JListDatos getListTabla() throws Exception{
        JTableDef loTable = moConsulta.getList().moServidor.getTableDefs().get(moConsulta.getList().msTabla);
        
        JListDatos loList = loTable.getListDatos();
        loList.moServidor=moConsulta.getList().moServidor;
        
        JFieldDefs loCampos = loList.getFields();
        for(int i = 0 ; i < loCampos.size(); i++){
            loCampos.get(i).setCaption( moConsultaDatos.getTextosForms().getCaption(loList.msTabla, loCampos.get(i).getNombre())   );
        }
        return loList;
        
    }
    public void editar(final int plIndex) throws Exception {
        moConsulta.getList().setIndex(plIndex);
        
        JListDatos loList = getListTabla();
        
        JSelect loSelect = loList.getSelect();
        JListDatosFiltroConj loFiltro = new JListDatosFiltroConj();
        
        JFieldDefs loCampos = moConsulta.getList().getFields();
        for(int i = 0 ; i < loCampos.size(); i++){
            if(loCampos.get(i).getPrincipalSN() && loCampos.get(i).getTabla().equalsIgnoreCase(loList.msTabla)){
                loFiltro.addCondicionAND(JListDatos.mclTIgual, loList.getFields().getIndiceDeCampo(loCampos.get(i).getNombre()), loCampos.get(i).getString());
            }
        }
        
        loFiltro.inicializar(loList);
        loSelect.getWhere().addCondicion(JListDatosFiltroConj.mclAND, loFiltro);
        loList.recuperarDatosNoCacheNormal(loSelect);
        if(loList.size()>1){
            throw new Exception("En la consulta falta algun campo principal de la tabla " + loList.msTabla);
        } else if(loList.size()<1){
            throw new Exception("No existe el registro de la " + loList.msTabla);
        }

        JPanelEDICIONGENERICO loPanel = new JPanelEDICIONGENERICO();
        loPanel.setDatos(loList, this);

        getParametros().getMostrarPantalla().mostrarEdicion(loPanel, loPanel);
    }

    public String getNombre() {
        return moConsultaDatos.getNombre();
    }

    public int[] getLongitudCampos() {
        return null;
    }

    public void actionPerformed(ActionEventCZ e, int[] plIndex) throws Exception {
        if(e.getActionCommand().equals(mcsListado)){
            JInfGeneralJasper loInf = new JInfGeneralJasper();
            loInf.setImagenHeader(moConsultaDatos.getImpresionLogo());
            loInf.setEstiloLineas(moConsultaDatos.getImpresionLineas());
            loInf.generarListado(moConsulta.getList(), getPanel(), moConsultaDatos.getImpresionTitulo());
        }
        if(e.getActionCommand().equals(mcsListadoConfig)){
            JGuiResultadosImpresion loForm = new JGuiResultadosImpresion();
            loForm.setDatos(moConsultaDatos, moConsulta.getList(), this);
            moConsultaDatos.getMostrarPantalla().mostrarEdicion(
                    loForm,
                    null,
                    loForm,
                    JMostrarPantalla.mclEdicionFrame);
        }
        if(e.getActionCommand().equals(mcsExportar)){
            IExportar[] loExportar = new IExportar[4];
            loExportar[0] = new JExportarTexto(true, '\t');
            loExportar[1] = new JExportarTexto(false, ',');
            loExportar[2] = new JExportarDBF();
            loExportar[3] = new JExportarExcel();

            JPanelExportar loExp = new JPanelExportar();
            loExp.setDatos(moConsulta.getList(),loExportar, moConsultaDatos.getDatosGeneralesXML(), moConsultaDatos.getMostrarPantalla());
            moConsultaDatos.getMostrarPantalla().mostrarForm(
                    new JMostrarPantallaParam(
                        loExp, 500, 300, JMostrarPantalla.mclEdicionFrame, "Exportación"
                        )
                    );
            
            
        }

    }


    public void addBotones() {
        JPanelGeneralBotones retValue;

        retValue = getParametros().getBotonesGenerales();
        retValue.getListaBotones().clear();
        
//        retValue.getNuevo().setVisible(false);
//        retValue.getBorrar().setVisible(false);
//        retValue.getEditar().setVisible(false);

        retValue.addBotonPrincipal(new JBotonRelacionado(mcsListado, mcsListado, "/paquetesGeneradorInf/images/Print24.gif", this));
        retValue.addBotonPrincipal(new JBotonRelacionado(mcsListadoConfig, mcsListadoConfig, "/paquetesGeneradorInf/images/Preferences24.gif", this));
        retValue.addBotonPrincipal(new JBotonRelacionado(mcsExportar, mcsExportar, "/paquetesGeneradorInf/images/Export24.gif", this));

    }

    public JListDatos getTablaBase() {
        JListDatos loList=null;
        try {
            loList = moConsulta.getList().moServidor.getTableDefs().get(
                    moConsultaDatos.getSelect().getFrom().getTablaUnion(0).getTabla2()
                ).getListDatos();
            loList.moServidor = moConsulta.getList().moServidor;
        } catch (Throwable ex) {
            JDepuracion.anadirTexto(getClass().getName(), ex);
        }
        
        return loList;
    }

}