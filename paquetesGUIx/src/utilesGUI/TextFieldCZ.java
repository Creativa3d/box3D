package utilesGUI;

import java.awt.*;
import java.awt.event.*;
import utiles.*;
import ListDatos.*;
import utilesGUI.tiposTextos.ITipoTexto;
import utilesGUI.tiposTextos.JTipoTextoEstandar;
import utilesGUI.tiposTextos.KeyEventCZ;

/**Componente de texto*/
public class TextFieldCZ extends TextField implements utilesGUI.tabla.IComponentParaTabla {
    public static int mlTipoDefectoCadenaBD = JTipoTextoEstandar.mclTextCadena;
    
    private ITipoTexto moTipo = new JTipoTextoEstandar(mlTipoDefectoCadenaBD);
    
    //efecto cuando coge el foco
    private Color moBackColorConFoco = new Color(51,255,255);
    private Color moBackColor = null;
    private Color moForeColorCambio = new Color(255,0,0);
    private Color moForeColorNormal = null;
    
    /**Constructor*/
    public TextFieldCZ() {
      super();
      enableEvents(AWTEvent.FOCUS_EVENT_MASK);
      enableEvents(AWTEvent.KEY_EVENT_MASK);
      enableEvents(AWTEvent.TEXT_EVENT_MASK);
    }
    
    /**
     * setValorOriginal
     * si el valor original es null, se anula el efecto
     * si el valor original es <> null se activa el efecto
     * @param psValor valor
     */
    public void setValorOriginal(final String psValor){
        moTipo.setTextOriginal(psValor);
        ponerColorSiCambio();
    }
    /**
     * Valor original
     * @return el valor
     */
    public String getValorOriginal(){
        return moTipo.getTextOriginal();
    }
    /**Color de letra si cambia el valor
     * @return color
     */
    public Color getForeColorCambio(){
        return moForeColorCambio;
    }
    /**
     * Color de letra si cambia el valor
     * @param poColor el color
     */
    public void setForeColorCambio(Color poColor){
        moForeColorCambio = poColor;
        ponerColorSiCambio();
    }

    /**
     * devuelve el si el texto a cabiado y si hay un valor orioginal
     * @return si ha cambiado
     */
    public boolean getTextoCambiado(){
        return moTipo.isTextoCambiado();
    }
    /**pone el color si el texto cambia con respecto al valor original*/
    public void ponerColorSiCambio(){
        if(!moTipo.isTextoCambiado()){
            if(moForeColorNormal!=null){
                setForeground(moForeColorNormal);
            }
        }else{
            if(getForeColorCambio()!=getForeground()){
                moForeColorNormal = getForeground();
            }
            setForeground(getForeColorCambio());
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
    /**
     * Estabecemos el tipo que viene directo de un JListDatos
     * y el tamaño del campo
     * @param plTipoBD tipo de Base datos(de JListDatos)
     * @param plTamano tamaño máximo
     */
    public void setTipoBD(int plTipoBD, int plTamano){
        setTipoBD(plTipoBD);
        if(plTamano>0){
            setColumns(plTamano);
        }
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
            case JListDatos.mclTipoNumero:
                setTipo(JTipoTextoEstandar.mclTextNumeroEntero);
                break;
            default:
                setTipo(mlTipoDefectoCadenaBD);
        }
    }
    /**
     * color de fondo cuando tiene el foco
     * @return color
     */
    public Color getBackColorConFoco(){
        return moBackColorConFoco;
    }
    /**
     * color de fondo cuando tiene el foco
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
    protected void processFocusEvent(FocusEvent e){
        boolean lbContinuar = true;
        int id = e.getID();
        switch(id) {
          case FocusEvent.FOCUS_GAINED:
              super.setText(moTipo.getText());
              moBackColor = getBackground();
              setBackgroundP(moBackColorConFoco);
              setSelectionStart(0);
              setSelectionEnd(moTipo.getText().length());
            break;
          case FocusEvent.FOCUS_LOST:
              setBackgroundP(moBackColor);
              String lsTexto = super.getText();
              if (moTipo.isTipoCorrecto(lsTexto)) {
                  moTipo.lostFocus(lsTexto);
                  super.setText(moTipo.getTextFormateado());
              }else{
                  lbContinuar = !moTipo.isTipoCorrectoObligatorio();
                  if(lbContinuar){
                      moTipo.lostFocus(lsTexto);
                      super.setText(moTipo.getTextFormateado());
                  }
                  
                  utilesGUI.msgbox.JDialogo.showDialog(null, moTipo.getTextoError(lsTexto));
              }
              
              
              //anulamos la seleccion para que solo haya un campo con todo seleccionado
              if(lbContinuar ){
                setSelectionStart(0);
                setSelectionEnd(0);
                ponerColorSiCambio();
              }
            break;
            default:
        }
        super.processFocusEvent(e);
        //no se hace esto pq en windows se mete en un bucle infinito
//        if(lbContinuar) 
//            super.processFocusEvent(e);
//        else 
//            this.requestFocus();
   }
    public void forzarLostFocus(){
        processFocusEvent(new FocusEvent(this, FocusEvent.FOCUS_LOST));
    }
   /**
    * Devuelve si el texto que hay en el componente es del tipo correcto, según mlTipo
    * @return si es correcto
    */
   public boolean isTipoCorrecto(){
        return moTipo.isTipoCorrecto(moTipo.getText());
   }
   
   protected void processKeyEvent(final KeyEvent e){
        int id = e.getID();
        switch(id) {
            case KeyEvent.KEY_TYPED:
                KeyEventCZ loKey = new KeyEventCZ(e.getKeyChar());
                moTipo.getTecla(super.getText(),loKey);
                e.setKeyChar(loKey.getKeyChar());
                break;
            case KeyEvent.KEY_PRESSED:
            if(e.getKeyCode()==e.VK_ENTER){
                e.setKeyCode(0);
                transferFocus();
            }
            if(e.getKeyCode()==e.VK_ESCAPE){
                e.setKeyCode(0);
                moTipo.restaurarTexto();
                super.setText(moTipo.getText());
            }
            break;
          case KeyEvent.KEY_RELEASED:

            break;
            default:
        }
        super.processKeyEvent(e);
   }
    protected void processTextEvent(TextEvent e) {
        int id = e.getID();
        switch (id) {
            case TextEvent.TEXT_VALUE_CHANGED:
                ponerColorSiCambio();
                break;
            default:
        }
        super.processTextEvent(e);
    }
    /**Devuelve el valor actual*/
    public Object getValueTabla() {
        return getText();
    }
    public String getText() {
        return moTipo.getText();
    }
    public void setText(final String t) {
        moTipo.setText(t);
        super.setText(moTipo.getTextFormateado());
    }

    /**
     * Establece el valor del text y el valor original, para lo del cambio de color si cambia
     * @param poValor valor
     */
    public void setValueTabla(final Object poValor)  {
       String lsValor;
        if(poValor==null){
            lsValor = "";
        }else{
            lsValor = poValor.toString();
        }
        moTipo.setText(lsValor);
        moTipo.setTextOriginal(moTipo.getText());
        super.setText(moTipo.getTextFormateado());
    }

}
