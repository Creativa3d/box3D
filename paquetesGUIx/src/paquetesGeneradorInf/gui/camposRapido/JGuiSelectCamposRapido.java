/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * JGuiSelectCamposRapido.java
 *
 * Created on 17-may-2010, 11:50:15
 */

package paquetesGeneradorInf.gui.camposRapido;

import ListDatos.JListDatos;
import ListDatos.JSelect;
import ListDatos.JSelectCampo;
import ListDatos.JSelectUnionTablas;
import ListDatos.estructuraBD.JFieldDef;
import ListDatos.estructuraBD.JTableDef;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import paquetesGeneradorInf.gui.JGuiConsultaDatos;
import paquetesGeneradorInf.gui.util.JSelectMotorNeutro;
import utiles.IListaElementos;
import java.util.Iterator;
import utiles.JListaElementos;
import utilesBD.filasPorColumnas.JTEEATRIBUTOS;

public class JGuiSelectCamposRapido extends javax.swing.JPanel {
    private static final long serialVersionUID = 1;

    private JGuiConsultaDatos moConsulta;
    private DefaultMutableTreeNode moTop;
    private final DefaultTreeModel moTreeModel;
    private ListModelCampos moModelCampos;



    /** Creates new form JGuiSelectCamposRapido */
    public JGuiSelectCamposRapido() {
        initComponents();
        moTop = new DefaultMutableTreeNode("Tablas");
        moTreeModel = new DefaultTreeModel(moTop);
        jTree1.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        jTree1.setShowsRootHandles(true);
        jTree1.setScrollsOnExpand(true);
        jTree1.setModel(moTreeModel);
        moModelCampos = new ListModelCampos(this);
        jListCZ1.setModel(moModelCampos);
    }
    /**se establece la consulta*/
    public void setConsulta(JGuiConsultaDatos poConsulta) throws Exception {
        moConsulta = poConsulta;
        refrescar();
    }
    //devuelve la select
    private JSelect getSelect(){
        return moConsulta.getSelect();
    }
    /**refrescamos los componentes a traves de la select*/
    public void refrescar() throws Exception{
        JSelect loSelect = getSelect();
        //añadimos las tablas con los campos al arbol de seleccion de campos
        if(loSelect.getFrom()!=null && loSelect.getFrom().getTablasUnion().size()>0){
            Iterator loEnum = loSelect.getFrom().getTablasUnion().iterator();
            while (loEnum.hasNext()) {
                JSelectUnionTablas loFromUnion = (JSelectUnionTablas) loEnum.next();
                addRelacionVisual(loFromUnion);
            }
            loEnum = null;
        }
        //añadimos los atributos extra
        for(int i = 0 ; i < moConsulta.getCamposRapidosListaTablasExtra().size(); i++){
            JTEEATRIBUTOS loAtrib = (JTEEATRIBUTOS) moConsulta.getCamposRapidosListaTablasExtra().get(i);
            addTablaGui(loAtrib.getList());
        }
        //expandimos el arbolito para q quede mas chachi
        jTree1.expandPath(new TreePath(moTop));
        jTree1.repaint();
        //añadimos los campos que vienen de la consulta
        JSelectCampo loCampo;
        for(int i = 0 ; i < loSelect.getCountCampos(); i++){
            loCampo = loSelect.getCampo(i);
            if(loCampo.getCaption()==null || loCampo.getCaption().equals("")){
                loCampo.setCaption(
                        moConsulta.getTextosForms().getCaption(
                            loCampo.getTabla(), loCampo.getNombre())
                            );
            }
            moModelCampos.addCampo(loCampo);
        }
        jListCZ1.repaint();
    }
    /**guardamos la select*/
    public void guardar(){
        JSelect loSelect = getSelect();
        loSelect.getCampos().clear();
        for(int i = 0 ; i < moModelCampos.moCampos.size(); i++){
            loSelect.getCampos().add(moModelCampos.moCampos.get(i));
        }
        loSelect.setAgrupado(loSelect.getCamposGroup().size()>0);
    }
    //devuelve el JTableDef de la tabla
    private JTableDef getTabla(String psTabla, String psAlias) throws Exception {
        JTableDef loResult = null;
        if(psTabla == null || psTabla.equals("") ){
            psTabla = psAlias;
        }
        if (psTabla != null && !psTabla.equals("")) {
            loResult = moConsulta.getServer().getTableDefs().get(psTabla);
        }
        return loResult;
    }
    //devuelve el nombre chachi de la tabla
    public String getNombreTabla(String psTablaAlias, String psTabla) {
        String lsResult = "";
        //nombre tabla
        if (psTablaAlias != null) {
            lsResult = psTablaAlias;
        } else {
            if (moConsulta.isUsarNombresFisicos()) {
                lsResult = psTabla;
            } else {
                lsResult = moConsulta.getTextosForms().getString(psTabla);
            }
        }

        return lsResult;
    }
    private void addRelacionVisual(JSelectUnionTablas loFromUnion) throws Exception {
//        JGuiTabla loTabla = addTablaGui(null, poFromUnion.getTablaPrefijoCampos1());
//        JGuiTabla loTabla2 = addTablaGui(
//                poFromUnion.getTabla2(), poFromUnion.getTabla2Alias());
//        JGuiRelacion loRelacion = new JGuiRelacion(this);
//        loRelacion.setRelacion(poFromUnion, loTabla, loTabla2);
//        moRelaciones.add(loRelacion);

        addTablaGui(loFromUnion.getTabla2(), loFromUnion.getTabla2Alias());
        
    }

    //añadimos un nodo con los campos del JListDatos
    private void addTablaGui(JListDatos poList) throws Exception{
        DefaultMutableTreeNode loTablaNodo = new DefaultMutableTreeNode(
                getNombreTabla(null, poList.msTabla)
                );
        moTop.add(loTablaNodo);

        String lsTablaOALias = poList.msTabla;
        for (int i = 0; i < poList.getFields().size(); i++) {
            JFieldDef loCampo = poList.getFields().get(i);
            DefaultMutableTreeNode loCampoNodo = new DefaultMutableTreeNode(
                    new JArbolCampo(
                        loCampo.getNombre(),
                        loCampo.getCaption(),
                        lsTablaOALias)
                    );
            loTablaNodo.add(loCampoNodo);
        }
    }
    //añadimos un nuevo nodo con todos los campos de la tabla
    private void addTablaGui(String psTabla, String psAlias) throws Exception{
        JTableDef loTabla = getTabla(psTabla, psAlias);

        DefaultMutableTreeNode loTablaNodo = new DefaultMutableTreeNode(
                getNombreTabla(psAlias, loTabla.getNombre())
                );
        moTop.add(loTablaNodo);

        String lsTablaOALias = psTabla;
        if(psAlias!=null && !psAlias.equals("")){
            lsTablaOALias=psAlias;
        }
        for (int i = 0; i < loTabla.getCampos().size(); i++) {
            JFieldDef loCampo = loTabla.getCampos().get(i);
            DefaultMutableTreeNode loCampoNodo = new DefaultMutableTreeNode(
                    new JArbolCampo(
                        loCampo.getNombre(),
                        moConsulta.getTextosForms().getCaption(loTabla.getNombre(), loCampo.getNombre()),
                        lsTablaOALias)
                    );
            loTablaNodo.add(loCampoNodo);
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        jPanel3 = new javax.swing.JPanel();
        btnADD = new utilesGUIx.JButtonCZ();
        jLabelCZ1 = new utilesGUIx.JLabelCZ();
        btnDel = new utilesGUIx.JButtonCZ();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jListCZ1 = new utilesGUIx.JListCZ();

        setLayout(new java.awt.GridBagLayout());

        jPanel1.setPreferredSize(new java.awt.Dimension(290, 290));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jTree1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTree1MouseClicked(evt);
            }
        });
        jTree1.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                jTree1ValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jTree1);

        jPanel1.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(jPanel1, gridBagConstraints);

        jPanel3.setMinimumSize(new java.awt.Dimension(80, 23));
        jPanel3.setPreferredSize(new java.awt.Dimension(80, 290));
        jPanel3.setLayout(new java.awt.GridBagLayout());

        btnADD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/paquetesGeneradorInf/images/Forward24.gif"))); // NOI18N
        btnADD.setToolTipText("Añadir campos seleccionados");
        btnADD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnADDActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        jPanel3.add(btnADD, gridBagConstraints);

        jLabelCZ1.setText("   ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        jPanel3.add(jLabelCZ1, gridBagConstraints);

        btnDel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/paquetesGeneradorInf/images/Back24.gif"))); // NOI18N
        btnDel.setToolTipText("Quitar campos seleccionados");
        btnDel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDelActionPerformed(evt);
            }
        });
        jPanel3.add(btnDel, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        add(jPanel3, gridBagConstraints);

        jPanel2.setPreferredSize(new java.awt.Dimension(200, 290));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jListCZ1.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jListCZ1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jListCZ1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jListCZ1);

        jPanel2.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(jPanel2, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void jTree1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTree1MouseClicked
        try{
            //al hacer doble click se añade el campo
            if(evt.getClickCount()>1){
                btnADDActionPerformed(null);
            }
        }catch(Throwable e){
            utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this, e, getClass().getName());
        }
    }//GEN-LAST:event_jTree1MouseClicked

    private void btnADDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnADDActionPerformed
        try{
            //añadimos el nodo del arbol
            DefaultMutableTreeNode node = (DefaultMutableTreeNode)jTree1.getLastSelectedPathComponent();
            if (node != null ) {
                Object nodeInfo = node.getUserObject();
                if(nodeInfo instanceof JArbolCampo){
                    //mostrar nodos hijos
                    moModelCampos.addCampo(((JArbolCampo)nodeInfo).getDatos());
                }
            }
        }catch(Throwable e){
            utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this, e, getClass().getName());
        }

    }//GEN-LAST:event_btnADDActionPerformed

    private void btnDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelActionPerformed
        try{
            //borramos la seleccion del JListCZ
            int [] lal = jListCZ1.getSelectedIndices();
            if(lal.length>0){
                for(int i = 0 ; i < lal.length; i++){
                    moModelCampos.moCampos.remove(lal[i]);
                }
            }
            jListCZ1.repaint();
        }catch(Throwable e){
            utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this, e, getClass().getName());
        }

    }//GEN-LAST:event_btnDelActionPerformed

    private void jListCZ1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jListCZ1MouseClicked
        try{
            //al hacer doble click borrar
            if(evt.getClickCount()>1){
                btnDelActionPerformed(null);
            }
        }catch(Throwable e){
            utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this, e, getClass().getName());
        }

    }//GEN-LAST:event_jListCZ1MouseClicked

    private void jTree1ValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_jTree1ValueChanged
        try{
            //que al cambiar de nodo se expanda automaticamente
            DefaultMutableTreeNode node = (DefaultMutableTreeNode)jTree1.getLastSelectedPathComponent();
            if (node != null ) {
                jTree1.expandPath(new TreePath(node.getPath()));
                jTree1.repaint();
            }
        }catch(Throwable e){
            utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this, e, getClass().getName());
        }
    }//GEN-LAST:event_jTree1ValueChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private utilesGUIx.JButtonCZ btnADD;
    private utilesGUIx.JButtonCZ btnDel;
    private utilesGUIx.JLabelCZ jLabelCZ1;
    private utilesGUIx.JListCZ jListCZ1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTree jTree1;
    // End of variables declaration//GEN-END:variables



}

class ListModelCampos implements ListModel {
    public IListaElementos moListeners = new JListaElementos();
    public IListaElementos moCampos = new JListaElementos();
    private final JGuiSelectCamposRapido moPadre;
    public ListModelCampos(JGuiSelectCamposRapido poPAdre){
        moPadre=poPAdre;
    }
    public int getSize() {
        return moCampos.size();
    }

    public void addCampo(JSelectCampo poCampo){
        moCampos.add(poCampo);
        llamarListeners();
    }

    public Object getElementAt(int index) {
        JSelectCampo loCampo = (JSelectCampo) moCampos.get(index);

        return moPadre.getNombreTabla(null,loCampo.getTabla()) + "." + loCampo.getCaption();
    }

    private void llamarListeners(){
        ListDataEvent loEv= new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED, 0, moCampos.size());
        for(int i = 0 ; i < moListeners.size(); i++){
            ListDataListener l = (ListDataListener) moListeners.get(i);
            l.contentsChanged(loEv);
        }
    }

    public void addListDataListener(ListDataListener l) {
        moListeners.add(l);
    }

    public void removeListDataListener(ListDataListener l) {
        moListeners.remove(l);
    }

}