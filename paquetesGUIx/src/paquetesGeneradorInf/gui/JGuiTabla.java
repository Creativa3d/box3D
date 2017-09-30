/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * JGuiTable.java
 *
 * Created on 27-may-2009, 9:21:50
 */
package paquetesGeneradorInf.gui;

import ListDatos.JFilaDatosDefecto;
import ListDatos.JListDatos;
import ListDatos.JSTabla;
import ListDatos.JSelect;
import ListDatos.JSelectCampo;
import ListDatos.JSelectUnionTablas;
import ListDatos.estructuraBD.JFieldDef;
import ListDatos.estructuraBD.JTableDef;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.TransferHandler;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableModel;
import paquetesGeneradorInf.gui.util.JUtiles;
import utiles.JDepuracion;
import utilesGUIx.JTableModel;


public class JGuiTabla extends javax.swing.JPanel implements MouseMotionListener, ChangeListener, MouseListener {

    private JTableDef moTabla;
    private JGuiSelectTablas moSelectTablas;
    private JTableModel moTableModel;
    private JCCampos moListCampos;
    private String msTablaAlias;

    //redimension y movimiento
    private static final int mclNada = -1;
    private static final int mclMover = 0;
    private static final int mclIzqSup = 1;
    private static final int mclIzqInf = 2;
    private static final int mclDerSup = 3;
    private static final int mclDerInf = 4;
    private static final int mclIzq = 5;
    private static final int mclDer = 6;
    private static final int mclInf = 7;
    private static final int mclSup = 8;
    private int mlTipo = mclNada;

    /** Creates new form JGuiTable */
    public JGuiTabla(JGuiSelectTablas poSelectTablas) {
        moSelectTablas = poSelectTablas;
        initComponents();
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
        jScrollPane1.getViewport().addChangeListener(this);
        jTableCampos.setTransferHandler(new TransferHandler() {

           public boolean canImport( JComponent arg0, DataFlavor[] arg1 ) {
              for( int j = 0;j < arg1.length;j++ ) {
                 if( arg1[j].equals( DataFlavor.stringFlavor ) ) {
                    return true;
                 }
              }
              return false;
           }
//            public boolean canImport(TransferHandler.TransferSupport info) {
//                // Solo importamos Cadenas
//                if (!info.isDataFlavorSupported(DataFlavor.stringFlavor)) {
//                    return false;
//                }
//
//                JTable.DropLocation dl = (JTable.DropLocation) info.getDropLocation();
//                if (dl.getRow() == -1) {
//                    return false;
//                }
//                return true;
//            }

           public boolean importData(JComponent arg0, Transferable arg1 ) {
                int lRow;
//                JTable.DropLocation dl = jTableCampos.getDropLocation();
//                lRow=dl.getRow();
                lRow = jTableCampos.getSelectedRow();
                TableModel listModel = (TableModel) jTableCampos.getModel();

                //columna 1º de la relacion
                String lsColumna = null;
                if(lRow>=0){
                    lsColumna = (String) listModel.getValueAt(lRow, JCCampos.lPosiNombre);
                }

                // Conseguimos los datos
                String data="";
                 if( arg1.isDataFlavorSupported( DataFlavor.stringFlavor ) ) {
                    try {
                        data = (String) arg1.getTransferData( DataFlavor.stringFlavor );
                    } catch (Exception ex) {
                        JDepuracion.anadirTexto(getClass().getName(), ex);
                    }
                 }
//                Transferable t = info.getTransferable();
//                String data;
//                try {
//                    data = (String) t.getTransferData(DataFlavor.stringFlavor);
//                } catch (Exception e) {
//                    return false;
//                }

                // addRelacion
                JFilaDatosDefecto loFila = new JFilaDatosDefecto(data);
                String[] lasCamposIzq = new String[loFila.mlNumeroCampos()-2];
                String[] lasCamposDer = new String[loFila.mlNumeroCampos()-2];
                for(int i = 2 ; i < loFila.mlNumeroCampos(); i++){
                    lasCamposIzq[i-2] = loFila.msCampo(i);
                }
                lasCamposDer[0]=lsColumna;


                JSelectUnionTablas loUnion = moSelectTablas.getFromUnion(
                        (loFila.msCampo(1).equals("") ?loFila.msCampo(0): loFila.msCampo(1)),
                        getNombreTablaFisico());
                if(loUnion!=null){
                    try {
                        moSelectTablas.mostrarRelacion(loUnion, loFila.msCampo(0), false);
                    } catch (Exception ex) {
                        JDepuracion.anadirTexto(getClass().getName(), ex);
                        utilesGUIx.msgbox.JMsgBox.mensajeError(new JLabel(), ex);
                    }

                }else{
                    if(moSelectTablas.isTablaSecundaria(moTabla.getNombre(), getNombreTablaFisico())){
                        loUnion = new JSelectUnionTablas(
                                JSelectUnionTablas.mclInner,
                                (loFila.msCampo(1).equals("") ?loFila.msCampo(0): loFila.msCampo(1)),
                                moTabla.getNombre(),
                                getNombreTablaFisico(),
                                lasCamposIzq,
                                lasCamposDer);
                    }else{
                        loUnion = new JSelectUnionTablas(
                                JSelectUnionTablas.mclInner,
                                getNombreTablaFisico(),
                                loFila.msCampo(0),
                                loFila.msCampo(1),
                                lasCamposDer,
                                lasCamposIzq);
                    }
                    try {
                        moSelectTablas.mostrarRelacion(loUnion, loFila.msCampo(0), true);
                    } catch (Exception ex) {
                        JDepuracion.anadirTexto(getClass().getName(), ex);
                        utilesGUIx.msgbox.JMsgBox.mensajeError(new JLabel(), ex);
                    }
                }


                return false;
            }

            public int getSourceActions(JComponent c) {
                return COPY;
            }

            protected Transferable createTransferable(JComponent c) {
                JTable list = (JTable) c;
                int[] values = list.getSelectedRows();

                StringBuilder buff = new StringBuilder();

                //1º añadimos la tabla y tabla alias
                if(values.length>0){
                    buff.append(
                            getNombreTablaFisico()
                            );
                    buff.append(JFilaDatosDefecto.mccSeparacion1);
                    buff.append(
                            (msTablaAlias==null ? "": msTablaAlias)
                            );
                    buff.append(JFilaDatosDefecto.mccSeparacion1);
                }
                //2º añadimos los campos
                for (int i = 0; i < values.length; i++) {
                    int val = values[i];
                    buff.append(
                            moTableModel.getValueAt(val, JCCampos.lPosiNombre).toString()
                            );
                    buff.append(JFilaDatosDefecto.mccSeparacion1);
                }
                return new StringSelection(buff.toString());
            }
        });
//        jTableCampos.setDropMode(DropMode.ON);
        jTableCampos.setDragEnabled(true);
    }

    public JGuiTabla(JGuiSelectTablas poSelectTablas, JTableDef poTabla, String psTablaAlias) throws Exception {
        this(poSelectTablas);
        setTabla(poTabla, psTablaAlias);
    }

    public void setTabla(JTableDef poTabla, String psTablaAlias) throws Exception {
        moTabla = poTabla;
        msTablaAlias = psTablaAlias;
        mostrar();
    }
    public String getTablaAlias() {
        return msTablaAlias;
    }
    public JTableDef getTabla() {
        return moTabla;
    }

    JGuiConsulta getConsulta() {
        return moSelectTablas.getConsulta();
    }

    JGuiConsultaDatos getDatos() {
        return moSelectTablas.getConsulta().getDatos();
    }


    public String getNombreTablaFisico() {
        String lsResult = "";
        //nombre tabla
        if (msTablaAlias != null) {
            lsResult = msTablaAlias;
        } else {
            lsResult = moTabla.getNombre();
        }

        return lsResult;
    }

    private String getNombreTabla() {
        String lsResult = "";
        //nombre tabla
        if (msTablaAlias != null) {
            lsResult = msTablaAlias;
        } else {
            if (getDatos().isUsarNombresFisicos()) {
                lsResult = moTabla.getNombre();
            } else {
                lsResult = getDatos().getTextosForms().getString(moTabla.getNombre());
            }
        }

        return lsResult;
    }

    private void mostrar() throws Exception {
        //nombre tabla
        lblTabla.setText(getNombreTabla());
        //Nombre campos
        moListCampos = new JCCampos(this);
        moListCampos.setTabla(moTabla);
        moListCampos.refrescar(false, false);
        moTableModel = new JTableModel(moListCampos.moList);
        jTableCampos.setModel(moTableModel);
        if (getDatos().isUsarNombresFisicos()) {
            JUtiles.ponerAncho(jTableCampos.getColumnModel().getColumn(moListCampos.lPosiCaption), 0);
        } else {
            JUtiles.ponerAncho(jTableCampos.getColumnModel().getColumn(moListCampos.lPosiNombre), 0);
        }
    }

    public int getPosicionCampo(final String psCampo) {
        int lInicio = lblTabla.getHeight() +
                jTableCampos.getRowHeight();
        int lResult = lInicio;

        if (moListCampos.moList.buscar(
                JListDatos.mclTIgual,
                moListCampos.lPosiNombre,
                psCampo)) {
            lResult +=
                    (jTableCampos.getRowHeight() * moListCampos.moList.getIndex()) +
                    (jTableCampos.getRowHeight() / 2) -
                    jScrollPane1.getViewport().getViewPosition().getY();
        }
        if (lResult < lInicio) {
            lResult = lInicio;
        }
        if (lResult > getHeight()) {
            lResult = getHeight();
        }

        return lResult;


    }
    private void borrar() throws Exception {
        moSelectTablas.borrarTabla(moTabla.getNombre(), msTablaAlias);
    }

    public void stateChanged(ChangeEvent e) {
        moSelectTablas.componentMoved(null);
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        mlTipo = mclNada;
        if (mlTipo == mclNada) {
            if (e.getX() <= 3) {
                if (e.getY() <= 3) {
                    mlTipo = mclIzqSup;
                } else if (e.getY() >= (getHeight() - 3)) {
                    mlTipo = mclIzqInf;
                } else {
                    mlTipo = mclIzq;
                }
            }
        }
        if (mlTipo == mclNada) {
            if (e.getX() >= (getWidth() - 3)) {
                if (e.getY() <= 3) {
                    mlTipo = mclDerSup;
                } else if (e.getY() >= (getHeight() - 3)) {
                    mlTipo = mclDerInf;
                } else {
                    mlTipo = mclDer;
                }
            }
        }
        if (mlTipo == mclNada) {
            if (e.getY() <= 3) {
                mlTipo = mclSup;
            }
        }
        if (mlTipo == mclNada) {
            if (e.getY() >= (getHeight() - 3)) {
                mlTipo = mclInf;
            }
        }

        if (mlTipo == mclNada) {
            mlTipo = mclMover;
        }
        maybeShowPopup(e);
    }

    public void mouseReleased(MouseEvent e) {
        maybeShowPopup(e);
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent mme) {
        switch (mlTipo) {
            case mclMover:
                setLocation(
                        this.getX() + mme.getX() - this.getWidth() / 2,
                        this.getY() + mme.getY() - lblTabla.getHeight() / 2);
                break;
        }
        int lWidth = getWidth();
        int lHeight = getHeight();
        int lX = getX();
        int lY = getY();
        switch (mlTipo) {
            case mclIzq:
            case mclIzqInf:
            case mclIzqSup:
                lWidth = Math.abs(getWidth() - mme.getX());
                break;
            case mclDer:
            case mclDerInf:
            case mclDerSup:
                lWidth = Math.abs(mme.getX());
                break;
        }
        switch (mlTipo) {
            case mclInf:
            case mclDerInf:
            case mclIzqInf:
                lHeight = Math.abs(mme.getY());
                break;
            case mclSup:
            case mclDerSup:
            case mclIzqSup:
                lHeight = Math.abs(getHeight() - mme.getY());
                break;
        }
        switch (mlTipo) {
            case mclIzq:
            case mclIzqInf:
            case mclIzqSup:
                lX = mme.getX() + getX();
        }
        switch (mlTipo) {
            case mclSup:
            case mclIzqSup:
            case mclDerSup:
                lY = mme.getY() + getY();
        }

        setBounds(lX, lY, lWidth, lHeight);

        revalidate();
    }

    public void mouseMoved(MouseEvent mme) {
    }
    private void maybeShowPopup(MouseEvent e) {
        if (e.isPopupTrigger()) {
            jPopupMenu1.show(e.getComponent(),
                       e.getX(), e.getY());
        }
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuBorrar = new javax.swing.JMenuItem();
        lblTabla = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableCampos = new utilesGUIx.JTableCZ();

        FormListener formListener = new FormListener();

        jMenuBorrar.setText("Quitar tabla");
        jMenuBorrar.addActionListener(formListener);
        jPopupMenu1.add(jMenuBorrar);

        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255), 3));
        setPreferredSize(new java.awt.Dimension(120, 120));
        setLayout(new java.awt.BorderLayout());

        lblTabla.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTabla.setText("tabla");
        lblTabla.addKeyListener(formListener);
        add(lblTabla, java.awt.BorderLayout.NORTH);

        jTableCampos.setModel(new javax.swing.table.DefaultTableModel(
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
        jTableCampos.addMouseListener(formListener);
        jTableCampos.addKeyListener(formListener);
        jScrollPane1.setViewportView(jTableCampos);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);
    }

    // Code for dispatching events from components to event handlers.

    private class FormListener implements java.awt.event.ActionListener, java.awt.event.KeyListener, java.awt.event.MouseListener {
        FormListener() {}
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            if (evt.getSource() == jMenuBorrar) {
                JGuiTabla.this.jMenuBorrarActionPerformed(evt);
            }
        }

        public void keyPressed(java.awt.event.KeyEvent evt) {
            if (evt.getSource() == lblTabla) {
                JGuiTabla.this.lblTablaKeyPressed(evt);
            }
            else if (evt.getSource() == jTableCampos) {
                JGuiTabla.this.jTableCamposKeyPressed(evt);
            }
        }

        public void keyReleased(java.awt.event.KeyEvent evt) {
        }

        public void keyTyped(java.awt.event.KeyEvent evt) {
        }

        public void mouseClicked(java.awt.event.MouseEvent evt) {
            if (evt.getSource() == jTableCampos) {
                JGuiTabla.this.jTableCamposMouseClicked(evt);
            }
        }

        public void mouseEntered(java.awt.event.MouseEvent evt) {
        }

        public void mouseExited(java.awt.event.MouseEvent evt) {
        }

        public void mousePressed(java.awt.event.MouseEvent evt) {
        }

        public void mouseReleased(java.awt.event.MouseEvent evt) {
        }
    }// </editor-fold>//GEN-END:initComponents

    private void lblTablaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lblTablaKeyPressed
        try{
            if(evt.getKeyChar() == evt.VK_DELETE){
                borrar();
            }
        } catch (Exception ex) {
            JDepuracion.anadirTexto(getClass().getName(), ex);
            utilesGUIx.msgbox.JMsgBox.mensajeError(this, msTablaAlias);
        }

    }//GEN-LAST:event_lblTablaKeyPressed

    private void jTableCamposKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTableCamposKeyPressed
        try{
            if(evt.getKeyChar() == evt.VK_DELETE){
                borrar();
            }
        } catch (Exception ex) {
            JDepuracion.anadirTexto(getClass().getName(), ex);
            utilesGUIx.msgbox.JMsgBox.mensajeError(this, msTablaAlias);
        }

    }//GEN-LAST:event_jTableCamposKeyPressed

    private void jMenuBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuBorrarActionPerformed
        try{
            borrar();
        } catch (Exception ex) {
            JDepuracion.anadirTexto(getClass().getName(), ex);
            utilesGUIx.msgbox.JMsgBox.mensajeError(this, msTablaAlias);
        }

    }//GEN-LAST:event_jMenuBorrarActionPerformed

    private void jTableCamposMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableCamposMouseClicked
        try {
            if (evt.getClickCount() > 1) {
                if (jTableCampos.getSelectedRow() >= 0) {
                    moListCampos.getList().setIndex(jTableCampos.getSelectedRow());
                    JSelectCampo loCampo = new JSelectCampo(getNombreTablaFisico(), moListCampos.getNombre().getString());
                    moSelectTablas.getConsulta().addCampo(loCampo);
                }
            }
        } catch (Exception ex) {
            JDepuracion.anadirTexto(getClass().getName(), ex);
            utilesGUIx.msgbox.JMsgBox.mensajeError(this, msTablaAlias);
        }

    }//GEN-LAST:event_jTableCamposMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem jMenuBorrar;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private utilesGUIx.JTableCZ jTableCampos;
    private javax.swing.JLabel lblTabla;
    // End of variables declaration//GEN-END:variables


}
class JCCampos extends JSTabla {

    /**
     * Variables para las posiciones de los campos
     */
    public static int lPosiCaption = 0;
    public static int lPosiNombre = 1;
    /**
     * Variable nombre de tabla
     */
    public static String msCTabla = "";
    /**
     * Número de campos de la tabla
     */
    public static int mclNumeroCampos = 2;
    /**
     * Nombres de la tabla
     */
    public static String[] masNombres = new String[]{
        "Caption",
        "Nombre"
    };
    public static String[] masCaption = new String[]{
        "Nombre",
        "Nombre Real"
    };
    public static int[] malTipos = new int[]{
        JListDatos.mclTipoCadena,
        JListDatos.mclTipoCadena
    };
    public static int[] malTamanos = new int[]{
        0, 0
    };
    public static int[] malCamposPrincipales = new int[]{
        1
    };
    private JTableDef moTabla;
    private JGuiTabla moGuiTable;

    /**
     * Crea una instancia de la clase intermedia para la tabla CANONincluyendole el servidor de datos
     */
    public JCCampos(JGuiTabla poGuiTable) {
        super();
        moGuiTable = poGuiTable;
        moList = new JListDatos(null, msCTabla, masNombres, malTipos, malCamposPrincipales, masCaption, malTamanos);
        moList.addListener(this);
    }

    public JFieldDef getNombre() {
        return moList.getFields(lPosiNombre);
    }

    public JFieldDef getCaption() {
        return moList.getFields(lPosiCaption);
    }

    public void refrescar(final boolean pbPasarACache, final boolean pbLimpiarCache) throws Exception {
        moList.addNew();
        moList.getFields(lPosiNombre).setValue(JSelect.mcsTodosCampos);
        moList.getFields(lPosiCaption).setValue(JSelect.mcsTodosCampos);
        moList.update(false);
        for (int i = 0; i < moTabla.getCampos().size(); i++) {
            JFieldDef loCampo = moTabla.getCampos().get(i);
            moList.addNew();
            moList.getFields(lPosiNombre).setValue(loCampo.getNombre());
            moList.getFields(lPosiCaption).setValue(
                    moGuiTable.getDatos().getTextosForms().getCaption(moTabla.getNombre(), loCampo.getNombre()));
            moList.update(false);
        }
    }

    public JListDatos getList() {
        return moList;
    }

    public void setTabla(JTableDef poTabla) {
        moTabla = poTabla;
    }
}
