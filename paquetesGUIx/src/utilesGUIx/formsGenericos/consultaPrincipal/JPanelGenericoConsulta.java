/*
 * JPanelGenericoConsulta.java
 *
 * Created on 13 de septiembre de 2006, 18:29
 */

package utilesGUIx.formsGenericos.consultaPrincipal;


import java.awt.Container;
import javax.swing.*;

import ListDatos.*;
import utiles.IListaElementos;
import utiles.JConversiones;
import utiles.JDepuracion;
import utiles.JListaElementos;
import utilesGUIx.formsGenericos.IPanelControladorConsulta;
import utilesGUIx.formsGenericos.edicion.JFormEdicionParametros;
import utilesGUIx.formsGenericos.edicion.JPanelGENERALBASE;
import utilesGUIx.plugin.IPlugInConsulta;
import utilesGUIx.plugin.toolBar.IComponenteAplicacion;



public class JPanelGenericoConsulta extends javax.swing.JPanel implements IPlugInConsulta {
    private static final long serialVersionUID = 1L;
    
    private final IListaElementos moControladores;
    private boolean mbDesactiva = false;
    
    private IListaElementos moFiltroActual;

    private String msUltimoFiltro = "";
    private final JFormEdicionParametros moParametros = new JFormEdicionParametros();

    private String msNombre="consulta";
    
    public JPanelGenericoConsulta() {
        super();
        mbDesactiva = true;
        initComponents();

        moControladores = new JListaElementos();
        

//        lblDesde.setVisible(false);
//        lblHasta.setVisible(false);
//        txtDesde.setVisible(false);
//        txtHasta.setVisible(false);
        
        mbDesactiva = false;
        rellenarCombos();
        
    }
    public IComponenteAplicacion getListaComponentesAplicacion() {
        return null;
    }

    public void aplicarListaComponentesAplicacion() {
    }

    public void setNombre(String psNombre) throws Exception{
        msNombre=psNombre;
    }

    @Override
    public void setEnabled(boolean enabled) {
        try {
            JPanelGENERALBASE.setBloqueoControlesContainer(this, !enabled);
            super.setEnabled(enabled);
        } catch (Exception ex) {
        }
    }

    
    private void rellenarCombos(){
        boolean lbDesaAux = mbDesactiva;
        mbDesactiva = true;
        try{
            cmbTabla.borrarTodo();
            for(int i = 0 ; i < moControladores.size(); i++){
                IPanelControladorConsulta loContr = (IPanelControladorConsulta)moControladores.get(i);
                cmbTabla.addLinea(loContr.getParametros().getTitulo(), String.valueOf(i) + JFilaDatosDefecto.mcsSeparacion1);
            }
        }finally{
            mbDesactiva=lbDesaAux;
        }
    }
    public void setControlador(int plIndex) throws Exception{
        boolean lbDesaAux = mbDesactiva;
        mbDesactiva = true;
        try{
            cmbTabla.mbSeleccionarClave(String.valueOf(plIndex) + JFilaDatosDefecto.mcsSeparacion1);
        } finally {  
            mbDesactiva=lbDesaAux;
        }
        setControladorInterno(plIndex);
        
    }
    private void setControladorInterno(int plIndex) throws Exception{
        
        boolean lbDesaAux = mbDesactiva;
        mbDesactiva = true;
        try{
            System.gc();

            IPanelControladorConsulta loContro = (IPanelControladorConsulta)moControladores.get(plIndex);

            //rellenamos las fechas
            cmbCampo.borrarTodo();

            moFiltroActual = loContro.getFiltros();
            if(moFiltroActual == null || moFiltroActual.size()==0){
                jPanelFiltros.setVisible(false);
            }else{
                jPanelFiltros.setVisible(true);

                for(int i = 0; i < moFiltroActual.size();i++){
                    JPanelGenericoConsFiltro loFiltro = (JPanelGenericoConsFiltro)moFiltroActual.get(i);
                    cmbCampo.addLinea(
                            loFiltro.msCaption, 
                            String.valueOf(loFiltro.mlCampo) + JFilaDatosDefecto.mcsSeparacion1 + 
                                String.valueOf(i) + JFilaDatosDefecto.mcsSeparacion1 
                            );

                }
                jPanelFiltros.setVisible(true);

            }

            refrescarControlador(plIndex);

        } finally {  
            mbDesactiva=lbDesaAux;
        }
    }
    private void refrescarControlador(int plIndex) throws Exception{
        String lsFiltro = 
                String.valueOf(plIndex) + ";" + 
                txtDesde.getText() + ";" +  
                txtHasta.getText() + ";" + 
                cmbCampo.getFilaActual().msCampo(0) + ";";
        
        if(!lsFiltro.equals(msUltimoFiltro)){
            msUltimoFiltro = lsFiltro;
            
            IPanelControladorConsulta loContro = (IPanelControladorConsulta)moControladores.get(plIndex);
            //personalizamos el controlador
            loContro.setFechas(txtDesde.getText(), txtHasta.getText(), (int)JConversiones.cdbl(cmbCampo.getFilaActual().msCampo(0)));

            //lo establecemos al form
            jPanelConsultaGenerico1.setControlador(loContro);

        }
        

    }
    public IPanelControladorConsulta getControladorActual() {
        return (IPanelControladorConsulta) jPanelConsultaGenerico1.getControlador();
    }

    public void addControlador(IPanelControladorConsulta poControlador){
        moControladores.add(poControlador);
        msUltimoFiltro="";
        rellenarCombos();
    }
    public void removeAllControlador(){
        moControladores.clear();
        msUltimoFiltro="";
        rellenarCombos();
    }
    public void removeControlador(IPanelControladorConsulta poControlador){
        moControladores.remove(poControlador);
        msUltimoFiltro="";
        rellenarCombos();
    }
    public IPanelControladorConsulta getControlador(int i){
        return (IPanelControladorConsulta)moControladores.get(i);
    }
    public void getControladorSize(){

        moControladores.size();
    }
    public int getControladorIndex(){
        return (int)JConversiones.cdbl(cmbTabla.getFilaActual().msCampo(0));
    }
    
    public JMenuBar getMenu() {
        return null;
    }

    public String getIdentificador() {
        return msNombre;
    }

    public Container getContenedor() {
        return this;
        
    }
    public JFormEdicionParametros getParametros() {
        return moParametros;
    }

    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel3 = new javax.swing.JPanel();
        jLabelCZ1 = new utilesGUIx.JLabelCZ();
        cmbTabla = new utilesGUIx.JComboBoxCZ();
        jPanelFiltros = new javax.swing.JPanel();
        jLabelCZ4 = new utilesGUIx.JLabelCZ();
        cmbCampo = new utilesGUIx.JComboBoxCZ();
        lblDesde = new utilesGUIx.JLabelCZ();
        txtDesde = new utilesGUIx.JTextFieldCZ();
        lblHasta = new utilesGUIx.JLabelCZ();
        txtHasta = new utilesGUIx.JTextFieldCZ();
        jPanelConsultaGenerico1 = new utilesGUIx.formsGenericos.JPanelGenerico2();

        setLayout(new java.awt.BorderLayout());

        jPanel3.setLayout(new java.awt.GridBagLayout());

        jLabelCZ1.setText("Tabla");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanel3.add(jLabelCZ1, gridBagConstraints);

        cmbTabla.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbTabla.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbTablaItemStateChanged(evt);
            }
        });
        cmbTabla.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                cmbTablaPropertyChange(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanel3.add(cmbTabla, gridBagConstraints);

        jPanelFiltros.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtro"));
        jPanelFiltros.setLayout(new java.awt.GridBagLayout());

        jLabelCZ4.setText("Con");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanelFiltros.add(jLabelCZ4, gridBagConstraints);

        cmbCampo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbCampo.setMinimumSize(new java.awt.Dimension(80, 20));
        cmbCampo.setPreferredSize(new java.awt.Dimension(80, 20));
        cmbCampo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbCampoItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanelFiltros.add(cmbCampo, gridBagConstraints);

        lblDesde.setText("Desde");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanelFiltros.add(lblDesde, gridBagConstraints);

        txtDesde.setTipo(0);
        txtDesde.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesdeFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanelFiltros.add(txtDesde, gridBagConstraints);

        lblHasta.setText("hasta");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanelFiltros.add(lblHasta, gridBagConstraints);

        txtHasta.setTipo(0);
        txtHasta.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtHastaFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanelFiltros.add(txtHasta, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        jPanel3.add(jPanelFiltros, gridBagConstraints);

        add(jPanel3, java.awt.BorderLayout.NORTH);
        add(jPanelConsultaGenerico1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void cmbCampoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbCampoItemStateChanged
        try{
            if(!mbDesactiva){
                refrescarControlador(Integer.valueOf(cmbTabla.getFilaActual().msCampo(0)).intValue());
                
            }
        }catch(Exception e){
            JDepuracion.anadirTexto(getClass().getName(), e);
            utilesGUIx.msgbox.JMsgBox.mensajeError(this, e);
        }

    }//GEN-LAST:event_cmbCampoItemStateChanged

    private void txtHastaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHastaFocusLost
        try{
            if(!mbDesactiva){
                refrescarControlador(Integer.valueOf(cmbTabla.getFilaActual().msCampo(0)).intValue());
            }
        }catch(Exception e){
            JDepuracion.anadirTexto(getClass().getName(), e);
            utilesGUIx.msgbox.JMsgBox.mensajeError(this, e);
        }
            
    }//GEN-LAST:event_txtHastaFocusLost

    private void txtDesdeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesdeFocusLost
        try{
            if(!mbDesactiva){
                refrescarControlador(Integer.valueOf(cmbTabla.getFilaActual().msCampo(0)).intValue());
            }
        }catch(Exception e){
            JDepuracion.anadirTexto(getClass().getName(), e);
            utilesGUIx.msgbox.JMsgBox.mensajeError(this, e);
        }

    }//GEN-LAST:event_txtDesdeFocusLost

    private void cmbTablaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbTablaItemStateChanged
        try{
            if(!mbDesactiva){
                setControladorInterno(cmbTabla.getSelectedIndex());
            }
        }catch(Exception e){
            JDepuracion.anadirTexto(getClass().getName(), e);
            utilesGUIx.msgbox.JMsgBox.mensajeError(this, e);
        }
    }//GEN-LAST:event_cmbTablaItemStateChanged

    private void cmbTablaPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_cmbTablaPropertyChange
        if(!cmbTabla.isEnabled()){
            cmbTabla.setEnabled(true);
        }
    }//GEN-LAST:event_cmbTablaPropertyChange


    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private utilesGUIx.JComboBoxCZ cmbCampo;
    private utilesGUIx.JComboBoxCZ cmbTabla;
    private utilesGUIx.JLabelCZ jLabelCZ1;
    private utilesGUIx.JLabelCZ jLabelCZ4;
    private javax.swing.JPanel jPanel3;
    private utilesGUIx.formsGenericos.JPanelGenerico2 jPanelConsultaGenerico1;
    private javax.swing.JPanel jPanelFiltros;
    private utilesGUIx.JLabelCZ lblDesde;
    private utilesGUIx.JLabelCZ lblHasta;
    private utilesGUIx.JTextFieldCZ txtDesde;
    private utilesGUIx.JTextFieldCZ txtHasta;
    // End of variables declaration//GEN-END:variables



    
}
