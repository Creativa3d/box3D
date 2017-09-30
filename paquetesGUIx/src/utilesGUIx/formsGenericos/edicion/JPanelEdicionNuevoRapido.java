/*
 * JPanelEdicion.java
 *
 * Created on 10 de septiembre de 2004, 12:57
 */
package utilesGUIx.formsGenericos.edicion;

import java.awt.Component;
import java.awt.Container;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import utiles.IListaElementos;
import utiles.JDepuracion;
import utiles.JListaElementos;
import utilesGUIx.JGUIxConfigGlobal;
import utilesGUIx.formsGenericos.ISalir;
import utilesGUIx.plugin.IContainer;
import utilesGUIx.plugin.IPlugInFrame;
import utilesGUIx.plugin.toolBar.IComponenteAplicacion;

/**Muestra un form. de edición en forma de panel*/
public class JPanelEdicionNuevoRapido extends javax.swing.JPanel implements java.awt.event.ActionListener, ISalir, IPlugInFrame , IFormEdicionLista, IContainer {

    private static final long serialVersionUID = 1L;
    private ISalir moPadre;
    private IListaElementos moListaEdiciones = new JListaElementos();
//    private JButtonDesact moDesativado = new JButtonDesact();
    private boolean mbValidarDespuesEstablecer=true;
    private JFormEdicionParametros moParam=new JFormEdicionParametros();
    
    /** Creates new form JPanelEdicion */
    public JPanelEdicionNuevoRapido() {
        super();
        initComponents();
        btnCancelar.setVisible(false);
        jLabelAvisos1.setVisible(false);
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
    
    public void setValidarDespuesEstablecer(boolean pb){
        mbValidarDespuesEstablecer=pb;
    }

    public JButton getAceptar() {
        return btnAceptar;
    }

    public JButton getCancelat() {
        return btnCancelar;
    }
    public JButton getCancelar() {
        return btnCancelar;
    }

    public JButton getSalir() {
        return btnSalir;
    }

    public void addBoton(JComponent poComponent){
        jPanelBotones.add(poComponent);
    }
    public void addBoton(JComponent poComponent, int plPosicion){
        jPanelBotones.add(poComponent,null,plPosicion);
    }

    public void aceptar(){
        btnAceptarActionPerformed(null);
    }
    public void cancelar(){
        btnCancelarActionPerformed(null);
    }
    public void salir() {
        btnSalirActionPerformed(null);
    }
    public void setTitle(String psTitulo) {
    }
    /**
     * Establecemos el panel de los datos
     * @param poPanel Interfaz que debe cumplir el controlador de este panel
     * @param poPanelMismo componente a insertar en este panel, suele ser el mismo objeto que poPanel
     * @param poPadre el form. padre  de este panel, para que este panel pueda salir
     * @throws Exception error
     */
    public void setPanel(final IFormEdicionNavegador poPanel, final Component poPanelMismo, final ISalir poPadre) throws Exception {
        moPadre = poPadre;
        moListaEdiciones.add(poPanel);

        add(poPanelMismo, java.awt.BorderLayout.CENTER);
        setBounds(poPanel.getTanano().x, poPanel.getTanano().y, poPanel.getTanano().width, poPanel.getTanano().height);
        
        jLabelAvisos1.setAvisos(poPanel.getParametros().getAvisos());
        jLabelAvisos1.setVisible(poPanel.getParametros().getAvisos().size()>0);
        initEdicion(poPanel);
        if(poPanel.getParametros() !=null){
            poPanel.getParametros().getBotones().add(btnSalir);
            poPanel.getParametros().getBotones().add(btnAceptar);
            poPanel.getParametros().getBotones().add(btnCancelar);
            poPanel.getParametros().getBotones().add(jLabelAvisos1);
        }               

    }
    //inicializamos un panel
    private void initEdicion(final IFormEdicionNavegador poPanel) throws Exception{
        poPanel.rellenarPantalla();
        poPanel.ponerTipoTextos();
        
        
        if(poPanel.getParametros() != null && poPanel.getParametros().isSoloLectura()){
            poPanel.setBloqueoControles(true);
            if(Container.class.isAssignableFrom(poPanel.getClass())){
                JPanelEdicion.mostrarDatosBD((Container)poPanel);
            }
            poPanel.mostrarDatos();
        }else{
            poPanel.setBloqueoControles(false);
            if(Container.class.isAssignableFrom(poPanel.getClass())){
                JPanelEdicion.mostrarDatosBD((Container)poPanel);
            }
            poPanel.mostrarDatos();
            poPanel.habilitarSegunEdicion();
            
        }
    }
    
    /**
     * Añadimos una edición y la inicializamos
     */
    public void addEdicion(final IFormEdicion poPanel) throws Exception{
        moListaEdiciones.add(poPanel);
        initEdicion((IFormEdicionNavegador)poPanel);
    }
    /**
     * @return devolvemos la lista de ediciones
     */
    public IListaElementos getListaEdiciones() {
        return moListaEdiciones;
    }
    /**
     * Indica si todos los IFormEdicionNavegador son editables
     */
    public boolean isEditable() {
        boolean lbSoloLectura = true;
        for(int i = 0 ; i < moListaEdiciones.size(); i++){
            IFormEdicionNavegador loPanel = (IFormEdicionNavegador) moListaEdiciones.get(i);
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
        btnAceptar = new utilesGUIx.JButtonCZ();
        btnCancelar = new utilesGUIx.JButtonCZ();
        btnSalir = new utilesGUIx.JButtonCZ();

        setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        setLayout(new java.awt.BorderLayout());

        jPanelBotones.setBackground(new java.awt.Color(175, 181, 186));
        jPanelBotones.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanelBotones.setLayout(new java.awt.GridBagLayout());
        jPanelBotones.add(jLabelAvisos1, new java.awt.GridBagConstraints());

        jLabel1.setText(" ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
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

        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIx/images/Stop16.gif"))); // NOI18N
        btnSalir.setMnemonic('s');
        btnSalir.setText("Salir"); // NOI18N
        btnSalir.addActionListener(this);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanelBotones.add(btnSalir, gridBagConstraints);

        add(jPanelBotones, java.awt.BorderLayout.SOUTH);
    }

    // Code for dispatching events from components to event handlers.

    public void actionPerformed(java.awt.event.ActionEvent evt) {
        if (evt.getSource() == btnAceptar) {
            JPanelEdicionNuevoRapido.this.btnAceptarActionPerformed(evt);
        }
        else if (evt.getSource() == btnCancelar) {
            JPanelEdicionNuevoRapido.this.btnCancelarActionPerformed(evt);
        }
        else if (evt.getSource() == btnSalir) {
            JPanelEdicionNuevoRapido.this.btnSalirActionPerformed(evt);
        }
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        try {
            //para cada uno cancelamos
            for(int i = 0 ; i < moListaEdiciones.size(); i++){
                IFormEdicionNavegador loPanel = (IFormEdicionNavegador) moListaEdiciones.get(i);
                loPanel.cancelar();
            }

            if(moPadre==null){
                JGUIxConfigGlobal.getInstancia().getMostrarPantalla().cerrarForm(this);
            }else{
                moPadre.salir();
            }
            moPadre = null;
        } catch (Throwable e) {
            JDepuracion.anadirTexto(getClass().getName(), e);
            utilesGUIx.msgbox.JMsgBox.mensajeError(this, e);
        }
    }//GEN-LAST:event_btnSalirActionPerformed
    private void comprobarEdicion() throws Exception{
        if(!isEditable()){
            throw new Exception(JGUIxConfigGlobal.getInstancia().getEdicionNavegadorMensajeSoloLectura());
        }
    }
    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        if(btnAceptar.isEnabled()){
            try {
                btnAceptar.setEnabled(false);
                comprobarEdicion();
                boolean lbContinuar=true;
                if(!mbValidarDespuesEstablecer){
                    //para cada uno validamos
                    for(int i = 0 ; i < moListaEdiciones.size(); i++){
                        IFormEdicionNavegador loPanel = (IFormEdicionNavegador) moListaEdiciones.get(i);
                        lbContinuar &= loPanel.validarDatos();
                    }                      
                }
                if (lbContinuar) {
                    //para cada uno establecemos datos y  validamos
                    for(int i = 0 ; i < moListaEdiciones.size(); i++){
                        IFormEdicionNavegador loPanel = (IFormEdicionNavegador) moListaEdiciones.get(i);
                        if(Container.class.isAssignableFrom(loPanel.getClass())){
                            JPanelEdicion.establecerDatosBD((Container)loPanel);
                        }        
                        loPanel.establecerDatos();
                        if(mbValidarDespuesEstablecer){
                            lbContinuar &= loPanel.validarDatos();
                        }
                    }                      
                }
                if (lbContinuar) {
                    //para cada uno aceptamos 
                    for(int i = 0 ; i < moListaEdiciones.size() && lbContinuar; i++){
                        IFormEdicionNavegador loPanel = (IFormEdicionNavegador) moListaEdiciones.get(i);
                        loPanel.aceptar();
                    }                
                    //para cada uno nuevo
                    for(int i = 0 ; i < moListaEdiciones.size() && lbContinuar; i++){
                        IFormEdicionNavegador loPanel = (IFormEdicionNavegador) moListaEdiciones.get(i);
                        loPanel.nuevo();
                        if(Container.class.isAssignableFrom(loPanel.getClass())){
                            JPanelEdicion.mostrarDatosBD((Container)loPanel);
                        }        
                        loPanel.mostrarDatos();
                        loPanel.habilitarSegunEdicion();
                    }                

                    //el titulo solo el principal
                    moPadre.setTitle(((IFormEdicionNavegador) moListaEdiciones.get(0)).getTitulo());                
                }
            } catch (Throwable e) {
                JDepuracion.anadirTexto(getClass().getName(), e);
                utilesGUIx.msgbox.JMsgBox.mensajeError(this, e);
            } finally{
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        btnAceptar.setEnabled(true);
                    }
                });
            }
        }
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed

        
        try {
            //para cada uno nuevo, que ya cancela y mostramos
            for(int i = 0 ; i < moListaEdiciones.size(); i++){
                IFormEdicionNavegador loPanel = (IFormEdicionNavegador) moListaEdiciones.get(i);
                loPanel.nuevo();
                if(Container.class.isAssignableFrom(loPanel.getClass())){
                    JPanelEdicion.mostrarDatosBD((Container)loPanel);
                }        
                loPanel.mostrarDatos();
                loPanel.habilitarSegunEdicion();
                loPanel.setBloqueoControles(false);
                loPanel.habilitarSegunEdicion();
            }
            //el titulo solo el principal
            moPadre.setTitle(((IFormEdicionNavegador) moListaEdiciones.get(0)).getTitulo());                

        } catch (Exception e) {
            JDepuracion.anadirTexto(getClass().getName(), e);
            utilesGUIx.msgbox.JMsgBox.mensajeError(this, e);
        }
        
        

    }//GEN-LAST:event_btnCancelarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private utilesGUIx.JButtonCZ btnAceptar;
    private utilesGUIx.JButtonCZ btnCancelar;
    private utilesGUIx.JButtonCZ btnSalir;
    private javax.swing.JLabel jLabel1;
    private utilesGUIx.aplicacion.avisosGUI.JLabelAvisos jLabelAvisos1;
    private javax.swing.JPanel jPanelBotones;
    // End of variables declaration//GEN-END:variables
}
