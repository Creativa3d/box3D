/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * JPanelMostrarFiferencia.java
 *
 * Created on 19-sep-2009, 9:17:41
 */
package utilesSincronizacion.forms;

import ListDatos.ECampoError;
import ListDatos.JListDatos;
import ListDatos.JListDatosFiltroElem;
import ListDatos.JSTabla;
import ListDatos.estructuraBD.JFieldDef;
import java.awt.Color;
import java.awt.Rectangle;
import utiles.IListaElementos;
import utilesGUIx.ColorCZ;
import utilesGUIx.JTableModel;
import utilesGUIx.Rectangulo;
import utilesGUIx.formsGenericos.colores.JPanelGenericoColores;
import utilesGUIx.formsGenericos.edicion.JPanelGENERALBASE;
import utilesGUIx.msgbox.JMsgBox;
import utilesSincronizacion.sincronizacion.conflictos.JConflicto;
import utilesSincronizacion.sincronizacion.consultas.JCConflictos;

public class JPanelMostrarDiferencia extends JPanelGENERALBASE {

    private IListaElementos moListaConflictos;
    private boolean mbDesactivarChange=false;

    /** Creates new form JPanelMostrarFiferencia */
    public JPanelMostrarDiferencia() {
        initComponents();
    }

    public String getTitulo() {
        return "Mostrar diferencia";
    }

    public JSTabla getTabla() {
        return null;
    }

    public void setDatos(JCConflictos poConsultaConflictos) throws CloneNotSupportedException {
        moListaConflictos = poConsultaConflictos.getListaConflictos();
        moListDatos = poConsultaConflictos.moList.Clone();
        moListDatos.ordenar(poConsultaConflictos.moList.getOrdenacion());
        moListDatos.setIndex(poConsultaConflictos.moList.getIndex());
    }

    public void habilitarSegunEdicion() throws Exception {
    }

    public void rellenarPantalla() throws Exception {
    }

    public void ponerTipoTextos() throws Exception {
    }

    public void mostrarDatos() throws Exception {
        mbDesactivarChange = true;
        try{
            chkGanaServidor.setValueTabla(moListDatos.getFields(JCConflictos.lPosiGanaServidor).getBooleanConNull());
            lblTabla.setText(moListDatos.getFields(JCConflictos.lPosiTABLA).getString());
            lblTipoModificacion.setText(moListDatos.getFields(JCConflictos.lPosiTipoActualizacion).getString());

            JListDatos loList = new JListDatos(
                    null, "", new String[]{"Campo", "Cliente", "Servidor"},
                    new int[]{JListDatos.mclTipoCadena, JListDatos.mclTipoCadena, JListDatos.mclTipoCadena},
                    new int[]{0});
            JConflicto loConflicto = (JConflicto) moListaConflictos.get(
                    moListDatos.getFields(JCConflictos.lPosiIndiceBase).getInteger());
            rellenar(loList, loConflicto);


            JPanelGenericoColores loColores = new JPanelGenericoColores(loList);
            loColores.addCondicion( JListDatos.mclTDistinto, 1, 2, new ColorCZ(Color.YELLOW.getRGB()), new ColorCZ(Color.BLACK.getRGB()));
            jTableCZ1.setTableCZColores(loColores);

            JTableModel loModel = new JTableModel(loList);
            loModel.mbActualizarServidor = false;
            loModel.addMouseListenerToHeaderInTable(jTableCZ1);
            jTableCZ1.setModel(loModel);

        }finally{
            mbDesactivarChange=false;
        }


    }

    private void rellenar(JListDatos loList, JConflicto loConflicto) throws ECampoError {
        for (int i = 0; i < loConflicto.moCamposCliente.size(); i++) {
            JFieldDef loCampoC = loConflicto.moCamposCliente.get(i);
            JFieldDef loCampoS = loConflicto.moCamposServidor.get(i);
            loList.addNew();
            try{
                loList.getFields(0).setValue(loCampoC.getNombre());
            }catch(Exception e){
                loList.getFields(0).setValue(loCampoS.getNombre());
            }
            try{
                loList.getFields(1).setValue(loCampoC.getValue());
            }catch(Exception e){}
            try{
                loList.getFields(2).setValue(loCampoS.getValue());
            }catch(Exception e){}
            loList.update(false);
        }
    }

    public void establecerDatos() throws Exception {

        moListDatos.getFields(JCConflictos.lPosiGanaServidor).setValue(chkGanaServidor.isSelected());

    }

    public void aceptar() throws Exception {
        moListDatos.update(false);
    }

    public void cancelar() throws Exception {
    }

    public Rectangulo getTanano() {
        return new Rectangulo(700, 580);
    }
    public void recuperarDatos() throws Exception {
        
    }

    public void editar() throws Exception {
    }
    public void setBloqueoControles(final boolean pbBloqueo) throws Exception {
        super.setBloqueoControles(pbBloqueo);
        chkGanaServidor.setEnabled(true);
        jTableCZ1.setEnabled(true);
        jScrollPane2.setEnabled(true);
        
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel2 = new javax.swing.JPanel();
        chkGanaServidor = new utilesGUIx.JCheckBoxCZ();
        lblTipoModificacion = new utilesGUIx.JLabelCZ();
        lblTabla = new utilesGUIx.JLabelCZ();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableCZ1 = new utilesGUIx.JTableCZ();

        setLayout(new java.awt.BorderLayout());

        jPanel2.setLayout(new java.awt.GridBagLayout());

        chkGanaServidor.setText("Gana servidor");
        chkGanaServidor.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkGanaServidorItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        jPanel2.add(chkGanaServidor, gridBagConstraints);

        lblTipoModificacion.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        jPanel2.add(lblTipoModificacion, gridBagConstraints);

        lblTabla.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        jPanel2.add(lblTabla, gridBagConstraints);

        add(jPanel2, java.awt.BorderLayout.NORTH);

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
        jScrollPane2.setViewportView(jTableCZ1);

        add(jScrollPane2, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void chkGanaServidorItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkGanaServidorItemStateChanged
        if(!mbDesactivarChange){
            try {
                moListDatos.getFields(JCConflictos.lPosiGanaServidor).setValue(chkGanaServidor.isSelected());
                moListDatos.update(false);
            } catch (Exception ex) {
                JMsgBox.mensajeErrorYLog(this, ex, getClass().getName());
            }

        }
    }//GEN-LAST:event_chkGanaServidorItemStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private utilesGUIx.JCheckBoxCZ chkGanaServidor;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private utilesGUIx.JTableCZ jTableCZ1;
    private utilesGUIx.JLabelCZ lblTabla;
    private utilesGUIx.JLabelCZ lblTipoModificacion;
    // End of variables declaration//GEN-END:variables
}
