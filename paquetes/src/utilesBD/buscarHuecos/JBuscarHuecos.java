/*
 * JBuscarHuecos.java
 *
 * Created on 28 de octubre de 2004, 18:47
 */

package utilesBD.buscarHuecos;

import ListDatos.*;

public class JBuscarHuecos {
    
    private JBuscarHuecos(){
    }
    
    public static Variant siguienteHuecoLibre(IListaIterator poLista) {
        IListaIterator loLista;
        Variant key1,key2,solucion;
        
        loLista = poLista;
        
        solucion = null;
        key1 = loLista.ultimoMasUno();
        if (!loLista.esHuecoLibre(key1)) {
            //Busco un hueco
            key1 = loLista.primero();
            while(!loLista.haTerminado()) {
                key1 = loLista.siguiente();
                key2 = loLista.siguienteSecuencia();
                if (!loLista.compara(key1,key2)) {
                    if(loLista.esHuecoLibre(key2)) {
                        solucion = key2;
                    }
                }else {
                    solucion = new Variant(-1);
                }
            }
        }
        else {
            solucion = key1;
        }
        return(solucion);
    }
}
