/*
* JPanelTABLASINCRONIZACIONGENERAL.java
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

public class JPanelTABLASINCRONIZACIONGENERAL extends JPanelGENERALBASE {

    private JTEETABLASINCRONIZACIONGENERAL moTABLASINCRONIZACIONGENERAL;

    /** Creates new form JPanelTABLASINCRONIZACIONGENERAL*/
    public JPanelTABLASINCRONIZACIONGENERAL() {
        super();
        initComponents();
    }

    public void setDatos(final JTEETABLASINCRONIZACIONGENERAL poTABLASINCRONIZACIONGENERAL, final IPanelControlador poPadre, final IConsulta poConsulta) throws Exception {
        moTABLASINCRONIZACIONGENERAL = poTABLASINCRONIZACIONGENERAL;
        moPadre = poPadre;
        moConsulta = poConsulta;
        clonar(poConsulta.getList());
    }

    public String getTitulo() {
        String lsResult;
        if(moTABLASINCRONIZACIONGENERAL.moList.getModoTabla() == JListDatos.mclNuevo) {
            lsResult= JTEETABLASINCRONIZACIONGENERAL.msCTabla + " [Nuevo]" ;
        }else{
            lsResult=JTEETABLASINCRONIZACIONGENERAL.msCTabla  + 
                moTABLASINCRONIZACIONGENERAL.getNOMBRE().getString();
        }
        return lsResult;
    }

    public JSTabla getTabla(){
        return moTABLASINCRONIZACIONGENERAL;
    }

    public void rellenarPantalla() throws Exception {

        //ponemos los textos a los label
        lblNOMBRE.setField(moTABLASINCRONIZACIONGENERAL.getNOMBRE());
        txtNOMBRE.setField(moTABLASINCRONIZACIONGENERAL.getNOMBRE());
        lblVALOR.setField(moTABLASINCRONIZACIONGENERAL.getVALOR());
        txtVALOR.setField(moTABLASINCRONIZACIONGENERAL.getVALOR());
    }

    public void habilitarSegunEdicion() throws Exception {
        if(moTABLASINCRONIZACIONGENERAL.moList.getModoTabla() == JListDatos.mclNuevo) {
            txtNOMBRE.setEnabled(true);
        }else{
            txtNOMBRE.setEnabled(false);
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
        moTABLASINCRONIZACIONGENERAL.validarCampos();
    }

    public void aceptar() throws Exception {
        int lModo = moTABLASINCRONIZACIONGENERAL.moList.getModoTabla();
        IResultado loResult=moTABLASINCRONIZACIONGENERAL.moList.update(true);
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

        lblNOMBRE = new utilesGUIx.JLabelCZ();
        txtNOMBRE = new utilesGUIx.JTextFieldCZ();
        lblVALOR = new utilesGUIx.JLabelCZ();
        jScrollVALOR = new javax.swing.JScrollPane();
        txtVALOR = new utilesGUIx.JTextAreaCZ();
        jPanelEspaciador = new javax.swing.JPanel();

        setLayout(new java.awt.GridBagLayout());

        lblNOMBRE.setText("NOMBRE");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        add(lblNOMBRE, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        add(txtNOMBRE, gridBagConstraints);

        lblVALOR.setText("VALOR");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        add(lblVALOR, gridBagConstraints);

        txtVALOR.setColumns(20);
        txtVALOR.setRows(5);
        jScrollVALOR.setViewportView(txtVALOR);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        add(jScrollVALOR, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(jPanelEspaciador, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanelEspaciador;
    private javax.swing.JScrollPane jScrollVALOR;
    private utilesGUIx.JLabelCZ lblNOMBRE;
    private utilesGUIx.JLabelCZ lblVALOR;
    private utilesGUIx.JTextFieldCZ txtNOMBRE;
    private utilesGUIx.JTextAreaCZ txtVALOR;
    // End of variables declaration//GEN-END:variables
}
