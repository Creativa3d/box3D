/*
* JPanelAUXILIARES.java
*
* Creado el 17/10/2014
*/

package utilesGUIx.aplicacion.auxiliares.forms;

import utilesGUIx.Rectangulo;

import ListDatos.*;
import utilesGUIx.JGUIxConfigGlobal;
import utilesGUIx.formsGenericos.*;
import utilesGUIx.formsGenericos.edicion.*;

import utilesGUIx.aplicacion.auxiliares.tablasExtend.JTEEAUXILIARES;

public class JPanelAUXILIARES extends JPanelGENERALBASE {

    private JTEEAUXILIARES moAUXILIARES;

    /** Creates new form JPanelAUXILIARES*/
    public JPanelAUXILIARES() {
        super();
        initComponents();
    }

    public void setDatos(final JTEEAUXILIARES poAUXILIARES, final IPanelControlador poPadre) throws Exception {
        moAUXILIARES = poAUXILIARES;
        setDatos(poPadre);
    }

    public String getTitulo() {
        String lsResult;
        if(moAUXILIARES.moList.getModoTabla() == JListDatos.mclNuevo) {
            lsResult= JGUIxConfigGlobal.getInstancia().getTextosForms().getTexto(JTEEAUXILIARES.msCTabla) + " [Nuevo]" ;
        }else{
            lsResult=JGUIxConfigGlobal.getInstancia().getTextosForms().getTexto(JTEEAUXILIARES.msCTabla)  + 
                moAUXILIARES.getCODIGOAUXILIAR().getString();
        }
        return lsResult;
    }

    public JSTabla getTabla(){
        return moAUXILIARES;
    }

    public void rellenarPantalla() throws Exception {

        //ponemos los textos a los label
        lblCODIGOAUXILIAR.setField(moAUXILIARES.getCODIGOAUXILIAR());
        txtCODIGOAUXILIAR.setField(moAUXILIARES.getCODIGOAUXILIAR());
        lblCODIGOGRUPOAUX.setField(moAUXILIARES.getCODIGOGRUPOAUX());
        txtCODIGOGRUPOAUX.setField(moAUXILIARES.getCODIGOGRUPOAUX());
        lblACRONIMO.setField(moAUXILIARES.getACRONIMO());
        txtACRONIMO.setField(moAUXILIARES.getACRONIMO());
        lblDESCRIPCION.setField(moAUXILIARES.getDESCRIPCION());
        txtDESCRIPCION.setField(moAUXILIARES.getDESCRIPCION());
    }

    public void habilitarSegunEdicion() throws Exception {
        if(moAUXILIARES.moList.getModoTabla() == JListDatos.mclNuevo) {
            txtCODIGOAUXILIAR.setEnabled(true);
        }else{
            txtCODIGOAUXILIAR.setEnabled(false);
        }
    }

    public void ponerTipoTextos() throws Exception {
    }

    public void mostrarDatos() throws Exception {
        IFilaDatos loFila;

        //Establecemos los valores de los paneles si hay
        //jPanelColectorEntrada.setValueTabla(moAlbaran.getCOLECTORENTRADA().getString());
    }

    public void establecerDatos() throws Exception {
        moAUXILIARES.validarCampos();
    }

    public void aceptar() throws Exception {
        int lModo = getModoTabla();
        IResultado loResult=moAUXILIARES.guardar();
        if(loResult.getBien()){
             actualizarPadre(lModo);
        }else{
            throw new Exception(loResult.getMensaje());
        }
    }

    public Rectangulo getTanano(){
        return new Rectangulo(0,0, 740, 400);
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
        lblCODIGOAUXILIAR = new utilesGUIx.JLabelCZ();
        txtCODIGOAUXILIAR = new utilesGUIx.JTextFieldCZ();
        lblCODIGOGRUPOAUX = new utilesGUIx.JLabelCZ();
        txtCODIGOGRUPOAUX = new utilesGUIx.JTextFieldCZ();
        lblACRONIMO = new utilesGUIx.JLabelCZ();
        txtACRONIMO = new utilesGUIx.JTextFieldCZ();
        lblDESCRIPCION = new utilesGUIx.JLabelCZ();
        txtDESCRIPCION = new utilesGUIx.JTextFieldCZ();
        jPanelEspaciador = new javax.swing.JPanel();

        setLayout(new java.awt.GridBagLayout());

        jPanelGENERAL.setLayout(new java.awt.GridBagLayout());

        lblCODIGOAUXILIAR.setText("CODIGOAUXILIAR");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanelGENERAL.add(lblCODIGOAUXILIAR, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanelGENERAL.add(txtCODIGOAUXILIAR, gridBagConstraints);

        lblCODIGOGRUPOAUX.setText("CODIGOGRUPOAUX");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanelGENERAL.add(lblCODIGOGRUPOAUX, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanelGENERAL.add(txtCODIGOGRUPOAUX, gridBagConstraints);

        lblACRONIMO.setText("ACRONIMO");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanelGENERAL.add(lblACRONIMO, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanelGENERAL.add(txtACRONIMO, gridBagConstraints);

        lblDESCRIPCION.setText("DESCRIPCION");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanelGENERAL.add(lblDESCRIPCION, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanelGENERAL.add(txtDESCRIPCION, gridBagConstraints);
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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanelEspaciador;
    private javax.swing.JPanel jPanelGENERAL;
    private javax.swing.JTabbedPane jTabbedPane1;
    private utilesGUIx.JLabelCZ lblACRONIMO;
    private utilesGUIx.JLabelCZ lblCODIGOAUXILIAR;
    private utilesGUIx.JLabelCZ lblCODIGOGRUPOAUX;
    private utilesGUIx.JLabelCZ lblDESCRIPCION;
    private utilesGUIx.JTextFieldCZ txtACRONIMO;
    private utilesGUIx.JTextFieldCZ txtCODIGOAUXILIAR;
    private utilesGUIx.JTextFieldCZ txtCODIGOGRUPOAUX;
    private utilesGUIx.JTextFieldCZ txtDESCRIPCION;
    // End of variables declaration//GEN-END:variables
}
