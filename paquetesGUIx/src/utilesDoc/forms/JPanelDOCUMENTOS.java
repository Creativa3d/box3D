/*
* JPanelDOCUMENTOS.java
*
* Creado el 19/10/2016
*/

package utilesDoc.forms;

import utilesDoc.tablasExtend.JTEEDOCUMTIPOS;
import utilesDoc.tablasExtend.JTEEDOCUMENTOS;
import utilesDoc.tablasExtend.JTEEDOCUMCLASIF;
import utilesGUIx.Rectangulo;

import ListDatos.*;
import java.awt.Image;
import javax.swing.ImageIcon;
import utiles.JDepuracion;
import utilesDoc.JDocDatosGeneralesModelo;
import utilesGUIx.JGUIxConfigGlobalModelo;
import utilesGUIx.formsGenericos.*;
import utilesGUIx.formsGenericos.edicion.*;
import utilesGUIx.imgTrata.lista.IImagen;
import utilesGUIx.msgbox.JMsgBox;

public class JPanelDOCUMENTOS extends JPanelGENERALBASE {

    private JTEEDOCUMENTOS moDOCUMENTOS;
    private Thread moThread;
    private JVerImagen moVerImagen;

    /** Creates new form JPanelDOCUMENTOS*/
    public JPanelDOCUMENTOS() {
        super();
        initComponents();
    }

    public void setDatos(final JTEEDOCUMENTOS poDOCUMENTOS, final IPanelControlador poPadre) throws Exception {
        moDOCUMENTOS = poDOCUMENTOS;
        setDatos(poPadre);
    }
    

    public void visualizar() {
        if(moThread!=null){
            moVerImagen.setCancelar(true);
        }
        jPanelVistaPrevia1.setImagen(new javax.swing.ImageIcon(
                getClass().getResource("/utilesGUIx/images/ani20.gif")
        ).getImage());
        moVerImagen = new JVerImagen();
        moThread = new Thread(moVerImagen);
        moThread.start();

    }
    class JVerImagen implements Runnable {

        private boolean mbCancelar=false;
        JVerImagen(){
        }
        public void run() {
            try {
                IImagen loImgBasica = JGUIxConfigGlobalModelo.getInstancia().getGestorDocumental().getImagenBasicaFactory().getImagenNueva();
                loImgBasica.setDatos(moDOCUMENTOS.moList.getFields().moFilaDatos());
                if(!mbCancelar){
                    if(loImgBasica.getImagen()!=null){
                        jPanelVistaPrevia1.setImagen(((ImageIcon)loImgBasica.getImagen()).getImage());
                    }else{
                        jPanelVistaPrevia1.setImagen((Image)null);
                    }
                }
            } catch (Throwable ex) {
                ImageIcon loIcon = new ImageIcon(getClass().getResource("/utilesGUIx/images/crossed_circle.png"));
                jPanelVistaPrevia1.setImagen(loIcon.getImage());
                JDepuracion.anadirTexto(getClass().getName(), ex);
            }

        }
        public void setCancelar(boolean pbCancelar){
            mbCancelar=pbCancelar;
        }
    }    

    public String getTitulo() {
        String lsResult;
        if(moDOCUMENTOS.moList.getModoTabla() == JListDatos.mclNuevo) {
            lsResult= JDocDatosGeneralesModelo.getTextosForms().getTexto(JTEEDOCUMENTOS.msCTabla) + " [Nuevo]" ;
        }else{
            lsResult=JDocDatosGeneralesModelo.getTextosForms().getTexto(JTEEDOCUMENTOS.msCTabla)  + 
                moDOCUMENTOS.getGRUPO().getString() + "-" +
                moDOCUMENTOS.getGRUPOIDENT().getString() + "-" +
                moDOCUMENTOS.getCODIGODOCUMENTO().getString();
        }
        return lsResult;
    }

    public JSTabla getTabla(){
        return moDOCUMENTOS;
    }

    public void rellenarPantalla() throws Exception {
        jPanelCODCLASIF.setDatos(JTEEDOCUMCLASIF.getParamPanelBusq(moDOCUMENTOS.moList.moServidor, moPadre.getParametros().getMostrarPantalla()));
        jPanelCODTIPODOCUMENTO.setDatos(JTEEDOCUMTIPOS.getParamPanelBusq(moDOCUMENTOS.moList.moServidor, moPadre.getParametros().getMostrarPantalla()));

        //ponemos los textos a los label
        lblGRUPO.setField(moDOCUMENTOS.getGRUPO());
        txtGRUPO.setField(moDOCUMENTOS.getGRUPO());
        lblGRUPOIDENT.setField(moDOCUMENTOS.getGRUPOIDENT());
        txtGRUPOIDENT.setField(moDOCUMENTOS.getGRUPOIDENT());
        lblCODIGODOCUMENTO.setField(moDOCUMENTOS.getCODIGODOCUMENTO());
        txtCODIGODOCUMENTO.setField(moDOCUMENTOS.getCODIGODOCUMENTO());
        lblNOMBRE.setField(moDOCUMENTOS.getNOMBRE());
        txtNOMBRE.setField(moDOCUMENTOS.getNOMBRE());
        lblDESCRIPCION.setField(moDOCUMENTOS.getDESCRIPCION());
        txtDESCRIPCION.setField(moDOCUMENTOS.getDESCRIPCION());
        lblAUTOR.setField(moDOCUMENTOS.getAUTOR());
        txtAUTOR.setField(moDOCUMENTOS.getAUTOR());
        lblFECHA.setField(moDOCUMENTOS.getFECHA());
        txtFECHA.setField(moDOCUMENTOS.getFECHA());
        lblUSUARIO.setField(moDOCUMENTOS.getUSUARIO());
        txtUSUARIO.setField(moDOCUMENTOS.getUSUARIO());
        lblFECHAMODIF.setField(moDOCUMENTOS.getFECHAMODIF());
        txtFECHAMODIF.setField(moDOCUMENTOS.getFECHAMODIF());
        jPanelCODTIPODOCUMENTO.setLabel(moDOCUMENTOS.getCODTIPODOCUMENTO().getCaption());
        jPanelCODTIPODOCUMENTO.setField(moDOCUMENTOS.getCODTIPODOCUMENTO());
        jPanelCODCLASIF.setLabel(moDOCUMENTOS.getCODCLASIF().getCaption());
        jPanelCODCLASIF.setField(moDOCUMENTOS.getCODCLASIF());
        lblRUTA.setField(moDOCUMENTOS.getRUTA());
        txtRUTA.setField(moDOCUMENTOS.getRUTA());
        lblIDENTIFICADOREXTERNO.setField(moDOCUMENTOS.getIDENTIFICADOREXTERNO());
        txtIDENTIFICADOREXTERNO.setField(moDOCUMENTOS.getIDENTIFICADOREXTERNO());
        lblIDENTIFICADOROTRO.setField(moDOCUMENTOS.getIDENTIFICADOROTRO());
        txtIDENTIFICADOROTRO.setField(moDOCUMENTOS.getIDENTIFICADOROTRO());
    }

    public void habilitarSegunEdicion() throws Exception {
        if(moDOCUMENTOS.moList.getModoTabla() == JListDatos.mclNuevo) {
            txtGRUPO.setEnabled(true);
            txtGRUPOIDENT.setEnabled(true);
            txtCODIGODOCUMENTO.setEnabled(true);
        }else{
            txtGRUPO.setEnabled(false);
            txtGRUPOIDENT.setEnabled(false);
            txtCODIGODOCUMENTO.setEnabled(false);
        }
        txtFECHAMODIF.setEnabled(false);
        txtUSUARIO.setEnabled(false);
    }

    public void ponerTipoTextos() throws Exception {
    }

    public void mostrarDatos() throws Exception {
        IFilaDatos loFila;

        visualizar();

    }

    public void establecerDatos() throws Exception {
        moDOCUMENTOS.validarCampos();
    }

    public void aceptar() throws Exception {
        int lModo = getModoTabla();
        IResultado loResult=moDOCUMENTOS.guardar(moPadre);
        if(loResult.getBien()){
             actualizarPadre(lModo);
        }else{
            throw new Exception(loResult.getMensaje());
        }
    }

    public Rectangulo getTanano(){
        return new Rectangulo(0,0, 740, 658);
    }

    /** Este metodo es llamado desde el constructor para
     *  inicializar el formulario.
     *  AVISO: No modificar este codigo. El contenido de este metodo es
     *  siempre regenerado por el editor de formularios.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanelGENERAL = new javax.swing.JPanel();
        lblGRUPO = new utilesGUIx.JLabelCZ();
        txtGRUPO = new utilesGUIx.JTextFieldCZ();
        lblGRUPOIDENT = new utilesGUIx.JLabelCZ();
        txtGRUPOIDENT = new utilesGUIx.JTextFieldCZ();
        lblCODIGODOCUMENTO = new utilesGUIx.JLabelCZ();
        txtCODIGODOCUMENTO = new utilesGUIx.JTextFieldCZ();
        lblNOMBRE = new utilesGUIx.JLabelCZ();
        txtNOMBRE = new utilesGUIx.JTextFieldCZ();
        lblDESCRIPCION = new utilesGUIx.JLabelCZ();
        jScrollDESCRIPCION = new javax.swing.JScrollPane();
        txtDESCRIPCION = new utilesGUIx.JTextAreaCZ();
        lblAUTOR = new utilesGUIx.JLabelCZ();
        txtAUTOR = new utilesGUIx.JTextFieldCZ();
        lblFECHA = new utilesGUIx.JLabelCZ();
        txtFECHA = new utilesGUIx.JTextFieldCZ();
        lblUSUARIO = new utilesGUIx.JLabelCZ();
        txtUSUARIO = new utilesGUIx.JTextFieldCZ();
        lblFECHAMODIF = new utilesGUIx.JLabelCZ();
        txtFECHAMODIF = new utilesGUIx.JTextFieldCZ();
        jPanelCODTIPODOCUMENTO = new utilesGUIx.panelesGenericos.JPanelBusquedaCombo();
        jPanelCODCLASIF = new utilesGUIx.panelesGenericos.JPanelBusquedaCombo();
        lblRUTA = new utilesGUIx.JLabelCZ();
        txtRUTA = new utilesGUIx.JTextFieldCZ();
        lblIDENTIFICADOREXTERNO = new utilesGUIx.JLabelCZ();
        txtIDENTIFICADOREXTERNO = new utilesGUIx.JTextFieldCZ();
        lblIDENTIFICADOROTRO = new utilesGUIx.JLabelCZ();
        txtIDENTIFICADOROTRO = new utilesGUIx.JTextFieldCZ();
        jPanelEspaciador = new javax.swing.JPanel();
        jPanelVistaPrevia1 = new utilesGUIx.imgTrata.JPanelVistaPrevia();

        setLayout(new java.awt.GridBagLayout());

        jPanelGENERAL.setLayout(new java.awt.GridBagLayout());

        lblGRUPO.setText("GRUPO");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanelGENERAL.add(lblGRUPO, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanelGENERAL.add(txtGRUPO, gridBagConstraints);

        lblGRUPOIDENT.setText("GRUPOIDENT");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanelGENERAL.add(lblGRUPOIDENT, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanelGENERAL.add(txtGRUPOIDENT, gridBagConstraints);

        lblCODIGODOCUMENTO.setText("CODIGODOCUMENTO");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanelGENERAL.add(lblCODIGODOCUMENTO, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanelGENERAL.add(txtCODIGODOCUMENTO, gridBagConstraints);

        lblNOMBRE.setText("NOMBRE");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanelGENERAL.add(lblNOMBRE, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanelGENERAL.add(txtNOMBRE, gridBagConstraints);

        lblDESCRIPCION.setText("DESCRIPCION");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanelGENERAL.add(lblDESCRIPCION, gridBagConstraints);

        txtDESCRIPCION.setColumns(20);
        txtDESCRIPCION.setRows(5);
        jScrollDESCRIPCION.setViewportView(txtDESCRIPCION);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanelGENERAL.add(jScrollDESCRIPCION, gridBagConstraints);

        lblAUTOR.setText("AUTOR");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanelGENERAL.add(lblAUTOR, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanelGENERAL.add(txtAUTOR, gridBagConstraints);

        lblFECHA.setText("FECHA");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanelGENERAL.add(lblFECHA, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanelGENERAL.add(txtFECHA, gridBagConstraints);

        lblUSUARIO.setText("USUARIO");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanelGENERAL.add(lblUSUARIO, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanelGENERAL.add(txtUSUARIO, gridBagConstraints);

        lblFECHAMODIF.setText("FECHAMODIF");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanelGENERAL.add(lblFECHAMODIF, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanelGENERAL.add(txtFECHAMODIF, gridBagConstraints);

        jPanelCODTIPODOCUMENTO.setLabel("CODTIPODOCUMENTO");
        jPanelCODTIPODOCUMENTO.setLabelSize(new java.awt.Dimension(167, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanelGENERAL.add(jPanelCODTIPODOCUMENTO, gridBagConstraints);

        jPanelCODCLASIF.setLabel("CODCLASIF");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanelGENERAL.add(jPanelCODCLASIF, gridBagConstraints);

        lblRUTA.setText("RUTA");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanelGENERAL.add(lblRUTA, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanelGENERAL.add(txtRUTA, gridBagConstraints);

        lblIDENTIFICADOREXTERNO.setText("IDENTIFICADOREXTERNO");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanelGENERAL.add(lblIDENTIFICADOREXTERNO, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanelGENERAL.add(txtIDENTIFICADOREXTERNO, gridBagConstraints);

        lblIDENTIFICADOROTRO.setText("IDENTIFICADOROTRO");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanelGENERAL.add(lblIDENTIFICADOROTRO, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanelGENERAL.add(txtIDENTIFICADOROTRO, gridBagConstraints);

        jPanelEspaciador.setLayout(new java.awt.BorderLayout());

        jPanelVistaPrevia1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelVistaPrevia1MouseClicked(evt);
            }
        });
        jPanelEspaciador.add(jPanelVistaPrevia1, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanelGENERAL.add(jPanelEspaciador, gridBagConstraints);

        jTabbedPane1.addTab("General", jPanelGENERAL);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        add(jTabbedPane1, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void jPanelVistaPrevia1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelVistaPrevia1MouseClicked
        try {            
            if(evt.getClickCount()>1){
                IImagen loImgBasica = JGUIxConfigGlobalModelo.getInstancia().getGestorDocumental().getImagenBasicaFactory().getImagenNueva();
                loImgBasica.setDatos(moDOCUMENTOS.moList.getFields().moFilaDatos());
                loImgBasica.ver();
            }
        } catch (Throwable ex) {
            JMsgBox.mensajeErrorYLog(this, ex);
        }

    }//GEN-LAST:event_jPanelVistaPrevia1MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private utilesGUIx.panelesGenericos.JPanelBusquedaCombo jPanelCODCLASIF;
    private utilesGUIx.panelesGenericos.JPanelBusquedaCombo jPanelCODTIPODOCUMENTO;
    private javax.swing.JPanel jPanelEspaciador;
    private javax.swing.JPanel jPanelGENERAL;
    private utilesGUIx.imgTrata.JPanelVistaPrevia jPanelVistaPrevia1;
    private javax.swing.JScrollPane jScrollDESCRIPCION;
    private javax.swing.JTabbedPane jTabbedPane1;
    private utilesGUIx.JLabelCZ lblAUTOR;
    private utilesGUIx.JLabelCZ lblCODIGODOCUMENTO;
    private utilesGUIx.JLabelCZ lblDESCRIPCION;
    private utilesGUIx.JLabelCZ lblFECHA;
    private utilesGUIx.JLabelCZ lblFECHAMODIF;
    private utilesGUIx.JLabelCZ lblGRUPO;
    private utilesGUIx.JLabelCZ lblGRUPOIDENT;
    private utilesGUIx.JLabelCZ lblIDENTIFICADOREXTERNO;
    private utilesGUIx.JLabelCZ lblIDENTIFICADOROTRO;
    private utilesGUIx.JLabelCZ lblNOMBRE;
    private utilesGUIx.JLabelCZ lblRUTA;
    private utilesGUIx.JLabelCZ lblUSUARIO;
    private utilesGUIx.JTextFieldCZ txtAUTOR;
    private utilesGUIx.JTextFieldCZ txtCODIGODOCUMENTO;
    private utilesGUIx.JTextAreaCZ txtDESCRIPCION;
    private utilesGUIx.JTextFieldCZ txtFECHA;
    private utilesGUIx.JTextFieldCZ txtFECHAMODIF;
    private utilesGUIx.JTextFieldCZ txtGRUPO;
    private utilesGUIx.JTextFieldCZ txtGRUPOIDENT;
    private utilesGUIx.JTextFieldCZ txtIDENTIFICADOREXTERNO;
    private utilesGUIx.JTextFieldCZ txtIDENTIFICADOROTRO;
    private utilesGUIx.JTextFieldCZ txtNOMBRE;
    private utilesGUIx.JTextFieldCZ txtRUTA;
    private utilesGUIx.JTextFieldCZ txtUSUARIO;
    // End of variables declaration//GEN-END:variables
}
