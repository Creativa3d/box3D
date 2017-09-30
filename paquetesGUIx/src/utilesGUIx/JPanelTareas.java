/*
 * JPanelTareas.java
 *
 * Created on 20 de noviembre de 2006, 14:04
 */

package utilesGUIx;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import utiles.IListaElementos;
import utiles.JListaElementos;

public class JPanelTareas extends JPanel implements java.awt.event.MouseListener {
    
    private utilesGUIx.JLabelCZ jLabelCabecera2;
    private utilesGUIx.JLabelCZ jLabelCabezera;
    public javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;


    private boolean mbVisible = true;
    private final IListaElementos moListener = new JListaElementos();
    private Dimension moMaximo;
    private Color moBack;
    private Color moBackEncima;
    
    /** Creates new form JPanelTareas */
    public JPanelTareas() {
        super();
        inicializar();
        if(jLabelCabezera.getBackground()!=null){
            moBack = jLabelCabezera.getBackground();
        }
        if(moBack==null){
            moBack = jPanel1.getBackground();
        }
        if(moBack==null){
            moBackEncima = new Color(100,100,100);
        }else{
            moBackEncima = moBack.brighter();
        }
        jPanel1.setVisible(mbVisible);
    }
    public int getHeightMinimo(){
        return jLabelCabezera.getPreferredSize().height;
    }
    public void refrescarPreferenceSize(){
        if(mbVisible){
//            if(moMaximo==null){
//                moMaximo = new Dimension(jPanel1.getPreferredSize().width, jPanel1.getPreferredSize().height);
//                moMaximo.height = getHeightMinimo() + jPanel1.getPreferredSize().height;
//            }
//            setPreferredSize(new Dimension((int)getMaximumSize().width,(int)moMaximo.height));
            validate();
        }else{
//            setPreferredSize(new Dimension((int)getMaximumSize().width,getHeightMinimo()));            
        }
    }
    
    public void setText(final String psTexto){
        jLabelCabezera.setText(psTexto);
    }
    public void addActionListener(ActionListener listener) {
        moListener.add(listener);
    }

    public Container getPanel(){
        return jPanel1;
    }
    
    public void setAmpliado(final boolean lbAmpliado){
        mbVisible = lbAmpliado;
        jPanel1.setVisible(mbVisible);
        refrescarPreferenceSize();
        llamarListener();
    }
    private void llamarListener(){
        ActionEvent loEvent = new ActionEvent(this, 0, jLabelCabezera.getText());
        for(int i = 0 ; i < moListener.size(); i++){
            ActionListener loListener = (ActionListener)moListener.get(i);
            loListener.actionPerformed(loEvent);
        }
    }
    public boolean getAmpliado(){
        return mbVisible;
    }

    public void setMaximumSizeTotal(Dimension maximumSize) {
        setMaximumSize(new Dimension(maximumSize.width,10000));
        jPanel1.setMaximumSize(new Dimension(maximumSize.width,10000));
        moMaximo = new Dimension(maximumSize.width,maximumSize.height);
        validate();
    }

    public Component add(Component comp) {
        return getPanel().add(comp);
    }

    public void add(Component comp, Object constraints) {
        getPanel().add(comp, constraints);
    }

    private void inicializar() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel2 = new javax.swing.JPanel();
        jLabelCabecera2 = new utilesGUIx.JLabelCZ();
        jLabelCabezera = new utilesGUIx.JLabelCZ();
        jPanel1 = new javax.swing.JPanel();

        super.setLayout(new java.awt.GridBagLayout());

        jPanel2.setLayout(new java.awt.GridBagLayout());

        jLabelCabecera2.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.darkShadow"));
        jLabelCabecera2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIx/images/Zoom16.gif"))); // NOI18N
        jLabelCabecera2.setText("  ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        jPanel2.add(jLabelCabecera2, gridBagConstraints);

        jLabelCabezera.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabelCabezera.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelCabezera.setText("jLabelCZ1");
        jLabelCabezera.setFont(new java.awt.Font("Dialog", 1, 14));
        jLabelCabezera.setOpaque(true);
        jLabelCabezera.addMouseListener(this);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        jPanel2.add(jLabelCabezera, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = gridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        super.add(jPanel2, gridBagConstraints);

        jPanel1.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.shadow"));
        jPanel1.setLayout(new java.awt.GridLayout(0, 1));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = gridBagConstraints.RELATIVE;
        gridBagConstraints.gridy = gridBagConstraints.RELATIVE;
        gridBagConstraints.gridwidth = gridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        super.add(jPanel1, gridBagConstraints);
        
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = gridBagConstraints.RELATIVE;
        gridBagConstraints.gridy = gridBagConstraints.RELATIVE;
        gridBagConstraints.gridwidth = gridBagConstraints.REMAINDER;
        gridBagConstraints.gridheight = gridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        super.add(new JLabel(" "), gridBagConstraints);
        
    }

    // Code for dispatching events from components to event handlers.

    public void mouseClicked(java.awt.event.MouseEvent evt) {
        if (evt.getSource() == jLabelCabezera) {
            JPanelTareas.this.jLabelCabezeraMouseClicked(evt);
        }
    }

    public void mouseEntered(java.awt.event.MouseEvent evt) {
        if (evt.getSource() == jLabelCabezera) {
            JPanelTareas.this.jLabelCabezeraMouseEntered(evt);
        }
    }

    public void mouseExited(java.awt.event.MouseEvent evt) {
        if (evt.getSource() == jLabelCabezera) {
            JPanelTareas.this.jLabelCabezeraMouseExited(evt);
        }
    }

    public void mousePressed(java.awt.event.MouseEvent evt) {
    }

    public void mouseReleased(java.awt.event.MouseEvent evt) {
    }

    private void jLabelCabezeraMouseEntered(java.awt.event.MouseEvent evt) {
        jLabelCabecera2.setBackground(moBackEncima);
        jLabelCabezera.setBackground(moBackEncima);
        jPanel1.validate();
    }

    private void jLabelCabezeraMouseExited(java.awt.event.MouseEvent evt) {
        jLabelCabecera2.setBackground(moBack);
        jLabelCabezera.setBackground(moBack);
        jPanel1.validate();
    }

    private void jLabelCabezeraMouseClicked(java.awt.event.MouseEvent evt) {
        mbVisible = !mbVisible;
        setAmpliado(mbVisible);
    }
    
    
}
