/*
* JPanelSQLGENERADORATRIB.java
*
* Creado el 9/9/2013
*/

package paquetesGeneradorInf.gest1.forms;

import java.awt.Rectangle;

import ListDatos.*;
import utilesGUIx.formsGenericos.*;
import utilesGUIx.formsGenericos.edicion.*;
import paquetesGeneradorInf.gest1.tablasExtend.*;
import paquetesGeneradorInf.gest1.*;
import utilesGUIx.Rectangulo;

public class JPanelSQLGENERADORATRIB extends JPanelGENERALBASE {

    private JTEESQLGENERADORATRIB moSQLGENERADORATRIB;

    /** Creates new form JPanelSQLGENERADORATRIB*/
    public JPanelSQLGENERADORATRIB() {
        super();
        initComponents();
    }

    public void setDatos(final JTEESQLGENERADORATRIB poSQLGENERADORATRIB, final IPanelControlador poPadre) throws Exception {
        moSQLGENERADORATRIB = poSQLGENERADORATRIB;
        setDatos(poPadre);
    }

    public String getTitulo() {
        String lsResult;
        if(moSQLGENERADORATRIB.moList.getModoTabla() == JListDatos.mclNuevo) {
            lsResult= JDatosGeneralesP.getDatosGenerales().getTextosForms().getTexto(JTEESQLGENERADORATRIB.msCTabla) + " ["+JDatosGeneralesP.getDatosGenerales().getTextosForms().getTexto("Nuevo")+"]" ;
        }else{
            lsResult=JDatosGeneralesP.getDatosGenerales().getTextosForms().getTexto(JTEESQLGENERADORATRIB.msCTabla)  + 
                moSQLGENERADORATRIB.getCODIGOSQLGENERADOR().getString() + "-" +
                moSQLGENERADORATRIB.getATRIBUTODEF().getString();
        }
        return lsResult;
    }

    public JSTabla getTabla(){
        return moSQLGENERADORATRIB;
    }

    public void rellenarPantalla() throws Exception {

        //ponemos los textos a los label
        lblCODIGOSQLGENERADOR.setField(moSQLGENERADORATRIB.getCODIGOSQLGENERADOR());
        txtCODIGOSQLGENERADOR.setField(moSQLGENERADORATRIB.getCODIGOSQLGENERADOR());
        lblATRIBUTODEF.setField(moSQLGENERADORATRIB.getATRIBUTODEF());
        txtATRIBUTODEF.setField(moSQLGENERADORATRIB.getATRIBUTODEF());
        lblVALOR.setField(moSQLGENERADORATRIB.getVALOR());
        txtVALOR.setField(moSQLGENERADORATRIB.getVALOR());
    }

    public void habilitarSegunEdicion() throws Exception {
        if(moSQLGENERADORATRIB.moList.getModoTabla() == JListDatos.mclNuevo) {
            txtCODIGOSQLGENERADOR.setEnabled(true);
            txtATRIBUTODEF.setEnabled(true);
        }else{
            txtCODIGOSQLGENERADOR.setEnabled(false);
            txtATRIBUTODEF.setEnabled(false);
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
        moSQLGENERADORATRIB.validarCampos();
    }

    public void aceptar() throws Exception {
        int lModo = getModoTabla();
        IResultado loResult=moSQLGENERADORATRIB.guardar();
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
        lblATRIBUTODEF = new utilesGUIx.JLabelCZ();
        txtATRIBUTODEF = new utilesGUIx.JTextFieldCZ();
        lblVALOR = new utilesGUIx.JLabelCZ();
        txtVALOR = new utilesGUIx.JTextFieldCZ();
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
        lblATRIBUTODEF.setText("ATRIBUTODEF");
        add(lblATRIBUTODEF, new java.awt.GridBagConstraints());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        add(txtATRIBUTODEF, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        lblVALOR.setText("VALOR");
        add(lblVALOR, new java.awt.GridBagConstraints());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        add(txtVALOR, gridBagConstraints);
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
    private utilesGUIx.JLabelCZ lblATRIBUTODEF;
    private utilesGUIx.JTextFieldCZ txtATRIBUTODEF;
    private utilesGUIx.JLabelCZ lblVALOR;
    private utilesGUIx.JTextFieldCZ txtVALOR;
    // Fin de la declaracion de controles
//GEN-END:variables
}
