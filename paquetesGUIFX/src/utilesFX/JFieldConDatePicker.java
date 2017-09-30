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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.DatePicker;
import javafx.util.StringConverter;
import utiles.FechaMalException;
import utiles.JDateEdu;
import utiles.JDepuracion;
import utiles.JFormat;
import utilesFX.msgbox.JMsgBox;
import utilesGUI.tiposTextos.ITipoTexto;
import utilesGUI.tiposTextos.JTipoTextoEstandar;

/**
 *
 * @author eduardo
 */
public class JFieldConDatePicker implements IFieldControl {

    private String msFormato = "dd/MM/yyyy";
    private JTipoTextoEstandar moTipo = new JTipoTextoEstandar(JTipoTextoEstandar.mclTextFecha, msFormato);

    private final DatePicker moControl;
    private JFieldDef moFieldDef;
    private boolean mbDesactivarSetText = false;
    private boolean mbAsocidoATabla = false;
    private boolean mbMensajePresentado=false;
    private DateTimeFormatter moFormat = DateTimeFormatter.ofPattern(msFormato);

    public JFieldConDatePicker(DatePicker poControl, boolean pbAsociadoATabla) {
        mbAsocidoATabla=pbAsociadoATabla;
        moControl = poControl;
        
        
        if(!mbAsocidoATabla){
            
            moControl.focusedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    boolean lbContinuar = true;
                    if (!mbAsocidoATabla) {
                        if (newValue) {
//                            setTextReal(moTipo.getText());
                            ponerColorFondo();
                        } else {
                            quitarColorFondo();
                            moTipo.lostFocus(moFormat.format(moControl.getValue()));
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
    public JFieldConDatePicker(DatePicker poControl) {
        this(poControl, false);
    }

    public JFieldConDatePicker(DatePicker poControl, JFieldDef poFieldDef) throws Exception {
        this(poControl);
        setField(poFieldDef);
    }
    public DatePicker getComponente(){
        return moControl;
    }

    @Override
    public JFieldDef getCampo() {
        return moFieldDef;
    }
    public void setFormato(String psFormato){
        msFormato = psFormato;
        moFormat = DateTimeFormatter.ofPattern(msFormato);
        
        String lsTexto = moTipo.getText();
        String lsTextoO = moTipo.getTextOriginal();
        moTipo = new JTipoTextoEstandar(JTipoTextoEstandar.mclTextFecha, msFormato);
        moTipo.setText(lsTexto);
        moTipo.setTextOriginal(lsTextoO);
        
         
        StringConverter converter = new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return moFormat.format(date);
                } else {
                    return "";
                }
            }
            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, moFormat);
                } else {
                    return null;
                }
            }
        };             
        moControl.setConverter(converter);
        moControl.setPromptText(msFormato.toLowerCase());
        
    }
    public void setField(final JFieldDef poCampo) throws Exception{
        moFieldDef = poCampo;
    }
    @Override
    public void setValueTabla(Object poValor) {
        mbDesactivarSetText = true;
        try {
            String lsValor="";
            if (poValor != null) {
                try {
                    lsValor = JFormat.msFormatearFecha(new JDateEdu(poValor.toString()).getDate(), msFormato);
                } catch (FechaMalException ex) {
                    JDepuracion.anadirTexto(getClass().getName(), ex);
                }
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
        return moFormat.format(moControl.getValue());
    }

    public void setText(final String t) {
        moTipo.setText(t);
        setTextReal(moTipo.getTextFormateado());
    }

    private void setTextReal(String psText) {
        boolean lbDesactOrig = mbDesactivarSetText;
        mbDesactivarSetText = true;
        try {
            moControl.setValue(LocalDate.parse(psText, moFormat));
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
