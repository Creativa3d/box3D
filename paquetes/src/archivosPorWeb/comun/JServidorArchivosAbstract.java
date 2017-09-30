/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package archivosPorWeb.comun;

import utiles.IListaElementos;

public abstract class JServidorArchivosAbstract implements IServidorArchivos {
    public abstract void copiarFichero(IServidorArchivos poServidorOrigen , JFichero poFicheroOrigen, JFichero poFicherodestino) throws Exception;
    public abstract void moverFichero(IServidorArchivos poServidorOrigen, JFichero poFicheroOrigen, JFichero poFicherodestino) throws Exception;
    
    public void copiarA(IServidorArchivos poServidorOrigen, JFichero poFicheroOrigen, JFichero poFicherodestino) throws Exception{
        if(poFicheroOrigen.getEsDirectorio()){
            JFichero loFicheroDirDest = new JFichero(poFicherodestino.getPathSinNombre(), poFicheroOrigen.getNombre(), true, 0, null);
            crearCarpeta(loFicheroDirDest);
            IListaElementos loLista = poServidorOrigen.getListaFicheros(poFicheroOrigen);
            for(int i = 0 ; i < loLista.size(); i++){
                JFichero loAux = (JFichero)loLista.get(i);
                JFichero loAuxDest = new JFichero(loFicheroDirDest.getPath(), loAux.getNombre(), loAux.getEsDirectorio(), 0, null);
                copiarA(poServidorOrigen, loAux, loAuxDest);
            }
            
        }else{
            copiarFichero(poServidorOrigen, poFicheroOrigen, poFicherodestino);
        }
    }

    public void mover(IServidorArchivos poServidorOrigen, JFichero poFicheroOrigen, JFichero poFicherodestino) throws Exception {
        if(poFicheroOrigen.getEsDirectorio()){
            JFichero loFicheroDirDest = new JFichero(poFicherodestino.getPathSinNombre(), poFicheroOrigen.getNombre(), true, 0, null);
            crearCarpeta(loFicheroDirDest);
            IListaElementos loLista = poServidorOrigen.getListaFicheros(poFicheroOrigen);
            for(int i = 0 ; i < loLista.size(); i++){
                JFichero loAux = (JFichero)loLista.get(i);
                JFichero loAuxDest = new JFichero(loFicheroDirDest.getPath(), loAux.getNombre(), loAux.getEsDirectorio(), 0, null);
                mover(poServidorOrigen, loAux, loAuxDest);
            }
            borrar(poFicheroOrigen);
        }else{
            moverFichero(poServidorOrigen, poFicheroOrigen, poFicherodestino);
        }
    }


    
}
