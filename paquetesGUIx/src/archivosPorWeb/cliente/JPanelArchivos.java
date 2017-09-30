/*
 * JPanelArchivos.java
 *
 * Created on 4 de mayo de 2006, 18:05
 */

package archivosPorWeb.cliente;

import archivosPorWeb.comun.IServidorArchivos;
import archivosPorWeb.comun.JFichero;
import archivosPorWeb.comun.JServidorArchivos;
import java.io.File;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import utiles.IListaElementos;
import utiles.JListaElementos;
import utilesGUIx.JGUIxConfigGlobalModelo;
import utilesGUIx.Rectangulo;
import utilesGUIx.formsGenericos.CallBack;
import utilesGUIx.formsGenericos.edicion.JPanelEdicionAbstract;
import utilesx.JEjecutar;

public class JPanelArchivos extends JPanelEdicionAbstract {
    private static final long serialVersionUID = 1L;
    private IServidorArchivos moServidor;
    private JTableModelArchivos moModelo;
    private DefaultMutableTreeNode top = new DefaultMutableTreeNode(new JArchivosNodoArbol(new JFichero("/","",true,0,null)));
    private DefaultTreeModel treeModel = new DefaultTreeModel(top);
    
    private JFichero moFicheroInicial= new JFichero("/", "", true, 0, null);
    private CallBack<IListaElementos<JFichero>> moCallBackRuta;
    private IListaElementos<JFichero> moElementos;
    /** Creates new form JPanelArchivos */
    public JPanelArchivos() {
        initComponents();
        jTree1.setShowsRootHandles(true);
        jTree1.setScrollsOnExpand(true);
        jTree1.setModel(treeModel);
//        txtDireccion.addCellEditorListener(null);
    }
    
    public void setDatos(IServidorArchivos poServidor) throws Exception {
        moServidor=poServidor;
    }
    
    public void setCallBack(CallBack<IListaElementos<JFichero>> poCallBackRuta){
        moCallBackRuta=poCallBackRuta;
    }

    @Override
    public void rellenarPantalla() throws Exception {
        moModelo= new JTableModelArchivos(new JListaElementos());
        jTable1.setModel(moModelo);
        moModelo.addMouseListenerToHeaderInTable(jTable1);
        refrescar(top);
    }

    @Override
    public void mostrarDatos() throws Exception {
        txtDireccion.setText(moFicheroInicial.getPath());
    }

    @Override
    public void establecerDatos() throws Exception {
        int[] lalFilas = jTable1.getSelectedRows();
        for(int i = 0; i<lalFilas.length;i++){
            JFichero loFichero = (JFichero)moModelo.moLista.get(lalFilas[i]);
            moElementos.add(loFichero);
        }
        if(moElementos.isEmpty()){
            throw new Exception("Ningun archivo seleccionado");
        }
    }
    @Override
    public void aceptar() throws Exception {
        if(moCallBackRuta!=null){
            moCallBackRuta.callBack(moElementos);
        }
    }

    @Override
    public void cancelar() throws Exception {
        
    }

    @Override
    public void habilitarSegunEdicion() throws Exception {
    }

    @Override
    public void ponerTipoTextos() throws Exception {
    }

    @Override
    public Rectangulo getTanano() {
        return new Rectangulo(800, 600);
    }

    @Override
    public String getTitulo() {
        return "Selección archivos";
    }
    
    public void refrescar() throws Exception {
        //recuperamos la informacion del nodo
        DefaultMutableTreeNode node = 
            (DefaultMutableTreeNode)jTree1.getLastSelectedPathComponent();

        if (node != null) {
            Object nodeInfo = top.getUserObject();
            JArchivosNodoArbol loDatosNodo = (JArchivosNodoArbol)nodeInfo;
            loDatosNodo.moFichero.refrescarListaFicheros();
            refrescar(node);
            //da un casque pero no veo otra forma de refrescar pantalla
            jTree1.updateUI();
        }
        
    }
    public void refrescar(DefaultMutableTreeNode top) throws Exception {
        //ponemos la direccion en el encabezado
        Object nodeInfo = top.getUserObject();
        JArchivosNodoArbol loDatosNodo = (JArchivosNodoArbol)nodeInfo;
        txtDireccion.setText(loDatosNodo.moFichero.getPath());
        if(txtDireccion.getText().compareTo("")==0){
            txtDireccion.setText("/");
        }
        //actualizamos la tabla
        IListaElementos loLista = moServidor.getListaFicheros(loDatosNodo.moFichero);
        moModelo.setDatos(loLista);
        jTable1.setModel(moModelo);
        jTable1.updateUI();
        //actualizamos el arbol
        crearNodosHijos(top,loLista);
    }
    
    private void crearNodosHijos(DefaultMutableTreeNode poPadre, IListaElementos poLista){
        poPadre.removeAllChildren();
        for(int i =0; i< poLista.size();i++){
            JFichero loFichero = (JFichero)poLista.get(i);
            if(loFichero.getEsDirectorio()){
                poPadre.add(new DefaultMutableTreeNode(new JArchivosNodoArbol(loFichero)));
            }
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

        jPanelCentral = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanelArriva = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        btnRefrescar = new utilesGUIx.JButtonCZ();
        jLabel2 = new javax.swing.JLabel();
        btnCopiar = new utilesGUIx.JButtonCZ();
        btnCortar = new utilesGUIx.JButtonCZ();
        btnPegar = new utilesGUIx.JButtonCZ();
        jLabel3 = new javax.swing.JLabel();
        btnBorrar = new utilesGUIx.JButtonCZ();
        jLabel4 = new javax.swing.JLabel();
        btnCrearCarpeta = new utilesGUIx.JButtonCZ();
        jLabel1 = new javax.swing.JLabel();
        txtDireccion = new utilesGUIx.JTextFieldCZ();
        jPanelIzq = new javax.swing.JPanel();
        jPanelBajo = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout());

        jPanelCentral.setLayout(new java.awt.BorderLayout());

        jSplitPane1.setDividerLocation(150);

        jTree1.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                jTree1ValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jTree1);

        jSplitPane1.setLeftComponent(jScrollPane1);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable1);

        jSplitPane1.setRightComponent(jScrollPane2);

        jPanelCentral.add(jSplitPane1, java.awt.BorderLayout.CENTER);

        add(jPanelCentral, java.awt.BorderLayout.CENTER);

        jPanelArriva.setPreferredSize(new java.awt.Dimension(10, 65));
        jPanelArriva.setLayout(new java.awt.GridBagLayout());

        btnRefrescar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/archivosPorWeb/cliente/images/Refresh16.gif"))); // NOI18N
        btnRefrescar.setToolTipText("Refrescar");
        btnRefrescar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefrescarActionPerformed(evt);
            }
        });
        jToolBar1.add(btnRefrescar);

        jLabel2.setText("        ");
        jToolBar1.add(jLabel2);

        btnCopiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/archivosPorWeb/cliente/images/Copy16.gif"))); // NOI18N
        btnCopiar.setToolTipText("Copiar");
        btnCopiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCopiarActionPerformed(evt);
            }
        });
        jToolBar1.add(btnCopiar);

        btnCortar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/archivosPorWeb/cliente/images/Cut16.gif"))); // NOI18N
        btnCortar.setToolTipText("Cortar");
        btnCortar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCortarActionPerformed(evt);
            }
        });
        jToolBar1.add(btnCortar);

        btnPegar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/archivosPorWeb/cliente/images/Paste16.gif"))); // NOI18N
        btnPegar.setToolTipText("Pegar");
        btnPegar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPegarActionPerformed(evt);
            }
        });
        jToolBar1.add(btnPegar);

        jLabel3.setText("        ");
        jToolBar1.add(jLabel3);

        btnBorrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/archivosPorWeb/cliente/images/Delete16.gif"))); // NOI18N
        btnBorrar.setToolTipText("Borrar");
        btnBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarActionPerformed(evt);
            }
        });
        jToolBar1.add(btnBorrar);

        jLabel4.setText("        ");
        jToolBar1.add(jLabel4);

        btnCrearCarpeta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/archivosPorWeb/cliente/images/Open16.gif"))); // NOI18N
        btnCrearCarpeta.setToolTipText("Crear carpeta");
        btnCrearCarpeta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearCarpetaActionPerformed(evt);
            }
        });
        jToolBar1.add(btnCrearCarpeta);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanelArriva.add(jToolBar1, gridBagConstraints);

        jLabel1.setText("Dirección");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanelArriva.add(jLabel1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanelArriva.add(txtDireccion, gridBagConstraints);

        add(jPanelArriva, java.awt.BorderLayout.NORTH);

        jPanelIzq.setLayout(new java.awt.BorderLayout());
        add(jPanelIzq, java.awt.BorderLayout.WEST);

        jPanelBajo.setPreferredSize(new java.awt.Dimension(10, 20));
        jPanelBajo.setLayout(new java.awt.BorderLayout());
        add(jPanelBajo, java.awt.BorderLayout.SOUTH);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCrearCarpetaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearCarpetaActionPerformed
        try{
            String lsCarpeta = JOptionPane.showInputDialog("Nombre carpeta");
            if(lsCarpeta!=null && lsCarpeta.compareTo("")!=0){
                //recuperamos la informacion del nodo
                DefaultMutableTreeNode node = 
                    (DefaultMutableTreeNode)jTree1.getLastSelectedPathComponent();

                if (node != null) {
                    Object nodeInfo = node.getUserObject();
                    JArchivosNodoArbol loDatosNodo = (JArchivosNodoArbol)nodeInfo;
                    moServidor.crearCarpeta(new JFichero(loDatosNodo.moFichero.getPath(), lsCarpeta,true,0,null));
                }
            }
            refrescar();
        }catch(Exception e){
             utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this, e);
        }

    }//GEN-LAST:event_btnCrearCarpetaActionPerformed

    private void btnBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarActionPerformed
        try{
            String lsMensaje = "";
            int[] lalFilas = jTable1.getSelectedRows();
            for(int i = 0; i<lalFilas.length;i++){
                JFichero loFichero = (JFichero)moModelo.moLista.get(lalFilas[i]);
                lsMensaje+=loFichero.getNombre() + ", ";
            }
            if(JOptionPane.showConfirmDialog(this, "¿Deseas borrar "+lsMensaje+"?","Borrar",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION)
            for(int i = 0; i<lalFilas.length;i++){
                JFichero loFichero = (JFichero)moModelo.moLista.get(lalFilas[i]);
                moServidor.borrar(loFichero);
            }
            refrescar();
        }catch(Exception e){
             utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this, e);
        }
        
    }//GEN-LAST:event_btnBorrarActionPerformed

    private void btnPegarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPegarActionPerformed
        try{
            //recuperamos la informacion del nodo
            DefaultMutableTreeNode node = 
                (DefaultMutableTreeNode)jTree1.getLastSelectedPathComponent();

            if (node == null) return;

            Object nodeInfo = node.getUserObject();
            JArchivosNodoArbol loDatosNodo = (JArchivosNodoArbol)nodeInfo;
            
            JArchivosClienteComun.pegar(moServidor, loDatosNodo.moFichero);
            refrescar(node);
        }catch(Exception e){
             utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this, e);
        }
    }//GEN-LAST:event_btnPegarActionPerformed

    private void btnCortarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCortarActionPerformed
        try{
            int[] lalFilas = jTable1.getSelectedRows();
            JArchivosClienteAccion loAccion = new JArchivosClienteAccion(moServidor);
            loAccion.mlAccion = loAccion.mclCortar;
            for(int i = 0; i<lalFilas.length;i++){
                JFichero loFichero = (JFichero)moModelo.moLista.get(lalFilas[i]);
                loAccion.moElementos.add(loFichero);
            }
        }catch(Exception e){
             utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this, e);
        }
        
    }//GEN-LAST:event_btnCortarActionPerformed

    private void btnCopiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCopiarActionPerformed
        try{
            int[] lalFilas = jTable1.getSelectedRows();
            JArchivosClienteAccion loAccion = new JArchivosClienteAccion(moServidor);
            loAccion.mlAccion = loAccion.mclCopiar;
            for(int i = 0; i<lalFilas.length;i++){
                JFichero loFichero = (JFichero)moModelo.moLista.get(lalFilas[i]);
                loAccion.moElementos.add(loFichero);
            }
            JArchivosClienteComun.setAccion(loAccion);
        }catch(Exception e){
            utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this, e);
        }

    }//GEN-LAST:event_btnCopiarActionPerformed

    private void jTree1NodoCambiar(DefaultMutableTreeNode node ) throws Exception {                                    
        //recuperamos la informacion del nodo
        if (node == null) return;

        TreePath loTreePath = new TreePath(node.getPath());
        //lo seleccionamos
        jTree1.setSelectionPath(loTreePath);
        //no aseguramos q es visible
        jTree1.makeVisible(loTreePath);

        Object nodeInfo = node.getUserObject();
        JArchivosNodoArbol loDatosNodo = (JArchivosNodoArbol)nodeInfo;

        //procesamos
        txtDireccion.setText(loDatosNodo.moFichero.getPath());
        refrescar(node);

        //expandimos el nodo
        jTree1.expandPath(loTreePath);
    }                                   
    
    private void jTree1ValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_jTree1ValueChanged
        try{
            //recuperamos la informacion del nodo
            DefaultMutableTreeNode node = 
                (DefaultMutableTreeNode)jTree1.getLastSelectedPathComponent();

            jTree1NodoCambiar(node);
        }catch(Exception e){
             utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this, e);
        }

    }//GEN-LAST:event_jTree1ValueChanged

    private void btnRefrescarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefrescarActionPerformed
        try{
            DefaultMutableTreeNode node = 
                (DefaultMutableTreeNode)jTree1.getLastSelectedPathComponent();

            if (node == null) {
                refrescar(top);
            }else{
                refrescar(node);
            }
        }catch(Exception e){
             utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this, e);
        }

    }//GEN-LAST:event_btnRefrescarActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        try{
            if(jTable1.getSelectedRow()<moModelo.moLista.size() && evt.getClickCount()>1){
                JFichero loFichero = (JFichero) moModelo.moLista.get(jTable1.getSelectedRow());
                if(loFichero.getEsDirectorio()){
                    DefaultMutableTreeNode node = 
                        (DefaultMutableTreeNode)jTree1.getLastSelectedPathComponent();

                    if (node == null) {
                        node = (top);
                    }
                    
                    for(int i = 0 ; i < node.getChildCount(); i++){
                        DefaultMutableTreeNode loNodeAux = ((DefaultMutableTreeNode)node.getChildAt(i));
                        Object nodeInfo = loNodeAux.getUserObject();
                        JArchivosNodoArbol loDatosNodo = (JArchivosNodoArbol)nodeInfo;
                        if(loDatosNodo.moFichero.getPath().equals(loFichero.getPath())){
                            jTree1NodoCambiar(loNodeAux);
                        }
                    }
                } else {
                    if(moCallBackRuta!=null){
                        establecerDatos();
                        aceptar();
                        JGUIxConfigGlobalModelo.getInstancia().getMostrarPantalla().cerrarForm(this);
                    } else {
                        if(moServidor instanceof JServidorArchivos){
                            JEjecutar.abrirDocumento(loFichero.getPath());
                        } else {
                            File loFile ;  
                            if(loFichero.getNombre().length()>4){
                                loFile = File.createTempFile(loFichero.getNombre().substring(0, loFichero.getNombre().length()-4) + "zzzzzzzz", loFichero.getNombre().substring(loFichero.getNombre().length()-4));
                            } else {
                                loFile = File.createTempFile(loFichero.getNombre() + "zzzzzzzz", loFichero.getNombre());
                            }

                            new JServidorArchivos().copiarA(moServidor, loFichero, new JFichero(loFile));
                            JEjecutar.abrirDocumento(loFile.getAbsolutePath());
                        }
                    }
                }
            }
        }catch(Exception e){
            utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this, e);
        }

    }//GEN-LAST:event_jTable1MouseClicked
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private utilesGUIx.JButtonCZ btnBorrar;
    private utilesGUIx.JButtonCZ btnCopiar;
    private utilesGUIx.JButtonCZ btnCortar;
    private utilesGUIx.JButtonCZ btnCrearCarpeta;
    private utilesGUIx.JButtonCZ btnPegar;
    private utilesGUIx.JButtonCZ btnRefrescar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanelArriva;
    private javax.swing.JPanel jPanelBajo;
    private javax.swing.JPanel jPanelCentral;
    private javax.swing.JPanel jPanelIzq;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTree jTree1;
    private utilesGUIx.JTextFieldCZ txtDireccion;
    // End of variables declaration//GEN-END:variables

}
