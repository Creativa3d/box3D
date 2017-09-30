/*
 * JPanelGeneralFiltro.java
 *
 * Created on 5 de septiembre de 2005, 13:33
 */

package utilesGUIx.formsGenericos;

import ListDatos.ECampoError;
import ListDatos.JFilaDatosDefecto;
import ListDatos.JListDatos;
import ListDatos.JUtilTabla;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;


import java.awt.Color;
import utiles.Copiar;
import utiles.JCadenas;
import utilesGUIx.JComboBoxCZ;
import utilesGUIx.JGUIxConfigGlobal;
import utilesGUIx.JTableCZSelecCeldas;
import utilesGUIx.JTableModel;

public class JPanelGeneralFiltro extends javax.swing.JPanel {
    public static final String mcsCambioFiltro = JPanelGenericoEvent.mcsCambioFiltro;

    
    private JPanelGeneralFiltroModelo moFiltroModelo;

    private JComboBoxCZ moComboBoxComparaciones; 
    private JComboBoxCZ moComboBoxUniones;
    private JTableCZSelecCeldas moSelect;
    
    private transient boolean mbInicializando=false;
    /** Creates new form JPanelGeneralFiltro */
    public JPanelGeneralFiltro() {
        super();
        initComponents();
        
        JGUIxConfigGlobal.getInstancia().setTextoTodosComponent(this);
        moSelect = new JTableCZSelecCeldas(moTabla);
        
        moTabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    public void setDatos(final JPanelGeneralFiltroModelo poDatos) throws Exception{
        moFiltroModelo=poDatos;
        mbInicializando=true;
        try{
            crearCombos();    
            mostrarTabla();
        }finally{
            mbInicializando=false;
        }
    }
    public void antesmostrar(){
        mbInicializando=true;
        try{
            cmbFiltros.mbSeleccionarClave(moFiltroModelo.getTablaFiltro().moList.msTabla+JFilaDatosDefecto.mcsSeparacion1);
        }finally{
            mbInicializando=false;
        }            
    }

    private void crearCombos() throws ECampoError{
        moComboBoxComparaciones = new JComboBoxCZ(); 
        
        moComboBoxComparaciones.RellenarCombo(moFiltroModelo.getListComparaciones(), new int[]{0}, new int[]{0}, false);
        
        moComboBoxUniones = new JComboBoxCZ();
        
        moComboBoxUniones.RellenarCombo(moFiltroModelo.getListUniones(), new int[]{0}, new int[]{0}, false);
        
        
        cmbFiltros.RellenarCombo(moFiltroModelo.getFiltrosDisponibles(), new int[]{0}, new int[]{0}, false);
        cmbFiltros.mbSeleccionarClave(moFiltroModelo.getTablaFiltro().moList.msTabla+JFilaDatosDefecto.mcsSeparacion1);
        
    }
    
    //rellena la tabla
    private void mostrarTabla() throws Exception{
        
        JTableModel loModel = new JTableModel(moFiltroModelo.getTablaFiltro().getList());
        loModel.mbEditable=true;
        moTabla.setModel(loModel);
        moTabla.setColorBackgroundDesac(Color.lightGray);
        // creamos los combos de las comparaciones
  
        TableColumn loColumn = moTabla.getColumnModel().getColumn(JTFiltro.lPosiComparacion); 

        loColumn.setCellEditor(new DefaultCellEditor(moComboBoxComparaciones)); 
         
        // creamos los combos de las comparaciones
  
        loColumn = moTabla.getColumnModel().getColumn(JTFiltro.lPosiUnion); 

        loColumn.setCellEditor(new DefaultCellEditor(moComboBoxUniones)); 
        
        //alto de fila
        moTabla.setRowHeight(26); 
        //ancho columnas
        setLongColumna(moTabla.getColumnModel().getColumn(JTFiltro.lPosiCodigo), 0);
        setLongColumna(moTabla.getColumnModel().getColumn(JTFiltro.lPosiNombre), 150);
        setLongColumna(moTabla.getColumnModel().getColumn(JTFiltro.lPosiComparacion), 100);
        setLongColumna(moTabla.getColumnModel().getColumn(JTFiltro.lPosiValor), 200);
        setLongColumna(moTabla.getColumnModel().getColumn(JTFiltro.lPosiUnion), 40);
        setLongColumna(moTabla.getColumnModel().getColumn(JTFiltro.lPosiDuplicadoSN), 0);
        setLongColumna(moTabla.getColumnModel().getColumn(JTFiltro.lPosiVisibleSN), 0);

        moTabla.getSelectionModel().setSelectionInterval(0, 0);
        moTabla.setColumnSelectionInterval(JTFiltro.lPosiValor, JTFiltro.lPosiValor);

        moTabla.requestFocus();
        
        
    }
    private void setLongColumna(final TableColumn loColumn, final int plLong){
        if(plLong==0){
            loColumn.setMinWidth(plLong);
            loColumn.setPreferredWidth(plLong);
            loColumn.setWidth(plLong);
            loColumn.setMaxWidth(plLong);
        }else{
            loColumn.setPreferredWidth(plLong);
        }
    }
    
    public void duplicar() throws Exception {
        if(moTabla.getSelectedRow()>=0){
            moFiltroModelo.duplicar(moTabla.getSelectedRow());
        }else{
            throw new Exception(JGUIxConfigGlobal.getInstancia().getTextosForms().getTexto("no existe la fila a duplicar"));
        }
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jScrollPane2 = new javax.swing.JScrollPane();
        moTabla = new utilesGUIx.JTableCZ();
        jPanel1 = new javax.swing.JPanel();
        jLabelCZ1 = new utilesGUIx.JLabelCZ();
        cmbFiltros = new utilesGUIx.JComboBoxCZ();
        btnNuevo = new utilesGUIx.JButtonCZ();
        jLabelCZ2 = new utilesGUIx.JLabelCZ();
        btnBorrar = new utilesGUIx.JButtonCZ();
        jPanelBotones = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jBtnLimpiar = new utilesGUIx.JButtonCZ();
        jBtnDuplicar = new utilesGUIx.JButtonCZ();
        chkFiltroDefecto = new utilesGUIx.JCheckBoxCZ();
        jLabel1 = new javax.swing.JLabel();
        jBtnCopiarTabla = new utilesGUIx.JButtonCZ();
        jLabel2 = new javax.swing.JLabel();

        setLayout(new java.awt.BorderLayout());

        jScrollPane2.setBackground(new java.awt.Color(237, 234, 229));
        jScrollPane2.setPreferredSize(new java.awt.Dimension(100, 100));

        moTabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        moTabla.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        moTabla.setColumnSelectionAllowed(true);
        moTabla.setNextFocusableComponent(jBtnDuplicar);
        jScrollPane2.setViewportView(moTabla);

        add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jPanel1.setPreferredSize(new java.awt.Dimension(367, 35));
        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabelCZ1.setText("Filtros");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanel1.add(jLabelCZ1, gridBagConstraints);

        cmbFiltros.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbFiltros.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbFiltrosItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanel1.add(cmbFiltros, gridBagConstraints);

        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIx/images/New16.gif"))); // NOI18N
        btnNuevo.setToolTipText("Nuevo");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanel1.add(btnNuevo, gridBagConstraints);

        jLabelCZ2.setText("  ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanel1.add(jLabelCZ2, gridBagConstraints);

        btnBorrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIx/images/Delete16.gif"))); // NOI18N
        btnBorrar.setToolTipText("Borrar");
        btnBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanel1.add(btnBorrar, gridBagConstraints);

        add(jPanel1, java.awt.BorderLayout.NORTH);

        jPanelBotones.setBackground(new java.awt.Color(175, 181, 186));
        jPanelBotones.setOpaque(false);
        jPanelBotones.setLayout(new java.awt.GridBagLayout());

        jLabel3.setText("   ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanelBotones.add(jLabel3, gridBagConstraints);

        jBtnLimpiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIx/images/RowDelete16.gif"))); // NOI18N
        jBtnLimpiar.setText("Limpiar ");
        jBtnLimpiar.setFocusable(false);
        jBtnLimpiar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jBtnLimpiar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jBtnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnLimpiarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanelBotones.add(jBtnLimpiar, gridBagConstraints);

        jBtnDuplicar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIx/images/RowInsertAfter16.gif"))); // NOI18N
        jBtnDuplicar.setText("Duplicar");
        jBtnDuplicar.setToolTipText("<html>Duplica la linea actual del filtro,<br> para poder hacer 2 filtros<br> por el mismo campo</html>");
        jBtnDuplicar.setFocusable(false);
        jBtnDuplicar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jBtnDuplicar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jBtnDuplicar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnDuplicarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanelBotones.add(jBtnDuplicar, gridBagConstraints);

        chkFiltroDefecto.setText("Filtro defecto");
        chkFiltroDefecto.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkFiltroDefectoItemStateChanged(evt);
            }
        });
        chkFiltroDefecto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkFiltroDefectoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        jPanelBotones.add(chkFiltroDefecto, gridBagConstraints);

        jLabel1.setText("   ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanelBotones.add(jLabel1, gridBagConstraints);

        jBtnCopiarTabla.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIx/images/Copy16.gif"))); // NOI18N
        jBtnCopiarTabla.setText("Copiar  T.");
        jBtnCopiarTabla.setToolTipText("Copiar la tabla de datos completa");
        jBtnCopiarTabla.setFocusable(false);
        jBtnCopiarTabla.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jBtnCopiarTabla.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jBtnCopiarTabla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnCopiarTablaActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanelBotones.add(jBtnCopiarTabla, gridBagConstraints);

        jLabel2.setText("   ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanelBotones.add(jLabel2, gridBagConstraints);

        add(jPanelBotones, java.awt.BorderLayout.EAST);
    }// </editor-fold>//GEN-END:initComponents

    private void jBtnDuplicarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnDuplicarActionPerformed
        try{
            duplicar();
            moTabla.validate();
            moTabla.repaint();
        }catch(Exception e){
            utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this, e, getClass().getName());
        }
    }//GEN-LAST:event_jBtnDuplicarActionPerformed

    private void jBtnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnLimpiarActionPerformed
        try{
            moFiltroModelo.limpiar();
            moTabla.repaint();
        }catch(Exception e){
            utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this, e, getClass().getName());
        }
    }//GEN-LAST:event_jBtnLimpiarActionPerformed

    private void jBtnCopiarTablaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnCopiarTablaActionPerformed
        try {
        JListDatos loList = JTablaConfig.getListOrdenado(
                moFiltroModelo.getDatos(), moFiltroModelo.getTablaConfig().getConfigTablaConcreta());
        loList.msTabla = moFiltroModelo.getDatos().msTabla;
        Copiar.getInstance().setClip(JUtilTabla.getListDatos2String(loList));

        }catch(Exception e){
            utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this, e, getClass().getName());
        }
        
    }//GEN-LAST:event_jBtnCopiarTablaActionPerformed

    private void cmbFiltrosItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbFiltrosItemStateChanged
        try{
            if(!mbInicializando){
                if(!moFiltroModelo.getTablaFiltro().moList.msTabla.equals(cmbFiltros.getFilaActual().msCampo(0))){
                    moFiltroModelo.setFiltro(cmbFiltros.getFilaActual().msCampo(0));
                    chkFiltroDefecto.setSelected(
                            cmbFiltros.getFilaActual().msCampo(0)
                                    .equalsIgnoreCase(moFiltroModelo.getTablaConfig().getConfigTabla().getFiltroDefecto())
                    );                    
                    moTabla.validate();
                    moTabla.repaint();
                }
            }
        }catch(Exception e){
            utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this, e, getClass().getName());
        }
    }//GEN-LAST:event_cmbFiltrosItemStateChanged

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        try{
            mbInicializando=true;
            String lsNombre = JOptionPane.showInputDialog("Introducir nombre de la nueva configuración");
            if(!JCadenas.isVacio(lsNombre)){
                moFiltroModelo.addFiltro(lsNombre);
                cmbFiltros.addLinea(lsNombre, lsNombre + JFilaDatosDefecto.mcsSeparacion1);
                cmbFiltros.mbSeleccionarClave(lsNombre + JFilaDatosDefecto.mcsSeparacion1);
                moFiltroModelo.setFiltro(lsNombre);
                moTabla.validate();
                moTabla.repaint();
            }
        }catch(Exception e){
            utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this, e, getClass().getName());
        } finally{
            mbInicializando=false;
        }
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarActionPerformed

        try{
            if(cmbFiltros.getFilaActual().msCampo(0).compareTo("0")==0){
                utilesGUIx.msgbox.JMsgBox.mensajeInformacion(this, "No se puede borrar la configuración por defecto");
            }else{
                if(JOptionPane.showConfirmDialog(this, "¿Deseas borrar la configuración actual?", "", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                    moFiltroModelo.borrarFiltro(cmbFiltros.getFilaActual().msCampo(0));
                    cmbFiltros.RellenarCombo(moFiltroModelo.getFiltrosDisponibles(), new int[]{0}, new int[]{0}, false);
                    //datos defecto
                    cmbFiltros.mbSeleccionarClave(JTablaConfigTabla.mcsSinFiltro + JFilaDatosDefecto.mcsSeparacion1);
                    moFiltroModelo.setFiltro(JTablaConfigTabla.mcsSinFiltro);
                    moTabla.validate();
                    moTabla.repaint();
                }
            }
        }catch(Exception e){
            utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this, e, getClass().getName());
        } finally{
            mbInicializando=false;
        }
        
    }//GEN-LAST:event_btnBorrarActionPerformed

    
//    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {                                           
//        try {
//            mbInicializando=true;
//         
//            if(cmbFiltros.getFilaActual().msCampo(0).equalsIgnoreCase(JTablaConfigTabla.mcsSinFiltro)){
//                String lsNombre = JOptionPane.showInputDialog("Introducir nombre de la nueva configuración");
//                if(!JCadenas.isVacio(lsNombre)){
//                    JTFiltro loFiltro = moFiltroModelo.addFiltro(lsNombre);
//                    moFiltroModelo.pasarDatos(moFiltroModelo.getTablaFiltro(), loFiltro);
//                    loFiltro.moList.msTabla=lsNombre;
//                    moFiltroModelo.setFiltro(lsNombre);
//                    cmbFiltros.addLinea(lsNombre, lsNombre + JFilaDatosDefecto.mcsSeparacion1);
//                    cmbFiltros.mbSeleccionarClave(lsNombre + JFilaDatosDefecto.mcsSeparacion1);
//                }
//            }else{
//                moFiltroModelo.pasarDatosAModelo();
//            }
//            JGUIxConfigGlobal.getInstancia().getMostrarPantalla().mensajeFlotante(this, "Guardado correctamente");
//            moTabla.validate();
//            moTabla.repaint();
//        } catch (Exception ex) {
//            utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this, ex);
//        } finally{
//            mbInicializando=false;
//        }
//        
//        
//    }      
    
    
    
    private void chkFiltroDefectoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkFiltroDefectoItemStateChanged
        if(!mbInicializando){
            if(chkFiltroDefecto.isSelected()){
                moFiltroModelo.getTablaConfig().getConfigTabla().setFiltroDefecto(cmbFiltros.getFilaActual().msCampo(0));
            }
        }
    }//GEN-LAST:event_chkFiltroDefectoItemStateChanged

    private void chkFiltroDefectoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkFiltroDefectoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkFiltroDefectoActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private utilesGUIx.JButtonCZ btnBorrar;
    private utilesGUIx.JButtonCZ btnNuevo;
    private utilesGUIx.JCheckBoxCZ chkFiltroDefecto;
    private utilesGUIx.JComboBoxCZ cmbFiltros;
    public utilesGUIx.JButtonCZ jBtnCopiarTabla;
    private utilesGUIx.JButtonCZ jBtnDuplicar;
    private utilesGUIx.JButtonCZ jBtnLimpiar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private utilesGUIx.JLabelCZ jLabelCZ1;
    private utilesGUIx.JLabelCZ jLabelCZ2;
    private javax.swing.JPanel jPanel1;
    public javax.swing.JPanel jPanelBotones;
    private javax.swing.JScrollPane jScrollPane2;
    private utilesGUIx.JTableCZ moTabla;
    // End of variables declaration//GEN-END:variables


    
}

