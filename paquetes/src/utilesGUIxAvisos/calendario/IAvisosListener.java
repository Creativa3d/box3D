/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesGUIxAvisos.calendario;

import utilesGUIxAvisos.tablasExtend.JTEEGUIXAVISOS;
import utilesGUIxAvisos.tablasExtend.JTEEGUIXEVENTOS;

/**
 *
 * @author eduardo
 */
public interface IAvisosListener {
    /**
     * Se llama a este método con el evento recientemente ejecutado y todavía no notificado
     */
    public void eventos(JTEEGUIXEVENTOS poEventos) throws Throwable;
    /**
     * Se llama a este método con el aviso recientemente ejecutado y todavía no notificado
     */
    public void avisos(JTEEGUIXAVISOS poAviso) throws Throwable;
    /**
     * Se llama a este método desde el formulario de edición, botón ver datos relacionados
     */
    public void mostrar(JTEEGUIXEVENTOS poEvento) throws Throwable;
}
