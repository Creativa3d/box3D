package paquetesGeneradorInf.gui;

import ListDatos.JSelect;
import ListDatos.JSelectCampo;
import ListDatos.JSelectUnionTablas;
import ListDatos.estructuraBD.JFieldDef;
import ListDatos.estructuraBD.JTableDef;
import java.awt.Component;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import paquetesGeneradorInf.gui.util.JUtiles;
import utiles.JDepuracion;
import utilesGUI.tiposTextos.JTipoTextoEstandar;
import utilesGUIx.JComboBoxCZ;
import utilesGUIx.JTableCZ;
import utilesGUIx.JTableCZEditor;
import utilesGUIx.JTextFieldCZ;


public class JGuiSelectCampos extends javax.swing.JPanel implements TableModelListener {

    private static final String mcsAscendente = "Ascendente";
//    private static final String mcsDescendente = "Descendente";
    private static final String mcsAgrupadoPor = "Agrupado por";
    private static final String mcsAVG = "Promedio";
    private static final String mcsCOUNT = "Cuenta";
    private static final String mcsMAX = "Máximo";
    private static final String mcsMIN = "Mínimo";
    private static final String mcsSUM = "Suma";
    private static final String mcsO = "O";
    private static final String mcsY = "Y";

    
    private JGuiConsulta moConsulta;
    private JTableCZConsulta jTableSelectCampos;
    private DefaultComboBoxModel moComboBoxModelCampos = new DefaultComboBoxModel();
    private DefaultComboBoxModel moComboBoxModelTablas = new DefaultComboBoxModel();
    private DefaultComboBoxModel moComboBoxModelOrden = new DefaultComboBoxModel();
    private DefaultComboBoxModel moComboBoxModelAgrupacion = new DefaultComboBoxModel();
    private DefaultComboBoxModel moComboBoxModelUnionCondiciones = new DefaultComboBoxModel();

    private JGuiSelectCamposTableModel moTableModelCampos;

    private JGuiSelectCamposFiltros moSelectFiltro;

    /** Creates new form JGuiCampos */
    public JGuiSelectCampos() {
        initComponents();

        moComboBoxModelOrden.addElement("");
        moComboBoxModelOrden.addElement(mcsAscendente);
//        moComboBoxModelOrden.addElement(mcsDescendente);
        moComboBoxModelUnionCondiciones.addElement(mcsY);
        moComboBoxModelUnionCondiciones.addElement(mcsO);


        moComboBoxModelAgrupacion.addElement("");
        moComboBoxModelAgrupacion.addElement(mcsAgrupadoPor);
        moComboBoxModelAgrupacion.addElement(mcsAVG);
        moComboBoxModelAgrupacion.addElement(mcsCOUNT);
        moComboBoxModelAgrupacion.addElement(mcsMAX);
        moComboBoxModelAgrupacion.addElement(mcsMIN);
        moComboBoxModelAgrupacion.addElement(mcsSUM);

        jTableSelectCampos = new JTableCZConsulta(this);
        moTableModelCampos = new JGuiSelectCamposTableModel(this);
        moSelectFiltro = new JGuiSelectCamposFiltros(moTableModelCampos, this);

        jTableSelectCampos.setModel(moTableModelCampos);
        jTableSelectCampos.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTableSelectCampos.setColumnSelectionAllowed(true);
        jTableSelectCampos.setRowSelectionAllowed(true);

        jTableSelectCampos.getModel().addTableModelListener(this);

        jScrollPane1.setViewportView(jTableSelectCampos);

        JUtiles.ponerAncho(
                jTableSelectCampos.getColumnModel().getColumn(0),
                80);
        for (int i = 1; i < jTableSelectCampos.getColumnModel().getColumnCount(); i++) {
            JUtiles.ponerAncho(
                    jTableSelectCampos.getColumnModel().getColumn(i),
                    120);

        }
        jTableSelectCampos.setRowHeight(20);
    
    }

    JGuiConsultaDatos getDatos() {
        return moConsulta.getDatos();
    }
    
    JSelect getSelect(){
        return moConsulta.getSelect();
    }



    void guardar() throws Exception {
        if(jTableSelectCampos.isEditing()){
            jTableSelectCampos.getCellEditor().stopCellEditing();
        }
        JSelect loSelect = getSelect();
        loSelect.getCampos().clear();
        loSelect.getCamposGroup().clear();
        loSelect.getCamposOrder().clear();
        loSelect.getWhere().Clear();

        for (int i = 0; i < moTableModelCampos.moList.getFields().size(); i++) {
            JSelectCampo loCampo = getSelectCampo(i, false);
            if (loCampo != null) {
                addCampoSelect(loSelect, i, loCampo);
            } else {
                if (moTableModelCampos.moList.getFields(i).getString().equals(JSelect.mcsTodosCampos)) {
                    loCampo = getSelectCampo(i, true);
                    addCampoSelect(loSelect, i, loCampo);
                //desglosa el * en todos sus campos
//                    JTableDef loTabla = getTablaDeFormat(loCampo.msTabla);
//                    for(int ii = 0 ; ii < loTabla.getCampos().size(); ii++){
//                        JFieldDef loCampoF = loTabla.getCampos().get(ii);
//                        loCampo.msNombre = loCampoF.getNombre();
//                        loCampo.msCaption = loTabla.getNombre() + "_" + loCampoF.getNombre();
//                        addCampoSelect(loSelect, i, loCampo);
//                    }
                }
            }
        }
        moSelectFiltro.guardarFiltro(loSelect);
//        System.out.println(loSelect.msSQL(new JSelectMotorNeutro(true)));
    }

    void setAgrupado(boolean pbAgrupado) throws Exception {
        guardar();
        getSelect().setAgrupado(pbAgrupado);
        mostrar();
    }

    private void addCampoSelect(JSelect poSelect, int plColumna, JSelectCampo poCampo) throws CloneNotSupportedException {
        moTableModelCampos.moList.setIndex(JGuiSelectCamposTableModel.lPosiMostrar);
        //si se muestra se añade al select y a la agrupacion si procede
        if (moTableModelCampos.moList.getFields(plColumna).getBoolean()) {
            poSelect.addCampo((JSelectCampo) poCampo.clone());

            if (jTableSelectCampos.getRowHeight(JGuiSelectCamposTableModel.lPosiAgrupacion) > 1 &&
                    !poCampo.getNombre().equals(JSelect.mcsTodosCampos) &&
                    poCampo.getFuncion() == JSelectCampo.mclFuncionNada) {
                poSelect.addCampoGroup((JSelectCampo) poCampo.clone());
            }
        }
        moTableModelCampos.moList.setIndex(JGuiSelectCamposTableModel.lPosiOrden);
        if (!moTableModelCampos.moList.getFields(plColumna).isVacio() &&
                !poCampo.getNombre().equals(JSelect.mcsTodosCampos)) {
            if (moTableModelCampos.moList.getFields(plColumna).getString().equals(mcsAscendente)) {
                if (!poSelect.isCampoOrdenado(poCampo.getTabla(), poCampo.getNombre())) {
                    poSelect.addCampoOrder((JSelectCampo) poCampo.clone());
                }
            }
        }
    }


    private JSelectCampo getSelectCampo(int plColumn, boolean pbIncluirTodos) throws Exception {
        JSelectCampo loCampo = null;
        moTableModelCampos.moList.setIndex(JGuiSelectCamposTableModel.lPosiCampos);
        if (!moTableModelCampos.moList.getFields(plColumn).isVacio() &&
            (!moTableModelCampos.moList.getFields(plColumn).getString().equals(JSelect.mcsTodosCampos) ||
             pbIncluirTodos)) {
            loCampo = new JSelectCampo(
                    getCampoDeFormatNombre(moTableModelCampos.moList.getFields(plColumn).getString()));
            loCampo.setCaption(getCampoDeFormatCaption(moTableModelCampos.moList.getFields(plColumn).getString()));
            moTableModelCampos.moList.setIndex(JGuiSelectCamposTableModel.lPosiTablas);
            if (!moTableModelCampos.moList.getFields(plColumn).isVacio()) {
                loCampo.setTabla(getTablaOAliasDeFormat(moTableModelCampos.moList.getFields(plColumn).getString()));
            }
            moTableModelCampos.moList.setIndex(JGuiSelectCamposTableModel.lPosiAgrupacion);
            if (!moTableModelCampos.moList.getFields(plColumn).isVacio()) {
                loCampo.setFuncion(getAgrupacionDePantalla(moTableModelCampos.moList.getFields(plColumn).getString()));
            }
        }

        return loCampo;

    }

    private void mostrarCampo(JSelectCampo loCampo, int plColumm) throws Exception{
        //ponemos el campo
        moTableModelCampos.moList.setIndex(JGuiSelectCamposTableModel.lPosiCampos);
        moTableModelCampos.moList.getFields(plColumm).setValue(
                getCampoFormat(loCampo.getTabla(), loCampo.getNombre(), loCampo.getCaption()));
        moTableModelCampos.moList.update(false);

        //ponemos la tabla
        moTableModelCampos.moList.setIndex(JGuiSelectCamposTableModel.lPosiTablas);
        moTableModelCampos.moList.getFields(plColumm).setValue(
                getTablaFormat(loCampo.getTabla(), ""));
        moTableModelCampos.moList.update(false);

        //en funcion de si es agrupado o no se pone la funcion de agrupacion
        if (getSelect().isAgrupado()) {
            if (loCampo.getFuncion() == loCampo.mclFuncionNada) {
                moTableModelCampos.moList.setIndex(JGuiSelectCamposTableModel.lPosiAgrupacion);
                moTableModelCampos.moList.getFields(plColumm).setValue(mcsAgrupadoPor);
                moTableModelCampos.moList.update(false);
            } else {
                moTableModelCampos.moList.setIndex(JGuiSelectCamposTableModel.lPosiAgrupacion);
                moTableModelCampos.moList.getFields(plColumm).setValue(
                        getAgrupacion(loCampo.getFuncion()));
                moTableModelCampos.moList.update(false);
            }
        } else {
            moTableModelCampos.moList.setIndex(JGuiSelectCamposTableModel.lPosiAgrupacion);
            moTableModelCampos.moList.getFields(plColumm).setValue("");
            moTableModelCampos.moList.update(false);
        }
        //lo campos del JSelect siempre se muestran
        moTableModelCampos.moList.setIndex(JGuiSelectCamposTableModel.lPosiMostrar);
        moTableModelCampos.moList.getFields(plColumm).setValue(true);
        moTableModelCampos.moList.update(false);

        //si es campo ordenado se marca
        if (getSelect().isCampoOrdenado(loCampo.getTabla(), loCampo.getNombre())) {
            moTableModelCampos.moList.setIndex(JGuiSelectCamposTableModel.lPosiOrden);
            moTableModelCampos.moList.getFields(plColumm).setValue(mcsAscendente);
            moTableModelCampos.moList.update(false);
        }
    }
    void mostrar() throws Exception {
        //1º lo borramos todo
        if(moTableModelCampos.moList.moveFirst()){
            do{
                for(int i = 0; i < moTableModelCampos.moList.getFields().size(); i++){
                    moTableModelCampos.moList.getFields(i).setValue("");
                }
                moTableModelCampos.moList.update(false);
            }while(moTableModelCampos.moList.moveNext());
        }
        recargarComboTablas();
        for (int i = 0; i < getSelect().getCampos().size(); i++) {
            JSelectCampo loCampo = (JSelectCampo) getSelect().getCampos().get(i);
            mostrarCampo(loCampo, i);
        }
        //en funcion de si es agrupado o no se muestra la fila de agrupacion
        if (getSelect().isAgrupado()) {
            jTableSelectCampos.setRowHeight(
                    JGuiSelectCamposTableModel.lPosiAgrupacion,
                    jTableSelectCampos.getRowHeight());
        } else {
            jTableSelectCampos.setRowHeight(
                    JGuiSelectCamposTableModel.lPosiAgrupacion,
                    1);
        }
        //CONDICION
        moSelectFiltro.mostrarFiltro(getSelect());
        jTableSelectCampos.repaint();
    }



    private String getAgrupacion(int plFuncion) {
        String lsResult = null;
        switch (plFuncion) {
            case JSelectCampo.mclFuncionAvg:
                lsResult = mcsAVG;
                break;
            case JSelectCampo.mclFuncionCount:
                lsResult = mcsCOUNT;
                break;
            case JSelectCampo.mclFuncionMax:
                lsResult = mcsMAX;
                break;
            case JSelectCampo.mclFuncionMin:
                lsResult = mcsMIN;
                break;
            case JSelectCampo.mclFuncionSum:
                lsResult = mcsSUM;
                break;
        }
        return lsResult;
    }

    private int getAgrupacionDePantalla(String psValor) {
        int lResult = JSelectCampo.mclFuncionNada;

        if (psValor.equals(mcsAVG)) {
            lResult = JSelectCampo.mclFuncionAvg;
        }
        if (psValor.equals(mcsCOUNT)) {
            lResult = JSelectCampo.mclFuncionCount;
        }
        if (psValor.equals(mcsMAX)) {
            lResult = JSelectCampo.mclFuncionMax;
        }
        if (psValor.equals(mcsMIN)) {
            lResult = JSelectCampo.mclFuncionMin;
        }
        if (psValor.equals(mcsSUM)) {
            lResult = JSelectCampo.mclFuncionSum;
        }
        return lResult;
    }

    private void rellenarModelCampos(int plColumna) throws Exception {
        moComboBoxModelCampos.removeAllElements();
        if (jTableSelectCampos != null) {
            int lColumn = -1;
            if (plColumna >= 0) {
                lColumn = plColumna;
            } else {
                if (jTableSelectCampos.getSelectedColumn() >= 0) {
                    lColumn = jTableSelectCampos.getSelectedColumn();
                }
                if (jTableSelectCampos.getEditingColumn() >= 0) {
                    lColumn = jTableSelectCampos.getEditingColumn();
                }
            }
            if (lColumn >= 0) {
                String lsValor = (String) jTableSelectCampos.getModel().getValueAt(1, lColumn);

                JTableDef loTabla = getTablaDeFormat(lsValor);
                if (loTabla != null) {
                    moComboBoxModelCampos.addElement("");
                    if (!getSelect().isAgrupado()) {
                        moComboBoxModelCampos.addElement(JSelect.mcsTodosCampos);
                    }
                    for (int i = 0; i < loTabla.getCampos().size(); i++) {
                        JFieldDef loCampo = loTabla.getCampos().get(i);
                        moComboBoxModelCampos.addElement(
                                getCampoFormat(
                                loTabla.getNombre(),
                                loCampo.getNombre(),
                                null));
                    }
                }
            }
        }
    }

    void cambioTablas() throws Exception {
        mostrar();
    }
    void recargarComboTablas() throws Exception {
        JSelect loSelect = getSelect();
        moComboBoxModelTablas.removeAllElements();
        if (loSelect.getFrom()!=null) {
            for (int i = 0; i < loSelect.getFrom().getTablasUnion().size(); i++) {
                JSelectUnionTablas loUnion = (JSelectUnionTablas) loSelect.getFrom().getTablasUnion().get(i);
                if(loUnion.getTabla2() == null || loUnion.getTabla2().equals("")){
                    moComboBoxModelTablas.addElement(
                        getTablaFormat(
                            null,
                            loUnion.getTablaPrefijoCampos1()));
                }else{
                    moComboBoxModelTablas.addElement(
                        getTablaFormat(
                            loUnion.getTabla2(),
                            loUnion.getTabla2Alias()));
                }
            }
        }
    }

    void addCampo(JSelectCampo poCampo) throws Exception {
        //esto se llama cuando del Panel Tables se hace doble click sobre un campo
        //por lo q se añadira el ultimo campo
        int lColumnaLibre = -1;
        for(int i = 0 ; i < moTableModelCampos.moList.getFields().size() && lColumnaLibre<0; i++){
            moTableModelCampos.moList.setIndex(0);
            if(moTableModelCampos.moList.getFields(i).isVacio()){
                lColumnaLibre = i;
            }
        }
//        //ult. campo del JSelect
//        JSelectCampo loCampo = (JSelectCampo) getSelect().getCampos().get(getSelect().getCampos().size()-1);
        mostrarCampo(poCampo, lColumnaLibre);
        jTableSelectCampos.repaint();

    }

    String getCampoDeFormatCaption(String psValor) {
        String lsResult = "";
        int lPosi = psValor.indexOf(":");
        if (lPosi > 0) {
            lsResult = psValor.substring(0, lPosi);
        } else {
            lsResult = psValor;
        }
        return lsResult.replace("[", "").replace("]", "");
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

    String getTablaOAliasDeFormat(String psValor) throws Exception {
        String lsResult = "";
        int lPosi1 = psValor.indexOf("[");
        int lPosi2 = psValor.indexOf("]");
        //vemos si existen corchetes, entonces el nombre fisico esta entre los corchetes
        if (lPosi1 >= 0) {
            lsResult = psValor.substring(lPosi1 + 1, lPosi2).trim();
        } else {
            lsResult = psValor;
        }
        return lsResult;
    }
    JFieldDef getCampoDeColumna(int plColumn) throws Exception{
        JFieldDef loResult = null;
        String lsCampo = moTableModelCampos.getValueAt(moTableModelCampos.lPosiCampos, plColumn+1).toString();
        String lsTabla = moTableModelCampos.getValueAt(moTableModelCampos.lPosiTablas, plColumn+1).toString();
        JTableDef loTabla = getTablaDeFormat(lsTabla);
        return loTabla.getCampos().get(getCampoDeFormatNombre(lsCampo));

    }

    JTableDef getTablaDeFormat(int plColumn) throws Exception {
        return getTablaDeFormat(moTableModelCampos.getValueAt(moTableModelCampos.lPosiTablas, plColumn+1).toString());
    }
    JTableDef getTablaDeFormat(String psValor) throws Exception {
        JTableDef loResult = null;
        if(psValor!=null){
            String lsResult = "";
            int lPosi1 = psValor.indexOf("[");
            int lPosi2 = psValor.indexOf("]");
            //vemos si existen corchetes, entonces el nombre fisico esta entre los corchetes
            if (lPosi1 >= 0) {
                lsResult = psValor.substring(lPosi1 + 1, lPosi2).trim();
            } else {
                lsResult = psValor;
            }
            //comprobamos q existe la tabla
            loResult = getDatos().getServer().getTableDefs().get(lsResult);
            if (loResult == null) {
                //si no existe preguntamos a la select si este nombre es un alias y q nos de el nombre real
                lsResult = getSelect().getFrom().getNombreTablaDeAlias(lsResult);
                loResult = getDatos().getServer().getTableDefs().get(lsResult);
            }
        }

        return loResult;
    }

    String getTablaFormat(String psNombre, String psAlias) throws Exception {
        String lsResult = "";

        if (psAlias != null && !psAlias.equals("")) {
            lsResult = psAlias;
        } else {
            JTableDef loTabla = getDatos().getServer().getTableDefs().get(psNombre);
            //si no existe preguntamos a la select si este nombre es un alias y q nos de el nombre real
            if (loTabla == null) {
                String lsAux = getSelect().getFrom().getNombreTablaDeAlias(psNombre);
                if (lsAux != null && !lsAux.equals("")) {
                    psNombre = lsAux;
                }
            }

            if (getDatos().isUsarNombresFisicos()) {
                lsResult = psNombre;
            } else {
                String lsCaption = getDatos().getTextosForms().getString(psNombre);
                if (lsCaption.equalsIgnoreCase(psNombre)) {
                    lsResult = psNombre;
                } else {
                    lsResult = lsCaption + "[" + psNombre + "]";
                }
            }
        }
        return lsResult;
    }


    void setConsulta(JGuiConsulta poConsulta) {
        moConsulta = poConsulta;
    }

    JGuiConsulta getConsulta() {
        return moConsulta;
    }

    ComboBoxModel getModelAgrupacion() {
        return moComboBoxModelAgrupacion;
    }

    ComboBoxModel getModelCampos(int plColumna) throws Exception {
        rellenarModelCampos(plColumna);
        return moComboBoxModelCampos;
    }

    ComboBoxModel getModelTablas() {
        return moComboBoxModelTablas;
    }

    ComboBoxModel getModelOrden() {
        return moComboBoxModelOrden;
    }
    ComboBoxModel getModelUnionCondiciones() {
        return moComboBoxModelUnionCondiciones;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();

        setPreferredSize(new java.awt.Dimension(100, 100));
        setLayout(new java.awt.BorderLayout());
        add(jScrollPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

    public void tableChanged(TableModelEvent e) {
        try {
//puede tardar
//            guardar();
        } catch (Exception ex) {
            JDepuracion.anadirTexto(getClass().getName(), ex);
            utilesGUIx.msgbox.JMsgBox.mensajeError(this, ex);
        }

    }

}
class JTableCZConsulta extends JTableCZ {

    private JGuiSelectCampos moSelectCampos;
    private JComboBox moComboCampos = new JComboBoxCZ();
    private DefaultCellEditor moEditorCampos;
    private JComboBox moComboTablas = new JComboBoxCZ();
    private DefaultCellEditor moEditorTablas;
    private JComboBox moComboAgrupacion = new JComboBoxCZ();
    private DefaultCellEditor moEditorAgrupacion;
    private JComboBox moComboOrden = new JComboBoxCZ();
    private DefaultCellEditor moEditorOrden;
    private JCheckBox moCheckMostrar = new JCheckBox();
    private JTableCZEditor moEditorMostrar;
    private JTextFieldCZ moTextCriterio = new JTextFieldCZ();
    private JTableCZEditor moEditorCriterio;

//    JComboBox moComboUnionCondiciones = new JComboBoxCZ();
//    DefaultCellEditor moEditorUnionCondiciones;

    private DefaultTableCellRenderer moRenderColumn0;

    JTableCZConsulta(JGuiSelectCampos poSelectCampos) {
        moSelectCampos = poSelectCampos;

        moComboCampos.setEditable(false);
        moEditorCampos = new DefaultCellEditor(moComboCampos) {

            public Object getCellEditorValue() {
                return moComboCampos.getSelectedItem();
            }
        };
        moComboTablas.setModel(poSelectCampos.getModelTablas());
        moEditorTablas = new DefaultCellEditor(moComboTablas) {

            public Object getCellEditorValue() {
                return moComboTablas.getSelectedItem();
            }
        };
        moComboAgrupacion.setModel(poSelectCampos.getModelAgrupacion());
        moEditorAgrupacion = new DefaultCellEditor(moComboAgrupacion) {

            public Object getCellEditorValue() {
                return moComboAgrupacion.getSelectedItem();
            }
        };


        moComboOrden.setModel(poSelectCampos.getModelOrden());
        moEditorOrden = new DefaultCellEditor(moComboOrden) {

            public Object getCellEditorValue() {
                return moComboOrden.getSelectedItem();
            }
        };

//        moComboUnionCondiciones.setModel(poSelectCampos.getModelUnionCondiciones());
//        moEditorUnionCondiciones = new DefaultCellEditor(moComboUnionCondiciones) {
//
//            public Object getCellEditorValue() {
//                return moComboUnionCondiciones.getSelectedItem();
//            }
//        };



        moEditorMostrar = new JTableCZEditor(moCheckMostrar);

        moTextCriterio.mbAsocidoATabla = true;
        moTextCriterio.setTipo(JTipoTextoEstandar.mclTextCadena);
        moEditorCriterio = new JTableCZEditor(moTextCriterio);
        moEditorCriterio.addCellEditorListener(this);

        moRenderColumn0 = new DefaultTableCellRenderer() {

            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                if (table != null) {
                    JTableHeader header = table.getTableHeader();
                    if (header != null) {
                        setForeground(header.getForeground());
                        setBackground(header.getBackground());
                        setFont(header.getFont());
                    }
                }

                setText((value == null) ? "" : value.toString());
                return this;
            }
        };
        moRenderColumn0.setHorizontalAlignment(JLabel.RIGHT);

    }

    public TableCellRenderer getCellRenderer(int row, int column) {
        if (row == JGuiSelectCamposTableModel.lPosiMostrar && column != 0) {
            return this.getDefaultRenderer(Boolean.class);
        } else {
            if (column == 0) {
                //1º columna como la cabezera
                return moRenderColumn0;
            } else {
                return super.getCellRenderer(row, column);
            }
        }
    }

    public TableCellEditor getCellEditor(int row, int column) {
        TableCellEditor loResult = null;
        if (row == JGuiSelectCamposTableModel.lPosiCampos) {//campos
            try {
                //campos
                moComboCampos.setModel(moSelectCampos.getModelCampos(column));
            } catch (Exception ex) {
                JDepuracion.anadirTexto(getClass().getName(), ex);
                utilesGUIx.msgbox.JMsgBox.mensajeError(this, ex);

            }
            loResult = moEditorCampos;
        }
        if (row == JGuiSelectCamposTableModel.lPosiTablas) {//tablas
            loResult = moEditorTablas;
        }
        if (row == JGuiSelectCamposTableModel.lPosiAgrupacion) {//agrupacion
            loResult = moEditorAgrupacion;
        }
        if (row == JGuiSelectCamposTableModel.lPosiOrden) {//orden
            loResult = moEditorOrden;
        }
        if (row == JGuiSelectCamposTableModel.lPosiMostrar) {//mostrar
            loResult = moEditorMostrar;
        }
        if (row >= JGuiSelectCamposTableModel.lPosiCriterios) {//criterio
            loResult = moEditorCriterio;
        }

        return loResult;
    }

    public void changeSelection(int rowIndex, int columnIndex,
            boolean toggle, boolean extend) {
        if (columnIndex == 0 && rowIndex < JGuiSelectCamposTableModel.lPosiCriterios)
        // Podemos llamar a changeSelecion() incrementando la columna en 1
        // o bien podríamos hacer directamente un return.
        {
            super.changeSelection(rowIndex, columnIndex + 1, toggle, extend);
        } else {
            super.changeSelection(rowIndex, columnIndex, toggle, extend);
        }
    }
}

