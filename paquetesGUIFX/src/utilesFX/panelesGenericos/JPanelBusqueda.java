/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesFX.panelesGenericos;


import ListDatos.*;
import ListDatos.estructuraBD.*;
import java.io.IOException;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import utiles.JDepuracion;
import utilesFX.IFieldControl;
import utilesFX.JFXConfigGlobal;
import utilesFX.JFieldConComboBox;
import utilesFX.JFieldConTextField;
import utilesFX.formsGenericos.JMostrarPantalla;
import utilesFX.formsGenericos.JPanelGenerico;
import utilesFX.msgbox.JMsgBox;
import utilesGUIx.JGUIxConfigGlobalModelo;
import utilesGUIx.formsGenericos.*;
import utilesGUIx.formsGenericos.busqueda.*;
import utilesGUIx.formsGenericos.edicion.ITextBD;
import utilesGUIx.panelesGenericos.JConsulta;
import utilesGUIx.panelesGenericos.JPanelBusquedaParametros;

/**Componete para presentar un código y su descripción*/
public class JPanelBusqueda extends GridPane implements IFieldControl {
    
    // Variables declaration - do not modify                     
    @FXML private Button btnAnadir;
    @FXML private Button btnBuscar;
    @FXML public Label lblDescripcion;
    @FXML private Label lblNombre;
    @FXML public TextField txtCodigo;
    // End of variables declaration                   

    
//indica si se bloquea o no el lostfocus del texto

    
    protected boolean mbBloq = false;
    private String msValorCodigo = "";
    protected JPanelBusquedaParametros moParam;
    private JFieldDef[] moCampos;
    private JFieldConTextField moCodigoModelo;

    /**Constructo*/
    public JPanelBusqueda() {
        FXMLLoader loader = new FXMLLoader();
        loader.setRoot(this);
        loader.setLocation(this.getClass().getResource("/utilesFX/panelesGenericos/JPanelBusqueda.fxml"));
        loader.setController(this);
        try {
            final Node root = (Node)loader.load();
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        } 
        txtCodigo.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent t) {
                txtCodigoKeyPressed(t);
            }
        });
        txtCodigo.focusedProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                if(!t1.booleanValue()){
                    txtCodigoFocusLost();
                }
            }
        });
        txtCodigo.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                msValorCodigo=newValue;
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
        moCodigoModelo=new JFieldConTextField(txtCodigo);
        
    }

    public void setDatos(JPanelBusquedaParametros poParam) {
        moParam = poParam;
        moParam.inicializarPlugIn();
        btnAnadir.setVisible((moParam.moControlador != null));
        txtCodigo.setEditable(moParam.mlCamposPrincipales.length == 1); //Solo en este caso por ser clave compuesta
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
    public void setDatos(IPanelControlador poControlador, JSTabla poTabla, int[] plCamposPrincipales, int[] palDescripciones, boolean pbMensajeSiNoExiste, boolean pbConDatos, String[] pasTextosDescripciones) {
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
    public void setDatos(IPanelControlador poControlador, JSTabla poTabla, int[] plCamposPrincipales, int[] palDescripciones, boolean pbMensajeSiNoExiste) {
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
    public void setDatos(IPanelControlador poControlador, JSTabla poTabla, int plCampoPrincipal, int[] palDescripciones, boolean pbMensajeSiNoExiste, boolean pbConDatos, String[] pasTextosDescripciones) {
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
    public void setDatos(IPanelControlador poControlador, JSTabla poTabla, int plCampoPrincipal, int[] palDescripciones, boolean pbMensajeSiNoExiste, String[] pasTextosDescripciones) {
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
    public void setDatos(IPanelControlador poControlador, JSTabla poTabla, int plCampoPrincipal, int[] palDescripciones, boolean pbMensajeSiNoExiste, boolean pbConDatos) {
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
    public void setDatos(IPanelControlador poControlador, JSTabla poTabla, int plCampoPrincipal, int[] palDescripciones, boolean pbMensajeSiNoExiste) {
        setDatos(poControlador, poTabla, plCampoPrincipal, palDescripciones, pbMensajeSiNoExiste, false);
    }

    /**
     * establecemos una tabla relacionada con varias descripciones
     * @param poTabla Tabla a mostrar
     * @param plCamposPrincipales Lista posiciones de campos principales
     * @param palDescripciones Lista de posiciones de campos para la descripción
     * @param pbMensajeSiNoExiste Si presenta un mensaje si no existe
     */
    public void setDatos(JSTabla poTabla, int[] plCamposPrincipales, int[] palDescripciones, boolean pbMensajeSiNoExiste) {
        setDatos(null, poTabla, plCamposPrincipales, palDescripciones, pbMensajeSiNoExiste);
    }

    /**
     * establecemos una tabla relacionada con varias descripciones
     * @param palDescripcion posición de la descripción
     * @param poTabla Tabla a mostrar
     * @param plCamposPrincipales posición del campo principal
     * @param pbMensajeSiNoExiste Si presenta un mensaje si no existe
     */
    public void setDatos(JSTabla poTabla, int[] plCamposPrincipales, int palDescripcion, boolean pbMensajeSiNoExiste) {
        setDatos(null, poTabla, plCamposPrincipales, new int[]{palDescripcion}, pbMensajeSiNoExiste);
    }

    /**
     * establecemos una tabla relacionada con varias descripciones y una clave
     * @param plCampoPrincipal posición del campo principal
     * @param palDescripciones Lista de posiciones de las descripciones
     * @param poTabla Tabla a mostrar
     * @param pbMensajeSiNoExiste Si presenta un mensaje si no existe
     */
    public void setDatos(JSTabla poTabla, int plCampoPrincipal, int[] palDescripciones, boolean pbMensajeSiNoExiste) {
        setDatos(null, poTabla, plCampoPrincipal, palDescripciones, pbMensajeSiNoExiste, false);
    }

    /**
     * establecemos una tabla relacionada con una sola descripcion y una clave
     * @param plCampoPrincipal posición del campo principal
     * @param palDescripcion posición de la descripción
     * @param poTabla Tabla a mostrar
     * @param pbMensajeSiNoExiste Si presenta un mensaje si no existe
     */
    public void setDatos(JSTabla poTabla, int plCampoPrincipal, int palDescripcion, boolean pbMensajeSiNoExiste) {
        setDatos(null, poTabla, plCampoPrincipal, new int[]{palDescripcion}, pbMensajeSiNoExiste, false);
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

//    /**
//     * Añade un listener text
//     * @param l oyente
//     */
//    public void addTextListener(TextListener l) {
//        txtCodigo.addTextListener(l);
//    }
//
//    /**
//     * borra un listener text
//     * @param l oyente
//     */
//    public void removeTextListener(TextListener l) {
//        txtCodigo.removeTextListener(l);
//    }

    /**
     * Añade un listener text
     * @param l oyente
     */
    public void setOnKeyPressedText(EventHandler<KeyEvent> l) {
        txtCodigo.setOnKeyPressed(l);
    }


    /**
     * Añade un listener text
     * @param l oyente
     */
    public void addFocusListenerText(ChangeListener<Boolean> l) {
        txtCodigo.focusedProperty().addListener(l);
    }

    /**
     * borra un listener text
     * @param l oyente
     */
    public void removeFocusListenerText(ChangeListener<Boolean> l) {
        txtCodigo.focusedProperty().removeListener(l);
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
        lblNombre.setTooltip( new Tooltip(psTexto));
    }

    
    public void setVisibleBuscar(boolean pbBuscar) {
        btnBuscar.setVisible(pbBuscar);
    }

    public void setVisibleAnadir(boolean pbAnadir) {
        btnAnadir.setVisible(pbAnadir);
    }

    public boolean isVisibleBuscar() {
        return btnBuscar.isVisible();
    }

    public boolean isVisibleAnadir() {
        return btnAnadir.isVisible();
    }

    /**
     * establece el valor en el campo código y tambien busca la descripcion
     * si pbOriginal es true se pone como valor y al mismo tiempo como valor original y se vera en negro
     * si pbOriginal es false Si el valor es diferente al valor actual se pondra en rojo 
     * @param poValor valor
     */
    public void setValueConOriginal(Object poValor, boolean pbOriginal) {
        if (poValor == null) {
            msValorCodigo = "";
        } else {
            msValorCodigo = poValor.toString();
        }
        if (moParam != null
                && moParam.mlCamposPrincipales != null
                && moParam.mlCamposPrincipales.length == 1) {
            if (msValorCodigo.length() > 0
                    && msValorCodigo.charAt(msValorCodigo.length() - 1) == JFilaDatosDefecto.mccSeparacion1) {
                msValorCodigo = msValorCodigo.substring(0, msValorCodigo.length() - 1);
            }
            if(pbOriginal){
                moCodigoModelo.setValueTabla(msValorCodigo);
            }else{
                moCodigoModelo.getComponente().setText(msValorCodigo);
            }
        } else {
            if (msValorCodigo.indexOf(JFilaDatosDefecto.mccSeparacion1) >= 0) {
                if(pbOriginal){
                    moCodigoModelo.setValueTabla(
                            msValorCodigo.replace(
                            JFilaDatosDefecto.mccSeparacion1,
                            '-').substring(0, msValorCodigo.length() - 1));
                }else{
                    moCodigoModelo.getComponente().setText(
                            msValorCodigo.replace(
                            JFilaDatosDefecto.mccSeparacion1,
                            '-').substring(0, msValorCodigo.length() - 1));
                }
            } else {
                if(pbOriginal){
                    moCodigoModelo.setValueTabla(msValorCodigo);
                }else{
                    moCodigoModelo.getComponente().setText(msValorCodigo);
                }
            }
        }
        txtCodigoFocusLost();
    }
    /**
     * establece el valor en el campo código y tambien busca la descripcion
     * @param poValor valor
     */
    public void setValueTabla(Object poValor) {
        setValueConOriginal(poValor, true);
    }
    /**
     * establece el valor en el campo código y tambien busca la descripcion
     * Si el valor es diferente al valor actual se pondra en rojo
     * @param poValor valor
     */
    public void setText(Object poValor) {
        setValueConOriginal(poValor, false);
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
        return msValorCodigo;
    }

    /**
     * Devuelve el texto del campo plIndex, este sirve para campos compuestos
     * @return texto
     * @param plIndex Índice del campo a devolver
     */
    public String getText(int plIndex) {
        return new JFilaDatosDefecto(msValorCodigo).msCampo(plIndex);
    }

    /**
     * Establece la descripción, para hacer búsquedas manuales
     * @param psValor descripción
     */
    public void setDescripcion(String psValor) {
        lblDescripcion.setText(psValor);
    }

    /**
     * Devuelve la descrición actual
     * @return descrición
     */
    public String getDescripcion() {
        return lblDescripcion.getText();
    }

    /**Establece el foco*/
    public void setFocus() {
        txtCodigo.requestFocus();
    }

    public void requestFocus() {
        super.requestFocus();
        txtCodigo.requestFocus();
    }


    private void btnAnadirActionPerformed() {                                          
        anadir();
    }                                         

    private void btnBuscarActionPerformed() {                                          
        buscar();
    }                                         

    private void txtCodigoTextValueChanged() {                                           
        if (moParam == null || moParam.mlCamposPrincipales.length == 1) {
            msValorCodigo = moCodigoModelo.getTextReal();
        }

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
                            if (moCodigoModelo.getText().equals("")) {
                                moParam.moControlador.anadir();
                            } else {
                                IFilaDatos loFila = new JFilaDatosDefecto();
                                //buscamos el codigo
                                JListDatosFiltroConj loFiltro = new JListDatosFiltroConj();
                                int lPrinci = 0;
                                for (int lIndex = 0; lIndex < loDatosEdicion.getFields().count(); lIndex++) {
                                    if (loDatosEdicion.getFields(lIndex).getPrincipalSN()) {
                                        String lsValor = "";
                                        if (moParam.mlCamposPrincipales.length == 1) {
                                            lsValor = moCodigoModelo.getText();
                                        } else {
                                            lsValor = (new JFilaDatosDefecto(msValorCodigo).msCampo(lPrinci));
                                            lPrinci++;
                                        }
                                        loFila.addCampo(lsValor);
                                        loFiltro.addCondicion(JListDatosFiltroConj.mclAND,
                                                JListDatos.mclTIgual, lIndex,
                                                lsValor);
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
                                    loDatosEdicion.getFiltro().clear();
                                    loDatosEdicion.filtrarNulo();
                                    loDatosEdicion.setBookmark(loBook);
                                    moParam.moControlador.editar(loDatosEdicion.getIndex());
                                } else {
                                    loDatosEdicion.getFiltro().clear();
                                    loDatosEdicion.filtrarNulo();

                                    loFila.setTipoModif(JListDatos.mclNuevo);
                                    moParam.moControlador.getConsulta().addFilaPorClave(loFila);
                                    loDatosEdicion.moveLast();

                                    moParam.moControlador.editar(loDatosEdicion.getIndex());
                                }
                            }
                        } finally {
                            if (loDatosEdicion != null) {
                                loDatosEdicion.getFiltro().clear();
                                loDatosEdicion.filtrarNulo();
                            }
                        }


                    }
                } catch (Exception e) {
                    JDepuracion.anadirTexto(getClass().getName(), e);
                    JDepuracion.anadirTexto(JDepuracion.mclWARNING, getClass().getName(), "Presentación de form estandar");

                    JFXConfigGlobal.getInstancia().getMostrarPantalla().mostrarFormPrinci(moParam.moControlador, 640, 430, JPanelGenerico.mclTipo, JMostrarPantalla.mclEdicionFrame);
//
//                    JFormGenerico loForm = new JFormGenerico(moParam.moControlador);
//                    loForm.show();
                }
            }
        } catch (Exception e) {
            JFXConfigGlobal.getInstancia().getMostrarPantalla().mensajeErrorYLog(this, e, null);
        }
    }

    protected void buscar() {
        if (moParam.moTabla != null) {
            JListDatosFiltroConj loFiltro = moParam.moTabla.moList.getFiltro().Clone();
            try {
                mbBloq = true;
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
                mbBloq = false;
                txtCodigo.requestFocus();
            }
        }
    }

    protected void busquedaIndice(int plIndex) {

        if (plIndex >= 0) {
            moParam.moTabla.moList.setIndex(plIndex);
            if (moParam.mlCamposPrincipales.length == 1) {
                msValorCodigo = moParam.moTabla.moList.getFields().get(moParam.mlCamposPrincipales[0]).getString();
                moCodigoModelo.setText(msValorCodigo);
            } else {
                StringBuilder lsCad = new StringBuilder();
                for (int i = 0; i < moParam.mlCamposPrincipales.length; i++) {
                    lsCad.append(moParam.moTabla.moList.getFields().get(moParam.mlCamposPrincipales[i]).getString());
                    lsCad.append(JFilaDatosDefecto.mcsSeparacion1);
                }
                msValorCodigo = lsCad.toString();
                moCodigoModelo.setText(msValorCodigo.replace(JFilaDatosDefecto.mcsSeparacion1.charAt(0), "-".charAt(0)).substring(0, msValorCodigo.replace(JFilaDatosDefecto.mcsSeparacion1.charAt(0), "-".charAt(0)).length() - 1));
            }
            mostrarDescripcion();
        }

    }

    public JPanelBusquedaParametros getParam() {
        return moParam;
    }

    //mostramos la descripcion
    public void mostrarDescripcion() {
        StringBuilder lsCadena = new StringBuilder();
        for (int i = 0; i < moParam.malDescripciones.length; i++) {
            if (moParam.masTextosDescripciones != null) {
                if (moParam.masTextosDescripciones[i] != null) {
                    lsCadena.append(moParam.masTextosDescripciones[i]);
                    lsCadena.append(' ');
                }
            }
            if (moParam.mbTrim) {
                lsCadena.append(moParam.moTabla.moList.getFields().get(moParam.malDescripciones[i]).getString().trim());
            } else {
                lsCadena.append(moParam.moTabla.moList.getFields().get(moParam.malDescripciones[i]).getString());
            }
            lsCadena.append(' ');
        }
        //establecemos la descripcion
        if(Platform.isFxApplicationThread()){
            lblDescripcion.setText(lsCadena.toString());
            lblDescripcion.setTooltip(new Tooltip(lsCadena.toString()));
        } else {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    lblDescripcion.setText(lsCadena.toString());
                    lblDescripcion.setTooltip(new Tooltip(lsCadena.toString()));
                }
            });
        }

    }

    private void txtCodigoKeyPressed(KeyEvent evt) {                                     
        if (evt.getCode() == KeyCode.F3 && btnBuscar.isVisible()) {
            btnBuscarActionPerformed();
        }
        if (evt.getCode() == KeyCode.F2 && btnAnadir.isVisible()) {
            btnAnadirActionPerformed();
        }

//        if(evt.getKeyCode() == evt.VK_ESCAPE)
//            txtCodigoFocusLost(null);

    }                                    

    private void txtCodigoFocusLost() {                                    
        JListDatosFiltroConj loFiltroAux = null;
        //si existe una tabla asociada
        if (moParam != null && moParam.moTabla != null) {
            try {
                if (moParam.moTabla.getSelect() != null && moParam.moTabla.getSelect().getWhere().mbAlgunaCond()) {
                    loFiltroAux = moParam.moTabla.getSelect().getWhere().Clone();
                }

                if (!mbBloq) {
                    String lsCodigo = "";
                    for (int i = 0; i < moParam.mlCamposPrincipales.length; i++) {
                        lsCodigo += JFilaDatosDefecto.mcsSeparacion1;
                    }
                    //si el código es nulo ponemos la descripcion a ""
                    if (msValorCodigo == null || (msValorCodigo.compareTo("") == 0) || (msValorCodigo.compareTo(lsCodigo) == 0)) {
                        lblDescripcion.setText("");
                    } else {
                        //buscamos el codigo
                        String[] loVAlores = new String[moParam.mlCamposPrincipales.length];

                        if (moParam.mlCamposPrincipales.length == 1) {
                            loVAlores[0] = moCodigoModelo.getText();
                        } else {
                            for (int i = 0; i < moParam.mlCamposPrincipales.length; i++) {
                                loVAlores[i] = (new JFilaDatosDefecto(msValorCodigo).msCampo(i));
                            }
                        }

                        if (!moParam.moTabla.moList.buscar(JListDatos.mclTIgual, moParam.mlCamposPrincipales, loVAlores)) {
                            if (!moParam.mbConDatos) {
//								if (moParam.moTabla.getSelect().getWhere().mbAlgunaCond()) {
//									loFiltroAux = moParam.moTabla.getSelect().getWhere().Clone();
//								}
                                JListDatosFiltroConj loFiltro = new JListDatosFiltroConj();
                                loFiltro.addCondicion(JListDatosFiltroConj.mclAND,
                                        JListDatos.mclTIgual, moParam.mlCamposPrincipales,
                                        loVAlores);
                                moParam.moTabla.recuperarFiltradosNormal(loFiltro, false);
                                if (!moParam.moTabla.moList.moveFirst()) {
                                    //si no existre presentamos un mensaje en caso de que el prog. lo quiera
                                    if (moParam.mbMensajeSiNoExiste) {
                                        JMsgBox.mensajeInformacion(this, "El código no existe en la tabla relacionada");
                                        if(moParam.mbRecuperarFocoSinNoExiste){
                                            Platform.runLater(new Runnable() {
                                                public void run() {
                                                    txtCodigo.setText("");
                                                    txtCodigo.requestFocus();
                                                }
                                            });
                                        }
                                    }
                                    //descripcion a vacio
                                    lblDescripcion.setText("");
                                } else {
                                    mostrarDescripcion();
                                }
                            } else {
                                //si no existre presentamos un mensaje en caso de que el prog. lo quiera
                                if (moParam.mbMensajeSiNoExiste) {
                                    JMsgBox.mensajeInformacion(this, "El código no existe en la tabla relacionada");
                                    if(moParam.mbRecuperarFocoSinNoExiste){
                                        Platform.runLater(new Runnable() {
                                            public void run() {
                                                txtCodigo.setText("");
                                                txtCodigo.requestFocus();
                                            }
                                        });
                                    }
                                }
                                //descripcion a vacio
                                lblDescripcion.setText("");
                            }
                        } else {
                            //si existe un registro, creamos la descripcion
                            mostrarDescripcion();
                        }
                    }
                }
            } catch (Exception e) {
                JFXConfigGlobal.getInstancia().getMostrarPantalla().mensajeErrorYLog(this, e, null);
            } finally {
//                moParam.moTabla.moList.getFiltro().Clear();
//                moParam.moTabla.moList.filtrarNulo();
                if (moParam.moTabla.getSelect() != null) {
                    moParam.moTabla.getSelect().getWhere().Clear();
                    if (loFiltroAux != null) {
                        moParam.moTabla.getSelect().getWhere().addCondicion(JListDatosFiltroConj.mclAND, loFiltroAux);
                    }
                }
            }
        }
    }                                   


    /**Establecemos el campo de la BD
     * @param poCampo
     */
    public void setField(final JFieldDef poCampo) {
        setLabel(poCampo.getCaption());
        setFields(new JFieldDef[]{poCampo});
    }

    /**Establecemos el campo de la BD
     * @param poCampos
     */
    public void setFields(final JFieldDef[] poCampos) {
        moCampos = poCampos;
    }

    /**Devolvemos el campo de la BD
     * @return */
    public JFieldDef[] getCampos() {
        return moCampos;
    }

    /**Mostramos los datos del campo de BD guardado*/
    public void mostrarDatosBD() {
        if (moCampos != null) {
            StringBuilder lasString = new StringBuilder();
            for (int i = 0; i < moCampos.length; i++) {
                lasString.append(moCampos[i].getString());
                lasString.append(JFilaDatosDefecto.mccSeparacion1);
            }
            setValueTabla(lasString.toString());
        }
    }

    /**Establecemos los datos de campo de BD guardado*/
    public void establecerDatosBD() throws ECampoError {
        if (moCampos != null) {
            for (int i = 0; i < moCampos.length; i++) {
                moCampos[i].setValue(getText(i));
            }
        }
    }

    @Override
    public JFieldDef getCampo() {
       return moCodigoModelo.getCampo();
    }

    @Override
    public boolean getTextoCambiado() {
        return moCodigoModelo.getTextoCambiado();
    }
    
    public JFieldConTextField getModelo(){
        return moCodigoModelo;
    }
}
