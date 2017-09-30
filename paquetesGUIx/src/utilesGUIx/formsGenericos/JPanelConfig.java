/*
 * JPanelConfig.java
 *
 * Created on 26 de febrero de 2007, 12:15
 */

package utilesGUIx.formsGenericos;

import ListDatos.ECampoError;
import ListDatos.JFilaDatosDefecto;
import ListDatos.JListDatos;
import ListDatos.JListDatosFiltroConj;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.table.TableModel;
import utiles.JDepuracion;
import utilesGUIx.JGUIxConfigGlobal;
import utilesGUIx.JTableModel;

public class JPanelConfig extends javax.swing.JPanel implements java.awt.event.ActionListener {
    static final int mclPosiIndice = 0;
    static final int mclPosiNombre = 1;
    static final int mclPosiLong = 2;
    static final int mclPosiOrden = 3;
    static final int mclPosiVisible = 4;
    
    private transient JTablaConfig moConfigOriginal;
    private transient JListDatos moListDatos;
    private transient TableModel moModeloOriginal;
    private transient boolean mbInicializando=false;
    private JPanelGeneralParametros moParam;
    /** Creates new form JPanelConfig */
    public JPanelConfig() {
        initComponents();
        JGUIxConfigGlobal.getInstancia().setTextoTodosComponent(this);
    }
    public void setDatos(final JTablaConfig poConfig, final TableModel poModelo, final JPanelGeneralParametros poParam) throws ECampoError{
        //inicializacion de variables
        mbInicializando=true;
        moParam = poParam;
        moModeloOriginal = poModelo;
        moConfigOriginal = poConfig;

        moListDatos = new JListDatos(null, "",
                new String[]{
                    "Indice Columna","Nombre Columna",
                    "Ancho","Orden",
                    "Visible"},
                new int[]{
                    JListDatos.mclTipoNumero, JListDatos.mclTipoCadena,
                    JListDatos.mclTipoNumero, JListDatos.mclTipoNumero,
                    JListDatos.mclTipoBoolean} ,
                new int[]{0},
                new String[]{
                    JGUIxConfigGlobal.getInstancia().getTextosForms().getTexto("Indice Columna"),
                    JGUIxConfigGlobal.getInstancia().getTextosForms().getTexto("Nombre Columna"),
                    JGUIxConfigGlobal.getInstancia().getTextosForms().getTexto("Ancho"),
                    JGUIxConfigGlobal.getInstancia().getTextosForms().getTexto("Orden"),
                    JGUIxConfigGlobal.getInstancia().getTextosForms().getTexto("Visible")
                }
        );
        
        jTableConfig.setModel(new JTablaModelConfig(moListDatos, this));

        JTablaConfig.setLongColumna(jTableConfig.getColumnModel().getColumn(mclPosiVisible), 0);
        JTablaConfig.setLongColumna(jTableConfig.getColumnModel().getColumn(mclPosiIndice), 0);

        
        cmbColumnaDefecto.borrarTodo();
        for(int i = 0 ; i < moModeloOriginal.getColumnCount(); i++){
            cmbColumnaDefecto.addLinea(String.valueOf(i) + "-" + moModeloOriginal.getColumnName(i),  String.valueOf(i) + JFilaDatosDefecto.mcsSeparacion1);
        }
        
        leerConfig();
        
        //datos defecto
        cmbColumnaDefecto.mbSeleccionarClave(poConfig.getConfigTabla().getCampoBusqueda() + JFilaDatosDefecto.mcsSeparacion1);
//        cmbConfig.mbSeleccionarClave(String.valueOf(poConfig.getIndiceConfig()) + JFilaDatosDefecto.mcsSeparacion1);
        mostrarConfig(poConfig.getConfigTablaConcreta().getNombre());
        mbInicializando=false;
    }
    private void leerConfig(){
        //config
        cmbConfig.borrarTodo();
        boolean lbAddLineaDefecto = true;
        for(int i = 0; i < moConfigOriginal.getConfigTabla().size();i++){
            if(JTablaConfig.mcsNombreDefecto.equals(moConfigOriginal.getConfigTabla().getConfig(i).getNombre())){
                lbAddLineaDefecto = false;
            }
            cmbConfig.addLinea(
                    moConfigOriginal.getConfigTabla().getConfig(i).getNombre(), 
                    moConfigOriginal.getConfigTabla().getConfig(i).getNombre() + JFilaDatosDefecto.mcsSeparacion1);
        }
        if(lbAddLineaDefecto){
            cmbConfig.addLinea(JTablaConfig.mcsNombreDefecto, JTablaConfig.mcsNombreDefecto + JFilaDatosDefecto.mcsSeparacion1);
        }
    }
    
    private void mostrarConfig(final String psIndice) throws ECampoError{

        moConfigOriginal.setIndiceConfig(psIndice);
        moConfigOriginal.aplicar();
        
        actualizarTablaDatos();
    }
    private void actualizarTablaDatos() throws ECampoError{
        moListDatos.clear();
        for(int i = 0 ; i < moModeloOriginal.getColumnCount() ; i++){
            moListDatos.addNew();
            moListDatos.getFields(mclPosiIndice).setValue(
                    i
                    );
            moListDatos.getFields(mclPosiNombre).setValue(
                    moModeloOriginal.getColumnName(i)
                    );
            try{

                moListDatos.getFields(mclPosiLong).setValue(
                    moConfigOriginal.getConfigTablaConcreta().getTablaConfigColumnaDeCampoReal(
                        i
                        ).getLong()
                    );
            }catch(Exception e){
            }
            try{
                moListDatos.getFields(mclPosiOrden).setValue(
                    moConfigOriginal.getConfigTablaConcreta().getColumna(i).getOrden() 
                    );
            }catch(Exception e){
            }
            try{
                moListDatos.getFields(mclPosiVisible).setValue(true);
                if(moParam.getColumnasVisiblesConfig()!= null){
                    moListDatos.getFields(mclPosiVisible).setValue(
                        moParam.getColumnasVisiblesConfig()[i]
                        );
                }
            }catch(Exception e){
            }
            moListDatos.update(false);
        }
        //quitamos los campos no visilbes de config.
        moListDatos.getFiltro().Clear();
        moListDatos.getFiltro().addCondicion(
                JListDatosFiltroConj.mclAND,
                JListDatos.mclTIgual,
                mclPosiVisible,
                JListDatos.mcsTrue
                );
        moListDatos.filtrar();
        jTableConfig.updateUI();

    }
    public void actionPerformed(final ActionEvent e) {
        try{
            if(moListDatos.moveFirst()){
                do{
                    moConfigOriginal.getConfigTablaConcreta().getColumna(
                                moListDatos.getFields(mclPosiIndice).getString()
                            ).setLong(
                                moListDatos.getFields(mclPosiLong).getInteger()
                            );
                    moConfigOriginal.getConfigTablaConcreta().getColumna(
                                moListDatos.getFields(mclPosiIndice).getString()
                            ).setOrden(
                                moListDatos.getFields(mclPosiOrden).getInteger()
                            );
                }while(moListDatos.moveNext());
            }
            
            moConfigOriginal.getConfigTabla().setCampoBusqueda(cmbColumnaDefecto.getFilaActual().msCampo(0));
            moConfigOriginal.aplicar();
//            moConfigOriginal.guardarConfig();
        }catch(Exception e1){
            utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this, e1, getClass().getName());
        }
        
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jLabelCZ1 = new utilesGUIx.JLabelCZ();
        cmbConfig = new utilesGUIx.JComboBoxCZ();
        btnNuevo = new utilesGUIx.JButtonCZ();
        jLabelCZ2 = new utilesGUIx.JLabelCZ();
        btnBorrar = new utilesGUIx.JButtonCZ();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableConfig = new utilesGUIx.JTableCZ();
        jLabelCZ3 = new utilesGUIx.JLabelCZ();
        cmbColumnaDefecto = new utilesGUIx.JComboBoxCZ();

        setLayout(new java.awt.GridBagLayout());

        jLabelCZ1.setText("Config. columnas");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        add(jLabelCZ1, gridBagConstraints);

        cmbConfig.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbConfig.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbConfigItemStateChanged(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        add(cmbConfig, gridBagConstraints);

        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIx/images/New16.gif")));
        btnNuevo.setToolTipText("Nuevo");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        add(btnNuevo, gridBagConstraints);

        jLabelCZ2.setText("  ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        add(jLabelCZ2, gridBagConstraints);

        btnBorrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIx/images/Delete16.gif")));
        btnBorrar.setToolTipText("Borrar");
        btnBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        add(btnBorrar, gridBagConstraints);

        jTableConfig.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTableConfig);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        add(jScrollPane1, gridBagConstraints);

        jLabelCZ3.setText("Columna Defecto");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        add(jLabelCZ3, gridBagConstraints);

        cmbColumnaDefecto.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbColumnaDefecto.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbColumnaDefectoItemStateChanged(evt);
            }
        });
        cmbColumnaDefecto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                cmbColumnaDefectoFocusLost(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        add(cmbColumnaDefecto, gridBagConstraints);

    }// </editor-fold>//GEN-END:initComponents

    private void cmbColumnaDefectoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cmbColumnaDefectoFocusLost
        actionPerformed(null);
    }//GEN-LAST:event_cmbColumnaDefectoFocusLost

    private void cmbColumnaDefectoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbColumnaDefectoItemStateChanged
        //actionPerformed(null);
    }//GEN-LAST:event_cmbColumnaDefectoItemStateChanged

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        try{
            String lsNombre = JOptionPane.showInputDialog("Introducir nombre de la nueva configuración");
            if(!lsNombre.equals("")){
                JTablaConfigTablaConfig loConfig = new JTablaConfigTablaConfig();
                loConfig.setNombre(lsNombre);
                moConfigOriginal.getConfigTabla().addConfig(loConfig);
                mostrarConfig(lsNombre);
//                moConfigOriginal.guardarConfig();
                
                cmbConfig.addLinea(lsNombre, lsNombre + JFilaDatosDefecto.mcsSeparacion1);
                cmbConfig.mbSeleccionarClave(lsNombre + JFilaDatosDefecto.mcsSeparacion1);
            }
        }catch(Exception e){
            utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this, e, getClass().getName());
        }

    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarActionPerformed
        try{
            if(cmbConfig.getFilaActual().msCampo(0).compareTo("0")==0){
                utilesGUIx.msgbox.JMsgBox.mensajeInformacion(this, "No se puede borrar la configuración por defecto");
            }else{
                if(JOptionPane.showConfirmDialog(this, "¿Deseas borrar la configuración actual?") == JOptionPane.YES_OPTION){
                    mbInicializando=true;
                    moConfigOriginal.getConfigTabla().removeConfig( 
                            cmbConfig.getFilaActual().msCampo(0)
                            );
                    leerConfig();
                    //datos defecto
                    cmbConfig.mbSeleccionarClave(JTablaConfig.mcsNombreDefecto + JFilaDatosDefecto.mcsSeparacion1);
                    mostrarConfig(JTablaConfig.mcsNombreDefecto);
                    mbInicializando=false;
                }
            }
        }catch(Exception e){
            utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this, e, getClass().getName());
        }
        
    }//GEN-LAST:event_btnBorrarActionPerformed

    private void cmbConfigItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbConfigItemStateChanged
        try{
            if(!mbInicializando){
                mostrarConfig(cmbConfig.getFilaActual().msCampo(0));
            }
        }catch(Exception e){
            utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this, e, getClass().getName());
        }
    }//GEN-LAST:event_cmbConfigItemStateChanged
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private utilesGUIx.JButtonCZ btnBorrar;
    private utilesGUIx.JButtonCZ btnNuevo;
    private utilesGUIx.JComboBoxCZ cmbColumnaDefecto;
    private utilesGUIx.JComboBoxCZ cmbConfig;
    private utilesGUIx.JLabelCZ jLabelCZ1;
    private utilesGUIx.JLabelCZ jLabelCZ2;
    private utilesGUIx.JLabelCZ jLabelCZ3;
    private javax.swing.JScrollPane jScrollPane1;
    private utilesGUIx.JTableCZ jTableConfig;
    // End of variables declaration//GEN-END:variables
    
}
class JTablaModelConfig extends JTableModel {
    private final transient ActionListener moPadre;
    JTablaModelConfig(JListDatos poDatos, ActionListener poPadre) {
	super(poDatos);
	moPadre = poPadre;
    }
    
    public boolean isCellEditable(final int row, final int col) {
	return col == JPanelConfig.mclPosiLong || 
               col == JPanelConfig.mclPosiOrden;
    }
    
    public void setValueAt(final Object value, final int row, final int col) {
        
        if(col == JPanelConfig.mclPosiOrden){

            //establecemos la fila
            moList.setIndex(row);
            //recuperamos el orden anterior
            int lOrdenAux = moList.getFields(col).getInteger();
            int lOrdenNuevo=0; 
            //ponemos el nuevo orden
            try{
                moList.getFields(col).setValue(value);
                moList.update(false);
                lOrdenNuevo=moList.getFields(col).getInteger();
            }catch(Exception e){
                
            }
            //buscamos el nuevo orden en el resto de filas
            if(moList.moveFirst()){
                do{
                    if(moList.getFields(col).getInteger()==lOrdenNuevo && moList.getIndex()!=row ){
                        //ponemos el orden q tuviera la fila anterior
                        try{
                            moList.getFields(col).setValue(lOrdenAux);
                            moList.update(false);
                        }catch(Exception e){

                        }
                    }
                }while(moList.moveNext());
                fireTableDataChanged();
            }
        }else{
            super.setValueAt(value, row, col);
        }
	moPadre.actionPerformed(new ActionEvent((Component)moPadre, 1, ""));
    }
}
