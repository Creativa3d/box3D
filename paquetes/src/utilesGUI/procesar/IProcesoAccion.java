/*
 * IProcesoAccion.java
 *
 * Created on 17 de junio de 2008, 11:28
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utilesGUI.procesar;

public interface IProcesoAccion {
    
    /**Paramatros varios*/
    public JProcesoAccionParam getParametros();
    
    /**Titulo de la ventana del procesado*/
    public String getTitulo();
    
    /**Numero total de registros a procesar*/
    public int getNumeroRegistros();

    /**Metodo principal de procesamiento*/
    public void procesar() throws Throwable;
    
    /**Numero del registro actual*/
    public int getNumeroRegistro();

    /**Titulo del registro actual*/
    public String getTituloRegistroActual();
    
    /**pregunta si ha llegado a su fin*/
    public boolean isFin();

    /**Metodo q se ejecuta al pulsar cancelar desde la pantalla*/
    public void setCancelado(boolean pbCancelado);
    
    /**Para mostrar el mensaje*/
    public void mostrarMensaje(String psMensaje);
    
    /**Para mostrar el Error*/
    public void mostrarError(Throwable e);

    
    /**Ultimo q se ejecuta, sirve para finalizar recursos del proceso
     *Se llama aunque haya habido errores
     */
    public void finalizar();
}
