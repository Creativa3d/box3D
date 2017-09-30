/*
 * ListCZ.java
 *
 * Created on 20 de noviembre de 2003, 10:00
 */
package utilesGUI;


import  ListDatos.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Iterator;
import utiles.*;

/**ListBox*/
public class ListCZ extends java.awt.List  implements utilesGUI.tabla.IComponentParaTabla{
    private JListaElementos moListaClaves;
    /**Separación de la clave con la descripción*/
    public String msSeparacionDescri = " - ";
    
    private Color moBackColorConFoco = new Color(51,255,255);
    private Color moBackColor = null;
    //activamos el color de la letra si cambia con respectoa al original
    //si el valor original es null, se anula el efecto
    //si el valor original es <> null se activa el efecto
    private String  msValorOriginal = null; 
    private Color moForeColorCambio = new Color(255,0,0);
    private Color moForeColorNormal = null;

    /** Creates a new instance of ListCZ */
    public ListCZ() {
        super();
        
        enableEvents(AWTEvent.FOCUS_EVENT_MASK);
        enableEvents(AWTEvent.ITEM_EVENT_MASK);

        setCursor( new Cursor( Cursor.HAND_CURSOR ) );
        moListaClaves = new JListaElementos();
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
    public void setBackground(Color poColor){
        super.setBackground(poColor);
        moBackColor=poColor;
    }
    private void setBackgroundP(Color poColor){
        super.setBackground(poColor);
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
        this.removeAll();
        this.removeAll();
        moListaClaves = new JListaElementos();
        //    moListaClaves.add("");
        //    this.addItem("");
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
     * @param psClave clave(separada por JFilaDatos.mcsSeparador y termina en JFilaDatos.mcsSeparador)
     */
    public void addLinea(final String psDescri, final String psClave){
        this.addItem(psDescri);
        moListaClaves.add(psClave);
    }
    /**
     * Selecciona una fila por la clave
     * @param psClave clave, si son varios campos los valores deben estar separados por JFilaDatos.mcsSepracion1 y debe terminar siempre en JFilaDatos.mcsSepracion1
     * @return si ha seleccionado la clave en el componente
     */
    public boolean mbSeleccionarClave(final String psClave){
        boolean lbEncontrado=false;
        for (int i = 0 ;( i< this.getItemCount()) && (!lbEncontrado) ;i++){
          if (msDevolverValorActualCombo(i).compareTo(psClave)==0){
            this.select(i);
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
    public IFilaDatos getFila(final int i){
        return new JFilaDatosDefecto(msDevolverValorActualCombo(i));
    }
    /**Borramos la lista de datos*/
    public void borrarTodo(){
        moListaClaves = new JListaElementos();
        this.removeAll();
    }
    /**
     * Devuelve la clave del desplegable
     * @return clave
     * @param plIndex índice de la lista
     */
    public String msDevolverValorActualCombo(final int plIndex){
        String lsValor;
        if(plIndex<0) {
            lsValor=JFilaDatosDefecto.mcsSeparacion1;
        } else{
            String lsCond = (String)moListaClaves.get(plIndex);
            if (lsCond.compareTo("") == 0) {
                lsValor = JFilaDatosDefecto.mcsSeparacion1;
            }else  {
                lsValor=lsCond;
            }
        }
        return lsValor;
    }
    /**
     * Devuelve la descricipción actual
     * @return descripción
     */
    public String msDevolverDescri(){
        return getSelectedItem();
    }
    /**Método para procesar los eventos del teclado, si la tecla pulsada es el enter pasa al siguiente campo*/
    public void processKeyEvent(final KeyEvent e){
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
    /**Método para procesar los eventos del foco, pone el color de fondo en función de si tiene el foco o no*/
    protected void processFocusEvent(final FocusEvent e){
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
    /**Método para procesar los eventos de cambio de item seleccionado, pone el color de cambio
     en caso de que la fila sea distinta de la original*/
    protected void processItemEvent(final ItemEvent e) {
        ponerColorSiCambio();
        super.processItemEvent(e);

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

