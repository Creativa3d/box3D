/*
* JPanelGUIXAVISOS.java
*
* Creado el 3/11/2011
*/

package utilesGUIxAvisos.forms;



import utilesGUIxAvisos.tablasExtend.JTEEGUIXAVISOS;
import utilesGUIx.Rectangulo;
import ListDatos.*;
import java.awt.Color;
import utilesGUIx.formsGenericos.*;
import utilesGUIx.formsGenericos.edicion.*;
import utilesGUIx.formsGenericos.busqueda.*;
import utilesGUIxAvisos.calendario.JDatosGenerales;


public class JPanelGUIXAVISOS extends JPanelGENERALBASE {

    private JTEEGUIXAVISOS moGUIXAVISOS;
    private JDatosGenerales moDatosGenerales;

    /** Creates new form JPanelGUIXAVISOS*/
    public JPanelGUIXAVISOS() {
        super();
        initComponents();
    }

    public void setDatos(JDatosGenerales poDatosGenerales, final JTEEGUIXAVISOS poGUIXAVISOS, final IPanelControlador poPadre, final IConsulta poConsulta) throws Exception {
        moGUIXAVISOS = poGUIXAVISOS;
        moPadre = poPadre;
        moConsulta = poConsulta;
        moDatosGenerales=poDatosGenerales;
        
        if(poConsulta!=null){
            clonar(poConsulta.getList());
        }
    }

    @Override
    public String getTitulo() {
        String lsResult;
        if(moGUIXAVISOS.moList.getModoTabla() == JListDatos.mclNuevo) {
            lsResult= moDatosGenerales.getTextosForms().getTexto(JTEEGUIXAVISOS.msCTabla) + " [Nuevo]" ;
        }else{
            lsResult=moDatosGenerales.getTextosForms().getTexto(JTEEGUIXAVISOS.msCTabla)  + 
                moGUIXAVISOS.getCODIGO().getString();
        }
        return lsResult;
    }

    @Override
    public JSTabla getTabla(){
        return moGUIXAVISOS;
    }

    @Override
    public void rellenarPantalla() throws Exception {

//        //ponemos los textos a los label
//        lblCODIGO.setField(moGUIXAVISOS.getCODIGO());
//        txtCODIGO.setField(moGUIXAVISOS.getCODIGO());

        lblFECHACONCRETA.setField(moGUIXAVISOS.getFECHACONCRETA());
        txtFECHACONCRETA.setField(moGUIXAVISOS.getFECHACONCRETA());
        jCheckPANTALLASN.setField(moGUIXAVISOS.getPANTALLASN());
        jCheckAVISADOSN.setField(moGUIXAVISOS.getAVISADOSN());
        lblTELF.setField(moGUIXAVISOS.getTELF());
        txtTELF.setField(moGUIXAVISOS.getTELF());
        lblSENDER.setField(moGUIXAVISOS.getSENDER());
        txtSENDER.setField(moGUIXAVISOS.getSENDER());
        lblEMAIL.setField(moGUIXAVISOS.getEMAIL());
        txtEMAIL.setField(moGUIXAVISOS.getEMAIL());
        
        jCheckAVISADOSN.setForeground(Color.WHITE);
    }

    @Override
    public void habilitarSegunEdicion() throws Exception {

        
        lblTELF.setVisible(moDatosGenerales.isSMS());
        txtTELF.setVisible(moDatosGenerales.isSMS());
        lblSENDER.setVisible(moDatosGenerales.isSMS());
        txtSENDER.setVisible(moDatosGenerales.isSMS());
        lblEMAIL.setVisible(moDatosGenerales.iseMail());
        txtEMAIL.setVisible(moDatosGenerales.iseMail());
    }

    @Override
    public void ponerTipoTextos() throws Exception {
        
    }

    @Override
    public void mostrarDatos() throws Exception {
        IFilaDatos loFila;
        jCheckAVISADOSN.setForeground(Color.WHITE);
        //Establecemos los valores de los paneles si hay
        //jPanelColectorEntrada.setValueTabla(moAlbaran.getCOLECTORENTRADA().getString());
    }

    @Override
    public void establecerDatos() throws Exception {
        moGUIXAVISOS.validarCampos();
    }

    @Override
    public void aceptar() throws Exception {
        int lModo = getModoTabla();
        IResultado loResult=moGUIXAVISOS.guardar();
        if(loResult.getBien()){
             actualizarPadre(lModo);
        }else{
            throw new Exception(loResult.getMensaje());
        }
    }

    @Override
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
        jCheckAVISADOSN = new utilesGUIx.JCheckBoxCZ();
        lblFECHACONCRETA = new utilesGUIx.JLabelCZ();
        txtFECHACONCRETA = new utilesGUIx.JTextFieldCZ();
        jCheckPANTALLASN = new utilesGUIx.JCheckBoxCZ();
        jPanel1 = new javax.swing.JPanel();
        lblTELF = new utilesGUIx.JLabelCZ();
        txtTELF = new utilesGUIx.JTextFieldCZ();
        lblSENDER = new utilesGUIx.JLabelCZ();
        txtSENDER = new utilesGUIx.JTextFieldCZ();
        lblEMAIL = new utilesGUIx.JLabelCZ();
        txtEMAIL = new utilesGUIx.JTextFieldCZ();
        jPanelEspaciador = new javax.swing.JPanel();

        setLayout(new java.awt.GridBagLayout());

        jPanelGENERAL.setLayout(new java.awt.GridBagLayout());

        jCheckAVISADOSN.setBackground(new java.awt.Color(0, 102, 51));
        jCheckAVISADOSN.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jCheckAVISADOSN.setForeground(new java.awt.Color(255, 255, 255));
        jCheckAVISADOSN.setText("AVISADOSN");
        jCheckAVISADOSN.setMargin(new java.awt.Insets(0, 0, 0, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanelGENERAL.add(jCheckAVISADOSN, gridBagConstraints);

        lblFECHACONCRETA.setText("FECHACONCRETA");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanelGENERAL.add(lblFECHACONCRETA, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanelGENERAL.add(txtFECHACONCRETA, gridBagConstraints);

        jCheckPANTALLASN.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jCheckPANTALLASN.setText("PANTALLASN");
        jCheckPANTALLASN.setMargin(new java.awt.Insets(0, 0, 0, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanelGENERAL.add(jCheckPANTALLASN, gridBagConstraints);

        jPanel1.setLayout(new java.awt.GridBagLayout());

        lblTELF.setText("TELF");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanel1.add(lblTELF, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanel1.add(txtTELF, gridBagConstraints);

        lblSENDER.setText("SENDER");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanel1.add(lblSENDER, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanel1.add(txtSENDER, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanelGENERAL.add(jPanel1, gridBagConstraints);

        lblEMAIL.setText("EMAIL");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanelGENERAL.add(lblEMAIL, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanelGENERAL.add(txtEMAIL, gridBagConstraints);
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
    private utilesGUIx.JCheckBoxCZ jCheckAVISADOSN;
    private utilesGUIx.JCheckBoxCZ jCheckPANTALLASN;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelEspaciador;
    private javax.swing.JPanel jPanelGENERAL;
    private javax.swing.JTabbedPane jTabbedPane1;
    private utilesGUIx.JLabelCZ lblEMAIL;
    private utilesGUIx.JLabelCZ lblFECHACONCRETA;
    private utilesGUIx.JLabelCZ lblSENDER;
    private utilesGUIx.JLabelCZ lblTELF;
    private utilesGUIx.JTextFieldCZ txtEMAIL;
    private utilesGUIx.JTextFieldCZ txtFECHACONCRETA;
    private utilesGUIx.JTextFieldCZ txtSENDER;
    private utilesGUIx.JTextFieldCZ txtTELF;
    // End of variables declaration//GEN-END:variables
}
