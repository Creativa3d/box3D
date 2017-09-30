package utilesGUI;

import java.awt.*;
import java.applet.*;
import java.awt.event.*;
import utiles.*;
import java.util.Calendar;

/**Objeto calendario*/
public class CalendarCZ extends Component implements utilesGUI.tabla.IComponentParaTabla {

   //nombre de los controles por diseño
    private static final String base = "CalendarCZ";
    private static int nameCounter = 0;
   /**
     *Colores
     */
    private Color moColorLetrasDias = Color.blue;
    private Color moColorFondoDias = Color.white;
    private Color moColorLetrasDiasSemana = Color.white;
    private Color moColorFondoDiasSemana = new Color(339900);
    private Color moColorLetrasAnoMes = Color.black;
    private Color moColorFondoAnoMes  = new Color(192, 192, 192);
    private Color moColorLetrasDiaSeleccionado = Color.white;
    private Color moColorFondoDiaSeleccionado = Color.blue;
    private Color moColorLineaExtSel= new Color(51,153,101);

    /**
     *Elementos visuales de tamaños y posiciones
     */
    Rectangle[]	controls = new Rectangle[4];
    Rectangle   moRectDia = new Rectangle(26,16);
    Rectangle   moRectCompleto = new Rectangle(182,96);
    /**
     *Imagenes 
     */
    java.awt.Image moImageLeft=null;
    java.awt.Image moImageRight=null;
    /**
     *Alto de la cabezera del año, mes y dias semana(L,M,M...)
     */
    int mclAltoCabezera = 32;
    int mclAltoCabezeraAnoMes = 16;
    int mclAltoCabezeraDiasSemana = 16;
    /**
     *Elementos del calendario
     */
    String[]	msNombreMeses = new String[12];
    String[]	msDiasSemana = new String[7];
    /**
     *Fuentes
     */
    Font	fnt = new Font( "Courier New", Font.PLAIN, 12 );
    Font	fntDiaActual = new Font( "Courier New", Font.BOLD, 12 );
    FontMetrics	fntm = getFontMetrics( fnt );
    /**
     *foco
     */
     boolean mbConFoco = false;
    /**
     *Fecha
     */
    JDateEdu     moFecha = new JDateEdu();
    JListaElementos moListSelect = new JListaElementos();
    /**
     *Elementos que escuchan
     */
    ActionListener  actionListener;     

    /**Constructor*/
    public CalendarCZ() {
        super();
        setName(base + nameCounter++);
        moFecha.setHora(0);
        moFecha.setMinuto(0);
        moFecha.setSegundo(0);
        init();
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
    }
    /**inicializamos */
    public void init() {
        msDiasSemana[0] = "L"; msDiasSemana[1] = "M"; msDiasSemana[2] = "M";
        msDiasSemana[3] = "J"; msDiasSemana[4] = "V"; msDiasSemana[5] = "S";
        msDiasSemana[6] = "D";

        msNombreMeses = moFecha.masNombresMes();

        crearcontrols();
    }

    //creamos las variables de tamaño
    private void crearcontrols(){
        if (getBounds().height> 230){
            mclAltoCabezera = 64;
            mclAltoCabezeraAnoMes = 32;
            mclAltoCabezeraDiasSemana = 32;
        }else{
            mclAltoCabezera = 32;
            mclAltoCabezeraAnoMes = 16;
            mclAltoCabezeraDiasSemana = 16;
        }
        if (moImageLeft==null){
            moImageRight = getToolkit().getImage(getClass().getResource("images/FastForward16.gif"));
            moImageLeft = getToolkit().getImage(getClass().getResource("images/Rewind16.gif"));
        }

        moRectDia = new Rectangle(getBounds().width/7,(getBounds().height-mclAltoCabezera) / 6);
        moRectCompleto = new Rectangle(moRectDia.width * 7,moRectDia.height*6+mclAltoCabezera);

        controls[0] = new Rectangle( 2, 0, fntm.stringWidth( "<<" ), mclAltoCabezeraDiasSemana );
        controls[1] = new Rectangle( 108 - fntm.stringWidth( ">>" ), 0, fntm.stringWidth( ">>" ), mclAltoCabezeraDiasSemana );
        controls[2] = new Rectangle( moRectCompleto.width -66, 0, fntm.stringWidth( "<<" ), mclAltoCabezeraDiasSemana );
        controls[3] = new Rectangle( moRectCompleto.width -2 - fntm.stringWidth( ">>" ), 0, fntm.stringWidth( ">>" ), mclAltoCabezeraDiasSemana );
    }
    /**
     * añade una fecha para que se seleccione en el calendario
     * @param poFecha Fecha
     * @param poBackColor color de fondo
     * @param poForeColor color de letras
     */
    public void addSelec(JDateEdu poFecha, Color poBackColor, Color poForeColor){
        moListSelect.add(new JElementoSelec(poFecha, poBackColor, poForeColor));
    }
   /**
   * The preferred size of the button.
   */
   public Dimension getPreferredSize() {
       return new Dimension(40,40);
   }
   /**Borra las fechas seleccionadas*/
   public void removeSelecAll(){
        moListSelect.clear();
    }
    private int mlPosicionSelec(JDateEdu poFecha){
        int lPosi = -1;
        for(int i = 0 ; i< moListSelect.size();i++){
            if(((JElementoSelec)moListSelect.get(i)).moFecha.compareTo(poFecha)==0){
                lPosi=i;
                break;
            }
        }
        return lPosi;
    }
    /**
     * Devuelve la fecha actual
     * @return fecha
     */
    public JDateEdu getFecha(){
      return  moFecha;
    }
    /**
     * Establecer la fecha actual
     * @param pdia Día 
     * @param pmes mes
     * @param pano año
     */
    public void setFecha(int pdia,int pmes,int pano){
      moFecha.setDia(pdia);
      moFecha.setMes(pmes);
      moFecha.setAno(pano);
      
      repaint();
    }
    /**
     * Establecer la fecha actual
     * @param poFecha fecha
     */
    public void setFecha(JDateEdu poFecha){
      moFecha = poFecha;
      repaint();
    }
    /**lanzamos evento*/
    public void cambioFecha(){
        if(actionListener != null) {
           actionListener.actionPerformed(new ActionEvent(
               this, ActionEvent.ACTION_PERFORMED, moFecha.toString()));
        }        
    }
    /**
    * Procesa los eventos del raton
    */
     public void processMouseEvent(MouseEvent e) {
         switch(e.getID()) {
            case MouseEvent.MOUSE_PRESSED:
              requestFocus();
              mouseDown(e,e.getX(),e.getY());
              break;
            case MouseEvent.MOUSE_RELEASED:
              break;
            case MouseEvent.MOUSE_ENTERED:
              break;
            case MouseEvent.MOUSE_EXITED:
              break;
            case MouseEvent.MOUSE_MOVED:
              break;
             default:
         }
         super.processMouseEvent(e);
    }
     /**
      * procesamos el evento de pulsado raton
      * @return si se ha procesado
      * @param evt e
      * @param x x
      * @param y y
      */
    public boolean mouseDown( MouseEvent evt, int x, int y ) {
        int		i, a;
        boolean lbProcesar;
        lbProcesar=true;
        for ( i = 0; i < 4; i++ ) {
            if ( controls[i].contains( x, y ) ) {
                break;
            }
        }
        switch ( i){
            case 0:
                moFecha.add(Calendar.MONTH, -1);
                cambioFecha();
                break;
            case 1:
                moFecha.add(Calendar.MONTH, 1);
                cambioFecha();
                break;
            case 2:
                moFecha.add(Calendar.YEAR, -1);
                cambioFecha();
                break;
            case 3:
                moFecha.add(Calendar.YEAR, 1);
                cambioFecha();
                break;
            default:
                a = mlDiaPulsado(x,y);
                if ( a >= 0 ) {
                    moFecha.setDia(a);
                    cambioFecha();
                }
                else {
                    lbProcesar=false;
                }
                break;
        }
        if (lbProcesar) {
          repaint();
        }
        return true;
    }
    /**
     * devuelve el dia pulsado en funcion de x,y
     * @return día
     * @param x x
     * @param y y
     */
    public int mlDiaPulsado(int x, int y){
        //letras dias
        int lDia =-1;
        int lDiasMes = moFecha.mlDiasMesActual();
        JDateEdu loFechaAux = new JDateEdu();
        loFechaAux.setDate(moFecha.moDate());
        loFechaAux.setDia(1);
        int lDiaSemana= loFechaAux.getDiaSemanaEspana();
        Rectangle loRect = new Rectangle(moRectDia.width, moRectDia.height);
        
        for ( int i = lDiaSemana; i < (lDiasMes+lDiaSemana); i++ ) {
            loFechaAux.setDia(i+1-lDiaSemana);
            
            loRect.x = ( i % 7 ) * moRectDia.width;
            loRect.y = ( i / 7 ) * moRectDia.height + mclAltoCabezera;
            if (loRect.contains(x,y)) {
                lDia=loFechaAux.getDia();        
            }
        }
        return lDia;
    }
    /**actualizar componente*/
    public void update( Graphics gc ) {
        paint( gc );
    }
    /**actualizar componente*/
    public void paint( Graphics g ) {
        int		i;
        String		str;
        if (isShowing()){
        crearcontrols();

        //rectangulo año,mes
        g.setColor( moColorFondoAnoMes  );
        g.fillRect( 0, 0, moRectCompleto.width, mclAltoCabezeraAnoMes );

        //rectangulos dias semana
        g.setColor(moColorFondoDiasSemana);
        g.fillRect( 0, mclAltoCabezeraAnoMes, moRectCompleto.width, mclAltoCabezeraDiasSemana );
        
        //rectangulos dias
        g.setColor(moColorFondoDias);
        g.fillRect( 0, mclAltoCabezera, moRectDia.width * 7, moRectDia.height * 6 );

        //letras año,mes
        g.setFont( fnt );
        g.setColor( moColorLetrasAnoMes );
        
        g.drawImage(moImageLeft, controls[0].x, mclAltoCabezeraAnoMes/2+4-fntm.getHeight(), 16, 16, this);
        g.drawImage(moImageRight, controls[1].x, mclAltoCabezeraAnoMes/2+4-fntm.getHeight(), 16, 16, this);
        g.drawImage(moImageLeft, controls[2].x, mclAltoCabezeraAnoMes/2+4-fntm.getHeight(), 16, 16, this);
        g.drawImage(moImageRight, controls[3].x, mclAltoCabezeraAnoMes/2+4-fntm.getHeight(), 16, 16, this);
        
//        g.drawString( "<<", controls[0].x, mclAltoCabezeraAnoMes/2+2 );
//        g.drawString( ">>", controls[1].x, mclAltoCabezeraAnoMes/2+2 );
//        g.drawString( "<<", controls[2].x, mclAltoCabezeraAnoMes/2+2 );
//        g.drawString( ">>", controls[3].x, mclAltoCabezeraAnoMes/2+2 );

        str = msNombreMeses[moFecha.getMes()-1];
        g.drawString( str, 
            55 - fntm.stringWidth( str ) / 2, 
            mclAltoCabezeraAnoMes/2+2 );
        str = String.valueOf( moFecha.getAno() );
        g.drawString( str, 
            moRectCompleto.width - 32 - fntm.stringWidth( str ) / 2, 
            mclAltoCabezeraAnoMes/2+2);

        //letras dias semana
        g.setColor( moColorLetrasDiasSemana  );
        for ( i = 0; i < 7; i++ ){
          g.drawString( msDiasSemana[i], 
            i * moRectDia.width + 2 + moRectDia.width/2 - fntm.stringWidth( msDiasSemana[i] ) / 2, 
            mclAltoCabezeraAnoMes + (mclAltoCabezeraDiasSemana/2) + 3 );
        }
        //letras dias
        int lDiasMes = moFecha.mlDiasMesActual();
        JDateEdu loFechaAux = new JDateEdu();
        loFechaAux.setDate(moFecha.moDate());
        loFechaAux.setDia(1);
        int lDiaSemana= loFechaAux.getDiaSemanaEspana();
        
        for ( i = lDiaSemana; i < (lDiasMes+lDiaSemana); i++ )
        {
            loFechaAux.setDia(i+1-lDiaSemana);

            //buscamos en la colaccion la fecha actual a pintar
            int lPosi = mlPosicionSelec(loFechaAux);
            if (lPosi!=-1) {
                g.setColor( ((JElementoSelec)moListSelect.get(lPosi)).moBackColor);
                g.fillRect( 
                    ( i % 7 ) * moRectDia.width,
                    ( i / 7 ) * moRectDia.height + mclAltoCabezera,
                    moRectDia.width, moRectDia.height
                );
            }
            str = String.valueOf( loFechaAux.getDia());
            
            //si el dia es el seleccionado
            if (loFechaAux.compareTo(moFecha)==0 ){
                g.setFont( fntDiaActual );
                g.setColor( moColorFondoDiaSeleccionado );
                g.fillOval(
                ( i % 7 ) * moRectDia.width+moRectDia.width/2-fntm.stringWidth( "99" )+2, 
                ( i / 7 ) * moRectDia.height + mclAltoCabezera  + moRectDia.height/2 - fntm.getHeight() +4,
                         25, 14
                        );
                g.setColor( moColorLetrasDiaSeleccionado  );
            }else{
                g.setFont( fnt );
                //si el dia pertenece al conj. un Color si no el de por defecto
                if (lPosi==-1) {
                    g.setColor( moColorLetrasDias  );
                }else {
                    g.setColor( ((JElementoSelec)moListSelect.get(lPosi)).moForeColor);
                }
            }
            
                
            //escribimos el dia
            g.drawString( str, 
                ( i % 7 ) * moRectDia.width - fntm.stringWidth( str )+moRectDia.width/2+6, 
                ( i / 7 ) * moRectDia.height + mclAltoCabezera  + moRectDia.height/2+2);
        }
        if (mbConFoco){
            g.setColor(moColorLineaExtSel);
            g.drawRect(0,0, getSize().width-1, getSize().height-1);
        }
        }
    }
  /**
   * Adds the specified action listener to receive action events
   * from this button.
   * @param listener the action listener
   */
   public void addActionListener(ActionListener listener) {
       actionListener = AWTEventMulticaster.add(actionListener, listener);
       enableEvents(AWTEvent.MOUSE_EVENT_MASK);
   }

   /**
    * Removes the specified action listener so it no longer receives
    * action events from this button.
    * @param listener the action listener
    */
   public void removeActionListener(ActionListener listener) {
       actionListener = AWTEventMulticaster.remove(actionListener, listener);
   }
   
    /**
     * Color letras dias
   * @return color
     */
    public Color getColorLetrasDias(){
        return moColorLetrasDias;
    }
    /**
     * Color letras dias
     * @param poColor color
     */
    public void setColorLetrasDias(Color poColor){
        moColorLetrasDias = poColor;
    }
    /**
     * Devuelve el color de fondo de los días
     * @return color 
     */
    public Color getColorFondoDias(){
        return moColorFondoDias;
    }
    /**
     * color de fondo de los días
     * @param poColor color
     */
    public void setColorFondoDias(Color poColor){
        moColorFondoDias = poColor;
    }
    /**
     * Color letras dias semana
     * @return color
     */
    public Color getColorLetrasDiasSemana(){
        return moColorLetrasDiasSemana;
    }
    /**
     * Color letras dias semana
     * @param poValor color
     */
    public void setColorLetrasDiasSemana(Color poValor){
        moColorLetrasDiasSemana = poValor;
    }
    /**
     * Colo de fondo de los días de la semana
   * @return color
     */
    public Color getColorFondoDiasSemana(){
        return moColorFondoDiasSemana;
    }
    /**
     * Colo de fondo de los días de la semana
     * @param poColor color
     */
    public void setColorFondoDiasSemana(Color poColor){
        moColorFondoDiasSemana = poColor;
    }
    /**
     * Color letras del año y mes
   * @return color
     */
    public Color getColorLetrasAnoMes(){
        return moColorLetrasAnoMes;
    }
    /**
     * Color letras del año y mes
     * @param poColor color
     */
    public void setColorLetrasAnoMes(Color poColor){
        moColorLetrasAnoMes = poColor;
    }
    /**
     * Color de fonfo del año y mes
   * @return color
     */
    public Color getColorFondoAnoMes (){
        return moColorFondoAnoMes ;
    }
    /**
     * Color de fonfo del año y mes
     * @param poColor color
     */
    public void setColorFondoAnoMes (Color poColor){
        moColorFondoAnoMes  = poColor;
    }
    /**
     * Color letras del día seleccionado
   * @return color
     */
    public Color getColorLetrasDiaSeleccionado(){
        return moColorLetrasDiaSeleccionado;
    }
    /**
     * Color letras del dia seleccionado
     * @param poColor color
     */
    public void setColorLetrasDiaSeleccionado(Color poColor){
        moColorLetrasDiaSeleccionado = poColor;
    }
    /**
     * Colo de fondo del día seleccionado
   * @return color
     */
    public Color getColorFondoDiaSeleccionado(){
        return moColorFondoDiaSeleccionado;
    }
    /**
     * Colo de fondo del día seleccionado
     * @param poColor color
     */
    public void setColorFondoDiaSeleccionado(Color poColor){
        moColorFondoDiaSeleccionado = poColor;
    }
    /**
     * Color de la línea del componente cuando esta seleccionado
   * @return color
     */
    public Color getColorLineaExtSel(){
        return moColorLineaExtSel;
    }
    /**
     * Color de la línea del componente cuando esta seleccionado
     * @param poColor color
     */
    public void setColorLineaExtSel(Color poColor){
        moColorLineaExtSel = poColor;
    }
    /**Devuelve el valor actual*/
    public Object getValueTabla() {
        return getFecha();
    }
     /**
      * Establece el valor del text y el valor original, para lo del cambio de color si cambia
      * @param poValor valor
      * @throws Exception error
      */
    public void setValueTabla(Object poValor) throws Exception {
        setFecha(new JDateEdu(poValor.toString()));
    }
    /**
     * Devuelve si ha cambiado
     * @return si ha cambiado
     */
    public boolean getTextoCambiado(){
        return false;
    }
    
}
class JElementoSelec{
    JDateEdu moFecha;
    Color moBackColor;
    Color moForeColor;
    public JElementoSelec(JDateEdu poFecha, Color poBackColor, Color poForeColor){
        moFecha = poFecha;
        moBackColor=poBackColor;
        moForeColor=poForeColor;
    }
    public String toString(){
        return moFecha.toString();
    }
}
