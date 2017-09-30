/*
 * JBotonTactil.java
 *
 * Created on 19 de enero de 2007, 12:40
 */

package utilesGUIx.formsGenericos;

import java.awt.Component;
import java.awt.Image;
import javax.swing.ImageIcon;
import utilesGUIx.imgTrata.JIMGTrata;



import java.awt.event.*;
import java.awt.image.BufferedImage;
import utiles.IListaElementos;
import utiles.JListaElementos;


public class JMostrarPantallaForm extends javax.swing.JPanel implements  ComponentListener, MouseListener {
    private static final long serialVersionUID = 1L;

    private Image moImagen;
    private boolean mbSelected=false;
    private IListaElementos moListaAcciones = new JListaElementos();

    /** Creates new form JBotonTactil */
    public JMostrarPantallaForm() {
        super();
        initComponents();
        lblImagen.addMouseListener(this);
        lblTexto.addMouseListener(this);
    }
    public JMostrarPantallaForm(Image poImg) {
        this();
        setImage(poImg);
    }
    public void setImage(Image poImage){
        moImagen=poImage;
        pintar();
    }
    public void addActionListener(ActionListener poMouse) {
        moListaAcciones.add(poMouse);
    }
    public void addMouseListener(MouseListener poMouse) {
        super.addMouseListener(poMouse);
    }

    private void pintar(){
        try{
            pintarSelecc();
            if(moImagen!=null){
                if(moImagen.getWidth(null)<=0){
                    lblImagen.setIcon(null);
                }else{
                    Image loImg = null;
                    loImg = JIMGTrata.getIMGTrata().getImagenEscalada(
                        moImagen,
                        moImagen.getWidth(null), moImagen.getHeight(null),
                        lblImagen.getWidth(), lblImagen.getHeight());
                    if(!mbSelected){
                        loImg =  JIMGTrata.getIMGTrata().transformarA8Colores(
                                    (BufferedImage)loImg
                                );
                    }
                    lblImagen.setIcon(new ImageIcon(loImg));
                }
            }else{
                lblImagen.setIcon(null);
            }
        }catch(Throwable e){}
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
        pintar();
    }
    public Image getImagen(){
        return moImagen;
    }
    private void llamarListenerAction(ActionEvent e) {
        for(int i = 0; i <moListaAcciones.size(); i++){
            ((ActionListener)moListaAcciones.get(i)).actionPerformed(e);
        }
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
                    setSelected(true);
                    laoM[i].mouseEntered(loEvent);
                    break;
                case 4:
                    setSelected(false);
                    laoM[i].mouseExited(loEvent);
                    break;
            }
        }
        switch(plEvento){
            case 3:
                setSelected(true);
                break;
            case 4:
                setSelected(false);
                break;
        }
        
    }

    public void mouseClicked(MouseEvent e) {
        llamarListenerMouse(e, 0);
        llamarListenerAction(new ActionEvent(this, 0, ""));
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

        lblImagen = new javax.swing.JLabel();
        lblTexto = new utilesGUIx.JLabelCZ();

        setMaximumSize(new java.awt.Dimension(100, 100));
        setMinimumSize(new java.awt.Dimension(100, 100));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(100, 100));
        addComponentListener(this);
        setLayout(new java.awt.BorderLayout());

        lblImagen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIx/images/ani20.gif"))); // NOI18N
        add(lblImagen, java.awt.BorderLayout.CENTER);

        lblTexto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTexto.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        add(lblTexto, java.awt.BorderLayout.SOUTH);
    }

    // Code for dispatching events from components to event handlers.

    public void componentHidden(java.awt.event.ComponentEvent evt) {
    }

    public void componentMoved(java.awt.event.ComponentEvent evt) {
    }

    public void componentResized(java.awt.event.ComponentEvent evt) {
        if (evt.getSource() == JMostrarPantallaForm.this) {
            JMostrarPantallaForm.this.formComponentResized(evt);
        }
    }

    public void componentShown(java.awt.event.ComponentEvent evt) {
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        pintar();
    }//GEN-LAST:event_formComponentResized
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblImagen;
    private utilesGUIx.JLabelCZ lblTexto;
    // End of variables declaration//GEN-END:variables


    
}
