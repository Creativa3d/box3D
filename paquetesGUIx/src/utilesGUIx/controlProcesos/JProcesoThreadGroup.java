/*
 * JProcesoThreadGroup.java
 *
 * Created on 25 de julio de 2008, 17:49
 */

package utilesGUIx.controlProcesos;

import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.SwingUtilities;
import utiles.IListaElementos;
import utiles.JDepuracion;
import utilesGUI.procesar.IProcesoAccion;


public class JProcesoThreadGroup extends javax.swing.JPanel implements IProcesoThreadGroup, MouseListener{
    private static final long serialVersionUID = 1L;

    private transient IProcesoThreadGroup moManejador;
    private int mlSegundosRefresco=3;
    private JFormProcesoControl moForm=null;

    private boolean mbVerFormPorADD = true;
    private boolean mbPulsadoLupa = false;
    private boolean mbPuedePulsarseLupa = true;

    private long mlADD = -1;
    private boolean mbContinuar=true;
    private final Timer timer;
    
    /** Creates new form JProcesoThreadGroup */
    public JProcesoThreadGroup() {
        initComponents();
        jProgressBar1.setStringPainted(true);
        jProgressBar1.setVisible(false);
        mbPuedePulsarseLupa = false;
//        btnVer.setVisible(false);
        moManejador = new JProcesoManejador(new JProcesoElementoFactoryMethod());
        moForm = new JFormProcesoControl(this);
        
        timer = new Timer();
        timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    controlGrafico();
                }
            }, mlSegundosRefresco * 1000, mlSegundosRefresco * 1000);
        
        
        mbContinuar = true;
    }

    public void controlGrafico() {
        try{
            if(mbContinuar){
                if(!moManejador.isProcesosActivos()){
                    mbPulsadoLupa = false;
                    setMostrarBarra(false);
                    ocultarForm();
                }else{
                    long lAux = (new Date()).getTime();

                    if(isVerFormPorADD() && mlADD != -1 && (lAux - mlADD) > 2*1000  &&
                        JProcesoManejador.isProcesosMostrarForm(moManejador.getListaElementos()) ){
                        verForm();
                    }
                    if(!JProcesoManejador.isProcesosMostrarForm(moManejador.getListaElementos()) &&
                       !mbPulsadoLupa){
                        ocultarForm();
                    }
                }
            }
            if(mbContinuar){
                if(moManejador.isProcesosActivos()){
                    setMostrarBarra(true);
                }
            }
        }catch(Throwable e){
            JDepuracion.anadirTexto(getClass().getName(), e);
        }
    }
    public IProcesoThreadGroup getManejador(){
        return moManejador;
    }
    public void setManejador(IProcesoThreadGroup poManejador){
        moManejador=poManejador;
    }


    public void addProcesoSwingYEjecutar(final IProcesoAccion poProceso) {
        addProcesoSwingYEjecutar(poProceso, true);
    }

    public void addProcesoSwingYEjecutar(IProcesoAccion poProceso, boolean pbConMostrarForm) {
        addProcesoYEjecutar(poProceso, pbConMostrarForm, true);
    }

    public void addProcesoYEjecutar(IProcesoAccion poProceso){
        addProcesoYEjecutar(poProceso, true);
    }
    
    public void addProcesoYEjecutar(IProcesoAccion poProceso, boolean pbConMostrarForm) {
        addProcesoYEjecutar(poProceso,pbConMostrarForm,false);
    }

    private synchronized void addProcesoYEjecutar(IProcesoAccion poProceso, boolean pbConMostrarForm, boolean pbIsSwing) {
        mbContinuar = true;
//        if (pbIsSwing) {
//            moManejador.addProcesoSwingYEjecutar(poProceso, pbConMostrarForm);
//        } else {
            moManejador.addProcesoYEjecutar(poProceso, pbConMostrarForm);
//        }
        setMostrarBarra(true);
        if (pbConMostrarForm) {
            mlADD = (new Date()).getTime();
        } else {
            mlADD = -1;
        }

    }

    public boolean isProcesosActivos() {
        return moManejador.isProcesosActivos();
    }

    public int getProcesoTotal() {
        return moManejador.getProcesoTotal();
    }
    public String getProcesoTexto(){
        return moManejador.getProcesoTexto();
    }
    public int getIndice(Object elemento) {
        return moManejador.getIndice(elemento);
    }

    public IListaElementos getListaElementos() {
        return moManejador.getListaElementos();
    }

    public void addListener(IProcesoThreadGroupListener poListener) {
        moManejador.addListener(poListener);
    }

    public void removeListener(IProcesoThreadGroupListener poListener) {
        moManejador.removeListener(poListener);
    }
    public IListaElementos getListaProcesos(){
        return moManejador.getListaProcesos();
    }
    private void setMostrarBarra(final boolean pbVer){
        if(SwingUtilities.isEventDispatchThread()){
            jProgressBar1.setVisible(pbVer);
            mbPuedePulsarseLupa=pbVer;
//            btnVer.setVisible(pbVer);
            repaint();
            if(pbVer){
                texto();
            }

        }else{
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    jProgressBar1.setVisible(pbVer);
                    mbPuedePulsarseLupa=pbVer;
        //            btnVer.setVisible(pbVer);
                    repaint();
                    if(pbVer){
                        texto();
                    }
                }
            });
        }

    }
    private void texto(){
        final int lTotalFinal = moManejador.getProcesoTotal();
        jProgressBar1.setValue(lTotalFinal);
        jProgressBar1.setString("" + String.valueOf(lTotalFinal) + "% " + moManejador.getProcesoTexto());
        jProgressBar1.setStringPainted(true);
        if(getForm().isVisible()) {
            getForm().actualizar();
        }
    }

    private JFormProcesoControl getForm(){
        return moForm;
    }
//    public void cambioProcesos(IProcesoThreadGroup poProcesosGroup) {
//        getForm().actualizar();
//    }
//
    void formSalir() {
        mbPulsadoLupa=false;
        mlADD=-1;
    }
    
    void ocultarForm(){
        if(SwingUtilities.isEventDispatchThread()){
            getForm().setVisible(false);
        }else{
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    getForm().setVisible(false);
                }
            });
        }
    }
    
    void verForm(){
        if(SwingUtilities.isEventDispatchThread()){
            getForm().setVisible(true);
            getForm().actualizar();
        }else{

            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    if(moManejador.isProcesosActivos() || mbPulsadoLupa){
                        getForm().setVisible(true);
                        getForm().actualizar();
                    }
                }
            });
        }
        
    }



    protected void finalize() throws Throwable {
        mbContinuar=false;
        timer.cancel();
        timer.purge();
        super.finalize();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jProgressBar1 = new javax.swing.JProgressBar();

        setLayout(new java.awt.GridBagLayout());

        jProgressBar1.setStringPainted(true);
        jProgressBar1.addMouseListener(this);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(jProgressBar1, gridBagConstraints);
    }

    // Code for dispatching events from components to event handlers.

    public void mouseClicked(java.awt.event.MouseEvent evt) {
        if (evt.getSource() == jProgressBar1) {
            JProcesoThreadGroup.this.jProgressBar1MouseClicked(evt);
        }
    }

    public void mouseEntered(java.awt.event.MouseEvent evt) {
    }

    public void mouseExited(java.awt.event.MouseEvent evt) {
    }

    public void mousePressed(java.awt.event.MouseEvent evt) {
    }

    public void mouseReleased(java.awt.event.MouseEvent evt) {
    }// </editor-fold>//GEN-END:initComponents

    private void jProgressBar1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jProgressBar1MouseClicked

        if(mbPuedePulsarseLupa){
            mbPulsadoLupa=true;
            verForm();
        }

    }//GEN-LAST:event_jProgressBar1MouseClicked
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar jProgressBar1;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the mbVerFormPorADD
     */
    public boolean isVerFormPorADD() {
        return mbVerFormPorADD;
    }

    /**
     * @param mbVerFormPorADD the mbVerFormPorADD to set
     */
    public void setVerFormPorADD(boolean mbVerFormPorADD) {
        this.mbVerFormPorADD = mbVerFormPorADD;
    }

    
}
