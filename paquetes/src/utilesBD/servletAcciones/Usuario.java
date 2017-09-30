package utilesBD.servletAcciones;

import java.io.Serializable;

/** objeto usuario*/
public class Usuario implements Serializable {
    private static final long serialVersionUID = 33333355L;
    
    /**nombre usuario*/
    public String msNombre;
    /**nombre codigo*/
    public int mlCodigo;
    /**nombre permisos*/
    public int mlPermisos;
    
    /**
     * Creates a new instance of Usuario
     * @param psNombre nombre
     * @param plCodigo codigo
     * @param plPermisos permisos
     */
    public Usuario(String psNombre,int plCodigo,int plPermisos) {
        msNombre = psNombre;
        mlCodigo = plCodigo;
        mlPermisos = plPermisos;
    }
    
}
