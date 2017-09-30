/*
 * JFilaCrearDefecto.java
 *
 * Created on 20 de marzo de 2007, 9:16
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package ListDatos;

import utiles.JDepuracion;

public class JFilaCrearDefecto implements IFilaCrear {
    private static final long serialVersionUID = 3333333L;

    private final Class moClaseFilaCrear;
    private static IFilaCrear moFilaCrear=new JFilaCrearDefecto();
    
    
    /** Creates a new instance of JFilaCrearDefecto */
    public JFilaCrearDefecto() {
        this(ListDatos.JFilaDatosDefecto.class);
    }

    public JFilaCrearDefecto(final Class poClase) {
        super();
        moClaseFilaCrear=poClase;
    }
    public JFilaCrearDefecto(final String psClase) {
        super();
        try {
            moClaseFilaCrear = Class.forName(psClase);
        } catch (Exception ex) {
            JDepuracion.anadirTexto(getClass().getName(), ex);
            throw new InternalError(ex.toString());
        }
    }

    public IFilaDatos getFilaDatos(final String psTabla) {
        IFilaDatos loResult;
        try {
            loResult = (IFilaDatos)moClaseFilaCrear.newInstance();
        } catch (Exception ex) {
            JDepuracion.anadirTexto(getClass().getName(), ex);
            throw new InternalError(ex.toString());
        }
        return loResult;
    }
    
    public static void setFilaCrear(final IFilaCrear poCrear){
        moFilaCrear = poCrear;
    }
    public static IFilaCrear getFilaCrear(){
        return moFilaCrear;
    }
    
}
