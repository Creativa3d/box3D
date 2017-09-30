/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * JPanelExportar.java
 *
 * Created on 26-may-2010, 19:29:50
 */

package paquetesGeneradorInf.gui.resultados.exportar;

import ListDatos.JListDatos;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import paquetesGeneradorInf.plugin.JAccionesGeneradorConsultas;
import utiles.config.JDatosGeneralesXML;
import utilesGUIx.formsGenericos.IMostrarPantalla;
import utilesGUIx.msgbox.JMsgBox;
import utilesGUIx.seleccionFicheros.JFileChooserFiltroPorExtension;


public class JPanelExportar extends javax.swing.JPanel implements ItemListener{
    public static final String mcsRutaUlt = "RutaUlt";
    private JDatosGeneralesXML moDatosGeneralesXML;
    private IExportar[] moExportar;
    private IMostrarPantalla moMotrar;
    private JListDatos moListDatos;

    /** Creates new form JPanelExportar */
    public JPanelExportar() {
        initComponents();
    }
    public void setDatos(JListDatos poListDatos,IExportar[] poExportar, JDatosGeneralesXML poDatosGeneralesXML, IMostrarPantalla poMotrar) {
        moListDatos = poListDatos;
        moExportar=poExportar;
        moDatosGeneralesXML=poDatosGeneralesXML;
        moMotrar=poMotrar;
        rellenar();
    }
    private void rellenar() {
        for(int i = 0 ; i < moExportar.length; i++){
            addExportar(moExportar[i], i);
        }
        if(moDatosGeneralesXML!=null){
            txtRuta.setText(moDatosGeneralesXML.getPropiedad(JAccionesGeneradorConsultas.mcsExportar+"/"+mcsRutaUlt));
        }
    }
    private IExportar getExportar(Container poPanel){
        IExportar loResult = null;
        for(int i = 0 ; i < poPanel.getComponentCount() && loResult == null; i++){
            Component loCom = poPanel.getComponent(i);
            if(loCom.getClass().isAssignableFrom(JPanel.class)){
                loResult = getExportar((Container) loCom);
            }else if(loCom.getClass().isAssignableFrom(JRadioButton.class)){
                JRadioButton loR = (JRadioButton) loCom;
                if(loR.isSelected()){
                    loResult = moExportar[Integer.valueOf(loR.getActionCommand()).intValue()];
                }
            }
        }
        return loResult;
    }
    private String[] getOpciones()    {
        return getOpciones(this);
    }
    private String[] getOpcionesSoloTexto(Container poPanel) {
        String[] lasResult=null;
        for(int i = 0 ; i < poPanel.getComponentCount(); i++){
            Component loCom = poPanel.getComponent(i);
            if(loCom.getClass().isAssignableFrom(JPanel.class)){
                lasResult = getOpcionesSoloTexto((Container) loCom);
            }else if(JTextField.class.isAssignableFrom(loCom.getClass())){
                JTextField loR = (JTextField) loCom;
                if(lasResult==null){
                    lasResult=new String[1];
                    lasResult[0]=loR.getText();
                }else{
                    String[] lasAux = new String[lasResult.length+1];
                    System.arraycopy(lasResult, 0, lasAux, 0, lasResult.length);
                    lasAux[lasAux.length-1]=loR.getText();
                    lasResult=lasAux;
                }
            }
        }

        return lasResult;
        
    }
    private String[] getOpciones(Container poPanel) {
        String[] lasResult = null;
        for(int i = 0 ; i < poPanel.getComponentCount() && lasResult == null; i++){
            Component loCom = poPanel.getComponent(i);
            if(loCom.getClass().isAssignableFrom(JPanel.class)){
                lasResult = getOpciones((Container) loCom);
            }else if(loCom.getClass().isAssignableFrom(JRadioButton.class)){
                JRadioButton loR = (JRadioButton) loCom;
                if(loR.isSelected()){
                    lasResult = getOpcionesSoloTexto(loR.getParent());
                }
            }
        }

        return lasResult;
    }
    private IExportar getExportar(){
        return getExportar(this);
    }

    public void addExportar(IExportar poExp, int lIndex){
        javax.swing.JPanel loPanel1;
        javax.swing.JPanel loPanel2;
        javax.swing.JRadioButton jRadioButton1;
        javax.swing.JLabel lblNombre;
        java.awt.GridBagConstraints gridBagConstraints;

        loPanel1 = new javax.swing.JPanel();
        jRadioButton1 = new javax.swing.JRadioButton();
        loPanel2 = new javax.swing.JPanel();
        lblNombre = new javax.swing.JLabel();

        loPanel1.setLayout(new java.awt.GridBagLayout());
        loPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, null));

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setText("  ");
        jRadioButton1.setSelected(lIndex==0);
        jRadioButton1.addItemListener(this);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        gridBagConstraints.weighty = 1.0;
        loPanel1.add(jRadioButton1, gridBagConstraints);
        jRadioButton1.setActionCommand(String.valueOf(lIndex) );

        loPanel2.setLayout(new java.awt.GridBagLayout());

        lblNombre.setText(poExp.getNombre());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        loPanel2.add(lblNombre, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        loPanel1.add(loPanel2, gridBagConstraints);


        String[] lasP = poExp.getOpcionesNombre();
        String[] lasV = poExp.getOpciones();

        for(int i = 0 ; lasP!=null && i < lasP.length; i++){
            utilesGUIx.JLabelCZ lblPropiedad;
            utilesGUIx.JTextFieldCZ txtPropiedad;
            lblPropiedad = new utilesGUIx.JLabelCZ();
            txtPropiedad = new utilesGUIx.JTextFieldCZ();

            if(lasV!=null && lasV.length>i){
                txtPropiedad.setText(lasV[i]);
            }

            lblPropiedad.setText(lasP[i]);
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
            loPanel2.add(lblPropiedad, gridBagConstraints);

            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            gridBagConstraints.weightx = 1.0;
            gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
            gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
            loPanel2.add(txtPropiedad, gridBagConstraints);
            
        }


        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        jPanelCentral.add(loPanel1, gridBagConstraints);

    }
    public void itemStateChanged(ItemEvent e) {
        IExportar loExp = getExportar();
        if(txtRuta.getText().length()>0 && loExp!=null){
            int lIndex = txtRuta.getText().lastIndexOf('.');
            if(lIndex < 0 ){
                txtRuta.setText(txtRuta.getText() + "." + loExp.getExtensiones()[0]);
            }else{
                String lsExt = txtRuta.getText().substring(lIndex+1);
                String[] lasExtOrg = loExp.getExtensiones();
                boolean lbCoincide = false;
                for(int i = 0 ; i < lasExtOrg.length && !lbCoincide ; i++){
                    if(lasExtOrg[i].equalsIgnoreCase(lsExt)){
                        lbCoincide=true;
                    }
                }
                if(!lbCoincide){
                    txtRuta.setText(txtRuta.getText().substring(0, lIndex) + "." + loExp.getExtensiones()[0]);
                }
            }
        }
    }

    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanelCentral = new javax.swing.JPanel();
        jPanelSur = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabelCZ1 = new utilesGUIx.JLabelCZ();
        txtRuta = new utilesGUIx.JTextFieldCZ();
        btnOpen = new utilesGUIx.JButtonCZ();
        jPanel2 = new javax.swing.JPanel();
        btnGuardar = new utilesGUIx.JButtonCZ();
        btnCancelar = new utilesGUIx.JButtonCZ();

        setLayout(new java.awt.BorderLayout());

        jPanelCentral.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanelCentral.setLayout(new java.awt.GridBagLayout());
        add(jPanelCentral, java.awt.BorderLayout.CENTER);

        jPanelSur.setLayout(new java.awt.GridBagLayout());

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabelCZ1.setText("Guardar en:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanel1.add(jLabelCZ1, gridBagConstraints);

        txtRuta.setText(" ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanel1.add(txtRuta, gridBagConstraints);

        btnOpen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/paquetesGeneradorInf/images/Open16.gif"))); // NOI18N
        btnOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOpenActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanel1.add(btnOpen, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        jPanelSur.add(jPanel1, gridBagConstraints);

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/paquetesGeneradorInf/images/Save16.gif"))); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel2.add(btnGuardar);

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/paquetesGeneradorInf/images/cancel.gif"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        jPanel2.add(btnCancelar);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        jPanelSur.add(jPanel2, gridBagConstraints);

        add(jPanelSur, java.awt.BorderLayout.SOUTH);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        try{
            moMotrar.cerrarForm(this);
        }catch(Throwable e){
            JMsgBox.mensajeErrorYLog(this, e, getClass().getName());
        }

    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        try{
            boolean lbContinuar = true;
            File loFile=new File(txtRuta.getText());
            if(loFile.exists()){
                int result = JOptionPane.showConfirmDialog(getTopLevelAncestor(),
                        "El archivo seleccionado ya existe. " +
                        "¿Desea sobreescribirlo?",
                        "El archivo ya existe",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                switch(result)  {
                    case JOptionPane.YES_OPTION:
                        lbContinuar = true;
                        break;
                    case JOptionPane.NO_OPTION:
                        lbContinuar = false;
                        break;
                }
            }
            if(lbContinuar){
                IExportar loExp = getExportar();
                loExp.setOpciones(getOpciones());
                loExp.exportar(moListDatos, loFile);
                try{
                    if(moDatosGeneralesXML!=null){
                        moDatosGeneralesXML.setPropiedad(
                                JAccionesGeneradorConsultas.mcsExportar+"/"+mcsRutaUlt,
                                txtRuta.getText());
                        moDatosGeneralesXML.guardarFichero();
                    }
                }catch(Throwable e1){
                    JMsgBox.mensajeErrorYLog(this, e1, getClass().getName());
                }
                moMotrar.cerrarForm(this);
            }
        }catch(Throwable e){
            JMsgBox.mensajeErrorYLog(this, e, getClass().getName());
        }

    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpenActionPerformed
        try{
            IExportar loExp = getExportar();
            JFileChooser loCh = new JFileChooser() ;

            loCh.setFileFilter(new JFileChooserFiltroPorExtension(loExp.getNombre(), loExp.getExtensiones()));
            if(txtRuta.getText().length()>0){
                loCh.setSelectedFile(new File(txtRuta.getText()));
            }
            loCh.showSaveDialog(this);

            if(loCh.getSelectedFile()!=null){
                txtRuta.setText(loCh.getSelectedFile().getAbsolutePath());
                if(txtRuta.getText().length()>4){
                    int lIndex = txtRuta.getText().lastIndexOf('.');
                    if(lIndex < 0){
                        txtRuta.setText(txtRuta.getText() + "." + loExp.getExtensiones()[0]);
                    }

                }
            }

        }catch(Throwable e){
            JMsgBox.mensajeErrorYLog(this, e, getClass().getName());
        }

    }//GEN-LAST:event_btnOpenActionPerformed




    // Variables declaration - do not modify//GEN-BEGIN:variables
    private utilesGUIx.JButtonCZ btnCancelar;
    private utilesGUIx.JButtonCZ btnGuardar;
    private utilesGUIx.JButtonCZ btnOpen;
    private javax.swing.ButtonGroup buttonGroup1;
    private utilesGUIx.JLabelCZ jLabelCZ1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanelCentral;
    private javax.swing.JPanel jPanelSur;
    private utilesGUIx.JTextFieldCZ txtRuta;
    // End of variables declaration//GEN-END:variables



}
