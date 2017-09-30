/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesFX;

import ListDatos.ECampoError;
import ListDatos.IFilaDatos;
import ListDatos.JFilaDatosDefecto;
import ListDatos.estructuraBD.JFieldDef;
import com.sun.javafx.scene.traversal.Direction;
import java.util.Iterator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import utiles.IListaElementos;

/**
 *
 * @author eduardo
 */
public class JFieldConComboBox implements IFieldControl {

    private static final long serialVersionUID = 1L;

    private ObservableList<JCMBLinea> moLista;
    /**
     * Separador del código con la descripción
     */
    public String msSeparacionDescri = " - ";
    //activamos el color de la letra si cambia con respectoa al original
    //si el valor original es null, se anula el efecto
    //si el valor original es <> null se activa el efecto
    private String msValorOriginal = null;
    private int[] mPosiDescris = null;
    private int[] mPosiCods = null;
    private final ComboBox<JCMBLinea> moControl;
    private JFieldDef[] moCampos;
    private final boolean mbAsocidoATabla;

//    private Color moBackColorConFoco = JGUIxConfigGlobal.getInstancia().getBackColorFocoDefecto();
//    private Color moBackColorAux = null;
//    private Color moBackColorConDatos = JGUIxConfigGlobal.getInstancia().getBackColorConDatos();
//    private Color moForeColorCambio = JGUIxConfigGlobal.getInstancia().getForeColorCambio();
//    private Color moForeColorNormal = null;
    public JFieldConComboBox(ComboBox<JCMBLinea> poCombo) {
        this(poCombo, false);
    }
    
    public JFieldConComboBox(ComboBox<JCMBLinea> poCombo, boolean pbAsociadoATabla) {
        moControl = poCombo;
        mbAsocidoATabla=pbAsociadoATabla;
        moLista = FXCollections.observableArrayList();
        moControl.setItems(moLista);

        if(!mbAsocidoATabla){

            moControl.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent e) -> {
                if (e.getEventType() == KeyEvent.KEY_PRESSED) {
                    if ((e.getCode() == KeyCode.ENTER
                            || e.getCode() == KeyCode.DOWN)) {
                        e.consume();
                        moControl.impl_traverse(Direction.NEXT);
                    }
                }
            });
            moControl.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
                if (newValue) {
                    salvarBackcolor();
                    //                          setBackground(moBackColorConFoco);
                } else {
                    //                          setBackground(moBackColorAux);
                    ponerColorSiCambio();
                    
                }
            });
        }

    }

    public JFieldConComboBox(ComboBox<JCMBLinea> poCombo, JFieldDef poCampo) {
        this(poCombo, false);
        setField(poCampo);
    }

    public JFieldConComboBox(ComboBox<JCMBLinea> poCombo, JFieldDef[] poCampos) {
        this(poCombo, false);
        setFields(poCampos);
    }
    public ComboBox getComponente(){
        return moControl;
    }
    
    /**
     * Establecemos el campo de la BD
     * @param poCampo
     */
    public void setField(final JFieldDef poCampo) {
        setFields(new JFieldDef[]{poCampo});
    }

    /**
     * Establecemos el campo de la BD
     * @param poCampos
     */
    public void setFields(final JFieldDef[] poCampos) {
        moCampos = poCampos;
    }

    @Override
    public JFieldDef getCampo() {
        if (moCampos != null && moCampos.length > 0) {
            return moCampos[0];
        } else {
            return null;
        }
    }

    /**
     * Devolvemos el campo de la BD
     * @return 
     */
    public JFieldDef[] getCampos() {
        return moCampos;
    }


    @Override
    public void mostrarDatosBD() {
        if (moCampos != null) {
            StringBuilder lasString = new StringBuilder();
            for (int i = 0; i < moCampos.length; i++) {
                lasString.append(moCampos[i].getString());
                lasString.append(JFilaDatosDefecto.mccSeparacion1);
            }
            setValueTabla(lasString.toString());
        }
    }

    @Override
    public void establecerDatosBD() throws ECampoError {
        if (moCampos != null) {
            IFilaDatos loFila = getFilaActual();
            for (int i = 0; i < moCampos.length && i < loFila.mlNumeroCampos(); i++) {
                moCampos[i].setValue(loFila.msCampo(i));
            }
            for (int i = loFila.mlNumeroCampos(); i < moCampos.length; i++) {
                moCampos[i].setValue("");
            }
        }
    }

    /**
     * setValorOriginal si el valor original es null, se anula el efecto si el
     * valor original es <> null se activa el efecto
     *
     * @param psValor valor
     */
    public void setValorOriginal(String psValor) {
        msValorOriginal = psValor;
        ponerColorSiCambio();
    }

    /**
     * Devuelve el valor original
     *
     * @return valor
     */
    public String getValorOriginal() {
        return msValorOriginal;
    }
//    /**
//     * Devuelve el color de la letra cuando cambia el texto del campo
//     * @return color
//     */    
//    public Color getForeColorCambio(){
//        return moForeColorCambio;
//    }
//    /**
//     * Establece el color de cambio
//     * @param poColor color
//     */    
//    public void setForeColorCambio(Color poColor){
//        moForeColorCambio = poColor;
//    }

    /**
     * devuelve el si el texto a cabiado y si hay un valor orioginal
     *
     * @return si ha cambiado
     */
    @Override
    public boolean getTextoCambiado() {
        boolean lbCambiado = false;
        if (msValorOriginal != null) {
            lbCambiado = (msDevolverValorActualCombo().compareTo(msValorOriginal) != 0);
        }
        return lbCambiado;
    }

    private void salvarBackcolor() {
//        if(moBackColorAux==null){
//            moBackColorAux = getBackground();
//        }
    }

    /**
     * pòne el color cuando el componente cambia con respectoa al valor original
     */
    public void ponerColorSiCambio() {
//        if(getTextoCambiado()){
//            if(getForeColorCambio()!=getForeground()){
//                moForeColorNormal = getForeground();
//                if(moForeColorNormal == null) {
//                    moForeColorNormal = Color.black;
//                }
//            }
//            setForeground(getForeColorCambio());
//        }else{
//            if(moForeColorNormal!=null){
//                setForeground(moForeColorNormal);
//            }
//        }
//        if(moBackColorConDatos!=null){
//            if(getSelectedIndex()>=0 && !getFilaActual().toString().equals(JFilaDatosDefecto.mcsSeparacion1)){
//                salvarBackcolor();
//                setBackground(moBackColorConDatos);
//            }else{
//                setBackground(moBackColorAux);
//            }
//        }

    }

//    /**
//     * Establecemos el color de fondo cuando tiene el foco
//     * @return color
//     */
//    public Color getBackColorConFoco(){
//        return moBackColorConFoco;
//    }
//    /**
//     * Establece el color de fondo para cuando tiene el foco
//     * @param poColor color
//     */    
//    public void setBackColorConFoco(Color poColor){
//        moBackColorConFoco = poColor;
//    }
    /**
     * Rellena la lista de datos
     *
     * @param poDatos datos
     * @param lPosiDescri posición de la descripción
     * @param lPosiCods posiciones de los códigos
     */
    public void RellenarCombo(IListaElementos poDatos, int lPosiDescri, int[] lPosiCods) {
        RellenarCombo(poDatos, new int[]{lPosiDescri}, lPosiCods, true);
    }

    /**
     * Rellena la lista de datos
     *
     * @param lPosiDescris posiciones de las descripciones
     * @param poDatos datos
     * @param lPosiCods posiciones de los códigos
     */
    public void RellenarCombo(IListaElementos poDatos, int[] lPosiDescris, int[] lPosiCods) {
        RellenarCombo(poDatos, lPosiDescris, lPosiCods, true);
    }

    /**
     * Rellena la lista de datos
     *
     * @param lPosiDescris lista de descipciones
     * @param poDatos datos
     * @param lPosiCods posiciones de los códigos
     * @param pbConBlanco si el primer elemento es blanco
     */
    public void RellenarCombo(IListaElementos poDatos, int[] lPosiDescris, int[] lPosiCods, boolean pbConBlanco) {
        this.mPosiDescris = lPosiDescris;
        this.mPosiCods = lPosiCods;
        StringBuffer lsClave;
        String lsClaveS;
        String lsUltValor = "";
        String lsDescriS = "";
        IFilaDatos loFilaDatos;
        try {
            moLista.clear();
        } catch (Exception e) {
        }
        if (pbConBlanco) {
            moLista.add(new JCMBLinea("", ""));
        }
        Iterator enum1 = poDatos.iterator();
        lsClave = new StringBuffer(25);
        for (; enum1.hasNext();) {
            loFilaDatos = (IFilaDatos) enum1.next();
            lsClave.setLength(0);
            for (int i = 0; i < lPosiCods.length; i++) {
                lsClave.append(loFilaDatos.msCampo(lPosiCods[i]));
                lsClave.append(JFilaDatosDefecto.mcsSeparacion1);
            }
            lsClaveS = lsClave.toString();
            lsClave.setLength(0);
            for (int i = 0; i < lPosiDescris.length; i++) {
                if (i != 0) {
                    lsClave.append(msSeparacionDescri);
                }
                lsClave.append(loFilaDatos.msCampo(lPosiDescris[i]));
            }
            lsDescriS = lsClave.toString();
            if ((lsUltValor.compareTo(lsClaveS) != 0) && (lsDescriS.compareTo("") != 0)) {
                moLista.add(new JCMBLinea(lsDescriS, lsClaveS));
                lsUltValor = lsClaveS;
            }
        }
        moControl.setItems(moLista);
    }
    public static String getDescrip(IFilaDatos poFila, int[] lPosiDescris){
        return getDescrip(poFila, lPosiDescris, " - ");
    }
    
    public static String getDescrip(IFilaDatos poFila, int[] lPosiDescris, String psSeparacionDescrip){
        StringBuilder lsClave = new StringBuilder(25);
        lsClave.setLength(0);
        for (int i = 0; i < lPosiDescris.length; i++) {
            if (i != 0) {
                lsClave.append(psSeparacionDescrip);
            }
            lsClave.append(poFila.msCampo(lPosiDescris[i]));
        }
        return lsClave.toString();
    }

    /**
     * Añade una linea
     *
     * @param psDescri descripción
     * @param psClave clave(separada por JFilaDatos.mcsSeparador)
     */
    public void addLinea(String psDescri, String psClave) {
        boolean lbVacioPrev = moLista.isEmpty();
        moLista.add(new JCMBLinea(psDescri, psClave));
        if(lbVacioPrev){
            getComponente().getSelectionModel().select(0);
        }
    }

    /**
     * Selecciona una fila por la clave
     *
     * @return si ha tenido éxito
     * @param psClave clave
     */
    public boolean mbSeleccionarClave(String psClave) {
        boolean lbEncontrado = false;
        for (int i = 0; (i < moLista.size()) && (!lbEncontrado); i++) {
            if (msDevolverValorActualCombo(i).compareTo(psClave) == 0) {
                moControl.getSelectionModel().select(i);
                lbEncontrado = true;
            }
            if (lbEncontrado) {
                break;
            }
        }
        ponerColorSiCambio();
        return lbEncontrado;
    }

    /**
     * Devuelve la clave del elemento actual (separada por
     * JFilaDatos.mcsSeparador)
     *
     * @return valor
     */
    public String msDevolverValorActualCombo() {
        return msDevolverValorActualCombo(moControl.getSelectionModel().getSelectedIndex());
    }

    /**
     * Devuelve el obj. FilaDatos del elemento actual
     *
     * @return fila
     */
    public IFilaDatos getFilaActual() {
        return new JFilaDatosDefecto(msDevolverValorActualCombo());
    }

    /**
     * Devuelve el obj. FilaDatos del índice pasado por parámetro
     *
     * @return Fila de datos
     * @param i Índice de la fila
     */
    public IFilaDatos getFila(int i) {
        return new JFilaDatosDefecto(msDevolverValorActualCombo(i));
    }

    /**
     * Borramos la lista de datos
     */
    public void borrarTodo() {
        moLista.clear();
    }

    /**
     * Devuelve la clave del desplegable
     *
     * @return clave
     * @param plIndex índice de la lista
     */
    public String msDevolverValorActualCombo(int plIndex) {
        String lsValor = JFilaDatosDefecto.mcsSeparacion1;
        if ((plIndex >= 0) && ((moLista.size() > 0))) {
            JCMBLinea lsCond = moLista.get(plIndex);
            if (lsCond.getclave().compareTo("") != 0) {
                lsValor = lsCond.getclave();
            }
        }
        return lsValor;
    }

    /**
     * Devuelve la descricipción actual
     *
     * @return descripción
     */
    public String msDevolverDescri() {
        return moControl.valueProperty().toString();
    }
//  /**Método para procesar los eventos del foco, pone el color de fondo en función de si tiene el foco o no*/
//   protected void processFocusEvent(FocusEvent e){
//        int id = e.getID();
//        switch(id) {
//          case FocusEvent.FOCUS_GAINED:
//              salvarBackcolor();
//              setBackground(moBackColorConFoco);
//            break;
//          case FocusEvent.FOCUS_LOST:
//              setBackground(moBackColorAux);
//              ponerColorSiCambio();
//            break;
//          default:
//        }
//        super.processFocusEvent(e);
//   }
//   /**Método para procesar los eventos del teclado, si la tecla pulsada es el enter pasa al siguiente campo*/
//   public void processKeyEvent(KeyEvent e){
//        int id = e.getID();
//        switch(id) {
//          case KeyEvent.KEY_PRESSED:
//            if(e.getKeyCode()==e.VK_ENTER || e.getKeyCode()==e.VK_PAGE_DOWN){
//                this.transferFocus();
//            }
//            break;
//          default:
//        }
//        super.processKeyEvent(e);
//   }
//    protected void processEvent(java.awt.AWTEvent e) {
//        if(e.ITEM_EVENT_MASK == e.getID()){
//            ponerColorSiCambio();
//        }
//        super.processEvent(e);
//    }

    /**
     * Devuelve el valor actual
     *
     * @return valor
     */
    @Override
    public Object getValueTabla() {
        return msDevolverValorActualCombo();
    }

    /**
     * Establece el valor del text y el valor original, para lo del cambio de
     * color si cambia
     *
     * @param poValor valor
     */
    @Override
    public void setValueTabla(Object poValor) {
        if (poValor == null) {
            setValorOriginal(JFilaDatosDefecto.mcsSeparacion1);
        } else {
            if (poValor.toString().compareTo("") == 0) {
                setValorOriginal(JFilaDatosDefecto.mcsSeparacion1);
            } else {
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
