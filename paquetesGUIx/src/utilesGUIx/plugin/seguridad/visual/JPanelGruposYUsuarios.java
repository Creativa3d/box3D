/*
 * JPanelGruposYUsuarios.java
 *
 * Created on 23-mar-2009, 13:58:51
 */

package utilesGUIx.plugin.seguridad.visual;

import utilesGUIx.ActionEventCZ;
import utilesGUIx.formsGenericos.IPanelGenerico;
import ListDatos.EBookmarkIncorrecto;
import ListDatos.IFilaDatos;
import ListDatos.JFilaDatosDefecto;
import ListDatos.JListDatos;
import ListDatos.JListDatosFiltroConj;
import ListDatos.JSTabla;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import utiles.JDepuracion;
import utilesGUIx.formsGenericos.*;
import utilesGUIx.formsGenericos.edicion.*;
import utilesGUIx.formsGenericos.busqueda.IConsulta;
import utilesGUIx.msgbox.JMsgBox;
import utilesGUIx.plugin.seguridad.IPlugInSeguridadRW;
import utilesGUIx.plugin.seguridad.JTPlugInGrupos;
import utilesGUIx.plugin.seguridad.JTPlugInListaPermisos;
import utilesGUIx.plugin.seguridad.JTPlugInUsuarios;
import utilesGUIx.plugin.seguridad.JTPlugInUsuariosGrupos;


public class JPanelGruposYUsuarios extends javax.swing.JPanel implements IPanelControlador {
    private static final long serialVersionUID = 1L;
    public static final String mcsGrupos = "Perfiles";
    public static final String mcsUsuarios = "Usuarios";
    public static final String mcsSeguridad = "Seguridad";

    private IPlugInSeguridadRW moRW;
    private DefaultMutableTreeNode moTop;
    private DefaultTreeModel moTreeModel;
    private JPanelGeneralParametros moParametros = new JPanelGeneralParametros();
    private DefaultMutableTreeNode moNodoGrupos;
    private DefaultMutableTreeNode moNodoUsuarios;
    private JTPlugInListaPermisos moListaPermisosBase;
    private boolean mbPantallasConCodigo=true;

    /** Creates new form JPanelGruposYUsuarios */
    public JPanelGruposYUsuarios() {
        initComponents();
    }

    public void setDatos(IPlugInSeguridadRW poRW) throws Exception{
        setDatos(poRW, true);
    }
    public void setDatos(IPlugInSeguridadRW poRW, boolean pbPantallasConCodigo) throws Exception{
        mbPantallasConCodigo = pbPantallasConCodigo;
        moRW = poRW;
        //se recogen los permisos aqui para q al cargarse las clases
        //no vuelvan a cargar los permisos
        moListaPermisosBase = moRW.getListaPermisosBase();
        mostrarDatos();
    }
    public void mostrarDatos() throws Exception{
        moTop = new DefaultMutableTreeNode(
                new JNodoArbol(
                    new int[]{0},
                    new JFilaDatosDefecto(new String[]{mcsSeguridad})
                )

                );
        crearNodosRaiz();
        moTreeModel = new DefaultTreeModel(moTop);
        jTree1.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        jTree1.setShowsRootHandles(true);
        jTree1.setScrollsOnExpand(true);
        jTree1.setModel(moTreeModel);
    }


    private void crearNodosRaiz() throws Exception {
        moNodoGrupos = null;
        moNodoGrupos = new DefaultMutableTreeNode(
                new JNodoArbol(
                    new int[]{0},
                    new JFilaDatosDefecto(new String[]{mcsGrupos})
                )
                );
        JTPlugInGrupos loGrupos = moRW.getGrupos();
        if(loGrupos.moList.moveFirst()){
            do{
                moNodoGrupos.add(new DefaultMutableTreeNode(
                new JNodoArbol(
                        mbPantallasConCodigo ?
                    new int[]{JTPlugInGrupos.lPosiCODIGOGRUPO, JTPlugInGrupos.lPosiNOMBRE}:
                    new int[]{JTPlugInGrupos.lPosiNOMBRE},
                    loGrupos.moList.moFila()
                )
                ));
            }while(loGrupos.moList.moveNext());
        }
        moTop.add(moNodoGrupos);

        moNodoUsuarios = new DefaultMutableTreeNode(
                new JNodoArbol(
                    new int[]{0},
                    new JFilaDatosDefecto(new String[]{mcsUsuarios})
                )
                );
        JTPlugInUsuarios loUsuarios = moRW.getUsuarios();
        if(loUsuarios.moList.moveFirst()){
            do{
                moNodoUsuarios.add(new DefaultMutableTreeNode(
                new JNodoArbol(
                        mbPantallasConCodigo ?
                    new int[]{JTPlugInUsuarios.lPosiCODIGOUSUARIO , JTPlugInGrupos.lPosiNOMBRE}:
                    new int[]{JTPlugInGrupos.lPosiNOMBRE},
                    loUsuarios.moList.moFila()
                )
                ));
            }while(loUsuarios.moList.moveNext());
        }
        moTop.add(moNodoUsuarios);
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        btnNuevo = new javax.swing.JButton();
        btnBorrar = new javax.swing.JButton();
        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        jPanel1 = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout());

        jToolBar1.setRollover(true);

        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIx/images/Add16.gif"))); // NOI18N
        btnNuevo.setText("Nuevo");
        btnNuevo.setFocusable(false);
        btnNuevo.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnNuevo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });
        jToolBar1.add(btnNuevo);

        btnBorrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIx/images/Delete16.gif"))); // NOI18N
        btnBorrar.setText("Borrar");
        btnBorrar.setFocusable(false);
        btnBorrar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnBorrar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarActionPerformed(evt);
            }
        });
        jToolBar1.add(btnBorrar);

        add(jToolBar1, java.awt.BorderLayout.NORTH);

        jTree1.setAutoscrolls(true);
        jTree1.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                jTree1ValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jTree1);

        jSplitPane1.setLeftComponent(jScrollPane1);
        jSplitPane1.setRightComponent(jPanel1);

        add(jSplitPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void presentarEdicion(String psRama, IFilaDatos poFila) throws Exception{
        if(psRama.equals(mcsGrupos)){
            JTPlugInGrupos loGrupo = new JTPlugInGrupos();
            if(poFila==null){
                loGrupo.moList.addNew();
            }else{
                loGrupo.moList.add(poFila);
                loGrupo.moList.setIndex(0);
            }
            JPanelGrupo loPanel = new JPanelGrupo();
            loPanel.setDatos(loGrupo, moRW, this, moListaPermisosBase);
            loPanel.setPantallasConCodigo(mbPantallasConCodigo);

            JPanelEdicion jPanelEdicion1;
            jPanelEdicion1 = new JPanelEdicion();
            jPanelEdicion1.setBorder(new javax.swing.border.BevelBorder(javax.swing.border.BevelBorder.RAISED));
            jPanelEdicion1.setPanel(loPanel, loPanel, null);
            jPanelEdicion1.btnAceptar.setText("Aplicar");
            jPanelEdicion1.btnCancelar.setVisible(false);
            jSplitPane1.remove(jSplitPane1.getRightComponent());
            jSplitPane1.add(jPanelEdicion1);
        }
        if(psRama.equals(mcsUsuarios)){
            JTPlugInUsuarios loUsuarios = new JTPlugInUsuarios();
            if(poFila==null){
                loUsuarios.moList.addNew();
            }else{
                loUsuarios.moList.add(poFila);
                loUsuarios.moList.setIndex(0);
            }
            JPanelUsuario loPanel = new JPanelUsuario();
            loPanel.setDatos(loUsuarios,moRW,this,moListaPermisosBase);
            loPanel.setPantallasConCodigo(mbPantallasConCodigo);

            JPanelEdicion jPanelEdicion1;
            jPanelEdicion1 = new JPanelEdicion();
            jPanelEdicion1.setBorder(new javax.swing.border.BevelBorder(javax.swing.border.BevelBorder.RAISED));
            jPanelEdicion1.setPanel(loPanel, loPanel, null);
            jPanelEdicion1.btnAceptar.setText("Aplicar");
            jPanelEdicion1.btnCancelar.setVisible(false);
            jSplitPane1.remove(jSplitPane1.getRightComponent());
            jSplitPane1.add(jPanelEdicion1);
        }
    }

    private void jTree1ValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_jTree1ValueChanged
        //visualizamos los datos en el form.
        try{
            DefaultMutableTreeNode loNodo = (DefaultMutableTreeNode)jTree1.getLastSelectedPathComponent();
            if (loNodo != null ) {
                JNodoArbol loNodoInfo = (JNodoArbol)loNodo.getUserObject();
                //expandimos
                jTree1.expandPath(new TreePath(loNodo.getPath()));
                //editar nodo actual
                DefaultMutableTreeNode loNodoPadre = (DefaultMutableTreeNode)loNodo.getParent();
                if(loNodoPadre!=null){
                    JNodoArbol loNodoPadreInfo = (JNodoArbol) loNodoPadre.getUserObject();
                    if(loNodoPadreInfo.getDatos().msCampo(0).equals(mcsGrupos)){
                        presentarEdicion(mcsGrupos, loNodoInfo.getDatos());
                    }
                    if(loNodoPadreInfo.getDatos().msCampo(0).equals(mcsUsuarios)){
                        presentarEdicion(mcsUsuarios, loNodoInfo.getDatos());
                    }
                }
            }
        }catch(Exception e){
            utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this, e, getClass().getName());
        }

    }//GEN-LAST:event_jTree1ValueChanged

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        try{
            DefaultMutableTreeNode loNodo = (DefaultMutableTreeNode)jTree1.getLastSelectedPathComponent();
            if (loNodo != null ) {
                JNodoArbol loNodoInfo = (JNodoArbol)loNodo.getUserObject();
                if(isUsuario(loNodoInfo.getDatos())){
                    presentarEdicion(mcsUsuarios, null);
                }else{
                    presentarEdicion(mcsGrupos, null);
                }
            }

        }catch(Exception e){
            utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this, e, getClass().getName());
        }

}//GEN-LAST:event_btnNuevoActionPerformed

    private void btnBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarActionPerformed
        //visualizamos los datos en el form.
        try{
            DefaultMutableTreeNode loNodo = (DefaultMutableTreeNode)jTree1.getLastSelectedPathComponent();
            if (loNodo != null ) {
                if(JOptionPane.showConfirmDialog(jSplitPane1, "¿Estas seguro de borrar el registro actual?", "Confirmación", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
                    JNodoArbol loNodoInfo = (JNodoArbol)loNodo.getUserObject();
                    //expandimos
                    jTree1.expandPath(new TreePath(loNodo.getPath()));
                    //editar nodo actual
                    DefaultMutableTreeNode loNodoPadre = (DefaultMutableTreeNode)loNodo.getParent();
                    if(loNodoPadre!=null){
                        JNodoArbol loNodoPadreInfo = (JNodoArbol) loNodoPadre.getUserObject();
                        if(loNodoPadreInfo.getDatos().msCampo(0).equals(mcsGrupos)){
                            JTPlugInUsuariosGrupos loUsuariosGrupos=moRW.getUsuariosGrupos();
                            loUsuariosGrupos.moList.getFiltro().Clear();
                            loUsuariosGrupos.moList.filtrarNulo();
                            loUsuariosGrupos.moList.getFiltro().addCondicion(
                                    JListDatosFiltroConj.mclAND,
                                    JListDatos.mclTIgual,
                                    loUsuariosGrupos.lPosiCODIGOGRUPO,
                                    loNodoInfo.getDatos().msCampo(JTPlugInGrupos.lPosiCODIGOGRUPO)
                                    );
                            loUsuariosGrupos.moList.filtrar();
                            try{
                                while(loUsuariosGrupos.moList.moveFirst()){
                                    loUsuariosGrupos.moList.borrar(false);
                                }
                            }finally{
                                loUsuariosGrupos.moList.getFiltro().Clear();
                                loUsuariosGrupos.moList.filtrarNulo();
                                moRW.guardareUsuariosGrupos(loUsuariosGrupos);
                            }
                            loNodoInfo.getDatos().setTipoModif(JListDatos.mclBorrar);
                            moRW.guardarGrupo(loNodoInfo.getDatos());
                            datosactualizados(loNodoInfo.getDatos());
                        }
                        if(loNodoPadreInfo.getDatos().msCampo(0).equals(mcsUsuarios)){
                            JTPlugInUsuariosGrupos loUsuariosGrupos=moRW.getUsuariosGrupos();
                            loUsuariosGrupos.moList.getFiltro().Clear();
                            loUsuariosGrupos.moList.filtrarNulo();
                            loUsuariosGrupos.moList.getFiltro().addCondicion(
                                    JListDatosFiltroConj.mclAND,
                                    JListDatos.mclTIgual,
                                    loUsuariosGrupos.lPosiCODIGOUSUARIO,
                                    loNodoInfo.getDatos().msCampo(JTPlugInUsuarios.lPosiCODIGOUSUARIO)
                                    );
                            loUsuariosGrupos.moList.filtrar();
                            try{
                                while(loUsuariosGrupos.moList.moveFirst()){
                                    loUsuariosGrupos.moList.borrar(false);
                                }
                            }finally{
                                loUsuariosGrupos.moList.getFiltro().Clear();
                                loUsuariosGrupos.moList.filtrarNulo();
                                moRW.guardareUsuariosGrupos(loUsuariosGrupos);
                            }
                            loNodoInfo.getDatos().setTipoModif(JListDatos.mclBorrar);
                            moRW.guardarUsuario(loNodoInfo.getDatos());
                            datosactualizados(loNodoInfo.getDatos());
                        }
                    }
                }
            }
        }catch(Exception e){
            utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this, e, getClass().getName());
        }

}//GEN-LAST:event_btnBorrarActionPerformed

    private boolean isUsuario(IFilaDatos poFila){
        return (poFila.mlNumeroCampos() > JTPlugInGrupos.mclNumeroCampos) ||
               (poFila.msCampo(0).equals(mcsUsuarios) && poFila.mlNumeroCampos()<=1);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBorrar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTree jTree1;
    // End of variables declaration//GEN-END:variables

    public void refrescar() throws Exception {
        jTree1.updateUI();
    }
    public void datosactualizados(IFilaDatos poFila) throws Exception {
        if(poFila.getTipoModif() == JListDatos.mclNuevo){
            if(isUsuario(poFila)){
                moNodoUsuarios.add(new DefaultMutableTreeNode(
                    new JNodoArbol(
                        mbPantallasConCodigo ?
                            new int[]{JTPlugInUsuarios.lPosiCODIGOUSUARIO , JTPlugInGrupos.lPosiNOMBRE}:
                            new int[]{JTPlugInGrupos.lPosiNOMBRE},
                        poFila
                    )
                    ));
            }else{
                moNodoGrupos.add(new DefaultMutableTreeNode(
                    new JNodoArbol(
                        mbPantallasConCodigo ?
                            new int[]{JTPlugInGrupos.lPosiCODIGOGRUPO, JTPlugInGrupos.lPosiNOMBRE}:
                            new int[]{JTPlugInGrupos.lPosiNOMBRE},
                        poFila
                    )
                    ));
            }
        }
        if(poFila.getTipoModif() == JListDatos.mclBorrar){
            //solo se puede borrar el nodo actual
            moTreeModel.removeNodeFromParent(
                    (MutableTreeNode) jTree1.getLastSelectedPathComponent());
            jSplitPane1.setRightComponent(jPanel1);
        }
        refrescar();
    }

    public IConsulta getConsulta() {
        return null;
    }

    public void anadir() throws Exception {
    }

    public void valoresDefecto(JSTabla poTabla) throws Exception {
    }

    public void borrar(int plIndex) throws Exception {
    }

    public void editar(int plIndex) throws Exception {
    }

    public JListDatos getDatos() throws Exception {
        return null;
    }


    public IPanelGenerico getPanel() {
        return null;
    }

    public void setPanel(IPanelGenerico poPanel) {
    }


    public void mostrarFormPrinci() throws Exception {
    }

    public void setIndexs(int[] plIndex) throws EBookmarkIncorrecto {
    }
    
    public int[] getIndexs() {
        return new int[0];
    }

    public int getIndex() {
        return -1;
    }

    public JPanelGeneralParametros getParametros() {
        return moParametros;
    }

//
//    public void salir() {
//    }
//
//    public void setTitle(String psTitulo) {
//    }

}
