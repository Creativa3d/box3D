/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesFX;

import ListDatos.ECampoError;
import ListDatos.estructuraBD.JFieldDef;
import javafx.beans.value.ObservableValue;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.web.HTMLEditor;
import utiles.JDepuracion;
import utilesGUI.tiposTextos.ITipoTexto;
import utilesGUI.tiposTextos.JTipoTextoEstandar;

/**
 *
 * @author eduardo
 */
public class JFieldConHTMLEditor implements IFieldControl {

    private ITipoTexto moTipo = new JTipoTextoEstandar(JTipoTextoEstandar.mclTextCadena);

    private final HTMLEditor moControl;
    private JFieldDef moFieldDef;
    private boolean mbDesactivarSetText = false;
    private boolean mbAsocidoATabla = false;


    public JFieldConHTMLEditor(HTMLEditor poControl, boolean pbAsociadoATabla) {
        mbAsocidoATabla=pbAsociadoATabla;
        moControl = poControl;
        if(!mbAsocidoATabla){
            moControl.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent e) -> {
                if(e.getEventType()==KeyEvent.KEY_PRESSED){
                    if(e.getCode()==KeyCode.ESCAPE){
                        e.consume();
                        moTipo.restaurarTexto();
                        setTextReal(moTipo.getText());
                    }
                }
            });
            moControl.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
                boolean lbContinuar = true;
                if (!mbAsocidoATabla) {
                    if (newValue) {
                        setTextReal(moTipo.getText());
                        ponerColorFondo();
                    } else {
                        quitarColorFondo();
                        String lsTexto = moControl.getHtmlText();
                        
                        moTipo.lostFocus(lsTexto);
                        setTextReal(moTipo.getTextFormateado());
                        
                        //anulamos la seleccion para que solo haya un campo con todo seleccionado
                        if (lbContinuar) {
                            
                            ponerColorSiCambio();
                        }
                        
                    }
                }
            });
        }
    }
    public JFieldConHTMLEditor(HTMLEditor poControl) {
        this(poControl, false);
    }

    public JFieldConHTMLEditor(HTMLEditor poControl, JFieldDef poFieldDef) {
        this(poControl);
        setField(poFieldDef);
    }
    public HTMLEditor getComponente(){
        return moControl;
    }

    @Override
    public JFieldDef getCampo() {
        return moFieldDef;
    }
    public void setField(final JFieldDef poCampo){
        moFieldDef = poCampo;
    }
    /**
     * el tipo del textbox(numerico, texto, fecha, DNI)
     * @return tipo
     */
    public int getTipo(){
        return moTipo.getTipo();
    }
    /**
     * establecemos el tipo del textbox(numerico, texto, fecha, DNI)
     * @param plTipo el tipo
     */
    public void setTipo(final int plTipo){
        setTipo(new JTipoTextoEstandar(plTipo));
    }
    /**
     * establecemos el tipo del textbox
     * @param poTipo el tipo de texto
     */
    public void setTipo(final ITipoTexto poTipo){
        String lsTexto = moTipo.getText();
        String lsTextoO = moTipo.getTextOriginal();
        moTipo = poTipo;
        moTipo.setText(lsTexto);
        moTipo.setTextOriginal(lsTextoO);
    }
    public ITipoTexto getTipoO(){
        return moTipo;
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
            if (!mbAsocidoATabla) {
                setTextReal(moTipo.getTextFormateado());
            } else {
                setTextReal(moTipo.getText());
            }
        } finally {
            mbDesactivarSetText = false;
        }
    }

    @Override
    public Object getValueTabla() {
        return getText();
    }

    /**
     * devuelve el si el texto a cabiado y si hay un valor orioginal
     *
     * @return si ha cambiado
     */
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
        return moControl.getHtmlText();
    }

    public void setText(final String t) {
        moTipo.setText(t);
        setTextReal(moTipo.getTextFormateado());
    }

    private void setTextReal(String psText) {
        boolean lbDesactOrig = mbDesactivarSetText;
        mbDesactivarSetText = true;
        try {
            moControl.setHtmlText(psText);
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
                String lsTexto = moControl.getHtmlText();
                if (!moTipo.getTextFormateado().equals(lsTexto)) {
                    if (moTipo.isTipoCorrecto(lsTexto)) {
                        moTipo.lostFocus(lsTexto);
//                        if(!mbPopUPActivo){
//                            setTextReal(moTipo.getTextFormateado());
//                        }
                    } else {
                        lbContinuar = !moTipo.isTipoCorrectoObligatorio();
                        if (lbContinuar) {
                            moTipo.lostFocus(lsTexto);
//                            if(!mbPopUPActivo){
//                                setTextReal(moTipo.getTextFormateado());
//                            }
                        }
//
//                        if(!mbMensajePresentado){
//                            mbMensajePresentado=true;
//                            JMsgBox.mensajeError(this, moTipo.getTextoError(lsTexto));
//                            mbMensajePresentado=false;
//                        }
                    }
                }
            } catch (Throwable e) {
            } finally {
                mbDesactivarSetText = false;
            }
        }
    }

}
