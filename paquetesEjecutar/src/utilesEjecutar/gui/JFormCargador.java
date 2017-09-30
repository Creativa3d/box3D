/*
 * JFormCargador1.java
 *
 * Created on 17-feb-2009, 19:07:33
 */

package utilesEjecutar.gui;

import java.awt.Image;
import java.awt.MediaTracker;
import java.io.File;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import utiles.IListaElementos;
import utiles.JDepuracion;
import utiles.JListaElementos;
import utilesEjecutar.JControladorCoordinadorEjecutar;

/**Visualiza el proceso pendiente*/
public class JFormCargador extends javax.swing.JFrame {
    private static final long serialVersionUID = 1L;
    private JControladorCoordinadorEjecutar moCoordinador;
    private Thread moThread;
    private boolean mbLogo=false;
    private boolean mbLogo2Plano=false;

    /** Creates new form JFormCargador1 */
    public JFormCargador() {
        initComponents();
        jProgressBar1.setIndeterminate(true);
        setTamanoLogo(0,0);
        JFormCargador.this.setVisible(true);
        moThread = new Thread(new Runnable() {
            public void run() {
                try{
                    while(moCoordinador==null || !moCoordinador.isFin()){
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ex) {
                        }
                        JFormCargador.this.actuGUI();
                    }
                    JFormCargador.this.actuGUI();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                    }
                }finally{
                    if(moCoordinador!=null){
                        if(moCoordinador.getErrores().size()>0 &&
                           moCoordinador.isMostrarErrores()){
                           SwingUtilities.invokeLater(new Runnable() {
                                        public void run() {
                                            JFormErrores loFormErrores = new JFormErrores(moCoordinador);
                                            loFormErrores.setVisible(true);
                                        }
                                    });
                            JFormCargador.this.cerrar();
                        }else{
                            JFormCargador.this.cerrar();
                            if(moCoordinador.isSalirAlFinalizar()){
                                System.exit(0);
                            }
                        }
                    }

                }
            }
        });
        moThread.start();
    }
    private synchronized void actuGUI(){
        try {
            SwingUtilities.invokeLater(new Runnable() {

                public void run() {
//                    if(moCoordinador==null){
//                        jProgressBar1.setIndeterminate(true);
//                    }
                    if(moCoordinador!=null){
                        setTotal(moCoordinador.getTotal());
                        setCompletado(moCoordinador.getCompletado());
                        addTexto(moCoordinador.getTextoGUI());
                        if (moCoordinador.isFin()) {
                            setCompletado(moCoordinador.getTotal());
                        }
                        setLogo(moCoordinador.getLogo());
                    }
                }
            });
        } catch (Exception ex) {
            JDepuracion.anadirTexto(getClass().getName(), ex);
        }

    }

    public synchronized void setCoordinador(JControladorCoordinadorEjecutar loCoor) {
        moCoordinador = loCoor;
        setLogo( moCoordinador.getLogo());
    }
    private synchronized void setLogo2Plano(final String psImagen) {
        if(!mbLogo2Plano && !mbLogo){
            new Thread(new Runnable() {
                public void run() {
                    setLogo(psImagen);
                }
            }).start();
        }

    }
    private synchronized void setLogo(String psImagen) {
       if(psImagen!=null && !psImagen.equals("") && !mbLogo){
            mbLogo2Plano = true;
            try{
                Image loImage=null;
                File lofile = new File(psImagen);
                if(lofile.exists()){
                    loImage= (new ImageIcon(psImagen)).getImage();
                }else{
                    try{
                        loImage = (new ImageIcon(new URL(psImagen))).getImage() ;
                    }catch(Exception e){
                    }
                }

                if(loImage!=null){
                    final MediaTracker MTracker = new MediaTracker(this);
                    MTracker.addImage(loImage, 0);
                    try {
                        MTracker.waitForAll(2000);
                        if(MTracker.isErrorAny()){
                            JDepuracion.anadirTexto(getClass().getName(), "Algun error al cargar la imagen " + psImagen);
                        }else{
                            mbLogo=true;
                        }
                        lblLogo.setIcon(new ImageIcon(loImage));
//                        lblLogo.updateUI();
                        setTamanoLogo(
                                loImage.getWidth(lblLogo),
                                loImage.getHeight(lblLogo)
                                );

                    } catch (InterruptedException IE) {
                        JDepuracion.anadirTexto(getClass().getName(),IE);
                    }
                }
            }finally{
                mbLogo2Plano=false;
            }
       }
    }
    private synchronized void setTamanoLogo(int lWidth, int lHeight){
            if(lWidth<300){
                lWidth = 500;
            }
            if(lWidth>800){
                lWidth = 800;
            }
            if(lHeight<100){
                lHeight=100;
            }
            if(lHeight>600){
                lHeight=600;
            }
            setSize(
                    lWidth, 
                    lHeight + jProgressBar1.getHeight() + lblActual.getHeight() + 50);
        
    }

    public synchronized void setTotal(final int plTotal) {
        try{
            jProgressBar1.setMaximum(plTotal);
        }catch(Exception e){
            
        }
    }

    public synchronized void addTexto(final String psTexto) {
        try{
                lblActual.setText(psTexto);
        }catch(Exception e){

        }
    }

    public synchronized void setCompletado(final int plCompletado) {
        if(jProgressBar1.isIndeterminate() != (jProgressBar1.getMaximum()<=0)){
            jProgressBar1.setIndeterminate(jProgressBar1.getMaximum()<=0);
        }
        try{
                jProgressBar1.setValue(plCompletado);
        }catch(Exception e){
        }
    }

    public synchronized void cerrar() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFormCargador.this.dispose();
            }
        });
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        lblLogo = new javax.swing.JLabel();
        jProgressBar1 = new javax.swing.JProgressBar();
        btnCancelar = new javax.swing.JButton();
        lblActual = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Actualizador");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridBagLayout());

        lblLogo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/sun/resources/java32.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        getContentPane().add(lblLogo, gridBagConstraints);

        jProgressBar1.setStringPainted(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        getContentPane().add(jProgressBar1, gridBagConstraints);

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesEjecutar/gui/Stop16.gif"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.setToolTipText("Cancelar proceso");
        btnCancelar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnCancelar.setMaximumSize(new java.awt.Dimension(110, 32));
        btnCancelar.setMinimumSize(new java.awt.Dimension(110, 32));
        btnCancelar.setPreferredSize(new java.awt.Dimension(110, 32));
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        getContentPane().add(btnCancelar, gridBagConstraints);

        lblActual.setText(" ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        getContentPane().add(lblActual, gridBagConstraints);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-460)/2, (screenSize.height-240)/2, 460, 240);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        setVisible(false);
        moCoordinador.cancelar();

}//GEN-LAST:event_btnCancelarActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        btnCancelarActionPerformed(null);
    }//GEN-LAST:event_formWindowClosing



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JLabel lblActual;
    private javax.swing.JLabel lblLogo;
    // End of variables declaration//GEN-END:variables




}
class ListModelPropio extends  javax.swing.AbstractListModel {
    private static final long serialVersionUID = 1L;
    IListaElementos moLista = new JListaElementos();
    public int getSize() { return moLista.size(); }
    public Object getElementAt(int i) { return moLista.get(i); }
}