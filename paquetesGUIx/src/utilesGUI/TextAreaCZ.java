package utilesGUI;

import java.awt.*;
import java.awt.event.*;
import utiles.*;
import ListDatos.*;
import utilesGUI.tiposTextos.ITipoTexto;
import utilesGUI.tiposTextos.JTipoTextoEstandar;
import utilesGUI.tiposTextos.KeyEventCZ;

/**Componte para escribir texto en varias lineas*/
public class TextAreaCZ extends TextArea implements utilesGUI.tabla.IComponentParaTabla {
    
    public static int mlTipoDefectoCadenaBD = JTipoTextoEstandar.mclTextCadena;
    private ITipoTexto moTipo = new JTipoTextoEstandar(mlTipoDefectoCadenaBD);
    
    //efecto cuando coge el foco
    private Color moBackColorConFoco = new Color(51,255,255);
    private Color moBackColor = null;
    private Color moForeColorCambio = new Color(255,0,0);
    private Color moForeColorNormal = null;
    
    /**Contructor*/
    public TextAreaCZ() {
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
    /**
     * Devuelve el color de la letra cuando el componente cambia con respectoa al valor original
     * @return color
     */
    public Color getForeColorCambio(){
        return moForeColorCambio;
    }
    /**
     * Establece el color de la letra cuando el componente cambia con respectoa al valor original
     * @param poColor color
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
    /**pòne el color cuando el componente cambia con respectoa al valor original*/
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
     * establecemos el tipo del textbox(numerico, texto, fecha)
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
     * devolvemos el color de fondo cuando tiene el foco
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
            if(e.getKeyCode()==e.VK_TAB){
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
//  /**
//   * El tamaño preferido
//   */
//  public Dimension getPreferredSize() {
//      return super.preferredSize(0,0);
//  }
//
//  /**
//   * el Tamaño minimo
//   */
//   public Dimension getMinimumSize() {
//      return super.minimumSize(0,0);
//   }
    /**Devuelve el valor actual*/
    public Object getValueTabla() {
        return moTipo.getText();
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