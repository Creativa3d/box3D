package utilesGUIx;

import ListDatos.*;
import ListDatos.estructuraBD.JFieldDef;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.util.Iterator;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;

import utiles.*;
import utilesGUI.tiposTextos.ITipoTexto;
import utilesGUI.tiposTextos.JTipoTextoEstandar;
import utilesGUI.tiposTextos.KeyEventCZ;
import utilesGUIx.formsGenericos.edicion.ITextBD;
import utilesGUIx.msgbox.JMsgBox;
import utilesGUIx.textPopUpMenu.PopupMenuEventQueue;

/**Componente de texto*/
public class JTextFieldCZ extends javax.swing.JTextField implements utilesGUI.tabla.IComponentParaTabla, DocumentListener, BeanInfo, ITextBD {
    private static final long serialVersionUID = 1L;
    
    private ITipoTexto moTipo = new JTipoTextoEstandar(JGUIxConfigGlobal.getInstancia().getTipoDefectoCadenaBD());
    private String msFormato=null;
    
    //efecto cuando coge el foco
    private Color moBackColorConFoco = JGUIxConfigGlobal.getInstancia().getBackColorFocoDefecto();
    private Color moBackColorAux = null;
    private Color moBackColorConDatos = JGUIxConfigGlobal.getInstancia().getBackColorConDatos();
    private Color moForeColorCambio = JGUIxConfigGlobal.getInstancia().getForeColorCambio();
    private Color moForeColorNormal = null;
    
    private IListaElementos moListChanged = null;
    
//    protected EventListenerList listenerList;
//    protected ChangeEvent changeEvent;
    
    public boolean mbAsocidoATabla = false;
    
    private JFieldDef moCampo = null;
    private boolean mbMensajePresentado = false;
    private boolean mbPopUPActivo=false;
    private boolean mbDesactivarSetText=false;
    
    /**Constructor*/
    public JTextFieldCZ() {
        super();
        enableEvents(AWTEvent.FOCUS_EVENT_MASK);
        enableEvents(AWTEvent.KEY_EVENT_MASK);
        setDocument(createDefaultModel());
    }
    
    protected Document createDefaultModel() {
        return createDefaultModel(0);
    }
    protected Document createDefaultModel(final int plTamanoMax) {
        PlainDocument loDocu;
        if(plTamanoMax>0){
            loDocu = new JPlainDocumentMax(plTamanoMax);
            loDocu.addDocumentListener(this);
        }else{
            loDocu = new PlainDocument();
            loDocu.addDocumentListener(this);
        }
        return loDocu;
    }
    public void setLongitudTextoMaxima(int plTamanoMax){
        getDocument().removeDocumentListener(this);
        setDocument(createDefaultModel(plTamanoMax));
    }
    
    public JFieldDef getField(){
        return moCampo;
    }
    /**Establecemos el campo de la BD*/
    public void setField(final JFieldDef poCampo){
        moCampo = poCampo;
        setTipoBD(poCampo.getTipo());
        if(poCampo.getTipo() == JListDatos.mclTipoCadena &&
                poCampo.getTamano() > 0 &&
                poCampo.getTamano() < 1000){
            setLongitudTextoMaxima(poCampo.getTamano());
//            getDocument().removeDocumentListener(this);
//            setDocument(createDefaultModel(poCampo.getTamano()));
        }
    }
    /**Devolvemos el campo de la BD*/
    public JFieldDef getCampo(){
        return moCampo;
    }
    
    /**Mostramos los datos del campo de BD guardado*/
    public void mostrarDatosBD(){
        if(moCampo!=null){
            setValueTabla(moCampo.toString());
        }
    }
    /**Establecemos los datos de campo de BD guardado*/
    public void establecerDatosBD() throws ECampoError{
        if(moCampo!=null){
            moCampo.setValue(getText());
        }
    }
    
    /**
     * @return the msFormato
     */
    public String getFormato() {
        return msFormato;
    }

    /**
     * @param msFormato the msFormato to set
     */
    public void setFormato(String msFormato) {
        this.msFormato = msFormato;
    }
    
    /**
     * Añade un oyente para cuando cambia el texto
     * @param poText oyente
     */
    public void addTextListener(final TextListener poText){
        if(moListChanged == null) {
            moListChanged = new JListaElementos();
        }
        moListChanged.add(poText);
    }
    /**
     * Borra un oyente para cuando cambia el texto
     * @param poText oyente
     */
    public void removeTextListener(final TextListener poText){
        if(moListChanged == null) {
            moListChanged = new JListaElementos();
        }
        moListChanged.remove(poText);
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
    public void setForeColorCambio(final Color poColor){
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
    private void salvarBackcolor(){
        if(moBackColorAux==null){
            moBackColorAux = getBackground();
        }
    }
    /**pone el color si el texto cambia con respecto al valor original*/
    public void ponerColorSiCambio(){
        if(moTipo.isTextoCambiado()){
            if(getForeColorCambio()!=getForeground()){
                moForeColorNormal = getForeground();
            }
            setForeground(getForeColorCambio());
        }else{
            if(moForeColorNormal!=null){
                setForeground(moForeColorNormal);
            }
        }
        if(moBackColorConDatos!=null){
            if(!moTipo.getTextFormateado().equals("")){
                salvarBackcolor();
                setBackground(moBackColorConDatos);
            }else{
                setBackground(moBackColorAux);
            }
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
        if(getFormato()==null || getFormato().equals("")){
            setTipo(new JTipoTextoEstandar(plTipo));
        }else{
            setTipo(new JTipoTextoEstandar(plTipo, getFormato()));
        }
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
     * y el tamaño del campo
     * @param plTipoBD tipo de Base datos(de JListDatos)
     * @param plTamano tamaño máximo
     */
    public void setTipoBD(final int plTipoBD, final int plTamano){
        setTipoBD(plTipoBD);
        if(plTamano>0){
            getDocument().removeDocumentListener(this);
            setDocument(createDefaultModel(plTamano));
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
            case JListDatos.mclTipoNumero:
                setTipo(JTipoTextoEstandar.mclTextNumeroEntero);
                setHorizontalAlignment(JTextField.RIGHT);
                break;
            case JListDatos.mclTipoNumeroDoble:
                setTipo(JTipoTextoEstandar.mclTextNumeroDoble);
                setHorizontalAlignment(JTextField.RIGHT);
                break;
            case JListDatos.mclTipoMoneda3Decimales:
                setTipo(JTipoTextoEstandar.mclTextMoneda3Decimales);
                setHorizontalAlignment(JTextField.RIGHT);
                break;
            case JListDatos.mclTipoMoneda:
                setTipo(JTipoTextoEstandar.mclTextMoneda);
                setHorizontalAlignment(JTextField.RIGHT);
                break;
            case JListDatos.mclTipoPorcentual3Decimales:
                setTipo(JTipoTextoEstandar.mclTextPorcentual3Decimales);
                setHorizontalAlignment(JTextField.RIGHT);
                break;
            case JListDatos.mclTipoPorcentual:
                setTipo(JTipoTextoEstandar.mclTextPorcentual);
                setHorizontalAlignment(JTextField.RIGHT);
                break;
            default:
                setTipo(JGUIxConfigGlobal.getInstancia().getTipoDefectoCadenaBD());
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
    public void setBackColorConFoco(final Color poColor){
        moBackColorConFoco = poColor;
    }
    
    private void quitarColorFondo(){
        setBackground(moBackColorAux);
    }
    private void ponerColorFondo(){
        moBackColorAux = getBackground();
        setBackground(moBackColorConFoco);
    }

    protected void processMouseEvent(MouseEvent e) {
        super.processMouseEvent(e);

        if (e.isPopupTrigger()) {
            try{
                mbPopUPActivo=true;
                PopupMenuEventQueue lo = new PopupMenuEventQueue();
                lo.createPopupMenu(this);
                lo.showPopup(this, e);
            }finally{
                SwingUtilities.invokeLater(new Runnable() {

                    public void run() {
                        mbPopUPActivo=false;
                    }
                });
                
            }
        }
    }
    
    protected void processFocusEvent(final FocusEvent e){
        boolean lbContinuar = true;
        if(!mbAsocidoATabla){
            int id = e.getID();
            switch(id) {
                case FocusEvent.FOCUS_GAINED:
                    if(!mbPopUPActivo){
                        setTextReal(moTipo.getText());
                    }
                    ponerColorFondo();
                    if(!mbPopUPActivo){
                        setSelectionStart(0);
                        setSelectionEnd(moTipo.getText().length());
                    }
                    break;
                case FocusEvent.FOCUS_LOST:
                    quitarColorFondo();
                    String lsTexto = super.getText();
                    if(moTipo.getTextFormateado().equals(lsTexto)){
                        lsTexto = moTipo.getText();
                    }
                    if (moTipo.isTipoCorrecto(lsTexto)) {
                        moTipo.lostFocus(lsTexto);
                        if(!mbPopUPActivo){
                            setTextReal(moTipo.getTextFormateado());
                        }
                    }else{
                        lbContinuar = !moTipo.isTipoCorrectoObligatorio();
                        if(lbContinuar){
                            moTipo.lostFocus(lsTexto);
                            if(!mbPopUPActivo){
                                setTextReal(moTipo.getTextFormateado());
                            }
                        }
                        
                        if(!mbMensajePresentado){
                            mbMensajePresentado=true;
                            JMsgBox.mensajeError(this, moTipo.getTextoError(lsTexto));
                            mbMensajePresentado=false;
                        }
                    }
                    //anulamos la seleccion para que solo haya un campo con todo seleccionado
                    if(lbContinuar ){
                        if(!mbPopUPActivo){
                            setSelectionStart(0);
                            setSelectionEnd(0);
                        }
                        ponerColorSiCambio();
                    }
                    break;
                default:
            }
        }

        if(lbContinuar){
            super.processFocusEvent(e);
        }else{
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    requestFocus();
                }
            });
            
        }
//        super.processFocusEvent(e);
    }
    private void establecerTextoEnTipo(){ 
        if(!mbDesactivarSetText){
            mbDesactivarSetText=true;
            try{

                boolean lbContinuar;
                String lsTexto = super.getText();
                if(!moTipo.getTextFormateado().equals(lsTexto)){
                    if (moTipo.isTipoCorrecto(lsTexto)) {
                        moTipo.lostFocus(lsTexto);
//                        if(!mbPopUPActivo){
//                            setTextReal(moTipo.getTextFormateado());
//                        }
                    }else{
                        lbContinuar = !moTipo.isTipoCorrectoObligatorio();
                        if(lbContinuar){
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
            }catch(Throwable e){
            }finally{
                mbDesactivarSetText=false;
            }
        }        
    }
    /**Devuelve si el texto que hay en el componente es del tipo correcto, según mlTipo
     * @return si es correcto
     */
    public boolean isTipoCorrecto(){
        return moTipo.isTipoCorrecto(moTipo.getText());
    }
//  /**
//   * El tamaño preferido
//   */
//  public Dimension getPreferredSize() {
//      return super.getPreferredSize();
//  }
//
//  /**
//   * el Tamaño minimo
//   */
//  public Dimension getMinimumSize() {
//      return super.getMinimumSize();
//  }
    
    protected void processKeyEvent(final KeyEvent e){
//        if((e.getKeyCode() == '\n')||(e.getKeyCode() == '\b')||(e.getKeyCode() == ' ')){
        int id = e.getID();
        switch(id) {
            case KeyEvent.KEY_TYPED:
                KeyEventCZ loKey = new KeyEventCZ(e.getKeyChar());
                moTipo.getTecla(super.getText(),loKey);
                e.setKeyChar(loKey.getKeyChar());
                break;
            case KeyEvent.KEY_PRESSED:
                if((e.getKeyCode()==e.VK_ENTER || e.getKeyCode()==e.VK_PAGE_DOWN)&&(!mbAsocidoATabla)){
                    e.setKeyCode(0);
                    transferFocus();
                }
                if(e.getKeyCode()==e.VK_ESCAPE){
                    e.setKeyCode(0);
                    moTipo.restaurarTexto();
                    setTextReal(moTipo.getText());
                }
                break;
            case KeyEvent.KEY_RELEASED:
                break;
            default:
        }
        super.processKeyEvent(e);
    }
    public void insertUpdate(final DocumentEvent e){
        ponerColorSiCambio();
        llamarChanged();
    }
    public void removeUpdate(final DocumentEvent e){
        ponerColorSiCambio();
        llamarChanged();
    }
    public void changedUpdate(final DocumentEvent e){
        ponerColorSiCambio();
        llamarChanged();
    }
    private void llamarChanged(){
        if(moListChanged!=null){
            Iterator loEnum= moListChanged.iterator();
            TextEvent e = new TextEvent(this, TextEvent.TEXT_VALUE_CHANGED);
            while(loEnum.hasNext()){
                ((TextListener)loEnum.next()).textValueChanged(e);
            }
        }
    }
    /**Devuelve el valor actual*/
    public Object getValueTabla() {
        return getText();
    }
    public String getText() {
        establecerTextoEnTipo();
        return moTipo.getText();
    }
    public String getTextReal() {
        return super.getText();
    }
    public void setText(final String t) {
        moTipo.setText(t);
        setTextReal(moTipo.getTextFormateado());
    }
    private void setTextReal(String psText){
        boolean lbDesactOrig = mbDesactivarSetText;
        mbDesactivarSetText=true;
        try{
            super.setText(psText);
        }finally{
            mbDesactivarSetText=lbDesactOrig;
        }
    }
    
    /**
     * Establece el valor del text y el valor original, para lo del cambio de color si cambia
     * @param poValor valor
     */
    public void setValueTabla(final Object poValor)  {
        mbDesactivarSetText=true;
        try{
            String lsValor;
            if(poValor==null){
                lsValor = "";
            }else{
                lsValor = poValor.toString();
            }
            moTipo.setText(lsValor);
            moTipo.setTextOriginal(moTipo.getText());
            if(!mbAsocidoATabla){
                setTextReal(moTipo.getTextFormateado());
            }else{
                setTextReal(moTipo.getText());
            }
        }finally{
            mbDesactivarSetText=false;
        }
    }
    
    
    public BeanDescriptor getBeanDescriptor() {return null;}
//    private boolean mbHayMetodo(java.lang.reflect.Method[] poMetodos, String psMetodo){
//        boolean lbHay = false;
//        for(int i = 0; i<poMetodos.length; i++){
//            if(poMetodos[i].getName().compareTo(psMetodo) == 0){
//                lbHay = true;
//            }
//        }
//        return lbHay;
//    }
    private boolean mbExisteMetodo(final IListaElementos poList, final String psMetodo){
        Iterator loEnum = poList.iterator();
        String lsMetodoAux = psMetodo.toUpperCase();
        boolean lbExiste = false;
        while(loEnum.hasNext()){
            PropertyDescriptor loPor = (PropertyDescriptor)loEnum.next();
            if(lsMetodoAux.compareTo(loPor.getName().toUpperCase())==0){
                lbExiste=true;
            }
        }
        return lbExiste;
    }
    public PropertyDescriptor[] getPropertyDescriptors() {
        try{
            IListaElementos loList = new JListaElementos();
            PropertyDescriptor psc  = new PropertyDescriptor("tipo",utilesGUIx.JTextFieldCZ.class);
            psc.setPropertyEditorClass(utilesGUIx.JTextoTipo.class);
            loList.add(psc);
            java.lang.reflect.Method[] loMetodos = getClass().getMethods();
            for(int i = 0; i<loMetodos.length; i++){
                if(loMetodos[i].getName().compareTo("getDefaultLocale")!=0){
                    if(loMetodos[i].getName().substring(0,3).compareTo("get") == 0){
                        try{
//                            java.lang.reflect.Method loMetodo = getClass().getMethod("set"+loMetodos[i].getName().substring(3), new Class[]{loMetodos[i].getReturnType()});
                            if(!mbExisteMetodo(loList, loMetodos[i].getName().substring(3))){
                                psc  = new PropertyDescriptor(
                                        loMetodos[i].getName().substring(3,4).toLowerCase() +
                                        loMetodos[i].getName().substring(4),
                                        utilesGUIx.JTextFieldCZ.class);
                                loList.add(psc);
                            }
                        }catch(Exception eMetod){
                            //NO BORRAR que luego pega unos casques en diseño de form que flipas
                        }
                    }
                }
            }
            PropertyDescriptor[] loPro = new PropertyDescriptor[loList.size()];
            for(int i = 0 ; i< loPro.length; i++){
                loPro[i] = (PropertyDescriptor)loList.get(i);
            }
            return loPro;
            
        } catch (Exception e) {
            throw new Error(e.toString());
        }
    }
    public int getDefaultPropertyIndex() {return -1;}
    public EventSetDescriptor[] getEventSetDescriptors() {return null;}
    public int getDefaultEventIndex() {return -1;}
    public MethodDescriptor[] getMethodDescriptors() {return null;}
    public BeanInfo[] getAdditionalBeanInfo() {	return null;}
    public java.awt.Image getIcon(final int iconKind) {return null;}
    /**
     * Carga la imagen para la paleta de propiedades del programa de diseño de JAVA
     * @param resourceName source name
     * @return imagen
     */
    public java.awt.Image loadImage(final String resourceName) {
        java.awt.Image loReturn = null;
//	try {
//	    final Class c = getClass();
//	    java.awt.image.ImageProducer ip = (java.awt.image.ImageProducer)
//		java.security.AccessController.doPrivileged(
//		new java.security.PrivilegedAction() {
//		    public Object run() {
//			java.net.URL url = c.getResource(resourceName);
//			if (url == null) {
//			    return null;
//			} else {
//			    try {
//				return url.getContent();
//			    } catch (java.io.IOException ioe) {
//				return null;
//			    }
//			}
//		    }
//	    });
//
//	    if (ip != null){
//                java.awt.Toolkit tk = java.awt.Toolkit.getDefaultToolkit();
//                loReturn=tk.createImage(ip);
//            }
//	} catch (Exception ex) {
//	    loReturn=null;
//	}
        return loReturn;
    }
    
//    ////////////////////////////////////////////////////////////////7
//    ///                   implementar  TableCellEditor
//    ////////////////////////////////////////////////////////////////7
    public Object getCellEditorValue() {
        if(!moTipo.isTipoCorrecto(moTipo.getText())){
            if(!mbMensajePresentado){
                mbMensajePresentado=true;
                JMsgBox.mensajeError(this, moTipo.getTextoError(super.getText()));
                mbMensajePresentado=false;
            }
            throw new InternalError(moTipo.getTextoError(super.getText()));
        }
        moTipo.lostFocus(super.getText());
        return getText();
    }


    
//    private void crearLinenerChange(){
//        if(changeEvent==null){
//            changeEvent = new ChangeEvent(this);
//        }
//        if(listenerList==null){
//            listenerList = new EventListenerList();
//        }
//        DefaultCellEditor lo; 
//    }
//    public void addCellEditorListener(final CellEditorListener listener) {
//        crearLinenerChange();
//        listenerList.add(CellEditorListener.class, listener);
//        mbNoAsocidoATabla = false;
//    }
//    public void removeCellEditorListener(final CellEditorListener listener) {
//        crearLinenerChange();
//        listenerList.remove(CellEditorListener.class, listener);
//    }
//    protected void fireEditingCanceled() {
//        CellEditorListener listener;
//        crearLinenerChange();
//        Object[] listeners = listenerList.getListenerList();
//        for (int i = 0; i < listeners.length; i++) {
//            if (listeners[i] == CellEditorListener.class) {
//                listener = (CellEditorListener) listeners[i + 1];
//                listener.editingCanceled(changeEvent);
//            }
//        }
//    }
//    protected void fireEditingStopped() {
//        CellEditorListener listener;
//        Object[] listeners = listenerList.getListenerList();
//        for (int i = 0; i < listeners.length; i++) {
//            if (listeners[i] == CellEditorListener.class) {
//                listener = (CellEditorListener) listeners[i + 1];
//                listener.editingStopped(changeEvent);
//            }
//        }
//    }
//    public void cancelCellEditing() {
//        fireEditingCanceled();
//    }
//    
//    public boolean stopCellEditing() {
//        fireEditingStopped();
//        return true;
//    }
//    
//    
//    public Component getTableCellEditorComponent(final JTable table, final Object value, final boolean isSelected, final int row, final int column) {
//        setValueTabla(value);
//        
//        if(value!=null){
//            setSelectionStart(0);
//            setSelectionEnd(value.toString().length());
//        }
//        
//        return this;
//    }
//    
//    public boolean isCellEditable(final EventObject event) {
//        return true;
//    }
//    
//    public boolean shouldSelectCell(final EventObject event) {
//        return true;
//    }
}
