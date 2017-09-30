/*
 * IMostrarPantalla.java
 *
 * Created on 15 de abril de 2005, 13:07
 */

package utilesGUIx.formsGenericos;

import utilesGUIx.formsGenericos.edicion.*;

/**Interfaz para mostrar formularios y mensajes en pantalla*/
public interface IMostrarPantalla {
    /**Formulario tipo modal, no sigue la ejecución del programa hasta que se conteste*/
    public static final int mclEdicionDialog = 0;
    /**Formulario tipo no modal, la ejecución del programa continua despues de mostrarse el formulario*/
    public static final int mclEdicionFrame = 1;
    /**Formulario tipo no modal interno, se muestra dentro del entorno, la ejecución del programa continua despues de mostrarse el formulario*/
    public static final int mclEdicionInternal = 2;
    /**Formulario tipo no modal interno, se muestra dentro del entorno, sin botones*/
    public static final int mclEdicionInternalBlanco = 3;

    /**Mensaje de información*/
    public static final int mclMensajeInformacion = 0;
    /**Mensaje de advertencia*/
    public static final int mclMensajeAdvertencia = 1;
    /**Mensaje de error*/
    public static final int mclMensajeError = 2;

    /**Mostramos formulario de edición, con aceptar/cancelar o con navegador, en funcion del parametro instanciad
     * @param poPanel
     * @param poPanelMismo
     * @param plTipoMostrar
     * @param poPanelNave
     * @throws java.lang.Exception*/
    public void mostrarEdicion(IFormEdicion poPanel, IFormEdicionNavegador poPanelNave, Object poPanelMismo, int plTipoMostrar) throws Exception;
    /**Mostramos formulario de edición con aceptar/cancela
     * @param poPanel
     * @param poPanelMismo
     * @throws java.lang.Exception*/
    public void mostrarEdicion(IFormEdicion poPanel, Object poPanelMismo) throws Exception ;
    /**Mostramos formulario de edición con navegado
     * @param poPanel
     * @param poPanelMismo
     * @throws java.lang.Exception*/
    public void mostrarEdicion(IFormEdicionNavegador poPanel, Object poPanelMismo) throws Exception ;
    
    /**Mostramos formulario principal con los parámetros por defecto del siste
     * @param poControlador
     * @throws java.lang.Exception*/
    public void mostrarFormPrinci(IPanelControlador poControlador) throws Exception;
    /**Mostramos formulario principal con ancho/alt
     * @param poControlador
     * @param plAncho
     * @param plAlto
     * @throws java.lang.Exception*/
    public void mostrarFormPrinci(IPanelControlador poControlador,int plAncho, int plAlto) throws Exception;
    /**Mostramos formulario principal con ancho/alto,
     * @param poControlador
     * @param plAncho Ancho
     * @param plAlto Alto
     * @param plTipoForm tipo de formulario principal (JPanelGenerico.mclTipò, JPanelGenerico2.mclTipò, JPanelGenerico3.mclTipò)
     * @param plTipoMostrar mclEdicionDialog, mclEdicionFrame, mclEdicionInternal, mclEdicionInternalBlanco
     * @throws java.lang.Exception
     */
    public void mostrarFormPrinci(IPanelControlador poControlador,int plAncho, int plAlto, int plTipoForm, int plTipoMostrar) throws Exception;
    /**Mostramos un componente en un formulario principal
     * @param poPanel
     * @param plAncho Ancho
     * @param plAlto Alto
     * @param plTipoMostrar mclEdicionDialog, mclEdicionFrame, mclEdicionInternal, mclEdicionInternalBlanco
     * @throws java.lang.Exception
     */
    public void mostrarFormPrinci(Object poPanel,int plAncho, int plAlto, int plTipoMostrar) throws Exception;
    /**Mostramos un componente que se crea a traves del interfaz IMostrarPantallaCrear
     * @param poPanel Interfaz con un método que es llamado despues de instanciarse el formulario principal
     * @throws java.lang.Exception
     */
    public void mostrarForm(IMostrarPantallaCrear poPanel) throws Exception;
    /**Mostramos formulario en funcion de los datos de la clase de parámetr
     * @param poParam
     * @throws java.lang.Exception*/
    public void mostrarForm(JMostrarPantallaParam poParam) throws Exception;
    /**Mostramos pregunta con 2 opcione
     * @param poActividad
     * @param psTitulo
     * @param poSI
     * @param poNO*/
    public void mostrarOpcion(
            Object poActividad
            , String psTitulo
            , Runnable poSI
            , Runnable poNO
            );
    /**Mostramos mensaje de error y al mismo tiempo se guarda el error en los log
     * @param poContex
     * @param e
     * @param poOK*/
    public void mensajeErrorYLog(Object poContex , Throwable e, Runnable poOK);
    /**Mostramos mensaje
     * @param poPadre
     * @param psMensaje
     * @param plMensajeTipo mclMensajeInformacion, mclMensajeAdvertencia, mclMensajeError
     * @param poOK
        */
    public void mensaje(Object poPadre, String psMensaje, int plMensajeTipo, Runnable poOK);
    /**Mostramos mensaje flotante, que desaparece a los pocos segundo
     * @param poPadre
     * @param psMensaje*/
    public void mensajeFlotante(Object  poPadre, String psMensaje);
    /**Cerramos el formulario pasado por parametro o el formulario asociado al component
     * @param poForm*/
    public void cerrarForm(Object poForm);
    /**Añadimos Listener que avisa de cada formulario abierto o cerrad
     * @param poListener*/
    public void addMostrarListener(IMostrarPantallaListener poListener);
    /**Eliminamos Listener que avisa de cada formulario abierto o cerrad
     * @param poListener*/
    public void removeMostrarListener(IMostrarPantallaListener poListener);
    /**Devuelve formulario activ
     * @return o*/
    public Object getActiveInternalFrame();
    /**Devuelve parametros del formulario que corresponde con el identificador pasado por paramet
     * @param psNumeroForm
     * @return */
    public JMostrarPantallaParam getParam(String psNumeroForm);
    /**Establecemos la actividad actual (Solo Android
     * @param poAct)*/
    public void setActividad(Object poAct);
    /**Devuelve  el contexto de la aplicación (Solo Android
     * @return )*/
    public Object getContext();
    /**icono de la aplicación
     * @return  */
    public Object getImagenIcono();
    
    /**icono de la aplicación
     * @param moImagenIcono */
    public void setImagenIcono(Object moImagenIcono);
}
