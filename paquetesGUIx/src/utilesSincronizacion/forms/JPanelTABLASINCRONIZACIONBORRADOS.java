/*
* JPanelTABLASINCRONIZACIONBORRADOS.java
*
* Creado el 2/10/2008
*/

package utilesSincronizacion.forms;

import java.awt.Rectangle;
import utiles.JDepuracion;

import ListDatos.*;
import utilesGUIx.Rectangulo;
import utilesGUIx.formsGenericos.*;
import utilesGUIx.formsGenericos.edicion.*;
import utilesGUIx.formsGenericos.busqueda.*;

import utilesSincronizacion.tablasExtend.*;

public class JPanelTABLASINCRONIZACIONBORRADOS extends JPanelGENERALBASE {

    private JTEETABLASINCRONIZACIONBORRADOS moTABLASINCRONIZACIONBORRADOS;

    /** Creates new form JPanelTABLASINCRONIZACIONBORRADOS*/
    public JPanelTABLASINCRONIZACIONBORRADOS() {
        super();
        initComponents();
    }

    public void setDatos(final JTEETABLASINCRONIZACIONBORRADOS poTABLASINCRONIZACIONBORRADOS, final IPanelControlador poPadre, final IConsulta poConsulta) throws Exception {
        moTABLASINCRONIZACIONBORRADOS = poTABLASINCRONIZACIONBORRADOS;
        moPadre = poPadre;
        moConsulta = poConsulta;
        clonar(poConsulta.getList());
    }

    public String getTitulo() {
        String lsResult;
        if(moTABLASINCRONIZACIONBORRADOS.moList.getModoTabla() == JListDatos.mclNuevo) {
            lsResult= JTEETABLASINCRONIZACIONBORRADOS.msCTabla + " [Nuevo]" ;
        }else{
            lsResult=JTEETABLASINCRONIZACIONBORRADOS.msCTabla  + 
                moTABLASINCRONIZACIONBORRADOS.getCODIGOBORRADO().getString();
        }
        return lsResult;
    }

    public JSTabla getTabla(){
        return moTABLASINCRONIZACIONBORRADOS;
    }

    public void rellenarPantalla() throws Exception {

        //ponemos los textos a los label
        lblCODIGOBORRADO.setField(moTABLASINCRONIZACIONBORRADOS.getCODIGOBORRADO());
        txtCODIGOBORRADO.setField(moTABLASINCRONIZACIONBORRADOS.getCODIGOBORRADO());
        lblTABLA.setField(moTABLASINCRONIZACIONBORRADOS.getTABLA());
        txtTABLA.setField(moTABLASINCRONIZACIONBORRADOS.getTABLA());
        lblREGISTRO.setField(moTABLASINCRONIZACIONBORRADOS.getREGISTRO());
        txtREGISTRO.setField(moTABLASINCRONIZACIONBORRADOS.getREGISTRO());
        lblNUMEROTRANSACSINCRO.setField(moTABLASINCRONIZACIONBORRADOS.getNUMEROTRANSACSINCRO());
        txtNUMEROTRANSACSINCRO.setField(moTABLASINCRONIZACIONBORRADOS.getNUMEROTRANSACSINCRO());
    }

    public void habilitarSegunEdicion() throws Exception {
        if(moTABLASINCRONIZACIONBORRADOS.moList.getModoTabla() == JListDatos.mclNuevo) {
            txtCODIGOBORRADO.setEnabled(true);
        }else{
            txtCODIGOBORRADO.setEnabled(false);
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
        moTABLASINCRONIZACIONBORRADOS.validarCampos();
    }

    public void aceptar() throws Exception {
        int lModo = moTABLASINCRONIZACIONBORRADOS.moList.getModoTabla();
        IResultado loResult=moTABLASINCRONIZACIONBORRADOS.moList.update(true);
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

        lblCODIGOBORRADO = new utilesGUIx.JLabelCZ();
        txtCODIGOBORRADO = new utilesGUIx.JTextFieldCZ();
        lblTABLA = new utilesGUIx.JLabelCZ();
        txtTABLA = new utilesGUIx.JTextFieldCZ();
        lblREGISTRO = new utilesGUIx.JLabelCZ();
        jScrollREGISTRO = new javax.swing.JScrollPane();
        txtREGISTRO = new utilesGUIx.JTextAreaCZ();
        lblNUMEROTRANSACSINCRO = new utilesGUIx.JLabelCZ();
        txtNUMEROTRANSACSINCRO = new utilesGUIx.JTextFieldCZ();
        jPanelEspaciador = new javax.swing.JPanel();

        setLayout(new java.awt.GridBagLayout());

        lblCODIGOBORRADO.setText("CODIGOBORRADO");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        add(lblCODIGOBORRADO, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        add(txtCODIGOBORRADO, gridBagConstraints);

        lblTABLA.setText("TABLA");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        add(lblTABLA, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        add(txtTABLA, gridBagConstraints);

        lblREGISTRO.setText("REGISTRO");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        add(lblREGISTRO, gridBagConstraints);

        txtREGISTRO.setColumns(20);
        txtREGISTRO.setRows(5);
        jScrollREGISTRO.setViewportView(txtREGISTRO);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        add(jScrollREGISTRO, gridBagConstraints);

        lblNUMEROTRANSACSINCRO.setText("NUMEROTRANSACSINCRO");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        add(lblNUMEROTRANSACSINCRO, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        add(txtNUMEROTRANSACSINCRO, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(jPanelEspaciador, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanelEspaciador;
    private javax.swing.JScrollPane jScrollREGISTRO;
    private utilesGUIx.JLabelCZ lblCODIGOBORRADO;
    private utilesGUIx.JLabelCZ lblNUMEROTRANSACSINCRO;
    private utilesGUIx.JLabelCZ lblREGISTRO;
    private utilesGUIx.JLabelCZ lblTABLA;
    private utilesGUIx.JTextFieldCZ txtCODIGOBORRADO;
    private utilesGUIx.JTextFieldCZ txtNUMEROTRANSACSINCRO;
    private utilesGUIx.JTextAreaCZ txtREGISTRO;
    private utilesGUIx.JTextFieldCZ txtTABLA;
    // End of variables declaration//GEN-END:variables
}
