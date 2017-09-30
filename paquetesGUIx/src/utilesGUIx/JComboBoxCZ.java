package utilesGUIx;


import ListDatos.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Iterator;
import javax.swing.*;
import utiles.*;

/**Lista de elementos en un desplegable*/
public class JComboBoxCZ extends  JComboBox implements utilesGUI.tabla.IComponentParaTabla{
    private static final long serialVersionUID = 1L;

    private IListaElementos moListaClaves;
    /**Separador del código con la descripción*/
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
    private boolean mbFocusable = true;
    private int[] mPosiDescris = null;
    private int[] mPosiCods = null;
    
    /**Constructor*/
    public JComboBoxCZ() {
        super(new JComboBoxModelCZ());

        enableEvents(java.awt.AWTEvent.FOCUS_EVENT_MASK);
        enableEvents(java.awt.AWTEvent.ITEM_EVENT_MASK);
        enableEvents(AWTEvent.KEY_EVENT_MASK);
        
        setCursor( new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR ) );
        moListaClaves = new JListaElementos();
        
    }
    public void setFocusable(final boolean focusable){
        final boolean lbAntig = mbFocusable;
        mbFocusable = focusable;
        firePropertyChange("focusable", lbAntig, mbFocusable);
    }
    
    public boolean isFocusable(){
        return mbFocusable;
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
     * Devuelve el valor original
     * @return valor
     */    
    public String getValorOriginal(){
        return msValorOriginal;
    }
    /**
     * Devuelve el color de la letra cuando cambia el texto del campo
     * @return color
     */    
    public Color getForeColorCambio(){
        return moForeColorCambio;
    }
    /**
     * Establece el color de cambio
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

    /**pòne el color cuando el componente cambia con respectoa al valor original*/
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
     * Establecemos el color de fondo cuando tiene el foco
     * @return color
     */
    public Color getBackColorConFoco(){
        return moBackColorConFoco;
    }
    /**
     * Establece el color de fondo para cuando tiene el foco
     * @param poColor color
     */    
    public void setBackColorConFoco(Color poColor){
        moBackColorConFoco = poColor;
    }
        /**
     * Rellena la lista de datos
     * @param poDatos datos
     * @param lPosiDescri posición de la descripción
     * @param lPosiCods posiciones de los códigos
     */
   public void RellenarCombo(IListaElementos poDatos,int lPosiDescri,int[] lPosiCods){
      RellenarCombo(poDatos, new int[]{lPosiDescri}, lPosiCods, true);
  }
       /**
     * Rellena la lista de datos
     * @param lPosiDescris posiciones de las descripciones
     * @param poDatos datos
     * @param lPosiCods posiciones de los códigos
     */
  public void RellenarCombo(IListaElementos poDatos,int[] lPosiDescris,int[] lPosiCods){
      RellenarCombo(poDatos, lPosiDescris, lPosiCods, true);
  }
      /**
     * Rellena la lista de datos
     * @param lPosiDescris lista de descipciones
     * @param poDatos datos
     * @param lPosiCods posiciones de los códigos
     * @param pbConBlanco si el primer elemento es blanco
     */
  public void RellenarCombo(IListaElementos poDatos,int[] lPosiDescris,int[] lPosiCods, boolean pbConBlanco){
    this.mPosiDescris = lPosiDescris;
    this.mPosiCods = lPosiCods;
    StringBuilder lsClave;
    String lsClaveS;
    String lsUltValor="";
    String lsDescriS="";
    IFilaDatos loFilaDatos;
    try{
    this.removeAllItems();
    }catch(Exception e) {}
    moListaClaves = new JListaElementos();
    if(pbConBlanco){
        moListaClaves.add("");
        this.addItem("");
    }
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
          this.addItem(lsDescriS);
          moListaClaves.add(lsClaveS);
          lsUltValor=lsClaveS;
        }
      }
  }
      /**
     * Añade una linea
     * @param psDescri descripción
     * @param psClave clave(separada por JFilaDatos.mcsSeparador)
     */
  public void addLinea(String psDescri, String psClave){
    this.addItem(psDescri);
    moListaClaves.add(psClave);
  }
      /**
     * Selecciona una fila por la clave
     * @return si ha tenido éxito
     * @param psClave clave
     */
  public boolean mbSeleccionarClave(String psClave){
    boolean lbEncontrado=false;
    for (int i = 0 ;( i< this.getItemCount()) && (!lbEncontrado) ;i++){
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
      /**
     * Devuelve la clave del elemento actual (separada por JFilaDatos.mcsSeparador)
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
     * @return Fila de datos
     * @param i Índice de la fila
     */
  public IFilaDatos getFila(int i){
      return new JFilaDatosDefecto(msDevolverValorActualCombo(i));
  }
  /**Borramos la lista de datos*/
  public void borrarTodo(){
    moListaClaves = new JListaElementos();
    this.removeAllItems();
  }
      /**
     * Devuelve la clave del desplegable
     * @return clave
     * @param plIndex índice de la lista
     */
  public String msDevolverValorActualCombo(int plIndex){
    String lsValor = JFilaDatosDefecto.mcsSeparacion1;
    if((plIndex>=0)&&((moListaClaves.size()>0))) {
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
    return getSelectedItem().toString();
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
            setValorOriginal(JFilaDatosDefecto.mcsSeparacion1);
        }else{
            if(poValor.toString().compareTo("")==0){
                setValorOriginal(JFilaDatosDefecto.mcsSeparacion1);
            }else{
                setValorOriginal(poValor.toString());
            }
        }
        mbSeleccionarClave(msValorOriginal);
    }

    public int[] getmPosiDescris() {
        return mPosiDescris;
    }

    public int[] getmPosiCods() {
        return mPosiCods;
    }
}
