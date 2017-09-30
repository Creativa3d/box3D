/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package impresionXML.impresion.xml.diseno.componentes;

import impresionXML.impresion.xml.IxmlObjetos;
import impresionXML.impresion.xml.JxmlInforme;
import impresionXML.impresion.xml.diseno.IXMLDesign;
import impresionXML.impresion.xml.diseno.JPanelDESIGN;
import java.awt.Rectangle;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.beans.PropertyChangeEvent;
import utilesGUIx.msgbox.JMsgBox;

/**
 *
 * @author eduardo
 */
public abstract class JPanelBASE extends javax.swing.JPanel  implements MouseMotionListener,  MouseListener, KeyListener, ClipboardOwner {
    //redimension y movimiento
    protected static final int mclNada = -1;
    protected static final int mclMover = 0;
    protected static final int mclIzqSup = 1;
    protected static final int mclIzqInf = 2;
    protected static final int mclDerSup = 3;
    protected static final int mclDerInf = 4;
    protected static final int mclIzq = 5;
    protected static final int mclDer = 6;
    protected static final int mclInf = 7;
    protected static final int mclSup = 8;
    protected int mlTipo = mclNada;
    protected IXMLDesign moDesign;
    protected JPanelDESIGN moPadre;
    protected JxmlInforme moInforme;


    protected boolean mbExterno=true;
    protected int mlMoverX;
    protected int mlMoverY;
    
    /**
     * Creates new form JPanelBASE
     */
    public JPanelBASE() {
        initComponents();
        this.addMouseMotionListener(this);
        this.addMouseListener(this);     
        this.addKeyListener(this);     
        
    }
    public void setPadre(JPanelDESIGN poPadre){
        moPadre=poPadre;
   }
    public void setDatos(IXMLDesign poDesign){
        moDesign=poDesign;

    }
    public abstract IxmlObjetos getXMLObjeto();
    
    public IXMLDesign getXMLDesign(){
        return moDesign; 
    }

    public void mostrarPropiedades(){
        try {
            moPadre.setPropiedades(moDesign.getPropiedades());
        } catch (Exception ex) {
            JMsgBox.mensajeError(moPadre, ex);
        }
    }
    protected abstract void mostrarDatos();
    
    public void propertyChange(PropertyChangeEvent evt) {
        if(mbExterno){
            mostrarDatos();
        }
        if(!mbExterno){
            mostrarPropiedades();
        }
    }
    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        boolean lbMover = true;
        boolean lbSIZE = false;
        if((e.getModifiersEx() & KeyEvent.SHIFT_DOWN_MASK)==KeyEvent.SHIFT_DOWN_MASK){
            lbMover = false;
            lbSIZE=true;
        }
        Rectangle loRect = this.getBounds();
        if(e.getKeyCode() == e.VK_DOWN){
            mlTipo=mclNada;
            if(lbMover){
                loRect.y=loRect.y + 1;
            }
            if(lbSIZE){
                loRect.height=loRect.height+1;
            }
        }
        if(e.getKeyCode() == e.VK_UP){
            mlTipo=mclNada;
            if(lbMover){
                loRect.y=loRect.y - 1;
            }
            if(lbSIZE){
                loRect.height=loRect.height-1;
            }
        }
        if(e.getKeyCode() == e.VK_RIGHT){
            mlTipo=mclNada;
            if(lbMover){
                loRect.x=loRect.x + 1;
            }
            if(lbSIZE){
                loRect.width=loRect.width + 1;
            }
        }
        if(e.getKeyCode() == e.VK_LEFT){
            mlTipo=mclNada;
            if(lbMover){
                loRect.x=loRect.x - 1;
            }
            if(lbSIZE){
                loRect.width=loRect.width - 1;
            }
        }
        //Copiar
        if(e.getKeyCode() == KeyEvent.VK_C && ((e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK)==KeyEvent.CTRL_DOWN_MASK)){
            moPadre.copiar();
        }
                
        //pegar
        if(e.getKeyCode() == KeyEvent.VK_V && ((e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK)==KeyEvent.CTRL_DOWN_MASK)){
            try {
                moPadre.pegar();
            } catch (Throwable ex) {
                JMsgBox.mensajeError(moPadre, ex);
            }
        }
        
        this.setBounds(loRect);
        if(e.getKeyCode() == e.VK_DOWN
                || e.getKeyCode() == e.VK_LEFT
                || e.getKeyCode() == e.VK_RIGHT
                || e.getKeyCode() == e.VK_UP
                ){
            mouseDragged(null);
        }
    }
    public void lostOwnership(Clipboard clipboard, Transferable contents) {
    }

    public void keyReleased(KeyEvent e) {
    }
    
    public void mouseClicked(MouseEvent e) {
        try {
            if(!e.isControlDown()){
                moPadre.getSeleccion().clear();
            }
            moPadre.getSeleccion().add(moDesign);
            this.getParent().setComponentZOrder(this, 0);
//            moPadre.setComponentZOrder(this, 0);
//            mostrarPropiedades();
        } catch (Exception ex) {
        }
    }

    public void mousePressed(MouseEvent e) {
        mlTipo = getTipoBordes(e.getX(), e.getY());
        if (mlTipo == mclNada) {
            mlTipo = mclMover;
            mlMoverX=e.getX();
            mlMoverY=e.getY();

        }
        maybeShowPopup(e);
    }
    public int getTipoBordes(int plX, int plY){
        getX();getY();
        double ldMargen = 3 * moInforme.getDiseno().getZoom();
        int lTipo = mclNada;
        if (lTipo == mclNada) {
            if (plX <= ldMargen) {
                if (plY <= ldMargen) {
                    lTipo = mclIzqSup;
                } else if (plY >= (getHeight() - ldMargen)) {
                    lTipo = mclIzqInf;
                } else {
                    lTipo = mclIzq;
                }
            }
        }
        if (lTipo == mclNada) {
            if (plX >= (getWidth() - ldMargen)) {
                if (plY <= ldMargen) {
                    lTipo = mclDerSup;
                } else if (plY >= (getHeight() - ldMargen)) {
                    lTipo = mclDerInf;
                } else {
                    lTipo = mclDer;
                }
            }
        }
        if (lTipo == mclNada) {
            if (plY <= ldMargen) {
                lTipo = mclSup;
            }
        }
        if (lTipo == mclNada) {
            if (plY >= (getHeight() - ldMargen)) {
                lTipo = mclInf;
            }
        }

        return lTipo;
    }

    public void mouseReleased(MouseEvent e) {
        maybeShowPopup(e);
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent mme) {
        if(mme!=null && mlTipo==mclMover){
            if(!mme.isControlDown()){
                moPadre.getSeleccion().clear();
            }
            moPadre.getSeleccion().add(moDesign);        
        }
        if(moPadre.getSeleccion().isSelected(moDesign) || mlTipo==mclMover){
            switch (mlTipo) {
                case mclMover:
                    setLocation(
                            this.getX() + mme.getX() - mlMoverX,
                            this.getY() + mme.getY() - mlMoverY);
                    break;
            }
            int lWidth = getWidth();
            int lHeight = getHeight();
            int lX = getX();
            int lY = getY();
            switch (mlTipo) {
                case mclIzq:
                case mclIzqInf:
                case mclIzqSup:
                    lWidth = Math.abs(getWidth() - mme.getX());
                    break;
                case mclDer:
                case mclDerInf:
                case mclDerSup:
                    lWidth = Math.abs(mme.getX());
                    break;
            }
            switch (mlTipo) {
                case mclInf:
                case mclDerInf:
                case mclIzqInf:
                    lHeight = Math.abs(mme.getY());
                    break;
                case mclSup:
                case mclDerSup:
                case mclIzqSup:
                    lHeight = Math.abs(getHeight() - mme.getY());
                    break;
            }
            switch (mlTipo) {
                case mclIzq:
                case mclIzqInf:
                case mclIzqSup:
                    lX = mme.getX() + getX();
            }
            switch (mlTipo) {
                case mclSup:
                case mclIzqSup:
                case mclDerSup:
                    lY = mme.getY() + getY();
            }

            setBounds(lX, lY, lWidth, lHeight);

            revalidate();
        }
    }
    public void mouseMoved(MouseEvent mme) {
        
    }
    private void maybeShowPopup(MouseEvent e) {
        if (e.isPopupTrigger()) {
            jPopupMenu1.show(e.getComponent(),
                       e.getX(), e.getY());
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuBorrar = new javax.swing.JMenuItem();

        jPopupMenu1.add(jSeparator1);

        jMenuBorrar.setText("Borrar");
        jMenuBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuBorrarActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuBorrar);

        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51), 0));
        setLayout(new java.awt.BorderLayout());
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuBorrarActionPerformed
        try{
           moInforme.getBanda(0).remove(getXMLObjeto());
//           Container loC = getParent();
//           loC.remove(this);
//           loC.repaint();
        }catch(Throwable e){
            JMsgBox.mensajeErrorYLog(this, e);
        }
        
    }//GEN-LAST:event_jMenuBorrarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem jMenuBorrar;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    // End of variables declaration//GEN-END:variables

}
