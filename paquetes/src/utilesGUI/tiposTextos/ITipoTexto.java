/*
 * ITipoTexto.java
 *
 * Created on 20 de septiembre de 2004, 8:23
 */

package utilesGUI.tiposTextos;

import java.io.Serializable;

public interface ITipoTexto extends Serializable {
    /**Texto de error cuando el tipo no es correcto*/
    public String getTextoError(final String psTexto);
    /**Analiza si el tipo es correcto*/
    public boolean isTipoCorrecto(final String psValor);
    /*Captura el ecvento keytyped para poder cambiar el keycode de la tecla pulsada (por ejemplo siempre a mayusculas)**/
    public void getTecla(String psTexto, KeyEventCZ poEvent);
    /**Se llama cuando el control pierde el foco, se suele usar para poner el texto modificado (q es distinto al formateado)por ejem: DNI: usuario pone: 22999102 el texto modificado sera 22999102E*/
    public void lostFocus(String psTexto);
    /**Devuelve el texto normal(modificado, por ejem: DNI: usuario pone: 22999102 el texto modificado sera 22999102E) */
    public String getText();
    /**Devuelve el texto formateado, por ejem: numeros con coma en vez de con punto*/
    public String getTextFormateado();
    /**Para restaurar el texto antes del cambio*/
    public void restaurarTexto();
    /**Devuelve el tipo, se usa para los tipos basicos*/
    public int getTipo();
    /**Establece el texto*/
    public void setText(String psText);
    /**Indica q si el tipo no es correcto no se puede pasar de campo*/
    public boolean isTipoCorrectoObligatorio();
    /**establece q si el tipo no es correcto no se puede pasar de campo*/
    public void setTipoCorrectoObligatorio(boolean pbValor);
    
    /**
     * Establecemos el texto origienal antes del cambio
     * activamos el color de la letra si cambia con respectoa al original
     * si el valor original es null, se anula el efecto
     * si el valor original es <> null se activa el efecto
     */
    public void setTextOriginal(String psText);
    /*** Devuelve el texto original antes del cambio*/
    public String getTextOriginal();
    /**Devuelve si el texto ha cambiado con respecto al original */
    public boolean isTextoCambiado();
    
    
}
