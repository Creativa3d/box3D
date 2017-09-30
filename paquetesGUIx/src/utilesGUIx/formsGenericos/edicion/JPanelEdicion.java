/*
 * JPanelEdicion.java
 *
 * Created on 10 de septiembre de 2004, 12:57
 */

package utilesGUIx.formsGenericos.edicion;

import ListDatos.ECampoError;
import ListDatos.JListDatos;
import java.awt.Component;
import java.awt.Container;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import utiles.IListaElementos;
import utiles.JListaElementos;
import utilesGUIx.JGUIxConfigGlobal;
import utilesGUIx.JGUIxConfigGlobalModelo;
import utilesGUIx.formsGenericos.ISalir;
import utilesGUIx.plugin.IContainer;
import utilesGUIx.plugin.IPlugInFrame;
import utilesGUIx.plugin.toolBar.IComponenteAplicacion;
import utilesGUIx.plugin.toolBar.JComponenteAplicacionGrupo;

/**Muestra un form. de edición en forma de panel*/
public class JPanelEdicion extends javax.swing.JPanel implements java.awt.event.ActionListener, ISalir, IPlugInFrame, IFormEdicionLista, IContainer {

    private ISalir moPadre;
    private IListaElementos moListaEdiciones = new JListaElementos();
    private JFormEdicionParametros moParam=new JFormEdicionParametros();
        
    /** Creates new form JPanelEdicion */
    public JPanelEdicion() {
        super();
        initComponents();
        JGUIxConfigGlobal.getInstancia().setTextoTodosComponent(this, "JPanelEdicion");
    }

    public String getIdentificador() {
        return this.getClass().getName();
    }

    public IContainer getContenedorI() {
        return this;
    }

    public JFormEdicionParametros getParametros() {
        return moParam;
    }
    public IComponenteAplicacion getListaComponentesAplicacion() {
        return null;
    }

    public void aplicarListaComponentesAplicacion() {
    }
    

    /**
     * Establecemos el panel de los datos
     * @param poPanel Interfaz que debe cumplir el controlador de este panel
     * @param poPanelMismo componente a insertar en este panel, suele ser el mismo objeto que poPanel
     * @param poPadre el form. padre  de este panel, para que este panel pueda salir
     * @throws Exception error
     */
    public void setPanel(final IFormEdicion poPanel, final Component poPanelMismo, final ISalir poPadre) throws Exception {
        moPadre = poPadre;
        add(poPanelMismo, java.awt.BorderLayout.CENTER);
        moListaEdiciones.add(poPanel);

        jLabelAvisos1.setAvisos(poPanel.getParametros().getAvisos());
        jLabelAvisos1.setVisible(poPanel.getParametros().getAvisos().size()>0);
        
        initEdicion(poPanel);
        setBounds(poPanel.getTanano().x, poPanel.getTanano().y, poPanel.getTanano().width, poPanel.getTanano().height);
        if(poPanel.getParametros() !=null){
            if(poPanel.getParametros().isSoloLectura()){
                btnAceptar.setVisible(false);
                btnCancelar.setText("Salir");
            }
            poPanel.getParametros().getBotones().add(btnAceptar);
            poPanel.getParametros().getBotones().add(btnCancelar);
            poPanel.getParametros().getBotones().add(jLabelAvisos1);
        }
    }
    //inicializamos un panel
    private void initEdicion(final IFormEdicion poPanel) throws Exception{
        poPanel.rellenarPantalla();
        poPanel.ponerTipoTextos();
        if(Container.class.isAssignableFrom(poPanel.getClass())){
            mostrarDatosBD((Container)poPanel);
        }        
        poPanel.mostrarDatos();
        poPanel.habilitarSegunEdicion();
    }
    /**
     * Añadimos una edición y la inicializamos
     */
    public void addEdicion(final IFormEdicion poPanel) throws Exception{
        moListaEdiciones.add(poPanel);
        initEdicion(poPanel);
    }
    /**
     * @return devolvemos la lista de ediciones
     */
    public IListaElementos getListaEdiciones() {
        return moListaEdiciones;
    }    
    public static void mostrarDatosBD(final java.awt.Container  poComponente){
        for(int i = 0; i < poComponente.getComponentCount(); i++){
            //establecemos el bloque al componente concreto
            if(ITextBD.class.isAssignableFrom(poComponente.getComponent(i).getClass())){
                ITextBD loText = (ITextBD)poComponente.getComponent(i);
                loText.mostrarDatosBD();
            }
            //llamada recursiva para los contenedores
            if(java.awt.Container.class.isAssignableFrom(poComponente.getComponent(i).getClass()) &&
               poComponente != poComponente.getComponent(i)
              ){
                mostrarDatosBD((java.awt.Container)poComponente.getComponent(i));
            }
        }
    }
    public static void establecerDatosBD(final java.awt.Container  poComponente) throws ECampoError{
        for(int i = 0; i < poComponente.getComponentCount(); i++){
            //establecemos el bloque al componente concreto
            if(ITextBD.class.isAssignableFrom(poComponente.getComponent(i).getClass())){
                ITextBD loText = (ITextBD)poComponente.getComponent(i);
                loText.establecerDatosBD();
            }
            //llamada recursiva para los contenedores
            if(java.awt.Container.class.isAssignableFrom(poComponente.getComponent(i).getClass()) &&
               poComponente != poComponente.getComponent(i)
              ){
                establecerDatosBD((java.awt.Container)poComponente.getComponent(i));
            }
        }
    }

    public void setEstado( String psEstado ) {
        jLabel1.setText(psEstado);
    }
    

    public JLabel getEstadoLabel() {
        return jLabel1;
    }

    public JPanel getPanelBotones() {
        return jPanelBotones;
    }
    /**
     * Indica si todos los IFormEdicion son editables
     */
    public boolean isEditable() {
        boolean lbSoloLectura = true;
        for(int i = 0 ; i < moListaEdiciones.size(); i++){
            IFormEdicion loPanel = (IFormEdicion) moListaEdiciones.get(i);
            lbSoloLectura &= loPanel.getParametros() != null && loPanel.getParametros().isSoloLectura();
        }
        return !lbSoloLectura;
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanelBotones = new javax.swing.JPanel();
        jLabelAvisos1 = new utilesGUIx.aplicacion.avisosGUI.JLabelAvisos();
        jLabel1 = new javax.swing.JLabel();
        btnAceptar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        setLayout(new java.awt.BorderLayout());

        jPanelBotones.setBackground(new java.awt.Color(175, 181, 186));
        jPanelBotones.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanelBotones.setLayout(new java.awt.GridBagLayout());

        jLabelAvisos1.setText(" ");
        jPanelBotones.add(jLabelAvisos1, new java.awt.GridBagConstraints());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        jPanelBotones.add(jLabel1, gridBagConstraints);

        btnAceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIx/images/accept.gif"))); // NOI18N
        btnAceptar.setMnemonic('a');
        btnAceptar.setText("Aceptar"); // NOI18N
        btnAceptar.addActionListener(this);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelBotones.add(btnAceptar, gridBagConstraints);

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIx/images/cancel.gif"))); // NOI18N
        btnCancelar.setMnemonic('c');
        btnCancelar.setText("Cancelar"); // NOI18N
        btnCancelar.addActionListener(this);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelBotones.add(btnCancelar, gridBagConstraints);

        add(jPanelBotones, java.awt.BorderLayout.SOUTH);
    }

    // Code for dispatching events from components to event handlers.

    public void actionPerformed(java.awt.event.ActionEvent evt) {
        if (evt.getSource() == btnAceptar) {
            JPanelEdicion.this.btnAceptarActionPerformed(evt);
        }
        else if (evt.getSource() == btnCancelar) {
            JPanelEdicion.this.btnCancelarActionPerformed(evt);
        }
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        try{
            //para cada uno cancelamos
            for(int i = 0 ; i < moListaEdiciones.size(); i++){
                IFormEdicion loPanel = (IFormEdicion) moListaEdiciones.get(i);
                loPanel.cancelar();
            }
            
            if(moPadre!=null){
                moPadre.salir();
            }
            moPadre = null;
            moListaEdiciones = null;
        }catch(Throwable e){
            utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this, e, getClass().getName());
        }        
        
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
       boolean lbContinuar = true;
        try{
            btnAceptar.setEnabled(false);            

            if(!isEditable()){
                throw new Exception(JGUIxConfigGlobal.getInstancia().getEdicionNavegadorMensajeSoloLectura() );
            }
            //para cada uno establecemos datos y  validamos
            for(int i = 0 ; i < moListaEdiciones.size(); i++){
                IFormEdicion loPanel = (IFormEdicion) moListaEdiciones.get(i);
                if(Container.class.isAssignableFrom(loPanel.getClass())){
                    establecerDatosBD((Container)loPanel);
                }        
                loPanel.establecerDatos();
                lbContinuar &= loPanel.validarDatos();
            }
            //para cada uno aceptamos
            for(int i = 0 ; i < moListaEdiciones.size() && lbContinuar; i++){
                IFormEdicion loPanel = (IFormEdicion) moListaEdiciones.get(i);
                loPanel.aceptar();
            }
            if(moPadre!=null && lbContinuar){
                moPadre.salir();
            }else{
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        btnAceptar.setEnabled(true);
                    }
                });
            }
        }catch(Throwable e){
            utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this, e, getClass().getName());
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    btnAceptar.setEnabled(true);
                }
            });
        } finally{
        }         
    }//GEN-LAST:event_btnAceptarActionPerformed

    public void salir() {
        btnCancelarActionPerformed(null);
    }

    public void setTitle(String psTitulo) {
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnAceptar;
    public javax.swing.JButton btnCancelar;
    private javax.swing.JLabel jLabel1;
    private utilesGUIx.aplicacion.avisosGUI.JLabelAvisos jLabelAvisos1;
    private javax.swing.JPanel jPanelBotones;
    // End of variables declaration//GEN-END:variables

    
}
