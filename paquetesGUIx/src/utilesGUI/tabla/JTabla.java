/*
 * JTabla.java
 *
 * Created on 27 de septiembre de 2003, 9:09
 */

package utilesGUI.tabla;

import ListDatos.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Iterator;
import utiles.*;

/**Componente tabla, para presentar unos datos en formato tabla*/
public class JTabla extends Container implements java.io.Serializable, java.awt.event.FocusListener, java.awt.event.KeyListener {
    //nombre de los controles por diseño
    private static final String base = "JTabla";
    private static int nameCounter = 0;
    
    /**Alineación izq.*/
    public static final int mclAlinIzq = 0;
    /**Alineación Der.*/
    public static final int mclAlinDer = 1;
    /**Alineación Centro*/
    public static final int mclAlinCentro = 2;
    
    //evento cuando cambia fila
    private final JListaElementos moRowColChange = new JListaElementos();

    //variables de modelo y dibujado
    ITablaAntig moTabla = null;
    double madAnchos[]=new double[0];
    int malAlineaciones[]=new int[0];
    int malPosiCols[]=new int[0];
    /**
     *fila actual
     */
    int mlFilaSelec = 0;
    /**
     *Columna actual
     */
    int mlColSelec = 0;
    /**
     *Columna completa seleccionada
     */
    int mlColCabSelec = -1;
    /**
     *Fila Completa selecconada
     */
    int mlRowCabSelec = -1;
    /**
     *Ancho de la izq.
     */
    int mlAnchoIzq = 20;
    /**
     *Alto de la celda
     */
    int mlAltoCelda = 0;
    /**
     *Indica si tiene el foco
     */
    boolean mbConFoco = false;

    /**
     *Color fondo izq.
     */
    Color moBackColorIzq = new Color(239,239,239);
    /**
     *Color fondo cab.
     */
    Color moBackColorCab = new Color( 67, 105, 118);
    /**
     *Color fondo celdas 
     */
    Color moBackColor = new Color(222,231,236);
    /**
     *Color fondo fila select.
     */
    Color moBackColorSel = (new Color(222,231,236)).darker();
    /**
     *Color fore Izq
     */
    Color moForeColorIzq = (new Color(222,231,236)).darker();
    /**
     *Color fore de cabezera
     */
    Color moForeColorCab = Color.white;
    /**
     *Color fore letras seleccionadas
     */
    Color moForeColorSel = Color.white;
    /**
     *Color fore celdas
     */
    Color moForeColor = new Color( 67, 105, 118);
    /**
     *Color lineas
     */
    Color moColorLinea = Color.white;
    /**
     *Color barra extremos
     */
    Color moColorBarraExtremos = new Color( 67, 105, 118);
    /**
     *Color barra centro
     */
    Color moColorBarraCentro = new Color(222,231,236);
    /**
     *Color Barra Lineas
     */
    Color moColorBarraLineas = Color.white;
    /**
     *Color fore Izq
     */
    Color moColorLineaExtSel = new Color(51,153,101);
    
    /**
     *barras desplazac vertical
     */
    JBarraDesplazamiento moBarraV = new JBarraDesplazamiento(true);
    /**
     *barras desplazac horizontal
     */
    JBarraDesplazamiento moBarraH = new JBarraDesplazamiento(false);
    
    /**
     *Fuente celdas
     */
    Font fnt = new Font( "Arial", Font.PLAIN, 12 );
    /**
     *Fuente cabezera
     */
    Font fntCab = new Font( "Arial", Font.BOLD, 12 );
    /**
     *tamaño fuente celda
     */
    FontMetrics	fntm = getFontMetrics( fnt );
    /**
     *tamaño fuente cabezera
     */
    FontMetrics	fntmCab = getFontMetrics( fntCab );
    /**
    * Imagen para el buffer en donde pintar
    */
    Image moImg=null;
    transient  PintarTabla moThreadPintar=null;
    /**
     *Componentes de edición
     */
    Object[] maoEdicion = new Component[0];
    int mlUltCol = -1;

    /**
     *constructor
     */
    public JTabla() {
        super();
        initComponet();
	setName(base + nameCounter++);
    }
    /**
     * constructor con la tabla
     * @param poTabla en donde se sacarán los datos
     */
    public JTabla(ITablaAntig poTabla){
        super();
        initComponet();
        setTabla(poTabla);
    }
    /**
     *inicializamos componentes, eventos
     */
    private void initComponet(){
        moThreadPintar = new PintarTabla(this);
        moThreadPintar.start();
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        enableEvents(AWTEvent.ACTION_EVENT_MASK);
        enableEvents(AWTEvent.FOCUS_EVENT_MASK);
        enableEvents(AWTEvent.KEY_EVENT_MASK);
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
    }
    /**
     * Devuelve el motor de datos
     * @return motor de datos
     */
    public ITabla getTabla(){
        return moTabla;
    }
    /**
     * Establece el ano de la columna
     * @param plCol columna
     * @param pdAncho ancho en píxeles
     */
    public void setAncho(int plCol, double pdAncho){
        madAnchos[malPosiCols[plCol]] = pdAncho;
        refrescar();
    }
    /**
     * Devuelve el ancho de la columna
     * @return ancho ancho
     * @param plCol Columna
     */
    public double getAncho(int plCol){
        double ldAncho=0.0;
        if(malPosiCols.length!=0) {
            ldAncho=madAnchos[malPosiCols[plCol]];
        }
        return ldAncho;
    }
    /**
     * Establece la alineación de la columna
     * @param plCol columna
     * @param plAlineacion Alineación
     */
    public void setAlineacion(int plCol, int plAlineacion){
        malAlineaciones[malPosiCols[plCol]] = plAlineacion;
        refrescar();
    }
    /**
     * Devuelve la alineación de la columna
     * @return alineación
     * @param plCol índice de la col.
     */
    public int getAlineacion(int plCol){
        int lAli=0;
        if(malPosiCols.length!=0) {
            lAli=malAlineaciones[malPosiCols[plCol]];
        }
        return lAli;
    }
    /**
     * Devuelve el número de columnas
     * @return número de columnas
     */
    public int getCols(){
        return  malAlineaciones.length;
    }
    /**
     * Devuelve la columna seleccionada
     * @return Columna
     */
    public int getColSelTabla(){
        int lCol = 0;
        if(malPosiCols.length!=0){
            lCol = malPosiCols[mlColSelec];
        }
        return lCol;
    }
    /**
     * Devuelve la fila seleccionada
     * @return fila
     */
    public int getRowSelTabla(){
        return mlFilaSelec;
    }
//    public Component getComponenteTabla(int plFila,int plCol, Object poValor) throws Exception {
//        ((IComponentParaTabla)maoEdicion[plCol]).setValueTabla(poValor);
//        return (Component)maoEdicion[plCol];
//    }
    /**
     * Intercambia las columnas de sitio
     * @param plCol1 columna 1
     * @param plCol2 columna 2
     */
    public void intercambiarCol(int plCol1, int plCol2){
        int lPosi = malPosiCols[plCol1];
        malPosiCols[plCol1] = malPosiCols[plCol2];
        malPosiCols[plCol2] = lPosi;
        refrescar();
    }
    /**
     * establecemos una nueva tabla
     * @param poTabla tabla de datos
     */
    public void setTabla(ITablaAntig poTabla){
        moTabla = poTabla;
        madAnchos = new double[moTabla.getColumnCount()];
        malAlineaciones = new int[moTabla.getColumnCount()];
        malPosiCols = new int[moTabla.getColumnCount()];
        maoEdicion = new Object[moTabla.getColumnCount()];
	this.removeAll();
        int lAncho = (getBounds().width-mlAnchoIzq-moBarraV.mclAncho)/moTabla.getColumnCount();
        if (lAncho<100){
            lAncho = 100;
        }
        for(int i = 0 ; i< moTabla.getColumnCount(); i++){
            madAnchos[i] = lAncho;
            if ((moTabla.getColumnClass(i) == Integer.class)|
                (moTabla.getColumnClass(i) == Double.class)|
                (moTabla.getColumnClass(i) == Float.class)){
                malAlineaciones[i]=mclAlinDer;
            }else{
                if (moTabla.getColumnClass(i) == Boolean.class){
                    malAlineaciones[i]=mclAlinCentro;
                }else{
                    malAlineaciones[i]=mclAlinIzq;
                }
            }
            //cargamos los componentes
            if(moTabla.isCellEditable(0,i)){
                maoEdicion[i] = moTabla.getComponent(0, i);
            }else{
                maoEdicion[i] = null;
            }
            if(maoEdicion[i] != null){
                ((Component)maoEdicion[i]).setVisible(false);
                this.add((Component)maoEdicion[i]);
                ((Component)maoEdicion[i]).addFocusListener(this);
                ((Component)maoEdicion[i]).addKeyListener(this);
            }
            //establecemos el array de posiciones
            malPosiCols[i]=i;
        }
        madAnchos[moTabla.getColumnCount()-1] -= JBarraDesplazamiento.mclAncho;
        moBarraV.setIndex(0);
        moBarraH.setIndex(0);
        lanzarRowColChange(-1,-1);
        refrescar();
    }
    /**
     * Establecemos la fila seleccionada
     * @param i fila
     */
    public void setFilaSelec(int i){
        int lAnt = mlFilaSelec;
        mlFilaSelec = i;
        if(mlFilaSelec<0) {
            mlFilaSelec=lAnt;
        }
        if(mlFilaSelec>=moTabla.getRowCount()) {
            mlFilaSelec = lAnt;
        }
        if(mlFilaSelec != lAnt) {
            lanzarRowColChange(lAnt,mlColSelec);
            refrescar();
        }
    }
    /**
     * establecemos la col selec.
     * @param i columna
     */
    public void setColSelec(int i){
        int lAnt = mlColSelec;
        mlColSelec = i;
        if(mlColSelec<0) {
            mlColSelec=lAnt;
        }
        if(mlColSelec>=moTabla.getColumnCount()) {
            mlColSelec=lAnt;
        }
        if(mlColSelec!=lAnt) {
            lanzarRowColChange(mlFilaSelec,lAnt);
            refrescar();
        }
    }
    /**
     * Devuelve la primera fila visible
     * @return 1º fila visible
     */
    public int getVisibleFilaPrimera(){
        return moBarraV.mlIndex;
    }
    /**
     * Establece la primera fila visible
     * @param plIndex fila
     */
    public void setVisibleFilaPrimera(int plIndex){
        moBarraV.setIndex(plIndex);
        refrescar();
    }
    /**Refrescar*/
    public void refrescar(){
        moThreadPintar.setRefrescar(true);
        //ya repinta el trhead de pintado
//        if(moGraphics!=null)
//            updateg();
//        updateg();
    }
    /**
     * Devuelve si se auto repinta
     * @return si se repinta solo
     */
    public boolean getAutoRedraw(){
        return moThreadPintar.getAutoRedraw();
    }
    /**
     * establece si se auto repinta
     * @param pbAutoredraw si se auto repinta 
     */
    public void setAutoRedraw(boolean pbAutoredraw){
        boolean lbAuto = moThreadPintar.getAutoRedraw();
        moThreadPintar.setAutoRedraw(pbAutoredraw);
        if(lbAuto != moThreadPintar.getAutoRedraw()){
            refrescar();
        }
    }

    /**
    * The preferred size of the button.
    */
    public Dimension getPreferredSize() {
        return new Dimension(100, 100);
    }
    /**
    * The minimum size of the button.
    */
    public Dimension getMinimumSize() {
        return new Dimension(100, 50);
    }
    /**actualizamos el componente*/
    public void updateg() {
        if(moThreadPintar.getAutoRedraw()){
            Rectangle r = bounds();
            if (moImg==null){
                refrescar();
            }else{
                if((r.height!= moImg.getHeight(this))||
                   (r.width!= moImg.getWidth(this))) {
                    refrescar();
                }
            }
        }
        if (moImg!=null){
          getGraphics().drawImage(moImg, 0, 0, this);
        }
    }
    /**actualizamos el componente*/
    public void update(Graphics g) {
        updateg();
    }
    /**
     *pintamos la tabla
     */
    public void paint(Graphics g){
        updateg();
    }
   
    /**
     * Añade listener de cuando cambia una col. o fila
     * @param poList oyente
     */
   public void addRowColChangeListener(RowColChangeListener poList){
    moRowColChange.add(poList);
   }
   /**
    * borra listener de cuando cambia una col. o fila
    * @param poList oyente a borrar
    */
   public void removeRowColChangeListener(RowColChangeListener poList){
    moRowColChange.remove(poList);
   }
   /**
    *lanza el evento rowColChange a todos los listeners
    */
   private void lanzarRowColChange(int plFilaAnt, int plColAnt){
       try{
            for(Iterator loIter = moRowColChange.iterator(); loIter.hasNext(); ){
                RowColChangeListener loRow = (RowColChangeListener)loIter.next();
                loRow.CambioFila(new RowColChangeEvent(this, plFilaAnt, plColAnt));
            }
       }catch(Exception e){
           JDepuracion.anadirTexto(getClass().getName(), e);
       }
   }

   /** finalizamos el trhead del pintado */
    protected void finalize() throws Throwable {
        moThreadPintar.close();
        super.finalize();
    }   
   /**
    *procesamos el evetno de conseguir/perder foco
    */
   public void processFocusEvent(FocusEvent e){
        int id = e.getID();
//        boolean lbAnt = mbConFoco;
        switch(id) {
          case FocusEvent.FOCUS_GAINED:
               mbConFoco = true;
            break;
          case FocusEvent.FOCUS_LOST:
               mbConFoco = false;
            break;
            default:
        }
        //OJO: no hace falta pq el click ya refresca la tabla y pq por ahora es la unica manera de coger el foco
//        if(lbAnt != mbConFoco) refrescar();
        super.processFocusEvent(e);
   }
   /**
    *procesamos el evento de click
    */
   private void procesarClick(MouseEvent e) {
        if (moTabla!=null){
            //si ha pulsado en la barra
            if (moBarraV.mbClick(e)) {
                refrescar();
            }else {
                if (moBarraH.mbClick(e)) {
                    refrescar();
                }else{
                    //vemos la columna seleccionada
                    double ldAncho = mlAnchoIzq+1;
                    double ldAnchoAnt = ldAncho;
                    int lColSel = -1;
                    if(ldAncho <= e.getX()) {
                        for(int i = moBarraH.mlIndex; (i< moTabla.getColumnCount());i++){
                            ldAncho +=getAncho(i);
                            if((ldAncho > e.getX())&&(ldAnchoAnt < e.getX())) {
                                lColSel = i;
                            }
                            ldAnchoAnt = ldAncho;
                        }
                    }
                    //si se ha pulsado en la rejilla
                    int lFila = e.getY()/mlAltoCelda-1;
                    //si se ha pulsado en la cabezera
                    if (lFila>=0){
                        //si es distinto de la fila actual
                        if (((lFila + moBarraV.mlIndex) != mlFilaSelec)&&(moTabla.getRowCount()>(lFila + moBarraV.mlIndex))){
                            //ponemos la fila actual
                            setFilaSelec(lFila + moBarraV.mlIndex);
                            if(lColSel>=0) {
                                setColSelec(lColSel);
                            }
                        }
                    }else{

                        if((lColSel>=0)&&(lColSel<moTabla.getColumnCount())){
                            mlColCabSelec = lColSel;
                            moTabla.sortByColumn(malPosiCols[lColSel], true);
                            //ponemos la fila actual por si las moscas
                            lanzarRowColChange(mlFilaSelec,mlColSelec);
                            refrescar();
                        }else{
                            JFormProp loForm = new JFormProp(new java.awt.Frame(), true, this);
                            loForm.show();
                        }
                    }
                }
            }
        }
   }
   /**Procesamo el evento del ratón*/
   public void processMouseEvent(MouseEvent e) {
        switch(e.getID()) {
          case MouseEvent.MOUSE_CLICKED:
              procesarClick(e);
              break;
          case MouseEvent.MOUSE_PRESSED:
              requestFocus();
              break;
           default:   
        }
        super.processMouseEvent(e);
   }
   /**
    *procesamos el evento de cuendo se pulsa una tecla
    */
   public void processKeyEvent(KeyEvent e){
        int VK_LEFT           = 0x25;
        int VK_RIGHT          = 0x27;
        int VK_UP             = 0x26;
        int VK_DOWN           = 0x28;
        int VK_PAGE_UP        = 0x21;
        int VK_PAGE_DOWN      = 0x22;
        
        int lFilaAnt = mlFilaSelec;
        int lColAnt = mlColSelec;

        if (e.getKeyCode() ==VK_PAGE_UP){
            setFilaSelec(mlFilaSelec-moBarraV.mlBloque);
        }
        if (e.getKeyCode() ==VK_PAGE_DOWN){
            setFilaSelec(mlFilaSelec+moBarraV.mlBloque);
        }
        if (e.getKeyCode() ==VK_DOWN){
            setFilaSelec(mlFilaSelec+1);
        }
        if (e.getKeyCode() ==VK_UP){
            setFilaSelec(mlFilaSelec-1);
        }
        if (e.getKeyCode() ==VK_RIGHT){
            setColSelec(mlColSelec+1);
        }
        if (e.getKeyCode() == VK_LEFT){
            setColSelec(mlColSelec-1);
        }
        if(mlFilaSelec != lFilaAnt){
            moBarraV.setIndex(mlFilaSelec);
        }
        if(mlColSelec != lColAnt){
            moBarraH.setIndex(mlColSelec);
        }
        super.processKeyEvent(e);
   }
    /**
     * Color de fondo de la izq.
     * @return color
     */
    public Color getBackColorIzq(){
        return moBackColorIzq;
    }
    /**
     * Color de fondo de la izq.
     * @param poColor color
     */
    public void setBackColorIzq(Color poColor){
        moBackColorIzq = poColor;
    }
    /**
     * Color de fondo de la Cabezera
     * @return color
     */
    public Color getBackColorCab(){
        return moBackColorCab;
    }
    /**
     * Color de fondo de la Cabezera
     * @param poColor color
     */
    public void setBackColorCab(Color poColor){
        moBackColorCab = poColor;
    }
    /**
     * Color de fondo de la rejilla
     * @return color
     */
    public Color getBackColor(){
        return moBackColor;
    }
    /**
     * Color de fondo de la rejilla
     * @param poColor color
     */
    public void setBackColor(Color poColor){
        moBackColor = poColor;
    }
    /**
     * Color de fondo de la fila seleccionada
     * @return color
     */
    public Color getBackColorSel(){
        return moBackColorSel;
    }
    /**
     * Color de fondo de la fila seleccionada
     * @param poColor color
     */
    public void setBackColorSel(Color poColor){
        moBackColorSel = poColor;
    }
    /**
     * Color de letra(ovalo) de la izq.
     * @return color
     */
    public Color getForeColorIzq(){
        return moForeColorIzq;
    }
    /**
     * Color de letra(ovalo) de la izq.
     * @param poColor color
     */
    public void setForeColorIzq(Color poColor){
        moForeColorIzq = poColor;
    }
    /**
     * Color de letra de la cabezera
     * @return color

     */
    public Color getForeColorCab(){
        return moForeColorCab;
    }
    /**
     * Color de letra de la cabezera
     * @param poColor color
     */
    public void setForeColorCab(Color poColor){
        moForeColorCab = poColor;
    }
    /**Color de letra de la fila seleccionada
     * @return color
     */
    public Color getForeColorSel(){
        return moForeColorSel;
    }
    /**
     * Color de letra de la fila seleccionada
     * @param poColor color
     */
    public void setForeColorSel(Color poColor){
        moForeColorSel = poColor;
    }
    /**
     * Color de letra de la rejilla
     * @return color
     */
    public Color getForeColor(){
        return moForeColor;
    }
    /**
     * Color de letra de la rejilla
     * @param poColor color
     */
    public void setForeColor(Color poColor){
        moForeColor = poColor;
    }
    /**
     * Color de la linea que separa las filas
     * @return color
     */
    public Color getColorLinea(){
        return moColorLinea;
    }
    /**
     * Color de la linea que separa las filas
     * @param poColor color
     */
    public void setColorLinea(Color poColor){
        moColorLinea = poColor;
    }
    /**
     * Color de la barra de desplazamiento cuadro inicio/fin
     * @return color
     */
    public Color getColorBarraExtremos(){
        return moColorBarraExtremos;
    }
    /**
     * Color de la barra de desplazamiento cuadro inicio/fin
     * @param poColor color
     */
    public void setColorBarraExtremos(Color poColor){
        moColorBarraExtremos = poColor;
    }
    /**
     * Color de la barra de desplazamiento línea de en medio
     * @return color
     */
    public Color getColorBarraCentro(){
        return moColorBarraCentro;
    }
    /**
     * Color de la barra de desplazamiento línea de en medio
     * @param poColor color
     */
    public void setColorBarraCentro(Color poColor){
        moColorBarraCentro = poColor;
    }
    /**
     * Color de la barra de desplazamiento lineas de sepración de los elementos
     * @return color
     */
    public Color getColorBarraLineas(){
        return moColorBarraLineas;
    }
    /**
     * Color de la barra de desplazamiento lineas de sepración de los elementos
     * @param poColor color
     */
    public void setColorBarraLineas(Color poColor){
        moColorBarraLineas = poColor;
    }
    /**
     * Color de linea exterior de la tabla cuando tiene el foco
     * @return color
     */
    public Color getColorLineaExtSel(){
        return moColorLineaExtSel;
    }
    /**
     * Color de linea exterior de la tabla cuando tiene el foco
     * @param poColor color
     */
    public void setColorLineaExtSel(Color poColor){
        moColorLineaExtSel = poColor;
    }
  
    ///////////////////////////////////////////////////////////////////////////
    //// gestión de eventos para los componentes de edición                 ///
    ///////////////////////////////////////////////////////////////////////////
    
    /**consigue el foco*/
    public void focusGained(FocusEvent e) {
        Object loComp =  e.getSource();
        int lCol = 0;
        int lColDef =  0;
        for(int i = 0; i < maoEdicion.length ;i++){
            if(loComp == maoEdicion[i]) {
                lCol = i;
            }
        }
        for(int i = 0; i < malPosiCols.length ;i++){
            if(lCol == malPosiCols[i]) {
                lColDef = i;
            }
        }
        
        if((mlUltCol==lColDef) && ((malPosiCols.length-1) > mlUltCol)){
            moBarraH.setIndex(lColDef);
            refrescar();
        }
    }
    
    /**pierde el foco*/
    public void focusLost(FocusEvent e) {
        guardarDatos(e.getSource());
    }
    private void guardarDatos(Object poObjeto){
        Object loComp =  poObjeto;
        IComponentParaTabla loComp2 = (IComponentParaTabla)loComp;
        int lCol = 0;
        for(int i = 0; i < maoEdicion.length ;i++){
            if(loComp == maoEdicion[i]) {
                lCol = i;
            }
        }
        if(loComp2.getTextoCambiado()){
            moTabla.setValueAt(loComp2.getValueTabla(), getRowSelTabla(), lCol);
        }
    }
    
    /**tecla pulsada*/
    public void keyPressed(KeyEvent e) {
//        int VK_LEFT           = 0x25;
//        int VK_RIGHT          = 0x27;
        int VK_UP             = 0x26;
        int VK_DOWN           = 0x28;
        int VK_PAGE_UP        = 0x21;
        int VK_PAGE_DOWN      = 0x22;
        
        int lFilaAnt = mlFilaSelec;
        int lColAnt = mlColSelec;

        if (e.getKeyCode() ==VK_PAGE_UP){
            guardarDatos(e.getSource());
            setFilaSelec(mlFilaSelec-moBarraV.mlBloque);
        }
        if (e.getKeyCode() ==VK_PAGE_DOWN){
            guardarDatos(e.getSource());
            setFilaSelec(mlFilaSelec+moBarraV.mlBloque);
        }
        if (e.getKeyCode() ==VK_DOWN){
            guardarDatos(e.getSource());
            setFilaSelec(mlFilaSelec+1);
        }
        if (e.getKeyCode() ==VK_UP){
            guardarDatos(e.getSource());
            setFilaSelec(mlFilaSelec-1);
        }
        
        
//        if (e.getKeyCode() ==VK_RIGHT){
//            setColSelec(mlColSelec+1);
//        }
//        if (e.getKeyCode() == VK_LEFT){
//            setColSelec(mlColSelec-1);
//        }
        
        if(mlFilaSelec != lFilaAnt){
            moBarraV.setIndex(mlFilaSelec);
        }
        if(mlColSelec != lColAnt){
            moBarraH.setIndex(mlColSelec);
        }
    }
    
    /**tecla soltada*/
    public void keyReleased(KeyEvent e) {
        //vacio
    }
    
    /**tecla*/
    public void keyTyped(KeyEvent e) {
        //vacio
    }
    
}

