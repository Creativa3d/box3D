/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesFX.panelesGenericos;

import ListDatos.*;
import ListDatos.estructuraBD.JFieldDef;
import java.io.IOException;
import java.util.Iterator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import utiles.IListaElementos;
import utiles.JDepuracion;
import utiles.JListaElementos;
import utilesFX.IFieldControl;
import utilesFX.JCMBLinea;
import utilesFX.JFXConfigGlobal;
import utilesFX.JFieldConComboBox;
import utilesFX.formsGenericos.JMostrarPantalla;
import utilesFX.formsGenericos.JPanelGenerico;
import utilesFX.msgbox.JMsgBox;
import utilesGUIx.JGUIxConfigGlobalModelo;
import utilesGUIx.formsGenericos.*;
import utilesGUIx.formsGenericos.busqueda.*;
import utilesGUIx.panelesGenericos.JConsulta;
import utilesGUIx.panelesGenericos.JPanelBusquedaParametros;

/**Componete para presentar un código y su descripción*/
public class JPanelBusquedaCombo extends GridPane implements IFieldControl {
    // Variables declaration - do not modify                     
    @FXML private Button btnAnadir;
    @FXML private Button btnBuscar;
    @FXML private ComboBox<JCMBLinea> cmbCodigo;
    @FXML private Label lblNombre;
    // End of variables declaration                   

    //indica si se bloquea o no el lostfocus del texto
//    private boolean mbBloq = false;

    private JPanelBusquedaParametros moParam;
    private boolean mbRefrescar = true;
    private IListaElementos<ChangeListener<JCMBLinea>> moItemListeners = new JListaElementos<ChangeListener<JCMBLinea>>();
    private JFieldConComboBox moCodigoModelo;

    /**Constructo*/
    public JPanelBusquedaCombo() {
        FXMLLoader loader = new FXMLLoader();
        loader.setRoot(this);
        loader.setLocation(this.getClass().getResource("/utilesFX/panelesGenericos/JPanelBusquedaCombo.fxml"));
        loader.setController(this);
        try {
            final Node root = (Node)loader.load();
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        } 
        moCodigoModelo = new JFieldConComboBox(cmbCodigo);
        moCodigoModelo.borrarTodo();
        cmbCodigo.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<JCMBLinea>() {
            @Override
            public void changed(ObservableValue<? extends JCMBLinea> observable, JCMBLinea oldValue, JCMBLinea newValue) {
                itemStateChanged(observable, oldValue, newValue);
            }
        });
                
                
        
                
        cmbCodigo.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent t) {
                cmbCodigoKeyPressed(t);
            }
        });
        cmbCodigo.focusedProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                if(t1.booleanValue()){
                    cmbCodigoFocusGained();
                }
            }
        });        
        btnAnadir.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                btnAnadirActionPerformed();
            }
        });
        btnBuscar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                btnBuscarActionPerformed();
            }
        });

    }

    public void setDatos(JPanelBusquedaParametros poParam) throws Exception {
        moParam = poParam;
        moParam.inicializarPlugIn();
        btnAnadir.setVisible((moParam.moControlador != null));
        refrescar();
    }

    /**
     * establecemos una tabla relacionada con varias descripciones
     * @param poControlador Controlador
     * @param poTabla Tabla a mostrar
     * @param plCamposPrincipales Lista de posiciones de campos principales
     * @param palDescripciones Lista de posiciones de campos para la descripción
     * @param pbMensajeSiNoExiste Si presenta un mensaje si no existe
     * @param pasTextosDescripciones Lista de textos previos a las descripciones
     * @param pbConDatos Si la tabla viene con todos los datos y no hace falta recuperar del servidor
     */
    public void setDatos(IPanelControlador poControlador, JSTabla poTabla, int[] plCamposPrincipales, int[] palDescripciones, boolean pbMensajeSiNoExiste, boolean pbConDatos, String[] pasTextosDescripciones) throws Exception {
        JPanelBusquedaParametros loParam = new JPanelBusquedaParametros();

        loParam.moControlador = poControlador;
        loParam.moTabla = poTabla;
        loParam.mlCamposPrincipales = plCamposPrincipales;
        loParam.malDescripciones = palDescripciones;
        loParam.mbMensajeSiNoExiste = pbMensajeSiNoExiste;
        loParam.masTextosDescripciones = pasTextosDescripciones;
        loParam.mbConDatos = pbConDatos;

        setDatos(loParam);
    }

    /**
     * establecemos una tabla relacionada con varias descripciones
     * @param poControlador Controlador
     * @param poTabla Tabla a mostrar
     * @param plCamposPrincipales Lista de posiciones de campos principales
     * @param palDescripciones Lista de posiciones de campos para la descripción
     * @param pbMensajeSiNoExiste Si presenta un mensaje si no existe
     */
    public void setDatos(IPanelControlador poControlador, JSTabla poTabla, int[] plCamposPrincipales, int[] palDescripciones, boolean pbMensajeSiNoExiste) throws Exception {
        setDatos(poControlador, poTabla, plCamposPrincipales, palDescripciones, pbMensajeSiNoExiste, false, null);
    }

    /**
     * establecemos una tabla relacionada con varias descripciones
     * @param plCampoPrincipal campo principal
     * @param poControlador Controlador
     * @param poTabla Tabla a mostrar
     * @param palDescripciones Lista de posiciones de campos para la descripción
     * @param pbMensajeSiNoExiste Si presenta un mensaje si no existe
     * @param pbConDatos Si la tabla viene con todos los datos y no hace falta recuperar del servidor
     * @param pasTextosDescripciones Lista de textos previos a las descripciones
     */
    public void setDatos(IPanelControlador poControlador, JSTabla poTabla, int plCampoPrincipal, int[] palDescripciones, boolean pbMensajeSiNoExiste, boolean pbConDatos, String[] pasTextosDescripciones) throws Exception {
        setDatos(poControlador, poTabla, new int[]{plCampoPrincipal}, palDescripciones, pbMensajeSiNoExiste, pbConDatos, pasTextosDescripciones);
    }

    /**
     * establecemos una tabla relacionada con varias descripciones
     * @param plCampoPrincipal campo principal
     * @param poControlador Controlador
     * @param poTabla Tabla a mostrar
     * @param palDescripciones Lista de posiciones de campos para la descripción
     * @param pbMensajeSiNoExiste Si presenta un mensaje si no existe
     * @param pbConDatos Si la tabla viene con todos los datos y no hace falta recuperar del servidor
     * @param pasTextosDescripciones Lista de textos previos a las descripciones
     */
    public void setDatos(IPanelControlador poControlador, JSTabla poTabla, int plCampoPrincipal, int[] palDescripciones, boolean pbMensajeSiNoExiste, String[] pasTextosDescripciones) throws Exception {
        setDatos(poControlador, poTabla, plCampoPrincipal, palDescripciones, pbMensajeSiNoExiste, false, pasTextosDescripciones);
    }

    /**
     * establecemos una tabla relacionada con varias descripciones
     * @param plCampoPrincipal campo principal
     * @param poControlador Controlador
     * @param poTabla Tabla a mostrar
     * @param palDescripciones Lista de posiciones de campos para la descripción
     * @param pbMensajeSiNoExiste Si presenta un mensaje si no existe
     * @param pbConDatos Si la tabla viene con todos los datos y no hace falta recuperar del servidor
     */
    public void setDatos(IPanelControlador poControlador, JSTabla poTabla, int plCampoPrincipal, int[] palDescripciones, boolean pbMensajeSiNoExiste, boolean pbConDatos) throws Exception {
        setDatos(poControlador, poTabla, plCampoPrincipal, palDescripciones, pbMensajeSiNoExiste, pbConDatos, null);
    }

    /**
     * establecemos una tabla relacionada con varias descripciones
     * @param plCampoPrincipal campo principal
     * @param poControlador Controlador
     * @param poTabla Tabla a mostrar
     * @param palDescripciones Lista de posiciones de campos para la descripción
     * @param pbMensajeSiNoExiste Si presenta un mensaje si no existe
     */
    public void setDatos(IPanelControlador poControlador, JSTabla poTabla, int plCampoPrincipal, int[] palDescripciones, boolean pbMensajeSiNoExiste) throws Exception {
        setDatos(poControlador, poTabla, plCampoPrincipal, palDescripciones, pbMensajeSiNoExiste, false);
    }

    /**
     * establecemos una tabla relacionada con varias descripciones
     * @param poTabla Tabla a mostrar
     * @param plCamposPrincipales Lista posiciones de campos principales
     * @param palDescripciones Lista de posiciones de campos para la descripción
     * @param pbMensajeSiNoExiste Si presenta un mensaje si no existe
     */
    public void setDatos(JSTabla poTabla, int[] plCamposPrincipales, int[] palDescripciones, boolean pbMensajeSiNoExiste) throws Exception {
        setDatos(null, poTabla, plCamposPrincipales, palDescripciones, pbMensajeSiNoExiste);
    }

    /**
     * establecemos una tabla relacionada con varias descripciones
     * @param palDescripcion posición de la descripción
     * @param poTabla Tabla a mostrar
     * @param plCamposPrincipales posición del campo principal
     * @param pbMensajeSiNoExiste Si presenta un mensaje si no existe
     */
    public void setDatos(JSTabla poTabla, int[] plCamposPrincipales, int palDescripcion, boolean pbMensajeSiNoExiste) throws Exception {
        setDatos(null, poTabla, plCamposPrincipales, new int[]{palDescripcion}, pbMensajeSiNoExiste);
    }

    /**
     * establecemos una tabla relacionada con varias descripciones y una clave
     * @param plCampoPrincipal posición del campo principal
     * @param palDescripciones Lista de posiciones de las descripciones
     * @param poTabla Tabla a mostrar
     * @param pbMensajeSiNoExiste Si presenta un mensaje si no existe
     */
    public void setDatos(JSTabla poTabla, int plCampoPrincipal, int[] palDescripciones, boolean pbMensajeSiNoExiste) throws Exception {
        setDatos(null, poTabla, plCampoPrincipal, palDescripciones, pbMensajeSiNoExiste, false);
    }

    /**
     * establecemos una tabla relacionada con una sola descripcion y una clave
     * @param plCampoPrincipal posición del campo principal
     * @param palDescripcion posición de la descripción
     * @param poTabla Tabla a mostrar
     * @param pbMensajeSiNoExiste Si presenta un mensaje si no existe
     */
    public void setDatos(JSTabla poTabla, int plCampoPrincipal, int palDescripcion, boolean pbMensajeSiNoExiste) throws Exception {
        setDatos(null, poTabla, plCampoPrincipal, new int[]{palDescripcion}, pbMensajeSiNoExiste, false);
    }

    /**
     * Añade un listener text
     * @param l oyente
     */
    public void addFocusListenerText(ChangeListener<Boolean> l) {
        cmbCodigo.focusedProperty().addListener(l);
    }

    /**
     * borra un listener text
     * @param l oyente
     */
    public void removeFocusListenerText(ChangeListener<Boolean> l) {
        cmbCodigo.focusedProperty().removeListener(l);
    }

    /**
     * Añade un listener añadir
     * @param l oyente
     */
    public void setOnActionBuscar(ActionListenerBuscar l) {
        btnBuscar.setOnAction(new ActionListenerWrapper(null, l));
    }


    /**
     * Añade un listener añadir
     * @param l oyente
     */
    public void setOnActionAnadir(ActionListenerAnadir l) {
        btnAnadir.setOnAction(new ActionListenerWrapper(l, null));
    }

    public void itemStateChanged(ObservableValue<? extends JCMBLinea> ov, JCMBLinea t, JCMBLinea t1) {
        try {
            cmbCodigo.setTooltip(new Tooltip(moCodigoModelo.msDevolverDescri()));
        } catch (Exception ex) {
        }
        for (int i = 0; i < moItemListeners.size(); i++) {
            ChangeListener<JCMBLinea> loItem = (ChangeListener<JCMBLinea>) moItemListeners.get(i);
            loItem.changed(ov, t, t1);
        }
    }

    /**
     * Añade un listener text
     * @param l oyente
     */
    public void addItemListener(ChangeListener<JCMBLinea> l) {
        moItemListeners.add(l);
    }

    /**
     * borra un listener text
     * @param l oyente
     */
    public void removeItemListener(ChangeListener<JCMBLinea> l) {
        moItemListeners.remove(l);
    }


    /**
     * Devuelve el label
     * @return label
     */
    public String getLabel() {
        return lblNombre.getText();
    }

    /**
     * Establece el label
     * @param psTexto label
     */
    public void setLabel(String psTexto) {
        lblNombre.setText(psTexto);
    }


    /**
     * establece el valor en el combo
     * Si el valor es diferente al valor actual se pondra en rojo
     * @param poValor valor
     */
    public void setText(Object poValor) {
        setValueConOriginal(poValor, true);
    }
    /**
     * establece el valor en el combo
     * @param poValor valor
     */
    public void setValueTabla(Object poValor) {
        setValueConOriginal(poValor, true);
    }
    /**
     * posicioona el valor en el comob
     * si pbOriginal es true se pone como valor y al mismo tiempo como valor original y se vera en negro
     * si pbOriginal es false Si el valor es diferente al valor actual se pondra en rojo 
     * @param poValor valor
     */
    public void setValueConOriginal(Object poValor, boolean pbOriginal) {
        String lsValor;
        if (poValor == null) {
            lsValor = "";
        } else {
            lsValor = poValor.toString();
        }
        if (lsValor.length() == 0) {
            lsValor = JFilaDatosDefecto.mcsSeparacion1;
        } else {
            if (!lsValor.substring(lsValor.length() - 1).equals(JFilaDatosDefecto.mcsSeparacion1)) {
                lsValor += JFilaDatosDefecto.mcsSeparacion1;
            }
        }
        if(pbOriginal){
            moCodigoModelo.setValueTabla(lsValor);
        }else{
            moCodigoModelo.mbSeleccionarClave(lsValor);
        }
    }

    public void lanzarBusqueda() {
        btnBuscarActionPerformed();
    }

    /**
     * Devuelve el valor
     * @return valor
     */
    public Object getValueTabla() {
        return getText();
    }

    /**
     * Devuelve el texto
     * @return texto
     */
    public String getText() {
        return moCodigoModelo.getFilaActual().msCampo(0);
    }

    /**
     * Devuelve el texto del campo plIndex, este sirve para campos compuestos
     * @return texto
     * @param plIndex Índice del campo a devolver
     */
    public String getText(int plIndex) {
        return moCodigoModelo.getFilaActual().msCampo(plIndex);
    }
    
    @Override
    public void requestFocus(){
        super.requestFocus();
        cmbCodigo.requestFocus();
    }
    
    /**Establece el foco*/
    public void setFocus() {
        requestFocus();
    }


    private void refrescar() throws Exception {
        JConsulta loCons = new JConsulta(moParam.moTabla, moParam.mbConDatos);
        loCons.refrescar(false, false);
        String lsAux = moCodigoModelo.msDevolverValorActualCombo();
        rellenarCombo(loCons.getList());
        setValueTabla(lsAux);
        mbRefrescar = false;
    }

    private void rellenarCombo(JListDatos poList) {
        moCodigoModelo.borrarTodo();
        StringBuilder lsAux;
        String lsClaveS;
        String lsUltValor = "";
        String lsDescriS = "";
        IFilaDatos loFilaDatos;
        moCodigoModelo.addLinea("", "");
        Iterator enum1 = poList.iterator();
        lsAux = new StringBuilder(25);
        for (; enum1.hasNext();) {
            loFilaDatos = (IFilaDatos) enum1.next();
            lsAux.setLength(0);
            for (int i = 0; i < moParam.mlCamposPrincipales.length; i++) {
                lsAux.append(loFilaDatos.msCampo(moParam.mlCamposPrincipales[i]));
                lsAux.append(JFilaDatosDefecto.mcsSeparacion1);
            }
            lsClaveS = lsAux.toString();
            lsAux.setLength(0);
            for (int i = 0; i < moParam.malDescripciones.length; i++) {
                if (moParam.masTextosDescripciones != null) {
                    if (moParam.masTextosDescripciones[i] != null && !moParam.masTextosDescripciones[i].equals("")) {
                        lsAux.append(moParam.masTextosDescripciones[i]);
                        lsAux.append(' ');
                    }
                }
                if (moParam.mbTrim) {
                    lsAux.append(loFilaDatos.msCampo(moParam.malDescripciones[i]).trim());
                } else {
                    lsAux.append(loFilaDatos.msCampo(moParam.malDescripciones[i]));
                }
                lsAux.append(' ');
            }
            lsDescriS = lsAux.toString();
            if ((lsUltValor.compareTo(lsClaveS) != 0) && (lsDescriS.compareTo("") != 0)) {
                moCodigoModelo.addLinea(lsDescriS, lsClaveS);
                lsUltValor = lsClaveS;
            }
        }

    }
    public String getDescripcion(){
        return cmbCodigo.getSelectionModel().getSelectedItem().getDescripcion();
    }

    private void cmbCodigoKeyPressed(KeyEvent evt) {                                     
        if (evt.getCode() == KeyCode.F3 && btnBuscar.isVisible()) {
            btnBuscarActionPerformed();
        }
        if (evt.getCode() == KeyCode.F2 && btnAnadir.isVisible()) {
            btnAnadirActionPerformed();
        }

    }                                    

    private void cmbCodigoFocusGained() {                                      
        try {
            if (mbRefrescar) {
                refrescar();
            }
        } catch (Exception e) {
            JFXConfigGlobal.getInstancia().getMostrarPantalla().mensajeErrorYLog(this, e, null);
        }

    }                                     

    private void btnAnadirActionPerformed() {                                          
        anadir();
    }                                         

    private void btnBuscarActionPerformed() {                                          
        buscar();
    }                                         
    private void anadir() {
        try {
            if (moParam.moControlador == null) {
                JMsgBox.mensajeInformacion(this, "No se puede editar datos");
            } else {
                //probamos a llamar al metodo del controlador
                try {
                    if (moParam.mbEdicionLista) {
                        moParam.moControlador.mostrarFormPrinci();
//                        busquedaIndice(moParam.moControlador.getIndex());
                    } else {
                        JListDatos loDatosEdicion = null;
                        loDatosEdicion = moParam.moControlador.getConsulta().getList();
                        try {
                            if (moCodigoModelo.getFilaActual().msCampo(0).equals("")) {
                                mbRefrescar = true;
                                moParam.moControlador.anadir();
                            } else {
                                IFilaDatos loFila = new JFilaDatosDefecto();
                                //buscamos el codigo
                                JListDatosFiltroConj loFiltro = new JListDatosFiltroConj();
                                int lPrinci = 0;
                                for (int lIndex = 0; lIndex < loDatosEdicion.getFields().count(); lIndex++) {
                                    if (loDatosEdicion.getFields(lIndex).getPrincipalSN()) {
                                        String lsValor = "";
                                        lsValor = moCodigoModelo.getFilaActual().msCampo(lPrinci);
                                        lPrinci++;
                                        loFila.addCampo(lsValor);
                                        loFiltro.addCondicion(JListDatosFiltroConj.mclAND,
                                                JListDatos.mclTIgual, lIndex, lsValor);
                                    } else {
                                        loFila.addCampo("");
                                    }
                                }

                                loDatosEdicion.getFiltro().Clear();
                                loDatosEdicion.filtrarNulo();
                                loDatosEdicion.getFiltro().addCondicion(
                                        JListDatosFiltroConj.mclAND, loFiltro);
                                loDatosEdicion.filtrar();
                                if (loDatosEdicion.moveFirst()) {
                                    JListDatosBookMark loBook = loDatosEdicion.getBookmark();
                                    loDatosEdicion.getFiltro().Clear();
                                    loDatosEdicion.filtrarNulo();
                                    loDatosEdicion.setBookmark(loBook);
                                    moParam.moControlador.editar(loDatosEdicion.getIndex());
                                    mbRefrescar = true;
                                } else {
                                    loDatosEdicion.getFiltro().Clear();
                                    loDatosEdicion.filtrarNulo();

                                    loFila.setTipoModif(JListDatos.mclNuevo);
                                    moParam.moControlador.getConsulta().addFilaPorClave(loFila);
                                    loDatosEdicion.moveLast();

                                    moParam.moControlador.editar(loDatosEdicion.getIndex());
                                    mbRefrescar = true;
                                }

                            }
                        } finally {
                            if (loDatosEdicion != null) {
                                loDatosEdicion.getFiltro().Clear();
                                loDatosEdicion.filtrarNulo();
                            }
                        }
                    }
                } catch (Exception e) {
                    JDepuracion.anadirTexto(getClass().getName(), e);
                    JDepuracion.anadirTexto(JDepuracion.mclWARNING, getClass().getName(), "Presentación de form estandar");

                            JFXConfigGlobal.getInstancia().getMostrarPantalla().mostrarFormPrinci(moParam.moControlador, 640, 430, JPanelGenerico.mclTipo, JMostrarPantalla.mclEdicionFrame);

//                    JFormGenerico loForm = new JFormGenerico(moParam.moControlador);
//                    loForm.show();
                }
            }
        } catch (Exception e) {
            JFXConfigGlobal.getInstancia().getMostrarPantalla().mensajeErrorYLog(this, e, null);
        }
    }

    private void buscar() {
        if (moParam.moTabla != null) {
            JListDatosFiltroConj loFiltro = moParam.moTabla.moList.getFiltro().Clone();
            try {
                final JBusqueda loBusq = new JBusqueda(new JConsulta(moParam.moTabla, moParam.mbConDatos), moParam.moTabla.moList.msTabla);
                loBusq.mlAlto = moParam.mlAlto;
                loBusq.mlAncho = moParam.mlAncho;
                loBusq.mbFiltro = moParam.mbFiltro;
                loBusq.getParametros().msTipoFiltroRapido = moParam.msTipoFiltroRapido;
                loBusq.getParametros().setColoresTabla(moParam.moColores);
                loBusq.getParametros().setCallBack((IPanelControlador poControlador) -> {
                    busquedaIndice(loBusq.getIndex());
                });
                JFXConfigGlobal.getInstancia().getMostrarPantalla().mostrarFormPrinci(loBusq, loBusq.mlAncho, loBusq.mlAlto, 0 , IMostrarPantalla.mclEdicionFrame);

            } catch (Exception e) {
                JFXConfigGlobal.getInstancia().getMostrarPantalla().mensajeErrorYLog(this, e, null);
            } finally {
                if (loFiltro.mbAlgunaCond()) {
                    moParam.moTabla.moList.getFiltro().addCondicion(JListDatosFiltroConj.mclAND, loFiltro);
                }
                cmbCodigo.requestFocus();
            }
        }
    }

    private void busquedaIndice(int plIndex) {

        if (plIndex >= 0) {
            moParam.moTabla.moList.setIndex(plIndex);
            StringBuilder lsCad = new StringBuilder();
            for (int i = 0; i < moParam.mlCamposPrincipales.length; i++) {
                lsCad.append(moParam.moTabla.moList.getFields().get(moParam.mlCamposPrincipales[i]).getString());
                lsCad.append(JFilaDatosDefecto.mcsSeparacion1);
            }
            moCodigoModelo.setValueTabla(lsCad.toString());
        }

    }

    /**Establecemos el campo de la BD*/
    public void setField(final JFieldDef poCampo) {
        setLabel(poCampo.getCaption());
        setFields(new JFieldDef[]{poCampo});
    }

    /**Establecemos el campo de la BD*/
    public void setFields(final JFieldDef[] poCampos) {
        moCodigoModelo.setFields(poCampos);
    }

    /**Devolvemos el campo de la BD*/
    public JFieldDef[] getCampos() {
        return moCodigoModelo.getCampos();
    }

    /**Mostramos los datos del campo de BD guardado*/
    public void mostrarDatosBD() {
        moCodigoModelo.mostrarDatosBD();
    }

    /**Establecemos los datos de campo de BD guardado*/
    public void establecerDatosBD() throws ECampoError {
        moCodigoModelo.establecerDatosBD();
    }

    @Override
    public JFieldDef getCampo() {
       return moCodigoModelo.getCampo();
    }

    @Override
    public boolean getTextoCambiado() {
        return moCodigoModelo.getTextoCambiado();
    }
    
    public JFieldConComboBox getModelo(){
        return moCodigoModelo;
    }
}
