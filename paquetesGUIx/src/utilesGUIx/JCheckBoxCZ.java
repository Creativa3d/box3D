   /*
 * CheckBoxCZ.java
 *
 * Created on 19 de noviembre de 2003, 13:02
 */

package utilesGUIx;

import ListDatos.ECampoError;
import ListDatos.estructuraBD.JFieldDef;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import utilesGUI.tabla.*;
import utilesGUIx.formsGenericos.edicion.ITextBD;
/**CheckBox personalizado*/
public class JCheckBoxCZ extends JCheckBox implements IComponentParaTabla, ITextBD  {
    private static final long serialVersionUID = 1L;
    
    private Color moBackColorConFoco = JGUIxConfigGlobal.getInstancia().getBackColorFocoDefecto();
    private Color moBackColorAux = null;
    private Color moBackColorConDatos = JGUIxConfigGlobal.getInstancia().getBackColorConDatos();
    //si el valor original es null, se anula el efecto
    //si el valor original es <> null se activa el efecto
    private Boolean  mbValorOriginal = null; 
    private Color moForeColorCambio = JGUIxConfigGlobal.getInstancia().getForeColorCambio();
    private Color moForeColorNormal = null;
    private boolean mbLabelHTMLDefecto = JGUIxConfigGlobal.getInstancia().isLabelHTMLDefecto();


    private JFieldDef moCampo = null;

    /** Creates a new instance of CheckBoxCZ */
    public JCheckBoxCZ() {
        super();
        enableEvents(AWTEvent.FOCUS_EVENT_MASK);
        enableEvents(AWTEvent.KEY_EVENT_MASK);
    }
    public JFieldDef getField(){
        return moCampo;
    }

    /**Establecemos el campo de la BD*/
    public void setField(final JFieldDef poCampo){
        moCampo = poCampo;
        if(mbLabelHTMLDefecto){
            setText("<html><p>"+poCampo.getCaption()+"</p></html>");
            setToolTipText("<html><p>"+poCampo.getCaption()+"</p></html>");
        }else{
            setText(poCampo.getCaption());
            setToolTipText(poCampo.getCaption());
        }
        setForeground((poCampo.getNullable()?null:JGUIxConfigGlobal.getInstancia().getLabelColorObligatorio()));
    }
    /**Devolvemos el campo de la BD*/
    public JFieldDef getCampo(){
        return moCampo;
    }
    
    /**Mostramos los datos del campo de BD guardado*/
    public void mostrarDatosBD(){
        if(moCampo!=null){
            setValueTabla(moCampo.getBooleanConNull());
        }
    }
    
    /**Establecemos los datos de campo de BD guardado*/
    public void establecerDatosBD() throws ECampoError{
        if(moCampo!=null){
            moCampo.setValue(isSelected());
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
        boolean lbCambiado = false;
        if(mbValorOriginal!=null){
            lbCambiado = (isSelected()!=mbValorOriginal.booleanValue());
        }
        return lbCambiado;
    }
    private void salvarBackcolor(){
        if(moBackColorAux==null){
            moBackColorAux = getBackground();
        }
    }
    /**pone el color si ha cambiado*/
    public void ponerColorSiCambio(){
        if(getTextoCambiado()){
            if(getForeColorCambio()!=getForeground()){
                moForeColorNormal = getForeground();
                if(moForeColorNormal == null) {
                    moForeColorNormal = Color.black;
                }
            }
             setForeground(getForeColorCambio());
        }else{
            if(moForeColorNormal!=null){
                setForeground(moForeColorNormal);
            }
        }
        if(moBackColorConDatos!=null){
            if(isSelected()){
                salvarBackcolor();
                setBackground(moBackColorConDatos);
            }else{
                setBackground(moBackColorAux);
            }
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
    /**
     *Procesamos el evento de conseguir/perder foco
     */    
   protected void processFocusEvent(java.awt.event.FocusEvent e){
        int id = e.getID();
        switch(id) {
          case java.awt.event.FocusEvent.FOCUS_GAINED:
              salvarBackcolor();
              setBackground(moBackColorConFoco);
            break;
          case java.awt.event.FocusEvent.FOCUS_LOST:
              setBackground(moBackColorAux);
              ponerColorSiCambio();
            break;
          default:
        }
        super.processFocusEvent(e);
   }
    /**
     *Procesamos el evento del teclado
     */
   protected void processKeyEvent(KeyEvent e){
        int id = e.getID();
        switch(id) {
          case KeyEvent.KEY_PRESSED:
            if(e.getKeyCode()==e.VK_ENTER  || e.getKeyCode()==e.VK_PAGE_DOWN){
                this.transferFocus();
            }
            break;
          default:
        }
        super.processKeyEvent(e);
   }
   /**Establecemos el estado*/
    public void setSelected(boolean state) {
        super.setSelected(state);
        ponerColorSiCambio();
    }
    /**
     * Devuelve el valor actual del componenete en modo objeto
     * @return valor
     */    
    public Object getValueTabla() {
        return (isSelected() ? Boolean.TRUE : Boolean.FALSE);
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
        setSelected(mbValorOriginal.booleanValue());
    }

    public void setValueTabla(boolean pbValor) {
        if(pbValor) {
            setValueTabla(Boolean.TRUE);
        } else {
            setValueTabla(Boolean.FALSE);
        }
    }
    

//  ////////////////////////////////////////////////////////////////7
//    ///                   implementar  TableCellEditor
//    ////////////////////////////////////////////////////////////////7
//    
//    private void crearLinenerChange(){
//        if(changeEvent==null){
//            changeEvent = new ChangeEvent(this);
//        }
//        if(listenerList==null){
//            listenerList = new EventListenerList();
//        }
//    }
//    public void addCellEditorListener(CellEditorListener listener) {
//        crearLinenerChange();
//        listenerList.add(CellEditorListener.class, listener);
//    }    
//    public void removeCellEditorListener(CellEditorListener listener) {
//        crearLinenerChange();
//        listenerList.remove(CellEditorListener.class, listener);
//    }
//    protected void fireEditingCanceled() {
//      CellEditorListener listener;
//      crearLinenerChange();
//      Object[] listeners = listenerList.getListenerList();
//      for (int i = 0; i < listeners.length; i++) {
//        if (listeners[i] == CellEditorListener.class) {
//          listener = (CellEditorListener) listeners[i + 1];
//          listener.editingCanceled(changeEvent);
//        } 
//      } 
//    }
//    protected void fireEditingStopped() {
//      CellEditorListener listener;
//      Object[] listeners = listenerList.getListenerList();
//      for (int i = 0; i < listeners.length; i++) {
//        if (listeners[i] == CellEditorListener.class) {
//          listener = (CellEditorListener) listeners[i + 1];
//          listener.editingStopped(changeEvent);
//        } 
//      } 
//    } 
//    public void cancelCellEditing() {
//        fireEditingCanceled();
//    }
//    
//    public boolean stopCellEditing() {
//      fireEditingStopped();
//      return true;
//    }     
//
//    public Object getCellEditorValue() {
//        return getValueTabla();
//    }
//    
//    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
//        setValueTabla(value);
//        return this;        
//    }
//    
//    public boolean isCellEditable(EventObject event) {
//        return true;
//    } 
//
//    public boolean shouldSelectCell(EventObject event) {
//      return true;
//    } 
 
}
