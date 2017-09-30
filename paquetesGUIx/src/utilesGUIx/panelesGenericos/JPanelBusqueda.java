/*
 * JPanelBusqueda.java
 *
 * Created on 16 de septiembre de 2004, 10:38
 */
package utilesGUIx.panelesGenericos;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.beans.*;


import ListDatos.*;
import ListDatos.estructuraBD.*;
import utiles.JDepuracion;
import utilesGUIx.JGUIxConfigGlobal;
import utilesGUIx.formsGenericos.*;
import utilesGUIx.formsGenericos.busqueda.*;
import utilesGUIx.formsGenericos.edicion.ITextBD;

/**Componete para presentar un código y su descripción*/
public class JPanelBusqueda extends javax.swing.JPanel implements java.beans.BeanInfo, ITextBD {
    //indica si se bloquea o no el lostfocus del texto

    protected boolean mbBloq = false;
    private String msValorCodigo = "";
    protected JPanelBusquedaParametros moParam;
    private JFieldDef[] moCampos;

    /**Constructo*/
    public JPanelBusqueda() {
        super();
        initComponents();
        if (lblDescripcion.getBackground().equals(lblDescripcion.getForeground())) {
            if (lblDescripcion.getBackground().getRed() == 0) {
                lblDescripcion.setForeground(Color.white);
            } else {
                lblDescripcion.setForeground(Color.black);
            }
        }
        btnBuscar.setFocusable(false);
        btnAnadir.setFocusable(false);
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
    public void addActionListenerBuscar(ActionListenerBuscar l) {
        btnBuscar.addActionListener(new ActionListenerWrapper(null, l));
    }

    /**
     * Borra un listener añadir
     * @param l oyente
     */
    public void removeActionListenerBuscar(ActionListenerBuscar l) {
        btnBuscar.removeActionListener(new ActionListenerWrapper(null, l));
    }

    /**
     * Añade un listener añadir
     * @param l oyente
     */
    public void addActionListenerAnadir(ActionListenerAnadir l) {
        btnAnadir.addActionListener(new ActionListenerWrapper(l, null));
    }

    /**
     * borra un listener añadir
     * @param l oyente
     */
    public void removeActionListenerAnadir(ActionListenerAnadir l) {
        btnAnadir.removeActionListener(new ActionListenerWrapper(l, null));
    }

    /**
     * Añade un listener text
     * @param l oyente
     */
    public void addTextListener(TextListener l) {
        txtCodigo.addTextListener(l);
    }

    /**
     * borra un listener text
     * @param l oyente
     */
    public void removeTextListener(TextListener l) {
        txtCodigo.removeTextListener(l);
    }

    /**
     * Añade un listener text
     * @param l oyente
     */
    public void addKeyListener(KeyListener l) {
        txtCodigo.addKeyListener(l);
    }

    /**
     * borra un listener text
     * @param l oyente
     */
    public void removeKeyListener(KeyListener l) {
        txtCodigo.removeKeyListener(l);
    }

    /**
     * Añade un listener text
     * @param l oyente
     */
    public void addFocusListenerText(FocusListener l) {
        txtCodigo.addFocusListener(l);
    }

    /**
     * borra un listener text
     * @param l oyente
     */
    public void removeFocusListenerText(FocusListener l) {
        txtCodigo.removeFocusListener(l);
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
        lblNombre.setToolTipText(psTexto);
    }

    /**
     * Devuelve la fuente del label
     * @return label
     */
    public Font getLabelFont() {
        return null;
    }

    /**
     * Establece la fuente del label
     * @param psTexto label
     */
    public void setLabelFont(Font font) {
        lblNombre.setFont(font);
    }

    /**
     * devuelve el color de fondo del texto
     * @return color
     */
    public Color getBackgroundText() {
        return txtCodigo.getBackground();
    }

    /**
     * Establece el color de fondo del texto
     * @param poColor color
     */
    public void setBackgroundText(Color poColor) {
        txtCodigo.setBackground(poColor);
    }
    public void setPreferenceSizeText(Dimension poDim){
        txtCodigo.setPreferredSize(poDim);
    }
    /**
     * Devuelve el icono añadir
     * @return icono
     */
    public Icon getIconoAnadir() {
        return btnAnadir.getIcon();
    }

    /**
     * Establece el icono añadir
     * @param poIcono icono
     */
    public void setIconoAnadir(Icon poIcono) {
        btnAnadir.setIcon(poIcono);
    }

    /**
     * Devuelve el icono buscar
     * @return icono
     */
    public Icon getIconoBuscar() {
        return btnBuscar.getIcon();
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
     * establece el icono buscar
     * @param poIcono icono
     */
    public void setIconoBuscar(Icon poIcono) {
        btnBuscar.setIcon(poIcono);
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
                txtCodigo.setValueTabla(msValorCodigo);
            }else{
                txtCodigo.setText(msValorCodigo);
            }
        } else {
            if (msValorCodigo.indexOf(JFilaDatosDefecto.mccSeparacion1) >= 0) {
                if(pbOriginal){
                    txtCodigo.setValueTabla(
                            msValorCodigo.replace(
                            JFilaDatosDefecto.mccSeparacion1,
                            '-').substring(0, msValorCodigo.length() - 1));
                }else{
                    txtCodigo.setText(
                            msValorCodigo.replace(
                            JFilaDatosDefecto.mccSeparacion1,
                            '-').substring(0, msValorCodigo.length() - 1));
                }
            } else {
                if(pbOriginal){
                    txtCodigo.setValueTabla(msValorCodigo);
                }else{
                    txtCodigo.setText(msValorCodigo);
                }
            }
        }
        txtCodigoFocusLost(null);
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
        btnBuscarActionPerformed(null);
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

    /**
     * Establece tamaño del label
     * @param poSize tamaño
     */
    public void setLabelSize(Dimension poSize) {
        lblNombre.setPreferredSize(poSize);
        lblNombre.setMinimumSize(poSize);
        lblNombre.setMaximumSize(poSize);
    }

    /**
     * Devuelve tamaño del label
     * @return tamaño
     */
    public Dimension getLabelSize() {
        return lblNombre.getPreferredSize();
    }

    /**Establece si esta habilitado*/
    public void setEnabled(boolean pbValor) {
        super.setEnabled(pbValor);
        txtCodigo.setEnabled(pbValor);
        btnAnadir.setEnabled(pbValor);
        btnBuscar.setEnabled(pbValor);
    }

    public void requestFocus() {
        super.requestFocus();
        txtCodigo.requestFocus();
    }

    public void setNextFocusableComponent(Component aComponent) {
        super.setNextFocusableComponent(aComponent);
        txtCodigo.setNextFocusableComponent(aComponent);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        lblNombre = new javax.swing.JLabel();
        btnAnadir = new utilesGUIx.JButtonCZ();
        txtCodigo = new utilesGUIx.JTextFieldCZ();
        btnBuscar = new utilesGUIx.JButtonCZ();
        lblDescripcion = new javax.swing.JLabel();

        setLayout(new java.awt.GridBagLayout());

        lblNombre.setText("Sist. Expl.");
        lblNombre.setAlignmentX(0.5F);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        add(lblNombre, gridBagConstraints);

        btnAnadir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIx/images/New16.gif"))); // NOI18N
        btnAnadir.setToolTipText("Nuevo/Editar");
        btnAnadir.setMaximumSize(new java.awt.Dimension(25, 25));
        btnAnadir.setMinimumSize(new java.awt.Dimension(25, 25));
        btnAnadir.setPreferredSize(new java.awt.Dimension(25, 25));
        btnAnadir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnadirActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        add(btnAnadir, gridBagConstraints);

        txtCodigo.setMinimumSize(new java.awt.Dimension(70, 23));
        txtCodigo.setPreferredSize(new java.awt.Dimension(80, 23));
        txtCodigo.addTextListener(new java.awt.event.TextListener() {
            public void textValueChanged(java.awt.event.TextEvent evt) {
                txtCodigoTextValueChanged(evt);
            }
        });
        txtCodigo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodigoFocusLost(evt);
            }
        });
        txtCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoKeyPressed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        add(txtCodigo, gridBagConstraints);

        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIx/images/Zoom16.gif"))); // NOI18N
        btnBuscar.setToolTipText("Buscar");
        btnBuscar.setMaximumSize(new java.awt.Dimension(25, 25));
        btnBuscar.setMinimumSize(new java.awt.Dimension(25, 25));
        btnBuscar.setPreferredSize(new java.awt.Dimension(25, 25));
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        add(btnBuscar, gridBagConstraints);

        lblDescripcion.setBackground(javax.swing.UIManager.getDefaults().getColor("info"));
        lblDescripcion.setText(" ");
        lblDescripcion.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lblDescripcion.setOpaque(true);
        lblDescripcion.setForeground(javax.swing.UIManager.getDefaults().getColor("infoText"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        add(lblDescripcion, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAnadirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnadirActionPerformed
        anadir();
    }//GEN-LAST:event_btnAnadirActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        buscar();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void txtCodigoTextValueChanged(java.awt.event.TextEvent evt) {//GEN-FIRST:event_txtCodigoTextValueChanged
        if (moParam == null || moParam.mlCamposPrincipales.length == 1) {
            msValorCodigo = txtCodigo.getTextReal();
        }

    }//GEN-LAST:event_txtCodigoTextValueChanged
    private void anadir() {
        try {
            if (moParam.moControlador == null) {
                JOptionPane.showMessageDialog(this, "No se puede editar datos", "", JOptionPane.OK_OPTION);
            } else {
                //probamos a llamar al metodo del controlador
                try {
                    
                    JListDatos loDatosEdicion = null;
                    loDatosEdicion = moParam.moControlador.getConsulta().getList();
                    try {
                        if (txtCodigo.getText().equals("")) {
                            if (moParam.mbEdicionLista) {
                                moParam.moControlador.mostrarFormPrinci();
                                moParam.moControlador.getConsulta().getList().setIndex(moParam.moControlador.getIndex());
                                int lIndex = 0;
                                if(moParam.mbConDatos){

                                }else{
                                    moParam.moTabla.moList.add(moParam.moControlador.getConsulta().getList().moFila());
                                    lIndex = moParam.moTabla.moList.size()-1;
                                }
                                busquedaIndice(lIndex);
                            } else {
                                moParam.moControlador.anadir();
                            }
                        } else {
                            IFilaDatos loFila = new JFilaDatosDefecto();
                            //buscamos el codigo
                            JListDatosFiltroConj loFiltro = new JListDatosFiltroConj();
                            int lPrinci = 0;
                            for (int lIndex = 0; lIndex < loDatosEdicion.getFields().count(); lIndex++) {
                                if (loDatosEdicion.getFields(lIndex).getPrincipalSN()) {
                                    String lsValor = "";
                                    if (moParam.mlCamposPrincipales.length == 1) {
                                        lsValor = txtCodigo.getText();
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
                } catch (Exception e) {
                    JDepuracion.anadirTexto(getClass().getName(), e);
                    JDepuracion.anadirTexto(JDepuracion.mclWARNING, getClass().getName(), "Presentación de form estandar");

                    JGUIxConfigGlobal.getInstancia().getMostrarPantalla().mostrarFormPrinci(moParam.moControlador, 640, 430, JPanelGenerico.mclTipo, JMostrarPantalla.mclEdicionFrame);
//
//                    JFormGenerico loForm = new JFormGenerico(moParam.moControlador);
//                    loForm.show();
                }
            }
        } catch (Exception e) {
            utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this, e, getClass().getName());
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


                loBusq.mostrarFormPrinci();
                
                busquedaIndice(loBusq.getIndex());

            } catch (Exception e) {
                utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this, e, getClass().getName());
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
                txtCodigo.setText(msValorCodigo);
            } else {
                StringBuilder lsCad = new StringBuilder();
                for (int i = 0; i < moParam.mlCamposPrincipales.length; i++) {
                    lsCad.append(moParam.moTabla.moList.getFields().get(moParam.mlCamposPrincipales[i]).getString());
                    lsCad.append(JFilaDatosDefecto.mcsSeparacion1);
                }
                msValorCodigo = lsCad.toString();
                txtCodigo.setText(msValorCodigo.replace(JFilaDatosDefecto.mcsSeparacion1.charAt(0), "-".charAt(0)).substring(0, msValorCodigo.replace(JFilaDatosDefecto.mcsSeparacion1.charAt(0), "-".charAt(0)).length() - 1));
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
        lblDescripcion.setText(lsCadena.toString());
        lblDescripcion.setToolTipText(lsCadena.toString());
    }

    private void txtCodigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoKeyPressed
        if (evt.getKeyCode() == evt.VK_F3 && btnBuscar.isVisible()) {
            btnBuscarActionPerformed(null);
        }
        if (evt.getKeyCode() == evt.VK_F2 && btnAnadir.isVisible()) {
            btnAnadirActionPerformed(null);
        }

//        if(evt.getKeyCode() == evt.VK_ESCAPE)
//            txtCodigoFocusLost(null);

    }//GEN-LAST:event_txtCodigoKeyPressed

    private void txtCodigoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodigoFocusLost
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
                            loVAlores[0] = txtCodigo.getText();
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
                                        utilesGUIx.msgbox.JMsgBox.mensajeInformacion(this, "El código no existe en la tabla relacionada", true);
                                        if(moParam.mbRecuperarFocoSinNoExiste){
                                            SwingUtilities.invokeLater(new Runnable() {
                                                public void run() {
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
                                    utilesGUIx.msgbox.JMsgBox.mensajeInformacion(this, "El código no existe en la tabla relacionada", true);
                                    if(moParam.mbRecuperarFocoSinNoExiste){
                                        SwingUtilities.invokeLater(new Runnable() {
                                            public void run() {
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
                utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this, e, getClass().getName());
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
    }//GEN-LAST:event_txtCodigoFocusLost

    /**
     * Deny knowledge about the class and customizer of the bean.
     * You can override this if you wish to provide explicit info.
     */
    public BeanDescriptor getBeanDescriptor() {
        return null;
    }

    /**
     * Deny knowledge of properties. You can override this
     * if you wish to provide explicit property info.
     */
    public PropertyDescriptor[] getPropertyDescriptors() {
        return null;
    }

    /**
     * Deny knowledge of a default property. You can override this
     * if you wish to define a default property for the bean.
     */
    public int getDefaultPropertyIndex() {
        return -1;
    }

    /**
     * Deny knowledge of event sets. You can override this
     * if you wish to provide explicit event set info.
     */
    public EventSetDescriptor[] getEventSetDescriptors() {
        EventSetDescriptor[] rv = null;
        try {
            EventSetDescriptor push = new EventSetDescriptor(
                    utilesGUIx.panelesGenericos.JPanelBusqueda.class,
                    "actionListenerBuscar",
                    utilesGUIx.panelesGenericos.ActionListenerBuscar.class,
                    new String[]{"actionPerformedBuscar"},
                    "addActionListenerBuscar",
                    "removeActionListenerBuscar");
            EventSetDescriptor push1 = new EventSetDescriptor(
                    utilesGUIx.panelesGenericos.JPanelBusqueda.class,
                    "actionListenerAnadir",
                    utilesGUIx.panelesGenericos.ActionListenerAnadir.class,
                    new String[]{"actionPerformedAnadir"},
                    "addActionListenerAnadir",
                    "removeActionListenerAnadir");
            EventSetDescriptor push2 = new EventSetDescriptor(
                    utilesGUIx.panelesGenericos.JPanelBusqueda.class,
                    "textListener",
                    TextListener.class,
                    new String[]{"textValueChanged"},
                    "addTextListener",
                    "removeTextListener");
            EventSetDescriptor push3 = new EventSetDescriptor(
                    utilesGUIx.panelesGenericos.JPanelBusqueda.class,
                    "keyListener",
                    KeyListener.class,
                    new String[]{"keyTyped", "keyPressed", "keyReleased"},
                    "addKeyListener",
                    "removeKeyListener");

            EventSetDescriptor push4 = new EventSetDescriptor(
                    utilesGUIx.panelesGenericos.JPanelBusqueda.class,
                    "FocusListenerText",
                    FocusListener.class,
                    new String[]{"focusGained", "focusLost"},
                    "addFocusListenerText",
                    "removeFocusListenerText");

            push.setDisplayName("Pulsar Boton Buscar");
            push1.setDisplayName("Pulsar Boton Anadir");
            push2.setDisplayName("cambia el texto");
            push3.setDisplayName("teclas");
            push4.setDisplayName("Focus");

            rv = new EventSetDescriptor[]{push, push1, push2, push3, push4};
        } catch (Exception e) {
            throw new Error(e.toString());
        }
        return rv;
    }

    /**
     * Deny knowledge of a default event. You can override this
     * if you wish to define a default event for the bean.
     */
    public int getDefaultEventIndex() {
        return -1;
    }

    /**
     * Deny knowledge of methods. You can override this
     * if you wish to provide explicit method info.
     */
    public MethodDescriptor[] getMethodDescriptors() {
        return null;
    }

    /**
     * Claim there are no other relevant BeanInfo objects.  You
     * may override this if you want to (for example) return a
     * BeanInfo for a base class.
     */
    public BeanInfo[] getAdditionalBeanInfo() {
        return null;
    }

    /**
     * Claim there are no icons available.  You can override
     * this if you want to provide icons for your bean.
     */
    public java.awt.Image getIcon(int iconKind) {
        return null;
    }

    /**
     * This is a utility method to help in loading icon images.
     * It takes the name of a resource file associated with the
     * current object's class file and loads an image object
     * from that file.  Typically images will be GIFs.
     * <p>
     * @param resourceName  A pathname relative to the directory
     *		holding the class file of the current class.  For example,
     *		"wombat.gif".
     * @return  an image object.  May be null if the load failed.
     */
    public java.awt.Image loadImage(final String resourceName) {
        java.awt.image.ImageProducer ip = null;
        java.awt.Image loReturn = null;
//	try {
//	    final Class c = getClass();
//	    ip = (java.awt.image.ImageProducer)
//		java.security.AccessController.doPrivileged(
//		new java.security.PrivilegedAction() {
//		    public Object run() {
//			java.net.URL url=c.getResource(resourceName);
//			if (url == null) {
//			    return null;
//			} else {
//			    try {
//				return url.getContent();
//			    } catch (java.io.IOException ioe) {
//				return null;
//			    }
//			}
//		    }
//	    });
//	} catch (Exception ex) {
//	    ip = null;
//	}
//        if (ip != null){
//            loReturn = java.awt.Toolkit.getDefaultToolkit().createImage(ip);
//        }
        return loReturn;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private utilesGUIx.JButtonCZ btnAnadir;
    private utilesGUIx.JButtonCZ btnBuscar;
    public javax.swing.JLabel lblDescripcion;
    private javax.swing.JLabel lblNombre;
    public utilesGUIx.JTextFieldCZ txtCodigo;
    // End of variables declaration//GEN-END:variables

    /**Establecemos el campo de la BD*/
    public void setField(final JFieldDef poCampo) {
        setFields(new JFieldDef[]{poCampo});
    }

    /**Establecemos el campo de la BD*/
    public void setFields(final JFieldDef[] poCampos) {
        moCampos = poCampos;
    }

    /**Devolvemos el campo de la BD*/
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
}
