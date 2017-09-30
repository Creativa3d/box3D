/*
 * JFormConexion.java
 *
 * Created on 5 de agosto de 2005, 18:10
 */

package utilesGUIx.configForm.antig;

import utilesGUIx.configForm.JConexion;
import utilesGUIx.configForm.*;
import utiles.JDepuracion;


public class JFormConexion extends javax.swing.JDialog {
    private JPanelConexiones moPanelConex;
    
    /**
     * Creates new form JFormConexion 
     */
    public JFormConexion(java.awt.Frame parent, boolean modal, JConexion poConexion) throws Exception {
        this(parent, modal, poConexion, true);
    }
    public JFormConexion(java.awt.Frame parent, boolean modal, JConexion poConexion, boolean pbLeerconfig) throws Exception {
        super(parent, modal);
        initComponents();
        moPanelConex = new JPanelConexiones();
        if(pbLeerconfig){
            moPanelConex.setDatosYLeerConfig(poConexion);
        }else{
            moPanelConex.setDatosAPelo(poConexion);
        }
        getContentPane().add(moPanelConex, java.awt.BorderLayout.CENTER);
        
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        btnProbar = new javax.swing.JButton();
        btnAceptar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        btnProbar.setText("Probar");
        btnProbar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProbarActionPerformed(evt);
            }
        });
        jPanel2.add(btnProbar);

        btnAceptar.setText("Aceptar");
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });
        jPanel2.add(btnAceptar);

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        jPanel2.add(btnCancelar);

        getContentPane().add(jPanel2, java.awt.BorderLayout.SOUTH);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-336)/2, (screenSize.height-248)/2, 336, 248);
    }// </editor-fold>//GEN-END:initComponents

    private void btnProbarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProbarActionPerformed
        try{
            moPanelConex.probar(); 
            utilesGUIx.msgbox.JMsgBox.mensajeInformacion(this, "Prueba realizada con �xito");

        }catch(Exception e){
            JDepuracion.anadirTexto(getClass().getName(), e);
            utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this, e, getClass().getName());
        }
        
    }//GEN-LAST:event_btnProbarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        try{
            moPanelConex.cancelar();
            dispose();
        }catch(Exception e){
            JDepuracion.anadirTexto(getClass().getName(), e);
            utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this, e, getClass().getName());
        }
        
    }//GEN-LAST:event_btnCancelarActionPerformed
    
    
    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        try{
            moPanelConex.establecerDatos();
            moPanelConex.aceptar();
            dispose();
        }catch(Exception e){
            JDepuracion.anadirTexto(getClass().getName(), e);
            utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this, e, getClass().getName());
        }
        
    }//GEN-LAST:event_btnAceptarActionPerformed

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnProbar;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
    
}