/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesGUIx;

import ListDatos.config.JDevolverTextos;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import utiles.IListaElementos;
import utiles.JCadenas;
import utilesGUIx.formsGenericos.IMostrarPantalla;
import utilesGUIx.formsGenericos.IPanelControlador;
import utilesGUIx.formsGenericos.JMostrarPantalla;
import utilesGUIx.formsGenericos.boton.IBotonRelacionado;
import utilesGUIx.formsGenericos.boton.ICargarIcono;
import utilesGUIx.formsGenericos.edicion.JPanelEdicionNavegador;
import utilesGUIx.plugin.IPlugInFactoria;

public class JGUIxConfigGlobal {
    private static JGUIxConfigGlobal moInstancia;

    
    private Color moBackColorFocoDefecto = new Color(222,223,255);
    private Color moBackColorConDatosDefecto = null;
    private Color moLabelColorObligatorio = Color.red;;
    private Color moForeColorCambio = new Color(255,0,0);

    private String msEdicionNavegadorMensajeSoloLectura="El formulario es de solo lectura";
    private int mlEdicionNavegadorTipoSalida = JPanelEdicionNavegador.mclSalidaCancelar;
    private int mlEdicionNavegINICIODefect = JPanelEdicionNavegador.mclINICIOEDICION;

    private boolean mbLabelHTMLDefecto = false;

    private JDevolverTextos moToolTipTextLabels;
    private JDevolverTextos moAyudaURLLabels;

    
    private HashMap moUIPropiedades=new HashMap();
    
    private int mlTableAltoFilas;
    
    private JGUIxConfigGlobal(){
        if(getMostrarPantalla()==null){
            setMostrarPantalla(new JMostrarPantalla(null, JMostrarPantalla.mclEdicionFrame, JMostrarPantalla.mclEdicionFrame));
        }
    }
    static {
        moInstancia = new JGUIxConfigGlobal();
        JGUIxConfigGlobalModelo.getInstancia().setTipoFiltroRapidoDefecto(moInstancia.getTipoFiltroRapidoDefecto());
        if(JGUIxConfigGlobalModelo.getInstancia().getCargarIcono()==null){
            JGUIxConfigGlobalModelo.getInstancia().setCargarIcono(new ICargarIcono() {
                public Object cargarIcono(String psImageCamino, Object poEjecutar, Object poEjecutarExtend) {
                    return JGUIxUtil.getIconoCargado(psImageCamino, poEjecutar, poEjecutarExtend);
                }
            });
        }
        moInstancia.setFormato(moInstancia.getFormato());
    }
    public static JGUIxConfigGlobal getInstancia(){
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
     */
    public synchronized void setMostrarPantalla(IMostrarPantalla poMostrar){
        JGUIxConfigGlobalModelo.getInstancia().setMostrarPantalla(poMostrar);
    }

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
    
    public void setTextoBoton(JButton poBoton){
        setTextoBoton(poBoton, "");
    }
    public void setTextoBoton(JButton poBoton, String psPadre){
        String lsActionCommand = poBoton.getActionCommand();
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
            if(
                    !
                    (JCadenas.isVacio(lsTExtO) 
                    && (JCadenas.isVacio(lsTexto) || lsTexto.equalsIgnoreCase(lsActionCommand) ) 
                    ) && !JCadenas.isVacio(lsTexto)
                    ){
                poBoton.setText(lsTexto);
            }
            poBoton.setActionCommand(lsActionCommand);
        }
        if(!JCadenas.isVacio(poBoton.getToolTipText())){           
            String lsTexto="";
            if(JCadenas.isVacio(psPadre)){
                lsTexto=(
                    JGUIxConfigGlobalModelo.getInstancia().getTextosForms()
                        .getTextoVacio(poBoton.getToolTipText()));
            }else{
                lsTexto=(
                    JGUIxConfigGlobalModelo.getInstancia().getTextosForms()
                        .getCaption(psPadre, poBoton.getToolTipText()));
            }
            poBoton.setToolTipText(lsTexto);
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
    public void setTextoTodosComponent(final java.awt.Container poComponente, JMenuBar jMenuBar1) {
        for(int i = 0 ; i < jMenuBar1.getMenuCount(); i++ ){
            JMenu loMenuAux = jMenuBar1.getMenu(i);
            if(loMenuAux!=null){
                setTextoTodosComponent(poComponente, loMenuAux);
            }
        }
    }
    public void setTextoTodosComponent(final java.awt.Container poComponente, JMenu jMenuOrigen) {
        String lsActionCommand = jMenuOrigen.getActionCommand();
        jMenuOrigen.setText(JGUIxConfigGlobalModelo.getInstancia().getTextosForms()
                        .getCaption(poComponente.getClass().getSimpleName(), lsActionCommand));
        jMenuOrigen.setActionCommand(lsActionCommand);
        for(int i = 0 ; i < jMenuOrigen.getMenuComponentCount(); i++ ){
            Component loAux = jMenuOrigen.getMenuComponent(i);
            if(loAux!=null && loAux instanceof JMenuItem){
                JMenuItem loItem = ((JMenuItem) loAux);
                lsActionCommand = loItem.getActionCommand();
                loItem.setText(
                    JGUIxConfigGlobalModelo.getInstancia().getTextosForms()
                        .getCaption(poComponente.getClass().getSimpleName(), lsActionCommand));
                loItem.setActionCommand(lsActionCommand);
                loItem.setToolTipText(JGUIxConfigGlobalModelo.getInstancia().getTextosForms()
                        .getCaption(poComponente.getClass().getSimpleName(), loItem.getToolTipText()));
            } else if(loAux!=null && loAux instanceof JMenu){
                setTextoTodosComponent(poComponente, (JMenu) loAux);
            }
        }
    }
    
    public void setTextoTodosComponent(final java.awt.Container poComponente) {
        setTextoTodosComponent(poComponente, poComponente.getClass().getSimpleName());
    }
    
    public void setTextoTodosComponent(final java.awt.Container poComponente, String psPadre) {
        for(int i = 0; i < poComponente.getComponentCount(); i++){
            if((poComponente != poComponente.getComponent(i))){
                setTextoComponent(poComponente.getComponent(i), psPadre);
            }
        }
    }
    public void setTextoComponent(final java.awt.Component poComponente, String psPadre) {
        //establecemos el bloque al componente concreto
        if (((JButton.class.isAssignableFrom(poComponente.getClass()))
            || JLabel.class.isAssignableFrom(poComponente.getClass())
            || JCheckBox.class.isAssignableFrom(poComponente.getClass())
                )
                   ) {

            if(JButton.class.isAssignableFrom(poComponente.getClass())){
                JButton loButon = (JButton) poComponente;
                if(!JCadenas.isVacio(loButon.getActionCommand())){
                    setTextoBoton(loButon, psPadre);
                    
                }
            }else if(JLabel.class.isAssignableFrom(poComponente.getClass())){
                JLabel lo = (JLabel) poComponente;
                if(!JCadenas.isVacio(lo.getText().trim())){
                    lo.setText(JGUIxConfigGlobalModelo.getInstancia().getTextosForms().getCaption(psPadre, lo.getText()));
                    lo.setToolTipText(JGUIxConfigGlobalModelo.getInstancia().getTextosForms().getCaption(psPadre, lo.getToolTipText()));
                }
            }else if(JCheckBox.class.isAssignableFrom(poComponente.getClass())){
                JCheckBox lo = (JCheckBox) poComponente;
                if(!JCadenas.isVacio(lo.getText().trim())){
                    lo.setText(JGUIxConfigGlobalModelo.getInstancia().getTextosForms().getCaption(psPadre, lo.getText()));
                    lo.setToolTipText(JGUIxConfigGlobalModelo.getInstancia().getTextosForms().getCaption(psPadre, lo.getToolTipText()));
                }
            }                
        } else if (((poComponente instanceof java.awt.Container))
                ){
            if(JPanel.class.isAssignableFrom(poComponente.getClass())){
                JPanel loPanel  = (JPanel) poComponente;
                if(loPanel.getBorder() !=null && TitledBorder.class.isAssignableFrom(loPanel.getBorder().getClass())){
                    TitledBorder loT = (TitledBorder) loPanel.getBorder();
                    if(!JCadenas.isVacio(loT.getTitle().trim())){
                        loT.setTitle(JGUIxConfigGlobalModelo.getInstancia().getTextosForms().getCaption(psPadre, loT.getTitle()));

                    }
                }

            }
            setTextoTodosComponent((java.awt.Container)poComponente, psPadre);
        }
    }
    
    public int getFormato(){
        return JGUIxConfigGlobalModelo.getInstancia().getFormato();
    }

    public void setFormato(int plFormato){
        JGUIxConfigGlobalModelo.getInstancia().setFormato(plFormato);
        switch(JGUIxConfigGlobalModelo.getInstancia().getFormato()){
            case JGUIxConfigGlobalModelo.mclFormatoNormal:
                mlTableAltoFilas=20;
                restaurarPropiedad("ScrollBar.width", null);
                restaurarPropiedad("FontUIResource", null);
                restaurarPropiedad("TextArea.font", null);
                restaurarPropiedad("TextField.font", null);
                restaurarPropiedad("Table.font", null);
                restaurarPropiedad("Table.rowHeight", mlTableAltoFilas);//no va, lo he hecho en JTableRenderConColor o en JTableCZ
                break;
            case JGUIxConfigGlobalModelo.mclFormatoPantallasGrandes:
            case JGUIxConfigGlobalModelo.mclFormatoTablet:
                mlTableAltoFilas=25;
                setPropiedad("ScrollBar.width", 30);
                setPropiedad("FontUIResource", new javax.swing.plaf.FontUIResource("Dialog",Font.BOLD,14));
                setPropiedad("TextArea.font", new Font("Dialog",Font.PLAIN,14));
                setPropiedad("TextField.font", new Font("Dialog",Font.PLAIN,14));
                setPropiedad("Table.font", new Font("Dialog",Font.PLAIN,14));
                setPropiedad("Table.rowHeight", mlTableAltoFilas);//no va, lo he hecho en JTableRenderConColor o en JTableCZ
                break;
        }
    }
    private void setPropiedad(String ps, Object poValor){
        moUIPropiedades.put(ps, poValor);
        if(ps.equalsIgnoreCase("FontUIResource")){
            setUIFont((FontUIResource) poValor);
        }else{
            UIManager.getLookAndFeelDefaults().put(ps, poValor);
            UIManager.put(ps, poValor);
        }
    }
    private void restaurarPropiedad(String ps, Object poValorDefecto){
        Object loValor = moUIPropiedades.get(ps);
        if(loValor == null){
            if(poValorDefecto!=null){
                UIManager.put(ps, poValorDefecto);
                UIManager.getLookAndFeelDefaults().put(ps, poValorDefecto);
            }
        }else{
            if(ps.equalsIgnoreCase("FontUIResource")){
                setUIFont((FontUIResource) loValor);
            }else{
                UIManager.put(ps, loValor);
                UIManager.getLookAndFeelDefaults().put(ps, loValor);
            }
        }

    }
    private static javax.swing.plaf.FontUIResource getUIFont (){
        java.util.Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
          Object key = keys.nextElement();
          Object value = UIManager.get (key);
          if (value != null && value instanceof javax.swing.plaf.FontUIResource)
            return (FontUIResource) value;
          }
        return null;
    } 
    private static void setUIFont (javax.swing.plaf.FontUIResource f){
        java.util.Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
          Object key = keys.nextElement();
          Object value = UIManager.get (key);
          if (value != null && value instanceof javax.swing.plaf.FontUIResource)
            UIManager.put (key, f);
          }
    } 


    public int getTableAltoFilas() {
        return mlTableAltoFilas;
    }
    public void setTableAltoFilas(int aMlTableAltoFilas) {
        mlTableAltoFilas = aMlTableAltoFilas;
    }


}
