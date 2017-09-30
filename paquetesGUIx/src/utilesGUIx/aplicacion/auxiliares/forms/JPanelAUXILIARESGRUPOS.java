/*
* JPanelAUXILIARESGRUPOS.java
*
* Creado el 17/10/2014
*/

package utilesGUIx.aplicacion.auxiliares.forms;

import utilesGUIx.Rectangulo;

import ListDatos.*;
import utilesGUIx.formsGenericos.*;
import utilesGUIx.formsGenericos.edicion.*;
import utilesGUIx.JGUIxConfigGlobal;
import utilesGUIx.aplicacion.auxiliares.tablasExtend.JTEEAUXILIARESGRUPOS;

public class JPanelAUXILIARESGRUPOS extends JPanelGENERALBASE {

    private JTEEAUXILIARESGRUPOS moAUXILIARESGRUPOS;

    /** Creates new form JPanelAUXILIARESGRUPOS*/
    public JPanelAUXILIARESGRUPOS() {
        super();
        initComponents();
    }

    public void setDatos(final JTEEAUXILIARESGRUPOS poAUXILIARESGRUPOS, final IPanelControlador poPadre) throws Exception {
        moAUXILIARESGRUPOS = poAUXILIARESGRUPOS;
        setDatos(poPadre);
    }

    public String getTitulo() {
        String lsResult;
        if(moAUXILIARESGRUPOS.moList.getModoTabla() == JListDatos.mclNuevo) {
            lsResult= JGUIxConfigGlobal.getInstancia().getTextosForms().getTexto(JTEEAUXILIARESGRUPOS.msCTabla) + " [Nuevo]" ;
        }else{
            lsResult=JGUIxConfigGlobal.getInstancia().getTextosForms().getTexto(JTEEAUXILIARESGRUPOS.msCTabla)  + 
                moAUXILIARESGRUPOS.getCODIGOGRUPOAUX().getString();
        }
        return lsResult;
    }

    public JSTabla getTabla(){
        return moAUXILIARESGRUPOS;
    }

    public void rellenarPantalla() throws Exception {

        //ponemos los textos a los label
        lblCODIGOGRUPOAUX.setField(moAUXILIARESGRUPOS.getCODIGOGRUPOAUX());
        txtCODIGOGRUPOAUX.setField(moAUXILIARESGRUPOS.getCODIGOGRUPOAUX());
        lblDESCRIPCION.setField(moAUXILIARESGRUPOS.getDESCRIPCION());
        txtDESCRIPCION.setField(moAUXILIARESGRUPOS.getDESCRIPCION());
    }

    public void habilitarSegunEdicion() throws Exception {
        if(moAUXILIARESGRUPOS.moList.getModoTabla() == JListDatos.mclNuevo) {
            txtCODIGOGRUPOAUX.setEnabled(true);
        }else{
            txtCODIGOGRUPOAUX.setEnabled(false);
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
        moAUXILIARESGRUPOS.validarCampos();
    }

    public void aceptar() throws Exception {
        int lModo = getModoTabla();
        IResultado loResult=moAUXILIARESGRUPOS.guardar();
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
        lblCODIGOGRUPOAUX = new utilesGUIx.JLabelCZ();
        txtCODIGOGRUPOAUX = new utilesGUIx.JTextFieldCZ();
        lblDESCRIPCION = new utilesGUIx.JLabelCZ();
        txtDESCRIPCION = new utilesGUIx.JTextFieldCZ();
        jPanelEspaciador = new javax.swing.JPanel();

        setLayout(new java.awt.GridBagLayout());

        jPanelGENERAL.setLayout(new java.awt.GridBagLayout());

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
    private utilesGUIx.JLabelCZ lblCODIGOGRUPOAUX;
    private utilesGUIx.JLabelCZ lblDESCRIPCION;
    private utilesGUIx.JTextFieldCZ txtCODIGOGRUPOAUX;
    private utilesGUIx.JTextFieldCZ txtDESCRIPCION;
    // End of variables declaration//GEN-END:variables
}
