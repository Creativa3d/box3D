/*
 * JPanelPermisos.java
 *
 * Created on 23-mar-2009, 12:59:50
 */

package utilesGUIx.plugin.seguridad.visual;

import utilesGUIx.Rectangulo;
import ListDatos.ECampoError;
import ListDatos.IListDatosFiltro;
import ListDatos.JListDatos;
import ListDatos.JListDatosFiltroConj;
import java.awt.Rectangle;
import javax.swing.JPanel;
import javax.swing.table.TableColumn;
import utilesGUIx.formsGenericos.*;
import utilesGUIx.formsGenericos.edicion.*;
import utilesGUIx.plugin.seguridad.*;
import utilesGUIx.*;

public class JPanelPermisos extends JPanelEdicionAbstract {
    private static final long serialVersionUID = 1L;

    private JTPlugInListaPermisos moPermisosFILTRADOS;
    private JTPlugInListaPermisos moPermisosTMP;
    private JTPlugInListaPermisos moPermisosBase;
    private IPanelControlador moPadre;
    private JTableModel moModel;
    private String msUsuarioGrupo;
    private boolean mbInicializado = false;

    /** Creates new form JPanelPermisos */
    public JPanelPermisos() {
        initComponents();
    }
    /**
     * @return the moPermisosTODOS
     */
    public JTPlugInListaPermisos getPermisosFILTRADOS() {
        return moPermisosFILTRADOS;
    }
    public void setDatos(JTPlugInListaPermisos poPermisosFILTRADOS, JTPlugInListaPermisos poPermisosBase, IPanelControlador poPadre,String psUsuarioGrupo) throws Exception {
        moPermisosFILTRADOS=poPermisosFILTRADOS;
        moPermisosTMP = new JTPlugInListaPermisos();
        moPermisosBase=poPermisosBase;
        msUsuarioGrupo = psUsuarioGrupo;
        moPadre = poPadre;
        mbInicializado = false;
    }
    public String getTitulo() {
        return "Permisos";
    }
    public void habilitarSegunEdicion() throws Exception {
        //nada
    }

    public void ponerTipoTextos() throws Exception {
        //nada 
    }

    public void rellenarPantalla() throws Exception {
        jListCZ1.borrarTodo();
        moPermisosBase.moList.ordenar(moPermisosBase.lPosiOBJETO);
        JListDatos loList = new JListDatos(null, "", new String[]{"", ""}, new int[]{0,0}, new int[]{0});
        if(moPermisosBase.moList.moveFirst()){
            String lsObjeto = "";
            do{
                if(!moPermisosBase.getOBJETO().getString().equals(lsObjeto)){
                    lsObjeto = moPermisosBase.getOBJETO().getString();
                    loList.addNew();
                    loList.getFields(0).setValue(lsObjeto);
                    loList.getFields(1).setValue(moPermisosBase.getCAPTIONOBJETO().getString());
                    loList.update(false);
                }
            }while(moPermisosBase.moList.moveNext());
        }
        loList.ordenar(1);
        if(loList.moveFirst()){
            do{
                jListCZ1.addLinea(
                        loList.getFields(1).getString(),
                        loList.getFields(0).getString());
            }while(loList.moveNext());
        }
        jListCZ1.validate();
        jScrollPane1.validate();

    }

    public void mostrarDatos() throws Exception {
        mbInicializado = false;
        //1º creamos la tabla temporal de permisos
        if(moPermisosBase.moList.moveFirst()){
            do{
                moPermisosTMP.moList.addNew();
                if(moPermisosFILTRADOS.moList.buscar(
                        JListDatos.mclTIgual,
                        new int[]{
                            moPermisosFILTRADOS.lPosiCODIGOGRUPOUSUARIO,
                            moPermisosFILTRADOS.lPosiOBJETO,
                            moPermisosFILTRADOS.lPosiACCION
                        },
                        new String[]{
                            msUsuarioGrupo,
                            moPermisosBase.getOBJETO().getString(),
                            moPermisosBase.getACCION().getString()
                        })){
                    moPermisosTMP.moList.getFields().cargar(
                            moPermisosFILTRADOS.moList.getFields().moFilaDatos()
                            );
                }else{
                    moPermisosTMP.moList.getFields().cargar(
                            moPermisosBase.moList.getFields().moFilaDatos()
                            );
                    moPermisosTMP.getACTIVOSN().setValue(true);
                    moPermisosTMP.getCODIGOGRUPOUSUARIO().setValue(msUsuarioGrupo);
                }
                moPermisosTMP.moList.update(false);
                moPermisosTMP.moList.moFila().setTipoModif(JListDatos.mclNada);
            }while(moPermisosBase.moList.moveNext());
        }

        moModel = new JTableModel(moPermisosTMP.moList);
        moModel.mbEditable=true;
        moModel.mbActualizarServidor=false;
        jTableCZ1.setModel(moModel);
        ponerAncho(moPermisosTMP.lPosiOBJETO, 0);
        ponerAncho(moPermisosTMP.lPosiCODIGOGRUPOUSUARIO, 0);
        ponerAncho(moPermisosTMP.lPosiCAPTIONOBJETO, 0);
        
        jListCZ1.setSelectedIndex(0);
        ponerPermisos(jListCZ1.getFilaActual().msCampo(0));
        mbInicializado = true;
    }
    public void ponerAncho(int plColumn, int plAncho){
        TableColumn loColumn = jTableCZ1.getColumnModel().getColumn(plColumn);
        ponerAncho(loColumn, plAncho);
    }
    private void ponerAncho(TableColumn loColumn, int plAncho){
        if(plAncho==0){
            //invisibles las 2 primeras columnas
            loColumn.setMinWidth(0);
            loColumn.setPreferredWidth(0);
            loColumn.setWidth(0);
            loColumn.setMaxWidth(0);
        }else{
            loColumn.setPreferredWidth(plAncho);
            loColumn.setWidth(plAncho);
        }

    }

    private void ponerPermisos(String psObjeto){
        moPermisosTMP.moList.getFiltro().Clear();
        moPermisosTMP.moList.filtrarNulo();
        moPermisosTMP.moList.getFiltro().addCondicion(
                JListDatosFiltroConj.mclAND,
                JListDatos.mclTIgual,
                new int[]{
                    moPermisosTMP.lPosiCODIGOGRUPOUSUARIO,
                    moPermisosTMP.lPosiOBJETO
                },
                new String[]{
                    msUsuarioGrupo,
                    psObjeto
                }
        );
        moPermisosTMP.moList.filtrar();
        moModel.fireTableDataChanged();
        jTableCZ1.updateUI();
        jTableCZ1.repaint();
        jTableCZ1.validate();
    }

    public void establecerDatos() throws Exception {
        try{
            moPermisosTMP.moList.filtrarNulo();
            if(moPermisosTMP.moList.moveFirst()){
                do{
    //                if(moPermisosTMP.moList.moFila().getTipoModif()!=JListDatos.mclNada){

                        if(!moPermisosFILTRADOS.moList.buscar(
                                JListDatos.mclTIgual,
                                new int[]{
                                    moPermisosTMP.lPosiCODIGOGRUPOUSUARIO,
                                    moPermisosTMP.lPosiOBJETO,
                                    moPermisosTMP.lPosiACCION
                                },
                                new String[]{
                                    msUsuarioGrupo,
                                    moPermisosTMP.getOBJETO().getString(),
                                    moPermisosTMP.getACCION().getString()
                                })){
                            moPermisosFILTRADOS.moList.addNew();
                        }

                        moPermisosFILTRADOS.moList.getFields().cargar(
                                moPermisosTMP.moList.moFila()
                                );
                        moPermisosFILTRADOS.moList.update(false);
    //                }
                }while(moPermisosTMP.moList.moveNext());
            }
        }finally{
            moPermisosTMP.moList.filtrar();
        }
    }

    public void aceptar() throws Exception {

    }

    public void cancelar() throws Exception {

    }


    public Rectangulo getTanano(){
        return new Rectangulo(0,0, 400, 150);
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jListCZ1 = new utilesGUIx.JListCZ();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableCZ1 = new utilesGUIx.JTableCZ();
        jPanel2 = new javax.swing.JPanel();
        btnAplicarTodos = new utilesGUIx.JButtonCZ();
        btnDesAplicarTodos = new utilesGUIx.JButtonCZ();

        setLayout(new java.awt.BorderLayout());

        jScrollPane1.setMinimumSize(new java.awt.Dimension(135, 130));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(135, 130));

        jListCZ1.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jListCZ1.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jListCZ1ValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jListCZ1);

        jSplitPane1.setLeftComponent(jScrollPane1);

        jPanel1.setLayout(new java.awt.BorderLayout());

        jTableCZ1.setModel(new javax.swing.table.DefaultTableModel(
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
        jTableCZ1.setRowHeight(24);
        jScrollPane2.setViewportView(jTableCZ1);

        jPanel1.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        btnAplicarTodos.setText(" Marcar todos");
        btnAplicarTodos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAplicarTodosActionPerformed(evt);
            }
        });
        jPanel2.add(btnAplicarTodos);

        btnDesAplicarTodos.setText("Quitar todos");
        btnDesAplicarTodos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDesAplicarTodosActionPerformed(evt);
            }
        });
        jPanel2.add(btnDesAplicarTodos);

        jPanel1.add(jPanel2, java.awt.BorderLayout.SOUTH);

        jSplitPane1.setRightComponent(jPanel1);

        add(jSplitPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void jListCZ1ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jListCZ1ValueChanged
        if(mbInicializado){
            ponerPermisos(jListCZ1.getFilaActual().msCampo(0));
        }

    }//GEN-LAST:event_jListCZ1ValueChanged

    private void btnDesAplicarTodosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDesAplicarTodosActionPerformed

        if(moPermisosTMP.moList.moveFirst()){
            do{
                try {
                    moPermisosTMP.getACTIVOSN().setValue(false);
                    moPermisosTMP.moList.update(false);
                } catch (ECampoError ex) {
                    ex.printStackTrace();
                }
            }while(moPermisosTMP.moList.moveNext());
        }
        moModel.fireTableDataChanged();
        jTableCZ1.updateUI();
        jTableCZ1.repaint();
        jTableCZ1.validate();

    }//GEN-LAST:event_btnDesAplicarTodosActionPerformed

    private void btnAplicarTodosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAplicarTodosActionPerformed
        if(moPermisosTMP.moList.moveFirst()){
            do{
                try {
                    moPermisosTMP.getACTIVOSN().setValue(true);
                    moPermisosTMP.moList.update(false);
                } catch (ECampoError ex) {
                    ex.printStackTrace();
                }
            }while(moPermisosTMP.moList.moveNext());
        }
        moModel.fireTableDataChanged();
        jTableCZ1.updateUI();
        jTableCZ1.repaint();
        jTableCZ1.validate();

    }//GEN-LAST:event_btnAplicarTodosActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private utilesGUIx.JButtonCZ btnAplicarTodos;
    private utilesGUIx.JButtonCZ btnDesAplicarTodos;
    private utilesGUIx.JListCZ jListCZ1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane1;
    private utilesGUIx.JTableCZ jTableCZ1;
    // End of variables declaration//GEN-END:variables




}
