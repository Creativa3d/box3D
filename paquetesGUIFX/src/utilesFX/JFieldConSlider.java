/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesFX;

import ListDatos.ECampoError;
import ListDatos.JListDatos;
import ListDatos.estructuraBD.JFieldDef;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Slider;
import javafx.util.StringConverter;
import utiles.JDepuracion;
import utilesFX.msgbox.JMsgBox;
import utilesGUI.tiposTextos.ITipoTexto;
import utilesGUI.tiposTextos.JTipoTextoEstandar;

/**
 *
 * @author eduardo
 */
public class JFieldConSlider implements IFieldControl {
    private JTipoTextoEstandar moTipo = new JTipoTextoEstandar(JTipoTextoEstandar.mclTextNumeroDoble);

    private final Slider moControl;
    private JFieldDef moFieldDef;
    private boolean mbDesactivarSetText = false;
    private boolean mbAsocidoATabla = false;
    private boolean mbMensajePresentado=false;

    public JFieldConSlider(Slider poControl, boolean pbAsociadoATabla) {
        mbAsocidoATabla=pbAsociadoATabla;
        moControl = poControl;
        
        
        if(!mbAsocidoATabla){
            
            moControl.focusedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    boolean lbContinuar = true;
                    if (!mbAsocidoATabla) {
                        if (newValue) {
                            setTextReal(moTipo.getText());
                            ponerColorFondo();
                        } else {
                            quitarColorFondo();
                            
                            String lsTexto = String.valueOf(moControl.getValue());
                            
                            if (moTipo.isTipoCorrecto(lsTexto)) {
                                moTipo.lostFocus(lsTexto);
                                setTextReal(moTipo.getTextFormateado());
                            } else {
                                lbContinuar = !moTipo.isTipoCorrectoObligatorio();
                                if (lbContinuar) {
                                    moTipo.lostFocus(lsTexto);
                                    setTextReal(moTipo.getTextFormateado());
                                }

                                if (!mbMensajePresentado) {
                                    mbMensajePresentado = true;
                                    JMsgBox.mensajeError(this, moTipo.getTextoError(lsTexto));
                                    mbMensajePresentado = false;
                                }
                            }
                            //anulamos la seleccion para que solo haya un campo con todo seleccionado
                            if (lbContinuar) {
                               ponerColorSiCambio();
                            }

                        }
                    }
                }
            }
            );
        }
    }
    public JFieldConSlider(Slider poControl) {
        this(poControl, false);
    }

    public JFieldConSlider(Slider poControl, JFieldDef poFieldDef) throws Exception {
        this(poControl);
        setField(poFieldDef);
    }
    public Slider getComponente(){
        return moControl;
    }

    @Override
    public JFieldDef getCampo() {
        return moFieldDef;
    }
    public void setField(final JFieldDef poCampo) throws Exception{
        moFieldDef = poCampo;
    }
    @Override
    public void setValueTabla(Object poValor) {
        mbDesactivarSetText = true;
        try {
            String lsValor;
            if (poValor == null) {
                lsValor = "";
            } else {
                lsValor = poValor.toString();
            }
            moTipo.setText(lsValor);
            moTipo.setTextOriginal(moTipo.getText());
            setTextReal(moTipo.getText());
        } finally {
            mbDesactivarSetText = false;
        }
    }

    @Override
    public Object getValueTabla() {
        return getText();
    }

    @Override
    public boolean getTextoCambiado() {
        return moTipo.isTextoCambiado();
    }

    @Override
    public void mostrarDatosBD() {
        if (moFieldDef != null) {
            try {
                setValueTabla(moFieldDef.getString());
            } catch (Exception ex) {
                JDepuracion.anadirTexto(getClass().getName(), ex);
            }
        }
    }

    @Override
    public void establecerDatosBD() throws ECampoError {
        if (moFieldDef != null) {
            moFieldDef.setValue(getValueTabla());
        }
    }

    /**
     * Devuelve si el texto que hay en el componente es del tipo correcto, según
     * mlTipo
     *
     * @return si es correcto
     */
    public boolean isTipoCorrecto() {
        return moTipo.isTipoCorrecto(moTipo.getText());
    }

    public String getText() {
        establecerTextoEnTipo();
        return moTipo.getText();
    }

    public String getTextReal() {
        return String.valueOf(moControl.getValue());
    }

    public void setText(final String t) {
        moTipo.setText(t);
        setTextReal(moTipo.getTextFormateado());
    }

    private void setTextReal(String psText) {
        boolean lbDesactOrig = mbDesactivarSetText;
        mbDesactivarSetText = true;
        try {
            moControl.setValue(Double.parseDouble(psText.replace(",", ".")));
        } finally {
            mbDesactivarSetText = lbDesactOrig;
        }
    }

    /**
     * setValorOriginal si el valor original es null, se anula el efecto si el
     * valor original es <> null se activa el efecto
     *
     * @param psValor valor
     */
    public void setValorOriginal(final String psValor) {
        moTipo.setTextOriginal(psValor);
        ponerColorSiCambio();
    }

    /**
     * Valor original
     *
     * @return el valor
     */
    public String getValorOriginal() {
        return moTipo.getTextOriginal();
    }
    private void quitarColorFondo(){
//        setBackground(moBackColorAux);
    }    
    private void ponerColorFondo(){
//        moBackColorAux = getBackground();
//        setBackground(moBackColorConFoco);
    }
    /**
     * pone el color si el texto cambia con respecto al valor original
     */
    public void ponerColorSiCambio() {
//        if(moTipo.isTextoCambiado()){
//            if(getForeColorCambio()!=moControl.getForeground()){
//                moForeColorNormal = getForeground();
//            }
//            setForeground(getForeColorCambio());
//        }else{
//            if(moForeColorNormal!=null){
//                setForeground(moForeColorNormal);
//            }
//        }
//        if(moBackColorConDatos!=null){
//            if(!moTipo.getTextFormateado().equals("")){
//                salvarBackcolor();
//                setBackground(moBackColorConDatos);
//            }else{
//                setBackground(moBackColorAux);
//            }
//        }

    }

    private void establecerTextoEnTipo() {
        if (!mbDesactivarSetText) {
            mbDesactivarSetText = true;
            try {

                boolean lbContinuar;
                String lsTexto = getTextReal();
                if (!moTipo.getTextFormateado().equals(lsTexto)) {
                    if (moTipo.isTipoCorrecto(lsTexto)) {
                        moTipo.lostFocus(lsTexto);
                    } else {
                        lbContinuar = !moTipo.isTipoCorrectoObligatorio();
                        if (lbContinuar) {
                            moTipo.lostFocus(lsTexto);
                        }
                    }
                }
            } catch (Throwable e) {
            } finally {
                mbDesactivarSetText = false;
            }
        }
    }

}
