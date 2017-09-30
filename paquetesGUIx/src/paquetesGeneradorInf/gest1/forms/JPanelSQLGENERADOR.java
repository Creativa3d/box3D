/*
* JPanelSQLGENERADOR.java
*
* Creado el 9/9/2013
*/

package paquetesGeneradorInf.gest1.forms;

import java.awt.Rectangle;

import ListDatos.*;
import paquetesGeneradorInf.gest1.JDatosGenerales;
import utilesGUIx.formsGenericos.*;
import utilesGUIx.formsGenericos.edicion.*;
import paquetesGeneradorInf.gest1.tablasExtend.*;
import paquetesGeneradorInf.gest1.*;
import utilesGUIx.Rectangulo;

public class JPanelSQLGENERADOR extends JPanelGENERALBASE {

    private JTEESQLGENERADOR moSQLGENERADOR;

    /** Creates new form JPanelSQLGENERADOR*/
    public JPanelSQLGENERADOR() {
        super();
        initComponents();
    }

    public void setDatos(final JTEESQLGENERADOR poSQLGENERADOR, final IPanelControlador poPadre) throws Exception {
        moSQLGENERADOR = poSQLGENERADOR;
        setDatos(poPadre);
        
    }

    public String getTitulo() {
        String lsResult;
        if(moSQLGENERADOR.moList.getModoTabla() == JListDatos.mclNuevo) {
            lsResult= JDatosGeneralesP.getDatosGenerales().getTextosForms().getTexto(JTEESQLGENERADOR.msCTabla) + " ["+JDatosGeneralesP.getDatosGenerales().getTextosForms().getTexto("Nuevo")+"]" ;
        }else{
            lsResult=JDatosGeneralesP.getDatosGenerales().getTextosForms().getTexto(JTEESQLGENERADOR.msCTabla)  + 
                moSQLGENERADOR.getCODIGOSQLGENERADOR().getString();
        }
        return lsResult;
    }

    public JSTabla getTabla(){
        return moSQLGENERADOR;
    }

    public void rellenarPantalla() throws Exception {

        //ponemos los textos a los label
        lblCODIGOSQLGENERADOR.setField(moSQLGENERADOR.getCODIGOSQLGENERADOR());
        txtCODIGOSQLGENERADOR.setField(moSQLGENERADOR.getCODIGOSQLGENERADOR());
        lblNOMBRE.setField(moSQLGENERADOR.getNOMBRE());
        txtNOMBRE.setField(moSQLGENERADOR.getNOMBRE());
        lblPALABRASCLAVE.setField(moSQLGENERADOR.getPALABRASCLAVE());
        txtPALABRASCLAVE.setField(moSQLGENERADOR.getPALABRASCLAVE());
        lblPADRE.setField(moSQLGENERADOR.getPADRE());
        txtPADRE.setField(moSQLGENERADOR.getPADRE());
        lblTABLAPRINCIPAL.setField(moSQLGENERADOR.getTABLAPRINCIPAL());
        txtTABLAPRINCIPAL.setField(moSQLGENERADOR.getTABLAPRINCIPAL());
        lblSQL.setField(moSQLGENERADOR.getSQL());
        txtSQL.setField(moSQLGENERADOR.getSQL());
        lblOBSERVACIONES.setField(moSQLGENERADOR.getOBSERVACIONES());
        txtOBSERVACIONES.setField(moSQLGENERADOR.getOBSERVACIONES());
    }

    public void habilitarSegunEdicion() throws Exception {
        if(moSQLGENERADOR.moList.getModoTabla() == JListDatos.mclNuevo) {
            txtCODIGOSQLGENERADOR.setEnabled(true);
        }else{
            txtCODIGOSQLGENERADOR.setEnabled(false);
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
        moSQLGENERADOR.validarCampos();
    }

    public void aceptar() throws Exception {
        int lModo = getModoTabla();
        IResultado loResult=moSQLGENERADOR.guardar();
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
//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        lblCODIGOSQLGENERADOR = new utilesGUIx.JLabelCZ();
        txtCODIGOSQLGENERADOR = new utilesGUIx.JTextFieldCZ();
        lblNOMBRE = new utilesGUIx.JLabelCZ();
        txtNOMBRE = new utilesGUIx.JTextFieldCZ();
        lblPALABRASCLAVE = new utilesGUIx.JLabelCZ();
        txtPALABRASCLAVE = new utilesGUIx.JTextFieldCZ();
        lblPADRE = new utilesGUIx.JLabelCZ();
        txtPADRE = new utilesGUIx.JTextFieldCZ();
        lblTABLAPRINCIPAL = new utilesGUIx.JLabelCZ();
        txtTABLAPRINCIPAL = new utilesGUIx.JTextFieldCZ();
        lblSQL = new utilesGUIx.JLabelCZ();
        jScrollSQL = new javax.swing.JScrollPane();
        txtSQL = new utilesGUIx.JTextAreaCZ();
        lblOBSERVACIONES = new utilesGUIx.JLabelCZ();
        jScrollOBSERVACIONES = new javax.swing.JScrollPane();
        txtOBSERVACIONES = new utilesGUIx.JTextAreaCZ();
        jPanelEspaciador = new javax.swing.JPanel();

        setLayout(new java.awt.GridBagLayout());


        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        lblCODIGOSQLGENERADOR.setText("CODIGOSQLGENERADOR");
        add(lblCODIGOSQLGENERADOR, new java.awt.GridBagConstraints());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        add(txtCODIGOSQLGENERADOR, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        lblNOMBRE.setText("NOMBRE");
        add(lblNOMBRE, new java.awt.GridBagConstraints());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        add(txtNOMBRE, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        lblPALABRASCLAVE.setText("PALABRASCLAVE");
        add(lblPALABRASCLAVE, new java.awt.GridBagConstraints());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        add(txtPALABRASCLAVE, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        lblPADRE.setText("PADRE");
        add(lblPADRE, new java.awt.GridBagConstraints());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        add(txtPADRE, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        lblTABLAPRINCIPAL.setText("TABLAPRINCIPAL");
        add(lblTABLAPRINCIPAL, new java.awt.GridBagConstraints());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        add(txtTABLAPRINCIPAL, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        lblSQL.setText("SQL");
        add(lblSQL, new java.awt.GridBagConstraints());
        jScrollSQL.setViewportView(txtSQL);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        add(jScrollSQL, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        lblOBSERVACIONES.setText("OBSERVACIONES");
        add(lblOBSERVACIONES, new java.awt.GridBagConstraints());
        jScrollOBSERVACIONES.setViewportView(txtOBSERVACIONES);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        add(jScrollOBSERVACIONES, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        add(jPanelEspaciador, gridBagConstraints);


    }
//GEN-END:initComponents


//GEN-BEGIN:variables
    // Declaracion de controles No modificar!!
    private javax.swing.JPanel jPanelEspaciador;
    private utilesGUIx.JLabelCZ lblCODIGOSQLGENERADOR;
    private utilesGUIx.JTextFieldCZ txtCODIGOSQLGENERADOR;
    private utilesGUIx.JLabelCZ lblNOMBRE;
    private utilesGUIx.JTextFieldCZ txtNOMBRE;
    private utilesGUIx.JLabelCZ lblPALABRASCLAVE;
    private utilesGUIx.JTextFieldCZ txtPALABRASCLAVE;
    private utilesGUIx.JLabelCZ lblPADRE;
    private utilesGUIx.JTextFieldCZ txtPADRE;
    private utilesGUIx.JLabelCZ lblTABLAPRINCIPAL;
    private utilesGUIx.JTextFieldCZ txtTABLAPRINCIPAL;
    private utilesGUIx.JLabelCZ lblSQL;
    private javax.swing.JScrollPane jScrollSQL;
    private utilesGUIx.JTextAreaCZ txtSQL;
    private utilesGUIx.JLabelCZ lblOBSERVACIONES;
    private javax.swing.JScrollPane jScrollOBSERVACIONES;
    private utilesGUIx.JTextAreaCZ txtOBSERVACIONES;
    // Fin de la declaracion de controles
//GEN-END:variables
}
