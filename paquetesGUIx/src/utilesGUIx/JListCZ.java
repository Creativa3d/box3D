/*
 * ListCZ.java
 *
 * Created on 20 de noviembre de 2003, 10:00
 */
package utilesGUIx;


import ListDatos.IFilaDatos;
import ListDatos.JFilaDatosDefecto;
import java.awt.*;
import java.awt.event.*;
import java.util.Iterator;
import javax.swing.*;
import utiles.*;

/**Componente para ver una lista de datos*/
public class JListCZ extends JList implements utilesGUI.tabla.IComponentParaTabla{
    private static final long serialVersionUID = 1L;

    private JModeloList moListaClaves;
    /**Separación de la clave con la descripción*/
    public String msSeparacionDescri = " - ";
    
    private Color moBackColorConFoco = JGUIxConfigGlobal.getInstancia().getBackColorFocoDefecto();
    private Color moBackColorAux = null;
    private Color moBackColorConDatos = JGUIxConfigGlobal.getInstancia().getBackColorConDatos();
    //activamos el color de la letra si cambia con respectoa al original
    //si el valor original es null, se anula el efecto
    //si el valor original es <> null se activa el efecto
    private String  msValorOriginal = null; 
    private Color moForeColorCambio = JGUIxConfigGlobal.getInstancia().getForeColorCambio();
    private Color moForeColorNormal = null;

    /** Creates a new instance of ListCZ */
    public JListCZ() {
        super();
        
        enableEvents(AWTEvent.FOCUS_EVENT_MASK);
        enableEvents(AWTEvent.ITEM_EVENT_MASK);
        enableEvents(AWTEvent.KEY_EVENT_MASK);
        
        setCursor( new java.awt.Cursor( java.awt.Cursor.HAND_CURSOR ) );
        moListaClaves = new JModeloList();
        setModel(moListaClaves);
    }
    /**
     * setValorOriginal
     * si el valor original es null, se anula el efecto
     * si el valor original es <> null se activa el efecto
     * @param psValor valor
     */
    public void setValorOriginal(String psValor){
        msValorOriginal = psValor;
        ponerColorSiCambio();
    }
    /**
     * Valor original
     * @return valor
     */    
    public String getValorOriginal(){
        return msValorOriginal;
    }
    /**Color de letra si cambia el valor
     * @return color
     */        
    public Color getForeColorCambio(){
        return moForeColorCambio;
    }
    /**
     * Color de letra si cambia el valor
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
        if(msValorOriginal!=null){
            lbCambiado = (msDevolverValorActualCombo().compareTo(msValorOriginal)!=0);
        }
        return lbCambiado;
    }
    private void salvarBackcolor(){
        if(moBackColorAux==null){
            moBackColorAux = getBackground();
        }
    }

    /**pone el color cuando el componente cambia con respectoa al valor original*/
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
            if(getSelectedIndex()>=0 && !getFilaActual().toString().equals(JFilaDatosDefecto.mcsSeparacion1)){
                salvarBackcolor();
                setBackground(moBackColorConDatos);
            }else{
                setBackground(moBackColorAux);
            }
        }

    }

    /**
     * Devuelve el color de fondo cuando tiene el foco
     * @return color
     */
    public Color getBackColorConFoco(){
        return moBackColorConFoco;
    }
    /**
     * Establece el color de fondo cuando el componente tiene el foco
     * @param poColor color
     */    
    public void setBackColorConFoco(Color poColor){
        moBackColorConFoco = poColor;
    }
    /**
     * Rellena la lista del componente
     * @param poDatos datos
     * @param lPosiDescri posición de la descripción
     * @param lPosiCods posiciones de los códigos
     */    
    public void RellenarCombo(IListaElementos poDatos,int lPosiDescri,int[] lPosiCods){
        RellenarCombo(poDatos, new int[]{lPosiDescri}, lPosiCods);
    }
    /**
     * Rellena la lista del componente
     * @param lPosiDescris posiciones de las descripciones
     * @param poDatos datos
     * @param lPosiCods posiciones de los códigos
     */    
    public void RellenarCombo(IListaElementos poDatos,int[] lPosiDescris,int[] lPosiCods){
        StringBuilder lsClave;
        String lsClaveS;
        String lsUltValor="";
        String lsDescriS="";
        IFilaDatos loFilaDatos;
        
        borrarTodo();
        
        Iterator enum1 = poDatos.iterator();
        lsClave=new StringBuilder(25);
        for ( ; enum1.hasNext() ;) {
            loFilaDatos=(IFilaDatos)enum1.next();
            lsClave.setLength(0);
            for( int i = 0 ; i<lPosiCods.length;i++){
              lsClave.append(loFilaDatos.msCampo(lPosiCods[i]));
              lsClave.append(JFilaDatosDefecto.mcsSeparacion1);
            }
            lsClaveS=lsClave.toString();
            lsClave.setLength(0);
            for( int i = 0 ; i<lPosiDescris.length;i++){
              if(i!=0){
                lsClave.append(msSeparacionDescri);
              }
              lsClave.append(loFilaDatos.msCampo(lPosiDescris[i]));
            }
            lsDescriS = lsClave.toString();
            if ((lsUltValor.compareTo(lsClaveS)!=0) && (lsDescriS.compareTo("") != 0)){
              moListaClaves.add(lsClaveS, lsDescriS);
              lsUltValor=lsClaveS;
            }
        }
    }
    /**
     * Añade una linea
     * @param psDescri descripción
     * @param psClave clave(separada por JFilaDatos.mcsSeparador y termina en JFilaDatos.mcsSeparador)
     */    
    public void addLinea(String psDescri, String psClave){
        moListaClaves.add(psClave, psDescri);
    }
    /**
     * Selecciona una fila por la clave
     * @param psClave clave, si son varios campos los valores deben estar separados por JFilaDatos.mcsSepracion1 y debe terminar siempre en JFilaDatos.mcsSepracion1
     * @return si ha seleccionado la clave en el componente
     */    
    public boolean mbSeleccionarClave(String psClave){
        boolean lbEncontrado=false;
        for (int i = 0 ;( i< getModel().getSize()) && (!lbEncontrado) ;i++){
          if (msDevolverValorActualCombo(i).compareTo(psClave)==0){
            this.setSelectedIndex(i);
            lbEncontrado=true;
          }
          if (lbEncontrado) {
              break;
          }
        }
        ponerColorSiCambio();
        return lbEncontrado;
    }
    /**Borra una línea*/
    public void borrarLinea(){
        moListaClaves.remove(getSelectedIndex());
    }
    /**
     * Devuelve la clave del elemento actual (separada por JFilaDatos.mcsSeparador y termina en JFilaDatos.mcsSeparador)
     * @return valor
     */    
    public String msDevolverValorActualCombo(){
        return msDevolverValorActualCombo(getSelectedIndex());
    }
    /**
     * Devuelve el obj. FilaDatos del elemento actual
     * @return fila 
     */    
    public IFilaDatos getFilaActual(){
        return new JFilaDatosDefecto(msDevolverValorActualCombo());
    }
    /**
     * Devuelve el obj. FilaDatos del índice pasado por parámetro
     * @return fila de datos
     * @param i índice de la fila
     */    
    public IFilaDatos getFila(int i){
        return new JFilaDatosDefecto(msDevolverValorActualCombo(i));
    }
    /**Borramos la lista de datos*/
    public void borrarTodo(){
        moListaClaves = new JModeloList();
        setModel(moListaClaves);
    }
    /**
     * Devuelve la clave del desplegable
     * @return clave
     * @param plIndex índice de la lista
     */    
    public String msDevolverValorActualCombo(int plIndex){
        String lsValor = JFilaDatosDefecto.mcsSeparacion1;
        if(plIndex>=0) {
            String lsCond = (String)moListaClaves.get(plIndex);
            if (lsCond.compareTo("") != 0) {
                lsValor = lsCond;
            }
        }
        return lsValor;
    }
    /**
     * Devuelve la descricipción actual
     * @return descripción
     */    
    public String msDevolverDescri(){
        return getSelectedValue().toString();
    }
    /**Método para procesar los eventos del foco, pone el color de fondo en función de si tiene el foco o no*/
    protected void processFocusEvent(FocusEvent e){
        int id = e.getID();
        switch(id) {
          case FocusEvent.FOCUS_GAINED:
              salvarBackcolor();
              setBackground(moBackColorConFoco);
            break;
          case FocusEvent.FOCUS_LOST:
              setBackground(moBackColorAux);
              ponerColorSiCambio();
            break;
          default:
        }
        super.processFocusEvent(e);
   }
    /**Método para procesar los eventos del teclado, si la tecla pulsada es el enter pasa al siguiente campo*/
   public void processKeyEvent(KeyEvent e){
        int id = e.getID();
        switch(id) {
          case KeyEvent.KEY_PRESSED:
            if(e.getKeyCode()==e.VK_ENTER || e.getKeyCode()==e.VK_PAGE_DOWN){
                this.transferFocus();
            }
            break;
          default:
        }
        super.processKeyEvent(e);
   }
    protected void processEvent(java.awt.AWTEvent e) {
        if(e.ITEM_EVENT_MASK == e.getID()){
            ponerColorSiCambio();
        }
        super.processEvent(e);
    }
    /**
     * Devuelve el valor actual
     * @return valor
     */    
    public Object getValueTabla() {
        return msDevolverValorActualCombo();
    }
     /**
     * Establece el valor del text y el valor original, para lo del cambio de color si cambia
     * @param poValor valor
     */
    public void setValueTabla(Object poValor)  {
        if(poValor==null){
            setValorOriginal("");
        }else{
            setValorOriginal(poValor.toString());
        }
        mbSeleccionarClave(msValorOriginal);
    }
    
}

class JModeloList implements javax.swing.ListModel{
    private final IListaElementos moListClaves;
    private final IListaElementos moListDescri;
    private final IListaElementos moListeners;
    
    public JModeloList() {
        moListClaves = new JListaElementos();
        moListDescri = new JListaElementos();
        moListeners = new JListaElementos();
    }
    
    public void add(String psClave, String psDescripcion){
        moListClaves.add(psClave);
        moListDescri.add(psDescripcion);
        Iterator loEnum = moListeners.iterator();
        javax.swing.event.ListDataEvent loEvent = new javax.swing.event.ListDataEvent(this,javax.swing.event.ListDataEvent.INTERVAL_ADDED,moListClaves.size(),moListClaves.size());
        for(;loEnum.hasNext();){
            Object loObj = loEnum.next();
            javax.swing.event.ListDataListener loLis = (javax.swing.event.ListDataListener)loObj;
            loLis.contentsChanged(loEvent);
        }
    }
    public Object get(int i){
        return moListClaves.get(i);
    }
    
    public void addListDataListener(javax.swing.event.ListDataListener l) {
        moListeners.add(l);
    }
    
    public Object getElementAt(int index) {
        return moListDescri.get(index);
    }
    
    public int getSize() {
        return moListClaves.size();
    }
    
    public void removeListDataListener(javax.swing.event.ListDataListener l) {
        moListeners.remove(l);
    }
    
    public void remove(int index){
        moListDescri.remove(index);
        moListClaves.remove(index);
        Iterator loEnum = moListeners.iterator();
        javax.swing.event.ListDataEvent loEvent = new javax.swing.event.ListDataEvent(this,javax.swing.event.ListDataEvent.INTERVAL_ADDED,moListClaves.size(),moListClaves.size());
        for(;loEnum.hasNext();){
            Object loObj = loEnum.next();
            javax.swing.event.ListDataListener loLis = (javax.swing.event.ListDataListener)loObj;
            loLis.contentsChanged(loEvent);
        }
        
    }
    
}

