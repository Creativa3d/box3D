/*
 * IChangePagina.java
 *
 * Created on 27 de noviembre de 2003, 13:31
 */

package utilesGUI.TabControl;

/**
 *Interfaz que debe cumplir el objto que captura el evento cambioPagina
 */
public interface IChangePagina {
    /**
     * Cambio de página
     * @param e objeto evento
     */
    public void cambioPagina(ChangePaginaEvent e);
}
