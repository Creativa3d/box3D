/*
 * JPanelOpciones.java
 *
 * Created on 30 de septiembre de 2008, 10:33
 */

package utilesGUIx.formsGenericos.opciones;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Icon;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import utiles.IListaElementos;
import utiles.JListaElementos;
import utilesGUIx.formsGenericos.IMostrarPantalla;
import utilesGUIx.formsGenericos.edicion.IFormEdicion;
import utilesGUIx.formsGenericos.edicion.JFormEdicionParametros;
import utilesGUIx.plugin.IContainer;
import utilesGUIx.plugin.toolBar.IComponenteAplicacion;
import utilesGUIx.plugin.toolBar.JComponenteAplicacionGrupo;



public class JPanelOpciones extends javax.swing.JPanel implements ActionListener, IPanelOpciones, IContainer {


    private IListaElementos moElementos = new JListaElementos();
    private IMostrarPantalla moMostrar;
    private JFormEdicionParametros moParametros = new JFormEdicionParametros();
    
    
    /** Creates new form JFormOpciones */
    /** Creates new form JPanelOpciones */
    public JPanelOpciones() {
        initComponents();
    }
    
    public void inicializar(IFormEdicion[] paoFormEdiciones, IMostrarPantalla poMostrar) throws Exception{
        moMostrar = poMostrar;
        if(paoFormEdiciones!=null){
            for(int i = 0 ; i < paoFormEdiciones.length; i++){
                add(paoFormEdiciones[i]);
            }
        }
        this.setSize(550, 640);
        if(moElementos.size()>0){
            visualizarPanel((JOpcionesElemento)moElementos.get(0));
        }
    }
    
    public void add(IFormEdicion poForm) {
        JOpcionesElemento loElem = new JOpcionesElemento(poForm); 
        moElementos.add(loElem);
        addBoton(loElem, moElementos.size()-1);
        if(moElementos.size()==1){
            try {
                visualizarPanel((JOpcionesElemento)moElementos.get(0));
            } catch (Exception ex) {
                throw new Error(ex);
            }
        }
    }

    public void remove(IFormEdicion poForm) {
        for(int i = 0 ; i < moElementos.size(); i++){
            JOpcionesElemento loElem = (JOpcionesElemento)moElementos.get(i);
            if(loElem.moFormEdicion==poForm){
                moElementos.remove(loElem);
            }
        }
    }

    public IFormEdicion get(int i) {
        return ((JOpcionesElemento)moElementos.get(i)).moFormEdicion;
    }
    public int count(){
        return moElementos.size();
    }
    public String getIdentificador() {
        return this.getClass().getName();
    }

    public IContainer getContenedorI() {
        return this;
    }

    public JFormEdicionParametros getParametros() {
        return moParametros;
    }
    public IComponenteAplicacion getListaComponentesAplicacion() {
        return null;
    }

    public void aplicarListaComponentesAplicacion() {
    }
   
    private java.awt.GridBagConstraints getConstraintsPanelOpciones(){
        java.awt.GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        
        return gridBagConstraints;
    }
    private void addBoton(JOpcionesElemento poElemento, int plIndex){
        java.awt.GridBagConstraints gridBagConstraints;
        javax.swing.JButton jButtonGeneral = new javax.swing.JButton();
        jButtonGeneral.setBackground(new java.awt.Color(237, 243, 251));
        jButtonGeneral.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        jButtonGeneral.setText(poElemento.moFormEdicion.getTitulo());
        jButtonGeneral.setToolTipText(poElemento.moFormEdicion.getTitulo());
        jButtonGeneral.setIcon((Icon)poElemento.moFormEdicion.getParametros().getIcono());
        jButtonGeneral.setBorderPainted(false);
        jButtonGeneral.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
//        jButtonGeneral.setMaximumSize(new java.awt.Dimension(90, 50));
        jButtonGeneral.setMinimumSize(new java.awt.Dimension(10, 50));
        jButtonGeneral.setPreferredSize(new java.awt.Dimension(90, 50));
        jButtonGeneral.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonGeneral.addActionListener(this);
        jButtonGeneral.setActionCommand(String.valueOf(plIndex));
        
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        
        jPanelBotones2.add(jButtonGeneral, gridBagConstraints, plIndex);
        
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 0.0;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        
        jPanelBotones2.add(lblFinal, gridBagConstraints, plIndex+1);
        
        jPanelOpciones.updateUI();
        
    }
    private void visualizarPanel(JOpcionesElemento poElemento) throws Exception{
        cargarPantalla(poElemento);

        jPanelOpciones.removeAll();
        lblTitulo.setText(poElemento.moFormEdicion.getTitulo());
        ((JPanel)poElemento.moFormEdicion).setMinimumSize(jPanelOpciones.getMinimumSize());
        ((JPanel)poElemento.moFormEdicion).setMaximumSize(jPanelOpciones.getMaximumSize());
        ((JPanel)poElemento.moFormEdicion).setPreferredSize(jPanelOpciones.getPreferredSize());
        ((JPanel)poElemento.moFormEdicion).setVisible(true);
        jPanelOpciones.add(((JPanel)poElemento.moFormEdicion), getConstraintsPanelOpciones());
        jPanelOpciones.updateUI();
    }    
    private void cargarPantalla(JOpcionesElemento poElemento) throws Exception {
        if(!poElemento.mbCargado){
            poElemento.moFormEdicion.habilitarSegunEdicion();
            poElemento.moFormEdicion.rellenarPantalla();
            poElemento.moFormEdicion.ponerTipoTextos();
            poElemento.moFormEdicion.mostrarDatos();
            poElemento.mbCargado=true;
        }
    }
    
    private void visualizarPanel(String actionCommand) throws Exception {
        visualizarPanel(
                (JOpcionesElemento)moElementos.get(Integer.valueOf(actionCommand).intValue())
                );
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanelBotones = new javax.swing.JPanel();
        jPanelBotones2 = new javax.swing.JPanel();
        lblFinal = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanelTitulo = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        jPanelOpciones = new javax.swing.JPanel();
        jPanelNavegacion = new javax.swing.JPanel();
        jButtonAceptar = new javax.swing.JButton();
        jButtonCancelar = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(201, 533));
        setLayout(new java.awt.BorderLayout());

        jSplitPane1.setBorder(null);
        jSplitPane1.setDividerLocation(130);

        jPanelBotones.setLayout(new java.awt.GridBagLayout());

        jPanelBotones2.setBackground(new java.awt.Color(237, 243, 251));
        jPanelBotones2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanelBotones2.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weighty = 0.1;
        jPanelBotones2.add(lblFinal, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 10, 5);
        jPanelBotones.add(jPanelBotones2, gridBagConstraints);

        jScrollPane1.setViewportView(jPanelBotones);

        jSplitPane1.setLeftComponent(jScrollPane1);

        jPanel3.setPreferredSize(new java.awt.Dimension(42, 500));
        jPanel3.setLayout(new java.awt.GridBagLayout());

        jPanelTitulo.setBackground(new java.awt.Color(0, 0, 255));
        jPanelTitulo.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jPanelTitulo.setLayout(new java.awt.GridBagLayout());

        lblTitulo.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(255, 255, 255));
        lblTitulo.setText("jLabel1");
        lblTitulo.setMaximumSize(new java.awt.Dimension(34, 40));
        lblTitulo.setMinimumSize(new java.awt.Dimension(34, 40));
        lblTitulo.setPreferredSize(new java.awt.Dimension(34, 40));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanelTitulo.add(lblTitulo, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        jPanel3.add(jPanelTitulo, gridBagConstraints);

        jPanelOpciones.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel3.add(jPanelOpciones, gridBagConstraints);

        jSplitPane1.setRightComponent(jPanel3);

        add(jSplitPane1, java.awt.BorderLayout.CENTER);

        jButtonAceptar.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        jButtonAceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIx/images/accept.gif"))); // NOI18N
        jButtonAceptar.setText("Aceptar");
        jButtonAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAceptarActionPerformed(evt);
            }
        });
        jPanelNavegacion.add(jButtonAceptar);

        jButtonCancelar.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        jButtonCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIx/images/cancel.gif"))); // NOI18N
        jButtonCancelar.setText("Cancelar");
        jButtonCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelarActionPerformed(evt);
            }
        });
        jPanelNavegacion.add(jButtonCancelar);

        add(jPanelNavegacion, java.awt.BorderLayout.SOUTH);
    }// </editor-fold>//GEN-END:initComponents

    


    public void actionPerformed(ActionEvent e) {
        try {
            visualizarPanel(e.getActionCommand());
        } catch (Exception ex) {
            utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this, ex, getClass().getName());
        }
    }


private void jButtonAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAceptarActionPerformed
        try {
            for(int i = 0 ; i < moElementos.size(); i++){
                JOpcionesElemento loElem = (JOpcionesElemento)moElementos.get(i);
                if(loElem.mbCargado){
                    loElem.moFormEdicion.establecerDatos();
                }
            }
            for(int i = 0 ; i < moElementos.size(); i++){
                JOpcionesElemento loElem = (JOpcionesElemento)moElementos.get(i);
                if(loElem.mbCargado){
                    loElem.moFormEdicion.aceptar();
                }
            }
            moMostrar.cerrarForm(this);
        } catch (Exception ex) {
            utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this, ex, getClass().getName());
        }
}//GEN-LAST:event_jButtonAceptarActionPerformed

private void jButtonCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelarActionPerformed
    
        try {
            for(int i = 0 ; i < moElementos.size(); i++){
                JOpcionesElemento loElem = (JOpcionesElemento)moElementos.get(i);
                if(loElem.mbCargado){
                    loElem.moFormEdicion.cancelar();
                }
            }

            moMostrar.cerrarForm(this);
        } catch (Exception ex) {
            utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this, ex, getClass().getName());
        }
    
}//GEN-LAST:event_jButtonCancelarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAceptar;
    private javax.swing.JButton jButtonCancelar;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanelBotones;
    private javax.swing.JPanel jPanelBotones2;
    private javax.swing.JPanel jPanelNavegacion;
    private javax.swing.JPanel jPanelOpciones;
    private javax.swing.JPanel jPanelTitulo;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JLabel lblFinal;
    private javax.swing.JLabel lblTitulo;
    // End of variables declaration//GEN-END:variables


 




}
class JOpcionesElemento {
    public IFormEdicion moFormEdicion;
    public boolean mbCargado=false;
    
    JOpcionesElemento (IFormEdicion poFormEdicion){
        moFormEdicion = poFormEdicion;
    }
}
