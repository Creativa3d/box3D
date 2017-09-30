/*
 * JPanelModificacionMasiva.java
 *
 * Created on 04/05/2017
 */
package utilesGUIx.modificacionMasiva;

import ListDatos.JListDatos;
import ListDatos.JResultado;
import ListDatos.JSTabla;
import ListDatos.estructuraBD.JFieldDef;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import utilesGUIx.JButtonGroupCZ;
import utilesGUIx.JCheckBoxCZ;
import utilesGUIx.JComboBoxCZ;
import utilesGUIx.JLabelCZ;
import utilesGUIx.JTextAreaCZ;
import utilesGUIx.JTextFieldCZ;
import utilesGUIx.Rectangulo;
import utilesGUIx.formsGenericos.IPanelControlador;
import utilesGUIx.formsGenericos.edicion.JPanelGENERALBASE;

/**
 *
 * @author rtenor
 */
public class JPanelModificacionMasiva extends JPanelGENERALBASE {

    protected static Font moFuente = new Font("Dialog", 0, 11);
    protected static Insets moInsets = new Insets(2, 5, 0, 5);
    protected GridBagConstraints gridBagConstraints;

    protected String msRutaAgrupado = "/images/agrupado.png";

    protected List moLabels;
    protected List moComponentes;
    protected List moComponentesEditar;
    protected List moComponentesBorrar;

    protected List moComponentesPropios;
    protected List moComponentesPropiosEditar;
    protected List moComponentesPropiosBorrar;

    protected JSTabla moTabla;
    protected List<CampoModificacionMasiva> moCamposMasivos;

    /**
     * Creates new form JPanelModificacionMasiva
     */
    public JPanelModificacionMasiva() {
        initComponents();
    }

    public void setDatos(JSTabla poTabla, IPanelControlador poPadre, List<CampoModificacionMasiva> poCamposMasivos) {
        moTabla = poTabla;
        moPadre = poPadre;
        moCamposMasivos=poCamposMasivos;
    }

    protected void inicializar() throws Exception {
        this.jPanelModificacionMasiva.removeAll();

        moLabels = new ArrayList();
        moComponentes = new ArrayList();
        moComponentesEditar = new ArrayList();
        moComponentesBorrar = new ArrayList();

        moComponentesPropios = new ArrayList();
        moComponentesPropiosEditar = new ArrayList();
        moComponentesPropiosBorrar = new ArrayList();

    }

    protected void crearLabel(String psLabel, boolean pbAgrupado) {

        JLabelCZ label;

        if (pbAgrupado) {
            label = new JLabelCZ(psLabel, new ImageIcon(getClass().getResource(msRutaAgrupado)), JLabelCZ.LEFT);
        } else {
            label = new JLabelCZ(psLabel);
        }

        label.setFont(moFuente);
        label.setText(psLabel);
        label.setVisible(true);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = moInsets;
        gridBagConstraints.anchor = GridBagConstraints.NORTH;
        jPanelModificacionMasiva.add(label, gridBagConstraints);
        moLabels.add(label);
    }

    protected void creaLabelFinal() {
        JLabelCZ label = new JLabelCZ();
        label.setVisible(true);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = moInsets;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.anchor = GridBagConstraints.NORTH;
        jPanelModificacionMasiva.add(label, gridBagConstraints);
        moLabels.add(label);
    }

    protected void crearRadiosEditarBorrar(boolean pbCampoPropio) {

        JRadioButton radioEditar = new JRadioButton();
        radioEditar.setFont(moFuente);
        radioEditar.setText("Editar");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = moInsets;
        jPanelModificacionMasiva.add(radioEditar, gridBagConstraints);

        if (pbCampoPropio) {
            moComponentesPropiosEditar.add(radioEditar);
        } else {
            moComponentesEditar.add(radioEditar);
        }

        JRadioButton radioBorrar = new JRadioButton();
        radioBorrar.setFont(moFuente);
        radioBorrar.setText("Borrar");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
        gridBagConstraints.insets = moInsets;
        jPanelModificacionMasiva.add(radioBorrar, gridBagConstraints);
        if (pbCampoPropio) {
            moComponentesPropiosBorrar.add(radioBorrar);
        } else {
            moComponentesBorrar.add(radioBorrar);
        }

        JButtonGroupCZ loButtonGroup = new JButtonGroupCZ();
        loButtonGroup.add(radioEditar);
        loButtonGroup.add(radioBorrar);
    }

    protected void crearCheckBox(String psLabel, boolean pbCampoPropio, boolean pbAgrupado) {
        JCheckBoxCZ checkBox = new JCheckBoxCZ();
        if (pbAgrupado) {

            String label = "<html><table cellpadding=0><tr><td><img src="
                    + getClass().getResource(msRutaAgrupado) + "/></td><td width=" + 3 + "><td>"
                    + psLabel + "</td></tr></table></html>";

            checkBox.setText(label);
        } else {
            checkBox.setText(psLabel);
        }

        checkBox.setFont(moFuente);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = moInsets;
        jPanelModificacionMasiva.add(checkBox, gridBagConstraints);

        if (pbCampoPropio) {
            moComponentesPropios.add(checkBox);
        } else {
            moComponentes.add(checkBox);
        }

        crearRadiosEditarBorrar(pbCampoPropio);
    }

    protected void crearTextField(String psLabel, int plTipo, boolean pbCampoPropio, boolean pbAgrupado) {
        crearLabel(psLabel, pbAgrupado);

        JTextFieldCZ textField = new JTextFieldCZ();
        textField.setFont(moFuente);
        textField.setTipoBD(plTipo);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = moInsets;
        jPanelModificacionMasiva.add(textField, gridBagConstraints);

        if (pbCampoPropio) {
            moComponentesPropios.add(textField);
        } else {
            moComponentes.add(textField);
        }

        crearRadiosEditarBorrar(pbCampoPropio);
    }

    protected void crearTextArea(String psLabel, int plTipo) {
        crearLabel(psLabel, false);

        JTextAreaCZ textArea = new JTextAreaCZ();
        textArea.setTipoBD(plTipo);
        textArea.setFont(moFuente);
        textArea.setColumns(20);
        textArea.setRows(5);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = moInsets;
        JScrollPane jScrollPane2 = new JScrollPane(textArea);
        jPanelModificacionMasiva.add(jScrollPane2, gridBagConstraints);
        moComponentes.add(textArea);

        crearRadiosEditarBorrar(false);
    }

    protected void crearComboBox(String psLabel, JListDatos poListaValores, int[] poDescripciones, int[] poCodigos, boolean pbConBlanco) {
        crearLabel(psLabel, false);

        JComboBoxCZ comboBox = new JComboBoxCZ();
        comboBox.setFont(moFuente);

        if (poListaValores.moveFirst()) {
            do {
                comboBox.RellenarCombo(
                        poListaValores,
                        poDescripciones,
                        poCodigos,
                        pbConBlanco
                );
            } while (poListaValores.moveNext());
        }

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = moInsets;
        jPanelModificacionMasiva.add(comboBox, gridBagConstraints);
        moComponentes.add(comboBox);

        crearRadiosEditarBorrar(false);

    }

    protected void crearFormulario() throws Exception {
        for (CampoModificacionMasiva loCampoMasivo : moCamposMasivos) {
            if (loCampoMasivo.getTipo() == CampoModificacionMasiva.mclTipoCheckBox) {
                crearCheckBox(loCampoMasivo.getField().getCaption(), false, false);
            } else if (loCampoMasivo.getTipo() == CampoModificacionMasiva.mclTipoTextField) {
                crearTextField(loCampoMasivo.getField().getCaption(), loCampoMasivo.getField().getTipo(), false, false);
            } else if (loCampoMasivo.getTipo() == CampoModificacionMasiva.mclTipoTextArea) {
                crearTextArea(loCampoMasivo.getField().getCaption(), loCampoMasivo.getField().getTipo());
            } else if (loCampoMasivo.getTipo() == CampoModificacionMasiva.mclTipoComboBox) {
                crearComboBox(
                        loCampoMasivo.getField().getCaption(),
                        loCampoMasivo.getListaValores(),
                        loCampoMasivo.getDescripciones(),
                        loCampoMasivo.getCodigos(),
                        loCampoMasivo.getConBlanco()
                );
            }
        }

        this.updateUI();
    }

    public void rellenarPantalla() throws Exception {
        inicializar();
        crearFormulario();
        creaLabelFinal();
    }

    public void ponerTipoTextos() throws Exception {

    }

    public void mostrarDatos() throws Exception {
    }

    public void habilitarSegunEdicion() throws Exception {

    }

    public String getTitulo() {
        return "Modificación Masiva";
    }

    public Rectangulo getTanano() {
        return new Rectangulo(0, 0, 805, 600);
    }

    public JSTabla getTabla() {
        return moTabla;
    }

    public boolean validarDatos() throws Exception {
        return true;
    }

    protected void editarCampo(Object poComponente, JFieldDef poCampo) throws Exception {
        if (poComponente instanceof JTextFieldCZ) {
            if (!((JTextFieldCZ) poComponente).getText().equals("")) {
                moTabla.getField(poCampo.getNombre()).setValue(((JTextFieldCZ) poComponente).getText());
            }
        } else if (poComponente instanceof JTextAreaCZ) {
            if (!((JTextAreaCZ) poComponente).getText().equals("")) {
                moTabla.getField(poCampo.getNombre()).setValue(((JTextAreaCZ) poComponente).getText());
            }
        } else if (poComponente instanceof JComboBoxCZ) {
            moTabla.getField(poCampo.getNombre()).setValue(((JComboBoxCZ) poComponente).getFilaActual().msCampo(0));
        } else if (poComponente instanceof JCheckBoxCZ) {
            moTabla.getField(poCampo.getNombre()).setValue(((JCheckBoxCZ) poComponente).isSelected());
        }

        moTabla.moList.update(false);
    }

    protected void borrarCampo(Object poComponente, JFieldDef poCampo) throws Exception {
        if (poComponente instanceof JTextFieldCZ || poComponente instanceof JTextAreaCZ || poComponente instanceof JComboBoxCZ) {
            moTabla.getField(poCampo.getNombre()).setValue("");
        } else {
            moTabla.getField(poCampo.getNombre()).setValue(false);
        }

        moTabla.moList.update(false);
    }

    public void establecerDatos() throws Exception {

        if (moTabla.moveFirst()) {
            do {

                for (int i = 0; i < moCamposMasivos.size(); i++) {
                    JFieldDef loField = ((CampoModificacionMasiva) moCamposMasivos.get(i)).getField();

                    // Editar
                    if (moComponentesEditar.get(i) instanceof JRadioButton) {
                        if (((JRadioButton) moComponentesEditar.get(i)).isSelected()) {
                            editarCampo(moComponentes.get(i), loField);
                        }
                    }

                    // Borrar
                    if (moComponentesBorrar.get(i) instanceof JRadioButton) {
                        if (((JRadioButton) moComponentesBorrar.get(i)).isSelected()) {
                            borrarCampo(moComponentes.get(i), loField);
                        }
                    }
                }

            } while (moTabla.moveNext());
        }
    }

    public void aceptar() throws Exception {
        JResultado loResult = new JResultado("", true);
        if (moTabla.moList.moveFirst()) {
            do {

                moTabla.validarCampos();

                loResult.addResultado(moTabla.guardar());
            } while (moTabla.moList.moveNext() && loResult.getBien());
        }
        actualizarPadre(getModoTabla());

        if (!loResult.getBien()) {
            throw new Exception(loResult.getMensaje());
        }
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jScrollModificacionMasiva = new javax.swing.JScrollPane();
        jPanelModificacionMasiva = new javax.swing.JPanel();
        jLabelCZ1 = new utilesGUIx.JLabelCZ();

        setAutoscrolls(true);
        setMinimumSize(new java.awt.Dimension(47, 14));
        setPreferredSize(new java.awt.Dimension(47, 14));
        setLayout(new java.awt.GridBagLayout());

        jScrollModificacionMasiva.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jScrollModificacionMasiva.setMaximumSize(null);
        jScrollModificacionMasiva.setMinimumSize(null);

        jPanelModificacionMasiva.setAutoscrolls(true);
        jPanelModificacionMasiva.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jPanelModificacionMasiva.setRequestFocusEnabled(false);
        jPanelModificacionMasiva.setLayout(new java.awt.GridBagLayout());

        jLabelCZ1.setText("jLabelCZ1");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        jPanelModificacionMasiva.add(jLabelCZ1, gridBagConstraints);

        jScrollModificacionMasiva.setViewportView(jPanelModificacionMasiva);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(jScrollModificacionMasiva, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private utilesGUIx.JLabelCZ jLabelCZ1;
    private javax.swing.JPanel jPanelModificacionMasiva;
    private javax.swing.JScrollPane jScrollModificacionMasiva;
    // End of variables declaration//GEN-END:variables
}
