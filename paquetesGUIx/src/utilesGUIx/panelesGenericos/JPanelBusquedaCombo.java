/*
 * JPanelBusquedaCombo.java
 *
 * Created on 16 de septiembre de 2004, 10:38
 */
package utilesGUIx.panelesGenericos;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.beans.*;


import ListDatos.*;
import ListDatos.estructuraBD.JFieldDef;
import utiles.IListaElementos;
import java.util.Iterator;
import utiles.JDepuracion;
import utiles.JListaElementos;
import utilesGUIx.JGUIxConfigGlobal;
import utilesGUIx.formsGenericos.*;
import utilesGUIx.formsGenericos.busqueda.*;
import utilesGUIx.formsGenericos.edicion.ITextBD;

/**Componete para presentar un código y su descripción*/
public class JPanelBusquedaCombo extends javax.swing.JPanel implements java.beans.BeanInfo, java.awt.event.ActionListener, java.awt.event.FocusListener, java.awt.event.KeyListener, ItemListener, ITextBD, ItemSelectable {
    //indica si se bloquea o no el lostfocus del texto
//    private boolean mbBloq = false;

    private JPanelBusquedaParametros moParam;
    private boolean mbRefrescar = true;
    private IListaElementos moItemListeners = new JListaElementos();
    private JFieldDef[] moCampos;

    /**Constructo*/
    public JPanelBusquedaCombo() {
        super();
        initComponents();
        cmbCodigo.borrarTodo();
        btnBuscar.setFocusable(false);
        btnAnadir.setFocusable(false);
        cmbCodigo.addItemListener(this);

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
    public void addFocusListenerText(FocusListener l) {
        cmbCodigo.addFocusListener(l);
    }

    /**
     * borra un listener text
     * @param l oyente
     */
    public void removeFocusListenerText(FocusListener l) {
        cmbCodigo.removeFocusListener(l);
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

    public void itemStateChanged(ItemEvent e) {
        try {
            cmbCodigo.setToolTipText(cmbCodigo.msDevolverDescri());
        } catch (Exception ex) {
        }
        Object loSource = e.getSource();
        e.setSource(this);
        try {
            for (int i = 0; i < moItemListeners.size(); i++) {
                ItemListener loItem = (ItemListener) moItemListeners.get(i);
                loItem.itemStateChanged(e);
            }
        } finally {
            e.setSource(loSource);
        }
    }

    /**
     * Añade un listener text
     * @param l oyente
     */
    public void addItemListener(ItemListener l) {
        moItemListeners.add(l);
    }

    /**
     * borra un listener text
     * @param l oyente
     */
    public void removeItemListener(ItemListener l) {
        moItemListeners.remove(l);
    }

    /**
     * Añade un listener text
     * @param l oyente
     */
    public void addKeyListener(KeyListener l) {
        cmbCodigo.removeKeyListener(l);
    }

    /**
     * borra un listener text
     * @param l oyente
     */
    public void removeKeyListener(KeyListener l) {
        cmbCodigo.removeKeyListener(l);
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
        return cmbCodigo.getBackground();
    }

    /**
     * Establece el color de fondo del texto
     * @param poColor color
     */
    public void setBackgroundText(Color poColor) {
        cmbCodigo.setBackground(poColor);
    }
    public void setPreferenceSizeText(Dimension poDim){
        cmbCodigo.setPreferredSize(poDim);
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

    /**
     * establece el icono buscar
     * @param poIcono icono
     */
    public void setIconoBuscar(Icon poIcono) {
        btnBuscar.setIcon(poIcono);
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
            cmbCodigo.setValueTabla(lsValor);
        }else{
            cmbCodigo.mbSeleccionarClave(lsValor);
        }
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
        return cmbCodigo.getFilaActual().msCampo(0);
    }

    /**
     * Devuelve el texto del campo plIndex, este sirve para campos compuestos
     * @return texto
     * @param plIndex Índice del campo a devolver
     */
    public String getText(int plIndex) {
        return cmbCodigo.getFilaActual().msCampo(plIndex);
    }
    public boolean requestFocusInWindow(){
        return cmbCodigo.requestFocusInWindow();
    }
    public void requestFocus(){
        super.requestFocus();
        cmbCodigo.requestFocus();
    }
    
    /**Establece el foco*/
    public void setFocus() {
        requestFocus();
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
        cmbCodigo.setEnabled(pbValor);
        btnAnadir.setEnabled(pbValor);
        btnBuscar.setEnabled(pbValor);
    }

    private void refrescar() throws Exception {
        JConsulta loCons = new JConsulta(moParam.moTabla, moParam.mbConDatos);
        loCons.refrescar(false, false);
        String lsAux = cmbCodigo.msDevolverValorActualCombo();
        rellenarCombo(loCons.getList());
        setValueTabla(lsAux);
        mbRefrescar = false;
    }

    private void rellenarCombo(JListDatos poList) {
        cmbCodigo.borrarTodo();
        StringBuffer lsAux;
        String lsClaveS;
        String lsUltValor = "";
        String lsDescriS = "";
        IFilaDatos loFilaDatos;
        cmbCodigo.addLinea("", "");
        Iterator enum1 = poList.iterator();
        lsAux = new StringBuffer(25);
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
                cmbCodigo.addLinea(lsDescriS, lsClaveS);
                lsUltValor = lsClaveS;
            }
        }

    }
    public String getDescripcion(){
        return cmbCodigo.getSelectedItem().toString();
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
        cmbCodigo = new utilesGUIx.JComboBoxCZ();
        btnBuscar = new utilesGUIx.JButtonCZ();

        setLayout(new java.awt.GridBagLayout());

        lblNombre.setText("Sist. Expl."); // NOI18N
        lblNombre.setAlignmentX(0.5F);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        add(lblNombre, gridBagConstraints);

        btnAnadir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIx/images/New16.gif"))); // NOI18N
        btnAnadir.setToolTipText("Nuevo/Editar"); // NOI18N
        btnAnadir.setMaximumSize(new java.awt.Dimension(25, 25));
        btnAnadir.setMinimumSize(new java.awt.Dimension(25, 25));
        btnAnadir.setPreferredSize(new java.awt.Dimension(25, 25));
        btnAnadir.addActionListener(this);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        add(btnAnadir, gridBagConstraints);

        cmbCodigo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbCodigo.addFocusListener(this);
        cmbCodigo.addKeyListener(this);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        add(cmbCodigo, gridBagConstraints);

        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIx/images/Zoom16.gif"))); // NOI18N
        btnBuscar.setToolTipText("Buscar"); // NOI18N
        btnBuscar.setMaximumSize(new java.awt.Dimension(25, 25));
        btnBuscar.setMinimumSize(new java.awt.Dimension(25, 25));
        btnBuscar.setPreferredSize(new java.awt.Dimension(25, 25));
        btnBuscar.addActionListener(this);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        add(btnBuscar, gridBagConstraints);
    }

    // Code for dispatching events from components to event handlers.

    public void actionPerformed(java.awt.event.ActionEvent evt) {
        if (evt.getSource() == btnAnadir) {
            JPanelBusquedaCombo.this.btnAnadirActionPerformed(evt);
        }
        else if (evt.getSource() == btnBuscar) {
            JPanelBusquedaCombo.this.btnBuscarActionPerformed(evt);
        }
    }

    public void focusGained(java.awt.event.FocusEvent evt) {
        if (evt.getSource() == cmbCodigo) {
            JPanelBusquedaCombo.this.cmbCodigoFocusGained(evt);
        }
    }

    public void focusLost(java.awt.event.FocusEvent evt) {
    }

    public void keyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getSource() == cmbCodigo) {
            JPanelBusquedaCombo.this.cmbCodigoKeyPressed(evt);
        }
    }

    public void keyReleased(java.awt.event.KeyEvent evt) {
    }

    public void keyTyped(java.awt.event.KeyEvent evt) {
    }// </editor-fold>//GEN-END:initComponents

    private void cmbCodigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbCodigoKeyPressed
        if (evt.getKeyCode() == evt.VK_F3 && btnBuscar.isVisible()) {
            btnBuscarActionPerformed(null);
        }
        if (evt.getKeyCode() == evt.VK_F2 && btnAnadir.isVisible()) {
            btnAnadirActionPerformed(null);
        }

    }//GEN-LAST:event_cmbCodigoKeyPressed

    private void cmbCodigoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cmbCodigoFocusGained
        try {
            if (mbRefrescar) {
                refrescar();
            }
        } catch (Exception e) {
            utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this, e, getClass().getName());
        }

    }//GEN-LAST:event_cmbCodigoFocusGained

    private void btnAnadirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnadirActionPerformed
        anadir();
    }//GEN-LAST:event_btnAnadirActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        buscar();
    }//GEN-LAST:event_btnBuscarActionPerformed
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
                            if (cmbCodigo.getFilaActual().msCampo(0).equals("")) {
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
                                    mbRefrescar = true;
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
                                        lsValor = cmbCodigo.getFilaActual().msCampo(lPrinci);
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
                    
                } catch (Exception e) {
                    JDepuracion.anadirTexto(getClass().getName(), e);
                    JDepuracion.anadirTexto(JDepuracion.mclWARNING, getClass().getName(), "Presentación de form estandar");

                    JGUIxConfigGlobal.getInstancia().getMostrarPantalla().mostrarFormPrinci(moParam.moControlador, 640, 430, JPanelGenerico.mclTipo, JMostrarPantalla.mclEdicionFrame);

//                    JFormGenerico loForm = new JFormGenerico(moParam.moControlador);
//                    loForm.show();
                }
            }
        } catch (Exception e) {
            utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this, e, getClass().getName());
        }
    }

    private void buscar() {
        if (moParam.moTabla != null) {
            JListDatosFiltroConj loFiltro = moParam.moTabla.moList.getFiltro().Clone();
            try {
                JBusqueda loBusq = new JBusqueda(new JConsulta(moParam.moTabla, moParam.mbConDatos), moParam.moTabla.moList.msTabla);
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
                cmbCodigo.requestFocus();
            }
        }
    }
    public void vaciaCombo() {
        cmbCodigo.borrarTodo();
    }

    private void busquedaIndice(int plIndex) {

        if (plIndex >= 0) {
            moParam.moTabla.moList.setIndex(plIndex);
            StringBuffer lsCad = new StringBuffer();
            for (int i = 0; i < moParam.mlCamposPrincipales.length; i++) {
                lsCad.append(moParam.moTabla.moList.getFields().get(moParam.mlCamposPrincipales[i]).getString());
                lsCad.append(JFilaDatosDefecto.mcsSeparacion1);
            }
            cmbCodigo.setValueTabla(lsCad.toString());
        }

    }

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
                    utilesGUIx.panelesGenericos.JPanelBusquedaCombo.class,
                    "actionListenerBuscar",
                    utilesGUIx.panelesGenericos.ActionListenerBuscar.class,
                    new String[]{"actionPerformedBuscar"},
                    "addActionListenerBuscar",
                    "removeActionListenerBuscar");
            EventSetDescriptor push1 = new EventSetDescriptor(
                    utilesGUIx.panelesGenericos.JPanelBusquedaCombo.class,
                    "actionListenerAnadir",
                    utilesGUIx.panelesGenericos.ActionListenerAnadir.class,
                    new String[]{"actionPerformedAnadir"},
                    "addActionListenerAnadir",
                    "removeActionListenerAnadir");
            EventSetDescriptor push2 = new EventSetDescriptor(
                    utilesGUIx.panelesGenericos.JPanelBusquedaCombo.class,
                    "itemListener",
                    ItemListener.class,
                    new String[]{"itemStateChanged"},
                    "addItemListener",
                    "removeItemListener");
            EventSetDescriptor push3 = new EventSetDescriptor(
                    utilesGUIx.panelesGenericos.JPanelBusquedaCombo.class,
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
            push2.setDisplayName("cambia el combo");
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
    public utilesGUIx.JComboBoxCZ cmbCodigo;
    private javax.swing.JLabel lblNombre;
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
            StringBuffer lasString = new StringBuffer();
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

    public Object[] getSelectedObjects() {
        return new Object[]{getText()};
    }
}
