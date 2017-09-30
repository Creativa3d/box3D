/*
* JPanelGUIXMENSAJESSEND.java
*
* Creado el 8/9/2012
*/

package utilesGUIxAvisos.forms;



import utilesGUIxAvisos.tablasExtend.JTEEGUIXMENSAJESBD;
import utilesGUIx.Rectangulo;
import ListDatos.*;
import utilesGUIx.formsGenericos.*;
import utilesGUIx.formsGenericos.edicion.*;
import utilesGUIx.formsGenericos.busqueda.*;
import utilesGUIxAvisos.calendario.JDatosGenerales;

import utilesGUIxAvisos.tablasExtend.*;

public class JPanelGUIXMENSAJESSEND extends JPanelGENERALBASE {

    private JTEEGUIXMENSAJESBD moGUIXMENSAJESSEND;
    private JDatosGenerales moDatosGenerales;

    /** Creates new form JPanelGUIXMENSAJESSEND*/
    public JPanelGUIXMENSAJESSEND() {
        super();
        initComponents();
    }

    public void setDatos(JDatosGenerales poDatosGenerales, final JTEEGUIXMENSAJESBD poGUIXMENSAJESSEND, final IPanelControlador poPadre, final IConsulta poConsulta) throws Exception {
        moGUIXMENSAJESSEND = poGUIXMENSAJESSEND;
        moPadre = poPadre;
        moConsulta = poConsulta;
        moDatosGenerales=poDatosGenerales;
        
        if(poConsulta!=null){
            clonar(poConsulta.getList());
        }
    }

    public String getTitulo() {
        String lsResult;
        if(moGUIXMENSAJESSEND.moList.getModoTabla() == JListDatos.mclNuevo) {
            lsResult= moDatosGenerales.getTextosForms().getTexto(JTEEGUIXMENSAJESBD.msCTabla) + " [Nuevo]" ;
        }else{
            lsResult=moDatosGenerales.getTextosForms().getTexto(JTEEGUIXMENSAJESBD.msCTabla)  + 
                moGUIXMENSAJESSEND.getCODIGO().getString();
        }
        return lsResult;
    }

    public JSTabla getTabla(){
        return moGUIXMENSAJESSEND;
    }

    public void rellenarPantalla() throws Exception {

        //ponemos los textos a los label
        lblGRUPO.setField(moGUIXMENSAJESSEND.getGRUPO());
        txtGRUPO.setField(moGUIXMENSAJESSEND.getGRUPO());
        lblUSUARIO.setField(moGUIXMENSAJESSEND.getUSUARIO());
        txtUSUARIO.setField(moGUIXMENSAJESSEND.getUSUARIO());
        lblFECHA.setField(moGUIXMENSAJESSEND.getFECHA());
        txtFECHA.setField(moGUIXMENSAJESSEND.getFECHA());
        lblEMAILTO.setField(moGUIXMENSAJESSEND.getEMAILTO());
        txtEMAILTO.setField(moGUIXMENSAJESSEND.getEMAILTO());
        lblASUNTO.setField(moGUIXMENSAJESSEND.getASUNTO());
        txtASUNTO.setField(moGUIXMENSAJESSEND.getASUNTO());
        lblTEXTO.setField(moGUIXMENSAJESSEND.getTEXTO());
        txtTEXTO.setField(moGUIXMENSAJESSEND.getTEXTO());
        lblADJUNTOS.setField(moGUIXMENSAJESSEND.getADJUNTOS());
        txtADJUNTOS.setField(moGUIXMENSAJESSEND.getADJUNTOS());
    }

    public void habilitarSegunEdicion() throws Exception {

    }

    public void ponerTipoTextos() throws Exception {
    }

    public void mostrarDatos() throws Exception {
        IFilaDatos loFila;

        //Establecemos los valores de los paneles si hay
        //jPanelColectorEntrada.setValueTabla(moAlbaran.getCOLECTORENTRADA().getString());
    }

    public void establecerDatos() throws Exception {
        moGUIXMENSAJESSEND.validarCampos();
    }

    public void aceptar() throws Exception {
        int lModo = getModoTabla();
        IResultado loResult=moGUIXMENSAJESSEND.guardar();
        if(loResult.getBien()){
             actualizarPadre(lModo);
        }else{
            throw new Exception(loResult.getMensaje());
        }
    }

    public Rectangulo getTanano(){
        return new Rectangulo(0,0, 740, 540);
    }

    /** Este metodo es llamado desde el constructor para
     *  inicializar el formulario.
     *  AVISO: No modificar este codigo. El contenido de este metodo es
     *  siempre regenerado por el editor de formularios.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        lblGRUPO = new utilesGUIx.JLabelCZ();
        txtGRUPO = new utilesGUIx.JTextFieldCZ();
        lblUSUARIO = new utilesGUIx.JLabelCZ();
        txtUSUARIO = new utilesGUIx.JTextFieldCZ();
        lblFECHA = new utilesGUIx.JLabelCZ();
        txtFECHA = new utilesGUIx.JTextFieldCZ();
        lblEMAILTO = new utilesGUIx.JLabelCZ();
        jScrollEMAILTO = new javax.swing.JScrollPane();
        txtEMAILTO = new utilesGUIx.JTextAreaCZ();
        lblASUNTO = new utilesGUIx.JLabelCZ();
        jScrollASUNTO = new javax.swing.JScrollPane();
        txtASUNTO = new utilesGUIx.JTextAreaCZ();
        lblTEXTO = new utilesGUIx.JLabelCZ();
        jScrollTEXTO = new javax.swing.JScrollPane();
        txtTEXTO = new utilesGUIx.JTextAreaCZ();
        lblADJUNTOS = new utilesGUIx.JLabelCZ();
        jScrollADJUNTOS = new javax.swing.JScrollPane();
        txtADJUNTOS = new utilesGUIx.JTextAreaCZ();

        setLayout(new java.awt.GridBagLayout());

        lblGRUPO.setText("GRUPO");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        add(lblGRUPO, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        add(txtGRUPO, gridBagConstraints);

        lblUSUARIO.setText("USUARIO");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        add(lblUSUARIO, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        add(txtUSUARIO, gridBagConstraints);

        lblFECHA.setText("FECHA");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        add(lblFECHA, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        add(txtFECHA, gridBagConstraints);

        lblEMAILTO.setText("EMAILTO");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        add(lblEMAILTO, gridBagConstraints);

        txtEMAILTO.setColumns(20);
        txtEMAILTO.setRows(5);
        jScrollEMAILTO.setViewportView(txtEMAILTO);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        add(jScrollEMAILTO, gridBagConstraints);

        lblASUNTO.setText("ASUNTO");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        add(lblASUNTO, gridBagConstraints);

        txtASUNTO.setColumns(20);
        txtASUNTO.setRows(5);
        jScrollASUNTO.setViewportView(txtASUNTO);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        add(jScrollASUNTO, gridBagConstraints);

        lblTEXTO.setText("TEXTO");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        add(lblTEXTO, gridBagConstraints);

        txtTEXTO.setColumns(20);
        txtTEXTO.setRows(5);
        jScrollTEXTO.setViewportView(txtTEXTO);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        add(jScrollTEXTO, gridBagConstraints);

        lblADJUNTOS.setText("ADJUNTOS");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        add(lblADJUNTOS, gridBagConstraints);

        txtADJUNTOS.setColumns(20);
        txtADJUNTOS.setRows(5);
        jScrollADJUNTOS.setViewportView(txtADJUNTOS);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        add(jScrollADJUNTOS, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollADJUNTOS;
    private javax.swing.JScrollPane jScrollASUNTO;
    private javax.swing.JScrollPane jScrollEMAILTO;
    private javax.swing.JScrollPane jScrollTEXTO;
    private utilesGUIx.JLabelCZ lblADJUNTOS;
    private utilesGUIx.JLabelCZ lblASUNTO;
    private utilesGUIx.JLabelCZ lblEMAILTO;
    private utilesGUIx.JLabelCZ lblFECHA;
    private utilesGUIx.JLabelCZ lblGRUPO;
    private utilesGUIx.JLabelCZ lblTEXTO;
    private utilesGUIx.JLabelCZ lblUSUARIO;
    private utilesGUIx.JTextAreaCZ txtADJUNTOS;
    private utilesGUIx.JTextAreaCZ txtASUNTO;
    private utilesGUIx.JTextAreaCZ txtEMAILTO;
    private utilesGUIx.JTextFieldCZ txtFECHA;
    private utilesGUIx.JTextFieldCZ txtGRUPO;
    private utilesGUIx.JTextAreaCZ txtTEXTO;
    private utilesGUIx.JTextFieldCZ txtUSUARIO;
    // End of variables declaration//GEN-END:variables
}
