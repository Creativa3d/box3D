/*
 * JPanelGeneralFiltro.java
 *
 * Created on 5 de septiembre de 2005, 13:33
 */

package utilesGUIx.formsGenericos;


import ListDatos.*;
import android.content.Context;
import utilesGUIx.ActionEventCZ;
import utilesGUIx.ActionListenerCZ;
import utilesGUIx.JGUIxConfigGlobal;

public class JPanelGeneralFiltro implements ActionListenerCZ {
    public static final String mcsCambioFiltro = "CambioFiltro";

    private JListDatos moDatos;
    private boolean[] mabVisibles;
    private JTableModelFiltro moTableModel;
    private ActionListenerCZ moAccion;
    
    private boolean mbConOperadorComo =true;
    private ITablaConfig moTablaConfig;
    
    /** Creates new form JPanelGeneralFiltro */
    public JPanelGeneralFiltro() {
        super();
    }
    public void setDatos(final JListDatos poDatos, final boolean[] pabVisibles, final ActionListenerCZ poAccion){
        setDatos(poDatos, pabVisibles, poAccion, true, null);
    }
    public void setDatos(final JListDatos poDatos, final boolean[] pabVisibles, final ActionListenerCZ poAccion, final boolean pbConOperadorComo){
        setDatos(poDatos, pabVisibles, poAccion, pbConOperadorComo, null);
    }
    public void setDatos(final JListDatos poDatos, final boolean[] pabVisibles, final ActionListenerCZ poAccion, final boolean pbConOperadorComo, final ITablaConfig poTablaConfig){
        moDatos = poDatos;
        moAccion = poAccion;
        mbConOperadorComo=pbConOperadorComo;
        mabVisibles = new boolean[moDatos.getFields().count()];
        moTablaConfig = poTablaConfig;
        
//        crearCombos();
        
        setVisibles(pabVisibles);
    }
//    private void crearCombos(){
//        moComboBoxComparaciones = new JComboBox(); 
//        if(mbConOperadorComo){
//            moComboBoxComparaciones.addItem(JTableModelFiltro.mcsComo);
//        }
//        moComboBoxComparaciones.addItem(JTableModelFiltro.mcsIgual);
//        moComboBoxComparaciones.addItem(JTableModelFiltro.mcsDistinto);
//        moComboBoxComparaciones.addItem(JTableModelFiltro.mcsMayor);
//        moComboBoxComparaciones.addItem(JTableModelFiltro.mcsMayorIgual);
//        moComboBoxComparaciones.addItem(JTableModelFiltro.mcsMenor);
//        moComboBoxComparaciones.addItem(JTableModelFiltro.mcsMenorIgual);
//        moComboBoxComparaciones.addItem(JTableModelFiltro.mcsMasOMenos);
//        
//        moComboBoxUniones = new JComboBox(); 
//        moComboBoxUniones.addItem(JTableModelFiltro.mcsY);
//        moComboBoxUniones.addItem(JTableModelFiltro.mcsO);
//    }
    
    public void setVisibles(final boolean[] pabVisibles){
        for(int i = 0 ; i < mabVisibles.length ; i++){
            if(pabVisibles!=null){
                mabVisibles[i] = pabVisibles[i];
            }else{
                mabVisibles[i] = true;
            }
        }
        mostrarTabla();
    }
    public JTableModelFiltro getTableModelFiltro(){
        return moTableModel;
    }
    //rellena la tabla
    private void mostrarTabla(){
        //creamos las filas de datos
        int lMax = 0;
        if(mabVisibles==null){
            lMax = moDatos.getFields().count();
        }else{
            for(int i = 0; i< mabVisibles.length; i++){
                if(mabVisibles[i]){
                    lMax++;
                }
            }
        }
        Object[][] data = new Object[lMax][6];
        boolean lbCampoValido = true;
        int lCampo = 0;
        for(int i = 0; i < moDatos.getFields().count(); i++ ){
            lbCampoValido = true;
            if(mabVisibles!=null){
                if(mabVisibles.length > i){
                    lbCampoValido=mabVisibles[i];
                }else{
                    lbCampoValido=false;
                }
            }
            if(lbCampoValido){
                String lsOperador =JTableModelFiltro.mcsComo;
                if(!mbConOperadorComo){
                    lsOperador =JTableModelFiltro.mcsIgual;
                }
                data[lCampo] = new Object[]{
                    new Integer(i), 
                    moDatos.getFields().get(i).getCaption(), 
                    lsOperador, 
                    "" , 
                    JTableModelFiltro.mcsY,
                    Boolean.FALSE
                    };
                lCampo++;
            }
        }
        // creamos el modelo de los datos
        if(moTableModel==null){
            moTableModel = new JTableModelFiltro(data, this);
        }else{
            moTableModel.setDatos(data);
        }
//        moTabla.setModel(moTableModel);
          
//        // creamos los combos de las comparaciones
//  
//        TableColumn loColumn = moTabla.getColumn(JTableModelFiltro.mcsComparacion); 
//
//        loColumn.setCellEditor(new DefaultCellEditor(moComboBoxComparaciones)); 
//         
//        // creamos los combos de las comparaciones
//  
//        loColumn = moTabla.getColumn(JTableModelFiltro.mcsUnion); 
//
//        loColumn.setCellEditor(new DefaultCellEditor(moComboBoxUniones)); 
        
//        //ancho columnas
//        setLongColumna(moTabla.getColumnModel().getColumn(JTableModelFiltro.mclCodigo), 0);
//        setLongColumna(moTabla.getColumnModel().getColumn(JTableModelFiltro.mclNombre), 150);
//        setLongColumna(moTabla.getColumnModel().getColumn(JTableModelFiltro.mclComparacion), 100);
//        setLongColumna(moTabla.getColumnModel().getColumn(JTableModelFiltro.mclValor), 200);
//        setLongColumna(moTabla.getColumnModel().getColumn(JTableModelFiltro.mclUnion), 40);
//        setLongColumna(moTabla.getColumnModel().getColumn(JTableModelFiltro.mclDuplicadoSN), 0);
//
//        moTabla.getSelectionModel().setSelectionInterval(0, 0);
//        moTabla.setColumnSelectionInterval(JTableModelFiltro.mclValor, JTableModelFiltro.mclValor);
//        //color columna
//        DefaultTableCellRenderer colorRenderer = new DefaultTableCellRenderer() { 
// 	    public void setValue(Object value) { 
//                setBackground(new java.awt.Color(204,255,255));
//                setForeground(java.awt.Color.black); 
//                setText(value.toString());
// 	    } 
//         };
//        loColumn = moTabla.getColumn(JTableModelFiltro.mcsValor); 
//        loColumn.setCellRenderer(colorRenderer); 
    }
    
    public boolean setFiltroTodosCampos(String psText) throws Exception {
        JTableModelFiltro loFiltro = getTableModelFiltro();
        try{
            loFiltro.anularCambios();
            for(int i = 0 ; i < loFiltro.getRowCount(); i++){
                loFiltro.setValueAt(psText, i, JTableModelFiltro.mclValor);
                if(!loFiltro.getValueAt(i, JTableModelFiltro.mclComparacion).toString().equals(JTableModelFiltro.mcsComo) ){
                    loFiltro.setValueAt(JTableModelFiltro.mcsComo, i, JTableModelFiltro.mclComparacion);
                }
                if(psText.equals("")){
                    loFiltro.setValueAt(JTableModelFiltro.mcsY, i, JTableModelFiltro.mclUnion);
                }else{
                    loFiltro.setValueAt(JTableModelFiltro.mcsO, i, JTableModelFiltro.mclUnion);
                }
            }

        }finally{
            loFiltro.activarCambios();
        }      
        return true;
    }    
    /**
     *Limpia la tabla y el filtro y lo ejecuta
     */
    public void limpiar(){
        if(moTableModel!=null) {
            try{
                moTableModel.anularCambios();
                for(int i = 0; i< moTableModel.getRowCount();i++){
                    moTableModel.setValueAt("", i, JTableModelFiltro.mclValor);
                    moTableModel.setValueAt(JTableModelFiltro.mcsComo, i, JTableModelFiltro.mclComparacion);
                    moTableModel.setValueAt(JTableModelFiltro.mcsY, i, JTableModelFiltro.mclUnion);

                }
    //            buscar();
            }finally{
                moTableModel.activarCambios();
            }
        }
    }
    /**
     * crea el filtro y lo ejecuta
     */
    public void buscar(){

        boolean lbRefrescar = false;
        //aplicamos el filtro
        if(moDatos.getFiltro().mbAlgunaCond()){
            moDatos.getFiltro().Clear();
            moDatos.filtrarNulo();
            lbRefrescar = true;
        }
        if(moTableModel.getFiltro()!=null && moTableModel.getFiltro().mbAlgunaCond()){
            moDatos.getFiltro().addCondicion(JListDatosFiltroConj.mclAND, moTableModel.getFiltro());
            moDatos.filtrar();
            lbRefrescar = true;
        }
        //llamamos al panel para que refresque
        if(moAccion!=null && lbRefrescar){
            moAccion.actionPerformed(new ActionEventCZ(this,0,mcsCambioFiltro));
        }
    }
    
    //cada vez que cambia una fila se llama a este evento y se ejecuta el filtrar
    public void actionPerformed(final ActionEventCZ e) {
        try{
            buscar();
//            moTabla.refrescarDatos();
        }catch(Exception ex){
            utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(
                    (Context)JGUIxConfigGlobal.getInstancia().getMostrarPantalla().getContext(), ex, getClass().getName());
        }
    }
//    public void duplicar() throws Exception {
//        try{
//            if(moTabla.getSelectedRow()>=0){
//                moTableModel.duplicar(moTabla.getSelectedRow());
//            }else{
//                throw new Exception("no existe la fila a duplicar");
//            }
//        }catch(Exception e){
//            utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this, e, getClass().getName());
//        }
//    }
    
//
//    private void jBtnDuplicarActionPerformed() {
//        try{
//            duplicar();
//            moTabla.validate();
//            moTabla.repaint();
//        }catch(Exception e){
//            utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this, e, getClass().getName());
//        }
//    }//GEN-LAST:event_jBtnDuplicarActionPerformed

    private void jBtnLimpiarActionPerformed() {//GEN-FIRST:event_jBtnLimpiarActionPerformed
        try{
            limpiar();
//            moTabla.refrescarDatos();
        }catch(Exception ex){
            utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(
                    (Context)JGUIxConfigGlobal.getInstancia().getMostrarPantalla().getContext(), ex, getClass().getName());
        }
    }//GEN-LAST:event_jBtnLimpiarActionPerformed

    
    
}

