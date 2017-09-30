/*
 * JBotonTactil.java
 *
 * Created on 19 de enero de 2007, 12:40
 */

package utilesGUIx.imgTrata.lista;

import ListDatos.IFilaDatos;
import java.awt.Component;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.event.*;
import java.awt.image.ImageObserver;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import utiles.JDepuracion;
import utilesGUIx.imgTrata.JIMGTrata;
import utilesGUIx.msgbox.JMsgBox;


public class JElementoImagen extends javax.swing.JPanel implements  ComponentListener, ActionListener, MouseListener {
    private static final long serialVersionUID = 1L;

    private boolean mbSelected=false;
    private IFilaDatos moFila;
    private IImagen moImagen;
    private boolean mbCargado = false;
    private JElementoImagenCargar moCargar;
    private Thread moThr;

    /** Creates new form JBotonTactil */
    public JElementoImagen() {
        super();
        initComponents();
        btnSeleccionar.setVisible(false);

    }
    @Override
    public void addMouseListener(MouseListener poMouse) {
        super.addMouseListener(poMouse);
        lblImagen.addMouseListener(this);
        lblTexto.addMouseListener(this);
    }


    protected void imagenCargada(){
        try{
            mbCargado=true;
            if(moImagen!=null && moImagen.getImagen()!=null){
                btnSeleccionar.setVisible(true);
            }            
            pintar();
        }catch(Throwable e){
            JDepuracion.anadirTexto(getClass().getName(), e);
        }
    }

    private void pintar(){
        pintarSelecc();
        if(mbCargado){
            if(moImagen!=null && moImagen.getImagen()!=null){
                if(((ImageIcon)moImagen.getImagen()).getIconWidth()<=0){
                    lblImagen.setIcon(null);
                }else{
                    Image loImg = JIMGTrata.getIMGTrata().getImagenEscalada(
                        ((ImageIcon)moImagen.getImagen()).getImage(),
                        ((ImageIcon)moImagen.getImagen()).getIconWidth(), ((ImageIcon)moImagen.getImagen()).getIconHeight(),
                        lblImagen.getWidth(), lblImagen.getHeight());
                    lblImagen.setIcon(new ImageIcon(loImg));
                }
            }else{
                lblImagen.setIcon(null);
            }
        }
    }
    private void pintarSelecc(){
        if(mbSelected){
            setBorder(
                    new javax.swing.border.LineBorder(
                        javax.swing.UIManager.getDefaults().getColor("Table.selectionBackground"), 4, true));

        }else{
            setBorder(null);
        }
    }

    
    protected void setText(final String psTexto){
        lblTexto.setText(psTexto);
        lblTexto.setToolTipText(psTexto);
        lblImagen.setToolTipText(psTexto);
//        btnImagen.setVisible(psTexto != null && psTexto.compareTo("")!=0 );
    }

    public boolean isSelected(){
        return mbSelected;
    }
    public void setSelected(boolean pbSelected){
        mbSelected=pbSelected;
        pintarSelecc();
    }
    public void setFila(IFilaDatos poFila, IImagen poImagen) {
        if(poFila==null){
            setText("");
            imagenCargada();
        }else{
            moFila = poFila;
            moImagen = poImagen;
            moCargar = new JElementoImagenCargar(this, poFila, poImagen);
            moThr = new Thread(moCargar);
            moThr.start();
        }
    }
    public IFilaDatos getFila(){
        return moFila;
    }
    public IImagen getImagen(){
        return moImagen;
    }
    private void llamarListenerMouse(MouseEvent e, int plEvento) {
        MouseEvent loEvent = new MouseEvent(
                this, e.getID(), e.getWhen(),
                e.getModifiers(),
                ((Component)e.getSource()).getX()+e.getX(), ((Component)e.getSource()).getY()+e.getY(),
                e.getClickCount(),
                e.isPopupTrigger());
        MouseListener[] laoM= getMouseListeners();
        for(int i = 0; i <laoM.length; i++){
            switch(plEvento){
                case 0:
                    laoM[i].mouseClicked(loEvent);
                    break;
                case 1:
                    laoM[i].mousePressed(loEvent);
                    break;
                case 2:
                    laoM[i].mouseReleased(loEvent);
                    break;
                case 3:
                    laoM[i].mouseEntered(loEvent);
                    break;
                case 4:
                    laoM[i].mouseExited(loEvent);
                    break;
            }
        }
    }

    public void mouseClicked(MouseEvent e) {
        if(e.getClickCount()>1 && !e.isPopupTrigger()){
            try {
                moImagen.ver();
            } catch (Throwable ex) {
               JMsgBox.mensajeErrorYLog(this, ex, getClass().getName());
            }            
        }else{
            llamarListenerMouse(e, 0);
        }
    }

    public void mousePressed(MouseEvent e) {
        llamarListenerMouse(e, 1);
    }

    public void mouseReleased(MouseEvent e) {
        llamarListenerMouse(e, 2);
    }

    public void mouseEntered(MouseEvent e) {
        llamarListenerMouse(e, 3);
    }

    public void mouseExited(MouseEvent e) {
        llamarListenerMouse(e, 4);
    }

    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        lblImagen = new javax.swing.JLabel();
        lblTexto = new utilesGUIx.JLabelCZ();
        btnSeleccionar = new javax.swing.JButton();

        setMaximumSize(new java.awt.Dimension(100, 100));
        setMinimumSize(new java.awt.Dimension(100, 100));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(100, 100));
        addComponentListener(this);
        setLayout(new java.awt.GridBagLayout());

        lblImagen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIx/images/ani20.gif"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(lblImagen, gridBagConstraints);

        lblTexto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTexto.setText("Cargando...");
        lblTexto.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        add(lblTexto, gridBagConstraints);

        btnSeleccionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIx/images/adept_preview.png"))); // NOI18N
        btnSeleccionar.setToolTipText("Seleccionar");
        btnSeleccionar.setMinimumSize(new java.awt.Dimension(18, 18));
        btnSeleccionar.setPreferredSize(new java.awt.Dimension(18, 18));
        btnSeleccionar.addActionListener(this);
        add(btnSeleccionar, new java.awt.GridBagConstraints());
    }

    // Code for dispatching events from components to event handlers.

    public void actionPerformed(java.awt.event.ActionEvent evt) {
        if (evt.getSource() == btnSeleccionar) {
            JElementoImagen.this.btnSeleccionarActionPerformed(evt);
        }
    }

    public void componentHidden(java.awt.event.ComponentEvent evt) {
    }

    public void componentMoved(java.awt.event.ComponentEvent evt) {
    }

    public void componentResized(java.awt.event.ComponentEvent evt) {
        if (evt.getSource() == JElementoImagen.this) {
            JElementoImagen.this.formComponentResized(evt);
        }
    }

    public void componentShown(java.awt.event.ComponentEvent evt) {
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        pintar();
    }//GEN-LAST:event_formComponentResized

    private void btnSeleccionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeleccionarActionPerformed
        try {
            MouseEvent e = new MouseEvent(
                this, evt.getID(), evt.getWhen(),
                evt.getModifiers(),
                ((Component)evt.getSource()).getX()+btnSeleccionar.getX(), btnSeleccionar.getY()+btnSeleccionar.getY(),
                2,
                false);
            mouseClicked(e);
        } catch (Throwable ex) {
           JMsgBox.mensajeErrorYLog(this, ex, getClass().getName());

        }
    }//GEN-LAST:event_btnSeleccionarActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSeleccionar;
    private javax.swing.JLabel lblImagen;
    private utilesGUIx.JLabelCZ lblTexto;
    // End of variables declaration//GEN-END:variables


    
}
class JElementoImagenCargar implements Runnable, ImageObserver {
    private IFilaDatos moFila;
    private IImagen moImagen;
    private final JElementoImagen moPadre;

    public JElementoImagenCargar(JElementoImagen poPadre,IFilaDatos poFila, IImagen poImagen) {
        moFila = poFila;
        moImagen = poImagen;
        moPadre=poPadre;
    }
    public void run() {
        try {
            moImagen.setDatos(moFila);
            moPadre.setText(moImagen.getDescripcion());
            ImageIcon loIcon = ((ImageIcon)moImagen.getImagen());
            loIcon.setImageObserver(this);
            synchronized(this){
                try {
                    wait(1000);
                } catch (InterruptedException ex) {
                }
            }
            while(loIcon.getImageLoadStatus()!=MediaTracker.COMPLETE &&
                  loIcon.getImageLoadStatus()!=MediaTracker.ERRORED){
                synchronized(this){
                    try {
                        wait(1000);
                    } catch (InterruptedException ex) {
                    }
                }
            }
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    moPadre.imagenCargada();
                }
            });
        } catch (Throwable e) {
            moPadre.setText(moImagen.getDescripcion());
            JDepuracion.anadirTexto(getClass().getName(), e);
            moPadre.imagenCargada();
        }
    }

    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
        if ((infoflags & ImageObserver.ALLBITS) != 0) {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    moPadre.imagenCargada();
                }
            });
            return false;
        }
        return true;
    }

}
