/*
* JPanelDOCUMTIPOS.java
*
* Creado el 19/10/2016
*/

package utilesDoc.forms;

import utilesDoc.tablasExtend.JTEEDOCUMTIPOS;
import utilesGUIx.Rectangulo;
import utiles.JDepuracion;

import ListDatos.*;
import utilesDoc.JDocDatosGeneralesModelo;
import utilesGUIx.formsGenericos.*;
import utilesGUIx.formsGenericos.edicion.*;

import utilesDoc.tablas.*;
import utilesDoc.tablasExtend.*;

public class JPanelDOCUMTIPOS extends JPanelGENERALBASE {

    private JTEEDOCUMTIPOS moDOCUMTIPOS;

    /** Creates new form JPanelDOCUMTIPOS*/
    public JPanelDOCUMTIPOS() {
        super();
        initComponents();
    }

    public void setDatos(final JTEEDOCUMTIPOS poDOCUMTIPOS, final IPanelControlador poPadre) throws Exception {
        moDOCUMTIPOS = poDOCUMTIPOS;
        setDatos(poPadre);
    }

    public String getTitulo() {
        String lsResult;
        if(moDOCUMTIPOS.moList.getModoTabla() == JListDatos.mclNuevo) {
            lsResult= JDocDatosGeneralesModelo.getTextosForms().getTexto(JTEEDOCUMTIPOS.msCTabla) + " [Nuevo]" ;
        }else{
            lsResult=JDocDatosGeneralesModelo.getTextosForms().getTexto(JTEEDOCUMTIPOS.msCTabla)  + 
                moDOCUMTIPOS.getCODIGOTIPODOC().getString();
        }
        return lsResult;
    }

    public JSTabla getTabla(){
        return moDOCUMTIPOS;
    }

    public void rellenarPantalla() throws Exception {

        //ponemos los textos a los label
        lblCODIGOTIPODOC.setField(moDOCUMTIPOS.getCODIGOTIPODOC());
        txtCODIGOTIPODOC.setField(moDOCUMTIPOS.getCODIGOTIPODOC());
        lblDESCRIPCION.setField(moDOCUMTIPOS.getEXTENSION());
        txtDESCRIPCION.setField(moDOCUMTIPOS.getEXTENSION());
        lblIMAGENSN.setField(moDOCUMTIPOS.getIMAGENSN());
        txtIMAGENSN.setField(moDOCUMTIPOS.getIMAGENSN());
    }

    public void habilitarSegunEdicion() throws Exception {
        if(moDOCUMTIPOS.moList.getModoTabla() == JListDatos.mclNuevo) {
            txtCODIGOTIPODOC.setEnabled(true);
        }else{
            txtCODIGOTIPODOC.setEnabled(false);
        }
    }

    public void ponerTipoTextos() throws Exception {
    }

    public void mostrarDatos() throws Exception {
        IFilaDatos loFila;


        jTabbedPane1StateChanged(null);

        //Establecemos los valores de los paneles si hay
        //jPanelColectorEntrada.setValueTabla(moAlbaran.getCOLECTORENTRADA().getString());
    }

    public void establecerDatos() throws Exception {
        moDOCUMTIPOS.validarCampos();
    }

    public void aceptar() throws Exception {
        int lModo = getModoTabla();
        IResultado loResult=moDOCUMTIPOS.guardar();
        if(loResult.getBien()){
             actualizarPadre(lModo);
        }else{
            throw new Exception(loResult.getMensaje());
        }
    }

    public Rectangulo getTanano(){
        return new Rectangulo(0,0, 740, 400);
    }

    private void compruebaPK() throws Exception {
        if(
            txtCODIGOTIPODOC.getText().compareTo("") == 0
          ) {
            jTabbedPane1.setSelectedIndex(0);
            throw new Exception("Es necesario guardar datos antes de continuar");
        }
    }
    public void setBloqueoControles(final boolean pbBloqueo) throws Exception {
        super.setBloqueoControles(pbBloqueo);
        setBloqueoControlesContainer(jPanelDOCUMENTOSCODTIPODOCUMENTO,false);
   }
    private void jTabbedPane1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTabbedPane1StateChanged
        try{
            if(jTabbedPane1.getSelectedIndex()>=0){
                switch(jTabbedPane1.getSelectedIndex()){
                    case 0://General
                        break;
                    case 1://DOCUMENTOSCODTIPODOCUMENTO
                        compruebaPK();
                        jPanelGenericoDOCUMENTOSCODTIPODOCUMENTO.setControlador(moDOCUMTIPOS.getControlador(JTDOCUMENTOS.msCTabla, moPadre.getParametros().getMostrarPantalla()));
                        break;
                }
            }
        }catch(Exception e){
            JDepuracion.anadirTexto(getClass().getName(), e);
            utilesGUIx.msgbox.JMsgBox.mensajeError(this, e.toString());
        }
    }//GEN-LAST:event_jTabbedPane1StateChanged
    /** Este metodo es llamado desde el constructor para
     *  inicializar el formulario.
     *  AVISO: No modificar este codigo. El contenido de este metodo es
     *  siempre regenerado por el editor de formularios.
     */
//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanelGENERAL = new javax.swing.JPanel();
        jPanelDOCUMENTOSCODTIPODOCUMENTO = new javax.swing.JPanel();
        jPanelGenericoDOCUMENTOSCODTIPODOCUMENTO = new utilesGUIx.formsGenericos.JPanelGenerico();
        lblCODIGOTIPODOC = new utilesGUIx.JLabelCZ();
        txtCODIGOTIPODOC = new utilesGUIx.JTextFieldCZ();
        lblDESCRIPCION = new utilesGUIx.JLabelCZ();
        txtDESCRIPCION = new utilesGUIx.JTextFieldCZ();
        lblIMAGENSN = new utilesGUIx.JLabelCZ();
        txtIMAGENSN = new utilesGUIx.JTextFieldCZ();
        jPanelEspaciador = new javax.swing.JPanel();

        setLayout(new java.awt.GridBagLayout());

        jTabbedPane1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTabbedPane1StateChanged(evt);
            }
        });

        jPanelGENERAL.setLayout(new java.awt.GridBagLayout());
        jPanelDOCUMENTOSCODTIPODOCUMENTO.setLayout(new java.awt.GridBagLayout());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        lblCODIGOTIPODOC.setText("CODIGOTIPODOC");
        jPanelGENERAL.add(lblCODIGOTIPODOC, new java.awt.GridBagConstraints());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanelGENERAL.add(txtCODIGOTIPODOC, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        lblDESCRIPCION.setText("DESCRIPCION");
        jPanelGENERAL.add(lblDESCRIPCION, new java.awt.GridBagConstraints());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanelGENERAL.add(txtDESCRIPCION, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        lblIMAGENSN.setText("IMAGENSN");
        jPanelGENERAL.add(lblIMAGENSN, new java.awt.GridBagConstraints());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanelGENERAL.add(txtIMAGENSN, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        jPanelGENERAL.add(jPanelEspaciador, gridBagConstraints);

        jTabbedPane1.addTab("GENERAL", jPanelGENERAL);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        jPanelDOCUMENTOSCODTIPODOCUMENTO.add(jPanelGenericoDOCUMENTOSCODTIPODOCUMENTO, gridBagConstraints);
        jTabbedPane1.addTab("DOCUMENTOSCODTIPODOCUMENTO", jPanelDOCUMENTOSCODTIPODOCUMENTO);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        add(jTabbedPane1, gridBagConstraints);
    }
//GEN-END:initComponents


//GEN-BEGIN:variables
    // Declaracion de controles No modificar!!
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPanel jPanelGENERAL;
    private javax.swing.JPanel jPanelEspaciador;
    private javax.swing.JPanel jPanelDOCUMENTOSCODTIPODOCUMENTO;
    private utilesGUIx.formsGenericos.JPanelGenerico jPanelGenericoDOCUMENTOSCODTIPODOCUMENTO;
    private utilesGUIx.JLabelCZ lblCODIGOTIPODOC;
    private utilesGUIx.JTextFieldCZ txtCODIGOTIPODOC;
    private utilesGUIx.JLabelCZ lblDESCRIPCION;
    private utilesGUIx.JTextFieldCZ txtDESCRIPCION;
    private utilesGUIx.JLabelCZ lblIMAGENSN;
    private utilesGUIx.JTextFieldCZ txtIMAGENSN;
    // Fin de la declaracion de controles
//GEN-END:variables
}
