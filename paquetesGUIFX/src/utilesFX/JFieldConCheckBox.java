/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesFX;

import ListDatos.ECampoError;
import ListDatos.estructuraBD.JFieldDef;
import com.sun.javafx.scene.traversal.Direction;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author eduardo
 */
public class JFieldConCheckBox implements IFieldControl {

    private final boolean mbLabelHTMLDefecto = JFXConfigGlobal.getInstancia().isLabelHTMLDefecto();

    private final CheckBox moControl;
    private JFieldDef moFieldDef;
    private Boolean  mbValorOriginal = null; 

//    private boolean mbMensajePresentado=false;

    private boolean mbAsocidoATabla = false;
    public JFieldConCheckBox(CheckBox poControl, boolean pbAsociadoATabla) {
        mbAsocidoATabla=pbAsociadoATabla;
        moControl = poControl;
        if(!mbAsocidoATabla){
            moControl.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent e) -> {
                if(e.getEventType()==KeyEvent.KEY_PRESSED){
                    if((e.getCode()==KeyCode.ENTER
                            || e.getCode()==KeyCode.DOWN)
                            ){
                        e.consume();
                        moControl.impl_traverse(Direction.NEXT);
                    }
                }
            });
            moControl.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
                if(newValue){
                    salvarBackcolor();
                    //                          setBackground(moBackColorConFoco);
                }else{
                    //                          setBackground(moBackColorAux);
                    ponerColorSiCambio();
                    
                }
            });
        }

    }

    public JFieldConCheckBox(CheckBox poControl, JFieldDef poFieldDef) {
        this( poControl, false);
        setField(poFieldDef);
    }
    public CheckBox getComponente(){
        return moControl;
    }

    public JFieldDef getField(){
        return moFieldDef;
    }

    /**Establecemos el campo de la BD
     * @param poCampo
     */
    public void setField(final JFieldDef poCampo){
        moFieldDef = poCampo;
        if(!mbAsocidoATabla){
            if(mbLabelHTMLDefecto){
                moControl.setText("<html><p>"+poCampo.getCaption()+"</p></html>");
                moControl.setTooltip(new Tooltip("<html><p>"+poCampo.getCaption()+"</p></html>"));
            }else{
                moControl.setText(poCampo.getCaption());
                moControl.setTooltip(new Tooltip(poCampo.getCaption()));
            }
        }
//        setForeground((poCampo.getNullable()?null:JFXConfigGlobal.getInstancia().getLabelColorObligatorio()));
    }

    @Override
    public JFieldDef getCampo(){
        return moFieldDef;
    }

    @Override
    public void mostrarDatosBD(){
        if(moFieldDef!=null){
            setValueTabla(moFieldDef.getBooleanConNull());
        }
    }

    @Override
    public void establecerDatosBD() throws ECampoError{
        if(moFieldDef!=null){
            moFieldDef.setValue(moControl.isSelected());
        }
    }
    
    
    /**
     * establece el valor original, si cambia el texto con respecto este origen se pone el texto en rojo 
     * @param pbValor si el valor original es null, se anula el efecto
     *                si el valor original es <> null se activa el efecto
     */
    public void setValorOriginal(Boolean pbValor){
        mbValorOriginal = pbValor;
        ponerColorSiCambio();
    }
    /**
     * Devuelve el valor original
     * @return valor
     */    
    public Boolean getValorOriginal(){
        return mbValorOriginal;
    }
//    /**
//     * Devuelve el color de cambio
//     * @return color
//     */    
//    public Color getForeColorCambio(){
//        return moForeColorCambio;
//    }
//    /**
//     * establece el color de cambio
//     * @param poColor color
//     */    
//    public void setForeColorCambio(Color poColor){
//        moForeColorCambio = poColor;
//    }

    @Override
    public boolean getTextoCambiado(){
        boolean lbCambiado = false;
        if(mbValorOriginal!=null){
            lbCambiado = (moControl.isSelected()!=mbValorOriginal.booleanValue());
        }
        return lbCambiado;
    }
    private void salvarBackcolor(){
//        if(moBackColorAux==null){
//            moBackColorAux = getBackground();
//        }
    }
    /**pone el color si ha cambiado*/
    public void ponerColorSiCambio(){
//        if(getTextoCambiado()){
//            if(getForeColorCambio()!=getForeground()){
//                moForeColorNormal = getForeground();
//                if(moForeColorNormal == null) {
//                    moForeColorNormal = Color.black;
//                }
//            }
//             setForeground(getForeColorCambio());
//        }else{
//            if(moForeColorNormal!=null){
//                setForeground(moForeColorNormal);
//            }
//        }
//        if(moBackColorConDatos!=null){
//            if(isSelected()){
//                salvarBackcolor();
//                setBackground(moBackColorConDatos);
//            }else{
//                setBackground(moBackColorAux);
//            }
//        }
    }
//    /**
//     * Devolvemos el color de fondo cuando tiene el foco
//     * @return color
//     */
//    public Color getBackColorConFoco(){
//        return moBackColorConFoco;
//    }
//    /**
//     * Establecemos el color de fondo cuando tiene el foco
//     * @param poColor color
//     */
//    public void setBackColorConFoco(Color poColor){
//        moBackColorConFoco = poColor;
//    }

    
   /**Establecemos el estado
     * @param state
    */
    public void setSelected(boolean state) {
        moControl.setSelected(state);
        ponerColorSiCambio();
    }

    @Override
    public Object getValueTabla() {
        return (moControl.isSelected() ? Boolean.TRUE : Boolean.FALSE);
    }
    

    @Override
    public void setValueTabla(Object poValor)  {
        if(poValor==null){
            setValorOriginal(Boolean.FALSE);
        }else{
            String lsValor = poValor.toString();
            if((lsValor.compareTo("1")==0)||(lsValor.compareTo("-1")==0)){
                setValorOriginal(Boolean.TRUE);
            }else{
                setValorOriginal(Boolean.valueOf(poValor.toString()));
            }
        }
        setSelected(mbValorOriginal.booleanValue());
    }

    public void setValueTabla(boolean pbValor) {
        if(pbValor) {
            setValueTabla(Boolean.TRUE);
        } else {
            setValueTabla(Boolean.FALSE);
        }
    }
}
