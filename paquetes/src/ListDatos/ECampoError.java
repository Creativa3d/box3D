/*
 * CampoError.java
 *
 * Created on 13 de febrero de 2004, 13:59
 */

package ListDatos;

import ListDatos.estructuraBD.JFieldDef;

/**
 * Error en el campo
 */
public class ECampoError extends Exception {
    private JFieldDef moCampo;
    /**
     * Constructor 
     */
    public ECampoError() {
        super("Campo erroneo");
    }
    /**
     * Constructor con un mensaje anadido
     * @param psMensaje mensaje anadido
     */
    public ECampoError(String psMensaje) {
        super("Campo Erroneo("+psMensaje+")");
    }
    /**
     * Constructor con un mensaje anadido
     * @param poCampo Campo origen del error
     * @param psMensaje mensaje anadido
     */
    public ECampoError(JFieldDef poCampo, String psMensaje) {
        super("El Campo '" + poCampo.getCaption() + "' es erroneo" +
                ( psMensaje==null || psMensaje.equals("") ? "" : "("+psMensaje+")")
                );
        moCampo=poCampo;
    }

    public JFieldDef getCampo(){
        return moCampo;
    }
}
