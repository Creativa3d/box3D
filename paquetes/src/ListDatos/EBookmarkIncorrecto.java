/*
 * EBookmarkIncorrecto.java
 *
 * Created on 21 de octubre de 2003, 3:01
 */

package ListDatos;
/**
 * BookMark no valido, pq se ha borrado el registro en concreto
 */
public final class EBookmarkIncorrecto extends Exception {
    
    public EBookmarkIncorrecto(Exception ex) {
        super(ex.toString());

    }
    /** Crea una instancia de  EBookmarkIncorrecto */
    public EBookmarkIncorrecto() {
        super("Bookmark no existe");
    }
    
}
