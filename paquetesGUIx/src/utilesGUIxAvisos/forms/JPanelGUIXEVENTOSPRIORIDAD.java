/*
* JPanelGUIXPRIORIDAD.java
*
* Creado el 18/2/2012
*/

package utilesGUIxAvisos.forms;

import utilesGUIx.Rectangulo;
import ListDatos.IFilaDatos;
import ListDatos.IResultado;
import ListDatos.JListDatos;
import ListDatos.JSTabla;
import java.awt.Color;

import javax.swing.JColorChooser;
import utilesGUIx.formsGenericos.IPanelControlador;
import utilesGUIx.formsGenericos.busqueda.IConsulta;
import utilesGUIx.formsGenericos.edicion.JPanelGENERALBASE;
import utilesGUIxAvisos.calendario.JDatosGenerales;
import utilesGUIxAvisos.tablasExtend.JTEEGUIXEVENTOSPRIORIDAD;

public class JPanelGUIXEVENTOSPRIORIDAD extends JPanelGENERALBASE {

    private JTEEGUIXEVENTOSPRIORIDAD moGUIXPRIORIDAD;
    private JDatosGenerales moDatosGenerales;

    /** Creates new form JPanelGUIXPRIORIDAD*/
    public JPanelGUIXEVENTOSPRIORIDAD() {
        super();
        initComponents();
    }

    public void setDatos(JDatosGenerales poDatosGenerales, final JTEEGUIXEVENTOSPRIORIDAD poGUIXPRIORIDAD, final IPanelControlador poPadre, final IConsulta poConsulta) throws Exception {
        moGUIXPRIORIDAD = poGUIXPRIORIDAD;
        moPadre = poPadre;
        moConsulta = poConsulta;
        moDatosGenerales=poDatosGenerales;

        if(poConsulta!=null){
            clonar(poConsulta.getList());
        }
    }

    public String getTitulo() {
        String lsResult;
        if(moGUIXPRIORIDAD.moList.getModoTabla() == JListDatos.mclNuevo) {
            lsResult= moDatosGenerales.getTextosForms().getTexto(JTEEGUIXEVENTOSPRIORIDAD.msCTabla) + " [Nuevo]" ;
        }else{
            lsResult=moDatosGenerales.getTextosForms().getTexto(JTEEGUIXEVENTOSPRIORIDAD.msCTabla)  + 
                moGUIXPRIORIDAD.getGUIXPRIORIDAD().getString();
        }
        return lsResult;
    }

    public JSTabla getTabla(){
        return moGUIXPRIORIDAD;
    }

    public void rellenarPantalla() throws Exception {

        //ponemos los textos a los label
        lblGUIXPRIORIDAD.setField(moGUIXPRIORIDAD.getGUIXPRIORIDAD());
        txtGUIXPRIORIDAD.setField(moGUIXPRIORIDAD.getGUIXPRIORIDAD());
        lblNOMBRE.setField(moGUIXPRIORIDAD.getNOMBRE());
        txtNOMBRE.setField(moGUIXPRIORIDAD.getNOMBRE());
    }

    public void habilitarSegunEdicion() throws Exception {
        if(moGUIXPRIORIDAD.moList.getModoTabla() == JListDatos.mclNuevo) {
            txtGUIXPRIORIDAD.setEnabled(true);
        }else{
            txtGUIXPRIORIDAD.setEnabled(false);
        }
    }

    public void ponerTipoTextos() throws Exception {
    }

    public void mostrarDatos() throws Exception {
        IFilaDatos loFila;
        lblColor.setBackground(new Color(moGUIXPRIORIDAD.getCOLOR().getInteger()));

        //Establecemos los valores de los paneles si hay
        //jPanelColectorEntrada.setValueTabla(moAlbaran.getCOLECTORENTRADA().getString());
    }

    public void establecerDatos() throws Exception {
        moGUIXPRIORIDAD.getCOLOR().setValue(lblColor.getBackground().getRGB());
        moGUIXPRIORIDAD.validarCampos();
    }

    public void aceptar() throws Exception {
        int lModo = getModoTabla();
        IResultado loResult=moGUIXPRIORIDAD.guardar();
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
        lblGUIXPRIORIDAD = new utilesGUIx.JLabelCZ();
        txtGUIXPRIORIDAD = new utilesGUIx.JTextFieldCZ();
        lblNOMBRE = new utilesGUIx.JLabelCZ();
        txtNOMBRE = new utilesGUIx.JTextFieldCZ();
        jPanel1 = new javax.swing.JPanel();
        btnColor = new javax.swing.JButton();
        lblColor = new javax.swing.JLabel();
        jPanelEspaciador = new javax.swing.JPanel();

        setLayout(new java.awt.GridBagLayout());

        jPanelGENERAL.setLayout(new java.awt.GridBagLayout());

        lblGUIXPRIORIDAD.setText("GUIXPRIORIDAD");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanelGENERAL.add(lblGUIXPRIORIDAD, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanelGENERAL.add(txtGUIXPRIORIDAD, gridBagConstraints);

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

        jPanel1.setLayout(new java.awt.GridBagLayout());

        btnColor.setText("Color");
        btnColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnColorActionPerformed(evt);
            }
        });
        jPanel1.add(btnColor, new java.awt.GridBagConstraints());

        lblColor.setText("        ");
        lblColor.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel1.add(lblColor, gridBagConstraints);

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

    private void btnColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnColorActionPerformed

        //        final JColorChooser colorChooser = new JColorChooser();         
        Color loColor = JColorChooser.showDialog(this, "", new Color(moGUIXPRIORIDAD.getCOLOR().getInteger()));         
        if (loColor != null) {             
            lblColor.setBackground(loColor);         
        }     

}//GEN-LAST:event_btnColorActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnColor;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelEspaciador;
    private javax.swing.JPanel jPanelGENERAL;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblColor;
    private utilesGUIx.JLabelCZ lblGUIXPRIORIDAD;
    private utilesGUIx.JLabelCZ lblNOMBRE;
    private utilesGUIx.JTextFieldCZ txtGUIXPRIORIDAD;
    private utilesGUIx.JTextFieldCZ txtNOMBRE;
    // End of variables declaration//GEN-END:variables
}
