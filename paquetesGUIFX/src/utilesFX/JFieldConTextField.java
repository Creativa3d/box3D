/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesFX;

import ListDatos.ECampoError;
import ListDatos.JListDatos;
import ListDatos.estructuraBD.JFieldDef;
import com.sun.javafx.scene.traversal.Direction;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import utiles.JDepuracion;
import utilesFX.msgbox.JMsgBox;
import utilesGUI.tiposTextos.ITipoTexto;
import utilesGUI.tiposTextos.JTipoTextoEstandar;
import utilesGUI.tiposTextos.KeyEventCZ;

/**
 *
 * @author eduardo
 */
public class JFieldConTextField implements IFieldControl {

    private ITipoTexto moTipo = new JTipoTextoEstandar(JFXConfigGlobal.getInstancia().getTipoDefectoCadenaBD());

    private final TextInputControl moControl;
    private JFieldDef moFieldDef;
    private boolean mbDesactivarSetText = false;
    private boolean mbAsocidoATabla = false;
    private boolean mbMensajePresentado=false;
    private ChangeListener<String> moTamanoMax;

    public JFieldConTextField(TextInputControl poControl, boolean pbAsociadoATabla) {
        mbAsocidoATabla=pbAsociadoATabla;
        moControl = poControl;
        if(!mbAsocidoATabla){
            moControl.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent e) -> {
                if(e.getEventType()==KeyEvent.KEY_TYPED){
                    KeyEventCZ loKey = new KeyEventCZ(e.getCharacter().charAt(0));
                    moTipo.getTecla(moControl.getText(),loKey);
                    //ojo                        e.getCode().set (loKey.getKeyChar());
                }
                if(e.getEventType()==KeyEvent.KEY_PRESSED){
                    if((e.getCode()==KeyCode.ENTER
                            || e.getCode()==KeyCode.DOWN)
                            &&(!mbAsocidoATabla)){
                        e.consume();
                        moControl.impl_traverse(Direction.NEXT);
                    }
                    if(e.getCode()==KeyCode.ESCAPE){
                        e.consume();
                        moTipo.restaurarTexto();
                        setTextReal(moTipo.getText());
                    }
                }
            });
            moControl.focusedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    boolean lbContinuar = true;
                    if (!mbAsocidoATabla) {
                        if (newValue) {
                            setTextReal(moTipo.getText());
                            ponerColorFondo();
                            moControl.positionCaret(0);
                            moControl.selectEnd();
                        } else {
                            quitarColorFondo();
                            String lsTexto = moControl.getText();
                            if (moTipo.getTextFormateado().equals(lsTexto)) {
                                lsTexto = moTipo.getText();
                            }
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


                                moControl.positionCaret(0);
                               ponerColorSiCambio();
                            }

                        }
                    }
                }
            }
            );
        }
    }
    public JFieldConTextField(TextInputControl poControl) {
        this(poControl, false);
    }

    public JFieldConTextField(TextInputControl poControl, JFieldDef poFieldDef) {
        this(poControl);
        setField(poFieldDef);
    }
    public TextInputControl getComponente(){
        return moControl;
    }

    @Override
    public JFieldDef getCampo() {
        return moFieldDef;
    }
    public void setField(final JFieldDef poCampo){
        moFieldDef = poCampo;
        setTipoBD(poCampo.getTipo());
        if(poCampo.getTipo() == JListDatos.mclTipoCadena &&
                poCampo.getTamano() > 0 &&
                poCampo.getTamano() < 1000){
            if(moTamanoMax!=null){
                moControl.textProperty().removeListener(moTamanoMax);
            }
            moTamanoMax = (final ObservableValue<? extends String> ov, final String oldValue, final String newValue) -> {
                if (moControl.getText().length() > poCampo.getTamano()) {
                    String s = moControl.getText().substring(0, poCampo.getTamano());
                    moControl.setText(s);
                }
            };
            moControl.textProperty().addListener(moTamanoMax); 
        }
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
    
   
/**
     * Estabecemos el tipo que viene directo de un JListDatos
     * @param plTipoBD tipo
     */
    public void setTipoBD(final int plTipoBD){
        switch(plTipoBD){
            case JListDatos.mclTipoFecha:
                setTipo(JTipoTextoEstandar.mclTextFecha);
                break;
            case JListDatos.mclTipoNumero:
                setTipo(JTipoTextoEstandar.mclTextNumeroEntero);
                break;
            case JListDatos.mclTipoNumeroDoble:
                setTipo(JTipoTextoEstandar.mclTextNumeroDoble);
                break;
            case JListDatos.mclTipoMoneda3Decimales:
                setTipo(JTipoTextoEstandar.mclTextMoneda3Decimales);
                break;
            case JListDatos.mclTipoMoneda:
                setTipo(JTipoTextoEstandar.mclTextMoneda);
                break;
            case JListDatos.mclTipoPorcentual3Decimales:
                setTipo(JTipoTextoEstandar.mclTextPorcentual3Decimales);
                break;
            case JListDatos.mclTipoPorcentual:
                setTipo(JTipoTextoEstandar.mclTextPorcentual);
                break;
            default:
                setTipo(JFXConfigGlobal.getInstancia().getTipoDefectoCadenaBD());
        }
        if(TextField.class.isAssignableFrom(moControl.getClass())
                && (
                plTipoBD==JListDatos.mclTipoMoneda
                || plTipoBD==JListDatos.mclTipoMoneda3Decimales
                || plTipoBD==JListDatos.mclTipoNumero
                || plTipoBD==JListDatos.mclTipoNumeroDoble
                || plTipoBD==JListDatos.mclTipoPorcentual
                || plTipoBD==JListDatos.mclTipoPorcentual3Decimales
                )){
            ((TextField)moControl).setAlignment(Pos.BASELINE_RIGHT);
        }        
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
        return moControl.getText();
    }

    public void setText(final String t) {
        moTipo.setText(t);
        setTextReal(moTipo.getTextFormateado());
    }

    private void setTextReal(String psText) {
        boolean lbDesactOrig = mbDesactivarSetText;
        mbDesactivarSetText = true;
        try {
            moControl.setText(psText);
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
                String lsTexto = moControl.getText();
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
