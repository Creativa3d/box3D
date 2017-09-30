/*
 * JTabPaneCZ.java
 *
 * Created on 3 de diciembre de 2007, 11:05
 */

package utilesGUIx.tabPane;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import utiles.IListaElementos;
import utiles.JListaElementos;


public class JTabPaneCZ extends javax.swing.JPanel implements MouseListener {
    /**
     * Only one <code>ChangeEvent</code> is needed per <code>TabPane</code>
     * instance since the
     * event's only (read-only) state is the source property.  The source
     * of events generated here is always "this".
     */
    protected transient ChangeEvent changeEvent = null;
    
    private IListaElementos moElementos = new JListaElementos();
    private int mlClave = 0;
    private int mlPaginas=0;
    private int mlAlto;
    private boolean mbAnularPrimerMouseClicked=false;
    /** Creates new form JTabPaneCZ */
    public JTabPaneCZ() {
        initComponents();
        setAltoPestana((int)(new JLabel("aa")).getPreferredSize().height + 4);
    }
    /**
     * Adds a <code>ChangeListener</code> to this tabbedpane.
     *
     * @param l the <code>ChangeListener</code> to add
     * @see #fireStateChanged
     * @see #removeChangeListener
     */
    public void addChangeListener(ChangeListener l) {
        listenerList.add(ChangeListener.class, l);
    }

    /**
     * Removes a <code>ChangeListener</code> from this tabbedpane.
     *
     * @param l the <code>ChangeListener</code> to remove
     * @see #fireStateChanged
     * @see #addChangeListener
     */
    public void removeChangeListener(ChangeListener l) {
        listenerList.remove(ChangeListener.class, l);
    }

   /**
     * Returns an array of all the <code>ChangeListener</code>s added
     * to this <code>JTabbedPane</code> with <code>addChangeListener</code>.
     *
     * @return all of the <code>ChangeListener</code>s added or an empty
     *         array if no listeners have been added
     * @since 1.4
     */
    public ChangeListener[] getChangeListeners() {
        return (ChangeListener[])listenerList.getListeners(
                ChangeListener.class);
    }

    public void setAltoPestana(final int plAlto){
        mlAlto = plAlto;
        for(int i = 0 ; i < moElementos.size(); i++){
            JElementoConPestana loElem = (JElementoConPestana)moElementos.get(i);
            loElem.getPesta().setPreferredSize(
                    new Dimension(
                        (int)loElem.getPesta().getPreferredSize().width, 
                        plAlto
                    ));
        }
        
    }
    public int getPaginas(){
        return mlPaginas;
    }
    public void setPaginas(final int plPaginas){
        for(int i = moElementos.size(); i < plPaginas; i++ ){
            addTabPane("Tab" + String.valueOf(i), new JPanel());
        }
        while(plPaginas < moElementos.size()){
            removeTabPane(plPaginas);
        }
        mlPaginas = plPaginas;
    }
    public void removeTabPane(final int plIndex){
        JElementoConPestana loElem = (JElementoConPestana)moElementos.get(plIndex);
        moElementos.remove(plIndex);
        borrarPestana(loElem);
        mlPaginas = moElementos.size();
    }
    public int addTabPane(final String psTitulo, final JComponent poComponente){
        return addTabPane(new JLabel(psTitulo), poComponente);
    }
    public int addTabPane(final JComponent poPestana, final JComponent poComponente){
        mlClave++;
        if(JCheckBox.class.isInstance(poPestana)){
            ((JCheckBox)poPestana).setBorderPainted(true);
        }
        JElementoConPestana loElem = new JElementoConPestana(poPestana, poComponente, mlClave);
        poPestana.addMouseListener(this);
        moElementos.add(loElem);
        addElementoAlGrafico(loElem);
        if(moElementos.size()==1){
            activarPestana(poPestana);
        }
        mlPaginas = moElementos.size();
        return moElementos.size()-1;
    }
    
    private void addElementoAlGrafico(final JElementoConPestana poElem){
        poElem.getPesta().setBorder(
                new BorderTab(false, moElementos.size()==1, false));
        poElem.getPesta().setBackground(poElem.getColorDesActivo());
        java.awt.GridBagConstraints gridBagConstraints;
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
//        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 0);
        poElem.getPesta().setOpaque(true);
        jPanelPestana.add(poElem.getPesta(), gridBagConstraints);
        jPanelComponente.add(poElem.getClave(),poElem.getComp());
        poElem.getPesta().setPreferredSize(
                    new Dimension(
                        (int)poElem.getPesta().getPreferredSize().width, 
                        mlAlto
                    ));
    }
    private void borrarPestana(final JElementoConPestana poElem){
        jPanelComponente.remove(poElem.getComp());
        jPanelPestana.remove(poElem.getPesta());
    }

    /**
     * Returns the tab component at <code>index</code>.
     *
     * @param index  the index of the item being queried
     * @return the tab component at <code>index</code>
     * @exception IndexOutOfBoundsException if index is out of range
     *            (index < 0 || index >= tab count)
     *
     * @see #setTabComponentAt
     * @since 1.6
     */
    public JComponent getTabComponentAt(int plIndex){
        if (plIndex >=0 && plIndex < moElementos.size())
            return ((JElementoConPestana)moElementos.get(plIndex)).getPesta();
        else return null;
    }
    /**
     * Returns the currently selected index for this tabbedpane.
     * Returns -1 if there is no currently selected tab.
     *
     * @return the index of the selected tab
     * @see #setSelectedIndex
     */
    public int getSelectedIndex() {
        int lResult = -1;
        for(int i = 0 ; i < moElementos.size() && lResult < 0; i++){
            JElementoConPestana loElem = (JElementoConPestana)moElementos.get(i);
            if(loElem.isActivado()){
                lResult = i;
            }
        }
        return lResult;
    }
    /**
     * Returns the currently selected component for this tabbedpane.
     * Returns <code>null</code> if there is no currently selected tab.
     *
     * @return the component corresponding to the selected tab
     * @see #setSelectedComponent
     */
    public Component getSelectedComponent() {
        return getSelectedElementoConPestana().getComp();
    }
    public JElementoConPestana getSelectedElementoConPestana() {
        int index = getSelectedIndex();
        if (index == -1) {
            return null;
        }
        return ((JElementoConPestana)moElementos.get(index));
    }

    private void activarPestana(final JElementoConPestana poElem){
        if(getSelectedElementoConPestana()!=poElem){
            //desactivamos las pestañas activas y activamos la nueva pestaña
            for(int i = 0 ; i < moElementos.size(); i++){
                JElementoConPestana loElem = (JElementoConPestana)moElementos.get(i);
                if(loElem.isActivado() && loElem != poElem){
                    //desactivamos las pestañas activas
                    loElem.getPesta().setBorder(
                            new BorderTab(
                                false,
                                i == 0,
                                i == (moElementos.size()-1)
                                )
                            );
                    loElem.getPesta().setBackground(loElem.getColorDesActivo());
                    loElem.setActivado(false);
                }else{
                    //activamos la pestaña
                    if(loElem == poElem){
                        poElem.getPesta().setBorder(
                             new BorderTab(
                                true,
                                i == 0,
                                i == (moElementos.size()-1)
                                )
                             );
                        loElem.getPesta().setBackground(loElem.getColor());
                        ((CardLayout)(jPanelComponente.getLayout())).show(
                                jPanelComponente,
                                poElem.getClave());
                        poElem.setActivado(true);
                    }
                }
            }
            //lanzamos el evento de cambio
            // Guaranteed to return a non-null array
            Object[] listeners = listenerList.getListenerList();
            // Process the listeners last to first, notifying
            // those that are interested in this event
            for (int i = listeners.length-2; i>=0; i-=2) {
                if (listeners[i]==ChangeListener.class) {
                    // Lazily create the event:
                    if (changeEvent == null)
                        changeEvent = new ChangeEvent(this);
                    ((ChangeListener)listeners[i+1]).stateChanged(changeEvent);
                }
            }

        }

    }
    private JElementoConPestana getElementoPorPestana(final JComponent poPestana){
        JElementoConPestana loResult = null;
        for(int i = 0 ; i < moElementos.size(); i++){
            JElementoConPestana loElem = (JElementoConPestana)moElementos.get(i);
            if(loElem.getPesta() == poPestana){
                loResult = loElem;
                i=moElementos.size();
            }
        }
        return loResult; 
    }
    private void activarPestana(final JComponent poPestana){
        activarPestana(getElementoPorPestana(poPestana));
    }
    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelPestana = new javax.swing.JPanel();
        jPanelComponente = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout());

        jPanelPestana.setLayout(new java.awt.GridBagLayout());
        add(jPanelPestana, java.awt.BorderLayout.NORTH);

        jPanelComponente.setLayout(new java.awt.CardLayout());
        add(jPanelComponente, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanelComponente;
    private javax.swing.JPanel jPanelPestana;
    // End of variables declaration//GEN-END:variables

    public void mouseClicked(MouseEvent e) {
        if(!mbAnularPrimerMouseClicked){
            mbAnularPrimerMouseClicked=false;
            activarPestana((JComponent)e.getSource());
        }
    }

    public void mouseEntered(MouseEvent e) {
        
    }

    public void mouseExited(MouseEvent e) {
        
    }

    public void mousePressed(MouseEvent e) {
        mbAnularPrimerMouseClicked = true;
        activarPestana((JComponent)e.getSource());
    }

    public void mouseReleased(MouseEvent e) {
        
    }
    
}
