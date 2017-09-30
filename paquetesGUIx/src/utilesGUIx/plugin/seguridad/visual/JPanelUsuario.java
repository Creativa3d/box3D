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

public class JPanelUsuario extends JPanelGENERALBASE {
    private static final long serialVersionUID = 1L;

    private JTPlugInUsuarios moUsuarios;
    private JTPlugInUsuariosGrupos moUsuariosGrupos;
    private JTPlugInGrupos moGrupos;
    private IPlugInSeguridadRW moRW;
    private JTPlugInListaPermisos moListaPermisosBase;
    private JCheckBoxCZ[] moDocumentos=new JCheckBoxCZ[0];

    /** Creates new form JPanelUsuario */
    public JPanelUsuario() {
        initComponents();
    }
    public JSTabla getTabla() {
        return moUsuarios;
    }
    public void setDatos(JTPlugInUsuarios poUsuarios, IPlugInSeguridadRW poRW, IPanelControlador poPadre, JTPlugInListaPermisos poListaPermisosBase) throws Exception {
        moListaPermisosBase = poListaPermisosBase;
        moUsuarios = poUsuarios;
        moGrupos = poRW.getGrupos();
        moUsuariosGrupos = poRW.getUsuariosGrupos();
        moPadre = poPadre;
        moRW = poRW;
    }
    public void setPantallasConCodigo(boolean pbPantallasConCodigo) {
        lblCODIGO.setVisible(pbPantallasConCodigo);
        txtCODIGO.setVisible(pbPantallasConCodigo);
    }
    public String getTitulo() {
        if(moUsuarios.moList.getModoTabla() == JListDatos.mclNuevo) {
            return "Usuario [Nuevo]" ;
        }else{
            return "Usuario " + moUsuarios.getCODIGOUSUARIO().getString() + "-" + moUsuarios.getNOMBRE().getString();
        }
    }

    public void rellenarPantalla() throws Exception {
        txtCODIGO.setField(moUsuarios.getCODIGOUSUARIO());
        lblCODIGO.setField(moUsuarios.getCODIGOUSUARIO());
        txtNOMBRE.setField(moUsuarios.getNOMBRE());
        lblNOMBRE.setField(moUsuarios.getNOMBRE());
        txtNOMBRECOMPLETO.setField(moUsuarios.getNOMBRECOMPLETO());
        lblNOMBRECOMPLETO.setField(moUsuarios.getNOMBRECOMPLETO());
        lblPASSWORD.setField(moUsuarios.getPASSWORD());
        chkActivoSN.setField(moUsuarios.getACTIVOSN());

//primero borramos los componenetes del panel de documentos
        for(int i = 0; i < moDocumentos.length;i++ ){
            jPanelDocumentos.remove(moDocumentos[i]);
        }
        if(moGrupos.moveFirst()){
            moDocumentos = new JCheckBoxCZ[moGrupos.moList.size()];
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
                            moGrupos.getCODIGOGRUPO().getString()+"-"+
                            moGrupos.getNOMBRE().getString());
                    i++;

            }while(moGrupos.moveNext());

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
        if(moUsuarios.moList.getModoTabla() != JListDatos.mclNuevo) {
            txtCODIGO.setEnabled(false);
        }
    }
    private void marcarDoc(String psCodigo){
        for(int i = 0; i < moDocumentos.length;i++ ){
            if(JFilaDatosDefecto.gfsExtraerCampo(moDocumentos[i].getText(), 0, '-').compareTo(psCodigo)==0){
                moDocumentos[i].setSelected(true);
            }
        }
    }
    public void ponerTipoTextos() throws Exception {
    }
    public void mostrarDatos() throws Exception {
//        mbPermisos=false;
        txtPASSWOD.setText(moUsuarios.getPASSWORD().getString());

        moUsuariosGrupos.moList.getFiltro().Clear();
        moUsuariosGrupos.moList.filtrarNulo();
        moUsuariosGrupos.moList.getFiltro().addCondicion(
                JListDatosFiltroConj.mclAND,
                JListDatos.mclTIgual,
                moUsuariosGrupos.lPosiCODIGOUSUARIO,
                moUsuarios.getCODIGOUSUARIO().getString()
                );
        moUsuariosGrupos.moList.filtrar();
        if(moUsuariosGrupos.moList.moveFirst()){
            do{
                marcarDoc(moUsuariosGrupos.getCODIGOGRUPO().getString());
            }while(moUsuariosGrupos.moList.moveNext());
        }
    }
    public void establecerDatos() throws Exception {
        moUsuarios.getPASSWORD().setValue(txtPASSWOD.getText());
        if(txtCODIGO.getText().equals("")){
            int lMax = 0;
            JTPlugInUsuarios loG = moRW.getUsuarios();
            if(loG.moveFirst()){
                do{
                    if(loG.getCODIGOUSUARIO().getInteger()>=lMax){
                        lMax=loG.getCODIGOUSUARIO().getInteger();
                    }
                }while(loG.moveNext());
            }
            moUsuarios.getCODIGOUSUARIO().setValue(lMax+1);
        }
        moUsuariosGrupos.moList.getFiltro().Clear();
        moUsuariosGrupos.moList.filtrarNulo();
        moUsuariosGrupos.moList.getFiltro().addCondicion(
                JListDatosFiltroConj.mclAND,
                JListDatos.mclTIgual,
                moUsuariosGrupos.lPosiCODIGOUSUARIO,
                moUsuarios.getCODIGOUSUARIO().getString()
                );
        moUsuariosGrupos.moList.filtrar();
        while(moUsuariosGrupos.moList.moveFirst()){
            moUsuariosGrupos.moList.borrar(false);
        }

        for(int i = 0; i < moDocumentos.length;i++ ){
            if(moDocumentos[i].isSelected()){
                moUsuariosGrupos.moList.addNew();

                moUsuariosGrupos.getCODIGOGRUPO().setValue(
                        JFilaDatosDefecto.gfsExtraerCampo(moDocumentos[i].getText(), 0, '-')
                        );
                moUsuariosGrupos.getCODIGOUSUARIO().setValue(
                        moUsuarios.getCODIGOUSUARIO().getString()
                        );

                moUsuariosGrupos.moList.update(false);
            }
        }
        moUsuariosGrupos.moList.getFiltro().Clear();
        moUsuariosGrupos.moList.filtrarNulo();

//        if(mbPermisos){
//            jPanelPermisos1.establecerDatos();
//        }

        moUsuarios.validarCampos();
    }

    public void aceptar() throws Exception {
        int lModo = moUsuarios.moList.getModoTabla();
        IResultado loResult=moUsuarios.moList.update(false);
        if(!loResult.getBien()){
            throw new Exception(loResult.getMensaje());
        }else{
           if(lModo== JListDatos.mclNada){
                lModo = JListDatos.mclEditar;
            }
            moUsuarios.moList.moFila().setTipoModif(lModo);
            moRW.guardarUsuario(moUsuarios.moList.moFila());
            moUsuarios.moList.cargarFields();
            if(moUsuariosGrupos.moveFirst()){
                do{
                    if(moUsuariosGrupos.getCODIGOUSUARIO().isVacio()){
                        moUsuariosGrupos.getCODIGOUSUARIO().setValue(moUsuarios.getCODIGOUSUARIO().getString());
                        moUsuariosGrupos.update(false);
                    }
                }while(moUsuariosGrupos.moveNext());
            }

            moRW.guardareUsuariosGrupos(moUsuariosGrupos);

//            if(mbPermisos){
//                moRW.guardarListaPermisosUsuario(jPanelPermisos1.getPermisosTODOS());
//            }
        }
        actualizarPadre(lModo);
        JPanelEdicion.mostrarDatosBD(this);
        mostrarDatos();
    }

    public void cancelar() throws Exception {
        moUsuarios.moList.cancel();
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
        lblNOMBRECOMPLETO = new utilesGUIx.JLabelCZ();
        txtNOMBRECOMPLETO = new utilesGUIx.JTextFieldCZ();
        lblPASSWORD = new utilesGUIx.JLabelCZ();
        txtPASSWOD = new javax.swing.JPasswordField();
        chkActivoSN = new utilesGUIx.JCheckBoxCZ();
        jLabelCZ1 = new utilesGUIx.JLabelCZ();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanelDocumentos = new javax.swing.JPanel();

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

        lblNOMBRE.setText("Login");
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

        lblNOMBRECOMPLETO.setText("Nombre completo");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanel1.add(lblNOMBRECOMPLETO, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanel1.add(txtNOMBRECOMPLETO, gridBagConstraints);

        lblPASSWORD.setText("Contraseña");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanel1.add(lblPASSWORD, gridBagConstraints);

        txtPASSWOD.setText("jPasswordField1");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanel1.add(txtPASSWOD, gridBagConstraints);

        chkActivoSN.setText("¿Es Activo?");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanel1.add(chkActivoSN, gridBagConstraints);

        jLabelCZ1.setText("Grupos a los que pertenece");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanel1.add(jLabelCZ1, gridBagConstraints);

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

        add(jTabbedPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void jTabbedPane1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTabbedPane1StateChanged
        try{
//            if(jTabbedPane1.getSelectedIndex()>0){
//                mbPermisos = true;
//                jPanelPermisos1.setDatos(
//                    moRW.getListaPermisosUsuario(),
//                    moListaPermisosBase,
//                    moPadre,
//                    txtCODIGO.getText()
//                    );
//                jPanelPermisos1.habilitarSegunEdicion();
//                jPanelPermisos1.rellenarPantalla();
//                jPanelPermisos1.ponerTipoTextos();
//                jPanelPermisos1.mostrarDatos();
//            }

        }catch(Throwable e){
            utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this, e, getClass().getName());
        }

    }//GEN-LAST:event_jTabbedPane1StateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private utilesGUIx.JCheckBoxCZ chkActivoSN;
    private utilesGUIx.JLabelCZ jLabelCZ1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelDocumentos;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private utilesGUIx.JLabelCZ lblCODIGO;
    private utilesGUIx.JLabelCZ lblNOMBRE;
    private utilesGUIx.JLabelCZ lblNOMBRECOMPLETO;
    private utilesGUIx.JLabelCZ lblPASSWORD;
    private utilesGUIx.JTextFieldCZ txtCODIGO;
    private utilesGUIx.JTextFieldCZ txtNOMBRE;
    private utilesGUIx.JTextFieldCZ txtNOMBRECOMPLETO;
    private javax.swing.JPasswordField txtPASSWOD;
    // End of variables declaration//GEN-END:variables




}
