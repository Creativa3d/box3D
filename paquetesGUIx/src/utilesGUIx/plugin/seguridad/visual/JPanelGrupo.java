/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * JPanelUsuario.java
 *
 * Created on 16-mar-2009, 9:16:16
 */

package utilesGUIx.plugin.seguridad.visual;
import utilesGUIx.Rectangulo;
import utilesGUIx.plugin.seguridad.*;
import ListDatos.IResultado;
import ListDatos.JFilaDatosDefecto;
import ListDatos.JListDatos;
import ListDatos.JListDatosFiltroConj;
import ListDatos.JSTabla;
import java.awt.Dimension;
import java.awt.Rectangle;
import javax.swing.JLabel;
import utilesGUIx.JCheckBoxCZ;

import utilesGUIx.formsGenericos.edicion.*;
import utilesGUIx.formsGenericos.*;

public class JPanelGrupo extends JPanelGENERALBASE {

    private JTPlugInGrupos moGrupo;
    private IPlugInSeguridadRW moRW;
    private boolean mbPermisos=false;
    private JTPlugInListaPermisos moListaPermisosBase;
    private JTPlugInUsuariosGrupos moUsuariosGrupos;
    private JCheckBoxCZ[] moDocumentos=new JCheckBoxCZ[0];

    /** Creates new form JPanelUsuario */
    public JPanelGrupo() {
        initComponents();
    }
    public JSTabla getTabla() {
        return moGrupo;
    }
    public void setDatos(JTPlugInGrupos poGrupo, IPlugInSeguridadRW poRW, IPanelControlador poPadre,JTPlugInListaPermisos poListaPermisosBase) throws Exception {
        moGrupo = poGrupo;
        moPadre = poPadre;
        moListaPermisosBase = poListaPermisosBase;
        moRW = poRW;
    }
    public void setPantallasConCodigo(boolean pbPantallasConCodigo) {
        lblCODIGO.setVisible(pbPantallasConCodigo);
        txtCODIGO.setVisible(pbPantallasConCodigo);
    }

    public String getTitulo() {
        if(moGrupo.moList.getModoTabla() == JListDatos.mclNuevo) {
            return JPanelGruposYUsuarios.mcsGrupos + " [Nuevo]" ;
        }else{
            return JPanelGruposYUsuarios.mcsGrupos + " " + moGrupo.getCODIGOGRUPO().getString() + "-" + moGrupo.getNOMBRE().getString();
        }
    }

    public void rellenarPantalla() throws Exception {
        txtCODIGO.setField(moGrupo.getCODIGOGRUPO());
        lblCODIGO.setField(moGrupo.getCODIGOGRUPO());
        txtNOMBRE.setField(moGrupo.getNOMBRE());
        lblNOMBRE.setField(moGrupo.getNOMBRE());

        moUsuariosGrupos = moRW.getUsuariosGrupos();

        JTPlugInUsuarios loUsu = moRW.getUsuarios();
        //primero borramos los componenetes del panel de documentos
        for(int i = 0; i < moDocumentos.length;i++ ){
            jPanelDocumentos.remove(moDocumentos[i]);
        }
        if(loUsu.moveFirst()){
            moDocumentos = new JCheckBoxCZ[loUsu.moList.size()];
            int i = 0;
            do{
                    moDocumentos[i] = new JCheckBoxCZ();
                    java.awt.GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
                    gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
                    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
                    gridBagConstraints.weightx = 1.0;
                    gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
                    jPanelDocumentos.add(moDocumentos[i], gridBagConstraints);
                    moDocumentos[i].setText(
                            loUsu.getCODIGOUSUARIO().getString()+"-"+
                            loUsu.getNOMBRE().getString());
                    i++;

            }while(loUsu.moveNext());

            jPanelDocumentos.setPreferredSize(new Dimension(100, moDocumentos.length*30));
            java.awt.GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            gridBagConstraints.weightx = 1.0;
            gridBagConstraints.weighty = 1.0;
            jPanelDocumentos.add(new JLabel(""), gridBagConstraints);

        }
    }
    public void habilitarSegunEdicion() throws Exception {
        if(moGrupo.moList.getModoTabla() != JListDatos.mclNuevo) {
            txtCODIGO.setEnabled(false);
        }
    }
    public void ponerTipoTextos() throws Exception {
    }
    private void marcarDoc(String psCodigo){
        for(int i = 0; i < moDocumentos.length;i++ ){
            if(JFilaDatosDefecto.gfsExtraerCampo(moDocumentos[i].getText(), 0, '-').compareTo(psCodigo)==0){
                moDocumentos[i].setSelected(true);
            }
        }
    }
    public void mostrarDatos() throws Exception {

        moUsuariosGrupos.moList.getFiltro().Clear();
        moUsuariosGrupos.moList.filtrarNulo();
        moUsuariosGrupos.moList.getFiltro().addCondicion(
                JListDatosFiltroConj.mclAND,
                JListDatos.mclTIgual,
                moUsuariosGrupos.lPosiCODIGOGRUPO,
                moGrupo.getCODIGOGRUPO().getString()
                );
        moUsuariosGrupos.moList.filtrar();
        if(moUsuariosGrupos.moList.moveFirst()){
            do{
                marcarDoc(moUsuariosGrupos.getCODIGOUSUARIO().getString());
            }while(moUsuariosGrupos.moList.moveNext());
        }
//        jTabbedPane1StateChanged(null);

        mbPermisos=false;
    }
    public void establecerDatos() throws Exception {

        moGrupo.validarCampos();

        if(txtCODIGO.getText().equals("")){
            JTPlugInGrupos loG = moRW.getGrupos();
            int lMax = 0;
            if(loG.moveFirst()){
                do{
                    if(loG.getCODIGOGRUPO().getInteger()>=lMax){
                        lMax=loG.getCODIGOGRUPO().getInteger();
                    }
                }while(loG.moveNext());
            }
            moGrupo.getCODIGOGRUPO().setValue(lMax+1);
        }

        moUsuariosGrupos.moList.getFiltro().Clear();
        moUsuariosGrupos.moList.filtrarNulo();
        moUsuariosGrupos.moList.getFiltro().addCondicion(
                JListDatosFiltroConj.mclAND,
                JListDatos.mclTIgual,
                moUsuariosGrupos.lPosiCODIGOGRUPO,
                moGrupo.getCODIGOGRUPO().getString()
                );
        moUsuariosGrupos.moList.filtrar();
        while(moUsuariosGrupos.moList.moveFirst()){
            moUsuariosGrupos.moList.borrar(false);
        }

        for(int i = 0; i < moDocumentos.length;i++ ){
            if(moDocumentos[i].isSelected()){
                moUsuariosGrupos.moList.addNew();

                moUsuariosGrupos.getCODIGOUSUARIO().setValue(
                        JFilaDatosDefecto.gfsExtraerCampo(moDocumentos[i].getText(), 0, '-')
                        );
                moUsuariosGrupos.getCODIGOGRUPO().setValue(
                        moGrupo.getCODIGOGRUPO().getString()
                        );

                moUsuariosGrupos.moList.update(false);
            }
        }
        moUsuariosGrupos.moList.getFiltro().Clear();
        moUsuariosGrupos.moList.filtrarNulo();

        if(mbPermisos){
            jPanelPermisos1.establecerDatos();
        }

    }

    public void aceptar() throws Exception {
        int lModo = moGrupo.moList.getModoTabla();
        IResultado loResult=moGrupo.moList.update(false);
        if(!loResult.getBien()){
            throw new Exception(loResult.getMensaje());
        }else{
            if(lModo== JListDatos.mclNada){
                lModo = JListDatos.mclEditar;
            }
            moGrupo.moList.moFila().setTipoModif(lModo);
            moRW.guardarGrupo(moGrupo.moList.moFila());
            moGrupo.moList.cargarFields();
            if(mbPermisos){
                moRW.guardarListaPermisosGrupo(
                        moGrupo.getCODIGOGRUPO().getString(),
                        jPanelPermisos1.getPermisosFILTRADOS()
                        );
            }
            moRW.guardareUsuariosGrupos(moUsuariosGrupos);
        }
        actualizarPadre(lModo);
        JPanelEdicion.mostrarDatosBD(this);
        mostrarDatos();
    }

    public void cancelar() throws Exception {
        moGrupo.moList.cancel();
    }
    public Rectangulo getTanano(){
        return new Rectangulo(0,0, 400, 440);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        lblCODIGO = new utilesGUIx.JLabelCZ();
        txtCODIGO = new utilesGUIx.JTextFieldCZ();
        lblNOMBRE = new utilesGUIx.JLabelCZ();
        txtNOMBRE = new utilesGUIx.JTextFieldCZ();
        jLabelCZ1 = new utilesGUIx.JLabelCZ();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanelDocumentos = new javax.swing.JPanel();
        jPanelPermisos1 = new utilesGUIx.plugin.seguridad.visual.JPanelPermisos();

        setPreferredSize(new java.awt.Dimension(400, 432));
        setLayout(new java.awt.BorderLayout());

        jTabbedPane1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTabbedPane1StateChanged(evt);
            }
        });

        jPanel1.setLayout(new java.awt.GridBagLayout());

        lblCODIGO.setText("Código");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanel1.add(lblCODIGO, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanel1.add(txtCODIGO, gridBagConstraints);

        lblNOMBRE.setText("Nombre");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanel1.add(lblNOMBRE, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanel1.add(txtNOMBRE, gridBagConstraints);

        jLabelCZ1.setText("Usuarios asignados");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanel1.add(jLabelCZ1, gridBagConstraints);

        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setAutoscrolls(true);

        jPanelDocumentos.setBackground(new java.awt.Color(255, 255, 255));
        jPanelDocumentos.setAutoscrolls(true);
        jPanelDocumentos.setLayout(new java.awt.GridBagLayout());
        jScrollPane1.setViewportView(jPanelDocumentos);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 0.1;
        jPanel1.add(jScrollPane1, gridBagConstraints);

        jTabbedPane1.addTab("General", jPanel1);
        jTabbedPane1.addTab("Permisos", jPanelPermisos1);

        add(jTabbedPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void jTabbedPane1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTabbedPane1StateChanged
        try{
            if(jTabbedPane1.getSelectedIndex()>0){
                mbPermisos = true;
                jPanelPermisos1.setDatos(
                        moRW.getListaPermisosGrupo(txtCODIGO.getText()),
                        moListaPermisosBase,
                        moPadre,
                        txtCODIGO.getText()
                        );
                jPanelPermisos1.habilitarSegunEdicion();
                jPanelPermisos1.rellenarPantalla();
                jPanelPermisos1.ponerTipoTextos();
                jPanelPermisos1.mostrarDatos();
            }
        }catch(Throwable e){
            utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this, e, getClass().getName());
        }
    }//GEN-LAST:event_jTabbedPane1StateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private utilesGUIx.JLabelCZ jLabelCZ1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelDocumentos;
    private utilesGUIx.plugin.seguridad.visual.JPanelPermisos jPanelPermisos1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private utilesGUIx.JLabelCZ lblCODIGO;
    private utilesGUIx.JLabelCZ lblNOMBRE;
    private utilesGUIx.JTextFieldCZ txtCODIGO;
    private utilesGUIx.JTextFieldCZ txtNOMBRE;
    // End of variables declaration//GEN-END:variables



}
