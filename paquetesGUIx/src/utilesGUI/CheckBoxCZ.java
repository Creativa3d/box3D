/*
 * CheckBoxCZ.java
 *
 * Created on 19 de noviembre de 2003, 13:02
 */

package utilesGUI;

import java.awt.*;
import java.awt.event.*;

/**CheckBox personalizado*/
public class CheckBoxCZ extends java.awt.Checkbox  implements utilesGUI.tabla.IComponentParaTabla{
    
    private Color moBackColorConFoco = new Color(222,223,255);
    private Color moBackColor = null;
    //activamos el color de la letra si cambia con respectoa al original
    //si el valor original es null, se anula el efecto
    //si el valor original es <> null se activa el efecto
    private Boolean  mbValorOriginal = null; 
    private Color moForeColorCambio = new Color(255,0,0);
    private Color moForeColorNormal = null;


    /** Creates a new instance of CheckBoxCZ */
    public CheckBoxCZ() {
        super();
        enableEvents(AWTEvent.FOCUS_EVENT_MASK);
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
    /**
     * Devuelve el color de cambio
     * @return color
     */
    public Color getForeColorCambio(){
        return moForeColorCambio;
    }
    /**
     * establece el color de cambio
     * @param poColor color
     */
    public void setForeColorCambio(Color poColor){
        moForeColorCambio = poColor;
    }

    /**
     * devuelve el si el texto a cabiado y si hay un valor orioginal
     * @return si ha cambiado
     */
    public boolean getTextoCambiado(){
        boolean lbCambio = false;
        if(mbValorOriginal!=null){
            lbCambio = (getState()!=mbValorOriginal.booleanValue());
        }
        return lbCambio;
    }
    /**pone el color si ha cambiado*/
    public void ponerColorSiCambio(){
        if(!getTextoCambiado()){
            if(moForeColorNormal!=null){
                setForeground(moForeColorNormal);
            }
        }else{
            if(getForeColorCambio()!=getForeground()){
                moForeColorNormal = getForeground();
                if(moForeColorNormal == null) {
                    moForeColorNormal = Color.black;
                }
            }

                setForeground(getForeColorCambio());
        }
    }
    /**
     * Devolvemos el color de fondo cuando tiene el foco
     * @return color
     */
    public Color getBackColorConFoco(){
        return moBackColorConFoco;
        
    }
    /**
     * Establecemos el color de fondo cuando tiene el foco
     * @param poColor color
     */
    public void setBackColorConFoco(Color poColor){
        moBackColorConFoco = poColor;
    }
    public void setBackground(Color poColor){
        super.setBackground(poColor);
        moBackColor=poColor;
    }
    private void setBackgroundP(Color poColor){
        super.setBackground(poColor);
    }
    /**
     *Procesamos el evento del teclado
     */
    protected void processKeyEvent(KeyEvent e){
         int id = e.getID();
        switch(id) {
          case KeyEvent.KEY_PRESSED:
            if(e.getKeyCode()==e.VK_ENTER){
                this.transferFocus();
            }
            break;
            default:
        }
        super.processKeyEvent(e);
   }
    /**
     *Procesamos el evento de conseguir/perder foco
     */
   protected void processFocusEvent(FocusEvent e){
        int id = e.getID();
        switch(id) {
          case FocusEvent.FOCUS_GAINED:
              moBackColor = getBackground();
              setBackgroundP(moBackColorConFoco);
            break;
          case FocusEvent.FOCUS_LOST:
              setBackgroundP(moBackColor);
              ponerColorSiCambio();
            break;
            default:
        }
        super.processFocusEvent(e);
   }
   /**Establecemos el estado*/
    public void setState(boolean state) {
        super.setState(state);
        ponerColorSiCambio();
    }
    /**
     * Devuelve el valor actual del componenete en modo objeto
     * @return valor
     */
    public Object getValueTabla() {
        return new Boolean(getState());
    }
    
    /**
     * Establece el valor del text y el valor original, para lo del cambio de color si cambia
     * @param poValor valor
     */
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
        setState(mbValorOriginal.booleanValue());
    }
    
}
