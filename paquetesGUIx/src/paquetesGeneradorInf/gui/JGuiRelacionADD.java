package paquetesGeneradorInf.gui;

import ListDatos.JListDatos;
import ListDatos.JSelect;
import ListDatos.JSelectCampo;
import ListDatos.JSelectUnionTablas;
import ListDatos.estructuraBD.JFieldDef;
import ListDatos.estructuraBD.JTableDef;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.SwingUtilities;
import utiles.IListaElementos;
import utiles.JDepuracion;
import utiles.JListaElementos;
import utilesGUIx.JComboBoxCZ;
import utilesGUIx.JTableModel;

public class JGuiRelacionADD extends javax.swing.JPanel {
    private JGuiSelectTablas moTablas;
    private JSelectUnionTablas moFromUnion;

    private DefaultComboBoxModel moComboBoxModelCampos1 = new DefaultComboBoxModel();
    private DefaultComboBoxModel moComboBoxModelCampos2 = new DefaultComboBoxModel();
    private DefaultCellEditor moEditorCampos1;
    private DefaultCellEditor moEditorCampos2;
    private JComboBox moComboCampos1 = new JComboBoxCZ();
    private JComboBox moComboCampos2 = new JComboBoxCZ();
    private JListDatos moListTable1;
    private JListDatos moListTable2;
    private String msNombreTabla1;
    private boolean mbADD;

    /** Creates new form JGuiRelacionADD */
    public JGuiRelacionADD(JGuiSelectTablas poTablas) {
        initComponents();
        moTablas = poTablas;
        //asigamos el modelo
        moListTable1 = getListDatos();
        moListTable2 = getListDatos();
        JTableModel loModel = new JTableModel(moListTable1);
        loModel.mbEditable=true;
        jTableCZ1.setModel(loModel);
        loModel = new JTableModel(moListTable2);
        loModel.mbEditable=true;
        jTableCZ2.setModel(loModel);

        //creamos los reditores para cada tabla
        moEditorCampos1 = new DefaultCellEditor(moComboCampos1) {
            public Object getCellEditorValue() {
                return moComboCampos1.getSelectedItem();
            }
        };
        moEditorCampos2 = new DefaultCellEditor(moComboCampos2) {
            public Object getCellEditorValue() {
                return moComboCampos2.getSelectedItem();
            }
        };
        moComboCampos1.setModel(moComboBoxModelCampos1);
        moComboCampos2.setModel(moComboBoxModelCampos2);

        jTableCZ1.setDefaultEditor(String.class, moEditorCampos1);
        jTableCZ2.setDefaultEditor(String.class, moEditorCampos2);

    }
    private JListDatos getListDatos(){
        JListDatos loListTable1 = new JListDatos(
                null, "",
                new String[1],
                new int[1],
                new int[0]);
        loListTable1.getFields(0).setCaption("Campos");
        for (int i = 0; i < 20; i++) {
            loListTable1.addNew();
            loListTable1.update(false);
        }

        return loListTable1;

    }

    private JGuiConsultaDatos getDatos() {
        return moTablas.getConsulta().getDatos();
    }

    public void setUnion(JSelectUnionTablas poUnion, String psNombreTabla1, boolean pbADD) throws Exception{
        moFromUnion = poUnion;
        msNombreTabla1 = psNombreTabla1;
        mbADD=pbADD;
        mostrar();
    }

    private void mostrar() throws Exception {
        if(mbADD) {
            btnBorrar.setVisible(false);
        }
        rellenarModelCampos(moComboBoxModelCampos1, msNombreTabla1);
        lblTabla1.setText(moFromUnion.getTablaPrefijoCampos1());
        rellenarModelCampos(moComboBoxModelCampos2,moFromUnion.getTabla2());
        if(moFromUnion.getTabla2Alias()!=null && !moFromUnion.getTabla2Alias().equals("")){
            lblTabla2.setText(moFromUnion.getTabla2Alias());
        }else{
            lblTabla2.setText(moFromUnion.getTabla2());
        }
        switch(moFromUnion.getTipo()){
            case JSelectUnionTablas.mclInner:
                optInner.setSelected(true);
                break;
            case JSelectUnionTablas.mclLeft:
                optLeft.setSelected(true);
                break;
            case JSelectUnionTablas.mclRight:
                optRight.setSelected(true);
                break;
        }
        for(int i = 0 ; i < moFromUnion.getCampos1().length; i++){
            moListTable1.setIndex(i);
            if(moFromUnion.getCampos1()[i]!=null && !moFromUnion.getCampos1()[i].equals("")){
                moListTable1.getFields(0).setValue(
                    getCampoFormat(msNombreTabla1, moFromUnion.getCampos1()[i], "")
                    );
            }
            moListTable1.update(false);
            moListTable2.setIndex(i);
            if(moFromUnion.getCampos2()[i]!=null && !moFromUnion.getCampos2()[i].equals("")){
                moListTable2.getFields(0).setValue(
                    getCampoFormat(moFromUnion.getTabla2(), moFromUnion.getCampos2()[i], "")
                    );
            }
            moListTable2.update(false);

        }
        
    }
    private void guardar() throws Exception {
        String[] lasCampos1 = getListaCampos(moListTable1);
        String[] lasCampos2 = getListaCampos(moListTable2);
        if(lasCampos1.length<=0 && lasCampos2.length<=0){
            throw new Exception("Debes añadir algún campo a la relación");
        }
        if(lasCampos1.length!=lasCampos2.length){
            throw new Exception("Hay distinto número de campos en la relación");
        }
        moFromUnion.setCampos1(lasCampos1);
        moFromUnion.setCampos2(lasCampos2);
        if(optInner.isSelected()){
            moFromUnion.setTipo(moFromUnion.mclInner);
        }
        if(optLeft.isSelected()){
            moFromUnion.setTipo(moFromUnion.mclLeft);
        }
        if(optRight.isSelected()){
            moFromUnion.setTipo(moFromUnion.mclRight);
        }
    }

    private String[] getListaCampos(JListDatos poList) throws Exception {
        JListaElementos loLista = new JListaElementos();
        for(int i = 0 ; i < poList.size(); i++){
            poList.setIndex(i);
            if (!poList.getFields(0).isVacio() &&
                (!poList.getFields(0).getString().equals(JSelect.mcsTodosCampos)
                )) {
                loLista.add(
                        getCampoDeFormatNombre(poList.getFields(0).getString())
                        );

            }
        }
        String[] lasCadenas = new String[loLista.size()];
        for(int i = 0 ; i < lasCadenas.length; i++){
            lasCadenas[i] = (String) loLista.get(i);
        }

        return lasCadenas;

    }

    private void rellenarModelCampos(DefaultComboBoxModel poModelCampos, String psTabla) throws Exception {
        poModelCampos.removeAllElements();
        JTableDef loTabla =  moTablas.getConsulta().getDatos().getServer().getTableDefs().get(psTabla);
        if (loTabla != null) {
            poModelCampos.addElement("");
            for (int i = 0; i < loTabla.getCampos().size(); i++) {
                JFieldDef loCampo = loTabla.getCampos().get(i);
                poModelCampos.addElement(
                        getCampoFormat(
                            loTabla.getNombre(),
                            loCampo.getNombre(),
                            "")
                        );
            }
        }
    }
    String getCampoFormat(String psNombreTabla, String psNombre, String psAlias) {
        String lsResult = "";
        if (psNombre.equals(JSelect.mcsTodosCampos)) {
            lsResult = psNombre;
        } else {
            if (getDatos().isUsarNombresFisicos()) {
                if (psAlias != null && !psAlias.equalsIgnoreCase("")) {
                    lsResult = psAlias + ":[" + psNombre + "]";
                } else {
                    lsResult = psNombre;
                }
            } else {
                lsResult = getDatos().getTextosForms().getCaption(psNombreTabla, psNombre) + ":[" + psNombre + "]";
            }
        }
        return lsResult;
    }
    String getCampoDeFormatNombre(String psValor) {
        String lsResult = "";
        int lPosi = psValor.indexOf(":");
        if (lPosi > 0) {
            lsResult = psValor.substring(lPosi + 1);
        } else {
            lsResult = psValor;
        }
        return lsResult.replace("[", "").replace("]", "");
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        lblTabla1 = new utilesGUIx.JLabelCZ();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableCZ1 = new utilesGUIx.JTableCZ();
        jPanel2 = new javax.swing.JPanel();
        lblTabla2 = new utilesGUIx.JLabelCZ();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableCZ2 = new utilesGUIx.JTableCZ();
        jPanel3 = new javax.swing.JPanel();
        optInner = new javax.swing.JRadioButton();
        optLeft = new javax.swing.JRadioButton();
        optRight = new javax.swing.JRadioButton();
        jPanel4 = new javax.swing.JPanel();
        btnAceptar = new utilesGUIx.JButtonCZ();
        btnCancelar = new utilesGUIx.JButtonCZ();
        btnBorrar = new utilesGUIx.JButtonCZ();

        setLayout(new java.awt.GridBagLayout());

        jPanel1.setLayout(new java.awt.BorderLayout(2, 2));

        lblTabla1.setText(" ");
        jPanel1.add(lblTabla1, java.awt.BorderLayout.NORTH);

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
        jScrollPane1.setViewportView(jTableCZ1);

        jPanel1.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        add(jPanel1, gridBagConstraints);

        jPanel2.setLayout(new java.awt.BorderLayout(2, 2));

        lblTabla2.setText(" ");
        jPanel2.add(lblTabla2, java.awt.BorderLayout.NORTH);

        jTableCZ2.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(jTableCZ2);

        jPanel2.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        add(jPanel2, gridBagConstraints);

        jPanel3.setLayout(new java.awt.GridLayout(3, 1, 2, 2));

        buttonGroup1.add(optInner);
        optInner.setText("<html>Incluir solo las filas donde los campos combinados de ambas tablas sean iguales</html>");
        jPanel3.add(optInner);

        buttonGroup1.add(optLeft);
        optLeft.setText("<html>Incluir TODOS los registros de TABLA1 y SOLO aquellos registros de TABLA2 cuyos campos combinados sean iguales</html>");
        jPanel3.add(optLeft);

        buttonGroup1.add(optRight);
        optRight.setText("<html>Incluir TODOS los registros de TABLA2 y SOLO aquellos registros de TABLA1 cuyos campos combinados sean iguales</html>");
        jPanel3.add(optRight);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        add(jPanel3, gridBagConstraints);

        btnAceptar.setText("Aceptar");
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });
        jPanel4.add(btnAceptar);

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        jPanel4.add(btnCancelar);

        btnBorrar.setText("Borrar");
        btnBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarActionPerformed(evt);
            }
        });
        jPanel4.add(btnBorrar);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        add(jPanel4, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        try{
            guardar();
            if(mbADD){
                moTablas.addRelacionSelect(moFromUnion);
            }
            try{
                moTablas.getConsulta().getDatos().getMostrarPantalla().cerrarForm(this);
            }catch(Exception e){
                SwingUtilities.getWindowAncestor(this).dispose();
            }
        }catch(Exception e){
            JDepuracion.anadirTexto(getClass().getName(), e);
            utilesGUIx.msgbox.JMsgBox.mensajeError(this, e);
        }

    }//GEN-LAST:event_btnAceptarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed

        try{
            moTablas.getConsulta().getDatos().getMostrarPantalla().cerrarForm(this);
        }catch(Exception e){
            SwingUtilities.getWindowAncestor(this).dispose();
        }

    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarActionPerformed
        try{
            if(!mbADD){
                moTablas.borrarRelacion(moFromUnion);
            }
            try{
                moTablas.getConsulta().getDatos().getMostrarPantalla().cerrarForm(this);
            }catch(Exception e){
                SwingUtilities.getWindowAncestor(this).dispose();
            }
        }catch(Exception e){
            JDepuracion.anadirTexto(getClass().getName(), e);
            utilesGUIx.msgbox.JMsgBox.mensajeError(this, e);
        }
}//GEN-LAST:event_btnBorrarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private utilesGUIx.JButtonCZ btnAceptar;
    private utilesGUIx.JButtonCZ btnBorrar;
    private utilesGUIx.JButtonCZ btnCancelar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private utilesGUIx.JTableCZ jTableCZ1;
    private utilesGUIx.JTableCZ jTableCZ2;
    private utilesGUIx.JLabelCZ lblTabla1;
    private utilesGUIx.JLabelCZ lblTabla2;
    private javax.swing.JRadioButton optInner;
    private javax.swing.JRadioButton optLeft;
    private javax.swing.JRadioButton optRight;
    // End of variables declaration//GEN-END:variables


}
class JGuiRelaccionADDModel extends JTableModel {


    /**
     * Contructor
     * @param poList Datos
     */
    public JGuiRelaccionADDModel() {
        super(null);
        mbEditable = true;
        mbActualizarServidor = false;
    }

    public int getColumnCount() {
        return moList.getFields().count();
    }
}

