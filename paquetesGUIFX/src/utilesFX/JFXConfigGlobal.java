/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesFX;

import ListDatos.config.JDevolverTextos;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import utiles.IListaElementos;
import utiles.JCadenas;
import utilesFX.formsGenericos.JMostrarPantalla;
import utilesFX.formsGenericos.JMostrarPantallaInitFX;
import utilesFX.formsGenericos.edicion.JPanelEdicionNavegador;
import utilesGUIx.ColorCZ;
import utilesGUIx.JGUIxConfigGlobalModelo;
import utilesGUIx.formsGenericos.IMostrarPantalla;
import utilesGUIx.formsGenericos.IPanelControlador;
import utilesGUIx.formsGenericos.boton.IBotonRelacionado;
import utilesGUIx.formsGenericos.boton.ICargarIcono;
import utilesGUIx.plugin.IPlugInFactoria;

public class JFXConfigGlobal {
    private static JFXConfigGlobal moInstancia;
    public static final double mcdTiempoTransiciones = 0.2;
    

    private Color moBackColorFocoDefecto = new Color(222/255,223/255,255/255,1);
    private Color moBackColorConDatosDefecto = null;
    private Color moLabelColorObligatorio = Color.RED;
    private Color moForeColorCambio = new Color(1,0,0,1);


    private String msEdicionNavegadorMensajeSoloLectura="El formulario es de solo lectura";
//    private int mlEdicionNavegadorTipoSalida = JPanelEdicionNavegador.mclSalidaCancelar;
//    private int mlEdicionNavegINICIODefect = JPanelEdicionNavegador.mclINICIOEDICION;

    private boolean mbLabelHTMLDefecto = false;

    private JDevolverTextos moToolTipTextLabels;
    private JDevolverTextos moAyudaURLLabels;
    private String msEstilo=this.getClass().getResource(
                    "/utilesFX/aplicacion/JFormPrincipa1.css" ).toExternalForm();


    private boolean mbInicializadoJAVAFX = false;
    private boolean mbControladoresConSalir = false;
    private int mlEdicionNavegadorTipoSalida = JPanelEdicionNavegador.mclSalidaCancelar;
    private int mlEdicionNavegINICIODefect = JPanelEdicionNavegador.mclINICIOEDICION;
    
    private JFXConfigGlobal(){
        if(getMostrarPantalla()==null){
            setMostrarPantalla(new JMostrarPantalla((Pane)null, JMostrarPantalla.mclEdicionFrame, JMostrarPantalla.mclEdicionFrame));
        }
    }
    static {
        moInstancia = new JFXConfigGlobal();
        JGUIxConfigGlobalModelo.getInstancia().setTipoFiltroRapidoDefecto(moInstancia.getTipoFiltroRapidoDefecto());
        if(JGUIxConfigGlobalModelo.getInstancia().getCargarIcono()==null){
            JGUIxConfigGlobalModelo.getInstancia().setCargarIcono(new ICargarIcono() {
                public Object cargarIcono(String psImageCamino, Object poEjecutar, Object poEjecutarExtend) {
                    if(poEjecutar!=null){
                        return getImagenCargada(psImageCamino, poEjecutar.getClass());
                    }else if(poEjecutarExtend!=null){
                        return getImagenCargada(psImageCamino, poEjecutarExtend.getClass()); 
                    } else{
                        return getImagenCargada(psImageCamino); 
                    }

                }
            });
        }
    }
    public static JFXConfigGlobal getInstancia(){
        return moInstancia;
    }

    /**
     * @return el color del los cuadros de introduccion de datos por defecto cuando el control coje el foco
     */
    public Color getBackColorFocoDefecto(){
        return moBackColorFocoDefecto;
    }
    /**
     * el color del los cuadros de introduccion de datos por defecto cuando el control coje el foco
     * @param moBackColorFocoDefecto
     */
    public void setBackColorFocoDefecto(Color moBackColorFocoDefecto) {
        this.moBackColorFocoDefecto = moBackColorFocoDefecto;
    }
    /**
     *
     * @return el color por defecto de los cuadros de datos cuando tienen datos (port ejemplo en JTextFieldCZ hay algun texto escrito)
     */
    public Color getBackColorConDatos(){
        return moBackColorConDatosDefecto;
    }

    /**
     * el color por defecto de los cuadros de datos cuando tienen datos (port ejemplo en JTextFieldCZ hay algun texto escrito)
     * @param moBackColorConDatosDefecto
     */
    public void setBackColorConDatosDefecto(Color moBackColorConDatosDefecto) {
        this.moBackColorConDatosDefecto = moBackColorConDatosDefecto;
    }

    /**
     *
     * @return El color por defecto de los label cuando el cuadro asociado es obligatorio
     */
    public Color getLabelColorObligatorio(){
        return moLabelColorObligatorio;
    }

    /**
     * @param moLabelColorObligatorio the moLabelColorObligatorio to set
     */
    public void setLabelColorObligatorio(Color moLabelColorObligatorio) {
        this.moLabelColorObligatorio = moLabelColorObligatorio;
    }

    /**
     * @return Devuelve el color de la letra cuando se ha modificado el valor original
     */
    public Color getForeColorCambio() {
        return moForeColorCambio;
    }

    /**
     * establece color de la letra cuando se ha modificado el valor original
     * @param moForeColorCambio 
     */
    public void setForeColorCambio(Color moForeColorCambio) {
        this.moForeColorCambio = moForeColorCambio;
    }

    /**
     * @return tipo por defecto de los cuadros de texto
     */
    public int getTipoDefectoCadenaBD() {
        return JGUIxConfigGlobalModelo.getInstancia().getTipoDefectoCadenaBD();
    }

    /**
     * @param mlTipoDefectoCadenaBD tipo por defecto de los cuadros de texto
     */
    public void setTipoDefectoCadenaBD(int mlTipoDefectoCadenaBD) {
        JGUIxConfigGlobalModelo.getInstancia().setTipoDefectoCadenaBD(mlTipoDefectoCadenaBD);
    }

    /**
     * Tipo de filtro rapido por defecto del JPanelGenerico y JPanelGenerico2
     * @return 
     */
    public String getTipoFiltroRapidoDefecto() {
        return JGUIxConfigGlobalModelo.getInstancia().getTipoFiltroRapidoDefecto();
    }

    /**
     * @see JGUIxConfigGlobal.getTipoFiltroRapidoDefecto
     * @param msTipoFiltroRapidoDefecto the msTipoFiltroRapidoDefecto to set
     */
    public void setTipoFiltroRapidoDefecto(String msTipoFiltroRapidoDefecto) {
        JGUIxConfigGlobalModelo.getInstancia().setTipoFiltroRapidoDefecto(msTipoFiltroRapidoDefecto);
    }

    /**
     * Fichero de longitudes/ordenes de JPanelGenerico y JPanelGenerico2
     * @return the msFicheroLongTablas
     */
    public String getFicheroLongTablas() {
        return JGUIxConfigGlobalModelo.getInstancia().getFicheroLongTablas();
    }

    /**
     * @see JGUIxConfigGlobal.getFicheroLongTablas
     * @param msFicheroLongTablas the msFicheroLongTablas to set
     */
    public void setFicheroLongTablas(String msFicheroLongTablas) {
        JGUIxConfigGlobalModelo.getInstancia().setFicheroLongTablas(msFicheroLongTablas);
    }

    /**
     * Mensaje de solo lectura, para el form. de edicion JPanelEdicionNavegador
     * @return the msMensajeSoloLectura
     */
    public String getEdicionNavegadorMensajeSoloLectura() {
        return msEdicionNavegadorMensajeSoloLectura;
    }

    /**
     * @see JGUIxConfigGlobal.getEdicionNavegadorMensajeSoloLectura
     * @param msMensajeSoloLectura the msMensajeSoloLectura to set
     */
    public void setEdicionNavegadorMensajeSoloLectura(String psMensajeSoloLectura) {
        msEdicionNavegadorMensajeSoloLectura = psMensajeSoloLectura;
    }

    /**
     * Si el JPanelEdicionNavegador sale y esta en edicion que tipo de pregunta hace
     * @see JPanelEdicionNavegadorJPanelEdicionNavegador.mclSalidaCancelarJPanelEdicionNavegador.mclSalidaGuardar
   JPanelEdicionNavegador.mclSalidaNada
     * @return the mlEdicionNavegadorTipoSalida
     */
    public int getEdicionNavegadorTipoSalida() {
        return mlEdicionNavegadorTipoSalida;
    }

    /**
     * @param mlEdicionNavegadorTipoSalida the mlEdicionNavegadorTipoSalida to set
     */
    public void setEdicionNavegadorTipoSalida(int plEdicionNavegadorTipoSalida) {
        mlEdicionNavegadorTipoSalida = plEdicionNavegadorTipoSalida;
    }


    /**
     * @return the mbLabelHTMLDefecto
     */
    public boolean isLabelHTMLDefecto() {
        return mbLabelHTMLDefecto;
    }

    /**
     * @param mbLabelHTMLDefecto the mbLabelHTMLDefecto to set
     */
    public void setLabelHTMLDefecto(boolean mbLabelHTMLDefecto) {
        this.mbLabelHTMLDefecto = mbLabelHTMLDefecto;
    }

    /**
     * @return the moToolTipTextLabels
     */
    public JDevolverTextos getToolTipTextLabels() {
        return moToolTipTextLabels;
    }

    /**
     * @param moToolTipTextLabels the moToolTipTextLabels to set
     */
    public void setToolTipTextLabels(JDevolverTextos moToolTipTextLabels) {
        this.moToolTipTextLabels = moToolTipTextLabels;
    }

    /**
     * @return the moAyudaURLLabels
     */
    public JDevolverTextos getAyudaURLLabels() {
        return moAyudaURLLabels;
    }

    /**
     * @param moAyudaURLLabels the moAyudaURLLabels to set
     */
    public void setAyudaURLLabels(JDevolverTextos moAyudaURLLabels) {
        this.moAyudaURLLabels = moAyudaURLLabels;
    }

    /**
     * @return the moCargarIcono
     */
    public ICargarIcono getCargarIcono() {
        return JGUIxConfigGlobalModelo.getInstancia().getCargarIcono();
    }

    /**
     * @param moCargarIcono the moCargarIcono to set
     */
    public void setCargarIcono(ICargarIcono moCargarIcono) {
        JGUIxConfigGlobalModelo.getInstancia().setCargarIcono(moCargarIcono);
    }
    
    public void setPlugInFactoria(IPlugInFactoria poPluginFactoria){
        JGUIxConfigGlobalModelo.getInstancia().setPlugInFactoria(poPluginFactoria);
    }
    public IPlugInFactoria getPlugInFactoria(){
        return JGUIxConfigGlobalModelo.getInstancia().getPlugInFactoria();
    }
    /**
     * @return Devuelve el mostrar pantalla por defecto
     */
    public synchronized IMostrarPantalla getMostrarPantalla(){
        return JGUIxConfigGlobalModelo.getInstancia().getMostrarPantalla();
    }
    /**
     * Establece el mostrar pantalla por defecto
     * @param poMostrar
     */
    public synchronized void setMostrarPantalla(IMostrarPantalla poMostrar){
        JGUIxConfigGlobalModelo.getInstancia().setMostrarPantalla(poMostrar);
    }
    public String getEstilo(){
        return msEstilo;
    }
    public void setEstilo(String ps){
        msEstilo = ps;
    }
    
//
    /**
     * @return the mlEdicionINICIODefect
     */
    public int getEdicionNaveINICIODefect() {
        return mlEdicionNavegINICIODefect;
    }

    /**
     * @param mlEdicionINICIODefect the mlEdicionINICIODefect to set
     */
    public void setEdicionNavegINICIODefect(int mlEdicionINICIODefect) {
        this.mlEdicionNavegINICIODefect = mlEdicionINICIODefect;
    }

    /**
     * @return the mbInicializadoJAVAFX
     */
    public boolean isInicializadoJAVAFX() {
        return mbInicializadoJAVAFX;
    }

    /**
     * @param mbInicializadoJAVAFX the mbInicializadoJAVAFX to set
     */
    public void setInicializadoJAVAFX(boolean mbInicializadoJAVAFX) {
        this.mbInicializadoJAVAFX = mbInicializadoJAVAFX;
    }
    
    public void inicializarFX(){
        JMostrarPantallaInitFX.initJavaFX();
    }

    public static void saveToFile(Image image, String psFormatp, File outputFile) throws IOException {
        java.awt.image.BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
        javax.imageio.ImageIO.write(bImage, psFormatp, outputFile);
    }    
    public static Color toColor(String psHexColor){
        return Color.web(psHexColor);
    }
    public static Color toColor(ColorCZ poColor){
        return Color.web(toRGBCode(poColor));
    }
    public static ColorCZ toColorCZ(String psHexColor){
        return new ColorCZ(Integer.parseInt(psHexColor, 16));
    }
    public static String toRGBCode(ColorCZ poColor){
        return  String.format("#%06X", (0xFFFFFF & poColor.getColor()));        
    }
    public static String toRGBCode( Color color ) {
        return String.format( "#%02X%02X%02X",
            (int)( color.getRed() * 255 ),
            (int)( color.getGreen() * 255 ),
            (int)( color.getBlue() * 255 ) );
    }    
    public static int toRGB(Color poColor){
            return 
                    (((int)(poColor.getOpacity()*255)) << 24) 
                    | (((int)(poColor.getRed()*255)) << 16) 
                    | (((int)(poColor.getGreen()*255)) << 8) 
                    | ((int)(poColor.getBlue())*255);
    }
    public static Image getImagenCargada(String psImagen){
        return getImagenCargada(psImagen, JFXConfigGlobal.class);
    }
    public static Image getImagenCargada(String psImagen, Class poClase){
        Image loResult = null;
        if(!JCadenas.isVacio(psImagen)){
            //classpath
            try{
                loResult = new Image(poClase.getResourceAsStream(psImagen));
            }catch(Throwable e){}
            //file
            if(loResult==null){
                File loFile = new File(psImagen);
                if(loFile.exists()){
                    try {
                        loResult = new Image(new FileInputStream(loFile));
                    } catch (Exception ex) {}
                }
            }
            //url
            if(loResult==null){
                loResult = new Image(psImagen);
            }
        }
        return loResult;
    }

    /**
     * @return the mbControladoresConSalir
     */
    public boolean isControladoresConSalir() {
        return mbControladoresConSalir;
    }

    /**
     * @param mbControladoresConSalir the mbControladoresConSalir to set
     */
    public void setControladoresConSalir(boolean mbControladoresConSalir) {
        this.mbControladoresConSalir = mbControladoresConSalir;
    }
    
    

    /**
     * @return the moTextosForms
     */
    public JDevolverTextos getTextosForms() {
        return JGUIxConfigGlobalModelo.getInstancia().getTextosForms();
    }

    /**
     * @param moTextosForms the moTextosForms to set
     */
    public void setTextosForms(JDevolverTextos moTextosForms) {
        JGUIxConfigGlobalModelo.getInstancia().setTextosForms(moTextosForms);
    }
    
    public void setTextoBoton(Button poBoton){
        setTextoBoton(poBoton, "");
    }
    public void setTextoBoton(Button poBoton, String psPadre){
        String lsActionCommand = poBoton.getId();
        String lsTExtO = poBoton.getText();
        if(!JCadenas.isVacio(lsActionCommand)){
            String lsTexto = "";
            if(JCadenas.isVacio(psPadre)){
                lsTexto=(
                    JGUIxConfigGlobalModelo.getInstancia().getTextosForms()
                        .getTextoVacio(lsActionCommand));
            }else{
                lsTexto=(
                    JGUIxConfigGlobalModelo.getInstancia().getTextosForms()
                        .getCaption(psPadre, lsActionCommand));
            }
            if(lsTexto.equals(lsActionCommand)){
                lsTexto=lsTExtO;
            }
            if(
                    !
                    (JCadenas.isVacio(lsTExtO) 
                    && (JCadenas.isVacio(lsTexto) || lsTexto.equalsIgnoreCase(lsActionCommand) ) 
                    ) && !JCadenas.isVacio(lsTexto)
                    ){
                poBoton.setText(lsTexto);
            }
            poBoton.setId(lsActionCommand);
        }
        if(poBoton.getTooltip()!=null && !JCadenas.isVacio(poBoton.getTooltip().getText())){           
            String lsTexto="";
            if(JCadenas.isVacio(psPadre)){
                lsTexto=(
                    JGUIxConfigGlobalModelo.getInstancia().getTextosForms()
                        .getTextoVacio(poBoton.getTooltip().getText()));
            }else{
                lsTexto=(
                    JGUIxConfigGlobalModelo.getInstancia().getTextosForms()
                        .getCaption(psPadre, poBoton.getTooltip().getText()));
            }
            poBoton.getTooltip().setText(lsTexto);
        }
    }
    
    public void setTextoTodosBotones(final IPanelControlador poControlador) {
        if(poControlador.getParametros()!=null 
                && poControlador.getParametros().getBotonesGenerales()!=null
                && poControlador.getParametros().getBotonesGenerales().getListaBotones()!=null){
            IListaElementos loBotones = poControlador.getParametros().getBotonesGenerales().getListaBotones();
            for(int i = 0 ; i < loBotones.size(); i++){
                IBotonRelacionado loB = (IBotonRelacionado) loBotones.get(i);
                loB.setCaption(
                        JGUIxConfigGlobalModelo.getInstancia().getTextosForms().getCaption(
                                poControlador.getClass().getSimpleName()
                                , loB.getNombre()));
            }
        }
    }
    public void setTextoTodosComponent(final Pane poComponente, MenuBar jMenuBar1) {
        for(int i = 0 ; i < jMenuBar1.getMenus().size(); i++ ){
            Menu loMenuAux = jMenuBar1.getMenus().get(i);
            if(loMenuAux!=null){
                setTextoTodosComponent(poComponente, loMenuAux);
            }
        }
    }
    public void setTextoTodosComponent(final Pane poComponente, Menu jMenuOrigen) {
        String lsActionCommand = jMenuOrigen.getId();
        jMenuOrigen.setText(JGUIxConfigGlobalModelo.getInstancia().getTextosForms()
                        .getCaption(poComponente.getClass().getSimpleName(), lsActionCommand));
        jMenuOrigen.setId(lsActionCommand);
        for(int i = 0 ; i < jMenuOrigen.getItems().size(); i++ ){
            MenuItem loAux = jMenuOrigen.getItems().get(i);
            if(loAux!=null && loAux instanceof MenuItem){
                MenuItem loItem = ((MenuItem) loAux);
                lsActionCommand = loItem.getId();
                loItem.setText(
                    JGUIxConfigGlobalModelo.getInstancia().getTextosForms()
                        .getCaption(poComponente.getClass().getSimpleName(), lsActionCommand));
                loItem.setId(lsActionCommand);
            } else if(loAux!=null && loAux instanceof Menu){
                setTextoTodosComponent(poComponente, (Menu) loAux);
            }
        }
    }
    
    public void setTextoTodosComponent(final Pane poComponente) {
        setTextoTodosComponent(poComponente, poComponente.getClass().getSimpleName());
    }
    
    public void setTextoTodosComponent(final Pane poComponente, String psPadre) {
        for(int i = 0; i < poComponente.getChildren().size(); i++){
            if((poComponente != poComponente.getChildren().get(i))){
                setTextoComponent(poComponente.getChildren().get(i), psPadre);
            }
        }
    }
    public void setTextoComponent(final Node poComponente, String psPadre) {
        //establecemos el bloque al componente concreto
        if (((Button.class.isAssignableFrom(poComponente.getClass()))
            || Label.class.isAssignableFrom(poComponente.getClass())
            || CheckBox.class.isAssignableFrom(poComponente.getClass())
                )
                   ) {

            if(Button.class.isAssignableFrom(poComponente.getClass())){
                Button loButon = (Button) poComponente;
                if(!JCadenas.isVacio(loButon.getId())){
                    setTextoBoton(loButon, psPadre);
                    
                }
            }else if(Label.class.isAssignableFrom(poComponente.getClass())){
                Label lo = (Label) poComponente;
                if(!JCadenas.isVacio(lo.getText().trim())){
                    lo.setText(JGUIxConfigGlobalModelo.getInstancia().getTextosForms().getCaption(psPadre, lo.getText()));
//                    lo.setToolTipText(JGUIxConfigGlobalModelo.getInstancia().getTextosForms().getCaption(psPadre, lo.getToolTipText()));
                }
            }else if(CheckBox.class.isAssignableFrom(poComponente.getClass())){
                CheckBox lo = (CheckBox) poComponente;
                if(!JCadenas.isVacio(lo.getText().trim())){
                    lo.setText(JGUIxConfigGlobalModelo.getInstancia().getTextosForms().getCaption(psPadre, lo.getText()));
//                    lo.setToolTipText(JGUIxConfigGlobalModelo.getInstancia().getTextosForms().getCaption(psPadre, lo.getToolTipText()));
                }
            }                
        } else if (((poComponente instanceof Pane))){
            if(Pane.class.isAssignableFrom(poComponente.getClass())){
//                Pane loPanel  = (Pane) poComponente;
//                if(loPanel.getBorder() !=null && TitledBorder.class.isAssignableFrom(loPanel.getBorder().getClass())){
//                    TitledBorder loT = (TitledBorder) loPanel.getBorder();
//                    if(!JCadenas.isVacio(loT.getTitle().trim())){
//                        loT.setTitle(JGUIxConfigGlobalModelo.getInstancia().getTextosForms().getCaption(psPadre, loT.getTitle()));
//
//                    }
//                }

            }
            setTextoTodosComponent((Pane)poComponente, psPadre);
        }
    }    
            

            
}
