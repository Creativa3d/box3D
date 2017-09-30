/*
* JPanelGUIXCALENDARIO.java
*
* Creado el 15/3/2012
*/

package utilesGUIxAvisos.forms;



import utilesGUIxAvisos.tablasExtend.JTEEGUIXCALENDARIO;
import utilesGUIx.Rectangulo;
import ListDatos.*;
import utilesGUI.tiposTextos.JTipoTextoEstandar;
import utilesGUIx.formsGenericos.*;
import utilesGUIx.formsGenericos.edicion.*;
import utilesGUIx.formsGenericos.busqueda.*;
import utilesGUIxAvisos.calendario.JDatosGenerales;

import utilesGUIxAvisos.tablasExtend.*;

public class JPanelGUIXCALENDARIO extends JPanelGENERALBASE {

    private JTEEGUIXCALENDARIO moGUIXCALENDARIO;
    private JDatosGenerales moDatosGenerales;

    /** Creates new form JPanelGUIXCALENDARIO*/
    public JPanelGUIXCALENDARIO() {
        super();
        initComponents();
    }

    public void setPKInvisible() {
        lblCALENDARIO.setVisible(false);
        txtCALENDARIO.setVisible(false);
    }

    public void setDatos(JDatosGenerales poDatosGenerales, final JTEEGUIXCALENDARIO poGUIXCALENDARIO, final IPanelControlador poPadre, final IConsulta poConsulta) throws Exception {
        moGUIXCALENDARIO = poGUIXCALENDARIO;
        moPadre = poPadre;
        moConsulta = poConsulta;
        moDatosGenerales=poDatosGenerales;
        
        if(poConsulta!=null){
            clonar(poConsulta.getList());
        }
    }

    public String getTitulo() {
        String lsResult;
        if(moGUIXCALENDARIO.moList.getModoTabla() == JListDatos.mclNuevo) {
            lsResult= moDatosGenerales.getTextosForms().getTexto(JTEEGUIXCALENDARIO.msCTabla) + " [Nuevo]" ;
        }else{
            lsResult=moDatosGenerales.getTextosForms().getTexto(JTEEGUIXCALENDARIO.msCTabla)  + 
                moGUIXCALENDARIO.getCALENDARIO().getString();
        }
        return lsResult;
    }

    public JSTabla getTabla(){
        return moGUIXCALENDARIO;
    }

    public void rellenarPantalla() throws Exception {

        //ponemos los textos a los label
        lblCALENDARIO.setField(moGUIXCALENDARIO.getCALENDARIO());
        txtCALENDARIO.setField(moGUIXCALENDARIO.getCALENDARIO());
        lblNOMBRE.setField(moGUIXCALENDARIO.getNOMBRE());
        txtNOMBRE.setField(moGUIXCALENDARIO.getNOMBRE());
        lblCLIENTID.setField(moGUIXCALENDARIO.getCLIENTID());
        txtCLIENTID.setField(moGUIXCALENDARIO.getCLIENTID());
        lblCLIENTSECRET.setField(moGUIXCALENDARIO.getCLIENTSECRET());
    }

    public void habilitarSegunEdicion() throws Exception {
        if(moGUIXCALENDARIO.moList.getModoTabla() == JListDatos.mclNuevo) {
            txtCALENDARIO.setEnabled(true);
        }else{
            txtCALENDARIO.setEnabled(false);
        }
        setPKInvisible();
    }

    public void ponerTipoTextos() throws Exception {
        txtCLIENTID.setTipo(JTipoTextoEstandar.mclTextCadena);
    }

    public void mostrarDatos() throws Exception {
        IFilaDatos loFila;
        txtCLIENTSECRET.setText(moGUIXCALENDARIO.getCLIENTSECRET().getString());

        //Establecemos los valores de los paneles si hay
        //jPanelColectorEntrada.setValueTabla(moAlbaran.getCOLECTORENTRADA().getString());
    }

    public void establecerDatos() throws Exception {
        moGUIXCALENDARIO.getCLIENTSECRET().setValue(txtCLIENTSECRET.getText());
        moGUIXCALENDARIO.validarCampos();
    }

    public void aceptar() throws Exception {
        int lModo = getModoTabla();
        IResultado loResult=moGUIXCALENDARIO.guardar();
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
        lblCALENDARIO = new utilesGUIx.JLabelCZ();
        txtCALENDARIO = new utilesGUIx.JTextFieldCZ();
        lblNOMBRE = new utilesGUIx.JLabelCZ();
        txtNOMBRE = new utilesGUIx.JTextFieldCZ();
        jPanel1 = new javax.swing.JPanel();
        lblCLIENTID = new utilesGUIx.JLabelCZ();
        txtCLIENTID = new utilesGUIx.JTextFieldCZ();
        lblCLIENTSECRET = new utilesGUIx.JLabelCZ();
        txtCLIENTSECRET = new javax.swing.JPasswordField();
        jPanelEspaciador = new javax.swing.JPanel();

        setLayout(new java.awt.GridBagLayout());

        jPanelGENERAL.setLayout(new java.awt.GridBagLayout());

        lblCALENDARIO.setText("CALENDARIO");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanelGENERAL.add(lblCALENDARIO, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanelGENERAL.add(txtCALENDARIO, gridBagConstraints);

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

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Sincronización GMAIL"));
        jPanel1.setLayout(new java.awt.GridBagLayout());

        lblCLIENTID.setText("CLIENTID");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanel1.add(lblCLIENTID, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanel1.add(txtCLIENTID, gridBagConstraints);

        lblCLIENTSECRET.setText("CLIENTSECRET");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanel1.add(lblCLIENTSECRET, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanel1.add(txtCLIENTSECRET, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanelGENERAL.add(jPanel1, gridBagConstraints);
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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelEspaciador;
    private javax.swing.JPanel jPanelGENERAL;
    private javax.swing.JTabbedPane jTabbedPane1;
    private utilesGUIx.JLabelCZ lblCALENDARIO;
    private utilesGUIx.JLabelCZ lblCLIENTID;
    private utilesGUIx.JLabelCZ lblCLIENTSECRET;
    private utilesGUIx.JLabelCZ lblNOMBRE;
    private utilesGUIx.JTextFieldCZ txtCALENDARIO;
    private utilesGUIx.JTextFieldCZ txtCLIENTID;
    private javax.swing.JPasswordField txtCLIENTSECRET;
    private utilesGUIx.JTextFieldCZ txtNOMBRE;
    // End of variables declaration//GEN-END:variables
}
