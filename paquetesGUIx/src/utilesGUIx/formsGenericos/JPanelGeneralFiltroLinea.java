/*
 * JPanelGeneralFiltroLinea.java
 *
 * Created on 25 de julio de 2007, 11:57
 */

package utilesGUIx.formsGenericos;

import ListDatos.JListDatos;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import utiles.Copiar;
import utiles.JConversiones;
import utilesGUIx.JTableModel;

public class JPanelGeneralFiltroLinea extends javax.swing.JPanel implements ChangeListener, ComponentListener, FocusListener, TableColumnModelListener, KeyListener {
    private JTable jTableDatos;
    private JScrollPane jScrollPane1;
    private int mlColumnaDefecto=0;
    private int mlPasadas=0;
    private boolean mbAnularSetLong = false;
    private boolean mbInicializado=false;
//    private JTableModelFiltro moFiltro;
    private JListDatos moFiltroRapido;
    
    /** Creates new form JPanelGeneralFiltroLinea */
    public JPanelGeneralFiltroLinea() {
        initComponents();
//        jTableFiltroRapido.getTableHeader().setVisible(true);
        jTableFiltroRapido.getTableHeader().setVisible(false);
        jTableFiltroRapido.getTableHeader().setMaximumSize(new Dimension(0,0));
        jTableFiltroRapido.getTableHeader().setPreferredSize(new Dimension(0,0));

        jTableFiltroRapido.setCellSelectionEnabled(true);

        
        jTableFiltroRapido.addFocusListener(this);
        
    }
    @Override
    public void requestFocus(){
        jTableFiltroRapido.requestFocus();
    }

    @Override
    public boolean requestFocusInWindow() {
        return jTableFiltroRapido.requestFocusInWindow();
    }    
    public void setComponentes(
            final JTable poTableDatos, 
            final JScrollPane poScrollPane1
            ){
        jTableDatos = poTableDatos;
        jScrollPane1 = poScrollPane1;
        jScrollPane1.getViewport().addChangeListener(this);
        jTableDatos.addKeyListener(this);
        jTableDatos.addComponentListener(this);
        jTableDatos.addFocusListener(this);
        jTableDatos.getColumnModel().addColumnModelListener(this);
        
    }
    
    public void setDatos(
            final JPanelGeneralFiltroModelo loFiltro, 
            final String psCampo) throws CloneNotSupportedException{
        mbInicializado=true;
        if(JConversiones.isNumeric(psCampo)){
            mlColumnaDefecto = getColumn((int)JConversiones.cdbl(psCampo));
        }
        moFiltroRapido = loFiltro.getFiltroPorCampo();
        JTableModel loModel = new JTableModel(moFiltroRapido);
        loModel.mbEditable=true;
        jTableFiltroRapido.setModel(loModel);
        
        setLongsTableFiltroRapido();
        try{
            jTableFiltroRapido.setColumnSelectionInterval(mlColumnaDefecto,mlColumnaDefecto);
            jTableFiltroRapido.requestFocus();
        }catch(Throwable e){
            mlColumnaDefecto=0;
        }
    }
    
//    private int getIndiceTable(final JTable poTable, final String psNombre){
//        int lResult = -1;
//        for(int i = 0 ; i < jTableDatos.getColumnModel().getColumnCount() && lResult == -1;i++){
//            if(JTablaConfig.getNombreColumna(poTable,i).toUpperCase().equals(psNombre.toUpperCase())){
//                lResult = i;
//            }
//        }
//        return lResult;
//    }
    
    private int getColumn(final int plModel){
        int lResult = -1;
        for(int i = 0; i< jTableFiltroRapido.getColumnModel().getColumnCount() && lResult == -1; i++){
            if(jTableFiltroRapido.getColumnModel().getColumn(i).getModelIndex() == plModel){
                lResult = i;
            }
        }
        return lResult;
    }
    
    public void setLongsTableFiltroRapido(){
        if(!isAnularSetLong() && jTableDatos != null && isVisible()){
            try{
                jTableFiltroRapido.getCellEditor().cancelCellEditing();
            }catch(Exception e1){

            }            
            try{
            for(int i = 0; i< jTableDatos.getColumnModel().getColumnCount(); i++){
                jTableFiltroRapido.getColumnModel().moveColumn(getColumn(jTableDatos.getColumnModel().getColumn(i).getModelIndex()), i);
            }
            for(int i = 0; i< jTableDatos.getColumnModel().getColumnCount(); i++){
                JTablaConfig.setLongColumna(
                        jTableFiltroRapido.getColumnModel().getColumn(i), 
                        jTableDatos.getColumnModel().getColumn(i).getPreferredWidth()
                        );
            }
            }catch(Exception e){

            }
        }
    }

    @Override
    public void setVisible(boolean aFlag) {
        super.setVisible(aFlag);
        if(aFlag){
            setLongsTableFiltroRapido();
        }
    }

    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPaneTableFiltro = new javax.swing.JScrollPane();
        jTableFiltroRapido = new javax.swing.JTable();

        setLayout(new java.awt.BorderLayout());

        jScrollPaneTableFiltro.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPaneTableFiltro.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPaneTableFiltro.setMinimumSize(new java.awt.Dimension(7, 17));
        jScrollPaneTableFiltro.setPreferredSize(new java.awt.Dimension(100, 17));

        jTableFiltroRapido.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Título 1", "Título 2", "Título 3", "Título 4"
            }
        ));
        jTableFiltroRapido.setToolTipText("<html>El texto escrito aquí lo busca en la columna de la tabla correspondiente</html>");
        jTableFiltroRapido.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTableFiltroRapido.setAutoscrolls(false);
        jTableFiltroRapido.setMinimumSize(new java.awt.Dimension(60, 17));
        jTableFiltroRapido.setRowSelectionAllowed(false);
        jTableFiltroRapido.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTableFiltroRapidoFocusLost(evt);
            }
        });
        jTableFiltroRapido.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTableFiltroRapidoKeyPressed(evt);
            }
        });
        jScrollPaneTableFiltro.setViewportView(jTableFiltroRapido);

        add(jScrollPaneTableFiltro, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void jTableFiltroRapidoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTableFiltroRapidoKeyPressed
        if(mbInicializado){
            int lColumn = jTableFiltroRapido.getSelectedColumn();
            if(lColumn < 0 ){
                lColumn = jTableFiltroRapido.getEditingColumn();
            }
            if(evt.getKeyCode() == evt.VK_ENTER || evt.getKeyCode() == evt.VK_DOWN){
                try{
                    jTableFiltroRapido.getCellEditor().stopCellEditing();
                }catch(Exception e1){

                }
            }
            if(evt.getKeyCode() == evt.VK_DOWN){
                jTableDatos.setColumnSelectionInterval(lColumn, lColumn);
                jTableDatos.requestFocus();
            }
            if(evt.getKeyCode() == evt.VK_V && (evt.getModifiers() & evt.CTRL_MASK)>0  ){
                Copiar loCopiar = new Copiar();
                String lsTexto = loCopiar.getClipboardString();
                if(lsTexto !=null && !lsTexto.equals("")){
                    jTableFiltroRapido.getModel().setValueAt(lsTexto, 0, lColumn);
                }
                evt.consume();
            }
        }


    }//GEN-LAST:event_jTableFiltroRapidoKeyPressed

    private void jTableFiltroRapidoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTableFiltroRapidoFocusLost
        try{
//            jTableFiltroRapido.getCellEditor().cancelCellEditing();
        }catch(Exception e1){

        }
    }//GEN-LAST:event_jTableFiltroRapidoFocusLost

    @Override
    public void stateChanged(final ChangeEvent e) {
        if(mbInicializado){
            try{
                Point loPunto = jScrollPane1.getViewport().getViewPosition();
                Point loPunto2 = jScrollPaneTableFiltro.getViewport().getViewPosition();
                loPunto.setLocation(loPunto.x, loPunto2.y);
                jScrollPaneTableFiltro.getViewport().setViewPosition(loPunto);
            }catch(Exception e1){
                //nada
            }
        }
    }

    @Override
    public void componentResized(final ComponentEvent e) {
        if(mbInicializado){
            if(e.getSource()!=jTableFiltroRapido){
                setLongsTableFiltroRapido();
            }
        }
    }

    @Override
    public void componentMoved(final ComponentEvent e) {
        if(mbInicializado){
            
        }
    }

    @Override
    public void componentShown(final ComponentEvent e) {
        if(mbInicializado){
            
        }
        
    }

    @Override
    public void componentHidden(final ComponentEvent e) {
        if(mbInicializado){
            
        }
    }

    @Override
    public void focusGained(final FocusEvent e) {
        if(mbInicializado){
    //        System.out.print(e.getSource().getClass().getName());
    //        System.out.print("-");
    //        System.out.print(((JTable)e.getSource()).getSelectedColumn());
    //        System.out.print("-");
    //        System.out.println(((JTable)e.getSource()).getSelectedRow());
            if(e.getSource().getClass()== JTable.class && mlPasadas < 2){

                int lColumn = ((JTable)e.getSource()).getSelectedColumn();

                if(lColumn<0){
                    lColumn = mlColumnaDefecto;
                }
                ((JTable)e.getSource()).setColumnSelectionInterval(lColumn,lColumn);
                ((JTable)e.getSource()).requestFocus();
                mlPasadas++;
            }
        }
    }

    @Override
    public void focusLost(final FocusEvent e) {
        if(mbInicializado){
            if(e.getSource()!=jTableFiltroRapido){
//                setLongsTableFiltroRapido();
            }
        }
    }

    @Override
    public void columnAdded(final TableColumnModelEvent e) {
        if(mbInicializado){
            
        }
    }

    @Override
    public void columnRemoved(final TableColumnModelEvent e) {
        if(mbInicializado){
            
        }
    }

    @Override
    public void columnMoved(final TableColumnModelEvent e) {
        if(mbInicializado){
            if(e.getSource()!=jTableFiltroRapido){
                setLongsTableFiltroRapido();
            }
        }
    }

    @Override
    public void columnMarginChanged(final ChangeEvent e) {
        if(mbInicializado){
            
        }
    }

    @Override
    public void columnSelectionChanged(final ListSelectionEvent e) {
        if(mbInicializado){
            try{
                if(e.getSource()!=jTableFiltroRapido){
                    int lColumn = jTableDatos.getSelectedColumn();
                    if(lColumn>=0){
                        jTableFiltroRapido.setColumnSelectionInterval(lColumn,lColumn);
                    }
                }
            }catch(Exception e1){}
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if(mbInicializado){
            
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(mbInicializado){
            if(e.getSource() == jTableDatos && isVisible()){
                String lsValor=null;
                if(
                   (e.getKeyChar() >= 'a' &&  e.getKeyChar() <= 'z') ||
                   (e.getKeyChar() >= 'A' &&  e.getKeyChar() <= 'Z') ||
                   (e.getKeyChar() >= '0' &&  e.getKeyChar() <= '9') ||
                   e.getKeyChar() == '/' ||  e.getKeyChar() == '\\' ||
                   e.getKeyChar() == ',' ||  e.getKeyChar() == '.' ||
                   e.getKeyChar() == '-' ||  e.getKeyChar() == '_' ||
                   e.getKeyChar() == ';'  ||  e.getKeyChar() == ':'    
                ){
                    lsValor =String.valueOf(e.getKeyChar());
                }else{
                    if(
                       e.getKeyChar() == e.VK_BACK_SPACE ||  e.getKeyChar() == e.VK_DELETE
                    ){
                        lsValor ="";
                    }
                }

                if(lsValor !=null){
                    int lColumn = jTableDatos.getSelectedColumn();
                    int lColumnReal = jTableDatos.getColumnModel().getColumn(jTableDatos.getSelectedColumn()).getModelIndex();
                    jTableFiltroRapido.getModel().setValueAt( lsValor ,0, lColumnReal);
                    jTableFiltroRapido.setColumnSelectionInterval(lColumn,lColumn);
//                    jTableFiltroRapido.setEditingColumn(lColumn);
                    jTableFiltroRapido.requestFocus();

    //                KeyEvent loEvent  = new KeyEvent(
    //                        jTableFiltroRapido, 
    //                        e.KEY_RELEASED, e.getWhen(), 0, 
    //                        e.getKeyCode(), e.getKeyChar(), e.KEY_LOCATION_UNKNOWN
    //                        );


                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(mbInicializado){
            
        }
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPaneTableFiltro;
    private javax.swing.JTable jTableFiltroRapido;
    // End of variables declaration//GEN-END:variables

    public boolean isAnularSetLong() {
        return mbAnularSetLong;
    }

    public void setAnularSetLong(boolean mbAnularSetLong) {
        this.mbAnularSetLong = mbAnularSetLong;
        if(!mbAnularSetLong){
            setLongsTableFiltroRapido();
        }
    }
    
}
